<%@ page contentType="text/html;charset=utf-8" errorPage="../include/error.jsp"%>
<%@ page language="java" import="t4j.TBlog"%>
<%@ page language="java" import="t4j.TBlogException"%>
<%@ page language="java" import="t4j.http.AccessToken"%>
<%@ page language="java" import="t4j.http.RequestToken"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.net.URL"%>
<%@ page import="java.util.Properties"%>
<%@ page import="org.apache.log4j.helpers.Loader"%>
<%@ include file="../include/public_server_nologin.jsp"%>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor"%>
<%	
	TBlog tblog = new TBlog();
	RequestToken requestToken = (RequestToken)session.getAttribute("requestToken");  
	if(requestToken==null){
		requestToken = tblog.getOAuthRequestToken();
		session.setAttribute("requestToken",requestToken) ;  
	}

	AccessToken accessToken = null;
	accessToken = tblog.getOAuthAccessToken(requestToken);
	session.removeAttribute("requestToken");   
	String sUrl =   requestToken.getAuthenticationURL();
	 String sToken =  accessToken.getToken();
	 String sSecret  = accessToken.getTokenSecret();

	// 保存到数据库表中
	int nSCMGroupId = Integer.valueOf((String)session.getAttribute("BindSCMGroupId"));
	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
	String sServiceId = "wcm61_scmaccount" , sMethodName = "save";
	HashMap parameters = new HashMap();
	parameters.put("ObjectId",String.valueOf(0));
	parameters.put("SCMGroupId",String.valueOf(nSCMGroupId));
	parameters.put("AccessToken",sToken);
	parameters.put("AccessSecret",sSecret);
	parameters.put("Platform","T163");

	Integer nAccountId = (Integer) processor.excute(sServiceId,sMethodName,parameters);
	if(nAccountId >0){
%>
<!DOCTYPE html>
<html>
<head>
	<title>网易绑定帐号回调页面</title>
	<link rel="stylesheet" href="css/public.css">
</head>
<body>
		<div  class="sabrosus">
			<font size="4">认证成功！进入帐号管理即可看到新绑定的帐号<BR>
				
				系统将在&nbsp;<span style="color:#ff0000" id= "time"> 5 </span> &nbsp;秒钟后关闭本页面<br>
				或点击<a href="javaScript:window.closeExplore()" style="color:#ff0000">关闭</a>本页面
			</font>	
		</div>
</body>
<script language="javascript">
		var times=6;
		clock();
		function clock()
		{
		   window.setTimeout('clock()',1000);
		   times=times-1;
		   document.getElementById("time").innerHTML =times;
		   if(times == 0){
				window.closeExplore();
		   }
		}
		function closeExplore(){
			window.opener = null;
			window.open("","_self");
			window.close();
		}
		
	</script>
</html>
<%
		}else{
%>
	<div  class="sabrosus">
			<font color="red" size="4"><BR>
				认证失败,请您稍后重试
			</font>	
		</div>
<%}%>
