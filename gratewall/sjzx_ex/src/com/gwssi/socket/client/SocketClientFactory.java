package com.gwssi.socket.client;

import com.genersoft.frame.base.database.DBException;

/**
 * 
 *     
 * 项目名称：bjgs_exchange    
 * 类名称：ClientFactory    
 * 类描述：客户端工厂    
 * 创建人：lizheng    
 * 创建时间：Apr 2, 2013 3:53:01 PM    
 * 修改人：lizheng    
 * 修改时间：Apr 2, 2013 3:53:01 PM    
 * 修改备注：    
 * @version     
 *
 */
public class SocketClientFactory
{
	public static SocketGeneralClient getClient() throws DBException
	{
		//调用客户端
		return new SocketExcuteClient();
	}
}
