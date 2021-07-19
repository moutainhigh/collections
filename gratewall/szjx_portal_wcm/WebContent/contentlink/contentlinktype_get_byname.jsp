<%--
/** Title:			contentlinktype_get_byname.jsp
 *  Description:
 *		ContentLinkType列表页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WenYehui
 *  Created:		2006-12-11 14:09
 *  Vesion:			1.0
 *  Last EditTime:	2006-12-11 / 2006-12-11
 *	Update Logs:
 *		WenYehui@2006-12-11 产生此文件
 *
 *  Parameters:
 *		see contentlinktype_get_byname.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.persistent.ContentLinkType" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentLinkTypes" %>
<%@ page import="com.trs.infra.persistent.WCMFilter"%>
<%@ page import="com.trs.infra.util.CMyString"%>
<!------- WCM IMPORTS END ------------>

<%@include file="../include/public_server.jsp"%>

<%
	//if(!AuthServer.hasRight
	int nTypeId = currRequestHelper.getInt("TypeId",0);
	String sTypeName = currRequestHelper.getString("TypeName");
	
	WCMFilter filter = new WCMFilter("","","");

	String condition = "TypeName=?";
	filter.addSearchValues(0,sTypeName);

	if(nTypeId > 0){
		condition += " AND NOT " + ContentLinkType.DB_ID_NAME + "=?";
		filter.addSearchValues(1,nTypeId);
	}

	filter.setWhere(condition);

	ContentLinkTypes types = new ContentLinkTypes(loginUser);
	types.open(filter);

	out.clear();
	out.println("{Result:"+!types.isEmpty()+"}");
	if(true){
		types.clear();
		return;
	}
%>