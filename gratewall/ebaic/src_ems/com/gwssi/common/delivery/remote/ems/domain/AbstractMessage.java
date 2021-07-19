package com.gwssi.common.delivery.remote.ems.domain;

/**
 * 对于DATE类型参数，要求日期格式为：YYYYMMDDHH24MISS，如：20100801091050。
 * 
 * @author 海龙
 *
 */
public abstract class AbstractMessage {
	
	public String toJSonString(){
		String ret= String.format("{\"HEAD\":%s,\"BODY\":%s}",
				this.getHEAD().toJSonString(),
				this.getBODY().toJSonString());
		return ret;
	}
	
	public abstract MessageHeader getHEAD();
	
	public MessageBody getBODY(){
		return this.BODY;
	}
	
	protected MessageBody BODY = new MessageBody();
}
