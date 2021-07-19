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
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�GSGeneralClient ��������webservice�ͻ��˷��� �����ˣ�lizheng
 * ����ʱ�䣺Apr 2, 2013 3:47:21 PM �޸��ˣ�lizheng �޸�ʱ�䣺Apr 2, 2013 3:47:21 PM �޸ı�ע��
 * 
 * @version
 * 
 */
public class SocketGSGeneralClient
{
	// ��־
	protected static Logger	logger	= TxnLogger.getLogger(SocketGSGeneralClient.class
											.getName());

	/**
	 * 
	 * colGetCrownRentDom �ɼ���˰����
	 * 
	 * @param param
	 * @return String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public String colGetDom(Map param, CollectLogVo collectLogVo)
	{
		try {
			SocketGeneralClient client = SocketClientFactory.getClient();
			String result = client.colGetDom(param, collectLogVo);
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
