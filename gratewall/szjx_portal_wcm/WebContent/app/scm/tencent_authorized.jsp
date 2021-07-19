<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="include/error_scm.jsp"%>
<%@ page import="com.tencent.weibo.beans.OAuth"%>
<%@ page import="com.tencent.weibo.utils.OAuthClient"%>
<%@ include file="../include/public_server.jsp"%>
<%

	String sServerName = request.getHeader("X-FORWARDED-HOST");
	if (sServerName == null || sServerName.length() < 1) {  
		sServerName = request.getServerName() + (request.getServerPort() == 80 ? "" : (":" + request.getServerPort()));  
	} else if (sServerName.contains(",")) {  
		sServerName = sServerName.substring(0, sServerName.indexOf(",")).trim();  
	}

	String path = request.getContextPath();
	sServerName = sServerName +  path + "/";

	com.tencent.weibo.beans.OAuth oauth = new com.tencent.weibo.beans.OAuth(request.getScheme()+"://"+sServerName+"app/scm/tencent_authorized_callback.jsp");
	com.tencent.weibo.utils.OAuthClient oauthClient = new com.tencent.weibo.utils.OAuthClient();

	// 获取request token
	oauth = oauthClient.requestToken(oauth);

	if (oauth.getStatus() == 1) {
	%>
		<div  class="sabrosus">
			<font color="red" size="4"><BR>
				无法获取腾讯微博Token！请稍候重试！
			</font>
		</div>
	<%
		return;
	}

	String oauth_token = oauth.getOauth_token();
	String url = "http://open.t.qq.com/cgi-bin/authorize?oauth_token="
			+ oauth_token;
	// 授权信息保存到session中
	session.setAttribute("OAuth",oauth);
	session.setAttribute("oauth_token",oauth_token);

	// 链接到授权页面
	//防止CRLF注入，去除回车换行
	url = url.replace("\n","");
	url = url.replace("\r","");
	response.sendRedirect(url);
%>
