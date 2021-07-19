package com.gwssi.ebaic.approve.identity.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.ebaic.approve.util.ApproveUserUtil;
import com.gwssi.ebaic.common.util.ApplyUserUtil;
import com.gwssi.ebaic.domain.SysmgrIdentityBO;
import com.gwssi.ebaic.domain.SysmgrUser;
import com.gwssi.ebaic.domain.TPtYhBO;
import com.gwssi.optimus.util.MD5Util;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.indentity.IdentityCardUtil;
import com.gwssi.rodimus.indentity.domain.IdentityCardBO;
import com.gwssi.rodimus.sms.SmsUtil;
import com.gwssi.rodimus.sms.SmsVerCodeUtil;
import com.gwssi.rodimus.sms.domain.SmsBusiType;
import com.gwssi.rodimus.util.DateUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.rodimus.util.UUIDUtil;


/**
 * 现场身份认证。
 * 
 * @author xupeng
 */

@Service(value="sysIdentityService")
public class SysIdentityService {
	
	/**
	 * 调用公安部接口，校验身份信息。
	 * 
	 * @param cerNo
	 * @param name
	 * @return
	 * @throws EBaicException
	 */
	public IdentityCardBO checkIdentityCard(String name, String cerNo) throws EBaicException{
		if(StringUtil.isBlank(name)){
			throw new EBaicException("姓名不能为空。");
		}
		
		if(StringUtil.isBlank(cerNo)){
			throw new EBaicException("证件号码不能为空。");
		}
		
		IdentityCardBO ret = IdentityCardUtil.getIdentityCardInfo(name, cerNo);
		
		if(ret == null || StringUtil.isBlank(ret.getFolk())){
			ret = null;
			//throw new EBaicException("姓名或证件号码不正确，请检查后重新校验。");
		}
		
		saveIdentity(name, cerNo);
		
		return ret;
	}
	
	/**
	 * 保存证件类型不是身份证的证件信息
	 * @param name
	 * @param cerNo
	 * @param cerType
	 * @throws EBaicException
	 */
	public void saveCer(String name,String cerNo,String cerType) throws EBaicException{
		//1、校验数据
		if(StringUtil.isBlank(name)){
			throw new EBaicException("姓名不能为空！");
		}
		if(StringUtil.isBlank(cerNo)){
			throw new EBaicException("证件号码不能为空！");
		}
		if(StringUtil.isBlank(cerType)){
			throw new EBaicException("证件类型不能为空！");
		}
		//判断之前是否已经经过认证
		List<SysmgrIdentityBO> ret = this.getIdentityByCerNo(cerNo,cerType);
		
		
		if(ret.isEmpty()){
			SysmgrIdentityBO sysmgrIdentityBO = new SysmgrIdentityBO();
			String identityId = UUIDUtil.getUUID();
			sysmgrIdentityBO.setIdentityId(identityId);
			sysmgrIdentityBO.setName(name);
			sysmgrIdentityBO.setCerNo(cerNo);
			sysmgrIdentityBO.setCerType(cerType);
			sysmgrIdentityBO.setType("0");  //认证方式  0-现场认证
			sysmgrIdentityBO.setCreateTime(DateUtil.getCurrentTime());
			sysmgrIdentityBO.setTimestamp(DateUtil.getCurrentTime());
			DaoUtil.getInstance().insert(sysmgrIdentityBO);
		}else{
			SysmgrIdentityBO sysmgrIdentityBO = ret.get(0);
			sysmgrIdentityBO.setName(name);
			sysmgrIdentityBO.setCerNo(cerNo);
			sysmgrIdentityBO.setCerType(cerType);
			sysmgrIdentityBO.setType("0");
			sysmgrIdentityBO.setCreateTime(DateUtil.getCurrentTime());
			sysmgrIdentityBO.setTimestamp(DateUtil.getCurrentTime());
			DaoUtil.getInstance().update(sysmgrIdentityBO);
		}
		
	}
	
