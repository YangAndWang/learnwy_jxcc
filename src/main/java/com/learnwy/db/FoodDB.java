package com.learnwy.db;

import com.learnwy.db.mysql.MySQL;
import com.learnwy.model.Food;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class FoodDB {
    public static List<Food> getAllFoods() {
        LinkedList<Food> ret = new LinkedList<Food>();
        String sql = " select food_id,food_name,food_price,food_img_path  from `food`";
        ResultSet rs = MySQL.excuteSQL(sql);
        Food food = null;
        try {
            while (rs.next()) {
                food = new Food(rs.getLong(1), rs.getString(2), rs.getBigDecimal(3), rs.getString(4));
                ret.add(food);
            }
        } catch (Exception ex) {
        }
        return ret;
    }

    public static int deleteFood(Food food) {
        if (food == null) {
            return -1;
        }
        String sql = "delete from `food` where food_id = " + food.getFoodId();
        return MySQL.updateSQL(sql);
    }

    public static int updateOrAddFood(Food food) {
        if (food == null) {
            return -1;
        } else if (food.getFoodId() == -1) {
            return addFood(food);
        } else {
            return updateFood(food);
        }
    }

    public static int addFood(Food food) {
        String sql = "insert into `food`(food_name,food_price,food_img_path)values(" + "'" + food.getFoodName() + "'" + "," + food.getFoodPrice().toString() + "," + "'" + food.getFoodImgPath() + "'" + ")";
        int ret = MySQL.updateSQL(sql);
        return ret;
    }

    public static int updateFood(Food food) {
        if (food == null) {
            return -1;
        } else if (food.getFoodId() == -1) {
            return addFood(food);
        }
        String sql = "update `food` set " + "food_name = '" + food.getFoodName() + "'" + "," + "food_price = " + food.getFoodPrice().toString() + "," + "food_img_path = '" + food.getFoodImgPath() + "'" + " where food_id = " + food.getFoodId();
        return MySQL.updateSQL(sql);
    }
}