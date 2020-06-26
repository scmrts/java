package com.lgcns.tct.name;


import java.util.Arrays;
import java.util.List;

public class NameRun {

	public static String familyName;
	
    public static void main(String[] args) {    	
		// 제공 데이터 세트2를 실행하려면 loadData에서 제공 데이터 세트1을 주석 처리하고 제공 데이터 세트2를 주석 해제하여 실행
    	List<String> inputData=loadData();  
		printInput(inputData);	
    	Name name = new Name();
    	
    	// 표기법 변환 기능
    	List<String> changedList = name.changeNotation(inputData);
    	printNotationConversionList(changedList);
    	    	
    	// 동일 성씨 계산 기능
    	int numberOfSameName = name.calculateSameName(changedList, familyName);
    	printNumberOfSameFamilyName(numberOfSameName); 
    }

    private static List<String> loadData() {
    	
		//////////////////////////////////
		// 제공 데이터 세트 1
		/////////////////////////////////
//    	
//    	List<String>  inputData = Arrays.asList(
//    			"Kim yu na", "Ban Ki moon", "Ha Kyoung Min", "Kim Yu Na", "Lee Sang Wook", "Park Ji sung", "Ryu Hyun Jin", "Chung Myung whun"
//		);
//    	
//    	familyName = "Kim";
    	
		//////////////////////////////////
		// 제공 데이터 세트 2
		/////////////////////////////////    	
    	
    	List<String>  inputData = Arrays.asList(
    			"Han Yu mi", "Choi Yu Na", "Hong Gil dong", "Ha Kyoung Min", "Kim Min A", "Park Sang Myun", "Park Ji sung", "Ryu Hyung Jin"
		);
		familyName = "Park";
    	
    	
    	return inputData;
	}    
    
    private static void printInput(List<String>inputData){
    	printLineInitial();    	
    	for(String data : inputData){
    		System.out.println(data);	
    	}
    	printLine();
    }
    
    private static void printNotationConversionList(List<String> changedList) {   
    	
    	System.out.println("[표기법 변환 결과]");    
    	if(changedList.size() == 0){
    		System.out.println("결과값이 없습니다.");
    	}else{    		
        	for(String notationConversion : changedList){
        		System.out.println(notationConversion);	
        	}	
    	}
    	printLine();    	
    }    

    private static void printNumberOfSameFamilyName(int numberOfSameName) {   
    	System.out.println("[동일 성씨("+familyName+") 계산]");
    	if(numberOfSameName == 0){
    		System.out.println("결과값이 없습니다.");
    	}else{
    		System.out.println("[동일 성씨 수 ]: "+numberOfSameName);
    	}
    	printLine();    	
    }   

	private static void printLine(){
		System.out.println("-------------------------------------------");
	}	
    
	private static void printLineInitial(){
		System.out.println("[초기 입력 데이터]");
    }
}

