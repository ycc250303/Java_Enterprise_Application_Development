package com.garfieldrestaurant.model.food;

import java.util.Objects;

/**
 * 食物抽象基类，定义所有食物的共同属性和行为。
 * 遵循良好的封装原则。
 */
public abstract class Food {
    private final String name;
    private final double weightGrams;
    private final int caloriesKcal;
    private final double price;
    private final String features;
    private final String imageUrl;

    public Food(String name, double weightGrams, int caloriesKcal, double price, String features, String imageUrl) {
        this.name = Objects.requireNonNull(name, "食物名称不能为空。");
        this.weightGrams = weightGrams;
        this.caloriesKcal = caloriesKcal;
        this.price = price;
        this.features = Objects.requireNonNull(features, "食物特点不能为空。");
        this.imageUrl = Objects.requireNonNull(imageUrl, "图片URL不能为空。");
    }

    // Getters
    public String getName() { return name; }
    public double getWeightGrams() { return weightGrams; }
    public int getCaloriesKcal() { return caloriesKcal; }
    public double getPrice() { return price; }
    public String getFeatures() { return features; }
    public String getImageUrl() { return imageUrl; }

    /**
     * 抽象方法：获取食物特有属性的显示文本 (利用多态)。
     * @return 食物特有属性的字符串描述。
     */
    public abstract String getUniqueAttributesDisplay();

    /**
     * 用于在 OrderItem 中分组和比较的 equals/hashCode。
     * 仅基于名称和类类型，确保同名同类型的食物视为同一商品。
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return Objects.equals(name, food.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}