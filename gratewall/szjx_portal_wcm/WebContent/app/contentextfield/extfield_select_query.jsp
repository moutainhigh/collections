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
<td grid_sortby="WCMCONTENTEXTFIELD.LOGICFIELDDESC"  width="33%"><span WCMAnt:param="contentextfield_list.head.LOGICFIELDDESC">显示名称</span><%=getOrderFlag("WCMCONTENTEXTFIELD.LOGICFIELDDESC", sCurrOrderBy)%></td>
<td grid_sortby="FieldName" width="33%"><span WCMAnt:param="contentextfield_list.head.FieldName">字段名称</span><%=getOrderFlag("FieldName", sCurrOrderBy)%></td>
<td grid_sortby="WCMCONTENTEXTFIELD.CRUSER" width="60"><span WCMAnt:param="contentextfield_list.head.CRUSER">创建者</span><%=getOrderFlag("WCMCONTENTEXTFIELD.CRUSER", sCurrOrderBy)%></td>
<td grid_sortby="WCMCONTENTEXTFIELD.CRTIME" width="80"><span WCMAnt:param="contentextfield_list.head.CRTIME">创建时间</span><%=getOrderFlag("WCMCONTENTEXTFIELD.CRTIME", sCurrOrderBy)%></td>

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
			// String sRightValue = getRightValue(host, loginUser).toString();
			String sRowClassName = i%2==0?"grid_row_even":"grid_row_odd";
%>
	<tr id="tr_<%=nRowId%>" rowid="<%=nRowId%>" class="grid_row <%=sRowClassName%> <%=(bIsSelected)?"grid_row_active":""%>">
		<td><input type="checkbox" id="cb_<%=nRowId%>" class="grid_checkbox" name="RowId" value="<%=nRowId%>"  <%=(bIsSelected)?"checked":""%>/><span class="grid_index" id="grid_index_<%=nRowId%>"><%=i%></span></td>		
		<td id="LOGICFIELDDESC_<%=nRowId%>" style="text-align:left" title="<%=CMyString.transDisplay(obj.getPropertyAsString("LOGICFIELDDESC"))%>">
				<%=CMyString.transDisplay(obj.getPropertyAsString("LOGICFIELDDESC"))%></td>
		<td id="FieldName_<%=nRowId%>" style="text-align:left" title="<%=CMyString.transDisplay(obj.getName())%>">
				<%=CMyString.transDisplay(obj.getName())%></td>
		<td id="CRUSER_<%=nRowId%>">
				<%=obj.getPropertyAsString("CRUSER")%></td>
		<td id="CRTIME_<%=nRowId%>">
				<%=convertDateTimeValueToString(oMethodContext, obj.getPropertyAsDateTime("CRTIME"))%></td>
	</tr>
<%
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
%>
	</tbody>
	<tbody id="grid_NoObjectFound" style="display:none;">
		<tr><td colspan="10" class="no_object_found" WCMAnt:param="list.NoObjectFound">不好意思, 没有找到符合条件的对象!</td></tr>
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