package com.learnwy.servlet;

import com.learnwy.controller.SysMenuController;
import com.learnwy.model.SysMenu;
import com.learnwy.model.User;
import com.learnwy.util.json.MenuTreeJson;
import com.learnwy.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


public class SysMenuServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        User login_user = (User) httpSession.getAttribute("u");
        String action = request.getParameter("action");
        System.out.println(action);
        if (StringUtil.isNullOrEmpty(action)) {
            List<SysMenu> listMenus = SysMenuController.getAllSysMenus(login_user);
            if (listMenus.size() > 0) {
                response.setContentType("JSON");
                PrintWriter pw = response.getWriter();
                pw.write(MenuTreeJson.getSysMenuJson(listMenus));
                pw.flush();
                pw.close();
            } else {
                response.setStatus(403);
            }
        } else if (StringUtil.update.equals(action)) {
            //now is only can update
            String name = request.getParameter("name");
            String id = request.getParameter("id");
            String pid = request.getParameter("pid");
            String path = request.getParameter("path");
            path = StringUtil.trimPath(path);
            long _id = -1;
            long _pid = -1;
            if (StringUtil.canParseLong(id)) {
                _id = Long.valueOf(id);
            }
            if (StringUtil.canParseLong(pid)) {
                _pid = Long.valueOf(pid);
            }
            SysMenu sysMenu = new SysMenu(_id, name, path, _pid);
            boolean isUp = SysMenuController.addOrUpdateSysMenu(sysMenu, login_user);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(404);
    }
}
