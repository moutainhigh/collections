package com.gwssi.rodimus.step.config;

import java.util.List;

import com.gwssi.rodimus.config.BaseConfigManager;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.step.domain.SysStepConfigBO;

public class StepConfigManager extends BaseConfigManager<List<SysStepConfigBO>> {

	public static StepConfigManager instance = new StepConfigManager();
	
	@Override
	protected String getCachePrefix() {
		return "step_";
	}

	@Override
	public List<SysStepConfigBO> getConfigFromDb(String configId) {
		String sql = "select * from sys_step_config c where c.config_id=? and c.flag='1' order by sn asc";
		List<SysStepConfigBO> ret = DaoUtil.getInstance().queryForListBo(sql, SysStepConfigBO.class, configId);
		return ret;
	}

}
