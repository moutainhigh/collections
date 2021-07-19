<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.metadata.definition.ClassInfo" %>
<%@ page import="com.trs.components.metadata.definition.ClassInfos" %>
<%
try{
%>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof ClassInfos)){
		throw new WCMException(CMyString.format(LocaleServer.getString("classinfo_query.jsp.servicenoobject","服务(com.trs.components.metadata.service.ClassInfoServiceProvider.query)返回的对象集合类型不为classinfos，而为{0}，请确认。"),new Object[]{result.getClass()}));
	}
	ClassInfos objs = (ClassInfos)result;
	if (!loginUser.isAdministrator()) {
        throw new WCMException(ExceptionNumber.ERR_USER_NORIGHT,LocaleServer.getString("classinfo_query.jsp.youarenotmng","您不是管理员，没有权限管理分类法列表！"));
    }
%>
<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
	<tr id="grid_head" class="grid_head">
		<td onclick="wcm.Grid.selectAll();" width="50px" WCMAnt:param="list.selectall" class="selAll">全选</td>
		<td WCMAnt:param="classinfo_list.head.edit" width="40">维护</td>
		<td grid_sortby="cName" width="150px"><span WCMAnt:param="classinfo_list.head.cName">名称</span><%=getOrderFlag("cName", sCurrOrderBy)%></td>
		<td grid_sortby="cDesc"><span WCMAnt:param="classinfo_list.head.cDesc">描述</span><%=getOrderFlag("cDesc", sCurrOrderBy)%></td>
		<td grid_sortby="cruser" width="70px"><span WCMAnt:param="classinfo_list.head.cruser">创建者</span><%=getOrderFlag("cruser", sCurrOrderBy)%></td>
		<td grid_sortby="crtime" width="120px"><span WCMAnt:param="classinfo_list.head.crtime">创建时间</span><%=getOrderFlag("crtime", sCurrOrderBy)%></td>
<td WCMAnt:param="classinfo_list.head.delete" style="width:30px;border-right:0">删除</td>

	</tr>
	<tbody class="grid_body" id="grid_body">
<%
//5. 遍历生成表现
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			ClassInfo obj = (ClassInfo)objs.getAt(i - 1);
			if (obj == null)
				continue;
			int nRowId = obj.getId();
			boolean bIsSelected = strSelectedIds.indexOf(","+nRowId+",")!=-1;
			String sRightValue = getRightValue(obj, loginUser).toString();
			String sRowClassName = i%2==0?"grid_row_even":"grid_row_odd";
			String sName = CMyString.transDisplay(obj.getPropertyAsString("CNAME"));
			String sDesc = CMyString.transDisplay(obj.getPropertyAsString("CDESC"));
			String sCrUser = obj.getPropertyAsString("CRUSER");
			String sCrTime = convertDateTimeValueToString(oMethodContext,
		obj.getPropertyAsDateTime("CRTIME"));
%>
	<tr id="tr_<%=nRowId%>" rowid="<%=nRowId%>" objectName="" class="grid_row  <%=sRowClassName%> <%=(bIsSelected)?" grid_row_active":""%>" right="<%=sRightValue%>">
		<td><input type="checkbox" id="cb_<%=nRowId%>" class="grid_checkbox" name="RowId" value="<%=nRowId%>" <%=(bIsSelected)?"checked":""%>/><span class="grid_index" id="grid_index_<%=nRowId%>"><%=i%></span></td>
		
		<td><span class="object_edit grid_function" grid_function="config">&nbsp;</span></td>
<td id="CNAME_<%=nRowId%>" class="title" title="<%=sName%>">
		<%=sName%></td>
<td id="CDESC_<%=nRowId%>" class="title" title="<%=sDesc%>">
		<%=sDesc%></td>
<td id="CRUSER_<%=nRowId%>">
		<%=sCrUser%></td>
<td id="CRTIME_<%=nRowId%>">
		<%=sCrTime%></td>
		<td><span class="object_delete grid_function" style="width:30px;border-right:0" grid_function="delete">&nbsp;</span></td>
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
	throw new WCMException(LocaleServer.getString("classinfo_query.jsp.runtimeex","classinfo_query.jsp运行期异常!"), tx);
}
%>