package com.learnwy.controller;

import com.learnwy.db.UserDB;
import com.learnwy.model.User;

/**
 * Created by 25973 on 2017-05-05.
 */
public class LoginController {
    /**
     * 根据传来的用户名和用户密码来登录，登录成功则设置相应的显示姓名和用户ID
     * @param user
     * @return
     */
    public static boolean login(User user) {
        User u = UserDB.getUserByLogin(user);
        if (u.getUserId() != -1 && u.getUserName().equals(user.getUserName()) && u.getUserPwd().equals(user.getUserPwd
                ())) {
            user.setUserId(u.getUserId());
            user.setDisplayName(u.getDisplayName());
            return true;
        }
        return false;
    }
}
