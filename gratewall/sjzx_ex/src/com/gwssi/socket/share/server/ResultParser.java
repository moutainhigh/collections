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
 * 项目名称：bjgs_exchange 类名称：ResultParser 类描述：服务返回值封装类 创建人：lizheng 创建时间：Apr 1, 2013
 * 11:18:27 AM 修改人：lizheng 修改时间：Apr 1, 2013 11:18:27 AM 修改备注：
 * 
 * @version
 * 
 */
public class ResultParser
{

	/**
	 * 
	 * createResultMap 调用服务成功返回结果
	 * 
	 * @param resultMap
	 * @param params
	 * @param total
	 * @return Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public synchronized static Map createResultMap(List resultMap, Map params,
			String total, ShareLogVo shareLogVo)
	{
		Map result = new HashMap();
		if (resultMap.size() < 1) {
			// 查询成功但结果条数为0
			result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
					ShareConstants.SERVICE_FHDM_NO_RESULT);
			shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_NO_RESULT);
			shareLogVo.setRecord_amount(String.valueOf(resultMap.size())); // 本次访问的数据量
		} else {
			result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
					ShareConstants.SERVICE_FHDM_SUCCESS);
			shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_SUCCESS);
			shareLogVo.setRecord_amount(String.valueOf(resultMap.size()));// 本次访问的数据量
		}
		result.put(ShareConstants.SERVICE_OUT_PARAM_KSJLS, params
				.get(ShareConstants.SERVICE_OUT_PARAM_KSJLS));// 开始记录数
		result.put(ShareConstants.SERVICE_OUT_PARAM_JSJLS, params
				.get(ShareConstants.SERVICE_OUT_PARAM_JSJLS));// 结束记录数
		result.put(ShareConstants.SERVICE_OUT_PARAM_ZTS, total);// 总条数
		Map[] maps = new HashMap[resultMap.size()];
		for (int i = 0; i < resultMap.size(); i++) {
			Map map = (Map) resultMap.get(i);
			if (map.containsKey(ShareConstants.SERVICE_OUT_RN))
				map.remove(ShareConstants.SERVICE_OUT_RN);// 将RN去掉
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
	 * createResultXml调用服务成功返回结果
	 * 
	 * @param resultMap
	 * @param params
	 * @param total
	 * @return String
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public synchronized static String createResultXml(List resultMap,
			Map params, String total, ShareLogVo shareLogVo)
	{
		Map result = new HashMap();
		if (resultMap.size() < 1) {
			result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
					ShareConstants.SERVICE_FHDM_NO_RESULT);// 查询成功但结果条数为0
			shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_NO_RESULT);
			shareLogVo.setRecord_amount(String.valueOf(resultMap.size())); // 本次访问的数据量
		} else {
			result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
					ShareConstants.SERVICE_FHDM_SUCCESS);
			shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_SUCCESS);
			shareLogVo.setRecord_amount(String.valueOf(resultMap.size()));// 本次访问的数据量
		}
		result.put(ShareConstants.SERVICE_OUT_PARAM_KSJLS, params
				.get(ShareConstants.SERVICE_OUT_PARAM_KSJLS));// 开始记录数
		result.put(ShareConstants.SERVICE_OUT_PARAM_JSJLS, params
				.get(ShareConstants.SERVICE_OUT_PARAM_JSJLS));// 结束记录数
		result.put(ShareConstants.SERVICE_OUT_PARAM_ZTS, total);// 总条数
		Map[] maps = new HashMap[resultMap.size()];
		for (int i = 0; i < resultMap.size(); i++) {
			Map map = (Map) resultMap.get(i);
			if (map.containsKey(ShareConstants.SERVICE_OUT_RN))
				map.remove(ShareConstants.SERVICE_OUT_RN); // 将RN去掉
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
	 * createSuccuseMap 服务能被调用
	 * 
	 * @param param
	 * @return Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public synchronized static Map createSuccuseMap()
	{
		Map result = new HashMap();
		result.put(ShareConstants.SERVICE_CAN_BE_USED, "Y");
		return result;
	}

	/**
	 * 
	 * createUserErrorlMap 用户名检查失败
	 * 
	 * @param param
	 * @return Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
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
	 * createPwdErrorlMap 密码检查失败
	 * 
	 * @param param
	 * @return Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
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
	 * createLoginFailMap 登录检查失败
	 * 
	 * @param params
	 * @return Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
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
	 * createUserFailMap 用户无效
	 * 
	 * @return Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
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
	 * createServiceIPFailMap 绑定IP检查失败
	 * 
	 * @param param
	 * @return Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
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
	 * createSvrNotFoundMap 未找到服务
	 * 
	 * @param param
	 * @return Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
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
	 * createServiceStateFailMap 服务状态错误
	 * 
	 * @param param
	 * @return Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
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
	 * createServiceDateFailMap 例外时间错误
	 * 
	 * @param param
	 * @return Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
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
	 * createSvrTimeErrorMap 访问时限受限制
	 * 
	 * @param params
	 * @return Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
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
	 * createSvrNumErrorMap 访问总次数受限制
	 * 
	 * @param params
	 * @return Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
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
	 * createSvrTotalErrorMap 访问总条数受限制
	 * 
	 * @param params
	 * @return Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
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
	 * createSystemErrorMap 系统错误
	 * 
	 * @param params
	 * @return Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
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
	 * createSqlErrorMap SQL查询错误
	 * 
	 * @return Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
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
