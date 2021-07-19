<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>业务办理</title>


</head>
<% 		
	// HttpSession usersession = request.getSession(false);
	//VoUser voUser = (VoUser) usersession.getAttribute(TxnContext.OPER_DATA_NODE); 
	//String userID = voUser.getValue("username");	
%>
<% 
    String user="admin";
	String password="123456"; 
%>
<frameset rows="*" cols="*" frameborder="no" border="no" framespacing="0" scrolling="no">
<!-- <frameset rows="132,*" cols="*" frameborder="no" border="no" framespacing="1">
		<frame src="analysisLogo.jsp"  class="fxLogo"   style="padding: 0;margin: 0" /> -->
	<frame src="http://10.1.2.124/ibmcognos/cgi-bin/cognosisapi.dll?b_action=xts.run&m=portal/launch.xts&ui.gateway=http://10.1.2.124/ibmcognos/cgi-bin/cognosisapi.dll&ui.tool=AnalysisStudio&ui.object=%2fcontent%2ffolder%5b%40name%3d%27cube%27%5d%2fpackage%5b%40name%3d%27%e4%b8%9a%e5%8a%a1%e5%8a%9e%e7%90%86%e7%bb%9f%e8%ae%a1%27%5d&ui.action=new&launch.openJSStudioInFrame=true&CAMUsername=<%=user%>&CAMPassword=<%=password%>" />
</frameset>
<body>  
</body>

</html>
