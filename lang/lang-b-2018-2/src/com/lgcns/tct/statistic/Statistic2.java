package com.lgcns.tct.statistic;

public class Statistic2 {

	/**
	 * 출생아 수가 가장 많은 연도 검색 기능
	 *
	 * @param 	inputData   	int[]			입력데이터(출생아 수 데이터) ([0]:2010년, [1]:2011년, [2]:2012년, [3]:2013년, [4]:2014년, [5]:2015년, [6]:2016년) 
	 * @return 					int 			출생아 수가 가장 많은 연도
	 */
	public int getMaxYear(int[] inputData) {
		int maxYear = 0;
		//////////////////////// 여기부터 구현 (1) ---------------->
		
		int max = 0;
		for (int i = 0; i < inputData.length; i++) {
			if(max < inputData[i]) {
				max = inputData[i];
				maxYear = 2010 + i; 				
			}
		}
		
		///////////////////////////// <-------------- 여기까지 구현 (1)
		return maxYear;
	}

	/**
	 * 출생아 수가 가장 크게 감소한 연도 검색 기능
	 *
	 * @param 	inputData   	int[]			입력데이터(출생아 수 데이터) ([0]:2010년, [1]:2011년, [2]:2012년, [3]:2013년, [4]:2014년, [5]:2015년, [6]:2016년)
	 * @return 					int 			출생아 수가 가장 크게 감소한 연도
	 */
	public int getMaxDecreaseYear(int[] inputData) {
		int maxDecreaseYear = 0;
		//////////////////////// 여기부터 구현 (2) ---------------->
		
		int Decrease = inputData[1] - inputData[0];
		maxDecreaseYear = 2011;
		
		for (int i = 1; i < inputData.length-1; i++) {
			int temp = inputData[i+1] - inputData[i];
			if(Decrease > temp) {
				Decrease = temp;
				maxDecreaseYear = 2010 + (i+1); 				
			}
		}
		
		///////////////////////////// <-------------- 여기까지 구현 (2)
		return maxDecreaseYear;
	}

}