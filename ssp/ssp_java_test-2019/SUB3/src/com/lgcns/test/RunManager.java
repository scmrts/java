package com.lgcns.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

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
	
	public static String transformToString(Date time) {
		SimpleDateFormat transFormat = new SimpleDateFormat("HH:mm:ss");

		return transFormat.format(time);
	}
	
	public static void main(String[] args) throws IOException {
		StringHolder before = new StringHolder();;
		BusManager busManager = BusManager.getInstance();
		StationManager stationManager = StationManager.getInstance();
		
		Path path = Paths.get("./OUTFILE/PREPOST.TXT");
		if(Files.exists(path)) {
			Files.delete(path);
		} 
		
		Path path2 = Paths.get("./OUTFILE/ARRIVAL.TXT");
		if(Files.exists(path2)) {
			Files.delete(path2);
		} 
		
		Files.lines(Paths.get("./INFILE/LOCATION.TXT")).forEach(o -> {
			if(o.equals("PRINT")) {
				String time = before.value.split("#")[0];
				Date currentTime = RunManager.transformToDate(time);
				busManager.transformPrePostBusInfo(busManager.getPrePostBusInfo(currentTime)).forEach(s -> {
					try {
						Files.write(path, s.getBytes(), StandardOpenOption.APPEND,StandardOpenOption.CREATE);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});;
				stationManager.getNearestBusesInfo(currentTime).forEach(s -> {
					try {
						Files.write(path2, s.getBytes(), StandardOpenOption.APPEND,StandardOpenOption.CREATE);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});;
			} else {
				String[] info = o.split("#");
				Date currentTime = RunManager.transformToDate(info[0]);
				Stream<String> stream = Arrays.stream(info);
				stream.skip(1).forEach(s-> busManager.push(currentTime, s));
				before.value = o;
			}
		});
		System.out.print("");
	}
}
