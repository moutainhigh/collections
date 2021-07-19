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
		<td onclick="wcm.Grid.selectAll();" width="60" WCMAnt:param="flow.query.using.channels.selectall" class="selAll">全选</td>
		<td WCMAnt:param="flow.query.using.channels.chnldesc" grid_sortby="chnldesc" width="50%">显示名称</td>
		<td WCMAnt:param="flow.query.using.channels.biaoshi" grid_sortby="chnlname" width="50%">唯一标识</td>
		<td WCMAnt:param="flow.query.using.channels.cancelfenpei"  width="90" border-right="0">取消分配</td>
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
			String sRightValue = getRightValueByMember(obj, loginUser).toString();
			String sRowClassName = i%2==0?"grid_row_even":"grid_row_odd";
%>
	<tr id="tr_<%=nRowId%>" rowid="<%=nRowId%>" class="grid_row grid_selectable_row <%=sRowClassName%> <%=(bIsSelected)?" grid_row_active":""%>" right="<%=sRightValue%>">
		<td><input type="checkbox" id="cb_<%=nRowId%>" class="grid_checkbox" name="RowId" value="<%=nRowId%>" <%=(bIsSelected)?"checked":""%>/><span class="grid_index" id="grid_index_<%=nRowId%>"><%=i%></span></td>
		<td><span class="chnldesc"><%=CMyString.transDisplay(obj.getPropertyAsString("chnldesc"))%></span></td>
		<td><span class="chnldesc"><%=CMyString.transDisplay(obj.getPropertyAsString("chnlname"))%></span></td>
		<td><span class="object_delete grid_function" grid_function="disableFlow">&nbsp;</span></td>
 
	</tr>
<%
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
%>
	</tbody>
	<tbody id="grid_NoObjectFound" style="display:none;">
		<td colspan="4" class="no_object_found" WCMAnt:param="flow.query.using.channels.none">不好意思, 没有找到符合条件的对象!</td>
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