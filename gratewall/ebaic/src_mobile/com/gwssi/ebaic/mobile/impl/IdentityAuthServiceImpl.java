package com.gwssi.ebaic.mobile.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.ebaic.mobile.api.IdentityAuthService;
import com.gwssi.ebaic.mobile.domain.IdentityBo;
import com.gwssi.ebaic.mobile.domain.IdentityPictureBo;
import com.gwssi.optimus.util.MD5Util;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.RpcException;
import com.gwssi.rodimus.sms.SmsVerCodeUtil;
import com.gwssi.rodimus.util.DateUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.rodimus.util.UUIDUtil;


@Service(value="identityServiceImpl")
public class IdentityAuthServiceImpl implements IdentityAuthService {
	
	
	public IdentityBo get(String identityId) {
		if(StringUtil.isBlank(identityId)) {
		    throw new RpcException("身份编号不能为空。");
		}
		String sql = "select t.identity_id,t.name,t.cer_type,t.cer_no,t.mobile,t.approve_msg,t.reg_org,t.flag,t.lock_sign,"
		        + "t.create_time,t.submit_date,t.approver_date,t.person_sign from sysmgr_identity t where t.identity_id=?";
		IdentityBo identityBo = DaoUtil.getInstance().queryForRowBo(sql, IdentityBo.class, identityId);
		if(identityBo==null) {
		    throw new RpcException("非法的身份编号。");
		}
		String picSql = "select t.picture_id,t.identity_id,t.type,t.file_id,t.approve_msg,t.flag from sysmgr_identity_picture t where t.identity_id=?";
        List<IdentityPictureBo> pictures = DaoUtil.getInstance().queryForListBo(picSql, IdentityPictureBo.class, identityId);
        identityBo.setPictures(pictures);
		return identityBo;
	}

	public void savePicture(String identityId, String type, String fileId,String thumbFileId) {
	    if(StringUtil.isBlank(identityId)) {
            throw new RpcException("身份编号不能为空。");
        }
	    if(StringUtil.isBlank(type)) {
            throw new RpcException("图片类型不能为空。");
        }
	    if(StringUtil.isBlank(fileId)) {
            throw new RpcException("图片编号不能为空。");
        }
	    if(StringUtil.isBlank(thumbFileId)) {
            throw new RpcException("图片缩略图编号不能为空。");
        }
	    if(type.length()!=3) {
	        throw new RpcException("上传图片类型错误。");
	    }
	    Map<String,Object> identityMap = DaoUtil.getInstance().queryForRow("select t.cer_type,t.person_sign from sysmgr_identity t where t.identity_id=?", identityId);
	    if(identityMap==null) {
	        throw new RpcException("非法的身份编号。");
	    }
	    String cerType = (String) identityMap.get("cerType");
	    String personSign = (String) identityMap.get("personSign");
	    if(StringUtil.isBlank(cerType)) {
	        throw new RpcException("数据异常，证件类型为空。");
	    }
	    if(StringUtil.isBlank(personSign)) {
            throw new RpcException("数据异常。");
        }
	    if("1".equals(personSign)){
	        //个人,证件类型请参照代码集CB16
	        if(!type.equals("0"+cerType+"0")&&!type.equals("0"+cerType+"1")&&!type.equals("0"+cerType+"2")) {
	            throw new RpcException("上传图片类型错误。");
	        }
	    }
	    if("0".equals(personSign)){
            //企业 ，证件类型请参照代码集CA50
	        if(!type.startsWith("1")) {
                throw new RpcException("上传图片类型错误。");
            }
        }
	    //判断要保存的图片类型是否已经存在，如果存在，更新；否则插入
	    Long count = DaoUtil.getInstance().queryForOneLong("select count(t.picture_id) from sysmgr_identity_picture t where t.identity_id=? and t.type=?", identityId,type);
	    if(count!=null&&count>0) {
	        DaoUtil.getInstance().execute("update sysmgr_identity_picture t set t.file_id=?,t.thumb_file_id=?,t.flag='-1',t.timestamp=sysdate where t.identity_id=? and t.type=?", fileId,thumbFileId,identityId,type);
	    }else {
	        //创建一条新的图片信息记录
	        String pictureId = UUIDUtil.getUUID();
	        String insertSql = "insert into sysmgr_identity_picture(picture_id,identity_id,file_id,flag,thumb_file_id,type,timestamp) "
	                + "values(?,?,?,?,?,?,sysdate)";
	        DaoUtil.getInstance().execute(insertSql, pictureId,identityId,fileId,"-1",thumbFileId,type);
	    }
	}

