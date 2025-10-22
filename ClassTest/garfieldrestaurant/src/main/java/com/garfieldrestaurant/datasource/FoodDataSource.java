package com.garfieldrestaurant.datasource;

import com.garfieldrestaurant.model.food.Food;
import java.util.List;

/**
 * 食物数据源接口，定义数据检索方法。
 */
public interface FoodDataSource {
    /**
     * 检索并返回所有食物列表。
     * @return 包含所有食物的列表。
     * @throws DataSourceException 如果数据检索失败（如文件I/O错误或解析错误）。
     */
    List<Food> retrieveAllFood() throws DataSourceException;
}