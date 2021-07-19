package com.gwssi.ebaic.mobile.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.ebaic.mobile.api.ElicMyBusiService;
@Service(value="elicMyBusiServiceImpl")
public class ElicMyBusiServiceImpl implements ElicMyBusiService {

	public List<Map<String, Object>> getList(String userId, String entName,
			String oprType, String state, int pageSize, int pageIdx) {
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

	public List<Map<String, Object>> getIdentityState(String gid) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, Object> getReqInfo(String gid) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Map<String, Object>> getConfirmState(String gid) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Map<String, Object>> getApproveMsgs(String gid) {
		// TODO Auto-generated method stub
		return null;
	}

}
