package com.gwssi.socket.share.server;

import com.genersoft.frame.base.database.DBException;

/**
 * 
 * 
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�ServiceFactory �����������񹤳� �����ˣ�lizheng ����ʱ�䣺Mar 28, 2013
 * 10:08:15 AM �޸��ˣ�lizheng �޸�ʱ�䣺Mar 28, 2013 10:08:15 AM �޸ı�ע��
 * 
 * @version
 * 
 */
public class ServiceFactory
{
	public static GeneralService getService() throws DBException
	{
		// ִ�з����ѯ�����������װ�����
		return new ExcuteService();
	}
}
