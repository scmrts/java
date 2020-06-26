package com.lgcns.tct.puzzle;

import java.util.List;

import org.omg.CORBA.StringHolder;

public class Puzzle {
	
	/**
	 * 퍼즐의 구성이 정상인지 판별하는 기능
	 * 
	 * @param 		puzzle		퍼즐 구성
	 * @param 		answer		해답 (퍼즐로 만들 영어 단어)
	 * @return		boolean		정상인 퍼즐(true) 또는 불량인 퍼즐(false) 여부
	 */
	public boolean isCorrectPuzzle( List<Character> puzzle, String answer ) {
		
		boolean isCorrect = false;
		
		//////////////////////여기부터 구현 (1) ---------------->
		StringHolder sh = new StringHolder(answer);
		int sum = puzzle.stream().mapToInt(o -> {
			int index = sh.value.indexOf(o);
			if(index == -1) {
				return 1;
			} else {
				sh.value = sh.value.substring(0, index) + sh.value.substring(index + 1);
				return 0;
			}
		}).sum();
		
		///////////////////////////// <-------------- 여기까지 구현 (1)
		
		return sum <= 0;
	}
}