package com.learnwy.controller;

import com.learnwy.db.DictDB;
import com.learnwy.db.DishDB;
import com.learnwy.util.json.DictJson;
import com.learnwy.util.json.DishJson;

/**
 * 用于客户调用,不用检测权限
 */
public class DictController {

    /**
     * 获取所有的菜分类信息
     * @return
     */
    public static String getDictJson() {
        return DictJson.ListToJson(DictDB.getAllDicts());
    }

    /**
     * 获取所有的菜分类信息 ，根据分类的ID排序
     * @return
     */
    public static String getDishJson() {
        return DishJson.ListToJson2(DishDB.getAllDishsOrderByDictId());
    }
}
