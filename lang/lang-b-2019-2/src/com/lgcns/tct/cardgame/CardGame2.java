package com.lgcns.tct.cardgame;

import java.util.Arrays;

public class CardGame2 {
	
	/**
	 * PLAYER3이 받은 카드를 구하는 기능
	 * 
	 * @param 		cards			초기 카드 순서 (cards[0]이 가장 아래 카드)
	 * @param 		shuffleInfo		카드를 섞는 정보 (shuffleInfo[0]부터 순서대로 섞음)
	 * @return		int[]			PLAYER3이 받은 카드 (카드번호 오름차순 정렬)
	 */
	public int[] getCardsOfPlayer3( int[] cards, String[] shuffleInfo ) {
		
		int[] cardsOfPlayer3 = new int[5];
		
		////////////////////////여기부터 코딩 (1) ---------------->

		int[] temp= Arrays.copyOf(cards, cards.length);
		for(int i=0; i<shuffleInfo.length; i++){
			int start = Integer.parseInt(shuffleInfo[i].split(",")[0]) - 1;
			int end = Integer.parseInt(shuffleInfo[i].split(",")[1]);
			int[] temp1 = Arrays.copyOfRange(temp, 0, start); //섞지 않는 앞부분
			int[] temp2 = Arrays.copyOfRange(temp, start, end); //섞이는 부분
			int[] temp3 = Arrays.copyOfRange(temp, end, cards.length);	 //섞지 않는 뒷 부분
			
			//temp1, temp3, temp2 순서대로 배열에 다시 추가
			for(int j=0; j<temp1.length; j++) temp[j] = temp1[j];
			for(int j=0; j<temp3.length; j++) temp[j+temp1.length] = temp3[j];
			for(int j=0; j<temp2.length; j++) temp[j+temp1.length+temp3.length] = temp2[j];
			
		}
		//끝에서부터 3번째씩만 추가
		for(int i=27; i>14; i=i-3){
			cardsOfPlayer3[(27-i)/3] = temp[i];
		}
		Arrays.sort(cardsOfPlayer3);
		
		
		
		///////////////////////////// <-------------- 여기까지 코딩 (1)
		
		return cardsOfPlayer3;
	}
	
	/**
	 * 각 PLAYER들의 최고점을 구하는 기능
	 * 
	 * @param 		cards			초기 카드 순서 (cards[0]이 가장 아래 카드)
	 * @param 		shuffleInfo		카드를 섞는 정보 (shuffleInfo[0]부터 순서대로 섞음)
	 * @return		int[]			각 PLAYER들의 최고 점수
	 *                                - int[0] : PLAYER1의 최고점
	 *                                - int[1] : PLAYER2의 최고점
	 *                                - int[2] : PLAYER3의 최고점
	 */
	public int[] getGameResult( int[] cards, String[] shuffleInfo ) {
		
		int[] gameResult = new int[3];
		
		////////////////////////여기부터 코딩 (2) ---------------->
		int[] temp= Arrays.copyOf(cards, cards.length);
		for(int i=0; i<shuffleInfo.length; i++){
			int start = Integer.parseInt(shuffleInfo[i].split(",")[0]) - 1;
			int end = Integer.parseInt(shuffleInfo[i].split(",")[1]);
			int[] temp1 = Arrays.copyOfRange(temp, 0, start);
			int[] temp2 = Arrays.copyOfRange(temp, start, end);
			int[] temp3 = Arrays.copyOfRange(temp, end, cards.length);	
			
			for(int j=0; j<temp1.length; j++) temp[j] = temp1[j];
			for(int j=0; j<temp3.length; j++) temp[j+temp1.length] = temp3[j];
			for(int j=0; j<temp2.length; j++) temp[j+temp1.length+temp3.length] = temp2[j];
			
		}
		int[] cardsOfPlayer1 = new int[5];
		int[] cardsOfPlayer2 = new int[5];
		int[] cardsOfPlayer3 = new int[5];
		//전체 합한 sum을 먼저 계산
		int sum1 = 0;
		int sum2 = 0;
		int sum3 = 0;
		for(int i=29; i>16; i=i-3){
			cardsOfPlayer1[(29-i)/3] = temp[i];
			sum1+= temp[i];
		}
		for(int i=28; i>15; i=i-3){
			cardsOfPlayer2[(28-i)/3] = temp[i];
			sum2+= temp[i];
		}
		for(int i=27; i>14; i=i-3){
			cardsOfPlayer3[(27-i)/3] = temp[i];
			sum3+= temp[i];
		}
		Arrays.sort(cardsOfPlayer1);
		Arrays.sort(cardsOfPlayer2);
		Arrays.sort(cardsOfPlayer3);
		int max1 = 0;
		int max2 = 0;
		int max3 = 0;

		for(int i=0; i<5; i++){
			//sum에서 제외하는 한장의 카드를 뺀다.
			int temp1 = sum1 - cardsOfPlayer1[i];
			//남은 카드들 temp_array에 저장
			int[] temp_array1 = new int[4];
			for(int j=0; j<i; j++)temp_array1[j] = cardsOfPlayer1[j];
			for(int j=i; j<4; j++) temp_array1[j] = cardsOfPlayer1[j+1];
			//temp_array에 같은 카드가 있을 경우 7씩 더한다.
			for(int j=0; j<3; j++){
				if(temp_array1[j] == temp_array1[j+1])  temp1+=7;
			}
			if(max1 < temp1) max1 = temp1;
			
			int temp2 = sum2 - cardsOfPlayer2[i];
			int[] temp_array2 = new int[4];
			for(int j=0; j<i; j++)temp_array2[j] = cardsOfPlayer2[j];
			for(int j=i; j<4; j++) temp_array2[j] = cardsOfPlayer2[j+1];
	
			for(int j=0; j<3; j++){
				if(temp_array2[j] == temp_array2[j+1])  temp2+=7;
			}
			if(max2 < temp2) max2 = temp2;
			
			int temp3 = sum3 - cardsOfPlayer3[i];
			int[] temp_array3 = new int[4];
			for(int j=0; j<i; j++)temp_array3[j] = cardsOfPlayer3[j];
			for(int j=i; j<4; j++) temp_array3[j] = cardsOfPlayer3[j+1];
	
			for(int j=0; j<3; j++){
				if(temp_array3[j] == temp_array3[j+1])  temp3+=7;
			}
			if(max3 < temp3) max3 = temp3;
		}
		
		gameResult[0] = max1;
		gameResult[1] = max2;
		gameResult[2] = max3;
		
				
		///////////////////////////// <-------------- 여기까지 코딩 (2)
		
		return gameResult;
	}
}