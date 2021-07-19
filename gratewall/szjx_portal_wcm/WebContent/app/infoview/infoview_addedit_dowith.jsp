<%--
/** Title:			infoview_addedit_dowith.jsp
 *  Description:
 *		WCM5.2 自定义表单的编辑修改的处理文件。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			fcr
 *  Created:		2006.04.12 20:36:04
 *  Vesion:			1.0
 *	Update Logs:
 *		2006.04.12	fcr created
 *
 *  Parameters:
 *		see infoview_addedit_dowith.xml
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%-- ----- WCM IMPORTS BEGIN ---------- --%>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.content.WCMObjHelper" %>
<%@ page import="com.trs.components.infoview.persistent.InfoView" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViews" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.service.IInfoViewService" %>
<%-- ----- WCM IMPORTS END ---------- --%>
<%@include file="./infoview_public_include.jsp"%>
<%-- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --%>
<%@include file="../include/public_server.jsp"%> 
<%!boolean IS_DEBUG = false;%>

<%
	String sErrMsg = null;
//4.初始化(获取数据)
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

//2.业务代码
	int nSubmitType = currRequestHelper.getInt("SubmitType", 0);
	InfoView aInfoView = null;
	try
	{
		long start = System.currentTimeMillis();
		aInfoView = (InfoView) WCMObjHelper.toWCMObj(currRequestHelper.getString("ObjectXML"), loginUser, currRequestHelper.getInt("InfoViewId", 0), InfoView.class);
	}
	catch(Exception ex)
	{
        throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, LocaleServer.getString("infoview_addedit_dowith.jsp.label.fail2create_infoview", "从XML构造自定义表单时因属性值不正确而失败！"),ex);
	}
	try{
		IInfoViewService oInfoViewService = (IInfoViewService) DreamFactory.createObjectById("IInfoViewService");
		oInfoViewService.save(aInfoView);
	}catch(Exception ex)
	{
		sErrMsg = ex.getMessage();
		ex.printStackTrace();
	}
//3.结束
	
%>
<%if(sErrMsg != null){%>
{error: "<%=CMyString.filterForJs(sErrMsg)%>"}
<%}else{%>
{id:<%=aInfoView.getId()%>}
<%}%>