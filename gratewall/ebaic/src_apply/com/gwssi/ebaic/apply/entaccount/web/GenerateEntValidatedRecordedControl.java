package com.gwssi.ebaic.apply.entaccount.web;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.ebaic.apply.entaccount.service.GenerateEntValidatedRecordedService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.rodimus.util.ParamUtil;


//ye
//用户调用工商局的企业认证时，自动利用Torch对企业认证记录表插入一条句句
@RequestMapping("/apply/saveEntAccount")
@Controller("generateEntValidatedRecordedControl")
public class GenerateEntValidatedRecordedControl {
	
	
	//推荐采用这个注入的方式，让spring 可以轻易的和java注解整合和移植
	@Resource
	private GenerateEntValidatedRecordedService geService;

	
	@RequestMapping("/entValidate")
	public void save(OptimusRequest request,OptimusResponse response) throws OptimusException{
		System.out.println(geService);
		System.out.println(1);
		
		/*
		String authCode = ParamUtil.get("authCode",false);
		String entName = ParamUtil.get("entName",false);
		String regNo = ParamUtil.get("regNo",false);
		String unicCode = ParamUtil.get("unicCode",false);
		*/
		
		
		String  operCode = ParamUtil.get("operCode");
		String  authCode = ParamUtil.get("authCode");
		String  entName = ParamUtil.get("entName");
		//String str = new String(entName.getBytes("ISO-8858-1","UTF-8"));
		String  regNo = ParamUtil.get("regNo");
		String  unicCode = ParamUtil.get("unicCode");
		geService.save(operCode,authCode, entName, regNo, unicCode);
		
		response.addAttr("result", "success");
		
	}
	
	
}
