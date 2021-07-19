package com.gwssi.trs.service;

import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.trs.PageResult;
import com.trs.client.Date;

@Service
public class TrsService  extends BaseService{
	
	
	public PageResult querydata(){
        PageResult pageResult=null;
        //long totalCount, int pageNo, int pageSize, List items
		return pageResult;
	}
	
	public static void main(String[] args) {
		Date t = new Date();
		System.out.println(t.getFullDate());
		System.out.println(t.getFullTime());
		System.out.println(t.getTRSFormat());
		System.out.println(t.toString());
	}
}
