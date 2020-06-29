package com.lgcns.test;

import java.util.Date;
import java.util.StringJoiner;

public class Arrival {
	private Bus bus;
	private Date estimatedTime;
	private Station station;
	public Arrival(Station station, Bus bus, Date date) {
		this.setStation(station);
		this.bus = bus;
		this.estimatedTime = date;
	}
	public Date getEstimatedTime() {
		return estimatedTime;
	}
	public void setEstimatedTime(Date estimatedTime) {
		this.estimatedTime = estimatedTime;
	}
	public Bus getBus() {
		return bus;
	}
	public void setBus(Bus bus) {
		this.bus = bus;
	}
	public Station getStation() {
		return station;
	}
	public void setStation(Station station) {
		this.station = station;
	}
	public String toString() {
		StringJoiner sj = new StringJoiner("\n");
		sj.add(RunManager.transformToString(this.estimatedTime));
		sj.add(this.bus.toString());
		sj.add(this.station.toString());
		return sj.toString();
	}
}
