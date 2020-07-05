package com.lgcns.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.StringJoiner;

import org.omg.CORBA.IntHolder;
import org.omg.CORBA.StringHolder;

public class RunManager {

	public static void main(String[] args) throws IOException {
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
				String tmp = null;
				
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
//						stj.add(count+"#"+cu);
						
						Files.write(path, (count+"#"+cu+"\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
					} else {
//						stj.add(cu);
						Files.write(path, (cu+"\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND, StandardOpenOption.WRITE);
					}
					
				}
				
				return FileVisitResult.CONTINUE;
			}
		});
	}
}
