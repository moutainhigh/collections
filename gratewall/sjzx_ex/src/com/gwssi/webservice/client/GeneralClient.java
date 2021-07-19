package com.gwssi.webservice.client;

import java.util.Map;

import com.gwssi.log.collectlog.dao.CollectLogVo;

/**
 * 
 * 
 * 项目名称：bjgs_exchange 类名称：GeneralClient 类描述：客户端接口 创建人：lizheng 创建时间：Apr 2, 2013
 * 3:51:22 PM 修改人：lizheng 修改时间：Apr 2, 2013 3:51:22 PM 修改备注：
 * 
 * @version
 * 
 */
public interface GeneralClient
{
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
	String collectData(Map param, CollectLogVo collectLogVo, Map countMap);

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
	Map collectQualitySupervisionData(Map param, CollectLogVo collectLogVo);

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
	String collectSocialSecurityData(Map param, CollectLogVo collectLogVo);
}