	public void submit(String identityId) {
	    if(StringUtil.isBlank(identityId)) {
            throw new RpcException("身份编号不能为空。");
        }
	    //flag=0;已提交，等待审核
	    int rows = DaoUtil.getInstance().execute("update sysmgr_identity t set t.flag='0',t.timestamp=sysdate,t.SUBMIT_DATE=sysdate where t.lock_sign='0' and t.flag='-1' and t.identity_id=?", identityId);
	    if(rows==0){ 
            //未更新到数据
            throw new RpcException("非法的身份编号。");
        }
	    int picRows = DaoUtil.getInstance().execute("update sysmgr_identity_picture t set t.flag='0',t.timestamp=sysdate where t.identity_id=? and t.flag='-1'", identityId);
	    if(picRows==0){ 
            //未更新到数据
            throw new RpcException("非法的身份编号。");
        }
	}

	public void saveContact(String identityId, String contactName,String contactCerType, String contactCerNo) {
	    if(StringUtil.isBlank(identityId)) {
            throw new RpcException("身份编号不能为空。");
        }
	    if(StringUtil.isBlank(contactName)) {
            throw new RpcException("企业联系人姓名不能为空。");
        }
	    if(StringUtil.isBlank(contactCerNo)) {
            throw new RpcException("企业联系人证件号码不能为空。");
        }
	    int rows = DaoUtil.getInstance().execute("update sysmgr_identity t set t.contact_name=?,t.contact_cerno=?,t.timestamp=sysdate where t.lock_sign='0' and t.person_sign='0' and t.identity_id=?", contactName,contactCerNo,identityId);
        if(rows==0){ 
            //未更新到数据
            throw new RpcException("保存身份认证经办人信息失败。");
        }

	}

	public void modifyMobile(String name, String cerType, String cerNo, String oriMobile, String oriMobileVerCode,
			String newMobile, String newMobileVerCode) {
	    if(StringUtil.isBlank(name)) {
            throw new RpcException("名称不能为空。");
        }
	    if(StringUtil.isBlank(cerNo)) {
            throw new RpcException("证件号码不能为空。");
        }
	    if(StringUtil.isBlank(oriMobile)){
            throw new RpcException("原移动电话不能为空。");
        }
        if(StringUtil.isBlank(oriMobileVerCode)){
            throw new RpcException("原移动电话校验码不能为空。");
        }
        if(StringUtil.isBlank(newMobile)){
            throw new RpcException("新移动电话不能为空。");
        }
        if(StringUtil.isBlank(newMobileVerCode)){
            throw new RpcException("新移动电话校验码不能为空。");
        }
        if(StringUtil.isBlank(cerType)) {
            cerType = "1";//默认为身份证号码
        }
        // 校验手机校验码是否正确
        SmsVerCodeUtil.verify(oriMobile, oriMobileVerCode);
        SmsVerCodeUtil.verify(newMobile, newMobileVerCode);
        String updateSql = "update sysmgr_identity t set t.mobile=?,t.timestamp=sysdate where t.name=? and t.cer_type=? and t.mobile=? and t.lock_sign='0'";
        int rows =DaoUtil.getInstance().execute(updateSql, newMobile,name,cerType,oriMobile);
        if(rows==0){ 
            //未更新到数据
            throw new RpcException("更换手机号码失败。");
        }
	}

	public String reset(String identityId) {
	    if(StringUtil.isBlank(identityId)) {
            throw new RpcException("身份编号不能为空。");
        }
	    IdentityBo identityBo = this.get(identityId);
	    if(identityBo==null) {
	        throw new RpcException("非法的身份编号。");
	    }
	    //将当前有效的身份认证数据，设置标记为不可用状态(flag='3')。 
	    DaoUtil.getInstance().execute("update sysmgr_identity t set t.flag='3',t.timestamp=sysdate where t.lock_sign='0' and t.identity_id=?", identityId);
	    //将当前相关的照片全部标记为不可用状态(flag='3')。
	    DaoUtil.getInstance().execute("update sysmgr_identity_picture t set t.flag='3',t.timestamp=sysdate where t.identity_id=?", identityId);
	    //创建一条新的身份认证记录
	    String newIdentityId = UUIDUtil.getUUID();
	    String insertSql="insert into sysmgr_identity(identity_id,type,name,cer_type,cer_no,mobile,flag,lock_sign,person_sign,timestamp,create_time) "
	            + "values(?,?,?,?,?,?,?,?,?,sysdate,sysdate)";
	    DaoUtil.getInstance().execute(insertSql, newIdentityId,"0",identityBo.getName(),identityBo.getCerType(),identityBo.getCerNo(),
	            identityBo.getMobile(),"-1","0",identityBo.getPersonSign());
	    return newIdentityId;
	}

