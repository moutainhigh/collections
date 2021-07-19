package com.gwssi.ebaic.mobile.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.ebaic.mobile.api.LoginService;
import com.gwssi.optimus.util.MD5Util;
import com.gwssi.rodimus.dao.BaseDaoUtil;
import com.gwssi.rodimus.dao.DaoUtil;
import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.exception.RpcException;
import com.gwssi.rodimus.indentity.IdentityCardUtil;
import com.gwssi.rodimus.sms.SmsVerCodeUtil;
import com.gwssi.rodimus.util.DateUtil;
import com.gwssi.rodimus.util.MapUtil;
import com.gwssi.rodimus.util.StringUtil;
import com.gwssi.torch.util.UUIDUtil;

/**
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Service(value="loginServiceImpl")
public class LoginServiceImpl implements LoginService {
	
	

	public Map<String, String> personLogin(String cerType, String cerNo, String password) {
		if(StringUtils.isBlank(cerType)){
			//1-中华人民共和国居民身份证
			cerType = "1";
		}
		if(StringUtils.isBlank(cerNo)){
			throw new RpcException("证件号码不能为空。");
		}
		if(StringUtils.isBlank(password)){
			throw new RpcException("登录密码不能为空。");
		}
		StringBuffer sql = new StringBuffer();
		//md5编码后的密码值
		String md5Pwd =  MD5Util.MD5Encode(password);
		//查询用户表信息
		sql.append(" select y.user_id,y.user_name, ");
		sql.append(" case when y.facility_id is null then 'account' else 'agent' end userType, ");
		sql.append(" y.check_state from t_pt_yh y where ");
		sql.append(" y.cer_type=? and y.cer_no=? and y.user_pwd=? and y.yx_bj='1' ");
		List<String> params = new ArrayList<String>();
		params.add(cerType);
		params.add(cerNo);
		params.add(md5Pwd);
		BaseDaoUtil dao = DaoUtil.getInstance();
		Map<String,Object> userMap = dao.queryForRow(sql.toString(), params);
		if(userMap==null || userMap.isEmpty()){
			throw new RpcException("登录信息有误，请核对后重试。");
		}
		Map<String, String> retMap  = new HashMap<String, String>();
		String checkState = StringUtil.safe2String(userMap.get("checkState"));
		retMap = MapUtil.oMap2strMap(userMap);
		//身份证校验未通过
		if("1".equals(checkState)){
			return retMap;
		}
		//查询身份认证状态
		sql.setLength(0);
		params.clear();
		sql.append(" select i.flag as identity_state from sysmgr_identity i where i.flag<>'3' and t.person_sign='1' and i.cer_type=? and i.cer_no=? order by i.timestamp desc");
		params.add(cerType);
		params.add(cerNo);
		Map<String, Object> indiMap = dao.queryForRow(sql.toString(), params);
		if(indiMap==null || indiMap.isEmpty()){
			retMap.put("identityState", "-1");
			return retMap;
		}
		String identityState = StringUtil.safe2String(indiMap.get("identityState"));
		retMap.put(identityState, identityState);
		return retMap;
	}

	public Map<String, String> eEntLogin(String entName, String password, String mobile, String mobileValidateCode) {
		
		if(StringUtils.isBlank(entName)){
			throw new RpcException("企业名称不能为空。");
		}
		if(StringUtils.isBlank(password)){
			throw new RpcException("登录密码不能为空。");
		}
		String md5Pwd =  MD5Util.MD5Encode(password);
		String sql = "select a.account_id,a.leg_mobile from cp_account a where a.ent_name = ? and a.account_pwd = ? and a.accout_state='0' ";
		Map<String,Object> row = DaoUtil.getInstance().queryForRow(sql, entName,md5Pwd);
		if(row==null || row.isEmpty()){
			throw new RpcException("企业名称或密码不正确。");
		}
		if(StringUtils.isBlank(mobile)){
			throw new RpcException("移动电话不能为空");
		}		
		String accountId = StringUtil.safe2String(row.get("accountId"));
		mobile = mobile.trim();
		String legMobile = StringUtil.safe2String(row.get("legMobile"));
		

		Map<String, String> ret = new HashMap<String, String>();
		ret.put("entName", entName);
		if(!mobile.equals(legMobile)){
			sql = "select mgr.manager_id,mgr.operation from cp_account_manager mgr where mgr.account_id=? and mgr.mobile=? and mgr.acc_state='1'";
			Map<String,Object> mgrRow = DaoUtil.getInstance().queryForRow(sql, accountId,mobile);
			if(mgrRow==null||mgrRow.isEmpty()){
				throw new RpcException("移动电话不正确。");
			}
			SmsVerCodeUtil.verify(mobile, mobileValidateCode);
			String userAuth = StringUtil.safe2String(row.get("operation"));
			String managerId = StringUtil.safe2String(row.get("managerId"));
			ret.put("userAuth", userAuth);
			ret.put("userType", "mgr");
			ret.put("accountId", managerId);
		}else{
			SmsVerCodeUtil.verify(mobile, mobileValidateCode);
			ret.put("userAuth", "all");
			ret.put("userType", "leg");
			ret.put("accountId", accountId);
		}
		
		return ret;
	}
	
	public Map<String, String> personEntry(String name, String cerType, String cerNo, String mobile,
			String mobileVerCode,String entryType) {
		// 参数校验
		if(StringUtil.isBlank(name)){ 
			throw new RpcException("姓名不能为空。");
		}
		if(StringUtil.isBlank(cerType)){
			cerType = "1";//默认为身份证号码
		}
		if(StringUtil.isBlank(cerNo)){
			throw new RpcException("身份证号不能为空。");
		}
		if(StringUtil.isBlank(mobile)){
			throw new RpcException("移动电话不能为空。");
		}
		if(StringUtil.isBlank(mobileVerCode)){
			throw new RpcException("校验码不能为空。");
		}
		if(StringUtil.isBlank(entryType)){
            throw new RpcException("进入类型不能为空。");
        }
		name = name.trim();
        cerType = cerType.trim();
        cerNo = cerNo.trim();
        mobile = mobile.trim();
        mobileVerCode = mobileVerCode.trim();
        entryType=entryType.trim();
		boolean isIdCardOk = IdentityCardUtil.check(name, cerNo);
        if(!isIdCardOk){
            throw new EBaicException("姓名和身份证号码不匹配，请查对后重新输入。");
        }
		// 定义返回值
		Map<String, String> ret = new HashMap<String,String>();
		
		String sql = "select i.identity_id, i.name, i.mobile, i.lock_sign,i.flag  from sysmgr_identity i "
				+ " where i.cer_type = ? and i.cer_no = ? and i.type='0' and person_sign = '1' and i.flag!='3' "
				+ " order by i.timestamp desc ";
		BaseDaoUtil dao = DaoUtil.getInstance();
		List<Map<String,Object>> rows = dao.queryForList(sql, cerType, cerNo);
		
		// 第一次进入或者重新认证
		if(rows==null || rows.isEmpty()){
		    //entryType 进入类型 0-身份认证入口；1-业务确认入口
		    if("1".equals(entryType)) {
		        throw new RpcException("未进行身份认证，请先进行身份认证。");
		    }
			// 校验手机校验码是否正确
			SmsVerCodeUtil.verify(mobile, mobileVerCode);
			if("1".equals(cerType)){
				boolean isIdInfoOk = IdentityCardUtil.check(name, cerNo);
				if(!isIdInfoOk){
					throw new RpcException("姓名和身份证号码不匹配，请查对后再试。");
				}
			}
			sql = "insert into sysmgr_identity(identity_id, type, name, cer_type, cer_no,person_sign,lock_sign,flag,timestamp,mobile ,create_time) "
					+ " values(?,?,?, ?,?,?, ?,?,?,?,?) ";
			String identityId = UUIDUtil.getUUID();
			Calendar now = DateUtil.getCurrentTime();
			dao.execute(sql, identityId,"0", name, cerType, cerNo, "1","0","-1",now,mobile,now);
			ret.put("identityId", identityId);
			ret.put("flag", "-1");
			return ret;
		}
		// 应该最多有1条记录返回，一旦返回多条数据，则取最新1条
		Map<String,Object> row = rows.get(0);
		String identityId = StringUtil.safe2String(row.get("identityId"));
		//String nameInDb = StringUtil.safe2String(row.get("name"));
		String mobileInDb = StringUtil.safe2String(row.get("mobile"));
		String lockSign = StringUtil.safe2String(row.get("lockSign"));
		String flag = StringUtil.safe2String(row.get("flag"));
		
		if(StringUtil.isBlank(mobileInDb)){
			// TODO 做个按钮直接反馈，采集identityId等关键信息
			throw new RpcException("数据异常，故障编号[NON_MOBILE_IN_IDENTITY]，请通过“意见反馈”功能联系系统管理员。");
		}
		mobileInDb = mobileInDb.trim();
		if(!mobileInDb.equals(mobile)){
			throw new RpcException(String.format("移动电话不正确，请输入原预留的%s，如原号码已经不可用，请联系系统管理员。",StringUtil.maskMobile(mobileInDb)));
		}
		// 校验手机校验码是否正确
		SmsVerCodeUtil.verify(mobile, mobileVerCode);
		ret.put("identityId", identityId);
		ret.put("flag", flag);
		ret.put("lockSign", lockSign);
		return ret;
	}
	
	public Map<String, String> entEntry(String entName, String regNo, String mobile, String mobileVerCode,String entryType) {
		// 参数校验
		if(StringUtil.isBlank(entName) && "0".equals(entryType)){ //TODO 校验长度
			throw new RpcException("企业名称不能为空。");
		}
		if(StringUtil.isBlank(regNo)){
			throw new RpcException("统一社会信用代码/注册号不能为空。");
		}
		if(StringUtil.isBlank(mobile)){
			throw new RpcException("联系人移动电话不能为空。");
		}
		if(StringUtil.isBlank(mobileVerCode)){
			throw new RpcException("校验码不能为空。");
		}
		if(StringUtil.isBlank(entryType)){
            throw new RpcException("进入类型不能为空。");
        }
		entName = entName.trim();
		regNo = regNo.trim();
		mobile = mobile.trim();
		mobileVerCode = mobileVerCode.trim();
		entryType=entryType.trim();
		// 定义返回值
		Map<String, String> ret = new HashMap<String,String>();
		
		String sql = "select i.identity_id, i.name, i.mobile, i.lock_sign,i.flag  from sysmgr_identity i "
				+ " where i.cer_no = ? and i.type='0' and person_sign = '0' and i.flag!='3' "
				+ " order by i.timestamp desc ";
		BaseDaoUtil dao = DaoUtil.getInstance();
		List<Map<String,Object>> rows = dao.queryForList(sql, regNo);
		
		// 第一次进入或者重新认证
		if(rows==null || rows.isEmpty()){
		    //entryType 进入类型 0-身份认证入口；1-业务确认入口
		    if("1".equals(entryType)) {
                throw new RpcException("未进行身份认证，请先进行身份认证。");
            }
			// 校验手机校验码是否正确
			SmsVerCodeUtil.verify(mobile, mobileVerCode);
			sql = "insert into sysmgr_identity(identity_id, type, name, cer_no,person_sign,lock_sign,flag,timestamp,mobile,create_time ) "
					+ " values(?,?,?, ?,?,?, ?,?,?,?) ";
			String identityId = UUIDUtil.getUUID();
			Calendar now = DateUtil.getCurrentTime();
			dao.execute(sql, identityId,"0", entName, regNo, "0","0","-1",now,mobile,now);
			ret.put("identityId", identityId);
			ret.put("flag", "-1");
			return ret;
		}
		// 应该最多有1条记录返回，一旦返回多条数据，则取最新1条
		Map<String,Object> row = rows.get(0);
		String identityId = StringUtil.safe2String(row.get("identityId"));
		String nameInDb = StringUtil.safe2String(row.get("name"));
		String mobileInDb = StringUtil.safe2String(row.get("mobile"));
		String lockSign = StringUtil.safe2String(row.get("lockSign"));
		String flag = StringUtil.safe2String(row.get("flag"));
		
		if(StringUtil.isBlank(mobileInDb)){
			// TODO 做个按钮直接反馈，采集identityId等关键信息
			throw new RpcException("数据异常，故障编号[NON_MOBILE_IN_IDENTITY]，请通过“意见反馈”功能联系系统管理员。");
		}
		mobileInDb = mobileInDb.trim();
		if(!mobileInDb.equals(mobile)){
			throw new RpcException(String.format("联系人移动电话不正确，请输入原预留的%s，如原号码已经不可用，请联系系统管理员。",StringUtil.maskMobile(mobileInDb)));
		}
		// 校验手机校验码是否正确
		SmsVerCodeUtil.verify(mobile, mobileVerCode);
		ret.put("identityId", identityId);
		ret.put("flag", flag);
		ret.put("lockSign", lockSign);
		ret.put("name", nameInDb);
		return ret;
	}

}
