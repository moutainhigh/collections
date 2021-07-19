package com.gwssi.ebaic.approve.process.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gwssi.ebaic.approve.util.ApproveAuthUtil;
import com.gwssi.ebaic.approve.util.ApproveUserUtil;
import com.gwssi.ebaic.approve.util.ProcessUtil;
import com.gwssi.ebaic.common.util.IdentityCertificateUtil;
import com.gwssi.ebaic.common.util.ModifyUtil;
import com.gwssi.ebaic.domain.BeWkEntBO;
import com.gwssi.ebaic.domain.BeWkReqBO;
import com.gwssi.ebaic.domain.BeWkReqprocessBO;
import com.gwssi.ebaic.domain.SysmgrUser;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.persistence.jdbc.StoredProcParam;
import com.gwssi.rodimus.dao.ApproveDaoUtil;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.doc.v2.DocUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.rule.domain.SysRuleStep;
import com.gwssi.rodimus.sms.SmsUtil;
import com.gwssi.rodimus.sms.domain.SmsBusiType;
import com.gwssi.rodimus.util.DateUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.rodimus.util.UUIDUtil;

/**
 * 审批。
 * 
 * @author sgz
 *
 */
@Service(value="beProcessService")
public class ProcessService { 
    
	protected final static Logger logger = Logger.getLogger(ProcessService.class);
	/**
	 * 单一保存界面中的某一字段。
	 * @param gid 业务流水号
	 * @param fieldName 字段对应的驼峰名称
	 * @param fieldValue 字段值
	 * @throws OptimusException 
	 */
    public void saveEntField(String gid, JSONArray fieldObjectArray,String saveType) throws OptimusException {
        if(StringUtil.isBlank(gid)){
            throw new OptimusException("业务流水号不能为空!");
        }
        if(fieldObjectArray==null||fieldObjectArray.size()==0) {
            throw new OptimusException("字段对象不能为空!");
        }
        try {
             //判断是否是第一次保存，如果是往BeWkReqprocess插入一条记录
             ProcessUtil.generatorBeWkReqprocess(gid);
             BeWkEntBO entBO = DaoUtil.getInstance().queryForRowBo("select * from be_wk_ent t where t.gid=?", BeWkEntBO.class, gid);
             //StringBuffer businessScope = new StringBuffer();
             Set<String> scopeSet = new HashSet<String>(Arrays.asList("mainScope","opScope","ptBusScope","opCustomScope","opSuffix","businessScope"));
             boolean isScope = false;
             //BigDecimal version = (BigDecimal)DaoUtil.getInstance().queryForOne("select r.version from be_wk_requisition r where r.gid = ?", gid);
             for (Object object : fieldObjectArray) {
                JSONObject jsonObject = (JSONObject) object;
                String fieldName = jsonObject.getString("fieldName");
                String fieldValue = "";
                if("cxSave".equals(saveType)) {
                    fieldValue = DaoUtil.getInstance().queryForOneString("select m.before from be_wk_modify m,be_wk_requisition n where m.gid=n.gid and m.version=n.version and m.table_name = 'BE_WK_ENT' and m.table_field = ? and m.gid = ?", fieldName.toUpperCase(),gid);
                }else {
                    fieldValue=jsonObject.getString("fieldValue");
                }
                if(StringUtil.isBlank(fieldName)){
                    throw new OptimusException("字段名称不能为空!");
                }
                String editBeforeFieldValue="";
                Field[] fields = entBO.getClass().getDeclaredFields();  
                for (int j = 0; j < fields.length; j++) { 
                    if(fieldName.trim().equals(fields[j].getName())) {
                        fields[j].setAccessible(true); 
                        if(fields[j].getType().getName().equals(java.lang.String.class.getName())){
                            editBeforeFieldValue= (String) fields[j].get(entBO);
                        }
                        if(fields[j].getType().getName().equals(java.math.BigDecimal.class.getName())){
                            editBeforeFieldValue= String.valueOf(fields[j].get(entBO));
                        }
                        break;
                    }
                }
                //businessScope.append(fieldValue);
                BeanUtils.setProperty(entBO, fieldName, fieldValue);
                //entBO.setBusinessScope(businessScope.toString());
                if(!scopeSet.contains(fieldName)){//经营范围不保存标记
	                int type = 2; // 1-申请用户保存，2-审批用户保存
	                ModifyUtil.saveEntModify(gid, type, fieldName, fieldValue,editBeforeFieldValue);
                }else{
                	isScope = true;
                }
            }
            Calendar currentTime = DateUtil.getCurrentTime();
            entBO.setTimestamp(currentTime);
            DaoUtil.getInstance().update(entBO);
            if(isScope){//如果编辑了经营范围
            	String sql = "update be_wk_ent r set r.op_scope_type='3' where r.gid = ?";
            	DaoUtil.getInstance().execute(sql, gid);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(),e);
        }
        
    }
    
    
    /**
     * 如果为核准，且当前用户和辅助审查人员为同一人，则返回false，否则返回true
     * @param gid
     * @return
     * @throws EBaicException
     */
    public boolean checkIsOnePerson(String gid) throws EBaicException{
    	if(StringUtil.isBlank(gid)){
    		throw new EBaicException("参数传递错误!");
    	}
    	SysmgrUser user = ApproveUserUtil.getLoginUser();
    	if(user == null){
    		throw new EBaicException("登录超时，请重新登录。");
    	}
    	//判断当前环节是否为核准
    	boolean canApprove = ApproveAuthUtil.canApproved(gid);
    	
    	//如果当前环节不是核准，则返回true
    	if(!canApprove){
    		return true;
    	}
    	
    	String sql = "select req.censor_user_id from be_wk_Requisition req where req.gid = ?";
		String censorUserId = (String) DaoUtil.getInstance().queryForOne(sql, gid);
		if(StringUtil.isBlank(censorUserId)){
			throw new EBaicException("获取辅助审查人员信息失败！");
		}
		
		//如果为核准，且当前用户和辅助审查人员为同一人，则返回false，否则返回true
		if(user.getUserId().equals(censorUserId)){
			return false;
		}else{
			return true;
		}
    	
    }
    
    
    /**
     * 根据gid得到企业经营范围
     * @param gid
     * @return
     */
    public String getEntBusinessScope(String gid) throws OptimusException{
    	if(StringUtil.isBlank(gid)){
    		throw new OptimusException("参数传递错误！");
    	}
    	String sql = "select ent.business_scope from be_wk_ent ent where ent.gid = ?";
    	String businessScope = (String) DaoUtil.getInstance().queryForOne(sql, gid);
    	return businessScope;
    }
    
   
    /**
     * 查询当前业务需要显示哪些页签
     * @param gid
     * @return
     * @throws OptimusException 
     */
    public String getTabNames(String gid) throws OptimusException {
        if(StringUtil.isBlank(gid)){
            throw new OptimusException("业务流水号不能为空!");
        }
        SysmgrUser sysmgrUser=ApproveUserUtil.getLoginUser();
		if(null == sysmgrUser){
			throw new EBaicException("登录超时，请重新登录。");
		}
        StringBuffer tabNames = new StringBuffer();
        Map<String,Object> identityInfo = this.checkReqIdentityState(gid);
        if(identityInfo!=null) {
            Integer identityState = (Integer) identityInfo.get("identityState");
            if(identityState!=null&&identityState==IdentityCertificateUtil.IDENTITY_STATE_PICTURE_OK) {
                //tabNames.append("censor_identity,");
                tabNames.append("identity_check,");
            }
        }
        tabNames.append("approve_main,");
        tabNames.append("processAllInfo,");
        //判断是否有核准权限
        boolean canApprove = ApproveAuthUtil.canApproved(sysmgrUser.getUserId(), gid);
        if(canApprove) {
            tabNames.append("approve_doc,");
        }
        return tabNames.toString().substring(0, tabNames.toString().length()-1);
    }
    
