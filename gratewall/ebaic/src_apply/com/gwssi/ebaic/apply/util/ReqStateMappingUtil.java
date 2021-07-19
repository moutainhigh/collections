package com.gwssi.ebaic.apply.util;

import java.util.HashMap;
import java.util.Map;

import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.exception.RodimusException;
import com.gwssi.rodimus.util.StringUtil;
/**
 * 将业务类型、业务状态根据代码20，30...转化为文本设立、变更...
 * @author chaiyoubing
 *
 */
@SuppressWarnings({"serial"})
public class ReqStateMappingUtil {
	@SuppressWarnings("unused")
	private static Map<String,String> CHANGE_ID_STATE_TEXT = new HashMap<String, String>(){{
			put("-1","未提交");
			put("0","待审核");
			put("1","正常");
			put("2","审批未通过");
			put("3","失效");
		}
	};
	private static Map<String,String> CHANGE_OPERATION_TEXT = new HashMap<String, String>(){{
			put("1","公司变更");
			put("2","公司备案");
			put("3","股权质押");
			put("6","企业认证服务");
			put("8","企业账号管理员");
			put("9","法定代表人移动电话");
			put("10","企业补充信息");
			put("11","企业联系人");
		}
	};
	private static Map<String,String> CHANGE_STATE_TEXT = new HashMap<String, String>(){{
			put("1","未提交");
			put("2","等待审查");
			put("3","通过");
			put("4","退回修改");
			put("8","已核准");
			put("9","已驳回");
			put("12","已终止");
			put("15","审查中");
			put("16","审查中");
			put("17", "待确认");
		}
	};
	private static Map<String,String> CHANGE_OPER_TYPE_TEXT = new HashMap<String, String>(){{
			put("10","公司设立");
			put("20","公司变更");
			put("23","备案");
			put("30","注销");
			put("40","增减补换照");
			put("92","股权出质");
		}
	};
	/**
	 * 法人身份认证状态代码转化为文本
	 * @param map
	 */
	public static void changeIdStateText(Map<String, Object> map) {
		String legIdentityState = (String)map.get("legIdentityState");
		if(StringUtil.isBlank(legIdentityState)){
			throw new EBaicException("法定代表人身份认证状态为空，请联系运维人员。");
		}
		String text = CHANGE_STATE_TEXT.get(legIdentityState);
		map.put("legIdentityState",text);
	}
	/**
	 * 将操作权限代码转化为文本
	 * @param map
	 */
	public static void changeOperationText(Map<String, Object> map){
		String operation = (String)map.get("operation");
		if(StringUtil.isBlank(operation)){
			throw new EBaicException("当前操作权限为空，请联系运维人员。");
		}
		String[] opr = operation.split(",");
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<opr.length-1;i++){
			String text = CHANGE_OPERATION_TEXT.get(opr[i]);
			sb.append(text).append(",");
		}
		String text = CHANGE_OPERATION_TEXT.get(opr[opr.length-1]);
		sb.append(text);
		map.put("operation",sb.toString());
	}
	/**
	 * 将业务状态代码转化为文本
	 * @param map
	 */
	public static void changeStateText(Map<String, Object> map){
		String state = (String)map.get("state");
		if(StringUtil.isBlank(state)){
			throw new RodimusException("当前业务状态为空，请联系运维人员。");
		}
		String text = CHANGE_STATE_TEXT.get(state);
		map.put("state",text);
	}
	/**
	 * 将业务类型转化为文本
	 * @param map
	 */
	public static void changeOperTypeText(Map<String, Object> map){
		String operationType = (String)map.get("operationType");
		if(StringUtil.isBlank(operationType)){
			throw new RodimusException("当前业务类型为空，请联系运维人员。");
		}
		String text = CHANGE_OPER_TYPE_TEXT.get(operationType);
		map.put("operationType",text);
	}
	/**
	 * 将日期转化为文本
	 * @param map
	 */
	public static void changeDateText(Map<String, Object> map) {
		String text =(String) map.get("appDate");
		if(StringUtil.isBlank(text)){
			throw new RodimusException("当前业务申请日期为空，请联系运维人员。");
		}
		map.put("appDate",text);
	}
	/**
	 * 根据dm 转化为  text
	 */
	public static String changeTextByDm(String dm) {
		String sql = "select d.wb from t_pt_dmsjb d where d.dmb_id='CA01' and d.dm=?";
		String text = DaoUtil.getInstance().queryForOneString(sql, dm);
		return text;
	}
}
