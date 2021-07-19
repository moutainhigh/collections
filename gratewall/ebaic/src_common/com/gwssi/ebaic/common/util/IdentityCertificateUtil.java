package com.gwssi.ebaic.common.util;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.StringUtil;

/**
 * 
 * 处理身份认证相关事宜。
 * 不包括调用公安部接口验证身份证号码，另行参见：IdentityCardUtil
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class IdentityCertificateUtil {
	
	/**
	 * 是否通过了身份认证。
	 * 
	 * @param cerType
	 * @param cerNo
	 * @return
	 */
	public static boolean isCertificated(String cerType,String cerNo){
		if(StringUtils.isBlank(cerType)){
			cerType="1";
		}
		String sql = "select identity_id,flag,type from SYSMGR_IDENTITY i where i.type ='0' and i.cer_type = ? and i.cer_no = ?  and i.flag in ('0','1')";
		Map<String,Object> rowMap = DaoUtil.getInstance().queryForRow(sql, cerType,cerNo);
		
		if(rowMap ==null || rowMap.isEmpty()){
			return false;//没有认证记录
		}
		@SuppressWarnings("unused")
		String type=StringUtil.safe2String(rowMap.get("type"));
		String flag=StringUtil.safe2String(rowMap.get("flag"));
		//return flag ;
		if(!"0".equals(flag) && !"1".equals(flag)){
			return false;//认证不通过
		}
		return true;
		/*String identityId=StringUtil.safe2String(rowMap.get("identityId"));
		sql ="select count(type) as cnt from sysmgr_identity_picture where identity_id=? and flag not in ('2')";
		long cnt = DaoUtil.getInstance().queryForOneLong(sql, identityId);
		return cnt==3;//上传了全部所需的实名照片
*/		
	}
	
	
	
	/**
	 * 全部通过身份校验。
	 */
	public static final int IDENTITY_STATE_ALL_OK 			= 1;
	/**
	 * 全部通过身份校验，或者上传了认证材料。
	 */
	public static final int IDENTITY_STATE_PICTURE_OK 		= 2;
	/**
	 * 经办人未通过身份校验，且未上传认证材料。
	 */
	public static final int IDENTITY_STATE_LINKMAN_NOT_OK 	= 3;
	/**
	 * 法定代表人未通过身份校验，且未上传认证材料。
	 */
	public static final int IDENTITY_STATE_LE_REP_NOT_OK 	= 4;
	
	public static final int IDENTITY_STATE_NOT_OK 	= 5;
	
	/**
	 * 
	 * @param cerType
	 * @param cerNo
	 * @return -1 未通过身份校验，而且未上传图片 ； 1 通过身份校验 ；0未通过身份校验,但上传了图片
	 */
	public static int checkIdentityStateDetail(String cerType, String cerNo){
		if(StringUtil.isBlank(cerType) || StringUtil.isBlank(cerNo) ){
			return -1;
		}
		String sql = "select count(1) as cnt from sysmgr_identity i where i.cer_type = ? and i.cer_no = ? and i.type in ('0','1') and i.flag='1' ";
		long cnt = DaoUtil.getInstance().queryForOneLong(sql, cerType, cerNo);
		if(cnt > 0){
			return 1;//通过了身份校验
		}
		// 如果未通过，继续看是否传递了图片
		sql = "select i.name,i.flag,count(distinct p.picture_id) as cnt from sysmgr_identity i "
				+ " left join sysmgr_identity_picture p on i.identity_id=p.identity_id "
				+ " where i.cer_type = ? and i.cer_no = ? and i.type = '0' group by i.name, i.flag";
		Map<String,Object> row = DaoUtil.getInstance().queryForRow(sql, cerType,cerNo);
		if(row==null || row.isEmpty()){
			return -1;//未通过身份校验，而且未上传图片
		}
		cnt = ((BigDecimal)row.get("cnt")).longValue();
		if(cnt<1){
			return -1;//未通过身份校验，而且未上传图片
		}
		return 0;//未通过身份校验,但上传了图片
	}
	
	/**
	 * 检查是否法定代表人和申请人是否通过了身份校验。
	 * 
	 * @param gid
	 * @return
	 */
	public static int checkReqIdentityState(String gid) {
		String sql = "select r.linkman,r.cert_type,r.cert_no,i.le_rep_name,i.le_rep_cer_type,i.le_rep_cer_no from be_wk_requisition r "
				+ " left join be_wk_le_rep i on r.gid = i.gid where r.gid = ? ";
		
		Map<String,Object> row = DaoUtil.getInstance().queryForRow(sql, gid);
		if(row==null || row.isEmpty()){
			throw new EBaicException("根据gid找不到申请数据（"+gid+"）。");
		}
		String lkCerType = StringUtil.safe2String(row.get("certType"));
		String lkCerNo = StringUtil.safe2String(row.get("certNo"));
		if(StringUtil.isBlank(lkCerType) || StringUtil.isBlank(lkCerNo)){
			throw new EBaicException("申请数据中未包含申请人信息。");
		}
		String leCerType = StringUtil.safe2String(row.get("leRepCerType"));
		String leCerNo = StringUtil.safe2String(row.get("leRepCerNo"));
		if(StringUtil.isBlank(leCerType) || StringUtil.isBlank(leCerNo)){
			throw new EBaicException("申请数据中未包含法定代表人信息。");
		}
		
		//-1 未通过身份校验，而且未上传图片 ； 1 通过身份校验 ；0未通过身份校验,但上传了图片
		int lkState = checkIdentityStateDetail(lkCerType,lkCerNo);
		int leState = checkIdentityStateDetail(leCerType,leCerNo);
		
		// 全部通过校验
		if(lkState==1 && leState==1){
			return IDENTITY_STATE_ALL_OK;
		}
		// 未通过，也未传图片
		if(lkState==-1 && leState==-1){
			throw new EBaicException("申请人和法定代表人未通过实名认证，且未上传认证图片。");
		}
		if(lkState==-1){
			throw new EBaicException("申请人未通过实名认证，且未上传认证图片。");
		}
		if(leState==-1){
			throw new EBaicException("法定代表人未通过实名认证，且未上传认证图片。");
		}
		
		// 有未通过的，但上传了材料
		return IDENTITY_STATE_PICTURE_OK;
	}
	
	/**
	 * 检查是否通过了身份认证。
	 * 
	 * @param gid
	 */
	public static boolean checkIdentityState(String cerType, String cerNo){
		if(StringUtil.isBlank(cerType) || StringUtil.isBlank(cerNo) ){
			return false;
		}
		String sql = "select count(1) as cnt from SYSMGR_IDENTITY i where i.cer_type = ? and i.cer_no = ? and i.type in ('0','1') and i.flag = '1' ";
		long cnt = DaoUtil.getInstance().queryForOneLong(sql, cerType, cerNo);
		if(cnt<1){
			return false;
		}
		
		return true;
	}
}
