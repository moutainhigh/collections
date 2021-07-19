<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.common.publish.persistent.element.PublishElementFactory" %>
<%@ page import="com.trs.components.wcm.publish.WCMContentPublishConfig" %>
<%@ page import="com.trs.components.wcm.publish.WCMFolderPublishConfig" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishElement" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishFolder" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishContent" %>
<%@ page import="com.trs.components.common.publish.persistent.template.Template" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<%@ page import="com.trs.infra.common.WCMException" %>

<%
try{
%>
<%@include file="../include/findbyid_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%!
	private WCMFolderPublishConfig getPublishConfig(User loginUser,CMSObj obj) throws WCMException{
		IPublishFolder publishFolder = (IPublishFolder)PublishElementFactory.makeElementFrom(obj);
		WCMFolderPublishConfig folderConfig = new WCMFolderPublishConfig(publishFolder);
		return folderConfig;
	}
%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof WebSite)){
		throw new WCMException(CMyString.format(LocaleServer.getString("siterecycle_findbyid.jsp.service", "服务(com.trs.ajaxservice.WebSiteServiceProvider.findbyid)返回的对象类型不为WebSite，而为{0}，请确认。"), new Object[]{result.getClass()}));
	}
	boolean bCanEdit = false;
	String sEditable = bCanEdit?"editable":"readonly";
	WebSite obj = (WebSite)result;
	WCMFolderPublishConfig siteConfig = getPublishConfig(loginUser,obj);
	int nOutlineTempId = siteConfig.getDefaultOutlineTemplateId();
	Template outlineTemp = Template.findById(nOutlineTempId);
	String sOutlineTempName = (outlineTemp != null)?outlineTemp.getName():LocaleServer.getString("siterecycle_findbyid.label.none", "无");
	int nDetailTempId = siteConfig.getDetailTemplateId();
	Template DetailTemp = Template.findById(nDetailTempId);
	String sDetailTempName = (DetailTemp != null)?DetailTemp.getName():LocaleServer.getString("siterecycle_findbyid.label.none", "无");
	String sDataPath = siteConfig.getDataPath();
	String sRootDomain = siteConfig.getRootDomain();
	int nSiteId = obj.getSiteId();
	String sSiteName = obj.getName();
	String sSiteDesc = obj.getDesc();
	String sCruser = obj.getPropertyAsString("cruser");
	String sCrtime = obj.getPropertyAsString("crtime");

%>
<!--//TODO type findbyid here-->

<div class="attribute_row SiteDesc <%=sEditable%>">
	<span class="wcm_attr_value" _fieldName="SiteDesc" _fieldValue="<%=sSiteDesc%>" title="<%=sSiteDesc%>">
		<%=sSiteDesc%>&nbsp;
	</span>
</div>
<div class="attribute_row_sep"></div>
<div class="attribute_row descinfo readonly">
	<span title="<%=LocaleServer.getString("website.label.siteid", "站点编号")%>:&nbsp;<%=nSiteId%>&#13;<%=LocaleServer.getString("website.label.cruser", "创建者")%>:&nbsp;<%=sCruser%>&#13;<%=LocaleServer.getString("website.label.crtime", "创建时间")%>:&nbsp;<%=sCrtime%>&#13" style="white-space:nowrap;overflow:hidden;" >
		<span WCMAnt:param="siterecycle_findbyid.jsp.cruser">用户</span><span class="value"><%=sCruser%></span><span WCMAnt:param="siterecycle_findbyid.jsp.crtime">创建于</span><span class="value"><%=sCrtime%></span>
	</span>
</div>
<div class="attribute_row_sep"></div>
<div class="attribute_row SiteName readonly">
	<span class="wcm_attr_name" WCMAnt:param="siterecycle_findbyid.jsp.SiteName">站点名称:</span>
	<span class="wcm_attr_value" title="<%=sSiteName%>">
		<%=sSiteName%>&nbsp;
	</span>
</div>
<div class="attribute_row OutlineTempName readonly">
	<span class="wcm_attr_name" WCMAnt:param="siterecycle_findbyid.jsp.OutlineTempName">首页模板:</span>
	<span class="wcm_attr_value">
		<%=sOutlineTempName%>&nbsp;
	</span>
</div>
<div class="attribute_row DetailTempName readonly">
	<span class="wcm_attr_name" WCMAnt:param="siterecycle_findbyid.jsp.DetailTempName">细缆模板:</span>
	<span class="wcm_attr_value" >
		<%=sDetailTempName%>&nbsp;
	</span>
</div>
<div class="attribute_row_sep"></div>


<div class="attribute_row datapath readonly">
	<span class="wcm_attr_name" WCMAnt:param="siterecycle_findbyid.jsp.datapath">存放位置:</span>
	<span class="wcm_attr_value" _fieldName="DataPath" _fieldValue="<%=sDataPath%>">
		<%=sDataPath%>&nbsp;
	</span>
</div>
<div class="attribute_row RootDomain">
	<span class="wcm_attr_name" WCMAnt:param="siterecycle_findbyid.jsp.RootDomain" style="width:100px">站点HTTP(S):</span>
	
</div>
<div class="attribute_row RootDomain <%=sEditable%>">
	<span class="wcm_attr_value" _fieldName="RootDomain" _fieldValue="<%=sRootDomain%>" title="<%=sRootDomain%>" style="width:120px; margin-left:2px">
		<%=sRootDomain%>&nbsp;
	</span>
</div>

<%
}catch(Throwable tx){
	tx.printStackTrace();
	throw new WCMException(LocaleServer.getString("siterecycle_findbyid.runtime.error", "siterecycle_findbyid.jsp运行期异常!"), tx);
}
%>