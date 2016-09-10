package com.may.twentyseven;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class CandidateCode {
	static int days;
	static int num;
	Set<String> Employees = new HashSet<String>();

	public static String[] getInput(){

		Scanner in = new Scanner(System.in);
		days = in.nextInt();
		num = in.nextInt();in.nextLine();
		String[] entries = new String[days*num];
		for(int i=0;i<(days*num);i++){
			entries[i] = in.nextLine();
		}
		return entries;
	}

	public static List<DayReport> getDay(String[] input){

		List<DayReport> dayReports = new ArrayList<DayReport>();
		int dayCount=1;
		for(int i=0;i<input.length;i+=num){
			String[] temp = new String[num]; 
			for(int j=0;j<num;j++){
				temp[j] = input[i+j];
			}
			DayReport dayReport = new DayReport(dayCount, temp);dayCount++;	
			dayReports.add(dayReport);
		}
		return dayReports;
	}

	public static List<DayReport> findStarCommitters(List<DayReport> dayReports){
		int max =0;
		String previousStar = "";
		Map<String,Integer> map = new HashMap<String,Integer>();
		for(DayReport dayReport : dayReports){
			String dayWinner = dayReport.getCleanCommitter();
			if(map.containsKey(dayWinner)){
				int count = map.get(dayWinner);
				count++;
				map.put(dayWinner, count);
			}else{
				map.put(dayWinner, 1);
			}
			String star = getStar(map,previousStar);
			previousStar = star;
			dayReport.setStarCommitter(star);
		}
		return dayReports;
	}
	
	public static String getStar(Map<String,Integer> map,String previousStar){
		
		int max = 0;
		for(String name : map.keySet()){
			int thiscount = map.get(name);
			if(thiscount>max){
				max = thiscount;
			}
		}
		
		List<String> tentativeStars = new ArrayList<String>();
		for(String name : map.keySet()){
			int thiscount = map.get(name);
			if(thiscount==max){
				tentativeStars.add(name);
			}
		}
		
		if(tentativeStars.size()>1 && !previousStar.equals("")){
			for(String name : tentativeStars){
				if(name.equals(previousStar)){
					return name;
				}
			}
		}else{
			return tentativeStars.get(0);
		}
		return "";
	}
	
	public static void main(String args[] ) throws Exception {
		String[] entries =getInput();
		List<DayReport> dayreports=getDay(entries);
		dayreports = findStarCommitters(dayreports);
		for(DayReport day : dayreports){
			System.out.println(day.getDay()+" "+day.getCleanCommitter()+" "+day.getStarCommitter());
		}
	}
}

class DayReport{

	int day;
	String cleanCommitter;
	String starCommitter;
	Map<String,Integer[]> employeesReport;

	public String getStarCommitter() {
		return starCommitter;
	}

	public void setStarCommitter(String starCommitter) {
		this.starCommitter = starCommitter;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public String getCleanCommitter() {
		return cleanCommitter;
	}

	public void setCleanCommitter(String cleanCommitter) {
		this.cleanCommitter = cleanCommitter;
	}

	public DayReport(int nday, String[] employeeReportArray){
		employeesReport = new HashMap<String,Integer[]>();
		day = nday;
		for(int i=0;i<employeeReportArray.length;i++){
			Integer[] goodBadCommit = new Integer[2];
			String[] temp = employeeReportArray[i].split(" ", -1);
			goodBadCommit[0] = Integer.parseInt(temp[1]);
			goodBadCommit[1] = Integer.parseInt(temp[2]);

			employeesReport.put(temp[0], goodBadCommit);
		}
		getEmployeeCommits();
	}

	//1
	public void getEmployeeCommits(){
		Map<String,Integer> map = new HashMap<String,Integer>();
		int max = 0;
		for(String name :employeesReport.keySet()){

			Integer[] commits = employeesReport.get(name);
			int diff = commits[0];
			//2
			if(diff>max){
				max = diff;
			}
			map.put(name, diff);
		}

		//3
		List<String> tentativeWinners = new ArrayList<String>();
		for(String name : map.keySet()){
			if(max == map.get(name)){
				tentativeWinners.add(name);
			}
		}
		/*if(tentativeWinners.size()==1){
			cleanCommitter = tentativeWinners.get(0); 
			return;
		}else{
			int maxCommit = 0;
			for(String name : tentativeWinners){
				Integer[] commits = employeesReport.get(name);
				if(maxCommit<commits[0]){
					maxCommit = commits[0];
				}
			}

			for(String name : tentativeWinners){
				Integer[] commits = employeesReport.get(name);
				if(maxCommit ==commits[0]){
					this.cleanCommitter = name;
					return;
				}
			}
		}*/
		
		if(tentativeWinners.size()==1){
			cleanCommitter = tentativeWinners.get(0); 
			return;
		}else{
			int maxCommit = 0;
			for(String name : tentativeWinners){
				Integer[] commits = employeesReport.get(name);
				if(maxCommit<commits[1]){
					maxCommit = commits[1];
				}
			}

			for(String name : tentativeWinners){
				Integer[] commits = employeesReport.get(name);
				if(maxCommit ==commits[1]){
					this.cleanCommitter = name;
					return;
				}
			}
		}
	}

}

