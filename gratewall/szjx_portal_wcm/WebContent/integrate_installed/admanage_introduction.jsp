<%
/** Title:			integrate_introduction.jsp
 *  Description:
 *		WCM5.2 扩展功能介绍页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			CH
 *  Created:		2005-01-03 21:05
 *  Vesion:			1.0
 *  Last EditTime:	2005-01-03/2005-01-03
 *	Update Logs:
 *		CH@2005-01-03 Created integrate_introduction.jsp
 *
 */
%>

<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.common.conjection.ComponentEntryConfigConstants" %>
<%@ page import="com.trs.components.common.conjection.persistent.EntryConfig" %>
<%@ page import="com.trs.components.common.conjection.service.IComponentEntryConfigService" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<!------- WCM IMPORTS END ------------>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	errorPage="../include/error.jsp"%>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%
	int nURL = currRequestHelper.getInt("URL", 1);
	int nDocumentId = currRequestHelper.getInt("DocumentId", 0);
	String sChannel=currRequestHelper.getString("Channel");
	IComponentEntryConfigService configSrv = (IComponentEntryConfigService) DreamFactory
			.createObjectById("IComponentEntryConfigService");

	EntryConfig currConfig = configSrv.getTypedEntryConfig(ComponentEntryConfigConstants.TYPE_ADVERTISEMENT);

	String sUserLoginEncodInfo = currLoginHelper.createPluginEncodeUserInfo();
	String sURL = CMyString.setStrEndWith(currConfig.getLinkPath(), '/');
	
	if (currConfig.isEnable())  {
		String sNeedRedirect = request.getParameter("redirectable");								switch(nURL){
			case 1: sURL +="order_main.jsp";
					break;
			case 2: sURL +="adlocation_list.jsp";
					break;
			case 3: sURL +="advendor_list.jsp";
					break;
			case 4: sURL +="adtype_list.jsp";
					break;
			case 5: sURL +="order_list_of_advendor.jsp";
					break;
			case 6: sURL +="order_list_of_location.jsp";
					break;
			case 7: sURL +="adlocation_select.jsp?"+currRequestHelper.toURLParameters();
					break;
			case 8: sURL +="adschedule_show.jsp";
					break;
			default:sURL +="admanage_introduction.jsp";
					break; 
		}
		if(nURL!=7){
			sURL += "?" + sUserLoginEncodInfo;	
		}else{
			sURL += "&" + sUserLoginEncodInfo;	
		}
		
		
		if (sNeedRedirect == null || sNeedRedirect.trim().compareToIgnoreCase("false") != 0){
			currRequestHelper.getResponse().sendRedirect(sURL);
		}
		
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TRS WCM 5.2 Preview 扩展功能介绍页面::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
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
<script src="../js/TRSButton.js"></script>
<!-- 结束：WCM按钮的style-->

<script language="javascript" type="text/javascript">
	var win = null;
	function newWindow(mypage,myname,w,h,scroll){
		LeftPosition = (screen.width) ? (screen.width-w)/2 : 0;
		TopPosition = (screen.height) ? (screen.height-h)/2 : 0;
		settings =
			'height='+h+',width='+w+',top='+TopPosition+',left='+LeftPosition+',scrollbars='+scroll+',resizable'
		win = window.open(mypage,myname,settings)
		if(win.window.focus){
			win.window.focus();
		}
	}
</script>

</head>

<body>

<table width="100%"  border="0" cellspacing="4" cellpadding="0">
	<tr>
    <td><table width="100%"  border="0" cellpadding="0" cellspacing="1" bgcolor="A6A6A6">
      <tr>
        <td height="26" background="../images/tdbg.jpg"><table width="100%" height="26"  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="24"><img src="../images/bite-blue-open.gif" width="24" height="24"></td>
              <td>广告管理介绍</td>
              <td width="15">&nbsp;</td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td valign="top"><table width="100%" border="0" cellpadding="2" cellspacing="0" bgcolor="#FFFFFF">
          <tr>
            <td valign="top"><table width="100%"  border="0" cellspacing="2" cellpadding="0">
                <tr>
                  <td>
                    <table width="100%"  border="0" cellpadding="2">
                      <tr>
                        <td bgcolor="#Ffffff"><table width="100%"  border="0" cellpadding="0" cellspacing="0" >
                            <tr>
                              <td height="20" >
&nbsp;&nbsp;&nbsp;&nbsp;网站常用广告的发布、维护。<BR><BR>
<% if (!currConfig.isEnable()) {
	if(nURL==1){%>
&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">该选件已成功关闭。</font>
<%}else{%>
&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">该选件尚未安装。</font>
<%}%>
<%}else{%>
&nbsp;&nbsp;&nbsp;&nbsp;<font color="green">该选件功能已经安装。</font><span><a
													href="<%=CMyString.filterForHTMLValue(sURL)%>" target="_self">点击进入</a></span><BR>

<%}%>
<P style="MARGIN-LEFT: 42.25pt; TEXT-INDENT: -21pt; LINE-HEIGHT: 20pt; tab-stops: list 42.25pt">&nbsp;</P>
</td>
                            </tr>
                        </table></td>
                      </tr>
                  </table></td>
                </tr>
            </table></td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>



</table>
</body>
</html>