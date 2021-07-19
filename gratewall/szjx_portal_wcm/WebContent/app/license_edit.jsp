<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.infra.util.CMyFile" %>
<%
	String sReferer = request.getHeader("referer");	
	if(CMyString.isEmpty(sReferer)) {
		out.clear();
		out.println("页面禁止直接访问.");
		return;
	}
	String sLicenseInfo = (String)session.getAttribute("sLicenseInfo");
	boolean bHiddenLicenseInfo = CMyString.isEmpty(sLicenseInfo) && sReferer.indexOf("dialog")!=-1;
		
	sLicenseInfo=!CMyString.isEmpty(sLicenseInfo)?sLicenseInfo:LocaleServer.getString("license_edit.jsp.label.licencesincorrect", "注册码不正确或者已过期，请到注册码文件中修改注册码！");
%>

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">    
	<title WCMAnt:param="trswcmmodifyregistercode">TRS WCM 修改注册码</title>
	<script src="../app/js/runtime/myext-debug.js"></script>
	<LINK href="../style/style.css" rel="stylesheet" type="text/css">
	<LINK href="../console/style/style.css" rel="stylesheet" type="text/css">
	<SCRIPT src="../js/CWCMDialogHead.js"></SCRIPT>
</head>
<body style="overflow:hidden">
<form NAME="frmData" ID="frmData" method="post">
    <TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
    <TR>
		<TD height="25">
			<SCRIPT LANGUAGE="JavaScript">
				//WCMDialogHead.draw("<%=LocaleServer.getString("user.label.addedit", "用户新建/修改")%>",true);
				WCMDialogHead.draw("修改注册码",true);
			</SCRIPT>	    
		</TD>
    </TR>	
	<TR>
		<TD style="padding-left:20px;height:30px;">
			<div style="color:red;font-size:14px;<%=bHiddenLicenseInfo?"display:none":""%>"><b WCMAnt:param="license_edit.jsp.warning">警告：</b><span><%=CMyString.transDisplay(sLicenseInfo)%></span>
			<a href="license_show.jsp">查看详细</a>
			</div>
		</TD>
	</TR>
	<TR>
		<TD style="padding-left:20px;height:30px">
			<b><span WCMAnt:param="machinecode">机器码：</span></b><span style="width:150px;font-family:COURIER;font-color:gray" ><%=getMachineCode()%></span>
		</TD>
	</TR>
	<TR>
		<TD style="padding-left:20px;height:30px">
			<b><span WCMAnt:param="">注册码文件：</span></b><span style="width:150px;font-family:COURIER;font-color:gray" >WCM应用目录/WEB-INF/classes/license/LICENSE.trswcm</span>
		</TD>
	</TR>
	</TABLE>
	<div align="center" style="padding-top:80px" >
		<SCRIPT SRC="../console/js/CTRSButton.js"></SCRIPT>
		<SCRIPT>
			function submitData(){
				if(window.top) {
					window.top.close();
				} else {
					window.close();
				}
			}
			//定义一个TYPE_ROMANTIC_BUTTON按钮
			var oTRSButtons = new CTRSButtons();
			
			oTRSButtons.cellSpacing = "0";
			oTRSButtons.nType = TYPE_ROMANTIC_BUTTON;
			
			oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.confirm", "确认")%>", "submitData()");
			oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.cancel", "取消")%>", "window.top.close();");
			oTRSButtons.draw();
		</SCRIPT>
	</div>
</form>
</body>
</html>
<%!
	private String getMachineCode() throws Exception{
		  com.trs.tools.KeyAPITools apiTools = new com.trs.tools.KeyAPITools();
          // 获取系统标识
          return apiTools.identifyHost();
	}	
%>