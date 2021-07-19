<%
/** Title:			template_parser.jsp
 *  Description:
 *		WCM61 操作产生器
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

<%@ page contentType="text/plain;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
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
String SourceName = currRequestHelper.getString("SourceName");
String sWhere = "SRCNAME like ?";
WCMFilter filter = new WCMFilter("", "", "SOURCEORDER DESC");
filter.addSearchValues('%' + SourceName + '%');
int nSiteId = currRequestHelper.getInt("SiteId", 0);
if(nSiteId != 0){
	sWhere += " and ( SiteId = ? or SiteId = 0 )";
	filter.addSearchValues(nSiteId);
}
filter.setWhere(sWhere);
Sources sources = Sources.openWCMObjs(loginUser, filter);
out.println("[");
boolean bFirst = true;
if(sources.size() > 0){
	for (int i = 0, nSize = sources.size(); i < nSize; i++) {
		Source source = (Source) sources.getAt(i);
		if (source == null)
			continue;
		if(!bFirst){
		out.println(",");
		}
		bFirst = false;
		out.println("{");
		out.println("title:\"" + CMyString.transJsDisplay(source.getName())+"\",");
		out.println("desc:\"" + CMyString.filterForJs(source.getDesc())+"\"");
		out.println("}");
	}
}
out.println("];");
%>