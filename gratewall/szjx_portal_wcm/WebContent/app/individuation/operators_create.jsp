<%
/** Title:			template_parser.jsp
 *  Description:
 *		WCM65 操作产生器
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Last EditTime:	
 *	Update Logs:
 *
 *  Parameters:
 *		
 *
 */
%>

<%@ page contentType="text/javascript;charset=UTF-8" pageEncoding="utf-8" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.individuation.Individuations" %>
<%@ page import="com.trs.components.wcm.individuation.Individuation" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<!------- WCM IMPORTS END ------------>


<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%
out.clear();
String oprType = currRequestHelper.getString("oprtype");
String where = "userId=? and paramName=? and paramValue=? ";
WCMFilter filter = new WCMFilter("", where, "paramValue", "paramValue,objectIdsValue");
filter.addSearchValues(loginUser.getId());
filter.addSearchValues("operator");
filter.addSearchValues(oprType);
Individuations individuations = Individuations.openWCMObjs(null, filter);	

if(individuations.size() <= 0) {
	out.println("");
}else{
	Individuation individuation = (Individuation) individuations.getAt(0);
	if(individuation != null)
		out.println(individuation.getObjectIdsValue());
}
%>