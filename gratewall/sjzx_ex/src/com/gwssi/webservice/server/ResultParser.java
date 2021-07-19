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
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�ResultParser �����������񷵻�ֵ��װ�� �����ˣ�lizheng ����ʱ�䣺Apr 1, 2013
 * 11:18:27 AM �޸��ˣ�lizheng �޸�ʱ�䣺Apr 1, 2013 11:18:27 AM �޸ı�ע��
 * 
 * @version
 * 
 */
public class ResultParser
{
	// ��־
	protected static Logger	logger	= TxnLogger.getLogger(ResultParser.class
											.getName());

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
	public static Map createResultMap(List resultMap, Map params, String total,
			ShareLogVo shareLogVo)
	{
		String recordAmount = "0";
		Map result = new HashMap();
		if (resultMap.size() < 1) {
			// ��ѯ�ɹ����������Ϊ0
			result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
					ShareConstants.SERVICE_FHDM_NO_RESULT);
			shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_NO_RESULT);
			shareLogVo.setRecord_amount(String.valueOf(resultMap.size())); // ���η��ʵ�������
			recordAmount = String.valueOf(resultMap.size());
		} else {
			result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
					ShareConstants.SERVICE_FHDM_SUCCESS);
			shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_SUCCESS);
			shareLogVo.setRecord_amount(String.valueOf(resultMap.size()));// ���η��ʵ�������
			recordAmount = String.valueOf(resultMap.size());
		}
		result.put(ShareConstants.SERVICE_OUT_PARAM_KSJLS,
				params.get(ShareConstants.SERVICE_OUT_PARAM_KSJLS));// ��ʼ��¼��
		result.put(ShareConstants.SERVICE_OUT_PARAM_JSJLS,
				params.get(ShareConstants.SERVICE_OUT_PARAM_JSJLS));// ������¼��
		result.put(ShareConstants.SERVICE_OUT_PARAM_ZTS, total);// ������

		if (params.containsKey("USER_TYPE")
				&& "TEST".equals(params.get("USER_TYPE"))) {
			Map[] maps = new HashMap[resultMap.size()];
			for (int i = 0; i < resultMap.size(); i++) {
				Map map = (Map) resultMap.get(i);
				if (map.containsKey(ShareConstants.SERVICE_OUT_RN))
					map.remove(ShareConstants.SERVICE_OUT_RN);// ��RNȥ��
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
			// У��
			CheckService checkService = new CheckService();
			Map checkMap = checkService.checkRules(serviceId,
					Integer.parseInt(recordAmount));

			if ("Y".equals(checkMap.get(ShareConstants.SERVICE_CAN_BE_USED)
					.toString())) {
				Map[] maps = new HashMap[resultMap.size()];
				for (int i = 0; i < resultMap.size(); i++) {
					Map map = (Map) resultMap.get(i);
					if (map.containsKey(ShareConstants.SERVICE_OUT_RN))
						map.remove(ShareConstants.SERVICE_OUT_RN);// ��RNȥ��
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
				// ������λ�ȡʧ����ô���λ�ȡ����������Ϊ0
				shareLogVo.setRecord_amount("0");
				return checkMap;
			}
		}

	}

	/**
	 * 
	 * createResultMap ���÷���ɹ����ؽ��
	 * 
	 * @param resultMap
	 * @param params
	 * @param total
	 * @return Map
	 * @throws SQLException
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map createResultMapByRs(ResultSet rs, Map params,
			String total, ShareLogVo shareLogVo) throws SQLException
	{
		Map result = new HashMap();
		/** �̶�����ֵ **/
		result.put(ShareConstants.SERVICE_OUT_PARAM_KSJLS,
				params.get(ShareConstants.SERVICE_OUT_PARAM_KSJLS));// ��ʼ��¼��
		result.put(ShareConstants.SERVICE_OUT_PARAM_JSJLS,
				params.get(ShareConstants.SERVICE_OUT_PARAM_JSJLS));// ������¼��
		result.put(ShareConstants.SERVICE_OUT_PARAM_ZTS, total);// ������

		/** ��װ������� **/
		//long s = System.currentTimeMillis();
		ResultSetMetaData md = rs.getMetaData(); // ��ȡ��ѯ���ֶ�
		int columnCount = md.getColumnCount(); // ��ȡÿ�е�������
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
			//ȥ��rn��
			rowData.remove(ShareConstants.SERVICE_OUT_RN);
			list.add(rowData);
		}
		//long e = System.currentTimeMillis();
		//logger.debug("��List ��ʱ��"+(e-s)/1000f+"��");
		/** ���÷����� **/
		int count = list.size();
		
		// ��listת����map����
		Map[] maps = (HashMap[]) list.toArray(new HashMap[count]);
		
		if (count < 1) {
			// ��ѯ�ɹ����������Ϊ0
			result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
					ShareConstants.SERVICE_FHDM_NO_RESULT);
			shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_NO_RESULT);
			shareLogVo.setRecord_amount(String.valueOf(count)); // ���η��ʵ�������
			// recordAmount = String.valueOf(count);
		} else {
			result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
					ShareConstants.SERVICE_FHDM_SUCCESS);
			shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_SUCCESS);
			shareLogVo.setRecord_amount(String.valueOf(count));// ���η��ʵ�������
			// recordAmount = String.valueOf(count);
		}

		// ���Ϊ���Ե��ã�ֱ�ӷ��ؽ��
		if (params.containsKey("USER_TYPE")
				&&( "TEST".equals(params.get("USER_TYPE"))
				|| "SHARE_FTP".equals(params.get("USER_TYPE")))) {
			result.put(ShareConstants.SERVICE_OUT_PARAM_ARRAY, maps);
			return result;
		} else {// ��ʽ���ã���Ҫ��֤���ַ��ʹ���
			logger.debug("��ʼ���й���У��");
			String serviceId = params.get("SERVICE_ID").toString();
			// У��
			CheckService checkService = new CheckService();
			Map checkMap = checkService.checkRules(serviceId, count);

			if ("Y".equals(checkMap.get(ShareConstants.SERVICE_CAN_BE_USED)
					.toString())) {
				result.put(ShareConstants.SERVICE_OUT_PARAM_ARRAY, maps);
				return result;
			} else {
				// У����Ϊ�����ã���ô���η��ص���������Ϊ0
				shareLogVo.setRecord_amount("0");
				shareLogVo.setReturn_codes(checkMap.get("FHDM").toString());
				return checkMap;
			}
		}
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
	public static String createResultXml(List resultMap, Map params,
			String total, ShareLogVo shareLogVo)
	{
		String recordAmount = "0";
		Map result = new HashMap();
		if (resultMap.size() < 1) {
			result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
					ShareConstants.SERVICE_FHDM_NO_RESULT);// ��ѯ�ɹ����������Ϊ0
			shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_NO_RESULT);
			shareLogVo.setRecord_amount(String.valueOf(resultMap.size())); // ���η��ʵ�������
			recordAmount = String.valueOf(resultMap.size());
		} else {
			result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
					ShareConstants.SERVICE_FHDM_SUCCESS);
			shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_SUCCESS);
			shareLogVo.setRecord_amount(String.valueOf(resultMap.size()));// ���η��ʵ�������
			recordAmount = String.valueOf(resultMap.size());
		}
		result.put(ShareConstants.SERVICE_OUT_PARAM_KSJLS,
				params.get(ShareConstants.SERVICE_OUT_PARAM_KSJLS));// ��ʼ��¼��
		result.put(ShareConstants.SERVICE_OUT_PARAM_JSJLS,
				params.get(ShareConstants.SERVICE_OUT_PARAM_JSJLS));// ������¼��
		result.put(ShareConstants.SERVICE_OUT_PARAM_ZTS, total);// ������

		if (params.containsKey("USER_TYPE")
				&& "TEST".equals(params.get("USER_TYPE"))) {
			Map[] maps = new HashMap[resultMap.size()];
			for (int i = 0; i < resultMap.size(); i++) {
				Map map = (Map) resultMap.get(i);
				if (map.containsKey(ShareConstants.SERVICE_OUT_RN))
					map.remove(ShareConstants.SERVICE_OUT_RN); // ��RNȥ��
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
			// У��
			CheckService checkService = new CheckService();
			Map checkMap = checkService.checkRules(serviceId,
					Integer.parseInt(recordAmount));

			if ("Y".equals(checkMap.get(ShareConstants.SERVICE_CAN_BE_USED)
					.toString())) {
				Map[] maps = new HashMap[resultMap.size()];
				for (int i = 0; i < resultMap.size(); i++) {
					Map map = (Map) resultMap.get(i);
					if (map.containsKey(ShareConstants.SERVICE_OUT_RN))
						map.remove(ShareConstants.SERVICE_OUT_RN); // ��RNȥ��
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
				// ������λ�ȡʧ����ô���λ�ȡ����������Ϊ0
				shareLogVo.setRecord_amount("0");

				shareLogVo.setReturn_codes(checkMap.get("FHDM").toString());
				return XmlToMapUtil.map2Dom(checkMap);
			}
		}

	}

	public static Map[] rsToMaps(ResultSet rs, int total) throws SQLException
	{

		Map[] maps = new HashMap[total];

		ResultSetMetaData md = rs.getMetaData(); // ��ȡ��ѯ���ֶ�
		int columnCount = md.getColumnCount(); // ��ȡÿ�е�������
		Map rowData;
		int index = 0;
		while (rs.next()) {
			rowData = new HashMap(columnCount);
			for (int i = 1; i <= columnCount; i++) {
				// ���������
				if (null != rs.getObject(i))
					rowData.put(md.getColumnName(i), rs.getObject(i));
				else
					rowData.put(md.getColumnName(i), "");
			}
			if (rowData.containsKey(ShareConstants.SERVICE_OUT_RN))
				rowData.remove(ShareConstants.SERVICE_OUT_RN); // ��RNȥ��
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
	 * createResultXmlStr���÷���ɹ����ؽ��
	 * 
	 * @param resultMap
	 * @param params
	 * @param total
	 * @return String
	 * @throws SQLException
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	@SuppressWarnings({ "rawtypes" })
	public static String createResultXmlStr(ResultSet rs, Map params,
			String total, ShareLogVo shareLogVo) throws SQLException
	{

		Document document = DocumentHelper.createDocument();
		Element param = document.addElement("params");
		// Element element=
		/** ���ù̶��ķ��ز�����ʼ **/
		// ��ʼ��¼��
		param.addElement(ShareConstants.SERVICE_OUT_PARAM_KSJLS).setText(
				params.get(ShareConstants.SERVICE_OUT_PARAM_KSJLS).toString());
		// ������¼��
		param.addElement(ShareConstants.SERVICE_OUT_PARAM_JSJLS).setText(
				params.get(ShareConstants.SERVICE_OUT_PARAM_JSJLS).toString());
		// ������
		param.addElement(ShareConstants.SERVICE_OUT_PARAM_ZTS).setText(total);

		// Ĭ�����÷��ش���Ϊ�ɹ�
		param.addElement(ShareConstants.SERVICE_OUT_PARAM_FHDM).setText(
				ShareConstants.SERVICE_FHDM_SUCCESS);
		/** ���ù̶��ķ��ز������� **/

		/** ����������ݣ�����֯���ݸ�ʽ��ʼ **/
		ResultSetMetaData md = rs.getMetaData(); // ��ȡ��ѯ���ֶ�

		int columnCount = md.getColumnCount(); // ��ȡÿ�е�������
		int count = 0;
		Element data = null;
		while (rs.next()) {
			if (count == 0) {
				data = param.addElement("data");
			}
			Element row = data.addElement("row");
			for (int i = 1; i <= columnCount; i++) {
				// �������ų��ֶ�ΪRN���ֶ�
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
							logger.error("��ȡLONG�����ֶγ���"+e.getMessage());
						}finally{
							try {
								if(reader!=null){
									reader.close();
								}
								if(bufReader!=null){
									bufReader.close();
								}
							} catch (Exception e2) {
								logger.debug("�ر�����������"+e2.getMessage());
							}
							if(bufStr!=null){
								row.addElement(md.getColumnName(i)).setText(bufStr.toString());
							}else{
								row.addElement(md.getColumnName(i)).setText("");
							}
							
						}
					}else{//����ͨ�������ֶδ���
						Object obj = rs.getObject(i);
						text = (obj==null)?"":obj.toString();
						row.addElement(md.getColumnName(i)).setText(text);
					}
					
				}
			}
			count++;
		}
		// ���ñ��η�������
		shareLogVo.setRecord_amount(String.valueOf(count));
		/** ����������ݣ�����֯���ݸ�ʽ���� **/

		/** �Է��ش���Ĵ���ʼ **/
		if (count < 1) {
			// ��ѯ�ɹ����������Ϊ0
			param.element(ShareConstants.SERVICE_OUT_PARAM_FHDM).setText(
					ShareConstants.SERVICE_FHDM_NO_RESULT);
			shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_NO_RESULT);
		} else {
			// ��ѯ�ɹ�,�����Ϊ��
			param.element(ShareConstants.SERVICE_OUT_PARAM_FHDM).setText(
					ShareConstants.SERVICE_FHDM_SUCCESS);
			shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_SUCCESS);
		}
		/** �Է��ش���Ĵ������ **/

		/** �����շ��ؽ���Ĵ���ʼ **/
		if (params.containsKey("USER_TYPE")
				&& "TEST".equals(params.get("USER_TYPE"))) {

			return XmlToMapUtil.dom2String(document);
		} else {
			String serviceId = params.get("SERVICE_ID").toString();
			// У��
			long start = System.currentTimeMillis();
			CheckService checkService = new CheckService();
			Map checkMap = checkService.checkRules(serviceId, count);
			long end = System.currentTimeMillis();
			logger.debug("��־���κ�Ϊ��" + params.get("batch") + " ����У���ʱ��"
					+ String.valueOf(((end - start) / 1000f)) + "�룡");

			if ("Y".equals(checkMap.get(ShareConstants.SERVICE_CAN_BE_USED)
					.toString())) {
				return XmlToMapUtil.dom2String(document);
			} else {
				// У����Ϊ�����ã����λ�ȡ����������Ϊ0
				shareLogVo.setRecord_amount("0");
				shareLogVo.setReturn_codes(checkMap.get("FHDM").toString());
				return XmlToMapUtil.map2Dom(checkMap);
			}
		}
		/** �����շ��ؽ���Ĵ������ **/
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
	public static Map createSuccuseMap()
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
	 * createPwdErrorlMap ������ʧ��
	 * 
	 * @param param
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
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
	 * createLoginFailMap ��¼���ʧ��
	 * 
	 * @param params
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
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
	 * createUserFailMap �û���Ч
	 * 
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
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
	 * createServiceIPFailMap ��IP���ʧ��
	 * 
	 * @param param
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
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
	 * createSvrNotFoundMap δ�ҵ�����
	 * 
	 * @param param
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
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
	 * createServiceStateFailMap ����״̬����
	 * 
	 * @param param
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
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
	 * createServiceDateFailMap ����ʱ�����
	 * 
	 * @param param
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
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
	 * createSvrTimeErrorMap ����ʱ��������
	 * 
	 * @param params
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
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
	 * createSvrNumErrorMap �����ܴ���������
	 * 
	 * @param params
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
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
	 * createSvrTotalErrorMap ����������������
	 * 
	 * @param params
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
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
	 * createSvrTimeCountErrorMap ���η�������������
	 * 
	 * @param params
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
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
	 * createSystemErrorMap ϵͳ����
	 * 
	 * @param params
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
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
	 * createSqlErrorMap SQL��ѯ����
	 * 
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
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
