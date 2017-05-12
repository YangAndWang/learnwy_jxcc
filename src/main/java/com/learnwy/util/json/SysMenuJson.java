package com.learnwy.util.json;

import com.learnwy.model.Power;
import com.learnwy.model.SysMenu;

import java.util.List;

/**
 * Created by 25973 on 2017-05-10.
 */
public class SysMenuJson {
    public static String listToJson(List<SysMenu> sysMenus) {
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        for (SysMenu r : sysMenus) {
            sb.append("[");
            sb.append(r.getSysMenuId()).append(",\"");
            sb.append(r.getDisplayName()).append("\"],");
        }
        sb.setLength(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }
}
