package com.lgcns.tct.elevator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Elevator {
	
	
	/**
	 * 사용 전력량을 계산하는 기능
	 *
     * @param		inputData			String			입력 데이터(운행정보)
     * @return							int				사용 전력량
	 */
	public int calculatePower(String inputData) {
		int power = 0;
		inputData = inputData.replace("B1", "0");
		inputData = inputData.replace("B2", "-1");
		inputData = inputData.replace("B3", "-2");
		inputData = inputData.replace("B4", "-3");
		
		
		String[] split = inputData.split(",");
		int sum = 0;
		for(int i = 0 ; i < split.length - 1 ; i++) {
			int before = Integer.parseInt(split[i]);
			int after = Integer.parseInt(split[i + 1]);
			if(before < after) { //올라갈때
				sum += (after - before) * 9;
			} else {  
				sum += (before - after) * 5;
			}
			
		}
		power = sum;
		return power;
	}
	
	/**
	 * 사용 전력량을 기준으로 내림차순 정렬하는 기능
	 *
     * @param		elevatorInfoList	List			입력데이터(각 엘리베이터의 정보)
     * @return							List			사용 전력량을 기준으로 정렬된 정보
	 */
	public List<ElevatorInfo> sortDescending(List<ElevatorInfo> elevatorInfoList) {
		List<ElevatorInfo> powerList = null;
		for (ElevatorInfo tmp : elevatorInfoList) {
			tmp.setPower(this.calculatePower(tmp.getDrivingInfo()));
		}
		elevatorInfoList.sort(new Comparator<ElevatorInfo>() {

			@Override
			public int compare(ElevatorInfo arg0, ElevatorInfo arg1) {
 
				return arg1.getPower() - arg0.getPower();
			}
		});
		return elevatorInfoList;
	}
	
}