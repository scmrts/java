package com.lgcns.tct.ladder;

public class LadderRun {
	
	public static int LADDER_COUNT = 5; 
	
	public static void main( String[] args ) {
		
		// 제공 데이터 세트2를 실행하려면 loadData에서 제공 데이터 세트1을 주석 처리하고 제공 데이터 세트2를 주석 해제하여 실행
		int[][] inputData = loadData();
		Ladder ladder = new Ladder();
		
		printInput( inputData );
		
		// 1. 주어진 사다리 정보를 이용하여 시작 번호에 대한 결과 번호를 구하는 기능
		int[] resultData = new int[LADDER_COUNT];
		
		for ( int inx = 0; inx < LADDER_COUNT; inx++ ) {
			resultData[inx] = ladder.getResultNo( inputData, (inx + 1 ) );
		}
		
		printResult( resultData );
	}
	
	private static int[][] loadData() {
		
		//////////////////////////////////
		// 제공 데이터 세트 1
		/////////////////////////////////
//		int[][] inputData = {
//				{1, 2}, {2, 4}, {2, 3}, {3, 4}, {3, 5}, {2, 3}, {1, 5}
//		};
		
		//////////////////////////////////
		// 제공 데이터 세트 2
		/////////////////////////////////
		int[][] inputData = {
				{2, 4}, {1, 3}, {3, 5}, {2, 3}, {1, 5}, {3, 4}, {2, 3}, {2, 5}, {1, 3}
		};
		
		return inputData;
	}
	
	private static void printInput( int[][] inputData ) {
		System.out.println( "[초기 입력 데이터]" );
		System.out.print( "[" );
		for ( int inx = 0; inx < inputData.length; inx++ ) {
			System.out.print( "(" + inputData[inx][0] + ", " + inputData[inx][1] + ")" );
			if ( inx < inputData.length - 1 ) {
				System.out.print( ", " );
			}
		}
		System.out.print( "]" );
		System.out.println();
	}
	
	private static void printResult( int[] resultData ) {
		System.out.println( "\n[사다리 결과]" );
		for ( int inx = 0; inx < LADDER_COUNT; inx++ ) {
			System.out.println( "입력 : " + (inx + 1) + " -> 출력 : " + resultData[inx] );
		}
	}
}