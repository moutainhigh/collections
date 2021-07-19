<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSites" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channels" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Documents" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.domain.DocumentMgr" %>
<%@ page import="com.trs.components.wcm.content.domain.ChannelMgr" %>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.components.metadata.center.MetaViewData" %>
<%@ page import="com.trs.components.metadata.center.IMetaViewEmployerMgr" %>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%
	// 2. 权限校验
/*
	if (!loginUser.isAdministrator()) {
		throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT,
				"您不是管理员，没有同步文档标题的权限！");
	}
*/

	//获得站点IDS
	String sSiteIds = null;
	String[] aSiteIds = {"siteIds", "siteids", "SiteIds"};
	for(int i = 0; i < aSiteIds.length; i++){
		sSiteIds = request.getParameter(aSiteIds[i]);
		if(sSiteIds != null){
			break;
		}
	}

	//获得栏目IDS
	String sChannelIds = null;
	String[] aChannelIds = {"channelIds", "channelids", "ChannelIds"};
	for(int i = 0; i < aChannelIds.length; i++){
		sChannelIds = request.getParameter(aChannelIds[i]);
		if(sChannelIds != null){
			break;
		}
	}

	if(sSiteIds == null && sChannelIds == null){
		throw new WCMException(LocaleServer.getString("syn_document.jsp.label.no_appoint_siteorchannel", "没有指定要同步文档的站点或栏目ID[siteIds/channelIds]"));
	}
	
	int[] aResult = new int[2];
	//同步站点集合下的文档
	if(sSiteIds != null){
		synDocument(loginUser, WebSites.findByIds(loginUser, sSiteIds), aResult);
	}

	//同步栏目集合下的文档
	if(sChannelIds != null){
		synDocument(loginUser, Channels.findByIds(loginUser, sChannelIds), aResult);
	}

	out.println(LocaleServer.getString("syn_document.jsp.label.syn_doc_finished", "文档同步操作完成")
	+"．<br>");
	   out.println(CMyString.format(LocaleServer.getString("syn_document.jsp.label.involve_doc_nums", "本次同步涉及文档总数：[<b style='color:red;'>{0}</b>]<br>)"),new int[]{aResult[0]}));
	   out.println(CMyString.format(LocaleServer.getString("syn_document.jsp.label.not_handle_nums","其中[<b style='color:red;'>{0}</b>]篇没有权限处理<br>"),new int[]{aResult[1]}));

%>

<%!
	/**
	*同步站点集合下的文档
	*/
	private void synDocument(User currUser, WebSites sites, int[] aResult)throws Exception{
		for (int i = 0, nSize = sites.size(); i < nSize; i++) {
			WebSite site = (WebSite) sites.getAt(i);
			if (site == null)
				continue;
			synDocument(currUser, site, aResult);
		}
	}

	/**
	*同步站点下的文档
	*/
	private void synDocument(User currUser, WebSite site, int[] aResult)throws Exception{
		if(site == null) return;
		Channels channels = new ChannelMgr().getChannels(site.getId(), null);
		synDocument(currUser, channels, aResult);
	}

	/**
	*同步栏目集合下的文档
	*/
	private void synDocument(User currUser, Channels channels, int[] aResult)throws Exception{
		for (int i = 0, nSize = channels.size(); i < nSize; i++) {
			Channel channel = (Channel) channels.getAt(i);
			if (channel == null)
				continue;
			synDocument(currUser, channel, aResult);
		}
	}

	/**
	*同步栏目下的文档
	*/
	private void synDocument(User currUser, Channel channel, int[] aResult)throws Exception{
		Documents documents = getDocuments(currUser, channel);
		if(documents == null) return;
		for (int i = 0, nSize = documents.size(); i < nSize; i++) {
			Document document = (Document) documents.getAt(i);
			if (document == null)
				continue;
			synDocument(currUser, document, aResult);
		}
	}

	/**
	*获取栏目下资源库类型的文档
	*/
	private Documents getDocuments(User currUser, Channel channel)throws Exception{
		MetaView metaView = getMetaViewEmployerMgr().getViewOfEmployer(channel);
		if(metaView == null) return null;
		WCMFilter filter = new WCMFilter("", "DocKind=? and DocChannel=?", "");
		filter.addSearchValues(metaView.getId());
		filter.addSearchValues(channel.getId());
		return Documents.openWCMObjs(currUser, filter);		
	}

	/**
	*同步单篇文档
	*/
	private void synDocument(User currUser, Document document, int[] aResult)throws Exception{
		if(document == null){
			return;
		}
		aResult[0]++;
		//校验权限
        if (!AuthServer.hasRight(currUser, document.getChannel(), WCMRightTypes.DOC_EDIT)){
			aResult[1]++;
			//skip the no right document.
			return;
		}
		int docId = document.getId();
		MetaViewData metaViewData = MetaViewData.findById(docId);
		metaViewData.synDocumentFieldValueWithRule(document);
		//document.setTitle(metaViewData.getTitle());
		//document.setContent(metaViewData.getContent());
		getDocumentMgr().save(document);
	}

	private DocumentMgr getDocumentMgr(){
		return (DocumentMgr) DreamFactory.createObjectById("DocumentMgr");	
	}

	private IMetaViewEmployerMgr getMetaViewEmployerMgr(){
		return (IMetaViewEmployerMgr) DreamFactory.createObjectById("IMetaViewEmployerMgr");	
	}
%>