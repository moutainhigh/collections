package com.gwssi.ebaic.approve.censor.contrller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.ebaic.approve.censor.service.ApproveCensorService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.rodimus.indentity.domain.IdentityCardBO;
import com.gwssi.rodimus.util.ParamUtil;

/**
 * 受理业务包含 领取、分配、退回、受理
 * 
 * @author xupeng
 */
@Controller
@RequestMapping("/approve/censor")
public class ApproveCensorContrller{ 
	
	protected final static Logger logger = Logger.getLogger(ApproveCensorContrller.class);
	
	@Autowired
	private ApproveCensorService censorService;
	
	/**
	 * 辅助审查分配
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/saveAssign")
	public void saveAssign(OptimusRequest request, OptimusResponse response) throws OptimusException{
		String userId = ParamUtil.get("userId");
		List<String> reqIdList = (List<String>) request.getAttr("reqArr");
		censorService.saveAssign(userId,reqIdList);
		response.addAttr("result", "success");
	}
	
	/**
	 * 辅助审查领取
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/saveAdopt")
	public void saveAdopt(OptimusRequest request, OptimusResponse response) throws OptimusException{
		List<String> reqIdList = (List<String>) request.getAttr("reqArr");
		censorService.saveAdopt(reqIdList);
		response.addAttr("result", "success");
	}
	
	/**
	 * 辅助审查退回修改再分配
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/saveBackAssign")
	public void saveBackAssign(OptimusRequest request,OptimusResponse response) throws OptimusException{
		List<String> censorUserList = (List<String>) request.getAttr("censorUserArr");
		censorService.saveBackAssign(censorUserList);
		response.addAttr("result", "success");
	}
	
	/**
	 * 根据姓名和身份证号调用公安部接口验证身份证
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/showIdentityPic")
	public void showIdentityPic(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String name = ParamUtil.get("name");
		String cerNo = ParamUtil.get("cerNo");
		IdentityCardBO identityCardBO = censorService.getIdentityPic(name, cerNo);
		response.addAttr("result", identityCardBO);
	}
	
	
	/**
	 * 辅助审查-身份认证
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/showUpFilePic")
	public void censorIdentity(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String identityId = ParamUtil.get("identityId");
		List<Map<String,Object>> fileIdList = censorService.getIdentityfileUrlById(identityId);
		response.addAttr("result", fileIdList);
		
	}
	
}
