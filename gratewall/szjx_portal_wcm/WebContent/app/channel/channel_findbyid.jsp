<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.components.wcm.publish.WCMFolderPublishConfig" %>
<%@ page import="com.trs.components.common.publish.persistent.template.Template" %>
<%@ page import="com.trs.components.common.publish.persistent.element.PublishElementFactory" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishFolder" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.common.WCMTypes" %>
 
<%@include file="../include/findbyid_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%!
	private boolean hasRight(User _currUser, CMSObj _objCurrent,int _nRightIndex) throws WCMException{
		return AuthServer.hasRight(_currUser,_objCurrent,_nRightIndex);
	}
%>
<%!
	private String getParentName(Channel _chnl) throws WCMException{
		int parentChannelId = _chnl.getParentId();
		if(parentChannelId == 0){
		    return LocaleServer.getString("channel.label.none","无");

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
		throw new WCMException(CMyString.format(LocaleServer.getString("channel_findbyid.type","服务(com.trs.ajaxservice.ChannelServiceProvider.findbyid)返回的对象类型不为Channel，而为{0}，请确认。"),new Object[]{result.getClass()}));
	}
	Channel obj = (Channel)result;
	int nStatus = obj.getStatus();
	if(nStatus < 0){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("channel_findbyid.object.not.found", "当前栏目[ID={0}]已经被删除了."), new String[]{String.valueOf(obj.getId()), WCMTypes.getLowerObjName(Channel.OBJ_TYPE)}));
	}

	boolean bCanEdit = hasRight(loginUser,obj,13);
	String sEditable = bCanEdit?"editable":"readonly";
	boolean bHasTemplateAddRight = hasRight(loginUser, obj, WCMRightTypes.TEMPLATE_ADD);
	boolean bHasTemplateEditRight = hasRight(loginUser, obj, WCMRightTypes.TEMPLATE_EDIT);
	int nChnlId = obj.getId();
	String sChnlDesc = CMyString.transDisplay(obj.getDesc());
	String sChnlName = CMyString.transDisplay(obj.getName());
	String sParentName = CMyString.transDisplay(getParentName(obj));
	WCMFolderPublishConfig chnlConfig = getPublishConfig(obj);
	int nOutlineTempId = chnlConfig.getDefaultOutlineTemplateId();
	Template outlineTemp = Template.findById(nOutlineTempId);
	String sOutlineTempName = CMyString.transDisplay((outlineTemp != null)?outlineTemp.getName():LocaleServer.getString("channel.label.none", "无"));
	boolean bHasOutTemplateRight = outlineTemp != null ? bCanEdit && bHasTemplateEditRight : (bCanEdit && bHasTemplateAddRight);
	boolean bCanSetTemplate = hasRight(loginUser, obj, WCMRightTypes.CHNL_SETTEMPLATE);
	boolean bOutLineIsVisual = outlineTemp != null ? outlineTemp.getPropertyAsBoolean("Visual", false) : false;

	int nDetailTempId = chnlConfig.getDetailTemplateId();
	Template detailTemp = Template.findById(nDetailTempId);
	String sDetailTempName = CMyString.transDisplay((detailTemp != null)?detailTemp.getName():LocaleServer.getString("channel.label.none", "无"));
	boolean bHasDetailTemplateRight = detailTemp != null ? bCanEdit && bHasTemplateEditRight : (bCanEdit && bHasTemplateAddRight);
	boolean bDetailTemplateIsVisual = detailTemp != null ? detailTemp.getPropertyAsBoolean("Visual", false) : false;
	int nInfoviewPrintTemplateId = 0;
	String sInfoviewPrintTemplateName = LocaleServer.getString("channel.label.none", "无");
	Template infoviewPrintTemplete = null;
	int nChannelType = obj.getType();
	if(nChannelType == Channel.TYPE_INFOVIEW){
		nInfoviewPrintTemplateId = chnlConfig.getInfoviewPrintTemplateId();
		if(nInfoviewPrintTemplateId > 0)
			infoviewPrintTemplete = Template.findById(nInfoviewPrintTemplateId);
		if(infoviewPrintTemplete != null){
			sInfoviewPrintTemplateName = infoviewPrintTemplete.getName();
		}
	}

	String sTypeDesc = CMyString.transDisplay(obj.getTypeDesc());
	String sDataPath = CMyString.transDisplay(chnlConfig.getDataPath());
	String sURL = null;
	try{
		sURL = chnlConfig.getURL();
	}catch(Exception e){
		//忽略获取url的异常信息
	}
	sURL = CMyString.transDisplay(sURL);
	String sCruser = CMyString.transDisplay(obj.getPropertyAsString("cruser"));
	String sCrtime = obj.getPropertyAsDateTime("crtime").toString(CMyDateTime.DEF_DATETIME_FORMAT_PRG);
	String sHostServiceAttr = "_serviceId=\"wcm61_channel\"";
	String sHostMethodAttr = "_methodName=\"saveChannelPublishConfig\"";
