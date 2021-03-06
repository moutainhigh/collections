<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.common.publish.persistent.template.Template" %>
<%@ page import="com.trs.components.common.publish.persistent.template.Templates" %>
<%@ page import="com.trs.components.common.publish.PublishConstants" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishFolder" %>
<%@ page import="com.trs.webframework.xmlserver.ConvertException" %>
<%@ page import="com.trs.components.common.publish.persistent.template.TemplateEmploy" %>
<%@ page import="com.trs.components.common.publish.persistent.template.TemplateEmploys" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishElement" %>
<%@ page import="com.trs.components.wcm.content.persistent.BaseChannel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.common.publish.domain.template.TemplateEmployMgr" %>
<%@ page import="com.trs.cms.auth.persistent.RightValue" %>
<%@ page import="com.trs.ajaxservice.TemplateAuthHelper" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.components.wcm.content.domain.auth.DocumentAuthServer" %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.Stack" %>
<%@ page import="com.trs.service.ITemplateService" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<%
try{
%>
<%@include file="../include/findbyid_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%!
	public RightValue getRightValue(Template template, User loginUser,
            MethodContext context) throws WCMException {
        RightValue rightValue = null;
        if (loginUser.isAdministrator()
                || loginUser.getName().equals(template.getCrUserName())) {
            rightValue = new RightValue(RightValue.VALUE_ADMINISTRATOR);
        } else {
            IPublishFolder host = (IPublishFolder) context
                    .getContextCacheData("Host");
            rightValue = TemplateAuthHelper.makeTemplateRightValue(context,
                    loginUser, template, host);
        }
        return rightValue;
    }
%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof Template)){
		throw new WCMException(CMyString.format(LocaleServer.getString("template_findbyid.jsp.service", "??????(com.trs.ajaxservice.TemplateServiceProvider.findbyid)???????????????????????????Template?????????[{0}]???????????????!"), new Object[]{result.getClass()}));
	}
	int nMax = 3;
	Template obj = (Template)result;
	//Template obj = (Template)request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	RightValue rightValue = getRightValue(obj, loginUser, oMethodContext);
	boolean bCanEdit = rightValue.isHasRight(23);
	//boolean bCanEdit = hasRight(loginUser,obj,23);
	int nTemplateId = obj.getId();
	String sTemplateName = CMyString.filterForHTMLValue(obj.getName());
	String sTemplateDesc = CMyString.filterForHTMLValue(obj.getDesc());
	int nTempFolderId = obj.getFolderId();
	IPublishFolder folder = findHost(obj);
	
	String sTempExt = CMyString.filterForHTMLValue(obj.getOutputFileExt());
	String sOutputFileName = CMyString.filterForHTMLValue(obj.getOutputFileName());
	int nTempType = obj.getType();
	String sTempType = makeTemplateTypeName(nTempType);
	String sTemplateCruser = obj.getPropertyAsString("CRUSER");
	String sTemplateTime = obj.getPropertyAsString("CRTIME");
	String sEditable = bCanEdit?"editable":"readonly";
	TemplateEmployMgr m_oEmployMgr = (TemplateEmployMgr) DreamFactory
                .createObjectById("TemplateEmployMgr");
	ITemplateService currTemplateService = (ITemplateService)DreamFactory.createObjectById("ITemplateService");
	List lstTemplateEmployers = currTemplateService.getTemplateEmployers(obj);
	Templates nestedTemplates = currTemplateService.getNestedTemplates(obj);//?????????..
	Templates templatesNesteds = currTemplateService.getTemplatesNested(obj);//???..??????
	TemplateEmploys employs = m_oEmployMgr.getTemplateEmployers(obj, null);
	
	TemplateEmploys objs = new TemplateEmploys(loginUser);
	for(int index = 0, length = employs.size(); index < length; index++){
		TemplateEmploy anEmploy = (TemplateEmploy) employs.getAt(index);
		if(anEmploy == null) continue;
        IPublishElement publishElement = anEmploy.getEmployer();
		if(publishElement == null) {
			System.out.println("template findbyid jsp error: employer is null. [Employer: type="+anEmploy.getEmployerType()+", id="+anEmploy.getEmployerId()+"]");
			continue;
		}
		CMSObj tempObj = publishElement.getSubstance();
		if(isDeleted(tempObj)) continue;
		objs.addElement(anEmploy);
	}
