package com.gwssi.rodimus.exception;

/**
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class RpcException extends RuntimeException implements Cloneable{
	
	private static final long serialVersionUID = -3534384410635756195L;

	public RpcException(String msg) {
		super(msg);
		this.message = msg;
	}

	public RpcException(String message, Exception e) {
		super(message,e);
	}

	private int code = -1;
	private String message = "";
	private Object data = null;

	private RpcException(){}
	
	public RpcException(int code){
		this.code = code;
	}
	
	
	public RpcException(int code,String message){
		this.message = message;
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	
	public RpcException clone(){
		RpcException ret = new RpcException();
		ret.setCode(this.code);
		ret.setMessage(this.message);
		ret.setData(this.data);
		return ret;
	}

}
