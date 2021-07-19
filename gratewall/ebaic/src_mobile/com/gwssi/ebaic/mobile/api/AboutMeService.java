package com.gwssi.ebaic.mobile.api;

import com.gwssi.ebaic.mobile.domain.UserBo;
import com.gwssi.rodimus.rpc.ParameterName;

/**
 * <h2>我的</h2>
 * <pre>
 * 数据库表：p_pt_yh
 * 试用范围：全程电子化
 * 提供功能： 编辑个人信息；修改密码；使用手册；意见反馈（采用网上登记“意见”反馈模块）。
 * </pre>
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public interface AboutMeService {
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public UserBo get(@ParameterName(value="userId") String userId);
	/**
	 * 
	 * @param user
	 */
	public void save(@ParameterName(value="user") UserBo user);
	
	/**
	 * 
	 * @param userId
	 * @param oriPassword
	 * @param newPassword
	 */
	public void modifyPassword(@ParameterName(value="userId") String userId, 
			@ParameterName(value="oriPassword") String oriPassword, 
			@ParameterName(value="newPassword") String newPassword);
	
	/**
	 * 
	 * @return fileId
	 */
	public String getManual();
}
