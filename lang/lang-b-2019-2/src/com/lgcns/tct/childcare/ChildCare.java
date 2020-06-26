package com.lgcns.tct.childcare;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChildCare {
	
	/**
	 * 선발 제외 대상 데이터를 삭제하는 기능
	 * 
	 * @param 		inputData		입력 데이터
	 * @return		List<String>	제외하고 남은 선발대상 데이터 (지원자의 사번, 순서 상관 없음)
	 */
	public List<String> getRemovedList( String inputData ) {
		
		List<String> removedList = new ArrayList<String>();
		
		////////////////////////여기부터 코딩 (1) ---------------->
		Map<Integer, List<Child>> children = Arrays.stream(inputData.split("#")).map(o -> {
			Child ch = new Child();
			String[] t= o.split(",");
			ch.no = t[0];
			ch.year = Integer.parseInt(t[1]);
			ch.grade = t[2].charAt(0);
			return ch;
		}).collect(Collectors.groupingBy(o -> o.year));
		List<String> collect = children.entrySet().stream().filter(o -> o.getKey() >= 2014 && o.getKey() <= 2017)
				.<Child>flatMap(o -> o.getValue().stream()).map(o -> ""+o.no).sorted(Comparator.reverseOrder()).collect(Collectors.toList());

		return collect;
	}
	
	public static class Child{
		String no;
		int year;
		char grade;
		public String toString() {
			return String.format("%s, %d, %c", no, year, grade);
		}
	}
	
	/**
	 * 선발 대상 선정하기
	 * 
	 * @param 		inputData		입력 데이터
	 * @return		List<String>	원아로 최종 선정된 선발대상 데이터 (지원자의 사번, 순서 상관 없음)
	 */
	public List<String> getSelectedList( String inputData ) {
		
		List<String> removedList = new ArrayList<String>();
		////////////////////////여기부터 코딩 (2) ---------------->
		Comparator<Child> c = (a, b) -> {
			int gap =  a.grade - b.grade  ;
			if (gap == 0) {
				gap = a.no.compareTo(b.no);
			}
			return gap;
		};
		Map<Integer, List<Child>> children = Arrays.stream(inputData.split("#")).map(o -> {
			Child ch = new Child();
			String[] t= o.split(",");
			ch.no = t[0];
			ch.year = Integer.parseInt(t[1]);
			ch.grade = t[2].charAt(0);
			return ch;
		}).collect(Collectors.groupingBy(o -> o.year));
		
		List<String> collect = children.entrySet().stream().filter(o -> o.getKey() >= 2014 && o.getKey() <= 2017)
				.<Child>flatMap(o -> o.getValue().stream()).map(o -> ""+o.no).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
		
		List<String> collect2017 = children.entrySet().stream().flatMap(o -> o.getValue().stream())
				.filter(o -> o.year == 2017).sorted(c).limit(4).map(o -> ""+o.no).collect(Collectors.toList());
		
		List<String> collect2016 = children.entrySet().stream().flatMap(o -> o.getValue().stream())
				.filter(o -> o.year == 2016).sorted(c).limit(2).map(o -> ""+o.no).collect(Collectors.toList());
//		
		List<String> collect2015 = children.entrySet().stream().flatMap(o -> o.getValue().stream())
				.filter(o -> o.year == 2015).sorted(c).limit(2).map(o -> ""+o.no).collect(Collectors.toList());
		
		List<String> collect2014 = children.entrySet().stream().flatMap(o -> o.getValue().stream())
				.filter(o -> o.year == 2014).sorted(c).limit(1).map(o -> ""+o.no).collect(Collectors.toList());
		
//		77444  77449 443, 442, 431
		removedList.addAll(collect2014);
		removedList.addAll(collect2015);
		removedList.addAll(collect2016);
		removedList.addAll(collect2017);
		Set<String> set = new HashSet<String>(removedList);
		///////////////////////////// <-------------- 여기까지 코딩 (1)
		removedList = new ArrayList<String>(set);
		///////////////////////////// <-------------- 여기까지 코딩 (2)
		removedList.sort((a, b) -> b.compareTo(a));
		return removedList;
	}
}