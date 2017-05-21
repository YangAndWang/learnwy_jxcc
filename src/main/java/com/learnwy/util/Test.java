package com.learnwy.util;

import com.learnwy.controller.CreateOrderController;
import com.learnwy.controller.CustomerController;
import com.learnwy.model.User;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 25973 on 2017-05-10.
 */
public class Test {
    static User login_user = new User("display_name", "fwy", "fwy", 1);

    public static void main(String[] args) {
        // 将插入 15条数据
        // table_no 由 1 到 15，
        // dish_id 由 1 到 15，
        // dish_price 由 20 到 300
        // dish_count 由 1 到 15
//        for (int i = 1; i < 16; i++) {
//            int finalI = i;
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    long order_no = CustomerController.addOrder(finalI);
//                    CustomerController.addOrderDishs(order_no, new long[]{finalI}, new BigDecimal[]{new BigDecimal(String.valueOf(finalI * 20))}
//                            , new int[]{finalI});
//                }
//            }).start();
//        }
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

    }
}
