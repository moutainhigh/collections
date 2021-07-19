package com.gwssi.optimus.plugin.auth.model;

import java.util.List;

public class User {
    
    private String userId;  
    private String loginName;
    private List<String> roleIdList;
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getLoginName() {
        return loginName;
    }
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    public List<String> getRoleIdList() {
        return roleIdList;
    }
    public void setRoleIdList(List<String> roleIdList) {
        this.roleIdList = roleIdList;
    }

}
