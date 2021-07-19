package com.gwssi.trs;

import java.util.Map;

import com.gwssi.optimus.core.exception.OptimusException;

public class test {

	public static void main(String[] args){
		PageDataUtil PageDataUtil =new PageDataUtil();
		try {
			Map map = PageDataUtil.pagesDate(1, 10, "深圳", "");
			System.out.println(map.toString());
		} catch (OptimusException e) {
			e.printStackTrace();
		}
	}
}
