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
public interface ElicMyBusiService {
	/**
	 * 我的业务。
	 * 
	 * @param accountId
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
			@ParameterName(value="pageSize")int pageSize,@ParameterName(value="pageIdx")int pageIdx);
	
	/**
	 * 
	 * @param gid 业务编号。
	 * @return
	 * 		type : 类型，取值为1-“自然人股东”、2-“非自然人股东”，3-“法定代表人”
	 * 		name : 名称
	 * 		state : 状态，取值为“未提交”、“已提交”、“未通过”、“通过”
	 */
	public List<Map<String,Object>> getIdentityState(@ParameterName(value="gid") String gid);
	
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
	public Map<String,Object> getReqInfo(@ParameterName(value="gid") String gid);
	
	/**
	 * 获取业务确认状态。
	 * 
	 * @param gid
	 * @return
	 * 		type : 类型，取值为1-“自然人股东”、2-“非自然人股东”，3-“法定代表人”
	 * 		name : 名称
	 * 		state : 状态，取值为“未确认”、“已确认”、“已授权”、“退回申请人”
	 * 		msg  : 退回原因。 如果状态为“退回申请人”，则需显示“退回原因”。 效果图中是错的，以此为准。
	 */
	public List<Map<String,Object>> getConfirmState(@ParameterName(value="gid") String gid);
	
	/**
	 * 获取审批意见。
	 * 
	 * @param gid
	 * @return
	 * 		regOrg：审核机关
	 * 		approveDate：审核日期
	 * 		approveResult：审批结果
	 * 		approveMsg : 审批意见
	 */
	public List<Map<String,Object>> getApproveMsgs(@ParameterName(value="gid") String gid);
	/**
	 * 业务提交。
	 * 暂时手机端无需提供这个功能。
	 * 
	 * @param gid
	 * @return
	 */
	public void submit(@ParameterName(value="userId") String userId, @ParameterName(value="gid") String gid);
}
