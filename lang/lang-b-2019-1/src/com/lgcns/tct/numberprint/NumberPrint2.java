package com.lgcns.tct.numberprint;

public class NumberPrint2 {
	
	/**
	 * 가로 방향으로 숫자를 출력하는 기능
	 * 
	 * @param 		width		출력할 배열의 column 수
	 * @param 		height		출력할 배열의 row 수
	 * @param 		maxNum		최대 숫자
	 * @return		int[][]		가로 방향으로 출력된 2차원 배열
	 */
	public int[][] getNumberPrintRow( int width, int height, int maxNum ) {
		
		int[][] numberArr = null;
		
		//////////////////////여기부터 구현 (1) ---------------->
		numberArr = new int[height][width];
		for ( int idx = 0 ; idx < height ; idx++ ){
			for ( int jdx = 0 ; jdx < width ; jdx++ ) {
				numberArr[idx][jdx] = 0;
			}
		}
		
		if (maxNum < 1) {
			return numberArr;
		}
		
		int point = 1;

		for ( int idx = 0 ; idx < height ; idx++ ){
			for ( int jdx = 0 ; jdx < width ; jdx++ ) {
				numberArr[idx][jdx] = point;
				point++;
				if ( point > maxNum) {
					point = 0;
					break;
				}
			}
			
			if ( point == 0 ) {
				break;
			}
		}
		///////////////////////////// <-------------- 여기까지 구현 (1)
		
		return numberArr;
	}
	
	/**
	 * 세로 방향으로 숫자를 출력하는 기능
	 * 
	 * @param 		width		출력할 배열의 column 수
	 * @param 		height		출력할 배열의 row 수
	 * @param 		maxNum		최대 숫자
	 * @return		int[][]		세로 방향으로 출력된 2차원 배열
	 */
	public int[][] getNumberPrintColumn( int width, int height, int maxNum ) {
		
		int[][] numberArr = null;
		
		//////////////////////여기부터 구현 (2) ---------------->

		numberArr = new int[height][width];
		for ( int idx = 0 ; idx < height ; idx++ ){
			for ( int jdx = 0 ; jdx < width ; jdx++ ) {
				numberArr[idx][jdx] = 0;
			}
		}
		
		if (maxNum < 1) {
			return numberArr;
		}
		
		int point = 1;
		boolean reverse = false;

		for ( int idx = 0 ; idx < width ; idx++ ){
			for ( int jdx = 0 ; jdx < height ; jdx++ ) {
				numberArr[jdx][idx] = point;
				
				if ( !reverse ) {
					point++;
				} else {
					point--;
				}
				
				if (point == maxNum) {
					reverse = true;
				}
				
				if (point == 0 ) {
					break;
				}
			}
			
			if ( point == 0 ) {
				break;
			}
		}
		
		
		///////////////////////////// <-------------- 여기까지 구현 (2)
		
		return numberArr;
	}
}