package com.gwssi.socket.client;

import com.genersoft.frame.base.database.DBException;

/**
 * 
 *     
 * ��Ŀ���ƣ�bjgs_exchange    
 * �����ƣ�ClientFactory    
 * ���������ͻ��˹���    
 * �����ˣ�lizheng    
 * ����ʱ�䣺Apr 2, 2013 3:53:01 PM    
 * �޸��ˣ�lizheng    
 * �޸�ʱ�䣺Apr 2, 2013 3:53:01 PM    
 * �޸ı�ע��    
 * @version     
 *
 */
public class SocketClientFactory
{
	public static SocketGeneralClient getClient() throws DBException
	{
		//���ÿͻ���
		return new SocketExcuteClient();
	}
}
