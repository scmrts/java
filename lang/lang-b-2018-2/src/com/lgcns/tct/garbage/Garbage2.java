package com.lgcns.tct.garbage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Garbage2 {

	/**
	 * 가장 많이 수거된 재활용품의 종류 검색 기능
	 *
	 * @param 	inputData   	String			입력데이터(재활용품과 일반쓰레기 정보를 담고 있는 정보)
	 * @return 					List			가장 많이 수거된 재활용품 (P:플라스틱, B:비닐, S:스티로폼, W:종이)
	 */
	public List<Character> getNameList(String inputData) {
		List<Character> nameList = null;
		//////////////////////// 여기부터 구현 (1) ---------------->
		
		nameList = new ArrayList<Character>();
		
		char[] charData = inputData.toCharArray();
		HashMap<Character, Integer> NameMap = new HashMap<Character, Integer>();
		
		NameMap.put('P', 0);
		NameMap.put('G', 0);
		NameMap.put('B', 0);
		NameMap.put('S', 0);
		NameMap.put('W', 0);
		
		for (int i = 0; i < charData.length-1; i=i+2) {
			int tempCount = (int) NameMap.get((Character)charData[i+1]) + Integer.parseInt(charData[i]+"");
			NameMap.put(charData[i+1], tempCount);
		}
		
		int maxCount = 0;
		for (Character c : NameMap.keySet()) {
			if(!c.equals('G') && maxCount < NameMap.get(c)) {
				maxCount = NameMap.get(c);
			}
		}
		
		for (Character c : NameMap.keySet()) {
			if(!c.equals('G') && maxCount == NameMap.get(c)) {
				nameList.add(c);
			}
		}
		
		///////////////////////////// <-------------- 여기까지 구현 (1)
		return nameList;
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
		char[] charData = inputData.toCharArray();
		HashMap<Character, Integer> NameMap = new HashMap<Character, Integer>();
		
		NameMap.put('P', 0);
		NameMap.put('G', 0);
		NameMap.put('B', 0);
		NameMap.put('S', 0);
		NameMap.put('W', 0);
		
		for (int i = 0; i < charData.length-1; i=i+2) {
			int tempCount = (int) NameMap.get((Character)charData[i+1]) + Integer.parseInt(charData[i]+"");
			NameMap.put(charData[i+1], tempCount);
		}
		
		int G = 0;
		int PBSW = 0;
		for (Character c : NameMap.keySet()) {
			if(c.equals('G')) {
				G = NameMap.get(c);
			} else {
				PBSW += NameMap.get(c);
			}
		}
		
		ratio = (100 * PBSW) / (G + PBSW);
		
		///////////////////////////// <-------------- 여기까지 구현 (2)
		return ratio;
	}

}