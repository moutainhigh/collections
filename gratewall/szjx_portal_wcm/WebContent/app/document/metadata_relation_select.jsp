<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.metadata.center.IMetaViewEmployerMgr" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.presentation.util.RequestHelper" %>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定,登录校验,参数获取,都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%boolean IS_DEBUG = false;%>
<%
	int nChannelId = currRequestHelper.getInt("channelid", 0);
	Channel currChannel = null;
	if(nChannelId>0){
		currChannel = Channel.findById(nChannelId);	
		if(currChannel == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("document_addedit_label_3","获取栏目ID为[{0}]的栏目失败!"),new int[]{nChannelId}));
		}
	}
	else{
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, LocaleServer.getString("metadata_relation_select.document_addedit_label_15","未指定ChannelId"));
	}
	String sDefaultPage = "../document/document_relation_select.html";
	IMetaViewEmployerMgr m_oMetaViewEmployerMgr = (IMetaViewEmployerMgr) DreamFactory
			.createObjectById("IMetaViewEmployerMgr");
	MetaView view = null;
	int nViewId = 0;
	if (currChannel != null) {
		view = m_oMetaViewEmployerMgr.getViewOfEmployer(currChannel);
	}
	if(view != null){
		nViewId = view.getId();
		sDefaultPage = "../application/"+nViewId+"/metaviewdata_select_list.html";
	}

	String sRedirectPage = sDefaultPage;
	char cJoin = (sRedirectPage.indexOf("?")==-1) ? '?' : '&';
	String sTarget = sRedirectPage + cJoin + request.getQueryString();
	//防止CRLF注入，去除回车换行
	sTarget = sTarget.replaceAll("(?i)%0d|%0a","");
	response.sendRedirect(sTarget);
	out.clear();%>