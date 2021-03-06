package com.lgcns.test2;

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
	 * ??¬ ? λ₯μ₯?? κ°??₯ λΉ λ₯Έ λ²μ€? ?μ°©μκ°?
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
			
			//λ²μ€ ?μΉ? μ§μ ? ? λ₯μ₯ μ‘°ν
			Station orElseGet = stationManager.stations.stream().filter(o -> o.location < bus.location).max((a, b) -> a.location - b.location).orElseGet(Station::new);
			
			
			//λ²μ€ ?μΉ? μ§μ ? ? λ₯μ₯λΆ??° ??¬ ? λ₯μ₯? μ§μ  ? λ₯μ₯ κΉμ?? λͺ¨λ  ? λ₯μ₯ μ‘°ν
			int fromStation = stationManager.stations.indexOf(orElseGet);
			int toStation = stationManager.stations.indexOf(this);
			List<Station> stations = stationManager.stations.subList(fromStation, toStation);
			
//			List<Station> stations = stationManager.stations.stream().filter(o -> o.location >= orElseGet.location && o.location < this.location).collect(Collectors.toList());
			System.out.print("");
			
			for(int i = 0 ; i < stations.size() ; i++) {
				Station tmp = stations.get(i);
				// ??¬ λ²μ€κ°? μ§??κ°?? κ΅¬κ°? ?€? ? λ₯μ₯ μ‘°ν
				int index = stationManager.stations.indexOf(tmp);
				Station nextStation = stationManager.stations.get(index + 1);
				//?€? κ΅¬κ° ? λ₯μ₯ μ§μ κΉμ?
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
