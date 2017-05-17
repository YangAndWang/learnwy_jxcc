package com.learnwy.util.json;

import com.learnwy.db.DishDB;
import com.learnwy.model.Dish;

import java.util.List;

/**
 * Created by 25973 on 2017-05-12.
 */
public class DishJson {

    public static String ListToJson1(List<Dish> listDish) {
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        for (Dish d : listDish) {
            sb.append("[");
            sb.append(d.getDishId()).append(",\"");
            sb.append(d.getDishName()).append("\",\"");
            sb.append(d.getDishImgPath()).append("\",");
            sb.append(d.getDishPrice().toString()).append(",");
            sb.append(d.getDishDiscount().toString()).append("],");
        }
        sb.setLength(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }

    public static String ListToJson2(List<DishDB.DishHasDictID> allDishsOrderByDictId) {
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        for (DishDB.DishHasDictID d : allDishsOrderByDictId) {
            sb.append("[");
            sb.append(d.getDict_id()).append(",\"");
            sb.append(d.getDish_id()).append("\",\"");
            sb.append(d.getDish_name()).append("\",");
            sb.append(d.getDish_price().toString()).append(",\"");
            sb.append(d.getDish_img_path()).append("\",");
            sb.append(d.getDish_discount().toString()).append("],");
        }
        sb.setLength(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }
}
