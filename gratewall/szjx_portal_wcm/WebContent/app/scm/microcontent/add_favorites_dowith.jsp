<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor" %>
<%@ page import="com.trs.scm.sdk.model.Favorite" %>
<%@ include file="../../include/public_server.jsp"%>
<%! static final boolean IS_DEBUG = false;%>
<%
	// 1 获取参数
	JSPRequestProcessor oProcessor = new JSPRequestProcessor(request,response);
	int nAccountId = currRequestHelper.getInt("AccountId",0);
	int nOperateType = currRequestHelper.getInt("OperateType",0);
	String sMicroContentId = currRequestHelper.getString("MicroContentId");
	if(IS_DEBUG){
		System.out.println("nAccountId:"+nAccountId);
		System.out.println("sMicroContentId:"+sMicroContentId);
		System.out.println("nOperateType:"+nOperateType);
	}
	// 2 调用服务
	String sServiceId = "wcm61_scmfavorite",sMethodName=null;
	// 2.1 添加收藏
	if(nOperateType==1){
		sMethodName="createFavorite";
		Favorite oResult = null;
		try{
			oResult = (Favorite)oProcessor.excute(sServiceId,sMethodName);
		}catch(Exception e){
			String sErrorMsg = e.getMessage();
			if(sErrorMsg.indexOf("[ERR-") >= 0 && sErrorMsg.indexOf("<--") >= 0 ){
				sErrorMsg = sErrorMsg.substring(sErrorMsg.indexOf("[ERR-"),sErrorMsg.indexOf("<--"));
			}
			out.print(sErrorMsg);
			return;
		}
		if(oResult!=null){out.print(1);}else{out.print(0);}
	}else{// 2.2 删除收藏
		sMethodName="destroyFavorite";
		Boolean oResult = false;
		try{
			oResult = (Boolean)oProcessor.excute(sServiceId,sMethodName);
		}catch(Exception e){
			String sErrorMsg = e.getMessage();
			if(sErrorMsg.indexOf("[ERR-") >= 0 && sErrorMsg.indexOf("<--") >= 0 ){
				sErrorMsg = sErrorMsg.substring(sErrorMsg.indexOf("[ERR-"),sErrorMsg.indexOf("<--"));
			}
			out.print(sErrorMsg);
			return;
		}
		if(oResult){out.print(1);}else{out.print(0);}
	}
%>