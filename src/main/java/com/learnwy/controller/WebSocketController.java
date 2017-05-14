package com.learnwy.controller;

import com.learnwy.db.UserDB;

/**
 * Created by 25973 on 2017-05-14.
 */
public class WebSocketController {
    public static long checkPower(long user_id){
        return UserDB.getUserPowerRoleIDByUserId(user_id);
    }
}
