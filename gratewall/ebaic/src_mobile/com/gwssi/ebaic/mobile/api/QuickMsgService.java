package com.gwssi.ebaic.mobile.api;

import java.util.List;

import com.gwssi.ebaic.mobile.domain.QuickMsgBo;
import com.gwssi.rodimus.rpc.ParameterName;

/**
 * <h2>页面上方显示的快速通知</h2>
 * <pre>
 * 数据库表：p_pt_yh
 * 试用范围：全程电子化
 * 提供功能： 编辑个人信息；修改密码；使用手册；意见反馈（采用网上登记“意见”反馈模块）。
 * </pre>
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public interface QuickMsgService {
	/**
	 * 
	 * @param userId	
	 * @param moduleId （登录入口 1-网登 2-全流程电子化 3-电子营业执照）
	 * @param topN	（显示条数）
	 * @return
	 */
	public List<QuickMsgBo> getTopN(@ParameterName(value="userId") String userId,
			@ParameterName(value="moduleId") String moduleId,
			@ParameterName(value="topN") int topN);
}
