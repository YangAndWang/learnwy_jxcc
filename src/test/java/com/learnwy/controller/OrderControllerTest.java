package com.learnwy.controller;

import com.learnwy.model.User;
import com.learnwy.util.TranValueClass;
import com.learnwy.util.json.OrderJson;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by 25973 on 2017-05-19.
 */
public class OrderControllerTest {
    static User fwy = new User("服务员", "fwy", "fwy", 28);
    static User cs = new User("张涛", "zt", "zt", 27);

    @Test
    public void getAllNoCompleteOrder() throws Exception {
        TranValueClass fwy_rows = new TranValueClass();
        TranValueClass cs_rows = new TranValueClass();

        System.out.println(OrderJson.ListToJson1(OrderController.getAllNoCompleteOrder(fwy, 0, fwy_rows)));
        System.out.println("FWY_ROWS:" + fwy_rows.getValue().toString());
        System.out.println(OrderJson.ListToJson1(OrderController.getAllNoCompleteOrder(cs, 0, cs_rows)));
        System.out.println("CS_ROWS:" + cs_rows.getValue().toString());

    }

    @Test
    public void getDishsJson() throws Exception {
    }

    @Test
    public void completeDish() throws Exception {
    }

    @Test
    public void confirmOrder() throws Exception {
    }

}