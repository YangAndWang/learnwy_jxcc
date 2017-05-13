package com.learnwy.controller;

import com.learnwy.db.OrderDB;
import com.learnwy.db.OrderDishDB;
import com.learnwy.model.Order;
import com.learnwy.model.SysMenu;
import com.learnwy.model.User;
import com.learnwy.util.TranValueClass;
import com.learnwy.util.json.OrderJson;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by 25973 on 2017-05-13.
 */
public class OrderController {

    public static List<Order> getAllNoCompleteOrder(User login_user, long page, TranValueClass rows) {
        if (!checkPower(login_user)) {
            return new LinkedList<>();
        }
        return OrderDB.getgetAllNoCompleteOrder(page, rows);
    }

    private static boolean checkPower(User login_user) {
        List<SysMenu> sysMenus = SysMenuController.getSysMenusByUser(login_user);
        for (SysMenu sysMenu : sysMenus) {
            if ("/order_manage".equals(sysMenu.getPath())) {
                return true;
            }
        }
        return false;
    }

    public static String getDishsJson(User login_user, long id) {
        if (id == -1 || !checkPower(login_user)) {
            return "[]";
        }
        return OrderJson.ListToJson12(OrderDishDB.getDetailOrderDishs(id));
    }
}
