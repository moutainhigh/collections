package com.gwssi.webservice.server.dotnet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.gwssi.common.component.logger.TxnLogger;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.webservice.server.GSGeneralWebService;

/**
 * 
 * 
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�GSGeneralWebServiceForDotnet ��������dotnet����webservice�Ľӿ�
 * �����ˣ�lizheng ����ʱ�䣺Jun 24, 2013 10:34:25 AM �޸��ˣ�lizheng �޸�ʱ�䣺Jun 24, 2013
 * 10:34:25 AM �޸ı�ע��
 * 
 * @version
 * 
 */
public class GSGeneralWebServiceForDotnet
{
	// ��־
	protected static Logger	logger	= TxnLogger
											.getLogger(GSGeneralWebServiceForDotnet.class
													.getName());

	/**
	 * 
	 * query ���ڷ���query�ӿڵķ���
	 * 
	 * @param params
	 * @return CommonResult
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public CommonResult query(CommonMapObject params)
	{
		logger.debug("��ʼʹ��.net�������query����...");
		Map paramMap = convertCommonMapObjectToMap(params);
		if (paramMap == null) {
			return new CommonResult();
		}
		GSGeneralWebService service = new GSGeneralWebService();
		Map resultMap = new HashMap();
		try {
			resultMap = service.query(paramMap);
		} catch (DBException e) {
			e.printStackTrace();
		}
		CommonResult result = convertResultMapToCommonResult(resultMap);
		return result;
	}

	/**
	 * 
	 * queryData �½ӿڷ��ʷ���
	 * 
	 * @param param
	 * @return String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public String queryData(String param)
	{
		logger.debug("��ʼʹ��.net�������queryData����...");
		GSGeneralWebService service = new GSGeneralWebService();
		String result = "";
		try {
			result = service.queryData(param);
		} catch (DBException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	protected CommonResult convertResultMapToCommonResult(Map resultMap)
	{
		logger.debug("����convertResultMapToCommonResult����...");
		CommonResult result = new CommonResult();
		result.setFHDM((String) resultMap.get("FHDM"));
		result.setKSJLS((String) resultMap.get("KSJLS"));
		result.setJSJLS((String) resultMap.get("JSJLS"));
		String zts = (String) resultMap.get("ZTS");
		result.setZTS(zts);
		int total = Integer.parseInt(zts);
		if (total == 0) {
			return result;
		}

		HashMap[] rs = (HashMap[]) resultMap.get("GSDJ_INFO_ARRAY");
		if (rs == null) {
			return result;
		}
		logger.debug("��.net����ʱtotal=" + rs.length);
		CommonMapObject[] commonObject = new CommonMapObject[rs.length];
		for (int i = 0; i < rs.length; i++) {
			commonObject[i] = convertMapToArray(rs[i]);
		}
		result.setCommonObject(commonObject);
		logger.debug("result.getCommonObject().length is : "
				+ result.getCommonObject().length);
		/*
		 * if(total>1){ System.out.println(rs.length);
		 * 
		 * CommonMapObject[][] mutiple = new CommonMapObject[rs.length][];
		 * for(int i=0;i<rs.length;i++){ System.out.println("adddsf");
		 * mutiple[i] = convertMapToArray(rs[i]); }
		 * result.setMultipleObject(mutiple); }else{ CommonMapObject[] single =
		 * convertMapToArray(rs[0]); result.setSingleObject(single); }
		 */
		return result;
	}

	protected CommonMapObject convertMapToArray(Map result)
	{
		logger.debug("����convertMapToArray����...");
		CommonMapObject common = new CommonMapObject(result.size());
		Iterator keyIte = result.keySet().iterator();
		int i = 0;
		while (keyIte.hasNext()) {
			String key = (String) keyIte.next();
			String value = String.valueOf(result.get(key));
			common.getKey()[i] = key;
			common.getValue()[i] = value;
			i++;
		}
		return common;
	}

	protected Map convertCommonMapObjectToMap(CommonMapObject params)
	{
		logger.debug("����convertCommonMapObjectToMap����...");
		if (params == null) {
			return null;
		}
		String[] key = params.getKey();
		String[] value = params.getValue();
		if (key == null || key.length == 0) {
			return null;
		}
		Map m = new HashMap();
		for (int i = 0; i < key.length; i++) {
			m.put(key[i], value[i]);
		}
		return m;
	}
}
