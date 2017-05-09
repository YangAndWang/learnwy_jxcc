package com.learnwy.model;

public class RolePower {
	public RolePower() {
	}

	public RolePower(long role_power_id, long power_id, long role_id) {
		this.role_power_id = role_power_id;
		this.power_id = power_id;
		this.role_id = role_id;
	}

	private long role_power_id;
	private long power_id;
	private long role_id;

	public long getRolePowerId() {
		return this.role_power_id;
	}

	public void setRolePowerId(long role_power_id) {
		this.role_power_id = role_power_id;
	}

	public long getPowerId() {
		return this.power_id;
	}

	public void setPowerId(long power_id) {
		this.power_id = power_id;
	}

	public long getRoleId() {
		return this.role_id;
	}

	public void setRoleId(long role_id) {
		this.role_id = role_id;
	}
}
