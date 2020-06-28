package com.lgcns.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.omg.CORBA.StringHolder;


public class RunManager {

	public static Date transformToDate(String time) {
		SimpleDateFormat transFormat = new SimpleDateFormat("HH:mm:ss");

		try {
			return transFormat.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) throws IOException {
		
		BusManager busManager = BusManager.getInstance();
		StationManager stationManager = StationManager.getInstance();
		
		StringHolder before = new StringHolder();;
		
		Files.lines(Paths.get("./INFILE/STATION.TXT")).forEach(line -> stationManager.push(line));
		
		Files.lines(Paths.get("./INFILE/LOCATION.TXT")).forEach(o -> {
			if(o.equals("PRINT")) {
				String time = before.value.split("#")[0];
				Date currentTime = RunManager.transformToDate(time);
				List<Bus> prePostBusInfo = busManager.getPrePostBusInfo(currentTime);
				busManager.printPrePostBusInfo(prePostBusInfo);
				Path path = Paths.get("./OUTFILE/ARRIVAL.TXT");
				if(Files.exists(path)) {
					try {
						Files.delete(path);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} 
				stationManager.stations.stream().forEach(s -> stationManager.printNearestBusInfo(s, s.getNearestBus(currentTime)));
				
			} else {
				busManager.push(o);
				before.value = o;
			}
		});
		
		
		
	}

	
}




