package com.learnwy.model;

public class UserRole {
	public UserRole() {
	}

	public UserRole(long user_role_id, long user_id, long role_id) {
		this.user_role_id = user_role_id;
		this.user_id = user_id;
		this.role_id = role_id;
	}

	private long user_role_id;
	private long user_id;
	private long role_id;

	public long getUserRoleId() {
		return this.user_role_id;
	}

	public void setUserRoleId(long user_role_id) {
		this.user_role_id = user_role_id;
	}

	public long getUserId() {
		return this.user_id;
	}

	public void setUserId(long user_id) {
		this.user_id = user_id;
	}

	public long getRoleId() {
		return this.role_id;
	}

	public void setRoleId(long role_id) {
		this.role_id = role_id;
	}
}
