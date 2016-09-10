package com.suntec;

public class CandidateCode {

	public static String[] getFinalRatPositions(String[] initialPositions,String[] temperatures){
		return null;
	}
	public static void main(String[] args) {
		String[] input1 = {"1#1","2#5","3#3","6#3"};
		String[] input2 = {"2#6#8#6#-7","2#5#-5#-5#0","-1#3#-8#8#7","3#2#0#6#9","2#1#-4#5#8","-5#6#7#4#7"};
		String[] output = getFinalRatPositions(input1, input2);
		for(String str : output){
			System.out.println(str);
		}
	}

}
