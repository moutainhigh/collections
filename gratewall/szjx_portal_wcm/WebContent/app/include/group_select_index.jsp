<%
/** Title:			user_select_index.jsp
 *  Description:
 *		标准WCM5.2 用户选择首页
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WCM
 *  Created:		2004-12-26 20:17
 *  Vesion:			1.0
 *  Last EditTime:	2004-12-26/2004-12-26
 *	Update Logs:
 *		CH@2004-12-11 created the file 
 *
 *  Parameters:
 *		see user_select_index.jsp
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>

<%
//4.初始化(获取数据)	
	int nTransferName = currRequestHelper.getInt("TransferName", 0);
	int nTreeType = currRequestHelper.getInt("TreeType", 0);
	boolean bTransferName	= currRequestHelper.getInt("TransferName", 0) > 0;
%>


<HTML>
<HEAD>
  <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <TITLE WCMAnt:param="group_select_index.jsp.title">TRS WCM 用户选择首页::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
</HEAD>
<body style="padding:0px;margin:0px;overflow:hidden;">
<table border="0" cellspacing="0" cellpadding="0" style="width:100%;height:100%;table-layout:fixed;">
<tbody>
	<tr>
		<td style="width:150px;" >
			<iframe src="group_select_left.jsp?TreeType=<%=nTreeType%>" name="left" width="100%" height="100%" frameborder="no" border="0"  scrolling="no"></iframe>
		</td>
		<td>
			<iframe src="group_select_list.jsp?TransferName=<%=nTransferName%>" name="main" frameborder="no" border="0" width="100%" height="100%"  scrolling="no"></iframe>
		</td>
	</tr>
</tbody>
</table>	
<script>
function getMainWin(){
	return window.main;
}

function getParentWin(){
	return window.parent;
}
</script>
</body>
</HTML>