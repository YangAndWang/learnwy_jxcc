package com.learnwy.controller;

import com.learnwy.db.OrderDB;
import com.learnwy.db.OrderDishDB;
import com.learnwy.db.RoleDB;
import com.learnwy.db.UserRoleDB;
import com.learnwy.model.Order;
import com.learnwy.model.OrderDish;
import com.learnwy.model.SysMenu;
import com.learnwy.model.User;
import com.learnwy.servlet.WebSocketWY;
import com.learnwy.util.TranValueClass;
import com.learnwy.util.json.OrderJson;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by 25973 on 2017-05-13.
 */
public class OrderController {

    /**
     * 获取所有未完成的订单
     * 服务员获取的是所有未完成的
     * 厨师获取的是所有未完成且经服务员确认的
     *
     * @param login_user
     * @param page
     * @param rows
     * @return
     */
    public static List<Order> getAllNoCompleteOrder(User login_user, long page, TranValueClass rows) {
        if (!checkPower(login_user)) {
            return new LinkedList<>();
        }
        //判断用户角色来决定订单的状态
        long role_id = UserRoleDB.getRoleIdById(login_user.getUserId());
        if (role_id == 33) {
            //fwy
            return OrderDB.getAllNoCompleteOrder(page, rows);
        } else if (role_id == 32) {
            //cs
            return OrderDB.getAllNoCompleteOrderExpectCustomer(page, rows);
        }
        return new LinkedList<>();
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

    /**
     * 根据订单编号获取所有的订菜信息
     *
     * @param login_user
     * @param id
     * @return
     */
    public static String getDishsJson(User login_user, long id) {
        if (id == -1 || !checkPower(login_user)) {
            return "[]";
        }
        return OrderJson.ListToJson12(OrderDishDB.getDetailOrderDishs(id));
    }

    /**
     * 单个菜完成
     * 服务员完成则将状态改为4，如果全部完成，则将订单状态改为4
     * 厨师完成则将状态改为2
     *
     * @param login_user
     * @param order_id
     * @param dish_id
     * @return
     */
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
            WebSocketWY.completeFWY();
            return OrderDishDB.updateDishState2(order_id, dish_id) > 0;
        }
        return false;
        // maybe will use in future
        //return OrderDishDB.updateDishState(order_id, dish_id) > 0;
    }

    /**
     * 服务员确定订单，将订单的状态由8改为1
     * @param login_user
     * @param order_id
     * @param dish_id
     * @return
     */
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
