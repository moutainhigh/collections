package com.gwssi.ebaic.common.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.gwssi.ebaic.domain.TPtYhBO;
import com.gwssi.optimus.util.MD5Util;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.util.StringUtil;

/**
 * 网上登记用户。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class ApplyUserUtil {

	/**
	 * 是否注册了网上登记账号。
	 * @param cerType
	 * @param cerNo
	 * @return
	 */
	public static boolean hasApplyAccount(String cerType,String cerNo){
		String sql = "select count(1) as cnt from t_pt_yh y where y.yx_bj='1' and y.cer_type = ? and y.cer_no = ? ";
		long cnt = DaoUtil.getInstance().queryForOneLong(sql, cerType,cerNo);
		boolean ret = cnt > 0;
		return ret;
	}
	/**
	 * FIXME
	 * @param cerNo
	 * @return
	 */
	public static TPtYhBO getUserByCerNo(String cerNo){
		String userId = getLastestActiveUserId(cerNo);
		TPtYhBO ret = DaoUtil.getInstance().get(TPtYhBO.class, userId);
		return ret;
	}
	
	/**
	 * 根据证件类型，证件号码取得网登账号
	 * @param cerNo
	 * @param cerType
	 * @return
	 */
	public static TPtYhBO getUserByCerNoAndCerType(String cerNo,String cerType){
		String userId = getLastestActiveUserIdByCerNoAndCerType(cerNo,cerType);
		TPtYhBO ret = DaoUtil.getInstance().get(TPtYhBO.class, userId);
		return ret;
	}
	
	/**
	 * 重置密码。
	 * 
	 * @param userId
	 * @return 新密码
	 */
	public static String resetPassword(String userId){
		String ret = genNewPassword();
		String pwd = MD5Util.MD5Encode(ret);
		String sql = "update t_pt_yh y set y.user_pwd = ? where y.user_id = ?";
		DaoUtil.getInstance().execute(sql, pwd, userId);
		return ret;
	}
	
	/**
	 * 生成新密码。
	 * @return
	 */
	public static String genNewPassword(){
		String ret = (int)((Math.random()*9+1)*100000) + "";
		return ret;
	}
	

	/**
	 * 根据身份证号码查找网登用户。
	 * 如果有多个，则取最近活跃的。
	 * @param cerNo
	 * @return
	 */
	private static String getLastestActiveUserId(String cerNo) {
		
		String sql = "select t.user_id from t_pt_yh t where t.cer_no = ?";
		List<Map<String, Object>> userList = DaoUtil.getInstance().queryForList(sql, cerNo);
		if(userList==null || userList.isEmpty()){
			return null;
		}
		
		// 找到userId
		String userId = "";
		if(userList.size()>1){
			// 当前身份证号码存在多个用户
			// 找最近活跃的用户
			sql = "select y.user_id from t_pt_yh y left join nm_wk_transact tr on y.user_id=tr.user_id where tr.transact_id is not null and tr.app_date is not null and y.cer_no=? order by tr.app_date desc";
			userId = DaoUtil.getInstance().queryForOneString(sql, cerNo);
			
			if(StringUtils.isEmpty(userId)){// 没办理过名称业务
				sql = "select y.user_id from t_pt_yh y left join cp_wk_requisition r on y.user_id = r.user_id where r.requisition_id is not null and r.timestamp is not null and y.cer_no = ? order by r.timestamp desc";
				userId = DaoUtil.getInstance().queryForOneString(sql, cerNo);
			}
			
			if(StringUtils.isEmpty(userId)){// 没办理企业业务
				sql = "select t.user_id from t_pt_yh t where t.cer_no = ? order by t.create_time desc";
				userId = DaoUtil.getInstance().queryForOneString(sql, cerNo);
			}
		}else{
			Map<String, Object> row = userList.get(0);
			userId = StringUtil.safe2String(row.get("userId"));
		}
		
		return userId ;
	}
	
	
	/**
	 * 根据身份证号码和证件类型查找网登用户。
	 * 如果有多个，则取最近活跃的。
	 * @param cerNo
	 * @return
	 */
	private static String getLastestActiveUserIdByCerNoAndCerType(String cerNo,String cerType) {
		
		String sql = "select t.user_id from t_pt_yh t where t.cer_no = ? and t.cer_type = ?";
		List<Map<String, Object>> userList = DaoUtil.getInstance().queryForList(sql, cerNo,cerType);
		if(userList==null || userList.isEmpty()){
			return null;
		}
		
		// 找到userId
		String userId = "";
		if(userList.size()>1){
			// 当前身份证号码存在多个用户
			// 找最近活跃的用户
			sql = "select y.user_id from t_pt_yh y left join nm_wk_transact tr on y.user_id=tr.user_id where tr.transact_id is not null and tr.app_date is not null and y.cer_no=? order by tr.app_date desc";
			userId = DaoUtil.getInstance().queryForOneString(sql, cerNo);
			
			if(StringUtils.isEmpty(userId)){// 没办理过名称业务
				sql = "select y.user_id from t_pt_yh y left join cp_wk_requisition r on y.user_id = r.user_id where r.requisition_id is not null and r.timestamp is not null and y.cer_no = ? order by r.timestamp desc";
				userId = DaoUtil.getInstance().queryForOneString(sql, cerNo);
			}
			
			if(StringUtils.isEmpty(userId)){// 没办理企业业务
				sql = "select t.user_id from t_pt_yh t where t.cer_no = ? order by t.create_time desc";
				userId = DaoUtil.getInstance().queryForOneString(sql, cerNo);
			}
		}else{
			Map<String, Object> row = userList.get(0);
			userId = StringUtil.safe2String(row.get("userId"));
		}
		
		return userId ;
	}
	
}
