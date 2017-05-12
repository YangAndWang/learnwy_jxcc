package com.learnwy.db;

import com.learnwy.db.mysql.MySQL;
import com.learnwy.model.SysMenu;
import com.learnwy.util.StringUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class SysMenuDB {
    public static List<SysMenu> getSysMenus() {
        LinkedList<SysMenu> ret = new LinkedList<SysMenu>();
        String sql = "select distinct sys_menu_id ,display_name ,path ,parent_id from sys_menu";
        ResultSet rs = MySQL.excuteSQL(sql);
        SysMenu sys_menu = null;
        try {
            while (rs.next()) {
                sys_menu = new SysMenu(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getLong(4));
                ret.add(sys_menu);
            }
        } catch (Exception ex) {
        }
        return ret;
    }

    public static int delSysMenu(long id) {
        String sql = "delete from sys_menu where sys_menu_id = " + id;
        return MySQL.updateSQL(sql);
    }

    public static int updateSysMenu(SysMenu sys_menu) {
        if (sys_menu == null) {
            return -1;
        } else if (sys_menu.getSysMenuId() == -1) {
            return addSysMenu(sys_menu);
        }
        String sql = "update sys_menu set display_name = '" + sys_menu.getDisplayName() + "',parent_id = '" + sys_menu
                .getParentId() + "' where sys_menu_id = " + sys_menu.getSysMenuId();
        return MySQL.updateSQL(sql);
    }

    public static int updateSysMenu2(SysMenu sys_menu) {
        if (sys_menu == null) {
            return -1;
        } else if (sys_menu.getSysMenuId() == -1) {
            return addSysMenu(sys_menu);
        }
        String sql = "update sys_menu set display_name = '" + sys_menu.getDisplayName() + "',path = '" + sys_menu.getPath()
                + "',parent_id = '" + sys_menu.getParentId() + "' where sys_menu_id = " + sys_menu.getSysMenuId();
        return MySQL.updateSQL(sql);
    }

    public static int addSysMenu(SysMenu sys_menu) {
        String sql = "insert into sys_menu(display_name ,path ,parent_id )values( '" + sys_menu.getDisplayName() + "','"
                + sys_menu.getPath() + "','" + sys_menu.getParentId() + "'";
        return MySQL.updateSQL(sql);
    }

    public static SysMenu getSysMenuById(long id) {
        String sql = "select distinct sys_menu_id ,display_name ,path ,parent_id  from sys_menu where sys_menu_id = " + id;
        ResultSet rs = MySQL.excuteSQL(sql);
        SysMenu sys_menu = null;
        try {
            if (rs.next()) {
                sys_menu = new SysMenu(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getLong(4));
            }
        } catch (Exception ex) {
        }
        return sys_menu;
    }

    public static List<SysMenu> getSysMenusByPowerIds(long[] powerIds) {
        String sql = "select distinct s.sys_menu_id,display_name,path,parent_id from sys_menu s,sys_menu_power sp where s" +
                ".sys_menu_id = sp.sys_menu_id and sp.power_id in (" + StringUtil.join(powerIds, ",") + ")";
        ResultSet rs = MySQL.excuteSQL(sql);
        List<SysMenu> ret = new LinkedList<>();
        try {
            while (rs.next()) {
                ret.add(new SysMenu(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getLong(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
