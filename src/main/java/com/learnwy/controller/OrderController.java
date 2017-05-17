package com.learnwy.controller;

import com.learnwy.db.OrderDB;
import com.learnwy.db.OrderDishDB;
import com.learnwy.db.RoleDB;
import com.learnwy.db.UserRoleDB;
import com.learnwy.model.Order;
import com.learnwy.model.OrderDish;
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
        return OrderDB.getAllNoCompleteOrder(page, rows);
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

    public static boolean completeDish(User login_user, long order_id, long dish_id) {
        if (!checkPower(login_user)) {
            return false;
        }
        //判断用户角色来决定订单的状态
        long role_id = UserRoleDB.getRoleIdById(login_user.getUserId());
        if (role_id == 33) {
            //fwy
            return OrderDishDB.updateDishState4(order_id, dish_id) > 0;
        } else if (role_id == 32) {
            //cs
            return OrderDishDB.updateDishState2(order_id, dish_id) > 0;
        }
        return false;
        // maybe will use in future
        //return OrderDishDB.updateDishState(order_id, dish_id) > 0;
    }

    public static boolean confirmOrder(User login_user, long order_id, long dish_id) {
        if (!checkPower(login_user)) {
            return false;
        }
        //判断用户角色来决定订单的状态
        long role_id = UserRoleDB.getRoleIdById(login_user.getUserId());
        if (role_id == 33) {
            //fwy
            return OrderDB.updateOrderState(order_id) > 0;
        }
        return false;
        // maybe will use in future
        //return OrderDishDB.updateDishState(order_id, dish_id) > 0;
    }
}
