package com.gwssi.ebaic.apply.security.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.ebaic.domain.TPtYhBO;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.sms.SmsVerCodeUtil;
import com.gwssi.rodimus.util.HttpSessionUtil;
import com.gwssi.rodimus.util.StringUtil;

@Service("userSecurityCenterService")
public class UserSecurityCenterService {
	
	/**
	 * 修改用户密码
	 * @param mm
	 * @param userId
	 * @return
	 */
	public boolean updatePassword(String mm,String userId)   {

		String sql = "update t_pt_yh set user_pwd=? where user_id = ?";
		List<String> params = new ArrayList<String>();
		params.add(mm);
		params.add(userId);
		DaoUtil.getInstance().execute(sql, params);
		updateUserSession();
		return true;

	}

	/**
	 * 获取用户的手机验证状态,1代表已验证，其他为未验证
	 * @param userId
	 * @return
	 */
	public String getMobileValidate(String userId) {
		String sql = " select y.mob_check_state from t_pt_yh y where y.user_id = ? ";
		List<String> params = new ArrayList<String>();
		params.add(userId);
		String flag = DaoUtil.getInstance().queryForOneString(sql, params);
		return flag;
		
	}
	
	public Map<String ,Object> getUserInfo(String userId) {
		String sql = "select substr(mobile,0,3)||'****'||substr(mobile,8,4) as mobile,login_name,mob_check_state from t_pt_yh  where user_id = ? and yx_bj='1'";
		List<String> params = new ArrayList<String>();
		params.add(userId);
		Map<String ,Object> user = DaoUtil.getInstance().queryForRow(sql, params);
		return user;
	}
	
	public String getAppovedUserMobile(String userId){
		String sql = "select  mobile from T_PT_YH where user_id = ? and yx_bj = '1'";
		String ret = DaoUtil.getInstance().queryForOneString(sql, userId);
		return ret;
	}
	
	
	public Map<String ,Object> getUserRegInfo(String userId)  {
		String sql = "select y.login_name,y.pwd_question,y.pwd_answer,y.email,y.address,y.zip_code from t_pt_yh y where y.user_id =? and y.yx_bj='1' ";
		List<String> params = new ArrayList<String>();
		params.add(userId);
		Map<String ,Object> map = DaoUtil.getInstance().queryForRow(sql, params);
		return map;
	}
	
	public String validateMobile(Map<String,String> map,TPtYhBO user)  {
		
		
		
		if(map == null || map.isEmpty()){
			throw new EBaicException("获取表单信息异常，请联系管理员！");
		}
		String userId = user.getUserId();
		
		String mobCheckState = map.get("mobCheckState");
//		String displayMobile = map.get("displayMobile");
		String oracleDbMobile =  user.getMobile();//库中存的原始移动电话
		String ucerNo = user.getCerNo();//库中证件号码
		
		String mobileNew = map.get("mobileNew");
		String mobileCodeNew = map.get("mobileCodeNew");
		//String mobileOld = map.get("mobileOld");
		String mobileCodeOld = map.get("mobileCodeOld");
		String cerNo = map.get("cerNo");
		
		if(StringUtils.isBlank(userId) || StringUtils.isBlank(oracleDbMobile) || StringUtils.isBlank(ucerNo)
				|| StringUtils.isBlank(mobileNew) || StringUtils.isBlank(mobileCodeNew)){
			throw new EBaicException("获取表单信息异常，请联系管理员！");
		}
		
		//校验证件号码
		if(!ucerNo.equals(cerNo)){
			throw new EBaicException("输入的证件号码不匹配，请重新输入！");
		}
		
		boolean res = false;//是否更新成功标记，成功返回true
		String result = "";
		
		if(mobCheckState.equals("1")){//手机已验证
			if(StringUtils.isEmpty(mobileCodeOld)){
				throw new EBaicException("获取表单信息异常，请联系管理员！");
			}
//			if(!oracleDbMobile.equals(mobileOld)){
//				throw new EBaicException("您输入移动电话和原移动电话不匹配，请重新输入！");
//			}
			SmsVerCodeUtil.verify(getAppovedUserMobile(userId), mobileCodeOld);
			SmsVerCodeUtil.verify(mobileNew, mobileCodeNew);
			
		}else{//手机未验证
			SmsVerCodeUtil.verify(mobileNew, mobileCodeNew);
		}
		res = updateMobAndCheckState(mobileNew,userId);
		
		if(res){
			result = "1";
			updateUserSession();
			
		}else{
			result = "2";
		}
		
		return result;
	}
	
	/**
	 * 更换移动电话以及更改验证状态
	 * @param mobile
	 * @param userId
	 */
	public boolean updateMobAndCheckState(String mobile,String userId)  {
		String sql = " update t_pt_yh y set y.mob_check_state = '1',y.mobile =? where y.user_id = ? and y.yx_bj = '1' ";
		List<String> params = new ArrayList<String>();
		params.add(mobile);
		params.add(userId);
		DaoUtil.getInstance().execute(sql, params);
		updateUserSession();
		return true;
	}
	
	public boolean validateCerNo(String cerNo,String userId){
		if(StringUtils.isBlank(cerNo)){
			throw new EBaicException("证件号码获取失败，请联系管理员！");
		}
		String sql = " select t.cer_no from t_pt_yh t where t.user_id = ? ";
		List<String> params = new ArrayList<String>();
		params.add(userId);
		String rawCerNo = DaoUtil.getInstance().queryForOneString(sql, params);
		return rawCerNo.equals(cerNo);
		
	}
	
	/**
	 * 更新session缓存,目的用于用户一次登录多次更改相关信息时sesssion即时更新
	 */
	public void updateUserSession(){
		TPtYhBO currentUser = HttpSessionUtil.getCurrentUser();
		if(currentUser == null){
			throw new EBaicException("获取信息失效，请重新登录");
		}
		String sql = "select * from t_pt_yh y where y.login_name = ? and y.yx_bj='1' and user_type='1' ";
		TPtYhBO tPtBo = DaoUtil.getInstance().queryForRowBo(sql, TPtYhBO.class, currentUser.getLoginName());
		HttpSession session = HttpSessionUtil.getSession();
		session.setAttribute(OptimusAuthManager.USER, tPtBo);
	}

	/**
	 * 发送短信验证码到当前登录用户的移动电话。
	 */
	public void sendVerCode() {
		TPtYhBO currentUser = HttpSessionUtil.getCurrentUser();
		if(currentUser == null){
			throw new EBaicException("登录超时，请重新登录。");
		}
		String userId = currentUser.getUserId();
		if(StringUtil.isBlank(userId)){
			throw new EBaicException("未获取当前登录用户编号。");
		}
		TPtYhBO currentUserInDb = DaoUtil.getInstance().queryForRowBo("select * from t_pt_yh y where y.user_id = ? and y.yx_bj = '1'", TPtYhBO.class, userId);
		if(currentUserInDb==null){
			throw new EBaicException("当前登录用户已经被停用。");
		}
		String mobCheckState = currentUserInDb.getMobCheckState();
		if(!"1".equals(mobCheckState)){
			throw new EBaicException("当前登录用户移动电话未经过校验，请先认证校验移动电话。");
		}
		String mobile = currentUserInDb.getMobile();
		if(StringUtil.isBlank(mobile)){
			throw new EBaicException("当前登录用户未设置移动电话，请先设置移动电话。");
		}
		SmsVerCodeUtil.send(mobile);
	}

}
