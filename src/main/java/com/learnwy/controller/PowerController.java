package com.learnwy.controller;

import com.learnwy.db.PowerDB;
import com.learnwy.model.Power;
import com.learnwy.model.Role;
import com.learnwy.model.SysMenu;
import com.learnwy.model.User;
import com.learnwy.util.TranValueClass;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by 25973 on 2017-05-05.
 */
public class PowerController {
    /**
     * 根据角色的ID[]来获取这些对应角色所对应的权限
     * @param roleIds
     * @return
     */
    public static List<Power> getPowersByRoleIds(long[] roleIds) {
        return PowerDB.getPowersByRoleIds(roleIds);
    }

    /**
     * 根据角色s来获取权限
     * @param roles
     * @return
     */
    public static List<Power> getPowersByRoles(List<Role> roles) {
        long[] roleIds = new long[roles.size()];
        int i = 0;
        for (Role role : roles) {
            roleIds[i++] = role.getRoleId();
        }
        return getPowersByRoleIds(roleIds);
    }

    private static boolean checkPower(User login_user) {
        List<SysMenu> sysMenus = SysMenuController.getSysMenusByUser(login_user);
        for (SysMenu sysMenu : sysMenus) {
            if ("/power_manage".equals(sysMenu.getPath())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取权限列表，分页为10
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
     * 更新或者添加新的权限
     * @param power
     * @param login_user
     * @return
     */
    public static boolean updateOrAddPower(Power power, User login_user) {
        if (!checkPower(login_user)) {
            return false;
        }
        if (power.getPowerId() == -1) {
            return PowerDB.addPower(power) > 0;
        }
        return PowerDB.updatePower(power) > 0;
    }
}
