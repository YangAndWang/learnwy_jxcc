package com.learnwy.db;

import com.learnwy.db.mysql.MySQL;
import com.learnwy.model.Order;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class OrderDB {
    public static List<Order> getAllOrders() {
        LinkedList<Order> ret = new LinkedList<Order>();
        String sql = " select order_id,create_date,table_no  from `order`";
        ResultSet rs = MySQL.excuteSQL(sql);
        Order order = null;
        try {
            while (rs.next()) {
                order = new Order(rs.getLong(1), rs.getDate(2), rs.getLong(3));
                ret.add(order);
            }
        } catch (Exception ex) {
        }
        return ret;
    }

    public static int deleteOrder(Order order) {
        if (order == null) {
            return -1;
        }
        String sql = "delete from `order` where order_id = " + order.getOrderId();
        return MySQL.updateSQL(sql);
    }

    public static int updateOrAddOrder(Order order) {
        if (order == null) {
            return -1;
        } else if (order.getOrderId() == -1) {
            return addOrder(order);
        } else {
            return updateOrder(order);
        }
    }

    public static int addOrder(Order order) {
        String sql = "insert into `order`(create_date,table_no)values(" + "'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getCreateDate()) + "'" + "," + order.getTableNo() + ")";
        int ret = MySQL.updateSQL(sql);
        return ret;
    }

    public static int updateOrder(Order order) {
        if (order == null) {
            return -1;
        } else if (order.getOrderId() == -1) {
            return addOrder(order);
        }
        String sql = "update `order` set " + "create_date = '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getCreateDate()) + "'" + "," + "table_no = " + order.getTableNo() + " where order_id = " + order.getOrderId();
        return MySQL.updateSQL(sql);
    }
}