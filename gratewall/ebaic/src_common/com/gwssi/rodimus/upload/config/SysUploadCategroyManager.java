package com.gwssi.rodimus.upload.config;

import com.gwssi.rodimus.config.BaseConfigManager;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.upload.domain.SysUploadCategoryBO;

/**
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class SysUploadCategroyManager extends BaseConfigManager<SysUploadCategoryBO> {
	public static SysUploadCategroyManager instance = new SysUploadCategroyManager();
	
	@Override
	protected String getCachePrefix() {
		return "upload_config_";
	}

	@Override
	public SysUploadCategoryBO getConfigFromDb(String key) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from sys_upload_category t where t.flag='1' and t.category_id = ?");
		SysUploadCategoryBO ret = DaoUtil.getInstance().queryForRowBo(sql.toString(), SysUploadCategoryBO.class, key);
		return ret;
	}

}
