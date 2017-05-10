package com.learnwy.model;

import java.math.BigDecimal;

public class Food {
    private long food_id;

    public long getFoodId() {
        return this.food_id;
    }

    public void setFoodId(long food_id) {
        this.food_id = food_id;
    }

    private String food_name;

    public String getFoodName() {
        return this.food_name;
    }

    public void setFoodName(String food_name) {
        this.food_name = food_name;
    }

    private BigDecimal food_price;

    public BigDecimal getFoodPrice() {
        return this.food_price;
    }

    public void setFoodPrice(BigDecimal food_price) {
        this.food_price = food_price;
    }

    private String food_img_path;

    public String getFoodImgPath() {
        return this.food_img_path;
    }

    public void setFoodImgPath(String food_img_path) {
        this.food_img_path = food_img_path;
    }

    public Food(long food_id, String food_name, BigDecimal food_price, String food_img_path) {
        this.food_id = food_id;
        this.food_name = food_name;
        this.food_price = food_price;
        this.food_img_path = food_img_path;
    }
}