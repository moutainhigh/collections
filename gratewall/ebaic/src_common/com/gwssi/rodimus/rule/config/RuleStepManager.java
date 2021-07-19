package com.gwssi.rodimus.rule.config;

import java.util.List;

import com.gwssi.rodimus.cache.CacheUtil;
import com.gwssi.rodimus.dao.RuleConfigDaoUtil;
import com.gwssi.rodimus.rule.RuleUtil;
import com.gwssi.rodimus.rule.domain.SysRuleStep;

/**
 * 配置读取。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class RuleStepManager {
	
	private static final String cachePrefix = "rule_step_";
	
	private static String getCacheKey(String ruleId){
		String ret = String.format("%s%s", cachePrefix,ruleId);
		return ret;
	}
	
	public static List<SysRuleStep> getConfig(String ruleId){
		List<SysRuleStep> ret = getConfigFromCache(ruleId);
		if(ret==null){
			ret = getConfigFromDb(ruleId);
			if(ret!=null){
				//String cacheKey = getCacheKey(ruleId);
				//CacheUtil.put(cacheKey, ret);
			}
		}
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public static List<SysRuleStep> getConfigFromCache(String ruleId){

		if(!RuleUtil.isReadConfigFromCache){
			return null;
		}
		List<SysRuleStep> ret = null;
		String cacheKey = getCacheKey(ruleId);
		Object o = CacheUtil.get(cacheKey);
		if(o!=null){
			ret = (List<SysRuleStep>)o;
		}
		return ret;
	}
	
	/**
	 * 让缓存失效。
	 * 
	 * @param ruleId
	 * @return
	 */
	public static void setConfigRotten(String ruleId){
		String cacheKey = getCacheKey(ruleId);
		CacheUtil.put(cacheKey, null);
	}
	
	/**
	 * @param valCode
	 * @return
	 */
	public static List<SysRuleStep> getConfigFromDb(String ruleId){
		StringBuffer sql = new StringBuffer();
		sql.append("select * from sys_rule_step t where t.flag='1' and t.rule_id = ? order by to_number(t.sn) asc");
		List<SysRuleStep> ret = RuleConfigDaoUtil.getInstance().queryForListBo(sql.toString(), SysRuleStep.class, ruleId);
		return ret;
	}
}
