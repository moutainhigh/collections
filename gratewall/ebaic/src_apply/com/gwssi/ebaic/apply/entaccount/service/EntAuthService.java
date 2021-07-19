package com.gwssi.ebaic.apply.entaccount.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.util.StringUtil;

@Service("entAuthService")
public class EntAuthService {
	
	/**
	 * 是否电子营业执照，只有电子营业执照，才可以执行企业认证服务。
	 * 
	 * @param regNo 注册号或统一社会信用代码。
	 * @return
	 */
	public Map<String, Object> isECert(String regNo) {
		Map<String,Object> ret = new HashMap<String,Object>();
		if(StringUtil.isBlank(regNo)){
			throw new EBaicException("注册号或统一社会信用代码不能为空。");
		}
		String sql = "select count(1) as cnt from cp_account a left join cp_rs_ent t on a.uni_scid=t.uni_scid and t.reg_no is not null where a.uni_scid=? or t.reg_no=?";
		long cnt = DaoUtil.getInstance().queryForOneLong(sql, regNo, regNo);
		boolean isECert = cnt > 0;
		ret.put("result", isECert);
		return ret;
	}
	
	
	/**
	 * 根据企业名称 企业注册号获取服务认证码
	 * @param entName
	 */
	public Map<String,Object> getServerCode(String regNo){
		//企业校验
		String entName = this.checkEnt(regNo);
		//校验成功后，获取认证服务码
		Map<String,Object> map = new HashMap<String,Object>();
		String sql = "insert into cp_account_auth (auth_id,oper_code,auth_code,ent_name,reg_no,unic_code," +
				"validate_code_start_time," +
				"validate_code_end_time," +
				"time_stamp_code) "
				+ " values (sys_guid(),?,?,?,?,?," +
				"to_date(?,'yyyy-MM-dd HH24:mi:ss')," +
				"to_date(?,'yyyy-MM-dd HH24:mi:ss')," +
				"to_date(?,'yyyy-MM-dd HH24:mi:ss'))";
		Random r = new Random();
		String authCode = String.valueOf(r.nextInt(999999));//认证服务码,6位随机数字
		String operCode = String.valueOf(r.nextInt(999999));//校验码
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		java.sql.Date sqlStartTime = new java.sql.Date(cal.getTime().getTime());
		cal.add(Calendar.MINUTE, 30);
		java.sql.Date sqlEndTime = new java.sql.Date(cal.getTime().getTime());
		DaoUtil.getInstance().execute(sql, operCode, authCode, entName, regNo, "",sdf.format(sqlStartTime),sdf.format(sqlEndTime),sdf.format(sqlStartTime));
		map.put("startTime", sdf.format(sqlStartTime));
		map.put("serverCode", operCode);
		map.put("entName", entName);
		return map;
	}
	/**
	 * 根据服务认证码获取企业认证结果
	 * @param serverCode
	 */
	public String checkEntAuth(String authCode,String serverCode){
		if(StringUtil.isBlank(authCode)){
			throw new EBaicException("企业验证码不能为空。");
		}
		if(StringUtil.isBlank(serverCode)){
			throw new EBaicException("企业认证服务码不能为空。");
		}
		String sql = "select a.validate_code_end_time,a.oper_code from cp_account_auth a where a.auth_code = ?";
		Map<String, Object> ret = DaoUtil.getInstance().queryForRow(sql, authCode);
		if(ret==null){
			return "1";//认证失败
		}
		Calendar sqlEndTime = (Calendar)ret.get("validateCodeEndTime");
		String operCode = (String) ret.get("operCode");
		if(serverCode.equals(operCode)){
			Calendar cal = Calendar.getInstance();
			Date nowTime = new Date(cal.getTime().getTime());
			Date endTime = new Date(sqlEndTime.getTime().getTime());
			if(nowTime.getTime()>endTime.getTime()){
				return "2";//认证超时
			}else{
				return "0";//认证成功
			}
		}else{
			return "1";//认证失败
		}
		
	}
	/**
	 * 根据认证码修改认证后的认证状态
	 * @param state
	 * @param authCode
	 */
	public void updateState(String state,String authCode){
		if(StringUtil.isBlank(authCode)){
			throw new EBaicException("企业验证码不能为空。");
		}
		if(StringUtil.isBlank(state)){
			throw new EBaicException("认证状态不能为空。");
		}
		String sql = "update cp_account_auth set auth_state=? where authCode=?";
		DaoUtil.getInstance().execute(sql,state,authCode);
	}
	
	/**
	 * 校验企业和企业注册号
	 * @param entName
	 * @param regNo
	 */
	private String checkEnt(String regNo){
//		if(StringUtil.isBlank(entName)){
//			throw new EBaicException("认证企业名称不能为空。");
//		}
		if(StringUtil.isBlank(regNo)){
			throw new EBaicException("认证企业注册号或统一社会信用代码不能为空。");
		}
		String sql="select c.ent_name from cp_rs_ent c where c.reg_no=? or c.uni_scid=?";
		String entName = DaoUtil.getInstance().queryForOneString(sql, regNo,regNo);
		if(StringUtil.isBlank(entName)){
			throw new EBaicException("该认证企业不存在，请重新输入。");
		}
		return entName ;
	}
	
}
