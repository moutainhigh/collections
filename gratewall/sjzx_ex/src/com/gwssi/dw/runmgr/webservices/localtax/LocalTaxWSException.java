package com.gwssi.dw.runmgr.webservices.localtax;


public class LocalTaxWSException extends Exception
{
	private String message;
	public LocalTaxWSException(String s){
		super(s);
        this.message = s;
	}
	
	public LocalTaxWSException(String message,Exception e){
		super("",e);
        this.message = message;
	}	
    
    public String getMessage(){
        return message;
    }
}
