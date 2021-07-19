package com.gwssi.ebaic.mobile.api;

import java.util.Map;

import com.gwssi.ebaic.mobile.domain.IdentityBo;
import com.gwssi.rodimus.rpc.ParameterName;

/**
 * 身份认证接口。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com) , shigaozhan 
 */
public interface IdentityAuthService {
	
	/**
	 * 
	 * @param identityId
	 * @return  
	 * 		  
	 */
	public IdentityBo get(@ParameterName(value="identityId")String identityId);
	
	
	/**
	 * <h3>拍完照片保存图片信息</h3>
	 * 
	 * @param identityId
	 * @param type :<pre>三位编码，第一位表示个人还是企业，0-个人，1-企业；
	 * 如果是个人，第二位表示证件类型，如果是个人参照码表CB16，第三位为顺序号；
	 * 如果是企业，第二三位参照码表CA50。
	 * 例如：010-个人身份证正面；
	 *  011-个人身份证反面；
	 *  012-个人手持身份证；
	 *  1AA-企业营业执照。	
	 * </pre>
	 * @param fileId 图片编号
	 * @param thumbFileId 图片缩略图编号
	 * @return
	 */
	public void savePicture(@ParameterName(value="identityId")String identityId, @ParameterName(value="type")String type, 
			@ParameterName(value="fileId")String fileId,@ParameterName(value="thumbFileId")String thumbFileId);

	
	/**
	 * <h3>提交身份认证<h3>
	 * @param identityId
	 */
	public void submit(@ParameterName(value="identityId")String identityId);
	
	

	/**
	 * <h3>保存身份认证经办人信息</h3>
	 * 
	 * 只适用于单位股东。
	 * 
	 * @param identityId
	 * @param contactName 长度x
	 * @param contactCerNo
	 */
	public void saveContact(@ParameterName(value="identityId")String identityId, 
			@ParameterName(value="contactName")String contactName,
			@ParameterName(value="contactCerType")String contactCerType,
			@ParameterName(value="contactCerNo")String contactCerNo);
	
	/**
	 * <h3>更换手机号码</h3>
	 * 
	 * @param name  	个人姓名 或 法人股东名称
	 * @param cerType	如果是个人传 1，如果是法人股东，传空字符串
	 * @param cerNo		身份证号码 或 企业注册号
	 * @param oriMobile	原手机号码
	 * @param oriMobileVerCode	原手机号码接收到的校验码
	 * @param newMobile			新手机号码
	 * @param newMobileVerCode	新手机号码接收到的校验码
	 */
	public void modifyMobile(@ParameterName(value="name")String name,
			@ParameterName(value="cerType")String cerType,@ParameterName(value="cerNo")String cerNo,
			@ParameterName(value="oriMobile")String oriMobile,@ParameterName(value="oriMobileVerCode")String oriMobileVerCode,
			@ParameterName(value="newMobile")String newMobile,@ParameterName(value="newMobileVerCode")String newMobileVerCode);
	
	/**
	 * <h3>重新认证</h3>
	 * 
	 * 将当前有效的身份认证数据，设置标记为不可用（-1）。
	 * 创建一条新的身份认证记录
	 * 
	 * @param identityId
	 * @return 新的identityId
	 */
	public String reset(@ParameterName(value="identityId")String identityId);
	
	/**
	 * <h3>通过身份验证信息查找网登用户</h3>
	 * 
	 * @param identityId
	 * @return Map : 
	 * 		loginName : 登录名 （为空表示没有注册网登用户）
	 */
	public Map<String, String> getLoginName(
			@ParameterName(value="identityId")String identityId);
	
	/**
	 * <h3>身份验证提交后，快速注册接口</h3>
	 * 
	 * @param identityId
	 * @param loginName
	 * @param loginPwd
	 * @return
	 */
	public void quickRegister(@ParameterName(value="identityId")String identityId,
			@ParameterName(value="loginName")String loginName,
			@ParameterName(value="loginPwd")String loginPwd);
	
}
