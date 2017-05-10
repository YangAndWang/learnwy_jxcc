package com.learnwy.model;

import java.util.Date;

public class Order {
    private long order_id;

    public long getOrderId() {
        return this.order_id;
    }

    public void setOrderId(long order_id) {
        this.order_id = order_id;
    }

    private Date create_date;

    public Date getCreateDate() {
        return this.create_date;
    }

    public void setCreateDate(Date create_date) {
        this.create_date = create_date;
    }

    private long table_no;

    public long getTableNo() {
        return this.table_no;
    }

    public void setTableNo(long table_no) {
        this.table_no = table_no;
    }
}