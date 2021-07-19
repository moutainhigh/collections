package com.gwssi.socket.client;

import java.util.Map;

import org.apache.log4j.Logger;

import cn.gwssi.common.component.logger.TxnLogger;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.util.XmlToMapUtil;
import com.gwssi.log.collectlog.dao.CollectLogVo;
import com.gwssi.webservice.client.ResultFormat;

/**
 * 
 * 
 * 项目名称：bjgs_exchange 类名称：GSGeneralClient 类描述：webservice客户端方法 创建人：lizheng
 * 创建时间：Apr 2, 2013 3:47:21 PM 修改人：lizheng 修改时间：Apr 2, 2013 3:47:21 PM 修改备注：
 * 
 * @version
 * 
 */
public class SocketGSGeneralClient
{
	// 日志
	protected static Logger	logger	= TxnLogger.getLogger(SocketGSGeneralClient.class
											.getName());

	/**
	 * 
	 * colGetCrownRentDom 采集地税数据
	 * 
	 * @param param
	 * @return String
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public String colGetDom(Map param, CollectLogVo collectLogVo)
	{
		try {
			SocketGeneralClient client = SocketClientFactory.getClient();
			String result = client.colGetDom(param, collectLogVo);
			return result;
		} catch (DBException e) {
			logger.debug("数据库操作失败...");
			collectLogVo
					.setReturn_codes(CollectConstants.CLIENT_FHDM_SQL_ERROR);
			e.printStackTrace();
			return XmlToMapUtil.map2Dom(ResultFormat.createDbError());
		}
	}

	
}
