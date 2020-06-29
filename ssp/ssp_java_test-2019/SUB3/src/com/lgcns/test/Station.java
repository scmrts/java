package com.lgcns.test;

import java.util.Date;

public class Station {
	private String name;
	private int location;
	private int speed;
	
	public String toString() {
		return String.format("name : %s, location : %d, speed : %d", getName(), getLocation(), getSpeed());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public Bus getNearestBus(Date time) {
		BusManager busManager = BusManager.getInstance();
		return busManager.getNearestBusInfoFromStation(this, time);
	}
}
