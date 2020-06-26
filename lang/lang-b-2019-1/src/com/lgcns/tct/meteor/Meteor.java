package com.lgcns.tct.meteor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Meteor {
	
	/**
	 * 운석 낙하 위치의 개수를 계산하는 기능
	 * 
	 * @param 		inputData		운석 낙하 위치 입력 데이터
	 * @return		int				낙하한 운석의 개수
	 */
	public int getMeteorFallCount( String inputData ) {
		
		int count = 0;
		
		//////////////////////여기부터 구현 (1) ---------------->
		String[] split = inputData.split("#");
		
		int[][] map = new int[15][15];
		for(int i = 0 ; i < split.length ; i++) {
			String[] unit = split[i].split("\\*");
			Pattern p = Pattern.compile("\\(([0-9]+)\\,([0-9]+)\\)");
			Matcher matcher = p.matcher(unit[1]);
			matcher.find();
			int x = Integer.parseInt(matcher.group(1));
			int y = Integer.parseInt(matcher.group(2));
			if(x < 15 && y < 15) {
				count++;
			}
			
		}
		///////////////////////////// <-------------- 여기까지 구현 (1)
		return count;
	}
	
	/**
	 * 위험도 2 이상의 지역의 개수를 계산하는 기능
	 * 
	 * @param 		inputData		운석 낙하 위치 입력 데이터
	 * @return		int				운석 낙하 위험도 2 이상 지역의 개수
	 */
	public int getMeteorDangerAreaCount( String inputData ) {
		
		int count = 0;
		
		//////////////////////여기부터 구현 (2) ---------------->
		
		String[] split = inputData.split("#");
		
		int[][] map = new int[15][15];
		for(int i = 0 ; i < split.length ; i++) {
			String[] unit = split[i].split("\\*");
			Pattern p = Pattern.compile("\\(([0-9]+)\\,([0-9]+)\\)");
			Matcher matcher = p.matcher(unit[1]);
			matcher.find();
			int x = Integer.parseInt(matcher.group(1));
			int y = Integer.parseInt(matcher.group(2));
			int power = Integer.parseInt(unit[0]);
			if(x < 15 && y < 15) {
				
				map[x][y] = map[x][y] + power;
				
				
				int xstart = x;
				int ystart = y;
				int xend = x + 1;
				int yend = y + 1;
				while(power-- > 0) {
					
					for(int m = ystart ; m < yend; m++) {
						for (int n = xstart ; n < xend ; n++) {
							{
								if(m < 0) m = 0;
								if(m > 14) m = 14;
								if(n < 0) n = 0;
								if(n > 14) n = 14;
								if(!(n == x && m == y)) 
									map[n][m] += 1;
							} 
						}
					}
					xstart -= 1;
					ystart -= 1;
					xend += 1;
					yend += 1;
					
				}
				
			}
			
		}
		///////////////////////////// <-------------- 여기까지 구현 (1)
		for(int i = 0 ; i < map.length ; i++) {
			for(int j = 0 ; j < map.length ; j++) {
				System.out.print(map[i][j]+ "  ");
				if(map[i][j] > 1) count++;
			}
			System.out.println();
		}
		
		///////////////////////////// <-------------- 여기까지 구현 (2)
		
		return count;
	}
}