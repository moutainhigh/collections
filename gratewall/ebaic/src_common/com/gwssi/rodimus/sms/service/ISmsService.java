package com.gwssi.rodimus.sms.service;

public interface ISmsService {

    public String sendSms(String tel,String smsTypeCode, String msg);
	
	public String sendMoreSms(String[] tel,String smsTypeCode,String msg);
	
	public String getSmsDeliverStatus(String returnCode);
}
