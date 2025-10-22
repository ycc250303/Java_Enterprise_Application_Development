package com.garfieldrestaurant.model.food;

/**
 * FriedChicken 类，继承自 Food，增加特有属性：Spiciness。
 */
public class FriedChicken extends Food {
    private final String spiciness;

    public FriedChicken(String name, double weightGrams, int caloriesKcal, double price, String features, String imageUrl, String spiciness) {
        super(name, weightGrams, caloriesKcal, price, features, imageUrl);
        this.spiciness = spiciness;
    }

    public String getSpiciness() {
        return spiciness;
    }

    @Override
    public String getUniqueAttributesDisplay() {
        return String.format("辣度: %s, 特点: %s", spiciness, getFeatures());
    }
}