package com.garfieldrestaurant.model;

import com.garfieldrestaurant.model.food.Food;

/**
 * 订单项，用于记录订单中某一食物及其数量。
 */
public class OrderItem {
    private final Food food;
    private int quantity;

    public OrderItem(Food food, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("数量必须大于0。");
        }
        this.food = food;
        this.quantity = quantity;
    }

    public Food getFood() {
        return food;
    }

    public int getQuantity() {
        return quantity;
    }

    public void incrementQuantity(int amount) {
        if (amount <= 0) {
            return;
        }
        this.quantity += amount;
    }

    /**
     * 计算该订单项的总价格（未折扣）。
     */
    public double getSubTotal() {
        return food.getPrice() * quantity;
    }

    /**
     * 计算该订单项的总卡路里。
     */
    public int getTotalCalories() {
        return food.getCaloriesKcal() * quantity;
    }
}