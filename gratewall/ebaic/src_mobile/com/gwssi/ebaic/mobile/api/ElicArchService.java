package com.gwssi.ebaic.mobile.api;

import java.util.List;
import java.util.Map;

import com.gwssi.mobile.api.annotation.ParameterName;

/**
 * <h2>档案管理接口</h2>
 * 
 * @author liuxiangqian
 *
 */
public interface ElicArchService {
	/**
	 * <h3>查询登录用户的档案列表</h3>
	 * 
	 * @param uniScid ： 统一社会信用代码
	 * @return List<Map<String,String>> :
	 * 	  [{dossName : 档案名称
	 * 		approveTime ： 核准时间
	 * 		busiType : 业务类型（中文汉字）
	 * 		fileId ： 档案文件Id}]
	 */
	public List<Map<String, String>> getMyArchList(@ParameterName(value="uniScid")String uniScid);
}
