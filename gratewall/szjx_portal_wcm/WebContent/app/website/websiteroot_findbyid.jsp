<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.persistent.RightValue" %>
<%@ page import="com.trs.cms.auth.persistent.User" %>

<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
		String sRight = null;
		if (loginUser.isAdministrator())
			sRight = RightValue.getAdministratorValues();
		else
			sRight = "0";
		Object obj = findSiteTypeDesc(currRequestHelper.getInt("ObjectId", 0));
%>
<%!
	public static String findSiteTypeDesc(int _nSiteType) throws Throwable {
		switch (_nSiteType) {
		case 0:
			return LocaleServer.getString("websiteroot_findbyid.label.wordsitetype", "文字库");
		case 1:
			return LocaleServer.getString("websiteroot_findbyid.label.imgsitetype", "图片库");
		case 2:
			return LocaleServer.getString("websiteroot_findbyid.label.videositetype", "视频库");
		case 4:
			return LocaleServer.getString("websiteroot_findbyid.label.resource", "资源库");
		default:
			return LocaleServer.getString("websiteroot_findbyid.label.wordsitetype", "文字库");
		}
	}
%>
<%=obj%>