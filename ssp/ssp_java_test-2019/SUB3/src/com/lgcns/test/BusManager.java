package com.lgcns.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class BusManager {
	
	private Map<String, List<Bus>> buses = new ConcurrentHashMap<String, List<Bus>>();
	
	public static Comparator<Bus> timeComparator = (o1, o2) -> o1.getTime().compareTo(o2.getTime()); 
	
	public static Comparator<Bus> locationComparator = (o1, o2) ->o1.getLocation() - o2.getLocation();
	
	public static Comparator<Bus> nameComparator = (o1, o2) ->o1.getName().compareTo(o2.getName());
	
	private static BusManager instance;
	
	public static synchronized BusManager getInstance() {
		if(instance == null) {
			instance = new BusManager();
		}
		
		return instance;
	}
	
	public synchronized String toString() {
		StringJoiner str = new StringJoiner("\n");
		Iterator<Entry<String, List<Bus>>> iterator = this.buses.entrySet().iterator();
		while(iterator.hasNext()) {
			
			Entry<String, List<Bus>> next = iterator.next();
			str.add(next.getKey());
			StringJoiner busstr = new StringJoiner("\n");
			next.getValue().stream().forEach(o -> busstr.add(o.toString()));
			str.add(busstr.toString());
		}
		return str.toString();
	}
	
	public synchronized void push(Date time, Bus busInfo, boolean isTouch) {
		List<Bus> busList = this.buses.getOrDefault(busInfo.getName(), Collections.synchronizedList(new ArrayList<Bus>()));
		if(busList.size() > 0 && !isTouch) {
			Bus bus2 = busList.get(busList.size() - 1);
			int timeGap = ((int) (busInfo.getTime().getTime() - bus2.getTime().getTime())) / 1000;
			int locationGap = busInfo.getLocation() - bus2.getLocation();
			int speed = locationGap / timeGap;
			busInfo.setSpeed(speed);
		}
		busList.add(busInfo);
		
		this.buses.put(busInfo.getName(), busList);
		if (busList.size() > 2) {
			busList.remove(0);
		}
	}
	public synchronized void push(Date time, String busInfo, boolean isTouch) {
		String[] busStr = busInfo.split(",");
		Bus bus = new Bus();
		bus.setTime(time);
		bus.setName(busStr[0]);
		bus.setLocation(Integer.parseInt(busStr[1]));
		this.push(time, bus, false);
	}
	
	
	public synchronized List<Bus> getPrePostBusInfo(Date time) {
		List<Bus> collect = this.buses.entrySet().stream()
				.map(e -> e.getValue().stream().sorted(locationComparator.reversed()).findFirst().orElseGet(Bus::new))
				.sorted(locationComparator).collect(Collectors.toList());
		
		return collect;
	}
	
	public synchronized Bus[] getPrePostBusInfo(Bus bus, Date time) {
		List<Bus> prePostBusInfo = this.getPrePostBusInfo(time);
		Bus noBus = new Bus();
		noBus.setName("NOBUS");
		noBus.setTime(time);
		noBus.setLocation(0);
		//직전
		Bus[] buses = new Bus[2];
		buses[0] = prePostBusInfo.stream().filter(o -> o.getLocation() < 1).collect(Collectors.maxBy(Comparator.comparing(Bus::getLocation))).orElseGet(() ->{
			return noBus;
		});
		//직후
		buses[1] = prePostBusInfo.stream().filter(o -> o.getLocation() > 1).collect(Collectors.minBy(Comparator.comparing(Bus::getLocation))).orElseGet(() ->{
			return noBus;
		});
		return buses;
	}
	
	public synchronized List<String> transformPrePostBusInfo(Date currentTime) {
		List<String> ret = new ArrayList<String>();
		this.getPrePostBusInfo(currentTime).stream().sorted(Comparator.comparing(Bus::getName)).forEach(o -> {
			Bus[] buses = this.getPrePostBusInfo(o, currentTime);
			//11:00:06#BUS01#BUS03,05370#BUS02,01230
			int prediff = buses[0].getLocation() - o.getLocation() <  0 ? 0 : buses[0].getLocation() - o.getLocation() ;
			int postdiff = o.getLocation() - buses[1].getLocation() <  0 ? 0 : o.getLocation() - buses[1].getLocation();
			
			String str = String.format("%s#%s#%s,%05d#%s,%05d\n", 
					RunManager.transformToString(o.getTime()), o.getName(), buses[0].getName(), buses[0].getName().equals("NOBUS") ? 0 : prediff, buses[1].getName(), buses[1].getName().equals("NOBUS") ? 0 : postdiff);
			ret.add(str);
		});
		return ret;
	}

	
	public synchronized Bus getNearestBusInfoFromStation(Station station, Date time) {
		Bus bus = this.getPrePostBusInfo(time).stream().filter(o -> o.getLocation() <= station.getLocation()).max(BusManager.locationComparator).orElseGet(() -> {
			Bus tmp = new Bus();
			tmp.setName("NOBUS");
			tmp.setTime(time);
			
			return tmp;
		}) ;
		return bus;
	}
	
	private synchronized Arrival getFastestBusToStation(Station station) {
		Bus min = null;
		int rm = Integer.MAX_VALUE;
		System.out.println("####");
		System.out.println(station.getName());
		for(Iterator iter = this.buses.keySet().iterator() ; iter.hasNext() ; ) {
			List<Bus> busList = this.buses.get(iter.next());
			busList.sort(BusManager.locationComparator);
			Bus bus = busList.get(busList.size() -1);
			int estimatedTimeToGetToTheStation = this.getEstimatedTimeToGetToTheStation(bus.copy(), station, 0);
			System.out.println(bus.getName());
			System.out.println(estimatedTimeToGetToTheStation);
			
			if(rm > estimatedTimeToGetToTheStation && estimatedTimeToGetToTheStation > 0) {
				rm = estimatedTimeToGetToTheStation;
				min = bus;
			}
//			System.out.println(min);
		}
		if(min == null) {
			min = new Bus();
			min.setName("NOBUS");
			min.setLocation(0);
			min.setTime(RunManager.transformToDate("00:00:00"));
			Arrival ar = new Arrival(station, min, RunManager.transformToDate("00:00:00"));
			return ar;
		} else {
			Date date = new Date(min.getTime().getTime() + rm * 1000);
			Arrival ar = new Arrival(station, min, date);
			return ar;
		}
	}
	
	private synchronized int getEstimatedTimeToGetToTheStation(Bus bus, Station station, int needTime) { 
		if(bus.getLocation() > station.getLocation()) return 0;
		
		StationManager stationManager = StationManager.getInstance();
		int remain = 0;
		while(true) {
			Optional<Station> nextStationFromBus = stationManager.getNextStationStationFromBus(bus);
			Station next = nextStationFromBus.get();
			if(station.getName().equals(next.getName())) {
				int speed = Math.min(station.getSpeed(), bus.getSpeed());
				if (speed == 0)  {
					speed = bus.getSpeed();
				}
				remain += (next.getLocation() - bus.getLocation()) / speed;
				bus.setLocation(next.getLocation());
				break;
			}
			Optional<Station> prevStationFromBus = stationManager.getPreviousStationFromBus(bus);
			int speed = 0;
			
			if(prevStationFromBus.isPresent()) {
				Station prev = prevStationFromBus.get();
				speed = Math.min(prev.getSpeed(), bus.getSpeed());
			} else {
				speed = bus.getSpeed();
			}
			remain += (next.getLocation() - bus.getLocation()) / speed;
			bus.setLocation(next.getLocation());
		}
		
		
		return remain;
	}
	
	private synchronized int getEstimatedTimeToGetToTheStation2(Bus bus, Station station, int needTime) {
		if(bus.getLocation() > station.getLocation()) return 0;
		
		StationManager stationManager = StationManager.getInstance();
		Optional<Station> nextStationStationFromBus = stationManager.getNextStationStationFromBus(bus);
		Station nextStation = nextStationStationFromBus.get();
		if(! nextStation.getName().equals(station.getName())) {
			//종착점과 버스의 다음 정류장이 다른 경우
			Optional<Station> previousStation = stationManager.getPreviousStation(station);
			if(previousStation.isPresent()) {
				needTime += this.getEstimatedTimeToGetToTheStation(bus, nextStation, needTime);
			} else {
				//종착역이 버스의 다음 정류장(1)과 다를 경우 정류장(1) 이전 정류장이 없을 경우는 없어서 구현 안함
				
			}
		}
//		} else {
			//종착점과 버스의 다음 정류장이 같은 경우
		Optional<Station> previousStationFromBus = stationManager.getPreviousStationFromBus(bus);
		int remainDistance = 0;
		int speed = 0;
		remainDistance = nextStation.getLocation() - bus.getLocation();
		if(previousStationFromBus.isPresent()) {
			Station tmp = previousStationFromBus.get();
			speed = Math.min(bus.getSpeed(), tmp.getSpeed());
		}else {
			speed = bus.getSpeed();
		}
		bus.setLocation(bus.getLocation() + remainDistance);
		needTime = remainDistance / speed;
//		}
		
		return needTime;
	}
	
	public List<String> transformFastestBusToStations(Date curTime) {
		List<Arrival> arrivals = this.getFastestBusToStations();
		List<String> ret = new ArrayList<String>();
		arrivals.stream().forEach(o -> {
			String str = String.format("%s#%s#%s,%s\n", 
					RunManager.transformToString(curTime), o.getStation().getName(), o.getBus().getName(), RunManager.transformToString(o.getEstimatedTime()));
			ret.add(str);
		});
		return ret;
	}
	
	public List<Arrival> getFastestBusToStations() {
		StationManager stationManager = StationManager.getInstance();
		
		List<Station> stations = stationManager.getStations();
		List<Arrival> collect = stations.stream().map(this::getFastestBusToStation).collect(Collectors.toList());
		return collect;
	}
	
	
	
//	public static void main(String[] args) {
////		StationManager stationManager = StationManager.getInstance();
//		Bus bus = new Bus();
//		bus.setLocation(900);
//		bus.setName("BUS01");
//		bus.setTime(RunManager.transformToDate("00:00:05"));
//		bus.setSpeed(10);
//		Station e = new Station();
//		e.setName("STA05");
//		e.setLocation(4620);
//		e.setSpeed(72);
//		BusManager busManager = BusManager.getInstance();
//		int estimatedTimeToGetToTheStation = busManager.getEstimatedTimeToGetToTheStation(bus, e);
//		System.out.println(estimatedTimeToGetToTheStation);
//		
//	}
	
}
