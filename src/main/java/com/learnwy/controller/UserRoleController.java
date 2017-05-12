package com.learnwy.controller;

import com.learnwy.db.RoleDB;
import com.learnwy.db.SysMenuDB;
import com.learnwy.db.UserDB;
import com.learnwy.db.UserRoleDB;
import com.learnwy.model.Role;
import com.learnwy.model.SysMenu;
import com.learnwy.model.User;
import com.learnwy.util.TranValueClass;
import com.learnwy.util.json.RoleJson;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by 25973 on 2017-05-12.
 */
public class UserRoleController {
    public static List<Role> getAllRole(User login_user, long page, TranValueClass rows) {
        if (!checkPower(login_user)) {
            return new LinkedList<>();
        }
        return RoleDB.getRoles();
    }

    private static boolean checkPower(User login_user) {
        List<SysMenu> sysMenus = SysMenuController.getSysMenusByUser(login_user);
        for (SysMenu sysMenu : sysMenus) {
            if ("/user_role_manage".equals(sysMenu.getPath())) {
                return true;
            }
        }
        return false;
    }

    public static List<User> getAllUser(User login_user) {
        if (!checkPower(login_user)) {
            return new LinkedList<>();
        }
        return UserDB.getUsers();
    }

    public static List<Role> getAllRole(User login_user) {
        if (!checkPower(login_user)) {
            return new LinkedList<>();
        }
        return RoleDB.getRoles();
    }

    public static List<User> getAllUser(User login_user, long page, TranValueClass rows) {
        if (!checkPower(login_user)) {
            return new LinkedList<>();
        }
        return UserDB.getUsers(page, rows);
    }

    public static String getRolesJsonByRoleID(User login_user, long id) {
        if (id == -1) {
            return "[]";
        }
        if (!checkPower(login_user)) {
            return "[]";
        }
        List<Role> roles = RoleDB.getRolesByUserId(id);
        return RoleJson.ListToJson(roles);
    }

    public static boolean delete(User login_user, long user_id, long role_id) {
        if (!checkPower(login_user)) {
            return false;
        }
        return UserRoleDB.delUserRole(user_id, role_id) > 0;
    }

    public static boolean add(User login_user, long user_id, long role_id) {
        if (!checkPower(login_user)) {
            return false;
        }
        return UserRoleDB.addUserRole(user_id, role_id) > 0;
    }
}
