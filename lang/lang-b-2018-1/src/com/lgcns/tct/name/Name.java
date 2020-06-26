package com.lgcns.tct.name;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Name {

	/**
	 * 표기법 변환 기능
	 *
	 * @param 	inputData   	List			입력데이터(영문이름 정보)
	 * @return 					List 			표기법 변환 후 영문이름 정보
	 */
	public List<String> changeNotation(List<String> inputData) {
		List<String> changedList = new ArrayList<>();
		List<String> collect = inputData.stream().map(o -> {
			String[] split = o.split(" ");
			
			String name = split[0].substring(0, 1).toUpperCase()+split[0].substring(1).toLowerCase();
			name += " ";
			name += split[1].substring(0, 1).toUpperCase()+split[1].substring(1).toLowerCase();
			name += split[2].toLowerCase();
			
			return name;
		}).collect(Collectors.toCollection(ArrayList::new));
		return collect;
	}


	/**
	 * 동일 성씨 계산 기능
	 *
	 * @param 	changedList			List			표기법 변환 후 영문이름 정보
	 * @param 	name				String 			기준 성
	 * @return 						int 			동일 성씨 수
	 */
	public int calculateSameName(List<String> changedList, String name) {
		int numberOfSameName = 0;
		Set<String> setInput = new HashSet<String>(changedList);
		for(String tmp : setInput) {
			if(tmp.startsWith(name)) {
				numberOfSameName += 1;
			}
		}
		return numberOfSameName;
	}

}