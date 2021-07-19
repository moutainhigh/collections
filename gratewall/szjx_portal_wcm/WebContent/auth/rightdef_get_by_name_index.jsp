<%
/** Title:			channels_get.jsp
 *  Description:
 *		标准WCM5.2 页面，用于“获取栏目列表”。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WCM
 *  Created:		2004-12-11 16:12
 *  Vesion:			1.0
 *  Last EditTime:	2004-12-11/2004-12-11
 *	Update Logs:
 *		CH@2004-12-11 created the file 
 *
 *  Parameters:
 *		see channels_get.jsp
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.persistent.RightDef" %>
<%@ page import="com.trs.cms.auth.persistent.RightDefs" %>
<%@ page import="com.trs.infra.persistent.ObjToXmlConverter" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化（获取数据）
	int nObjType = currRequestHelper.getInt("ObjType", 0);
	String strRightName = currRequestHelper.getString("RightName");
	int nRightIndex = currRequestHelper.getInt("RightIndex", -100);
	if(strRightName == null && nRightIndex == -100 ) {
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID , "传入参数无效！");
	}
	String sXMLInfo = "";

//5.权限校验

//6.业务代码
	String strWhere = "OBJTYPE=" + nObjType;
	if(strRightName != null) {
		strWhere += " AND RIGHTNAME='"+strRightName+"'";
	}
	if(nRightIndex != -100) {
		strWhere += " AND RIGHTINDEX="+nRightIndex;
	}

	WCMFilter filter = new WCMFilter("", strWhere, null);
	RightDefs currRightDefs = RightDefs.openWCMObjs(loginUser, filter);

	if(currRightDefs != null && currRightDefs.size() > 0){
		RightDef currRightDef = (RightDef)currRightDefs.getAt(0);
		if(currRightDef != null) {
			ObjToXmlConverter aXMLConvert = new ObjToXmlConverter();
			sXMLInfo = aXMLConvert.toXMLString(currRightDef, null, "RightDefId");
		}
	}

//7.结束
	out.clear();
%>
<%=sXMLInfo%>