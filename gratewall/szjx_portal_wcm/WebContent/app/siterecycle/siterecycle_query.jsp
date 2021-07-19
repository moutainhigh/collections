<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSites" %>
<%
try{
%>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof WebSites)){
		throw new WCMException(CMyString.format(LocaleServer.getString("siterecycle_query.jsp.service", "服务(com.trs.ajaxservice.WebSiteServiceProvider.query)返回的对象类型不为WebSite，而为{0}，请确认。"), new Object[]{result.getClass()}));
		
	}
	WebSites objs = (WebSites)result;
%>
<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
	<tr id="grid_head" class="grid_head">
		<td onclick="wcm.Grid.selectAll();" width="50" WCMAnt:param="list.selectall" class="selAll">全选</td>
		
		<td WCMAnt:param="website_list.head.restore" width="40">还原</td>
<td grid_sortby="SiteDesc"><span  WCMAnt:param="siterecycle_list.head.SiteDesc">显示名称</span><%=getOrderFlag("SiteDesc", sCurrOrderBy)%></td>
<td grid_sortby="SiteName"><span  WCMAnt:param="siterecycle_list.head.SiteName">唯一标识</span><%=getOrderFlag("SiteName", sCurrOrderBy)%></td>
<td grid_sortby="OperTime" width="120"><span  WCMAnt:param="siterecycle_list.head.OperTime">删除时间</span><%=getOrderFlag("OperTime", sCurrOrderBy)%></td>
<td grid_sortby="OperUser" width="60"><span WCMAnt:param="siterecycle_list.head.OperUser">删除者</span><%=getOrderFlag("OperUser", sCurrOrderBy)%></td>
<td WCMAnt:param="siterecycle_list.head.delete" width="60">删除</td>

	</tr>
	<tbody class="grid_body" id="grid_body">
<%
//5. 遍历生成表现
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			WebSite obj = (WebSite)objs.getAt(i - 1);
			if (obj == null)
				continue;
			int nRowId = obj.getId();
			boolean bIsSelected = strSelectedIds.indexOf(","+nRowId+",")!=-1;
			String sRightValue = getRightValue(obj, loginUser).toString();
			String sRowClassName = i%2==0?"grid_row_even":"grid_row_odd";
%>
	<tr id="tr_<%=nRowId%>" rowid="<%=nRowId%>" class="grid_row  <%=sRowClassName%> <%=(bIsSelected)?" grid_row_active":""%>" right="<%=sRightValue%>">
		<td><input type="checkbox" id="cb_<%=nRowId%>" class="grid_checkbox" name="RowId" value="<%=nRowId%>" <%=(bIsSelected)?"checked":""%>/><span class="grid_index" id="grid_index_<%=nRowId%>"><%=i%></span></td>
		
		<td><span class="object_restore grid_function" grid_function="restore">&nbsp;</span></td>
<td id="SiteDesc_<%=nRowId%>" style="text-align:left;padding-left:10px" title="[站点-<%=nRowId%>]" WCMAnt:paramattr="title:siterecycle_list.head.site">
		<%=CMyString.transDisplay(obj.getPropertyAsString("SiteDesc"))%></td>
<td id="SiteName_<%=nRowId%>" style="text-align:left;padding-left:10px">
		<%=CMyString.transDisplay(obj.getPropertyAsString("SiteName"))%></td>
<td id="OperTime_<%=nRowId%>">
		<%=convertDateTimeValueToString(oMethodContext, obj.getPropertyAsDateTime("OperTime"))%></td>
<td id="OperUser_<%=nRowId%>">
		<%=CMyString.transDisplay(obj.getPropertyAsString("OperUser"))%></td>
<td><span class="object_delete grid_function" grid_function="delete">&nbsp;</span></td>

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
	throw new WCMException(LocaleServer.getString("siterecycle_query.jsp.label.runtimeexception", "website_query.jsp运行期异常!"), tx);
}
%>