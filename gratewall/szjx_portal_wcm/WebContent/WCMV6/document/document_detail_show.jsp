<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.presentation.util.RequestHelper" %>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%boolean IS_DEBUG = false;%>
<%
	int nDocumentId	  = currRequestHelper.getInt("DocumentId", 0);
	int nChannelId = currRequestHelper.getInt("ChannelId", 0);
	int nSiteId = currRequestHelper.getInt("SiteId", 0);
	Channel currChannel = null;
	WebSite currWebSite = null;
	if(nDocumentId > 0){
		Document currDocument  = Document.findById(nDocumentId);
		if(currDocument == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取ID为["+nDocumentId+"]的文档失败！");
		}
		currChannel = currDocument.getChannel();	
		currWebSite = currChannel.getSite();
	}else if(nChannelId>0){
		currChannel = Channel.findById(nChannelId);	
		if(currChannel == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取栏目ID为["+nChannelId+"]的栏目失败！");
		}
		currWebSite = currChannel.getSite();
	}
	else if(nSiteId>0){
		currWebSite = WebSite.findById(nSiteId);	
		if(currWebSite == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取站点ID为["+nSiteId+"]的站点失败！");
		}
	}
	else{
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "未指定ChannelId和SiteId.");
	}
	String sDefaultListPage = "../document/document_detail_show.html";
	switch(currWebSite.getPropertyAsInt("SiteType",0)){
		case 0:
			//文字库
			break;
		case 1:
			//TODO
			//图片库
			sDefaultListPage = "../photo/photo_show.html";
			break;
		case 2:
			//视频库
			sDefaultListPage = "../video/video_show.jsp";
			break;
	}
	String sRedirectPage = sDefaultListPage;
	String s404Message = "";
	if(currChannel != null){
		if(currChannel.isLink()){
			//TODO?
		}
//		sRedirectPage = currChannel.getContentListPage();
		sRedirectPage = currChannel.getPropertyAsString("ContentShowPage");
		if(sRedirectPage==null||"".equals(sRedirectPage)||"./document/document_show.jsp".equals(sRedirectPage)){
			sRedirectPage = sDefaultListPage;
		}
	}
	if(IS_DEBUG){
		System.out.println(sRedirectPage);
	}
	String sTarget = "";
	if(sRedirectPage.indexOf("?")==-1){
		sTarget = sRedirectPage+"?"+request.getQueryString();
	}
	else{
		sTarget = sRedirectPage+"&"+request.getQueryString();
	}
%>
<script src="../js/com.trs.util/Common.js"></script>
<script>
/*
	function checkPage404(_hrefSrc){
		var r = new Ajax.Request(_hrefSrc,{
			asynchronous:false
		});
		return r.transport.status==404;
	}
	if(!checkPage404('<%=sRedirectPage%>')){
		(top.actualTop||top).location.href = '<%=sTarget%>';
	}
	else{
		(top.actualTop||top).location.href = 'document_detail_show_404.html';
	}
*/
		(top.actualTop||top).location.href = '<%=sTarget%>';
</script>