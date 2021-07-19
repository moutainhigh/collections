package com.gwssi.yaoxie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.comselect.service.CaseShowService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.util.CaseSelectCacheUtile;
import com.gwssi.util.FreemarkerUtil;
import com.gwssi.yaoxie.service.YaoXieQyService;
import com.gwssi.yaoxie.service.YaoXieZZService;

import org.apache.commons.lang3.StringUtils;


@Controller
@RequestMapping("/yaoxieZZ")
public class YaoXieZhengZhaoController {

	@Resource
	private YaoXieZZService yaoxieShowService;

	
	
	@RequestMapping("/queryUserRolesByUserId")
	public void queryUserRolesByUserId(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		
		List<String> roleCodes  = yaoxieShowService.queryListUserRolesByUserId();
		if(roleCodes !=null && roleCodes.size() >0 && roleCodes.contains("ZHCX_SPQY_EXPORT")){
			System.out.println("success  ----"+"ZHCX_SPQY_EXPORT");
			resp.addAttr("msg", "success");
		}else{
			System.out.println("fail  ----"+"");
			resp.addAttr("msg", "fail");
		}
		//resp.addResponseBody(lstParams.toString());
	}
	
	
	
	
	
	
	
	@ResponseBody
	@RequestMapping("zhenZhaoType")
	public void getZhengZhaoType(OptimusRequest req, OptimusResponse resp) throws OptimusException{
		/*String zhengZhaoType="";
		if ((String)req.getAttr("type")==null) {
			zhengZhaoType=req.getParameter("type");
		}else{
			zhengZhaoType=(String)req.getAttr("type"); //关联字段
		}*/
		
		/*if(StringUtils.isEmpty(zhengZhaoType)){
		}*/
		resp.addTree("zhenZhaoType" ,yaoxieShowService.queryZhentZhaoType());
		
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 获取代码集
	 */
	@ResponseBody
	@RequestMapping("code_value")
	public void getCode_value(OptimusRequest req, OptimusResponse resp) throws OptimusException{
		HttpServletRequest request=req.getHttpRequest();
		String type="";
		String param = "";
		if ((String)req.getAttr("type")==null) {
			type=req.getParameter("type");
		}else{
			type=(String)req.getAttr("type"); //关联字段
		}
		
		if ((String)req.getAttr("param")==null) {
			param=req.getParameter("param");
		}else{
			param=(String)req.getAttr("param"); //关联字段
		}
		
		
		
		if(StringUtils.isEmpty(param)){
			resp.addTree(type ,yaoxieShowService.queryCode_value(type));
		}else{
			resp.addTree(type ,yaoxieShowService.queryCode_value(type,param));
		}
		
	}
	
	
	
	
	
	/**
	 * 药械证照查询列表
	 * 
	 * @param req
	 * @param resp
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/yaoxieZZQueryList")
	public void caseQueryList(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		Map<String, String> form = req.getForm("SpQyQueryListPanel");
		String flag = req.getParameter("flag");
		if (flag != null) {
			String count = yaoxieShowService.getYaoXieListCount(form);
			resp.addAttr("count", count);
		} else {
			List<Map> lstParams = yaoxieShowService.getYaoXieList(form, req.getHttpRequest());
			// System.out.println(lstParams.toString());
			resp.addGrid("SpQyQueryListGrid", lstParams);
		}
	}

	
	
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/yaoXieQyQueryDetail")
	public void SPQyQueryDetail(OptimusRequest req, OptimusResponse resp) throws OptimusException {
		String pripid = "";
		
		if ((String)req.getAttr("pripid")==null) {
			pripid=req.getParameter("pripid");
		}else{
			pripid=(String)req.getAttr("pripid"); //关联字段
		}
		List<Map> lstParams= yaoxieShowService.getSpQyDetail(pripid);//查询前100条信息 
		System.out.println(lstParams.toString());
		resp.addGrid("SPLicQueryListGrid",lstParams);
	}
	
	
	
	
	
	
	
	
	
	
}
