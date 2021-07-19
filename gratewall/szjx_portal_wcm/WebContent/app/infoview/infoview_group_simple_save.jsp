<%--
/** Title:			infoview_save_view.jsp
 *  Description:
 *		WCM5.2 自定义表单，保存表单视图信息的修改。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			fcr
 *  Created:		2006.06.12 20:36:04
 *  Vesion:			1.0
 *	Update Logs:
 *		2006.06.12	fcr created
 *
 *  Parameters:
 *		see infoview_save_view.xml
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
	int nInfoViewId = currRequestHelper.getInt("InfoViewId", 0);
	if (nInfoViewId <= 0){
		throw new WCMException(LocaleServer.getString("simple_save.id.zero","InfoViewId为0，无法找到InfoView！"));
	}
	InfoView oInfoView = InfoView.findById(nInfoViewId);
	if (oInfoView == null){
		throw new WCMException(LocaleServer.getString("simple_save.obj.not.found","无法找到InfoView！"));
	}
	IInfoViewService oInfoViewService = (IInfoViewService) DreamFactory.createObjectById("IInfoViewService");

	String	sGroupName	= currRequestHelper.getString("IVGroupName");
	boolean	bPublicFill	= currRequestHelper.getBoolean("PublicFill", false);

	InfoViewGroups oInfoViewGroups = InfoViewGroups.findByName(sGroupName, nInfoViewId);
	for(int i=0;i<oInfoViewGroups.size();i++){
		//2.业务代码
		InfoViewGroup aGroup = (InfoViewGroup)oInfoViewGroups.getAt(i);
		aGroup.setPublicFill(bPublicFill);
		oInfoViewService.save(aGroup);
	}
	out.clear();
%>