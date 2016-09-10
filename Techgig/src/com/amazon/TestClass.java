package com.amazon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class TestClass {
   
	
	public static String isDivisibleBy7(int l, int r,final String Num){
		
		String temp = Num;
		int left = l-1;
		int right = r;
		String subNumString = temp.substring(left, right);
		
		if(subNumString.length()<5){
			int N = Integer.parseInt(subNumString);
			if(N%7==0){
				return "YES";
			}else{
				return "NO";
			}
		}else{
			String newNumS = isDivisible(subNumString);
			int newNum = Integer.parseInt(newNumS);
			if(newNum%7==0){
				return "YES";
			}else{
				return "NO";
			}
		}
		
	}
	
	public static String isDivisible(final String num){
		String temp = num;
		
		while(temp.length()>5){
		int length = temp.length();
		String lastdigitS = temp.substring(length-1, length);
		String lastThreeDigitS = temp.substring(length-4, length-1);
		int lastDigit = Integer.parseInt(lastdigitS);
		int lastThreeDigit = Integer.parseInt(lastThreeDigitS);
		int difference = lastThreeDigit-(lastDigit*2);
		String t = temp.substring(0,length-4);
		temp = t+difference;
		}
		return temp;
	}
   public static void main(String args[] ){
      
	   try{
	   BufferedReader bi = new BufferedReader(new InputStreamReader(System.in));
        String numberString = bi.readLine();
        
        String QuestionsStr = bi.readLine();
        int noOfQuestions =Integer.parseInt(QuestionsStr);
        for (int i = 0; i < noOfQuestions; i++) {
           
        	try{
        	String temp = bi.readLine();
        	String[] t=temp.split(" ",-1);
        	if(t.length==2){
        	System.out.println(isDivisibleBy7(Integer.parseInt(t[0]),Integer.parseInt(t[1]), numberString));
        	}else{
        		System.out.println("NO");
        	}
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
	   }catch(IOException e){
		   e.printStackTrace();
	   }catch(Exception ee){
		   ee.printStackTrace();
	   }
    }
}
