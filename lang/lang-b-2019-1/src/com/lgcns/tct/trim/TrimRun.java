package com.lgcns.tct.trim;

public class TrimRun {
	
	public static void main( String[] args ) {
		
		// 제공 데이터 세트2를 실행하려면 loadData에서 제공 데이터 1을 주석 처리하고 제공 데이터 2를 주석 해제하여 실행
		String[] inputData = loadData();
		
		Trim trim = new Trim();
		
		printInput( inputData );
		
		// 1. 입력 받은 문자열에서 앞, 뒤의 특정 문자를 제거하는 기능
		String result = trim.getTrimString( inputData[0], inputData[1] );
		printResult( result );
	}
	
	private static String[] loadData() {
		
		//////////////////////////////////
		// 제공 데이터 1
		/////////////////////////////////
//		String[] inputData = new String[] {
//				"****1ab**cde****",
//				"*"
//		};
		
		//////////////////////////////////
		// 제공 데이터 2
		/////////////////////////////////
		String[] inputData = new String[] {
				"-fktuj#-pp---$",
				"-"
		};
//		
		return inputData;
	}
	
	private static void printInput( String[] inputData ) {
		System.out.println( "[입력 문자열] " + inputData[0] );
		System.out.println( "[제거할 문자] " + inputData[1] );
	}
	
	private static void printResult( String result ) {
		System.out.println( "[결과] " + result );
	}
}