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
<%@ page import="com.trs.components.infoview.persistent.InfoViewField" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewFields" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="java.util.ArrayList" %>
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

	String	sPropertyName	= currRequestHelper.getString("PropertyName");
	String	sOutlineFields	= currRequestHelper.getString("OutlineFields");
	String	sSearchFields	= currRequestHelper.getString("SearchFields");
	String	sDocContentPattern	= currRequestHelper.getString("DocContentPattern");
	String	sOrderField	= currRequestHelper.getString("OrderField");

	if("OutlineFields".equalsIgnoreCase(sPropertyName)){
		oInfoView.setOutlineFields(sOutlineFields);
	}
	else  if("SearchFields".equalsIgnoreCase(sPropertyName)){
		oInfoView.setSearchFields(sSearchFields);
		
		//将是否是检索字段设置在infoviewField表中
		InfoViewFields oInfoFields = new InfoViewFields(loginUser);
		String[] aSearchField = sSearchFields.split(",");
		ArrayList aInfoViewSearchField = new ArrayList();
		for (int i = 0; i < aSearchField.length; i++) {
			String[] sInfoViewSearchField = aSearchField[i].split(":");
			if (sInfoViewSearchField.length == 1)
				continue;
			aInfoViewSearchField.add(sInfoViewSearchField[1]);
		}
		WCMFilter oFilter = new WCMFilter();
		oFilter.setFrom("WcmInfoviewField");
		oFilter.setWhere("infoviewid=?");
		oFilter.addSearchValues(0, nInfoViewId);
		oInfoFields.open(oFilter);
		for (int k = 0; k < oInfoFields.size(); k++) {
			InfoViewField oInfoField = (InfoViewField) oInfoFields.getAt(k);
			if(!aInfoViewSearchField.contains(oInfoField.getName())){
				oInfoField.setSearchField(false);
			}
			else oInfoField.setSearchField(true);
			oInfoField.save();
		}
	}
	else  if("DocContentPattern".equalsIgnoreCase(sPropertyName)){
		oInfoView.setDocContentPattern(sDocContentPattern);
	}else if("OrderField".equalsIgnoreCase(sPropertyName)){
		oInfoView.setOrderField(sOrderField);
	}
//2.业务代码
	oInfoView.save(loginUser);
	out.clear();
%>