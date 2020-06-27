package com.lgcns.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;


public class StationManager {
	
	ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
	ReadLock readLock = reentrantReadWriteLock.readLock();
	WriteLock writeLock = reentrantReadWriteLock.writeLock();
	
	public List<Station> stations = new ArrayList<Station>();
	private static StationManager instance;
	public static StationManager getInstance() {
		if(instance == null) {
			instance = new StationManager();
		}
		
		return instance;
	}
	public void push(String curLine) {
		RunManager.writeLock.lock();
		String[] line = curLine.split("#");
		Station e = new Station();
		e.name = line[0];
		e.location = Integer.parseInt(line[1]);
		e.speed = Integer.parseInt(line[2]) * 1000 / 3600;
		this.stations.add(e);
		RunManager.writeLock.unlock();
	}
	
	public void printNearestBusInfo(Station station, Bus bus) {
		RunManager.readLock.lock();
		try {
			Path path = Paths.get("./OUTFILE/ARRIVAL.TXT");
			int distance = bus.name.equals("NOBUS") ? 0 : station.location - bus.location;
			String str = String.format("%s#%s#%s,%05d\n", bus.getTime(), station.name, bus.name, distance);
			Files.write(path, str.getBytes(),  StandardOpenOption.APPEND,StandardOpenOption.CREATE);
			
			
		} catch(Exception e) {e.printStackTrace();}
		finally {
			RunManager.readLock.unlock();
		}
	}
	public void printNearestBusWithArraivalTime(Station station, Date time) {
		RunManager.readLock.lock();
		String[] nearestBusWithArrivalTime = station.getNearestBusWithArrivalTime(time);
		
		SimpleDateFormat dformat = new SimpleDateFormat("hh:mm:ss");
		
		String format = null;
		if(nearestBusWithArrivalTime == null) {
			format = String.format("%s#%s#%s,%s\n", dformat.format(time), station.name, "NOBUS", "00:00:00");
		} else {
			format = String.format("%s#%s#%s,%s\n", dformat.format(time), station.name, nearestBusWithArrivalTime[0], nearestBusWithArrivalTime[1]);
		}
		Path path = Paths.get("./OUTFILE/SIGNAGE.TXT");
		try {
			Files.write(path, format.getBytes(),  StandardOpenOption.APPEND,StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		RunManager.readLock.unlock();
	}
	
}
