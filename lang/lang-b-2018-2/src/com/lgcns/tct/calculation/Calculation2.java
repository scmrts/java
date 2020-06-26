package com.lgcns.tct.calculation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Calculation2 {
	
	
	/**
	 * 세 수를 만들어서 큰 순서로 정렬하는 기능
	 *
     * @param		inputData		String			입력데이터(숫자열)
     * @return						List			정렬된 숫자열 목록
	 */
	public List<String> sortNumbers(String inputData) {
		List<String> sortedNumbers = null;
		
		////////////////////////여기부터 구현 (1) ---------------->
		
		sortedNumbers = new ArrayList<String>();

		char[] charData = inputData.toCharArray();

		int count = 0;
		int[] intNumbers = new int[3];
		for (int i = (charData.length); i >= 0; i=i-3) {
			if(i >= 3){
				intNumbers[count++] = Integer.parseInt(inputData.substring(i-3, i));
			} else if(i==2){
				intNumbers[count++] = Integer.parseInt(inputData.substring(i-2, i)+"1");
			} else if(i==1){
				intNumbers[count++] = Integer.parseInt(inputData.substring(i-1, i)+"12");
			}
		}
		
		Arrays.sort(intNumbers);		
		
		for (int i = intNumbers.length-1; i >= 0; i--) {
			sortedNumbers.add(intNumbers[i]+"");
		}

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
		
		int[] num = {0,0,0}; // [0] 1의 자리 [1] 2의 자리 [2] 3의자리
		for (int i = 0; i < sortedNumbers.size(); i++) {
			num[2] += Integer.parseInt(sortedNumbers.get(i).substring(0, 1));
			num[1] += Integer.parseInt(sortedNumbers.get(i).substring(1, 2));
			num[0] += Integer.parseInt(sortedNumbers.get(i).substring(2, 3));
		}
		
		for (int i = 0; i < num.length; i++) {
			num[i] %= 10;
			generatedNumber += num[i]*Math.pow(10, i);
		}
		
		///////////////////////////// <-------------- 여기까지 구현 (2)
		return generatedNumber;
	}
	
}