%>
<%!
	
	private String makeTemplateTypeName(int type) {
        switch (type) {
        case PublishConstants.TEMPLATE_TYPE_NESTED:
            return LocaleServer.getString("template_findbyid.label.nested", "??????");
        case PublishConstants.TEMPLATE_TYPE_OUTLINE:
            return LocaleServer.getString("template_findbyid.label.outline", "??????");
        case PublishConstants.TEMPLATE_TYPE_DETAIL:
            return LocaleServer.getString("template_findbyid.label.detail", "??????");
		case PublishConstants.TEMPLATE_TYPE_INFOVIEWPRINT:
			return LocaleServer.getString("template_findbyid.label.infoviewprint", "????????????");
        default:
            return CMyString.format(LocaleServer.getString("template_findbyid.jsp.unknow", "??????[{0}]"), new int[]{type});
		// LocaleServer.getString("template_findbyid.label.unknow", "??????")+"[" + type + "]";
        }
    }

	private IPublishFolder findHost(Template _template) throws ConvertException {
        IPublishFolder folder = null;
        try {
            folder = _template.getFolder();
        } catch (WCMException e) {
            throw new ConvertException(
				ExceptionNumber.ERR_TEMPLATE_HOST_NOT_FOUND,
				LocaleServer.getString("template_addedit_label_4","???????????????????????????/??????????????????!"), e);
        }
        return folder;
    }

%>
<!--//TODO type findbyid here-->

<div class="attribute_row tempname <%=sEditable%> main_attr">
	<span class="wcm_attr_value" _fieldName="tempname" _fieldValue="<%=sTemplateName%>" ><%=sTemplateName%></span>
</div>
<div class="attribute_row_sep"></div>
<div class="attribute_row descinfo readonly">
	<span class="wcm_attr_value" title="<%=LocaleServer.getString("template.label.templateId", "???????????????")%> <%=nTemplateId%>&#13;<%=LocaleServer.getString("template.label.cruser", "????????????")%> <%=sTemplateCruser%>&#13;<%=LocaleServer.getString("template.label.crtime", "???????????????")%> <%=sTemplateTime%>"><span WCMAnt:param="template_findbyid.jsp.cruser">??????</span><span class="value"><%=sTemplateCruser%></span><span WCMAnt:param="template_findbyid.jsp.crtime">?????????</span><span class="value"><%=sTemplateTime%></span></span>
</div>
<div class="attribute_row_sep"></div>
<div class="attribute_row readonly location">
	<span WCMAnt:param="template_findbyid.jsp.TempFolderId" class="wcm_attr_name" >????????????:</span>
	<span class="wcm_attr_value" title="<%=folder.getInfo()%>"><%=folder.getInfo()%></span>
</div>

<div class="attribute_row temptype <%=sEditable%>">
	<span WCMAnt:param="template_findbyid.jsp.TempType" class="wcm_attr_name">????????????:</span>
	<span class="wcm_attr_value select" _selectEl="selTempType" _fieldName="temptype" _fieldValue="<%=nTempType%>" ><%=sTempType%>&nbsp;</span>
	<span style="display:none">
		<select name="selTempType">
				<option value="1" WCMAnt:param="template_findbyid.jsp.outline">??????</option>
				<option value="2" WCMAnt:param="template_findbyid.jsp.detail">??????</option>
				<option value="0" WCMAnt:param="template_findbyid.jsp.nest">??????</option>
				<option value="3" WCMAnt:param="template_findbyid.jsp.infoviewprint">????????????</option>
		</select>
	</span>
