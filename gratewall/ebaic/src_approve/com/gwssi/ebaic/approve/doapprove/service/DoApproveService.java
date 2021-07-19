package com.gwssi.ebaic.approve.doapprove.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwssi.ebaic.apply.util.SessionConst;
import com.gwssi.ebaic.approve.util.ApproveUserUtil;
import com.gwssi.ebaic.approve.util.ProcessUtil;
import com.gwssi.ebaic.common.service.BjcaService;
import com.gwssi.ebaic.common.util.EbaicConsts;
import com.gwssi.ebaic.domain.BeWkEntBO;
import com.gwssi.ebaic.domain.BeWkReqBO;
import com.gwssi.ebaic.domain.SysmgrUser;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.persistence.jdbc.StoredProcParam;
import com.gwssi.rodimus.dao.ApproveDaoUtil;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.doc.v1.core.arch.ArchBuilder;
import com.gwssi.rodimus.doc.v2.DocUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.rule.RuleUtil;
import com.gwssi.rodimus.rule.domain.SysRuleStep;
import com.gwssi.rodimus.util.JSonUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.rodimus.util.UUIDUtil;
import com.gwssi.rodimus.validate.msg.ValidateMsg;
import com.gwssi.rodimus.validate.msg.ValidateMsgEntity;

@Service("doApproveService")
public class DoApproveService {

	/**
	 * 准备跑规则，搬库。
	 * 
	 * @param gid
	 */
	public void rulePrepare(String gid) {
		if (StringUtil.isBlank(gid)) {
			throw new EBaicException("业务流水号不能为空!");
		}
		// 如果已经核准，则略过
		String sql = "select state from be_wk_requisition r where r.gid = ?";
		String state = DaoUtil.getInstance().queryForOneString(sql, gid);
		if("8".equals(state)){
			return ;
		}
		if("9".equals(state)){
			throw new EBaicException("当前业务已经驳回，不能核准。");
		}
		if("12".equals(state)){
			throw new EBaicException("当前业务已经驳回，不能核准。");
		}
		List<StoredProcParam> params = new ArrayList<StoredProcParam>();
		params.add(new StoredProcParam(1, gid, StoredProcParam.IN, oracle.jdbc.OracleTypes.VARCHAR));
		IPersistenceDAO dao = DaoUtil.getInstance().getDao();
		try {
			// 先删后插，符合幂等性
			dao.callStoreProcess("{call proc_to_wd(?)}", params);
		} catch (OptimusException e) {
			throw new EBaicException("规则准备失败：" + e.getMessage(), e);
		}
	}

	/**
	 * 运行核准规则。
	 * 
	 * @param gid
	 * @return
	 */
	public ValidateMsg runRule(String gid) {
		// 如果已经核准，则略过
		String sql = "select state from be_wk_requisition r where r.gid = ?";
		String state = DaoUtil.getInstance().queryForOneString(sql, gid);
		if("8".equals(state)){
			return new ValidateMsg();
		}
		if("9".equals(state)){
			throw new EBaicException("当前业务已经驳回，不能核准。");
		}
		if("12".equals(state)){
			throw new EBaicException("当前业务已经驳回，不能核准。");
		}
		ValidateMsg ret = RuleUtil.getInstance("approveDataSource").runApp("ebaic_setup_approve", gid);
		if (ret == null || ret.isEmpty()) {
			return ret;
		}
		List<ValidateMsgEntity> errorList = ret.getAllMsg();
		if (errorList == null || errorList.isEmpty()) {
			return ret;
		}

		for (ValidateMsgEntity validateMsgEntity : errorList) {
			if ("0".equals(validateMsgEntity.getType())) {
				validateMsgEntity.setType("提示");
			}
			if ("1".equals(validateMsgEntity.getType())) {
				validateMsgEntity.setType("锁定");
			}
		}
		if(ret!=null && !ret.isEmpty()){
			throw new EBaicException(ret.toString());
		}
		return ret;
	}

	
	
	private void checkIfAllImagePass(String gid) {
		String sql = "select count(1) as cnt from be_wk_upload_file f where f.gid = ? and f.state = '4'";
		long cnt = DaoUtil.getInstance().queryForOneLong(sql, gid);
		if(cnt>0){
			throw new EBaicException("有审核未通过的图像，不能执行核准。");
		}
	}

