package com.lgcns.test2.util;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.lgcns.test2.vo.Bus;
import com.lgcns.test2.vo.BusHistory;

public class FileIO {
	public static void main(String args[]) throws Exception {
		List<BusHistory> busHistory = getBuses("./INFILE/LOCATION.TXT");
		BusHistory h = busHistory.get(busHistory.size() - 1);
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
	
	public static List<BusHistory> getBuses(String file) throws Exception{
		Iterator<String> iterator = Files.lines(Paths.get(file)).iterator();
		List<BusHistory> busHistory = new LinkedList<BusHistory>();
		String before = null;
		while(iterator.hasNext()) {
			String curLine = iterator.next();
			if(curLine.equals("PRINT")) {
				break;
			} else {
				BusHistory h = new BusHistory();
				String[] line = curLine.split("#");
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
				busHistory.add(h);
			}
		}
		
		return busHistory;
	}
}
