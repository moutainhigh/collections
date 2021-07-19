<%
/** Title:			template_parser.jsp
 *  Description:
 *		WCM5.2 模板语法检测
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WSW
 *  Created:		2004/12/15
 *  Vesion:			1.0
 *  Last EditTime:	
 *	Update Logs:
 *
 *  Parameters:
 *		see template_parser.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.components.common.publish.PublishConstants" %>
<%@ page import="com.trs.components.common.publish.domain.template.TemplateParseMgr" %>
<%@ page import="com.trs.components.common.publish.persistent.template.Template" %>
<%@ page import="com.trs.components.common.publish.persistent.template.Templates" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.util.Reports" %>
<%@ page import="com.trs.service.ITemplateService" %>
<!------- WCM IMPORTS END ------------>


<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
	int nSiteId = currRequestHelper.getInt("SiteId", 0);
	WebSite currWebSite = (WebSite)WebSite.findById(nSiteId);

	//5.权限校验
	if(!AuthServer.hasRight(loginUser, currWebSite, WCMRightTypes.TEMPLATE_CHECK)){
		throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT, "对不起！您没有权限在["+currWebSite+"]校验模板！");
	}


	TemplateParseMgr m_oTemplateParseMgr = (TemplateParseMgr) DreamFactory
			.createObjectById("TemplateParseMgr");

	Templates templates = null;
	//templates = Templates.openWCMObjs(loginUser, null);
	ITemplateService currTemplateService = (ITemplateService)DreamFactory.createObjectById("ITemplateService");
	templates = currTemplateService.getManagedTemplates(currWebSite, PublishConstants.TEMPLATE_TYPE_ANY, null, true);

	Reports aReports = new Reports("模板校验");
	int nTemplateCount = 0;
	for (int i = 0, nSize = templates.size(); i < nSize; i++) {
		Template template = (Template) templates.getAt(i);
		if (template == null)
			continue;

		nTemplateCount++;
		aReports.addReport(m_oTemplateParseMgr.checkTemplate(template));
	}

	if (nTemplateCount == 0)
	{
		aReports.addWarnedReport("当前站点下没有需要检验的模板！", null);
	}
	else
	{
		String	sTitle	= "共检查了[" + nTemplateCount + "]个模板！正确：["
				+ aReports.getSucessedReporter().size() + "]个！错误：["
				+ aReports.getFailedReporter().size() + "]个！";
		aReports.setTitle(sTitle);
	}

	currRequestHelper.handleOpReports("../tools/template_reports.jsp", aReports);
%>