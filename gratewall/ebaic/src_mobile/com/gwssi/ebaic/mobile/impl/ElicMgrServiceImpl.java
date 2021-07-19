package com.gwssi.ebaic.mobile.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.ebaic.mobile.api.ElicMgrService;
@Service(value="elicMgrServiceImpl")
public class ElicMgrServiceImpl implements ElicMgrService {

	public List<Map<String, String>> getManagerList(String accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, String> editManager(String managerId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveManager(String managerId, String mobileVerCode,
			String name, String cerNo, String mobile, String role,
			String accState, String operation) {
		// TODO Auto-generated method stub

	}

	public void addManager(String accountId, String mobileVerCode, String name,
			String cerNo, String mobile, String role, String accState,
			String operation) {
		// TODO Auto-generated method stub

	}

	public void deleteManager(String managerId) {
		// TODO Auto-generated method stub

	}

}
