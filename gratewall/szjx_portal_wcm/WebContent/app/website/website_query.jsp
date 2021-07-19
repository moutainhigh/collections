<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSites" %>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	WebSites objs = (WebSites)request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
%>
<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
	<tr id="grid_head" class="grid_head">
		<td onclick="wcm.Grid.selectAll();" width="50" WCMAnt:param="list.selectall" class="selAll">全选</td>
		
		<td WCMAnt:param="website_list.head.preview" width="40">预览</td>
<td grid_sortby="sitedesc"><span WCMAnt:param="website_list.head.sitedesc">站点描述</span><%=getOrderFlag("sitedesc", sCurrOrderBy)%></td>
<td grid_sortby="cruser" width="60"><span WCMAnt:param="website_list.head.cruser">创建者</span><%=getOrderFlag("cruser", sCurrOrderBy)%></td>
<td grid_sortby="crtime" width="120"><span WCMAnt:param="website_list.head.crtime">创建时间</span><%=getOrderFlag("crtime", sCurrOrderBy)%></td>
		<td WCMAnt:param="website_list.head.edit" width="40">修改</td>
		<td WCMAnt:param="website_list.head.increasepublish" width="80">增量发布</td>

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
			String sCrTime = convertDateTimeValueToString(oMethodContext, obj.getPropertyAsDateTime("CrTime"));
%>
	<tr id="tr_<%=nRowId%>" rowid="<%=nRowId%>" class="grid_row grid_selectable_row <%=(bIsSelected)?" grid_row_curr":""%>" right="<%=sRightValue%>">
		<td><input type="checkbox" id="cb_<%=nRowId%>" class="grid_checkbox" name="RowId" value="<%=nRowId%>" <%=(bIsSelected)?"checked":""%>/><span class="grid_index" id="grid_index_<%=nRowId%>"><%=i%></span></td>
		
		<td><span class="object_preview" style="width:30px" grid_function="preview">&nbsp;</span></td>
<td id="sitedesc_<%=nRowId%>" class="sitedesc">
		<a href="#" onclick="return false;" grid_function="trace"><%=CMyString.transDisplay(obj.getPropertyAsString("sitedesc"))%></a>
</td>
		<td id="cruser_<%=nRowId%>">
		<%=CMyString.transDisplay(obj.getPropertyAsString("cruser"))%></td>
		<td id="crtime_<%=nRowId%>"><%=sCrTime%></td>
		<td><span class="oper object_edit" grid_function="edit">&nbsp;</span></td>
		<td><span class="oper object_increasepublish" grid_function="increasepublish">&nbsp;</span></td>
	</tr>
<%
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
%>
	</tbody>
	<tbody id="grid_NoObjectFound" style="display:none;">
		<td colspan="8" class="no_object_found" WCMAnt:param="list.NoObjectFound">不好意思, 没有找到符合条件的对象!</td>
	</tbody>
</table>
<%!
    private RightValue getRightValue(WebSite site, User loginUser) throws WCMException {
		try {
			if (loginUser.isAdministrator()) {
				return RightValue.getAdministratorRightValue();
			}
			return getRightValueByMember(site, loginUser);
		} catch (Exception e) {
			throw new WCMException(CMyString.format(LocaleServer.getString("website_query.jsp.service", "构造[{0}]权限信息失败!"), new Object[]{site}),e);
		}
	}
%>
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