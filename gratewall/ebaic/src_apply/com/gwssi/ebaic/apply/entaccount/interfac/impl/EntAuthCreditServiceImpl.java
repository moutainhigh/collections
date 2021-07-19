package com.gwssi.ebaic.apply.entaccount.interfac.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwssi.ebaic.apply.entaccount.interfac.api.EntAuthCreditService;
import com.gwssi.ebaic.apply.entaccount.service.EntAuthService;
import com.gwssi.rodimus.rpc.ParameterName;
import com.gwssi.torch.util.StringUtil;

/**
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Service(value="entAuthCreditServiceImpl")
public class EntAuthCreditServiceImpl implements EntAuthCreditService{

	@Autowired
	EntAuthService entAuthService;
	
	public Map<String, String> getServerCode(@ParameterName(value="regNo")String regNo) {
		Map<String, Object> map = entAuthService.getServerCode(regNo);
		Map<String, String> ret = new HashMap<String,String>();
		ret.put("startTime", StringUtil.safe2String(map.get("startTime")));
		ret.put("serverCode", StringUtil.safe2String(map.get("serverCode")));
		ret.put("entName", StringUtil.safe2String(map.get("entName")));
		return ret;
	}

	public Map<String, String> checkEntAuth(@ParameterName(value="authCode")String authCode, @ParameterName(value="serverCode")String serverCode) {
		String flag = entAuthService.checkEntAuth(authCode, serverCode);
		Map<String, String> ret = new HashMap<String,String>();
		//ret.put("authEntName", authEntName);
		ret.put("authFlag", flag);
		return ret;
	}

	public boolean isECert(@ParameterName(value="regNo")String regNo) {
		Map<String,Object> map = entAuthService.isECert(regNo);
		if(map!=null){
			Boolean ret = (Boolean)map.get("result");
			return ret;
		}else{
			return Boolean.FALSE;
		}
	}

}
