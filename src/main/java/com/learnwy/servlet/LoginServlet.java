package com.learnwy.servlet;

import com.learnwy.controller.LoginController;
import com.learnwy.model.User;
import com.learnwy.util.StringUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by 25973 on 2017-05-04.
 */
public class LoginServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        String name = request.getParameter("name");
        String pwd = request.getParameter("pwd");
        if (StringUtil.empty(name) || StringUtil.empty(pwd)) {
            response.sendRedirect("/login.jsp");
        }
        User user = new User();
        user.setUserName(name);
        user.setUserPwd(pwd);
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
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if ("u".equals(c.getName())) {
                        c.setValue(user.getDisplayName());
                    } else {
                        response.addCookie(new Cookie("u", user.getDisplayName()));
                    }
                    if ("n".equals(c.getName())) {
                        c.setValue(user.getUserName());
                    } else {
                        response.addCookie(new Cookie("n", user.getUserName()));
                    }
                }
            }
            response.sendRedirect("/index.jsp");
        } else {
            response.sendRedirect("/login.jsp");
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.sendRedirect("/login.jsp");
    }
}
