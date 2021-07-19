<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channels" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%
try{
%>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof Channels)){
		throw new WCMException(CMyString.format(LocaleServer.getString("chnlrecycle_query.jsp.servicenoobject","服务(com.trs.ajaxservice.ChannelServiceProvider.query)返回的对象集合类型不为Channels，而为{0}，请确认。"),new Object[]{result.getClass()}));
	}
	Channels objs = (Channels)result;
%>
<%!
	private String getParentName(Channel _chnl) throws WCMException{
		int parentChannelId = _chnl.getParentId();
		if(parentChannelId == 0){
				WebSite parentChannel = _chnl.getSite();
				return (parentChannel != null)?LocaleServer.getString("channel.label.none","无"):"";
			}else{
				Channel parentChannel = Channel.findById(parentChannelId);
				return (parentChannel != null)?parentChannel.getName():"";
			}
	}
	private String getRightValue(Channel _channel,User loginUser) throws WCMException{
        String rightValue = "";
        if (loginUser.isAdministrator()
                || loginUser.equals(_channel.getCrUser())) {
            rightValue = RightValue.getAdministratorValues();
        } else {
            rightValue = getRightValueByMember(_channel, loginUser)
                    .toString();
        }
		return rightValue;
	}
%>
<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
	<tr id="grid_head" class="grid_head">
		<td onclick="wcm.Grid.selectAll();" width="50" WCMAnt:param="list.selectall" class="selAll">全选</td>
		
		<td WCMAnt:param="chnlrecycle_list.head.restore" width="40">还原</td>
<td grid_sortby="ChnlDesc"><span WCMAnt:param="chnlrecycle_list.head.ChnlDesc">显示名称</span><%=getOrderFlag("ChnlDesc", sCurrOrderBy)%></td>
<td grid_sortby="ChnlName"><span WCMAnt:param="chnlrecycle_list.head.ChnlName">唯一标识</span><%=getOrderFlag("ChnlName", sCurrOrderBy)%></td>
<td WCMAnt:param="chnlrecycle_list.head.ParentName" width="100">原位置</td>
<td grid_sortby="OperTime" width="120"><span WCMAnt:param="chnlrecycle_list.head.OperTime">删除时间</span><%=getOrderFlag("OperTime", sCurrOrderBy)%></td>
<td grid_sortby="OperUser" width="60"><span  WCMAnt:param="chnlrecycle_list.head.OperUser">删除者</span><%=getOrderFlag("OperUser", sCurrOrderBy)%></td>
<td WCMAnt:param="chnlrecycle_list.head.delete" width="60">删除</td>

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
			
			//Channel parentChannel = obj.getParent();
			String sParentName = getParentName(obj);
			boolean bIsSelected = strSelectedIds.indexOf(","+nRowId+",")!=-1;
			String sRightValue = getRightValue(obj, loginUser);
			String sRowClassName = i%2==0?"grid_row_even":"grid_row_odd";
			String sTypeDesc = obj.getTypeDesc();
%>
	<tr id="tr_<%=nRowId%>" rowid="<%=nRowId%>" class="grid_row  <%=sRowClassName%> <%=(bIsSelected)?" grid_row_active":""%>" right="<%=sRightValue%>" title="[栏目-<%=nRowId%>-<%=sTypeDesc%>]" WCMAnt:paramattr="title:chnlrecycle_list.head.chnl">
		<td><input type="checkbox" id="cb_<%=nRowId%>" class="grid_checkbox" name="RowId" value="<%=nRowId%>" <%=(bIsSelected)?"checked":""%>/><span class="grid_index" id="grid_index_<%=nRowId%>"><%=i%></span></td>
		
		<td><span class="object_restore grid_function" grid_function="restore">&nbsp;</span></td>
<td id="ChnlDesc_<%=nRowId%>" style="text-align:left;padding-left:10px">
		<%=CMyString.transDisplay(obj.getPropertyAsString("ChnlDesc"))%></td>
<td id="ChnlName_<%=nRowId%>" style="text-align:left;padding-left:10px">
		<%=CMyString.transDisplay(obj.getPropertyAsString("ChnlName"))%></td>
<td id="ParentName_<%=nRowId%>">
		<%=sParentName%></td>
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
	throw new WCMException(LocaleServer.getString("chnlrecycle_query.jsp.chnlrecqueryruntimeex","chnlrecycle_query.jsp运行期异常!"), tx);
}
%>