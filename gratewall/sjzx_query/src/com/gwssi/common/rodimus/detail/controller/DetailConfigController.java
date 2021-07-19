package com.gwssi.common.rodimus.detail.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.gwssi.common.rodimus.detail.core.DetailDataManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

@Controller
@RequestMapping("/detail")
public class DetailConfigController {
	
	@RequestMapping("/test")
	public void detailedInfo(OptimusRequest req, OptimusResponse res)throws OptimusException{
		String code = req.getParameter("code");
		String id = req.getParameter("id");
		Map<String,Object> map= new HashMap<String,Object>();
		map.put("id", id);//主键信息
		JSONObject ret = DetailDataManager.getData(code,map);
		res.addAttr("configData", ret);
	}
}
