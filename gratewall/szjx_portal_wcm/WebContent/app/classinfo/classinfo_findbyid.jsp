<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.metadata.definition.ClassInfo" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%
try{
%>
<%@include file="../include/findbyid_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof ClassInfo)){
		throw new WCMException(CMyString.format(LocaleServer.getString("classinfo_findbyid.jsp.servicenoobject","服务(com.trs.components.metadata.service.ClassInfoServiceProvider.findbyid)返回的对象类型不为classinfo，而为{0}，请确认。"),new Object[]{result.getClass()}));
	}
	ClassInfo obj = (ClassInfo)result;
	String sEditable = loginUser.isAdministrator() ? "editable" : "readonly";
	String sName = CMyString.transDisplay(obj.getName());
	String sDesc = CMyString.transDisplay(obj.getDesc());
	String sCode = CMyString.transDisplay(obj.getCode());
%>
<div class="attribute_row descinfo readonly">
		<span title="<%=LocaleServer.getString("classinfo.label.classinfoId", "分类法编号")%>:<%=obj.getId()%>&#13;<%=LocaleServer.getString("classinfo.label.cruser", "创建者")%>:<%=obj.getCrUser().getName()%>&#13;<%=LocaleServer.getString("classinfo.label.crtime", "创建时间")%>:<%=obj.getCrTime().toString("yyyy-MM-dd HH:mm")%> " class="wcm_attr_value"><span WCMAnt:param="classinfo_findbyid.jsp.cruser">用户</span><span class="value"><%=obj.getCrUser().getName()%></span><span WCMAnt:param="classinfo_findbyid.jsp.create">创建于</span><span class="value"><%=obj.getCrTime().toString("yyyy-MM-dd HH:mm")%></span></span>
    </div>	
<div class="attribute_row readonly">
	<span class="wcm_attr_name" style="width:40px;" WCMAnt:param="classinfo_findbyid.jsp.classInfoId">编号:</span>
	<span class="wcm_attr_value" title="<%=obj.getId()%>"><%=obj.getId()%></span>
</div>
<div class="attribute_row <%=sEditable%>">
	<span class="wcm_attr_name" style="width:40px;" WCMAnt:param="classinfo_findbyid.jsp.classInfoName">名称:</span>
	<span class="wcm_attr_value" _serviceId="wcm6_classinfo" _methodName="saveClassInfo" _fieldName="Cname" _fieldValue="<%=sName%>" title="<%=sName%>" style="color:#09549F" >
		<%=sName%>
	</span>
</div>
<div class="attribute_row <%=sEditable%>">
	<span class="wcm_attr_name" style="width:40px;" WCMAnt:param="classinfo_findbyid.jsp.classInfoDesc">描述:</span>
	<span class="wcm_attr_value" _serviceId="wcm6_classinfo" _methodName="saveClassInfo" _fieldName="Cdesc" _fieldValue="<%=sDesc%>" title="<%=sDesc%>" style="color:#09549F" >
		<%=sDesc%>
	</span>
</div>
<div class="attribute_row <%=sEditable%>">
	<span class="wcm_attr_name" style="width:40px;" WCMAnt:param="classinfo_findbyid.jsp.encoding">编码:</span>
	<span class="wcm_attr_value" _serviceId="wcm6_classinfo" _methodName="saveClassInfo" _fieldName="Ccode" _fieldValue="<%=sCode%>" title="<%=sCode%>" style="color:#09549F" validation="max_len:'50',type:'string',desc:'编码'"  validation_desc="编码" WCMAnt:paramattr="validation_desc:classinfo_findbyid.jsp.code">
		<%=sCode%>
	</span>
</div>

<%
}catch(Throwable tx){
	throw new WCMException(LocaleServer.getString("classinfo_findbyid.jsp.classinfo_findbyidruntimeex","classinfo_findbyid.jsp运行期异常!"), tx);
}
%>