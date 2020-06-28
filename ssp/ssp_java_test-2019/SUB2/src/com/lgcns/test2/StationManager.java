package com.lgcns.test2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class StationManager {
	public List<Station> stations = new ArrayList<Station>();
	private static StationManager instance;
	public static StationManager getInstance() {
		if(instance == null) {
			instance = new StationManager();
		}
		
		return instance;
	}
	public void push(String curLine) {
		String[] line = curLine.split("#");
		Station e = new Station();
		e.name = line[0];
		e.location = Integer.parseInt(line[1]);
//		if(line.length > 2) {
//			e.speed = Integer.parseInt(line[2]);
//		}
		this.stations.add(e);
	}
	
	public void printNearestBusInfo(Station station, Bus bus) {
		try {
			Path path = Paths.get("./OUTFILE/ARRIVAL.TXT");
			String str = String.format("%s#%s#%s,%05d\n", bus.getTime(), station.name, bus.name, station.location - bus.location);
			Files.write(path, str.getBytes(),  StandardOpenOption.APPEND,StandardOpenOption.CREATE);
			
			
		} catch(Exception e) {e.printStackTrace();}
		
	}
}
