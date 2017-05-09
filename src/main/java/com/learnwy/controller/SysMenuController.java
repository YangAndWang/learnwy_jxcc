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
    public static List<SysMenu> getSysMenusByUserId(long userId) {
        return getSysMenusByPowers(PowerController.getPowersByRoles(RoleController.getRolesByUserID(userId)));
    }

    public static List<SysMenu> getSysMenusByUser(User user) {
        if (user == null) {
            return new LinkedList<SysMenu>();
        }
        return getSysMenusByUserId(user.getUserId());
    }

    public static List<SysMenu> getSysMenusByPowers(List<Power> powers) {
        long[] powerIds = new long[powers.size()];
        int i = 0;
        for (Power p : powers) {
            powerIds[i++] = p.getPowerId();
        }
        return getSysMenusByPowerIds(powerIds);
    }

    public static List<SysMenu> getSysMenusByPowerIds(long[] powerIds) {
        return SysMenuDB.getSysMenusByPowerIds(powerIds);
    }

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

    public static boolean updateSysMenu(SysMenu sysMenu, User u) {
        if (!checkPower(u)) {
            return false;
        }
        return SysMenuDB.updateSysMenu(sysMenu) > 0;
    }
}
