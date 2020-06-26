package com.lgcns.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

import org.omg.CORBA.StringHolder;

import com.lgcns.test.vo.Bus;

public class RunManager {

	public static void main(String[] args) throws IOException {
		BusHistory history = new BusHistory();
		StringHolder before = new StringHolder();;

		
		Files.lines(Paths.get("./INFILE/LOCATION.TXT")).forEach(o -> {
			if(o.equals("PRINT")) {
				List<Bus> busListAtTime = history.getBusListAtTime(before.value.split("#")[0]);
				busListAtTime.stream().sorted(new Comparator<Bus>() {

					@Override
					public int compare(Bus o1, Bus o2) {
						return o1.name.compareTo(o2.name);
					}
				}).forEach(Bus::printSequence);
			} else {
				history.push(o);
				before.value = o;
			}
		});
		
	}

	
}




