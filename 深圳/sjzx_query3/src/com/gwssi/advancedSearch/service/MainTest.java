package com.gwssi.advancedSearch.service;

public class MainTest {

	public static void main(String[] args) {
		String str = "123_";
		try {
		    int b = Integer.valueOf(str).intValue();
		    System.out.println(b);
		} catch (NumberFormatException e) {
		    e.printStackTrace();
		}
	}
}