	/**
	 * 核准提交。
	 * 
	 * @param gid
	 */
	public void submit(String gid) {
	  	if(StringUtils.isBlank(gid)) {
            throw new EBaicException("业务流水号不能为空!");
        }
		String sql = "select state from be_wk_requisition r where r.gid = ?";
		String state = DaoUtil.getInstance().queryForOneString(sql, gid);
	  	if("8".equals(state)){
			return ;
		}
		if("9".equals(state)){
			throw new EBaicException("当前业务已经驳回，不能核准。");
		}
		if("12".equals(state)){
			throw new EBaicException("当前业务已经驳回，不能核准。");
		}
		checkIfAllImagePass(gid);
        BeWkReqBO beWkReqBO = ApproveDaoUtil.getInstance().queryForRowBo("select * from be_wk_requisition t where t.gid=?", BeWkReqBO.class, gid);
        BeWkEntBO beWkEntBO = ApproveDaoUtil.getInstance().queryForRowBo("select * from be_wk_ent t where t.gid=?", BeWkEntBO.class, gid);
        if(beWkReqBO==null) {//找不到申请单，退出。
            throw new EBaicException("数据异常：找不到申请单数据!");
        }
        if(beWkEntBO==null) {//找不到企业信息
            throw new EBaicException("数据异常：找不到企业数据!");
        }
        String operationType = beWkReqBO.getOperationType();
        if(StringUtils.isBlank(operationType)) {//不能确定业务类型
            throw new EBaicException("不能确定业务类型!");
        }
        if ((EbaicConsts.SQYW_PTSL).equals(operationType)) {//普通设立
        	IPersistenceDAO approveDao = ApproveDaoUtil.getInstance().getDao();
    		IPersistenceDAO ebaicDao = DaoUtil.getInstance().getDao();
        	ProcessUtil.savePTSL(approveDao,ebaicDao,beWkEntBO,beWkReqBO);
        }else{
        	throw new EBaicException("业务类型（"+operationType+"）不支持，目前仅支持普通设立（10）业务!");
        }
	}

	@Autowired
	BjcaService bjcaService;

	/**
	 * 生成电子文书。
	 * 同时做签名准备。
	 * 
	 * @param gid
	 * @return 文件Hash码
	 */
	public String docAndHash(String gid, HttpServletRequest request) {
		String docCode = "setup_1100";
		String phyPdfFilePath = DocUtil.buildDoc(docCode, gid,"","1");
		if (StringUtil.isBlank(phyPdfFilePath)) {
			throw new EBaicException("生成文书失败！");
		}
		request.setAttribute(SessionConst.BJCA_PATH, phyPdfFilePath);
		
		// 签名准备
		request.setAttribute("PATH", phyPdfFilePath);
		Map<String, Object> ret = bjcaService.service("preSign", request);
		if (ret == null || ret.isEmpty()) {
			throw new EBaicException("签名失败！");
		}
		String hashCode = StringUtil.safe2String(ret.get("hashCode"));
		if (StringUtil.isBlank(hashCode)) {
			throw new EBaicException("签名失败！");
		}
		return hashCode;
	}

	/**
	 * 执行签名。
	 * @param gid
	 * @param httpRequest
	 * @return
	 */
	public void sign(String gid, HttpServletRequest httpRequest) {
		Map<String, Object> ret = bjcaService.service("sign", httpRequest);
		String error = StringUtil.safe2String(ret.get("error"));
		if(StringUtil.isNotBlank(error)){
			throw new EBaicException(error);
		}
		//生成XML
		HttpSession session = httpRequest.getSession();
		String pdfPath = StringUtil.safe2String(session.getAttribute(SessionConst.BJCA_PATH));
	
		System.out.println("ret::"+JSonUtil.toJSONString(ret));
		System.out.println("bjca_path:"+pdfPath);
		String xmlPath = pdfPath.replace(DocUtil.PDF_NAME, DocUtil.XML_NAME);
		System.out.println("bjca_path:"+xmlPath);
		ArchBuilder.buildXml(gid,xmlPath);
	}

