package com.learnwy.db;

import com.learnwy.db.mysql.MySQL;
import com.learnwy.model.Dish;
import com.learnwy.model.Power;
import com.learnwy.util.StringUtil;
import com.learnwy.util.TranValueClass;

import java.math.BigDecimal;
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

    public static class DishHasDictID {
        long dict_id;
        long dish_id;
        String dish_name;
        BigDecimal dish_price;
        String dish_img_path;
        BigDecimal dish_discount;

        public DishHasDictID() {
        }

        public DishHasDictID(long dict_id, long dish_id, String dish_name, BigDecimal dish_price, String dish_img_path, BigDecimal dish_discount) {
            this.dict_id = dict_id;
            this.dish_id = dish_id;
            this.dish_name = dish_name;
            this.dish_price = dish_price;
            this.dish_img_path = dish_img_path;
            this.dish_discount = dish_discount;
        }

        public long getDict_id() {
            return dict_id;
        }

        public void setDict_id(long dict_id) {
            this.dict_id = dict_id;
        }

        public long getDish_id() {
            return dish_id;
        }

        public void setDish_id(long dish_id) {
            this.dish_id = dish_id;
        }

        public String getDish_name() {
            return dish_name;
        }

        public void setDish_name(String dish_name) {
            this.dish_name = dish_name;
        }

        public BigDecimal getDish_price() {
            return dish_price;
        }

        public void setDish_price(BigDecimal dish_price) {
            this.dish_price = dish_price;
        }

        public String getDish_img_path() {
            return dish_img_path;
        }

        public void setDish_img_path(String dish_img_path) {
            this.dish_img_path = dish_img_path;
        }

        public BigDecimal getDish_discount() {
            return dish_discount;
        }

        public void setDish_discount(BigDecimal dish_discount) {
            this.dish_discount = dish_discount;
        }
    }

    public static List<DishHasDictID> getAllDishsOrderByDictId() {
        LinkedList<DishHasDictID> ret = new LinkedList<DishHasDictID>();
        String sql = "SELECT dd.dict_id, d.dish_id, d.dish_name, d.dish_price, d.dish_img_path, d.dish_discount FROM dish_dict dd, dish d WHERE dd.dish_id = d.dish_id ORDER BY dict_id";
        ResultSet rs = MySQL.excuteSQL(sql);
        try {
            DishHasDictID dish = null;
            while (rs.next()) {
                dish = new DishHasDictID(rs.getLong(1), rs.getLong(2), rs.getString(3), rs.getBigDecimal(4), rs
                        .getString(5), rs.getBigDecimal(6));
                ret.add(dish);
            }
        } catch (Exception ex) {
        }
        return ret;
    }
}