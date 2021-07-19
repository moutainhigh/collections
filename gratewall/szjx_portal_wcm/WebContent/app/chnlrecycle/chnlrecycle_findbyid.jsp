<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.components.wcm.publish.WCMFolderPublishConfig" %>
<%@ page import="com.trs.components.common.publish.persistent.template.Template" %>
<%@ page import="com.trs.components.common.publish.persistent.element.PublishElementFactory" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishFolder" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.common.WCMTypes" %>
<%@include file="../include/findbyid_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%!
	private String getParentName(Channel _chnl) throws WCMException{
		int parentChannelId = _chnl.getParentId();
		if(parentChannelId == 0){
				WebSite parentChannel = _chnl.getSite();
				return (parentChannel != null)?LocaleServer.getString("channel.label.none","无"):"";
			}else{
				Channel parentChannel = Channel.findById(parentChannelId);
				return (parentChannel != null)?parentChannel.getName():"";
			}
	}
	
	private WCMFolderPublishConfig getPublishConfig(CMSObj obj) throws WCMException{
		IPublishFolder publishFolder = (IPublishFolder)PublishElementFactory.makeElementFrom(obj);
		WCMFolderPublishConfig folderConfig = new WCMFolderPublishConfig(publishFolder);
		return folderConfig;
	}
%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof Channel)){
		throw new WCMException(CMyString.format(LocaleServer.getString("chnlrectycle_findbyid.jsp.servicenoobject","服务(com.trs.ajaxservice.ChannelServiceProvider.findbyid)返回的对象类型不为Channel，而为{0}，请确认。"),new Object[]{result.getClass()}));
	}
	Channel obj = (Channel)result;
	int nStatus = obj.getStatus();
	if(nStatus == 0){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("chnlrecycle_findbyid.object.not.found", "无法找到栏目[ID={0}]."), new String[]{String.valueOf(obj.getId()), WCMTypes.getLowerObjName(Channel.OBJ_TYPE)}));
	}
	boolean bCanEdit = false;
	String sEditable = bCanEdit?"editable":"readonly";
	int nChnlId = obj.getId();
	String sChnlDesc = obj.getDesc();
	String sChnlName = obj.getName();
	String sParentName = getParentName(obj);
	WCMFolderPublishConfig chnlConfig = getPublishConfig(obj);
	int nOutlineTempId = chnlConfig.getDefaultOutlineTemplateId();
	Template outlineTemp = Template.findById(nOutlineTempId);
	String sOutlineTempName = (outlineTemp != null)?outlineTemp.getName():LocaleServer.getString("chnlrecycle_findbyid.label.none", "无");
	int nDetailTempId = chnlConfig.getDetailTemplateId();
	Template DetailTemp = Template.findById(nDetailTempId);
	String sDetailTempName = (DetailTemp != null)?DetailTemp.getName():LocaleServer.getString("chnlrecycle_findbyid.label.none", "无");
	//String sOutlineFile = obj.getOutlineFile();
	//String sDetailFile = obj.getDetailFile();
	String sTypeDesc = obj.getTypeDesc();
	String sDataPath = chnlConfig.getDataPath();
	//String sDataPath = obj.getPropertyAsString("chnldatapath");
	String sCruser = obj.getPropertyAsString("cruser");
	String sCrtime = obj.getPropertyAsString("crtime");
%>
<!--//TODO type findbyid here-->
<div class="attribute_row SiteDesc <%=sEditable%>">
	<span class="wcm_attr_value" _fieldName="ChnlDesc" _fieldValue="<%=sChnlDesc%>" title="<%=sChnlDesc%>" style="font-size:14px;color:black">
		<%=sChnlDesc%>&nbsp;
	</span>
</div>
<div class="attribute_row_sep"></div>
<div class="attribute_row descinfo readonly">
	<span title="<%=LocaleServer.getString("channel.label.channelid", "栏目编号")%>:&nbsp;<%=nChnlId%>&#13;<%=LocaleServer.getString("channel.label.cruser", "创建者")%>:&nbsp;<%=sCruser%>&#13;<%=LocaleServer.getString("channel.label.crtime", "创建时间")%>:&nbsp;<%=sCrtime%>&#13" style="white-space:nowrap;overflow:hidden;" >
		<span WCMAnt:param="chnlrecycle_findbyid.jsp.cruser">用户</span><span class="value" style="color:black"><%=sCruser%></span><span WCMAnt:param="chnlrecycle_findbyid.jsp.crtime">创建于</span><span class="value" style="color:black"><%=sCrtime%></span>
	</span>
</div>
<div class="attribute_row_sep"></div>
<div class="attribute_row ChnlName readonly">
	<span class="wcm_attr_name" WCMAnt:param="chnlrecycle_findbyid.jsp.ChnlName">唯一标识:</span>
	<span class="wcm_attr_value" title="<%=sChnlName%>">
		<%=sChnlName%>&nbsp;
	</span>
</div>
<div class="attribute_row TypeDesc readonly">
	<span class="wcm_attr_name" WCMAnt:param="chnlrecycle_findbyid.jsp.TypeDesc">栏目类型:</span>
	<span class="wcm_attr_value" title="<%=sTypeDesc%>">
		<%=sTypeDesc%>&nbsp;
	</span>
</div>
<%
	if(obj.isOnlySearch() || !obj.isVirtual()){		
%>
<div class="attribute_row ParentName readonly">
	<span class="wcm_attr_name" WCMAnt:param="chnlrecycle_findbyid.jsp.ParentName">栏目位置:</span>
	<span class="wcm_attr_value" title="<%=sParentName%>">
		<%=sParentName%>&nbsp;
	</span>
</div>
<div class="attribute_row_sep"></div>
<div class="attribute_row OutlineTempName readonly">
	<span class="wcm_attr_name" WCMAnt:param="chnlrecycle_findbyid.jsp.OutlineTempName">首页模板:</span>
	<span class="wcm_attr_value">
		<%=sOutlineTempName%>&nbsp;
	</span>
</div>
<div class="attribute_row DetailTempName readonly">
	<span class="wcm_attr_name" WCMAnt:param="chnlrecycle_findbyid.jsp.DetailTempName">细缆模板:</span>
	<span class="wcm_attr_value" >
		<%=sDetailTempName%>&nbsp;
	</span>
</div>
<div class="attribute_row_sep"></div>


<div class="attribute_row datapath readonly">
	<span class="wcm_attr_name" WCMAnt:param="chnlrecycle_findbyid.jsp.datapath">存放位置:</span>
	<span class="wcm_attr_value" _fieldName="DataPath" _fieldValue="<%=sDataPath%>">
		<%=sDataPath%>&nbsp;
	</span>
</div>
<%
	}
	if(obj.isLink()){
		String sLinkUrl = obj.getLinkUrl();
%>
<div class="attribute_row datapath readonly">
	<span class="wcm_attr_name" WCMAnt:param="chnlrecycle_findbyid.jsp.link">URL:</span>
	<span class="wcm_attr_value" _fieldName="LinkUrl" _fieldValue="<%=sLinkUrl%>" title="<%=sLinkUrl%>" style="color:#09549F" _serviceId="wcm6_channel" _methodName="save">
		<%=sLinkUrl%>&nbsp;
	</span>
</div>
<%
	}	
%>

 