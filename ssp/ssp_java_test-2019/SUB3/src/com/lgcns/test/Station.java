package com.lgcns.test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Station {
	String name;
	int location;
	int speed;
	
	public String toString() {
		return String.format("name : %s, location : %d, speed : %d", name, location, speed);
	}
	
	public Bus getNearestBus(Date time) {
		BusManager busManager = BusManager.getInstance();
		Bus bus = busManager.getPrePostBusInfo(time).stream().filter(o -> o.location <= this.location).max(BusManager.locationComparator).orElseGet(Bus::new);
		return bus;
	}
	
	/** 
	 * 현재 정류장에서 가장 빠른 버스의 도착시간
	 * @param time
	 */
	public String[] getNearestBusWithArrivalTime(Date time) {
		BusManager busManager = BusManager.getInstance();
		List<Bus> buses = busManager.getPrePostBusInfo(time);
		Bus nearestBus = null;
		int nearestSecond = Integer.MAX_VALUE;
		StationManager stationManager = StationManager.getInstance();
		List<Bus> targetBuses = buses.stream().filter(o -> o.location < this.location).collect(Collectors.toList());
		for(Bus bus : targetBuses) {
			int diffDistance = this.location - bus.location;
			int location = bus.location;
			int second = 0;
			
			//버스 위치 직전의 정류장 조회
			Station orElseGet = stationManager.stations.stream().filter(o -> o.location < bus.location).max((a, b) -> a.location - b.location).orElseGet(Station::new);
			
			
			//버스 위치 직전의 정류장부터 현재 정류장의 직전 정류장 까지의 모든 정류장 조회
			int fromStation = stationManager.stations.indexOf(orElseGet);
			int toStation = stationManager.stations.indexOf(this);
			List<Station> stations = stationManager.stations.subList(fromStation, toStation);
			
//			List<Station> stations = stationManager.stations.stream().filter(o -> o.location >= orElseGet.location && o.location < this.location).collect(Collectors.toList());
			System.out.print("");
			
			for(int i = 0 ; i < stations.size() ; i++) {
				Station tmp = stations.get(i);
				// 현재 버스가 지나가는 구간의 다음 정류장 조회
				int index = stationManager.stations.indexOf(tmp);
				Station nextStation = stationManager.stations.get(index + 1);
				//다음 구간 정류장 직전까지
				while(location < nextStation.location) {
					diffDistance -= Math.min(tmp.speed, bus.speed);
					location += Math.min(tmp.speed, bus.speed);
					second++;
				}
				if(diffDistance <= 0) {
					if(nearestSecond > second) {
						nearestSecond = second;
						nearestBus = bus;
					}
					break;
				}
			}
			
			
//			while(diffDistance > 0) {
//				if(stations.size() > 0) {
//					for(int i = 0 ; i < stations.size() ; i++) {
//						Station tmp = stations.get(i);
//						diffDistance -= Math.min(tmp.speed, bus.speed);
//						location += Math.min(tmp.speed, bus.speed);
//						second++;
//						if(diffDistance <= 0) {
//							break;
//						}
//						if(location >= tmp.location) {
//							continue;
//						}
//					}
//				} else {
//					diffDistance -= bus.speed;
//					second++;
//					if(diffDistance <= 0) {
//						break;
//					}
//					
//				}
//			}
//			if(nearestSecond > second) {
//				nearestSecond = second;
//				nearestBus = bus;
//			}
		}
		if(nearestBus == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(nearestBus.time);
		cal.add(Calendar.SECOND, nearestSecond);
		SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
		
		return Stream.of(nearestBus.name, format.format(cal.getTime())).toArray(String[]::new);
	}
}
