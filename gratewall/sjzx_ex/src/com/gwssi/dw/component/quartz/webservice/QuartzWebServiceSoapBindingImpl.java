/**
 * QuartzWebServiceSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gwssi.dw.component.quartz.webservice;

import com.gwssi.dw.component.quartz.QuartzInitService;
import com.gwssi.dw.runmgr.services.vo.VoSysCltUser;

import cn.gwssi.common.context.DataBus;

public class QuartzWebServiceSoapBindingImpl implements com.gwssi.dw.component.quartz.webservice.IQuartzWebService{
    public void resetJob(java.lang.String in0, java.lang.String in1,
			java.lang.String in2, java.lang.String in3, java.lang.String in4,
			java.lang.String in5, java.lang.String in6, java.lang.String in7,
			java.lang.String in8, java.lang.String in9)
			throws java.rmi.RemoteException
	{    	
    	DataBus db = new DataBus();
    	db.setValue(VoSysCltUser.ITEM_JOBNAME,in0);
    	db.setValue(VoSysCltUser.ITEM_GROUPNAME,in1);
    	db.setValue(VoSysCltUser.ITEM_CLASSNAME,in2);
    	db.setValue(VoSysCltUser.ITEM_HOURS,in3);
    	db.setValue(VoSysCltUser.ITEM_MINUTES,in4);
    	db.setValue(VoSysCltUser.ITEM_SECONDS,in5);
    	db.setValue(VoSysCltUser.ITEM_STRATEGY,in6);
    	db.setValue(VoSysCltUser.ITEM_STRATEGYDESC,in7);
    	db.setValue(VoSysCltUser.ITEM_STARTDATE,in8);
    	db.setValue(VoSysCltUser.ITEM_ENDDATE,in9);
    	System.out.println("数据结构："+db);
    	try {
			QuartzInitService.resetJob(db);
		} catch (Exception e) {
			new java.rmi.RemoteException("远程重新启动采集调度失败",e);
		}
	}

	public void stopJob(java.lang.String in0, java.lang.String in1,
			java.lang.String in2, java.lang.String in3, java.lang.String in4,
			java.lang.String in5, java.lang.String in6, java.lang.String in7,
			java.lang.String in8, java.lang.String in9)
			throws java.rmi.RemoteException
	{
    	DataBus db = new DataBus();
    	db.setValue(VoSysCltUser.ITEM_JOBNAME,in0);
    	db.setValue(VoSysCltUser.ITEM_GROUPNAME,in1);
    	db.setValue(VoSysCltUser.ITEM_CLASSNAME,in2);
    	db.setValue(VoSysCltUser.ITEM_HOURS,in3);
    	db.setValue(VoSysCltUser.ITEM_MINUTES,in4);
    	db.setValue(VoSysCltUser.ITEM_SECONDS,in5);
    	db.setValue(VoSysCltUser.ITEM_STRATEGY,in6);
    	db.setValue(VoSysCltUser.ITEM_STRATEGYDESC,in7);
    	db.setValue(VoSysCltUser.ITEM_STARTDATE,in8);
    	db.setValue(VoSysCltUser.ITEM_ENDDATE,in9);    	
    	try {
			QuartzInitService.stopJob(db);
		} catch (Exception e) {
			new java.rmi.RemoteException("远程停止采集调度失败",e);
		}
	}

	public void startJob(java.lang.String in0, java.lang.String in1,
			java.lang.String in2, java.lang.String in3, java.lang.String in4,
			java.lang.String in5, java.lang.String in6, java.lang.String in7,
			java.lang.String in8, java.lang.String in9)
			throws java.rmi.RemoteException
	{
    	DataBus db = new DataBus();
    	db.setValue(VoSysCltUser.ITEM_JOBNAME,in0);
    	db.setValue(VoSysCltUser.ITEM_GROUPNAME,in1);
    	db.setValue(VoSysCltUser.ITEM_CLASSNAME,in2);
    	db.setValue(VoSysCltUser.ITEM_HOURS,in3);
    	db.setValue(VoSysCltUser.ITEM_MINUTES,in4);
    	db.setValue(VoSysCltUser.ITEM_SECONDS,in5);
    	db.setValue(VoSysCltUser.ITEM_STRATEGY,in6);
    	db.setValue(VoSysCltUser.ITEM_STRATEGYDESC,in7);
    	db.setValue(VoSysCltUser.ITEM_STARTDATE,in8);
    	db.setValue(VoSysCltUser.ITEM_ENDDATE,in9);    	
    	try {
			QuartzInitService.startJob(db);
		} catch (Exception e) {
			new java.rmi.RemoteException("远程启动采集调度失败",e);
		}
	}
}
