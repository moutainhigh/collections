//package com.gwssi.ebaic.approve.censor.contrller;
//
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.gwssi.ebaic.approve.censor.service.ApproveIdentityService;
//import com.gwssi.optimus.core.exception.OptimusException;
//import com.gwssi.optimus.core.web.event.OptimusRequest;
//import com.gwssi.optimus.core.web.event.OptimusResponse;
//import com.gwssi.rodimus.util.ParamUtil;
//
///**
// * 辅助审查-身份认证
// * @author xupeng
// *
// */
//@Controller
//@RequestMapping("/approve/censor")
//public class ApproveIndentityContrller {
//	
//	@Autowired
//	private ApproveIdentityService approveIdentityService;
//	
//	/**
//	 * 获取附件图片地址
//	 * @param request
//	 * @param response
//	 * @throws OptimusException
//	 */
//	@RequestMapping("/getIdentityUpfile")
//	public void getIdentityUpfile(OptimusRequest request,OptimusResponse response) throws OptimusException{
//		String identityId = ParamUtil.get("identityId");
//		List<Map<String,Object>> list = approveIdentityService.getIdentityUpfile(identityId);
//		response.addAttr("result",list);
//	}
//	
//	/**
//	 * 对辅助审查-身份认证 进行认证
//	 * @param request
//	 * @param response
//	 * @throws OptimusException
//	 */
//	@RequestMapping("/doIdentity")
//	public void doIdentity(OptimusRequest request,OptimusResponse response) throws OptimusException{
//		String identityId = ParamUtil.get("identityId");
//		String appUserId = ParamUtil.get("appUserId");
//		String approveMsg = ParamUtil.get("approveMsg");
//		String checkSate = ParamUtil.get("checkSate");
//		String gid = ParamUtil.get("gid");
//		approveIdentityService.doIdentity(identityId, appUserId, approveMsg, checkSate,gid);
//		response.addAttr("result", "success");
//	}
//	
//	
//	/**
//	 * 对单张图片进行审批操作
//	 * @param request
//	 * @param response
//	 * @throws OptimusException
//	 */
//	public void doIdentityToPic(OptimusRequest request,OptimusResponse response) throws OptimusException{
//		String pictureId = ParamUtil.get("pictureId");
//		String approveMsg = ParamUtil.get("approveMsg");
//		String gid = ParamUtil.get("gid");
//		approveIdentityService.doIdentityToPic(pictureId, approveMsg,gid);
//		response.addAttr("result", "success");
//	}
//
//}
