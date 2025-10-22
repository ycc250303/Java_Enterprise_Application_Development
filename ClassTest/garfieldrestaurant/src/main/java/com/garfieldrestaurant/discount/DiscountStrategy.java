package com.garfieldrestaurant.discount;

import com.garfieldrestaurant.model.Order;

/**
 * 折扣策略接口 (Strategy Pattern)。
 */
public interface DiscountStrategy {
    String getName();
    String getDescription();
    /**
     * 应用折扣到订单。
     * @param order 待折扣的订单。
     * @return 折扣后的最终价格。
     */
    double apply(Order order);
}