package com.garfieldrestaurant.discount;

import com.garfieldrestaurant.model.Order;
import com.garfieldrestaurant.model.OrderItem;

public class MargheritaHalfPriceDiscount implements DiscountStrategy {
    private static final String TARGET_FOOD = "Margherita Pizza";
    private static final double DISCOUNT_RATE = 0.5; // 50% off

    @Override
    public String getName() {
        return "玛格丽塔半价";
    }

    @Override
    public String getDescription() {
        return String.format("'%s' 半价优惠。", TARGET_FOOD);
    }

    @Override
    public double apply(Order order) {
        double finalPrice = 0.0;
        for (OrderItem item : order.getItems()) {
            if (item.getFood().getName().equalsIgnoreCase(TARGET_FOOD)) {
                // Margherita Pizza 半价
                finalPrice += item.getSubTotal() * DISCOUNT_RATE;
            } else {
                // 其他食物全价
                finalPrice += item.getSubTotal();
            }
        }
        return finalPrice;
    }
}