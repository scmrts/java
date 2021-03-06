package com.lgcns.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



public class StationManager {
	public List<Station> stations = new ArrayList<Station>();
	private static StationManager instance;
	public static synchronized StationManager getInstance() {
		if(instance == null) {
			instance = new StationManager();
		}
		
		return instance;
	}
	
	public List<Station> getStations() {
		return stations;
	}
	
	public Optional<Station> getPreviousStation(Station station) {
		
		Optional<Station> collect = this.stations.stream().filter(o -> o.getLocation() < station.getLocation()).collect(Collectors.maxBy(Comparator.comparing(Station::getLocation)));
		
		return collect;
	}
	
	private StationManager() {
		try {
			Files.lines(Paths.get("./INFILE/STATION.TXT")).forEach(line -> this.push(line));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void push(String curLine) {
		String[] line = curLine.split("#");
		Station e = new Station();
		e.setName(line[0]);
		e.setLocation(Integer.parseInt(line[1]));
		e.setSpeed(Integer.parseInt(line[2]));
		this.stations.add(e);
	}
	
	public List<String> getNearestBusesInfo(Date time) {
		return this.stations.stream().map(o -> this.getNearestBusInfo(o, time)).collect(Collectors.toList());
	}
	
	public String getNearestBusInfo(Station station, Date time) {
		Bus bus = BusManager.getInstance().getNearestBusInfoFromStation(station, time);
		return String.format("%s#%s#%s,%05d\n", RunManager.transformToString(bus.getTime()), station.getName(), bus.getName(), station.getLocation() - bus.getLocation());
	}

	/**
	 * 특정 버스의 직전 버스 정유장
	 * @param bus
	 * @return
	 */
	public Optional<Station> getPreviousStationFromBus(Bus bus) {
		return this.stations.stream().filter(o -> o.getLocation() < bus.getLocation()).collect(Collectors.maxBy(Comparator.comparing(Station::getLocation)));
	}
	
	/**
	 * 특정 버스의 직후 버스 정유장
	 * @param bus
	 * @return
	 */
	public Optional<Station> getNextStationStationFromBus(Bus bus) {
		return this.stations.stream().filter(o -> o.getLocation() > bus.getLocation()).collect(Collectors.minBy(Comparator.comparing(Station::getLocation)));
	}

	
	
}
