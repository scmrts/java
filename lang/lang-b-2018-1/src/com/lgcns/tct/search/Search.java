package com.lgcns.tct.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Search {
	
	
	/**
	 * 유효한 검색어 목록을 추출하는 기능
	 *
     * @param		inputData		List		입력데이터(검색어 목록)
     * @return						List		유효한 검색어
	 */
	public List<String> getValidWordList(List<String> inputData) {
		List<String> validWordList = new ArrayList<String>();
		////////////////////////여기부터 구현 (1) ---------------->
		for(String tmp : inputData) {
			boolean matches = tmp.matches(".*[0-9]+.*");
			if(!matches) {
				validWordList.add(tmp);
			}
		}
		validWordList.stream().forEach(System.out::println);
		///////////////////////////// <-------------- 여기까지 구현 (1)
		return validWordList;
	}
	
	
	/**
	 * 실시간 검색어 순위 1위를 계산하는 기능
	 *
     * @param		validWordList		List		유효한 검색어
     * @return							String		실시간 검색어 순위 1위
	 */
	public String getSearchWord(List<String> validWordList) {
		String searchWord = "";
		////////////////////////여기부터 구현 (2) ---------------->
		Map<String, List<String>> collect = validWordList.stream().map(o -> o.toUpperCase()).collect(Collectors.groupingBy(o -> o));
		int count = 0;

		for(Iterator<String> iter = collect.keySet().iterator() ; iter.hasNext() ; ) {
			String key = iter.next();
			List<String> value = collect.get(key);
			if(count < value.size()) {
				count = value.size();
				searchWord = key;
			}
		}
		
		
		
		///////////////////////////// <-------------- 여기까지 구현 (2)
		return searchWord;
	}	
}