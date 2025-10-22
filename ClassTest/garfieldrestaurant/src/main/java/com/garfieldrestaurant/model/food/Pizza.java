package com.garfieldrestaurant.model.food;

/**
 * Pizza 类，继承自 Food，增加特有属性：Radius。
 */
public class Pizza extends Food {
    private final int radiusInches;

    public Pizza(String name, double weightGrams, int caloriesKcal, double price, String features, String imageUrl, int radiusInches) {
        super(name, weightGrams, caloriesKcal, price, features, imageUrl);
        this.radiusInches = radiusInches;
    }

    public int getRadiusInches() {
        return radiusInches;
    }

    @Override
    public String getUniqueAttributesDisplay() {
        return String.format("半径: %d 英寸, 特点: %s", radiusInches, getFeatures());
    }
}