<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.ChannelContentLink" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChannelContentLinks" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel"%>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite"%>
<%@ page import="com.trs.infra.common.WCMRightTypes"%>

<%
try{
%>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof ChannelContentLinks)){
		throw new WCMException(CMyString.format(LocaleServer.getString("channelcontentlink_query.jsp.servicereturncertain","服务(com.trs.ajaxservice.ContentLinkServiceProvider.query)返回的对象集合类型不为ChannelContentLinks，而为{0}，请确认。"),new Object[]{result.getClass()}));
	}
	ChannelContentLinks objs = (ChannelContentLinks)result;
%>
<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
	<tr id="grid_head" class="grid_head">
		<td onclick="wcm.Grid.selectAll();" width="50" WCMAnt:param="list.selectall" class="selAll">全选</td>
		<td WCMAnt:param="channelcontentlink_list.head.edit" width="40">修改</td>
		<td grid_sortby="linkName" width="115"><span WCMAnt:param="channelcontentlink_list.head.linkName">热词名称</span><%=getOrderFlag("linkName", sCurrOrderBy)%></td>
		<td grid_sortby="linkTitle" width="245"><span WCMAnt:param="channelcontentlink_list.head.linkTitle">热词描述</span><%=getOrderFlag("linkTitle", sCurrOrderBy)%></td>
		<td  grid_sortby="linkUrl"><span WCMAnt:param="channelcontentlink_list.head.linkUrl">热词链接</span><%=getOrderFlag("linkUrl", sCurrOrderBy)%></td>
		<td WCMAnt:param="channelcontentlink_list.head.delete" width="40">删除</td>
	</tr>
	<tbody class="grid_body" id="grid_body">
<%
//5. 遍历生成表现
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		ChannelContentLink obj = (ChannelContentLink)objs.getAt(i - 1);
		if (obj == null)
			continue;
		int nRowId = obj.getId();
		/**
		* edit by ffx @2012-06-08
		* 如果是站点下的热词，则所属栏目为null。所以这里要兼容站点下的热词
		* 处理问题 WCM-346 普通用户创建的站点热词，自己在热词列表中看不到
		*/
		BaseChannel srcChannel = obj.getChannel();
		if(srcChannel == null){
			srcChannel = WebSite.findById(obj.getSiteId());
		}
		boolean bIsSelected = strSelectedIds.indexOf(","+nRowId+",")!=-1;
		boolean bCanEdit = hasRight(loginUser,srcChannel,WCMRightTypes.CONTENT_LINK_EDIT);
		boolean bCanDel = hasRight(loginUser,srcChannel,WCMRightTypes.CONTENT_LINK_DELETE);
		String sRightValue = getRightValueByMember(srcChannel, loginUser).toString();
		String sRowClassName = i%2==0?"grid_row_even":"grid_row_odd";
%>
	<tr id="tr_<%=nRowId%>" rowid="<%=nRowId%>" class="grid_row <%=sRowClassName%> <%=(bIsSelected)?" grid_row_active":""%>" right="<%=sRightValue%>" linkorder="<%=obj.getOrder()%>">
		<td><input type="checkbox" id="cb_<%=nRowId%>" class="grid_checkbox" name="RowId" value="<%=nRowId%>" <%=(bIsSelected)?"checked":""%>/><span class="grid_index" id="grid_index_<%=nRowId%>"><%=i%></span></td>
		<td><span class="<%=(bCanEdit)?"object_edit":"objectcannot_edit"%> grid_function" grid_function="edit">&nbsp;</span></td>
		<td id="linkName_<%=nRowId%>">
		<%=CMyString.transDisplay(obj.getPropertyAsString("linkName"))%></td>
		<td id="linkTitle_<%=nRowId%>">
				<%=CMyString.transDisplay(obj.getPropertyAsString("linkTitle"))%></td>
		<td id="linkUrl_<%=nRowId%>">
				<%=CMyString.transDisplay(obj.getPropertyAsString("linkUrl"))%></td>
		<td><span class="<%=(bCanDel)?"object_delete":"objectcannot_delete"%> grid_function" grid_function="delete">&nbsp;</span></td>
	</tr>
<%
	}
%>
	</tbody>
	<tbody id="grid_NoObjectFound" style="display:none;">
		<tr><td colspan="6" class="no_object_found" WCMAnt:param="list.NoObjectFound">不好意思, 没有找到符合条件的对象!</td></tr>
	</tbody>
</table>
<script>
	try{
		Ext.apply(PageContext, {
			CanSort : true
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
	}catch(err){
		//Just skip it.
	}
</script>
<%
}catch(Throwable tx){
	tx.printStackTrace();
	throw new WCMException(LocaleServer.getString("channelcontentlink_query.jsp.chnlctlnkruntimeex","channelcontentlink_query.jsp运行期异常!"), tx);
}
%>