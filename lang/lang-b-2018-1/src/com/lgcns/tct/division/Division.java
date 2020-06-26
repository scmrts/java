package com.lgcns.tct.division;

import java.util.ArrayList;

import javax.xml.stream.events.Characters;

public class Division {
	

	/**
	 * 연산결과를 구하는 기능
	 * 
     * @param		inputData		int			입력데이터(숫자)
     * @return						int			연산 결과
	 */
	public int getOperationResult(int inputData){
		int operationResult = 0;
		//////////////////////여기부터 구현 (1) ---------------->
		char[] input = (""+inputData).replace("0", "1").toCharArray();
		String sum = "";
		for( int i = 0 ; i < input.length - 1 ; i++) {
			int a = Character.getNumericValue(input[i]);
			int b = Character.getNumericValue(input[i+1]);
		
			if( a < b ) {
				sum += a % b;
			} else {
				sum += a / b;
			}
		}
		operationResult = Integer.parseInt(sum);
		///////////////////////////// <-------------- 여기까지 구현 (1)
		return operationResult;
	}
	
	/**
	 * 중복을 제거한 수를 만드는 기능
	 * 
     * @param		operationResult		int			연산 결과
     * @return							int			중복을 제거한 수
	 */
	public int getNumber(int operationResult){
		int number = 0;
		//////////////////////여기부터 구현 (2) ---------------->
		String input = ""+operationResult;
		String res = "";
		for(int i = 0 ; i < input.length() ; i++) {
			char tmp = input.charAt(i);
			int indexOf = res.indexOf(Character.toString(tmp));
			if(indexOf == -1) { 
				res += Character.toString(tmp);
			}
		}
		number = Integer.parseInt(res);
		///////////////////////////// <-------------- 여기까지 구현 (2)
		return number;
	}
	
}