%>
<!--//TODO type findbyid here-->
<SCRIPT LANGUAGE="JavaScript">
//datapath单独放到这是为了避免和站点的相互覆盖
ValidationHelper.bindValidations([
	{
		renderTo : 'DataPath',
		type :'common_char',
		required :'',
		desc :wcm.LANG.CHANNEL_47||'存放位置',
		max_len :'50',
		methods : PageContext.validExistProperty(function(oParams){
			var wcmEvent = PageContext.event;
			var obj = wcmEvent.getObjs().getAt(0) || wcmEvent.getHost();
			Ext.apply(oParams, {
				ChannelId : obj.getId()
			});
		})		
	}
]);
</SCRIPT>
<div class="attribute_row main_attr ChnlDesc <%=sEditable%>">
	<span class="wcm_attr_value" <%=sHostServiceAttr%> _fieldName="ChnlDesc" _fieldValue="<%=sChnlDesc%>" title="<%=sChnlDesc%>">
		<%=sChnlDesc%>&nbsp;
	</span>
</div>
<div class="attribute_row_sep"></div>
<div class="attribute_row descinfo readonly">
	<span title="<%=LocaleServer.getString("channel.label.channelid", "栏目编号")%>:&nbsp;<%=nChnlId%>&#13;<%=LocaleServer.getString("channel.label.cruser", "创建者")%>:&nbsp;<%=sCruser%>&#13;<%=LocaleServer.getString("channel.label.crtime", "创建时间")%>:&nbsp;<%=sCrtime%>&#13" style="white-space:nowrap;overflow:hidden;" >
		<span WCMAnt:param="channel_findbyid.jsp.cruser">用户</span><span class="value"><%=sCruser%></span><span WCMAnt:param="channel_findbyid.jsp.crtime">创建于</span><span class="value"><%=sCrtime%></span>
	</span>
</div>
<div class="attribute_row_sep"></div>
<div class="attribute_row ChnlName <%=sEditable%>">
	<span class="wcm_attr_name" WCMAnt:param="channel_findbyid.jsp.ChnlName">唯一标识:</span>
	<span class="wcm_attr_value" title="<%=sChnlName%>" _fieldName="ChnlName" _fieldValue="<%=sChnlName%>" style="color:#09549F" <%=sHostServiceAttr%> >
		<%=sChnlName%>&nbsp;
	</span>
</div>
<div class="attribute_row TypeDesc readonly">
	<span class="wcm_attr_name" WCMAnt:param="channel_findbyid.jsp.TypeDesc">栏目类型:</span>
	<span class="wcm_attr_value" title="<%=sTypeDesc%>" >
		<%=sTypeDesc%>&nbsp;
	</span>
</div>
<div class="attribute_row ParentName readonly">
	<span class="wcm_attr_name" WCMAnt:param="channel_findbyid.jsp.ParentName">栏目位置:</span>
	<span class="wcm_attr_value" title="<%=sParentName%>">
		<%=sParentName%>&nbsp;
	</span>
