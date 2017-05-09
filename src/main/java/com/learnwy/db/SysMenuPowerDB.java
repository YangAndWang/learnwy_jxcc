package com.learnwy.db;

import com.learnwy.db.mysql.MySQL;
import com.learnwy.model.SysMenuPower;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class SysMenuPowerDB {
	public static List<SysMenuPower> getSysMenuPowers() {
		LinkedList<SysMenuPower> ret = new LinkedList<SysMenuPower>();
		String sql = "select sys_menu_power_id ,sys_menu_id ,power_id from sys_menu_power";
		ResultSet rs = MySQL.excuteSQL(sql);
		SysMenuPower sys_menu_power = null;
		try {
			while (rs.next()) {
				sys_menu_power = new SysMenuPower(rs.getLong(1), rs.getLong(2), rs.getLong(3));
				ret.add(sys_menu_power);
			}
		} catch (Exception ex) {
		}
		return ret;
	}

	public static int delSysMenuPower(long id) {
		String sql = "delete from sys_menu_power where sys_menu_power_id = " + id;
		return MySQL.updateSQL(sql);
	}

	public static int updateSysMenuPower(SysMenuPower sys_menu_power) {
		if (sys_menu_power == null) {
			return -1;
		} else if (sys_menu_power.getSysMenuPowerId() == -1) {
			return addSysMenuPower(sys_menu_power);
		}
		String sql = "update sys_menu_power set sys_menu_id = '" + sys_menu_power.getSysMenuId() + "',power_id = '"
				+ sys_menu_power.getPowerId() + "' where sys_menu_power_id = " + sys_menu_power.getSysMenuPowerId();
		return MySQL.updateSQL(sql);
	}

	public static int addSysMenuPower(SysMenuPower sys_menu_power) {
		String sql = "insert into sys_menu_power(sys_menu_id ,power_id )values( '" + sys_menu_power.getSysMenuId()
				+ "','" + sys_menu_power.getPowerId() + "'";
		return MySQL.updateSQL(sql);
	}

	public static SysMenuPower getSysMenuPowerById(long id) {
		String sql = "select sys_menu_power_id ,sys_menu_id ,power_id  from sys_menu_power where sys_menu_power_id = "
				+ id;
		ResultSet rs = MySQL.excuteSQL(sql);
		SysMenuPower sys_menu_power = null;
		try {
			if (rs.next()) {
				sys_menu_power = new SysMenuPower(rs.getLong(1), rs.getLong(2), rs.getLong(3));
			}
		} catch (Exception ex) {
		}
		return sys_menu_power;
	}
}
