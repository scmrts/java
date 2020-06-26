package com.lgcns.tct.pad;

public class Pad2 {

	/**
	 * 이동문자열을 추출하는 기능
	 *
	 * @param 	inputData   	String			입력데이터(문자열)
	 * @return 					String 			이동문자열
	 */
	public String getRotationStr(String inputData) {
		String rotationStr = "";
		//////////////////////// 여기부터 구현 (1) ---------------->
		
		char[] charData = inputData.toCharArray();
		
		for (int i = 0; i < charData.length - 1; i = i + 2) {
			if (charData[i + 1] == 'U' || charData[i + 1] == 'D' || charData[i + 1] == 'L' || charData[i + 1] == 'R') {
				if (charData[i] != '0') {
					rotationStr += charData[i];
					rotationStr += charData[i + 1];
				}
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
		int[][] numberPad = null;
		//////////////////////// 여기부터 구현 (2) ---------------->
		char[] charData = rotationStr.toCharArray();
		
		// 이전 단계 배열
		int[][] inputNumberPad2 = new int[inputNumberPad.length][inputNumberPad[0].length];
		
		for (int i = 0; i < inputNumberPad.length; i++) {
			for (int j = 0; j < inputNumberPad[i].length; j++) {
				inputNumberPad2[i][j] = inputNumberPad[i][j];
			}
		}
		
		numberPad = new int[inputNumberPad2.length][inputNumberPad2[0].length];
		for (int i = 0; i < charData.length - 1; i = i + 2) {
			
			int U = 0;
			int D = 0;
			int L = 0;
			int R = 0;
			
			if (charData[i + 1] == 'U') {
				U = charData[i] - '0';
			} else if (charData[i + 1] == 'D') {
				D = charData[i] - '0';
			} else if (charData[i + 1] == 'L') {
				L = charData[i] - '0';
			} else if (charData[i + 1] == 'R') {
				R = charData[i] - '0';
			}
			
			int maxj = inputNumberPad2.length;
			for (int j = 0; j < maxj; j++) {
				
				int maxk = inputNumberPad2[j].length;
				for (int k = 0; k < maxk; k++) {
					int Padj = (j+D-U) % maxj;
					int Padk = (k+R-L) % maxk;
					
					while(Padj <0){
						Padj += maxj;
					}
					while(Padk <0){
						Padk += maxk;
					}
					Padj %= maxj;
					Padk %= maxk;
					
					numberPad[Padj][Padk] = inputNumberPad2[j][k];					
				}
			}
			
			// 이전단계 배열 초기화
			for (int m = 0; m < numberPad.length; m++) {
				for (int m2 = 0; m2 < numberPad[m].length; m2++) {
					inputNumberPad2[m][m2] = numberPad[m][m2];
				}
			}
		}
		
		///////////////////////////// <-------------- 여기까지 구현 (2)
		return numberPad;
	}

}