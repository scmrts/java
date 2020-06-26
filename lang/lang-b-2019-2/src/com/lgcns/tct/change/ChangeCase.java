package com.lgcns.tct.change;

import java.util.stream.Collectors;

public class ChangeCase {
	
	/**
	 * 영문 대/소문자를 변환하는 기능
	 * 
	 * @param 		inputData		입력 데이터 (문자열)
	 * @return		String			대/소문자 변환 결과
	 */
	public String changeCase( String inputData ) {
		
		String result = "";
		
		////////////////////////여기부터 코딩 (1) ---------------->

		result = inputData.chars().<String>mapToObj(o -> {
			if(o >= 'A' && o <= 'Z') {
				return Character.toString((char) (o + 32));
			} else {
				return Character.toString((char) (o - 32));
			}
		}).collect(Collectors.joining());
		
		
		///////////////////////////// <-------------- 여기까지 코딩 (1)
		
		return result;
	}
}