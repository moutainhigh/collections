package com.gwssi.ebaic.approve.doapprove.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwssi.ebaic.approve.doapprove.service.DoApproveService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.util.JSON;
import com.gwssi.rodimus.util.ParamUtil;
import com.gwssi.rodimus.validate.msg.ValidateMsg;

/**
 * 执行核准操作。
 * 
 * 核准前需要检查是否修改过。如果修改过不允许提交。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Controller 
@RequestMapping("/approve/doapprove")
public class DoApproveContrller{ 
	
	protected final static Logger logger = Logger.getLogger(DoApproveContrller.class);
	
	@Autowired
	DoApproveService doApproveService ;
    /**
     * 1. 搬库  申请到审批。
     * @param request
     * @param response
     * @throws OptimusException
     */
    @RequestMapping("/rulePrepare")
    public void rulePrepare(OptimusRequest request, OptimusResponse response) throws OptimusException{
        String gid = ParamUtil.get("gid");
        //	搬库
        doApproveService.rulePrepare(gid);
        response.addAttr("result","success");
    }
    
	/**
     * 2. 审批 跑规则。
     * @param request
     * @param response
     * @throws OptimusException
     */
    @RequestMapping("/runRule")
    public void runRule(OptimusRequest request, OptimusResponse response) throws OptimusException{
        String gid = ParamUtil.get("gid");
        ValidateMsg ret = doApproveService.runRule(gid);
        if(ret==null || ret.isEmpty()){
        	response.addAttr("result","success");
        	return ;
        }
    }
    
    /**
     * 3. 核准 提交操作。
     * @param request
     * @param response
     * @throws OptimusException
     */
    @RequestMapping("/submit")
    public void submit(OptimusRequest request, OptimusResponse response) throws OptimusException{
        String gid = ParamUtil.get("gid");
        doApproveService.submit(gid);
    	response.addAttr("result","success");
    }
    
    /**
	 * 4. 核准环节-点击核准  生成文书并且返回文书哈希码给客户端
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doc")
	@ResponseBody
	public String docAndHash(OptimusRequest request,OptimusResponse response)throws OptimusException{
		String gid = ParamUtil.get("gid");
		String hashCode = doApproveService.docAndHash(gid,request.getHttpRequest());
		return hashCode;
	}
	
	/**
	 * 签名 。
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/sign")
	public void sign(OptimusRequest request,OptimusResponse response)throws OptimusException{
		String gid = ParamUtil.get("gid");
		doApproveService.sign(gid,request.getHttpRequest());
		response.addAttr("result", "success");
	}
	
   /** 特殊通道 申请。
     * @param request
     * @param response
     * @throws OptimusException
     */
    @RequestMapping("/applySpecial")
    public void applySpecial(OptimusRequest request, OptimusResponse response) throws OptimusException{
        String gid = ParamUtil.get("gid");
        String ruleStepIds = ParamUtil.get("ruleStepIds");
        String resultInfo = doApproveService.applySpecial(gid,ruleStepIds);
        response.addResponseBody(JSON.toJSON(resultInfo));
    }
	
    /**
     * 执行特殊通道。
     * @param request
     * @param response
     * @throws OptimusException
     */
    @RequestMapping("/specialGalleryPass")
    public void specialGalleryPass(OptimusRequest request, OptimusResponse response) throws OptimusException{
        String gid = ParamUtil.get("gid");
        String ruleStepIds = ParamUtil.get("ruleStepIds");
        String resultInfo = doApproveService.specialGalleryPass(gid,ruleStepIds);
        response.addResponseBody(JSON.toJSON(resultInfo));
    }
	
}