	/**
	 * 通过姓名、证件类型、证件号码比对，判断是否已经经过身份认证
	 * @param name
	 * @param cerNo
	 * @param cerType
	 * @return
	 * @throws EBaicException
	 */
	public boolean checkIsIdentity(String name,String cerNo,String cerType) throws EBaicException{
			//1、校验数据
			if(StringUtil.isBlank(name)){
				throw new EBaicException("姓名不能为空！");
			}
			if(StringUtil.isBlank(cerNo)){
				throw new EBaicException("证件号码不能为空！");
			}
			if(StringUtil.isBlank(cerType)){
				throw new EBaicException("证件类型不能为空！");
			}
			
			String sql = "select * from sysmgr_identity i where (i.type = '0' or i.type ='1') and i.flag = '1' and i.app_user_id is not null and i.cer_no = ? and i.cer_type = ? and i.name = ?";
			List<String> params = new ArrayList<String>();
			params.add(cerNo);
			params.add(cerType);
			params.add(name);
			List<Map<String,Object>> ret = DaoUtil.getInstance().queryForList(sql, params);
			if(ret.isEmpty()){
				return true;
			}else{
				return false;
			}
	}
	
	
	/**
	 * 根据证件号，移动电话， 校验移动电话是否已经被当前证件号使用
	 * 如果移动电话已经存在并且当前证件号使用，或者移动电话码不存在，返回true
	 * 否则，返回false
	 * @param cerNo
	 * @param mobile
	 * @return
	 * @throws EBaicException
	 */
	public boolean checkMobileIsExist(String cerNo,String mobile) throws EBaicException{
		String sql = "select * from t_pt_yh yh where yh.cer_no = ? and yh.mobile = ?";
		List<String> params = new ArrayList<String>();
		params.add(cerNo);
		params.add(mobile);
		List<Map<String,Object>> ret = DaoUtil.getInstance().queryForList(sql, params);
		if(ret!=null && ret.size()>0){
			return true;
		}else{
			String sqlNewMobile = "select * from t_pt_yh yh where yh.mobile = ?";
			List<Map<String,Object>> retNew = DaoUtil.getInstance().queryForList(sqlNewMobile, mobile);
			if(retNew!=null && retNew.size()>0){
				return false;
			}else{
				return true;	
			}
		}
	}
	
	
	/**
	 * 现场身份核查通过后，保存校验信息。
	 * 
	 * @param name
	 * @param cerNo
	 * @throws EBaicException
	 */
	private void saveIdentity(String name , String cerNo) throws EBaicException{
		
		//1、参数判断
		if(StringUtil.isBlank(name)){
			throw new EBaicException("姓名不能为空。");
		}
		
		if(StringUtil.isBlank(cerNo)){
			throw new EBaicException("证件号码不能为空。");
		}
		
		//2、判断之前该身份信息是否做过认证
		List<SysmgrIdentityBO> ret = this.getIdentityByCerNo(cerNo,"1");
		
		//3、执行对应的更新或者插入操作
		Calendar now = DateUtil.getCurrentTime();
		if(ret.isEmpty()){
			//插入操作
			SysmgrIdentityBO sysmgrIdentityBO = new SysmgrIdentityBO();
			String identityId = UUIDUtil.getUUID();
			sysmgrIdentityBO.setIdentityId(identityId);
			sysmgrIdentityBO.setType("0");// 身份认证方式：0-现场，1-移动客户端
			sysmgrIdentityBO.setCerNo(cerNo);
			sysmgrIdentityBO.setName(name);
			sysmgrIdentityBO.setCerType("1"); // 证件类型，1-身份证
			sysmgrIdentityBO.setTimestamp(now);
			sysmgrIdentityBO.setCreateTime(DateUtil.getCurrentTime());
			DaoUtil.getInstance().insert(sysmgrIdentityBO);
		}else{
			//更新操作
			SysmgrIdentityBO sysmgrIdentityBO = ret.get(0);
			sysmgrIdentityBO.setType("0");// 身份认证方式：0-现场，1-移动客户端
			sysmgrIdentityBO.setCerNo(cerNo);
			sysmgrIdentityBO.setName(name);
			sysmgrIdentityBO.setCerType("1");// 证件类型，1-身份证
			sysmgrIdentityBO.setTimestamp(now);
			DaoUtil.getInstance().update(sysmgrIdentityBO);
		}
	}
	
