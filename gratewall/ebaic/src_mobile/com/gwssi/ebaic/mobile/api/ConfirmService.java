package com.gwssi.ebaic.mobile.api;

import java.util.List;
import java.util.Map;

import com.gwssi.rodimus.rpc.ParameterName;


/**
 * 手机业务确认接口。
 * 
 * @author lxb
 */
public interface ConfirmService {
	
	/**
	 * 自然人 
	 * 查询担任股东或法人的待确认和已确认的业务信息列表
	 * @param cerNo 人员证件编号
	 * @param cerType 人员证件类型
	 * @return 结果列表 如果没查到 返回null
	 *  参考格式
	 *  [{gid:'89808797897adcbf8797d8',entName='北京海天科技有限公司',operationType='设立',submitDate='2016-09-02',isConfirm:'已确认',authToApplyUser:'已授权', applyFileId:'sssssssssssssssss'}]
	 */
	public List<Map<String, Object>> queryReqListByPerson(@ParameterName(value="cerType")String cerType,@ParameterName(value="cerNo")String cerNo);

	/**
	 * 非自然人
	 * 查询担任股东或法人的业务信息列表
	 * @param licNo 证照号码，先统一为 统一社会信用代码 （或注册号，取决于申请业务时非自然人股东信息填写的是哪一个，以此作为标识关联业务）
	 * @return 结果列表 结果列表 如果没查到 返回null
	 *  参考格式：
	 * [{gid:'89808797897adcbf8797d8',entName='北京海天科技有限公司',operationType='设立',submitDate='2016-09-02',isConfirm:'已确认',authToApplyUser:'已授权'}]
	 */
	public List<Map<String, Object>> queryReqListByCp(@ParameterName(value="licNo")String licNo);
	
	/**
	 * 自然人股东或自然人法人提交业务确认
	 * @param cerNo 人员证件编号
	 * @param cerType 人员证件类型
	 * @param gid 要确认的业务标识gid
	 * @param withAuth 是否同时授权 1为授权，其他值为不授权
	 * @return 提交状态 0-提交成功，其他值均为错误信息
	 */
	public String personConfirm(@ParameterName(value="cerType")String cerType,
			@ParameterName(value="cerNo")String cerNo,
			@ParameterName(value="gid")String gid,
			@ParameterName(value="withAuth")String withAuth);
	
	/**
	 * 非自然人股东或非自然人法人提交业务确认
	 * @param licNo 统一社会信用代码 （或注册号，取决于申请业务时非自然人股东信息填写的是哪一个，以此作为标识关联业务）
	 * @param licType 证件类型（扩展备用，目前无用，默认为1）
	 * @param gid 要确认的业务标识gid
	 * @param withAuth 是否同时授权 1为授权，其他值为不授权
	 * @return 提交状态 0-提交成功，其他值均为错误信息
	 */
	public String cpConfirm(@ParameterName(value="licType")String licType,
			@ParameterName(value="licNo")String licNo,
			@ParameterName(value="gid")String gid,
			@ParameterName(value="withAuth")String withAuth);
	
	/**
	 * 自然人股东或自然人法人 退回申请人
	 * @param gid 要退回的业务标识gid
	 * @param reason 退回原因
	 * @param cerNo 人员证件编号
	 * @param cerType 人员证件类型
	 * @return 提交状态 0-提交成功，其他值均为错误信息
	 */
	public String personBackToAppUser(@ParameterName(value="cerType")String cerType,
			@ParameterName(value="cerNo")String cerNo,
			@ParameterName(value="gid")String gid,
			@ParameterName(value="reason")String reason );
	
	/**
	 * 非自然人股东或非自然人法人 退回申请人
	 * @param gid 业务标识gid
	 * @param reason 退回原因 最长
	 * @param licType 证件类型（扩展备用，目前无用，默认为1）
	 * @param licNo 证照号码，先统一为 统一社会信用代码 （或注册号，取决于申请业务时非自然人股东信息填写的是哪一个，以此作为标识关联业务）
	 * @return 提交状态 0-提交成功，其他值均为错误信息
	 */
	public String cpBackToAppUser(@ParameterName(value="licType")String licType,
			@ParameterName(value="licNo")String licNo,
			@ParameterName(value="gid")String gid,
			@ParameterName(value="reason")String reason );
	
	
	
	
	
}
