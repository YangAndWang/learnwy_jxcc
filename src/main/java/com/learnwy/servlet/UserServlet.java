package com.learnwy.servlet;

import com.learnwy.controller.UserController;
import com.learnwy.model.User;
import com.learnwy.util.StringUtil;
import com.learnwy.util.TranValueClass;
import com.learnwy.util.json.MenuTreeJson;
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


@WebServlet(name = "UserServlet", urlPatterns = {"/user_manage"})
public class UserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        User login_user = (User) httpSession.getAttribute("u");
        String action = request.getParameter("action");
        if (StringUtil.isNullOrEmpty(action)) {
            String pageS = request.getParameter("page");
            long page = pageS == null ? 0 : StringUtil.parseToLong(pageS);
            TranValueClass rows = new TranValueClass(new Long(0));
            List<User> listUser = UserController.getAllUser(login_user, page, rows);
            if (listUser.size() > 0) {
                response.setContentType("JSON");
                PrintWriter pw = response.getWriter();
                pw.write("[" + rows.getValue().toString() + ",");
                pw.write(UserJson.ListToJson(listUser));
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
            String pwd = request.getParameter("pwd");
            String name = request.getParameter("name");
            name = StringUtil.trimPath(name);
            pwd = StringUtil.trimPath(pwd);
            display_name = StringUtil.trimPath汉字(display_name);
            long _id;

            if (StringUtil.isNullOrEmpty(id)) {
                _id = -1;
            } else {
                _id = Long.valueOf(id);
            }
            User user = new User(display_name, name, pwd, _id);
            boolean isUp = UserController.updateOrAddUser(user, login_user);
        }
        response.sendRedirect("/index.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(404);
    }
}
