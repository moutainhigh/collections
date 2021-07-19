package com.gwssi.query.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.util.StringUtil;
import com.gwssi.query.service.HouseAddService;
import com.gwssi.sms.util.TmStringUtils;

@Controller
@RequestMapping("getHouse")
public class HouseAddController {

	
	@Resource
	private HouseAddService houseAddService;
	
	
	
	@RequestMapping("getHouseByKeyWord")
	public void getHouseList(OptimusRequest req, OptimusResponse resp)
			throws OptimusException, UnsupportedEncodingException {
		
		HttpServletRequest request = req.getHttpRequest();
		String keyWord = request.getParameter("keyword");
		if(TmStringUtils.isNotEmpty(keyWord)){
			keyWord=URLDecoder.decode(keyWord, "utf-8");
			List list = houseAddService.getHouseList(keyWord);
			resp.addAttr("list", list);
		}else{
			resp.addAttr("msg", "请输入关键字");
		}
		
	}
}