	/**
	 * 手机验证完成后，更新sysmgr_identity表中的mobile字段。
	 *  TODO 确认需求：是否需要校验这个移动电话码是否有其他人用过。
	 * @param cerNo
	 * @param mobile
	 * @throws EBaicException
	 */
	public void saveIdentityMobile(String cerNo,String cerType,String mobile, String verCode){
		//1、参数判断
		if(StringUtil.isBlank(mobile) ){
			throw new EBaicException("移动电话码不能为空。");
		}
		if(StringUtil.isBlank(cerType)){
			throw new EBaicException("证件类型不能为空。");
		}
		if(StringUtil.isBlank(cerNo) && "1".equals(cerType)){
			throw new EBaicException("证件号码不能为空。");
		}
		
		
		
		//2、校验手机验证码
		SmsVerCodeUtil.verify(mobile, verCode);
		
		//3、根据cerNo,cerType判断是否进行身份认证
		List<SysmgrIdentityBO> ret = this.getIdentityByCerNo(cerNo,cerType);
		if(ret.isEmpty()){
			throw new EBaicException("数据错误！");
		}
		SysmgrIdentityBO sysmgrIdentityBO = ret.get(0);
		sysmgrIdentityBO.setMobile(mobile);
		Calendar now = DateUtil.getCurrentTime();
		sysmgrIdentityBO.setTimestamp(now);
		DaoUtil.getInstance().update(sysmgrIdentityBO);
	}
	
	
	/**
	 * 审核身份认证信息。
	 * 
	 * 认证通过、或者认证不通过的执行方法。
	 * 
	 * @param cerNo
	 * @param flag 1-通过，2-不通过
	 * @param approveMsg
	 * 
	 * @throws EBaicException
	 */
	public void approveIdentity(String cerNo,String cerType,String flag,String approveMsg){
		
		SysmgrUser sysmgrUser = ApproveUserUtil.getLoginUser();
		if(sysmgrUser==null){
			throw new EBaicException("获取当前用户信息失败！");
		}
		
		//1、校验数据
		if("2".equals(flag) && StringUtil.isBlank(approveMsg) ){
			throw new EBaicException("审批意见不能为空。");
		}
		
		if("1".equals(cerType) && StringUtil.isBlank(cerNo)){
			throw new EBaicException("证件号码不能为空。");
		}
		
		if(StringUtil.isBlank(flag)){
			throw new EBaicException("审批状态不能为空。");
		}
		
		if(!"1".equals(flag) && !"2".equals(flag)){
			throw new EBaicException("审批状态取值不正确，请联系系统管理员。");
		}
		
		// 取得身份认证记录
		List<SysmgrIdentityBO> ret = this.getIdentityByCerNo(cerNo,cerType);
		if(ret.isEmpty()){
			throw new EBaicException("根据证件号码未找到身份认证记录，请先校验姓名和证件号码。");
		}
		//取得验证移动电话码
		if(ret.get(0)==null || StringUtil.isBlank(ret.get(0).getMobile())){
			throw new EBaicException("请验证移动电话码。");
		}
		
//		// 取得当前登录用户
//		String currentUserId = ApproveUserUtil.getCurrentUserId();
//		if(StringUtil.isBlank(currentUserId)){
//			throw new EBaicException("登录超时，请重新登录。");
//		}
		
		Calendar now = DateUtil.getCurrentTime();
		
		//2、更新身份认证信息
		SysmgrIdentityBO sysmgrIdentityBO = ret.get(0);
		sysmgrIdentityBO.setFlag(flag);
		sysmgrIdentityBO.setApproveMsg(approveMsg);
		sysmgrIdentityBO.setApproveUserId(sysmgrUser.getUserId());
		sysmgrIdentityBO.setRegOrg(sysmgrUser.getOrgCodeFk());
		sysmgrIdentityBO.setApproveUserName(sysmgrUser.getUserName());
		sysmgrIdentityBO.setApproveDate(now);
		DaoUtil.getInstance().update(sysmgrIdentityBO);
		
		//3、flag为1、认证通过、更新网登账号信息
		if("1".equals(flag)){
			String appUserId = sysmgrIdentityBO.getAppUserId();
			if(StringUtil.isBlank(appUserId)){
				throw new EBaicException("请先进行账号绑定！");
			}
			TPtYhBO yhBO = ApplyUserUtil.getUserByCerNoAndCerType(cerNo, cerType);
			if(yhBO==null){
				throw new EBaicException("获取用户信息失败！");
			}
			yhBO.setIdentityCheckState("1");    	//身份认证状态更新为1-已经认证
			yhBO.setCheckState("0");				//更新身份证号为合法0-合法
			if(StringUtil.isNotBlank(sysmgrIdentityBO.getMobile())){
				yhBO.setMobile(sysmgrIdentityBO.getMobile());
				yhBO.setMobCheckState("1");			//更新移动电话码为1-已验证
			}
			DaoUtil.getInstance().update(yhBO);
		}
		
	}
	
