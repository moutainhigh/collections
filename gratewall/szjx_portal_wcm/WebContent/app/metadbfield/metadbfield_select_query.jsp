<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.metadata.definition.MetaDBField" %>
<%@ page import="com.trs.components.metadata.definition.MetaDBFields" %>
<%@ page import="com.trs.components.metadata.definition.MetaDBTable" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewFields" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewField" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.components.metadata.right.AuthServerMgr" %>
<%@ page import="com.trs.components.metadata.MetaDataConstants" %>
<%
try{
%>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof MetaDBFields)){
		throw new WCMException( CMyString.format(LocaleServer.getString("select_query.type","服务(com.trs.components.metadata.service.MetaDataDefServiceProvider.query)返回的对象集合类型不为MetaDBFields，而为{0}，请确认。"),new Object[]{result.getClass()}));
	}
	MetaDBFields oMetaDBFields = (MetaDBFields)result;
	int nMetaDbTableId = oMethodContext.getValue("tableInfoId",0);
	int nHasRight = 0;
	MetaDBTable oMetaDBTable = MetaDBTable.findById(nMetaDbTableId);
	nHasRight = AuthServerMgr.hasRight(loginUser, oMetaDBTable)== true ? 1 :0;

	int nViewId = oMethodContext.getValue("ViewId",0);
	MetaView oMetaView = MetaView.findById(nViewId);
	String _sSelectIds = "";
	if(oMetaView == null){
		throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND,   CMyString.format(LocaleServer.getString("select_query.id.zero","没有找到ID为[{0}]的视图"),new int[]{nViewId}));
	}else{
		WCMFilter filter = new WCMFilter("", "", "", "DBFIELD");							 
		MetaViewFields oMetaViewFields = oMetaView.getViewFields(loginUser,filter);
		
		for(int i = 0 ; i < oMetaViewFields.size(); i++ ){
			MetaViewField oMetaViewField = (MetaViewField)oMetaViewFields.getAt(i);
			 _sSelectIds += "," + oMetaViewField.getPropertyAsInt("DBFIELD", 0); 
		}
		_sSelectIds += ",";
	}
%>
<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
	<tr id="grid_head" class="grid_head">
		<td onclick="wcm.Grid.selectAll();" width="50" WCMAnt:param="list.selectAll" class="selAll">全选</td>
		<td class="grid_head_column" style="width:50px" WCMAnt:param="metadbfield_list.head.edit">编辑</td>
		<td grid_sortby="anotherName" width="120"><span WCMAnt:param="metadbfield_list.head.anotherName">中文名称</span><%=getOrderFlag("anotherName", sCurrOrderBy)%></td>
		<td grid_sortby="fieldName" width="120"><span WCMAnt:param="metadbfield_list.head.fieldName">英文名称</span><%=getOrderFlag("fieldName", sCurrOrderBy)%></td>
		<td grid_sortby="fieldType"><span WCMAnt:param="metadbfield_list.head.fieldType">类型</span><%=getOrderFlag("fieldType", sCurrOrderBy)%></td>
		<td grid_sortby="dbType" width="100"><span WCMAnt:param="metadbfield_list.head.dbType">库中类型</span><%=getOrderFlag("dbType", sCurrOrderBy)%></td>
		<td class="grid_head_column" style="width:50px;border-right:0" WCMAnt:param="metadbfield_list.head.delete">删除</td>
	</tr>
	<tbody class="grid_body" id="grid_body">
<%
//5. 遍历生成表现
	for (int i = 0; i < oMetaDBFields.size(); i++) {
		try{
			MetaDBField obj = (MetaDBField)oMetaDBFields.getAt(i);
			if (obj == null)
				continue;
			int nRowId = obj.getId();
			boolean bIsSelected = _sSelectIds.indexOf("," + nRowId + ",")!=-1;
			String sRightValue = getRightValue(obj, loginUser).toString();
			String sRowClassName = i%2==0?"grid_row_even":"grid_row_odd";
			String sFieldName = CMyString.transDisplay(obj.getPropertyAsString("FIELDNAME"));
			String sAnotherName = CMyString.transDisplay(obj.getPropertyAsString("ANOTHERNAME"));
			int fieldType = obj.getPropertyAsInt("FIELDTYPE", 0);
			String sTypeDesc = "";
			switch(MetaDataConstants.formatOfEnumValue(fieldType)){
				case MetaDataConstants.FIELD_TYPE_ENUMVALUE:
					sTypeDesc = CMyString.transDisplay(obj.getTypeDesc()) + " " + CMyString.transDisplay(obj.getPropertyAsString("ENMVALUE"));
					break;
				case MetaDataConstants.FIELD_TYPE_CLASS:
					sTypeDesc = CMyString.transDisplay(obj.getTypeDesc()) + " " + CMyString.transDisplay(obj.getClassName());
					break;
				default:
					sTypeDesc = CMyString.transDisplay(obj.getTypeDesc());
			}
			String sDBTypeDesc = CMyString.transDisplay(obj.getDBTypeDesc());
%>
	<tr id="tr_<%=nRowId%>" rowid="<%=nRowId%>" class="grid_row  <%=sRowClassName%> <%=(bIsSelected)?" grid_row_active":""%>" right="<%=sRightValue%>">
		<td><input type="checkbox" id="cb_<%=nRowId%>" class="grid_checkbox" name="RowId" value="<%=nRowId%>" <%=(bIsSelected)?"checked":""%>/><span class="grid_index" id="grid_index_<%=nRowId%>"><%=i+1%></span></td>
		<td><span class="<%=(nHasRight==1)?"object_edit":"objectcannot_edit"%> grid_function" grid_function="edit">&nbsp;</span></td>
		<td id="ANOTHERNAME_<%=nRowId%>" class="title" title="<%=sAnotherName%>"><%=sAnotherName%></td>
		<td id="FIELDNAME_<%=nRowId%>" class="title" title="<%=sFieldName%>"><%=sFieldName%></td>
		<td id="FIELDTYPE_<%=nRowId%>" class="title fieldtype" title="<%=sTypeDesc%>"><%=sTypeDesc%></td>
		<td id="DBTYPE_<%=nRowId%>"><%=sDBTypeDesc%></td>
		<td><span class="<%=(nHasRight==1)?"object_delete":"objectcannot_delete"%> grid_function" style="width:30px;border-right:0" grid_function="delete">&nbsp;</span></td>
	</tr>
<%
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
%>
	</tbody>
	<tbody id="grid_NoObjectFound" style="display:none;">
		<tr><td colspan="7" class="no_object_found" WCMAnt:param="list.NoObjectFound">不好意思, 没有找到符合条件的对象!</td></tr>
	</tbody>
</table>
<script>
	try{
		var context = PageContext.getContext();
		Ext.apply(context.params, {
			HASRIGHT : <%=nHasRight%>
		});
		wcm.Grid.init({
			OrderBy : '<%=sCurrOrderBy%>',
			RecordNum : <%=currPager.getItemCount()%>
		});
	}catch(err){
		//Just skip it.
	}
</script>
<%
}catch(Throwable tx){	
	throw new WCMException(LocaleServer.getString("select_query.runExces","metadbfield_select_query.jsp运行期异常!"), tx);
}
%>