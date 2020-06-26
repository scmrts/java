package com.lgcns.tct.puzzle;

import java.util.Arrays;
import java.util.List;

public class PuzzleRun {
	
	// 제공 데이터 세트2를 실행하려면 loadData에서 제공 데이터 1을 주석 처리하고 제공 데이터 2를 주석 해제하여 실행
	static List<String> answerList;
	
	public static void main( String[] args ) {
		
		List<List<Character>> inputPuzzle = loadData();
		
		Puzzle puzzle = new Puzzle();
		
		printInput( inputPuzzle );
		printResultTitle();
		
		for ( int inx = 0; inx < inputPuzzle.size(); inx++ ) {
			// 1. 퍼즐의 구성이 정상인지 판별하는 기능
			boolean result = puzzle.isCorrectPuzzle( inputPuzzle.get(inx), answerList.get(inx) );
			printResultRow( inputPuzzle.get(inx), answerList.get(inx), result);
		}
	}
	
	private static List<List<Character>> loadData() {
		
		//////////////////////////////////
		// 제공 데이터 1
		/////////////////////////////////
//		List<List<Character>> inputPuzzle = Arrays.asList(
//				Arrays.asList( 'c', 'g', 'f', 'f', 'e', 'c' ),
//				Arrays.asList( 'k', 'i', 'l', 'e' ),
//				Arrays.asList( 't', 'a', 'c' ),
//				Arrays.asList( 'e', 'e', 't', 'r' ),
//				Arrays.asList( 'a', 't', 'e', 't' ),
//				Arrays.asList( 'g', 'o', 'o', 'd' )
//		);
//		answerList = Arrays.asList(
//				"coffee",
//				"like",
//				"cat",
//				"tree",
//				"tea",
//				"dog"
//		);
		
		//////////////////////////////////
		// 제공 데이터 2
		/////////////////////////////////
		List<List<Character>> inputPuzzle = Arrays.asList(
				Arrays.asList( 'n', 'e', 'p' ),
				Arrays.asList( 't', 's', 'e', 'a' ),
				Arrays.asList( 'c', 'h', 't', 'e', 'a' ),
				Arrays.asList( 'e', 'e', 'b', 'm', 'a', 'y' )
		);
		answerList = Arrays.asList(
				"pen",
				"sea",
				"beach",
				"maybee"
		);
		
		return inputPuzzle;
	}
	
	private static void printInput( List<List<Character>> inputPuzzle ) {
		System.out.println( "[초기 입력 데이터]" );
		System.out.println( "퍼즐\t해답" );
		for ( int inx = 0; inx < inputPuzzle.size(); inx++ ) {
			for ( Character character : inputPuzzle.get(inx) ) {
				System.out.print( character );
			}
			System.out.print( "\t" );
			System.out.println( answerList.get(inx) );
		}
		System.out.println( "----------------------------------------" );
	}
	
	private static void printResultTitle() {
		System.out.println( "[퍼즐 판별]" );
		System.out.println( "퍼즐\t해답\t정상여부" );
	}
	
	private static void printResultRow( List<Character> puzzle, String answer, boolean isCorrect ) {
		for ( Character character : puzzle ) {
			System.out.print( character );
		}
		System.out.print( "\t" );
		System.out.print( answer );
		System.out.print( "\t" );
		System.out.println( isCorrect );
	}
}