package com.gwssi.webservice.server;

import java.io.BufferedReader;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;



import cn.gwssi.common.component.logger.TxnLogger;

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
	// 日志
	protected static Logger	logger	= TxnLogger.getLogger(ResultParser.class
											.getName());

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
	public static Map createResultMap(List resultMap, Map params, String total,
			ShareLogVo shareLogVo)
	{
		String recordAmount = "0";
		Map result = new HashMap();
		if (resultMap.size() < 1) {
			// 查询成功但结果条数为0
			result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
					ShareConstants.SERVICE_FHDM_NO_RESULT);
			shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_NO_RESULT);
			shareLogVo.setRecord_amount(String.valueOf(resultMap.size())); // 本次访问的数据量
			recordAmount = String.valueOf(resultMap.size());
		} else {
			result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
					ShareConstants.SERVICE_FHDM_SUCCESS);
			shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_SUCCESS);
			shareLogVo.setRecord_amount(String.valueOf(resultMap.size()));// 本次访问的数据量
			recordAmount = String.valueOf(resultMap.size());
		}
		result.put(ShareConstants.SERVICE_OUT_PARAM_KSJLS,
				params.get(ShareConstants.SERVICE_OUT_PARAM_KSJLS));// 开始记录数
		result.put(ShareConstants.SERVICE_OUT_PARAM_JSJLS,
				params.get(ShareConstants.SERVICE_OUT_PARAM_JSJLS));// 结束记录数
		result.put(ShareConstants.SERVICE_OUT_PARAM_ZTS, total);// 总条数

		if (params.containsKey("USER_TYPE")
				&& "TEST".equals(params.get("USER_TYPE"))) {
			Map[] maps = new HashMap[resultMap.size()];
			for (int i = 0; i < resultMap.size(); i++) {
				Map map = (Map) resultMap.get(i);
				if (map.containsKey(ShareConstants.SERVICE_OUT_RN))
					map.remove(ShareConstants.SERVICE_OUT_RN);// 将RN去掉
				if (map.get(ShareConstants.SERVICE_OUT_ETL_TIMESTAMP) != null) {
					map.put(ShareConstants.SERVICE_OUT_ETL_TIMESTAMP,
							map.get(ShareConstants.SERVICE_OUT_ETL_TIMESTAMP)
									.toString());
				}
				maps[i] = map;
			}
			result.put(ShareConstants.SERVICE_OUT_PARAM_ARRAY, maps);
			return result;
		} else {

			String serviceId = params.get("SERVICE_ID").toString();
			// 校验
			CheckService checkService = new CheckService();
			Map checkMap = checkService.checkRules(serviceId,
					Integer.parseInt(recordAmount));

			if ("Y".equals(checkMap.get(ShareConstants.SERVICE_CAN_BE_USED)
					.toString())) {
				Map[] maps = new HashMap[resultMap.size()];
				for (int i = 0; i < resultMap.size(); i++) {
					Map map = (Map) resultMap.get(i);
					if (map.containsKey(ShareConstants.SERVICE_OUT_RN))
						map.remove(ShareConstants.SERVICE_OUT_RN);// 将RN去掉
					if (map.get(ShareConstants.SERVICE_OUT_ETL_TIMESTAMP) != null) {
						map.put(ShareConstants.SERVICE_OUT_ETL_TIMESTAMP, map
								.get(ShareConstants.SERVICE_OUT_ETL_TIMESTAMP)
								.toString());
					}
					maps[i] = map;
				}
				result.put(ShareConstants.SERVICE_OUT_PARAM_ARRAY, maps);
				return result;
			} else {
				// 如果本次获取失败那么本次获取的数据条数为0
				shareLogVo.setRecord_amount("0");
				return checkMap;
			}
		}

	}

	/**
	 * 
	 * createResultMap 调用服务成功返回结果
	 * 
	 * @param resultMap
	 * @param params
	 * @param total
	 * @return Map
	 * @throws SQLException
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map createResultMapByRs(ResultSet rs, Map params,
			String total, ShareLogVo shareLogVo) throws SQLException
	{
		Map result = new HashMap();
		/** 固定返回值 **/
		result.put(ShareConstants.SERVICE_OUT_PARAM_KSJLS,
				params.get(ShareConstants.SERVICE_OUT_PARAM_KSJLS));// 开始记录数
		result.put(ShareConstants.SERVICE_OUT_PARAM_JSJLS,
				params.get(ShareConstants.SERVICE_OUT_PARAM_JSJLS));// 结束记录数
		result.put(ShareConstants.SERVICE_OUT_PARAM_ZTS, total);// 总条数

		/** 封装结果数据 **/
		//long s = System.currentTimeMillis();
		ResultSetMetaData md = rs.getMetaData(); // 获取查询的字段
		int columnCount = md.getColumnCount(); // 获取每行的总列数
		Map rowData;
		List list = new ArrayList();
		while (rs.next()) {
			rowData = new HashMap();
			for (int i = 1; i <= columnCount; i++) {
				//if (!md.getColumnName(i).equals(ShareConstants.SERVICE_OUT_RN)) {
					if (null != rs.getObject(i))
						rowData.put(md.getColumnName(i), rs.getObject(i));
					else
						rowData.put(md.getColumnName(i), "");
				//}

			}
			//去掉rn列
			rowData.remove(ShareConstants.SERVICE_OUT_RN);
			list.add(rowData);
		}
		//long e = System.currentTimeMillis();
		//logger.debug("组List 耗时："+(e-s)/1000f+"秒");
		/** 设置返回码 **/
		int count = list.size();
		
		// 将list转换成map数组
		Map[] maps = (HashMap[]) list.toArray(new HashMap[count]);
		
		if (count < 1) {
			// 查询成功但结果条数为0
			result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
					ShareConstants.SERVICE_FHDM_NO_RESULT);
			shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_NO_RESULT);
			shareLogVo.setRecord_amount(String.valueOf(count)); // 本次访问的数据量
			// recordAmount = String.valueOf(count);
		} else {
			result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
					ShareConstants.SERVICE_FHDM_SUCCESS);
			shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_SUCCESS);
			shareLogVo.setRecord_amount(String.valueOf(count));// 本次访问的数据量
			// recordAmount = String.valueOf(count);
		}

		// 如果为测试调用，直接返回结果
		if (params.containsKey("USER_TYPE")
				&&( "TEST".equals(params.get("USER_TYPE"))
				|| "SHARE_FTP".equals(params.get("USER_TYPE")))) {
			result.put(ShareConstants.SERVICE_OUT_PARAM_ARRAY, maps);
			return result;
		} else {// 正式调用，需要验证部分访问规则
			logger.debug("开始进行规则校验");
			String serviceId = params.get("SERVICE_ID").toString();
			// 校验
			CheckService checkService = new CheckService();
			Map checkMap = checkService.checkRules(serviceId, count);

			if ("Y".equals(checkMap.get(ShareConstants.SERVICE_CAN_BE_USED)
					.toString())) {
				result.put(ShareConstants.SERVICE_OUT_PARAM_ARRAY, maps);
				return result;
			} else {
				// 校验结果为不可用，那么本次返回的数据条数为0
				shareLogVo.setRecord_amount("0");
				shareLogVo.setReturn_codes(checkMap.get("FHDM").toString());
				return checkMap;
			}
		}
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
	public static String createResultXml(List resultMap, Map params,
			String total, ShareLogVo shareLogVo)
	{
		String recordAmount = "0";
		Map result = new HashMap();
		if (resultMap.size() < 1) {
			result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
					ShareConstants.SERVICE_FHDM_NO_RESULT);// 查询成功但结果条数为0
			shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_NO_RESULT);
			shareLogVo.setRecord_amount(String.valueOf(resultMap.size())); // 本次访问的数据量
			recordAmount = String.valueOf(resultMap.size());
		} else {
			result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
					ShareConstants.SERVICE_FHDM_SUCCESS);
			shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_SUCCESS);
			shareLogVo.setRecord_amount(String.valueOf(resultMap.size()));// 本次访问的数据量
			recordAmount = String.valueOf(resultMap.size());
		}
		result.put(ShareConstants.SERVICE_OUT_PARAM_KSJLS,
				params.get(ShareConstants.SERVICE_OUT_PARAM_KSJLS));// 开始记录数
		result.put(ShareConstants.SERVICE_OUT_PARAM_JSJLS,
				params.get(ShareConstants.SERVICE_OUT_PARAM_JSJLS));// 结束记录数
		result.put(ShareConstants.SERVICE_OUT_PARAM_ZTS, total);// 总条数

		if (params.containsKey("USER_TYPE")
				&& "TEST".equals(params.get("USER_TYPE"))) {
			Map[] maps = new HashMap[resultMap.size()];
			for (int i = 0; i < resultMap.size(); i++) {
				Map map = (Map) resultMap.get(i);
				if (map.containsKey(ShareConstants.SERVICE_OUT_RN))
					map.remove(ShareConstants.SERVICE_OUT_RN); // 将RN去掉
				if (map.get(ShareConstants.SERVICE_OUT_ETL_TIMESTAMP) != null) {
					map.put(ShareConstants.SERVICE_OUT_ETL_TIMESTAMP,
							map.get(ShareConstants.SERVICE_OUT_ETL_TIMESTAMP)
									.toString());
				}
				maps[i] = map;
			}
			result.put(ShareConstants.SERVICE_OUT_PARAM_ARRAY, maps);
			return XmlToMapUtil.map2Dom(result);
		} else {

			String serviceId = params.get("SERVICE_ID").toString();
			// 校验
			CheckService checkService = new CheckService();
			Map checkMap = checkService.checkRules(serviceId,
					Integer.parseInt(recordAmount));

			if ("Y".equals(checkMap.get(ShareConstants.SERVICE_CAN_BE_USED)
					.toString())) {
				Map[] maps = new HashMap[resultMap.size()];
				for (int i = 0; i < resultMap.size(); i++) {
					Map map = (Map) resultMap.get(i);
					if (map.containsKey(ShareConstants.SERVICE_OUT_RN))
						map.remove(ShareConstants.SERVICE_OUT_RN); // 将RN去掉
					if (map.get(ShareConstants.SERVICE_OUT_ETL_TIMESTAMP) != null) {
						map.put(ShareConstants.SERVICE_OUT_ETL_TIMESTAMP, map
								.get(ShareConstants.SERVICE_OUT_ETL_TIMESTAMP)
								.toString());
					}
					maps[i] = map;
				}
				result.put(ShareConstants.SERVICE_OUT_PARAM_ARRAY, maps);
				return XmlToMapUtil.map2Dom(result);
			} else {
				// 如果本次获取失败那么本次获取的数据条数为0
				shareLogVo.setRecord_amount("0");

				shareLogVo.setReturn_codes(checkMap.get("FHDM").toString());
				return XmlToMapUtil.map2Dom(checkMap);
			}
		}

	}

	public static Map[] rsToMaps(ResultSet rs, int total) throws SQLException
	{

		Map[] maps = new HashMap[total];

		ResultSetMetaData md = rs.getMetaData(); // 获取查询的字段
		int columnCount = md.getColumnCount(); // 获取每行的总列数
		Map rowData;
		int index = 0;
		while (rs.next()) {
			rowData = new HashMap(columnCount);
			for (int i = 1; i <= columnCount; i++) {
				// 组合行数据
				if (null != rs.getObject(i))
					rowData.put(md.getColumnName(i), rs.getObject(i));
				else
					rowData.put(md.getColumnName(i), "");
			}
			if (rowData.containsKey(ShareConstants.SERVICE_OUT_RN))
				rowData.remove(ShareConstants.SERVICE_OUT_RN); // 将RN去掉
			if (rowData.get(ShareConstants.SERVICE_OUT_ETL_TIMESTAMP) != null) {
				rowData.put(ShareConstants.SERVICE_OUT_ETL_TIMESTAMP, rowData
						.get(ShareConstants.SERVICE_OUT_ETL_TIMESTAMP)
						.toString());
			}
			maps[index] = rowData;
			index++;
		}

		return maps;
	}

	/**
	 * 
	 * createResultXmlStr调用服务成功返回结果
	 * 
	 * @param resultMap
	 * @param params
	 * @param total
	 * @return String
	 * @throws SQLException
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	@SuppressWarnings({ "rawtypes" })
	public static String createResultXmlStr(ResultSet rs, Map params,
			String total, ShareLogVo shareLogVo) throws SQLException
	{

		Document document = DocumentHelper.createDocument();
		Element param = document.addElement("params");
		// Element element=
		/** 设置固定的返回参数开始 **/
		// 开始记录数
		param.addElement(ShareConstants.SERVICE_OUT_PARAM_KSJLS).setText(
				params.get(ShareConstants.SERVICE_OUT_PARAM_KSJLS).toString());
		// 结束记录数
		param.addElement(ShareConstants.SERVICE_OUT_PARAM_JSJLS).setText(
				params.get(ShareConstants.SERVICE_OUT_PARAM_JSJLS).toString());
		// 总条数
		param.addElement(ShareConstants.SERVICE_OUT_PARAM_ZTS).setText(total);

		// 默认设置返回代码为成功
		param.addElement(ShareConstants.SERVICE_OUT_PARAM_FHDM).setText(
				ShareConstants.SERVICE_FHDM_SUCCESS);
		/** 设置固定的返回参数结束 **/

		/** 遍历结果数据，并组织数据格式开始 **/
		ResultSetMetaData md = rs.getMetaData(); // 获取查询的字段

		int columnCount = md.getColumnCount(); // 获取每行的总列数
		int count = 0;
		Element data = null;
		while (rs.next()) {
			if (count == 0) {
				data = param.addElement("data");
			}
			Element row = data.addElement("row");
			for (int i = 1; i <= columnCount; i++) {
				// 列名中排除字段为RN的字段
				String colunm_type = md.getColumnTypeName(i);
				logger.debug("----------i="+i+"--------------"+md.getColumnName(i)+"------"+colunm_type);
				
				if (!md.getColumnName(i).equals(ShareConstants.SERVICE_OUT_RN)) {
					String text="";
					if("CLOB".equals(colunm_type)){
						Clob clob = (Clob)rs.getClob(i);
						text=(clob==null)?"":new String(clob.getSubString(1, (int)clob.length()));
						row.addElement(md.getColumnName(i)).setText(text);
					}else if("BLOB".equals(colunm_type)){
						Blob blob = (Blob)rs.getBlob(i);
						text=(blob==null)?"":new String(blob.getBytes(1, (int)blob.length()));
						row.addElement(md.getColumnName(i)).setText(text);
					}else if("LONG".equals(colunm_type)){
						logger.debug("---LONG--");
						Reader reader =null;
						BufferedReader bufReader =null;
						StringBuffer bufStr =null;
						try {
							reader = rs.getCharacterStream(i);
							bufReader = new BufferedReader(reader);
							bufStr= new StringBuffer();
							String line="";
							while((line=bufReader.readLine())!=null){
								bufStr.append(line);
								bufStr.append("\r\n");
							}
						} catch (Exception e) {
							logger.error("读取LONG类型字段出错："+e.getMessage());
						}finally{
							try {
								if(reader!=null){
									reader.close();
								}
								if(bufReader!=null){
									bufReader.close();
								}
							} catch (Exception e2) {
								logger.debug("关闭输入流出错："+e2.getMessage());
							}
							if(bufStr!=null){
								row.addElement(md.getColumnName(i)).setText(bufStr.toString());
							}else{
								row.addElement(md.getColumnName(i)).setText("");
							}
							
						}
					}else{//其他通用类型字段处理
						Object obj = rs.getObject(i);
						text = (obj==null)?"":obj.toString();
						row.addElement(md.getColumnName(i)).setText(text);
					}
					
				}
			}
			count++;
		}
		// 设置本次访问条数
		shareLogVo.setRecord_amount(String.valueOf(count));
		/** 遍历结果数据，并组织数据格式结束 **/

		/** 对返回代码的处理开始 **/
		if (count < 1) {
			// 查询成功但结果条数为0
			param.element(ShareConstants.SERVICE_OUT_PARAM_FHDM).setText(
					ShareConstants.SERVICE_FHDM_NO_RESULT);
			shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_NO_RESULT);
		} else {
			// 查询成功,结果不为空
			param.element(ShareConstants.SERVICE_OUT_PARAM_FHDM).setText(
					ShareConstants.SERVICE_FHDM_SUCCESS);
			shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_SUCCESS);
		}
		/** 对返回代码的处理结束 **/

		/** 对最终返回结果的处理开始 **/
		if (params.containsKey("USER_TYPE")
				&& "TEST".equals(params.get("USER_TYPE"))) {

			return XmlToMapUtil.dom2String(document);
		} else {
			String serviceId = params.get("SERVICE_ID").toString();
			// 校验
			long start = System.currentTimeMillis();
			CheckService checkService = new CheckService();
			Map checkMap = checkService.checkRules(serviceId, count);
			long end = System.currentTimeMillis();
			logger.debug("日志批次号为：" + params.get("batch") + " 规则校验耗时："
					+ String.valueOf(((end - start) / 1000f)) + "秒！");

			if ("Y".equals(checkMap.get(ShareConstants.SERVICE_CAN_BE_USED)
					.toString())) {
				return XmlToMapUtil.dom2String(document);
			} else {
				// 校验结果为不可用，本次获取的数据条数为0
				shareLogVo.setRecord_amount("0");
				shareLogVo.setReturn_codes(checkMap.get("FHDM").toString());
				return XmlToMapUtil.map2Dom(checkMap);
			}
		}
		/** 对最终返回结果的处理结束 **/
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
	public static Map createSuccuseMap()
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
	public static Map createUserErrorlMap()
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
	public static Map createPwdErrorlMap()
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
	public static Map createLoginFailMap()
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
	public static Map createUserFailMap()
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
	public static Map createServiceIPFailMap()
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
	public static Map createSvrNotFoundMap()
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
	public static Map createServiceStateFailMap()
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
	public static Map createServiceDateFailMap()
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
	public static Map createSvrTimeErrorMap()
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
	public static Map createSvrNumErrorMap()
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
	public static Map createSvrTotalErrorMap()
	{
		Map result = new HashMap();
		result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
				ShareConstants.SERVICE_FHDM_LOCK_TOTAL);
		result.put(ShareConstants.SERVICE_CAN_BE_USED, "N");
		return result;
	}

	/**
	 * 
	 * createSvrTimeCountErrorMap 单次访问条数受限制
	 * 
	 * @param params
	 * @return Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public static Map createSvrTimeCountErrorMap()
	{
		Map result = new HashMap();
		result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
				ShareConstants.SERVICE_FHDM_LOCK_TIME_COUNT);
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
	public static Map createSystemErrorMap()
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
	public static Map createSqlErrorMap()
	{
		Map result = new HashMap();
		result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
				ShareConstants.SERVICE_FHDM_SQL_ERROR);
		return result;
	}

	public static Map createParamErrorMap()
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

	public static Map createVerifyErrorMap()
	{
		Map result = new HashMap();

		result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
				ShareConstants.SERVICE_FHDM_VERIFY_ERROR);
		result.put(ShareConstants.SERVICE_CAN_BE_USED, "N");
		return result;
	}

	public static Map createTimeNotInMonthErrorMap()
	{
		Map result = new HashMap();
		result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
				ShareConstants.SERVICE_FHDM_OVER_MONTH);
		return result;
	}

	public static Map createTimeOutRuleErrorMap()
	{
		Map result = new HashMap();
		result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
				ShareConstants.SERVICE_FHDM_OVER_DATE);
		return result;
	}

	public static Map createOverMaxMap(Map params)
	{
		Map result = new HashMap();
		result.put("FHDM", ShareConstants.SERVICE_FHDM_OVER_MAX);
		result.put("KSJLS", params.get(ShareConstants.SERVICE_OUT_PARAM_KSJLS));
		result.put("JSJLS", params.get(ShareConstants.SERVICE_OUT_PARAM_JSJLS));
		result.put("ZTS", "0");
		result.put("GSDJ_INFO_ARRAY", null);

		return result;
	}

	public static Map createUnknownFailMap(Map params)
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
