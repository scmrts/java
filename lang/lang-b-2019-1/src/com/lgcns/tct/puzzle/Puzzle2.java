package com.lgcns.tct.puzzle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Puzzle2 {
	
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
		ArrayList<Character> newPuzzle = new ArrayList<>(puzzle);
		
		if ( answer.length() == newPuzzle.size() ) {
			for ( int idx = 0 ; idx < answer.length() ; idx++ ) {
				
				char answerChar = answer.charAt(idx);
				
				for ( Iterator<Character> it = newPuzzle.iterator() ; it.hasNext(); ) {
					if ( Character.toLowerCase(answerChar) == Character.toLowerCase(it.next()) ) {
						it.remove();
						break;
					}
				}
			}
			
			if ( newPuzzle.size() == 0 ) {
				isCorrect= true;
			}
		}
		///////////////////////////// <-------------- 여기까지 구현 (1)
		
		return isCorrect;
	}
}