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
<%@ page import="com.trs.wcm.trsserver.task.persistent.SearchTask" %>
<%@ page import="com.trs.wcm.trsserver.task.persistent.SearchTasks" %>
<%@ page import="com.trs.wcm.trsserver.task.persistent.TRSGateway" %>
<%@ page import="java.util.Properties" %>
<%@ page import="com.trs.gateway.client.GWConnection" %>
<%@ page import="com.trs.gateway.client.GWManager" %>
<%@ page import="com.trs.gateway.client.GWConstants" %>
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
	String sTaskIds = currRequestHelper.getString("taskIds");

//5.权限校验

//6.业务代码
	SearchTasks currSearchTasks = SearchTasks.findByIds(loginUser, sTaskIds);
	SearchTask task = null;
	for(int i=0,nSize = currSearchTasks.size();i<nSize;i++){
		task = (SearchTask) currSearchTasks.getAt(i);
		if(task == null) continue;
		//删除GateWay下的任务
		deleteGWTask(task.getTRSGWTaskName(),task.getTRSGWId());
	}
	currSearchTasks.removeAll(true);
	

//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="logo_delete.jsp.trswcmdeletesearchtask">TRS WCM 删除检索任务</TITLE>
<LINK href="../../style/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/data/locale/wcm52.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSHashtable.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSRequestParam.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSAction.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSHTMLTr.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSHTMLElement.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../js/wcm52/CTRSButton.js"></SCRIPT>
<%=currRequestHelper.toTRSRequestParam()%>
</HEAD>

<BODY>
<SCRIPT LANGUAGE="JavaScript">
top.returnValue = true;
top.close();
</SCRIPT>
</BODY>
</HTML>
<%!
	private void deleteGWTask(String sTaskName, int nGwId) throws WCMException {
		 try {
			TRSGateway gateway = TRSGateway.findById(nGwId);
			Properties properties = new Properties();
            // 网关连接参数
            properties.setProperty(GWConstants.GW_HOST, gateway.getIP());
            properties.setProperty(GWConstants.GW_PORT,
                    String.valueOf(gateway.getPort()));
            properties.setProperty(GWConstants.GW_USERNAME,
                    gateway.getUserName());
            properties.setProperty(GWConstants.GW_PASSWORD,
                    gateway.getPassword());
            GWConnection gwconnection = null;
            gwconnection = GWManager.getConnection(properties);
			gwconnection.deleteTask(sTaskName);
		 }catch(Exception ex){
		 }
	}
%>