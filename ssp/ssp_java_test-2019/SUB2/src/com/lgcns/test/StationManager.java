package com.lgcns.test;

import java.util.ArrayList;
import java.util.List;

import com.lgcns.test.vo.Station;

public class StationManager {
	public List<Station> stations = new ArrayList<Station>();
	public void push(String curLine) {
		String[] line = curLine.split("#");
		Station e = new Station();
		e.name = line[0];
		e.distance = Integer.parseInt(line[1]);
		if(line.length > 2) {
			e.speed = Integer.parseInt(line[2]);
		}
		this.stations.add(e);
	}
}
