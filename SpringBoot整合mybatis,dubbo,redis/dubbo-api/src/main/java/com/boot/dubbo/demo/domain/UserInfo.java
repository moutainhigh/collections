package com.boot.dubbo.demo.domain;

import java.io.Serializable;

/**
 * 用户信息类
 */
public class UserInfo implements Serializable {
    private String account;
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
