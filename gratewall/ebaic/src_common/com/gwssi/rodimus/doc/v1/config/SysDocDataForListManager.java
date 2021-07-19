package com.gwssi.rodimus.doc.v1.config;

import java.util.List;

import com.gwssi.rodimus.config.BaseConfigManager;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.doc.v1.domain.SysDocDataConfig;

/**
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 * @param <SysDocData>
 */
public class SysDocDataForListManager extends BaseConfigManager<List<SysDocDataConfig>> {
	public static SysDocDataForListManager instance = new SysDocDataForListManager();
	
	@Override
	protected String getCachePrefix() {
		return "doc_docdata_";
	}

	@Override
	public List<SysDocDataConfig> getConfigFromDb(String key) {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.* from sys_doc_data_config t where t.doc_config_id=? order by t.sn asc");
		List<SysDocDataConfig> ret = DaoUtil.getInstance().queryForListBo(sql.toString(), SysDocDataConfig.class, key);
		return ret;
	}

}
