
<%
/** Title:			documnet_introduction.jsp
 *  Description:
 *		WCM5.2 通用模块介绍页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			CH
 *  Created:		2004-12-14 21:05
 *  Vesion:			1.0
 *  Last EditTime:	2004-12-14/2004-12-14
 *	Update Logs:
 *		CH@2004-12-14 Created document_introduction.jsp
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = true;%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 通用模块介绍页面::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
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
<script src="../js/CTRSButton.js"></script>
</head>

<body>
<table width="100%"  border="0" cellspacing="4" cellpadding="0">
	<tr>
    <td><table width="100%"  border="0" cellpadding="0" cellspacing="1" bgcolor="A6A6A6">
      <tr>
        <td height="26" background="../images/tdbg.jpg"><table width="100%" height="26"  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="24"><img src="../images/bite-blue-open.gif" width="24" height="24"></td>
              <td>系统配置介绍</td>
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
                        <td bgcolor="#Ffffff">
						<table width="100%"  border="0" cellpadding="0" cellspacing="0" >
                            <tr>
                              <td height="20"  class="font_introduction">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;系统配置是TRS WCM中的一个重要组成部分，它用于系统中常用的非业务数据的配置，如计划调度任务、系统运行目录、系统权限值表等等，这些参数往往对系统的运行有重要的影响。合理的配置系统参数可以提高系统的灵活性和可扩展性，然而配置不当会也造成系统无法正常运行，因此为了确保系统在使用过程中的安全性，系统规定只有系统管理员有权查看系统配置并进行设置。<BR><BR>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;TRS WCM中一共有三大类系统配置，分别是<b>计划调度配置</b>、<b>系统参数配置</b>（简称系统配置）、<b>属性配置</b>。计划调度配置中维护系统定时执行的任务，系统参数配置中维护系统运行时所用到的参数，属性配置中维护系统预定义的对象属性。<BR><BR>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;除非有特殊需求，否则用户应尽量避免对这些参数进行增删改等操作。请确认您已经了解各个参数的含义后，再进行相关设置。<BR><BR>

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