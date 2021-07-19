package com.gwssi.dw.runmgr.webservices.localtax.in.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SWDJComparator implements Comparator
{

	public int compare(Object o1, Object o2)
	{
		SWDJInfo info1 = (SWDJInfo)o1;
		SWDJInfo info2 = (SWDJInfo)o2;
		return info1.getBGSJ().compareTo(info2.getBGSJ());
	}

	public static void main(String[] args){
		List list = new ArrayList();
		
		SWDJInfo info1 = new SWDJInfo();
		info1.setBGSJ("20080101");
		list.add(info1);
		info1 = new SWDJInfo();
		info1.setBGSJ("20080901");
		list.add(info1);
		info1 = new SWDJInfo();
		info1.setBGSJ("20080701");
		list.add(info1);
		info1 = new SWDJInfo();
		info1.setBGSJ("20080501");
		list.add(info1);
		info1 = new SWDJInfo();
		info1.setBGSJ("20080801");
		list.add(info1);
		info1 = new SWDJInfo();
		info1.setBGSJ("20080301");
		list.add(info1);
		info1 = new SWDJInfo();
		info1.setBGSJ("20070901");
		list.add(info1);
		info1 = new SWDJInfo();
		info1.setBGSJ("20090901");
		list.add(info1);
		
		Collections.sort(list, new SWDJComparator());
		for(int i=0;i<list.size();i++){
			SWDJInfo temp = (SWDJInfo)list.get(i);
			System.out.println(temp.getBGSJ());
		}
	}
}
