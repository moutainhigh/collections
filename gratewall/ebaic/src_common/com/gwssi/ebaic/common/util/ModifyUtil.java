package com.gwssi.ebaic.common.util;

import java.math.BigDecimal;
import java.util.Calendar;

import com.gwssi.ebaic.approve.util.ApproveUserUtil;
import com.gwssi.ebaic.domain.BeWkModifyBO;
import com.gwssi.ebaic.domain.TPtYhBO;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.util.DateUtil;
import com.gwssi.rodimus.util.HttpSessionUtil;
import com.gwssi.torch.util.UUIDUtil;

/**
 * 修改记录工具类。
 * 
 * 股东变化使用be_wk_investor表modify_sign字段表示。
 * 
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class ModifyUtil {
	/**
	 * 保存申请单数据变化。
	 * 
	 * @param gid
	 * @param type 1-申请用户保存，2-审批用户保存
	 * @param field
	 * @param newValue
	 */
	public static void saveReqModify(String gid,int type,String field,String newValue,String editBeforeFieldValue){
		String tableName = "be_wk_requisition";
		saveModify(gid,type,tableName,field,newValue,editBeforeFieldValue);
	}
	
	/**
	 * 保存企业信息数据变化。
	 * 
	 * @param gid
	 * @param type 1-申请用户保存，2-审批用户保存
	 * @param field
	 * @param newValue
	 */
	public static void saveEntModify(String gid,int type,String field,String newValue,String editBeforeFieldValue){
		String tableName = "be_wk_ent";
		saveModify(gid,type,tableName,field,newValue,editBeforeFieldValue);
	}
	
	/**
	 * 
	 * @param gid
	 * @param type
	 * @param table
	 * @param field
	 * @param newValue
	 */
	protected static void saveModify(String gid,int type, String table, String field, String newValue,String editBeforeFieldValue){
		String appUserId = null;
		String approveUserId = null;
		switch(type){
		case 1:
			TPtYhBO user = HttpSessionUtil.getCurrentUser();
			if(user==null){
				throw new RuntimeException("登录超时，请重新登录。");
			}
			appUserId = user.getUserId();
			break;
		case 2:
			approveUserId = ApproveUserUtil.getLoginUser().getUserId();
			break;
		default:
			throw new RuntimeException("用户类型只能是1或2。");	
		}
		
		
		BigDecimal version = (BigDecimal)DaoUtil.getInstance().queryForOne("select nvl(r.version,1) as version from be_wk_requisition r where r.gid = ?", gid);
		
		String tableName = table.trim().toUpperCase();
		String tableField = field.trim().toUpperCase();
		Calendar now = DateUtil.getCurrentTime();
		BeWkModifyBO modifyBo = DaoUtil.getInstance().queryForRowBo("select * from be_wk_modify m where m.gid = ? and m.version = ? and m.table_name = ? and m.table_field = ? ", 
				BeWkModifyBO.class, 
				gid,version,tableName,tableField);
		if(modifyBo==null){
			modifyBo = new BeWkModifyBO();
			String modifyId = UUIDUtil.getUUID();
			modifyBo.setModifyId(modifyId);
			modifyBo.setVersion(version);
			if(1==type){
				modifyBo.setAppUserId(appUserId);
			}
			if(2==type){
				modifyBo.setApproveUserId(approveUserId);
			}
			modifyBo.setTableName(tableName);
			modifyBo.setTableField(tableField);
			modifyBo.setAfter(newValue);
			modifyBo.setGid(gid);
			modifyBo.setTimestamp(now);
			modifyBo.setBefore(editBeforeFieldValue);
			DaoUtil.getInstance().insert(modifyBo);
		}else{
			if(1==type){
				modifyBo.setAppUserId(appUserId);
			}
			if(2==type){
				modifyBo.setApproveUserId(approveUserId);
			}
			modifyBo.setAfter(newValue);
			modifyBo.setTimestamp(now);
			DaoUtil.getInstance().update(modifyBo);
		}
	}
}
