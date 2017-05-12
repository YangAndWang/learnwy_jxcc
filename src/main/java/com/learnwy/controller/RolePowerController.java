package com.learnwy.controller;

import com.learnwy.db.PowerDB;
import com.learnwy.db.RoleDB;
import com.learnwy.db.RolePowerDB;
import com.learnwy.model.*;
import com.learnwy.util.TranValueClass;
import com.learnwy.util.json.PowerJson;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by 25973 on 2017-05-12.
 */
public class RolePowerController {

    public static List<Role> getAllRole(User login_user, long page, TranValueClass rows) {
        if (!checkPower(login_user)) {
            return new LinkedList<>();
        }
        return RoleDB.getRoles(page, rows);
    }

    private static boolean checkPower(User login_user) {
        List<SysMenu> sysMenus = SysMenuController.getSysMenusByUser(login_user);
        for (SysMenu sysMenu : sysMenus) {
            if ("/role_power_manage".equals(sysMenu.getPath())) {
                return true;
            }
        }
        return false;
    }

    public static List<Power> getAllPower(User login_user) {
        if (!checkPower(login_user)) {
            return new LinkedList<>();
        }
        return PowerDB.getPowers();
    }

    public static String getPowersJsonByRoleID(User login_user, long id) {
        if (id == -1) {
            return "[]";
        }
        if (!checkPower(login_user)) {
            return "[]";
        }
        long[] ids = new long[]{id};
        List<Power> powers = PowerDB.getPowersByRoleIds(ids);
        return PowerJson.ListToJson(powers);
    }

    public static boolean delete(User login_user, long power_id, long role_id) {
        if (!checkPower(login_user)) {
            return false;
        }
        return RolePowerDB.delRolePower(role_id, power_id) > 0;
    }

    public static boolean add(User login_user, long power_id, long role_id) {
        if (!checkPower(login_user)) {
            return false;
        }
        return RolePowerDB.addRolePower(power_id, role_id) > 0;
    }
}
