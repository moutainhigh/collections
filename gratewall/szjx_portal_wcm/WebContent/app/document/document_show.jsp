<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.presentation.util.RequestHelper" %>
<%@ page import="com.trs.infra.common.WCMTypes"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定,登录校验,参数获取,都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%boolean IS_DEBUG = false;%>
<%
	int nDocumentId	  = currRequestHelper.getInt("DocumentId", 0);
	int nChannelId = currRequestHelper.getInt("ChannelId", 0);
	int nSiteId = currRequestHelper.getInt("SiteId", 0);
	int nCanEdit = currRequestHelper.getInt("ReadOnly", 0);
	Channel currChannel = null;
	WebSite currWebSite = null;
	Document currDocument = null;
	if(nDocumentId > 0){
		currDocument  = Document.findById(nDocumentId);
		if(currDocument == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("object.not.found", "没有找到指定的{1} [ID={0}]."), new String[]{String.valueOf(nDocumentId),WCMTypes.getLowerObjName(Document.OBJ_TYPE)}));
		}
		currChannel = currDocument.getChannel();	
		currWebSite = currChannel.getSite();
	}else if(nChannelId>0){
		currChannel = Channel.findById(nChannelId);	
		if(currChannel == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("object.not.found", "没有找到指定的{1} [ID={0}]."), new String[]{String.valueOf(nChannelId),WCMTypes.getLowerObjName(Channel.OBJ_TYPE)}));
		}
		currWebSite = currChannel.getSite();
	}
	else if(nSiteId>0){
		currWebSite = WebSite.findById(nSiteId);	
		if(currWebSite == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("object.not.found", "没有找到指定的{1} [ID={0}]."), new String[]{String.valueOf(nSiteId),WCMTypes.getLowerObjName(WebSite.OBJ_TYPE)}));
		}
	}
	else{
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, LocaleServer.getString("document_addedit_label_15","未指定ChannelId和SiteId."));
	}
	String sDefaultListPage = "./document_detail.jsp";
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
		if(sRedirectPage==null||"".equals(sRedirectPage)||"../document/document_show.jsp".equals(sRedirectPage)){
			sRedirectPage = sDefaultListPage;
		}
	}
	if("../document/document_detail_show.html".equals(sRedirectPage)){
		sRedirectPage = "../document/document_detail.jsp";
	}
	if(sRedirectPage.indexOf("application") > 0){
		//若sRedirectPage字符串中包含application，即表示是元数据视图应用下的文档的话，查看的页面是根据视图id对应的目录下的目标进行显示的
		int nDocKind = currDocument.getKindId();
		sRedirectPage = "../application/"+nDocKind+"/viewdata_detail.jsp";
	}
	sRedirectPage += "?canEdit=" + nCanEdit;
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
<script>
/*
	function checkPage404(_hrefSrc){
		var r = new Ajax.Request(_hrefSrc,{
			asynchronous:false
		});
		return r.transport.status==404;
	}
	if(!checkPage404('<%=CMyString.filterForJs(sRedirectPage)%>')){
		(top.actualTop||top).location.href = '<%=CMyString.filterForJs(sTarget)%>';
	}
	else{
		(top.actualTop||top).location.href = 'document_detail_show_404.html';
	}
*/
		window.location.href = '<%=CMyString.filterForJs(sTarget)%>';
</script>