package com.lgcns.tct.numberprint;

public class NumberPrint {

	/**
	 * 가로 방향으로 숫자를 출력하는 기능
	 * 
	 * @param width  출력할 배열의 column 수
	 * @param height 출력할 배열의 row 수
	 * @param maxNum 최대 숫자
	 * @return int[][] 가로 방향으로 출력된 2차원 배열
	 */
	public int[][] getNumberPrintRow(int width, int height, int maxNum) {

		int[][] numberArr = new int[height][width];

//		//////////////////////여기부터 구현 (1) ---------------->;
		int n = 0;
		for(int i = 0 ; i < height ; i++) {
			
			for( int j = 0 ; j < width ; j++) {
				if( n < maxNum) {
					numberArr[i][j] = ++n;
					
				}
			}
		}
		///////////////////////////// <-------------- 여기까지 구현 (1)

		return numberArr;
	}

	/**
	 * 세로 방향으로 숫자를 출력하는 기능
	 * 
	 * @param width  출력할 배열의 column 수
	 * @param height 출력할 배열의 row 수
	 * @param maxNum 최대 숫자
	 * @return int[][] 세로 방향으로 출력된 2차원 배열
	 */
	public int[][] getNumberPrintColumn(int width, int height, int maxNum) {

		int[][] numberArr = new int[height][width];

		////////////////////// 여기부터 구현 (2) ---------------->
//////////////////////여기부터 구현 (1) ---------------->;
		int n = 0;
		boolean chk = false;
		for (int i = 0; i < width; i++) {

			for (int j = 0; j < height; j++) {
				if(!chk) {
					numberArr[j][i] = ++n;
					if(n == maxNum) chk = true;
				} else {
					if(n > 0)
						numberArr[j][i] = --n;
					else {}
				}

			}
		}

		///////////////////////////// <-------------- 여기까지 구현 (2)

		return numberArr;
	}
}