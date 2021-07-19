<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>市场主体概况</title>


</head>
<% 		
	// HttpSession usersession = request.getSession(false);
	//VoUser voUser = (VoUser) usersession.getAttribute(TxnContext.OPER_DATA_NODE); 
	//String userID = voUser.getValue("username");	
%>
<% 
    String user="admin";
	String password="123456";
	String stDt="2016-01-01";
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	Calendar cal=Calendar.getInstance();
	cal.set(Integer.valueOf(stDt.split("-")[0]).intValue(), Integer.valueOf(stDt.split("-")[1]).intValue()-1, Integer.valueOf(stDt.split("-")[2]).intValue());
	cal.add(Calendar.DATE, -1);
	stDt=df.format(cal.getTime()).substring(0, 10);
	String measure="001001";
%>
<frameset rows="*" cols="*" frameborder="no" border="no" framespacing="0" scrolling="no">
	<!--<frameset rows="132,*" cols="*" frameborder="no" border="no" framespacing="0" scrolling="no">
	<!-- <frame src="analysisLogo.jsp"  class="fxLogo"   style="padding: 0;margin: 0" /> -->
	<frame   style="padding: 0;margin: 0" src="http://10.1.2.124/ibmcognos/cgi-bin/cognosisapi.dll?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27report%27%5d%2freport%5b%40name%3d%27%e5%b8%82%e5%9c%ba%e4%b8%bb%e4%bd%93%e6%a6%82%e5%86%b5%27%5d&CAMUsername=<%=user%>&CAMPassword=<%=password%>&p_stDt=<%=stDt%>&p_measure=<%=measure%>" />
	</frameset>
<body>  

</body>

</html>
