package com.suntec;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class DancePairMenWomen {

	public static int totalmatching(int input1,String[] input2)
	{
		if(input1==input2.length){
			
			Map<String,Set<String>> menNPrfrncsMap = new HashMap<String,Set<String>>();
			Set<String> totalMen = new TreeSet<String>();
			Set<String> totalWomen = new TreeSet<String>();

			for(String thisManStr : input2){
				String[] thisManTempArr = thisManStr.split("#", -1);
				if(thisManTempArr.length<2){
					return -1;
				}else{
					String thisman =thisManTempArr[0]; 
					totalMen.add(thisman);
					Set<String> intrstdWmn = new TreeSet<String>();
					for(int i=1;i<thisManTempArr.length;i++){
						intrstdWmn.add(thisManTempArr[i]);
					}
					menNPrfrncsMap.put(thisman, intrstdWmn);
					totalWomen.addAll(intrstdWmn);
				}
			}
			List<String> oldCombinations = new LinkedList<String>();
			if(totalMen.size()==totalWomen.size()){
				int count=1;
				for(String key : menNPrfrncsMap.keySet()){
					Set<String> preferences = menNPrfrncsMap.get(key);
					if(count==1){
						oldCombinations.addAll(preferences);
					}else{
						oldCombinations = addCombination(oldCombinations, preferences);
					}
					count++;
				}
			}else{
				return -1;
			}
			if(oldCombinations.size()>0){
				return oldCombinations.size();
			}else{
				return -1;
			}
		}else{
			return -1;
		}
	}
	public static List<String> addCombination(List<String> oldCombinations, Set<String> preferences){
		List<String> newCombination = new LinkedList<String>();
		for(String combination : oldCombinations){
			for(String preferedWoman : preferences){
				if(!combination.contains(preferedWoman)){
					newCombination.add(combination+"#"+preferedWoman);
				}
			}
		}
		oldCombinations.clear();
		preferences.clear();
		return newCombination;
	}

	public static void main(String[] args) {
		String[] input1 = {"M1#W2#W4","M2#W1#W2","M3#W1#W3#W4","M4#W4#W5","M5"};
		String[] input2 = {"M1#W2#W4","M2#W1#W2","M3#W1#W3#W4","M4#W4#W5","M5#W4"};
		String[] input3 = {"M1#W4","M2#W1#W2","M3#W1#W3#W4","M4#W4#W5","M5#W4"};
		String[] input4 = {"M1#W6#W10","M2#W1#W5","M3#W1#W3#W5#W9","M4#W3#W4","M5#W2#W6","M6#W1#W2#W6","M7#W1#W7#W8","M8#W8#W10","M9#W3#W9","M10#W10"};

		System.out.println(totalmatching(10, input4));
	}

}
