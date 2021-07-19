<%
/** Title:			index.jsp
 *  Description:
 *		WCM5.2 WCM门户主操作界面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			CH
 *  Created:		2004-12-08 11:17 上午
 *  Vesion:			1.0
 *  Last EditTime:	2004-12-08 / 2004-12-08
 *	Update Logs:
 *		CH@2004-12-08 created
 *
 *  Parameters:
 *		see index.jsp
 *
 */
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TRS WCM5.2 WCM门户主操作界面 ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
</head>

<script src="../js/mainWindow_public.js"></script>
<frameset cols="200,10,*" frameborder="NO" border="0" framespacing="0">
	<frame src="auth_left.jsp" name="left" scrolling="NO" noresize>
	<frame src="../common/close_open_left.htm" name="control"  scrolling="NO">
	<frame src="auth_introduction.jsp" name="main">		
</frameset>

<noframes><body>Not Supply Frame!</body></noframes>
</html>