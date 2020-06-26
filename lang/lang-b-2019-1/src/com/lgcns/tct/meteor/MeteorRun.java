package com.lgcns.tct.meteor;

public class MeteorRun {
	
	public static void main( String[] args ) {
		
		// 제공 데이터 세트2를 실행하려면 loadData에서 제공 데이터 1을 주석 처리하고 제공 데이터 2를 주석 해제하여 실행
		String inputData = loadData();
		
		Meteor meteor = new Meteor();
		
		printInput( inputData );
		
		// 1. 운석 낙하 위치의 개수를 계산하는 기능
		int count = meteor.getMeteorFallCount( inputData );
		printMeteorFallCount( count );
		
		// 2. 위험도 2 이상의 지역의 개수를 계산하는 기능
		int count2 = meteor.getMeteorDangerAreaCount( inputData );
		printMeteorDangerAreaCount( count2 );
	}
	
	private static String loadData() {
		
		//////////////////////////////////
		// 제공 데이터 1
		/////////////////////////////////
//		String inputData = "4*(0,0)#3*(3,3)#2*(10,2)#4*(11,6)#2*(11,6)#3*(15,7)";
//		String inputData = "4*(0,0)#3*(3,3)#2*(10,2)#4*(11,6)#2*(11,6)#3*(15,7)";
		
		//////////////////////////////////
		// 제공 데이터 2
		/////////////////////////////////
		String inputData = "3*(0,3)#2*(2,4)#4*(3,8)#1*(8,11)#3*(8,16)#1*(8,11)#1*(9,11)";
		
		return inputData;
	}
	
	private static void printInput( String inputData ) {
		System.out.println( "운석 낙하위치 입력데이터 : " + inputData );
	}
	
	private static void printMeteorFallCount( int count ) {
		System.out.println( "--------------- 결과 ---------------" );
		System.out.println( "운석 낙하 개수 : " + count );
	}
	
	private static void printMeteorDangerAreaCount( int count ) {
		System.out.println( "운석 낙하 위험도 2 이상 지역의 개수 : " + count );
		System.out.println( "------------------------------------" );
	}
}