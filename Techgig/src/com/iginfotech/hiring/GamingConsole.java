package com.iginfotech.hiring;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GamingConsole 
{ 

	private static Map<String,String[]> Console = new HashMap<String,String[]>();
	public static void initializeConsole(){
		Console.put("A", new String[]{"A","B","D"});
		Console.put("B", new String[]{"A","B","C","E"});
		Console.put("C", new String[]{"B","C","F"});
		Console.put("D", new String[]{"A","D","E","G"});
		Console.put("E", new String[]{"B","D","E","F","H"});
		Console.put("F", new String[]{"C","E","F","I"});
		Console.put("G", new String[]{"D","G","H"});
		Console.put("H", new String[]{"E","G","H","I","J"});
		Console.put("I", new String[]{"F","H","I"});
		Console.put("J", new String[]{"J","H"});
	}
    public static int combinationCounts(int input1)
    {
    	if(input1==1){
    		return 10;
    	}
    	initializeConsole();
    	Set<StringBuilder> Solutions = new HashSet<StringBuilder>();
    	for(String thisKey : Console.keySet()){
    		Set<StringBuilder> thisSolution = new HashSet<StringBuilder>();
    		thisSolution.add(new StringBuilder(thisKey));
    		for(int i=1;i<input1;i++){
    			thisSolution=startFrom(thisSolution);
    		}
    		Solutions.addAll(thisSolution);
    	}
    	
    	return Solutions.size();
    }
    
    public static Set<StringBuilder> startFrom(Set<StringBuilder> PreviousSolutions){
    	
    	Set<StringBuilder> CurrentSolution = new HashSet<StringBuilder>();
    	for(StringBuilder oldKey:PreviousSolutions){
    		String key = oldKey.substring(oldKey.length()-1);
    		String[] possibilities = Console.get(key); 
    		for(String s:possibilities){
    			CurrentSolution.add(new StringBuilder(oldKey+s));
    		}
    	}
    	PreviousSolutions.clear();
    	return CurrentSolution;
    }
    public static void main(String[] args){
    	System.out.println(combinationCounts(5));
    }
}