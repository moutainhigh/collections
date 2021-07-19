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
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	RegionInfos objs = (RegionInfos)request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
%>
<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
	<tr id="grid_head" class="grid_head">
		<td width="50" WCMAnt:param="region_select_query.jsp.select">选择</td>
		<td grid_sortby="rname"><span WCMAnt:param="region_select_query.jsp.preview_scanname">导读名称</span><%=getOrderFlag("rname", sCurrOrderBy)%></td>
		<td width="150"><span WCMAnt:param="region_select_query.jsp.belong_position">所属位置</span></td>
		<td grid_sortby="CrTime" width="120"><span WCMAnt:param="region_select_query.jsp.crtime">创建时间</span><%=getOrderFlag("CrTime", sCurrOrderBy)%></td>
		<td grid_sortby="CrUser" width="90"><span WCMAnt:param="region_select_query.jsp.cruser">创建者</span><%=getOrderFlag("CrUser", sCurrOrderBy)%></td>
	</tr>
	<tbody class="grid_body" id="grid_body">
<%
	
	int nChannelId = oMethodContext.getValue("ChannelId", 0);
	int nSiteId = oMethodContext.getValue("WebSiteId", 0);
	String sCurrRegionName = oMethodContext.getValue("regionName");
	int currRegionId = 0;
	if(!CMyString.isEmpty(sCurrRegionName)){
		RegionInfo currRegionInfo = RegionInfo.findByName(sCurrRegionName);
		if(currRegionInfo != null)
			currRegionId = currRegionInfo.getId();
	}
	boolean bHasChecked = false;
	BaseChannel hostObj = null;
	if(nChannelId > 0)
		hostObj = Channel.findById(nChannelId);
	else 
		hostObj = WebSite.findById(nSiteId);
	String sRightValue = "";
	if(hostObj != null)
		sRightValue = getRightValue(hostObj, loginUser).toString();
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
			//boolean bIsSelected = strSelectedIds.indexOf(","+nRowId+",")!=-1; 
			String sRowClassName = i%2==0?"grid_row_even":"grid_row_odd";
			boolean bCanEdit = false;
			if(oBaseChannel.isSite()){
				bCanEdit = AuthServer.hasRight(loginUser,oBaseChannel, 1);
			}else{ 
				bCanEdit = AuthServer.hasRight(loginUser,oBaseChannel, 13);
			}
			bCanEdit = true;
			boolean bChecked = currRegionId==nRowId;
			bHasChecked = bChecked;
			String sRegionName = CMyString.transDisplay(obj.getPropertyAsString("Rname"));
%>
	<tr id="tr_<%=nRowId%>" rowid="<%=nRowId%>" class="grid_row <%=sRowClassName%> <%=(bCanEdit)?"grid_selectable_row":"grid_selectdisable_row"%> <%=(bHasChecked)?" grid_row_active":""%>" right="<%=sRightValue%>" channelid="<%=nOwnerChannelId%>" websiteId="<%=nOwnerWebsiteId%>" regionname="<%=sRegionName%>">
		<td><input type="radio" id="cb_<%=nRowId%>" class="grid_checkbox" name="RowId" _regionname="<%=sRegionName%>" value="<%=nRowId%>" <%=(bHasChecked)?"checked":""%> <%=(bCanEdit)?"":"disabled"%>/><span class="grid_index" id="grid_index_<%=nRowId%>"><%=i%></span></td>
		
		<td id="rerionname_<%=nRowId%>" class="rowtd"> <a href="#" onclick="return false;" grid_function="<%=bCanEdit ? "edit" : ""%>" title="<%=LocaleServer.getString("region.query.jsp.regiontitle","导读-") 
			+ nRowId%>">
		<%=sRegionName%></a></td>
		<td id="position_<%=nRowId%>">
		<%=CMyString.transDisplay(oBaseChannel.toString())%></td>
		<td id="crtime_<%=nRowId%>">
		<%=convertDateTimeValueToString(oMethodContext, obj.getPropertyAsDateTime("crtime"))%></td>
		<td id="cruser_<%=nRowId%>">
		<%=CMyString.transDisplay(obj.getPropertyAsString("cruser"))%></td>
	</tr>
<%
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
%>
	</tbody>
	<tbody id="grid_NoObjectFound" style="display:none;">
		<tr><td colspan="6" class="no_object_found" WCMAnt:param="region_select_query.jsp.sorry">不好意思, 没有找到符合条件的对象!</td></tr>
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