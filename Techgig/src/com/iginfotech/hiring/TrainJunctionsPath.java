package com.iginfotech.hiring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TrainJunctionsPath {

	public static String SOURCE="";
	public static String DESTINATION="";
	public static List<String> destinationSols = new ArrayList<String>();
	 public static String[] quickestroute(String[] input1,String input2)
	    {
		 AllPaths allPaths = new AllPaths();
		 for(int i=0;i<input1.length;i++){
			 //route level seperation
			 String[] stationsStr = input1[i].split("#", -1);
			 for(int j=0;j<stationsStr.length;j++){
				 String[] temp = stationsStr[j].split("-", -1);
				 Path path = new Path(temp[0],temp[1],Integer.parseInt(temp[2]),i);
				 allPaths.paths.add(path);
			 }
		 }
		 
		 String[] srcDestStr = input2.split("#", -1);
		 SOURCE = srcDestStr[0];
		 DESTINATION = srcDestStr[1];
		 getPaths(allPaths);
		 
		 return selectBestSolution(destinationSols,allPaths);
	    }
	 
	 public static String[] getSTringArray(List<String> sol){
		 String[] solArray = new String[sol.size()];
		 for(int i=0;i<sol.size();i++){
			 solArray[i]=sol.get(i);
		 }
		 return solArray;
	 }
	 public static boolean doesPathsContainDestination(Set<String> nexts, String destination){
		 for(String str : nexts){
			 if(str.equalsIgnoreCase(destination)){
				 return true;
			 }
		 }
		 return false;
	 }
	 public static List<String> getPaths(AllPaths allPaths){
		 List<String> SolutionPaths = new ArrayList<String>();
		 List<String> previousSource = new ArrayList<String>();
		 previousSource.add(SOURCE);
		 while(!previousSource.isEmpty()){
			 previousSource = findDestination(allPaths,previousSource);
			 previousSource =removeRedundantDest(previousSource);
			 
		 }
		 return SolutionPaths;
	 }
	 
	 public static List<String> removeRedundantDest(List<String> routes){
		 List<String> newRoutes = new ArrayList<String>();
		 Set<String> sources = new HashSet<String>();
		 for(String str:routes){
				 String[] pathSep = str.split("#", -1);
				 String[] t=str.split("-", -1);
				 String destination = t[(t.length-1)];
				 for(String sour:pathSep){
					 String[] sr = sour.split("-", -1);
					 sources.add(sr[0]);
				 }
				 if(!sources.contains(destination)){
					 newRoutes.add(str);
				 }
				 sources.clear();
		 }
		 routes.clear();
		 return newRoutes;
	 }
	 public static List<String> findDestination(AllPaths allPaths,List<String> sourcesPrevious){
		 List<String> route = new ArrayList<String>();
		 for(String str : sourcesPrevious){
			 String[] temp =str.split("-", -1);
			 String currSource =temp[(temp.length-1)];
			 Set<String> nextJunctions = allPaths.getNextJunctions(currSource);
			 for(String strs : nextJunctions){
				 if(strs.equalsIgnoreCase(DESTINATION)){
					 destinationSols.add(str+"#"+currSource+"-"+strs);
				 }else{
					 if(str.equalsIgnoreCase(SOURCE)){
						 route.add(currSource+"-"+strs);
					 }else{
					 route.add(str+"#"+currSource+"-"+strs);
					 }
				 }
			 }
		 }
		 sourcesPrevious.clear();
		 return route;
	 }
	 
	 public static String[] selectBestSolution(List<String> solutions,AllPaths allPaths){
		String[] Solution=null;
		 List<List<Path>> listPaths = getAsPaths(solutions,allPaths);
		 if(listPaths.size()>1){
			 listPaths=getBestSolBasedOntime(listPaths);
			 if(listPaths.size()>1){
				 listPaths=getBestSolBasedOnTransfers(listPaths);
				 if(listPaths.size()>1){
					 listPaths=getBestSolBasedOnStations(listPaths);
				 }
			 }
		 }
		 Solution=getAsString(listPaths.get(0));
		 return Solution;
	 }
	 
	 public static Map<Integer,String> getRouteBased(List<Path> listPaths){
		 Map<Integer,String> map = new HashMap<Integer,String>();
		 
		 int previous=-1;
		 for(Path path :listPaths){
			 if(previous==-1){
				 previous=path.metroRoute;
				 map.put(previous, path.toString(SOURCE,DESTINATION));
			 }else{
				 if(!map.containsKey(path.metroRoute)){
					 previous=path.metroRoute;
					 map.put(previous, path.toString(SOURCE,DESTINATION));
				 }else{
					 String s = map.get(previous);
					 s=s+"#"+path.toString(SOURCE,DESTINATION);
					 map.put(previous, s);
				 }
			 }
		 }
		 
		 return map;
	 }
	 
	 public static String[] getAsString(List<Path> listPaths){
		 String[] solution=null;
		 Map<Integer,String> map = getRouteBased(listPaths);
		 solution=new String[map.size()+2];
		 solution[0]="NC";
		 int count=1;
		 for(Integer integer : map.keySet()){
			 solution[count]=map.get(integer);
			 count++;
		 }
		 solution[map.size()+1]="NC";
		 return solution;
	 }
	 
	 public static List<List<Path>> getAsPaths(List<String> solutions,AllPaths allPaths){
		 List<List<Path>> asPaths = new ArrayList<List<Path>>();
		 
		 for(String thisSolution : solutions){
			 List<Path> thisPath = new ArrayList<Path>();
			 
			 String[] juncStrs = thisSolution.split("#", -1);
			 for(int i=0;i<juncStrs.length;i++){
				 Path path = allPaths.getPath(juncStrs[i]);
				 if(path!=null){
				 thisPath.add(path);
				 }
			 }
			 asPaths.add(thisPath);
		 }
		 
		 return asPaths;
	 }
	 
	 public static int computeTime(List<Path> paths){
		 int time=0;
		 time = paths.size()*15;
		 int previous=-1;
		 for(Path path :paths){
			 time+=path.time;
			 if(previous==-1){
				 previous=path.metroRoute;
			 }else{
				 if(previous!=path.metroRoute){
					 time+=30;
					 previous=path.metroRoute;
				 }
			 }
		 }
		 return time;
	 }
	 public static List<List<Path>> getBestSolBasedOntime(List<List<Path>> solutions){
		 List<List<Path>> bestSolutionsBasedOnTime = new ArrayList<List<Path>>();
		 int min=-1;int current=-1;
		 for(List<Path> thisSolution : solutions){
			current=computeTime(thisSolution);
			 if(min==-1){
				 min=current;
				 bestSolutionsBasedOnTime.add(thisSolution);
			 }else{
				 if(current==min){
					 bestSolutionsBasedOnTime.add(thisSolution);
				 }else if(current<min){
					bestSolutionsBasedOnTime.removeAll(bestSolutionsBasedOnTime);
					bestSolutionsBasedOnTime.add(thisSolution);
				 }
			 }
		 }
		 
		 solutions.clear();
		return bestSolutionsBasedOnTime; 
	 }
	 
	 public static int computeNoTransfers(List<Path> paths){
		 int noOftransfers=0;
		 int previous=-1;
		 for(Path path :paths){
			 if(previous==-1){
				 previous=path.metroRoute;
			 }else{
				 if(previous!=path.metroRoute){
					 noOftransfers++;
				 }
			 }
		 }
		 return noOftransfers;
	 }
	 public static List<List<Path>> getBestSolBasedOnTransfers(List<List<Path>> solutions){
		 List<List<Path>> bestSolutionsBasedOnTransfers = new ArrayList<List<Path>>();
		 int min=-1;int current=-1;
		 for(List<Path> thisSolution : solutions){
			current=computeNoTransfers(thisSolution);
			 if(min==-1){
				 min=current;
				 bestSolutionsBasedOnTransfers.add(thisSolution);
			 }else{
				 if(current==min){
					 bestSolutionsBasedOnTransfers.add(thisSolution);
				 }else if(current<min){
					bestSolutionsBasedOnTransfers.removeAll(bestSolutionsBasedOnTransfers);
					bestSolutionsBasedOnTransfers.add(thisSolution);
				 }
			 }
		 }
		 
		 solutions.clear();
		return bestSolutionsBasedOnTransfers; 
	 }
	 public static List<List<Path>> getBestSolBasedOnStations(List<List<Path>> solutions){
		 List<List<Path>> bestSolutionsBasedOnTransfers = new ArrayList<List<Path>>();
		 int min=-1;int current=-1;
		 for(List<Path> thisSolution : solutions){
			current=thisSolution.size();
			 if(min==-1){
				 min=current;
				 bestSolutionsBasedOnTransfers.add(thisSolution);
			 }else{
				 if(current==min){
					 bestSolutionsBasedOnTransfers.add(thisSolution);
				 }else if(current<min){
					bestSolutionsBasedOnTransfers.removeAll(bestSolutionsBasedOnTransfers);
					bestSolutionsBasedOnTransfers.add(thisSolution);
				 }
			 }
		 }
		 
		 solutions.clear();
		return bestSolutionsBasedOnTransfers; 
	 }
	public static void main(String[] args) {
		String[] input1={"1-2-30#2-3-25#3-4-30#4-5-45#5-6-30#6-7-15#7-8-60#8-9-40","10-11-45#11-4-60#4-12-60#12-13-45#13-14-30#14-15-35","1-3-40#3-4-25#4-16-30#16-17-15#17-18-20#18-19-30#19-20-25","21-12-30#12-17-180#17-22-45"};
		String input2 = "12#18";
		String[] result=quickestroute(input1,input2);
		for(int i=0;i<result.length;i++){
			System.out.println(result[i]);
		}
	}

}
class Path{
	public String start;
	public String destination;
	public int time;
	public final int metroRoute;
	public Path(String st,String dst,int time,int metroRoute){
		this.start=st;
		this.destination=dst;
		this.time=time;
		this.metroRoute=metroRoute;
	}
	public boolean contains(String s){
		if(start.equalsIgnoreCase(s) || destination.equalsIgnoreCase(s)){
			return true;
		}
		return false;
	}
	public String get(String s){
		if(start.equalsIgnoreCase(s)){
			return destination;
		}else{
			return start;
		}
	}
	public String toString(String source,String dest){
		if(source.equalsIgnoreCase(destination) || dest.equalsIgnoreCase(start)){
			return destination+"-"+start+"-"+time;
		}
		return start+"-"+destination+"-"+time;
	}
}
class AllPaths{
	public List<Path> paths = new ArrayList<Path>();
	
	public List<Path> get(String junction){
		List<Path> pathsContainingJuntion = new ArrayList<Path>();
		
		for(Path path:paths){
			if(path.contains(junction)){
				pathsContainingJuntion.add(path);
			}
		}
		return pathsContainingJuntion;
	}
	
	public Path getPath(String pathStr){
		String[] temp = pathStr.split("-", -1);
		for(Path path:paths){
			if(path.contains(temp[0]) && path.contains(temp[1])){
				return path;
			}
		}
		return null;
	}
	public Set<String> getNextJunctions(String junction){
		Set<String> nextJunctions = new HashSet<String>();
		
		List<Path> pathsContainingJunc = get(junction);
		for(Path path : pathsContainingJunc){
			nextJunctions.add(path.get(junction));
		}
		return nextJunctions;
	}
}