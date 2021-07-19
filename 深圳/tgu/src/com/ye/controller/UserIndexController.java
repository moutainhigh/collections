package com.ye.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ye.dc.dao.WCM_Asy_LogDao;
import com.ye.dc.pojo.WCM_Asy_Log;



@RestController
public class UserIndexController {
	@Autowired
	private WCM_Asy_LogDao zhcx_Asy_LogDao;
	
	
	
	@GetMapping("show")
	public List getUsers() {
		
		List list = zhcx_Asy_LogDao.getRecordList();
		
		return list;
	}
	
	
	@GetMapping("has")
	public List hasAdd() {
		
		List list = zhcx_Asy_LogDao.getHasAddList();
		
		return list;
	}
	
	
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("add")
	public Map add(String userid) {
		Map retMap = new HashMap();
		Map paramMap = new HashMap();
		paramMap.put("userid", userid);
		retMap.put("ret", "0");
		retMap.put("msg", "");
		WCM_Asy_Log log = zhcx_Asy_LogDao.findById(paramMap);
		try {
			if(log!=null) {
				log.setIsAdd("1");
				zhcx_Asy_LogDao.save(log);
				retMap.put("ret", "1");
				retMap.put("msg", "保存成功");
			}
		} catch (Exception e) {
			retMap.put("msg", "系统异常");
		}
		
		return retMap;
	}
}
