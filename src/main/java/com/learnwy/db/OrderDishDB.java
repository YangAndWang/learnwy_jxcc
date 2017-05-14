package com.learnwy.db;

import com.learnwy.db.mysql.MySQL;
import com.learnwy.model.OrderDish;
import com.learnwy.model.OrderDishDetail;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class OrderDishDB {
    public static List<OrderDish> getAllOrderDishs() {
        LinkedList<OrderDish> ret = new LinkedList<OrderDish>();
        String sql = " select order_dish_id,order_id,dish_id,nums,current_price,state  from `order_dish`";
        ResultSet rs = MySQL.excuteSQL(sql);
        OrderDish order_dish = null;
        try {
            while (rs.next()) {
                order_dish = new OrderDish(rs.getLong(1), rs.getLong(2), rs.getLong(3), rs.getLong(4), rs.getBigDecimal(5), rs.getLong(6));
                ret.add(order_dish);
            }
        } catch (Exception ex) {
        }
        return ret;
    }

    public static int deleteOrderDish(OrderDish order_dish) {
        if (order_dish == null) {
            return -1;
        }
        String sql = "delete from `order_dish` where order_dish_id = " + order_dish.getOrderDishId();
        return MySQL.updateSQL(sql);
    }

    public static int updateOrAddOrderDish(OrderDish order_dish) {
        if (order_dish == null) {
            return -1;
        } else if (order_dish.getOrderDishId() == -1) {
            return addOrderDish(order_dish);
        } else {
            return updateOrderDish(order_dish);
        }
    }

    public static int addOrderDish(OrderDish order_dish) {
        String sql = "insert into `order_dish`(order_id,dish_id,nums,current_price,state)values(" + order_dish.getOrderId() + "," + order_dish.getDishId() + "," + order_dish.getNums() + "," + order_dish.getCurrentPrice().toString() + "," + order_dish.getState() + ")";
        int ret = MySQL.updateSQL(sql);
        return ret;
    }

    public static int updateOrderDish(OrderDish order_dish) {
        if (order_dish == null) {
            return -1;
        } else if (order_dish.getOrderDishId() == -1) {
            return addOrderDish(order_dish);
        }
        String sql = "update `order_dish` set " + "order_id = " + order_dish.getOrderId() + "," + "dish_id = " + order_dish.getDishId() + "," + "nums = " + order_dish.getNums() + "," + "current_price = " + order_dish.getCurrentPrice().toString() + "," + "state = " + order_dish.getState() + " where order_dish_id = " + order_dish.getOrderDishId();
        return MySQL.updateSQL(sql);
    }

    /**
     * uncom
     *
     * @param dish_ids
     * @param dish_prices
     * @param dish_counts
     * @param order_no
     * @return
     */
    public static int addOrderDishs(long[] dish_ids, BigDecimal[] dish_prices, int[] dish_counts, long order_no) {
        StringBuffer sb = new StringBuffer("insert into `order_dish`(`order_id`,`dish_id`,`nums`,`current_price`," +
                "`state`)values");
        int i = 0;
        int len = dish_ids.length;
        for (i = 0; i < len; i++) {
            if (dish_ids[i] != -1 && dish_counts[i] > 0) {
                sb.append("(").append(order_no).append(",").append(dish_ids[i]).append(",").append(dish_counts[i])
                        .append(",").append(dish_prices[i]).append(",").append(1)
                        .append("),");
            }
        }
        sb.setLength(sb.length() - 1);
        if (len > 0) {
            return MySQL.updateSQL(sb.toString());
        }
        return 0;
    }

    /**
     * order.state 是订单状态 1是服务员填写,2是客户自己选择并付款的,4是服务员选择并且付款的(认为吃完结算),8是已经完成的订单,0001 0010 0100,1000
     *
     * @param id
     * @return
     */
    public static List<OrderDishDetail> getDetailOrderDishs(long id) {
        List<OrderDishDetail> ret = new LinkedList<>();
        String sql = "SELECT distinct d.dish_name, od.nums,d.dish_id,od.state FROM `order` o, order_dish od, dish d " +
                "WHERE" +
                " o.order_id = od.order_id AND od.dish_id = d.dish_id AND o.state in (1,2) AND o.order_id = " + id;
        ResultSet rs = MySQL.excuteSQL(sql);
        try {
            while (rs.next()) {
                ret.add(new OrderDishDetail(rs.getString(1), rs.getLong(2), rs.getLong(3), rs.getLong(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * order_dish.state 是当前菜的状态 1未做,2做完,####4已上菜
     *
     * @param order_id
     * @param dish_id
     * @return
     */
    public static int updateDishState(long order_id, long dish_id) {
        String sql = "update order_dish set state = CASE (state) WHEN 2 THEN 4 ELSE 2 END where order_id = " + order_id + " and dish_id = " + dish_id;
        return MySQL.updateSQL(sql);
    }

    public static int updateDishState4(long order_id, long dish_id) {
        String sql = "update order_dish set state = 4 where order_id = " + order_id + " and dish_id = " + dish_id;
        return MySQL.updateSQL(sql);
    }

    public static int updateDishState2(long order_id, long dish_id) {
        String sql = "update order_dish set state = 2 where order_id = " + order_id + " and dish_id = " + dish_id;
        return MySQL.updateSQL(sql);
    }
}