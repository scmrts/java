package com.lgcns.test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class BusManager {

	public Map<String, List<Bus>> buses = new HashMap<String, List<Bus>>();
	
	public static Comparator<Bus> timeComparator = (o1, o2) -> o1.time.compareTo(o2.time); 
	
	public static Comparator<Bus> locationComparator = (o1, o2) ->o1.location - o2.location;
	
	public static Comparator<Bus> nameComparator = (o1, o2) ->o1.name.compareTo(o2.name);
	
	private static BusManager instance;
	
	public static BusManager getInstance() {
		if(instance == null) {
			instance = new BusManager();
		}
		
		return instance;
	}
	
	/**
	 * 한줄을 읽어서 buses에 추가한다.
	 * @param curLine
	 */
	public void push(String curLine) {
		String[] line = curLine.split("#");
		if(line.length > 1) {
			String time = line[0];
			List<Bus> buses = Arrays.stream(line).skip(1).map(o ->  {
				Bus bus = new Bus();
				String[] businfo = o.split(",");
				bus.name = businfo[0];
				bus.location = Integer.parseInt(businfo[1]);
				SimpleDateFormat transFormat = new SimpleDateFormat("HH:mm:ss");
	
				try {
					bus.time = transFormat.parse(time);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return bus;
			}).collect(Collectors.toList());;
			buses.sort(locationComparator);
			
			buses.forEach(o -> BusManager.this.touch(o));
		}
		BusManager busManager = BusManager.getInstance();
		busManager.touch(RunManager.transformToDate(line[0]));
	}
	
	/**
	 * 특정 시간에 버스 위치를 추정
	 * @param curTime
	 */
	private void touch(Date curTime) {
		BusManager busManager = BusManager.getInstance();
		busManager.buses.entrySet().stream().forEach(e -> {
			/**
			 * 해당 시간에 버스 정보가 없으면 현재 시간에 맞는 버스 위치를 만들어 buses를 업데이트 한다. 
			 */
			Date d = curTime;
			if(e.getValue().stream().noneMatch(o -> o.time.getTime() == curTime.getTime())) {
				List<Bus> curBus = e.getValue();
				Bus prev = curBus.get(0);
				Bus next = curBus.get(1);

				Bus bus = new Bus();
				bus.name = e.getKey();
				bus.location = next.location;
				bus.time = curTime;
				bus.speed = next.speed;
				int timeGap = (int) (curTime.getTime() - next.time.getTime()) / 1000;
				
				Station beforeStation = StationManager.getInstance().stations.stream().filter(s -> s.location < next.location).max((a, b) -> a.location - b.location).orElseGet(Station::new);
				
				List<Station> stations = StationManager.getInstance().stations.stream().filter(s -> s.location >= beforeStation.location).collect(Collectors.toList());
				
				
				while(timeGap > 0 ) {
					for(Station station : stations) {
						bus.location += Math.min(bus.speed, station.speed);
						if(--timeGap <= 0) {
							break;
						}
						
						if(bus.location >= station.location) {
							continue;
						}
					}
				}
				this.touch(bus, true);
			}
		});
		System.out.println();
	}

	public void touch(Bus bus) {
		this.touch(bus, false);
	}
	
	/**
	 * 최신 버스 정보 buses 업데이트
	 * @param bus
	 */
	private void touch(Bus bus, boolean isAuto) {
		List<Bus> old = this.buses.getOrDefault(bus.name, new ArrayList<Bus>());
		if(old.size() == 0) {
			this.buses.put(bus.name, old);
		} 
		if(old.size() > 1) {
			old.remove(0);
		}
		if(! isAuto) {
			bus.setSpeed(old.stream().sorted(BusManager.timeComparator).findFirst());
		}
		old.add(bus);
		this.buses.put(bus.name,  old);
	}
	
	/**
	 * 선후행 버스 목록 조회
	 * @param time
	 * @return
	 */
	public List<Bus> getPrePostBusInfo(Date time) {
		List<Bus> collect = this.buses.entrySet().stream()
				.map(e -> e.getValue().stream().sorted(locationComparator.reversed()).findFirst().orElseGet(Bus::new))
				.sorted(locationComparator).collect(Collectors.toList());
		
		return collect;
	}
	
	/** 
	 * 선후행 버스 목록 출력
	 * @param buses
	 */
	public void printPrePostBusInfo(List<Bus> buses) {
		try {
			Path path = Paths.get("./OUTFILE/PREPOST.TXT");
			if(Files.exists(path)) {
				Files.delete(path);
			} 
			
			this.buses.keySet().stream().sorted().forEach(k -> {
				Bus preBus = null; 
				Bus postBus = null;
				Bus curBus = null;
				Bus noBus = new Bus();
				noBus.name = "NOBUS";
				noBus.location = 0;	
				for(int i = 0 ; i < buses.size(); i++) {
					Bus bus = buses.get(i);
					if(bus.name.equals(k)) {
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
				
				int prediff = preBus.location - curBus.location <  0 ? 0 : preBus.location - curBus.location ;
				int postdiff = curBus.location - postBus.location <  0 ? 0 : curBus.location - postBus.location;
				String str = String.format("%s#%s#%s,%05d#%s,%05d\n", 
						curBus.getTime().toString(), curBus.name, preBus.name, preBus.name.equals("NOBUS") ? 0 : prediff, postBus.name, postBus.name.equals("NOBUS") ? 0 : postdiff);
				
				try {
					Files.write(path, str.getBytes(),  StandardOpenOption.APPEND,StandardOpenOption.CREATE);
				} catch(Exception e) {e.printStackTrace();}
			});
		} catch(Exception e) {e.printStackTrace();}
		
	}
}
