package com.lgcns.tct.name;

import java.util.ArrayList;
import java.util.List;

public class Name2 {

	/**
	 * 표기법 변환 기능
	 *
	 * @param 	inputData   	List			입력데이터(영문이름 정보)
	 * @return 					List 			표기법 변환 후 영문이름 정보
	 */
	public List<String> changeNotation(List<String> inputData) {
		List<String> changedList = new ArrayList<>();
		for(String name : inputData){
			String [] splitName=name.split(" ");
			
			String secondName = splitName[1].substring(0, 1).toUpperCase() + splitName[1].substring(1);
			String thirdName = splitName[2].substring(0, 1).toLowerCase() + splitName[2].substring(1);
					
			name=splitName[0]+" "+secondName+thirdName;
			changedList.add(name);
		}
		return changedList;
	}


	/**
	 * 동일 성씨 계산 기능
	 *
	 * @param 	changedList			List			표기법 변환 후 영문이름 정보
	 * @param 	name				String 			기준 성
	 * @return 						int 			동일 성씨 수
	 */
	public int calculateSameName(List<String> changedList, String name) {
		
		List<String> removedList = new ArrayList<>();
		for(String tmpName : changedList){
			if(!removedList.contains(tmpName)){
					removedList.add(tmpName);
			}
		}
		
		int numberOfSameName = 0;
		for(String temp : removedList){			
			if(temp.split(" ")[0].equals(name)){
				numberOfSameName++;
			}
		}
		return numberOfSameName;
	}

}