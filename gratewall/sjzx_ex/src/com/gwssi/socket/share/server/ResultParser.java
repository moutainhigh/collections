package com.gwssi.socket.share.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gwssi.common.constant.ShareConstants;
import com.gwssi.common.util.XmlToMapUtil;
import com.gwssi.log.sharelog.dao.ShareLogVo;

/**
 * 
 * 
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�ResultParser �����������񷵻�ֵ��װ�� �����ˣ�lizheng ����ʱ�䣺Apr 1, 2013
 * 11:18:27 AM �޸��ˣ�lizheng �޸�ʱ�䣺Apr 1, 2013 11:18:27 AM �޸ı�ע��
 * 
 * @version
 * 
 */
public class ResultParser
{

	/**
	 * 
	 * createResultMap ���÷���ɹ����ؽ��
	 * 
	 * @param resultMap
	 * @param params
	 * @param total
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static Map createResultMap(List resultMap, Map params,
			String total, ShareLogVo shareLogVo)
	{
		Map result = new HashMap();
		if (resultMap.size() < 1) {
			// ��ѯ�ɹ����������Ϊ0
			result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
					ShareConstants.SERVICE_FHDM_NO_RESULT);
			shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_NO_RESULT);
			shareLogVo.setRecord_amount(String.valueOf(resultMap.size())); // ���η��ʵ�������
		} else {
			result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
					ShareConstants.SERVICE_FHDM_SUCCESS);
			shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_SUCCESS);
			shareLogVo.setRecord_amount(String.valueOf(resultMap.size()));// ���η��ʵ�������
		}
		result.put(ShareConstants.SERVICE_OUT_PARAM_KSJLS, params
				.get(ShareConstants.SERVICE_OUT_PARAM_KSJLS));// ��ʼ��¼��
		result.put(ShareConstants.SERVICE_OUT_PARAM_JSJLS, params
				.get(ShareConstants.SERVICE_OUT_PARAM_JSJLS));// ������¼��
		result.put(ShareConstants.SERVICE_OUT_PARAM_ZTS, total);// ������
		Map[] maps = new HashMap[resultMap.size()];
		for (int i = 0; i < resultMap.size(); i++) {
			Map map = (Map) resultMap.get(i);
			if (map.containsKey(ShareConstants.SERVICE_OUT_RN))
				map.remove(ShareConstants.SERVICE_OUT_RN);// ��RNȥ��
			if (map.get(ShareConstants.SERVICE_OUT_ETL_TIMESTAMP) != null) {
				map.put(ShareConstants.SERVICE_OUT_ETL_TIMESTAMP, map.get(
						ShareConstants.SERVICE_OUT_ETL_TIMESTAMP).toString());
			}
			maps[i] = map;
		}
		result.put(ShareConstants.SERVICE_OUT_PARAM_ARRAY, maps);
		return result;
	}

	/**
	 * 
	 * createResultXml���÷���ɹ����ؽ��
	 * 
	 * @param resultMap
	 * @param params
	 * @param total
	 * @return String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static String createResultXml(List resultMap,
			Map params, String total, ShareLogVo shareLogVo)
	{
		Map result = new HashMap();
		if (resultMap.size() < 1) {
			result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
					ShareConstants.SERVICE_FHDM_NO_RESULT);// ��ѯ�ɹ����������Ϊ0
			shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_NO_RESULT);
			shareLogVo.setRecord_amount(String.valueOf(resultMap.size())); // ���η��ʵ�������
		} else {
			result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
					ShareConstants.SERVICE_FHDM_SUCCESS);
			shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_SUCCESS);
			shareLogVo.setRecord_amount(String.valueOf(resultMap.size()));// ���η��ʵ�������
		}
		result.put(ShareConstants.SERVICE_OUT_PARAM_KSJLS, params
				.get(ShareConstants.SERVICE_OUT_PARAM_KSJLS));// ��ʼ��¼��
		result.put(ShareConstants.SERVICE_OUT_PARAM_JSJLS, params
				.get(ShareConstants.SERVICE_OUT_PARAM_JSJLS));// ������¼��
		result.put(ShareConstants.SERVICE_OUT_PARAM_ZTS, total);// ������
		Map[] maps = new HashMap[resultMap.size()];
		for (int i = 0; i < resultMap.size(); i++) {
			Map map = (Map) resultMap.get(i);
			if (map.containsKey(ShareConstants.SERVICE_OUT_RN))
				map.remove(ShareConstants.SERVICE_OUT_RN); // ��RNȥ��
			if (map.get(ShareConstants.SERVICE_OUT_ETL_TIMESTAMP) != null) {
				map.put(ShareConstants.SERVICE_OUT_ETL_TIMESTAMP, map.get(
						ShareConstants.SERVICE_OUT_ETL_TIMESTAMP).toString());
			}
			maps[i] = map;
		}
		result.put(ShareConstants.SERVICE_OUT_PARAM_ARRAY, maps);
		return XmlToMapUtil.map2Dom(result);
	}

	/**
	 * 
	 * createSuccuseMap �����ܱ�����
	 * 
	 * @param param
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static Map createSuccuseMap()
	{
		Map result = new HashMap();
		result.put(ShareConstants.SERVICE_CAN_BE_USED, "Y");
		return result;
	}

	/**
	 * 
	 * createUserErrorlMap �û������ʧ��
	 * 
	 * @param param
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static Map createUserErrorlMap()
	{
		Map result = new HashMap();
		result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
				ShareConstants.SERVICE_FHDM_USER_ERROR);
		result.put(ShareConstants.SERVICE_CAN_BE_USED, "N");
		return result;
	}

	/**
	 * 
	 * createPwdErrorlMap ������ʧ��
	 * 
	 * @param param
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static Map createPwdErrorlMap()
	{
		Map result = new HashMap();
		result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
				ShareConstants.SERVICE_FHDM_PWD_ERROR);
		result.put(ShareConstants.SERVICE_CAN_BE_USED, "N");
		return result;
	}

	/**
	 * 
	 * createLoginFailMap ��¼���ʧ��
	 * 
	 * @param params
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static Map createLoginFailMap()
	{
		Map result = new HashMap();
		result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
				ShareConstants.SERVICE_FHDM_LOGIN_FAIL);
		result.put(ShareConstants.SERVICE_CAN_BE_USED, "N");
		return result;
	}

	/**
	 * 
	 * createUserFailMap �û���Ч
	 * 
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static Map createUserFailMap()
	{
		Map result = new HashMap();
		result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
				ShareConstants.SERVICE_FHDM_USER_FAIL);
		result.put(ShareConstants.SERVICE_CAN_BE_USED, "N");
		return result;
	}

	/**
	 * 
	 * createServiceIPFailMap ��IP���ʧ��
	 * 
	 * @param param
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static Map createServiceIPFailMap()
	{
		Map result = new HashMap();
		result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
				ShareConstants.SERVICE_FHDM_ERROR_IP);
		result.put(ShareConstants.SERVICE_CAN_BE_USED, "N");
		return result;
	}

	/**
	 * 
	 * createSvrNotFoundMap δ�ҵ�����
	 * 
	 * @param param
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static Map createSvrNotFoundMap()
	{
		Map result = new HashMap();
		result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
				ShareConstants.SERVICE_FHDM_SERVICE_NOT_FOUND);
		result.put(ShareConstants.SERVICE_CAN_BE_USED, "N");
		return result;
	}

	/**
	 * 
	 * createServiceStateFailMap ����״̬����
	 * 
	 * @param param
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static Map createServiceStateFailMap()
	{
		Map result = new HashMap();
		result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
				ShareConstants.SERVICE_FHDM_SERVICE_PAUSE);
		result.put(ShareConstants.SERVICE_CAN_BE_USED, "N");
		return result;
	}

	/**
	 * 
	 * createServiceDateFailMap ����ʱ�����
	 * 
	 * @param param
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static Map createServiceDateFailMap()
	{
		Map result = new HashMap();
		result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
				ShareConstants.SERVICE_FHDM_LOCK_TIME);
		result.put(ShareConstants.SERVICE_CAN_BE_USED, "N");
		return result;
	}

	/**
	 * 
	 * createSvrTimeErrorMap ����ʱ��������
	 * 
	 * @param params
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static Map createSvrTimeErrorMap()
	{
		Map result = new HashMap();
		result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
				ShareConstants.SERVICE_FHDM_LOCK_TIME);
		result.put(ShareConstants.SERVICE_CAN_BE_USED, "N");
		return result;
	}

	/**
	 * 
	 * createSvrNumErrorMap �����ܴ���������
	 * 
	 * @param params
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static Map createSvrNumErrorMap()
	{
		Map result = new HashMap();
		result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
				ShareConstants.SERVICE_FHDM_LOCK_NUMBER);
		result.put(ShareConstants.SERVICE_CAN_BE_USED, "N");
		return result;
	}

	/**
	 * 
	 * createSvrTotalErrorMap ����������������
	 * 
	 * @param params
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static Map createSvrTotalErrorMap()
	{
		Map result = new HashMap();
		result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
				ShareConstants.SERVICE_FHDM_LOCK_TOTAL);
		result.put(ShareConstants.SERVICE_CAN_BE_USED, "N");
		return result;
	}

	/**
	 * 
	 * createSystemErrorMap ϵͳ����
	 * 
	 * @param params
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static Map createSystemErrorMap()
	{
		Map result = new HashMap();
		result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
				ShareConstants.SERVICE_FHDM_SYSTEM_ERROR);
		result.put(ShareConstants.SERVICE_CAN_BE_USED, "N");
		return result;
	}

	/**
	 * 
	 * createSqlErrorMap SQL��ѯ����
	 * 
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static Map createSqlErrorMap()
	{
		Map result = new HashMap();
		result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
				ShareConstants.SERVICE_FHDM_SQL_ERROR);
		return result;
	}

	public synchronized static Map createParamErrorMap()
	{
		Map result = new HashMap();
		// result.put("FHDM", code);
		// result.put("KSJLS",
		// params.get(ShareConstants.SERVICE_OUT_PARAM_KSJLS));
		// result.put("JSJLS",
		// params.get(ShareConstants.SERVICE_OUT_PARAM_JSJLS));
		// result.put("ZTS", "0");
		// result.put("GSDJ_INFO_ARRAY", null);
		result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
				ShareConstants.SERVICE_FHDM_INPUT_PARAM_ERROR);
		return result;
	}

	public synchronized static Map createTimeNotInMonthErrorMap()
	{
		Map result = new HashMap();
		result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
				ShareConstants.SERVICE_FHDM_OVER_MONTH);
		return result;
	}

	public synchronized static Map createOverMaxMap(Map params)
	{
		Map result = new HashMap();
		result.put("FHDM", ShareConstants.SERVICE_FHDM_OVER_MAX);
		result.put("KSJLS", params.get(ShareConstants.SERVICE_OUT_PARAM_KSJLS));
		result.put("JSJLS", params.get(ShareConstants.SERVICE_OUT_PARAM_JSJLS));
		result.put("ZTS", "0");
		result.put("GSDJ_INFO_ARRAY", null);

		return result;
	}

	public synchronized static Map createUnknownFailMap(Map params)
	{
		Map result = new HashMap();
		result.put("FHDM", ShareConstants.SERVICE_FHDM_UNKNOWN_ERROR);
		result.put("KSJLS", params.get(ShareConstants.SERVICE_OUT_PARAM_KSJLS));
		result.put("JSJLS", params.get(ShareConstants.SERVICE_OUT_PARAM_JSJLS));
		result.put("ZTS", "0");
		result.put("GSDJ_INFO_ARRAY", null);

		return result;
	}
}