	/**
	 * 根据cerNo判断是否存在对应的网上登记用户
	 * @param cerNo
	 * @return
	 * @throws EBaicException
	 */
	public TPtYhBO checkApplyUserExisit(String cerNo,String cerType) {
		if(StringUtil.isBlank(cerNo)){
			throw new EBaicException("参数传递错误！");
		}
		TPtYhBO yhBO = new TPtYhBO();
		yhBO = ApplyUserUtil.getUserByCerNoAndCerType(cerNo,cerType);
		return yhBO;
	}
	
	
	/**
	 * 对没有注册过的数据，生成账号并绑定
	 * @param cerNo
	 * @param yhBO
	 * @throws EBaicException
	 */
	public void createAndBinding(String cerNo,String cerType,TPtYhBO yhBO) {
		//1、数据校验
		if("1".equals(cerType) && StringUtil.isBlank(cerNo)){
			throw new EBaicException("身份证号不能为空！");
		}
		if(yhBO==null){
			throw new EBaicException("注册信息输入错误");
		}
		if(StringUtil.isBlank(yhBO.getLoginName())){
			throw new EBaicException("申请账号不能为空！");
		}
		if(StringUtil.isBlank(yhBO.getEmail())){
			throw new EBaicException("申请邮箱不能为空！");
		}
		if(StringUtil.isBlank(yhBO.getAddress())){
			throw new EBaicException("联系地址不能为空！");
		}
		//2、根据cerNo判断是否有注册，如果没有则新建用户
		TPtYhBO ptYhBO = null;
		ptYhBO = ApplyUserUtil.getUserByCerNoAndCerType(cerNo,cerType);
		if(ptYhBO!=null){
			throw new EBaicException("该身份证号存在注册信息！");
		}
		//3、判断输入的账号名称是否已经存在
		String loginName = yhBO.getLoginName();
		List<TPtYhBO> list = this.getTptYhboByloginName(loginName);
		if(list!=null && !(list.isEmpty())){
			throw new EBaicException("该账号名称已经存在！");
		}
		List<SysmgrIdentityBO> ret = this.getIdentityByCerNo(cerNo,cerType);
		if(ret.isEmpty()){
			throw new EBaicException("身份认证错误！");
		}
		SysmgrIdentityBO identityBO = ret.get(0);
		if(StringUtil.isBlank(identityBO.getMobile())){
			throw new EBaicException("请进行移动电话认证！");
		}
		
		//根据身份证号判断性别
		if("1".equals(cerType)){
			String sex = cerNo.substring(16, 17);
			if(Integer.parseInt(sex)%2==0){
				yhBO.setSex("2");       //2-女
			}else{
				yhBO.setSex("1");		//1-男
			}
		}
		
		String userId = UUIDUtil.getUUID();
		yhBO.setUserId(userId);
		yhBO.setUserName(identityBO.getName());
		String userPwd = ApplyUserUtil.genNewPassword(); //生成密码
		if(StringUtil.isBlank(userPwd)){
			throw new EBaicException("生成密码失败！");
		}
		String md5userPwd = MD5Util.MD5Encode(userPwd);
		yhBO.setUserPwd(md5userPwd); 				//更新密码
		yhBO.setCerType(identityBO.getCerType());
		yhBO.setCerNo(identityBO.getCerNo());
		yhBO.setIdentityCheckState("1");		//更新身份认证状态为1-已经认证
		if(StringUtil.isNotBlank(identityBO.getMobile())){
			yhBO.setMobile(identityBO.getMobile());
			yhBO.setMobCheckState("1");			//更新手机校验状态为1-已经校验
		}
		yhBO.setCheckState("0");				//更新身份证号合法状态0-合法
		yhBO.setYxBj("1");
		yhBO.setUserType("1");
		Calendar calendar = DateUtil.getCurrentTime();
		yhBO.setCreateTime(calendar);
		DaoUtil.getInstance().insert(yhBO);
		
		//3、更新sysmgr_identity表
		identityBO.setAppUserId(userId);
		identityBO.setTimestamp(calendar);
		identityBO.setFlag("1");
		DaoUtil.getInstance().update(identityBO);
		
		//账号生成并且绑定成功，发送短信至认证手机
		
		String mobile = yhBO.getMobile();
		String mobCheckSate = yhBO.getMobCheckState();
		if(StringUtil.isNotBlank(mobile) && StringUtil.isNotBlank(mobCheckSate) && "1".equals(mobCheckSate)){
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("loginName", userPwd);
			params.put("pwd", userPwd);
			SmsUtil.send(mobile, SmsBusiType.IDENTITY_MSG, params);
		}
		
	}
	
	
	/**
	 * 对现有账号进行绑定。
	 * @param cerNo
	 * @param curName
	 * @throws EBaicException
	 */
	public void bingding(String cerNo,String cerType,String curName){
		//1、数据校验
		if("1".equals(cerType) && StringUtil.isBlank(cerNo)){
			throw new EBaicException("证件号码不能为空！");
		}
		if(StringUtil.isBlank(curName)){
			throw new EBaicException("现有账号不能为空！");
		}
		TPtYhBO yhBO = ApplyUserUtil.getUserByCerNoAndCerType(cerNo, cerType);
		if(yhBO==null){
			throw new EBaicException("获取用户信息失败！");
		}
		if(!curName.equals(yhBO.getLoginName())){
			throw new EBaicException("现有账号与数据库不匹配！");
		}
		List<SysmgrIdentityBO> ret = this.getIdentityByCerNo(cerNo,cerType);
		if(ret.isEmpty()){
			throw new EBaicException("获取身份认证信息失败!");
		}
		//2、获取用户ID，更新sysmgr_identity表
		SysmgrIdentityBO identityBO = ret.get(0);
		identityBO.setAppUserId(yhBO.getUserId());
		DaoUtil.getInstance().update(identityBO);
		
	}
	
	
	/**
	 * 重置密码，并返回新密码
	 * @param cerNo
	 * @return
	 * @throws EBaicException
	 */
	public String resetPassword(String cerNo,String cerType) {
		//1、校验数据
		if(StringUtil.isBlank(cerNo)){
			throw new EBaicException("证件号码不能为空！");
		}
		if(StringUtil.isBlank(cerType)){
			throw new EBaicException("证件类型不能为空！");
		}
		TPtYhBO yhBO = null;
		yhBO = ApplyUserUtil.getUserByCerNoAndCerType(cerNo, cerType);
		if(yhBO==null){
			throw new EBaicException("获取用户失败！");
		}
		
		//2、重置密码
		String userId = yhBO.getUserId();
		String newPassword = ApplyUserUtil.resetPassword(userId);
		
		return newPassword;
	}
	
	
	