    /**
     * 检查是否法定代表人和申请人是否通过了身份校验。
     * 
     * @param gid
     * @return
     */
    private Map<String,Object> checkReqIdentityState(String gid) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        String identityTip="";
        Integer identityState = null;
        String sql = "select r.linkman,r.cert_type,r.cert_no,i.le_rep_name,i.le_rep_cer_type,i.le_rep_cer_no from be_wk_requisition r "
                + " left join be_wk_le_rep i on r.gid = i.gid where r.gid = ? ";
        
        Map<String,Object> row = DaoUtil.getInstance().queryForRow(sql, gid);
        if(row==null || row.isEmpty()){
            throw new EBaicException("根据gid找不到申请数据（"+gid+"）。");
        }
        String linkMan = StringUtil.safe2String(row.get("linkman"));
        String lkCerType = StringUtil.safe2String(row.get("certType"));
        String lkCerNo = StringUtil.safe2String(row.get("certNo"));
        if(StringUtil.isBlank(lkCerType) || StringUtil.isBlank(lkCerNo)){
            throw new EBaicException("申请数据中未包含申请人信息。");
        }
        String leRepName = StringUtil.safe2String(row.get("leRepName"));
        String leCerType = StringUtil.safe2String(row.get("leRepCerType"));
        String leCerNo = StringUtil.safe2String(row.get("leRepCerNo"));
        if(StringUtil.isBlank(leCerType) || StringUtil.isBlank(leCerNo)){
            throw new EBaicException("申请数据中未包含法定代表人信息。");
        }
        
