<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channels" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.Documents" %>
<%@ page import="com.trs.service.ServiceHelper" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishFolder" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishContent" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishElement" %>
<%@ page import="com.trs.components.common.publish.persistent.element.PublishElementFactory" %>
<%@ page import="com.trs.components.wcm.publish.WCMFolderPublishConfig" %>
<%@ page import="com.trs.components.common.publish.persistent.template.Template" %>
<%@ page import="com.trs.ajaxservice.xmlconvertors.AppendixToXML" %>
<%@ page import="com.trs.ajaxservice.xmlconvertors.RelationToXML" %>
<%@ page import="com.trs.components.wcm.content.domain.AppendixMgr" %>
<%@ page import="com.trs.components.wcm.content.domain.RelationMgr" %>
<%@ page import="com.trs.components.wcm.content.persistent.Appendix" %>
<%@ page import="com.trs.components.wcm.content.persistent.Appendixes" %>
<%@ page import="com.trs.components.wcm.content.persistent.Relations" %>
<%@ page import="com.trs.components.wcm.content.domain.auth.DocumentAuthServer" %>
<%@ page import="com.trs.service.IChannelService" %>
<%@ page import="com.trs.components.wcm.content.persistent.Documents" %>
<%@ page import="com.trs.service.IDocumentService" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.service.ITemplateService" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.components.common.publish.domain.template.ITemplateEmployCacheMgr" %>
<%@ page import="com.trs.components.metadata.definition.ClassInfo" %>
<%@ page import="com.trs.components.metadata.service.UserRelateInfoMaker" %>
<%@ page import="com.trs.components.metadata.service.DefaultChannelMakerCenter" %>
<%@ page import="com.trs.components.metadata.MetaDataConstants" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewFields" %>
<%@ page import="com.trs.components.metadata.definition.IMetaDataDefMgr" %>
<%@ page import="com.trs.components.metadata.center.IMetaViewEmployerMgr" %>
<%@ page import="com.trs.components.metadata.center.MetaViewData" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewField" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.components.metadata.definition.IMetaDataDefCacheMgr" %>
<%@ page import="com.trs.components.metadata.definition.ClassInfos" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channels" %>
<%@ page import="com.trs.components.metadata.definition.MetaView"%>
<%@ page import="com.trs.components.metadata.center.IMetaViewEmployerMgr"%> 
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="com.trs.components.wcm.resource.DocLevel"%>
<%@ page import="com.trs.components.wcm.resource.DocLevels"%>
<%@ page import="com.trs.components.wcm.resource.Keyword" %>
<%@ page import="com.trs.components.wcm.resource.Keywords" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor"%>
<%@ page import="com.trs.ajaxservice.WCMProcessServiceHelper"%>
<%@ page import="com.trs.cms.auth.domain.AuthServer"%>
<%@ page import="com.trs.components.wcm.resource.Status"%>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc"%>
<%@ page import="com.trs.presentation.plugin.PluginConfig"%>
<%@ page import="com.trs.infra.I18NMessage"%>
<%@ page import="com.trs.ajaxservice.PublishServiceProvider"%>
<%@ page import="com.trs.service.IWCMProcessService" %>
<%@ page import="com.trs.cms.process.definition.Flow" %>
<%@ page import="com.trs.components.common.job.*" %>
<%@ page import="com.trs.components.wcm.publish.domain.job.WithDrawJobWorker" %>
<%@ page import="com.trs.components.wcm.publish.WCMContentPublishConfig" %>
<%@ page import="com.trs.cms.process.definition.FlowEmployMgr" %>
<%
response.setHeader("Pragma","no-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires",0); 
response.setHeader("Cache-Control","private"); 
%>
<%@include file="/app/include/public_processor.jsp"%>