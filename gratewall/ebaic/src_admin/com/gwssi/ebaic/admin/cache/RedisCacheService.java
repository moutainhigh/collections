package com.gwssi.ebaic.admin.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gwssi.optimus.core.cache.CacheElement;
import com.gwssi.optimus.core.cache.redis.RedisManager;
import com.gwssi.optimus.util.SerializeUtil;
import com.gwssi.rodimus.cache.CacheUtil;
import com.gwssi.rodimus.exception.RodimusException;

import redis.clients.jedis.Jedis;

/**
 * Redis缓存管理。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Service
public class RedisCacheService {

	/**
	 * 统计Redis各项指标。
	 * 
	 * @return
	 */
	public Map<String, Object> stat() {
		return null;
	}

	/**
	 * 得到指定前缀的所有Key和对应的值。
	 * 
	 * @param prefix
	 * @return
	 */
	public List<Map<String, Object>> getListByPrefix(String prefix) {

		List<Map<String, Object>> redisList = new ArrayList<Map<String, Object>>();
		Jedis jedis = null;
		try {
			// 1、根据前台查询的参数获得key的集合
			jedis = RedisManager.getResource(CacheUtil.DEFAULT_REDIS_DB_IDX);
			Set<String> set = jedis.keys(prefix + "*");

			// 2、把key集合转化成数组
			String[] keys = set.toArray(new String[set.size()]);

			// 3、遍历集合 将key-value 存入map
			for (int i = 0; i < keys.length; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				String key = keys[i];
				String data = "";
				if (!"cfg_".equals(prefix)) {
					byte[] dataBytes = jedis.get(key.getBytes());
					if (dataBytes == null || dataBytes.length == 0) {
						data = "";
					} else {
						CacheElement cacheElement = SerializeUtil.unserialize(
								dataBytes, CacheElement.class);
						Object o = cacheElement.getValue();
						data = JSON.toJSONString(o, true);
					}
				} else {
					data = jedis.get(key);
				}
				map.put("key", key);
				map.put("value", data);
				redisList.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RodimusException("Redis操作异常：" + e.getMessage(), e);
		} finally {
			RedisManager.returnResource(jedis);
		}
		return redisList;
	}

	/**
	 * 清空指定key的值
	 * 
	 * @param key
	 */
	public void clear(String... key) {
		Jedis jedis = null;
		try {
			jedis = RedisManager.getResource(8);
			jedis.del(key);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RodimusException("Redis操作异常：" + e.getMessage(), e);
		} finally {
			RedisManager.returnResource(jedis);
		}
	}

	/**
	 * 设定值。
	 * 
	 * @param key
	 * @param value
	 */
	public void set(String key, String value) {

		Jedis jedis = null;
		try {
			jedis = RedisManager.getResource(8);
			jedis.set(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RodimusException("Redis操作异常：" + e.getMessage(), e);
		} finally {
			RedisManager.returnResource(jedis);
		}
	}

	/**
	 * 根据key清理单个redis缓存
	 * 
	 * @param key
	 */
	public void deleterRedis(String key) {
		Jedis jedis = null;
		try {
			jedis = RedisManager.getResource(6);
			jedis.del(key);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RodimusException("Redis操作异常：" + e.getMessage(), e);
		} finally {
			RedisManager.returnResource(jedis);
		}

	}

	/**
	 * 查询redis信息
	 * 
	 * @param key
	 * @return
	 */
	public List<Map<String, String>> getRedisInfo(String info) {
		Jedis jedis = null;
		//String datas = null;
		String data = null;
		List<Map<String, String>> redisInfoList = new ArrayList<Map<String, String>>();
		
		try {
			jedis = RedisManager.getResource(6);
			// 获取所有的redis的info信息
			/*datas = jedis.info();
			System.out.println(datas);*/
			// 获取指定的redis的单个info信息
			data = jedis.info(info);
//			System.out.println(data);

			String[] array = data.split("\r\n");
			for (String line : array) {
				Map<String, String> map = new LinkedHashMap<String, String>();
				String[] cells = line.split(":");
				if (cells.length > 1) {
//					map.put(cells[0], cells[1]);
					map.put("key", cells[0]);
					map.put("value", cells[1]);
					redisInfoList.add(map);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RodimusException("Redis操作异常：" + e.getMessage(), e);
		} finally {
			RedisManager.returnResource(jedis);
		}
		
		return redisInfoList;
	}

	/**
	 * 清理批次缓存
	 * 
	 * @param key
	 */
	public void deleteBatchResdis(String key) {
		Jedis jedis = null;
		try {
			jedis = RedisManager.getResource(6);
			jedis.del(key);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RodimusException("Redis操作异常：" + e.getMessage(), e);
		} finally {
			RedisManager.returnResource(jedis);
		}
		
	}

}
