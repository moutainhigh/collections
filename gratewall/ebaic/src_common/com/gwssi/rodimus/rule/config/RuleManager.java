package com.gwssi.rodimus.rule.config;


import com.gwssi.rodimus.cache.CacheUtil;
import com.gwssi.rodimus.dao.RuleConfigDaoUtil;
import com.gwssi.rodimus.rule.RuleUtil;
import com.gwssi.rodimus.rule.domain.SysRule;

/**
 * 读取配置。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class RuleManager {
	
	private static final String cachePrefix = "rule_";
	
	private static String getCacheKey(String ruleCode){
		String ret = String.format("%s%s", cachePrefix,ruleCode);
		return ret;
	}
	
	public static SysRule getConfig(String ruleCode){
		SysRule ret = getConfigFromCache(ruleCode);
		if(ret==null){
			ret = getConfigFromDb(ruleCode);
			if(ret!=null){
				String cacheKey = getCacheKey(ruleCode);
				CacheUtil.put(cacheKey, ret);
			}
		}
		return ret;
	}
	
	public static SysRule getConfigFromCache(String ruleCode){

		if(!RuleUtil.isReadConfigFromCache){
			return null;
		}
		SysRule ret = null;
		String cacheKey = getCacheKey(ruleCode);
		Object o = CacheUtil.get(cacheKey);
		if(o!=null){
			ret = (SysRule)o;
		}
		return ret;
	}
	
	/**
	 * 让缓存失效。
	 * 
	 * @param ruleCode
	 * @return
	 */
	public static void setConfigRotten(String ruleCode){
		String cacheKey = getCacheKey(ruleCode);
		CacheUtil.put(cacheKey, null);
	}
	
	/**
	 * @param valCode
	 * @return
	 */
	public static SysRule getConfigFromDb(String ruleCode){
		StringBuffer sql = new StringBuffer();
		sql.append("select * from sys_rule t where t.flag='1' and t.rule_code = ?");
		SysRule ret = RuleConfigDaoUtil.getInstance().queryForRowBo(sql.toString(), SysRule.class, ruleCode);
		return ret;
	}
	
}
