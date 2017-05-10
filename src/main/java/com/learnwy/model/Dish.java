package com.learnwy.model;

import java.math.BigDecimal;

public class Dish {
    private long dish_id;

    public long getDishId() {
        return this.dish_id;
    }

    public void setDishId(long dish_id) {
        this.dish_id = dish_id;
    }

    private String dish_name;

    public String getDishName() {
        return this.dish_name;
    }

    public void setDishName(String dish_name) {
        this.dish_name = dish_name;
    }

    private BigDecimal dish_price;

    public BigDecimal getDishPrice() {
        return this.dish_price;
    }

    public void setDishPrice(BigDecimal dish_price) {
        this.dish_price = dish_price;
    }

    private String dish_img_path;

    public String getDishImgPath() {
        return this.dish_img_path;
    }

    public void setDishImgPath(String dish_img_path) {
        this.dish_img_path = dish_img_path;
    }

    public Dish(long dish_id, String dish_name, BigDecimal dish_price, String dish_img_path) {
        this.dish_id = dish_id;
        this.dish_name = dish_name;
        this.dish_price = dish_price;
        this.dish_img_path = dish_img_path;
    }
}