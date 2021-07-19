package com.gwssi.house;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.house.service.HouseFeedBackService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

@Controller
@RequestMapping("houseFeed")
public class HouseFeedBackController {

	@Autowired
	private HouseFeedBackService houseFeedBackService;

	
	
	@ResponseBody
	@RequestMapping("code_value")
	public void getCode_value(OptimusRequest req, OptimusResponse resp) throws OptimusException{
		HttpServletRequest request=req.getHttpRequest();
			resp.addTree( null,houseFeedBackService.getCode());
		
	}
	
	@RequestMapping("houseList")
	public void getInfo(OptimusRequest req, OptimusResponse resp) throws OptimusException, IOException {
		
		Map<String, String> form = req.getForm("YRQueryListPanel");
		String flag = req.getParameter("flag");
		if(flag !=null){
			String count= houseFeedBackService.getHouseFeedCount(form);
			resp.addAttr("count", count);
		}else {
			List<Map> lstParams= houseFeedBackService.getHouseFeed(form,req.getHttpRequest());
			
			System.out.println(form);
			//System.out.println(lstParams.toString());
			resp.addGrid("YRQueryListGrid",lstParams);
		}
	}
}