        //-1 未通过身份校验，而且未上传图片 ； 1 通过身份校验 ；0未通过身份校验,但上传了图片
        int lkState = IdentityCertificateUtil.checkIdentityStateDetail(lkCerType,lkCerNo);
        int leState = IdentityCertificateUtil.checkIdentityStateDetail(leCerType,leCerNo);
        
        // 全部通过校验
        if(lkState==1 && leState==1){
            identityState= IdentityCertificateUtil.IDENTITY_STATE_ALL_OK;
            identityTip = "申请人和法定代表人全部通过校验。";
        }
        // 未通过，也未传图片
        if(lkState==-1 && leState==-1){
            throw new EBaicException("申请人和法定代表人未通过实名认证，且未上传认证图片。");
        }
        if(lkState==-1){
            throw new EBaicException("申请人未通过实名认证，且未上传认证图片。");
        }
        if(leState==-1){
            throw new EBaicException("法定代表人未通过实名认证，且未上传认证图片。");
        }
        //有未通过的，但上传了材料
        if(lkState==0&&leState==0) {
            identityState= IdentityCertificateUtil.IDENTITY_STATE_PICTURE_OK;
            identityTip = "申请人（"+linkMan+"）和法定代表人（"+leRepName+"）未通过校验，但上传了材料";
        }else if(lkState==0&&leState!=0) {
            identityState= IdentityCertificateUtil.IDENTITY_STATE_PICTURE_OK;
            identityTip = "申请人（"+linkMan+"）未通过校验，但上传了材料";
        }else if(lkState!=0&&leState==0) {
            identityState= IdentityCertificateUtil.IDENTITY_STATE_PICTURE_OK;
            identityTip = "法定代表人（"+leRepName+"）未通过校验，但上传了材料";
        }
        resultMap.put("identityState", identityState);
        resultMap.put("identityTip", identityTip);
        return resultMap;
    }
    /**
     * 搬库(申请到审批)
     * @param gid
     * @return
     * @throws OptimusException 
     */
    public void copyData(String gid) throws OptimusException {
        if(StringUtil.isBlank(gid)){
            throw new OptimusException("业务流水号不能为空!");
        }
        IPersistenceDAO dao = DaoUtil.getInstance().getDao();
        List<StoredProcParam> params = new ArrayList<StoredProcParam>();
        params.add(new StoredProcParam(1, gid, StoredProcParam.IN, oracle.jdbc.OracleTypes.VARCHAR));
        dao.callStoreProcess("{call proc_to_wd(?)}", params); 
    }
    /**
     * 提交前获取一些校验信息。
     * @param gid
     * @param pageType
     * @param entType
     * @return
     * @throws OptimusException 
     */
    public Map<String,Object> getSubmitInfo(String gid,String entType) throws OptimusException {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        if(StringUtil.isBlank(gid)){
            throw new OptimusException("业务流水号不能为空!");
        }
        if(StringUtil.isBlank(entType)){
            throw new OptimusException("页面参数传递错误!");
        }
        //通过企业类型获取类型种类。
        String entTypeCategory = "";
        if(StringUtils.isNotBlank(entType)){
            entTypeCategory = ApproveDaoUtil.getInstance().queryForOneString("select cast(t.reg_type as varchar(2)) from cp_rs_parameter t where t.ent_type=?", entType);
        }
        //检验工作人员是否编辑过审核内容数据
        Long count = DaoUtil.getInstance().queryForOneLong("select COUNT(T.MODIFY_ID) from be_wk_modify t,be_wk_requisition n where t.gid=n.gid and t.version=n.version and t.table_name='BE_WK_ENT' and t.before<>t.after and  t.gid=?", gid);
        String editFlag = "no";
        if(count!=null&&count>0) {
            //修改过
            editFlag = "yes";
        }
        resultMap.put("editFlag", editFlag);
        resultMap.put("entTypeCategory", entTypeCategory);
        resultMap.putAll(this.checkReqIdentityState(gid));
        //判断当前登录用户ca_cert_id值是否为空
        SysmgrUser sysmgrUser= ApproveUserUtil.getLoginUser();
        Map<String,Object> loginInfoMap = DaoUtil.getInstance().queryForRow("select t.SIGN_PIC_URL,t.CA_CERT_ID from view_sysmgr_user t where t.USER_ID=?", sysmgrUser.getUserId());
        //使用redis存取数据时有问题，暂时从数据库中取
        //String caCertId = sysmgrUser.getCaCertId();
        String caCertId = (String) loginInfoMap.get("caCertId");
        resultMap.put("caCertId", caCertId);
        //String signPicUrl= sysmgrUser.getSignPicUrl();
        String signPicUrl = (String) loginInfoMap.get("signPicUrl");
        resultMap.put("signPicUrl", signPicUrl);
        //当前登录用户ca_cert_id与证书编号(插上ca读取)是否一致
        return resultMap;
    }
    /**
     * 辅助审查，核准 提交操作。
     * @param gid
     * @param pageType
     * @return
     * @throws OptimusException 
     */
    public void execSubmit(String gid, String submitType) throws OptimusException {
        if(StringUtil.isBlank(gid)){
            throw new EBaicException("业务流水号不能为空!");
        }
        if(StringUtil.isBlank(submitType)){
            throw new EBaicException("页面参数传递错误!");
        }
        if(!"approve".equals(submitType)&& !"examine".equals(submitType)){
        	throw new EBaicException("页面参数传递错误!");
        }
        SysmgrUser sysmgrUser = ApproveUserUtil.getLoginUser();
        if(null == sysmgrUser){
            throw new EBaicException("登录超时，请重新登录!");
        }
        BeWkReqBO beWkReqBO = DaoUtil.getInstance().queryForRowBo("select t.* from be_wk_requisition t where t.gid=?", BeWkReqBO.class, gid);
        if(beWkReqBO==null) {
            throw new EBaicException("数据异常。");
        }
        
        if("approve".equals(submitType)) {
        	approveSubmit(gid,beWkReqBO,sysmgrUser);
        }
        if("examine".equals(submitType)) {
        	checkSumbit(gid,beWkReqBO,sysmgrUser);
        }
        
        // 更新图片审查审核状态
        DaoUtil.getInstance().execute("update be_wk_upload_file_msg t set t.flag='1' where t.gid = ? and t.flag='0'", gid);
        
    }
    /**
     * 执行核准操作。
     * 
     * @param gid
     * @param beWkReqBO
     * @param sysmgrUser
     * @throws OptimusException
     */
    private void approveSubmit(String gid,BeWkReqBO beWkReqBO,SysmgrUser sysmgrUser) throws OptimusException {
    	String state = "";//业务状态
        String updateReqSql = "update be_wk_requisition t set t.pre_step=?,t.cur_step=?,t.state=?,t.timestamp=sysdate where t.gid=?";
    	//核准
        String approveResult = beWkReqBO.getApproveResult();
        if("14".equals(approveResult)) {
            state = "8";//核准环节 通过
            // 这部分代码已经弃用。
            try {
                //执行核准入库等操作
                ProcessUtil.approveOperation(null,null,gid);
                //生成文书
                String docCode = "setup_1100";
                String phyPdfFilePath = DocUtil.buildDoc(docCode, gid,"","1");
                if(StringUtil.isBlank(phyPdfFilePath)){
                    throw new OptimusException("生成文书失败！");
                }
            } catch (OptimusException e) {
                e.printStackTrace();
            }
        }
        if("12".equals(approveResult)) {
            this.updateYwConfirm(gid);
            state = "4";//核准环节 退回修改
            DaoUtil.getInstance().execute(updateReqSql,"10","12",state,gid);
            // 发送短信通知
            String mobTel = ProcessUtil.getLinkmanMobile(beWkReqBO);
            String entName = beWkReqBO.getEntName();
    		Map<String,Object> params = new HashMap<String,Object>();
    		params.put("entName", entName);
    		SmsUtil.send(mobTel, SmsBusiType.CHECK_MSG, params);
        }
        if("13".equals(approveResult)) {
            state = "9";//核准环节 驳回
            DaoUtil.getInstance().execute(updateReqSql,"10","12",state,gid);
            //驳回 入cp_hs_* 历史库
            IPersistenceDAO dao = ApproveDaoUtil.getInstance().getDao();
            List<StoredProcParam> params = new ArrayList<StoredProcParam>();
            params.add(new StoredProcParam(1, gid, StoredProcParam.IN, oracle.jdbc.OracleTypes.VARCHAR));
            dao.callStoreProcess("{call proc_approve_betocphs(?)}", params);
            
            // 发送短信通知
            String mobTel = ProcessUtil.getLinkmanMobile(beWkReqBO);
            String entName = beWkReqBO.getEntName();
    		Map<String,Object> smsParams = new HashMap<String,Object>();
    		smsParams.put("entName", entName);
    		SmsUtil.send(mobTel, SmsBusiType.CHECK_REJECT, smsParams);
        }
        if("12".equals(approveResult)||"13".equals(approveResult)) {
            String processNotion ="";
            if("12".equals(approveResult)) {
                processNotion = "退回修改："+beWkReqBO.getApproveNotion();
            }
            if("13".equals(approveResult)) {
                processNotion = "驳回："+beWkReqBO.getApproveNotion();
            }
            BeWkReqprocessBO beWkReqprocessBO = ProcessUtil.findCurrentProcess(gid);
            if(beWkReqprocessBO==null) {
                String insertSql = "insert into be_wk_reqprocess(reqprocess_id,requisition_id,process_date,user_id,user_name,process_step,process_notion,"
                        + "process_result,pro_end_date,state,reg_org,gid,timestamp) values(sys_guid(),?,sysdate,?,?,'12',?,?,sysdate,?,?,?,sysdate)";
                DaoUtil.getInstance().execute(insertSql,beWkReqBO.getRequisitionId(),sysmgrUser.getUserId(),sysmgrUser.getUserName(),processNotion,beWkReqBO.getApproveResult(),state,sysmgrUser.getOrgCodeFk(),gid);
            }else {
                  beWkReqprocessBO.setProcessStep("12");
                  beWkReqprocessBO.setProcessNotion(processNotion);
                  beWkReqprocessBO.setProcessResult(beWkReqBO.getApproveResult());
                  beWkReqprocessBO.setProEndDate(DateUtil.getCurrentTime());
                  beWkReqprocessBO.setState(state);
                  beWkReqprocessBO.setTimestamp(DateUtil.getCurrentTime());
                  beWkReqprocessBO.setUserId(sysmgrUser.getUserId());
                  beWkReqprocessBO.setUserName(sysmgrUser.getUserName());
                  beWkReqprocessBO.setRegOrg(sysmgrUser.getOrgCodeFk());
                  DaoUtil.getInstance().update(beWkReqprocessBO);
            }
        }
    }
    /**
     * 辅助审查提交。
     * 
     * @param gid
     * @param beWkReqBO
     */
    private void checkSumbit(String gid,BeWkReqBO beWkReqBO,SysmgrUser sysmgrUser) throws OptimusException {
    	 String state = "";//业务状态
         String updateReqSql = "update be_wk_requisition t set t.pre_step=?,t.cur_step=?,t.state=?,t.timestamp=sysdate where t.gid=?";
    	//审查
        String censorResult = beWkReqBO.getCensorResult();
        String processNotion = "";
        if("10".equals(censorResult)) {
            processNotion = "辅助审查通过："+beWkReqBO.getCensorNotion();
        	checkIfAllImagePass(beWkReqBO.getGid());
            state = "3";//辅助审查 通过
            DaoUtil.getInstance().execute(updateReqSql, "10","12",state,gid);
        }
        if("12".equals(censorResult)) {
            processNotion = "退回修改："+beWkReqBO.getCensorNotion();
            state = "4";//辅助审查 退回修改
            DaoUtil.getInstance().execute(updateReqSql, "","10",state,gid);
            this.updateYwConfirm(gid);
            // 发送短信通知
            String mobTel = ProcessUtil.getLinkmanMobile(beWkReqBO);
            String entName = beWkReqBO.getEntName();
    		Map<String,Object> params = new HashMap<String,Object>();
    		params.put("entName", entName);
    		SmsUtil.send(mobTel, SmsBusiType.CHECK_MSG, params);
        }
        if("13".equals(censorResult)) {
            processNotion = "驳回："+beWkReqBO.getCensorNotion();
            state = "9"; //辅助审查 驳回
            DaoUtil.getInstance().execute(updateReqSql, "","10",state,gid);
            //驳回 入cp_hs_* 历史库
            IPersistenceDAO dao = ApproveDaoUtil.getInstance().getDao();
            List<StoredProcParam> params = new ArrayList<StoredProcParam>();
            params.add(new StoredProcParam(1, gid, StoredProcParam.IN, oracle.jdbc.OracleTypes.VARCHAR));
            dao.callStoreProcess("{call proc_approve_betocphs(?)}", params);
            
            // 发送短信通知
            String mobTel = ProcessUtil.getLinkmanMobile(beWkReqBO);
            String entName = beWkReqBO.getEntName();
    		Map<String,Object> smsParams = new HashMap<String,Object>();
    		smsParams.put("entName", entName);
    		SmsUtil.send(mobTel, SmsBusiType.CHECK_REJECT, smsParams);
        }
        BeWkReqprocessBO beWkReqprocessBO = ProcessUtil.findCurrentProcess(gid);
        if(beWkReqprocessBO==null) {
            String insertSql = "insert into be_wk_reqprocess(reqprocess_id,requisition_id,process_date,user_id,user_name,process_step,process_notion,"
                    + "process_result,pro_end_date,state,reg_org,gid,timestamp) values(sys_guid(),?,sysdate,?,?,'10',?,?,sysdate,?,?,?,sysdate)";
            DaoUtil.getInstance().execute(insertSql,beWkReqBO.getRequisitionId(),sysmgrUser.getUserId(),sysmgrUser.getUserName(),processNotion,beWkReqBO.getCensorResult(),state,sysmgrUser.getOrgCodeFk(),gid);
        }else {
              beWkReqprocessBO.setProcessStep("10");
              beWkReqprocessBO.setProcessNotion(processNotion);
              beWkReqprocessBO.setProcessResult(beWkReqBO.getCensorResult());
              beWkReqprocessBO.setProEndDate(DateUtil.getCurrentTime());
              beWkReqprocessBO.setState(state);
              beWkReqprocessBO.setTimestamp(DateUtil.getCurrentTime());
              beWkReqprocessBO.setUserId(sysmgrUser.getUserId());
              beWkReqprocessBO.setUserName(sysmgrUser.getUserName());
              beWkReqprocessBO.setRegOrg(sysmgrUser.getOrgCodeFk());
              DaoUtil.getInstance().update(beWkReqprocessBO);
        }
    }
    /**
     * 更新股东，法人的确认状态
     * @param gid
     */
    private void updateYwConfirm(String gid) {
       String invUpdateSql = "update be_wk_investor t set t.is_confirm ='0',t.timestamp=sysdate where t.gid=?";
       String leUpdateSql = "update be_wk_le_rep t set t.is_confirm ='0',t.timestamp=sysdate where t.gid=?";
       DaoUtil.getInstance().execute(invUpdateSql, gid);
       DaoUtil.getInstance().execute(leUpdateSql, gid);
    }


    /**
     * 检查是否所有图片都是审查通过或者忽略。
     * @param gid
     */
    private void checkIfAllImagePass(String gid) {
		String sql = "select count(1) as cnt from be_wk_upload_file f where f.gid = ? and f.state = '1' ";
		long cnt = DaoUtil.getInstance().queryForOneLong(sql, gid);
		if(cnt>0){
			throw new EBaicException("有尚未审核的图像，不能提交。");
		}

		sql = "select count(1) as cnt from be_wk_upload_file f where f.gid = ? and f.state = '4'";
		cnt = DaoUtil.getInstance().queryForOneLong(sql, gid);
		if(cnt>0){
			throw new EBaicException("有审核未通过的图像，不能提交。");
		}
	}

	/**
     * 退回修改，驳回，清除审批表相关信息
     * @throws OptimusException 
     */
    protected void deleteBeWkInfo_approve(String gid) throws OptimusException {
        if(StringUtil.isBlank(gid)){
            throw new OptimusException("业务流水号不能为空!");
        }
        IPersistenceDAO dao = ApproveDaoUtil.getInstance().getDao();
        List<StoredProcParam> params = new ArrayList<StoredProcParam>();
        params.add(new StoredProcParam(1, gid, StoredProcParam.IN, oracle.jdbc.OracleTypes.VARCHAR));
        dao.callStoreProcess("{call proc_deleteBeWkInfo(?)}", params);
    }
    
	/**
	 * 文书查看。
	 * 
	 * 获取章节的图片fileid。
	 * 
	 * @param gid
	 */
	public Map<String,List<Map<String,Object>>> doc(String gid) {
		if(StringUtil.isBlank(gid)){
			throw new EBaicException("gid不能为空。");
		}
		Map<String,List<Map<String,Object>>> ret = new HashMap<String,List<Map<String,Object>>>();
	
		String sql = "select distinct cfg.title as label,cfg.chap_config_id as chap_id,chap.page_no,chap.file_ids from be_wk_doc_chapter chap " +
				"left join sys_doc_chapter_config cfg on chap.chap_config_id=cfg.chap_config_id where chap.gid=? order by chap.page_no asc";
		List<Map<String,Object>> chapters = DaoUtil.getInstance().queryForList(sql, gid);
		ret.put("chapters", chapters);
		
		if(chapters==null || chapters.isEmpty()){
			return ret;
		}
		List<Map<String,Object>> pictures = new ArrayList<Map<String,Object>>(); 
		for(Map<String,Object> chap : chapters){
			Object chapId = chap.get("chapId");
			String fileids = StringUtil.safe2String(chap.get("fileIds"));
			if(StringUtil.isBlank(fileids)){
				continue;
			}
			String[] arrFileids = fileids.split(",");
			if(arrFileids==null||arrFileids.length<1){
				continue;
			}
			for(String fileid : arrFileids){ 
				Map<String,Object> pic = new HashMap<String,Object>();
				pic.put("chapId", chapId);
				pic.put("fileid", fileid);
				pictures.add(pic);
			}
		}
		ret.put("pictures", pictures);
		return ret;
	}
	   /**
     * 特殊通道申请
     * @param gid
     * @param ruleStepIdList
     * @throws OptimusException 
     */
    public String createTstd(String gid, String ruleStepIds) throws OptimusException {
            String str="特殊通道申请成功，请等待审批后使用！";
            if(StringUtil.isBlank(gid)){
                throw new OptimusException("业务流水号不能为空!");
            }
            if(StringUtils.isBlank(ruleStepIds)) {
                throw new OptimusException("请至少选择一条需要申请特殊通道的锁定类型的规则！");
            }
            BeWkEntBO beWkEntBO = ApproveDaoUtil.getInstance().queryForRowBo("select * from be_wk_ent t where t.gid=?", BeWkEntBO.class, gid);
            BeWkReqBO beWkReqBO = ApproveDaoUtil.getInstance().queryForRowBo("select * from be_wk_requisition t where t.gid=?", BeWkReqBO.class, gid);
            if(beWkEntBO==null||beWkReqBO==null) {
                throw new OptimusException("数据异常!");
            }
            String checkSql="select count(1) from cp_rs_privilegereq t where t.operation_id=? and t.flag='0' and t.not_no=? and t.operation_type=?";
            Long resultCount = ApproveDaoUtil.getInstance().queryForOneLong(checkSql, beWkReqBO.getRequisitionId(),beWkEntBO.getNameAppId(),beWkReqBO.getOperationType());
            if(resultCount!=null && resultCount>0){
                str="当前业务已经申请过特殊通道，请到特殊通道申请处查询！";
            }else{
                    SysmgrUser sysmgrUser = ApproveUserUtil.getLoginUser();
                    String curOrgName = ProcessUtil.getRegOrgByFk(sysmgrUser.getOrgCodeFk());
                    String codeName = ProcessUtil.getNameValue(beWkReqBO.getOperationType(), "DFCD05");
                    String reason ="该企业正在办理"+codeName+"业务，在规则校验时有锁定，申请打开特殊通道，请求批准！";
                    String privilegeReqId = UUIDUtil.getUUID();
                    String insertSql = "insert into cp_rs_privilegereq(privilegereq_id,operation_type,ent_name,reg_no,not_no,reason,apply_date,apply_person,apply_org,"
                            + "approve_org,flag,operation_id,apply_person_id,APPLY_ORG_NAME,APPROVE_ORG_NAME) values(?,?,?,?,?,?,sysdate,?,?,?,'0',?,?,?,?)";
                    ApproveDaoUtil.getInstance().execute(insertSql,privilegeReqId, beWkReqBO.getOperationType(),beWkEntBO.getEntName(),beWkEntBO.getRegNo(),beWkEntBO.getNameAppId(),reason,sysmgrUser.getUserName(),
                          sysmgrUser.getOrgCodeFk(),sysmgrUser.getOrgCodeFk(),beWkReqBO.getRequisitionId(),sysmgrUser.getUserId(),curOrgName,curOrgName);
                    String[] ruleStepIdArr = ruleStepIds.split(",");
                    for(String ruleStepId:ruleStepIdArr){
                        SysRuleStep sysRuleStep = DaoUtil.getInstance().get(SysRuleStep.class,ruleStepId);
                        int index = sysRuleStep.getStepName().indexOf("]");
                        String ruleNo = "";
                        if(index!=-1) {
                            ruleNo = sysRuleStep.getStepName().substring(1,index);
                        }
                        ApproveDaoUtil.getInstance().execute("insert into cp_rs_privilege(privilege_id,privilegereq_id,rule_no,rule_name,flag,rule_id) values(sys_guid(),?,?,?,'0',?)", privilegeReqId,ruleNo,sysRuleStep.getStepName(),sysRuleStep.getStepId());
                    }
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
    public String executeTstdRules(String gid,String ruleStepIds) throws OptimusException {
        String resultStr="pass";
        String sql = "select n.rule_id,t.privilegereq_id from cp_rs_privilegereq t,cp_rs_privilege n,be_wk_ent ent,be_wk_requisition req where t.privilegereq_id=n.privilegereq_id and ent.gid=req.gid and req.requisition_id=t.operation_id  and t.flag='1' and n.flag='1' and t.valid_date>=sysdate and req.gid=?";
        List<Map<String,Object>> contentList = ApproveDaoUtil.getInstance().queryForList(sql, gid);
        if(contentList!=null&&contentList.size()>0) {
            List<String> ruleStepIdList = new ArrayList<String>();
            String privilegereqId = "";
            for (Map<String,Object> map : contentList) {
                String ruleId = (String) map.get("ruleId");
                ruleStepIdList.add(ruleId);
                if(StringUtils.isBlank(privilegereqId)) {
                    privilegereqId=(String) map.get("privilegereqId");
                }
            }
            String[] ruleStepIdArr = ruleStepIds.split(",");
            for (String ruleStepId:ruleStepIdArr) {
                if (ruleStepIdList.contains(ruleStepId)) {
                    continue;
                } else {
                    return "noPass";
                }
            }
            if(resultStr=="pass"&&StringUtils.isNotBlank(privilegereqId)) {
                ApproveDaoUtil.getInstance().execute("update cp_rs_privilegereq t set t.flag='2' where t.privilegereq_id=?", privilegereqId);
            }
        }else {
            resultStr="error";
        }
        return resultStr;
    }
    //判断字段是否做过修改。
    public boolean checkfieldEdit(String gid, String fieldName) {
        if("locRemark".equals(fieldName)) {
            fieldName = "domDetail";
        }
        boolean editFlag = false;
        Long count = DaoUtil.getInstance().queryForOneLong("select COUNT(T.MODIFY_ID) from be_wk_modify t,be_wk_requisition n where t.gid=n.gid and t.version=n.version and t.approve_user_id is not null and t.table_name='BE_WK_ENT' and t.before<>t.after and t.table_field=? and t.gid=?",fieldName.toUpperCase(), gid);
        if(count!=null&&count>0) {
            //修改过
            editFlag = true;
        }
        return editFlag;
    }

    //判断字段在申请平台申请用户是否发生过修改。
    public boolean checkAppUserFieldEdit(String gid, String fieldName) {
        if("locRemark".equals(fieldName)) {
            fieldName = "domDetail";
        }
        boolean editFlag = false;
        Long count = DaoUtil.getInstance().queryForOneLong("select COUNT(T.MODIFY_ID) from be_wk_modify t,be_wk_requisition n,be_wk_reqprocess m where t.gid=n.gid and n.gid=m.gid and m.state='4' and t.version = (nvl(n.version,0) -1) and t.app_user_id is not null and t.table_name='BE_WK_ENT' and nvl(t.before,0) <> nvl(t.after,0) and t.table_field=? and t.gid=?",fieldName.toUpperCase(), gid);
        if(count!=null&&count>0) {
            //修改过
            editFlag = true;
        }
        return editFlag;
    }
}
