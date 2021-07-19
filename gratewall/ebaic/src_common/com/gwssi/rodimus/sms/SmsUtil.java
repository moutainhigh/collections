package com.gwssi.rodimus.sms;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gwssi.rodimus.exception.RodimusException;
import com.gwssi.rodimus.sms.domain.SmsBusiType;
import com.gwssi.rodimus.sms.domain.SmsWkTemplateBO;
import com.gwssi.rodimus.sms.service.ISmsService;
import com.gwssi.rodimus.util.SpringUtil;
import com.gwssi.rodimus.util.StringUtil;

/**
 * @author 刘海龙
 */
@Service
public class SmsUtil {
	protected final static Logger logger = Logger.getLogger(SmsUtil.class);
	
	private static ISmsService smsService;
	
	protected static ISmsService getSmsService(){
		if(smsService==null){
			smsService = (ISmsService)SpringUtil.getBean("smsService");
		}
		return smsService;
	}
	
//	
//	public static void send(String mobile,String content) {
//		ISmsService service = getSmsService();
//		if(service==null){
//			throw new RodimusException("未获取SmsService实例。");
//		}
//		service.sendSms(mobile, "001", content);
//	}
	
	public static void send(String mobile,SmsBusiType type,Map<String,Object> params) {
		// 准备短信内容
		String id = type.getTemplateId();
		SmsWkTemplateBO templateBO = TemplateManager.instance.getConfig(id);
		
		String licGetType = (String) params.get("licGetType");
		String template = null;
		String content = null;
		if("自取".equals(licGetType)){
			template = templateBO.getConetnt();
//			int length = template.length();
//			String subfix = template.substring(10,length-1);
//			template.replace(subfix, "...");
			String certGetSn = (String) params.get("certGetSn");
			template = template + ",领照编号:"+certGetSn+",请于3个工作日后凭本人身份证及领照编号到工商局领照";
			content = fillMsgValue(template,params);
			int length = content.indexOf("】");
			String subfix = content.substring(9,length);
			content = content.replace(subfix, "...");
		}else{
			template = templateBO.getConetnt();
			content = fillMsgValue(template,params);
		}
		// 发送短信
		ISmsService service = getSmsService();
		if(service==null){
			throw new RodimusException("未获取SmsService实例。");
		}
		service.sendSms(mobile,type.getCode(), content);
	}

