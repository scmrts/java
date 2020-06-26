package com.lgcns.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.lgcns.test.vo.Bus;
import com.lgcns.test.vo.Station;

public class BusManager{
	public Map<String, List<Bus>> buses = new HashMap<String, List<Bus>>();
	
	public static Comparator<Bus> timeComparator = (o1, o2) -> o1.time.compareTo(o2.time); 
	
	private static BusManager instance;
	public static BusManager getInstance() {
		if(instance == null) {
			instance = new BusManager();
		}
		
		return instance;
	}
	
	
	public static Comparator<Bus> fromComparator = (o1, o2) -> {
		return o1.from - o2.from;
	};
	
	public void push(String curLine) {
		String[] line = curLine.split("#");
		String time = line[0];
		List<Bus> buses = Arrays.stream(line).skip(1).map(o ->  {
			Bus bus = new Bus();
			String[] businfo = o.split(",");
			bus.name = businfo[0];
			bus.from = Integer.parseInt(businfo[1]);
			SimpleDateFormat transFormat = new SimpleDateFormat("HH:mm:ss");

			try {
				bus.time = transFormat.parse(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return bus;
		}).collect(Collectors.toList());;
		buses.sort(fromComparator);
		
		for(int i = 0 ; i< buses.size() ; i++) {
			if(i != buses.size() - 1)
				buses.get(i).after = buses.get(i+1);
			if(i != 0)
				buses.get(i).before = buses.get(i-1);
		}
		buses.forEach(o -> BusManager.this.touch(o));
	}
	public void touch(Bus bus) {
		List<Bus> old = this.buses.getOrDefault(bus.name, new ArrayList<Bus>());
		if(old == null) {
			this.buses.put(bus.name, old);
		} 
		if(old.size() > 1) {
			old.remove(0);
		}
		old.add(bus);
		this.buses.put(bus.name,  old);
	}
	

	public List<Bus> getBusListAtTime(String time) {
		List<Bus> collect = this.buses.entrySet().stream().map(o -> {
			Optional<Bus> findAny = BusManager.this.buses.get(o.getKey()).stream().filter(b -> b.time.equals(time)).findAny();
			List<Bus> ret;
			if(findAny.isPresent()) { 
				return findAny.get();
			} else {
				//TODO  누락 버스의 현재 시점 정보 생성
				System.out.println();
				Bus bus = new Bus();
				bus.name = o.getKey();
				
				List<Bus> busList = o.getValue();
				
				busList.sort(timeComparator);
				Bus before = busList.get(0);
				Bus after = busList.get(1);
				bus.from = after.from;
				int seconds = (int) (after.time.getTime() - before.time.getTime()) / 1000;
				int distance = after.from - before.from;
				int speed = distance / seconds;
				
				SimpleDateFormat transFormat = new SimpleDateFormat("HH:mm:ss");
				try {
					Date curTime = transFormat.parse(time);
					int timeDiff = (int) (curTime.getTime() - after.time.getTime()) / 1000;
					bus.time = curTime;
					
					StationManager stationManager = StationManager.getInstance();
					List<Station> fstation = stationManager.stations.stream().filter(s -> s.distance > distance).sorted((s1, s2) -> s1.distance - s2.distance).collect(Collectors.toList());
					for (int i = 0; i < fstation.size(); i++) {
						if(bus.from > fstation.get(i).distance) {
							continue;
						}
						while(timeDiff > 0) {
//							speed =  Math.min(speed, fstation.get(i).speed * 1000 /(60 * 60));
							bus.from += speed;
//							bus.from += Math.min(speed, fstation.get(i).speed * 1000 /(60 * 60));
							timeDiff--;
							if(bus.from > fstation.get(i).distance) {
								break;
							}
						}
					}
					
					bus.from = after.from + speed * timeDiff;
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				
				return bus;
			}
		}).collect(Collectors.toList());
		return collect;
	}

}

