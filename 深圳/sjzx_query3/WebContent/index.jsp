<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
	<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>深圳市市场和质量监督管理委员会</title>

</head>
<body>


<%
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String dateStr = format.format(new Date());
%>
<p id="tips"></p>
<p>OK!</p>
<p ><%= dateStr%></p>
<p ><a href=""  id="goso">请点击我！</a></p>
<script type="text/javascript">
   // window.location.href='http://10.3.70.135:9080/portal/index.html'
	
		//获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
            var curWwwPath = window.document.location.href;
            //获取主机地址之后的目录，如： uimcardprj/meun.jsp
            var pathName = window.document.location.pathname;
            var pos = curWwwPath.indexOf(pathName);
            //获取主机地址，如： http://localhost:8083
            var localhostPaht = curWwwPath.substring(0, pos);
			
			//  window.location.href=localhostPaht+'portal/index.html'
document.getElementById("tips").innerHTML = localhostPaht;
document.getElementById("goso").href=localhostPaht+'/query/page/integeration/index.jsp';
</script>
</body>
</html>