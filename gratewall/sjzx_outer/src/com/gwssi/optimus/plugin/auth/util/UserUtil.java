package com.gwssi.optimus.plugin.auth.util;

import java.util.ArrayList;
import java.util.List;

import com.gwssi.application.model.SmSysIntegrationBO;
import com.gwssi.optimus.plugin.auth.model.User;

public class UserUtil {
	
	/**
	 * 获取用户所具有的所有系统的主键
	 * @param user
	 * @return 
	 */
	public static List<String> getAllPksysList(User user){
		List<String> allpksyslist=new ArrayList<String>();
		List<SmSysIntegrationBO>  list=user.getUserSysList();
		if(list!=null &&list.size()>0){
			for(SmSysIntegrationBO bo:list){
				String s=bo.getPkSysIntegration();
				allpksyslist.add(s);
			}
		}
		return allpksyslist;
	}
	

}
