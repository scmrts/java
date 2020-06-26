package com.lgcns.tct.check;


public class Check {
	
	
	/**
	 * 체크수를 생성하는 기능
	 *
     * @param		inputData		int		입력데이터(숫자)
     * @return						int		체크수
	 */
	public int getCheckNum(int inputData) {
		int checkNum = 0;
		////////////////////////여기부터 구현 (1) ---------------->
		char[] num = (""+inputData).toCharArray();
		int length = num.length;
		int sum = 0;
		for( int i = 0 ; i <length ; i++) {
			int k = Character.getNumericValue(num[i]);
			sum += k * (length - i);
		}
		int dev = sum / length;
		int rem = sum % length;
		if ( rem == 0) {
			checkNum = dev;
		} else {
			checkNum = rem;
		}
		///////////////////////////// <-------------- 여기까지 구현 (1)
		return checkNum;
	}
	
	
	/**
	 * 새로운 수를 생성하는 기능
	 *
     * @param		inputData		int			입력데이터(숫자)
     * @param		checkNum		int			체크수
     * @return						int			새로운 수
	 */
	public int getNewNum(int inputData, int checkNum) {
		int newNum = 0;
		////////////////////////여기부터 구현 (2) ---------------->
		
		if( checkNum >= 10) {
			int dev = checkNum /10;
			int rem = checkNum % 10;
			if(dev > rem) {
				newNum = Integer.parseInt(""+dev + inputData + rem);
			} else {
				newNum = Integer.parseInt(""+rem + inputData + dev);
			}
		}
		else {
			newNum = inputData*10+ checkNum;
		}
		
		///////////////////////////// <-------------- 여기까지 구현 (2)
		return newNum;
	}
	
}