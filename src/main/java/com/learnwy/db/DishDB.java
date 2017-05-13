package com.learnwy.db;

import com.learnwy.db.mysql.MySQL;
import com.learnwy.model.Dish;
import com.learnwy.model.Power;
import com.learnwy.util.StringUtil;
import com.learnwy.util.TranValueClass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DishDB {
    public static List<Dish> getAllDishs() {
        LinkedList<Dish> ret = new LinkedList<Dish>();
        String sql = " select distinct dish_id,dish_name,dish_price,dish_img_path,dish_discount  from `dish`";
        ResultSet rs = MySQL.excuteSQL(sql);
        Dish dish = null;
        try {
            while (rs.next()) {
                dish = new Dish(rs.getLong(1), rs.getString(2), rs.getBigDecimal(3), rs.getString(4), rs.getBigDecimal(5));
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
        String sql = "insert into `dish`(dish_name,dish_price,dish_img_path,dish_discount)values(" + "'" + dish.getDishName() + "'" + "," + dish.getDishPrice().toString() + "," + "'" + dish.getDishImgPath() + "'" + "," + dish.getDishDiscount().toString() + ")";
        int ret = MySQL.updateSQL(sql);
        return ret;
    }

    public static int updateDish(Dish dish) {
        if (dish == null) {
            return -1;
        } else if (dish.getDishId() == -1) {
            return addDish(dish);
        }
        String sql = "update `dish` set " + "dish_name = '" + dish.getDishName() + "'" + "," + "dish_price = " + dish.getDishPrice().toString() + "," + "dish_img_path = '" + dish.getDishImgPath() + "'" + "," + "dish_discount = " + dish.getDishDiscount().toString() + " where dish_id = " + dish.getDishId();
        return MySQL.updateSQL(sql);
    }

    public static List<Dish> getDishsLikeName(long page, TranValueClass rows, String dish_name) {
        List<Dish> ret = new LinkedList<>();
        if (page == -1) {
            page = 0;
        }
        if (StringUtil.isNullOrEmpty(dish_name)) {
            dish_name = "";
        }
        String sql1 = "SELECT count(dish_id) from dish where dish_name like '%" + dish_name + "%'";
        String sql2 = " select distinct dish_id,dish_name,dish_price,dish_img_path,dish_discount  from `dish` where " +
                "dish_name like '%" + dish_name + "%'  limit  " + page * 10 + ",10";
        try {
            ResultSet rs = MySQL.excuteSQL(sql1);
            rs.next();
            rows.setValue(Long.valueOf(rs.getLong(1)));
            rs = MySQL.excuteSQL(sql2);
            while (rs.next()) {
                ret.add(new Dish(rs.getLong(1), rs.getString(2), rs.getBigDecimal(3), rs.getString(4), rs
                        .getBigDecimal(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static List<Dish> getDishsLikeId(long page, TranValueClass rows, long dish_id) {
        List<Dish> ret = new LinkedList<>();
        String likeSQL;
        if (page == -1) {
            page = 0;
        }
        if (dish_id == -1) {
            likeSQL = "";
        } else {
            likeSQL = " and dish_id like '%" + dish_id + "%' ";
        }
        String sql1 = "SELECT count(dish_id) from dish where 1=1" + likeSQL;
        String sql2 = " select distinct dish_id,dish_name,dish_price,dish_img_path,dish_discount  from `dish` where 1=1" +
                likeSQL + " limit " + page * 10 + ",10";
        try {
            ResultSet rs = MySQL.excuteSQL(sql1);
            rs.next();
            rows.setValue(Long.valueOf(rs.getLong(1)));
            rs = MySQL.excuteSQL(sql2);
            while (rs.next()) {
                ret.add(new Dish(rs.getLong(1), rs.getString(2), rs.getBigDecimal(3), rs.getString(4), rs
                        .getBigDecimal(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }
}