<%--
/** Title:			infoview_view_to_html.jsp
 *  Description:
 *		WCM5.2 自定义表单，转换自定义表单的视图为HTML页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			fcr
 *  Created:		2006.06.12 20:36:04
 *  Vesion:			1.0
 *	Update Logs:
 *		2006.06.12	fcr created
 *
 *  Parameters:
 *		see infoview_view_to_html.xml
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%-- ----- WCM IMPORTS BEGIN ---------- --%>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.components.infoview.persistent.InfoView" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViews" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewView" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewViews" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.service.IInfoViewService" %>
<%@ page import="java.io.File" %>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%-- ------- WCM IMPORTS END ---------- --%>

<%-- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --%>
<%@include file="../../include/public_server_openwin.jsp"%>
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
	int iViewId = currRequestHelper.getInt("IVViewId", 0);

//1.1校验获取的参数
	IInfoViewService currService = ServiceHelper.createInfoViewService();
	InfoView		oInfoView = currService.findById(iInfoViewId);
	InfoViewView	oThisView = currService.findViewById(iViewId);
	if (oInfoView == null)
	{
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,  CMyString.format(LocaleServer.getString("view_to_html.form.getFailed","获取ID为[{0}]的自定义表单失败！"),new int[]{ iInfoViewId}));
	}
	if (oThisView == null)
	{
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("view_to_html.view.getFailed","获取ID为[{0}]的自定义表单视图失败！"),new int[]{iViewId}));
	}

//2.权限校验

//3.业务代码

//3.结束
	out.clear();
	String sOutput = "";
	int nFlowDocId = currRequestHelper.getInt("FlowDocId", 0);
	boolean bIsAddEdit = currRequestHelper.getBoolean("isAddEdit", false);
	if(nFlowDocId>0){
		sOutput = currService.forFillOutInFlow(oInfoView, oThisView, nFlowDocId);
	}
	else{
		if(bIsAddEdit){
			sOutput = currService.forFillOutInner(oInfoView, oThisView);
		}
		else{
			sOutput = currService.forFillOut(oInfoView, oThisView);
		}
	}
	int nHeadEndIndex = sOutput.indexOf("</head>");
	if(nHeadEndIndex<0)nHeadEndIndex=0;
	String sOutput1 = sOutput.substring(0, nHeadEndIndex);
	String sOutput2 = sOutput.substring(nHeadEndIndex);
	int nViewMode = currRequestHelper.getInt("ViewMode", 0);
%>
<%=sOutput1%>
<link href="infoview/infoview.css" rel="stylesheet" type="text/css" />
<%
	switch(nViewMode){
		case 0://infoview_view
%>
<%
			break;
		case 1://infoview_setting
%>
<link href="../../app/js/easyversion/resource/crashboard.css" rel="stylesheet" type="text/css" />
<link href="infoview/infoview_setting.css" rel="stylesheet" type="text/css" />
		<%
			break;
		case 2://infoview_setting_inflow
		%>
<link href="../../app/js/easyversion/resource/crashboard.css" rel="stylesheet" type="text/css" />
<link href="infoview/infoview_setting.css" rel="stylesheet" type="text/css" />
		<%
			break;
		case 3://document_view
		%>
			<link href="infoview/document_view.css" rel="stylesheet" type="text/css" />
		<%
			break;
		case 4://document_addedit
		%>
<link href="infoview/document_addedit.css" rel="stylesheet" type="text/css" />
		<%
			break;
		case 5://document_addedit_inflow
		%>
<link href="../../app/js/easyversion/resource/crashboard.css" rel="stylesheet" type="text/css" />
<link href="infoview/document_addedit_inflow.css" rel="stylesheet" type="text/css" />
		<%
			break;
	}
%>
<%=sOutput2%>
<%
	switch(nViewMode){
		case 0://infoview_view
		%>
<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/data/locale/infoview.js"></script>
<script src="../../app/js/easyversion/position.js"></script>
<script src="infoview/infoview_view.js"></script>
<link href="infoview/infoview_setting.css" rel="stylesheet" type="text/css" />
		<%
			break;
		case 1://infoview_setting
		%>
<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/data/locale/infoview.js"></script>
<script src="../../app/js/easyversion/crashboard.js"></script>
<script src="../../app/js/easyversion/bubblepanel.js"></script>
<script src="../../app/js/easyversion/position.js"></script>
<script src="infoview/infoview_setting.js"></script>
		<%
			break;
		case 2://infoview_setting_inflow
		%>
<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/data/locale/infoview.js"></script>
<script src="../../app/js/easyversion/crashboard.js"></script>
<script src="../../app/js/easyversion/bubblepanel.js"></script>
<script src="../../app/js/easyversion/position.js"></script>
<script src="infoview/infoview_setting_inflow.js"></script>
		<%
			break;
		case 3://document_view
		%>
<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/data/locale/infoview.js"></script>
<script src="../../app/js/easyversion/xmlhelper.js"></script>
<script src="infoview/document_view.js"></script>
<script src="infoview/infoview_xmldatahelper.js"></script>
		<%
			String wcmAppPath = CMyString.setStrEndWith(application.getRealPath("/"), File.separatorChar);
			String sRelationJsViewFilePath = wcmAppPath + ("app/infoview/infoview/custom_view_" + iInfoViewId + ".js").replace('/', File.separatorChar);
			if(CMyFile.fileExists(sRelationJsViewFilePath)){
				String sIncludeViewFilePath = ("infoview/custom_view_" + iInfoViewId + ".js").replace('/', File.separatorChar);
		%>
<script src="<%=sIncludeViewFilePath%>"></script>
		<%
			}
		%>
		<%
			break;
		case 4://document_addedit
		%>
<script src="../../app/js/easyversion/cssrender.js"></script>
<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/data/locale/infoview.js"></script>
<script src="../../app/js/easyversion/bubblepanel.js"></script>
<script src="../../app/js/easyversion/position.js"></script>
<script src="../../app/js/easyversion/calendar_lang/cn.js" WCMAnt:locale="../../app/js/easyversion/calendar_lang/$locale$.js"></script>
<script src="../../app/js/easyversion/calendar3.js"></script>
<script src="../../app/js/easyversion/xmlhelper.js"></script>
<script src="infoview/document_addedit.js"></script>
<script src="infoview/infoview_elehelper.js"></script>
<script src="infoview/infoview_xmldatahelper.js"></script>
<script src="infoview/infoview_expression.js"></script>
<script src="infoview/infoview_validator.js"></script>
		<%
			String wcmPath = CMyString.setStrEndWith(application.getRealPath("/"), File.separatorChar);
			String sRelationJsFilePath = wcmPath + ("app/infoview/infoview/custom_" + iInfoViewId + ".js").replace('/', File.separatorChar);
			if(CMyFile.fileExists(sRelationJsFilePath)){
				String sIncludeFilePath = ("infoview/custom_" + iInfoViewId + ".js").replace('/', File.separatorChar);
		%>
<script src="<%=sIncludeFilePath%>"></script>
		<%
			}
		%>
		<%
			break;
		case 5://document_addedit_inflow
		%>
<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/data/locale/infoview.js"></script>
<script src="../../app/js/easyversion/crashboard.js"></script>
<script src="../../app/js/easyversion/bubblepanel.js"></script>
<script src="../../app/js/easyversion/calendar_lang/cn.js" WCMAnt:locale="../../app/js/easyversion/calendar_lang/$locale$.js"></script>
<script src="../../app/js/easyversion/calendar.js"></script>
<script src="infoview/document_addedit_inflow.js"></script>
		<%
			break;
	}
%>