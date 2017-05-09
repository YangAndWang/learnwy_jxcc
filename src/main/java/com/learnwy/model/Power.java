package com.learnwy.model;

public class Power {
	public Power() {
	}

	public Power(String display_name, long power_id) {
		this.display_name = display_name;
		this.power_id = power_id;
	}

	private String display_name;
	private long power_id;

	public String getDisplayName() {
		return this.display_name;
	}

	public void setDisplayName(String display_name) {
		this.display_name = display_name;
	}

	public long getPowerId() {
		return this.power_id;
	}

	public void setPowerId(long power_id) {
		this.power_id = power_id;
	}
}
