package com.gwssi.application.webservice.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.plugin.auth.model.User;

/**
 * 接口实现类/调用人事系统服务实现类 com.gwssi.application.webservice.receive
 * ReceiveHrAppImpl.java 下午2:01:05
 * 
 * @author wuminghua
 */
public class ReceiveHrAppImpl implements IReceiveHrApp {

	private static Logger logger = Logger.getLogger(ReceiveHrAppImpl.class);

	/**
	 * 根据userid查询user信息
	 */
	@Override
	public User getUserInfo(String userId) {
		User user = new User();
		List list = new ArrayList<String>();

		PostService postService = new PostService();
		try {
			HrUsersBO userBo = postService.getLoginUser(userId);
			list = postService.getPostByUser(userBo.getUserId());

			logger.info("登录用户userId为：" + userId);
			logger.info("登录用户userName为：" + userBo.getUserName());
			logger.info("登录用户岗位为：" + list);

			user.setUserId(userId);
			user.setUserName(userBo.getUserName());
			user.setPostList(list);
		} catch (OptimusException e) {
			e.printStackTrace();
		} finally {
			return user;
		}
	}
}
