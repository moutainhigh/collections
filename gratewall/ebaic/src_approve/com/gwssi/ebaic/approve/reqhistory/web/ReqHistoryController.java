package com.gwssi.ebaic.approve.reqhistory.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.gwssi.rodimus.util.ParamUtil;
import com.gwssi.ebaic.approve.reqhistory.service.ReqHistoryService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;

/**
 * 审批历史查询。
 * 
 * @author xupeng
 */
@Controller
@RequestMapping("/approve/reqhistory")
public class ReqHistoryController {
	@Autowired
	private ReqHistoryService reqHistoryService;
	
	
	/**
	 * 查询上传材料。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws OptimusException
	 */
	@RequestMapping("/getReqPicInfo")
	@ResponseBody
	public void getReqPicInfo(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String gid = ParamUtil.get("gid");
		Map<String, Object> paramMap = ParamUtil.getParams(request);
		List<Map<String,Object>> list = reqHistoryService.getReqPicInfo("CP_SETUP_1100",gid,paramMap);
		response.addResponseBody(JSON.toJSONString(list));
	}
	
	/**
	 * 得到当前数据的处理状态
	 * state,'2','待分配','3','辅助审查通过','4','退回修改',8,'核准通过','9','驳回','15','已分配','16','审核中
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/getReqState")
	public void getReqState(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String gid = ParamUtil.get("gid");
		String state = reqHistoryService.getReqState(gid);
		response.addResponseBody(JSON.toJSONString(state));
	}

}
