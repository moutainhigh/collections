<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.video.domain.DBTransfer" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<!------- WCM IMPORTS END ------------>
<%@include file="../include/public_server.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:TRS="">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>视频信息数据迁移</title>
</head>
<body>
<%
	DBTransfer transfer = new DBTransfer();
	int siteListSize = 0;
	int successedNum = 0;
%>
<br>一、WCM端迁移视频信息<br>
<%
if( transfer.needTransferSP2()) {
if (!transfer.isTransferedSP1()) {
%>
<%List siteList = new ArrayList();
	try {
		siteList = transfer.getSites();
	} catch (Throwable e) {
%>
&nbsp;&nbsp;&nbsp;&nbsp;数据库失败，无法获取视频站点！错误信息：<br>
&nbsp;&nbsp;&nbsp;&nbsp;<%=e.getMessage()%><br>
<%	}
	siteListSize = siteList.size();
	for (int siteIndex = 0; siteIndex < siteListSize; siteIndex++) {
		Integer site =(Integer)siteList.get(siteIndex);
		boolean successed = true;		
%>
<br>&nbsp;&nbsp;&nbsp;&nbsp;视频站点[<%=site.intValue()%>]迁移中...<br>	
<%
		List channelList = new ArrayList();
		try {
			channelList = transfer.getChannels(site);
		} catch (Throwable e) {
			successed = false;
%>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数据库失败，无法获取栏目！错误信息：<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=e.getMessage()%>
<%
		}
		for (int channelIndex = 0, channelListSize = channelList.size(); channelIndex < channelListSize; channelIndex++) {
			Integer channel =(Integer)channelList.get(channelIndex);
			String transResult = transfer.importInChannel(channel);
			if ("ok!".equals(transResult)) {
%>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;栏目[<%=channel.intValue()%>]迁移成功！<br>
<%
			} else if("hasUndefinedMeta".equals(transResult)) {
%>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;栏目[<%=channel.intValue()%>]迁移成功！但存在错误元数据，请查看日志！<br>
<%
			} else {
%>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;栏目[<%=channel.intValue()%>]迁移失败！错误信息：<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=transResult%>
<%
				successed = false;
				break;
			}
		}
		if (successed) {
			++successedNum;
%>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;整个站点迁移成功！<br>
<%
		} else {
			break;
		}
	}// 最外层 for ，for (int siteIndex = 0; siteIndex < siteListSize; siteIndex++)

	if (successedNum == siteListSize) {
		if (successedNum != 0) {
	%>
	<br>&nbsp;&nbsp;&nbsp;&nbsp;所有视频信息迁移成功！<br>
	<%
		} else {
	%>
	<br>&nbsp;&nbsp;&nbsp;&nbsp;视频信息已经做过迁移！<br>
	<%
		}
	
	//设置复制属性
	String setResult = transfer.setIsCopied();
	if ("ok!".equals(setResult)) {
	%>
	<br>&nbsp;&nbsp;&nbsp;&nbsp;设置视频文档的复制属性成功！<br>
	<%
		} else {
	%>
	<br>&nbsp;&nbsp;&nbsp;&nbsp;设置视频文档的复制属性时失败！错误信息：<br>
	&nbsp;&nbsp;&nbsp;&nbsp;<%=setResult%>
	<%
		}
	}
}// if (!transfer.isTransferedSP1())
else{
	try {
		transfer.oldToNewXVideo();
%>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;XVideo从旧表向新表迁移成功！<br>
<%		
	} catch (Throwable t) {
%>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;XVideo从旧表向新表迁移失败！原因:<%= t %><br>
<div>错误堆栈: 
<% t.printStackTrace(new java.io.PrintWriter(out)); %>
</div>
<%
	}
}
}// if( transfer.needTransferSP2())	
else {
%>
<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;WCM没有视频数据，或者已经做过迁移！<br>
<%
}
%>
</body>
</html>