</div>
<div class="attribute_row TempDesc <%=sEditable%>">
	<span WCMAnt:param="template_findbyid.jsp.TemplateDesc" class="wcm_attr_name">????????????:</span>
	<span class="wcm_attr_value" _fieldName="TempDesc" _fieldValue="<%=sTemplateDesc%>" style="color:#09549F"><%=sTemplateDesc%></span>
</div>
<%
	if(nTempType!=0){	
%>
<div class="attribute_row TempExt <%=sEditable%>">
	<span WCMAnt:param="template_findbyid.jsp.TempExt" class="wcm_attr_name" style="width:80px;">???????????????:</span>
	<span class="wcm_attr_value" _fieldName="TempExt" _fieldValue="<%=sTempExt%>" style="color:#09549F;width:60px"><%=sTempExt%></span>
</div>
<%
	}
	if(nTempType==1){
%>
<div class="attribute_row OutputFileName <%=sEditable%>">
	<span WCMAnt:param="template_findbyid.jsp.OutputFileName" class="wcm_attr_name" style="width:80px;">???????????????:</span>
	<span class="wcm_attr_value" _fieldName="OutputFileName" _fieldValue="<%=sOutputFileName%>" style="color:#09549F;width:60px" ><%=sOutputFileName%></span>
</div>
<%
	}
	if(nTempType!=0){
%>
<div class="attribute_row_sep"></div>

<div class="attribute_row">
	<%
		String sParam0 = "<span class='num'>"+objs.size()+"</span>";
	%>
	</SCRIPT>
	<span WCMAnt:param="template_findbyid.jsp.employ" class="wcm_attr_name" style="width:90%">???<%=sParam0%>????????????????????????:</span>
</div>
<div style="padding-left:5px;">
	<div style="margin-bottom:5px;"><span>(<a href="#" onclick="PageContext.showArrangeEmployment(<%=nTempType%>);return false;" WCMAnt:param="template_findbyid.jsp.fenpei">??????</a>)</span></div>
<%
	int nSize = objs.size();
	for(int i=0;i<nSize&i<nMax;i++ ){
		TemplateEmploy anEmploy = (TemplateEmploy) objs.getAt(i);
        IPublishElement publishElement = anEmploy.getEmployer();
		CMSObj tempObj = publishElement.getSubstance();
		int nObjType = tempObj.getWCMType();
		int nObjId = tempObj.getId();
		int nRightIndex = getPreviewRightIndexByType(publishElement.getType());
		boolean bPreview = false;
		// ?????????????????????DocumentAuthServer
		if (publishElement.getType() == Document.OBJ_TYPE) {
			if (DocumentAuthServer.hasRight(loginUser, null,
					(Document) tempObj, nRightIndex)) {
				bPreview = true;
			}
		} else {
			if (AuthServer.hasRight(loginUser, tempObj, nRightIndex)) {
				bPreview = true;
			}
		}
		boolean isDefault = anEmploy.getPropertyAsBoolean("IsDefault", false);
		String sUsenessTip = "";
		if(nTempType == 2){
			sUsenessTip = LocaleServer.getString("template_findbyid.label.usedindetail", "????????????????????????");
		}else{
			sUsenessTip = (isDefault)?LocaleServer.getString("template_findbyid.label.usedinfirst", "????????????????????????"):LocaleServer.getString("template_findbyid.label.usedinother", "??????????????????????????????");
		}
%>
	<span ><%=(i+1)%>.</span><a href="#" onclick="if(!<%=bPreview%>)return;PageContext.previewFolder(<%=nTempType%>,<%=nObjId%>,<%=nObjType%>); return false;" class="template_employer" style="color: <%=isDefault?"#010101":"red"%>; text-decoration: underline; cursor: pointer" title="<%=getChnlPath(tempObj)%><%=getName(tempObj)%>&#13;<%=sUsenessTip%>&#13;<%=LocaleServer.getString("template.label.clew", "???????????????????????????????????????")%>"><%=getName(tempObj)%></a><br>
<%
	}
	if(nMax<nSize){
%>
	<div style="margin-top:5px;"><span class="attribute_value"><a href="#" onclick="Event.stop(event);showCrashBoard()" WCMAnt:param="template_findbyid.jsp.more">??????</a></span></div>
<%
	}
%>
</div>

<div id="divBox" style="display:none;">
<div style="overflow:auto;height:200px;width:100%">
	<%
		String sParam1 = "<span style='color:red'>"+nSize+"</span>";
	%>
	<div style="height:40px">
		<span WCMAnt:param="template_findbyid.jsp.preview"  style="width:90%;color:blue;font-size:14px;padding-top:8px">??????<%=sParam1%>?????????????????????????????????</span>
	</div>
	<table style="height:auto;width:100%;table-layout:fixed;">
<%
	int nNum = 0;
	for(int i=0;i<nSize;i++ ){
		TemplateEmploy anEmploy = (TemplateEmploy) objs.getAt(i);
        IPublishElement publishElement = anEmploy.getEmployer();
		CMSObj tempObj = publishElement.getSubstance();
		int nObjId = tempObj.getId();
		int nObjType = tempObj.getWCMType();
		int nRightIndex = getPreviewRightIndexByType(publishElement.getType());
		boolean bPreview = false;
		// ?????????????????????DocumentAuthServer
		if (publishElement.getType() == Document.OBJ_TYPE) {
			if (DocumentAuthServer.hasRight(loginUser, null,
					(Document) tempObj, nRightIndex)) {
				bPreview = true;
			}
		} else {
			if (AuthServer.hasRight(loginUser, tempObj, nRightIndex)) {
				bPreview = true;
			}
		}
		boolean isDefault = anEmploy.getPropertyAsBoolean("IsDefault", false);
		String sUsenessTip = "";
		if(nTempType == 2){
			sUsenessTip = LocaleServer.getString("template_findbyid.label.usedindetail", "????????????????????????");
		}else{
			sUsenessTip = (isDefault)?LocaleServer.getString("template_findbyid.label.usedinfirst", "????????????????????????"):LocaleServer.getString("template_findbyid.label.usedinother", "??????????????????????????????");
		}

%>
<%
	if(nNum%2==0){
		out.println("<tr>");
	}	
	nNum++;
%>
	<td style="line-height:20px;">
	<span style="float:left;"><%=(i+1)%>.</span><span style="float:left;padding-left:5px; width:150px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;"><a href="#" onclick="if(!<%=bPreview%>)return;Event.stop(event);PageContext.previewFolder(<%=nTempType%>,<%=nObjId%>,<%=nObjType%>);" class="template_employer" style="color: <%=isDefault?"#010101":"red"%>; text-decoration: underline; cursor: pointer" title="<%=getChnlPath(tempObj)%><%=getName(tempObj)%>&#13;<%=sUsenessTip%>&#13;<%=LocaleServer.getString("template.label.clew", "???????????????????????????????????????")%>"><%=getName(tempObj)%></a></span></td>

<%
	if(nNum%2==0){
		out.println("</tr>");
	}	
%>
<%
	}	
%>
</table>
</div>
</div>
<%
	}
