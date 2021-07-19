package com.gwssi.common.delivery.remote.ems.domain;

import java.util.List;
import java.util.Map;


public class MessageRequest extends AbstractMessage{
		
	
	
	public MessageHeader getHEAD(){
		return this.HEAD;
	}
	
	public MessageHeaderRequest getRequestHeader(){
		return this.HEAD;
	}
	
	protected MessageHeaderRequest HEAD = new MessageHeaderRequest();
	
	/**
	 * 设置请求方法代码。
	 * 语法糖丸。
	 * @param funcCode
	 */
	public void setFuncCode(String funcCode){
		HEAD.setFUNC_CODE(funcCode);
	}
	/**
	 * 设置请求参数。
	 * 语法糖丸。
	 * @param dataSetName
	 * @param dataSetContent
	 */
	public void putDataSet(String dataSetName,List<Map<String, String>> dataSetContent){
		super.getBODY().putDataSet(dataSetName, dataSetContent);
	}
}
