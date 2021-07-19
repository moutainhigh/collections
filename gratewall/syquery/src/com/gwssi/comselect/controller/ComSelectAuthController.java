package com.gwssi.comselect.controller;





import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;






import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.comselect.service.ComSelectAuthService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.controller.BaseController;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;


@Controller
@RequestMapping("/comselect")
public class ComSelectAuthController extends BaseController{
	
	@Autowired
	private ComSelectAuthService comSelectAuthService;
	

	
	@RequestMapping("getCurrOrg")
	@ResponseBody
	public Map toTest(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		Map<String,String> regorgMap=comSelectAuthService.findRegorg();
		if(StringUtils.isNotEmpty(regorgMap.get("codeindexCode"))){
			regorgMap.put("state", "1");
			regorgMap.put("message", "获取当前组织机构成功");
		}else{
			regorgMap.put("state", "-1");
			regorgMap.put("message", "无法查询寻到您的所属分局，请您确认在人事系统中你所属的组织机构");	
		}
		return regorgMap;
		
	}
	
	/**
	 * 处理map中的空值
	 * @param key
	 * @param map
	 * @return
	 */
	private String getMapValue(String key,Map map){
		if(null==map.get(key)){
			return "";
		}else{
			if(!StringUtils.isEmpty(map.get(key).toString())){
				return map.get(key).toString();
			}else{
				return "";
			}
		}
	}
	


	
}	