	protected static final Pattern EXPR_PATTERN = Pattern.compile("(\\$\\{.*?\\})"); //"(\\{.*?\\})"
	private static String fillMsgValue(String msg, Map<String, Object> context) {
		if(StringUtil.isBlank(msg)){
			return "";
		}
		msg = msg.replaceAll("`", "\"");
		Matcher matcher = EXPR_PATTERN.matcher(msg);
		String paramPlaceHolder = null;
		String expr = null;
		Object exprValueObj = null;
		String exprValue = null;
		String ret = msg;
		
		while(matcher.find()){
			paramPlaceHolder = matcher.group(); // 如 ： {entName}
			paramPlaceHolder = paramPlaceHolder.trim();
			if(paramPlaceHolder!=null && paramPlaceHolder.length()>3){//至少包含${和}
				expr = paramPlaceHolder.substring(2, paramPlaceHolder.length()-1);
				if(StringUtil.isBlank(expr)){
					ret = ret.replace(paramPlaceHolder, "");
					continue ;
				}
				
				expr = expr.trim();
				exprValueObj = context.get(expr); //exprValueObj = ExprUtil.run(expr, context); //EXPR_ENGINE.run(expr,context);
				if(exprValueObj==null){
					exprValue = "";
				}else if((exprValueObj instanceof List) || (exprValueObj instanceof Map)){
					exprValue = JSON.toJSONString(exprValueObj, false);
				}else{
					exprValue = StringUtil.safe2String(exprValueObj);
				}
				if("NaN".equals(exprValue)){
					exprValue="";
				}
				ret = ret.replace(paramPlaceHolder, exprValue);
			}
		}
		
		return ret;
	}
//	public static void send(String mobile,String smsTypeKey,String content) {
//		ISmsService service = getSmsService();
//		if(service==null){
//			throw new RuntimeException("未获取S没事Service实例。");
//		}
//		service.sendSms(mobile, smsTypeKey, content);
//	}
//	
//	/**
//	 * 入口方法。
//	 * 
//	 * 增加短信发送任务。
//	 * 后台定时发送。
//	 * 
//	 * @param gid
//	 * @param receiverType 0-全发，1-经办人移动电话码，2-法定代表人移动电话码，3-企业联系人移动电话码
//	 * @param smsBusiType 业务类型代码，与SmsWkTemplateBO.subBusiType匹配
//	 * @throws OptimusException 
//	 */
//	public static String send(String gid,String receiverType,String smsBusiType) throws OptimusException{
//		IPersistenceDAO dao = DAOManager.getPersistenceDAO();
//		String mobile = fetchMobileNumber(gid,receiverType,dao);
//		logger.debug("mobile="+mobile);
//		String content = fetchSmsContent(gid,smsBusiType,dao);
//		logger.debug("content="+content);
//		String smsId = sendSms(smsBusiType,mobile,content);
//		logger.debug("smsId="+smsId);
//		return smsId;
//	}
//	
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	protected static String fetchSmsContent(String gid, String smsBusiType,IPersistenceDAO dao) throws OptimusException {
//		
//		String content = "";//模板
//		String ret = "";
//		
//		String sql = "";
//		List<Object> params = new ArrayList<Object>();
//		List<Map> list = null;
//		Map<String,Object> row = null;
//		
//		// 1、获得模板
//		sql = "select t.conetnt,t.params_sql from sms_wk_template t where t.busi_type='A' and t.flag='1' and t.sub_busi_type=?";
//		params.clear();
//		params.add(smsBusiType);
//		list = dao.queryForList(sql, params);
//		String paramSql = null;
//		if(list!=null && list.size()>0){
//			row = list.get(0);
//			content = StringUtil.safe2String(row.get("conetnt"));
//			paramSql = StringUtil.safe2String(row.get("paramsSql"));
//		}
//		if(StringUtils.isEmpty(content)){
//			throw new OptimusException("短信模板为空，请联系管理员，检查系统配置。");
//		}
//		
//		if(StringUtils.isEmpty(paramSql)){//参数SQL为空，表示不需要替换参数
//			ret = content ;
//		}else{
//			// 2、获得参数
//			Map<String, Object> PARAMS_CONTENT = null;
//			params.clear();
//			params.add(gid);
//			list = dao.queryForList(paramSql, params);
//			if(list!=null && list.size()>0){
//				PARAMS_CONTENT = list.get(0);
//			}
//			// 3、替换变量
//			if( PARAMS_CONTENT==null || PARAMS_CONTENT.isEmpty() ){
//				throw new OptimusException("获得短信数据出错，请联系管理员，检查系统配置。");
//			}
//			// 3.1、根据模板找出变量列表
//			Pattern pattern = Pattern.compile("(\\{.*?\\})");
//			Matcher matcher = pattern.matcher(content);
//			String paramPlaceHolder = null;
//			String paramName = null;
//			String paramValue = null;
//			ret = content;
//			
//			while(matcher.find()){
//				paramPlaceHolder = matcher.group(); // 如 ： {entName}
//				if(paramPlaceHolder!=null && paramPlaceHolder.length()>2){//至少包含{和}
//					paramName = paramPlaceHolder.substring(1, paramPlaceHolder.length()-1);
//					paramValue = StringUtil.safe2String(PARAMS_CONTENT.get(paramName));
//					
//					ret = ret.replace(paramPlaceHolder, paramValue);
//				}
//			}
//		}
//		return ret;
//	}
//	
//	
//
//	/**
//	 * FIXME 增加对“2-法定代表人移动电话码，3-企业联系人移动电话码”的支持。
//	 * @param gid
//	 * @param receiverType
//	 * @param dao
//	 * @return
//	 * @throws OptimusException 
//	 */
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	 static String fetchMobileNumber(String gid, String receiverType,IPersistenceDAO dao) throws OptimusException {
//		String mobile = null;
//		
//		String sql = "";
//		List<Object> params = new ArrayList<Object>();
//		List<Map> list = null;
//		Map<String,Object> row = null;
//		
//		if("1".equals(receiverType)){//经办人
//			sql = "select r.mob_tel as mobile from cp_wk_requisition r where r.gid=?";
//			params.clear();
//			params.add(gid);
//			list = dao.queryForList(sql, params);
//			if(list!=null && list.size()>0){
//				row = list.get(0);
//				mobile = StringUtil.safe2String(row.get("mobile"));
//			}
//		}else if("2".equals(receiverType)){
//			// FIXME 需要兼容设立、变更
//			sql = "select t.tel as mobile from cp_wk_entmember t,cp_wk_job t1 where t.entmember_id = t1.entmember_id and t1.gid = ? and t1.le_rep_sign = '1' and (t1.modify_sign is null or t1.modify_sign not like '3%')";
//			params.clear();
//			params.add(gid);
//			list = dao.queryForList(sql, params);
//			if(list!=null && list.size()>0){
//				row = list.get(0);
//				mobile = StringUtil.safe2String(row.get("mobile"));
//			}
//			
//		}else if("3".equals(receiverType)){
//			sql = "select e1.contact_mobile as mobile from cp_wk_entcontact e1 where e1.gid = ?";
//			params.clear();
//			params.add(gid);
//			list = dao.queryForList(sql, params);
//			if(list!=null && list.size()>0){
//				row = list.get(0);
//				mobile = StringUtil.safe2String(row.get("mobile"));
//			}
//		}else{
//			throw new OptimusException("不支持的短信接收人类型。");
//		}
//		
//		return mobile;
//	}

//	/**
//	 * 
//	 * @param mobile 接收短信的移动电话码
//	 * @param content 短息内容
//	 * @throws OptimusException 插入操作失败
//	 */
//	public static String sendSms(String smsBusiType,String mobile,String content) throws OptimusException{
//		
//		String smsMessageId = UUIDUtil.getUUID();
//		SmsWkMessageBO smsBo = new SmsWkMessageBO();
//		smsBo.setId(smsMessageId);
//		smsBo.setTemplateId(smsBusiType);
//		smsBo.setMobile(mobile);
//		smsBo.setContent(content);
//		Calendar now = DateUtil.getCurrentTime();
//		smsBo.setCreateTime(now);
//		DaoUtil.getInstance().insert(smsBo);
//		
//		/*TaskQueue tq = TaskQueueManager.get(TaskQueueManager.SMS_QUEUE);
//		tq.pushTask(smsMessageId);*/
//		return smsMessageId;
//	}
}
