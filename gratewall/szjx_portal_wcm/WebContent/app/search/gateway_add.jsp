<%--
/** Title:			logo_delete.jsp
 *  Description:
 *		删除Logo的页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			TRS WCM 5.2
 *  Created:		2006-03-28 19:56:25
 *  Vesion:			1.0
 *  Last EditTime:	2006-03-28 / 2006-03-28
 *	Update Logs:
 *		TRS WCM 5.2@2006-03-28 产生此文件
 *
 *  Parameters:
 *		see logo_delete.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.wcm.trsserver.task.persistent.TRSGateway" %>
<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
	// 1 权限校验
	if(!loginUser.isAdministrator()){
		throw new WCMException("您没有权限操作检索配置！");
	}
//4.初始化(获取数据)
	String sServer = currRequestHelper.getString("TargetServer");
	int nPort = currRequestHelper.getInt("TargetPort",0);
	String sLoginUser = currRequestHelper.getString("LoginUser");
	String sLoginPassword = currRequestHelper.getString("LoginPassword");
	String sName = currRequestHelper.getString("sName");
//5.权限校验

//6.业务代码
	TRSGateway gateway = TRSGateway.createNewInstance();
	gateway.setName(sName);
	gateway.setIP(sServer);
	gateway.setPort(nPort);
	gateway.setUserName(sLoginUser);
	gateway.setPassword(sLoginPassword);
	gateway.save(loginUser);
//7.结束
	WCMFilter filter = new WCMFilter("XWCMTRSGATEWAY","gip = ? and gport = ?","","trsgatewayid");
	filter.addSearchValues(0,sServer);
	filter.addSearchValues(1,nPort);
	int nGatewayId = DBManager.getDBManager().sqlExecuteIntQuery(filter);
	out.clear();
	out.println(nGatewayId);
%>