package com.gwssi.application.webservice.client;


import javax.servlet.http.HttpServletRequest;

import com.gwssi.optimus.plugin.auth.model.User;


/**调用人事webservice接口
 * com.gwssi.application.webservice.receive
 * ReceiveHr.java
 * 上午11:29:23
 * @author wuminghua
 */
public interface IReceiveHrApp {
	
	
	/**通过WebService调用人事系统，获取人员基本信息
	 * @param userId 用户英文名
	 * @return user对象，最主要的是user对象中的岗位信息，business对象，如果有多个岗位如果有多个岗位，需封装为list放入user对象中
	 */
	public User getUserInfo(String userId);
	public String getIP(HttpServletRequest request);
	
	/**
	 * 通过用户角色关联关系直接获取 先暂时模拟视图，后通过修改视图进行处理
	 * @param userId 用户ID
	 * @return user对象
	 */
	public User getUserInfoByUserRoles(String userId);
}
