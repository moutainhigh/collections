package com.esp.server.web.config;


import com.esp.server.web.util.CookieUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @ProjectName: service-demo
 * @Auther: GERRY
 * @Date: 2018/11/22 01:39
 * @Description:
 */
@Configuration
@Slf4j
public class FeignConfig implements RequestInterceptor
{
    @Override
    public void apply(RequestTemplate requestTemplate)
    {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs != null) {
            HttpServletRequest request = attrs.getRequest();
            // 如果在Cookie内通过如下方式取
            Cookie[] cookies = request.getCookies();
            if (cookies != null && cookies.length > 0) {
                for (Cookie cookie : cookies) {
                    log.info("name: {}, value: {}",cookie.getName(), cookie.getValue());
                    if (cookie.getName().equals(CookieUtil.USER_TOKEN)) {
                        // 把cookie中每一个数据放入到请求头中
                        requestTemplate.header(cookie.getName(), cookie.getValue());
                    }
                }
            } else {
                log.warn("FeignHeadConfiguration", "获取Cookie失败！");
            }
        }
    }

}

