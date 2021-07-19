<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.metadata.definition.MetaViewField" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewFields" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.components.metadata.MetaDataConstants" %>
<%@ page import="com.trs.components.metadata.right.AuthServerMgr" %>
<%
try{
%>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(result == null){
		result = MetaViewFields.createNewInstance(loginUser);
		currPager = new CPager(20);
		currPager.setItemCount(0);
	}
	if(!(result instanceof MetaViewFields)){
		throw new WCMException(CMyString.format(LocaleServer.getString("metaviewfield_query.jsp.result_not_matching", "服务(com.trs.components.metadata.service.MetaDataDefServiceProvider.findbyid)返回的对象类型不为MetaViewField，而为{0}，请确认。"), new Object[]{result.getClass()}));
	}
	MetaViewFields objs = (MetaViewFields)result;

	boolean bIsSingleTable = false;
	int nMainTableId = 0;
	int nViewId = 0;
 	MetaView oMetaView = (MetaView)oMethodContext.getObjectValue("View");
	int nHasRight = 0;
	String sRightValue = "";
	if(oMetaView != null){
		nHasRight = AuthServerMgr.hasRight(loginUser, oMetaView)== true ? 1 :0;
		sRightValue = (nHasRight==1)?"111111111111111111111111111111111111111111111111111111111111111":"0";
		bIsSingleTable = oMetaView.isMultiTable() ? false : true;
		nMainTableId = oMetaView.getMainTableId();
		nViewId = oMetaView.getId();
	}
%>
<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
	<tr id="grid_head" class="grid_head">
		<td onclick="wcm.Grid.selectAll();" width="50" WCMAnt:param="list.selectall" class="selAll">全选</td>		
		<td WCMAnt:param="metaviewfield_list.head.edit" width="40">编辑</td>
		<td grid_sortby="fieldName" width="100"><span WCMAnt:param="metaviewfield_list.head.fieldName">英文名称</span><%=getOrderFlag("fieldName", sCurrOrderBy)%></td>
		<td grid_sortby="anotherName" width="100"><span WCMAnt:param="metaviewfield_list.head.anotherName">中文名称</span><%=getOrderFlag("anotherName", sCurrOrderBy)%></td>
		<td grid_sortby="dbField" width="100"><span WCMAnt:param="metaviewfield_list.head.dbField">物理字段名称</span><%=getOrderFlag("dbField", sCurrOrderBy)%></td>
		<td grid_sortby="fieldType"><span WCMAnt:param="metaviewfield_list.head.fieldType">字段类型</span><%=getOrderFlag("fieldType", sCurrOrderBy)%></td>
		<td grid_sortby="inOutline" width="50"><span WCMAnt:param="metaviewfield_list.head.inOutline">概览显示</span><%=getOrderFlag("inOutline", sCurrOrderBy)%></td>
		<td grid_sortby="dbType" width="50"><span WCMAnt:param="metaviewfield_list.head.dbType">库中类型</span><%=getOrderFlag("dbType", sCurrOrderBy)%></td>
		<td WCMAnt:param="metaviewfield_list.head.delete" width="40">删除</td>
	</tr>
	<tbody class="grid_body" id="grid_body">
<%

//5. 遍历生成表现
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			MetaViewField obj = (MetaViewField)objs.getAt(i - 1);
			if (obj == null)
				continue;
			int nRowId = obj.getId();
			int nDBFieldId = obj.getDBFieldId();
			boolean bIsSelected = strSelectedIds.indexOf(","+nRowId+",")!=-1;
			//String sRightValue = getRightValueByMember(obj, loginUser).toString();
			String sRowClassName = i%2==0?"grid_row_even":"grid_row_odd";
			boolean bIsTitleField = obj.isTitleField();
			String sFieldName = CMyString.transDisplay(obj.getPropertyAsString("FIELDNAME"));
			String sAnotherName = CMyString.transDisplay(obj.getPropertyAsString("ANOTHERNAME"));
			String sDBName = CMyString.transDisplay(obj.getDBName());
			int fieldType = obj.getPropertyAsInt("FIELDTYPE", 0);
			String sTypeDesc = "";
			switch(MetaDataConstants.formatOfEnumValue(fieldType)){
				case MetaDataConstants.FIELD_TYPE_ENUMVALUE:
					sTypeDesc = CMyString.transDisplay(obj.getTypeDesc()) + " " + CMyString.transDisplay(obj.getPropertyAsString("ENMVALUE"));
					break;
				case MetaDataConstants.FIELD_TYPE_CLASS:
					String sClassName = CMyString.transDisplay(obj.getClassName());
					if(obj.getClassId() != 0 && CMyString.isEmpty(sClassName)){
						sClassName = CMyString.format(LocaleServer.getString("metaviewfield.object.not.found", "<font color=red> 指定的分类法不存在![ID={0}]</font>"), new int[]{ obj.getClassId()});
					}
					sTypeDesc = CMyString.transDisplay(obj.getTypeDesc()) + " " + sClassName;
					break;
				default:
					sTypeDesc = CMyString.transDisplay(obj.getTypeDesc());
			}
			String sDBTypeDesc = CMyString.transDisplay(obj.getDBTypeDesc());
			int nInOutline = obj.getPropertyAsInt("inOutline", 0);
%>
	<tr id="tr_<%=nRowId%>" rowid="<%=nRowId%>" class="grid_row grid_selectable_row <%=sRowClassName%> <%=(bIsSelected)?" grid_row_active":""%>" right="<%=sRightValue%>" dbFieldId=<%=nDBFieldId%> anotherName="<%=sAnotherName%>">
		<td><div class="<%=bIsTitleField ? "titleField" : "" %>"><input type="checkbox" id="cb_<%=nRowId%>" class="grid_checkbox " name="RowId" value="<%=nRowId%>" <%=(bIsSelected)?"checked":""%>/><span class="grid_index" id="grid_index_<%=nRowId%>"><%=i%></span></div></td>
		
		<td><span class="object_edit grid_function" style="width:30px" grid_function="edit">&nbsp;</span></td>
		<td class="title" title="<%=sFieldName%>"><%=sFieldName%></td>
		<td class="title" title="<%=sAnotherName%>"><%=sAnotherName%></td>
		<td class="title" title="<%=sDBName%>"><%=sDBName%></td>
		<td class="title" title="<%=sTypeDesc%>"><%=sTypeDesc%></td>
		<td><%=nInOutline == 1 ? 	LocaleServer.getString("metaviewdata.label.yes", "是") : LocaleServer.getString("metaviewdata.label.no", "否")%>
		</td>
		<td><%=sDBTypeDesc%></td>
		<td><span class="grid_function <%=nHasRight ==1 ?"object_delete":"objectcannot_delete no_right"%>" style="width:30px" grid_function="delete">&nbsp;</span></td>

	</tr>
<%
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
%>
	</tbody>
	<tbody id="grid_NoObjectFound" style="display:none;">
		<tr><td colspan="9" class="no_object_found" WCMAnt:param="metaviewfield_query.jsp.list.NoObjectFound">不好意思, 没有找到符合条件的对象!</td></tr>
	</tbody>
</table>
<script>
	try{
		var context = PageContext.getContext();
		Ext.apply(context.params, {
			ISSINGLETABLE : <%=bIsSingleTable%>,
			MAINTABLEID : <%=nMainTableId%>,		
			VIEWID : <%=nViewId%>,
			HASRIGHT : <%=nHasRight%>
		});
		wcm.Grid.init({
			OrderBy : '<%=sCurrOrderBy%>',
			RecordNum : <%=currPager.getItemCount()%>
		});
		PageContext.drawNavigator({
			Num : <%=currPager.getItemCount()%>,
			PageSize : <%=currPager.getPageSize()%>,
			PageCount : <%=currPager.getPageCount()%>,
			CurrPageIndex : <%=currPager.getCurrentPageIndex()%>
		});
		<%if(oMetaView !=null){%>
			if(getParameter('channelid') > 0){
				Element.hide($('returnTableInfoId'));
			}else{
				Element.show($('returnTableInfoId'));
				Event.observe("returnTableInfoId","click",function(){
					$MsgCenter.$main({
						objId : 4,
						objType : WCMConstants.OBJ_TYPE_WEBSITEROOT,
						tabType : 'metaview'			
					}).redirect();
				});
			}
			Element.show($('viewInfo'));
			Element.update("viewInfo","<%=CMyString.transDisplay(oMetaView.getDesc())%>[<%=oMetaView.getId()%>]");
		<%}else{%>
			Element.hide($('returnTableInfoId'));
			Element.hide($('viewInfo'));
		<%}%>
		
	}catch(err){
		alert(err.message);
		//Just skip it.
	}
</script>
<%
}catch(Throwable tx){
	tx.printStackTrace();
	throw new WCMException(LocaleServer.getString("metaviewfield_query.jsp.label.runtimeexception","metaviewfield_query.jsp运行期异常!"), tx);
}
%>