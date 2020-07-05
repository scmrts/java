package com.lgcns.test;

import java.io.IOException;
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
import java.util.function.BiFunction;
import java.util.stream.Collectors;


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
							ret += count+Character.toString(cu);
							
						} else if (count == 1){
							ret += Character.toString(cu);
						} else {
							BiFunction<Integer, Character, String> func = (a, b) -> {
								String out = "";
								for(int k = 0 ;k < a ; k++) {
									out += Character.toString(b);
								}
								return out;
							};
							ret += func.apply(count, cu);
						}
					}
					return ret;
				}).collect(Collectors.joining("\n"));
				
				Files.write(path, collect.getBytes());
				
				return FileVisitResult.CONTINUE;
			}
		});
	}
}
