package com.lgcns.tct.childcare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChildCare2 {
	
	/**
	 * 선발 제외 대상 데이터를 삭제하는 기능
	 * 
	 * @param 		inputData		입력 데이터
	 * @return		List<String>	제외하고 남은 선발대상 데이터 (지원자의 사번, 순서 상관 없음)
	 */
	public List<String> getRemovedList( String inputData ) {
		
		List<String> removedList = new ArrayList<String>();
		
		////////////////////////여기부터 코딩 (1) ---------------->
		String[] temp_array = inputData.split("#");
		for(int i=0; i<temp_array.length; i++){
			String[] test = temp_array[i].split(",");
			//2014, 2015, 2016, 2017일 경우에만 추가
			if(test[1].equals("2017") || test[1].equals("2016") || test[1].equals("2015") || test[1].equals("2014")){
				removedList.add(test[0]);
			}
		}
		
		
		///////////////////////////// <-------------- 여기까지 코딩 (1)
		
		return removedList;
	}
	
	/**
	 * 선발 대상 선정하기
	 * 
	 * @param 		inputData		입력 데이터
	 * @return		List<String>	원아로 최종 선정된 선발대상 데이터 (지원자의 사번, 순서 상관 없음)
	 */
	public List<String> getSelectedList( String inputData ) {
		
		List<String> selectedList = new ArrayList<String>();
		
		////////////////////////여기부터 코딩 (2) ---------------->
		List<String> removedList = new ArrayList<String>();
		List<String[]> list = new ArrayList<String[]>();
		String[] temp_array = inputData.split("#");
		for(int i=0; i<temp_array.length; i++){
			String[] test = temp_array[i].split(",");
			if(test[1].equals("2017") || test[1].equals("2016") || test[1].equals("2015") || test[1].equals("2014")){
				list.add(test);
			}
		}
		//정렬
		//기준1 : 년도, 기준2 : 등급, 기준3 : 사번 (3가지 모두 오름차순이며, 기준이 작은 것을 우선시)
		for(int i=0; i<list.size()-1; i++){
			for(int j=0; j<list.size()-1-i; j++){
				
				if(Integer.parseInt(list.get(j)[1]) > Integer.parseInt(list.get(j+1)[1])){
					Collections.swap(list, j, j+1);
				}else if(Integer.parseInt(list.get(j)[1]) == Integer.parseInt(list.get(j+1)[1])){
					if(list.get(j)[2].charAt(0) > list.get(j+1)[2].charAt(0)){
						Collections.swap(list, j, j+1);
					}else if(list.get(j)[2].charAt(0) == list.get(j+1)[2].charAt(0)){
						if(Integer.parseInt(list.get(j)[0]) > Integer.parseInt(list.get(j+1)[0])){
							Collections.swap(list, j, j+1);
						}
					}
				}
			}
		}
		//추가할 숫자 계산을 위한 count
		int count2014 = 0;
		int count2015 = 0;
		int count2016 = 0;
		int count2017 = 0;
		for(int i=0; i<list.size(); i++){
			//기준 순서대로 정렬되어 있기 때문에, 정원이 찰 때가지 추가가만 하면 됨
			if(list.get(i)[1].equals("2014")){
				if(count2014 < 1){
					selectedList.add(list.get(i)[0]);
					count2014++;
				}
			}else if(list.get(i)[1].equals("2015")){
				if(count2015 < 2){
					selectedList.add(list.get(i)[0]);
					count2015++;
				}
				
			}else if(list.get(i)[1].equals("2016")){
				if(count2016 < 2){
					selectedList.add(list.get(i)[0]);
					count2016++;
				}
				
			}else if(list.get(i)[1].equals("2017")){
				if(count2017 < 4){
					selectedList.add(list.get(i)[0]);
					count2017++;
				}
				
			}
			
		}
		
		
		///////////////////////////// <-------------- 여기까지 코딩 (2)
		
		return selectedList;
	}
}