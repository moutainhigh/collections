package com.gwssi.session;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.apache.commons.lang.StringUtils;

import com.gwssi.optimus.core.common.ThreadLocalManager;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;

@SuppressWarnings("deprecation")
public class HttpTorchSession implements HttpSession {

	private String sessionKey;
	private ConcurrentHashMap<String, Object> sessionMap;

	public HttpTorchSession() {
		HttpServletRequest req = (HttpServletRequest) ThreadLocalManager.get("http_request");
		HttpServletResponse response = (HttpServletResponse) ThreadLocalManager.get("http_response");
		sessionKey = SessionUtil.getCookie(req, SessionConstants.COOKIE_KEY);
		// 如果集群session同步Id不存在，初始化cookie
		if (StringUtils.isBlank(sessionKey)) {
			sessionKey = req.getSession(true).getId();
			SessionUtil.setCookie(response, SessionConstants.COOKIE_KEY,sessionKey);
			this.sessionMap = new ConcurrentHashMap<String, Object>();
			// 将sessionMap 放入 缓存
			RedisUtils.set(sessionKey, sessionMap);
		}

	}

	@SuppressWarnings("unchecked")
	public void setAttribute(String key, Object value) {

		Object obj = RedisUtils.get(this.sessionKey.getBytes());
		if (obj == null) {
			this.sessionMap = new ConcurrentHashMap<String, Object>();
			sessionMap.put(key, value);
			// 将sessionMap 放入缓存
			RedisUtils.set(sessionKey, sessionMap);
		} else {
			ConcurrentHashMap<String, Object> sessMap = (ConcurrentHashMap<String, Object>) obj;
			sessMap.put(key, value);
			// 将sessionMap 放入 缓存
			RedisUtils.set(sessionKey, sessMap);
		}
	}

	@SuppressWarnings("unchecked")
	public Object getAttribute(String key) {
		Object obj = RedisUtils.get(this.sessionKey.getBytes());
		HttpServletResponse response = (HttpServletResponse) ThreadLocalManager.get("http_response");
		if (obj == null) {
			SessionUtil.removeCookie(response, SessionConstants.COOKIE_KEY);
			throw new RuntimeException("用户登录信息已失效");
		} else {
			ConcurrentHashMap<String, Object> sessMap = (ConcurrentHashMap<String, Object>) obj;
			RedisUtils.expire(sessionKey, SessionConstants.EXPIRE_SECONDS);
			return sessMap.get(key);
		}
	}
	

	public void removeAttribute(String key) {
		if (key.equals(OptimusAuthManager.USER)) {
			HttpServletResponse response = (HttpServletResponse) ThreadLocalManager.get("http_response");
			if (sessionMap!=null) {
				sessionMap.clear();
			}
			RedisUtils.delete(sessionKey.getBytes());
			SessionUtil.removeCookie(response, SessionConstants.COOKIE_KEY);
		}
	}

	public Enumeration<String> getAttributeNames() {
		// Enumeration<String> enums=n
		return null;
	}

	public long getCreationTime() {

		return 0;
	}

	public String getId() {

		return this.sessionKey;
	}

	public long getLastAccessedTime() {

		return 0;
	}

	public int getMaxInactiveInterval() {

		return 0;
	}

	public ServletContext getServletContext() {

		return null;
	}

	public HttpSessionContext getSessionContext() {

		return null;
	}

	public Object getValue(String arg0) {

		return null;
	}

	public String[] getValueNames() {

		return null;
	}

	public void invalidate() {

	}

	public boolean isNew() {

		return false;
	}

	public void putValue(String arg0, Object arg1) {

	}

	public void removeValue(String key) {

	}

	public void setMaxInactiveInterval(int arg0) {

	}

}
