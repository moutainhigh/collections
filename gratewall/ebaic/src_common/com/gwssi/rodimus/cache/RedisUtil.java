package com.gwssi.rodimus.cache;

import com.gwssi.optimus.core.cache.redis.RedisManager;

import redis.clients.jedis.Jedis;

/**
 * 只限特殊情况使用。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class RedisUtil {
		
	/**
	 * 
	 * @param key
	 * @return
	 */
	public static String get(String key){
		Jedis jedis = null;
		String ret = null;
		try{
			jedis = RedisManager.getResource(CacheUtil.DEFAULT_REDIS_DB_IDX);
			ret = jedis.get(key);
		}catch(Throwable e){
			e.printStackTrace();
			ret = null;
		}finally{
			if(jedis!=null){
				RedisManager.returnResource(jedis);
			}
		}
		return ret;
	}
	/**
	 * 
	 * @param setKey
	 * @param value
	 * @return
	 */
	public static boolean sIsMember(String setKey,String value){
		Jedis jedis = null;
		boolean ret = false;
		try{
			jedis = RedisManager.getResource(CacheUtil.DEFAULT_REDIS_DB_IDX);
			ret = jedis.sismember(setKey, value);
		}catch(Throwable e){
			e.printStackTrace();
			ret = false;
		}finally{
			if(jedis!=null){
				RedisManager.returnResource(jedis);
			}
		}
		return ret;
	}
}
