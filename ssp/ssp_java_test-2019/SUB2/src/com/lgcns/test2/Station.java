package com.lgcns.test2;

import java.util.Date;

public class Station {
	String name;
	int location;
	
	public Bus getNearestBus(Date time) {
		BusManager busManager = BusManager.getInstance();
		Bus bus = busManager.getPrePostBusInfo(time).stream().filter(o -> o.location <= this.location).max(BusManager.locationComparator).orElseGet(Bus::new);
		return bus;
	}
}
