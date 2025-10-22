package com.garfieldrestaurant.app;

import com.garfieldrestaurant.datasource.DataSourceException;
import com.garfieldrestaurant.discount.DiscountFactory;
import com.garfieldrestaurant.discount.DiscountStrategy;
import com.garfieldrestaurant.model.Order;
import com.garfieldrestaurant.model.food.Food;
import com.garfieldrestaurant.pdf.PdfGenerator;
import com.garfieldrestaurant.pdf.PdfGeneratorFactory;
import com.garfieldrestaurant.service.FoodService;
import com.garfieldrestaurant.util.ConsoleUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.*;

/**
 * 餐厅命令行应用入口 (CLI)。
 */
public class RestaurantApp {
    private final FoodService foodService;
    private final String foodHtmlPath = "src/main/resources/food.html"; // 假设文件路径
    public static final String DEST = "./hello.pdf";
    public RestaurantApp() {
        this.foodService = new FoodService(foodHtmlPath);
    }

    public static void main(String[] args) throws IOException{
        // Task 5: 打印 matriculation number 和 name
//        System.out.println("Matriculation Number: [Your Matriculation Number]");
//        System.out.println("Full Name: [Your Full Name]");
//
//        RestaurantApp app = new RestaurantApp();
//        app.start();
        PdfDocument pdf = new PdfDocument(new PdfWriter(DEST));
        Document document = new Document(pdf);
        String line = "Hello! Welcome to iTextPdf";
        document.add(new Paragraph(line));
        document.close();

        System.out.println("Awesome PDF just got created.");
    }

    public void start() {
        ConsoleUtils.printSeparator();
        System.out.println("--- Garfield 餐厅系统启动 ---");
        try {
            foodService.loadFoodData(); // Task 1: 数据检索
            System.out.println("✅ 食物数据加载成功。总项目数: " + foodService.getAllFood().size());
        } catch (DataSourceException e) {
            System.err.println("❌ 严重错误: 加载食物数据失败。请检查文件路径和内容。");
            System.err.println("错误详情: " + e.getMessage());
            return;
        }
        mainMenu();
    }

    private void mainMenu() {
        boolean running = true;
        while (running) {
            ConsoleUtils.printSeparator();
            System.out.println("--- 主菜单 ---");
            System.out.println("1. 查看菜单 (显示与排序)");
            System.out.println("2. 创建新订单 (点餐、折扣与报告)");
            System.out.println("3. 退出");

            int choice = ConsoleUtils.readInt("请选择操作 (1-3): ");
            switch (choice) {
                case 1:
                    viewMenu(); // Task 2
                    break;
                case 2:
                    placeOrder(); // Task 3 & 4
                    break;
                case 3:
                    System.out.println("感谢使用，系统退出。");
                    running = false;
                    break;
                default:
                    System.out.println("无效的选择，请重新输入。");
            }
        }
    }

    // --- Task 2: 菜单显示与排序 ---
    private void viewMenu() {
        Map<Class<? extends Food>, List<Food>> categories = foodService.getFoodCategories();
        List<Class<? extends Food>> foodClasses = categories.keySet().stream().toList();

        ConsoleUtils.printSeparator();
        System.out.println("--- 食物类别 ---");
        AtomicInteger index = new AtomicInteger(1);
        foodClasses.forEach(c -> System.out.printf("%d. %s\n", index.getAndIncrement(), c.getSimpleName()));

        int categoryIndex = ConsoleUtils.readInt("请选择要查看的类别编号: ");
        if (categoryIndex < 1 || categoryIndex > foodClasses.size()) {
            System.out.println("无效的类别编号。");
            return;
        }

        Class<? extends Food> selectedClass = foodClasses.get(categoryIndex - 1);

        String sortCriteria = promptForSorting(); // 提示排序
        List<? extends Food> sortedFoods = foodService.getFoodListByType(selectedClass, sortCriteria);

        displayFoodList(sortedFoods); // 显示列表
    }

    private String promptForSorting() {
        System.out.println("\n--- 排序选项 ---");
        System.out.println("1. 按卡路里排序");
        System.out.println("2. 按价格排序");
        System.out.println("3. 不排序");
        int sortChoice = ConsoleUtils.readInt("请选择排序方式 (1-3): ");
        return switch (sortChoice) {
            case 1 -> "calories";
            case 2 -> "price";
            default -> "none";
        };
    }

    private void displayFoodList(List<? extends Food> foods) {
        if (foods.isEmpty()) {
            System.out.println("该类别中没有食物项目。");
            return;
        }

        ConsoleUtils.printSeparator();
        System.out.printf("--- %s 菜单 ---\n", foods.get(0).getClass().getSimpleName());
        for (int i = 0; i < foods.size(); i++) {
            Food food = foods.get(i);
            System.out.printf("[%d] %s ($%.2f, %d kcal)\n", i + 1, food.getName(), food.getPrice(), food.getCaloriesKcal());
            // 利用多态显示特有属性
            System.out.printf("    详细: %s\n", food.getUniqueAttributesDisplay());
        }
    }

