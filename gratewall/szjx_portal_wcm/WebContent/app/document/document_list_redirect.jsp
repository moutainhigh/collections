<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.presentation.util.RequestHelper" %>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定,登录校验,参数获取,都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%boolean IS_DEBUG = false;%>
<%
	int nChannelId = currRequestHelper.getInt("channelid", 0);
	int nSiteId = currRequestHelper.getInt("siteid", 0);
	Channel currChannel = null;
	WebSite currWebSite = null;
	if(nChannelId>0){
		currChannel = Channel.findById(nChannelId);	
		if(currChannel == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("document_addedit_label_3","获取栏目ID为[{0}]的栏目失败!"),new int[]{nChannelId}));
		}
		currWebSite = currChannel.getSite();
	}
	else if(nSiteId>0){
		currWebSite = WebSite.findById(nSiteId);	
		if(currWebSite == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("document_addedit_label_13","获取站点ID为[{0}]的站点失败!"),new int[]{nSiteId}));
		}
	}
	else{
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, LocaleServer.getString("document_addedit_label_15","未指定ChannelId和SiteId."));
	}
	String sDefaultListPage = "../document/document_list.html";
	switch(currWebSite.getPropertyAsInt("SiteType",0)){
		case 0:
			//文字库
			break;
		case 1:
			//TODO
			//图片库
			sDefaultListPage = "../photo/photo_thumb.html";
			break;
		case 2:
			//视频库
			sDefaultListPage = "../video/video_thumb.html";
			break;
	}
	String sRedirectPage = sDefaultListPage;
	String s404Message = "";
	if(currChannel != null){
		if(currChannel.isLink()){
			sRedirectPage = "../document/document_list_4_linkchannel.html";
		}
		else{
			sRedirectPage = CMyString.showEmpty(currChannel.getContentListPage());
			if("".equals(sRedirectPage)||
				"../document/document_normal_index.html".equals(sRedirectPage)||
				"../document/document_list_of_channel.jsp".equals(sRedirectPage)){
				sRedirectPage = sDefaultListPage;
			}
			if("../infoview/infoview_document_list_of_channel.jsp".equals(sRedirectPage))
				sRedirectPage = "../infoview/infoviewdoc_list.html";
		}
	}
	if(IS_DEBUG){
		System.out.println(sRedirectPage);
	}
	char cJoin = (sRedirectPage.indexOf("?")==-1) ? '?' : '&';
	String sTarget = sRedirectPage + cJoin + request.getQueryString();
	if(currChannel != null && currChannel.isLink()){
		String sQueryStr = request.getQueryString().replaceAll("&linkUrl=[^&]*(&|$)", "$1");
		sTarget = sRedirectPage + cJoin + sQueryStr + "&linkUrl="
						+ java.net.URLEncoder.encode(currChannel.getLinkUrl(), "UTF-8");
	}
	boolean bNotFittable = sRedirectPage.indexOf("&fit=0")!=-1 
							|| sRedirectPage.indexOf("?fit=0")!=-1;
	String sCheckUrl = sTarget;
	if(bNotFittable){		
		sTarget = "../include/tab_fittable.html?tabType=document&tabUrl=" 
			+ java.net.URLEncoder.encode(sTarget, "UTF-8")
			+ request.getQueryString();
		//response.sendRedirect(sTarget);
	}else{
		//response.sendRedirect(sTarget);
	}
	String sDefaultUrl = sDefaultListPage+sTarget.substring(sRedirectPage.length());
	out.clear();%>
<script language="javascript">
<!--
	var sDefaultUrl = '<%=CMyString.filterForJs(sDefaultUrl)%>';
	var sTarget = '<%=CMyString.filterForJs(sTarget)%>';
	var sCheckUrl = '<%=CMyString.filterForJs(sCheckUrl)%>';
	try{
		if(!/^http/i.test(sCheckUrl)){
			sCheckUrl = 'app/'+sCheckUrl;
		}
		if(top.wcm.CheckerOf404Page.handle(sCheckUrl)){
			sTarget = sDefaultUrl;
		}
	}catch(error){
	}
	window.location.href = sTarget;
//-->
</script>