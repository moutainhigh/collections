<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	errorPage="../include/error.jsp"%>

<%@ page import="com.trs.components.wcm.MyPlugin" %>
<%@ page import="com.trs.infra.util.CMyBitsValue" %>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../app/infoview/infoview_config.jsp"%>

<%
	//获取表单选件是否已经部署并可用(在文件../WCMV6/infoview/infoview_config.jsp中定义)
	String sRedirectUrl = "";
	if (bIsInfoviewEnable) {
		sRedirectUrl = "../app/infoview/infoview_list.jsp";
		String sNeedRedirect = request.getParameter("redirectable");
		if (sNeedRedirect == null || !sNeedRedirect.trim().equalsIgnoreCase("false")){
			currRequestHelper.getResponse().sendRedirect(sRedirectUrl);
		}
	}

	int nConfigId = 0;
	if(bRegisterForInfoview && currConfig != null) {
		nConfigId = currConfig.getId();
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TRS WCM 5.2
扩展功能介绍页面-表单选件::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<link href="../style/style.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
	margin-left: 5px;
	margin-top: 5px;
	margin-right: 5px;
	margin-bottom: 5px;
}
-->
</style>
<!-- WCM按钮的style-->
<style>
.button_table_2{
	height: 16px;
	font-size:9px;
	cursor:hand;
}
.button_table_diable_2{
	height: 16px;
	font-size:9px;
	color:#999999;
}
</style>
<%@include file="../include/public_client_list.jsp"%>
<script language="javascript">
<!--
	function startup(_nConfigId, _bDisabled){
		if(!(_nConfigId > 0)) {
			alert('您没有购买或部署该选件！');
			return;
		}
		var oTRSAction = new CTRSAction("../integrate_installed/entry_switch_dowith.jsp");
		oTRSAction.setParameter("EntryConfigId", _nConfigId);
		oTRSAction.setParameter("Enable", (_bDisabled ? 0: 1));		
		var bResult = oTRSAction.doDialogAction(500, 200);
		if(bResult){
			CTRSAction_refreshMe();
		}
	}
	function closeit(_nConfigId){
		startup(_nConfigId, true);
	}
//-->
</script>
</head>

<body>
<table width="100%" border="0" cellspacing="4" cellpadding="0">
	<tr>
		<td>
		<table width="100%" border="0" cellpadding="0" cellspacing="1"
			bgcolor="A6A6A6">
			<tr>
				<td height="26" background="../images/tdbg.jpg">
				<table width="100%" height="26" border="0" cellpadding="0"
					cellspacing="0">
					<tr>
						<td width="24"><img src="../images/bite-blue-open.gif" width="24"
							height="24"></td>
						<td>表单选件简介</td>
						<td width="15">&nbsp;</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td valign="top" style="background-color: #fff; padding: 8px;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td height="20">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;TRS WCM表单选件通过与TRS WCM产品的内容管理和流程控制功能结合使用，在帮助用户实现填报表格电子化的同时，简化和缩短公司内部事务审批的办理流程，是知识管理系统一个不可或缺的重要功能。
<BR><BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在政府互动方面，表单选件可以广泛适用于政府门户网站的网上咨询、在线投诉、领导信箱、网上审批等应用场景，增强政府的网上办事能力，从而有效体现政府信息化程度。
<BR>
						<BR>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<%
						if(bIsInfoviewEnable) {
						%>
							<font color="green">该选件已启用</font>
						<%
						}else{
						%>
							<font color="red"><%=(nConfigId > 0) ? "您尚未启用该选件，请先执行[打开选件]操作。" : "您尚未购买或部署该选件"%></font>
						<%
						}
						%>
						
						
						</td>
					</tr>
					<tr>
						<td>
							<hr size="1" color="#cccccc">
						</td>
					</tr>
					<tr>
						<td>
							<table width="100%" border="0" cellpadding="0" cellspacing="1" class="list_table" style="margin-top: 10px; margin-bottom: 8px; width: 60%; display: <%=(nConfigId > 0) ? "" : "none"%>">
								<!--~-- ROW11 --~-->
								<tr bgcolor="#f5f5f5" class="list_th" style="height: 18px;">
									<td bgcolor="#f5f5f5" align="center" style="width: 120px">名称</td>
									<td bgcolor="#f5f5f5" align="center">操作</td>
								</tr>
								<tr class="list_tr">
									<td align="center" style="background-color:<%=bIsInfoviewEnable?"#f6f6cc":"#f5f5f5"%>">表单选件</td>
									<td align="center">
									<%
										if (!bIsInfoviewEnable){

									%>
										<span>
											<a href="#" onclick="javascript:startup(<%=nConfigId%>); return false;">打开选件</a>
										</span>
									<%
									
										}
										else{
									%>
										<span>
											<a href="#" onclick="javascript:closeit(<%=nConfigId%>); return false;">关闭选件</a>
										</span>
										<span>
											<a href="<%=sRedirectUrl%>">进入表单列表</a>
										</span>
									<%
										}
									%>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html>