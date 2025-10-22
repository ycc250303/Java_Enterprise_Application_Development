package com.garfieldrestaurant.discount;
import java.util.List;

/**
 * 折扣策略工厂类。
 */
public class DiscountFactory {
    private static final List<DiscountStrategy> AVAILABLE_DISCOUNTS = List.of(
            new FullReductionDiscount(),
            new MargheritaHalfPriceDiscount()
    );

    private DiscountFactory() {}

    /**
     * 获取所有可用的折扣策略。
     * @return 折扣策略列表。
     */
    public static List<DiscountStrategy> getAvailableDiscounts() {
        return AVAILABLE_DISCOUNTS;
    }
}