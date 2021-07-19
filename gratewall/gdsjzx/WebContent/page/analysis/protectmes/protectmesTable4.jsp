<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>处理服务消费申诉情况统计表</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
		  
  </head>
  
  <body>
  
<!-- http://10.1.2.124:9300/p2pd/servlet/dispatch?b_action=cognosViewer&ui.action=run&   &run.outputFormat=&run.prompt=true&CAMUsername=admin&CAMPassword=123456&CAMNamespace=GdSjzxProvider -->       
   <form method="post" id="myform" action="http://10.1.2.124:9300/p2pd/servlet/dispatch?b_action=cognosViewer&ui.action=run&ui.object=%2fcontent%2ffolder%5b%40name%3d%27report%27%5d%2ffolder%5b%40name%3d%27%e5%b7%a5%e5%95%86%e5%88%b6%e5%bc%8f%e6%8a%a5%e8%a1%a8%27%5d%2freport%5b%40name%3d%27%e6%b6%88%e4%bf%9d4%e8%a1%a8%27%5d&ui.name=%e6%b6%88%e4%bf%9d4%e8%a1%a8&run.outputFormat=&run.prompt=true&CAMUsername=admin&CAMPassword=123456&CAMNamespace=GdSjzxProvider"  >  
            <input type="submit" style="display: none;"
                value="Submit" />  
   </form>  
   
   
	 <script type="text/javascript">  
		   	document.forms["myform"].submit(); 
		  </script>
  </body>
</html>
