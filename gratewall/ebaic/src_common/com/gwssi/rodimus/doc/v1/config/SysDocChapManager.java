package com.gwssi.rodimus.doc.v1.config;

import com.gwssi.rodimus.config.BaseConfigManager;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.doc.v1.domain.SysDocChapterConfig;

/**
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class SysDocChapManager extends BaseConfigManager<SysDocChapterConfig> {
	public static SysDocChapManager instance = new SysDocChapManager();
	
	@Override
	protected String getCachePrefix() {
		return "chap_config_";
	}

	@Override
	public SysDocChapterConfig getConfigFromDb(String key) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from sys_doc_chapter_config d where d.flag='1' and d.chap_config_id=?");
		SysDocChapterConfig ret = DaoUtil.getInstance().queryForRowBo(sql.toString(), SysDocChapterConfig.class, key);
		return ret;
	}

}
