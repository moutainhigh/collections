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

<%@ page contentType="text/xml;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%-- ----- WCM IMPORTS BEGIN ---------- --%>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.content.WCMObjHelper" %>
<%@ page import="com.trs.components.infoview.persistent.InfoView" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViews" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewField" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViewFields" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageHelper" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.service.IInfoViewService" %>
<%@ page import="java.util.ArrayList" %>
<%-- ----- WCM IMPORTS END ---------- --%>

<%-- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --%>
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
try{
//2.业务代码
	String sIVFieldIds = currRequestHelper.getString("IVFieldIds");
	String sObjectXML = currRequestHelper.getString("ObjectXML");
	int nInfoViewId = currRequestHelper.getInt("InfoViewId", 0);
	if (nInfoViewId <= 0){
		throw new WCMException(LocaleServer.getString("infoview_fieldattrs_dowith.id.zero","InfoViewId为0，无法找到InfoView！"));
	}
	InfoView currInfoView = InfoView.findById(nInfoViewId);
	if (currInfoView == null){
		throw new WCMException(LocaleServer.getString("infoview_fieldattrs_dowith.obj,ont.found","无法找到InfoView！"));
	}
	String[] aIVFieldIds = sIVFieldIds.split(",");
	InfoViewField oInfoViewField = null;
	ArrayList lstFields = new ArrayList();
	IInfoViewService oInfoViewService = (IInfoViewService) DreamFactory.createObjectById("IInfoViewService");
	for(int i=0;i<aIVFieldIds.length;i++){
		int nIVFieldId = 0;
		try{
			nIVFieldId = Integer.parseInt(aIVFieldIds[i]);
		}catch(Exception ex){
			nIVFieldId = 0;
			//Just Skip it.
		}
		if(nIVFieldId<=0)continue;
		try{
			oInfoViewField = (InfoViewField) WCMObjHelper.toWCMObj(sObjectXML, loginUser, nIVFieldId, InfoViewField.class);
			oInfoViewService.save(oInfoViewField);
		}catch(Exception ex){
			throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, LocaleServer.getString("infoview_fieldattrs_dowith.construct.failed","从XML构造自定义表单字段时因属性值不正确而失败！"), ex);
		}
		if (oInfoViewField != null) {
			lstFields.add(oInfoViewField);
		}
	}
	oInfoViewService.rebuildXSLTByFields(currInfoView, lstFields);
//	oInfoViewService.saveViewByFields(currInfoView, lstFields);
//3.结束
	out.clear();
}catch(Throwable th){
	out.clear();
	response.setHeader("TRSException", "true");
%><fault><code><![CDATA[5000]]></code><message><![CDATA[<%=th.getMessage()%>]]></message><detail><![CDATA[<%=com.trs.infra.util.CMyException.getStackTraceText(th)%>]]></detail><suggestion><![CDATA[]]></suggestion></fault><%}%>