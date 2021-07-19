package com.gerry.yitao.yitaoauthservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.gerry.yitao.auth.entity.JwtProperties;
import com.gerry.yitao.auth.service.AuthService;
import com.gerry.yitao.common.entity.UserInfo;
import com.gerry.yitao.common.exception.ServiceException;
import com.gerry.yitao.common.util.JwtUtils;
import com.gerry.yitao.domain.User;
import com.gerry.yitao.yitaoauthservice.client.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ProjectName: yitao-parent
 * @Auther: GERRY
 * @Date: 2019/5/6 19:10
 * @Description:
 */
@Slf4j
@Service(timeout = 40000)
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserClient userClient;

    @Autowired(required = false)
    private JwtProperties props;

    public String login(String username, String password) {
        try {
            User user = userClient.queryUser(username, password);
            if (user == null) {
                return null;
            }
            UserInfo userInfo = new UserInfo(user.getId(), user.getUsername());
            //生成Token
            String token = JwtUtils.generateToken(userInfo, props.getPrivateKey(), props.getExpire());

            return token;
        } catch (Exception e) {
            log.error("【授权中心】用户名和密码错误，用户名：{}", username,e);
            throw new ServiceException("用户名或者密码输入错误");
        }
    }
}
