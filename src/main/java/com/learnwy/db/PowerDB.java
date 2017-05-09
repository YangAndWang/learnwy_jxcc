package com.learnwy.db;

import com.learnwy.db.mysql.MySQL;
import com.learnwy.model.Power;
import com.learnwy.util.StringUtil;
import com.learnwy.util.TranValueClass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class PowerDB {
    public static void main(String[] args) {

    }

    public static List<Power> getPowers() {
        LinkedList<Power> ret = new LinkedList<Power>();
        String sql = "select display_name ,power_id from power";
        ResultSet rs = MySQL.excuteSQL(sql);
        Power power = null;
        try {
            while (rs.next()) {
                power = new Power(rs.getString(1), rs.getLong(2));
                ret.add(power);
            }
        } catch (Exception ex) {
        }
        return ret;
    }

    public static int delPower(long id) {
        String sql = "delete from power where power_id = " + id;
        return MySQL.updateSQL(sql);
    }

    public static int updatePower(Power power) {
        if (power == null) {
            return -1;
        } else if (power.getPowerId() == -1) {
            return addPower(power);
        }
        String sql = "update power set display_name = '" + power.getDisplayName() + "' where power_id = "
                + power.getPowerId();
        return MySQL.updateSQL(sql);
    }

    public static int addPower(Power power) {
        String sql = "insert into power(display_name )values( '" + power.getDisplayName() + "')";
        return MySQL.updateSQL(sql);
    }

    public static Power getPowerById(long id) {
        String sql = "select display_name ,power_id  from power where power_id = " + id;
        ResultSet rs = MySQL.excuteSQL(sql);
        Power power = null;
        try {
            if (rs.next()) {
                power = new Power(rs.getString(1), rs.getLong(2));
            }
        } catch (Exception ex) {
        }
        return power;
    }

    public static List<Power> getPowersByRoleIds(long[] roleIds) {
        String sql = "select p.power_id,p.display_name from power p,role_power rp where rp.power_id = p.power_id and rp.role_id in (" + StringUtil.join(roleIds, ",") + ")";
        ResultSet rs = MySQL.excuteSQL(sql);
        List<Power> ret = new LinkedList<>();
        try {
            while (rs.next()) {
                ret.add(new Power(rs.getString(2), rs.getLong(1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static List<Power> getRoles(long page, TranValueClass rows) {
        List<Power> ret = new LinkedList<>();
        String sql = "SELECT count(power_id) from power";
        String dataSQL = " SELECT power_id, display_name FROM power limit  " + page * 10 + ",10";
        try {
            ResultSet rs = MySQL.excuteSQL(sql);
            rs.next();
            rows.setValue(Long.valueOf(rs.getLong(1)));
            rs = MySQL.excuteSQL(dataSQL);
            while (rs.next()) {
                ret.add(new Power(rs.getString(2), rs.getLong(1)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
