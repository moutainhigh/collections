<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.metadata.definition.ClassInfo" %>
<%@ page import="com.trs.components.metadata.definition.ClassInfos" %>
<%
try{
%>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof ClassInfos)){
		throw new WCMException(CMyString.format(LocaleServer.getString("classinfo_select_query.jsp.servicenoobject","服务(com.trs.ajaxservice.ClassInfoServiceProvider.query)返回的对象集合类型不为ClassInfos，而为{0}，请确认。"),new Object[]{result.getClass()}));
	}
	ClassInfos objs = (ClassInfos)result;
	
%>

<table id="table_cnt" cellspacing=0 border="0" cellpadding=0 style="width:100%;border-top:1px dotted gray">
		
	<tbody>
<%
//5. 遍历生成表现
	String sType = CMyString.showNull(oMethodContext.getValue("selecttype"), "radio");
	int nNum = 0;
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			ClassInfo obj = (ClassInfo)objs.getAt(i - 1);
			if (obj == null)
				continue;
			int nRowId = obj.getId();
			boolean bIsSelected = strSelectedIds.indexOf(","+nRowId+",")!=-1;
			String sRightValue = getRightValueByMember(obj, loginUser).toString();
			String sCrUser = obj.getPropertyAsString("crUser");
			String sCrTime = convertDateTimeValueToString(oMethodContext,obj.getPropertyAsDateTime("crTime"));
			String sName = CMyString.transDisplay(obj.getPropertyAsString("CNAME"));
			String sTruncName = "";
			if(sName.length() > 8){
				sTruncName = sName.substring(0,8) + "...";
			}else{
				sTruncName = sName;
			}
%>
<%
			if(nNum%2==0){
				out.println("<tr>");
			}	
			nNum++;
%>
	<td width="50%" height="24" title="<%=LocaleServer.getString("classinfo.label.name", "名称")%>: <%=sName%>&#13;ID: <%=nRowId%>&#13;<%=LocaleServer.getString("classinfo.label.cruser", "创建者")%>: <%=sCrUser%>&#13;<%=LocaleServer.getString("classinfo.label.crtime", "创建时间")%>: <%=sCrTime%>">
		<span style="width: 10px;"><%=(i)%>.</span>
		<input type="<%=sType%>" class="chk_tempid" name="queryId" value="<%=nRowId%>" id="chk_<%=nRowId%>" _name="<%=sName%>">
		<label class="sp_name" _id="<%=nRowId%>" style="cursor: pointer; width: 100px; white-space:nowrap; text-overflow:ellipsis; overflow:hidden" for="chk_<%=nRowId%>"><%=sTruncName%></label>
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
	throw new WCMException(LocaleServer.getString("classinfo)_select_query.jsp.runtimeex","classic_select_query.jsp运行期异常!"), tx);
}
%>