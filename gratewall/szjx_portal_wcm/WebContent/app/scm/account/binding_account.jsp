<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error_scm.jsp"%>
<%@ page import="com.trs.scm.sdk.factory.PlatformFactory" %>
<%@ page import="com.trs.scm.sdk.model.Platform" %>
<%@ include file="../../include/public_server.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户管理-绑定</title>
<link rel="stylesheet" href="../css/public.css">
<link rel="stylesheet" href="binding_account.css">
<script src="../js/jquery-1.7.2.min.js"></script>
<script src="../js/scm_common.js"></script>
<script src="../js/iframe_public.js"></script>
</head>
<%
	int nSCMGroupId = currRequestHelper.getInt("SCMGroupId",0);	
	if(nSCMGroupId == 0){
		// todo
	}
	session.setAttribute("BindSCMGroupId",String.valueOf(nSCMGroupId));
%>
	<body>
		<div id="userList" class="lineHeight63">
			<div class="bindTitleFont">
				绑定微博帐号
			</div>
			<div class="clearFloat"></div>
		</div>
		<div class="bindContainer" id="counts">
			<p class="paddingTop35Px">
				微博授权后，“社会化内容管理系统”才能访问到您的数据。
			</p>
			<p class="bindFont">提示：在授权之前，请确认您要添加的帐号已经退出其他相关服务，如邮箱、微博、博客等。您在授权绑定帐号过程中，请允许【您的浏览器】弹出窗口，否则可能无法完成帐号绑定。</p>
			<div>
			<%
			List oPlatformList = PlatformFactory.getPlatforms();
			for(int i= 0; i < oPlatformList.size(); i++){
				Platform oPlatform = (Platform)oPlatformList.get(i);
				if(oPlatform == null) continue;
				String sName = oPlatform.getName();
			%>
				<span><a href="../<%=CMyString.filterForHTMLValue(sName.toLowerCase())%>_authorized.jsp" target="_blank"><img src="../images/check_<%=CMyString.filterForHTMLValue(sName)%>.png" border/></a></span>&nbsp;&nbsp;&nbsp;&nbsp;
			<%
			}
			%>
			</div>
			<%
			int nExtraType = currRequestHelper.getInt("ExtraType",0);
			if(nExtraType > 0){
				%>
				<p class="bindFont">新浪微博帐号授权信息已过期，请重新绑定该帐号！</p>
				<%
			}
			%>
		</div>
		
	</body>
</html>
