<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.Replace" %>
<%@ page import="com.trs.infra.util.CMyString" %>
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
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof Replace)){
		throw new WCMException(CMyString.format(LocaleServer.getString("replace_findbyid.jsp.service", "服务(com.trs.ajaxservice.ReplaceContentServiceProvider.findbyid)返回的对象类型不为Replace，而为{0}，请确认。"), new Object[]{result.getClass()}));
	}
	Replace obj = (Replace)result;
	String sReplaceName = CMyString.filterForHTMLValue(obj.getName());
	String sReplaceContent = CMyString.filterForHTMLValue(obj.getContent());
	boolean bCanEdit = hasRight(loginUser,obj,13);
	String sEditable = bCanEdit?"editable":"readonly";
%>
<!--//TODO type findbyid here-->
<div class="attribute_row ReplaceName <%=sEditable%>">
	<span WCMAnt:param="replace_findbyid.jsp.ReplaceName" class="wcm_attr_name" style="width:40px">标题:</span>
	<span class="wcm_attr_value" _fieldName="ReplaceName" _fieldValue="<%=sReplaceName%>" style="color:#09549F" title="<%=sReplaceName%>"><%=sReplaceName%></span>
</div>
<div class="attribute_row ReplaceContent <%=sEditable%>">
	<span WCMAnt:param="replace_findbyid.jsp.ReplaceContent" class="wcm_attr_name" style="width:40px">内容:</span>
	<span class="wcm_attr_value" _fieldName="ReplaceContent" _fieldValue="<%=sReplaceContent%>" style="color:#09549F" title="<%=sReplaceContent%>"><%=sReplaceContent%></span>
</div>

<%
}catch(Throwable tx){
	tx.printStackTrace();
		throw new WCMException(LocaleServer.getString("replace_findbyid.runtime.error", "replace_findbyid.jsp运行期异常!"),tx);
}
%>