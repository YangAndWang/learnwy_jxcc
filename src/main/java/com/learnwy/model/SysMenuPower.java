package com.learnwy.model;

public class SysMenuPower {
	public SysMenuPower() {
	}

	public SysMenuPower(long sys_menu_power_id, long sys_menu_id, long power_id) {
		this.sys_menu_power_id = sys_menu_power_id;
		this.sys_menu_id = sys_menu_id;
		this.power_id = power_id;
	}

	private long sys_menu_power_id;
	private long sys_menu_id;
	private long power_id;

	public long getSysMenuPowerId() {
		return this.sys_menu_power_id;
	}

	public void setSysMenuPowerId(long sys_menu_power_id) {
		this.sys_menu_power_id = sys_menu_power_id;
	}

	public long getSysMenuId() {
		return this.sys_menu_id;
	}

	public void setSysMenuId(long sys_menu_id) {
		this.sys_menu_id = sys_menu_id;
	}

	public long getPowerId() {
		return this.power_id;
	}

	public void setPowerId(long power_id) {
		this.power_id = power_id;
	}
}
