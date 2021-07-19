package com.gwssi.ad;

import java.util.Map;

public class PersonSearchUtil {

	public static void main(String[] args) {
		AdDao ad =new AdDao();
		//Person p=ad.searchOnePersion("changruan");
		Person p2=ad.searchOnePersion("changruan");
		//System.out.println("changruan====> " +p.getDistinguishedName());
		System.out.println("changruan  ===>" + p2.getDistinguishedName());
		
		
	/*	Person p2=ad.searchOnePersion("zhouxl");
		//Map map = ad.disablePerson(p2);
		System.out.println("zhouxl====> " +p2.getDistinguishedName());
		Map map = ad.disablePerson(p2);
		System.out.println(map);
		*/
		
		
	  /*  Person p = new Person();
	    p.setCn("吕");
	    p.setGiveName("丽珠");
	    p.setDisplayName("吕丽珠");
	   // p.setOu("罗湖局注册科,罗湖局内设机构,罗湖局,委派出机构,深圳市市场和质量监督管理委员会");
*/	  
	    
	    
	    /*Person p2=ad.searchOnePersion("weijunxiao");
	    p.setOu(p2.getOu());
		p.setDistinguishedName(p2.getDistinguishedName());
		ad.createOnePerson(p);*/
		
	
	/*
		
		<ret>CN=周小兰,OU=01 办公室,OU=0063 南山局,OU=用户,DC=szaic,DC=gov,DC=cn</ret>

		
		*/
	}

}
