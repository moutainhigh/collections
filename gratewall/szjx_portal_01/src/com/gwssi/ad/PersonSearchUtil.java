package com.gwssi.ad;

public class PersonSearchUtil {

	public static void main(String[] args) {
		AdDao ad =new AdDao();
		Person p=ad.searchOnePersion("chaihw");
		System.out.println(p);
	}

}
