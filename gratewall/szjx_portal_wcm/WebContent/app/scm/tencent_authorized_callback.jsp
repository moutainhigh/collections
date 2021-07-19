<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="include/error_scm.jsp"%>
<%@ include file="../include/public_server_nologin.jsp"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor"%>
<%! static final boolean IS_DEBUG = false; %>
<%
	String verifier=request.getParameter("oauth_verifier");
	int nSCMGroupId = Integer.valueOf((String)session.getAttribute("BindSCMGroupId"));
	
	// 验证码为空时，输出错误信息，并返回
	if(verifier == null){
		out.println("授权失败:\n\t 获取 verifier 失败!");
		return;
	}
	if(IS_DEBUG){
		System.out.println("oauth_verifier="+verifier);
	}
	com.tencent.weibo.beans.OAuth oOAuth=(com.tencent.weibo.beans.OAuth) session.getAttribute("OAuth");
	
	// OAuth对象为空时
	if(oOAuth == null) {
		out.println("授权失败:\n\t 获取 OAuth 对象失败!");
		return;
	}
	
	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
	String sServiceId = "wcm61_scmaccount" , sMethodName = "save";
	HashMap parameters = new HashMap();
			
	// 获取AccessToken
	com.tencent.weibo.utils.OAuthClient oauthClient = new com.tencent.weibo.utils.OAuthClient();
	try{
		oOAuth.setOauth_verifier(verifier);
		oOAuth=oauthClient.accessToken(oOAuth);
	}catch(Exception tecentException){
		tecentException.printStackTrace();
%>
		<div  class="sabrosus">
			<font color="red" size="4"><BR>
				不好意思，腾讯服务器异常， 获取 Access Token 失败!请您稍后重试！
			</font>
		</div>
<%
		return;
	}
	if (oOAuth.getStatus() == 2) {
%>
		<div  class="sabrosus">
			<font color="red" size="4"><BR>
				授权失败: 获取 Access Token 失败!
			</font>
		</div>
<%
		return;
	}else{
		parameters.put("ObjectId",String.valueOf(0));
		parameters.put("SCMGroupId",String.valueOf(nSCMGroupId));
		parameters.put("AccessToken",String.valueOf(oOAuth.getOauth_token()));
		parameters.put("AccessSecret",oOAuth.getOauth_token_secret());
		parameters.put("Platform","Tencent");
		
		// 保存账号信息
		try{
			Integer nAccountId = (Integer) processor.excute(sServiceId,sMethodName,parameters);
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
	}
%>
<!DOCTYPE html>
<html>
<head>
<title>腾讯帐号绑定回调页面</title>
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