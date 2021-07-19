package com.gwssi.rodimus.rule.config;

import java.util.List;
import java.util.Map;

import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.rodimus.cache.CacheUtil;
import com.gwssi.rodimus.dao.RuleConfigDaoUtil;
import com.gwssi.rodimus.rule.RuleUtil;

/**
 * 读取配置。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class RuleAppManager {
	
	private static final String cachePrefix = "rule_app_";
	
	private static String getCacheKey(String appCode){
		String ret = String.format("%s%s", cachePrefix ,appCode);
		return ret;
	}
	
	public static List<Map<String, Object>> getConfig(String appCode){
		List<Map<String, Object>> ret = getConfigFromCache(appCode);
		if(ret==null){
			ret = getConfigFromDb(appCode);
			if(ret!=null){
				String cacheKey = getCacheKey(appCode);
				CacheUtil.put(cacheKey, ret);
			}
		}
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> getConfigFromCache(String appCode){

		if(!RuleUtil.isReadConfigFromCache){
			return null;
		}
		
		List<Map<String, Object>> ret = null;
		String cacheKey = getCacheKey(appCode);
		Object o = CacheUtil.get(cacheKey);
		if(o!=null){
			ret = (List<Map<String, Object>>)o;
		}
		return ret;
	}
	
	/**
	 * 让缓存失效。
	 * 
	 * @param valCode
	 * @return
	 */
	public static void setConfigRotten(String appCode){
		String cacheKey = getCacheKey(appCode);
		CacheUtil.put(cacheKey, null);
	}
	
	/**
	 * @param appCode
	 * @return
	 */
	public static List<Map<String, Object>> getConfigFromDb(String appCode){
		StringBuffer sql = new StringBuffer();
		sql.append(" select ra.sn,r.rule_id,r.rule_code,r.rule_name,ra.triger_expr from sys_rule_app a ");
		sql.append(" left join sys_rule_to_app ra on a.app_id=ra.app_id ");
		sql.append(" left join sys_rule r on ra.rule_id=r.rule_id ");
		sql.append(" where a.flag='1' and ra.flag='1' and r.flag='1' and a.app_code = ? ");
		sql.append(" order by to_number(ra.sn) asc ");
		IPersistenceDAO dao = RuleConfigDaoUtil.getInstance().getDao();
		List<Map<String, Object>> list = RuleConfigDaoUtil.getInstance().queryForList(dao, sql.toString(), appCode);
		return list;
	}
}
