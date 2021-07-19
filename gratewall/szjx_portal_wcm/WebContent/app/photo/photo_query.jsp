<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.ViewDocument" %>
<%@ page import="com.trs.components.wcm.content.ViewDocuments" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document"%>
<%
try{
%>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof ViewDocuments)){
		throw new WCMException(CMyString.format(LocaleServer.getString("photo_query.jsp.objtype_isnot_ViewDocument","服务(com.trs.ajaxservice.PhotoServiceProvider.findbyid)返回的对象类型不为ViewDocument，而为{0}，请确认。"),new Object[]{result.getClass()}));
	}
	ViewDocuments objs = (ViewDocuments)result;
%>
<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
	<tr id="grid_head" class="grid_head">
		<td onclick="wcm.Grid.selectAll();" width="50" WCMAnt:param="list.selectall" class="selAll">全选</td>
		
		<td WCMAnt:param="photo_list.head.filename" width="60%">文件名</td>
<td WCMAnt:param="photo_list.head.logo" width="100">图片</td>
<td WCMAnt:param="photo_list.head.crtime" width="40%">所属分类</td>

	</tr>
	<tbody class="grid_body" id="grid_body">
<%
//5. 遍历生成表现
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			ViewDocument obj = (ViewDocument)objs.getAt(i - 1);
			if (obj == null)
				continue;
			Document currDocument = Document.findById(obj.getDocId());
			int nDocId = currDocument.getId();
			int nRowId = obj.getChnlDocProperty("recid",0);
			boolean bIsSelected = strSelectedIds.indexOf(","+nRowId+",")!=-1;
			String sRightValue = obj.getRightValue(loginUser).toString();
			String sRowClassName = i%2==0?"grid_row_even":"grid_row_odd";
%>
	<tr id="tr_<%=nRowId%>" rowid="<%=nRowId%>" docId="<%=nDocId%>" class="grid_row <%=sRowClassName%> <%=(bIsSelected)?" grid_row_active":""%>" right="<%=sRightValue%>">
		<td><input type="checkbox" id="cb_<%=nRowId%>" class="grid_checkbox" name="RowId" value="<%=nRowId%>" <%=(bIsSelected)?"checked":""%>/><span class="grid_index" id="grid_index_<%=nRowId%>"><%=i%></span></td>
		
		<td id="filename_<%=nRowId%>" style="text-align:left">
		<%=currDocument.getTitle()%></td>
		<td id="logo_<%=nRowId%>" align="center">
				<a href='../../file/read_image.jsp?FileName=<%=currDocument.getAttribute().substring(8)%>' target="_blank" title="点击查看原图" WCMAnt:paramattr="title:photo_query.jsp.titleAttr"><img border=0  width="50" height="50" src='../../file/read_image.jsp?FileName=<%=currDocument.getAttribute().substring(8)%>'></a></td>
		<td id="crtime_<%=nRowId%>">
				<%=currDocument.getChannelName()%></td>

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
	throw new WCMException(LocaleServer.getString("photo_query.jsp.label.runtimeexception", "photo_query.jsp运行期异常!"), tx);
}
%>