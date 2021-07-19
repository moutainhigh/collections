<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.DocBak" %>
<%@ page import="com.trs.components.wcm.content.persistent.DocBaks" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%
try{
%>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof DocBaks)){
		throw new WCMException(CMyString.format(LocaleServer.getString("docbak_query.jsp.servicenoobjects", "服务(com.trs.ajaxservice.DocumentBakServiceProvider.query)返回的对象集合类型不为DocBaks,而为{0},请确认."), new Object[]{result.getClass()}));
	}
	DocBaks objs = (DocBaks)result;
%>
<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
	<tr id="grid_head" class="grid_head">
		<td width="50" WCMAnt:param="list.listOrder">序号</td>
		
		<td grid_sortby="doctitle"><span WCMAnt:param="docbak_list.head.doctitle">文档标题</span><%=getOrderFlag("doctitle", sCurrOrderBy)%></td>
		<td grid_sortby="bakuser" width="85"><span WCMAnt:param="docbak_list.head.bakuser">保存用户</span><%=getOrderFlag("bakuser", sCurrOrderBy)%></td>
		<td grid_sortby="baktime" width="85"><span WCMAnt:param="docbak_list.head.baktime">保存时间</span><%=getOrderFlag("baktime", sCurrOrderBy)%></td>
		<td grid_sortby="docversion" width="60"><span WCMAnt:param="docbak_list.head.docversion">版本号</span><%=getOrderFlag("docversion", sCurrOrderBy)%></td>
		<td WCMAnt:param="docbak_list.head.recover" width="60">恢复版本</td>
		<td WCMAnt:param="docbak_list.head.delete" width="32">删除</td>

	</tr>
	<tbody class="grid_body" id="grid_body">
<%
//5. 遍历生成表现
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			DocBak obj = (DocBak)objs.getAt(i - 1);
			if (obj == null)
				continue;
			int nRowId = obj.getId();
			int nDocId = obj.getDocId();
			int nVersion = obj.getPropertyAsInt("docversion",0);
			boolean bIsSelected = strSelectedIds.indexOf(","+nRowId+",")!=-1;
			String sRightValue = getRightValueByMember(obj, loginUser).toString();
			String sRowClassName = i%2==0?"grid_row_even":"grid_row_odd";
			String sDoctitle = CMyString.filterForHTMLValue(obj.getPropertyAsString("doctitle"));
%>
	<tr id="tr_<%=nRowId%>" rowid="<%=nRowId%>" class="grid_row <%=sRowClassName%> <%=(bIsSelected)?" grid_row_active":""%>" right="<%=sRightValue%>">
		<td><span class="grid_index" id="grid_index_<%=nRowId%>"><%=i%></span></td>
		
		<td id="doctitle_<%=nRowId%>" style="text-align:left" title="<%=sDoctitle%>">
		<a href="#"><span onclick="viewBak(<%=nDocId%>,<%=nVersion%>)"><%=sDoctitle%></span></a></td>
<td id="bakuser_<%=nRowId%>">
		<%=obj.getPropertyAsString("bakuser")%></td>
<td id="baktime_<%=nRowId%>">
		<%=convertDateTimeValueToString(oMethodContext, obj.getPropertyAsDateTime("baktime"))%></td>
<td id="docversion_<%=nRowId%>">
		<%=nVersion+1%></td>
<td><span class="object_recover grid_function" onclick='recover(<%=nDocId%>,"<%=CMyString.filterForJs(obj.getPropertyAsString("doctitle"))%>",<%=nVersion%>,"true")'>&nbsp;</span></td>
<td><span class="object_delete grid_function" onclick='deleteVersion(<%=nDocId%>,<%=nVersion%>)'>&nbsp;</span></td>

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
	throw new WCMException(LocaleServer.getString("docbak_query.jsp.runtimeex", "docbak_query.jsp运行期异常!"), tx);
}
%>