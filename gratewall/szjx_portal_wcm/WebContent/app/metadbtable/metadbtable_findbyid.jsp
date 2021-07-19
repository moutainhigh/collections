<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.metadata.definition.MetaDBTable" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%
try{
%>
<%@include file="../include/findbyid_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof MetaDBTable)){
		throw new WCMException(CMyString.format(LocaleServer.getString("metadbtable_findbyid.type","服务(com.trs.components.metadata.service.MetaDataDefServiceProvider.findbyid)返回的对象类型不为MetaDBTable，而为({0})，请确认。"),new Object[]{result.getClass()}));
	}
	MetaDBTable obj = (MetaDBTable)result;
	String sTabelName = CMyString.filterForHTMLValue(obj.getPropertyAsString("TABLENAME"));
	String sAnotherName = CMyString.filterForHTMLValue(obj.getPropertyAsString("ANOTHERNAME"));
	String sTableDesc = CMyString.filterForHTMLValue(obj.getPropertyAsString("TABLEDESC"));
	String sEditable = canEdit(loginUser,obj)?"editable":"";
%>
	<div class="attribute_row descinfo readonly">
		<span title="<%=LocaleServer.getString("metadbtable.label.metadbtableId", "元数据编号")%>:<%=obj.getId()%>&#13;<%=LocaleServer.getString("metadbtable.label.cruser", "创建者")%>:<%=obj.getCrUser().getName()%>&#13;<%=LocaleServer.getString("metadbtable.label.crtime", "创建时间")%>:<%=obj.getCrTime()%> " class="wcm_attr_value"><span WCMAnt:param="metadbtable_findbyid.jsp.cruser">用户</span><span class="value"><%=obj.getCrUser().getName()%></span><span WCMAnt:param="metadbtable_findbyid.jsp.create">创建于</span><span class="value"><%=obj.getCrTime().toString("yyyy-MM-dd HH:mm")%></span></span>
    </div>	
	<div class="attribute_row <%=sEditable%>">
		<span class="wcm_attr_name" style="width:40px;" WCMAnt:param="metadbtable_findbyid.jsp.tableName">名称:</span>
        <span class="wcm_attr_value" _methodName="saveDBTableInfo" _fieldName="TableName" _fieldValue="<%=sTabelName%>" title="<%=sTabelName%>" style="color:#09549F">
            <%=sTabelName%>
        </span>
	</div>
	<div class="attribute_row <%=sEditable%>">
		<span class="wcm_attr_name" style="width:40px;" WCMAnt:param="metadbtable_findbyid.jsp.anotherName">别名:</span>
        <span class="wcm_attr_value" _methodName="saveDBTableInfo" _fieldName="AnotherName" _fieldValue="<%=sAnotherName%>" title="<%=sAnotherName%>" style="color:#09549F">
            <%=sAnotherName%>
        </span>
	</div>
	<div class="attribute_row <%=sEditable%>">
		<span class="wcm_attr_name" style="width:40px;" WCMAnt:param="metadbtable_findbyid.jsp.tableDesc">描述:</span>
        <span class="wcm_attr_value" _methodName="saveDBTableInfo" _fieldName="TableDesc" _fieldValue="<%=sTableDesc%>" title="<%=sTableDesc%>" style="color:#09549F">
            <%=sTableDesc%>
        </span>
	</div>
<%
}catch(Throwable tx){	
	throw new WCMException(LocaleServer.getString("metadbtable_findbyid.runExce","metadbtable_findbyid.jsp运行期异常!"), tx);
}
%>

<%!
	public boolean canEdit(User user,MetaDBTable obj){
		try{
			if(user.isAdministrator()) return true;
			int nOwnerType = obj.getOwnerType();
			int nOwnerId = obj.getOwnerId();
			return (nOwnerType == Channel.OBJ_TYPE) && AuthServer.hasRight(user, Channel.findById(nOwnerId), WCMRightTypes.CHNL_EDIT);				
		}catch(Exception e){
			return false;
		}
	}
%>