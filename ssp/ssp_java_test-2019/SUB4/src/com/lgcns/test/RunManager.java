package com.lgcns.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
//		StationManager stationManager = StationManager.getInstance();
		
		try(ServerSocket serverSocket = new ServerSocket(9876)) {
			ExecutorService executorService = Executors.newFixedThreadPool(50);
			Thread.interrupted();
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
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String readLine = null;
				String busName = null;
				BusManager busManager = BusManager.getInstance();
				while((readLine = reader.readLine()) != null) {
					
					if(readLine.startsWith("BUS")) {
						busName = readLine;
					} else if(readLine.startsWith("MOBILE")) {
						
					} else if(readLine.startsWith("STA")) {
						
					} else if(readLine.startsWith("PRINT")) {
						
					} else {
						System.out.println(busName + "#"+ readLine);
						String[] split = readLine.split("#");
						if(split.length > 1) {
							busManager.push(String.format("%s#%s,%s", split[0], busName, split[1]));
						} else {
							busManager.push(readLine);
						}
					} 
					System.out.print("");
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}




