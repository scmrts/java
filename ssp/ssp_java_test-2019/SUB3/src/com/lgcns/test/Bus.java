package com.lgcns.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class Bus {
	String name = "NOBUS";
	Date time;
	int location;
	int speed;
	
	
	public void setSpeed(Optional<Bus> prevBus) {
		prevBus.ifPresent(o->{
			long timeGap = (this.time.getTime() - o.time.getTime()) / 1000;
			this.speed = (int)((this.location - o.location) / timeGap);
		});
	}
	
	public String toString() {
		return String.format("name : %s, time : %s, location : %d, speed : %d", name, time.toString(), location, speed);
	}
	
	public String getTime() {
		if(! this.name.equals("NOBUS")) {
			SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
			return format.format(this.time);
		} else {
			return "00:00:00";
		}
	}
}
