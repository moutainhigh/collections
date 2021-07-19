package com.report.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.report.core.Business;

@RestController
public class RunSqlContoller {

	@Autowired
	private Business bus;
	
	@RequestMapping("doQuery")
	public Map ceateSQL(String time) {
		Map map = new  HashMap();
		String msg ="缺少时间参数";
		if(!StringUtils.isEmpty(time)) {
			 msg ="请求已提交，后台处理中，请稍后过来下载....";
			bus.doQuery(time);
			
		}
		
		map.put("msg", msg);
		return map;
	}
	
	
	
	
	@RequestMapping("lists")
	public void getList() {
		
	}
}
