package com.ckt.gobang.util;

import java.util.List;



import android.graphics.Point;

public class LogicUtil {
	// 检查是否有五子连起来
	public static boolean isWin(int x, int y) {
		if (isHFive(x, y, 5) || isVFive(x, y, 5) || isLTFive(x, y, 5)
				|| isRTFive(x, y, 5))
			return true;
		return false;

	}

	// 横向是否五子连珠
	public static boolean isHFive(int x, int y, int mode) {
		int count = 1;
		for (int i = x; i < 8; i++) {
			if (ConstantUtil.ground[y][x] == ConstantUtil.ground[y][i + 1]) {
				count++;
			} else {
				break;
			}
		}
		for (int j = x; j > 0; j--) {
			if (ConstantUtil.ground[y][x] == ConstantUtil.ground[y][j - 1]) {
				count++;
			} else {
				break;
			}
		}
		if (count >= mode) {
			return true;
		}
		return false;
	}

	// 纵向是否五子连珠
	public static boolean isVFive(int x, int y, int mode) {
		int count = 1;
		for (int i = y; i < 8; i++) {
			if (ConstantUtil.ground[y][x] == ConstantUtil.ground[i + 1][x]) {
				count++;
			} else {
				break;
			}
		}
		for (int j = y; j > 0; j--) {
			if (ConstantUtil.ground[y][x] == ConstantUtil.ground[j - 1][x]) {
				count++;
			} else {
				break;
			}
		}
		if (count >= mode) {
			return true;
		}
		return false;
	}

	// 左上斜线五子连珠
	public static  boolean isLTFive(int x, int y, int mode) {
		int count = 1;
		for (int i = 1; Math.min(x, y) - i >= 0; i++) {
			if (ConstantUtil.ground[y][x] == ConstantUtil.ground[y - i][x - i]) {
				count++;
			} else {
				break;
			}
		}
		for (int j = 1; Math.max(x, y) + j < 9; j++) {
			if (ConstantUtil.ground[y][x] == ConstantUtil.ground[y + j][x + j]) {
				count++;
			} else {
				break;
			}
		}
		if (count >= mode) {
			return true;
		}
		return false;
	}

	// 右上斜线五子连珠
	public static boolean isRTFive(int x, int y, int mode) {
		int count = 1;
		for (int i = 1; x + i < 9 && y - i >= 0; i++) {
			if (ConstantUtil.ground[y][x] == ConstantUtil.ground[y - i][x + i]) {
				count++;
			} else {
				break;
			}
		}
		for (int j = 1; x - j >= 0 && y + j < 9; j++) {
			if (ConstantUtil.ground[y][x] == ConstantUtil.ground[y + j][x - j]) {
				count++;
			} else {
				break;
			}
		}
		if (count >= mode) {
			return true;
		}
		return false;
	}
	
	public static void initGroup(){
		int length =  ConstantUtil.ground.length;
		for (int i = 0; i < length; i++) {
			for(int j = 0;j < length;j++){
				ConstantUtil.ground[i][j] = 0;
			}
		}
		
	}
	
	
	
	
	/**
	 * 判断横轴是否有相邻的五个同色子
	 * @param x
	 * @param y
	 * @param points
	 * @return
	 */
	public static boolean checkHorizontal(int x, int y, List<Point> points) {
		// TODO Auto-generated method stub
		int count=1;
		for(int i=1;i<ConstantUtil.MAX_COUNT_IN_LINE;i++){
			if(points.contains(new Point(x-i,y))){
				count++;
			}else{
				break;
			}
		}
		if(count==ConstantUtil.MAX_COUNT_IN_LINE)return true;
		for(int i=1;i<ConstantUtil.MAX_COUNT_IN_LINE;i++){
			if(points.contains(new Point(x+i,y))){
				count++;
			}else{
				break;
			}
		}
		if(count==ConstantUtil.MAX_COUNT_IN_LINE)return true;
		return false;
	}
	/**
	 * 判断纵轴是否有相邻的五个同色子
	 * @param x
	 * @param y
	 * @param points
	 * @return
	 */
	public static boolean checkVertical(int x, int y, List<Point> points) {
		// TODO Auto-generated method stub
		int count=1;
		for(int i=1;i<ConstantUtil.MAX_COUNT_IN_LINE;i++){
			if(points.contains(new Point(x,y-i))){
				count++;
			}else{
				break;
			}
		}
		if(count==ConstantUtil.MAX_COUNT_IN_LINE)return true;
		for(int i=1;i<ConstantUtil.MAX_COUNT_IN_LINE;i++){
			if(points.contains(new Point(x,y+i))){
				count++;
			}else{
				break;
			}
		}
		if(count==ConstantUtil.MAX_COUNT_IN_LINE)return true;
		return false;
	}
	/**
	 * 判断左斜是否有相邻的五个同色子
	 * @param x
	 * @param y
	 * @param points
	 * @return
	 */
	public static boolean checkLeftDiagonal(int x, int y, List<Point> points) {
		// TODO Auto-generated method stub
		int count=1;
		for(int i=1;i<ConstantUtil.MAX_COUNT_IN_LINE;i++){
			if(points.contains(new Point(x-i,y+i))){
				count++;
			}else{
				break;
			}
		}
		if(count==ConstantUtil.MAX_COUNT_IN_LINE)return true;
		for(int i=1;i<ConstantUtil.MAX_COUNT_IN_LINE;i++){
			if(points.contains(new Point(x+i,y-i))){
				count++;
			}else{
				break;
			}
		}
		if(count==ConstantUtil.MAX_COUNT_IN_LINE)return true;
		return false;
	}
	/**
	 * 判断右斜是否有相邻的五个同色子
	 * @param x
	 * @param y
	 * @param points
	 * @return
	 */
	public static boolean checkRightDiagonal(int x, int y, List<Point> points) {
		// TODO Auto-generated method stub
		int count=1;
		for(int i=1;i<ConstantUtil.MAX_COUNT_IN_LINE;i++){
			if(points.contains(new Point(x-i,y-i))){
				count++;
			}else{
				break;
			}
		}
		if(count==ConstantUtil.MAX_COUNT_IN_LINE)return true;
		for(int i=1;i<ConstantUtil.MAX_COUNT_IN_LINE;i++){
			if(points.contains(new Point(x+i,y+i))){
				count++;
			}else{
				break;
			}
		}
		if(count==ConstantUtil.MAX_COUNT_IN_LINE)return true;
		return false;
	}
}
