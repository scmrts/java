package com.lgcns.tct.ladder;

import java.util.Arrays;

public class Ladder {

	/**
	 * 주어진 사다리 정보를 이용하여 시작 번호에 대한 결과 번호를 구하는 기능
	 * 
	 * @param  		ladderData		사다리 정보
	 * @param  		startNo			시작 번호
	 * @return 		int				결과 번호
	 */
	public int getResultNo( int[][] ladderData, int startNo ) {
		
		int resultNo = 0;
		
		////////////////////////여기부터 코딩 (1) ---------------->
		resultNo = startNo;
		for (int i = 0; i < ladderData.length; i++) {
			if(ladderData[i][0] == resultNo) {
				resultNo = ladderData[i][1];
			} else if (ladderData[i][1] == resultNo) {
				resultNo = ladderData[i][0];
			} 
		}
		
		
		///////////////////////////// <-------------- 여기까지 코딩 (1)
		
		return resultNo;
	}
}