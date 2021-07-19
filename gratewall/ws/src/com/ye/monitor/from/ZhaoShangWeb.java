package com.ye.monitor.from;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bo.domain.Stock;
import com.ye.monitor.YeZhaoShangUtil;

@Component
public class ZhaoShangWeb {
public static Logger log = LogManager.getLogger(ZhaoShangWeb.class);
	
	
	@Autowired
	private YeZhaoShangUtil yeZhaoShangUtil;
	
	
	public String  getStockInfo(Stock stock){
		String code = "";
		if(stock.getStockCode().indexOf("6")==0){
			code="SH%3A"+stock.getStockCode();
		}else{
			code="SZ%3A"+stock.getStockCode();
		}
		
		String str = yeZhaoShangUtil.httpPost(code);
		 JSONObject object = JSON.parseObject(str);
		JSONArray personList = object.getJSONArray("results");
		//System.out.println("====>" +object);
		//System.out.println("====>" +personList);
		//System.out.println(personList.get(0));
		String str2 = personList.getString(0);
		JSONArray arr = personList.getJSONArray(0);
		
		for (int i = 0; i < arr.size(); i++) {
			System.out.println(arr.get(i));
		}
		return str;
	}
}
