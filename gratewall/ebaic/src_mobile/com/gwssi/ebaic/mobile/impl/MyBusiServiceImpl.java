package com.gwssi.ebaic.mobile.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.ebaic.mobile.api.MyBusiService;

@Service(value="myBusiServiceImpl")
public class MyBusiServiceImpl implements MyBusiService {

	public List<Map<String, Object>> getList(String userId, String entName, String oprType, String state, int pageSize,
			int pageIdx) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, Object> get(String gid) {
		// TODO Auto-generated method stub
		return null;
	}

	public void submit(String userId, String gid) {
		// TODO Auto-generated method stub

	}

	public void doTerminate(String gid, String reason, String password,
			String typeCo) {
		// TODO Auto-generated method stub
		
	}

}
