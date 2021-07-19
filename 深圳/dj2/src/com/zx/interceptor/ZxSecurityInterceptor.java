package com.zx.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.zx.util.ZxHttpClient;
import com.zx.util.ZxParamUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class ZxSecurityInterceptor implements HandlerInterceptor {

    private String authenUrl;

    private String accessAuthority;

    public ZxSecurityInterceptor() {
        this.authenUrl = ZxParamUtil.getConfigParam("com.zx.ssourl");
        this.accessAuthority = ZxParamUtil.getConfigParam("com.zx.zxauthority");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String access_token = request.getHeader("access_token");
        if (access_token != null && !"".equals(access_token) && authenUrl != null && accessAuthority != null) {
            String userUrl = null;
            if (authenUrl.endsWith("/")) {
                userUrl = authenUrl + "user";
            } else {
                userUrl = authenUrl + "/user";
            }
            String data = ZxHttpClient.getUserInfo(userUrl, access_token);
            if (data != null && !"".equals(data)) {
                JSONObject jsonData = JSONObject.parseObject(data);
                if (jsonData != null && jsonData.containsKey("authorities")) {
                    List<Map> datalist = jsonData.getObject("authorities", List.class);
                    for (Map map : datalist) {
                        String authority = String.valueOf(map.get("authority"));
                        if (authority.equals(accessAuthority)) {
                            return true;
                        }
                    }
                }
            }
        }
        request.setAttribute("msg", "您还没有登录，请先登录！");
        request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
