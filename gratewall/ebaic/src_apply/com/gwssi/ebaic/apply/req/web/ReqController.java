package com.gwssi.ebaic.apply.req.web;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.ebaic.apply.req.service.ReqService;
import com.gwssi.ebaic.apply.util.ReqStateMappingUtil;
import com.gwssi.ebaic.domain.SysmgrIdentityBO;
import com.gwssi.ebaic.domain.TPtYhBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.util.JSON;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.step.StepUtil;
import com.gwssi.rodimus.util.HttpSessionUtil;
import com.gwssi.rodimus.util.ParamUtil;

/**
 * 业务操作
 * 
 * @author lxb
 */
@Controller
@RequestMapping("/req")
public class ReqController {
	
	@Autowired
	ReqService reqService; 
	
	
	@RequestMapping("/stopReq")
	public void stopReq(OptimusRequest requeest,OptimusResponse response) {
		String gid = ParamUtil.get("gid");
		String reason = ParamUtil.get("reason");
		reqService.stopReq(gid,reason);		
	}
	
	
	@RequestMapping("/deleteReq")
	public void deleteReq(OptimusRequest requeest,OptimusResponse response) {
		String gid = ParamUtil.get("gid");
		reqService.deleteReq(gid);
	}
	@RequestMapping("/setAuthType")
	public void setAuthType(OptimusRequest requeest,OptimusResponse response) {
		String gid = ParamUtil.get("gid");
		String authType =ParamUtil.get("authType");
		reqService.setAuthType(gid,authType);
	}
	
	/**
	 * 确定取照方式：自提
	 * @param requeest
	 * @param response
	 */
	@RequestMapping("/sureSelfGet")
	public void sureSelfGet(OptimusRequest requeest,OptimusResponse response) {
		String gid = ParamUtil.get("gid");
		String licenseWay =ParamUtil.get("licenseWay");
		reqService.sureSelfGet(gid,licenseWay);
	}
	
	/**
	 * 打印领照单功能
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/getPrintParam")
	public String getPrintParam(HttpServletRequest request,HttpServletResponse response){
		String gid = request.getParameter("gid");
		HashMap<String,Object> params = reqService.getPrintParam(gid);
		return JSON.toJSON(params);
	}
	
	/**
	 * 判断是否该企业账号有选择取照方式  是否有取照编码
	 */
	@RequestMapping
	public void getPrintFlag(OptimusRequest requeest,OptimusResponse response){
		String gid = ParamUtil.get("gid");
		String flag = reqService.getPrintFlag(gid);
		response.addResponseBody(flag);
	}
	
	/**
	 * 查询帐号业务待确认列表
	 * @param request
	 * @param response
	 * @return
	 * @throws OptimusException 
	 */
	@RequestMapping("/getListForConfirm")
	public void getListForConfirm(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String userType = HttpSessionUtil.getLoginUserType();
		if(StringUtils.isBlank(userType)){
			throw new EBaicException("登录超时，请重新登录");
		}
		List<Map<String,Object>> list = null;
		if(OptimusAuthManager.LOGIN_USER_TYPE_ENT.equals(userType)){
			SysmgrIdentityBO ent = HttpSessionUtil.getEntUser();
			if(ent==null){
				throw new EBaicException("登录超时，请重新登录");
			}
			list = reqService.queryReqListByCp(ent.getCerNo());
			//将列表中的操作项查询出来
			StepUtil.getList("appy_person_account_list", list);
		}else if (OptimusAuthManager.LOGIN_USER_TYPE_PERSON.equals(userType)){
			TPtYhBO user = HttpSessionUtil.getCurrentUser();
			if(user==null){
				throw new EBaicException("登录超时，请重新登录");
			}
			list = reqService.queryReqListByPerson(user.getCerType(), user.getCerNo());
			//将列表中的操作项查询出来
			StepUtil.getList("appy_person_account_list", list);
		}
		
		//处理列表中的状态，转化为文本
		Iterator<Map<String, Object>> iterator = list.iterator();
		while(iterator.hasNext()){
			Map<String, Object> map = iterator.next();
			ReqStateMappingUtil.changeStateText(map);
		}
		response.addGrid("list", list);
	}
	/**
	 * 查询pdf文件信息的相对路径
	 */
	@RequestMapping("/getPdfRelativePath")
	public void getPdfRelativePath(OptimusRequest requeest,OptimusResponse response) throws OptimusException{
		String gid = ParamUtil.get("gid");
		Map<String,Object> ret = reqService.getPdfRelativePath(gid);
		response.addAttr("result", ret);
	}
	
}
