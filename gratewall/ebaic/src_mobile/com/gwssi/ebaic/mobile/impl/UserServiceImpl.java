package com.gwssi.ebaic.mobile.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.gwssi.ebaic.mobile.api.UserService;
import com.gwssi.ebaic.mobile.domain.UserBo;
import com.gwssi.optimus.util.DateUtil;
import com.gwssi.optimus.util.MD5Util;
import com.gwssi.rodimus.dao.BaseDaoUtil;
import com.gwssi.rodimus.dao.MobileDaoUtil;
import com.gwssi.rodimus.exception.RpcException;
import com.gwssi.rodimus.indentity.IdentityCardUtil;
import com.gwssi.torch.util.StringUtil;
import com.gwssi.torch.util.UUIDUtil;


/**
 * 用户服务实现类。
 * 
 * @author liuhailong
 */
@Service(value = "userServiceImpl")
public class UserServiceImpl implements UserService{



	/* (non-Javadoc)
	 * @see com.gwssi.mobile.api.service.UserService#regist(com.gwssi.mobile.api.domain.UserBo)
	 */
	public String regist(UserBo bo)  {
		// 0. 校验数据合法性
		Assert.notNull(bo, "用户数据");
		Assert.notNull(bo.getLoginName(), "登录名");
		Assert.notNull(bo.getUserName(), "姓名");
		Assert.notNull(bo.getUserPwd(), "密码");
		Assert.notNull(bo.getSex(), "性别");
		//Assert.notNull(bo.getTelphone(), "电话");
		Assert.notNull(bo.getCerNo(), "证件号码");
		Assert.notNull(bo.getMobile(), "移动电话");
		//Assert.notNull(bo.getZipCode(), "邮政编码");
		Assert.notNull(bo.getAddress(), "地址");
		Assert.notNull(bo.getEmail(), "电子邮箱");
		//Assert.notNull(bo.getPwdQuestion(), "密码找回问题");
		//Assert.notNull(bo.getPwdAnswer(), "密码找回答案");
		
		// 1. 检查身份证号码是否已经存在
		boolean isPass = checkCerNoExists(bo.getCerNo());
		if(!isPass){
			throw new RpcException("身份证号码已经被占用。");
		}
		
		// 2. 检查登录名是否已经存在
		isPass = checkLoginName(bo.getLoginName());
		if(!isPass){
			throw new RpcException("登录名已经存在。");
		}
		
		// 3. 数据处理
		String userId = UUIDUtil.getUUID() ;
		bo.setUserId(userId);//新生成用户编号
		bo.setLoginName(bo.getLoginName().trim());//对loginName进行trim处理
		bo.setUserPwd(MD5Util.MD5Encode(bo.getUserPwd()));//对密码进行MD5处理
		String zyJsId = "default_role";
		String countryCity = "156";
		String cerType = "1";
		String idCard = bo.getCerNo();
		String userType = "1";
		Calendar createTime = DateUtil.getCurrentDate();
		String yxBj = "1";
		String checkState = "1";
		
		// 4. 保存数据
		StringBuffer sql = new StringBuffer();
		sql.append("insert into t_pt_yh (user_id,login_name,user_pwd,user_name,zy_js_id,sex,telphone,email,mobile,")
		   .append("pwd_question,pwd_answer,id_card,address,zip_code,country_city,cer_type,cer_no,")
		   .append("user_type,create_time,yx_bj,check_state) values(?,?,?,?,?,?,?,?,?,?,  ?,?,?,?,?,?,?,?,  ?,?,?)");
		MobileDaoUtil.getInstance().execute(sql.toString(),
				bo.getUserId(),bo.getLoginName(),bo.getUserPwd(),bo.getUserName(),zyJsId,bo.getSex(),bo.getTelphone(),bo.getEmail(),bo.getMobile(),
				bo.getPwdQuestion(),bo.getPwdAnswer(),idCard,bo.getAddress(),bo.getZipCode(),countryCity,cerType,bo.getCerNo(),
				userType,createTime,yxBj,checkState );
		
		// 5. 返回userId
		return userId;
	}

	/* (non-Javadoc)
	 * @see com.gwssi.mobile.api.service.UserService#checkLoginName(java.lang.String)
	 */
	public boolean checkLoginName(String loginName)  {
		if (StringUtils.isEmpty(loginName)) {
			throw new RpcException("登录名不能为空。");
		}
		loginName = loginName.trim().toUpperCase();
		
		BaseDaoUtil dao = MobileDaoUtil.getInstance();
		String sql = "select count(1) as cnt from t_pt_yh u where UPPER(u.login_name) = ?";
		long cnt = dao.queryForOneLong(sql, loginName);
		
		if(cnt>0){
			return false;
		}else{
			return true;
		}
	}

