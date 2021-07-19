package com.gwssi.optimus.plugin.auth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service
public class UserService extends BaseService {

	public Map<String, String> getUser(String userId) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		String sql = "select t.* from t_pt_yh t,t_pt_js a where t.USER_ID = '"
				+ userId + "'";
		String sql2 = "select a.JS_MC as name from t_pt_yhjs t,t_pt_js a "
				+ "where a.JS_ID=t.JS_ID and t.USER_ID='" + userId + "'";
		List<Map> list = dao.queryForList(sql, null);
		List<Map> list2 = dao.queryForList(sql2, null);
		String roleList = "";
		for (int i = 0; i < list2.size(); i++) {
			roleList = roleList + (String) list.get(i).get("name");
			if (i != list2.size()) {
				roleList = roleList + ",";
			}
		}
		Map<String, String> map = list.get(0);
		map.put("jsId", roleList);
		return map;
	}

	public List<Map> queryUser(Map params) throws OptimusException {
		String loginName = (String) params.get("loginName");
		String userType = (String) params.get("userType");
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append("select t.*, a.JS_MC as zyJs "
				+ "from t_pt_yh t left join t_pt_js a "
				+ "on t.ZY_JS_ID=a.JS_ID where t.yx_bj='1'");
		if (StringUtils.isNotBlank(loginName)) {
			sql.append(" and t.LOGIN_NAME = '" + loginName + "'");
		}
		if (StringUtils.isNotBlank(userType)) {
			sql.append(" and t.user_type='" + userType + "'");
		}
		List<Map> userList = dao.pageQueryForList(sql.toString(), null);
		return userList;
	}

}
