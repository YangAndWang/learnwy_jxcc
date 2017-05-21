package com.learnwy.controller;

import com.learnwy.db.PowerDB;
import com.learnwy.db.RoleDB;
import com.learnwy.db.SysMenuDB;
import com.learnwy.db.mysql.MySQL;
import com.learnwy.model.Power;
import com.learnwy.model.SysMenu;
import com.learnwy.model.User;
import com.learnwy.util.StringUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 25973 on 2017-05-05.
 */
public class SysMenuController {
    /**
     * 获取用户对应的菜单
     * @param userId
     * @return
     */
    public static List<SysMenu> getSysMenusByUserId(long userId) {
        return getSysMenusByPowers(PowerController.getPowersByRoles(RoleController.getRolesByUserID(userId)));
    }

    /**
     * 通过登录的用户获取该用户对应的菜单
     *
     * @param user
     * @return
     */
    public static List<SysMenu> getSysMenusByUser(User user) {
        if (user == null) {
            return new LinkedList<SysMenu>();
        }
        return getSysMenusByUserId(user.getUserId());
    }

    /**
     * 通过权限[]获取菜单
     *
     * @param powers
     * @return
     */
    public static List<SysMenu> getSysMenusByPowers(List<Power> powers) {
        long[] powerIds = new long[powers.size()];
        int i = 0;
        for (Power p : powers) {
            powerIds[i++] = p.getPowerId();
        }
        return getSysMenusByPowerIds(powerIds);
    }

    /**
     * 通过权限ids[] 获取菜单
     *
     * @param powerIds
     * @return
     */
    public static List<SysMenu> getSysMenusByPowerIds(long[] powerIds) {
        return SysMenuDB.getSysMenusByPowerIds(powerIds);
    }

    /**
     * 检测权限后，成功获取所有菜单
     *
     * @param u
     * @return
     */
    public static List<SysMenu> getAllSysMenus(User u) {
        if (!checkPower(u)) {
            return new LinkedList<>();
        }
        return SysMenuDB.getSysMenus();
    }

    private static boolean checkPower(User u) {
        for (SysMenu s : getSysMenusByUser(u)) {
            if (s.getPath().equals("/sys_menu_manage")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 更新菜单，但是并没有设计这个功能
     *
     * @param sysMenu
     * @param u
     * @return
     */
    public static boolean updateSysMenu(SysMenu sysMenu, User u) {
        if (!checkPower(u)) {
            return false;
        }
        return SysMenuDB.updateSysMenu(sysMenu) > 0;
    }

    /**
     * 更新或添加系统菜单
     *
     * @param sysMenu
     * @param login_user
     * @return
     */
    public static boolean addOrUpdateSysMenu(SysMenu sysMenu, User login_user) {
        if (!checkPower(login_user)) {
            return false;
        }
        if (sysMenu.getSysMenuId() == -1) {
            return SysMenuDB.addSysMenu(sysMenu) > 0;
        } else {
            return SysMenuDB.updateSysMenu(sysMenu) > 0;
        }
    }
}
