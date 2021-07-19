<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.persistent.RightValue" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<%@ page import="com.trs.components.wcm.content.persistent.Appendix" %>
<%@ page import="com.trs.components.wcm.content.persistent.Appendixes" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.webframework.FrameworkConstants" %>
<%@ page import="com.trs.webframework.context.MethodContext" %>
<!------- WCM IMPORTS END ------------>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
//2. 获取业务数据

	Appendixes appendixes = (Appendixes)request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);

	strSelectedIds = ","+CMyString.showNull(oMethodContext.getValue("SelectAppendixIds"))+",";

//3. 构造分页参数,这段逻辑应该都可以放到服务器端	TODO
	int nPageSize = -1, nPageIndex = 1;

	if (oMethodContext != null) {
		nPageSize = oMethodContext.getValue(FrameworkConstants.ATTR_NAME_PAGESIZE, 20);
		nPageIndex = oMethodContext.getValue(FrameworkConstants.ATTR_NAME_CURRPAGE, 1); 
	}
	currPager = new CPager(nPageSize);
	currPager.setCurrentPageIndex(nPageIndex);
	currPager.setItemCount(appendixes.size());
	out.clear();
%>
 
<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
	<tr id="grid_head" class="grid_head">
		<td width="50" WCMAnt:param="document_attachment_query.jsp.select">选择</td>
		<td width="200" WCMAnt:param="document_attachment_query.jsp.originalfilename">原文件名</td>
		<td width="200" WCMAnt:param="document_attachment_query.jsp.filename">文件名</td>
		<td width="100%" WCMAnt:param="document_attachment_query.jsp.descname">显示名称</td>
		<td width="100" WCMAnt:param="document_attachment_query.jsp.tipinfo">提示信息</td> 
	</tr>
	<tbody class="grid_body" id="grid_body">
<%
//5. 遍历生成表现
	String sLoginUser = loginUser.getName();
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			Appendix appendixe = (Appendix)appendixes.getAt(i - 1);
			if (appendixe == null)
				continue;
			int nAppId = appendixe.getId();
			boolean bIsSelected = strSelectedIds.indexOf(","+nAppId+",")!=-1;
%>
	<tr id="tr_<%=nAppId%>" rowid="<%=nAppId%>" class="grid_row <%=(bIsSelected)?" grid_row_active":""%> grid_selectable_row" filename="<%=CMyString.filterForHTMLValue(appendixe.getFile())%>">
		<td><input type="radio" id="cb_<%=nAppId%>" class="grid_checkbox" name="RowId" filename="<%=CMyString.filterForHTMLValue(appendixe.getFile())%>" showname="<%=CMyString.transDisplay(appendixe.getDesc())%>" value="<%=nAppId%>" <%=(bIsSelected)?"checked":""%>/><span class="grid_index" id="grid_index_<%=nAppId%>"><%=i%></span></td>
		<td><%=CMyString.transDisplay(appendixe.getSrcFile())%></td>
		<td><%=CMyString.transDisplay(appendixe.getFile())%></td>
		<td><%=CMyString.transDisplay(appendixe.getDesc())%></td>
		<td><%=CMyString.transDisplay(appendixe.getAlt())%></td>
	</tr>
<%
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
%>
	</tbody>
	<tbody id="grid_NoObjectFound" style="display:none;">
		<tr>
			<td colspan="5" class="no_object_found" WCMAnt:param="document_query.jsp.noFound">不好意思, 没有找到符合条件的对象!</td>
		</tr>
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