</div>
<%
if(obj.isOnlySearch() || !obj.isVirtual()){
%>
<div class="attribute_row_sep"></div>
<div class="attribute_row readonly">
	<span class="wcm_attr_name" WCMAnt:param="channel_findbyid.jsp.OutlineTempName">首页模板:</span>
	<span class="wcm_attr_value"  style="width:60px;color:#09549F">
		<%if(bHasOutTemplateRight){%>
		<a href="#" onclick="channelhelper.PageContext.editTemplate(<%=nOutlineTempId%>, 1, <%=bOutLineIsVisual%>); return false;" style="cursor:pointer" name="channel_editTemplate" title="<%=sOutlineTempName%>[ID-<%=nOutlineTempId%>]&#13;<%=LocaleServer.getString("channel.label.explain", "提示:点击进入模板新建/修改页面")%>"  templateId="<%=nOutlineTempId%>"><span style="color:#09549F"><%=sOutlineTempName%>&nbsp;</span></a>
		<%}else{%>
		<a name="channel_editTemplate" title="<%=sOutlineTempName%>[ID-<%=nOutlineTempId%>]"><span style="color:gray;"><%=sOutlineTempName%>&nbsp;</span></a>
		<%}%>
	</span>
	<%if(bCanSetTemplate){%>
	<span><a href="#" onclick="channelhelper.PageContext.selectTemplate(<%=nOutlineTempId%>, 1); return false;" style="position:absolute;width:16px;height:16px;cursor: pointer;background:url(../images/icon/icons.gif) -22px -60px no-repeat;" title="选择模板" name="website_selectTemplate" WCMAnt:paramattr="title:channel_findbyid.jsp.selecttemp"></a></span>
	<%}%>
</div>
<div class="attribute_row readonly">
	<span class="wcm_attr_name" WCMAnt:param="channel_findbyid.jsp.DetailTemplate">细览模板:</span>
	<span class="wcm_attr_value"  style="width:60px;color:#09549F">
		<%if(bHasDetailTemplateRight){%>
		<a href="#" onclick="channelhelper.PageContext.editTemplate(<%=nDetailTempId%>, 2, <%=bDetailTemplateIsVisual%>); return false;" style="cursor:pointer" title="<%=sDetailTempName%>[ID-<%=nDetailTempId%>]&#13;<%=LocaleServer.getString("channel.label.explain", "提示:点击进入模板新建/修改页面")%>" name="channel_editTemplate" templateId="<%=nDetailTempId%>"><span style="color:#09549F"><%=sDetailTempName%>&nbsp;</span></a>
		<%}else{%>
		<a name="channel_editTemplate" title="<%=sDetailTempName%>[ID-<%=nDetailTempId%>]"><span style="color:gray;"><%=sDetailTempName%>&nbsp;</span></a>
		<%}%>
	</span>
	<%if(bCanSetTemplate){%>
	<span><a href="#" onclick="channelhelper.PageContext.selectTemplate(<%=nDetailTempId%>, 2); return false;" style="position:absolute;width:16px;height:16px;cursor: pointer;background:url(../images/icon/icons.gif) -22px -60px no-repeat;" title="选择模板" name="website_selectTemplate" WCMAnt:paramattr="title:channel_findbyid.jsp.selecttemp"></a></span>
	<%}%>
</div>
<%if(nChannelType == Channel.TYPE_INFOVIEW){%>
	<div class="attribute_row readonly">
		<span class="wcm_attr_name" WCMAnt:param="channel_findbyid.jsp.printtemplate">打印模板:</span>
		<span class="wcm_attr_value"  style="width:60px;color:#09549F">
			<%if(bHasDetailTemplateRight){%>
			<a href="#" onclick="channelhelper.PageContext.editTemplate(<%=nInfoviewPrintTemplateId%>, 3, false); return false;" style="cursor:pointer" title="<%=sInfoviewPrintTemplateName%>[ID-<%=nInfoviewPrintTemplateId%>]&#13;<%=LocaleServer.getString("channel.label.explain", "提示:点击进入模板新建/修改页面")%>" name="channel_editTemplate" templateId="<%=nInfoviewPrintTemplateId%>"><span style="color:#09549F"><%=sInfoviewPrintTemplateName%>&nbsp;</span></a>
			<%}else{%>
			<a name="channel_editTemplate" title="<%=sInfoviewPrintTemplateName%>[ID-<%=nInfoviewPrintTemplateId%>]"><span style="color:gray;"><%=sInfoviewPrintTemplateName%>&nbsp;</span></a>
			<%}%>
		</span>
		<%if(bCanSetTemplate){%>
		<span><a href="#" onclick="channelhelper.PageContext.selectTemplate(<%=nInfoviewPrintTemplateId%>, 3); return false;" style="position:absolute;width:16px;height:16px;cursor: pointer;background:url(../images/icon/icons.gif) -22px -60px no-repeat;" title="选择模板" name="website_selectTemplate" WCMAnt:paramattr="title:channel_findbyid.jsp.selecttemp"></a></span>
		<%}%>
	</div>
<%}%>
<div class="attribute_row_sep"></div>


<div class="attribute_row datapath <%=sEditable%>">
	<span class="wcm_attr_name" WCMAnt:param="channel_findbyid.jsp.datapath">存放位置:</span>
	<span class="wcm_attr_value" _fieldName="DataPath" _fieldValue="<%=sDataPath%>" title="<%=sDataPath%>" style="color:#09549F;height:20px;line-height:20px;" _serviceId="wcm6_publish" <%=sHostMethodAttr%> >
		<%=sDataPath%>&nbsp;
	</span>
</div>
<div class="attribute_row_sep"></div>
<div class="attribute_row URL readonly">
	<span class="wcm_attr_value"  title="<%=sURL%>" style="width:150px; margin-left:2px;color:#09549F">
		<a href="<%=sURL%>" target="_blank"><span style="color:#09549F"><%=sURL%>&nbsp;</span></a>
	</span>
</div>
<%
	}
%>
<%
	if(obj.isLink()){
		String sLinkUrl = obj.getLinkUrl();
%>
<div class="attribute_row datapath <%=sEditable%>">
	<span class="wcm_attr_name" WCMAnt:param="channel_findbyid.jsp.link">链接地址:</span>
	<span class="wcm_attr_value" _fieldName="LinkUrl" _fieldValue="<%=sLinkUrl%>" title="<%=sLinkUrl%>" style="color:#09549F" _serviceId="wcm6_channel" _methodName="save">
		<%=sLinkUrl%>&nbsp;
	</span>
</div>
<%
	}	
%>
 