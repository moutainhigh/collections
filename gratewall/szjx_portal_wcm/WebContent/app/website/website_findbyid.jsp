<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.components.common.publish.persistent.element.PublishElementFactory" %>
<%@ page import="com.trs.components.wcm.publish.WCMContentPublishConfig" %>
<%@ page import="com.trs.components.wcm.publish.WCMFolderPublishConfig" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishElement" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishFolder" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishContent" %>
<%@ page import="com.trs.components.common.publish.persistent.template.Template" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>

<%
try{
%>
<%@include file="../include/findbyid_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%!
	private boolean hasRight(User _currUser, CMSObj _objCurrent,int _nRightIndex) throws WCMException{
		return AuthServer.hasRight(_currUser,_objCurrent,_nRightIndex);
	}
%>
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
		throw new WCMException(CMyString.format(LocaleServer.getString("website_findbyid.jsp.service", "服务(com.trs.ajaxservice.WebSiteServiceProvider.findbyid)返回的对象类型不为WebSite，而为[{0}]，请确认。"), new Object[]{result.getClass()}));
	}
	
	WebSite obj = (WebSite)result;
	boolean bCanEdit = hasRight(loginUser, obj, WCMRightTypes.SITE_EDIT);
	String sEditable = bCanEdit?"editable":"readonly";
	boolean bHasTemplateAddRight = hasRight(loginUser, obj, WCMRightTypes.TEMPLATE_ADD);
	boolean bHasTemplateEditRight = hasRight(loginUser, obj, WCMRightTypes.TEMPLATE_EDIT);
	WCMFolderPublishConfig siteConfig = getPublishConfig(loginUser,obj);
	int nOutlineTempId = siteConfig.getDefaultOutlineTemplateId();
	Template outlineTemp = Template.findById(nOutlineTempId);
	String sOutlineTempName = CMyString.transDisplay((outlineTemp != null)?outlineTemp.getName():LocaleServer.getString("website_findbyid.label.none", "无"));
	boolean bHasOutTemplateRight = outlineTemp != null ? bCanEdit && bHasTemplateEditRight : (bCanEdit && bHasTemplateAddRight);
	boolean bCanSetTemplate = hasRight(loginUser, obj, WCMRightTypes.SITE_SETTEMPLATE);
	boolean bOutLineIsVisual = outlineTemp != null ? outlineTemp.getPropertyAsBoolean("Visual", false) : false;

	int nDetailTempId = siteConfig.getDetailTemplateId();
	Template detailTemp = Template.findById(nDetailTempId);
	String sDetailTempName = CMyString.transDisplay((detailTemp != null)?detailTemp.getName():LocaleServer.getString("website_findbyid.label.none", "无"));
	boolean bHasDetailTemplateRight = detailTemp != null ? bCanEdit && bHasTemplateEditRight : (bCanEdit && bHasTemplateAddRight);
	boolean bDetailTemplateIsVisual = detailTemp != null ? detailTemp.getPropertyAsBoolean("Visual", false) : false;

	String sDataPath = CMyString.transDisplay(siteConfig.getDataPath());
	String sRootDomain = CMyString.transDisplay(siteConfig.getRootDomain());
	int nSiteId = obj.getSiteId();
	String sSiteName = CMyString.transDisplay(obj.getName());
	String sSiteDesc = CMyString.transDisplay(obj.getDesc());
	String sCruser = CMyString.transDisplay(obj.getPropertyAsString("cruser"));
	String sCrtime = obj.getPropertyAsDateTime("crtime").toString(CMyDateTime.DEF_DATETIME_FORMAT_PRG);
	String sHostServiceAttr = "_serviceId=\"wcm61_website\"";
	String sHostMethodAttr = "_methodName=\"savesitepublishconfig\"";

%>
<!--//TODO type findbyid here-->
<SCRIPT LANGUAGE="JavaScript">
//datapath单独放到这是为了避免和栏目的相互覆盖
ValidationHelper.bindValidations([
	{
		renderTo : 'DataPath',
		type :'common_char',
		required :'',
		desc : wcm.LANG.WEBSITE_17||'存放位置',
		max_len :'50',
		methods : PageContext.validExistProperty(function(oParams){
			var wcmEvent = PageContext.event;
			var obj = wcmEvent.getObjs().getAt(0) || wcmEvent.getHost();
			Ext.apply(oParams, {
				SiteId : obj.getId()
			});
		})		
	}
]);

</SCRIPT>
<div class="attribute_row main_attr SiteDesc <%=sEditable%>">
	<span class="wcm_attr_value" <%=sHostServiceAttr%> _fieldName="SiteDesc" _fieldValue="<%=sSiteDesc%>" title="<%=sSiteDesc%>" >
		<%=sSiteDesc%>&nbsp;
	</span>
</div>
<div class="attribute_row_sep"></div>
<div class="attribute_row descinfo readonly">
	<span title="<%=LocaleServer.getString("website.label.siteid", "站点编号")%>:&nbsp;<%=nSiteId%>&#13;<%=LocaleServer.getString("website.label.cruser", "创建者")%>:&nbsp;<%=sCruser%>&#13;<%=LocaleServer.getString("website.label.crtime", "创建时间")%>:&nbsp;<%=sCrtime%>&#13" style="white-space:nowrap;overflow:hidden;" >
		<span WCMAnt:param="website_findbyid.jsp.cruser">用户</span><span class="value"><%=sCruser%></span><span WCMAnt:param="website_findbyid.jsp.crtime">创建于</span><span class="value"><%=sCrtime%></span>
	</span>
