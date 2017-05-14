package com.learnwy.servlet;

import com.learnwy.controller.RoleController;
import com.learnwy.controller.UserRoleController;
import com.learnwy.model.Role;
import com.learnwy.model.User;
import com.learnwy.util.StringUtil;
import com.learnwy.util.TranValueClass;
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
@WebServlet(name = "UserRoleServlet", urlPatterns = {"/user_role_manage"})
public class UserRoleServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //now is use userManage second I must use user_role_manage
        HttpSession httpSession = request.getSession();
        User login_user = (User) httpSession.getAttribute("u");
        String action = request.getParameter("action");
        if (StringUtil.isNullOrEmpty(action)) {
            String pageS = request.getParameter("page");
            long page = pageS == null ? 0 : StringUtil.parseToLong(pageS);
            TranValueClass rows = new TranValueClass(new Long(0));
            List<User> listUser = UserRoleController.getAllUser(login_user, page, rows);
            //List<User> users =UserRoleController.getAllUser(login_user);
            List<Role> roles = UserRoleController.getAllRole(login_user);
            response.setContentType("JSON");
            PrintWriter pw = response.getWriter();
            pw.write("[");
            pw.write("[" + rows.getValue().toString() + ",");
            pw.write(UserJson.ListToJson(listUser));
            pw.write("]");
            pw.write(",");
            pw.write(RoleJson.ListToJson(roles));
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
            Role user = new Role(display_name, _id);
            boolean isUp = RoleController.updateOrAddRole(user, login_user);
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
            pw.write(UserRoleController.getRolesJsonByRoleID(login_user, _id));
            pw.write("]");
            pw.flush();
            pw.close();
        } else if (StringUtil.del.equals(action)) {
            String user_id = request.getParameter("user_id");
            String role_id = request.getParameter("role_id");
            long _user_id;
            long _role_id;
            if (StringUtil.isNullOrEmpty(user_id)) {
                _user_id = -1;
            } else {
                _user_id = Long.valueOf(user_id);
            }
            if (StringUtil.isNullOrEmpty(role_id)) {
                _role_id = -1;
            } else {
                _role_id = Long.valueOf(role_id);
            }
            boolean isDel = UserRoleController.delete(login_user, _user_id, _role_id);
        } else if (StringUtil.add.equals(action)) {
            String user_id = request.getParameter("user_id");
            String role_id = request.getParameter("role_id");
            long _user_id;
            long _role_id;
            if (StringUtil.isNullOrEmpty(user_id)) {
                _user_id = -1;
            } else {
                _user_id = Long.valueOf(user_id);
            }
            if (StringUtil.isNullOrEmpty(role_id)) {
                _role_id = -1;
            } else {
                _role_id = Long.valueOf(role_id);
            }
            boolean isAdd = false;
            if (_user_id == -1 || _role_id == -1) {

            } else {
                isAdd = UserRoleController.add(login_user, _user_id, _role_id);
            }
        }
        response.sendRedirect("/index.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(404);
    }
}