    // --- Task 3 & 4: 点餐、折扣和 PDF ---
    private void placeOrder() {
        Order currentOrder = new Order();
        boolean ordering = true;

        // 1. 点餐
        while (ordering) {
            System.out.printf("\n--- 当前订单: 小计 $%.2f | 总卡路里 %d kcal ---\n", currentOrder.getSubTotal(), currentOrder.getTotalCalories());
            String input = ConsoleUtils.readLine("输入食物名称 (或 'list' 查看所有, 'done' 结束点餐): ");

            if (input.equalsIgnoreCase("done")) {
                ordering = false;
            } else if (input.equalsIgnoreCase("list")) {
                displayAllFoodForOrder();
            } else {
                foodService.findFoodByName(input).ifPresentOrElse(food -> {
                    try {
                        int quantity = ConsoleUtils.readInt("请输入数量 (默认为 1): ");
                        if (quantity < 1) quantity = 1;
                        currentOrder.addItem(food, quantity);
                        System.out.printf("✅ 已添加 %d 份 '%s' 到订单。\n", quantity, food.getName());
                    } catch (Exception e) {
                        System.out.println("数量输入无效。");
                    }
                }, () -> System.out.println("❌ 未找到该食物项目。"));
            }
        }

        if (currentOrder.getItems().isEmpty()) {
            System.out.println("订单已取消，没有添加任何食物。");
            return;
        }

        // 2. 应用折扣策略 (Task 3)
        DiscountStrategy selectedDiscount = promptForDiscount();
        currentOrder.applyDiscount(selectedDiscount);

        // 打印最终总结
        printOrderSummary(currentOrder);

        // 3. PDF 报告生成 (Task 4)
        promptForPdfReport(currentOrder);
    }

    private void displayAllFoodForOrder() {
        ConsoleUtils.printSeparator();
        System.out.println("--- 可用食物列表 (请精确输入名称) ---");
        foodService.getAllFood().forEach(f ->
                System.out.printf("- %s ($%.2f)\n", f.getName(), f.getPrice()));
        ConsoleUtils.printSeparator();
    }

    private DiscountStrategy promptForDiscount() {
        List<DiscountStrategy> discounts = DiscountFactory.getAvailableDiscounts();
        ConsoleUtils.printSeparator();
        System.out.println("--- 选择折扣方案 (Task 3) ---");
        AtomicInteger index = new AtomicInteger(1);
        discounts.forEach(p -> System.out.printf("%d. %s: %s\n", index.getAndIncrement(), p.getName(), p.getDescription()));

        int choice = ConsoleUtils.readInt("请选择折扣编号: ");
        if (choice >= 1 && choice <= discounts.size()) {
            return discounts.get(choice - 1);
        }
        System.out.println("无效选择。默认不使用折扣。");
        return discounts.get(0); // 默认返回第一个策略 (FullReductionDiscount) - 也可以实现一个 NoDiscount 策略
    }

    private void printOrderSummary(Order order) {
        ConsoleUtils.printSeparator();
        System.out.println("--- 最终订单总结 ---");
        System.out.printf("  - 原始小计: $%.2f\n", order.getSubTotal());
        System.out.printf("  - 折扣策略: %s\n", order.getAppliedDiscount().getName());
        System.out.printf("  - 最终价格: $%.2f\n", order.getFinalPrice());
        System.out.printf("  - 总卡路里: %d kcal\n", order.getTotalCalories());
        ConsoleUtils.printSeparator();
    }

    private void promptForPdfReport(Order order) {
        List<PdfGenerator> generators = PdfGeneratorFactory.getAvailableGenerators();
        System.out.println("--- PDF 报告生成 (Task 4) ---");
        AtomicInteger index = new AtomicInteger(1);
        generators.forEach(g -> System.out.printf("%d. 使用 %s 生成报告\n", index.getAndIncrement(), g.getName()));
        System.out.println("0. 跳过 PDF 报告");

        int choice = ConsoleUtils.readInt("请选择报告生成器编号: ");

        if (choice == 0) {
            System.out.println("PDF 报告生成已跳过。");
            return;
        }

        if (choice >= 1 && choice <= generators.size()) {
            PdfGenerator generator = generators.get(choice - 1);
            String fileName = String.format("Garfield_Order_Report_%s.pdf", generator.getName().replace(" ", "_"));
            try {
                generator.generateReport(order, fileName);
                System.out.println("✅ PDF 报告生成成功。");
            } catch (IOException e) {
                System.err.println("❌ PDF 报告生成失败: " + e.getMessage());
            }
        } else {
            System.out.println("无效的生成器编号。PDF 报告生成已跳过。");
        }
    }
}