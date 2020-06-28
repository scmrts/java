package com.lgcns.test2;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Bus {
	String name;
	Date time;
	int location;
	
	public String toString() {
		return String.format("name : %s, time : %s, location : %d", name, time.toString(), location);
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
