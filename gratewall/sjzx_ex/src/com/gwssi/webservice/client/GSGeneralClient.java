package com.gwssi.webservice.client;

import java.util.Map;

import org.apache.log4j.Logger;

import cn.gwssi.common.component.logger.TxnLogger;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.util.XmlToMapUtil;
import com.gwssi.log.collectlog.dao.CollectLogVo;

/**
 * 
 * 
 * 项目名称：bjgs_exchange 类名称：GSGeneralClient 类描述：webservice客户端方法 创建人：lizheng
 * 创建时间：Apr 2, 2013 3:47:21 PM 修改人：lizheng 修改时间：Apr 2, 2013 3:47:21 PM 修改备注：
 * 
 * @version
 * 
 */
public class GSGeneralClient
{
	// 日志
	protected static Logger	logger	= TxnLogger.getLogger(GSGeneralClient.class
											.getName());

	/**
	 * 
	 * collectData 按照工商提供的标准方式采集数据
	 * 
	 * @param param
	 * @param collectLogVo
	 * @param countMap
	 * @return String
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public String collectData(Map param, CollectLogVo collectLogVo, Map countMap)
	{
		try {
			GeneralClient client = ClientFactory.getClient();
			String result = client.collectData(param, collectLogVo, countMap);
			return result;
		} catch (DBException e) {
			logger.debug("数据库操作失败...");
			collectLogVo
					.setReturn_codes(CollectConstants.CLIENT_FHDM_SQL_ERROR);
			e.printStackTrace();
			return XmlToMapUtil.map2Dom(ResultFormat.createDbError());
		}
	}

	/**
	 * 
	 * collectQualitySupervisionData 采集质监的数据
	 * 
	 * @param param
	 * @param collectLogVo
	 * @return Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public Map collectQualitySupervisionData(Map param,
			CollectLogVo collectLogVo)
	{
		try {
			GeneralClient client = ClientFactory.getClient();
			Map result = client.collectQualitySupervisionData(param,
					collectLogVo);
			return result;
		} catch (DBException e) {
			logger.debug("数据库操作失败...");
			collectLogVo
					.setReturn_codes(CollectConstants.CLIENT_FHDM_SQL_ERROR);
			e.printStackTrace();
			return ResultFormat.createDbError();
		}
	}

	/**
	 * 
	 * collectSocialSecurityData 采集人力社保数据
	 * 
	 * @param param
	 * @param collectLogVo
	 * @return String
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public String collectSocialSecurityData(Map param, CollectLogVo collectLogVo)
	{
		try {
			GeneralClient client = ClientFactory.getClient();
			String result = client.collectSocialSecurityData(param,
					collectLogVo);
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
