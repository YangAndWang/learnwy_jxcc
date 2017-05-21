package com.learnwy.servlet;

import com.learnwy.controller.LoginController;
import com.learnwy.model.User;
import com.learnwy.util.StringUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by 25973 on 2017-05-04.
 */
public class LoginServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        /**
         * error_no 1:帐号或密码不能为空
         *          2:帐号或密码错误
         */
        String name = request.getParameter("name");
        String pwd = request.getParameter("pwd");
        if (StringUtil.empty(name) || StringUtil.empty(pwd)) {
            response.sendRedirect("/login.jsp?err_no=1");
            return;
        }
        User user = new User();
        user.setUserName(name.trim().replaceAll("'|\\-", ""));
        user.setUserPwd(pwd.trim().replaceAll("'|\\-", ""));
        if (LoginController.login(user)) {
            HttpSession httpSession = request.getSession();
            User u = (User) httpSession.getAttribute("u");
            if (u == null) {
                httpSession.setAttribute("u", user);
            } else {
                if (user.isNot(u)) {
                    u.setUser(user);
                }
            }
            response.addCookie(new Cookie("u", user.getDisplayName()));
            response.addCookie(new Cookie("n", user.getUserName()));
            response.addCookie(new Cookie("id", String.valueOf(user.getUserId())));
            response.sendRedirect("/index.jsp");
            return;
        } else {
            response.sendRedirect("/login.jsp?err_no=2");
            return;
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.sendRedirect("/login.jsp");
    }
}