%>
<div class="attribute_row_sep"></div>
<%	
	if(nTempType==0){	
%>
<div class="attribute_row">
	<%
		int nNestedSize = templatesNesteds!=null?templatesNesteds.size():0;
		String sNested = "<span class='num'>"+(nNestedSize)+"</span>";
	%>
	<span WCMAnt:param="template_findbyid.jsp.nested"  style="font-weight:bold;line-height:18px;padding-left:4px;width:90%">???<%=sNested%>???????????????</span><br>
	<%
		if(nNestedSize>0){
		for(int j=0;j<nNestedSize&j<nMax;j++){
			Template templatesNested = (Template)templatesNesteds.getAt(j);
			if(templatesNested == null)continue;
			int nNestedTempId = templatesNested.getId();
			String sNestedTempName = templatesNested.getName();
			int nNestedTempType = templatesNested.getType();
			String sNestedTempType = makeTemplateTypeName(nNestedTempType);
	%>
	<span class="num"><%=j+1%>.</span><span><%=sNestedTempType%>-<%=sNestedTempName%>-<%=nNestedTempId%></span><br>
	<%
			}
		}
		if(nMax<nNestedSize){
	%>
		<div style="margin-top:5px;"><span class="attribute_value"><a href="#" onclick="Event.stop(event);showCrashBoardNested()" WCMAnt:param="template_findbyid.jsp.more">??????</a></span></div>
	<%
		}
	%>
</div>


<div id="NesteddivBox" style="display:none;">
<div style="overflow:auto;height:200px;width:100%">
	<%
		String sTempParam = "<span style='color:red'>"+nNestedSize+"</span>";
	%>
	<div style="height:40px">
		<span WCMAnt:param="template_findbyid.jsp.moretemp"  style="width:90%;color:blue;font-size:14px;padding-top:8px">??????<%=sTempParam%>????????????</span>
	</div>
	<table style="height:auto;width:100%;table-layout:fixed;">
<%
	int nNestedNum = 0;
	for(int j=0;j<nNestedSize;j++){
			Template templatesNested = (Template)templatesNesteds.getAt(j);
			int nNestedTempId = templatesNested.getId();
			String sNestedTempName = templatesNested.getName();
			int nNestedTempType = templatesNested.getType();
			String sNestedTempType = makeTemplateTypeName(nNestedTempType);
%>
<%
	if(nNestedNum%2==0){
		out.println("<tr>");
	}	
	nNestedNum++;
%>
	<td style="line-height:20px;">
	<span style="float:left;"><%=(j+1)%>.</span><span style="float:left;padding-left:5px; width:150px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;"><%=sNestedTempType%>-<%=sNestedTempName%>-<%=nNestedTempId%></span></td>

<%
	if(nNestedNum%2==0){
		out.println("</tr>");
	}	
%>
<%
	}	
%>
</table>
</div>
</div>
<%
	}else{	
%>
<div class="attribute_row">
	<%
		int nNestSize =	nestedTemplates!=null?nestedTemplates.size():0;
		String sParam2 = "<span class='num'>"+(nNestSize)+"</span>";
	%>
	<span WCMAnt:param="template_findbyid.jsp.nesttemp"  style="font-weight:bold;line-height:18px;padding-left:4px;width:90%">???????????????<%=sParam2%>?????????</span><br>
	<%	
	if(nNestSize>0){
		for(int z=0;z<nNestSize&z<nMax;z++){
			Template nestedTemplate = (Template)nestedTemplates.getAt(z);
			if(nestedTemplate == null)continue;
			int nNestTempId = nestedTemplate.getId();
			String sNestTempName = nestedTemplate.getName();
			int nNestTempType = nestedTemplate.getType();
			String sNestTempType = makeTemplateTypeName(nNestTempType);
	%>
	<span class="num"><%=z+1%>.</span><span ><%=sNestTempType%>-<%=sNestTempName%>-<%=nNestTempId%></span><br>
	<%
			}
		}
		if(nMax<nNestSize){
	%>
		<div style="margin-top:5px;"><span class="attribute_value"><a href="#" onclick="Event.stop(event);showCrashBoardNest()" WCMAnt:param="template_findbyid.jsp.more">??????</a></span></div>
	<%
		}
	%>
</div>


<div id="NestdivBox" style="display:none;">
<div style="overflow:auto;height:200px;width:100%">
	<%
		String sTempParam2 = "<span style='color:red'>"+nNestSize+"</span>";
	%>
	<div style="height:40px">
		<span WCMAnt:param="template_findbyid.jsp.moretemp2"  style="width:90%;color:blue;font-size:14px;padding-top:8px">??????<%=sTempParam2%>????????????</span>
	</div>
	<table style="height:auto;width:100%;table-layout:fixed;">
<%
	int nNestNum = 0;
	for(int z=0;z<nNestSize;z++){
			Template nestedTemplate = (Template)nestedTemplates.getAt(z);
			if(nestedTemplate == null) continue;
			int nNestTempId = nestedTemplate.getId();
			String sNestTempName = nestedTemplate.getName();
			int nNestTempType = nestedTemplate.getType();
			String sNestTempType = makeTemplateTypeName(nNestTempType);
%>
<%
	if(nNestNum%2==0){
		out.println("<tr>");
	}	
	nNestNum++;
%>
	<td style="line-height:20px;">
	<span style="float:left;"><%=(z+1)%>.</span><span style="float:left;padding-left:5px; width:150px;text-overflow:ellipsis;overflow:hidden;white-space:nowrap;"><%=sNestTempType%>-<%=sNestTempName%>-<%=nNestTempId%></span></td>

<%
	if(nNestNum%2==0){
		out.println("</tr>");
	}	
%>
<%
	}	
%>
</table>
</div>
</div>
<%
	}		
