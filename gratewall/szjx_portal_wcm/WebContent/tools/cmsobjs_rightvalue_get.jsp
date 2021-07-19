<%
/** Title:			cmsobjs_rightvalue_get.jsp
 *  Description:
 *		WCM5.2 获取对象的权限值
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WSW
 *  Created:		2004/12/15
 *  Vesion:			1.0
 *  Last EditTime:	
 *	Update Logs:
 *
 *  Parameters:
 *		see cmsobjs_rightvalue_get.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="java.util.StringTokenizer" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<%@ page import="com.trs.infra.persistent.BaseObj" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<!------- WCM IMPORTS END ------------>


<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
	int nObjType = currRequestHelper.getInt("ObjType", 0);
	String sObjIds = CMyString.showNull(currRequestHelper.getString("ObjIds"));
	String sRightIndexes = currRequestHelper.getString("RightIndexes");

	StringTokenizer st = new StringTokenizer(sObjIds, ",");

	int nObjId = 0;
	String sRightValue = "";
	CMSObj currObj = null;

	out.clear();
%><?xml version="1.0" encoding="UTF-8"?>
<WCMOBJS>
<%
	while(st.hasMoreTokens()){
		nObjId = Integer.parseInt(st.nextToken());
		currObj = (CMSObj)BaseObj.findById(nObjType, nObjId);
		if(currObj == null){
			sRightValue = "0";
		} else {
			if(loginUser.equals(currObj.getCrUser()) || loginUser.isAdministrator()){
				//如果是CrUser或者如果是管理员，则64位权限全部置为1
				sRightValue = "1";
			} else {
				sRightValue = AuthServer.getLogicalRightValue(currObj, loginUser, CMyString.splitToInt(sRightIndexes, ","));
			}
		}
%>
	<WCMOBJ>
		<PROPERTIES>
			<OBJID><%=nObjId%></OBJID>
			<RIGHTVALUE><%=sRightValue%></RIGHTVALUE>
		</PROPERTIES>
	</WCMOBJ>
<%
	}
%>
</WCMOBJS>