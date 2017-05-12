package com.learnwy.util.json;

import com.learnwy.model.User;

import java.util.List;

/**
 * Created by 25973 on 2017-05-06.
 */
public class UserJson {
    public static String ListToJson(List<User> users) {
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        for (User u : users) {
            sb.append("[");
            sb.append(u.getUserId()).append(",\"");
            sb.append(u.getUserName()).append("\",\"");
            sb.append(u.getUserPwd()).append("\",\"");
            sb.append(u.getDisplayName()).append("\"],");
        }
        sb.setLength(sb.length() - 1);

        sb.append("]");
        return sb.toString();
    }
}
