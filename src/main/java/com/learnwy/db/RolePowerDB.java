package com.learnwy.db;

import com.learnwy.db.mysql.MySQL;
import com.learnwy.model.RolePower;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class RolePowerDB {
	public static List<RolePower> getRolePowers() {
		LinkedList<RolePower> ret = new LinkedList<RolePower>();
		String sql = "select role_power_id ,power_id ,role_id from role_power";
		ResultSet rs = MySQL.excuteSQL(sql);
		RolePower role_power = null;
		try {
			while (rs.next()) {
				role_power = new RolePower(rs.getLong(1), rs.getLong(2), rs.getLong(3));
				ret.add(role_power);
			}
		} catch (Exception ex) {
		}
		return ret;
	}

	public static int delRolePower(long id) {
		String sql = "delete from role_power where role_power_id = " + id;
		return MySQL.updateSQL(sql);
	}

	public static int updateRolePower(RolePower role_power) {
		if (role_power == null) {
			return -1;
		} else if (role_power.getRolePowerId() == -1) {
			return addRolePower(role_power);
		}
		String sql = "update role set power_id = '" + role_power.getPowerId() + "',role_id = '" + role_power.getRoleId()
				+ "' where role_power_id = " + role_power.getRolePowerId();
		return MySQL.updateSQL(sql);
	}

	public static int addRolePower(RolePower role_power) {
		String sql = "insert into role_power(power_id ,role_id )values( '" + role_power.getPowerId() + "','"
				+ role_power.getRoleId() + "'";
		return MySQL.updateSQL(sql);
	}

	public static RolePower getRolePowerById(long id) {
		String sql = "select role_power_id ,power_id ,role_id  from role_power where role_power_id = " + id;
		ResultSet rs = MySQL.excuteSQL(sql);
		RolePower role_power = null;
		try {
			if (rs.next()) {
				role_power = new RolePower(rs.getLong(1), rs.getLong(2), rs.getLong(3));
			}
		} catch (Exception ex) {
		}
		return role_power;
	}
}
