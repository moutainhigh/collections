package com.gwssi.dw.runmgr.webservices.localtax.servlet.zr;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;


public class Test
{
	public static void main(String[] args) throws ServiceException, RemoteException
	{
			ServiceSoap_PortType service = new ServiceLocator().getServiceSoap();
			ReturnMultiGSData data = service.getLJ_Query();
			System.out.println("���Ӳ��Է��ش��룺"+data.getFHDM());
			System.out.println("**************************************************\r\n");
			data = service.getGSDJ_INFO("����̫ƽ��������������","1101021063558");
			System.out.println("���ش��룺"+data.getFHDM());
			System.out.println("��ѯ���ļ�¼����"+data.getZTS());
			System.out.println("��ʼ��¼����"+data.getKSJLS());
			System.out.println("������¼����"+data.getJSJLS());
			System.out.println(data.getGSDJ_INFO_ARRAY()[0].getQYMC());
	}
}
