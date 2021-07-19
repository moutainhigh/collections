package com.ly.filter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ly.dao.impl.TokenKeyDao;
import com.ly.pojo.TokenKey;

@Component
public class MyService {

	@Autowired
	private TokenKeyDao tokenkeydao;
	
	//http://localhost:8080/soup/api/dologin.do?name=admin&pwd=root
	public boolean isLogin(String tokens) {
		boolean rs = false;
		
		List<TokenKey> keys = tokenkeydao.find("from TokenKey t where t.keyStr=? ",tokens+"_hao123");
		
		if(keys!=null&&keys.size()>0){
			rs = true;
		}
		return rs;
	}
}