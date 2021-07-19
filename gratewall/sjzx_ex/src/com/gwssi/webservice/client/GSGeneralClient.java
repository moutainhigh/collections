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
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�GSGeneralClient ��������webservice�ͻ��˷��� �����ˣ�lizheng
 * ����ʱ�䣺Apr 2, 2013 3:47:21 PM �޸��ˣ�lizheng �޸�ʱ�䣺Apr 2, 2013 3:47:21 PM �޸ı�ע��
 * 
 * @version
 * 
 */
public class GSGeneralClient
{
	// ��־
	protected static Logger	logger	= TxnLogger.getLogger(GSGeneralClient.class
											.getName());

	/**
	 * 
	 * collectData ���չ����ṩ�ı�׼��ʽ�ɼ�����
	 * 
	 * @param param
	 * @param collectLogVo
	 * @param countMap
	 * @return String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public String collectData(Map param, CollectLogVo collectLogVo, Map countMap)
	{
		try {
			GeneralClient client = ClientFactory.getClient();
			String result = client.collectData(param, collectLogVo, countMap);
			return result;
		} catch (DBException e) {
			logger.debug("���ݿ����ʧ��...");
			collectLogVo
					.setReturn_codes(CollectConstants.CLIENT_FHDM_SQL_ERROR);
			e.printStackTrace();
			return XmlToMapUtil.map2Dom(ResultFormat.createDbError());
		}
	}

	/**
	 * 
	 * collectQualitySupervisionData �ɼ��ʼ������
	 * 
	 * @param param
	 * @param collectLogVo
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
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
			logger.debug("���ݿ����ʧ��...");
			collectLogVo
					.setReturn_codes(CollectConstants.CLIENT_FHDM_SQL_ERROR);
			e.printStackTrace();
			return ResultFormat.createDbError();
		}
	}

	/**
	 * 
	 * collectSocialSecurityData �ɼ������籣����
	 * 
	 * @param param
	 * @param collectLogVo
	 * @return String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public String collectSocialSecurityData(Map param, CollectLogVo collectLogVo)
	{
		try {
			GeneralClient client = ClientFactory.getClient();
			String result = client.collectSocialSecurityData(param,
					collectLogVo);
			return result;
		} catch (DBException e) {
			logger.debug("���ݿ����ʧ��...");
			collectLogVo
					.setReturn_codes(CollectConstants.CLIENT_FHDM_SQL_ERROR);
			e.printStackTrace();
			return XmlToMapUtil.map2Dom(ResultFormat.createDbError());
		}
	}

}
