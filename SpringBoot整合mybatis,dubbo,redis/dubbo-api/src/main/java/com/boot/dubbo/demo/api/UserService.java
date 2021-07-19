package com.boot.dubbo.demo.api;

import com.boot.dubbo.demo.domain.UserInfo;

public interface UserService {
    // 定义用户登录的api
    UserInfo login(UserInfo user);
}
