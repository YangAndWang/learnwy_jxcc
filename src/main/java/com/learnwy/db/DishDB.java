package com.learnwy.db;

import com.learnwy.db.mysql.MySQL;
import com.learnwy.model.Dish;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class DishDB {
    public static List<Dish> getAllDishs() {
        LinkedList<Dish> ret = new LinkedList<Dish>();
        String sql = " select dish_id,dish_name,dish_price,dish_img_path  from `dish`";
        ResultSet rs = MySQL.excuteSQL(sql);
        Dish dish = null;
        try {
            while (rs.next()) {
                dish = new Dish(rs.getLong(1), rs.getString(2), rs.getBigDecimal(3), rs.getString(4));
                ret.add(dish);
            }
        } catch (Exception ex) {
        }
        return ret;
    }

    public static int deleteDish(Dish dish) {
        if (dish == null) {
            return -1;
        }
        String sql = "delete from `dish` where dish_id = " + dish.getDishId();
        return MySQL.updateSQL(sql);
    }

    public static int updateOrAddDish(Dish dish) {
        if (dish == null) {
            return -1;
        } else if (dish.getDishId() == -1) {
            return addDish(dish);
        } else {
            return updateDish(dish);
        }
    }

    public static int addDish(Dish dish) {
        String sql = "insert into `dish`(dish_name,dish_price,dish_img_path)values(" + "'" + dish.getDishName() + "'" + "," + dish.getDishPrice().toString() + "," + "'" + dish.getDishImgPath() + "'" + ")";
        int ret = MySQL.updateSQL(sql);
        return ret;
    }

    public static int updateDish(Dish dish) {
        if (dish == null) {
            return -1;
        } else if (dish.getDishId() == -1) {
            return addDish(dish);
        }
        String sql = "update `dish` set " + "dish_name = '" + dish.getDishName() + "'" + "," + "dish_price = " + dish.getDishPrice().toString() + "," + "dish_img_path = '" + dish.getDishImgPath() + "'" + " where dish_id = " + dish.getDishId();
        return MySQL.updateSQL(sql);
    }
}