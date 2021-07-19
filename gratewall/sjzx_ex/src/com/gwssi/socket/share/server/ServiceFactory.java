package com.gwssi.socket.share.server;

import com.genersoft.frame.base.database.DBException;

/**
 * 
 * 
 * 项目名称：bjgs_exchange 类名称：ServiceFactory 类描述：服务工厂 创建人：lizheng 创建时间：Mar 28, 2013
 * 10:08:15 AM 修改人：lizheng 修改时间：Mar 28, 2013 10:08:15 AM 修改备注：
 * 
 * @version
 * 
 */
public class ServiceFactory
{
	public static GeneralService getService() throws DBException
	{
		// 执行服务查询结果集，并封装结果集
		return new ExcuteService();
	}
}
