package com.learnwy.filter;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.HttpCookie;
import java.util.Enumeration;

/**
 * Created by 25973 on 2017-05-04.
 */
public class Check implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        Enumeration<String> n = req.getParameterNames();
        String cookieLogin;
        System.out.println(request.getRequestURI());
        HttpSession httpSession = ((HttpServletRequest) req).getSession();

        if (!request.getRequestURI().matches("(/login[\\S]*)|(\\S*\\.(html.section|js|css|html|png)$)|" +
                "(^/customer(|\\.jsp)$)")) {
            if (httpSession == null || httpSession.getAttribute("u") == null) {
                ((HttpServletResponse) resp).sendRedirect("/login.jsp");
            }
            Cookie[] cookies = request.getCookies();
            if (cookies == null || cookies.length < 2) {
                ((HttpServletResponse) resp).sendRedirect("/login.jsp");
            }
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
