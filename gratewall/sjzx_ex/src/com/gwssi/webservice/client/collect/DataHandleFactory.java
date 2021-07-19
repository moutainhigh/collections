package com.gwssi.webservice.client.collect;

public class DataHandleFactory
{
	/**
	 * 
	 * getDataHandle 将采集的数据插入采集表
	 * 
	 * @return DataHandleInterface
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public static DataHandleInterface getDataHandle()
	{
		return new DataHandle();
	}
}
