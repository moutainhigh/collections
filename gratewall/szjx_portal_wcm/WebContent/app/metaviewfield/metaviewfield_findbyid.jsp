<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.metadata.definition.MetaViewField" %>
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
	private String convertDateTimeValueToString(MethodContext _methodContext, CMyDateTime _dtValue) {
		String sDateTimeFormat = CMyDateTime.DEF_DATETIME_FORMAT_PRG;
		if (_methodContext != null) {
			sDateTimeFormat = _methodContext.getValue("DateTimeFormat");
			if (sDateTimeFormat == null) {
				sDateTimeFormat = CMyDateTime.DEF_DATETIME_FORMAT_PRG;
			}
		}
		String sDtValue = _dtValue.toString(sDateTimeFormat);
		return sDtValue;
	}
%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof MetaViewField)){
		throw new WCMException(CMyString.format(LocaleServer.getString("metaviewfield_findbyid.jsp.result_not_matching", "服务(com.trs.components.metadata.service.MetaDataDefServiceProvider.findbyid)返回的对象类型不为MetaViewField，而为{0}，请确认。"), new Object[]{result.getClass()}));
	}
	MetaViewField obj = (MetaViewField)result;
	int nViewFieldId = obj.getId();

	boolean bCanEdit = hasRight(loginUser,obj,13);
	String sEditable = bCanEdit?"editable":"readonly";

	String sCrUser = CMyString.transDisplay(obj.getPropertyAsString("cruser"));
	String sCrTime = convertDateTimeValueToString(oMethodContext,obj.getPropertyAsDateTime("CrTime"));
	String sFieldName = CMyString.transDisplay(obj.getPropertyAsString("FIELDNAME"));
	String sAnotherName = CMyString.transDisplay(obj.getPropertyAsString("ANOTHERNAME"));

    
    String	sFieldType = CMyString.transDisplay(obj.getTypeDesc());
	String	sDBType = CMyString.transDisplay(obj.getDBTypeDesc());
	String sHostServiceAttr = "_serviceId=\"wcm61_metaviewfield\"";
	String sHostMethodAttr = "_methodName=\"saveViewField\"";
%>
<!--//TODO type findbyid here-->
	<div class="attribute_row descinfo readonly">
        <span title="<%=LocaleServer.getString("metaviewfield.label.viewfieldid", "字段编号")%>:<%=nViewFieldId%>&#13;<%=LocaleServer.getString("metaviewfield.label.cruser", "创建者")%>:<%=sCrUser%>&#13;<%=LocaleServer.getString("metaviewfield.label.crtime", "创建时间")%>:<%=sCrTime%> " style="white-space:nowrap;overflow:hidden;">
            <span WCMAnt:param="metaviewfield_findbyid.jsp.CrUser">用户</span><span class="value"><%=sCrUser%></span>
			<span WCMAnt:param="metaviewfield_findbyid.jsp.CrTime">创建于</span><span class="value"><%=sCrTime%></span>
        </span>
    </div>
	<div class="attribute_row <%=sEditable%>">
		<span class="wcm_attr_name" WCMAnt:param="metaviewfield_findbyid.jsp.fieldName">英文名称:</span>
        <span class="wcm_attr_value" style="color:#09549F;" _fieldName="FieldName" _fieldValue="<%=sFieldName%>" title="<%=sFieldName%>" <%=sHostServiceAttr%> <%=sHostMethodAttr%> >
		<%=sFieldName%>&nbsp;
        </span>
	</div>
	<div class="attribute_row <%=sEditable%>">
		<span class="wcm_attr_name" WCMAnt:param="metaviewfield_findbyid.jsp.anotherName">中文名称:</span>
        <span class="wcm_attr_value" style="color:#09549F;" _fieldName="AnotherName" _fieldValue="<%=sAnotherName%>" title="<%=sAnotherName%>" <%=sHostServiceAttr%> <%=sHostMethodAttr%> >
		<%=sAnotherName%>&nbsp;
        </span>
	</div>
	<div class="attribute_row readonly">
	<span class="wcm_attr_name" style="width:40px;" WCMAnt:param="metaviewfield_findbyid.jsp.fieldType">类型:</span>
	<span class="wcm_attr_value" title="<%=sFieldType%>"><%=sFieldType%></span>
	</div>
	<div class="attribute_row readonly">
		<span class="wcm_attr_name" style="width:60px;" WCMAnt:param="metaviewfield_findbyid.jsp.dbType">库中类型:</span>
		<span class="wcm_attr_value" title="<%=sDBType%>"><%=sDBType%></span>
	</div>
	
<%
}catch(Throwable tx){
	tx.printStackTrace();
	throw new WCMException(LocaleServer.getString("metaviewfield_findbyid.jsp.label.runtimeexception","metaviewfield_findbyid.jsp运行期异常!"), tx);
}
%>