package com.learnwy.controller;

import com.learnwy.db.UserDB;
import com.learnwy.model.User;

/**
 * Created by 25973 on 2017-05-05.
 */
public class LoginController {
    public static boolean login(User user) {
        return UserDB.getUserByLogin(user);
    }
}
