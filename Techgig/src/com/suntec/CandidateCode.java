package com.suntec;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CandidateCode {
	public static int rightMost=0;
	public static String[] getHorizon(int input1,String[] input2)
    {
		String[] output=null;
		List<Building> buildingsList = new ArrayList<Building>();
		for(String buildingStr : input2){
			String[] temp = buildingStr.split("#", -1);
			if(temp.length==3){
				Building building = new Building(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]),Integer.parseInt(temp[2]));
				buildingsList.add(building);
				if(building.rightCorner>rightMost){
					rightMost=building.rightCorner;
				}
			}
		}
		
		buildingsList.sort(new Comparator<Building>(){
			public int compare(Building o1, Building o2) {
				        if(o1.getxLength() > o2.getxLength()){
				            return 1;
				        } else {
				            return -1;
				        }
				    }
			});
		List<String> outputList = new ArrayList<String>();
		outputList.add(buildingsList.get(0).leftCorner+"#"+0);
		for(Building building:buildingsList){
			boolean[] results= isVisibleHorizon(building, buildingsList);
			if(results[0]){
				outputList.add(building.leftCorner+"#"+building.height);
			}
			if(results[1]){
				outputList.add(building.rightCorner+"#"+building.height);
			}
		}
		for(Building building:buildingsList){
			outputList=addCorners(building,buildingsList,outputList);
		}
		outputList = removeRedundant(outputList);
		outputList.add(rightMost+"#"+0);
		output=new String[outputList.size()];
		return outputList.toArray(output);
    }

	public static String getNextCoordinate(boolean isHorizontal,List<String> outputList,String x){
		boolean isset=false;
		int min=-1;String solution="";
		for(String str : outputList){
			String[] temp = str.split("#", -1);
			if(isHorizontal){
				if(temp[1].equalsIgnoreCase(x)){
					if(!isset){
						solution=str;
						min=Integer.parseInt(temp[0]);
						isset=true;
					}else if(Integer.parseInt(temp[0])<min){
						solution=str;
						min=Integer.parseInt(temp[0]);
					}
				}
			}else{
				if(temp[0].equalsIgnoreCase(x)){
						solution=str;
				}
			}
		}
		return solution;
	}
	
	public static List<String> removeRedundant(List<String> outputList){
		List<String> tempList = new ArrayList<String>();
		tempList.addAll(outputList);
		List<String> deletion = new ArrayList<String>();
		List<String> Solution = new ArrayList<String>();
		int count=0;
		Solution.add(outputList.get(0));
		tempList.remove(outputList.get(0));
		
		Solution.add(outputList.get(1));
		tempList.remove(outputList.get(1));
		String current = outputList.get(1);
		
		while(!((deletion.size()+Solution.size())==outputList.size())){
			String[] temp = current.split("#", -1);
			String next;
			if(count%2==0){
				next=getNextCoordinate(true, tempList, temp[1]);
				tempList.remove(next);
				deletion.add(next);
			}else{
				next=getNextCoordinate(false, tempList, temp[0]);
				Solution.add(next);
				tempList.remove(next);
			}
			current=next;
			count++;
		}
		outputList.clear();
		return Solution;
	}
	
	public static List<String> addCorners(Building currentBuilding, List<Building> buildingList,List<String> outputList){
		for(Building otherBuilding : buildingList){
			if(otherBuilding.height==currentBuilding.height && otherBuilding.leftCorner==currentBuilding.leftCorner && otherBuilding.rightCorner==currentBuilding.rightCorner){
				continue;
			}else{
				
				if(otherBuilding.height!=currentBuilding.height && otherBuilding.leftCorner<currentBuilding.rightCorner &&otherBuilding.leftCorner>currentBuilding.leftCorner){
					if(currentBuilding.height>otherBuilding.height){
						if(!outputList.contains(currentBuilding.rightCorner+"#"+otherBuilding.height))
							outputList.add(currentBuilding.rightCorner+"#"+otherBuilding.height);
					}else{
						if(!outputList.contains(otherBuilding.leftCorner+"#"+currentBuilding.height))
							outputList.add(otherBuilding.leftCorner+"#"+currentBuilding.height);
					}
				}
				if(otherBuilding.height!=currentBuilding.height && otherBuilding.rightCorner>currentBuilding.leftCorner && otherBuilding.rightCorner<currentBuilding.rightCorner){
					if(currentBuilding.height>otherBuilding.height){
						if(!outputList.contains(currentBuilding.leftCorner+"#"+otherBuilding.height))
							outputList.add(currentBuilding.leftCorner+"#"+otherBuilding.height);
					}else{
						if(!outputList.contains(otherBuilding.rightCorner+"#"+currentBuilding.height))
							outputList.add(otherBuilding.rightCorner+"#"+currentBuilding.height);
					}
				}
			}
		}
		return outputList;
	}
	public static boolean[] isVisibleHorizon(Building currentBuilding, List<Building> buildingList){
		boolean[] result = new boolean[2];
		result[0]=true;result[1]=true;
		
		for(Building otherBuilding : buildingList){
			if(otherBuilding.height==currentBuilding.height && otherBuilding.leftCorner==currentBuilding.leftCorner && otherBuilding.rightCorner==currentBuilding.rightCorner){
				continue;
			}else{
				if(otherBuilding.height>currentBuilding.height && otherBuilding.leftCorner==currentBuilding.leftCorner){
					result[0]=false;
				}else if(otherBuilding.height>currentBuilding.height && otherBuilding.leftCorner<currentBuilding.leftCorner && otherBuilding.rightCorner>currentBuilding.leftCorner){
					result[0]=false;
				}
				
				if(otherBuilding.height>currentBuilding.height && otherBuilding.leftCorner<currentBuilding.rightCorner && otherBuilding.rightCorner>currentBuilding.rightCorner){
					result[1]=false;
				}
			}
		}
		return result;
	}
	public static void main(String[] args){
		String[] input2 = {"2#9#6","3#11#8","7#13#9","1#1#2"};
		String[] output = getHorizon(4, input2);
		for(String str : output){
			System.out.println(str);
		}
	}
}
class Building{
	public int getxLength() {
		return leftCorner;
	}
	public void setxLength(int xLength) {
		this.leftCorner = xLength;
	}
	public Building(int xLength, int height, int xWidth) {
		this.leftCorner = xLength;
		this.height = height;
		this.rightCorner = xWidth;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getxWidth() {
		return rightCorner;
	}
	public void setxWidth(int xWidth) {
		this.rightCorner = xWidth;
	}
	public int leftCorner;
	public int height;
	public int rightCorner;
	
}
