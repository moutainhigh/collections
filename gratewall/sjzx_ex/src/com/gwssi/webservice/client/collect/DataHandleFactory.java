package com.gwssi.webservice.client.collect;

public class DataHandleFactory
{
	/**
	 * 
	 * getDataHandle ���ɼ������ݲ���ɼ���
	 * 
	 * @return DataHandleInterface
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public static DataHandleInterface getDataHandle()
	{
		return new DataHandle();
	}
}
