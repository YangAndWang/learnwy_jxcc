package com.learnwy.servlet;

import com.learnwy.controller.PowerController;
import com.learnwy.model.Power;
import com.learnwy.model.User;
import com.learnwy.util.StringUtil;
import com.learnwy.util.TranValueClass;
import com.learnwy.util.json.PowerJson;

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
 * Created by 25973 on 2017-05-07.
 */
@WebServlet(name = "PowerServlet", urlPatterns = {"/power_manage"})
public class PowerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        User login_user = (User) httpSession.getAttribute("u");
        String action = request.getParameter("action");
        if (StringUtil.isNullOrEmpty(action)) {
            String pageS = request.getParameter("page");
            long page = pageS == null ? 0 : StringUtil.parseToLong(pageS);
            TranValueClass rows = new TranValueClass(new Long(0));
            List<Power> listPower = PowerController.getAllPower(login_user, page, rows);
            if (listPower.size() > 0) {
                response.setContentType("JSON");
                PrintWriter pw = response.getWriter();
                pw.write("[" + rows.getValue().toString() + ",");
                pw.write(PowerJson.ListToJson(listPower));
                pw.write("]");
                pw.flush();
                pw.close();
            } else {
                response.setStatus(403);
            }
        } else if (StringUtil.update.equals(action)) {
            //now is only can update
            String id = request.getParameter("id");
            String display_name = request.getParameter("display_name");
            display_name = StringUtil.trimPathLangCN(display_name);
            long _id;
            if (StringUtil.isNullOrEmpty(id)) {
                _id = -1;
            } else {
                _id = Long.valueOf(id);
            }
            Power power = new Power(display_name, _id);
            boolean isUp = PowerController.updateOrAddPower(power, login_user);
        }
        response.sendRedirect("/index.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(404);
    }
}
