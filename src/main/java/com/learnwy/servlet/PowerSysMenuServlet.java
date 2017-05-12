package com.learnwy.servlet;

import com.learnwy.controller.PowerController;
import com.learnwy.controller.PowerSysMenuController;
import com.learnwy.model.Power;
import com.learnwy.model.SysMenu;
import com.learnwy.model.User;
import com.learnwy.util.StringUtil;
import com.learnwy.util.TranValueClass;
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
 * Created by 25973 on 2017-05-10.
 */
@WebServlet(name = "PowerSysMenuServlet", urlPatterns = {"/power_sys_menu_manage"})
public class PowerSysMenuServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //now is use powerManage second I must use power_sys_menu_manage
        HttpSession httpSession = request.getSession();
        User login_user = (User) httpSession.getAttribute("u");
        String action = request.getParameter("action");
        if (StringUtil.isNullOrEmpty(action)) {
            String pageS = request.getParameter("page");
            long page = pageS == null ? 0 : StringUtil.parseToLong(pageS);
            TranValueClass rows = new TranValueClass(new Long(0));
            List<Power> listPower = PowerSysMenuController.getAllPower(login_user, page, rows);
            List<SysMenu> sysMenuList = PowerSysMenuController.getAllSysMenu(login_user);
            List<Power> powerList = PowerSysMenuController.getAllPower(login_user);
            if (listPower.size() > 0) {
                response.setContentType("JSON");
                PrintWriter pw = response.getWriter();
                pw.write("[");
                pw.write("[" + rows.getValue().toString() + ",");
                pw.write(PowerJson.ListToJson(listPower));
                pw.write("]");
                pw.write(",");
                pw.write(SysMenuJson.listToJson(sysMenuList));
                pw.write(",");
                pw.write(PowerJson.ListToJson(powerList));
                pw.write("]");
                pw.flush();
                pw.close();
            } else {
                response.setStatus(403);
            }
        }/* else if (StringUtil.update.equals(action)) {
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
        }*/ else if (StringUtil.query.equals(action)) {
            //id is powerID
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
            pw.write(PowerSysMenuController.getSysMenusJsonByPowerID(login_user, _id));
            pw.write("]");
            pw.flush();
            pw.close();
        } else if (StringUtil.del.equals(action)) {
            String power_id = request.getParameter("power_id");
            String sys_menu_id = request.getParameter("sys_menu_id");
            long _power_id;
            long _sys_menu_id;
            if (StringUtil.isNullOrEmpty(power_id)) {
                _power_id = -1;
            } else {
                _power_id = Long.valueOf(power_id);
            }
            if (StringUtil.isNullOrEmpty(sys_menu_id)) {
                _sys_menu_id = -1;
            } else {
                _sys_menu_id = Long.valueOf(sys_menu_id);
            }
            boolean isDel = PowerSysMenuController.delete(login_user, _power_id, _sys_menu_id);
        } else if (StringUtil.add.equals(action)) {
            String power_id = request.getParameter("power_id");
            String sys_menu_id = request.getParameter("sys_menu_id");
            long _power_id;
            long _sys_menu_id;
            if (StringUtil.isNullOrEmpty(power_id)) {
                _power_id = -1;
            } else {
                _power_id = Long.valueOf(power_id);
            }
            if (StringUtil.isNullOrEmpty(sys_menu_id)) {
                _sys_menu_id = -1;
            } else {
                _sys_menu_id = Long.valueOf(sys_menu_id);
            }
            if(_power_id == -1 || _sys_menu_id == -1){

            }else{            boolean isAdd = PowerSysMenuController.add(login_user, _power_id, _sys_menu_id);

            }
        }
        response.sendRedirect("/index.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(404);
    }
}
