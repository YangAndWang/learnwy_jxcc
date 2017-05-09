package com.learnwy.util.json;

import com.learnwy.model.Role;

import java.util.List;

/**
 * Created by 25973 on 2017-05-07.
 */
public class RoleJson {

    public static String ListToJson(List<Role> roles) {
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        for (Role r : roles) {
            sb.append("[");
            sb.append(r.getRoleId()).append(",\"");
            sb.append(r.getDisplayName()).append("\"],");

        }
        sb.setLength(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }
}
