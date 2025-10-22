package com.garfieldrestaurant.model.food;

/**
 * FrenchFries 类，继承自 Food，增加特有属性：Thickness。
 */
public class FrenchFries extends Food {
    private final String thickness;

    public FrenchFries(String name, double weightGrams, int caloriesKcal, double price, String features, String imageUrl, String thickness) {
        super(name, weightGrams, caloriesKcal, price, features, imageUrl);
        this.thickness = thickness;
    }

    public String getThickness() {
        return thickness;
    }

    @Override
    public String getUniqueAttributesDisplay() {
        return String.format("厚度: %s, 特点: %s", thickness, getFeatures());
    }
}