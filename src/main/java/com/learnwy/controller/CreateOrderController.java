package com.learnwy.controller;

import com.learnwy.db.DishDB;
import com.learnwy.db.OrderDB;
import com.learnwy.db.OrderDishDB;
import com.learnwy.model.Dish;
import com.learnwy.model.SysMenu;
import com.learnwy.model.User;
import com.learnwy.util.TranValueClass;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 25973 on 2017-05-12.
 */
public class CreateOrderController {
    /**
     * 通过dish_id或者dish_name来查询相似菜列表
     * @param login_user
     * @param page
     * @param rows
     * @param dish_name
     * @param dish_id
     * @return
     */
    public static List<Dish> getDishList(User login_user, long page, TranValueClass rows, String dish_name, long dish_id) {
        if (!checkPower(login_user)) {
            return new LinkedList<>();
        }
        if (dish_id == -1) {
            return DishDB.getDishsLikeName(page, rows, dish_name);
        }
        return DishDB.getDishsLikeId(page, rows, dish_id);
    }

    private static boolean checkPower(User login_user) {
        List<SysMenu> sysMenus = SysMenuController.getSysMenusByUser(login_user);
        for (SysMenu sysMenu : sysMenus) {
            if ("/create_order".equals(sysMenu.getPath())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据订单号插入所选菜的信息
     * @param login_user
     * @param dish_ids
     * @param dish_prices
     * @param dish_counts
     * @param order_no
     * @return -1 is no power
     */
    public static int addOrder(User login_user, long[] dish_ids, BigDecimal[] dish_prices, int[] dish_counts, long order_no) {
        if (!checkPower(login_user)) {
            return -1;
        }
        return OrderDishDB.addOrderDishs(dish_ids, dish_prices, dish_counts,order_no);
    }

    /**
     * 插入一条订单并将订单号返回
     * @param login_user
     * @param table_no
     * @return
     */
    public static long addOrderAndGetOrderNo(User login_user, int table_no) {
        if (!checkPower(login_user)) {
            return -1;
        }
        return OrderDB.addOrderAndGetOrderNo(table_no);
    }
}
