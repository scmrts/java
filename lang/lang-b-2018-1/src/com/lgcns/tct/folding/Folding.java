package com.lgcns.tct.folding;

import java.util.Arrays;

public class Folding {
	
	
	/**
	 * 초기배열을 생성하는 기능
	 *
     * @param		inputData		int[][]		입력데이터(이차원배열)
     * @return						int[][]		생성된 초기 배열
	 */
	public int[][] getIniArr(int[][] inputData) {
		int[][] iniArr = new int[inputData.length][inputData[0].length];
		////////////////////////여기부터 구현 (1) ---------------->	
		for(int i = 0 ; i < inputData.length ; i++) {
			for(int j = 0 ; j < inputData[i].length ; j++) {
				if(inputData[i][j] > 5) {
					iniArr[i][j] = inputData[i][j] % 5;
				} else {
					iniArr[i][j] = inputData[i][j];
				}
			}
		}
		///////////////////////////// <-------------- 여기까지 구현 (1)
		return iniArr;
	}
	
	/**
	 * 배열을 상하 좌우로 접는 기능
	 *
     * @param		iniArr		int[][]		생성된 초기 배열
     * @return					int[][]		상하 좌우로 접힌 배열	
	 */
	public int[][] getFoldingArr(int[][] iniArr) {
		int[][] foldingArr = iniArr;
		////////////////////////여기부터 구현 (2) ---------------->		
		boolean vertical = true;
		
		foldingArr = this.foldVertical(foldingArr);		
				
		
		foldingArr = this.foldHorizontal(foldingArr);		
		
		///////////////////////////// <-------------- 여기까지 구현 (2)
		return foldingArr;
	}
	
	public int[][] foldVertical(int[][] iniArr) {
		int[][] foldingArr = new int[iniArr.length / 2][iniArr[0].length];
		for(int i = 0 ; i < iniArr.length / 2 ; i++) {
			for(int j = 0 ; j < iniArr[i].length ; j++) {
				foldingArr[i][j]=iniArr[i][j] + iniArr[iniArr.length - i - 1][j];
			}
		}
		
		
		return foldingArr;
	}
	public int[][] foldHorizontal(int[][] iniArr) {
		int[][] foldingArr = new int[iniArr.length][iniArr[0].length / 2];
		for(int i = 0 ; i < iniArr.length ; i++) {
			for(int j = 0 ; j < iniArr[i].length / 2 ; j++) {
				foldingArr[i][j]=iniArr[i][j] * iniArr[i][iniArr[i].length - j - 1];
			}
		}
		return foldingArr;
	}
	
	/**
	 * 최종배열의 값을 구하는 기능
	 *
     * @param		foldingArr		int[][]		상하 좌우로 접힌 배열	
     * @return						int			최종배열의 값
	 */
	public int getFinalValue(int[][] foldingArr) {
		int finalValue = 0;
		int[][] onetwo = this.getFinalValue2(foldingArr);
		finalValue = Arrays.stream(onetwo).flatMapToInt(o-> Arrays.stream(o)).sum();
		return finalValue;
	}
	
	public int[][] getFinalValue2(int[][] foldingArr) {
		int len = foldingArr.length;
		if(len % 2 == 0) {
			foldingArr = this.even(foldingArr);
		} else {
			foldingArr = this.odd(foldingArr);
		}
		if(foldingArr.length + foldingArr[0].length == 2) {
			return foldingArr;
		} else {
			return this.getFinalValue2(foldingArr);
		}
	}
	
	
	public int[][] odd(int[][] foldingArr) {
		int[][] ret = new int[foldingArr.length - 1][foldingArr.length - 1];
		int skip = foldingArr.length / 2;
		int k = 0;
		for(int i = 0 ; i < foldingArr.length ; i++) {
			
			if(i == skip) continue;
			int l = 0;
			for(int j = 0 ; j < foldingArr.length ; j++) {
				
				if(j == skip) continue;
				ret[k][l++] = foldingArr[i][j];
				
			}
			k++;
		}
		
		return ret;
	}
	
	public int[][] even(int[][] foldingArr) {
		
		return this.getFoldingArr(foldingArr);
	}
}