<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.video.VSConfig" %>
<%
		if ("1".equals(request.getParameter("reload"))) {
		VSConfig.reloadConfigs();
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>WCM Video Component Config</title>
</head>

<body>
<div>
<div><span>Java URL: </span><span><%=VSConfig.getUploadJavaAppUrl()%></span></div>
<div><span>FMS URL:  </span><span><%=VSConfig.getUploadFMSAppUrl()%></span></div>
<div><span>IMG URL:  </span><span><%=VSConfig.getThumbsHomeUrl()%></span></div>
<div><span>getFLVPlayerBase():  </span><span><%=VSConfig.getFLVPlayerBase() %></span></div>
</div>
</body>
</html>