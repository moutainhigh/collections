<%
/** Title:			template_check.jsp
 *  Description:
 *		WCM5.2 模板语法检测
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WSW
 *  Created:		2004/12/15
 *  Vesion:			1.0
 *  Last EditTime:	
 *	Update Logs:
 *
 *  Parameters:
 *		see template_check.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.common.publish.persistent.template.Template" %>
<%@ page import="com.trs.infra.util.Report" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.service.ITemplateParseService" %>
<%@ page import="com.trs.service.ServiceHelper" %>
<!------- WCM IMPORTS END ------------>


<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
	int nTemplateId = currRequestHelper.getInt("TemplateId", 0);
	int nRootType = currRequestHelper.getInt("RootType", 0);
	int nRootId = currRequestHelper.getInt("RootId", 0);
	String sContent = currRequestHelper.getString("Content");

	Template currTemplate = null;
	if(nTemplateId > 0){
		currTemplate = Template.findById(nTemplateId);
		if(currTemplate == null)
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "没有找到ID为["+nTemplateId+"]的模板！");
	} else {
		currTemplate = Template.createNewInstance();
		currTemplate.setRoot(nRootType, nRootId);
		currTemplate.setText(sContent);
	}

	ITemplateParseService m_oTemplateParseService = ServiceHelper.createTemplateParseService();

	Report report = m_oTemplateParseService.checkTemplate(currTemplate);
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 系统信息 ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<style>
.style5 {font-size: 18px}
</style>
<SCRIPT src="../js/CTRSRequestParam.js"></SCRIPT>
<SCRIPT src="../js/CTRSAction.js"></SCRIPT>
<SCRIPT>
function copyToClipboard(){
	var sDetailMsg = document.all("spMsg").innerText;
	window.clipboardData.setData("Text", sDetailMsg);
	CTRSAction_alert("已经复制到剪切板中！");
}

function stop(){
	window.returnValue = true;
	window.close();
}

function continueDo(){
	window.returnValue = false;
	window.close();
}

<%
if(report == null || report.getType() != Report.TYPE_ERROR){	
	out.println("window.close();\n");
}
%>

</SCRIPT>
</HEAD>

<BODY>
<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<TD height="25">
	<SCRIPT src="../js/CWCMDialogHead.js"></SCRIPT>
	<SCRIPT LANGUAGE="JavaScript">
		WCMDialogHead.draw("语法检测",true);
	</SCRIPT>
</TD>
</tr>
<TR>
<TD align="left" valign="top" class="tanchu_content_td">
	<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
	<TR>
	<TD>
		<TABLE width="98%" border="0" cellpadding="5" cellspacing="1" bgcolor="a6a6a6">
		<TR>
		<TD valign="top" bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" align="center" cellpadding="3" cellspacing="0">
			<TR>
			<TD width="100%">
				<TABLE width="100%" border="0" cellspacing="5" cellpadding="0">
				<TR>
					<TD width="60" valign=top><IMG src="../images/warning.gif" width="60" height="60"></TD>
					<TD width="10">&nbsp;</TD>
					<TD align=left><span class="font_bluebold style5">模板语法存在如下错误：</span><BR><BR>
					<TABLE>
					<TR>
						<TD><%=PageViewUtil.toHtml(report.getRportDetail())%></TD>
					</TR>
					</TABLE>
					</TD>
				</TR>
				</TABLE>
			</TD>
			</TR>
			</TABLE>
		</TD>
		</TR>
		</TABLE>
	</TD>
	</TR>
	<TR>
	<TD align="center" height=10>&nbsp;</TD>
	</TR>

	<TR>
	<TD align="center">
		<script src="../js/CTRSButton.js"></script>
		<script>
		//定义一个TYPE_ROMANTIC_BUTTON按钮
		var oTRSButtons = new CTRSButtons();
		
		oTRSButtons.cellSpacing = "0";
		oTRSButtons.nType = TYPE_ROMANTIC_BUTTON;

		oTRSButtons.addTRSButton("返回修改", "stop()");
		oTRSButtons.addTRSButton("忽略错误", "continueDo()");

		oTRSButtons.draw();
		</script>
	</TD>
	</TR>
	</TABLE>
</TD>
</TR>
</TABLE>
</BODY>
</HTML>