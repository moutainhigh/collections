<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtField" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtFields" %>
<%@ page import="com.trs.cms.content.ExtendedField" %>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	ContentExtFields objs = (ContentExtFields)request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
%>
<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
	<tr id="grid_head" class="grid_head">
		<td onclick="wcm.Grid.selectAll();" width="50" WCMAnt:param="list.selectall" class="selAll">全选</td>
		
		<td WCMAnt:param="contentextfield_list.head.edit" width="40">修改</td>
<td grid_sortby="WCMCONTENTEXTFIELD.LOGICFIELDDESC"><span WCMAnt:param="contentextfield_list.head.LOGICFIELDDESC">显示名称</span><%=getOrderFlag("WCMCONTENTEXTFIELD.LOGICFIELDDESC", sCurrOrderBy)%></td>
<td><span WCMAnt:param="contentextfield_list.head.HOSTNAME">所属对象</span></td>
<td grid_sortby="FieldName"><span WCMAnt:param="contentextfield_list.head.FieldName">字段名称</span><%=getOrderFlag("FieldName", sCurrOrderBy)%></td>
<td width="80"><span WCMAnt:param="contentextfield_list.head.FieldType">字段类型</span><%=getOrderFlag("WCMEXTFIELD.FIELDTYPE", sCurrOrderBy)%></td>
<td grid_sortby="WCMCONTENTEXTFIELD.CRUSER" width="60"><span WCMAnt:param="contentextfield_list.head.CRUSER">创建者</span><%=getOrderFlag("WCMCONTENTEXTFIELD.CRUSER", sCurrOrderBy)%></td>
<td grid_sortby="WCMCONTENTEXTFIELD.CRTIME" width="80"><span WCMAnt:param="contentextfield_list.head.CRTIME">创建时间</span><%=getOrderFlag("WCMCONTENTEXTFIELD.CRTIME", sCurrOrderBy)%></td>
<td WCMAnt:param="contentextfield_list.head.delete" width="40">删除</td>

	</tr>
	<tbody class="grid_body" id="grid_body">
<%
//5. 遍历生成表现
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			ContentExtField obj = (ContentExtField)objs.getAt(i - 1);
			if (obj == null){
				continue;
			}
			ExtendedField currExtendedField = ExtendedField.findById(obj.getExtFieldId());
			int nRowId = obj.getId();
			CMSObj host = obj.getHost();
			boolean bIsSelected = strSelectedIds.indexOf(","+nRowId+",")!=-1;
			boolean bCanEdit = hasRight(loginUser,host,19);
			boolean bCanDel = hasRight(loginUser,host,19);
			//String sRightValue = RightValue.getAdministratorRightValue().toString();
			String sRightValue = getRightValueByMember(host, loginUser).toString();
			String sRowClassName = i%2==0?"grid_row_even":"grid_row_odd";
%>
	<tr id="tr_<%=nRowId%>" rowid="<%=nRowId%>" class="grid_row <%=sRowClassName%> <%=(bIsSelected)?"grid_row_active":""%> <%=(bCanEdit)?"":"grid_selectdisable_row"%>" right="<%=sRightValue%>">
		<td><input type="checkbox" id="cb_<%=nRowId%>" class="grid_checkbox" name="RowId" value="<%=nRowId%>" <%=(bCanEdit)?"":"disabled"%> <%=(bIsSelected)?"checked":""%>/><span class="grid_index" id="grid_index_<%=nRowId%>"><%=i%></span></td>		
		<td><span class="<%=(bCanEdit)?"object_edit":"objectcannot_edit"%> grid_function" grid_function="edit">&nbsp;</span></td>
		<td id="LOGICFIELDDESC_<%=nRowId%>" style="text-align:left" title="<%=CMyString.transDisplay(obj.getPropertyAsString("LOGICFIELDDESC"))%>">
				<%=CMyString.transDisplay(obj.getPropertyAsString("LOGICFIELDDESC"))%></td>
		<td id="HOSTNAME_<%=nRowId%>" style="text-align:left" title="<%=CMyString.transDisplay(host.toString())%>">
				<%=CMyString.transDisplay(host.toString())%></td>
		<td id="FieldName_<%=nRowId%>" style="text-align:left" title="<%=CMyString.transDisplay(obj.getName())%>">
				<%=CMyString.transDisplay(obj.getName())%></td>
		<td id="FieldType_<%=nRowId%>">
				<%=display(currExtendedField.getAttributeValue("FIELDTYPE"))%></td>
		<td id="CRUSER_<%=nRowId%>">
				<%=obj.getPropertyAsString("CRUSER")%></td>
		<td id="CRTIME_<%=nRowId%>">
				<%=convertDateTimeValueToString(oMethodContext, obj.getPropertyAsDateTime("CRTIME"))%></td>
		<td><span class="<%=(bCanDel)?"object_delete":"objectcannot_delete"%> grid_function" grid_function="delete">&nbsp;</span></td>
	</tr>
<%
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
%>
	</tbody>
	<tbody id="grid_NoObjectFound" style="display:none;">
		<tr><td colspan="9" class="no_object_found" WCMAnt:param="list.NoObjectFound">不好意思, 没有找到符合条件的对象!</td></tr>
	</tbody>
</table>
<script>
	try{
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
	}catch(err){
		//Just skip it.
	}
</script>

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
				return LocaleServer.getString("contentextfield.label.editor", "简易编辑器");
			case 0:
				return LocaleServer.getString("contentextfield.label.inputselect", "可输入下拉框");
			default	:
				return LocaleServer.getString("contentextfield.label.undefined", "未知类型");
		}
	}
%>