package com.gwssi.rodimus.config;
import com.gwssi.optimus.core.cache.redis.RedisManager;
import com.gwssi.rodimus.cache.CacheUtil;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.util.StringUtil;

import redis.clients.jedis.Jedis;

/**
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class ConfigUtil {
	
	protected static final String CACHE_KEY_PREFIX = "cfg_";
	
	private static String getCacheKey(String key){
		key = CACHE_KEY_PREFIX + key ;
		return key;
	}
	/**
	 * 从数据库读取简单配置，同时更新缓存。
	 * 如果数据库中配置为空，则更新缓存中的值为空。
	 * 
	 * @param key
	 * @return
	 */
	protected static String getFromDb(String key){
		String sql = "select value from sys_config c where c.key = ? and c.flag = '1' ";
		String ret = DaoUtil.getInstance().queryForOneString(sql, key);
		putIntoCache(key, ret);
		return ret ;
	}
	protected static void putIntoCache(String key,String value){
		String cacheKey = getCacheKey(key);
        Jedis jedis = null;
        try {
            jedis = RedisManager.getResource(CacheUtil.DEFAULT_REDIS_DB_IDX);
            jedis.set(cacheKey, value);
            jedis.expire(key, 86400); //one day
        } catch (Exception e) {
        	//throw new RodimusException("保存缓存出错："+e.getMessage(),e);
        	e.printStackTrace();
        } finally{
            RedisManager.returnResource(jedis);
        }
	}
	/**
	 * 
	 * @param key
	 * @return
	 */
	protected static String getFromCache(String key){
		String cacheKey = getCacheKey(key);
		String ret = "";
        Jedis jedis = null;
        try {
            jedis = RedisManager.getResource(CacheUtil.DEFAULT_REDIS_DB_IDX);
            ret = jedis.get(cacheKey);
        } catch (Exception e) {
        	//throw new RodimusException("保存缓存出错："+e.getMessage(),e);
        	e.printStackTrace();
        } finally{
            RedisManager.returnResource(jedis);
        }
		return ret;
	}
	
	/**
	 * 读取简单配置。
	 * 
	 * @param key
	 * @return
	 */
	public static String get(String key){
		String ret = getFromCache(key);
		if(StringUtil.isBlank(ret)){
			// 缓存中没有，从数据库中加载
			ret = getFromDb(key);
		}
		return ret;
	}
	/**
	 * 
	 * @param key
	 * @return
	 */
	public static int getInt(String key,int defaultValue){
		String str = get(key);
		int ret = StringUtil.str2Int(str);
		return ret;
	}
//	/**
//	 * 同时更新数据库和缓存。
//	 * 
//	 * @param key
//	 * @param value
//	 */
//	public static void update(String key,String value){
//		updateDb(key,value);
//		updateCache(key,value);
//	}
	
//	/**
//	 * 加载数据库配置到缓存。
//	 */
//	public static void load(){
//		List<ConfigBo> list = getConfigList();
//		if(list==null || list.isEmpty()){
//			return ;
//		}
//		// TODO 清除所有前缀为 CACHE_KEY_PREFIX 的缓存
//		for(ConfigBo c : list){
//			updateCache(c.getKey(),c.getValue());
//		}
//	}
//	/**
//	 * 
//	 * @return
//	 */
//	public static List<ConfigBo> getConfigList(){
//		String sql = "select * from sys_config c where c.flag = '1' ";
//		List<ConfigBo> list = DaoUtil.getInstance().queryForListBo(sql, ConfigBo.class);
//		return list;
//	}

//	/**
//	 * 
//	 * @param key
//	 * @param value
//	 */
//	protected static void updateCache(String key,String value){
//		String cacheKey = getCacheKey(key) ;
//		CacheUtil.put(cacheKey, value);
//	}
//	protected static void updateDb(String key,String value){
//		String sql = "update sys_config c set c.value = ? where c.key = ?";
//		DaoUtil.getInstance().execute(sql, value, key);
//	}
	
	
	
}
