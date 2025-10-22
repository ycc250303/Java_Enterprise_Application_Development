package com.garfieldrestaurant.service;

import com.garfieldrestaurant.datasource.DataSourceException;
import com.garfieldrestaurant.datasource.FoodDataSource;
import com.garfieldrestaurant.datasource.HtmlFoodDataSource;
import com.garfieldrestaurant.model.food.Food;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 食物业务服务层，负责数据的加载、存储、查询和排序 (Task 1 & 2)。
 */
public class FoodService {
    private final FoodDataSource dataSource;
    private List<Food> allFood;

    public FoodService(String foodHtmlPath) {
        // 使用 HtmlFoodDataSource 依赖
        this.dataSource = new HtmlFoodDataSource(foodHtmlPath);
    }

    /**
     * 加载食物数据。
     * @throws DataSourceException 如果数据加载失败。
     */
    public void loadFoodData() throws DataSourceException {
        this.allFood = dataSource.retrieveAllFood();
    }

    /**
     * 获取所有加载的食物。
     */
    public List<Food> getAllFood() {
        return Objects.requireNonNull(allFood, "食物数据未加载。");
    }

    /**
     * 按食物类型进行分组。
     */
    public Map<Class<? extends Food>, List<Food>> getFoodCategories() {
        return getAllFood().stream()
                .collect(Collectors.groupingBy(Food::getClass));
    }

    /**
     * 根据名称查找食物。
     */
    public Optional<Food> findFoodByName(String name) {
        return getAllFood().stream()
                .filter(f -> f.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    /**
     * 获取指定类型食物的列表，并根据条件排序 (Task 2)。
     * @param foodClass 食物类。
     * @param sortCriteria 排序标准 ("calories", "price", 或 "none")。
     * @return 排序后的食物列表。
     */
    @SuppressWarnings("unchecked")
    public <T extends Food> List<T> getFoodListByType(Class<T> foodClass, String sortCriteria) {
        List<T> filteredList = getAllFood().stream()
                .filter(foodClass::isInstance)
                .map(f -> (T) f) // 安全向下转型
                .collect(Collectors.toList());

        Comparator<T> comparator;
        switch (sortCriteria.toLowerCase()) {
            case "calories":
                comparator = Comparator.comparingInt(Food::getCaloriesKcal);
                break;
            case "price":
                comparator = Comparator.comparingDouble(Food::getPrice);
                break;
            case "none":
            default:
                return filteredList;
        }

        filteredList.sort(comparator);
        return filteredList;
    }
}