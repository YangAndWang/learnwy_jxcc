package com.learnwy.db;

import com.learnwy.db.mysql.MySQL;
import com.learnwy.model.UserRole;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UserRoleDB {
	public static List<UserRole> getUserRoles() {
		LinkedList<UserRole> ret = new LinkedList<UserRole>();
		String sql = "select user_role_id ,user_id ,role_id from user_role";
		ResultSet rs = MySQL.excuteSQL(sql);
		UserRole user_role = null;
		try {
			while (rs.next()) {
				user_role = new UserRole(rs.getLong(1), rs.getLong(2), rs.getLong(3));
				ret.add(user_role);
			}
		} catch (Exception ex) {
		}
		return ret;
	}

	public static int delUserRole(long id) {
		String sql = "delete from user_role where user_role_id = " + id;
		return MySQL.updateSQL(sql);
	}

	public static int updateUserRole(UserRole user_role) {
		if (user_role == null) {
			return -1;
		} else if (user_role.getUserRoleId() == -1) {
			return addUserRole(user_role);
		}
		String sql = "update user_role set user_id = '" + user_role.getUserId() + "',role_id = '" + user_role.getRoleId()
				+ "' where user_role_id = " + user_role.getUserRoleId();
		return MySQL.updateSQL(sql);
	}

	public static int addUserRole(UserRole user_role) {
		String sql = "insert into user_role(user_id ,role_id )values( '" + user_role.getUserId() + "','"
				+ user_role.getRoleId() + "'";
		return MySQL.updateSQL(sql);
	}

	public static UserRole getUserRoleById(long id) {
		String sql = "select user_role_id ,user_id ,role_id  from user_role where user_role_id = " + id;
		ResultSet rs = MySQL.excuteSQL(sql);
		UserRole user_role = null;
		try {
			if (rs.next()) {
				user_role = new UserRole(rs.getLong(1), rs.getLong(2), rs.getLong(3));
			}
		} catch (Exception ex) {
		}
		return user_role;
	}
}
