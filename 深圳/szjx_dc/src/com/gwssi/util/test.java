package com.gwssi.util;

public class test {
	public static void main(String[] args) {
		test t = new test();
        System.out.println(t.getClass());
        System.out.println(t.getClass().getClassLoader());
        System.out.println(t.getClass().getClassLoader().getResource(""));
        System.out.println(t.getClass().getClassLoader().getResource("/ftl"));//null
	}
}
