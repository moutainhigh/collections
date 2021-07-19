<%--
/** Title:			metaviewfieldgroup_delete.jsp
 *  Description:
 *		删除MetaViewFieldGroup的页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			TRS WCM 5.2
 *  Created:		2011-06-11 03:19:07
 *  Vesion:			1.0
 *  Last EditTime:	2011-06-11 / 2011-06-11
 *	Update Logs:
 *		TRS WCM 5.2@2011-06-11 产生此文件
 *
 *  Parameters:
 *		see metaviewfieldgroup_delete.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.metadata.definition.MetaViewFieldGroups" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化(获取数据)
	String sMetaViewFieldGroupIds = currRequestHelper.getString("MetaViewFieldGroupIds");

//5.权限校验

//6.业务代码
	/** TODO 建议改为向Service发出请求
	IMetaViewFieldGroupService currMetaViewFieldGroupService = (IMetaViewFieldGroupService)DreamFactory.createObjectById("IMetaViewFieldGroupService");
	currMetaViewFieldGroupService.delete(sMetaViewFieldGroupIds);
	**/
	MetaViewFieldGroups currMetaViewFieldGroups = MetaViewFieldGroups.findByIds(loginUser, sMetaViewFieldGroupIds);
	currMetaViewFieldGroups.removeAll(true);
	

//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="metaviewfieldgroup_delete.jsp.title">TRS WCM 删除MetaViewFieldGroup</TITLE>
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<%@include file="../include/public_client_addedit.jsp"%>
</HEAD>

<BODY>
<SCRIPT LANGUAGE="JavaScript">
top.returnValue = true;
top.close();
</SCRIPT>
</BODY>
</HTML>