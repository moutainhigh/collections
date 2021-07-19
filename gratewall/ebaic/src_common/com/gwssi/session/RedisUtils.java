package com.gwssi.session;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

import com.gwssi.optimus.core.cache.redis.RedisManager;

import redis.clients.jedis.Jedis;

public class RedisUtils {

	protected final static Logger logger = Logger.getLogger(HttpTorchSessionListener.class);
	
	public static void set(String key,Object value){
		Jedis jedis=null;
		try {
			jedis=RedisManager.getResource(SessionConstants.DB_INDEX);
			if (value instanceof String) {
				jedis.set(key, String.valueOf(value));
				jedis.expire(key, SessionConstants.EXPIRE_SECONDS);
			}else {
				jedis.set(key.getBytes(), RedisUtils.serialize(value));
				jedis.expire(key.getBytes(), SessionConstants.EXPIRE_SECONDS);
			}
			logger.info("redis存值:"+key);
			
		} catch (Exception e) {
			e.printStackTrace();
			RedisManager.returnResource(jedis);
		}finally{
			RedisManager.returnResource(jedis);
		}
	}
	
	public static void expire(String key,int secends){
		Jedis jedis=null;
		try {
			jedis=RedisManager.getResource(SessionConstants.DB_INDEX);
			jedis.expire(key.getBytes(), secends);
		} catch (Exception e) {
			e.printStackTrace();
			RedisManager.returnResource(jedis);
		}finally{
			RedisManager.returnResource(jedis);
		}
	}
	
	public static String get(String key){
		Jedis jedis=null;
		try {
			jedis=RedisManager.getResource(SessionConstants.DB_INDEX);
			return jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			RedisManager.returnResource(jedis);
			return null;
		}finally{
			RedisManager.returnResource(jedis);
		}
	}
	
	public static Object get(byte[] key){
		Jedis jedis=null;
		try {
			jedis=RedisManager.getResource(SessionConstants.DB_INDEX);
			logger.info("redis取值:"+key.toString());
			return RedisUtils.unserialize(jedis.get(key));
		} catch (Exception e) {
			e.printStackTrace();
			RedisManager.returnResource(jedis);
			return null;
		}finally{
			RedisManager.returnResource(jedis);
		}
	}
	
	public static void delete(byte[] key){
		Jedis jedis=null;
		try {
			jedis=RedisManager.getResource(SessionConstants.DB_INDEX);
			logger.info("redis删除:"+key.toString());
			jedis.del(key);
		} catch (Exception e) {
			e.printStackTrace();
			RedisManager.returnResource(jedis);
		}finally{
			RedisManager.returnResource(jedis);
		}
	}
	
	
	
	/**
	 * 序列化对象
	 * 
	 * @param object
	 * @return
	 */
	public static byte[] serialize(Object object) {

		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;

		try {
			// 序列化
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (null != baos) {
					baos.close();
				}
				if (null != oos) {
					oos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;

	}

	/**
	 * 反序列化对象
	 * 
	 * @param bytes
	 * @return
	 */
	public static Object unserialize(byte[] bytes) {

		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			// 反序列化
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
		} finally {
			try {
				if (null != bais) {
					bais.close();
				}
				if (null != ois) {
					ois.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
}
