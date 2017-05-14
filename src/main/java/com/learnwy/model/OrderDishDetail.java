package com.learnwy.model;

/**
 * Created by 25973 on 2017-05-13.
 */
public class OrderDishDetail {
    String dish_name;
    long nums;
    long dish_id;

    public long getState() {
        return state;
    }

    public void setState(long state) {
        this.state = state;
    }

    public OrderDishDetail(String dish_name, long nums, long dish_id, long state) {
        this.dish_name = dish_name;
        this.nums = nums;
        this.dish_id = dish_id;
        this.state = state;
    }

    long state;

    public OrderDishDetail(String dish_name, long nums, long dish_id) {
        this.dish_name = dish_name;
        this.nums = nums;
        this.dish_id = dish_id;
    }

    public long getDish_id() {
        return dish_id;
    }

    public void setDish_id(long dish_id) {
        this.dish_id = dish_id;
    }

    public OrderDishDetail() {
    }

    public OrderDishDetail(String dish_name, long nums) {
        this.dish_name = dish_name;
        this.nums = nums;
    }


    public String getDish_name() {
        return dish_name;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    public long getNums() {
        return nums;
    }

    public void setNums(long nums) {
        this.nums = nums;
    }
}