	/**
	 * 根据证件号码，证件类型判断是否进行过身份认证操作,并返回bo集合。
	 * 
	 * @param cerNo
	 * @return
	 */
	private List<SysmgrIdentityBO> getIdentityByCerNo(String cerNo,String cerType){
		String sql = "select * from sysmgr_identity where cer_no = ? and cer_type = ? order by timestamp desc";
		List<SysmgrIdentityBO> ret = DaoUtil.getInstance().pageQueryForListBo(sql, SysmgrIdentityBO.class, cerNo,cerType);
		return ret;
	}
	
	
	/**
	 * 根据证件号码判断是否进行过身份认证,并且认证通过,并返回bo集合。
	 * @param cerNo
	 * @return
	 */
	protected List<SysmgrIdentityBO> getIdentitySuccessByCerNo(String cerNo){
		String sql = "select * from sysmgr_identity where cer_no = ? and flag = '1' order by timestamp desc";
		List<SysmgrIdentityBO> ret = DaoUtil.getInstance().pageQueryForListBo(sql, SysmgrIdentityBO.class, cerNo);
		return ret;
	}
	
	/**
	 * 根据登录名获得用户Bo集合
	 * @param loginName
	 * @return
	 */
	public List<TPtYhBO> getTptYhboByloginName(String loginName){
		String sql = "select * from t_pt_yh t where t.login_name = ?";
		List<TPtYhBO> list = DaoUtil.getInstance().pageQueryForListBo(sql, TPtYhBO.class, loginName);
		return list;
	}
	

}
