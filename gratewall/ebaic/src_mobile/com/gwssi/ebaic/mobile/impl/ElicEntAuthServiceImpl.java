package com.gwssi.ebaic.mobile.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.ebaic.mobile.api.ElicEntAuthService;
import com.gwssi.rodimus.dao.BaseDaoUtil;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.RpcException;
import com.gwssi.rodimus.util.MapUtil;
@Service(value="elicEntAuthServiceImpl")
public class ElicEntAuthServiceImpl implements ElicEntAuthService {
	
	
	public List<Map<String, String>> getList(String uniScid, int authState,
			int pageIndex, int pageSize) {
		if(StringUtils.isBlank(uniScid)){
			throw new RpcException("统一社会信用代码不能为空。");
		}
		//更新失效信息
		updateInvalidRecord(uniScid);
		StringBuffer sql = new StringBuffer(" select a.auth_code,a.oper_code,a.auth_state, ");
		sql.append(" to_char(a.validate_code_start_time,'yyyy-mm-dd hh24:mi:ss') as validate_code_start_time ");
		sql.append(" from cp_account_auth a where a.reg_no=? ");
		sql.append(" order by a.validate_code_start_time desc ");
		List<String> params = new ArrayList<String>();
		params.add(uniScid);
		BaseDaoUtil dao = DaoUtil.getInstance();
		List<Map<String, Object>> ret = null;
		if(pageSize==-1){
			// 查询所有记录
			ret = dao.queryForList(sql.toString(), params);
		}else{
			// 分页查询
			pageSize = pageSize<1 ? 10 : pageSize;
			pageIndex  = pageIndex<1 ? 1 : pageIndex;
			ret = dao.pageQueryForList(sql.toString(), params, pageSize, pageIndex);
		}
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (Map<String, Object> map : ret) {
			Map<String, String> strMap = MapUtil.oMap2strMap(map);
			list.add(strMap);
		}
		return list;
	}
	
	/**
	 * <h3>更新失效的认证服务信息</h3>
	 * 
	 * @param regNo
	 */
	public void updateInvalidRecord(String uniScid){
		if(StringUtils.isBlank(uniScid)){
			throw new RpcException("统一社会信用代码不能为空。");
		}
		StringBuffer sql = new StringBuffer(" update cp_account_auth a ");
		sql.append(" set a.auth_state = '2' ");
		sql.append(" where a.reg_no = ? ");
		sql.append(" and a.auth_state is null ");
		sql.append(" and sysdate > a.validate_code_end_time ");
		List<String> params = new ArrayList<String>();
		params.add(uniScid);
		BaseDaoUtil dao = DaoUtil.getInstance();
		dao.execute(sql.toString(), params);
	}

}
