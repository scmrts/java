package com.lgcns.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
		
		StationManager stationManager = StationManager.getInstance();
		Files.lines(Paths.get("./INFILE/STATION.TXT")).forEach(line -> stationManager.push(line));
		try(ServerSocket serverSocket = new ServerSocket(9876)) {
			ExecutorService executorService = Executors.newFixedThreadPool(50);
			while(!Thread.currentThread().isInterrupted()) {
				try {
					Socket client = serverSocket.accept();
					
					executorService.execute(new ClientService(client));
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		}
		
		
//		
//		StringHolder before = new StringHolder();;
//		BusManager busManager = BusManager.getInstance();
//		StationManager stationManager = StationManager.getInstance();
//		
//		Path path = Paths.get("./OUTFILE/PREPOST.TXT");
//		if(Files.exists(path)) {
//			Files.delete(path);
//		} 
//		
//		Path path2 = Paths.get("./OUTFILE/ARRIVAL.TXT");
//		if(Files.exists(path2)) {
//			Files.delete(path2);
//		} 
//		Path path3 = Paths.get("./OUTFILE/SIGNAGE.TXT");
//		if(Files.exists(path3)) {
//			Files.delete(path3);
//		} 
//		
//		Files.lines(Paths.get("./INFILE/LOCATION.TXT")).forEach(o -> {
//			if(o.equals("PRINT")) {
//				String time = before.value.split("#")[0];
//				Date currentTime = RunManager.transformToDate(time);
//				busManager.transformPrePostBusInfo(currentTime).forEach(s -> {
//					try {
//						Files.write(path, s.getBytes(), StandardOpenOption.APPEND,StandardOpenOption.CREATE);
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				});;
//				stationManager.getNearestBusesInfo(currentTime).forEach(s -> {
//					try {
//						Files.write(path2, s.getBytes(), StandardOpenOption.APPEND,StandardOpenOption.CREATE);
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				});;
//				busManager.transformFastestBusToStations(currentTime).forEach(s -> {
//					try {
//						Files.write(path3, s.getBytes(), StandardOpenOption.APPEND,StandardOpenOption.CREATE);
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				});
//			} else {
//				String[] info = o.split("#");
//				Date currentTime = RunManager.transformToDate(info[0]);
//				Stream<String> stream = Arrays.stream(info);
//				stream.skip(1).forEach(s-> busManager.push(currentTime, s, false));
//				before.value = o;
//			}
//		});
//		System.out.print("");

	}
	
	
	private static class ClientService implements Runnable{

		private Socket socket;
		public ClientService(Socket socket) {
			this.socket = socket;
		}
		@Override
		public void run() {
			try(BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
				
				String readLine = null;
//				String busName = null;
				StringHolder busName = new StringHolder();
				BusManager busManager = BusManager.getInstance();
				Date lastUpdateTime = null;
				StationManager stationManager = StationManager.getInstance();
				while((readLine = reader.readLine()) != null) {
					System.out.println("readLine    --> " +busName+"  "+readLine);
					if(readLine.startsWith("BUS")) {
						busName.value = readLine;
					} else if(readLine.startsWith("MOBILE")) {
						
					} else if(readLine.startsWith("STA")) {
						
					} else if(readLine.startsWith("PRINT")) {
						List<String> prePostBusInfo = busManager.transformPrePostBusInfo(busManager.getCurrentTime());
						prePostBusInfo.stream().forEach(t -> {
							try {
								writer.write(t);
							} catch (IOException e) {
								e.printStackTrace();
							}
						});
						stationManager.getNearestBusesInfo(busManager.getCurrentTime()).forEach(s -> {
							try {
								writer.write(s);
							} catch (IOException e) {
								e.printStackTrace();
							}
						});
						busManager.transformFastestBusToStations(busManager.getCurrentTime()).forEach(s -> {
							try {
								writer.write(s);
							} catch (IOException e) {
								e.printStackTrace();
							}
						});
					} else {
						
						String[] info = readLine.split("#");
						Date currentTime = RunManager.transformToDate(info[0]);
						busManager.setCurrentTime(currentTime);
						Stream<String> stream = Arrays.stream(info);
						stream.skip(1).forEach(s-> busManager.push(currentTime, busName.value + "," + s, false));
						System.out.print("");
					} 
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