</div>
<div class="attribute_row_sep"></div>
<div class="attribute_row SiteName <%=sEditable%>">
	<span class="wcm_attr_name" WCMAnt:param="website_findbyid.jsp.SiteName">唯一标识:</span>
	<span class="wcm_attr_value" title="<%=sSiteName%>" <%=sHostServiceAttr%> _fieldName="SiteName" _fieldValue="<%=sSiteName%>" style="color:#09549F" >
		<%=sSiteName%>&nbsp;
	</span>
</div>
<div class="attribute_row readonly">
	<span class="wcm_attr_name" WCMAnt:param="website_findbyid.jsp.OutlineTempName">首页模板:</span>
	<span class="wcm_attr_value"  style="width:60px;color:#09549F">
		<%if(bHasOutTemplateRight){%>
		<a href="#" onclick="websitehelper.PageContext.editTemplate(<%=nOutlineTempId%>, 1, <%=bOutLineIsVisual%>); return false;" style="cursor:pointer" name="website_editTemplate" title="<%=sOutlineTempName%>[ID-<%=nOutlineTempId%>]&#13;<%=LocaleServer.getString("website.label.explain", "提示:点击进入模板新建/修改页面")%>"  templateId="<%=nOutlineTempId%>"><span style="color:#09549F"><%=sOutlineTempName%>&nbsp;</span></a>
		<%}else{%>
		<a name="website_editTemplate" title="<%=sOutlineTempName%>[ID-<%=nOutlineTempId%>]"><span style="color:gray;"><%=sOutlineTempName%>&nbsp;</span></a>
		<%}%>
	</span>
	<%if(bCanSetTemplate){%>
	<span><span onclick="websitehelper.PageContext.selectTemplate(<%=nOutlineTempId%>, 1); return false;" style="position:absolute;width:16px;height:16px;cursor: pointer;background:url(../images/icon/icons.gif) -22px -60px no-repeat;" title="选择模板" name="website_selectTemplate" WCMAnt:paramattr="title:website_findbyid.jsp.selecttemp">&nbsp;</span></span>
	<%}%>
</div>
<div class="attribute_row  readonly">
	<span class="wcm_attr_name" WCMAnt:param="website_findbyid.jsp.DetailTempName">细览模板:</span>
	<span class="wcm_attr_value" style="width:60px;color:#09549F">	
		<%if(bHasDetailTemplateRight){%>
		<a href="#" onclick="websitehelper.PageContext.editTemplate(<%=nDetailTempId%>, 2, <%=bDetailTemplateIsVisual%>); return false;" style="cursor:pointer" title="<%=sDetailTempName%>[ID-<%=nDetailTempId%>]&#13;<%=LocaleServer.getString("website.label.explain", "提示:点击进入模板新建/修改页面")%>" name="website_editTemplate" templateId="<%=nDetailTempId%>" ><span style="color:#09549F"><%=sDetailTempName%>&nbsp;</span></a>
		<%}else{%>
		<a name="website_editTemplate" title="<%=sDetailTempName%>[ID-<%=nDetailTempId%>]"><span style="color:gray"><%=sDetailTempName%>&nbsp;</span></a>
		<%}%>
	</span>
	<%if(bCanSetTemplate){%>
	<span><span onclick="websitehelper.PageContext.selectTemplate(<%=nDetailTempId%>, 2); return false;" style="position:absolute;width:16px;height:16px;cursor: pointer;background:url(../images/icon/icons.gif) -22px -60px no-repeat;" title="选择模板" WCMAnt:paramattr="title:website_findbyid.jsp.selecttemp">&nbsp;</span></span>
	<%}%>
</div>
<div class="attribute_row_sep"></div>


<div class="attribute_row datapath <%=sEditable%>">
	<span class="wcm_attr_name" WCMAnt:param="website_findbyid.jsp.datapath">存放位置:</span>
	<span class="wcm_attr_value" _fieldName="DataPath" _serviceId="wcm6_publish" <%=sHostMethodAttr%> _fieldValue="<%=sDataPath%>" title="<%=sDataPath%>" style="color:#09549F" >
		<%=sDataPath%>&nbsp;
	</span>
</div>
<div class="attribute_row RootDomain">
	<span class="wcm_attr_name" WCMAnt:param="website_findbyid.jsp.RootDomain" style="width:100px">站点HTTP(S):</span>
	
</div>
<div class="attribute_row RootDomain <%=sEditable%>">
	<span class="wcm_attr_value" _fieldName="RootDomain" _serviceId="wcm6_publish" <%=sHostMethodAttr%> _fieldValue="<%=sRootDomain%>" title="<%=sRootDomain%>" style="width:150px; margin-left:2px;color:#09549F">
		<%=sRootDomain%>&nbsp;
	</span>
</div>

<%
}catch(Throwable tx){
	tx.printStackTrace();
	throw new WCMException(LocaleServer.getString("website_findbyid.runtime.error", "website_findbyid.jsp运行期异常!"),tx);
}
%>