package com.learnwy.db;

import com.learnwy.db.mysql.MySQL;
import com.learnwy.model.Role;
import com.learnwy.util.TranValueClass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class RoleDB {
    public static List<Role> getRoles() {
        LinkedList<Role> ret = new LinkedList<Role>();
        String sql = "select display_name ,role_id from role";
        ResultSet rs = MySQL.excuteSQL(sql);
        Role role = null;
        try {
            while (rs.next()) {
                role = new Role(rs.getString(1), rs.getLong(2));
                ret.add(role);
            }
        } catch (Exception ex) {
        }
        return ret;
    }

    public static int delRole(long id) {
        String sql = "delete from role where role_id = " + id;
        return MySQL.updateSQL(sql);
    }

    public static int updateRole(Role role) {
        System.out.print("addRole");
        if (role == null) {
            return -1;
        } else if (role.getRoleId() == -1) {
            return addRole(role);
        }
        String sql = "update role set display_name = '" + role.getDisplayName() + "' where role_id = "
                + role.getRoleId();
        System.out.println(sql);
        return MySQL.updateSQL(sql);
    }

    public static int addRole(Role role) {
        String sql = "insert into role(display_name )values( '" + role.getDisplayName() + "')";
        return MySQL.updateSQL(sql);
    }

    public static Role getRoleById(long id) {
        String sql = "select display_name ,role_id  from role where role_id = " + id;
        ResultSet rs = MySQL.excuteSQL(sql);
        Role role = null;
        try {
            if (rs.next()) {
                role = new Role(rs.getString(1), rs.getLong(2));
            }
        } catch (Exception ex) {
        }
        return role;
    }

    public static List<Role> getRolesByUserId(Long userId) {
        List<Role> roles = new LinkedList<>();
        String sql = "SELECT r.display_name, r.role_id FROM role r, user_role ru WHERE ru.role_id = r.role_id AND ru.user_id = " + userId;
        ResultSet rs = MySQL.excuteSQL(sql);
        try {
            while (rs.next()) {
                roles.add(new Role(rs.getString(1), rs.getLong(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    public static List<Role> getRoles(long page, TranValueClass rows) {
        List<Role> ret = new LinkedList<>();
        String sql = "SELECT count(role_id) from role";
        String dataSQL = " SELECT role_id, display_name FROM role limit  " + page * 10 + ",10";
        try {
            ResultSet rs = MySQL.excuteSQL(sql);
            rs.next();
            rows.setValue(Long.valueOf(rs.getLong(1)));
            rs = MySQL.excuteSQL(dataSQL);
            while (rs.next()) {
                ret.add(new Role(rs.getString(2), rs.getLong(1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
