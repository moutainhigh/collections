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
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.DebugTimer" %>
<%@ page import="com.trs.presentation.nav.TreeCreator" %>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%
    DebugTimer aTimer = new DebugTimer();
	aTimer.start();
%>

<%!boolean IS_DEBUG = false;%>
<%
//4.初始化（获取数据）	
	

//5.权限校验

//6.业务代码
	out.clear();
        

	TreeCreator creator = new TreeCreator(loginUser, currRequestHelper,
                out);
	int nType = currRequestHelper.getInt("Type", 0);
	switch(nType){
		case 0:
			creator.writeTopChildrenHTML();
			break;
		case 1:
			creator.writeHTMLOfAPath();
			break;
		case 2:
			creator.writeHTMLContainsChannels();
			break;
		default:
			break;
	}
	

//7.结束
	//out.clear();
%>

<%
	aTimer.stop();
	if(IS_DEBUG)System.out.println(CMyString.format(LocaleServer.getString("tree_html_creator.jsp.createtime", "构造用户[{0}]用户组树所用时间为[{1}]毫秒！"), new Object[]{loginUser.getName(),String.valueOf(aTimer.getTime())}));
%>