package com.gwssi.rodimus.doc.v1.config;

import java.util.List;
import java.util.Map;

import com.gwssi.rodimus.config.BaseConfigManager;
import com.gwssi.rodimus.dao.DaoUtil;

/**
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class SysDocChapListManager extends BaseConfigManager<List<Map<String, Object>>> {
	
	public static SysDocChapListManager instance = new SysDocChapListManager();
	
	@Override
	protected String getCachePrefix() {
		return "chap_list_";
	}

	@Override
	public List<Map<String, Object>> getConfigFromDb(String docCode) {
		StringBuffer sql = new StringBuffer();
		sql.append("select r.chap_config_id,d.href,r.sn,r.triger_expr,d.title,d.template_url from sys_doc_config l  ")
			.append(" left join sys_doc_doc_to_chap r on l.doc_config_id=r.doc_config_id ")
			.append(" left join sys_doc_chapter_config d on r.chap_config_id=d.chap_config_id ")
			.append(" where l.flag='1' and r.flag='1' and d.flag='1' ")
			.append(" and l.doc_code = ? order by r.sn asc ");
   
		List<Map<String, Object>> list = DaoUtil.getInstance().queryForList(sql.toString(), docCode);
		return list;
	}

}
