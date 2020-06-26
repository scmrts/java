package com.lgcns.tct.childcare;

import java.util.List;

public class ChildCareRun {
	
	public static void main( String[] args ) {
		
		// 제공 데이터 세트2를 실행하려면 loadData에서 제공 데이터 세트1을 주석 처리하고 제공 데이터 세트2를 주석 해제하여 실행
		String inputData = loadData();
		ChildCare care = new ChildCare();
		
		printInput( inputData );
		
		// 1. 선발 제외 대상 데이터를 삭제하는 기능
		List<String> removedList = care.getRemovedList( inputData );
		printRemovedList( removedList );
		
		// 2. 선발 대상 선정하기
		List<String> selectedList = care.getSelectedList( inputData );
		printSelectedList( selectedList );
	}
	
	private static String loadData() {
		
		//////////////////////////////////
		// 제공 데이터 세트 1
		/////////////////////////////////
//		String inputData = "77449,2019,C#77448,2017,D#77447,2014,B#77446,2016,A#77445,2016,A#"
//				+ "77444,2016,B#77443,2014,C#77442,2014,A#77441,2015,D#77440,2015,C#77431,2014,A";
		
		//////////////////////////////////
		// 제공 데이터 세트 2
		/////////////////////////////////
		String inputData = "67448,2017,D#67146,2016,A#67196,2016,B#67111,2016,D#67425,2013,C#"
				+ "67358,2017,A#67349,2017,C#67154,2016,C#67444,2013,B#67843,2013,C#67354,2017,D#"
				+ "67427,2013,B#67442,2014,A#67402,2014,A#67241,2015,D#67410,2015,C#67312,2019,A#"
				+ "67323,2017,A#67325,2017,B";
		
		return inputData;
	}
	
	private static void printInput( String inputData ) {
		System.out.println( "[초기 입력 데이터]" );
		System.out.println( inputData );
		System.out.println( "--------------------------------------------------" );
	}
	
	private static void printRemovedList( List<String> removedList ) {
		System.out.println( "[선발대상 제외하기]" );
		for ( String employeeNo : removedList ) {
			System.out.println( employeeNo );
		}
		System.out.println( "--------------------------------------------------" );
	}
	
	private static void printSelectedList( List<String> selectedList ) {
		System.out.println( "[원아 선발 결과]" );
		for ( String employeeNo : selectedList ) {
			System.out.println( employeeNo );
		}
	}
}