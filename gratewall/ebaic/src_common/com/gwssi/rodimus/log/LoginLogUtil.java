package com.gwssi.rodimus.log;

import java.util.Calendar;

import com.gwssi.ebaic.domain.TPtYhBO;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.log.domain.SysmgrLogininfoLogBO;
import com.gwssi.rodimus.util.DateUtil;
import com.gwssi.rodimus.util.PathUtil;
import com.gwssi.rodimus.util.HttpSessionUtil;
import com.gwssi.rodimus.util.UUIDUtil;
/**
 * 登录日志。
 * 
 * @author liuhailong
 */
public class LoginLogUtil {
	/**
	 * TODO 登录成功后调用。
	 * 
	 * @param user
	 */
	public void logLogin(TPtYhBO user){
		Calendar now = DateUtil.getCurrentTime();
		SysmgrLogininfoLogBO bo = new SysmgrLogininfoLogBO();
		bo.setId(UUIDUtil.getUUID());
		bo.setSessionId(HttpSessionUtil.getId());
		bo.setLoginName(user.getLoginName());
		bo.setUserName(user.getUserName());
		bo.setUserId(user.getUserId());
		bo.setLoginTime(now);
		bo.setLoginIp(PathUtil.getClientIP());
		bo.setServerIp(PathUtil.getServerIP());
		DaoUtil.getInstance().insert(bo);
	}
	
	/**
	 * TODO 登出时调用。
	 * @param user
	 */
	public void logLogout(){
		String sql = "update sysmgr_logininfo_log t set t.exit_time=sysdate where t.session_id=?";
		DaoUtil.getInstance().execute(sql, HttpSessionUtil.getId());
	}
}
