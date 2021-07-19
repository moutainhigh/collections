<%--
/** Title:			infoview_load_views.jsp
 *  Description:
 *		WCM5.2 自定义表单，获取指定表单的全部视图的信息。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			fcr
 *  Created:		2006.06.12 15:51
 *  Vesion:			1.0
 *	Update Logs:
 *		2006.06.12	fcr created
 *
 *  Parameters:
 *		see infoview_load_views.xml
 */
--%>

<%@ page contentType="text/xml;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%-- ----- WCM IMPORTS BEGIN ---------- --%>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.components.infoview.InfoViewMgr" %>
<%@ page import="com.trs.components.infoview.persistent.InfoView" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViews" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewView" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewViews" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.persistent.ObjToXmlConverter" %>
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
	InfoView aInfoView = null;
	int iInfoViewId = currRequestHelper.getInt("InfoViewId", 0);
	aInfoView = InfoView.findById(iInfoViewId);
	if (aInfoView == null)
	{
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,  CMyString.format(LocaleServer.getString("infoview_load_views.getFailed","获取ID为[{0}]的自定义表单失败！"),new int[]{iInfoViewId}));
	}

//2.权限校验

//3.业务代码
	String	sFieldList	= "IVVIEWID,INFOVIEWID,VIEWNAME,VIEWDESC,VIEWWIDTH,DEFAULTVIEW,SPECIALRIGHT,PUBLICFILL";
	InfoViewViews	allViews	= InfoViewViews.findBy(aInfoView);
	InfoViewMgr m_oInfoViewMgr = (InfoViewMgr) DreamFactory
			.createObjectById("InfoViewMgr");
	int nFlowDocId = currRequestHelper.getInt("FlowDocId", 0);
	if(nFlowDocId>0){
		allViews = m_oInfoViewMgr.filterByFlow(allViews, nFlowDocId);
	}
	ObjToXmlConverter aConvert	= new ObjToXmlConverter();

//清除已有的不必要输出
	out.clear();
%>
<%= aConvert.toXMLString(allViews, null, sFieldList) %>