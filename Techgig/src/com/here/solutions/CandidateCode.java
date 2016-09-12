package com.here.solutions;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class CandidateCode {
	public static int ROW,COLUMN,firstx,firsty;
	public static int[][] tempcabin;
	  public static int minimumpossiblecost(String input1)
	    {
		  Set<String> Ps = new HashSet<String>();
		  Set<String> NPs=new HashSet<String>();
		  firstx = 0;firsty=0;
		  final int[][] Cabins;
		  String[] rows = input1.split("#", -1);
		  ROW=rows.length;
		  Cabins=new int[ROW][];
		  for(int i=0;i<rows.length;i++){
			  String[] columns = rows[i].split("@", -1);
			  COLUMN=columns.length;
			  Cabins[i]=new int[COLUMN];
			  for(int j=0;j<columns.length;j++){
				  int thisval = Integer.parseInt(columns[j]);
				  Cabins[i][j]=thisval;
				  if(thisval==-1){
					  Ps.add(i+"#"+j);
					  firstx=i;firsty=j;
				  }else{
					  NPs.add(i+"#"+j);
				  }
				  
			  }
		  }
		  if(Ps.size()==0){
			  return 0;
		  }
		  
		  if(checkIfAllConnected(Cabins,firstx,firsty,Ps.size())){
			  return 0;
		  }
		  
		  return 0;
	    }
	  
	  public static List<Integer[]> checkleft(int x,int y,List<Integer[]> temp){
		  if(x>0){
			  if(tempcabin[x-1][y]==-1){
				  temp.add(new Integer[]{x-1,y});
				  tempcabin[x-1][y]=-2;
			  }
		  }
		  return temp;
	  }

	  public static List<Integer[]> checkright(int x,int y,List<Integer[]> temp){
		  if(x<(ROW-1)){
			  if(tempcabin[x+1][y]==-1){
				  temp.add(new Integer[]{x+1,y});
				  tempcabin[x+1][y]=-2;
			  }
		  }
		  return temp;
	  }

	  public static List<Integer[]> checktop(int x,int y,List<Integer[]> temp){
		  if(y<(COLUMN-1)){
			  if(tempcabin[x][y+1]==-1){
				  temp.add(new Integer[]{x,y+1});
				  tempcabin[x][y+1]=-2;
			  }
		  }
		  return temp;
	  }

	  public static List<Integer[]> checkbottom(int x,int y,List<Integer[]> temp){
		  if(y>0){
			  if(tempcabin[x][y-1]==-1){
				  temp.add(new Integer[]{x,y-1});
				  tempcabin[x][y-1]=-2;
			  }
		  }
		  return temp;
	  }
	  public static List<Integer[]> checktopLcorner(int x,int y,List<Integer[]> temp){
		  if(y<(COLUMN-1) && x>0){
			  if(tempcabin[x-1][y+1]==-1){
				  temp.add(new Integer[]{x-1,y+1});
				  tempcabin[x-1][y+1]=-2;
			  }
		  }
		  return temp;
	  }
	  public static List<Integer[]> checktopRcorner(int x,int y,List<Integer[]> temp){
		  if(y<(COLUMN-1) && x<(ROW-1)){
			  if(tempcabin[x+1][y+1]==-1){
				  temp.add(new Integer[]{x+1,y+1});
				  tempcabin[x+1][y+1]=-2;
			  }
		  }
		  return temp;
	  }
	  public static List<Integer[]> checkbottomLcorner(int x,int y,List<Integer[]> temp){
		  if(y>0 && x>0){
			  if(tempcabin[x-1][y-1]==-1){
				  temp.add(new Integer[]{x-1,y-1});
				  tempcabin[x-1][y-1]=-2;
			  }
		  }
		  return temp;
	  }
	  public static List<Integer[]> checkbottomRcorner(int x,int y,List<Integer[]> temp){
		  if(y>0 && x<(ROW-1)){
			  if(tempcabin[x+1][y-1]==-1){
				  temp.add(new Integer[]{x+1,y-1});
				  tempcabin[x+1][y-1]=-2;
			  }
		  }
		  return temp;
	  }
	  public static boolean checkIfAllConnected(int[][] cabin,int x,int y,int totalPs){
		  tempcabin=cabin.clone();
		  int count=1;
		  Set<String> reachedPoints = new HashSet<String>();
		  List<Integer[]> nextPass = new LinkedList<Integer[]>();
		  nextPass.add(new Integer[]{x,y});
		  tempcabin[x][y]=-2;
		  while(!nextPass.isEmpty()){
			  List<Integer[]> temp = new LinkedList<Integer[]>();
			  for(Integer[] thiscoordinate:nextPass){
				  int CurrentX = thiscoordinate[0];
				  int CurrentY = thiscoordinate[1];
				  temp = checkbottom(CurrentX, CurrentY, temp);
				  temp = checktop( CurrentX, CurrentY, temp);
				  temp = checkleft( CurrentX, CurrentY, temp);
				  temp = checkright( CurrentX, CurrentY, temp);
				  temp = checktopLcorner( CurrentX, CurrentY, temp);
				  temp = checktopRcorner( CurrentX, CurrentY, temp);
				  temp = checkbottomLcorner( CurrentX, CurrentY, temp);
				  temp = checkbottomRcorner( CurrentX, CurrentY, temp);
			  }
			  nextPass.clear();
			  count+=temp.size();
			  if(count==totalPs){
				  return true;
			  }
			  nextPass.addAll(temp);temp.clear();
		  }
		  return false;
	  }
	public static void main(String[] args) {
		System.out.println(minimumpossiblecost("-1@10@-1#10@-1@10#-1@10@-1"));
	}

}