	public Map<String, String> getLoginName(String identityId) {
		if(StringUtil.isBlank(identityId)){
			throw new RpcException("身份认证编号不能为空。");
		}
		String sql = "select i.cer_type,i.cer_no,i.person_sign from sysmgr_identity i where i.identity_id = ? ";
		Map<String,Object> row = DaoUtil.getInstance().queryForRow(sql, identityId);
		if(row==null || row.isEmpty()){
			throw new RpcException("身份认证编号不正确："+identityId+"。");
		}
		String cerType = StringUtil.safe2String(row.get("cerType"));
		String cerNo   = StringUtil.safe2String(row.get("cerNo"));
		String personSign   = StringUtil.safe2String(row.get("personSign"));
		if(!"1".equals(personSign)){
			throw new RpcException("只有个人才需创建账号。");
		}
		
		
		sql = "select t.user_id,t.login_name from t_pt_yh t where t.cer_type= ? and t.cer_no = ? ";
		List<Map<String, Object>> userList = DaoUtil.getInstance().queryForList(sql, cerType, cerNo);
		
		Map<String, String> ret = new HashMap<String, String>();
		
		if(userList==null || userList.isEmpty()){
			ret.put("loginName", "");
			return new HashMap<String, String>();
		}
		
		// 找到userId
		String loginName = "";
		if(userList.size()>1){
			// 当前身份证号码存在多个用户
			// 找最近活跃的用户
			sql = "select y.login_name from t_pt_yh y left join nm_wk_transact tr on y.user_id=tr.user_id where tr.transact_id is not null and tr.app_date is not null and y.cer_no=? order by tr.app_date desc";
			loginName = DaoUtil.getInstance().queryForOneString(sql, cerNo);
			
			if(StringUtils.isEmpty(loginName)){// 没办理过名称业务
				sql = "select y.login_name from t_pt_yh y left join cp_wk_requisition r on y.user_id = r.user_id where r.requisition_id is not null and r.timestamp is not null and y.cer_no = ? order by r.timestamp desc";
				loginName = DaoUtil.getInstance().queryForOneString(sql, cerNo);
			}
			
			if(StringUtils.isEmpty(loginName)){// 没办理企业业务
				sql = "select t.login_name from t_pt_yh t where t.cer_no = ? order by t.create_time desc";
				loginName = DaoUtil.getInstance().queryForOneString(sql, cerNo);
			}
		}else{
			row = userList.get(0);
			loginName = StringUtil.safe2String(row.get("loginName"));
		}
		
		ret.put("loginName", loginName);
		return ret;
	}

	public void quickRegister(String identityId, String loginName,
			String loginPwd) {
		if(StringUtil.isBlank(identityId)){
			throw new RpcException("身份认证编号不能为空。");
		}
		if(StringUtil.isBlank(loginName)){
			throw new RpcException("登录名不能为空。");
		}
		loginName = loginName.trim();
		if(StringUtil.isBlank(loginPwd)){
			throw new RpcException("密码不能为空。");
		}
		loginPwd = loginPwd.trim();
		long cnt = DaoUtil.getInstance().queryForOneLong("select count(1) as cnt from t_pt_yh y where UPPER(y.login_name) = ?", loginName.toUpperCase());
		if(cnt>0){
			throw new RpcException("登录名已经有人使用，请更换其他登录名。");
		}
		String selectSql = "select * from sysmgr_identity i where i.identity_id = ? ";
		Map<String,Object> row = DaoUtil.getInstance().queryForRow(selectSql, identityId);
		if(row==null || row.isEmpty()){
			throw new RpcException("身份认证编号不正确："+identityId+"。");
		}
		
		String userId = UUIDUtil.getUUID();
		String pwd = MD5Util.MD5Encode(loginPwd);
		String userName = StringUtil.safe2String(row.get("userName"));
		String zyJsId = "default_role";
		String telphone = StringUtil.safe2String(row.get("mobile"));
		String cerType = StringUtil.safe2String(row.get("cerType"));
		String cerNo = StringUtil.safe2String(row.get("cerNo"));
		String userType = "1";
		Calendar now = DateUtil.getCurrentTime();
		String yxBj = "1";
		String checkState = "0";
		String mobCheckState = "1";
		String identityCheckState = "0"; 
		
		cnt = DaoUtil.getInstance().queryForOneLong("select count(1) as cnt from t_pt_yh y where y.cer_type = ? and y.cer_no = ?", cerType, cerNo);
		if(cnt>0){
			throw new RpcException("证件号码"+cerNo+"已经有关联的用户。");
		}
		
		String sql = "insert into t_pt_yh (user_id,login_name,user_pwd,user_name,zy_js_id,telphone,"
				+ " cer_type, cer_no, user_type,create_time,yx_bj,"
				+ " check_state,mob_check_state,identity_check_state) "
				+ " values (?,?,?,?,?, ?,?,?,?,?, ?,?,?,?)";
		DaoUtil.getInstance().execute(sql, userId,loginName,pwd,userName,zyJsId,telphone,
				cerType,cerNo,userType,now,yxBj,
				checkState,mobCheckState,identityCheckState);
	}

}
