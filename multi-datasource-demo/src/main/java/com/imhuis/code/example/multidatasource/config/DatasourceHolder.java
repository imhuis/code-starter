package com.imhuis.code.example.multidatasource.config;

/**
 * @author: imhuis
 * @date: 2021/12/22
 * @description:
 */
public class DatasourceHolder {

    private static final ThreadLocal<String> dataSources = new ThreadLocal<String>();

    // 设置数据源
    public static void setDataSource(String datasource) {
        dataSources.set(datasource);
    }

    // 获取数据源
    public static String getDataSource() {
        return dataSources.get();
    }

    // 清除数据源
    public static void clearDataSource() {
        dataSources.remove();
    }
}
