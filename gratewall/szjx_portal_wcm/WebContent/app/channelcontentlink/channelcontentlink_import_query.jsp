<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.ContentLinkType" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentLinkTypes" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%
try{
%>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof ContentLinkTypes)){
		throw new WCMException(CMyString.format(LocaleServer.getString("channelcontentlink_import_query.jsp.servicereturncertain","服务(com.trs.ajaxservice.ContentLinkServiceProvider.query)返回的对象集合类型不为ContentLinkTypes，而为{0}，请确认。"),new Object[]{result.getClass()}));
	}
	ContentLinkTypes objs = (ContentLinkTypes)result;
	
%>
<table id="table_cnt" cellspacing=0 border="0" cellpadding=0 style="width:100%;border-top:1px dotted gray">		
	<tbody>
<!--<div id="templates" class="templates_container" style="overflow: auto; display:none;"></div>-->
<%
//5. 遍历生成表现
	int nNum = 0;
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			ContentLinkType obj = (ContentLinkType)objs.getAt(i - 1);
			if (obj == null)
				continue;
			int nRowId = obj.getId();
			boolean bIsSelected = strSelectedIds.indexOf(","+nRowId+",")!=-1;
			String sRightValue = getRightValueByMember(obj, loginUser).toString();			
%>
<%
			if(nNum%2==0){
				out.println("<tr>");
			}	
			nNum++;
%>
	<td width="50%" height="24">
		<span style="width: 10px;"><%=(i)%>.</span>
		<input type="checkbox" name="LinkType" id="<%=obj.getTypeName()%>_<%=obj.getId()%>" value="<%=obj.getId()%>" onclick="recordValue(this);">
		<span class="sp_name" _id="<%=nRowId%>" style="cursor: pointer; width: 100px; white-space:nowrap; text-overflow:ellipsis; overflow:hidden"><label for="<%=obj.getTypeName()%>_<%=obj.getId()%>"><%=CMyString.transDisplay(obj.getTypeName())%></label></span>
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
	throw new WCMException(LocaleServer.getString("channelcontentlink_import_query.jsp.runtimeex","template_query.jsp运行期异常!"), tx);
}
%>