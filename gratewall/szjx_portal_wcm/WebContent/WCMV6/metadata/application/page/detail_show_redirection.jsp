<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.presentation.util.RequestHelper" %>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../../../include/public_server.jsp"%>
<%boolean IS_DEBUG = true;%>
<%
	int nObjectId = 0;
	String sObjectId = request.getParameter("objectId");
	if(sObjectId == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "没有指定文档ID！");
	}
	Document currDocument =	Document.findById(Integer.parseInt(sObjectId));
	if(currDocument == null || currDocument.isDeleted()){
		throw new WCMException("没有找到指定文档[" + sObjectId + "]或该文档已被删除！");
	}
	Channel currChannel = currDocument.getChannel();
	if(currChannel == null || currChannel.isDeleted()){
		throw new WCMException("没有找到相应的栏目[文档ID：" + sObjectId + "]或该栏目已被删除！");
	}
	WebSite currWebSite = currChannel.getSite();
	if(currWebSite == null || currWebSite.isDeleted()){
		throw new WCMException("没有找到指定站点[文档ID：" + sObjectId + "]或该站点已被删除！");
	}
	ChnlDoc currChnlDoc = ChnlDoc.findByDocument(currDocument);
	if(currChnlDoc == null || currChnlDoc.isDeleted()){
		throw new WCMException("没有找到指定文档对应的ChnlDoc[文档ID：" + sObjectId + "]！");
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
		case 4:
			//资源库
			int nViewId = currDocument.getPropertyAsInt("dockind", 0);
			if(nViewId != 0){
				String params = "?viewId=" + nViewId;
				params += "&objectId=" + currDocument.getId();
				params += "&channelId=" + currChannel.getId();
				sDefaultListPage = "../metadata/application/" + nViewId + "/detail_of_view.jsp" + params;
			}
			break;
	}
	String sRedirectPage = sDefaultListPage;
	String s404Message = "";
	if(currChannel != null && currWebSite.getPropertyAsInt("SiteType",0) != 4){
		if(currChannel.isLink()){
			//TODO?
		}
//		sRedirectPage = currChannel.getContentListPage();
		sRedirectPage = currChannel.getPropertyAsString("ContentShowPage");
		if(sRedirectPage==null||"".equals(sRedirectPage)||"../document/document_show.jsp".equals(sRedirectPage)){
			sRedirectPage = sDefaultListPage;
		}
	}
	if(sRedirectPage.indexOf("?") < 0){
		String params = "";
		params += "?DocumentId=" + currDocument.getId();
		params += "&ChannelId=" + currChannel.getId();
		params += "&RecId=" + currChnlDoc.getId();
		sRedirectPage += params;
	}
	if(IS_DEBUG){
		System.out.println(sRedirectPage);
	}
%>
<script language="javascript">
<!--
		var index = window.location.href.lastIndexOf("/");
		var sHref = window.location.href.substr(0, index);
		var urlPrefix = sHref + "/../../";
//-->
</script>
<script src="../../../js/com.trs.util/Common.js"></script>
<script>
	function checkPage404(_hrefSrc){
		var r = new Ajax.Request(_hrefSrc,{
			asynchronous:false
		});
		return r.transport.status==404;
	}
	if(!checkPage404(urlPrefix + '<%=sRedirectPage%>')){
		(top.actualTop||top).location.href = urlPrefix + '<%=sRedirectPage%>';
	}
	else{
		(top.actualTop||top).location.href = urlPrefix + '../document/document_detail_show_404.html';
	}
</script>