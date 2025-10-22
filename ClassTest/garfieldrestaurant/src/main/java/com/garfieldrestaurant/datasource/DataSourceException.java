package com.garfieldrestaurant.datasource;

/**
 * 自定义数据源异常，用于封装文件I/O或数据解析错误。
 */
public class DataSourceException extends RuntimeException {
    public DataSourceException(String message) {
        super(message);
    }

    public DataSourceException(String message, Throwable cause) {
        super(message, cause);
    }
}