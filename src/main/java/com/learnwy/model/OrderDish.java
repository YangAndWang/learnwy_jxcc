package com.learnwy.model;

import java.math.BigDecimal;

public class OrderDish {
    private long order_dish_id;

    public long getOrderDishId() {
        return this.order_dish_id;
    }

    public void setOrderDishId(long order_dish_id) {
        this.order_dish_id = order_dish_id;
    }

    private long order_id;

    public long getOrderId() {
        return this.order_id;
    }

    public void setOrderId(long order_id) {
        this.order_id = order_id;
    }

    private long dish_id;

    public long getDishId() {
        return this.dish_id;
    }

    public void setDishId(long dish_id) {
        this.dish_id = dish_id;
    }

    private long nums;

    public long getNums() {
        return this.nums;
    }

    public void setNums(long nums) {
        this.nums = nums;
    }

    private BigDecimal current_price;

    public BigDecimal getCurrentPrice() {
        return this.current_price;
    }

    public void setCurrentPrice(BigDecimal current_price) {
        this.current_price = current_price;
    }

    private long state;

    public long getState() {
        return this.state;
    }

    public void setState(long state) {
        this.state = state;
    }

    public OrderDish(long order_dish_id, long order_id, long dish_id, long nums, BigDecimal current_price, long state) {
        this.order_dish_id = order_dish_id;
        this.order_id = order_id;
        this.dish_id = dish_id;
        this.nums = nums;
        this.current_price = current_price;
        this.state = state;
    }
}