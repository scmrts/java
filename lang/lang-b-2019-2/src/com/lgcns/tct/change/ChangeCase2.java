package com.lgcns.tct.change;

public class ChangeCase2 {
	
	/**
	 * 영문 대/소문자를 변환하는 기능
	 * 
	 * @param 		inputData		입력 데이터 (문자열)
	 * @return		String			대/소문자 변환 결과
	 */
	public String changeCase( String inputData ) {
		
		String result = "";
		
		////////////////////////여기부터 코딩 (1) ---------------->

		for (int i=0; i<inputData.length(); i++){
			char ch = inputData.charAt(i); //한 글자씩 확인
			if (ch >= 'A' && ch <= 'Z') { // 대문자일 때
				result += String.valueOf((char) (ch + ('a' - 'A')));

			} else if (ch >= 'a' && ch <= 'z') { // 소문자일 때

				result += String.valueOf((char) (ch - ('a' - 'A')));

			}
		}
		

		
		
		///////////////////////////// <-------------- 여기까지 코딩 (1)
		
		return result;
	}
}