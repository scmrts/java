package com.lgcns.tct.cardgame;

import java.util.Arrays;
import java.util.List;

public class CardGameRun {
	
	public static void main( String[] args ) {
		
		// 제공 데이터 세트2를 실행하려면 loadData에서 제공 데이터 세트1을 주석 처리하고 제공 데이터 세트2를 주석 해제하여 실행
		List<Object> inputData = loadData();
		int[] cards = (int[])inputData.get(0);
		String[] shuffleInfo = (String[])inputData.get(1);
		CardGame cardGame = new CardGame();
		
		printInput( cards, shuffleInfo );
		
		// 1. PLAYER3이 받은 카드를 구하는 기능
		int[] cardsOfPlayer3 = cardGame.getCardsOfPlayer3( cards, shuffleInfo );
		printCardsOfPlayer3( cardsOfPlayer3 );
		
		// 2. 각 PLAYER들의 최고점을 구하는 기능
		int[] gameResult = cardGame.getGameResult( cards, shuffleInfo );
		printGameResult( gameResult );
	}
	
	private static List<Object> loadData() {
		
		//////////////////////////////////
		// 제공 데이터 세트 1
		/////////////////////////////////
//		List<Object> inputData = Arrays.asList(
//				new int[] {
//						1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
//						1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
//						1, 2, 3, 4, 5, 6, 7, 8, 9, 10
//				},
//				new String[] {
//						"8,10",
//						"5,7"
//				}
//		);
		
		//////////////////////////////////
		// 제공 데이터 세트 2
		/////////////////////////////////
		List<Object> inputData = Arrays.asList(
				new int[] {
						9, 10, 1, 2, 3, 4, 5, 6, 7, 8,
						9, 10, 1, 2, 3, 4, 5, 6, 7, 8,
						9, 10, 1, 2, 3, 4, 5, 6, 7, 8
				},
				new String[] {
						"1,6",
						"2,10",
						"1,15"
				}
		);
		
		return inputData;
	}
	
	private static void printInput( int[] cards, String[] shuffleInfo ) {
		System.out.println( "[입력]" );
		System.out.print( "카드 : " );
		for ( int inx = 0; inx < cards.length; inx++ ) {
			System.out.print( cards[inx] );
			if ( inx < cards.length - 1 ) {
				System.out.print( "," );
			}
		}
		System.out.println();
		System.out.println( "섞는 순서 : " );
		for ( int inx = 0; inx < shuffleInfo.length; inx++ ) {
			String[] shuffleSplit = shuffleInfo[inx].split(",");
			System.out.println( "  " + (inx+1) + ") " + shuffleSplit[0] + "번째~" + shuffleSplit[1] + "번째를 위로 이동" );
		}
		System.out.println( "--------------------------------------------------" );
	}
	
	private static void printCardsOfPlayer3( int[] cardsOfPlayer3 ) {
		System.out.println( "[PLAYER3 이 가지는 카드]" );
		for ( int inx = 0; inx < cardsOfPlayer3.length; inx++ ) {
			System.out.print( cardsOfPlayer3[inx] );
			if ( inx < cardsOfPlayer3.length - 1 ) {
				System.out.print( ", " );
			}
		}
		System.out.println();
		System.out.println( "--------------------------------------------------" );
	}
	
	private static void printGameResult( int[] gameResult ) {
		System.out.println( "[각 PLAYER 들의 최고 점수]" );
		for ( int inx = 0; inx < gameResult.length; inx++ ) {
			System.out.println( "PLAYER" + (inx + 1) + " : " + gameResult[inx] + "점" );
		}
		System.out.println( "--------------------------------------------------" );
	}
}