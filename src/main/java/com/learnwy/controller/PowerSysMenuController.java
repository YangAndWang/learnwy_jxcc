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

    /**
     * 获取权限分页，分页大小为10
     * @param login_user
     * @param page
     * @param rows
     * @return
     */
    public static List<Power> getAllPower(User login_user, long page, TranValueClass rows) {
        if (!checkPower(login_user)) {
            return new LinkedList<>();
        }
        return PowerDB.getPowers(page, rows);
    }

    /**
     * 获取所有的菜单
     * @param login_user
     * @return
     */
    public static List<SysMenu> getAllSysMenu(User login_user) {
        if (!checkPower(login_user)) {
            return new LinkedList<>();
        }
        return SysMenuDB.getSysMenus();
    }

    /**
     * 获取对应权限的系统菜单信息
     * @param login_user
     * @param id
     * @return
     */
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

    /**
     * 删除某个权限的菜单
     * @param login_user
     * @param power_id
     * @param sys_menu_id
     * @return
     */
    public static boolean delete(User login_user, long power_id, long sys_menu_id) {
        if (!checkPower(login_user)) {
            return false;
        }
        return SysMenuPowerDB.delSysMenuPower(power_id, sys_menu_id) > 0;
    }

    /**
     * 给某个权限添加菜单
     * @param login_user
     * @param power_id
     * @param sys_menu_id
     * @return
     */
    public static boolean add(User login_user, long power_id, long sys_menu_id) {
        if (!checkPower(login_user) || power_id == -1 || sys_menu_id == -1) {
            return false;
        }
        return SysMenuPowerDB.addSysMenuPower(power_id, sys_menu_id) > 0;
    }

    /**
     * 获取所有的权限
     * @param login_user
     * @return
     */
    public static List<Power> getAllPower(User login_user) {
        if (!checkPower(login_user)) {
            return new LinkedList<>();
        }
        return PowerDB.getPowers();
    }
}
