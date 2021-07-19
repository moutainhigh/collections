package com.gwssi.ebaic.approve.approval.contrller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwssi.ebaic.approve.approval.service.ApprovalService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.util.JSON;
import com.gwssi.rodimus.util.ParamUtil;

/**
 * 核准环节分配、领取。
 * 
 * @author shigaozhan
 */
@Controller
@RequestMapping("/approve/approval")
public class ApprovalContrller  { 
	protected final static Logger logger = Logger.getLogger(ApprovalContrller.class);
	
	@Autowired
	private ApprovalService approvalService;//service层操作对象
	/**
	 * 核准环节分配给审核人员。
	 * 
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@SuppressWarnings("unchecked")
    @RequestMapping("/saveAssign")
    public void saveAssign(OptimusRequest request, OptimusResponse response) throws OptimusException{
	    //分配审核人员的userId
        String userId = (String) request.getAttr("userId");
        List<String> requisitionIdlist = (List<String>) request.getAttr("reqArr");
        approvalService.saveAssign(userId,requisitionIdlist);
        response.addAttr("result", "success");
    }
	
	/**
     * 核准环节 退回修改再分配。
     * 
     * @param request
     * @param response
     * @throws OptimusException
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/backAgainAssign")
    public void backAgainAssign(OptimusRequest request, OptimusResponse response) throws OptimusException{
        List<String> userIdArr = (List<String>) request.getAttr("userIdArr");
        approvalService.saveBackAgainAssign(userIdArr);
        //数据返回
        response.addAttr("result", "success");
    }
	
	/**
     * 核准环节审核人员自己领取。
     * 
     * @param request
     * @param response
     * @throws OptimusException
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/doReceive")
    public void doReceive(OptimusRequest request, OptimusResponse response) throws OptimusException{
        List<String> requisitionIdlist = (List<String>) request.getAttr("requisitionArr");
        approvalService.saveAdopt(requisitionIdlist);
        //数据返回
        response.addAttr("result", "success");
    }
    
    /**
     * 判断当前数据是否为核准
     * @param request
     * @param response
     * @throws OptimusException
     */
    @RequestMapping("/checkIsHz")
    public void checkIsHz(OptimusRequest request,OptimusResponse response) throws OptimusException{
    	String gid = ParamUtil.get("gid");
    	boolean flag = approvalService.checkIsHz(gid);
    	response.addResponseBody(JSON.toJSON(flag));
    }
    
    
    
    /**
     * 在我的任务退回待核准数据至分配领取列表
     * @param request
     * @param response
     * @throws OptimusException
     */
    @RequestMapping("/backAssign")
    public void backAssign(OptimusRequest request,OptimusResponse response) throws OptimusException{
    	String gid = ParamUtil.get("gid");
    	approvalService.backAssign(gid);
    	response.addResponseBody(JSON.toJSON("success"));
    }
    
}
