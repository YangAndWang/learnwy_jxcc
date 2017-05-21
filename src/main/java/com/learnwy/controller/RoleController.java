package com.learnwy.controller;

import com.learnwy.db.RoleDB;
import com.learnwy.model.Role;
import com.learnwy.model.SysMenu;
import com.learnwy.model.User;
import com.learnwy.util.TranValueClass;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by 25973 on 2017-05-05.
 */
public class RoleController {
    /**
     * 获取该用户所有的角色
     * @param user
     * @return
     */
    public static List<Role> getRolesByUser(User user) {
        if (user == null) {
            return null;
        }
        return getRolesByUserID(user.getUserId());
    }

    /**
     * 获取该用户id对应的所有角色
     * @param id
     * @return
     */
    public static List<Role> getRolesByUserID(Long id) {
        return RoleDB.getRolesByUserId(id);
    }

    public static List<Role> getAllRole(User login_user, long page, TranValueClass rows) {
        if (!checkPower(login_user)) {
            return new LinkedList<>();
        }
        return RoleDB.getRoles(page, rows);
    }

    private static boolean checkPower(User login_user) {
        List<SysMenu> sysMenus = SysMenuController.getSysMenusByUser(login_user);
        for (SysMenu sysMenu : sysMenus) {
            if ("/role_manage".equals(sysMenu.getPath())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 添加或者更新角色
     * @param role
     * @param login_user
     * @return
     */
    public static boolean updateOrAddRole(Role role, User login_user) {
        if (!checkPower(login_user)) {
            return false;
        }
        if (role.getRoleId() == -1) {
            return RoleDB.addRole(role) > 0;
        }
        return RoleDB.updateRole(role) > 0;
    }
}
