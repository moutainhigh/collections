<%
/** Title:			template_reports.jsp
 *  Description:
 *		WCM5.2 系统信息，显示批量操作执行结果
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WSW
 *  Created:		2004/12/10
 *  Vesion:			1.0
 *  Last EditTime:	
 *	Update Logs:
 *		
 *
 *  Parameters:
 *
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="java.util.Hashtable" %>
<%@ page import="java.util.Iterator" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.common.publish.persistent.template.Template" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.Report" %>
<%@ page import="com.trs.infra.util.Reports" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化（获取数据）
	Reports currReports = currRequestHelper.getReports();
	if(currReports == null){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "没有获取到操作结果报告！");
	}

//5.权限校验

//6.业务代码
	List lstSuccess = currReports.getSucessedReporter();
	Iterator itSuccess = lstSuccess.iterator();
	List lstWarned = currReports.getWarnedReporter();
	Iterator itWarned = lstWarned.iterator();
	List lstFailed = currReports.getFailedReporter();
	Iterator itFailed = lstFailed.iterator();

	boolean bFailed = !((lstFailed == null) || (lstFailed.isEmpty()));
	boolean bWarned = !((lstWarned == null) || (lstWarned.isEmpty()));
	boolean bSuccess = !((lstSuccess == null) || (lstSuccess.isEmpty()));

	if(!(bSuccess || bFailed || bWarned)){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "传入Reports对象有误，正确、警告、错误队列均为空！");
	}

	int nCurrStatus = getCurrStatus(bWarned, bFailed);
	Hashtable htPageInfo = getPageInfo(nCurrStatus, currReports.getTitle());

	Report currReport = null;

%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 系统信息::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<style>
.style5 {font-size: 18px}
</style>
<%@include file="../include/public_client_list.jsp"%>
<SCRIPT>
function copyToClipboard(){
	var sDetailMsg = document.all("spMsg").innerText;
	window.clipboardData.setData("Text", sDetailMsg);
	alert("已经复制到剪切板中！");
}

function editTemplate(_nTempId){
	var oTRSAction = new CTRSAction("../template/template_addedit.jsp");
	oTRSAction.setParameter("TempId", _nTempId);
	oTRSAction.doOpenWinAction();	
}
</SCRIPT>
</HEAD>

<BODY>
<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
<TR>
<TD align="left" valign="top" class="tanchu_content_td">
	<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
	<TR>
	<TD>
		<TABLE width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="a6a6a6">
		<TR>
		<TD height="350" valign="top" bgcolor="#FFFFFF">
			<TABLE width="600" border="0" align="center" cellpadding="3" cellspacing="0">
			<TR>
			<TD>
				<TABLE width="100%" border="0" cellspacing="5" cellpadding="0">
				<TR>
					<TD width="60"><IMG src="../images/<%=(String)htPageInfo.get("image")%>" width="60" height="60"></TD>
					<TD>
					<%=(String)htPageInfo.get("title")%>
					</TD>
				</TR>
				</TABLE>
			</TD>
			</TR>
	<SPAN id="spMsg" name="spMsg">
	<%
		if(bWarned){
	%>
			<TR>
				<TD align="left" valign="top"> <SPAN class="font_redbold">警告信息</SPAN></TD>
			</TR>
			<TR>
				<TD align="left" valign="top">
				<TABLE BORDER="0" CELLSPACING="2" CELLPADDING="0">
	<%
			while(itWarned.hasNext()){
				currReport = (Report) itWarned.next();
	%>
				<TR>
				<TD width="100%" align="left"><%=PageViewUtil.toHtml(currReport.getRportTitle())%><BR>
				<%=PageViewUtil.toHtml(currReport.getRportDetail())%><BR>
				<HR size=1>
				</TD>
				</TR>
	<%
			}
	%>
				</TABLE>
				</TD>
			</TR>
	<%
		}//end success info
	%>
	<%
		if(bFailed){
	%>
			<TR>
				<TD align="left" valign="top"> <SPAN class="font_redbold">失败信息</SPAN></TD>
			</TR>
			<TR>
				<TD align="left" valign="top">
				<TABLE BORDER="0" CELLSPACING="2" CELLPADDING="0">
	<%
			while(itFailed.hasNext()){
				currReport = (Report) itFailed.next();
				Template currTemplate = (Template)currReport.getRelateObject();

				String sTitle = PageViewUtil.toHtml(currReport.getRportTitle());
				sTitle = CMyString.replaceStr(sTitle, "~Edit~", "&nbsp;<a href=\"###\" onclick=\"editTemplate("+currTemplate.getId()+");return false;\">修改</a>&nbsp;");
	%>
				<TR>
				<TD width="100%" align="left"><%=sTitle%><BR>
				<%=PageViewUtil.toHtml(currReport.getRportDetail())%><BR>
				<HR size=1>
				</TD>
				</TR>
	<%
			}
	%>
				</TABLE>
				</TD>
			</TR>
	<%
		}//end success info
	%>
	</SPAN>
			</TABLE>
		</TD>
		</TR>
		</TABLE>
	</TD>
	</TR>
	<TR>
	<TD align="center">
		<script src="../js/CTRSButton.js"></script>
		<script>
		//定义一个TYPE_ROMANTIC_BUTTON按钮
		var oTRSButtons = new CTRSButtons();
		
		oTRSButtons.cellSpacing = "0";
		oTRSButtons.nType = TYPE_ROMANTIC_BUTTON;

		oTRSButtons.addTRSButton("复制到剪切板", "copyToClipboard()");
		oTRSButtons.addTRSButton("关闭窗口", "window.close()");

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
<SCRIPT LANGUAGE="JavaScript">
window.returnValue = true;
</SCRIPT>

<%!
	private final static int STATUS_SUCCESS = 1;
	private final static int STATUS_WARNED = 2;
	private final static int STATUS_FAILED = 3;

	private int getCurrStatus(boolean _bWarned, boolean _bFailed){
		if(!(_bWarned || _bFailed)){
			return STATUS_SUCCESS;
		}

		if( _bWarned && !_bFailed){
			return STATUS_WARNED;
		}

		return STATUS_FAILED;
	}

	private Hashtable getPageInfo(int _nStatus, String _sOperName) throws WCMException {
		String sOperName = CMyString.showNull(_sOperName);
		Hashtable htPageInfo = new Hashtable();
		switch(_nStatus){
		case STATUS_SUCCESS:
			htPageInfo.put("image", "succeed.gif");
			htPageInfo.put("title", "<span class=\"font_bluebold style5\">" + sOperName + "</span>");
			break;
		case STATUS_WARNED:
			htPageInfo.put("image", "warning.gif");
			htPageInfo.put("title", "<span class=\"font_redbold style5\">" + sOperName + "</span>");
			break;
		case STATUS_FAILED:
			htPageInfo.put("image", "error.gif");
			htPageInfo.put("title", "<span class=\"font_redbold style5\">" + sOperName + "</span>");
			break;
		default:
			throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "无效的页面状态参数！");
		}

		return htPageInfo;
	}
%>