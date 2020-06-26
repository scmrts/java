package com.lgcns.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.omg.CORBA.StringHolder;

import com.lgcns.test.vo.Bus;

public class RunManager {

	public static void main(String[] args) throws IOException {
		BusManager busManager = BusManager.getInstance();
		StationManager stationManager = StationManager.getInstance();
		StringHolder before = new StringHolder();;
		
		Files.lines(Paths.get("./INFILE/STATION.TXT")).forEach(line -> stationManager.push(line));
		
		
		Files.lines(Paths.get("./INFILE/LOCATION.TXT")).forEach(o -> {
			if(o.equals("PRINT")) {
				String time = before.value.split("#")[0];
				List<Bus> busListAtTime = busManager.getBusListAtTime(time);
				
				stationManager.stations.stream().forEach(station -> {
					Bus afterBus = busListAtTime.stream().filter(bus -> bus.from < station.distance)
							.max((bus1, bus2) -> bus1.from - bus2.from).orElse(new Bus("NOBUS"));
					
					System.out.println(String.format("%s#%s#%s#%05d", 
							time, station.name, afterBus.name, station.distance - afterBus.from));
				});
			} else {
				busManager.push(o);
				before.value = o;
			}
		});
	}

	
}




