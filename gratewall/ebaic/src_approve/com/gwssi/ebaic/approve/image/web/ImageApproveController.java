package com.gwssi.ebaic.approve.image.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.ebaic.approve.image.service.ImageApproveService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.rodimus.util.ParamUtil;

/**
 * 图像审批意见。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Controller 
@RequestMapping("/approve/imageApprove")
public class ImageApproveController {
	
	@Autowired
	ImageApproveService imageApproveService;
	
	/**
	 * 保存图像审查审批意见。
	 */
	@RequestMapping("/saveResult")
	public void saveResult(OptimusRequest request, OptimusResponse response) throws OptimusException{
		String gid = ParamUtil.get("gid");
		String fId = ParamUtil.get("fId");
		String state = ParamUtil.get("state");
		String approveMsg = ParamUtil.get("approveMsg",false);
		imageApproveService.saveApproveResult(fId,gid,state, approveMsg);
		response.addAttr("result","success");
	}

}
