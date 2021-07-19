package com.gwssi.common.rodimus.detail.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.gwssi.common.rodimus.detail.core.DetailDataManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

@Controller
@RequestMapping("/detail")
public class DetailController {
	
	@RequestMapping("/data")
	public void detailedInfo(OptimusRequest request, OptimusResponse response)throws OptimusException{
		JSONObject joDetailData = DetailDataManager.getData(request.getHttpRequest());
		String strDetailData = joDetailData.toJSONString();
		response.addResponseBody(strDetailData);
	}
}
