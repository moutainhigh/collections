package com.esp.server.userserver.constant;

import lombok.Getter;

/**
 * @ProjectName: user-service
 * @Auther: GERRY
 * @Date: 2018/11/15 20:32
 * @Description:
 */
@Getter
public enum UserEnum {
    // 用户激活状态
    ACTIVE(1, "已激活"),
    DISABLE(0, "未激活"),
    EMAIL_REGISTERED(300001, "邮件已经注册"),
    EMAIL_INVALID(300002, "激活邮件的连接已经过期");

    private int code;
    private String msg;

    UserEnum(int code, String msg) {
        this.msg = msg;
        this.code = code;
    }
}
