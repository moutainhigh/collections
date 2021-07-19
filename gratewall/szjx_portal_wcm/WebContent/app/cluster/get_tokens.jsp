<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.infra.session.SessionContext" %>
<%@ page import="com.trs.cluster.TRSWCMClusterServer" %>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.infra.cluster.ClusterContext" %>
<%@ page import="com.trs.infra.cluster.MemberManager" %>
<%@ page import="com.trs.infra.cluster.locks.TokenManagerImpl"%>
<%@ page import="com.trs.infra.cluster.locks.TokenImpl"%>

<%@ page import=" java.util.Iterator" %>
<%-- /*页面状态设定、登录校验、参数获取，都放在public_server.jsp中*/ --%>
<%@include file="../include/public_server.jsp"%>
<%
	
	if(!loginUser.isAdministrator()){
		throw new WCMException(LocaleServer.getString("get_tokens.jsp.noright","您没有权限执行此操作！"));
	}

	Iterator iter = null;	
	boolean zClustered = true;
	TRSWCMClusterServer server = (TRSWCMClusterServer) DreamFactory.createObjectById("TRSWCMClusterServer");
	MemberManager memberManager = null;
	if(server.getContext()==null){
	    zClustered = false;
	}else{
	    iter=((TokenManagerImpl) server.getContext().getTokenManager()).tokens();
		memberManager = server.getContext().getMemberManager();
	}
	out.clear();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title WCMAnt:param="get_tokens.jsp.clusterlook">集群令牌查看::::..</title>
</head>
<body>
<%if(!zClustered){%>
<p WCMAnt:param="get_tokens.jsp.noclusterlook">非集群环境... </p>
<%}else if(iter!=null){%>
<ol WCMAnt:param="get_tokens.jsp.clusterhasfollow">目前集群中有以下令牌:
<%
	while(iter.hasNext()){
		TokenImpl token = (TokenImpl)iter.next();
		out.println("<li>"+token+":owner="+memberManager.find(token.getOwnerGUID())+"</li>");
	}
%>
</ol>
<%}%>
</body>
</html>