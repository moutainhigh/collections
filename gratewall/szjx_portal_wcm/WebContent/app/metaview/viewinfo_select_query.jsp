<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.components.metadata.definition.MetaViews" %>
<%
try{
%>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof MetaViews)){
		throw new WCMException(CMyString.format(LocaleServer.getString("viewinfo_select_query.type","服务(com.trs.components.metadata.service.MetaDataDefServiceProvider.query)返回的对象集合类型不为MetaViews，而为{0}，请确认。"),new Object[]{result.getClass()}));
	}
	int nExcludeViewId = oMethodContext.getValue("excludedViewId", 0);
	MetaViews objs = (MetaViews)result;
	
%>

<table id="table_cnt" cellspacing=0 border="0" cellpadding=0 style="width:100%;border-top:1px dotted gray">
	<tbody>
<%
//5. 遍历生成表现
	String sType = CMyString.showNull(oMethodContext.getValue("selecttype"), "radio");
	int nNum = 0;
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			MetaView obj = (MetaView)objs.getAt(i - 1);
			if (obj == null)
				continue;
			int nRowId = obj.getId();
			boolean bIsSelected = strSelectedIds.indexOf(","+nRowId+",")!=-1;
			String sRightValue = getRightValue(obj, loginUser).toString();
			String sCruser = obj.getPropertyAsString("cruser");
			String sCrtime = obj.getPropertyAsDateTime("crtime").toString("MM-dd hh:mm");
			String sViewDesc = CMyString.transDisplay(obj.getPropertyAsString("VIEWDESC"));
			String sViewInfoId = obj.getIdFieldName();
			boolean bCanSelect = nExcludeViewId == nRowId ? false : true;
%>
<%
			if(nNum%2==0){
				out.println("<tr>");
			}	
			nNum++;
%>
	<td width="50%" title="<%=LocaleServer.getString("metaview_select.label.viewName", "视图名称")%>: <%=sViewDesc%>&#13;<%=LocaleServer.getString("metaview_select.label.cruser", "创建者")%>: <%=obj.getPropertyAsString("crUser")%>&#13;<%=LocaleServer.getString("metaview_select.label.crtime", "创建时间")%>：<%=convertDateTimeValueToString(oMethodContext,obj.getPropertyAsDateTime("crTime"))%>">
		<span><%=(i)%>.</span><input type="<%=sType%>" class="chk_viewid" name="ViewId" value="<%=nRowId%>" id="chk_<%=nRowId%>" _name="<%=sViewDesc%>" <%=bCanSelect ? "" : "disabled"%>><span title="<%=LocaleServer.getString("metaview_select.label.owner", "属于")%>: <%=sViewInfoId%>" class="flag_own_<%=oMethodContext.getValue("objectType", 1)%>">&nbsp;</span>
		<span class="sp_name" _id="<%=nRowId%>" for="chk_<%=nRowId%>"><%=sViewDesc%></span>
	</td>
<%
			if(nNum%2==0){
				out.println("</tr>");
			}	
%>
<%
		}catch(Exception ex){
			ex.printStackTrace();
		}//end try
	}//end for
	if(nNum%2==1){
		out.println("<td>&nbsp;</td></tr>");
	}
	if(nNum==0){
%>
	<tr><td colspan="2" class="no_object_found" WCMAnt:param="list.NoObjectFound">不好意思, 没有找到符合条件的对象!</td></tr>
<%
	}
%>

	</tbody>
</table>
<script>
	try{
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
	throw new WCMException(LocaleServer.getString("select_query.runExce","view_select_query.jsp运行期异常!"), tx);
}
%>