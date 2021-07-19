<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>天气预报查询</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <%String ip = (String) request.getAttribute("info");
	  String[] info = null;
	  	if(ip!= null && !"".equals(ip)){
	  		info = ip.split("#");
	  	}
	 String remotePort = (String)request.getAttribute("ip");
  %>
 
<b>浏览器端口</b>:<%= remotePort %><p/>

  <body>
  	<form action="ip" method="POST" target="_blank">
	   	<input type="text" id="ip" name="ip"/>
	   	<input type="submit" value="查询"/>
   	</form>
   	<%if(info!=null && info.length>0){%>
   		您要查询的IP:<%=info[0] %><br/>
   		地址是:  <%=info[1] %>
   	<%}%>
  </body>
</html>
