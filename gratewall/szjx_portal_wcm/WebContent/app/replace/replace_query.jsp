<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.Replace" %>
<%@ page import="com.trs.components.wcm.content.persistent.Replaces" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel"%>
<%
try{
%>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof Replaces)){
		throw new WCMException(CMyString.format(LocaleServer.getString("replace_query.jsp.service", "服务(com.trs.ajaxservice.ReplaceContentServiceProvider.query)返回的对象集合类型不为Replaces，而为{0}，请确认。"), new Object[]{result.getClass()}));
	}
	Replaces objs = (Replaces)result;
%>
<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
	<tr id="grid_head" class="grid_head">
		<td onclick="wcm.Grid.selectAll();" width="50" WCMAnt:param="list.selectall" class="selAll">全选</td>
		
		<td WCMAnt:param="replace_list.head.edit" width="40">修改</td>
<td grid_sortby="replacename" width="30%"><span WCMAnt:param="replace_list.head.replacename">标题</span><%=getOrderFlag("replacename", sCurrOrderBy)%></td>
<td grid_sortby="replacecontent"><span WCMAnt:param="replace_list.head.replacecontent">内容</span><%=getOrderFlag("replacecontent", sCurrOrderBy)%></td>
<td WCMAnt:param="replace_list.head.delete" width="40">删除</td>

	</tr>
	<tbody class="grid_body" id="grid_body">
<%
//5. 遍历生成表现
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			Replace obj = (Replace)objs.getAt(i - 1);
			if (obj == null)
				continue;
			int nRowId = obj.getId();
			Channel srcChannel = obj.getChannel();
			boolean bIsSelected = strSelectedIds.indexOf(","+nRowId+",")!=-1;
			boolean bCanEdit = hasRight(loginUser,srcChannel,13);
			boolean bCanDel = hasRight(loginUser,srcChannel,13);
			String sRightValue = getRightValueByMember(srcChannel, loginUser).toString();
			String sRowClassName = i%2==0?"grid_row_even":"grid_row_odd";
%>
	<tr id="tr_<%=nRowId%>" rowid="<%=nRowId%>" class="grid_row  <%=sRowClassName%> <%=(bIsSelected)?" grid_row_active":""%>" right="<%=sRightValue%>">
		<td><input type="checkbox" id="cb_<%=nRowId%>" class="grid_checkbox" name="RowId" value="<%=nRowId%>" <%=(bIsSelected)?"checked":""%>/><span class="grid_index" id="grid_index_<%=nRowId%>"><%=i%></span></td>
		
		<td><span class="<%=(bCanEdit)?"object_edit":"objectcannot_edit"%> grid_function" grid_function="edit">&nbsp;</span></td>
<td id="replacename_<%=nRowId%>" style="text-align:left;">
		<%=CMyString.transDisplay(obj.getPropertyAsString("replacename"))%></td>
<td id="replacecontent_<%=nRowId%>" style="text-align:left;padding-left:10px">
		<%=CMyString.transDisplay(obj.getPropertyAsString("replacecontent"))%></td>
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
		<tr><td colspan="5" class="no_object_found" WCMAnt:param="list.NoObjectFound">不好意思, 没有找到符合条件的对象!</td></tr>
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
<%
}catch(Throwable tx){
	tx.printStackTrace();
	throw new WCMException(LocaleServer.getString("replace_query.jsp.label.runtimeexception", "replace_query.jsp运行期异常!"), tx);
}
%>