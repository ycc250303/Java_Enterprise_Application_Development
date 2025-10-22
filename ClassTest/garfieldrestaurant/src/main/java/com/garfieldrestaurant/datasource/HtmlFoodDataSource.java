package com.garfieldrestaurant.datasource;

import com.garfieldrestaurant.model.food.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 从 food.html 文件中检索食物数据。
 * 使用 Jsoup 库进行 HTML 解析。
 */
public class HtmlFoodDataSource implements FoodDataSource {
    private final String filePath;

    public HtmlFoodDataSource(String filePath) {
        this.filePath = Objects.requireNonNull(filePath);
    }

    @Override
    public List<Food> retrieveAllFood() throws DataSourceException {
        File input = new File(filePath);
        if (!input.exists() || !input.canRead()) {
            throw new DataSourceException("无法读取文件: " + filePath + "。文件不存在或无法访问。");
        }

        try {
            Document doc = Jsoup.parse(input, "UTF-8", "");
            List<Food> allFood = new ArrayList<>();
            allFood.addAll(retrievePizzas(doc));
            allFood.addAll(retrieveFrenchFries(doc));
            allFood.addAll(retrieveFriedChicken(doc));
            return allFood;

        } catch (IOException e) {
            throw new DataSourceException("文件 I/O 错误: " + filePath, e);
        }
    }

    // --- 解析特定食物类型的方法 ---

    private List<Food> retrievePizzas(Document doc) {
        List<Food> pizzas = new ArrayList<>();
        Elements pizzaElements = doc.select(".pizza-menu .pizza");

        for (Element element : pizzaElements) {
            try {
                String name = getElementText(element, "h2");
                double weight = parseDouble(getElementText(element, "p:contains(Weight)").replace("Weight: ", "").replace("g", ""));
                int calories = parseInt(getElementText(element, "p:contains(Calories)").replace("Calories: ", "").replace(" kcal", ""));
                double price = parsePrice(getElementText(element, ".price"));
                String features = getElementText(element, ".features").replace("Features: ", "");
                String imageUrl = element.select("img").attr("src");
                // Pizza unique attribute
                int radius = parseInt(getElementText(element, "p:contains(Radius)").replace("Radius: ", "").replace(" inches", ""));

                pizzas.add(new Pizza(name, weight, calories, price, features, imageUrl, radius));
            } catch (Exception e) {
                System.err.println("解析 Pizza item 时出错，跳过该项: " + e.getMessage());
            }
        }
        return pizzas;
    }

    private List<Food> retrieveFrenchFries(Document doc) {
        List<Food> fries = new ArrayList<>();
        Elements fryElements = doc.select(".pizza-menu .french-fries");

        for (Element element : fryElements) {
            try {
                String name = getElementText(element, "h2");
                double weight = parseDouble(getElementText(element, "p:contains(Weight)").replace("Weight: ", "").replace("g", ""));
                int calories = parseInt(getElementText(element, "p:contains(Calories)").replace("Calories: ", "").replace(" kcal", ""));
                double price = parsePrice(getElementText(element, ".price"));
                String features = getElementText(element, ".features").replace("Features: ", "");
                String imageUrl = element.select("img").attr("src");
                // FrenchFries unique attribute
                String thickness = getElementText(element, "p:contains(Thickness)").replace("Thickness: ", "");

                fries.add(new FrenchFries(name, weight, calories, price, features, imageUrl, thickness));
            } catch (Exception e) {
                System.err.println("解析 French Fries item 时出错，跳过该项: " + e.getMessage());
            }
        }
        return fries;
    }

    private List<Food> retrieveFriedChicken(Document doc) {
        List<Food> chickens = new ArrayList<>();
        Elements chickenElements = doc.select(".pizza-menu .fried-chicken");

        for (Element element : chickenElements) {
            try {
                String name = getElementText(element, "h2");
                double weight = parseDouble(getElementText(element, "p:contains(Weight)").replace("Weight: ", "").replace("g", ""));
                int calories = parseInt(getElementText(element, "p:contains(Calories)").replace("Calories: ", "").replace(" kcal", ""));
                double price = parsePrice(getElementText(element, ".price"));
                String features = getElementText(element, ".features").replace("Features: ", "");
                String imageUrl = element.select("img").attr("src");
                // FriedChicken unique attribute
                String spiciness = getElementText(element, "p:contains(Spiciness)").replace("Spiciness: ", "");

                chickens.add(new FriedChicken(name, weight, calories, price, features, imageUrl, spiciness));
            } catch (Exception e) {
                System.err.println("解析 Fried Chicken item 时出错，跳过该项: " + e.getMessage());
            }
        }
        return chickens;
    }

    // --- 健壮的数据解析工具方法 ---

    private String getElementText(Element parent, String cssQuery) {
        Element element = parent.selectFirst(cssQuery);
        // 处理数据缺失
        return Optional.ofNullable(element)
                .map(Element::text)
                .orElseThrow(() -> new IllegalArgumentException("缺少必需元素: " + cssQuery));
    }

    private double parseDouble(String value) {
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("无法解析 double 类型: " + value, e);
        }
    }

    private int parseInt(String value) {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("无法解析 integer 类型: " + value, e);
        }
    }

    private double parsePrice(String priceString) {
        // 移除 '$' 符号
        if (priceString.startsWith("$")) {
            return parseDouble(priceString.substring(1));
        }
        return parseDouble(priceString);
    }
}