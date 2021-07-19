package com.gwssi.ebaic.apply.security.user.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.ebaic.common.util.IdentityCertificateUtil;
import com.gwssi.ebaic.domain.SysmgrIdentityBO;
import com.gwssi.ebaic.domain.TPtYhBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.util.MD5Util;
import com.gwssi.rodimus.dao.BaseDaoUtil;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.indentity.IdentityCardUtil;
import com.gwssi.rodimus.util.HttpSessionUtil;
import com.gwssi.rodimus.util.StringUtil;

/**
 * 用户登录。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Service(value="userLoginService")
public class UserLoginService {

	/**
	 * 个人账户登录。
	 * 
	 * @param user
	 * @param password
	 * @param vercode
	 */
	public String personLogin(String loginName, String password) {
		if(StringUtil.isBlank(loginName)||StringUtil.isBlank(password)){
			return "登录名和密码不能为空。";
		}
		
		String sql = "select * from t_pt_yh y where (y.login_name = ? or (y.cer_no=? and cer_type='1')) and y.yx_bj='1' and user_type='1'";
		TPtYhBO user = DaoUtil.getInstance().queryForRowBo(sql, TPtYhBO.class, loginName,loginName);
		if(user==null){
			return "登录信息有误。";
		}
		
		password = MD5Util.MD5Encode(password);
		if(!password.equals(user.getUserPwd())){
			return "登录信息有误。";
		}
		
		/**调用公安接口，进行身份证验证**/
        if(!"0".equals(user.getCheckState())){
        	//checkState字段值不为0表示没进行过身份证校验，需要调用公安接口校验
        	boolean isValid= IdentityCardUtil.check(user.getUserName(),user.getCerNo());// IdentifyUtil.checkCerNo(user.getUserName(),user.getCerNo());
        	if(!isValid){        		
                return "needCheck";
        	}
        	if(isValid){
        		//更新帐号状态
        		user.setCheckState("0");
        		DaoUtil.getInstance().update(user);
        	}
        }
        // 登录成功，保存Session
     	HttpSession session = HttpSessionUtil.getSession();
     	session.setAttribute(OptimusAuthManager.USER, user);
		session.setAttribute(OptimusAuthManager.LOGIN_USER_TYPE, OptimusAuthManager.LOGIN_USER_TYPE_PERSON);

		return "success";
	}

	/**
	 * 得到当前登录状态。
	 * 
	 * @return
	 */
	public Map<String, Object> getLoginState()  {
		String userType = HttpSessionUtil.getLoginUserType();
		Map<String, Object> ret = new HashMap<String,Object>();
		if(StringUtils.isBlank(userType)){
			ret.put("result", "fail");
			return ret;
		}
		ret.put("userType", userType);
		if(OptimusAuthManager.LOGIN_USER_TYPE_ENT.equals(userType)){
			SysmgrIdentityBO ent = HttpSessionUtil.getEntUser();
			if(ent==null){
				ret.put("result", "fail");
				return ret;
			}
			ret.put("name",ent.getName());
			if("2".equals(ent.getFlag()) || "3".equals(ent.getFlag()) || "-1".equals(ent.getFlag())){
				ret.put("Iresult", "identityFail");
			}
			if(StringUtils.isBlank(ent.getMobile())){//移动电话码是否校验
				ret.put("Mresult", "mobFail");
			}
			
		}else if (OptimusAuthManager.LOGIN_USER_TYPE_PERSON.equals(userType)){
			TPtYhBO userSession = HttpSessionUtil.getCurrentUser();
			if(userSession==null){
				ret.put("result", "fail");
				return ret;
			}
			String userId = userSession.getUserId();
			String sql = "select * from t_pt_yh y where y.user_id = ? and y.yx_bj='1'";
			TPtYhBO user = DaoUtil.getInstance().queryForRowBo(sql, TPtYhBO.class, userId);
			if(user==null){
				throw new RuntimeException("未查询到您的用户信息，请您尝试重新登录。");
			}
			
			String idCardState   = user.getCheckState();// 身份证号是否合法（0:合法；-1:不合法）
			String identityState = user.getIdentityCheckState();// 身份核查状态（0或空：未认证；1-已经认证，2-未通过，3-过期失效）
			String mobState      = user.getMobCheckState();//移动电话码是否校验（0：已经校验，-1：校验未通过，空：未校验）
			String userName      = user.getUserName();
			
			ret.put("name",userName);
			if(!"1".equals(identityState)){//身份核查状态（0或空：未认证；1-已经认证，2-未通过，3-过期失效）
				boolean r = IdentityCertificateUtil.isCertificated(user.getCerType(), user.getCerNo());
						//IdentityCardUtil..getAppIdentityState(user.getUserName(), user.getCerNo());
				if(!r){
					ret.put("Iresult", "identityFail");
				}
				
			}
			if(!"1".equals(mobState)){//移动电话码是否校验（1：已经校验，-0：校验未通过，空：未校验）
				ret.put("Mresult", "mobFail");
			}
			if(!"0".equals(idCardState)){//不合法
				ret.put("Cresult","idCardFail");
			}
			
		}else{
			ret.put("result", "fail");
			
		}
		
		return ret;
	}

	/**
	 * 将身份认证用户的核查标记变更以及身份证存库
	 * @param userId
	 * @param cerNo
	 * @throws OptimusException
	 */
	public void alterCheckState(String loginName, String cerNo)throws OptimusException {
		if(StringUtil.isBlank(loginName)){
			throw new OptimusException("登录异常 请重新登录!");
		}
		BaseDaoUtil dao = DaoUtil.getInstance();
		String sql = "update t_pt_yh t set t.cer_No = "+cerNo+",t.id_card = "+cerNo+", t.check_state = '0' where t.login_Name = ?";
		dao.execute(sql, loginName);
	}
}
