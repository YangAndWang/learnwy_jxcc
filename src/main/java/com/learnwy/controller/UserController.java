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

    /**
     * 获取所有的用户信息
     *
     * @param login_user
     * @return
     */
    public static List<User> getAllUser(User login_user) {
        if (!checkPower(login_user)) {
            return new LinkedList<User>();
        }
        return UserDB.getUsers();
    }

    /**
     * 更新或添加用户
     *
     * @param user
     * @param login_user
     * @return
     */
    public static boolean updateOrAddUser(User user, User login_user) {
        if (!checkPower(login_user)) {
            return false;
        }
        if (user.getUserId() == -1) {
            return UserDB.addUser(user) > 0;
        }
        return UserDB.updateUser(user) > 0;
    }

    /**
     * 获取用户分页列表
     *
     * @param login_user
     * @param page
     * @param rows
     * @return
     */
    public static List<User> getAllUser(User login_user, long page, TranValueClass rows) {
        if (!checkPower(login_user)) {
            return new LinkedList<User>();
        }
        return UserDB.getUsers(page, rows);
    }

    /**
     * 用户自己更新自己的信息，包括，密码，用户名，登录名
     * 用户ID和之前的密码需要匹配才能更新
     * @param login_user
     * @param id
     * @param name
     * @param display_name
     * @param pwd
     * @param last_pwd
     * @return
     */
    public static boolean updateUserBySelf(User login_user, long id, String name, String display_name, String pwd, String last_pwd) {
        boolean ret = false;
        if (login_user.getUserId() == id) {
            ret = UserDB.updateUser(id, name, display_name, pwd, last_pwd) > 0;
            if (ret) {
                login_user.setUserName(name);
                login_user.setDisplayName(display_name);
                login_user.setUserPwd(pwd);
            }
        }
        return ret;
    }
}
