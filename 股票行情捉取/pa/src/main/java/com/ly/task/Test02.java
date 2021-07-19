package com.ly.task;

public class Test02 {
	/*public static void main(String[] args) {
		int size = 3583;
		int commitCountEveryTime = 50;
		int commitCount = (int) Math.ceil(size / (double) commitCountEveryTime);
		//System.out.println(commitCount);
		int start, stop;
		for (int i = 0; i < commitCount; i++) {
			start = i * commitCountEveryTime;
			stop = Math.min(i * commitCountEveryTime + commitCountEveryTime - 1, size - 1);
			System.out.println(start + "  "  + stop);
		}
	}*/
	
	public static void main(String[] args) {
	/*	int size = 100;
		int rows = 10;
		
		int step = size/rows;
		System.out.println(step);
		for (int i = 1; i <= step; i++) {
			System.out.println(i);
		}*/
		
		
		long time = 2697102;
		
		double i = time/1000/60.0/60.0;
		System.out.println(i);
	}
}
