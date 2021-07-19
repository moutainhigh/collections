<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.metadata.center.IMetaViewEmployerMgr" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.presentation.util.RequestHelper" %>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
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
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("list_redirect.doc.id.zero","获取栏目ID为[{0}]的栏目失败！"),new int[]{nChannelId}));
		}
		currWebSite = currChannel.getSite();
	}
	else if(nSiteId>0){
		currWebSite = WebSite.findById(nSiteId);	
		if(currWebSite == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("list_redirect.channel.id.zero","获取站点ID为[{0}]的站点失败！"),new int[]{nSiteId}));
		}
	}
	else{
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, LocaleServer.getString("list_redirect.noid","未指定ChannelId和SiteId."));
	}
	int bClassicList = currRequestHelper.getInt("bClassicList", 0);
	String sDefaultListPage = "../document/document_list.html";
	if(bClassicList ==0){
		sDefaultListPage = "../document/document_list.html";
	}
	if(bClassicList ==1){
		sDefaultListPage = "../document/document_classic_list.html";
	}
	String sRedirectPage = sDefaultListPage;
	String s404Message = "";
	if(currChannel != null){
		IMetaViewEmployerMgr m_MetaViewEmployerMgr = (IMetaViewEmployerMgr) DreamFactory
				.createObjectById("IMetaViewEmployerMgr");
		MetaView oMetaView = m_MetaViewEmployerMgr.getViewOfEmployer(currChannel);
		if(oMetaView == null){
			if(currChannel.isLink()){
				sRedirectPage = "../document/document_list_4_linkchannel.html";
			}
			else{
				sRedirectPage = CMyString.showEmpty(currChannel.getContentListPage());
				if("".equals(sRedirectPage)||
					"../document/document_list.html".equals(sRedirectPage)||
					"../document/document_classic_list.html".equals(sRedirectPage)){
					sRedirectPage = sDefaultListPage;
				}
			}
		}else{
			sRedirectPage = currChannel.getContentListPage();
			if(bClassicList ==0){
				/*
				if("".equals(sRedirectPage)||
						"../document/document_list.html".equals(sRedirectPage)||
						"../document/document_list_of_channel.jsp".equals(sRedirectPage)){
					sRedirectPage = "../metaviewdata/metaviewdata_list.html";
				}
				sRedirectPage = "../metaviewdata/metaviewdata_list.html";
				*/
			}
			if(bClassicList ==1){
				/*
				if("".equals(sRedirectPage)||
					"../document/document_classic_list.html".equals(sRedirectPage)||
					"../document/document_list_of_channel.jsp".equals(sRedirectPage)){
					sRedirectPage = "../metaviewdata/metaviewdata_classic_list.html";
				}
				sRedirectPage = "../metaviewdata/metaviewdata_classic_list.html";
				*/
				sRedirectPage = "../application/" + oMetaView.getId() + "/metaviewdata_classic_list.html";
			}
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