package com.lgcns.tct.calculation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Calculation {
	
	
	/**
	 * 세 수를 만들어서 큰 순서로 정렬하는 기능
	 *
     * @param		inputData		String			입력데이터(숫자열)
     * @return						List			정렬된 숫자열 목록
	 */
	public List<String> sortNumbers(String inputData) {
		List<String> sortedNumbers = new ArrayList<String>();
		
		////////////////////////여기부터 구현 (1) ---------------->
		int remains = inputData.length() % 3;
		
		if(remains == 1) {
			sortedNumbers.add(inputData.substring(0, remains) + "12");
			
		} else if( remains == 2) {
			sortedNumbers.add(inputData.substring(0, remains) + "1");
		}
		int v = inputData.length() / 3;
		 
		for(int i = 0 ; i < v ; i++) {
			int start = remains + i * 3;
			sortedNumbers.add(inputData.substring(start, start + 3));
		}
		Collections.sort(sortedNumbers, new Comparator<String>() {

			@Override
			public int compare(String arg0, String arg1) {
				 
				return -arg0.compareTo(arg1);
			}
		});
//		Collections.reverse(sortedNumbers);
		///////////////////////////// <-------------- 여기까지 구현 (1)
		return sortedNumbers;
	}
	
	/**
	 * 세 수를 이용하여 새로운 수를 생성하는 기능
	 *
     * @param		sortedNumbers	List			정렬된 숫자열 목록
     * @return						int				생성된 숫자
	 */
	public int generateNumber(List<String> sortedNumbers) {
		int generatedNumber = 0;
		////////////////////////여기부터 구현 (2) ---------------->
		String k = "";
		for(int i = 0 ; i < 3 ; i++) {
			int sum = 0;
			for(String tmp : sortedNumbers) {
				char[] ch = tmp.toCharArray();
				sum += Character.getNumericValue(ch[i]);
			}
			k += sum % 10;
		}
		generatedNumber = Integer.parseInt(k);
		///////////////////////////// <-------------- 여기까지 구현 (2)
		return generatedNumber;
	}
	
}