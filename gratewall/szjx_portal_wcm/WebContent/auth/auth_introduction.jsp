<%
/** Title:			auth_introduction.jsp
 *  Description:
 *		WCM5.2 通用模块介绍页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			CH
 *  Created:		2004-12-14 21:05
 *  Vesion:			1.0
 *  Last EditTime:	2004-12-14/2004-12-14
 *	Update Logs:
 *		CH@2004-12-14 Created introduction.xml
 *		
 *
 *  Parameters:
 *		see auth_introduction.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TRS WCM 5.2 通用模块介绍页面::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
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
    <td><table width="100%"  border="0" cellpadding="0" cellspacing="1" class="list_table">
      <tr>
        <td height="26" class="head_td"><table width="100%" height="26"  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="24"><img src="../images/bite-blue-open.gif" width="24" height="24"></td>
              <td>用户组织介绍</td>
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
                              <td height="20" class="font_introduction">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户组织为WCM的管理员用户提供管理和规划用户群的功能，它包括用户管理、组织管理和角色管理。<BR><BR>
<LI><b>用户管理</b>：WCM中的用户对应实际工作中的每个具体的业务人员。提供对用户的各类维护操作。</LI>
<LI><b>组织管理</b>：WCM中的组织类似于我们日常所说的一个单位内部的行政组织结构，它是树状结构。组织往往是用户的集合体，通常情况下，注册用户都隶属于某一个甚至几个组织。提供对组织的增删改、增减用户成员和指定组管理员等操作。 </LI>
<LI><b>角色管理</b>：WCM中的角色是指具有相同权限的用户集合，比如系统管理员。提供对角色的增删改和增减用户成员的操作。
</LI>
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