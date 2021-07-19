package com.gwssi.ebaic.apply.req.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.ebaic.approve.util.ProcessUtil;
import com.gwssi.ebaic.common.util.IdentityCertificateUtil;
import com.gwssi.ebaic.common.util.SubmitUtil;
import com.gwssi.ebaic.domain.BeWkReqBO;
import com.gwssi.ebaic.domain.BeWkReqprocessBO;
import com.gwssi.ebaic.domain.TPtYhBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.DAOManager;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.persistence.jdbc.StoredProcParam;
import com.gwssi.optimus.util.UuidGenerator;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.rule.RuleUtil;
import com.gwssi.rodimus.sms.SmsUtil;
import com.gwssi.rodimus.sms.domain.SmsBusiType;
import com.gwssi.rodimus.util.HttpSessionUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.rodimus.validate.msg.ValidateMsg;
import com.gwssi.rodimus.validate.msg.ValidateMsgEntity;

/**
 * 业务通用操作。
 * 
 * @author lxb
 */
@Service("reqService")
public class ReqService {

	private static AtomicInteger CERT_GET_CODE =new AtomicInteger();
	/**
	 * 业务终止
	 * @param gid
	 * @param reason
	 */
	public void stopReq(String gid,String reason) {
		if(StringUtil.isBlank(gid)) {
			 throw new EBaicException("传入的业务gid不能为空。");
		}
		TPtYhBO user = HttpSessionUtil.getCurrentUser();
		if(user==null){
			throw new EBaicException("登录超时，请重新登陆");
		}
		//执行规则 校验是否可以终止业务
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("gid", gid);
		paramsMap.put("userId", user.getUserId());
		ValidateMsg msg= RuleUtil.getInstance().runRule("ebaic_apply_req_stop",paramsMap);
		List<ValidateMsgEntity> errors =  msg.getAllMsg();
		if(errors!=null && !errors.isEmpty()){
			throw new EBaicException(msg.getAllMsgString());
		}
		//修改业务表记录
		String sql = "update be_wk_requisition set approve_notion=?,state='12',timestamp=sysdate where gid=?";
		DaoUtil.getInstance().execute(sql, reason,gid);
		
		//增加process表记录
		Calendar now = Calendar.getInstance();
		BeWkReqprocessBO bo = new BeWkReqprocessBO();
		bo.setGid(gid);
		bo.setReqprocessId(UuidGenerator.getUUID());
		bo.setRequisitionId(gid);
		bo.setProcessDate(now);
		bo.setUserId(user.getUserId());
		bo.setUserName(user.getUserName());
		bo.setProcessNotion(reason);
		bo.setProcessResult("90");
		bo.setState("12");
		bo.setProEndDate(now);
		bo.setTimestamp(now);
		DaoUtil.getInstance().insert(bo);
		
	}

	/**
	 * 业务删除
	 * @param gid
	 */
	
	public void deleteReq(String gid){
		if(StringUtil.isBlank(gid)) {
			 throw new EBaicException("传入的业务gid不能为空。");
		}
		TPtYhBO user = HttpSessionUtil.getCurrentUser();
		if(user==null){
			throw new EBaicException("登录超时，请重新登陆");
		}
		//执行规则 判断是否可以删除
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("gid", gid);
		paramsMap.put("userId", user.getUserId());
		ValidateMsg msg= RuleUtil.getInstance().runRule("ebaic_apply_req_del",paramsMap);
		List<ValidateMsgEntity> errors =  msg.getAllMsg();
		if(errors!=null && !errors.isEmpty()){
			throw new EBaicException(msg.getAllMsgString());
		}
		
		List<StoredProcParam> params = new ArrayList<StoredProcParam>();
		params.add(new StoredProcParam(1, gid, StoredProcParam.IN, oracle.jdbc.OracleTypes.VARCHAR));
		DaoUtil.getInstance().callStoreProcess("{call PROC_BE_CP_SETUP_DELETE(?)}", params);
		
	}
	
