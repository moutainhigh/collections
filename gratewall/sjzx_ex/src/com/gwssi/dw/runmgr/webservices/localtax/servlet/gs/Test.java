package com.gwssi.dw.runmgr.webservices.localtax.servlet.gs;

import java.net.URLEncoder;

public class Test
{
	public static void main(String[] args) throws Exception
	{
		ServiceSoap_PortType service = new ServiceLocator().getServiceSoap();
		ReturnMultiGSData data = service.getLJ_Query();
		System.out.println("���Ӳ��Է��ش��룺"+data.getFHDM());
		System.out.println("**************************************************\r\n");
		
		//���Բ�ѯ��ҵ�Ǽ���Ϣ��������¼��
		//����1����ҵ����
		//����2���Ǽ�ע���
		data = service.getGSDJ_INFO("�����ӽ����������̵�", "110102602637364");
		System.out.println("���ش��룺"+data.getFHDM());
		System.out.println("��ѯ���ļ�¼����"+data.getZTS());
		System.out.println("��ʼ��¼����"+data.getKSJLS());
		System.out.println("������¼����"+data.getJSJLS());
		if(Integer.parseInt(data.getZTS()) > 0){
			GSDJInfo[] info = data.getGSDJ_INFO_ARRAY();
			System.out.println("��ҵ���ƣ�"+info[0].getQYMC());
			System.out.println("ע��ţ�"+info[0].getYYZZH());
		}
		System.out.println("\r\n**************************************************\r\n");
		
		data = service.getGSDJ_QUERY("20080220", "20080221", "2", "5");
		System.out.println("���ش��룺"+data.getFHDM());
		System.out.println("��ѯ���ļ�¼����"+data.getZTS());
		System.out.println("��ʼ��¼����"+data.getKSJLS());
		System.out.println("������¼����"+data.getJSJLS());
		for(int i = 0;i<data.getGSDJ_INFO_ARRAY().length ;i++){
			GSDJInfo[] info = data.getGSDJ_INFO_ARRAY();
			System.out.println("ע��ţ�"+info[i].getYYZZH()+" ��ҵ���ƣ�"+info[i].getQYMC());
		}
	}
}
