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
 * 项目名称：bjgs_exchange 类名称：GSGeneralWebServiceForDotnet 类描述：dotnet访问webservice的接口
 * 创建人：lizheng 创建时间：Jun 24, 2013 10:34:25 AM 修改人：lizheng 修改时间：Jun 24, 2013
 * 10:34:25 AM 修改备注：
 * 
 * @version
 * 
 */
public class GSGeneralWebServiceForDotnet
{
	// 日志
	protected static Logger	logger	= TxnLogger
											.getLogger(GSGeneralWebServiceForDotnet.class
													.getName());

	/**
	 * 
	 * query 二期访问query接口的方法
	 * 
	 * @param params
	 * @return CommonResult
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public CommonResult query(CommonMapObject params)
	{
		logger.debug("开始使用.net程序访问query方法...");
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
	 * queryData 新接口访问方法
	 * 
	 * @param param
	 * @return String
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public String queryData(String param)
	{
		logger.debug("开始使用.net程序访问queryData方法...");
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
		logger.debug("进入convertResultMapToCommonResult方法...");
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
		logger.debug("用.net访问时total=" + rs.length);
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
		logger.debug("进入convertMapToArray方法...");
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
		logger.debug("进入convertCommonMapObjectToMap方法...");
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
