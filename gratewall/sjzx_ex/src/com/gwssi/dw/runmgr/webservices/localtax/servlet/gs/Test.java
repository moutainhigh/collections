package com.gwssi.dw.runmgr.webservices.localtax.servlet.gs;

import java.net.URLEncoder;

public class Test
{
	public static void main(String[] args) throws Exception
	{
		ServiceSoap_PortType service = new ServiceLocator().getServiceSoap();
		ReturnMultiGSData data = service.getLJ_Query();
		System.out.println("连接测试返回代码："+data.getFHDM());
		System.out.println("**************************************************\r\n");
		
		//测试查询企业登记信息（单条记录）
		//参数1：企业名称
		//参数2：登记注册号
		data = service.getGSDJ_INFO("北京子江吉力便利商店", "110102602637364");
		System.out.println("返回代码："+data.getFHDM());
		System.out.println("查询到的记录数："+data.getZTS());
		System.out.println("开始记录数："+data.getKSJLS());
		System.out.println("结束记录数："+data.getJSJLS());
		if(Integer.parseInt(data.getZTS()) > 0){
			GSDJInfo[] info = data.getGSDJ_INFO_ARRAY();
			System.out.println("企业名称："+info[0].getQYMC());
			System.out.println("注册号："+info[0].getYYZZH());
		}
		System.out.println("\r\n**************************************************\r\n");
		
		data = service.getGSDJ_QUERY("20080220", "20080221", "2", "5");
		System.out.println("返回代码："+data.getFHDM());
		System.out.println("查询到的记录数："+data.getZTS());
		System.out.println("开始记录数："+data.getKSJLS());
		System.out.println("结束记录数："+data.getJSJLS());
		for(int i = 0;i<data.getGSDJ_INFO_ARRAY().length ;i++){
			GSDJInfo[] info = data.getGSDJ_INFO_ARRAY();
			System.out.println("注册号："+info[i].getYYZZH()+" 企业名称："+info[i].getQYMC());
		}
	}
}
