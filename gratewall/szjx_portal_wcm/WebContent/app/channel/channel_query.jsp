<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channels" %>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	Channels objs = (Channels)request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
%>
<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
	<tr id="grid_head" class="grid_head">
		<td onclick="wcm.Grid.selectAll();" width="50" WCMAnt:param="list.selectall" class="selAll">全选</td>
		
		<td WCMAnt:param="channel_list.head.preview" width="40">预览</td>
<td grid_sortby="chnldesc"><span WCMAnt:param="channel_list.head.chnldesc">栏目描述</span><%=getOrderFlag("chnldesc", sCurrOrderBy)%></td>
<td grid_sortby="cruser" width="60"><span WCMAnt:param="channel_list.head.cruser">创建者</span><%=getOrderFlag("cruser", sCurrOrderBy)%></td>
<td WCMAnt:param="channel_list.head.crtime" width="120">创建时间</td>
<td grid_sortby="edit" width="40"><span WCMAnt:param="channel_list.head.edit">修改</span><%=getOrderFlag("edit", sCurrOrderBy)%></td>
<td grid_sortby="increasingpublish" width="80"><span WCMAnt:param="channel_list.head.increasingpublish">增量发布</span><%=getOrderFlag("increasingpublish", sCurrOrderBy)%></td>

	</tr>
	<tbody class="grid_body" id="grid_body">
<%
//5. 遍历生成表现
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			Channel obj = (Channel)objs.getAt(i - 1);
			if (obj == null)
				continue;
			int nRowId = obj.getId();
			boolean bIsSelected = strSelectedIds.indexOf(","+nRowId+",")!=-1;
			String sRightValue = getRightValue(obj, loginUser).toString();
%>
	<tr id="tr_<%=nRowId%>" rowid="<%=nRowId%>" class="grid_row grid_selectable_row <%=(bIsSelected)?" grid_row_curr":""%>" right="<%=sRightValue%>">
		<td><input type="checkbox" id="cb_<%=nRowId%>" class="grid_checkbox" name="RowId" value="<%=nRowId%>" <%=(bIsSelected)?"checked":""%>/><span class="grid_index" id="grid_index_<%=nRowId%>"><%=i%></span></td>
		
		<td><span class="object_preview" style="width:30px" grid_function="preview">&nbsp;</span></td>
<td id="chnldesc_<%=nRowId%>" class="chnldesc">
		<a href="#" onclick="return false;" grid_function="trace"><%=CMyString.transDisplay(obj.getPropertyAsString("chnldesc"))%></a>
</td>
<td id="cruser_<%=nRowId%>">
		<%=CMyString.transDisplay(obj.getPropertyAsString("cruser"))%></td>
<td id="crtime_<%=nRowId%>">
		<%=CMyString.transDisplay(obj.getPropertyAsString("crtime"))%></td>
<td><span class="oper object_edit" grid_function="edit">&nbsp;</span></td>
<td><span class="oper object_increasingpublish" grid_function="increasingpublish">&nbsp;</span></td>

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
    private RightValue getRightValue(Channel channel, User loginUser) throws WCMException {
		try {
			if (loginUser.isAdministrator()) {
				return RightValue.getAdministratorRightValue();
			}
			return getRightValueByMember(channel, loginUser);
		} catch (Exception e) {
		    throw new WCMException(CMyString.format((LocaleServer.getString("flowemployee.view.construct", "构造[{0}]权限信息失败!")),new Object[]{channel}), e);
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