package com.lgcns.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.lgcns.test.vo.Bus;

public class BusHistory{
	public Map<String, List<Bus>> buses = new HashMap<String, List<Bus>>();
	
	public void push(String curLine) {
		String[] line = curLine.split("#");
		String time = line[0];
		List<Bus> buses = Arrays.stream(line).skip(1).map(o ->  {
			Bus bus = new Bus();
			String[] businfo = o.split(",");
			bus.name = businfo[0];
			bus.from = Integer.parseInt(businfo[1]);
			bus.time = time;
			return bus;
		}).collect(Collectors.toList());;
		buses.sort(new Comparator<Bus>() {
			@Override
			public int compare(Bus o1, Bus o2) {
				return o1.from - o2.from;
			}
		});
		for(int i = 0 ; i< buses.size() ; i++) {
			if(i != buses.size() - 1)
				buses.get(i).after = buses.get(i+1);
			if(i != 0)
				buses.get(i).before = buses.get(i-1);
		}
		buses.forEach(o -> BusHistory.this.touch(o));
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
			Optional<Bus> findAny = BusHistory.this.buses.get(o.getKey()).stream().filter(b -> b.time.equals(time)).findAny();
			List<Bus> ret;
			if(findAny.isPresent()) { 
				return findAny.get();
			} else {
				//TODO  누락 버스의 현재 시점 정보 생성
				BusHistory.this.buses.get(o.getKey()).stream().max(new Comparator<Bus>() {
					@Override
					public int compare(Bus o1, Bus o2) {
						
						return 0;
					}
				});
				return null;
			}
		}).collect(Collectors.toList());
		return collect;
	}

}

