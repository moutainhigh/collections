package com.gwssi.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwssi.dao.OADao;

@Service
public class OAService {

	@Autowired
	private OADao oaDao;

	//登记系统
	public int getDengJi(String userId) {
		HashMap param = new HashMap();
		param.put("userId", userId);
		Integer i = oaDao.getData1(param);
		Integer j = oaDao.getData2(param);
		int total = 0;
		if (i != null) {
			total += i;
		}
		if (j != null) {
			total += j;
		}
		return total;
	}
	
	
	//人事系统
	public int getRenShiXiTong(String userId){
		HashMap param = new HashMap();
		param.put("userId", userId);
		Integer j = oaDao.getData3(param);
		int total = 0;
		if(j==null){
			total  = 0;
		}else{
			total = j;
		}
		
		
		return total;
	}
	
	
	//固定资产系统
	public int getGDZC(String userId) {
		HashMap param = new HashMap();
		param.put("userId", userId);
		Integer i = oaDao.getData4(param);
		int total = 0;
		if (i != null) {
			total += i;
		}
		return total;
	}
	
}
