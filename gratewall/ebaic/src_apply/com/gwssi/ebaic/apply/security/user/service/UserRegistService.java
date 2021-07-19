package com.gwssi.ebaic.apply.security.user.service;

import org.springframework.stereotype.Service;

import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.util.StringUtil;

@Service(value = "userRegistService")
public class UserRegistService {

	public boolean checkUser(String loginName)  {
		if (StringUtil.isBlank(loginName)) {
			return false;
		}
		long count = DaoUtil.getInstance().queryForOneLong("select count(1) as cnt from t_pt_yh u where UPPER(u.login_name) = ?",
				loginName.toUpperCase());
		return count > 0;
	}
	
	public boolean checkMobile(String mobile){
		if (StringUtil.isBlank(mobile)) {
			return false;
		}
		long count = DaoUtil.getInstance().queryForOneLong("select count(1) from t_pt_yh where mobile = ? ", mobile);
		
		return count > 0;
	}
}
