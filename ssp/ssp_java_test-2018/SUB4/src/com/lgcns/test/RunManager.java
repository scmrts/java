package com.lgcns.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import org.omg.CORBA.StringHolder;


public class RunManager {

	private static class ClientService implements Runnable{

		private Socket socket;
		private ServerSocket server;
		public ClientService(Socket socket, ServerSocket server) {
			this.socket = socket;
			this.server = server;
		}
		@Override
		public void run() {
			System.out.println(socket.hashCode());
			String[] contents = null;
			try(BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
					BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream())) {
				int bufferSize = 512;
				byte[] buffer = new byte[bufferSize];
//				int read = 0;
//				int currentLine = 0;
//				while((read = bufferedInputStream.read(buffer, 0, bufferSize)) != -1) {
//					String tmp = (new String(Arrays.copyOfRange(buffer, 0, read)));
//					
//					if(tmp.startsWith("ACK")) {
//						try {
//							bufferedOutputStream.write(contents[currentLine++].getBytes());
//						}catch(ArrayIndexOutOfBoundsException e) {
//							this.socket.close();
//							Thread.currentThread().interrupt();
//							break;
//						}
////							
////							
//					} else if(tmp.startsWith("ERR")){
//						bufferedOutputStream.write(contents[currentLine].getBytes());
//					} else if(isNumber(tmp)) {
//						currentLine = Integer.parseInt(tmp);
//						bufferedOutputStream.write(contents[currentLine++].getBytes());
//					} else { //파일명
//						contents = readFile(tmp).split("\n");
//						bufferedOutputStream.write(contents[currentLine++].getBytes());
//					}
//					bufferedOutputStream.flush();
//				}
				int currentLine = 0;
				while(!Thread.currentThread().isInterrupted()) {
					int read = bufferedInputStream.read(buffer, 0, bufferSize);
					String tmp = (new String(Arrays.copyOfRange(buffer, 0, read)));
					if(tmp.startsWith("ACK")) {
						bufferedOutputStream.write(contents[currentLine++].getBytes());
//						
//						
					} else if(tmp.startsWith("ERR")){
						bufferedOutputStream.write(contents[currentLine].getBytes());
					} else if(isNumber(tmp)) {
						currentLine = 0;
						contents = readFile(Integer.parseInt(tmp) - 1, tmp).split("\n");
						bufferedOutputStream.write(contents[currentLine++].getBytes());
					} else { //파일명
						contents = readFile(0, tmp).split("\n");
						bufferedOutputStream.write(contents[currentLine++].getBytes());
					}
					bufferedOutputStream.flush();
					if(currentLine == contents.length -1) {
						Thread.currentThread().interrupt();
					}
				}
				
			} catch (Exception e){
				e.printStackTrace();
			} finally {
				System.out.println(socket.isClosed());
			}
		}
	}
	public static void main(String[] args) throws IOException{
		
		ExecutorService executerService = Executors.newFixedThreadPool(10);
		try(ServerSocket server = new ServerSocket(9876);) {
			while(!Thread.currentThread().isInterrupted()) {
				Socket socket = server.accept();
				ClientService cl = new ClientService(socket, server);
				executerService.submit(cl);
					
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static boolean isNumber(String tmp) {
		boolean allMatch = tmp.chars().allMatch(o -> o >= 48 && o < 58);
		return allMatch;
	}
	
	
	public static String readFile(int line, String file) throws IOException {
		StringHolder h = new StringHolder();
		Files.walkFileTree(Paths.get("./BIGFILE"), new SimpleFileVisitor<Path>() {
			@Override
		    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				Path path = Paths.get("./OUTPUT/"+file.getFileName().toString());
				if(Files.notExists(Paths.get("./OUTPUT/"))) {
					Files.createDirectory(Paths.get("./OUTPUT/"));
				}
				if(Files.exists(path)) {
					Files.delete(path);
				} 
				
//				List<String> lines = Files.readAllLines(file);
				List<String> lines = Files.lines(file).skip(line).collect(Collectors.toList());
				StringJoiner stj = new StringJoiner("\n");
				for (int i = 0; i < lines.size(); i++) {
					int count = 1;
					String cu = lines.get(i);
					for (int j = i + 1; j < lines.size(); j++) {
						if(! cu.equals(lines.get(j))) {
							break;
						}
						count++;
						i++;
					}
					if(count > 1) {
						stj.add(count+"#"+cu);
						
					} else {
						stj.add(cu);
					}
					
				}
				BiFunction<Integer, Character, String> func = (a, b) -> {
					String out = "";
					for(int k = 0 ;k < a ; k++) {
						out += Character.toString(b);
					}
					return out;
				};
				
				String collect = Arrays.stream(stj.toString().split("\n")).map(line -> {
					char[] chLine = line.toCharArray();
					String ret = "";
					for (int i = 0; i < chLine.length; i++) {
						int count = 1;
						char cu = chLine[i];
						for (int j = i + 1; j < chLine.length; j++) {
							if(cu != chLine[j]) {
								break;
							}
							count++;
							i++;
						}
						if(count > 2) {
							if(cu >= 48 && cu <= 57) {
								ret += func.apply(count, cu);
							} else  {
								ret += count+Character.toString(cu);
							}
						} else if (count == 1){
							ret += Character.toString(cu);
						} else {
							ret += func.apply(count, cu);
						}
					}
					return ret;
				}).collect(Collectors.joining("\n"));
				
				
				String collect2 = collect.chars().<String>mapToObj(o -> {
					if(o >= 65 && o <= 90) {
						o = o - 5;
						if(o < 65) {
							o = o + 26;
						}
					} 
					return Character.toString((char) o);
				}).collect(Collectors.joining());
				h.value = collect2;
//				Files.write(path, collect2.getBytes());
				
				return FileVisitResult.CONTINUE;
			}
		});
		return h.value;
	}

	public static String readFile(String file) throws IOException {
		StringHolder h = new StringHolder();
		Files.walkFileTree(Paths.get("./BIGFILE"), new SimpleFileVisitor<Path>() {
			@Override
		    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				Path path = Paths.get("./OUTPUT/"+file.getFileName().toString());
				if(Files.notExists(Paths.get("./OUTPUT/"))) {
					Files.createDirectory(Paths.get("./OUTPUT/"));
				}
				if(Files.exists(path)) {
					Files.delete(path);
				} 
				
				List<String> lines = Files.readAllLines(file);
				StringJoiner stj = new StringJoiner("\n");
				for (int i = 0; i < lines.size(); i++) {
					int count = 1;
					String cu = lines.get(i);
					for (int j = i + 1; j < lines.size(); j++) {
						if(! cu.equals(lines.get(j))) {
							break;
						}
						count++;
						i++;
					}
					if(count > 1) {
						stj.add(count+"#"+cu);
						
					} else {
						stj.add(cu);
					}
					
				}
				BiFunction<Integer, Character, String> func = (a, b) -> {
					String out = "";
					for(int k = 0 ;k < a ; k++) {
						out += Character.toString(b);
					}
					return out;
				};
				
				String collect = Arrays.stream(stj.toString().split("\n")).map(line -> {
					char[] chLine = line.toCharArray();
					String ret = "";
					for (int i = 0; i < chLine.length; i++) {
						int count = 1;
						char cu = chLine[i];
						for (int j = i + 1; j < chLine.length; j++) {
							if(cu != chLine[j]) {
								break;
							}
							count++;
							i++;
						}
						if(count > 2) {
							if(cu >= 48 && cu <= 57) {
								ret += func.apply(count, cu);
							} else  {
								ret += count+Character.toString(cu);
							}
						} else if (count == 1){
							ret += Character.toString(cu);
						} else {
							ret += func.apply(count, cu);
						}
					}
					return ret;
				}).collect(Collectors.joining("\n"));
				
				
				String collect2 = collect.chars().<String>mapToObj(o -> {
					if(o >= 65 && o <= 90) {
						o = o - 5;
						if(o < 65) {
							o = o + 26;
						}
					} 
					return Character.toString((char) o);
				}).collect(Collectors.joining());
				h.value = collect2;
//				Files.write(path, collect2.getBytes());
				
				return FileVisitResult.CONTINUE;
			}
		});
		return h.value;
	}
}
