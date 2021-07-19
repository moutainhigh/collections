<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
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
	if(!(result instanceof MetaView)){
		throw new WCMException(CMyString.format(LocaleServer.getString("findbyid.type","服务(com.trs.components.metadata.service.MetaDataDefServiceProvider.findbyid)返回的对象类型不为MetaView，而为({0})，请确认。"),new Object[]{result.getClass()}));
	}
	MetaView obj = (MetaView)result;
	boolean bCanEdit = hasRight(loginUser,obj,-1);
	String sEditable = bCanEdit?"editable":"readonly";
	
	String sViewInfo = CMyString.filterForHTMLValue(obj.getIdFieldName());
	String sCruser = CMyString.filterForHTMLValue(obj.getPropertyAsString("cruser"));
	String sCrtime = obj.getPropertyAsDateTime("crtime").toString("yy-MM-dd HH:mm");
	String sViewDesc = CMyString.filterForHTMLValue(obj.getDesc());
	String sMainTableName = CMyString.filterForHTMLValue(obj.getMainTableName());
	String sHostServiceAttr = "_serviceId=\"wcm61_metaview\"";
	String sHostMethodAttr = "_methodName=\"saveView\"";

%>
<!--//TODO type findbyid here-->
	<div class="attribute_row descinfo readonly">
        <span title="<%=LocaleServer.getString("metaview_findById.label.viewInfo", "视图编号")%>: <%=sViewInfo%>&#13;<%=LocaleServer.getString("metaview_findById.label.cruser", "创建者")%>: <%=sCruser%>&#13;<%=LocaleServer.getString("metaview_findById.label.crtime", "创建时间")%>：<%=sCrtime%>" style="white-space:nowrap;overflow:hidden;">
            <span WCMAnt:param="metaview_findbyid.jsp.CrUser">用户</span><span class="value"><%=sCruser%></span>
			<span WCMAnt:param="metaview_findbyid.jsp.CrTime">创建于</span><span class="value"><%=sCrtime%></span>
        </span>
    </div>
	<div class="attribute_row <%=sEditable%>">
		<span class="wcm_attr_name" WCMAnt:param="metaview_findbyid.jsp.metaviewDesc">名称:</span>
        <span class="wcm_attr_value" style="color:#09549F;" _fieldName="ViewDesc" _fieldValue="<%=sViewDesc%>" title=<%=sViewDesc%> <%=sHostServiceAttr%> <%=sHostMethodAttr%>>
		<%=sViewDesc%>&nbsp;
        </span>
	</div>
	<div class="attribute_row readonly">
		<span class="wcm_attr_name" WCMAnt:param="metaview_findbyid.jsp.mainTableName">主表名:</span>
        <span class="wcm_attr_value" title="<%=sMainTableName%>"><%=sMainTableName%>&nbsp;</span>
	</div>
<%
}catch(Throwable tx){
	tx.printStackTrace();
	throw new WCMException(LocaleServer.getString("findbyid.runExce","metaview_findbyid.jsp运行期异常!"), tx);
}
%>