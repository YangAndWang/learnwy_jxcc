package com.learnwy.servlet;

import com.learnwy.controller.CreateOrderController;
import com.learnwy.controller.CustomerController;
import com.learnwy.controller.OrderController;
import com.learnwy.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

/**
 * Created by 25973 on 2017-05-16.
 */
@WebServlet(name = "CustomerServlet", urlPatterns = {"/customer"})
public class CustomerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (StringUtil.isNullOrEmpty(action)) {
            String[] dish_ids = request.getParameterValues("dish_id");
            String[] dish_counts = request.getParameterValues("dish_count");
            String[] dish_prices = request.getParameterValues("dish_price");
            String table_no = request.getParameter("table_no");
            response.setContentType("JSON");
            PrintWriter pw = response.getWriter();

            long[] _dish_ids = null;
            int[] _dish_counts = null;
            BigDecimal[] _dish_current_prices = null;
            int _table_no = -1;
            if (dish_counts.length == dish_ids.length && dish_counts.length == dish_prices.length) {
                _dish_ids = new long[dish_ids.length];
                _dish_counts = new int[dish_ids.length];
                _dish_current_prices = new BigDecimal[dish_ids.length];
                for (int i = 0; i < dish_counts.length; i++) {
                    _dish_counts[i] = Integer.valueOf(dish_counts[i]);
                    _dish_ids[i] = Long.valueOf(dish_ids[i]);
                    _dish_current_prices[i] = new BigDecimal(dish_prices[i]);
                }
            }
            if (StringUtil.canParseLong(table_no)) {
                _table_no = Integer.valueOf(table_no);
            }
            long order_no = CustomerController.addOrder(_table_no);
            boolean isOk = CustomerController.addOrderDishs(order_no, _dish_ids, _dish_current_prices, _dish_counts);
            if (isOk) {
                WebSocketWY.orderFWY();
            }
            pw.write("[" + (order_no != -1 ? 1 : 0) + "]");
            pw.flush();
            pw.close();
            return;
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(404);
    }
}
