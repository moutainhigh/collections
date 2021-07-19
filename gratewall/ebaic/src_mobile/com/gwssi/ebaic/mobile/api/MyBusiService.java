package com.gwssi.ebaic.mobile.api;

import java.util.List;
import java.util.Map;

import com.gwssi.rodimus.rpc.ParameterName;

/**
 * <h2>全程电子化：我的业务</h2>
 * <pre>通过 t_pt_yh 用户，显示我的业务列表。
 * 点击条目后可以查看明细。
 * 如果当前状态可以终止业务，则显示终止业务按钮。
 * 
 * </pre>
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public interface MyBusiService {
	/**
	 * 我的业务。
	 * 
	 * @param userId
	 * @param entName 查询条件：支持模糊查询
	 * @param oprType 查询条件：业务类型，需传入码表数字值
	 * @param state   查询条件：业务状态，需传入码表数字值
	 * @param pageSize 每页记录条数，如果传入0，则默认为10；如果传入-1，则返回所有记录。
	 * @param pageIdx 页数，从0开始；当pageSize传入-1时，则不生效。
	 * 
	 * @return Map ： 
	 *		appDate : 申请时间
	 * 		approveDate : 核准日期
	 * 		entName : 企业名称
	 * 		gid : 业务ID
	 * 		operationType : 业务类型（文本，无需转码）
	 *  	state : 业务状态（文本，无需转码）
	 */
	public List<Map<String,Object>> getList(@ParameterName(value="userId") String userId,
			@ParameterName(value="entName") String entName,
			@ParameterName(value="oprType") String oprType,
			@ParameterName(value="state") String state,
			@ParameterName(value="pageSize")int pageSize,
			@ParameterName(value="pageIdx")int pageIdx);
	
	/**
	 * 业务详情。
	 * 
	 * @param gid
	 * @return
	 * 		appDate: 申请时间
	 * 		approveDate : 核准日期
	 * 		entName : 企业名称
	 * 		regOrg : 审批机关
	 * 		operationType : 业务类型
	 * 		state : 业务状态
	 * 		censorNotion : 审查意见
	 * 		canTerminate : 是否可终止业务
	 */
	public Map<String,Object> get(@ParameterName(value="gid") String gid);
	
	/**
	 * <h3>执行业务终止共功能</h3>
	 * 
	 * @param gid 
	 * @param typeCo 终止类型
	 * @param reason 终止具体原因
	 * @param password 登录密码
	 *
	 */
	public void doTerminate(@ParameterName(value = "gid") String gid,
			@ParameterName(value = "reason") String reason,
			@ParameterName(value = "password") String password,
			@ParameterName(value = "typeCo") String typeCo);
	/**
	 * 业务提交。
	 * 
	 * @param gid
	 * @return
	 */
	public void submit(@ParameterName(value="userId") String userId,
			@ParameterName(value="gid") String gid);
}
