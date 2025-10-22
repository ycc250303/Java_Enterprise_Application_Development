package com.garfieldrestaurant.discount;

import com.garfieldrestaurant.model.Order;

/**
 * 满减折扣策略：满 $100 减 $50。
 */
public class FullReductionDiscount implements DiscountStrategy {
    private static final double THRESHOLD = 100.0;
    private static final double REDUCTION = 50.0;

    @Override
    public String getName() {
        return "满减优惠";
    }

    @Override
    public String getDescription() {
        return String.format("订单总额满 $%.2f 减 $%.2f。", THRESHOLD, REDUCTION);
    }

    @Override
    public double apply(Order order) {
        double subTotal = order.getSubTotal();
        if (subTotal >= THRESHOLD) {
            return subTotal - REDUCTION;
        }
        return subTotal;
    }
}