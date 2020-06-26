package com.lgcns.tct.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Search2 {
	
	
	/**
	 * 유효한 검색어 목록을 추출하는 기능
	 *
     * @param		inputData		List		입력데이터(검색어 목록)
     * @return						List		유효한 검색어
	 */
	public List<String> getValidWordList(List<String> inputData) {
		List<String> validWordList = null;
		validWordList = new ArrayList<String>();
		////////////////////////여기부터 구현 (1) ---------------->
		String num = "1234567890";
		
		for(String a : inputData) {	
			
			boolean chk = false;
			String k = null;
			for(int i = 0 ;i < num.length() ; i++) {
				char t = num.charAt(i);
				k = a;
				if(a.indexOf(t) != -1) {
					chk = true;
					break;
				}
			}
			if(!chk) {
				validWordList.add(k);
			}
		}
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
		Map<String, Integer> map = new HashMap<String, Integer>();
		for(String str : validWordList) {
			String key = str.toUpperCase();
			if(map.containsKey(key)) {
				int cnt = map.get(key);
				map.put(key, ++cnt);
			} else {
				map.put(key, 1);
			}
		}
		int max = 0;
		
		for(Iterator<String> iter = map.keySet().iterator(); iter.hasNext() ; ){
			String key = iter.next();
			int tmp = map.get(key);
			if(tmp > max) {
				max = tmp;
				searchWord = key; 
			}
			
		}
		///////////////////////////// <-------------- 여기까지 구현 (2)
		return searchWord;
	}	
}