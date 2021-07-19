package com.gwssi.util;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gwssi.application.webservice.service.XmlToMapUtil;
import com.gwssi.comselect.model.EntSelectQueryBo;






public class entTEST { 
	public static void main(String[] args){
		EntSelectQueryBo bo = new EntSelectQueryBo();
		bo.setEstdate_start("2000-01-01");
		bo.setEstdate_end("2015-01-01");
		String[] entname_term  = new String[2];
		entname_term[0]="include";
		entname_term[1]="include";
		bo.setEntname_term(entname_term);
		 String[] entname= new String[2] ;//企业名称
		 entname[0]="深圳";
		 entname[1]="食品";	 
		 bo.setEntname(entname);
		 
        Map<String, Object> map = new HashMap();  
     
	     JSONObject json = (JSONObject) JSON.toJSON(bo);  
	       
			System.out.println(json.toJSONString());
       map.put("bo", json.toJSONString());
        
		 String param = XmlToMapUtil.map2Dom(map);
		 System.out.println("参数 值：" + param);
	}

}
