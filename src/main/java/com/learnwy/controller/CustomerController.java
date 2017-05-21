package com.learnwy.controller;

import com.learnwy.db.OrderDB;
import com.learnwy.db.OrderDishDB;

import java.math.BigDecimal;

/**
 * Created by 25973 on 2017-05-16.
 */
public class CustomerController {

    /**
     * 客户插入一个订单并返回
     * @param table_no
     * @return
     */
    public static long addOrder(int table_no) {
        return OrderDB.addOrderState8AndGetOrderNo(table_no);
    }

    /**
     * 客户根据订单号插入菜信息
     * @param order_no
     * @param dish_ids
     * @param dish_current_prices
     * @param dish_counts
     * @return
     */
    public static boolean addOrderDishs(long order_no, long[] dish_ids, BigDecimal[] dish_current_prices, int[]
            dish_counts) {
        return OrderDishDB.addOrderDishs(dish_ids, dish_current_prices, dish_counts, order_no) > 0;
    }
}
