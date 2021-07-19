<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.publish.region.RegionInfo" %>
<%@ page import="com.trs.components.wcm.publish.region.RegionInfos" %>
<%@ page import="com.trs.ajaxservice.WebSiteServiceProvider" %>
<%@ page import="com.trs.infra.persistent.BaseObj" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.persistent.RightValue" %>
<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<%@ page import="com.trs.cms.content.WCMSystemObject" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.webframework.context.MethodContext" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	RegionInfos objs = (RegionInfos)request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
%>
<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
	<tr id="grid_head" class="grid_head">
		<td onclick="wcm.Grid.selectAll();" width="50" WCMAnt:param="region_query.jsp.selectall" class="selAll">全选</td>
		<td grid_sortby="rname"><span WCMAnt:param="region_query.jsp.preview_name">导读名称</span><%=getOrderFlag("rname", sCurrOrderBy)%></td>
		<td width="150"><span WCMAnt:param="region_query.jsp.belong">所属位置</span></td>
		<td grid_sortby="CrTime" width="120"><span WCMAnt:param="region_query.jsp.crtime">创建时间</span><%=getOrderFlag("CrTime", sCurrOrderBy)%></td>
		<td grid_sortby="CrUser" width="90"><span WCMAnt:param="region_query.jsp.cruser">创建者</span><%=getOrderFlag("CrUser", sCurrOrderBy)%></td>
		<td width="60" WCMAnt:param="region_query.jsp.use_state">使用情况</td>
	</tr>
	<tbody class="grid_body" id="grid_body">
<%
	
	int nChannelId = oMethodContext.getValue("ChannelId", 0);
	int nSiteId = oMethodContext.getValue("WebSiteId", 0);
	CMSObj hostObj = null;
	if(nChannelId > 0)
		hostObj = Channel.findById(nChannelId);
	else 
		hostObj = WebSite.findById(nSiteId);
// 遍历生成表现
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			RegionInfo obj = (RegionInfo)objs.getAt(i - 1);
			if (obj == null)
				continue;
			int nRowId = obj.getId();
			int nOwnerChannelId = obj.getChannelId();
			int nOwnerWebsiteId = obj.getWebSiteId();
			BaseChannel oBaseChannel = Channel.findById(nOwnerChannelId);
			if(oBaseChannel == null){
				oBaseChannel = WebSite.findById(nOwnerWebsiteId);
			}
			boolean bIsSelected = strSelectedIds.indexOf(","+nRowId+",")!=-1; 
			String sRightValue = getRightValue(hostObj, loginUser).toString();
			String sRowClassName = i%2==0?"grid_row_even":"grid_row_odd";
			// 导读的权限改为新的权限位控制
			boolean bCanEdit = AuthServer.hasRight(loginUser,oBaseChannel, WCMRightTypes.REGION_MGR);;
%>
	<tr id="tr_<%=nRowId%>" rowid="<%=nRowId%>" class="grid_row <%=sRowClassName%> <%=(bCanEdit)?"grid_selectable_row":"grid_selectdisable_row"%> <%=(bIsSelected)?" grid_row_active":""%>" right="<%=sRightValue%>" channelid="<%=nOwnerChannelId%>" websiteId="<%=nOwnerWebsiteId%>">
		<td><input type="checkbox" id="cb_<%=nRowId%>" class="grid_checkbox" name="RowId" value="<%=nRowId%>" <%=(bIsSelected)?"checked":""%> <%=(bCanEdit)?"":"disabled"%>/><span class="grid_index" id="grid_index_<%=nRowId%>"><%=i%></span></td>
		
		<td id="rerionname_<%=nRowId%>" class="rowtd"> <a href="#" onclick="return false;" grid_function="<%=bCanEdit ? "edit" : ""%>" title="<%=LocaleServer.getString("region.query.jsp.regiontitle","导读-") 
			+ nRowId%>">
		<%=CMyString.transDisplay(obj.getPropertyAsString("Rname"))%></a></td>
		<td id="position_<%=nRowId%>">
		<%=CMyString.transDisplay(oBaseChannel.toString())%></td>
		<td id="crtime_<%=nRowId%>">
		<%=convertDateTimeValueToString(oMethodContext, obj.getPropertyAsDateTime("crtime"))%></td>
		<td id="cruser_<%=nRowId%>">
		<%=CMyString.transDisplay(obj.getPropertyAsString("cruser"))%></td>
		<td><span class="<%=bCanEdit ? "object_setregion" : "object_cannot_setregion"%> grid_function" style="width:30px" grid_function="<%=bCanEdit ? "showEmploy" : ""%>">&nbsp;</span></td>
	</tr>
<%
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
%>
	</tbody>
	<tbody id="grid_NoObjectFound" style="display:none;">
		<tr><td colspan="6" class="no_object_found" WCMAnt:param="region_query.jsp.nofindobj">不好意思, 没有找到符合条件的对象!</td></tr>
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