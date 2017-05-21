package com.learnwy.controller;

import com.learnwy.model.Dish;
import com.learnwy.model.User;
import com.learnwy.util.TranValueClass;
import com.learnwy.util.json.DishJson;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by 25973 on 2017-05-18.
 */
public class CreateOrderControllerTest {
    static User login_user = new User("display_name", "fwy", "fwy", 1);

    @Test
    public void getDishList() throws Exception {
        TranValueClass rows = new TranValueClass();
        List<Dish> dishes = CreateOrderController.getDishList(login_user, 0,
                rows, "", -1);
        System.out.println("rows:" + rows.getValue());
        System.out.println(DishJson.ListToJson1(dishes));

    }

    static long[] dish_ids = new long[]{127};
    static BigDecimal[] dish_prices = new BigDecimal[]{new BigDecimal("28.00")};
    static int[] dish_counts = new int[]{1};
    long order_no = 127;

    @Test
    public void addOrder() throws Exception {
        long order = CreateOrderController.addOrderAndGetOrderNo(login_user, 11);
        CreateOrderController.addOrder(login_user, dish_ids, dish_prices, dish_counts, order);
    }

    @Test
    public void addOrderAndGetOrderNo() throws Exception {

    }

}