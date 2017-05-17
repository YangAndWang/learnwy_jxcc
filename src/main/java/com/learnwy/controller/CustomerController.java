package com.learnwy.controller;

import com.learnwy.db.OrderDB;
import com.learnwy.db.OrderDishDB;

import java.math.BigDecimal;

/**
 * Created by 25973 on 2017-05-16.
 */
public class CustomerController {

    public static long addOrder(int table_no) {
        return OrderDB.addOrderState8AndGetOrderNo(table_no);
    }

    public static boolean addOrderDishs(long order_no, long[] dish_ids, BigDecimal[] dish_current_prices, int[]
            dish_counts) {
        return OrderDishDB.addOrderDishs(dish_ids, dish_current_prices, dish_counts, order_no) > 0;
    }
}