	/**
	 * 设置提交方式 1:申请法人授权 0:提交给法人确认
	 * @param gid
	 * @param authType
	 */
	public void setAuthType(String gid ,String authType){
		if(StringUtil.isBlank(gid)) {
			 throw new EBaicException("传入的业务gid不能为空。");
		}
		String userId = HttpSessionUtil.getCurrentUserId();
		if(StringUtils.isBlank(userId)){
			throw new EBaicException("登录超时，请重新登录");
		}
		if(!"0".equals(authType) && !"1".equals(authType)){
			throw new EBaicException("参数无效，请刷新页面重试或联系工作人员。标识符："+authType);
		}
		String sql ="update be_wk_requisition set auth_type=? where gid=?";
		DaoUtil.getInstance().execute(sql, authType,gid);
	}
	/**
	 * 选择自取执照
	 * @param gid
	 * @param licenseWay
	 */
	public void sureSelfGet(String gid ,String licenseWay){
		if(StringUtil.isBlank(gid)) {
			 throw new EBaicException("传入的业务gid不能为空。");
		}
		if(StringUtil.isBlank(licenseWay)) {
			 throw new EBaicException("取照方式不能为空。");
		}
		String userId = HttpSessionUtil.getCurrentUserId();
		if(StringUtils.isBlank(userId)){
			throw new EBaicException("登录超时，请重新登录");
		}
		
		//生成取照编号
		String certGetSn = getCertGetSn();
		
		String sql ="update be_wk_requisition r set r.license_get_type =?,r.license_order_time=sysdate,r.cert_get_sn=? ,CONFIRM_GETCERT_DATE=sysdate where gid=?";
		DaoUtil.getInstance().execute(sql,licenseWay,certGetSn,gid);
		String sql1 ="update be_wk_requisition@test_2_100 r set r.license_get_type =?,r.license_order_time=sysdate,r.cert_get_sn=? ,CONFIRM_GETCERT_DATE=sysdate where gid=?";
		DaoUtil.getInstance().execute(sql1,licenseWay,certGetSn,gid);
//		ApproveDaoUtil.getInstance().execute(sql,licenseWay,gid);
		
		//3.发送短信通知
		IPersistenceDAO dao = DAOManager.getPersistenceDAO();
		BeWkReqBO beWkReqBO = null;
		try {
			beWkReqBO = dao.queryByKey(BeWkReqBO.class, gid);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		String mobTel = ProcessUtil.getBeWkLeRepMobile(gid);
		Map<String,Object> smsParams = new HashMap<String,Object>();
		String entName = beWkReqBO.getEntName();
		smsParams.put("entName", entName);
		smsParams.put("linkman", beWkReqBO.getLinkman());
		String licenseWayText = "";
		if("1".equals(licenseWay)){
			licenseWayText = "寄递";
		}else{
			licenseWayText = "自取";
		}
		
		smsParams.put("licGetType", licenseWayText);
		smsParams.put("certGetSn", certGetSn);
		//smsParams.put("certPrintSn", "取照编号为"+certPrintSn);
		SmsUtil.send(mobTel, SmsBusiType.LIC_GET_NOTICE, smsParams);
		
		SubmitUtil.processRecord(gid, "08","8", ("选择取照方式为"+licenseWayText),"");
		
	}
	/**
	 * 生成取照编号 每天自增
	 * @return
	 */
	private String getCertGetSn(){
		Calendar now = Calendar.getInstance();
		int h =now.get(Calendar.HOUR_OF_DAY);
		int m =now.get(Calendar.MINUTE);
		int n = CERT_GET_CODE.getAndIncrement();
		if(n>=100){
			n=0;
			CERT_GET_CODE.set(1);
		}
		
		return String.format("%02d", h)+String.format("%02d", m)+String.format("%02d", n);
	}

	/**
	 * 获取领照单中所需参数
	 * @param gid
	 * @return
	 */
	public HashMap<String, Object> getPrintParam(String gid) {
		String sql = "select r.cert_get_sn,r.ent_name,case when (r.operation_type = '10') then '核准' end as type,"
				+ "r.copy_no,r.linkman,r.cert_no,l.le_rep_name,l.le_rep_cer_no,to_char(r.approve_date,'yyyy-mm-dd') as approveDate "
				+ ",to_char(r.license_order_time,'yyyy-mm-dd') as licenseOt from "
				+ "be_wk_requisition r left join be_wk_le_rep l on r.ent_id = l.ent_id where r.gid = ?";
		List<Map<String, Object>> paramsList = DaoUtil.getInstance().queryForList(sql, gid);
		HashMap<String, Object> params = (HashMap<String, Object>) paramsList.get(0);
		return params;
	}

	/**
	 * 获取该企业是否有选择取照方式 是否有取照编码
	 * @param gid
	 * @return
	 */
	public String getPrintFlag(String gid) {
		String sql = "select cert_get_sn from be_wk_requisition where gid = ?";
		
		String cerGetSn = (String)DaoUtil.getInstance().queryForOne(sql, gid);
		
		if("".equals(cerGetSn)){
			return "0";
		}
		
		return "1";
	}
	/**
	 * 查询企业相对路径
	 * @param gid
	 * @return
	 */
	public Map<String,Object> getPdfRelativePath(String gid) throws OptimusException {
		String sql = "select r.doc_relative_path,decode(r.state, '1','1','2','1','4','1','17','1','8','0','15','1','16','1') as page_no from be_wk_requisition  r where r.gid=? ";
		Map<String, Object> row = DaoUtil.getInstance().queryForRow(sql, gid);
		String relativePath = (String)row.get("docRelativePath");
		if(StringUtil.isBlank(relativePath)){
			com.gwssi.rodimus.doc.v2.DocUtil.buildDoc("setup_1100", gid, null,"1");
			row = DaoUtil.getInstance().queryForRow(sql, gid);
			relativePath = (String)row.get("docRelativePath");
			if(StringUtil.isBlank(relativePath)){
				throw new EBaicException("没有生成申请文书，请联系系统管理员。");
			}
		}
		return row;
	}
	/**
	 * 手机端
	 * 查询担任股东或法人的业务信息列表
	 * 已授权的不再显示业务记录
	 * @param cerNo 人员证件编号
	 * @param cerType 人员证件类型
	 * @return 结果列表 如果没查到 返回null
	 *  参考格式
	 *  [{gid:'89808797897adcbf8797d8',entName='北京海天科技有限公司',operationType='设立',submitDate='2016-09-02',isConfirm:'已确认',authToApplyUser:'已授权'}]
	 */
	public List<Map<String, Object>> queryReqListByPerson(String cerType,
			String cerNo) {
		if(StringUtils.isBlank(cerType)){
			cerType="1";
		}
		if(StringUtils.isBlank(cerNo)){
			throw new EBaicException("无效的证件号码");
		}
		StringBuffer sql = new StringBuffer();
		sql.append("with a as (select distinct gid, is_confirm, auth_to_apply_user")
		.append(" from (select i.gid, i.is_confirm, i.auth_to_apply_user")
		.append(" from be_wk_investor i where i.cer_type = ? and i.cer_no = ?")
		.append(" union all select l.gid, l.is_confirm, l.auth_to_apply_user")
        .append(" from be_wk_le_rep l where l.le_rep_cer_type =? and l.le_rep_cer_no =? ))")
        .append(" select 'person'as user_type,'2' as flag,r.state,r.gid,r.ent_name, to_char(r.submit_date,'YYYY-MM-DD hh24:mi:ss') as submit_date,")
        .append(" b.wb as operation_type,a.auth_to_apply_user as with_auth,")
        .append(" decode(a.is_confirm,'1','已确认','待确认') is_confirm,")
        .append(" decode(a.auth_to_apply_user,'1','已授权','未授权') auth_to_apply_user,r.doc_file_id")
        .append(" from be_wk_requisition r ")
        .append(" left join t_pt_dmsjb b on b.dmb_id = 'DFCD05' and b.dm = r.operation_type")
        .append(" left join a on a.gid=r.gid where r.gid in (select distinct gid from a) and r.state = '17' and (a.auth_to_apply_user is null or a.auth_to_apply_user<>'1')");
		return DaoUtil.getInstance().queryForList(sql.toString(), cerType,cerNo,cerType,cerNo);
	}
	
	/**
	 * 手机端
	 * 查询担任股东或法人的业务信息列表
	 * 已授权的不显示
	 * @param licNo 证照号码，先统一为 统一社会信用代码 （或注册号，取决于申请业务时非自然人股东信息填写的是哪一个，以此作为标识关联业务）
	 * @return 结果列表 结果列表 如果没查到 返回null
	 *  参考格式：
	 * [{gid:'89808797897adcbf8797d8',entName='北京海天科技有限公司',operationType='设立',submitDate='2016-09-02',isConfirm:'已确认',authToApplyUser:'已授权'}]
	 */
	public List<Map<String, Object>> queryReqListByCp(String licNo) {
		
		if(StringUtils.isBlank(licNo)){
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("with a as (select distinct gid, is_confirm, auth_to_apply_user")
		.append(" from (select i.gid, i.is_confirm, i.auth_to_apply_user")
		.append(" from be_wk_investor i where i.b_lic_no = ?")
		.append(" union all select l.gid, l.is_confirm, l.auth_to_apply_user")
        .append(" from be_wk_le_rep l where l.le_rep_cer_no =? ))")
        .append(" select 'ent'as user_type,'2' as flag,r.state,r.app_date,r.gid,r.ent_name, to_char(r.submit_date,'YYYY-MM-DD hh24:mi:ss') as submit_date,")
        .append(" b.wb as operation_type,a.auth_to_apply_user as with_auth,")
        .append(" decode(a.is_confirm,'1','已确认','待确认') is_confirm,")
        .append(" decode(a.auth_to_apply_user,'1','已授权','未授权') auth_to_apply_user,r.doc_file_id")
        .append(" from be_wk_requisition r ")
        .append(" left join t_pt_dmsjb b on b.dmb_id = 'DFCD05' and b.dm = r.operation_type")
        .append(" left join a on a.gid=r.gid where r.gid in (select distinct gid from a)");//r.state = '17' and  and (a.auth_to_apply_user is null or a.auth_to_apply_user<>'1')
		List<Map<String, Object>> ret = DaoUtil.getInstance().queryForList(sql.toString(), licNo,licNo);
		return ret;
		
	}
	
	/**
	 * 手机端
	 * 自然人业务确认
	 * @param cerType
	 * @param cerNo
	 * @param gid
	 * @param withAuth
	 * @return
	 */
	public String personConfirm(String cerType, String cerNo, String gid,
			String withAuth) {
		if(StringUtils.isBlank(gid)){
			return "无效的业务标识：gid为空";			
		}
		if(StringUtils.isBlank(cerType)){
			cerType="1";//没有值按身份证处理
		}
		if(StringUtils.isBlank(withAuth)){
			withAuth="0";
		}
		if(StringUtils.isBlank(cerNo)){
			return "无效的证件号码";
		}
		
		//查询身份认证情况

		boolean flag = IdentityCertificateUtil.isCertificated(cerType,cerNo);
		if(!flag){
			return "没有提交身份认证信息，不能进行业务确认。";
		}
		//查询任职情况
		StringBuffer sql = new StringBuffer();
		sql.append(" select name||'_'||(listagg(t, ',') within GROUP(order by t)) as u")
		.append(" from (select i.inv name,i.cer_type, i.cer_no, 'i' as t from be_wk_investor i")
		.append(" where i.gid =? and i.cer_type =? and i.cer_no =? union all")
	    .append(" select l.le_rep_name name,l.le_rep_cer_type cer_type, l.le_rep_cer_no cer_no, 'l' as t")
	    .append(" from be_wk_le_rep l where l.gid = ? and l.le_rep_cer_type =? and l.le_rep_cer_no =?)")
        .append(" group by cer_no, cer_type,name");
        String u = DaoUtil.getInstance().queryForOneString(sql.toString(),gid,cerType,cerNo,gid,cerType,cerNo );
        
        if(StringUtils.isBlank(u)){
        	return "没有查询到业务相关的人员信息";
        }
        String[] ui = u.split("_");
        if(ui.length <=1){
        	return "只有股东或法定代表人才能执行确认操作";
        }
        
        
        if(ui[1].contains("i")){
        	//是股东
        	String s ="update be_wk_investor i set i.is_confirm='1',i.auth_to_apply_user=? where i.gid=? and i.cer_type=? and i.cer_no=?";
    		DaoUtil.getInstance().execute(s, withAuth,gid,cerType,cerNo);
        }
        if(ui[1].contains("l")){
        	//是法人
        	String s ="update be_wk_le_rep l set l.is_confirm='1',l.auth_to_apply_user=? where l.gid=? and l.le_rep_cer_type=? and l.le_rep_cer_no=?";
    		DaoUtil.getInstance().execute(s, withAuth,gid,cerType,cerNo);
        }		
		SubmitUtil.processRecord(gid, "09","17", "股东或法定代表人确认提交",ui[0]);
       
		if(SubmitUtil.allConfirmedOrAuthed(gid)){
			//所有股东和法人都已经确认或授权了，提交到内网
			SubmitUtil.cpSetupSubmit(gid);
			return "提交成功！您将在两个工作日内收到反馈结果，请留意系统信息与短信提醒。";
		}else{
			return "确认成功！需法定代表人和所有股东全部确认后，才提交到工商机关审核。";
		}
	}
	/**
	 * 手机端
	 * 非自然人 业务确认
	 * @param licType
	 * @param licNo
	 * @param gid
	 * @param withAuth
	 * @return
	 */
	public String cpConfirm(String licType, String licNo, String gid,
			String withAuth) {
		if(StringUtils.isBlank(gid)){
			return "无效的业务标识：gid为空";			
		}
		if(StringUtils.isBlank(licType)){
			licType="1";
		}
		if(StringUtils.isBlank(withAuth)){
			withAuth="0";
		}
		if(StringUtils.isBlank(licNo)){
			return "无效的登记号码";
		}
		//查询身份认证情况
		boolean flag = IdentityCertificateUtil.isCertificated("",licNo);
		if(!flag){
			return "没有提交身份认证信息，不能进行业务确认。";
		}
		//查询任职情况
		StringBuffer sql = new StringBuffer();
		sql.append(" select name||'_'||(listagg(t, ',') within GROUP(order by t)) as u")
		.append(" from (select i.inv name,nvl(i.b_lic_type,'1') b_lic_type,i.b_lic_no, 'i' as t from be_wk_investor i")
		.append(" where i.gid =? and  i.b_lic_no=? union all")
	    .append(" select l.le_rep_name name,nvl(l.le_rep_cer_type,'1') b_lic_type, l.le_rep_cer_no b_lic_no, 'l' as t")
	    .append(" from be_wk_le_rep l where l.gid = ? and  l.le_rep_cer_no =?)")
        .append(" group by name,b_lic_type, b_lic_no");
        String u = DaoUtil.getInstance().queryForOneString(sql.toString(),gid,licNo,gid,licNo );
        
        if(StringUtils.isBlank(u)){
        	return "没有查询到业务相关的人员信息";
        }
        String[] ui = u.split("_");
        if(ui.length <=1){
        	return "只有股东或法定代表人才能执行确认操作";
        }
        
        
        if(ui[1].contains("i")){
        	//是股东
        	String s ="update be_wk_investor i set i.is_confirm='1',i.auth_to_apply_user=? where i.gid=?  and i.b_lic_no=?";
    		DaoUtil.getInstance().execute(s, withAuth,gid,licNo);
        }
        if(ui[1].contains("l")){
        	//是法人
        	String s ="update be_wk_le_rep l set l.is_confirm='1',l.auth_to_apply_user=? where l.gid=? and l.le_rep_cer_no=?";
    		DaoUtil.getInstance().execute(s, withAuth,gid,licNo);
        }		
		
        SubmitUtil.processRecord(gid, "09","17", "股东或法定代表人确认提交",ui[0]);
		
		if(SubmitUtil.allConfirmedOrAuthed(gid)){
			//所有股东和法人都已经确认或授权了，提交到内网
			SubmitUtil.cpSetupSubmit(gid);
			return "提交成功！您将在两个工作日内收到反馈结果，请留意系统信息与短信提醒。";
		}else{
			return "确认成功！需法定代表人和所有股东全部确认后，才提交到工商机关审核。";
		}
	}
	
	/**
	 * 手机端
	 * 退回申请人
	 * @param cerType
	 * @param cerNo
	 * @param gid
	 * @param reason
	 * @return
	 */
	public String personBackToAppUser(String cerType, String cerNo, String gid,
			String reason) {
		if(StringUtils.isBlank(gid)){
			return "无效的业务标识：gid为空";			
		}
		if(StringUtils.isBlank(cerType)){
			cerType="1";//没有值按身份证处理
		}
		
		if(StringUtils.isBlank(cerNo)){
			return "无效的证件号码";
		}
		
		//查询任职情况
		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct name from (select i.inv name")
		.append(" from be_wk_investor i where i.gid = ? and i.cer_type =? and i.cer_no =?")
	    .append(" union all select l.le_rep_name     name from be_wk_le_rep l")
        .append(" where l.gid =? and l.le_rep_cer_type = ? and l.le_rep_cer_no =?)");
        String u = DaoUtil.getInstance().queryForOneString(sql.toString(),gid,cerType,cerNo,gid,cerType,cerNo );
        
        if(StringUtils.isBlank(u)){
        	return "只有股东或法定代表人才能执行操作";
        }
        
        //重置所有的确认状态
        //股东表
        String s ="update be_wk_investor i set i.is_confirm='0' where i.gid=? ";
    	DaoUtil.getInstance().execute(s,gid);
        //法人表
    	s ="update be_wk_le_rep l set l.is_confirm='0' where l.gid=? ";
    	DaoUtil.getInstance().execute(s,gid);
        //更新业务表状态		
		s="update be_wk_requisition set state='1',timestamp=sysdate where gid=?";
		DaoUtil.getInstance().execute(s, gid);
		//记录
		SubmitUtil.processRecord(gid, "09","17", "股东或法定代表人退回申请人："+reason,u);
		return "0";
	}
	
	/**
	 * 手机端
	 * 退回申请人
	 * @param licNo
	 * @param gid
	 * @param reason
	 * @return
	 */
	public String cpBackToAppUser(String licNo, String gid, String reason) {
		if(StringUtils.isBlank(gid)){
			return "无效的业务标识：gid为空";			
		}
		
		if(StringUtils.isBlank(licNo)){
			return "无效的登记号码";
		}
		
		//查询任职情况
		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct name from (select i.inv name")
		.append(" from be_wk_investor i where i.gid = ? and i.b_lic_no =? ")
	    .append(" union all select l.le_rep_name     name from be_wk_le_rep l")
        .append(" where l.gid =?  and l.le_rep_cer_no =?)");
        String u = DaoUtil.getInstance().queryForOneString(sql.toString(),gid,licNo,gid,licNo );
        
        if(StringUtils.isBlank(u)){
        	return "只有股东或法定代表人才能执行操作";
        }
        
        //重置所有的确认状态
        //股东表
        String s ="update be_wk_investor i set i.is_confirm='0' where i.gid=? ";
    	DaoUtil.getInstance().execute(s,gid);
        //法人表
    	s ="update be_wk_le_rep l set l.is_confirm='0' where l.gid=? ";
    	DaoUtil.getInstance().execute(s,gid);
        //更新业务表状态		
		s="update be_wk_requisition set state='1',timestamp=sysdate where gid=?";
		DaoUtil.getInstance().execute(s, gid);
		//记录
		SubmitUtil.processRecord(gid, "09","17", "股东或法定代表人退回申请人:"+reason,u);
		return "0";
	}
	
}
