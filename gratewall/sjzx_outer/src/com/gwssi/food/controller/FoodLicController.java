package com.gwssi.food.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.entSelect.util.IPUtil;
import com.gwssi.food.service.FoodLicService;
import com.gwssi.food.service.SafeFoodVisitService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

@Controller
@RequestMapping("/foodLic")
public class FoodLicController {
	
	@Autowired
	private FoodLicService foodLicService;
	@Autowired
	private SafeFoodVisitService safeService;
	
	@RequestMapping("/foodList")
	public void foodList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String entname = req.getParameter("entname").trim();
		String licno = req.getParameter("licno").trim();
		//记录IP
		String ip = IPUtil.getIpPropAddr(req.getHttpRequest());
		int count = safeService.count(ip);
		if(count<=19){
			List<Map> res = foodLicService.queryfoodLicList(entname,licno);
			if(res == null){
				resp.addAttr("data", "请重新查询");
			}else{
				safeService.saveIPLog(ip);
				resp.addAttr("data", res);
			}	
		}else{
			resp.addAttr("data", "您查询次数过多，请稍后再查！");
		}
		
	}
	
	@RequestMapping("/foodDetail")
	public void foodDetail(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String foodid = req.getParameter("foodid").trim();
		String lictype = req.getParameter("lictype").trim();
		List<Map> res = foodLicService.queryfoodLicDetail(foodid,lictype);
		if(res == null){
			resp.addAttr("data", "请重新查询");
		}else{
			resp.addAttr("data", res);
		}
	}
	

}
