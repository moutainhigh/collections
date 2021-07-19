<%--
/** Title:			infoview_uselist.jsp
 *  Description:
 *		WCM5.2 自定义表单的察看使用情况的ID页面。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			fcr
 *  Created:		2006.04.12 20:36:04
 *  Vesion:			1.0
 *	Update Logs:
 *		2006.04.12	fcr created
 *
 *  Parameters:
 *		see infoview_uselist.xml
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%-- ----- WCM IMPORTS BEGIN ---------- --%>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.components.infoview.persistent.InfoView" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViews" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.service.IInfoViewService" %>
<%-- ------- WCM IMPORTS END ---------- --%>

<%-- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --%>
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//1.初始化(获取数据)
//	int nSiteId    = currRequestHelper.getInt("SiteId",    0);
//	if (nSiteId == 0)
//	{
//		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "传入站点ID错误，必须传入一个有效的站点ID！");
//	}
//	WebSite oWebSite = (WebSite) WebSite.findById(nSiteId);
//	if (oWebSite == null)
//	{
//		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "没有找到ID为" + nSiteId + "]的站点");
//	}
	int iInfoViewId = currRequestHelper.getInt("InfoViewId", 0);

//2.权限校验

//3.业务代码
	IInfoViewService currService = ServiceHelper.createInfoViewService();
	InfoView aInfoView = currService.findById(iInfoViewId);
	List useList = currService.getInfoViewEmployers(iInfoViewId);
	int nEmployerSize = 0;
	for(int k=0; k<useList.size(); k++){
		Channel	aChannel = (Channel) useList.get(k);
		if(aChannel == null){
			continue;
		}
		if(aChannel.getStatus() < 0){
			continue;
		}
		nEmployerSize ++;
	}

//3.结束
	out.clear();
%>
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM <%= LocaleServer.getString("infoview.label.title.uselist", "查看自定义表单的使用") %>:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
</HEAD>
<style type="text/css">
html,body,iframe{font-size:12px;border:0;background:transparent;}
td{font-size:12px;}
</style>
<script language="javascript">
<!-- 
	function onOk(){
		window.close();
	}
//-->
</script>
<BODY>
<div style="overflow:auto;height:100%;width:100%">
	<%
		String sParam1 = "<span style='color:red'>"+nEmployerSize+"</span>";
	%>
	<div style="height:40px">
		<span style="width:90%;color:blue;font-size:14px;padding-top:8px" WCMAnt:param="infoview_uselist.jsp.recordtotal">总共<%=sParam1%>条记录。</span>
	</div>
	<table style="height:auto;width:100%;table-layout:fixed;">
<%
	int nNum = 0;
	if (useList != null && useList.size() > 0){
		for(int i = 0; i < useList.size(); i++){
			Channel	aChannel = (Channel) useList.get(i);
			if(aChannel == null){
				continue;
			}
			if(aChannel.getStatus() < 0){
				continue;
			}
			if(nNum%2==0){
				out.println("<tr>");
			}
			nNum++;
%>
			<td style="line-height:20px;">
	<span style="float:left;"><%=(nNum)%>.</span><span style="float:left;padding-left:5px; width:150px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;" title="<%= aChannel.getName() %>[ID=<%= aChannel.getId() %>]"><%= aChannel.getDesc() %></span></td>
<%
			if(nNum%2==0){
				out.println("</tr>");
			}
		}

%>
<%
	}else{
		LocaleServer.getString("infoview.label.uselist.nouse", "该表单没有使用");
	}
%>
</BODY>
</HTML>