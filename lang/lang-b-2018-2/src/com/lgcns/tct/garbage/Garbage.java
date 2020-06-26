package com.lgcns.tct.garbage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

public class Garbage {

	/**
	 * 가장 많이 수거된 재활용품의 종류 검색 기능
	 *
	 * @param 	inputData   	String			입력데이터(재활용품과 일반쓰레기 정보를 담고 있는 정보)
	 * @return 					List			가장 많이 수거된 재활용품 (P:플라스틱, B:비닐, S:스티로폼, W:종이)
	 */
	public List<Character> getNameList(String inputData) {
		List<Character> nameList = new ArrayList<Character>();
		//////////////////////// 여기부터 구현 (1) ---------------->
		
		Map<String, Integer> table = new HashMap<String, Integer>();
		
		for(int i = 0 ; i < inputData.length() / 2; i++) {
			String tmp = inputData.substring(i*2, i*2+2);
			String key = tmp.substring(1, 2);
			table.put(key, table.getOrDefault(key, 0) + Integer.parseInt(tmp.substring(0, 1)));
		}
		
		Entry<String, Integer> entry = table.entrySet().stream().max((a, b) -> a.getValue() - b.getValue()).get();
		int max = entry.getValue();
		List<Character> collect = table.entrySet().stream().filter(o -> o.getValue() == max).map(o -> o.getKey().toCharArray()[0]).collect(Collectors.toList());
		///////////////////////////// <-------------- 여기까지 구현 (1)
		return collect;
	}

	/**
	 * 재활용품의 백분율 계산 기능
	 *
	 * @param 	inputData   	String			입력데이터(재활용품과 일반쓰레기 정보를 담고 있는 정보)
	 * @return 					int				재활용품의 백분율
	 */
	public int getRatio(String inputData) {
		int ratio = 0;
		//////////////////////// 여기부터 구현 (2) ---------------->		
		Map<String, Integer> table = new HashMap<String, Integer>();
		for(int i = 0 ; i < inputData.length() / 2; i++) {
			String tmp = inputData.substring(i*2, i*2+2);
			String key = tmp.substring(1, 2);
			table.put(key, table.getOrDefault(key, 0) + Integer.parseInt(tmp.substring(0, 1)));
		}
		
		int g = table.get("G");
		table.remove("G");
		int sum = 0;
		for(Iterator<String> iter = table.keySet().iterator() ; iter.hasNext() ; ) {
			sum += table.get(iter.next());
		}
		
		///////////////////////////// <-------------- 여기까지 구현 (2)
		return (int) (sum *  100.0f / (sum + g));
	}

}