package com.garfieldrestaurant.pdf;

import com.garfieldrestaurant.model.Order;
import com.garfieldrestaurant.model.OrderItem;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.IOException;
import java.util.Comparator;

/**
 * 使用 iText Core 库生成 PDF 报告的实现 (Task 4)。
 */
public class ITextPdfGenerator implements PdfGenerator {

    @Override
    public String getName() {
        return "iText Core";
    }

    @Override
    public void generateReport(Order order, String outputPath) throws IOException {
        // 创建PDF文档
        PdfWriter writer = new PdfWriter(outputPath);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // 添加标题
        document.add(new Paragraph("Garfield 餐厅订单报告")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(20)
                .setBold());

        // 添加订单详情
        addOrderDetailsToDocument(document, order);

        // 关闭文档
        document.close();

        System.out.println(">>> [iText Core]: PDF 报告已保存到: " + outputPath);
    }

    private void addOrderDetailsToDocument(Document document, Order order) {
        // 添加食物项列表标题
        document.add(new Paragraph("--- 订单详情 ---")
                .setFontSize(16)
                .setBold());

        // 添加食物项列表
        List foodList = new List();
        order.getItems().stream()
              .sorted(Comparator.comparing(item -> item.getFood().getName()))
              .forEach(item -> {
                  String itemText = String.format("%s (x%d) | 价格: $%.2f | 卡路里: %d kcal",
                          item.getFood().getName(),
                          item.getQuantity(),
                          item.getSubTotal(),
                          item.getTotalCalories());
                  foodList.add(new ListItem(itemText));
              });
        document.add(foodList);

        // 添加折扣详情
        document.add(new Paragraph("\n--- 折扣详情 (b) ---")
                .setFontSize(14)
                .setBold());
        document.add(new Paragraph(String.format("  - 名称: %s", order.getAppliedDiscount().getName())));
        document.add(new Paragraph(String.format("  - 描述: %s", order.getAppliedDiscount().getDescription())));

        // 添加汇总信息
        document.add(new Paragraph("\n--- 汇总信息 (c) ---")
                .setFontSize(14)
                .setBold());
        document.add(new Paragraph(String.format("  - 原始总价: $%.2f", order.getSubTotal())));
        document.add(new Paragraph(String.format("  - 最终价格: $%.2f", order.getFinalPrice())));
        document.add(new Paragraph(String.format("  - 总卡路里: %d kcal", order.getTotalCalories())));

        // 添加食物详细属性
        document.add(new Paragraph("\n--- 食物详细属性 (d) ---")
                .setFontSize(14)
                .setBold());
        List attributeList = new List();
        order.getItems().stream()
              .map(OrderItem::getFood)
              .distinct()
              .forEach(food -> {
                  String attributeText = String.format("[%s]: %s",
                          food.getName(),
                          food.getUniqueAttributesDisplay());
                  attributeList.add(new ListItem(attributeText));
              });
        document.add(attributeList);
    }
}
