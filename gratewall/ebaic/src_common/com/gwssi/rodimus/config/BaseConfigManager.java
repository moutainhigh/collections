package com.gwssi.rodimus.config;

import com.gwssi.rodimus.cache.CacheUtil;
/**
 * 配置基类。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public abstract class BaseConfigManager<T> {
	
	//public abstract BaseConfigManager<T> getInstance();
	
	protected abstract String getCachePrefix();

	/**
	 * @param valCode
	 * @return
	 */
	public abstract T getConfigFromDb(String key);
	
	
	private String getCacheKey(String key){
		String ret = String.format("%s%s", getCachePrefix(),key);
		return ret;
	}
	
	public T getConfig(String key){
		T ret = getConfigFromCache(key);
		if(ret==null){
			ret = getConfigFromDb(key);
			if(ret!=null){
				//String cacheKey = getCacheKey(key);
				//CacheUtil.put(cacheKey, ret);
			}
		}
		return ret;
	}
	
//	@SuppressWarnings("unchecked")
	public T getConfigFromCache(String ruleCode){
		return null;
//		T ret = null;
//		String cacheKey = getCacheKey(ruleCode);
//		Object o = CacheUtil.get(cacheKey);
//		if(o!=null){
//			ret = (T)o;
//		}
//		return ret;
	}
	
	/**
	 * 让缓存失效。
	 * 
	 * @param ruleCode
	 * @return
	 */
	public void setConfigRotten(String ruleCode){
		String cacheKey = getCacheKey(ruleCode);
		CacheUtil.put(cacheKey, null);
	}
	
	
}
