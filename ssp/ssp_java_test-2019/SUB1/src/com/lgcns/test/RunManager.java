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
		
		Path path = Paths.get("./OUTFILE/PREPOST.TXT");
		if(Files.exists(path)) {
			Files.delete(path);
		} 
		
		Files.lines(Paths.get("./INFILE/LOCATION.TXT")).forEach(o -> {
			if(o.equals("PRINT")) {
				String time = before.value.split("#")[0];
				Date currentTime = RunManager.transformToDate(time);
				busManager.getPrePostBusInfo(busManager.getPrePostBusInfo(currentTime)).forEach(s -> {
					try {
						Files.write(path, s.getBytes(), StandardOpenOption.APPEND,StandardOpenOption.CREATE);
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
