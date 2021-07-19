<%
/** Title:			template_preview.jsp
 *  Description:
 *		WCM5.2 模板预览页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WSW
 *  Created:		2004/12/15
 *  Vesion:			1.0
 *  Last EditTime:	
 *	Update Logs:
 *
 *  Parameters:
 *		see template_preview.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="java.net.URL" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.common.publish.persistent.template.Template" %>
<%@ page import="com.trs.service.ITemplateService" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<!------- WCM IMPORTS END ------------>


<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server_openwin.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化（获取数据）
	int nTempId = currRequestHelper.getInt("TempId", 0);
	Template currTemplate = Template.findById(nTempId);
	if(currTemplate==null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("object.not.found_a", "没有找到指定ID为[{0}]的对象!"), new int[]{nTempId}));
	}
	response.setContentType("text/html;charset=" + currTemplate.getRoot().getEncoding());
//5.权限校验
	
//6.业务代码
/*
	URL currUrl = new URL(currRequestHelper.getRequest().getRequestURL().toString());
	StringBuffer sbServerPath = new StringBuffer();
	sbServerPath.append(currUrl.getProtocol());
	sbServerPath.append("://");
	sbServerPath.append(currUrl.getHost());
	sbServerPath.append(":");
	sbServerPath.append(currUrl.getPort());
	sbServerPath.append("/");
*/
	String sPreviewSrc = "";
	try{
		ITemplateService currTemplateService = (ITemplateService)DreamFactory.createObjectById("ITemplateService");
		//sPreviewSrc = currTemplateService.preview(currTemplate, sbServerPath.toString());
		sPreviewSrc = currTemplateService.preview(currTemplate);
	} catch(Exception ex){
		throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, LocaleServer.getString("template_addedit_label_5","模板预览失败!"), ex);
	}
//7.结束
	out.clear();%><%out.println(sPreviewSrc);%>