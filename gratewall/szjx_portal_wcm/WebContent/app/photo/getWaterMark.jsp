<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.wcm.photo.impl.JMarkUtilX" %>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@include file="../include/public_server.jsp"%>
<%
	//接受页面参数
	String sWmpic = request.getParameter("WMPIC");
	String sPic = request.getParameter("PIC");
	if(sWmpic != null && sWmpic.trim().length() > 0){//加水印
		String sPos = request.getParameter("WMPOS");
		int[] arPos = {3};
		if(sPos != null){
			arPos = CMyString.splitToInt(sPos,",");
		}
		com.trs.wcm.photo.impl.JMarkUtilX.addWatermark(sPic,sWmpic,arPos);
	}
	out.clear();
	out.print(CMyFile.extractFileName(sPic));
%>