<%@ page contentType="text/javascript;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>

<%
out.clear();
//firefox的getId会获取到csid，故遍历cookie
String jsessionidName = "jsessionid";
Cookie[] cookies = request.getCookies();
if(cookies != null){
	for(int i = 0; i < cookies.length; i++){
		if("CSID".equalsIgnoreCase(cookies[i].getName())){
			jsessionidName = "CSID";
			break;
		}
	}
}

out.print("var jsessionid = {Name : '" + jsessionidName + "',Value:'" + session.getId() + "'};");
%>