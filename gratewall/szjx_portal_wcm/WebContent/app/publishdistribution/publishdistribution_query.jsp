<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.common.publish.persistent.distribute.PublishDistribution" %>
<%@ page import="com.trs.components.common.publish.persistent.distribute.PublishDistributions" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite"%>
<%
try{
%>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof PublishDistributions)){
		throw new WCMException(CMyString.format(LocaleServer.getString("publishdistribution_query.jsp.service", "服务(com.trs.ajaxservice.DistributeServiceProvider.query)返回的对象集合类型不为PublishDistributions，而为{0}，请确认。"), new Object[]{result.getClass()}));
	}
	PublishDistributions objs = (PublishDistributions)result;
%>
<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
	<tr id="grid_head" class="grid_head">
		<td onclick="wcm.Grid.selectAll();" width="50" WCMAnt:param="list.selectall" class="selAll">全选</td>
		
		<td WCMAnt:param="publishdistribution_list.head.edit" width="40">修改</td>
<td grid_sortby="enabled" width="50px"><span WCMAnt:param="publishdistribution_list.head.enabled">状态</span><%=getOrderFlag("enabled", sCurrOrderBy)%></td>
<td grid_sortby="targettype" width="60px"><span WCMAnt:param="publishdistribution_list.head.targettype">分发类型</span><%=getOrderFlag("targettype", sCurrOrderBy)%></td>
<td grid_sortby="datapath"><span WCMAnt:param="publishdistribution_list.head.datapath">存放目录</span><%=getOrderFlag("datapath", sCurrOrderBy)%></td>
<td grid_sortby="targetserver" width="80px"><span WCMAnt:param="publishdistribution_list.head.targetserver">服务器地址</span><%=getOrderFlag("targetserver", sCurrOrderBy)%></td>
<td grid_sortby="cruser" width="80px"><span WCMAnt:param="publishdistribution_list.head.cruser">用户名</span><%=getOrderFlag("cruser", sCurrOrderBy)%></td>
<td WCMAnt:param="publishdistribution_list.head.delete" width="40">删除</td>

	</tr>
	<tbody class="grid_body" id="grid_body">
<%
//5. 遍历生成表现
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			PublishDistribution obj = (PublishDistribution)objs.getAt(i - 1);
			if (obj == null)
				continue;
			int nRowId = obj.getId();
			WebSite srcWebSite = WebSite.findById(obj.getFolderId());
			boolean bIsSelected = strSelectedIds.indexOf(","+nRowId+",")!=-1;
			boolean bCanEdit = hasRight(loginUser,srcWebSite,1);
			boolean bCanDel = hasRight(loginUser,srcWebSite,1);
			String sRightValue = getRightValueByMember(srcWebSite, loginUser).toString();
			String sRowClassName = i%2==0?"grid_row_even":"grid_row_odd";
%>
	<tr id="tr_<%=nRowId%>" rowid="<%=nRowId%>" class="grid_row <%=sRowClassName%> <%=(bIsSelected)?" grid_row_active":""%>" right="<%=sRightValue%>">
		<td><input type="checkbox" id="cb_<%=nRowId%>" class="grid_checkbox" name="RowId" value="<%=nRowId%>" <%=(bIsSelected)?"checked":""%>/><span class="grid_index" id="grid_index_<%=nRowId%>"><%=i%></span></td>
		
		<td><span class="<%=(bCanEdit)?"object_edit":"objectcannot_edit"%> grid_function" grid_function="edit">&nbsp;</span></td>
<td id="enabled_<%=nRowId%>">
		<%=typeConvert(Integer.parseInt(obj.getPropertyAsString("enabled")))%></td>
<td id="targettype_<%=nRowId%>">
		<%=CMyString.transDisplay(obj.getPropertyAsString("targettype"))%></td>
<td id="datapath_<%=nRowId%>" style="text-align:left">
		<%=CMyString.transDisplay(obj.getPropertyAsString("datapath"))%></td>
<td id="targetserver_<%=nRowId%>">
		<%=CMyString.transDisplay(obj.getPropertyAsString("targetserver"))%></td>
<td id="cruser_<%=nRowId%>">
		<%=CMyString.transDisplay(obj.getPropertyAsString("loginuser"))%></td>
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
		<tr><td colspan="8" class="no_object_found" WCMAnt:param="list.NoObjectFound">不好意思, 没有找到符合条件的对象!</td></tr>
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
	throw new WCMException(LocaleServer.getString("publishdistribution_query.jsp.label.runtimeexception", "publishdistribution_query.jsp运行期异常!"), tx);
}
%>
<%!
	private String typeConvert(int _nType) throws WCMException{
		switch(_nType){
			case 1 :
				return LocaleServer.getString("publishdistribution_query.label.enable", "已启用");
			default:
				return LocaleServer.getString("publishdistribution_query.label.unable", "未启用");
		}
	}
%>