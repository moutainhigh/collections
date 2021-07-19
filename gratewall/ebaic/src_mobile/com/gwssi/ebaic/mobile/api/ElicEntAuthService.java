package com.gwssi.ebaic.mobile.api;

import java.util.List;
import java.util.Map;

import com.gwssi.rodimus.rpc.ParameterName;

/**
 * <h2>企业认证服务</h2>
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public interface ElicEntAuthService {

	/**
	 * <h3>查询企业全部认证信息</h3>
	 * 
	 * @param uniScid 电子营业执照登录只有统一社会信用代码
	 * @param pageIndex  页数，从1开始；当pageSize传入-1时则不生效。
	 * @param pageSize 每页记录条数最少为1，如果传入0，则默认为10；如果传入-1，则返回所有记录。
	 * @return List<Map<String, String>> :
	 * 	[{
	 * 		authCode : 认证码
	 * 		operCode : 服务码
	 * 		authState : 认证状态
	 * 		validateCodeStartTime : 认证触发时间
	 * 		validateCodeEndTime : 认证触发时间
	 * 		
	 * }]
	 */
	public List<Map<String, String>> getList(@ParameterName(value="uniScid")String uniScid,
							  @ParameterName(value="authState")int authState,
							  @ParameterName(value="pageIndex")int pageIndex,
							  @ParameterName(value="pageSize")int pageSize);
}
