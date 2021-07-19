package com.gwssi.dw.runmgr.webservices.localtax.servlet.zr;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;


public class Test
{
	public static void main(String[] args) throws ServiceException, RemoteException
	{
			ServiceSoap_PortType service = new ServiceLocator().getServiceSoap();
			ReturnMultiGSData data = service.getLJ_Query();
			System.out.println("连接测试返回代码："+data.getFHDM());
			System.out.println("**************************************************\r\n");
			data = service.getGSDJ_INFO("北京太平洋世纪商务中心","1101021063558");
			System.out.println("返回代码："+data.getFHDM());
			System.out.println("查询到的记录数："+data.getZTS());
			System.out.println("开始记录数："+data.getKSJLS());
			System.out.println("结束记录数："+data.getJSJLS());
			System.out.println(data.getGSDJ_INFO_ARRAY()[0].getQYMC());
	}
}
