package com.gwssi.ebaic.approve.process.contrller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.gwssi.ebaic.approve.process.service.ProcessService;
import com.gwssi.ebaic.approve.util.ApproveAuthUtil;
import com.gwssi.ebaic.common.service.BjcaService;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.optimus.core.web.event.OptimusResponse;
import com.gwssi.optimus.util.JSON;
import com.gwssi.rodimus.doc.v1.DocUtil;
import com.gwssi.rodimus.rule.RuleUtil;
import com.gwssi.rodimus.util.ParamUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.rodimus.validate.msg.ValidateMsg;
import com.gwssi.rodimus.validate.msg.ValidateMsgEntity;


/**
 * 审批。
 * 
 * @author shigaozhan
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Controller 
@RequestMapping("/approve/process")
public class ProcessContrller{ 
	protected final static Logger logger = Logger.getLogger(ProcessContrller.class);
	
	@Autowired
	private ProcessService processService;
	@Autowired
	private BjcaService bjcaService;
	
	/**
     * 审批内容页面，保存单一字段。
     * 
     * @param request
     * @param response
     * @throws OptimusException
     */
    @RequestMapping("/saveEntField")
    public void saveEntField(OptimusRequest request, OptimusResponse response) throws OptimusException{
        String gid = ParamUtil.get("gid");
        String saveType = ParamUtil.get("saveType");
        JSONArray fieldObjectArray = (JSONArray) request.getAttr("fieldObject");
        processService.saveEntField(gid,fieldObjectArray,saveType);
        response.addAttr("result", "success");
    }
    
    /**
     * 对经营范围保存后进行刷新
     * @param request
     * @param response
     * @return
     * @throws OptimusException 
     */
    @RequestMapping("/getEntBusinessScope")
    public void getEntBusinessScope(OptimusRequest request,OptimusResponse response) throws OptimusException{
    	String gid = ParamUtil.get("gid");
    	String businessScope = processService.getEntBusinessScope(gid); 
    	response.addAttr("data", businessScope);
    }
	
    /**
     * 根据gid判断当前环节是审查还是核准。
     * 
     * @param request
     * @param response
     * @throws OptimusException
     */
    @RequestMapping("/canApprove")
    public void canApprove(OptimusRequest request,OptimusResponse response) throws OptimusException{
        String gid = ParamUtil.get("gid");
        boolean canApprove = ApproveAuthUtil.canApproved(gid);
        response.addResponseBody(JSON.toJSON(canApprove));
    }
    
    
    /**
     * 如果为核准，且当前用户和辅助审查人员为同一人，则返回false，否则返回true
     * @param request
     * @param response
     * @throws OptimusException
     */
    @RequestMapping("/checkIsOnePerson")
    public void checkIsOnePerson(OptimusRequest request,OptimusResponse response) throws OptimusException{
    	String gid = ParamUtil.get("gid");
    	boolean flag = processService.checkIsOnePerson(gid);
    	response.addResponseBody(JSON.toJSON(flag));
    }
    
    
    /**
     * 查询当前业务需要显示哪些页签。
     * @param request
     * @param response
     * @throws OptimusException
     */
    @RequestMapping("/getTabNames")
    public void getTabNames(OptimusRequest request,OptimusResponse response) throws OptimusException{
        String gid = ParamUtil.get("gid");
        String tabNames = processService.getTabNames(gid);
        response.addResponseBody(JSON.toJSON(tabNames));
    }
    /**
     * 辅助审查，核准 提交操作。
     * @param request
     * @param response
     * @throws OptimusException
     */
    @RequestMapping("/execSubmit")
    public void execSubmit(OptimusRequest request, OptimusResponse response) throws OptimusException{
        String gid = ParamUtil.get("gid");
        // approve - 核准，examine - 辅助审查
        String submitType = ParamUtil.get("pageType");
        processService.execSubmit(gid,submitType);
        response.addResponseBody(JSON.toJSON("success"));
    }
    /**
     * 提交前获取一些校验信息。
     * 
     * @param request
     * @param response
     * @throws OptimusException
     */
    @RequestMapping("/getSubmitInfo")
    public void getSubmitInfo(OptimusRequest request, OptimusResponse response) throws OptimusException{
        String gid = ParamUtil.get("gid");
        String entType = ParamUtil.get("entType");
        Map<String,Object> returnInfo = processService.getSubmitInfo(gid,entType);
        response.addResponseBody(JSON.toJSON(returnInfo));
    }
    /**
     * 审批 跑规则。
     * @param request
     * @param response
     * @throws OptimusException
     */
    @RequestMapping("/exeRule")
    public void exeRule(OptimusRequest request, OptimusResponse response) throws OptimusException{
        String gid = ParamUtil.get("gid");
        ValidateMsg ret = RuleUtil.getInstance("approveDataSource").runApp("ebaic_setup_approve", gid);
        List<ValidateMsgEntity> errorList = new ArrayList<ValidateMsgEntity>();
        if(ret!=null) {
            errorList = ret.getAllMsg();
            if(errorList!=null&&errorList.size()>0) {
                for (ValidateMsgEntity validateMsgEntity : errorList) {
                    if("0".equals(validateMsgEntity.getType())) {
                        validateMsgEntity.setType("提示");
                    }
                    if("1".equals(validateMsgEntity.getType())) {
                        validateMsgEntity.setType("锁定");
                    }
                }
            }
        }
        response.addGrid("interceptRuleGrid", errorList);
    }
    /**
     * 搬库  申请到审批。
     * @param request
     * @param response
     * @throws OptimusException
     */
    @RequestMapping("/copyData")
    public void copyData(OptimusRequest request, OptimusResponse response) throws OptimusException{
        String gid = ParamUtil.get("gid");
        processService.copyData(gid);//搬库
        response.addResponseBody(JSON.toJSON("success"));
    }
    
    /**
	 * 文书预览（以图片形式）。
	 * 
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/doc")
	public void doPreview(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String gid = ParamUtil.get("gid");
		Map<String,List<Map<String,Object>>> ret = processService.doc(gid);
		response.addAttr("result", ret);
	}
	 /**
     * 特殊通道 申请。
     * @param request
     * @param response
     * @throws OptimusException
     */
    @RequestMapping("/createTstd")
    public void createTstd(OptimusRequest request, OptimusResponse response) throws OptimusException{
        String gid = ParamUtil.get("gid");
        String ruleStepIds = ParamUtil.get("ruleStepIds");
        String resultInfo = processService.createTstd(gid,ruleStepIds);
        response.addResponseBody(JSON.toJSON(resultInfo));
    }
    /**
     * 执行特殊通道。
     * @param request
     * @param response
     * @throws OptimusException
     */
    @RequestMapping("/executeTstdRules")
    public void executeTstdRules(OptimusRequest request, OptimusResponse response) throws OptimusException{
        String gid = ParamUtil.get("gid");
        String ruleStepIds = ParamUtil.get("ruleStepIds");
        String resultInfo = processService.executeTstdRules(gid,ruleStepIds);
        response.addResponseBody(JSON.toJSON(resultInfo));
    }
    
	/**
	 * 核准环节-点击核准  生成文书并且返回文书哈希码给客户端
	 * @param request
	 * @param response
	 */
	@RequestMapping("/buildDocHash")
	@ResponseBody
	public Map<String,Object> buildDocHash(OptimusRequest optimusRequest,OptimusResponse response)throws OptimusException{
		// 文书生成
        String docCode = "setup_1100";
		String gid = ParamUtil.get("gid");
		String phyPdfFilePath = DocUtil.buildDoc(docCode, gid);
		//String phyPdfFilePath = "d:\\pdf\\ArchiveElecFile.pdf";
		if(StringUtil.isBlank(phyPdfFilePath)){
        	throw new OptimusException("生成文书失败！");
        }
		HttpServletRequest request = optimusRequest.getHttpRequest();
		request.setAttribute("PATH", phyPdfFilePath);
		Map<String,Object> resultMap = bjcaService.service("preSign",request);
        resultMap.put("filePath", phyPdfFilePath);
        return resultMap;
	}
	
	/**
	 * 核准之后，对生成的文件进行ca签名
	 * @param request
	 * @param response
	 * @throws OptimusException
	 */
	@RequestMapping("/signData")
	public void signData(OptimusRequest request,OptimusResponse response) throws OptimusException{
		String gid = ParamUtil.get("gid");
		String filePath = ParamUtil.get("filePath");
		DocUtil.SignAndCreateXml(gid, filePath,request,bjcaService);
		
	}
	/**
     * 判断字段是否做过修改。
     * 
     * @param request
     * @param response
     * @throws OptimusException
     */
    @RequestMapping("/checkfieldEdit")
    public void checkfieldEdit(OptimusRequest request,OptimusResponse response) throws OptimusException{
        String gid = ParamUtil.get("gid");
        String fieldName = ParamUtil.get("fieldName");
        boolean editFlag = processService.checkfieldEdit(gid,fieldName);
        response.addResponseBody(JSON.toJSON(editFlag));
    }
    /**
     * 判断字段在申请平台申请用户是否发生过修改。
     * 
     * @param request
     * @param response
     * @throws OptimusException
     */
    @RequestMapping("/checkAppUserFieldEdit")
    public void checkAppUserFieldEdit(OptimusRequest request,OptimusResponse response) throws OptimusException{
        String gid = ParamUtil.get("gid");
        String fieldName = ParamUtil.get("fieldName");
        boolean editFlag = processService.checkAppUserFieldEdit(gid,fieldName);
        response.addResponseBody(JSON.toJSON(editFlag));
    }
}
