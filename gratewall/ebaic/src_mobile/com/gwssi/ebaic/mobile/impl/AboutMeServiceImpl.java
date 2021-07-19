package com.gwssi.ebaic.mobile.impl;

import org.springframework.stereotype.Service;

import com.gwssi.ebaic.mobile.api.AboutMeService;
import com.gwssi.ebaic.mobile.domain.UserBo;

@Service(value="aboutMeServiceImpl")
public class AboutMeServiceImpl implements AboutMeService{

	public UserBo get(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void save(UserBo user) {
		// TODO Auto-generated method stub
		
	}

	public void modifyPassword(String userId, String oriPassword, String newPassword) {
		// TODO Auto-generated method stub
		
	}

	public String getManual() {
		// TODO Auto-generated method stub
		return null;
	}

}
