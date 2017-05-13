package com.learnwy.util.json;

import com.learnwy.model.Order;
import com.learnwy.model.OrderDishDetail;

import java.util.List;

/**
 * Created by 25973 on 2017-05-13.
 */
public class OrderJson {

    /**
     * use for /order_manage
     * @param listOrder
     * @return
     */
    public static String ListToJson1(List<Order> listOrder) {
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        for (Order r : listOrder) {
            sb.append("[");
            sb.append(r.getOrderId()).append(",\"");
            sb.append(r.getCreateDate()).append("\"],");
        }
        sb.setLength(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }

    /**
     *
     * @param detailOrderDishs
     * @return
     */
    public static String ListToJson12(List<OrderDishDetail> detailOrderDishs) {
        return "";
    }
}
