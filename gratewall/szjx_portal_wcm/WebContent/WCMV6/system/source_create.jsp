<%
/** Title:			template_parser.jsp
 *  Description:
 *		WCM6 操作产生器
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			CH
 *  Created:		2006-11-21 18:16 上午
 *  Vesion:			1.0
 *  Last EditTime:	
 *	Update Logs:
 *
 *  Parameters:
 *		
 *
 */
%>

<%@ page contentType="text/plain;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
<%@ page import="java.util.StringTokenizer" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.resource.Source" %>
<%@ page import="com.trs.components.wcm.resource.Sources" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<!------- WCM IMPORTS END ------------>


<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%
out.clear();
WCMFilter filter = new WCMFilter("", "", "SourceOrder Desc",
                Source.DB_ID_NAME);
Sources sources = Sources.openWCMObjs(loginUser, filter);
ConfigServer oConfigServer = ConfigServer.getServer();
String sCanNewDocSource = oConfigServer.getSysConfigValue("ENABLE_SOURCE_EDIT", "true");
boolean bCanNewDocSource = "true".equalsIgnoreCase(sCanNewDocSource)||"1".equals(sCanNewDocSource);
out.println("$package('com.trs.wcm');");
out.println("com.trs.wcm.CanEditDocSource = "+bCanNewDocSource+";");
out.println("com.trs.wcm.AllDocSource = [");
boolean bFirst = true;
for (int i = 0, nSize = sources.size(); i < nSize; i++) {
	Source source = (Source) sources.getAt(i);
	if (source == null)
		continue;
	if(!bFirst){
		out.println(",");
	}
	bFirst = false;
	out.println("{");
	out.println("value:\"" + source.getId()+"\",");
	out.println("label:\"" + CMyString.filterForJs(source.getName())+"\",");
	out.println("title:\"" + CMyString.filterForJs(source.getDesc())+"\"");
	out.println("}");
}
out.println("];");
%>