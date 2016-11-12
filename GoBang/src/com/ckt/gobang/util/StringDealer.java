package com.ckt.gobang.util;

public class StringDealer {
	/**
	 * 没用
	 * **/
	public static String getStringFromGroup(int[][] group){
		StringBuilder sb = new StringBuilder();
		for(int i = 0;i < 9; i++){
			for(int j = 0;j < 9;j ++){
				sb.append(group[i][j]);
				sb.append("a");
			}
		}
		return sb.toString();
	}
	/**
	 * 没用
	 * **/
	public static int[][] getGroupFromString(String ss){
		int[][] group = new int[9][9];
		String[] bufferString = ss.split("a");
		for(int i = 0;i < 9; i++){
			for(int j = 0;j < 9;j ++){
				group[i][j] = new Integer(bufferString[9*i+j]); 
			}
		}
		return group;
	}
	
	/**
	 * 从字符串中获取到信息
	 * example 3,3,0
	 * 第一个三表示第4行，第二个三表示第四列，最后一个0表示时候获胜，0为对方未获胜，1表示对方获胜
	 * **/
	public static int[] getDataFromString(String ss){
		String[] dataString = ss.split(",");
		int[] data = new int[3];
		data[0] = Integer.parseInt(dataString[0]);
		data[1] = Integer.parseInt(dataString[1]);
		data[2] = Integer.parseInt(dataString[2]);
		return data;
	}
	
	/**
	 * getDataFromString方法的逆向
	 * **/
	public static String getStringFromData(int[] data){
		StringBuilder ss = new StringBuilder("");
		for(int  i = 0;i < 3;i++){
			ss.append(data[i]);
			if(i < 2){
				ss.append(",");
			}
		}
		return ss.toString();
	}
}
