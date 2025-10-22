package com.garfieldrestaurant.model;

import com.garfieldrestaurant.discount.DiscountStrategy;
import com.garfieldrestaurant.model.food.Food;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 订单类，管理订单项和应用的折扣策略。
 */
public class Order {
    private final List<OrderItem> items;
    private DiscountStrategy appliedDiscount;

    public Order() {
        this.items = new ArrayList<>();
    }

    /**
     * 添加食物到订单。如果食物已存在，则增加数量。
     */
    public void addItem(Food food, int quantity) {
        Optional<OrderItem> existingItem = items.stream()
                .filter(item -> item.getFood().equals(food))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().incrementQuantity(quantity);
        } else {
            this.items.add(new OrderItem(food, quantity));
        }
    }

    /**
     * 获取订单中所有食物项。
     */
    public List<OrderItem> getItems() {
        return items;
    }

    /**
     * 计算订单总价（未应用折扣）。
     */
    public double getSubTotal() {
        return items.stream().mapToDouble(OrderItem::getSubTotal).sum();
    }

    /**
     * 计算订单总卡路里。
     */
    public int getTotalCalories() {
        return items.stream().mapToInt(OrderItem::getTotalCalories).sum();
    }

    /**
     * 应用折扣策略。
     */
    public void applyDiscount(DiscountStrategy strategy) {
        this.appliedDiscount = strategy;
    }

    /**
     * 获取应用的折扣策略。
     */
    public DiscountStrategy getAppliedDiscount() {
        return appliedDiscount;
    }

    /**
     * 计算最终价格（应用折扣后）。
     */
    public double getFinalPrice() {
        if (appliedDiscount == null) {
            return getSubTotal();
        }
        return appliedDiscount.apply(this);
    }
}