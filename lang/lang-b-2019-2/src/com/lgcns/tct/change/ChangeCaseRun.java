package com.lgcns.tct.change;

public class ChangeCaseRun {
	
	public static void main( String[] args ) {
		
		// 제공 데이터 세트2를 실행하려면 loadData에서 제공 데이터 세트1을 주석 처리하고 제공 데이터 세트2를 주석 해제하여 실행
		String inputData = loadData();
		ChangeCase change = new ChangeCase();
		
		printInput( inputData );
		
		// 1. 영문 대/소문자를 변환하는 기능
		String resultData = change.changeCase( inputData );
		printResult( resultData );
	}
	
	private static String loadData() {
		
		//////////////////////////////////
		// 제공 데이터 세트 1
		/////////////////////////////////
		String inputData = "TctTest";

		//////////////////////////////////
		// 제공 데이터 세트 2
		/////////////////////////////////
//		String inputData = "lgcns";
		
		return inputData;
	}
	
	private static void printInput( String inputData ) {
		System.out.println( "[입력 문자열] " + inputData );
	}
	
	private static void printResult( String resultData ) {
		System.out.println( "[영문 대/소문자 변경 결과] " + resultData );
	}
}