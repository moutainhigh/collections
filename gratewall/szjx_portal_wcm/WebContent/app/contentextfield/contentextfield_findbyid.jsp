<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtField" %>
<%@ page import="com.trs.cms.content.ExtendedField" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@include file="../include/findbyid_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%!
	private boolean hasRight(User _currUser, CMSObj _objCurrent,int _nRightIndex) throws WCMException{
		return AuthServer.hasRight(_currUser,_objCurrent,_nRightIndex);
	}
%>
<%
	ContentExtField obj = (ContentExtField)request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	boolean bCanEdit = hasRight(loginUser,obj,19);
	ExtendedField currExtendedField = ExtendedField.findById(obj.getExtFieldId());
	String sFieldTypeName = CMyString.filterForHTMLValue(currExtendedField.getAttributeValue("FIELDTYPE"));
	String sDefaultValue = CMyString.transDisplay(obj.getExtFieldProperty("FIELDDEFAULT"));
	String sEnumValue = CMyString.transDisplay(currExtendedField.getAttributeValue("ENMVALUE"));
	String sContentExtFieldName = obj.getName();
	String sContentExtFieldOutName = obj.getDesc();
	String sContentExtFieldTypeName = obj.getTypeDesc();
	int nContentExtFieldLength =  obj.getMaxLength();
	String sContentExtFieldCruser = obj.getPropertyAsString("CRUSER");
	String sContentExtFieldTime = obj.getPropertyAsString("CRTIME");
	CMSObj host = obj.getHost();
	String sEditable = bCanEdit?"editable":"readonly";
%>
<!--//TODO type findbyid here-->
<%//=obj%>
<div class="attribute_row <%=sEditable%> main_attr">
	<span class="wcm_attr_value" _fieldName="FieldDesc" _fieldValue="<%=CMyString.filterForHTMLValue(sContentExtFieldOutName)%>" validation="type:'string',required:'',max_len:'50',desc:'显示名称'" validation_desc="显示名称" WCMAnt:paramattr="validation_desc:contentextfield_findbyid.jsp.outName"><%=CMyString.filterForHTMLValue(sContentExtFieldOutName)%></span>
</div>
<div class="attribute_row_sep"></div>
<div class="attribute_row descinfo readonly">
	<span class="wcm_attr_value" title="<%=LocaleServer.getString("template.label.cruser", "创建者：")%><%=sContentExtFieldCruser%>&#13;<%=LocaleServer.getString("template.label.crtime", "创建时间：")%><%=sContentExtFieldTime%>"><span WCMAnt:param="contentextfield_findbyid.jsp.user">用户</span><span class="value"><%=sContentExtFieldCruser%></span><span  WCMAnt:param="contentextfield_findbyid.jsp.create">创建于</span><span class="value"><%=sContentExtFieldTime%></span></span>
</div>
<div class="attribute_row readonly">
	<span WCMAnt:param="contentextfield_findbyid.jsp.objectName" class="wcm_attr_name" style="width:75px;">字段名称:</span>
	<span class="wcm_attr_value" style="width:75px;"><%=sContentExtFieldName%></span>
</div>
<div class="attribute_row readonly">
	<span WCMAnt:param="contentextfield_findbyid.jsp.objectType1" class="wcm_attr_name" style="width:75px;">字段类型:</span>
	<span class="wcm_attr_value" style="width:75px;"><%=display(currExtendedField.getAttributeValue("FIELDTYPE"))%></span>
</div>
<div class="attribute_row readonly">
	<span WCMAnt:param="contentextfield_findbyid.jsp.objectType" class="wcm_attr_name" style="width:75px;">库中类型:</span>
	<span class="wcm_attr_value" style="width:75px;"><%=sContentExtFieldTypeName%></span>
</div>
<%
	if(sFieldTypeName == null || "678".indexOf(sFieldTypeName) == -1){
%>
<div class="attribute_row readonly">
	<span WCMAnt:param="contentextfield_findbyid.jsp.defaultvalue" class="wcm_attr_name" style="width:75px;">默认值:</span>
	<span class="wcm_attr_value" title="<%=sDefaultValue%>" style="width:75px;"><%=sDefaultValue%></span>
</div>
<%
	}else{
%>
<div class="attribute_row readonly">
	<span WCMAnt:param="contentextfield_findbyid.jsp.evumvalue" class="wcm_attr_name" style="width:75px;">枚举值:</span>
	<span class="wcm_attr_value" title="<%=sEnumValue%>" style="width:75px;"><%=sEnumValue%></span>
</div>
<div class="attribute_row readonly">
	<span WCMAnt:param="contentextfield_findbyid.jsp.enumdefault" class="wcm_attr_name" style="width:75px;">默认值:</span>
	<span class="wcm_attr_value" style="width:75px;"><%=sDefaultValue%></span>
</div>
<%
	}
%>
<div class="attribute_row readonly">
	<span WCMAnt:param="contentextfield_findbyid.jsp.objectLength" class="wcm_attr_name" style="width:75px;">字段长度:</span>
	<span class="wcm_attr_value" style="width:75px;"><%=nContentExtFieldLength%></span>
</div>
<div class="attribute_row_sep"></div>
<div class="attribute_row readonly channel">
	<span WCMAnt:param="contentextfield_findbyid.jsp.belongsTo" class="wcm_attr_name" style="width:75px;">所属对象:</span>
	<span class="wcm_attr_value" title="<%=host.toString()%>" style="width:75px;"><%=host.toString()%></span>
</div>

<%!
	private String display(String sOriginalValue){
		if(sOriginalValue == null || sOriginalValue.trim().equals(""))return LocaleServer.getString("contentextfield.label.selfdefine", "自定义类型");
		switch(Integer.parseInt(sOriginalValue)){
			case 1:
				return LocaleServer.getString("contentextfield.label.selfdefine", "自定义类型");
			case 2:
				return LocaleServer.getString("contentextfield.label.password", "密码文本");
			case 3:
				return LocaleServer.getString("contentextfield.label.normaltext", "普通文本");
			case 4:
				return LocaleServer.getString("contentextfield.label.multitext", "多行文本");
			case 7:
				return LocaleServer.getString("contentextfield.label.select", "下拉框");
			case 6:
				return LocaleServer.getString("contentextfield.label.radio", "单选框");
			case 8:
				return LocaleServer.getString("contentextfield.label.checkbox", "多选框");
			case 9:
				return LocaleServer.getString("contentextfield.label.timetemp", "时间");
			case 10:
				return LocaleServer.getString("contentextfield.label.eidtor", "简易编辑器");
			case 0:
				return LocaleServer.getString("contentextfield.label.inputselect", "可输入下拉框");
			default	:
				return LocaleServer.getString("contentextfield.label.undefined", "未知类型");
		}
	}
%>