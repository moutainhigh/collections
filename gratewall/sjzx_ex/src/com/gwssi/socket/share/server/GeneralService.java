package com.gwssi.socket.share.server;

import java.util.Map;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.log.sharelog.dao.ShareLogVo;

/**
 * 
 * 
 * 项目名称：bjgs_exchange 类名称：GeneralService 类描述：服务接口 创建人：lizheng 创建时间：Mar 28, 2013
 * 10:10:38 AM 修改人：lizheng 修改时间：Mar 28, 2013 10:10:38 AM 修改备注：
 * 
 * @version
 * 
 */
public interface GeneralService
{
	

	/**
	 * 
	 * queryData 返回值dom的方法
	 * 
	 * @param params
	 * @return
	 * @throws DBException
	 *             String
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	String queryData(Map params, ShareLogVo shareLogVo) throws DBException;
}
