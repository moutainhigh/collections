package com.gwssi.webservice.client;

import java.util.HashMap;
import java.util.Map;

import com.gwssi.common.constant.CollectConstants;

/**
 * 
 * 
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�ResultFormat ���������ͻ��˷��ؽ����װ �����ˣ�lizheng ����ʱ�䣺Apr 8,
 * 2013 10:04:09 AM �޸��ˣ�lizheng �޸�ʱ�䣺Apr 8, 2013 10:04:09 AM �޸ı�ע��
 * 
 * @version
 * 
 */
public class ResultFormat
{
	/**
	 * 
	 * createConnect���ӳɹ����ؽ��
	 * 
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static Map createConnect()
	{
		Map result = new HashMap();
		result.put(CollectConstants.CLIENT_STATE,
				CollectConstants.CLIENT_STATE_YES);
		return result;
	}

	/**
	 * 
	 * createConnectFailed ����ʧ�ܷ��ؽ��
	 * 
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static Map createConnectFailed()
	{
		Map result = new HashMap();
		result.put(CollectConstants.CLIENT_STATE,
				CollectConstants.CLIENT_STATE_NO);
		return result;
	}

	/**
	 * 
	 * createCollectSuccess �ɼ����ݳɹ�
	 * 
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static Map createCollectSuccess()
	{
		Map result = new HashMap();
		result.put(CollectConstants.CLIENT_COLLECT_PARAM_FHDM,
				CollectConstants.CLIENT_FHDM_SUCCESS);
		return result;
	}

	public synchronized static Map createSysError()
	{
		Map result = new HashMap();
		result.put(CollectConstants.CLIENT_COLLECT_PARAM_FHDM,
				CollectConstants.CLIENT_FHDM_SYS_ERROR);
		return result;
	}

	public synchronized static Map createDbError()
	{
		Map result = new HashMap();
		result.put(CollectConstants.CLIENT_COLLECT_PARAM_FHDM,
				CollectConstants.CLIENT_FHDM_SQL_ERROR);
		return result;
	}

	public synchronized static Map createColTblError()
	{
		Map result = new HashMap();
		result.put(CollectConstants.CLIENT_COLLECT_PARAM_FHDM,
				CollectConstants.CLIENT_FHDM_COL_TBL_ERROR);
		return result;
	}

	public synchronized static Map createDataitemError()
	{
		Map result = new HashMap();
		result.put(CollectConstants.CLIENT_COLLECT_PARAM_FHDM,
				CollectConstants.CLIENT_FHDM_DATAITEM_ERROR);
		return result;
	}

	public synchronized static Map createTaskError()
	{
		Map result = new HashMap();
		result.put(CollectConstants.CLIENT_COLLECT_PARAM_FHDM,
				CollectConstants.CLIENT_FHDM_TASK_ERROR);
		return result;
	}

	public synchronized static Map createWsError()
	{
		Map result = new HashMap();
		result.put(CollectConstants.CLIENT_COLLECT_PARAM_FHDM,
				CollectConstants.CLIENT_FHDM_WS_ERROR);
		return result;
	}

	public synchronized static Map createWsUrlError()
	{
		Map result = new HashMap();
		result.put(CollectConstants.CLIENT_COLLECT_PARAM_FHDM,
				CollectConstants.CLIENT_FHDM_WS_URL_ERROR);
		return result;
	}

	public synchronized static Map createInvokeError()
	{
		Map result = new HashMap();
		result.put(CollectConstants.CLIENT_COLLECT_PARAM_FHDM,
				CollectConstants.CLIENT_FHDM_INVOKE_ERROR);
		return result;
	}

	public synchronized static Map createTodomError()
	{
		Map result = new HashMap();
		result.put(CollectConstants.CLIENT_COLLECT_PARAM_FHDM,
				CollectConstants.CLIENT_FHDM_TODOM_ERROR);
		return result;
	}

}
