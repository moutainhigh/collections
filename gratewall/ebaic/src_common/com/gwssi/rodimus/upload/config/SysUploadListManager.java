package com.gwssi.rodimus.upload.config;

import java.util.List;
import java.util.Map;

import com.gwssi.rodimus.config.BaseConfigManager;
import com.gwssi.rodimus.dao.DaoUtil;

/**
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class SysUploadListManager extends BaseConfigManager<List<Map<String, Object>>> {
	
	public static SysUploadListManager instance = new SysUploadListManager();
	
	@Override
	protected String getCachePrefix() {
		return "upload_list_";
	}

	@Override
	public List<Map<String, Object>> getConfigFromDb(String key) {
		StringBuffer sql = new StringBuffer();
		sql.append("select u.category_id,r.triger_expr,u.title,u.sample_file_id,")
			.append("u.data_sql,u.data_params,u.rule,u.category_type,r.sign_page")
			.append(" from SYS_UPLOAD_LIST l, SYS_UPLOAD_LIST_TO_UPLOAD R, sys_upload_category u")
			.append(" where l.list_id = r.list_id and r.category_id = u.category_id")
			.append(" and l.flag = '1' and r.flag = '1' and u.flag = '1'")
			.append(" and l.list_code = ? order by r.sn asc");
   
		List<Map<String, Object>> list = DaoUtil.getInstance().queryForList(sql.toString(), key);
		return list;
	}

}
