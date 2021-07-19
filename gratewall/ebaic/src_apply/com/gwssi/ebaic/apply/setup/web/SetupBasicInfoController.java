package com.gwssi.ebaic.apply.setup.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.ebaic.apply.setup.service.SetupBasicInfoService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.rodimus.util.ParamUtil;

@Controller
@RequestMapping("/apply/setup/basicinfo")
public class SetupBasicInfoController {
	@Autowired
	private SetupBasicInfoService setupBasicInfoService;
	
	/**
	 * 是否是退回修改,是则返回 1
	 */
	@RequestMapping("/hasStateOfBacked")
	@ResponseBody
	public String hasStateOfBacked(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String gid = ParamUtil.get("gid");
		boolean flag = setupBasicInfoService.hasStateOfBacked(gid);
		String res = null;
		if(flag){
			res = "1";
		}else{
			res = "0";
		}
		return res;
	}

}
