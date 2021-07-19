<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.wcmonline.WCMOnlineService" %>
<!------- WCM IMPORTS END ------------>
<%
	/*
	* desc  从选件客户端向WCM发送服务请求，本页面测试是否可以连通
	*例如：使用场景,从嘉宾访谈选件客户端向WCM发送请求新建栏目时，
	*			需要判断其是否能够连通,连通时，返回值为文字库下所有站点的XML。
	*/
	
	// by CC 20120410 获取站点，返回xml封装值
	String sSitesXML = (new WCMOnlineService()).getSiteTree();
	//System.out.println("sSitesXML===" + sSitesXML);

	String sJsonP = request.getParameter("jsonp");
	if(sJsonP != null){
		out.println(sJsonP+"('" + sSitesXML + "');");
	}
%>