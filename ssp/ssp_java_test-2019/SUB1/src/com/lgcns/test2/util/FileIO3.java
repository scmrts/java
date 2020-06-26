package com.lgcns.test2.util;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.lgcns.test2.vo.Bus;
import com.lgcns.test2.vo.BusHistory;

public class FileIO3 {
	public static void main(String args[]) throws Exception {
		BusHistory h = getBuses("./INFILE/LOCATION.TXT");
		
		h.buses.sort(new Comparator<Bus>() {
			@Override
			public int compare(Bus o1, Bus o2) {
				return o1.name.compareToIgnoreCase(o2.name);
			}
		});		
		h.buses.forEach(o -> {
			String format = String.format("%s#%s#%s,%05d#%s,%05d", 
					h.time, o.name, 
					o.after != null ? o.after.name : "NOBUS", 
					o.after != null ? o.after.from - o.from : 0, 
					o.before != null ? o.before.name : "NOBUS", 
					o.before != null ? o.from - o.before.from : 0);
			System.out.println(format);
		});
	}
	
	public static BusHistory getBuses(String file) throws Exception{
		Iterator<String> iterator = Files.lines(Paths.get(file)).iterator();
		String before = null;
		BusHistory h = new BusHistory();
		while(iterator.hasNext()) {
			String next = iterator.next();
			if(next.equals("PRINT")) {
				
				String[] line = before.split("#");
				h.time = line[0];
				List<Bus> buses = Arrays.stream(line).skip(1).map(o ->  {
					Bus bus = new Bus();
					String[] businfo = o.split(",");
					bus.name = businfo[0];
					bus.from = Integer.parseInt(businfo[1]);
					return bus;
				}).collect(Collectors.toList());
				buses.sort(new Comparator<Bus>() {
					@Override
					public int compare(Bus o1, Bus o2) {
						return o1.from - o2.from;
					}
				});
				h.buses = buses;
				for(int i = 0 ; i< buses.size() ; i++) {
					if(i != buses.size() - 1)
						buses.get(i).after = buses.get(i+1);
					if(i != 0)
						buses.get(i).before = buses.get(i-1);
				}
				return h;
			} 
			before = next;
		}
		return null;
	}
}
