package com.learnwy.servlet;

import com.learnwy.controller.RoleController;
import com.learnwy.controller.RolePowerController;
import com.learnwy.controller.UserRoleController;
import com.learnwy.model.Power;
import com.learnwy.model.Role;
import com.learnwy.model.RolePower;
import com.learnwy.model.User;
import com.learnwy.util.StringUtil;
import com.learnwy.util.TranValueClass;
import com.learnwy.util.json.PowerJson;
import com.learnwy.util.json.RoleJson;
import com.learnwy.util.json.UserJson;

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
 * Created by 25973 on 2017-05-10.
 */
@WebServlet(name = "RolePowerServlet", urlPatterns = {"/role_power_manage"})
public class RolePowerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        User login_user = (User) httpSession.getAttribute("u");
        String action = request.getParameter("action");
        if (StringUtil.isNullOrEmpty(action)) {
            String pageS = request.getParameter("page");
            long page = pageS == null ? 0 : StringUtil.parseToLong(pageS);
            TranValueClass rows = new TranValueClass(new Long(0));
            List<Role> listRole = RolePowerController.getAllRole(login_user, page, rows);
            List<Power> powers = RolePowerController.getAllPower(login_user);
            response.setContentType("JSON");
            PrintWriter pw = response.getWriter();
            pw.write("[");
            pw.write("[" + rows.getValue().toString() + ",");
            pw.write(RoleJson.ListToJson(listRole));
            pw.write("]");
            pw.write(",");
            pw.write(PowerJson.ListToJson(powers));
            pw.write("]");
            pw.flush();
            pw.close();
        } /*else if (StringUtil.update.equals(action)) {
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
            boolean isUp = RolePowerController.updateOrAddRole(power, login_user);
        } */ else if (StringUtil.query.equals(action)) {
            //id is userID
            String id = request.getParameter("id");
            long _id;
            if (StringUtil.isNullOrEmpty(id)) {
                _id = -1;
            } else {
                _id = Long.valueOf(id);
            }
            response.setContentType("JSON");
            PrintWriter pw = response.getWriter();
            pw.write("[");
            pw.write(String.valueOf(_id));
            pw.write(",");
            pw.write(RolePowerController.getPowersJsonByRoleID(login_user, _id));
            pw.write("]");
            pw.flush();
            pw.close();
        } else if (StringUtil.del.equals(action)) {
            String power_id = request.getParameter("power_id");
            String role_id = request.getParameter("role_id");
            long _power_id;
            long _role_id;
            if (StringUtil.isNullOrEmpty(power_id)) {
                _power_id = -1;
            } else {
                _power_id = Long.valueOf(power_id);
            }
            if (StringUtil.isNullOrEmpty(role_id)) {
                _role_id = -1;
            } else {
                _role_id = Long.valueOf(role_id);
            }
            boolean isDel = RolePowerController.delete(login_user, _power_id, _role_id);
        } else if (StringUtil.add.equals(action)) {
            String power_id = request.getParameter("power_id");
            String role_id = request.getParameter("role_id");
            long _power_id;
            long _role_id;
            if (StringUtil.isNullOrEmpty(power_id)) {
                _power_id = -1;
            } else {
                _power_id = Long.valueOf(power_id);
            }
            if (StringUtil.isNullOrEmpty(role_id)) {
                _role_id = -1;
            } else {
                _role_id = Long.valueOf(role_id);
            }
            boolean isAdd = false;
            if (_power_id == -1 || _role_id == -1) {

            } else {
                isAdd = RolePowerController.add(login_user, _power_id, _role_id);
            }
        }
        response.sendRedirect("/index.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(404);
    }
}
