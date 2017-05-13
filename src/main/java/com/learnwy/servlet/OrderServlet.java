package com.learnwy.servlet;

import com.learnwy.controller.OrderController;
import com.learnwy.controller.PowerSysMenuController;
import com.learnwy.model.Order;
import com.learnwy.model.Power;
import com.learnwy.model.SysMenu;
import com.learnwy.model.User;
import com.learnwy.util.StringUtil;
import com.learnwy.util.TranValueClass;
import com.learnwy.util.json.OrderJson;
import com.learnwy.util.json.PowerJson;
import com.learnwy.util.json.SysMenuJson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by 25973 on 2017-05-13.
 */
@WebServlet(name = "OrderServlet", urlPatterns = {"/order_manage"})
public class OrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        User login_user = (User) httpSession.getAttribute("u");
        String action = request.getParameter("action");
        if (StringUtil.isNullOrEmpty(action)) {
            String pageS = request.getParameter("page");
            long page = pageS == null ? 0 : StringUtil.parseToLong(pageS);
            TranValueClass rows = new TranValueClass(new Long(0));
            List<Order> listOrder = OrderController.getAllNoCompleteOrder(login_user, page, rows);
            if (listOrder.size() > 0) {
                response.setContentType("JSON");
                PrintWriter pw = response.getWriter();
                pw.write("[");
                pw.write(rows.getValue().toString() + ",");
                pw.write(OrderJson.ListToJson1(listOrder));
                pw.write("]");
                pw.flush();
                pw.close();
            } else {
                response.setStatus(403);
            }
        } else if (StringUtil.query.equals(action)) {
            String id = request.getParameter("id");
            long _id;
            if (StringUtil.canParseLong(id)) {
                _id = Long.valueOf(id);
            } else {
                _id = -1;
            }
            response.setContentType("JSON");
            PrintWriter pw = response.getWriter();
            pw.write("[");
            pw.write(String.valueOf(_id));
            pw.write(",");
            pw.write(OrderController.getDishsJson(login_user, _id));
            pw.write("]");
            pw.flush();
            pw.close();
        }
        response.sendRedirect("/index.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(404);
    }
}
