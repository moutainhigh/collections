<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="include/error_scm.jsp"%>
<%@ page language="java" import="com.trs.infra.util.CMyDateTime"%>
<%@ page language="java" import="weibo4j.org.json.JSONObject"%>
<%@ page language="java" import="weibo4j.http.AccessToken"%>
<%@ page language="java" import="weibo4j.Oauth" %>
<%@ page language="java" import="weibo4j.model.WeiboException" %>
<%@ page language="java" import="weibo4j.Weibo" %>
<%@ page language="java" import="weibo4j.http.Response"%>
<%@ page import="com.trs.scm.persistent.SCMGroup"%>
<%@ page import="java.util.HashMap"%>
<%@ include file="../include/public_server_nologin.jsp"%>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor"%>
<%
	int nSCMGroupId = 0;
	try{
		nSCMGroupId = Integer.parseInt((String)session.getAttribute("BindSCMGroupId"));
	}catch(Exception e){}
	String sCode = request.getParameter("code");
	Oauth oauth = new Oauth();
%>
<!DOCTYPE html>
<html>
<head>
<title>新浪绑定帐号页面</title>
<link rel="stylesheet" href="css/public.css">
</head>
<body>
<%
	//获取访问的URL
	// sServerName = 127.0.0.1:8080
	String sServerName = request.getHeader("X-FORWARDED-HOST");
	if (sServerName == null || sServerName.length() < 1) {  
		sServerName = request.getServerName() + (request.getServerPort() == 80 ? "" : (":" + request.getServerPort()));  
	} else if (sServerName.contains(",")) {  
		sServerName = sServerName.substring(0, sServerName.indexOf(",")).trim();  
	}
	
	String path = request.getContextPath();
	sServerName = sServerName + path + "/";
	if(sCode == null || sCode.length() == 0){
		String sUrl = oauth.authorize("code")+"&forcelogin=true&state="+nSCMGroupId + "BaseServer" + sServerName;
		//防止CRLF注入，去除回车换行
		sUrl = sUrl.replace("\n","");
		sUrl = sUrl.replace("\r","");
		response.sendRedirect(sUrl);

	}else if(sCode != null && sCode.length() != 0){		
		String sState = request.getParameter("state");
		if(sState.indexOf("BaseServer") >= 0){
			String sRedirectServer = sState.substring(sState.indexOf("BaseServer")+10, sState.length());
			if(!sRedirectServer.equals(sServerName)){
				String sBaseHost = request.getScheme()+"://"+sRedirectServer;
				String sUrl = sBaseHost + "app/scm/sina_authorized.jsp?state="+sState+"&code=" + sCode;
				//防止CRLF注入，去除回车换行
				sUrl = sUrl.replace("\n","");
				sUrl = sUrl.replace("\r","");
				response.sendRedirect(sUrl);
				return;
			}
			nSCMGroupId = Integer.parseInt(sState.substring(0, sState.indexOf("BaseServer")));
		}
		String access_token = "";
		try {			
			// 输出授权得到的AccessToken（得到的AccessToken在测试接口时使用）
			AccessToken oAccessToken = oauth.getAccessTokenByCode(sCode);
			access_token = oAccessToken.getAccessToken();
		}catch (WeiboException e) {
			System.out.println(e.toString());
		%>
			<script>
				alert("不好意思，新浪服务器异常， 获取 Access Token 失败!请您稍后重试！");
			</script>
		<%
			return;
		}
		JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
		HashMap parameters = new HashMap();
		parameters.put("ObjectId",String.valueOf(0));
		parameters.put("SCMGroupId",String.valueOf(nSCMGroupId));
		parameters.put("AccessToken",access_token);
		parameters.put("Platform","Sina");
		// 保存帐号信息
		try{
			Integer nAccountId = (Integer) processor.excute("wcm61_scmaccount","save",parameters);
		}catch (Exception e) {
%>
		<div  class="sabrosus">
			<font color="red" size="4"><BR>
				认证失败,请您稍后重试
			</font>
		</div>
<%
			return;
		}
%>
		<div  class="sabrosus">
			<font size="4">认证成功！进入帐号管理即可看到新绑定的帐号<BR><BR>
				系统将在&nbsp;<span style="color:#ff0000" id= "time"> 10 </span> &nbsp;秒钟后关闭本页面<br>
				或点击<a href="javaScript:window.closeExplore()" style="color:#ff0000">关闭</a>本页面
			</font>	
		</div>
<%		
	}
%>
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