package com.lgcns.tct.meteor;

public class Meteor2 {
	
	/**
	 * 운석 낙하 위치의 개수를 계산하는 기능
	 * 
	 * @param 		inputData		운석 낙하 위치 입력 데이터
	 * @return		int				낙하한 운석의 개수
	 */
	public int getMeteorFallCount( String inputData ) {
		
		int count = 0;
		
		//////////////////////여기부터 구현 (1) ---------------->
		String[] splitedInputData = inputData.split("#");
		
		for ( int idx = 0 ; idx < splitedInputData.length ; idx++ ) {
			String[] meteorInfo = splitedInputData[idx].split("\\*");
			String meteorPositionXY = meteorInfo[1];
			
			meteorPositionXY = meteorPositionXY.substring(1, meteorPositionXY.length()-1);
			String[] meteorPosition = meteorPositionXY.split(",");
			
			if ( Integer.parseInt(meteorPosition[0]) < 0 || Integer.parseInt(meteorPosition[0]) > 14 ) {
				continue;
			}
			

			if ( Integer.parseInt(meteorPosition[1]) < 0 || Integer.parseInt(meteorPosition[1]) > 14 ) {
				continue;
			}
			
			count++;
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
		String[] splitedInputData = inputData.split("#");
		int[][] ground = new int[15][15];
		for ( int idx = 0 ; idx < ground.length ; idx++ ) {
			for ( int jdx = 0 ; jdx < ground.length; jdx++ ) {
				ground[idx][jdx] = 0;
			}
		}
		
		for ( int idx = 0 ; idx < splitedInputData.length ; idx++ ) {
			String[] meteorInfo = splitedInputData[idx].split("\\*");
			int meteorPower = Integer.parseInt(meteorInfo[0]);
			String meteorPositionXY = meteorInfo[1];
			
			meteorPositionXY = meteorPositionXY.substring(1, meteorPositionXY.length()-1);
			String[] meteorPosition = meteorPositionXY.split(",");
			
			if ( Integer.parseInt(meteorPosition[0]) < 0 || Integer.parseInt(meteorPosition[0]) > 14 ) {
				continue;
			}
			

			if ( Integer.parseInt(meteorPosition[1]) < 0 || Integer.parseInt(meteorPosition[1]) > 14 ) {
				continue;
			}
			
			// 계산
			int meteorPositionX = Integer.parseInt(meteorPosition[0]);
			int meteorPositionY = Integer.parseInt(meteorPosition[1]);
			meteorPower = meteorPower - 1;
			
			while (meteorPower >= 0) {
				
				for ( int jdx = meteorPositionX - meteorPower ; jdx <= meteorPositionX + meteorPower ; jdx++ ) {
					
					if ( jdx < 0 || jdx > 14 ) {
						continue;
					}
					
					for ( int kdx = meteorPositionY - meteorPower ; kdx <= meteorPositionY + meteorPower ; kdx++ ) {
						
						if ( kdx < 0 || kdx > 14 ) {
							continue;
						}
						ground[jdx][kdx] = ground[jdx][kdx]+1;
					}
				}
				meteorPower = meteorPower - 1;
			}
			
			
		}
		
		for ( int idx = 0 ; idx < ground.length ; idx++ ) {
			for ( int jdx = 0 ; jdx < ground.length; jdx++ ) {
				if ( ground[idx][jdx] >= 2 ) {
					count++;
				}
			}
		}
		///////////////////////////// <-------------- 여기까지 구현 (2)
		
		return count;
	}
}