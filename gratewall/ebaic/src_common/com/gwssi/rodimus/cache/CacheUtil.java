package com.gwssi.rodimus.cache;

import com.gwssi.optimus.core.cache.CacheBlock;
import com.gwssi.optimus.core.cache.CacheManager;
import com.gwssi.rodimus.util.StringUtil;

/**
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class CacheUtil {
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public static Object get(String key){
		CacheBlock cb = getCacheBlock();
		Object o = cb.get(key);
		return o;
	}
	/**
	 * 
	 * @param key
	 * @return
	 */
	public static String getString(String key){
		Object o = get(key);
		String ret = StringUtil.safe2String(o);
		return ret;
	}
	/**
	 * 
	 * @param key
	 * @param o
	 * @return
	 */
	public static Object put(String key,Object o){
		CacheBlock cb = getCacheBlock();
		Object oldValue = cb.put(key, o);
		return oldValue;
	}

	public static int DEFAULT_REDIS_DB_IDX = 6;
	
	/**
	 * 只限特殊情况使用。
	 * 
	 * @return
	 */
	public static CacheBlock getCacheBlock(){
		
		CacheBlock cb = CacheManager.getBlock(DEFAULT_REDIS_DB_IDX);
		return cb;
	}

}
