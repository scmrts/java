package com.lgcns.test.test2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import org.omg.CORBA.StringHolder;


public class RunManager {

	public static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
	public static ReadLock readLock = reentrantReadWriteLock.readLock();
	public static WriteLock writeLock = reentrantReadWriteLock.writeLock();
	
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
		
//		BusManager busManager = BusManager.getInstance();
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
				String busName = null;
				BusManager busManager = BusManager.getInstance();
				while((readLine = reader.readLine()) != null) {
					writeLock.lock();
					System.out.println("readLine    --> " +busName+"  "+readLine);
					if(readLine.startsWith("BUS")) {
						busName = readLine;
					} else if(readLine.startsWith("MOBILE")) {
						
					} else if(readLine.startsWith("STA")) {
						
					} else if(readLine.startsWith("PRINT")) {
						Date lastUpdateTime = busManager.getLastUpdateTime();
						List<String> prePostBusInfo = busManager.transformPrePostBusInfoToString(busManager.getPrePostBusInfo(lastUpdateTime));
						prePostBusInfo.stream().forEach(t -> {
							try {
								writer.write(t);
							} catch (IOException e) {
								e.printStackTrace();
							}
						});
					} else {
						String[] split = readLine.split("#");
						if(split.length > 1) {
							busManager.push(String.format("%s#%s,%s", split[0], busName, split[1]));
						} else {
							busManager.push(readLine);
						}
					} 
					writeLock.unlock();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}




