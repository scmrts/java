package com.lgcns.tct.check;


public class Check2 {
	
	
	/**
	 * 체크수를 생성하는 기능
	 *
     * @param		inputData		int		입력데이터(숫자)
     * @return						int		체크수
	 */
	public int getCheckNum(int inputData) {
		int checkNum = 0;
		////////////////////////여기부터 구현 (1) ---------------->
		int num = (int)(Math.log10(inputData)+1);
		String input = Integer.toString(inputData);
		for(int i = 0 ; i < num ; i++) {
			checkNum += Character.getNumericValue(input.charAt(i)) * (num -i);
		}
		///////////////////////////// <-------------- 여기까지 구현 (1)
		if(checkNum % num == 0) {
			checkNum = checkNum / num;
		} else {
			checkNum = checkNum % num;
		}
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
		int 자릿수 = (int)(Math.log10(inputData)+1);
		int 몫 = checkNum / 10;
		int 나머지 = checkNum % 10;
		if(몫 > 0) {
			 if(몫 > 나머지) {
				 String tmp = ""+몫 + inputData + 나머지;
				 newNum = Integer.parseInt(tmp);
			 } else {
				 String tmp = ""+나머지 + inputData + 몫;
				 newNum = Integer.parseInt(tmp);
			 }
		} else { // 몫이 0
			newNum = inputData * 10 + 나머지;
		}
		 
		
		///////////////////////////// <-------------- 여기까지 구현 (2)
		return newNum;
	}
	
}