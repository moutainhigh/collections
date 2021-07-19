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

<%@ page contentType="text/javascript;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="java.util.StringTokenizer" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.components.wcm.resource.Statuses" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>

<!------- WCM IMPORTS END ------------>


<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%@include file="status_locale.jsp"%>
<%
out.clear();
%>
try{
<%
out.println("Ext.ns('com.trs.wcm');");
out.println("com.trs.wcm.AllDocStatus = [");
WCMFilter filter = new WCMFilter("", "", Status.DB_ID_NAME, Status.DB_ID_NAME);
Statuses statuses = Statuses.openWCMObjs(loginUser, filter);
boolean bFirst = true;
for (int i = 0, nSize = statuses.size(); i < nSize; i++) {
	Status status = (Status) statuses.getAt(i);
	if (status == null)
		continue;
	if(!bFirst){
		out.println(",");
	}
	bFirst = false;
	out.println("{");
	
	out.println("value:\"" + status.getId()+"\",");
	out.println("label:\"" + CMyString.filterForJs(getStatusLocale(status.getDisp()))+"\",");
	out.println("title:\"" + CMyString.filterForJs(getStatusLocale(status.getDesc()))+"\",");
	out.println("rightindex:\"" + status.getRightIndex()+"\",");
	out.println("type:\""+status.getType()+"\"");
	out.println("}");
}
out.println("];");
%>
}catch(error){
	//just skip it。
}