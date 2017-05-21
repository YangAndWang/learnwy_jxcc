package com.learnwy.db;

import com.learnwy.db.mysql.MySQL;
import com.learnwy.model.Order;
import com.learnwy.util.TranValueClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                order = new Order(rs.getLong(1), rs.getTimestamp(2), rs.getLong(3));
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

    final static String lockSql = "lock table `order` write";
    final static String unlockSql = "unlock tables";

    public static long addOrderAndGetOrderNo(int table_no) {
        long ret = -1;
        ResultSet rs;
        String addSql = "insert into `order`(table_no)values(" + table_no + ")";
        String getSql = "select `order_id` from `order` order by order_id desc limit 0,1";
        Connection conn = null;
        while (conn == null) {
            conn = MySQL.getNewConnection();
        }
        PreparedStatement preparedStatement;
        try {
            preparedStatement = conn.prepareStatement(lockSql);
            boolean isLock = preparedStatement.execute();
            preparedStatement = conn.prepareStatement(addSql);
            boolean addOk = preparedStatement.executeUpdate() > 0;
            if (addOk) {
                preparedStatement = conn.prepareStatement(getSql);
                rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    ret = rs.getLong(1);
                }
            }
            preparedStatement = conn.prepareStatement(unlockSql);
            boolean unLockOk = preparedStatement.execute();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static long addOrderState8AndGetOrderNo(int table_no) {
        long ret = -1;
        ResultSet rs;
        String addSql = "insert into `order`(table_no,state)values(" + table_no + ",8)";
        String getSql = "select `order_id` from `order` order by order_id desc limit 0,1";
        Connection conn = null;
        while (conn == null) {
            conn = MySQL.getNewConnection();
        }
        PreparedStatement preparedStatement;
        try {
            preparedStatement = conn.prepareStatement(lockSql);
            boolean isLock = preparedStatement.execute();
            preparedStatement = conn.prepareStatement(addSql);
            boolean addOk = preparedStatement.executeUpdate() > 0;
            if (addOk) {
                preparedStatement = conn.prepareStatement(getSql);
                rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    ret = rs.getLong(1);
                }
            }
            preparedStatement = conn.prepareStatement(unlockSql);
            boolean unLockOk = preparedStatement.execute();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static List<Order> getAllNoCompleteOrder(long page, TranValueClass rows) {
        List<Order> ret = new LinkedList<>();
        String sql = "SELECT count(order_id) from `order` where state in(1,2,8) ";
        String dataSQL = " SELECT order_id,create_date,table_no,state FROM `order` where state in (1,2,8) order by " +
                "create_date desc" +
                " " +
                "limit" +
                "  " + page * 10 + ",10";
        try {
            ResultSet rs = MySQL.excuteSQL(sql);
            rs.next();
            rows.setValue(Long.valueOf(rs.getLong(1)));
            rs = MySQL.excuteSQL(dataSQL);
            Order order;
            while (rs.next()) {
                order = new Order();
                order.setOrderId(rs.getLong(1));
                order.setCreateDate(rs.getTimestamp(2));
                order.setTableNo(rs.getLong(3));
                order.setState(rs.getLong(4));
                ret.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static int updateOrderState(long order_id) {
        if (order_id == -1) {
            return -1;
        }
        String sql = "update `order` set state = 1 where order_id = " + order_id;
        return MySQL.updateSQL(sql);
    }

    public static List<Order> getAllNoCompleteOrderExpectCustomer(long page, TranValueClass rows) {
        List<Order> ret = new LinkedList<>();
        String sql = "SELECT count(order_id) from `order` where state in (1,2) ";
        String dataSQL = " SELECT order_id,create_date,table_no,state FROM `order` where state in (1,2) order by " +
                "create_date desc" +
                " " +
                "limit" +
                "  " + page * 10 + ",10";
        try {
            ResultSet rs = MySQL.excuteSQL(sql);
            rs.next();
            rows.setValue(Long.valueOf(rs.getLong(1)));
            rs = MySQL.excuteSQL(dataSQL);
            Order order;
            while (rs.next()) {
                order = new Order();
                order.setOrderId(rs.getLong(1));
                order.setCreateDate(rs.getTimestamp(2));
                order.setTableNo(rs.getLong(3));
                order.setState(rs.getLong(4));
                ret.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }
}