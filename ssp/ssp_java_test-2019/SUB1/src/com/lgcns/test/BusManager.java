package com.lgcns.test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


public class BusManager {
	
	private Map<String, List<Bus>> buses = new ConcurrentHashMap<String, List<Bus>>();
	
	public static Comparator<Bus> timeComparator = (o1, o2) -> o1.getTime().compareTo(o2.getTime()); 
	
	public static Comparator<Bus> locationComparator = (o1, o2) ->o1.getLocation() - o2.getLocation();
	
	public static Comparator<Bus> nameComparator = (o1, o2) ->o1.getName().compareTo(o2.getName());
	
	private static BusManager instance;
	
	public static BusManager getInstance() {
		if(instance == null) {
			instance = new BusManager();
		}
		
		return instance;
	}
	
	public String toString() {
		StringJoiner str = new StringJoiner("\n");
//		this.buses.entrySet().stream().mapToObj(e -> e.getValue().stream().collect(Collectors.joining("\n")));
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
	
	public void push(Date time, String busInfo) {
		
		String[] busStr = busInfo.split(",");
		if(busStr.length != 2) {
			return;
		}
		Bus bus = new Bus();
		bus.setTime(time);
		bus.setName(busStr[0]);
		bus.setLocation(Integer.parseInt(busStr[1]));
		List<Bus> busList = this.buses.getOrDefault(bus.getName(), Collections.synchronizedList(new ArrayList<Bus>()));
		busList.add(bus);
		if (busList.size() > 2) {
			busList.remove(0);
		}
		this.buses.put(bus.getName(), busList);
	}
	
	public List<Bus> getPrePostBusInfo(Date time) {
		List<Bus> collect = this.buses.entrySet().stream()
				.map(e -> e.getValue().stream().sorted(locationComparator.reversed()).findFirst().orElseGet(Bus::new))
				.sorted(locationComparator).collect(Collectors.toList());
		
		return collect;
	}
	
	
	public List<String> getPrePostBusInfo(List<Bus> buses) {
		List<String> ret = new ArrayList<String>();
		try {
			this.buses.keySet().stream().sorted().forEach(k -> {
				Bus preBus = null; 
				Bus postBus = null;
				Bus curBus = null;
				Bus noBus = new Bus();
				noBus.setName("NOBUS");
				noBus.setLocation(0);	
				for(int i = 0 ; i < buses.size(); i++) {
					Bus bus = buses.get(i);
					if(bus.getName().equals(k)) {
						curBus = bus;
						if(buses.size() == 1) {
							preBus = noBus;
							postBus = noBus;
						}
						if(i == 0) {
							preBus = buses.get(i+1);
							postBus = noBus;
						} else if(i == buses.size() - 1) {
							preBus = noBus;
							postBus = buses.get(i-1);
						} else {
							preBus = buses.get(i+1);
							postBus = buses.get(i-1);
						}
						break;
					} else {
						continue;
					}
					
				}
				
				int prediff = preBus.getLocation() - curBus.getLocation() <  0 ? 0 : preBus.getLocation() - curBus.getLocation() ;
				int postdiff = curBus.getLocation() - postBus.getLocation() <  0 ? 0 : curBus.getLocation() - postBus.getLocation();
				String str = String.format("%s#%s#%s,%05d#%s,%05d\n", 
						RunManager.transformToString(curBus.getTime()), curBus.getName(), preBus.getName(), preBus.getName().equals("NOBUS") ? 0 : prediff, postBus.getName(), postBus.getName().equals("NOBUS") ? 0 : postdiff);
				ret.add(str);
			});
		} catch(Exception e) {e.printStackTrace();}
		
		return ret;
	}
}
