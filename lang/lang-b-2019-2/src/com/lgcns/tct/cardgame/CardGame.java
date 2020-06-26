package com.lgcns.tct.cardgame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.omg.CORBA.IntHolder;

public class CardGame {
	
	/**
	 * PLAYER3이 받은 카드를 구하는 기능
	 * 
	 * @param 		cards			초기 카드 순서 (cards[0]이 가장 아래 카드)
	 * @param 		shuffleInfo		카드를 섞는 정보 (shuffleInfo[0]부터 순서대로 섞음)
	 * @return		int[]			PLAYER3이 받은 카드 (카드번호 오름차순 정렬)
	 */
	public int[] getCardsOfPlayer3( int[] cards, String[] shuffleInfo ) {
		
		int[] cardsOfPlayer3 = new int[5];
		
		////////////////////////여기부터 코딩 (1) ---------------->
		List<Integer> cardsList = Arrays.stream(cards).boxed().collect(Collectors.toList());
		for (String shuffle : shuffleInfo) {
			String[] split = shuffle.split(",");
			int end = Integer.parseInt(split[1]) - Integer.parseInt(split[0]) + 1;
			for(int i = 0 ; i < end ; i++) {
				Integer remove = cardsList.remove(Integer.parseInt(split[0]) - 1);
				System.out.println();
				cardsList.add(remove);
			}
		}
		int p = 0;
		
		for(int i = 0 ; i < cardsList.size(); i++) {
			int j = cardsList.size() - i - 1;
			int k = j % 3;
			if(k == 0) {
				cardsOfPlayer3[p++] = cardsList.get(j);
				if(p == 5) {
					break;
				}
			}
		}
		///////////////////////////// <-------------- 여기까지 코딩 (1)
		
		return cardsOfPlayer3;
	}
	
	/**
	 * 각 PLAYER들의 최고점을 구하는 기능
	 * 
	 * @param 		cards			초기 카드 순서 (cards[0]이 가장 아래 카드)
	 * @param 		shuffleInfo		카드를 섞는 정보 (shuffleInfo[0]부터 순서대로 섞음)
	 * @return		int[]			각 PLAYER들의 최고 점수
	 *                                - int[0] : PLAYER1의 최고점
	 *                                - int[1] : PLAYER2의 최고점
	 *                                - int[2] : PLAYER3의 최고점
	 */
	public int[] getGameResult( int[] cards, String[] shuffleInfo ) {
		
		int[] gameResult = new int[3];
		
		////////////////////////여기부터 코딩 (2) ---------------->
		List<Integer> cardsList = Arrays.stream(cards).boxed().collect(Collectors.toList());
		for (String shuffle : shuffleInfo) {
			String[] split = shuffle.split(",");
			int end = Integer.parseInt(split[1]) - Integer.parseInt(split[0]) + 1;
			for(int i = 0 ; i < end ; i++) {
				Integer remove = cardsList.remove(Integer.parseInt(split[0]) - 1);
				System.out.println();
				cardsList.add(remove);
			}
		}
		int p = 0;
		Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
		for(int i = 0 ; i < cardsList.size(); i++) {
			int j = cardsList.size() - i - 1;
			
			int k = j % 3;
			int index = 0;
			if(k == 0) {
				index = 2;
			} else if(k ==1) {
				index = 1;
			} else {
				index = 0;
			}
			List<Integer> orDefault = map.getOrDefault(index, new ArrayList<Integer>());
			if(orDefault.size() > 4) {
				continue;
			}
			orDefault.add(cardsList.get(j));
			map.put(index, orDefault);
		}
		
		for(int k = 0 ; k < map.size() ; k++) {
			List<Integer> p1 = map.get(k);
			int max = Integer.MIN_VALUE; 
			for(int i = 0 ; i< 5 ; i++) {
				Integer remove = p1.remove(i);
				
				IntHolder holder = new IntHolder(0);
				p1.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet()
						.stream().filter(o -> o.getValue()> 1).forEach(o -> {
							holder.value += (o.getValue().intValue() - 1) * 7;
						});;
				int sum = p1.stream().mapToInt(Integer::intValue).sum() + holder.value; 
				p1.add(i, remove);
				if(max < sum) {
					max = sum;
				}
	
			}
			gameResult[k] = max; 
		}
		///////////////////////////// <-------------- 여기까지 코딩 (2)
		
		return gameResult;
	}
}