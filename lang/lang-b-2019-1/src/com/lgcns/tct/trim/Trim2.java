package com.lgcns.tct.trim;

public class Trim2 {

	/**
	 * 입력 받은 문자열에서 앞, 뒤의 특정 문자를 제거하는 기능
	 * 
	 * @param 		message		입력 문자열
	 * @param 		trimStr		Trim할 문자
	 * @return		String		결과 문자열
	 */
	public String getTrimString( String message, String trimStr ) {
		
		String result = "";
		
		//////////////////////여기부터 구현 (1) ---------------->
		result = message;
		if ( result.length() < 1) {
			return result;
		}
			
		for (int idx = 0 ; idx < result.length() ; idx++) {
			if ( trimStr.equals(result.substring(0,1)) ) {
				result = result.substring(1, result.length());
			} else {
				break;
			}
		}
		
		for (int idx = result.length() ; idx > 0 ; idx--) {
			if ( trimStr.equals(result.substring(result.length()-1,result.length())) ) {
				result = result.substring(0, result.length()-1);
			} else {
				break;
			}
		}
		///////////////////////////// <-------------- 여기까지 구현 (1)
		
		return result;
	}
}