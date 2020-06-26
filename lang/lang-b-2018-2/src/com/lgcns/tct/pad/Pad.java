package com.lgcns.tct.pad;

import java.util.HashMap;
import java.util.Map;

public class Pad {

	/**
	 * 이동문자열을 추출하는 기능
	 *
	 * @param 	inputData   	String			입력데이터(문자열)
	 * @return 					String 			이동문자열
	 */
	public String getRotationStr(String inputData) {
		String rotationStr = "";
		//////////////////////// 여기부터 구현 (1) ---------------->
		
		
		for(int i = 0 ; i < inputData.length() / 2; i++) {
			String tmp = inputData.substring(i*2, i*2+2);
			


			if("UDLR".contains(tmp.substring(1)) && !"0".equals(tmp.substring(0, 1))) {
				rotationStr += tmp;
			}
		}
		
		///////////////////////////// <-------------- 여기까지 구현 (1)
		return rotationStr;
	}

	/**
	 * 숫자패드를 이동시키는 기능
	 *
	 * @param 	inputNumberPad   	int[][]			입력데이터(숫자패드)
	 * @param 	rotationStr   		String			이동문자열
	 * @return 						int[][]			이동된 숫자패드
	 */
	public int[][] getNumberPad(int[][] inputNumberPad, String rotationStr) {
		int[][] numberPad = new int[inputNumberPad.length][inputNumberPad[0].length];
		
		for(int i = 0 ;i  < inputNumberPad.length ; i++) {
			for(int j = 0 ;j  < inputNumberPad[i].length; j++) {
				numberPad[i][j] = inputNumberPad[i][j];
			}
		}
		//////////////////////// 여기부터 구현 (2) ---------------->
		for(int i = 0 ; i < rotationStr.length() / 2; i++) {
			String tmp = rotationStr.substring(i*2, i*2+2);
			int move = Integer.parseInt(tmp.substring(0, 1));
			String direction = tmp.substring(1, 2);
			for(int j = 0 ;j < move ; j++) {
				if(direction.equals("U")) {
					numberPad = this.up(numberPad);
				} else if(direction.equals("D")) {
					numberPad = this.down(numberPad);
				} else if(direction.equals("L")) {
					numberPad = this.left(numberPad);
				} else if(direction.equals("R")) {
					numberPad = this.right(numberPad);
				}
			}
			
		}
		///////////////////////////// <-------------- 여기까지 구현 (2)
		return numberPad;
	}
	
	public int[][] up(int[][] inputNumberPad) {
		int[] tmp = inputNumberPad[0];
		for(int i = 0 ;i  < inputNumberPad.length ; i++) {
			if(i == 0) continue;
			inputNumberPad[i - 1] = inputNumberPad[i];
		}
		
		inputNumberPad[inputNumberPad.length - 1] = tmp;
		return inputNumberPad;
	}
	
	public int[][] down(int[][] inputNumberPad) {
		int[] tmp = inputNumberPad[inputNumberPad.length - 1];
		for(int i = inputNumberPad.length - 2 ;i  >= 0 ; i--) {
			inputNumberPad[i + 1] = inputNumberPad[i];
		}
		inputNumberPad[0] = tmp;
		return inputNumberPad;
	}
	
	public int[][] left(int[][] inputNumberPad) {
		for(int i = 0 ;i  < inputNumberPad.length ; i++) {
			int tmp = inputNumberPad[i][0];
			for(int j = 0 ;j  < inputNumberPad[i].length; j++) {
				if(j == 0) continue;
				inputNumberPad[i][j - 1] = inputNumberPad[i][j];
			}
			inputNumberPad[i][inputNumberPad[i].length - 1] = tmp;
		}
		return inputNumberPad;
	}
	
	public int[][] right(int[][] inputNumberPad) {
		for(int i = 0 ;i  < inputNumberPad.length; i++) {
			int tmp = inputNumberPad[i][inputNumberPad.length - 1];
			for(int j = inputNumberPad[i].length - 2 ;j  >= 0 ; j--) {
				inputNumberPad[i][j + 1] = inputNumberPad[i][j];
			}
			inputNumberPad[i][0] = tmp;
		}
		return inputNumberPad;
	}

}