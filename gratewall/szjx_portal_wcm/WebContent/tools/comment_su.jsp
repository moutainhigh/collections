<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.common.conjection.service.IComponentEntryConfigService"%>
<%@ page import="com.trs.components.common.conjection.ComponentEntryConfigConstants"%>
<%@ page import="com.trs.components.common.conjection.persistent.EntryConfig"%>
<%@ page import="com.trs.components.comment.domain.Management" %>
<%@ page import="com.trs.components.comment.service.ICommentService" %>

<%@ page import="com.trs.components.comment.domain.NewsComment" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;
%>
<%

IComponentEntryConfigService configSrv = (IComponentEntryConfigService) DreamFactory
		.createObjectById("IComponentEntryConfigService");
EntryConfig currConfig = configSrv.getTypedEntryConfig(ComponentEntryConfigConstants.TYPE_COMMENT);

String sCommentURL = currConfig.getLinkPath();

if (sCommentURL == null) 
	sCommentURL = "http://localhost:8080/comment";

int nDocumentId = currRequestHelper.getInt("DocumentId", 0);

Document doc = null;
if ( nDocumentId > 0 ){
	doc = Document.findById(nDocumentId);
	if( doc==null )
		throw new WCMException("No Document [" + nDocumentId + "] found!" );
}	
Channel chnl = doc.getChannel();
WebSite site = chnl.getSite();

NewsComment nc = new NewsComment();

nc.setNewsId(nDocumentId);
nc.setNewsTitle(doc.getTitle());
nc.setSiteId(site.getId());
nc.setChannelId(chnl.getId());
nc.setLimit(1); //Confirm

String sSignedStr = nc.getDataWithSign();
%>
<HTML>
<HEAD>
<TITLE>评论字符串编码测试</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</HEAD>

<BODY>
<table border="0" cellspacing="3" cellpadding="2" width="100%">
<tbody>
	<tr>
		<td align=right style="font-size: 14px; white-space: nowrap; font-weight: bold">文档: </td>
		<td align=left style="font-size: 14px;">
			[ID=<%=doc.getId() %>]<%= doc.getTitle()%>
		</td>							
	</tr>
	<tr>
		<td align=right style="font-size: 14px; white-space: nowrap; font-weight: bold">加密串: </td>
		<td align=left style="font-size: 14px;">
			<%=sSignedStr %>
		</td>							
	</tr>
	<tr>
		<td align=right style="font-size: 14px; white-space: nowrap; font-weight: bold">解密串: </td>
		<td align=left style="font-size: 14px;">
			<%=new NewsComment(sSignedStr).toString() %>(<a href="<%=sCommentURL + "/comment_su_dis.jsp?data=" + sSignedStr%>">发送至comment验证</a>)
		</td>							
	</tr>															
</tbody>
</table>
</BODY>
</HTML>