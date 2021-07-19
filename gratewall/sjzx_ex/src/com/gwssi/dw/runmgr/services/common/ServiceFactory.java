package com.gwssi.dw.runmgr.services.common;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.dw.runmgr.services.CommonService;
import com.gwssi.dw.runmgr.services.GeneralService;
import com.gwssi.dw.runmgr.services.impl.TestShareService;

public class ServiceFactory
{
	public static GeneralService getService(String svrType) throws DBException{
		if(svrType.trim().equalsIgnoreCase(Constants.SERVICE_TYPE_GENERAL)){
			return new CommonService();
		}else if(svrType.trim().equalsIgnoreCase(Constants.SERVICE_TYPE_SELF)){
			return new TestShareService();//new SelfService();
		}else{
			return null;
		}
	}
}
