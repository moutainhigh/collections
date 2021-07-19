package com.gwssi.common.sysconfig.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gwssi.common.sysconfig.SystemParamsConfig;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.DAOManager;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.torch.util.StringUtil;

public class SystemParamsConfigDatabaseImpl implements SystemParamsConfig {

	@SuppressWarnings("rawtypes")
	public String get(String key) {
		String sql = "select t.config_value as val from system_params_config t where t.config_key=?";
		List<String> paramsList = new ArrayList<String>();
		paramsList.add(key);
		IPersistenceDAO dao = DAOManager.getPersistenceDAO();
		String ret = "";
		try {
			List<Map> list = dao.queryForList(sql, paramsList);
			if(list!=null && list.size()>0){
				Map row = list.get(0);
				Object o = row.get("val");
				if(o==null){
					ret = "";
				}else if(o instanceof BigDecimal){
					ret = ((BigDecimal)o).doubleValue()+"";
				}else{
					ret = StringUtil.safe2String(o);
				}
			}
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public void reload() {
		// do nothing
	}

	public void reload(String configTypeCode) {
		// do nothing
	}

}
