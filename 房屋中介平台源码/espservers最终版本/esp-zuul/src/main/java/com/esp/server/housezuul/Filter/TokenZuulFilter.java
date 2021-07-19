package com.esp.server.housezuul.Filter;

import com.alibaba.fastjson.JSONObject;
import com.esp.server.housezuul.result.Message;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * @ProjectName: espservers
 * @Auther: GERRY
 * @Date: 2018/11/27 21:44
 * @Description:
 */
@Component
public class TokenZuulFilter extends ZuulFilter {

    @Autowired
    private RestTemplate restTemplate;

    // 定义非鉴权列表
    private static String[] noCheckList = {
            "/signin",
            "/logout",
            "/register",
            "/accounts/upload",
            "/accounts/verify",
            "/remember",
            "/reset",
            "/newest",
            "/house/list",
            "/house/hot",
            "house/detail",
            "/agency/detail",
            "/rating",
            "/blog/list",
            "/blog/one",
            "comment/list"
    };

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 过滤执行的顺序
     * @return
     */
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1;
    }

    /**
     * 是否执行run方法逻辑(false不执行，如果true执行run方法)
     * @return
     */
    public boolean shouldFilter() {
        // 获取请求对象
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        // 获取当前请求的路径
        String requestURI = request.getRequestURI();
        // 判断请求路径是否需要处理
        for (String uri : noCheckList) {
            if (requestURI.contains(uri)) {
                // 不执行鉴权操作
                return false;
            }
        }

        // 执行鉴权操作
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        // 获取请求对象
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        // 获取头中的token执行
        String token = request.getHeader("token");

        if (token == null) {
            // 不允许请求后端服务器
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        } else {
            // 鉴权验证token是否合法
            String url = "http://user-service/token/verify?token="+token;
            String json = restTemplate.getForObject(url, String.class);
            Message message = JSONObject.parseObject(json, Message.class);
            if ("gerry".equals(message.getData())) {
                // 鉴权成功
                // 允许请求后端服务器
                context.setSendZuulResponse(true);
            } else {
                // 不允许请求后端服务器
                context.setSendZuulResponse(false);
                context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            }
        }

        return null;
    }
}
