<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.wcm.photo.Watermark" %>
<%@ page import="com.trs.wcm.photo.Watermarks" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%
try{
%>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof Watermarks)){
		throw new WCMException(CMyString.format(LocaleServer.getString("watermark_query.jsp.service", "服务(com.trs.ajaxservice.WatermarkServiceProvider.query)返回的对象集合类型不为Watermarks，而为[{0}]，请确认。"), new Object[]{result.getClass()}));
	}
	Watermarks objs = (Watermarks)result;
%>
<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
	<tr id="grid_head" class="grid_head">
		<td onclick="wcm.Grid.selectAll();" width="50" WCMAnt:param="list.selectall" class="selAll">全选</td>
		
		<td WCMAnt:param="watermark_list.head.wmname" width="60%">文件名</td>
<td WCMAnt:param="watermark_list.head.wmpicture" width="100">LOGO图片</td>
<td WCMAnt:param="watermark_list.head.crtime" width="40%">创建时间</td>

	</tr>
	<tbody class="grid_body" id="grid_body">
<%
//5. 遍历生成表现
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			Watermark obj = (Watermark)objs.getAt(i - 1);
			if (obj == null)
				continue;
			int nRowId = obj.getId();
			boolean bIsSelected = strSelectedIds.indexOf(","+nRowId+",")!=-1;
			WebSite currSite = WebSite.findById(obj.getLibId());
			String sRightValue = getRightValue(currSite, loginUser).toString();
			String sRowClassName = i%2==0?"grid_row_even":"grid_row_odd";
%>
	<tr id="tr_<%=nRowId%>" rowid="<%=nRowId%>" class="grid_row <%=sRowClassName%> <%=(bIsSelected)?" grid_row_active":""%>" right="<%=sRightValue%>">
		<td><input type="checkbox" id="cb_<%=nRowId%>" class="grid_checkbox" name="RowId" value="<%=nRowId%>" <%=(bIsSelected)?"checked":""%>/><span class="grid_index" id="grid_index_<%=nRowId%>"><%=i%></span></td>
		
		<td id="wmname_<%=nRowId%>">
		<%=obj.getPropertyAsString("wmname")%></td>
<td id="wmpicture_<%=nRowId%>"><a href='../../file/read_image.jsp?FileName=<%=obj.getPropertyAsString("wmpicture")%>' target="_blank" title="点击查看原图" WCMAnt:paramattr="title:watermark_query.jsp.titleAttr"><img border=0  width="50" height="50" src='../../file/read_image.jsp?FileName=<%=obj.getPropertyAsString("wmpicture")%>'></a></td>
<td id="crtime_<%=nRowId%>">
		<%=obj.getPropertyAsString("crtime")%></td>

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
	throw new WCMException(LocaleServer.getString("watermark_query.jsp.label.runtimeexception", "watermark_query.jsp运行期异常!"), tx);
}
%>