	/* (non-Javadoc)
	 * @see com.gwssi.mobile.api.service.UserService#login(java.lang.String, java.lang.String)
	 */
	public Map<String,Object> login(String loginName, String password)
			 {
		// 0. 检查登录名、密码是否为空。
		if(StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password)){
			throw new RpcException("用户名或密码不能为空。");
		}
		
		// 1. 通过登录名、密码查询数据库
		// TODO and t.zy_js_id='default_role' ,需要确认都有哪些角色可以通过手机App登录
		// user_type='1' 表示是普通用户，不是管理员等特殊用户
		// yx_bj='1' 有效标记为1，表示是正常状态的用户，而不是被禁用的用户
        String sql = " SELECT t.user_name,t.user_id,t.check_state,t.cer_no from t_pt_yh t where t.login_name=? and user_pwd=? and yx_bj='1' ";
        String userPwd = MD5Util.MD5Encode(password);
        BaseDaoUtil dao = MobileDaoUtil.getInstance();
        Map<String, Object> row = dao.queryForRow(sql,loginName,userPwd);
        // 1.1. 未找到匹配的数据库记录，登录失败。
        if(row==null){
        	throw new RpcException("用户名密码错误，登录失败。");
        }
        
        // 2. 身份证核查
        String checkState = StringUtil.safe2String(row.get("checkState"));
        String userId = StringUtil.safe2String(row.get("userId"));
        String userName = StringUtil.safe2String(row.get("userName"));
        // 3. 返回数据
        Map<String,Object> ret = new HashMap<String, Object>();
        
        if(StringUtils.isEmpty(userName)){
        	throw new RpcException("库中用户真实姓名为空，请联系系统管理员。");
        }
        
        if(!"0".equals(checkState)){// 0表示核查通过
        	// 2.1. 执行身份证核查
        	String cerNo = StringUtil.safe2String(row.get("cerNo"));
        	if(StringUtils.isEmpty(cerNo)){
        		ret.put("code", -107);
        		ret.put("message", "身份证号码未通过公安系统核查，请检查后重新提交身份证号码，需和注册时填写的姓名匹配");
        		ret.put("userId", userId); 
        		return ret;
            }
        	boolean isCerNoOk = IdentityCardUtil.check(userId, cerNo);
        	if(!isCerNoOk){
        		// 身份证核查未通过
        		ret.put("code", -107);
        		ret.put("message", "身份证号码未通过公安系统核查，请检查后重新提交身份证号码，需和注册时填写的姓名匹配");
        		ret.put("userId", userId); 
        		return ret;
        	}else{
        		sql = "update t_pt_yh y set y.check_state = '0' where y.user_id = ?";
        		dao.execute(sql, userId);
        	}
        }
        ret.put("userId", userId); 
        ret.put("userName", userName);
        return ret;
	}
	


	/* (non-Javadoc)
	 * @see com.gwssi.mobile.api.service.UserService#logout(java.lang.String)
	 */
	public void logout(String userId)  {
		// DO NOTHING
	}

	/* (non-Javadoc)
	 * @see com.gwssi.mobile.api.service.UserService#getVerifyCode()
	 */
	public String getVerifyCode()  {
		//Deprecated , NO NEED TO IMPLEMENTED.
		return null;
	}

	/* (non-Javadoc)
	 * @see com.gwssi.mobile.api.service.UserService#reCerNo(java.lang.String, java.lang.String)
	 */
	public void reCerNo(String userId, String newCerNo)  {
		// 0. 用户编号不能为空
		if(StringUtils.isEmpty(userId)){
			throw new RpcException("用户编号不能为空");
		}
		// 1. 证件号码不能为空
		if(StringUtils.isEmpty(newCerNo)){
			throw new RpcException("身份证号码不能为空");
		}
		// 2. 检查身份证号码是否已经存在
		boolean isPass = checkCerNoExists(newCerNo);
		if(!isPass){
			throw new RpcException("身份证号码已经被占用。");
		}
		// 3. 保存身份证号码
		String sql = "update t_pt_yh y set y.id_card = ?, y.cer_no=?,y.check_state='0' where y.user_id = ? and y.check_state='1'";
		int rows = MobileDaoUtil.getInstance().execute(sql, newCerNo,newCerNo,userId);
		if(rows==0){ 
			//未更新到数据
			throw new RpcException("非法的用户编号。");
		}
	}
	
	/**
	 * 检查身份证号码是否已经存在。
	 * 只检查状态为有效的用户。
	 * 
	 * @param cerNo
	 * @return 如果已经存在，返回false，表示核查失败；反之，返回true
	 */
	private boolean checkCerNoExists(String cerNo){
		if(StringUtils.isBlank(cerNo)){
			return false;
		}
		String sql = "select count(1) as cnt from t_pt_yh t where t.cer_no = ? and t.yx_bj='1'";
		long cnt = MobileDaoUtil.getInstance().queryForOneLong(sql, cerNo);
		if(cnt>0){
			return false;
		}else{
			return true;
		}
	}
	
}
