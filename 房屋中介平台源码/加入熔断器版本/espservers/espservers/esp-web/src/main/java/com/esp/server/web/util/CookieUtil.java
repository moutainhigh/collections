package com.esp.server.web.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ProjectName: espservers
 * @Auther: GERRY
 * @Date: 2018/11/27 21:07
 * @Description: Cookie工具类
 */
public class CookieUtil {
    public static final String USER_TOKEN = "token";

    /**
     * 设置cookie方法
     * @param token
     * @param response
     */
    public static void setToken(String token, HttpServletResponse response) {
        Cookie cookie = new Cookie(USER_TOKEN, token);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 获取cookie中的token值
     * @param request
     * @return
     */
    public static String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (USER_TOKEN.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    /**
     * 删除cookie方法
     * @param response
     */
    public static void delToken(HttpServletResponse response) {
        Cookie cookie = new Cookie(USER_TOKEN, null);
        cookie.setPath("/");
        cookie.setMaxAge(0); // 0 失效， -1 永久
        response.addCookie(cookie);
    }
}
