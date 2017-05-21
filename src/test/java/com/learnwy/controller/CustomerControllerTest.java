package com.learnwy.controller;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by 25973 on 2017-05-19.
 */
public class CustomerControllerTest {
    static long[] dish_ids = new long[]{127};
    static BigDecimal[] dish_prices = new BigDecimal[]{new BigDecimal("28.00")};
    static int[] dish_counts = new int[]{1};

    @Test
    public void addOrder() throws Exception {
        long order_no = CustomerController.addOrder(1);
        CustomerController.addOrderDishs(order_no, dish_ids, dish_prices, dish_counts);
    }

    @Test
    public void addOrderDishs() throws Exception {
    }

}