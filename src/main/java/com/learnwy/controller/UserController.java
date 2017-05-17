package com.learnwy.controller;

import com.learnwy.db.UserDB;
import com.learnwy.model.SysMenu;
import com.learnwy.model.User;
import com.learnwy.util.TranValueClass;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 25973 on 2017-05-06.
 */
public class UserController {
    public static boolean checkPower(User u) {
        List<SysMenu> sysMenus = SysMenuController.getSysMenusByUser(u);
        for (SysMenu sysMenu : sysMenus) {
            if ("/user_manage".equals(sysMenu.getPath())) {
                return true;
            }
        }
        return false;
    }

    public static List<User> getAllUser(User login_user) {
        if (!checkPower(login_user)) {
            return new LinkedList<User>();
        }
        return UserDB.getUsers();
    }

    public static boolean updateOrAddUser(User user, User login_user) {
        if (!checkPower(login_user)) {
            return false;
        }
        if (user.getUserId() == -1) {
            return UserDB.addUser(user) > 0;
        }
        return UserDB.updateUser(user) > 0;
    }

    public static List<User> getAllUser(User login_user, long page, TranValueClass rows) {
        if (!checkPower(login_user)) {
            return new LinkedList<User>();
        }
        return UserDB.getUsers(page, rows);
    }

    public static boolean updateUserBySelf(User login_user, long id, String name, String display_name, String pwd) {
        boolean ret = false;
        if (login_user.getUserId() == id) {
            ret = UserDB.updateUser(id, name, display_name, pwd) > 0;
            if (ret) {
                login_user.setUserName(name);
                login_user.setDisplayName(display_name);
                login_user.setUserPwd(pwd);
            }
        }
        return ret;
    }
}
