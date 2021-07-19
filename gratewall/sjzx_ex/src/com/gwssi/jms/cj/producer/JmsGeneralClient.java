package com.gwssi.jms.cj.producer;

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
public interface JmsGeneralClient
{
	/**
	 * 
	 * colGetDom 采集数据
	 * 
	 * @return String
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	String colGetDom(Map param, CollectLogVo collectLogVo);

	
}
