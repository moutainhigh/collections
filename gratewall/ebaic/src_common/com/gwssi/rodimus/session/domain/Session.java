package com.gwssi.rodimus.session.domain;

import java.io.Serializable;

import com.gwssi.optimus.core.cache.CacheBlock;
import com.gwssi.optimus.core.cache.CacheElement;
import com.gwssi.rodimus.cache.CacheUtil;
import com.gwssi.rodimus.exception.RodimusException;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.rodimus.util.UUIDUtil;
/**
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class Session implements Serializable {
	
	private static final long serialVersionUID = 1055965810150154404L;
	
	/**Session ID*/
    private final String              id;
    /**Session创建时间*/
    private long                creationTime;
    /**Session最后一次访问时间*/
    private long                lastAccessedTime;
    /**Session的最大空闲时间间隔*/
    private int                 maxInactiveInterval;
    /**是否是新建Session*/
    private boolean             newSession;
    
    private static final String SESSION_KEY_PREFIX = "SESS_";
    
    private final String sessionKey ;

	/**
	 * 创建新的Session。
	 * @param maxIdleSeconds
	 */
	public Session(int maxIdleSeconds){
    	id = UUIDUtil.getUUID();
    	long now = System.currentTimeMillis();
    	creationTime = now;
    	lastAccessedTime = now;
    	this.maxInactiveInterval = maxIdleSeconds;
    	newSession = true;
    	//this.attrNameSet.clear();
    	
    	sessionKey = SESSION_KEY_PREFIX + id;
    	CacheBlock cb = CacheUtil.getCacheBlock();
    	CacheElement ce = new CacheElement(sessionKey,this);
    	ce.setTimeToIdleSeconds(this.getMaxInactiveInterval());
    	cb.put(ce);
    }
    
	/**
	 * 通过Session id获取已经存在的Session，如果没有，返回null。
	 * @return
	 */
	public static Session get(String id){
		if(StringUtil.isBlank(id)){
			throw new RodimusException("Session id is null when get Session.");
		}
		String sessionKey = SESSION_KEY_PREFIX + id;
		CacheBlock cb = CacheUtil.getCacheBlock();
		Session ret = (Session) cb.get(sessionKey);
		if(ret!=null){
//			if(ret.isExpired()){
//				ret.invalidate();
//				return null;
//			}
			ret.newSession = false;
			ret.refresh();
		}
		return ret;
	}
	/**
	 * 更新 lastAccessedTime 。
	 */
	public void refresh() {
		this.lastAccessedTime = System.currentTimeMillis();
		CacheBlock cb = CacheUtil.getCacheBlock();
    	CacheElement ce = new CacheElement(sessionKey,this);
    	ce.setTimeToIdleSeconds(this.getMaxInactiveInterval());
    	cb.put(ce);
	}
	/**
	 * 是否超时过期。
	 * 
	 * @param session
	 * @return
	 */
	public boolean isExpired() {
		CacheBlock cb = CacheUtil.getCacheBlock();
		Session _this = (Session) cb.get(this.sessionKey);
		if(_this==null){//未从缓存中取到数据，过期了
			return true;
		}
		long now = System.currentTimeMillis();
		long last = this.getLastAccessedTime();
		long interal = now - last;
		if(interal>this.getMaxInactiveInterval()){
			return true;//过期了
		}else{
			return false;
		}
	}
    /**
     * 强制Session立即失效。
     */
    public synchronized void invalidate() {
    	CacheBlock cb = CacheUtil.getCacheBlock();
		cb.remove(this.sessionKey);
	}

	/**
	 * 移除属性。
	 * 
	 * @param attrName
	 * @return
	 */
	public synchronized Object removeAttribute(String attrName){
		this.refresh();
    	String attrSessionKey = getAttrSessionKey(attrName);
    	CacheBlock cb = CacheUtil.getCacheBlock();
    	Object ret = cb.remove(attrSessionKey);
    	return ret;
    }
    
    /**
     * 设置属性。
     * @param attrName
     * @param attrValue
     */
    public synchronized void setAttribute(String attrName,Object attrValue){
    	this.refresh();
    	String attrSessionKey = getAttrSessionKey(attrName);
    	CacheBlock cb = CacheUtil.getCacheBlock();
    	CacheElement ce = new CacheElement(attrSessionKey,attrValue);
    	ce.setTimeToIdleSeconds(this.getMaxInactiveInterval());
    	cb.put(ce);
    }
    
    /**
     * 获取属性的值。
     * @param attrName
     * @return
     */
    public Object getAttribute(String attrName){
    	this.refresh();
    	String attrSessionKey = getAttrSessionKey(attrName);
    	CacheBlock cb = CacheUtil.getCacheBlock();
    	Object retObject = cb.get(attrSessionKey);
    	return retObject;
    }
    
    
    
    
    private String getAttrSessionKey(String attrName){
    	String attrSessionKey = sessionKey + attrName;
    	return attrSessionKey;
    }
    
    public int getMaxInactiveInterval() {
    	if(maxInactiveInterval==-1){
    		maxInactiveInterval = 3600;
    	}
		return maxInactiveInterval;
	}

	public void setMaxInactiveInterval(int maxInactiveInterval) {
		this.maxInactiveInterval = maxInactiveInterval;
	}

	public String getId() {
		return id;
	}
    
	public long getCreationTime() {
		return creationTime;
	}

	public long getLastAccessedTime() {
		return lastAccessedTime;
	}
	
	public boolean isNewSession() {
		return newSession;
	}


}
