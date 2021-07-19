<%--
/** Title:			infoview_save_group.jsp
 *  Description:
 *		WCM5.2 自定义表单，保存表单数据节的修改。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			fcr
 *  Created:		2006.06.12 20:36:04
 *  Vesion:			1.0
 *	Update Logs:
 *		2006.06.12	fcr created
 *
 *  Parameters:
 *		see infoview_save_group.xml
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%-- ----- WCM IMPORTS BEGIN ---------- --%>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.content.WCMObjHelper" %>
<%@ page import="com.trs.components.infoview.persistent.InfoView" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViews" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewGroup" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewGroups" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.service.IInfoViewService" %>
<%-- ----- WCM IMPORTS END ---------- --%>

<%-- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --%>
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
	int		iGroupId	= currRequestHelper.getInt("IVGroupId", 0);
	String	sObjectXML	= currRequestHelper.getString("ObjectXML");
	InfoViewGroup aGroup = null;
	try
	{
		aGroup = (InfoViewGroup) WCMObjHelper.toWCMObj(sObjectXML, loginUser, iGroupId, InfoViewGroup.class);
	}
	catch(Exception ex)
	{
		throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, LocaleServer.getString("infoview_save_group.attribute.wrong","从XML构造自定义表单视图时因属性值不正确而失败！"),ex);
	}

//2.业务代码
	IInfoViewService oInfoViewService = (IInfoViewService) DreamFactory.createObjectById("IInfoViewService");
	oInfoViewService.save(aGroup);

//3.结束
	out.clear();
%>

<!-- 没有输出 -->