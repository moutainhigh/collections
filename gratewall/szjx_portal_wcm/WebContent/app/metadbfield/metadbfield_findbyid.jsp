<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.metadata.definition.MetaDBField" %>
<%
try{
%>
<%@include file="../include/findbyid_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof MetaDBField)){
		throw new WCMException(CMyString.format(LocaleServer.getString("etadbfield_findbyid.type","服务(com.trs.components.metadata.service.MetaDataDefServiceProvider.findbyid)返回的对象类型不为MetaDBField，而为({0})，请确认。"),new Object[]{result.getClass()}));
	}
	MetaDBField obj = (MetaDBField)result;
	String sFieldName = CMyString.filterForHTMLValue(obj.getName());
	String sAnotherName = CMyString.filterForHTMLValue(obj.getAnotherName());	
	String sDbTypeDesc = CMyString.filterForHTMLValue(obj.getDBTypeDesc());
	String sFieldTypeDesc = CMyString.filterForHTMLValue(obj.getTypeDesc());
	String sEditable = "editable";
%>
<div class="attribute_row descinfo readonly">
		<span title="<%=LocaleServer.getString("metadbfield.label.metadbfieldid", "元数据编号")%>:<%=obj.getId()%>&#13;<%=LocaleServer.getString("metadbfield.label.cruser", "创建者")%>:<%=obj.getCrUser().getName()%>&#13;<%=LocaleServer.getString("metadbfield.label.crtime", "创建时间")%>:<%=obj.getCrTime()%> " class="wcm_attr_value"><span  WCMAnt:param="metadbfield_findbyid.jsp.cruser">用户</span><span class="value"><%=obj.getCrUser().getName()%></span><span  WCMAnt:param="metadbfield_findbyid.jsp.create">创建于</span><span class="value"><%=obj.getCrTime().toString("HH:mm")%></span></span>
    </div>	
<div class="attribute_row readonly">
	<span class="wcm_attr_name" style="width:40px;" WCMAnt:param="metadbfield_findbyid.jsp.fieldTypeDesc">类型:</span>
	<span class="wcm_attr_value" title="<%=sFieldTypeDesc%>">
		<%=sFieldTypeDesc%>		
	</span>
</div>
<div class="attribute_row readonly">
	<span class="wcm_attr_name" style="width:60px;" WCMAnt:param="metadbfield_findbyid.jsp.dbTypeDesc">库中类型:</span>
	<span class="wcm_attr_value" title="<%=sDbTypeDesc%>">
		<%=sDbTypeDesc%>
	</span>
</div>
<div class="attribute_row <%=sEditable%>">
	<span class="wcm_attr_name" style="width:60px;" WCMAnt:param="metadbfield_findbyid.jsp.tableName">英文名称:</span>
	<span class="wcm_attr_value" _methodName="saveDBFieldInfo" _fieldName="FieldName" _fieldValue="<%=sFieldName%>" title ="<%=sFieldName%>" style="color:#09549F" >
		<%=sFieldName%>
	</span>
</div>
<div class="attribute_row <%=sEditable%>">
	<span class="wcm_attr_name" style="width:60px;" WCMAnt:param="metadbfield_findbyid.jsp.anotherName">中文名称:</span>
	<span class="wcm_attr_value" _methodName="saveDBFieldInfo" _fieldName="anotherName" _fieldValue="<%=sAnotherName%>" title ="<%=sAnotherName%>" style="color:#09549F" >
		<%=sAnotherName%>
	</span>
</div>
<%
}catch(Throwable tx){	
	throw new WCMException(LocaleServer.getString("metadbfield_findbyid.runExce","metadbfield_findbyid.jsp运行期异常!"), tx);
}
%>