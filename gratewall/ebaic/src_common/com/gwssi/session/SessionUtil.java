package com.gwssi.session;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gwssi.rodimus.session.domain.Session;
import com.gwssi.rodimus.util.RequestUtil;
import com.gwssi.rodimus.util.StringUtil;

/**
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class SessionUtil {

	public static String DEFAULT_SESSION_KEY = "jsessionid";
	public static int DEFAULT_MAX_INTERAL = 60 * 40;// in seconds

	/**
	 * 
	 * @param id
	 * @param create
	 * @return
	 */
	public static Session getSession(String id, boolean create) {
		Session ret = Session.get(id);
		if (ret == null) {
			if (create) {
				ret = new Session(DEFAULT_MAX_INTERAL);
			}
		}
		return ret;
	}

	/**
	 * 
	 * @param create
	 * @return
	 */
	public static Session getSession(boolean create) {

		HttpServletRequest request = RequestUtil.getHttpRequest();
		String sessionId = getSessionId(request);

		if (StringUtil.isBlank(sessionId)) {
			if (create) {
				Session ret = new Session(DEFAULT_MAX_INTERAL);
				return ret;
			} else {
				return null;
			}
		} else {
			Session ret = getSession(sessionId, create);
			return ret;
		}
	}

	/**
	 * 获取Session对象。
	 * 
	 * @return
	 */
	public static Session getSession() {
		Session ret = getSession(true);
		return ret;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	protected static String getSessionId(HttpServletRequest request) {
		if (request == null) {
			return null;
		}
		HttpSession httpSession = request.getSession(false);
		if (httpSession != null) {
			String ret = httpSession.getId();
			return ret;
		} else {
			String ret = getCookie(request, DEFAULT_SESSION_KEY);
			if (StringUtil.isBlank(ret)) {
				ret = request.getParameter(DEFAULT_SESSION_KEY);
			}
			return ret;
		}
	}

	/**
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getCookie(HttpServletRequest request, String key) {
		if (request == null) {
			return null;
		}
		if (StringUtil.isBlank(key)) {
			return null;
		}
		Cookie[] cookies = request.getCookies();
		if (cookies == null || cookies.length == 0) {
			return null;
		}
		for (Cookie c : cookies) {
			if (key.equals(c.getName())) {
				String ret = c.getValue();
				return ret;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static void setCookie(HttpServletResponse response, String key,String value) {
		Cookie sessionId = new Cookie(key, value);
		sessionId.setPath("/");
		response.addCookie(sessionId);
	}
	
	/**
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static void removeCookie(HttpServletResponse response, String key) {
		Cookie sessionId = new Cookie(key, null);
		sessionId.setMaxAge(0);
		sessionId.setPath("/");
		response.addCookie(sessionId);
	}
}