%>
<%
}catch(Throwable tx){
	tx.printStackTrace();
	throw new WCMException(LocaleServer.getString("template_findbyid.runtime.error", "template_findbyid.jsp???????????????!"),tx);
}
%>

<%!
	private boolean isDeleted(CMSObj obj)throws Exception{
		if(obj == null) return true;
		switch(obj.getWCMType()){
			case Channel.OBJ_TYPE :
			case WebSite.OBJ_TYPE : 
				return ((BaseChannel)obj).isDeleted();
			case Document.OBJ_TYPE :
				return ((Document)obj).isDeleted();
		}
		return false;
	}

	private String getName(CMSObj obj)throws Exception{
		if(obj == null) return "";
		String sName = "";
		switch(obj.getWCMType()){
			case WebSite.OBJ_TYPE : 
				sName =CMyString.format(LocaleServer.getString("template_findbyid.jsp.website", "{0}[??????-{1}]"), new Object[]{((WebSite)obj).getName() ,String.valueOf(obj.getId())}) ;
				break;
			case Channel.OBJ_TYPE :
				sName = CMyString.format(LocaleServer.getString("template_findbyid.jsp.channel", "{0}[??????-{1}]"), new Object[]{((Channel)obj).getName() ,String.valueOf(obj.getId())}) ;
				break;
			case Document.OBJ_TYPE :
				sName = CMyString.format(LocaleServer.getString("template_findbyid.jsp.document", "{0}[??????-{1}]"), new Object[]{((Document)obj).getTitle() ,String.valueOf(obj.getId())}) ;
				break;
		}
		return sName;
	}

	private int getPreviewRightIndexByType(int _nElementType) {
		switch (_nElementType) {
		case Document.OBJ_TYPE:
			return WCMRightTypes.DOC_PREVIEW;
		case Channel.OBJ_TYPE:
			return WCMRightTypes.CHNL_PREVIEW;
		case WebSite.OBJ_TYPE:
			return WCMRightTypes.SITE_PREVIEW;
		default:
			break;
		}
		return WCMRightTypes.OBJ_ACCESS;
	}

	private String getChnlPath(CMSObj obj){
		String sChnlPath = "";
		if(obj == null) return "";
		switch(obj.getWCMType()){
			case WebSite.OBJ_TYPE : 
				sChnlPath = "";
				break;
			case Channel.OBJ_TYPE:
				sChnlPath = getEntityChnl((Channel)obj);
				break;
			case Document.OBJ_TYPE :
				sChnlPath = "";
				break;
		}
		return sChnlPath;
	}

	private String getEntityChnl(Channel _currChannel){
		if(_currChannel == null) return "";
		Channel parent = null;	
		String sEntityChnl = "";
		Stack stack = new Stack ();
		try{
			parent = _currChannel.getParent();
		}catch(Exception e){
			e.printStackTrace();
		}
		if(parent != null)sEntityChnl += LocaleServer.getString("template_findbyid.label.entityPath", "????????????");
		while (parent != null) {
			//sEntityChnl += parent.getDesc()+">";
			stack.push(parent.getDesc()+">");
			try{
				parent = parent.getParent();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		while (!stack.empty()){
			String sChnldesc = stack.pop().toString();
			sEntityChnl += sChnldesc;
		}
		return sEntityChnl;
	}
%>