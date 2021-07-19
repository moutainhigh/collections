<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>企业注吊销登记分析</title>


</head>
<% 		
	// HttpSession usersession = request.getSession(false);
	//VoUser voUser = (VoUser) usersession.getAttribute(TxnContext.OPER_DATA_NODE); 
	//String userID = voUser.getValue("username");	
%>
<% 
    String user="admin";
	String password="123456";
	String stDt="2015-12-07";
	String edDt="2015-12-31";
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	Calendar cal=Calendar.getInstance();
	cal.set(Integer.valueOf(edDt.split("-")[0]).intValue(), Integer.valueOf(edDt.split("-")[1]).intValue()-1, Integer.valueOf(edDt.split("-")[2]).intValue());
	cal.add(Calendar.DATE, -6);
	stDt=df.format(cal.getTime()).substring(0, 10);
	String measure="001005";
%>
<!-- <frameset rows="132,*" cols="*" frameborder="no" border="no" framespacing="1">
		<frame src="analysisLogo.jsp"  class="fxLogo"   style="padding: 0;margin: 0" /> -->
<frameset rows="*" cols="*" frameborder="no" border="no" framespacing="0" scrolling="no">
<frame src="http://10.1.2.124/ibmcognos/cgi-bin/cognosisapi.dll?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27report%27%5d%2freport%5b%40name%3d%27%e6%b3%a8%e5%90%8a%e9%94%80%e7%99%bb%e8%ae%b0%e5%88%86%e6%9e%90%27%5d&CAMUsername=<%=user%>&CAMPassword=<%=password%>&p_stDt=<%=stDt%>&p_edDt=<%=edDt%>&p_measure=<%=measure%>" />
</frameset>
<body>  
</body>

</html>
