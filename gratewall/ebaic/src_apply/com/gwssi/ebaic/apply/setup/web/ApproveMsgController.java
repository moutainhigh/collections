package com.gwssi.ebaic.apply.setup.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.ebaic.apply.setup.service.ApproveMsgService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.rodimus.util.ParamUtil;

/**
 * 审批意见。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Controller
@RequestMapping("/apply/setup/approveMsg")
public class ApproveMsgController {
	
	@Autowired
	ApproveMsgService approveMsgService ;
	/**
	 * 最新一条审批意见。
	 * 
	 * @param requeest
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/lastest")
	public void lastest(OptimusRequest requeest,OptimusResponse response) throws OptimusException{
		String gid = ParamUtil.get("gid");
		Map<String,Object> msg = approveMsgService.getLastestOne(gid);
		//response.addResponseBody(JSON.toJSON(msg));
		response.addAttr("msg", msg);
	}
	
}
