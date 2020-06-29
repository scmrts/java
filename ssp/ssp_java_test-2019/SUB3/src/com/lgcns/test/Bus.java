package com.lgcns.test;

import java.util.Date;

public class Bus implements Cloneable {
	private String name;
	private Date time;
	private int location;
	private int speed;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
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
	
	public String toString() {
		return String.format("name : %s, time : %s, location : %d, speed : %d", name, time.toString(), location, speed);
	}

	public Bus copy() {
		try {
			return (Bus) this.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