	/**
	 * 特殊通道申请。
	 * 
	 * @param gid
	 * @param ruleStepIds
	 * @return
	 */
	public String applySpecial(String gid, String ruleStepIds) throws OptimusException{
		 //1.准备参数
		 String str="特殊通道申请成功，请等待审批后使用！";
         if(StringUtil.isBlank(gid)){
             throw new OptimusException("业务流水号不能为空!");
         }
         if(StringUtils.isBlank(ruleStepIds)) {
             throw new OptimusException("请至少选择一条需要申请特殊通道的锁定类型的规则！");
         }
         BeWkEntBO beWkEntBO = ApproveDaoUtil.getInstance().queryForRowBo("select * from be_wk_ent t where t.gid=?", BeWkEntBO.class, gid);
         BeWkReqBO beWkReqBO = ApproveDaoUtil.getInstance().queryForRowBo("select * from be_wk_requisition t where t.gid=?", BeWkReqBO.class, gid);
         if(beWkEntBO==null) {
             throw new OptimusException("数据异常：没有企业数据!");
         }
         if(beWkReqBO==null) {
             throw new OptimusException("数据异常：没有申请数据!");
         }
         //2、判断是否已经申请过特殊通道
         String[] ruleStepIdArr = ruleStepIds.split(",");
         long cnt = 0;
         String StepNames = "";
         String checkSql="select count(*) from cp_rs_privilegereq t left join cp_rs_privilege p on t.privilegereq_id = p.privilegereq_id where t.operation_id = ? and p.rule_id=? ";
         for(String ruleStepId:ruleStepIdArr){
        	 SysRuleStep sysRuleStep = DaoUtil.getInstance().get(SysRuleStep.class,ruleStepId);
        	 StepNames += sysRuleStep.getStepName()+"，";
        	 cnt = ApproveDaoUtil.getInstance().queryForOneLong(checkSql, gid,ruleStepId);
         }
         if(cnt>0){
        	 str = "当前业务规则："+StepNames+"已经申请过特殊通道，请到特殊通道申请处查询！";
         }
         //3、如果没有申请过，则进行主表cp_rs_privilegereq申请
         StringBuffer sql = new StringBuffer();
         SysmgrUser sysmgrUser = ApproveUserUtil.getLoginUser();
         String curOrgName = ProcessUtil.getRegOrgByFk(sysmgrUser.getOrgCodeFk());
         String codeName = ProcessUtil.getNameValue(beWkReqBO.getOperationType(), "DFCD05");
         String reason ="该企业正在办理"+codeName+"业务，在规则校验时有锁定，申请打开特殊通道，请求批准！";
         String privilegeReqId = UUIDUtil.getUUID();
         sql.append(" insert into cp_rs_privilegereq( ")
         	.append(" privilegereq_id,operation_type,ent_name,reg_no,not_no,reason,apply_date,apply_person, ")
         	.append(" apply_org,approve_org,flag,operation_id,apply_person_id,APPLY_ORG_NAME,APPROVE_ORG_NAME,operator)")
         	.append(" values(?,?,?,?,?,?,sysdate,?,?,?,'0',?,?,?,?,'ebaic')");
         ApproveDaoUtil.getInstance().execute(sql.toString(),privilegeReqId, beWkReqBO.getOperationType(),beWkEntBO.getEntName(),beWkEntBO.getRegNo(),beWkEntBO.getNameAppId(),reason,sysmgrUser.getUserName(),
               sysmgrUser.getOrgCodeFk(),sysmgrUser.getOrgCodeFk(),beWkReqBO.getRequisitionId(),sysmgrUser.getUserId(),curOrgName,curOrgName);
        
         //4、对该笔申请的细节进行描述
         String errCode = "";
         for(String ruleStepId:ruleStepIdArr){
             SysRuleStep sysRuleStep = DaoUtil.getInstance().get(SysRuleStep.class,ruleStepId);
             errCode = sysRuleStep.getErrCode();
             ApproveDaoUtil.getInstance().execute("insert into cp_rs_privilege(privilege_id,privilegereq_id,rule_no,rule_name,flag,rule_id) values(sys_guid(),?,?,?,'0',?)", privilegeReqId,errCode,sysRuleStep.getStepName(),ruleStepId);
         }
         return str;
	}
	
	/**
     * 执行特殊通道
     * @param gid
     * @param ruleStepIds 
     * @return
     * @throws OptimusException 
     */
    public String specialGalleryPass(String gid,String ruleStepIds) throws OptimusException {
        String resultStr="pass";
        String sql = "select n.rule_id,t.privilegereq_id from cp_rs_privilegereq t,cp_rs_privilege n,be_wk_ent ent,be_wk_requisition req where t.privilegereq_id=n.privilegereq_id and ent.gid=req.gid and req.requisition_id=t.operation_id  and t.flag='1' and n.flag='1' and req.gid=?";
        List<Map<String,Object>> contentList = ApproveDaoUtil.getInstance().queryForList(sql, gid);
        if(contentList!=null&&contentList.size()>0) {
            List<String> ruleStepIdList = new ArrayList<String>();
            String privilegereqId = "";
            for (Map<String,Object> map : contentList) {
                String ruleId = (String) map.get("ruleId");
                ruleStepIdList.add(ruleId);
            	privilegereqId=(String) map.get("privilegereqId");
            }
            String[] ruleStepIdArr = ruleStepIds.split(",");
            for (String ruleStepId:ruleStepIdArr) {
                if (ruleStepIdList.contains(ruleStepId)) {
                    continue;
                } else {
                    return "noPass";//数据库里的数据和页面选择的不一样；页面多余数据库有的；
                }
            }
            if(resultStr=="pass"&&StringUtils.isNotBlank(privilegereqId)) {
                ApproveDaoUtil.getInstance().execute("update cp_rs_privilegereq t set t.flag='2' where t.privilegereq_id=?", privilegereqId);
            }
        }else {
            resultStr="error";
        }
        
        //ValidateMsg ret = RuleUtil.getInstance("approveDataSource").runApp(appCode, gid, skipedStepIdList)
		
        return resultStr;
    }

}
