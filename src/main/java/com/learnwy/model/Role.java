package com.learnwy.model;

public class Role {
	public Role() {
	}

	public Role(String display_name, long role_id) {
		this.display_name = display_name;
		this.role_id = role_id;
	}

	private String display_name;
	private long role_id;

	public String getDisplayName() {
		return this.display_name;
	}

	public void setDisplayName(String display_name) {
		this.display_name = display_name;
	}

	public long getRoleId() {
		return this.role_id;
	}

	public void setRoleId(long role_id) {
		this.role_id = role_id;
	}
}
