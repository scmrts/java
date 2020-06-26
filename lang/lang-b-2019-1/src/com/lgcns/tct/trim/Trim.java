package com.lgcns.tct.trim;

public class Trim {

	/**
	 * 입력 받은 문자열에서 앞, 뒤의 특정 문자를 제거하는 기능
	 * 
	 * @param 		message		입력 문자열
	 * @param 		trimStr		Trim할 문자
	 * @return		String		결과 문자열
	 */
	public String getTrimString( String message, String trimStr ) {
		
		String result = message;
		System.out.println(result);
		//////////////////////여기부터 구현 (1) ---------------->
		for(int i = 0 ; i < message.length() ; i++) {
			if(result.charAt(0) == trimStr.charAt(0)) {
				result = result.substring(1);
				System.out.println(result);
			}
		}
		for(int i = message.length() - 1 ; i >= 0  ; i--) {
			if(result.charAt(result.length() - 1) == trimStr.charAt(0)) {
				result = result.substring(0, result.length() - 1);
				System.out.println(result);
			}
		}
		///////////////////////////// <-------------- 여기까지 구현 (1)
		
		return result;
	}
}