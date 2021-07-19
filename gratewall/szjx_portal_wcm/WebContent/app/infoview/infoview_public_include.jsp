<%
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
	response.addHeader("Cache-Control","no-store"); //Firefox
	response.setHeader("Pragma","no-cache"); //HTTP 1.0
	response.setDateHeader ("Expires", -1);
	response.setDateHeader("max-age", 0);
%>