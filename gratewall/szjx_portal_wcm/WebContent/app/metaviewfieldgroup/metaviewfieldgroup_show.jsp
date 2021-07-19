<%--
/** Title:			metaviewfieldgroup_show.jsp
 *  Description:
 *		MetaViewFieldGroup的显示页面
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
 *		see metaviewfieldgroup_show.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.metadata.definition.MetaViewFieldGroup" %>
<%@ page import="com.trs.presentation.util.PageViewUtil"%>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化（获取数据）
	int nMetaViewFieldGroupId = currRequestHelper.getInt("MetaViewFieldGroupId", 0);
	MetaViewFieldGroup currMetaViewFieldGroup = MetaViewFieldGroup.findById(nMetaViewFieldGroupId);
	if(currMetaViewFieldGroup == null){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, CMyString.format(LocaleServer.getString("metaviewfieldgroup_show.jsp.param_error", "参数有误，没有找到ID为[{0}]的MetaViewFieldGroup！!"), new int[]{nMetaViewFieldGroupId}));
	}

//5.权限校验

//6.业务代码

//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="metaviewfieldgroup_show.jsp.title">TRS WCM MetaViewFieldGroup信息</TITLE>
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
function closeWindow(){
	top.returnValue = true;
	top.close();
}
</SCRIPT>
</HEAD>

<BODY>
<TABLE width="100%" height="100%" border="0" cellpadding="0"
	cellspacing="1" bgcolor="A6A6A6">
	<TR>
		<TD height="25"><SCRIPT src="../js/CWCMDialogHead.js"></SCRIPT> <SCRIPT
			LANGUAGE="JavaScript">
		WCMDialogHead.draw("MetaViewFieldGroup信息");
	</SCRIPT></TD>
	</TR>
	<TR>
		<TD valign="top">
		<TABLE width="100%" border="0" cellpadding="2" height="100%"
			cellspacing="0" bgcolor="#FFFFFF">
			<TR>
				<TD align="left" valign="top" height="10">
				<SCRIPT src="../js/CTRSButton.js"></SCRIPT> 
				<script>
					//定义一个单行按钮
					var oTRSButtons = new CTRSButtons();
					oTRSButtons.addTRSButton("返回", "closeWindow();", "../images/button_return.gif", "返回");
					oTRSButtons.draw();
				</script>
				</TD>
			</TR>
			<TR>
				<TD align="left" valign="top">
				<TABLE width="100%" border="0" cellpadding="2" cellspacing="1"
					bgcolor="A6A6A6">
					
					<TR bgcolor="#F6F6F6">
						<TD>GROUPNAME</TD>
						<TD>&nbsp;<%=PageViewUtil.toHtml(currMetaViewFieldGroup.getPropertyAsString("GROUPNAME"))%></TD>
					</TR>
					

					<TR bgcolor="#F6F6F6">
						<TD>METAVIEWID</TD>
						<TD>&nbsp;<%=PageViewUtil.toHtml(currMetaViewFieldGroup.getPropertyAsString("METAVIEWID"))%></TD>
					</TR>
					

				</TABLE>
				</TD>
			</TR>
		</TABLE>
		</TD>
	</TR>
</TABLE>
</BODY>
</HTML>