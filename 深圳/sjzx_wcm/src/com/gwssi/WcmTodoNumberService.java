package com.gwssi;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.gwssi.Service.PortalHomeService;
import com.gwssi.WebService.Service.WcmTodo;

public class WcmTodoNumberService {
	private static Logger logger = Logger.getLogger(WcmTodoNumberService.class);
	
	public String  getTodoNumber(String userid){
		String str=null;
		//userid = "LINQY1@SZAIC";
		WcmTodo todo = new WcmTodo();
		try {
			logger.info("传入用户名：》》"+userid);
			 str = todo.getWaitNoByUserid(StringUtils.upperCase(userid));
			 logger.info("传入用户名："+userid+"返回参数：》》"+str);
			 todo=null;
		} catch (Exception e) {
			logger.info("返回参数出错:"+e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str ;
	}
	
	public static void main(String []args){
		WcmTodoNumberService a =new WcmTodoNumberService();
		a.getTodoNumber("LINQY1@SZAIC");
		
	}
}
