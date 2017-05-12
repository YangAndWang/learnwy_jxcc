package com.learnwy.controller;

import com.learnwy.db.PowerDB;
import com.learnwy.db.SysMenuDB;
import com.learnwy.db.SysMenuPowerDB;
import com.learnwy.model.Power;
import com.learnwy.model.SysMenu;
import com.learnwy.model.User;
import com.learnwy.util.TranValueClass;
import com.learnwy.util.json.SysMenuJson;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by 25973 on 2017-05-10.
 */
public class PowerSysMenuController {
    public static boolean checkPower(User login_user) {
        List<SysMenu> sysMenus = SysMenuController.getSysMenusByUser(login_user);
        for (SysMenu sysMenu : sysMenus) {
            if ("/power_sys_menu_manage".equals(sysMenu.getPath())) {
                return true;
            }
        }
        return false;
    }

    public static List<Power> getAllPower(User login_user, long page, TranValueClass rows) {
        if (!checkPower(login_user)) {
            return new LinkedList<>();
        }
        return PowerDB.getPowers(page, rows);
    }

    public static List<SysMenu> getAllSysMenu(User login_user) {
        if (!checkPower(login_user)) {
            return new LinkedList<>();
        }
        return SysMenuDB.getSysMenus();
    }

    public static String getSysMenusJsonByPowerID(User login_user, long id) {
        if (id == -1) {
            return "[]";
        }
        if (!checkPower(login_user)) {
            return "[]";
        }
        long[] ids = new long[]{id};
        List<SysMenu> sysMenus = SysMenuDB.getSysMenusByPowerIds(ids);
        return SysMenuJson.listToJson(sysMenus);
    }

    public static boolean delete(User login_user, long power_id, long sys_menu_id) {
        if (!checkPower(login_user)) {
            return false;
        }
        return SysMenuPowerDB.delSysMenuPower(power_id, sys_menu_id) > 0;
    }

    public static boolean add(User login_user, long power_id, long sys_menu_id) {
        if (!checkPower(login_user) || power_id == -1 || sys_menu_id == -1) {
            return false;
        }
        return SysMenuPowerDB.addSysMenuPower(power_id, sys_menu_id) > 0;
    }

    public static List<Power> getAllPower(User login_user) {
        if (!checkPower(login_user)) {
            return new LinkedList<>();
        }
        return PowerDB.getPowers();
    }
}
