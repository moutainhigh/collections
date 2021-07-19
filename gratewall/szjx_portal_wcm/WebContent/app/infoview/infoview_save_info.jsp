<%--
/** Title:			infoview_save_info.jsp
 *  Description:
 *		WCM5.2 自定义表单，保存表单基本属性信息的修改。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			fcr
 *  Created:		2006.06.12 20:36:04
 *  Vesion:			1.0
 *	Update Logs:
 *		2006.06.12	fcr created
 *
 *  Parameters:
 *		see infoview_save_info.xml
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%-- ----- WCM IMPORTS BEGIN ---------- --%>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.content.WCMObjHelper" %>
<%@ page import="com.trs.components.infoview.persistent.InfoView" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViews" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewView" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewViews" %>
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

	int		iInfoViewId	= currRequestHelper.getInt("InfoViewId", 0);
	String	sObjectXML	= currRequestHelper.getString("ObjectXML");
	InfoView aInfoView = null;
	try
	{
		aInfoView = (InfoView) WCMObjHelper.toWCMObj(sObjectXML, loginUser, iInfoViewId, InfoView.class);
	}
	catch(Exception ex)
	{
		throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, LocaleServer.getString("infoview_save_info.attribute.wrong","从XML构造自定义表单时因属性值不正确而失败！"), ex);
	}

//2.业务代码
	if(aInfoView.isAddMode()){
		IInfoViewService oInfoViewService = (IInfoViewService) DreamFactory.createObjectById("IInfoViewService");
		oInfoViewService.save(aInfoView);
	}else{
		if (!aInfoView.canEdit(loginUser)) {
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTLOCKED,
					CMyString.format(LocaleServer.getString("infoview.save.info.lock","对象正被其他用户锁定，无法修改!当前用户:[{0}] 锁定用户:[{1}]"),new String[]{loginUser.getName(),aInfoView.getLockerUserName()}));
		}
		aInfoView.save(loginUser);
	}
//3.结束
	out.clear();
%>
<SCRIPT LANGUAGE="JavaScript">
if(window.opener) {
	window.opener.CTRSAction_refreshMe();
	window.opener.focus();
	window.close();
} else {
	window.returnValue = true;
	window.close();
}
</SCRIPT>