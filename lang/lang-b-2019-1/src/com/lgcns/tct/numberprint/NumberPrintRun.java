package com.lgcns.tct.numberprint;

public class NumberPrintRun {
	
	public static void main( String[] args ) {
		
		// 제공 데이터 세트2를 실행하려면 loadData에서 제공 데이터 1을 주석 처리하고 제공 데이터 2를 주석 해제하여 실행
		// 최초 실행 시, NullPointerException이 발생하며, 각 소문항을 구현하면 Exception이 사라짐
		int[] inputData = loadData();
		
		NumberPrint print = new NumberPrint();
		
		printInput( inputData );
		
		// 1. 가로 방향으로 숫자를 출력하는 기능
		int[][] result1 = print.getNumberPrintRow( inputData[0], inputData[1], inputData[2] );
		printRow( result1 );
		
		// 2. 세로 방향으로 숫자를 출력하는 기능
		int[][] result2 = print.getNumberPrintColumn( inputData[0], inputData[1], inputData[2] );
		printColumn( result2 );
	}
	
	private static int[] loadData() {
		
		//////////////////////////////////
		// 제공 데이터 1
		/////////////////////////////////
//		int[] inputData = new int[] { 4, 3, 5 };
		
		//////////////////////////////////
		// 제공 데이터 2
		/////////////////////////////////
		int[] inputData = new int[] { 5, 6, 19 };
		
		return inputData;
	}
	
	private static void printInput( int[] inputData ) {
		System.out.println( "[입력 데이터]" );
		System.out.println( "width = " + inputData[0] + ", height = " + inputData[1] + ", maxNum = " + inputData[2] );
		System.out.println( "--------------------------------------------------" );
	}
	
	private static void printRow( int[][] result ) {
		System.out.println( "[가로방향 출력]" );
		for ( int[] row : result ) {
			for ( int data : row ) {
				System.out.print( data + "\t" );
			}
			System.out.println();
		}
	}
	
	private static void printColumn( int[][] result ) {
		System.out.println( "[세로방향 출력]" );
		for ( int[] row : result ) {
			for ( int data : row ) {
				System.out.print( data + "\t" );
			}
			System.out.println();
		}
	}
}