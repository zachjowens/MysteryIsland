package com.zjo.mysteryisland.test;

import java.text.DecimalFormat;

public class Area51 {
	
	public static void main(String[] args) {
		
		int testInt;
		testInt = (int) 27.6;
		System.out.println(testInt);
		
		DecimalFormat dfQuestion = new DecimalFormat("#0.##E0");
		System.out.println(dfQuestion.format(12.7896987));
	}
}
