<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page errorPage="../include/error_for_dialog.jsp"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CPager" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.trs.webframework.FrameworkConstants" %>
<%@ page import="com.trs.webframework.context.MethodContext" %>

<%
boolean bRightConfig = true;
try{
%>
<%@include file="../include/public_server.jsp"%>
<%
	MethodContext oMethodContext = (MethodContext)request.getAttribute(FrameworkConstants.ATTR_NAME_METHODCONTEXT);
    Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof ArrayList)){
            throw new WCMException(CMyString.format(LocaleServer.getString("trsserver_query.jsp.label.service", "服务(com.trs.ajaxservice.viewDocumentServiceProvider.trsQuery)返回的对象集合类型不为ArrayList,而为({0}),请确认."),new Object[]{result.getClass()}));
	}
	ArrayList objs = (ArrayList)result;

	int nPageSize = -1, nPageIndex = 1;
	String strSelectedIds = "";
	if (oMethodContext != null) {
		nPageSize = oMethodContext.getValue(FrameworkConstants.ATTR_NAME_PAGESIZE, 20);
		nPageIndex = oMethodContext.getValue(FrameworkConstants.ATTR_NAME_CURRPAGE, 1);
		strSelectedIds = ","+CMyString.showNull(oMethodContext.getValue("SelectIds"))+",";
	}	
	CPager currPager = new CPager(nPageSize);
	currPager.setCurrentPageIndex(nPageIndex);
	currPager.setItemCount(objs.size());
	String sCurrOrderBy = CMyString.showNull(oMethodContext.getValue("OrderBy"));
	if(objs.size() == 1 && objs.get(0).equals("1100")){
		objs.remove(0);
		bRightConfig = false;
	}
	
	out.clear();
%>
<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray" style="width:100%;">
	<tr id="grid_head" class="grid_head">
		<td onclick="wcm.Grid.selectAll(RowId);" width="50" WCMAnt:param="list.selectall" class="selAll">全选</td>
		
		<td grid_sortby="wcmdocument.doctitle"><span WCMAnt:param="documentlist.head.doctitle">文档标题</span></td>
		<td grid_sortby="wcmchnldoc.crtime" width="120"><span WCMAnt:param="documentlist.head.crtime">创建时间</span></td>
		<td grid_sortby="wcmchnldoc.cruser" width="70"><span WCMAnt:param="documentlist.head.publishman">发稿人</span></td>
		<td grid_sortby="wcmdocument.doctype" width="45"><span WCMAnt:param="abslist.head.view">查看</span></td>
	</tr>
	<tbody class="grid_body" id="grid_body">
<%
	  if(objs.size() > 0){
		  for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
			try{
				HashMap currMap = (HashMap)objs.get(i - 1);
				if (currMap == null)
					continue;
				//用transdisplay有点问题,&nbsp;会转成空格,暂时先改成filterForHTMLValue
				String sId = (String)currMap.get("IR_SID");
				String sTitle = CMyString.filterForJs((String)currMap.get("IR_URLTITLE"));
				String sCrtime = currMap.get("IR_URLTIME").toString();
				boolean bIsSelected = strSelectedIds.indexOf(","+i+",")!=-1;
				String sRowClassName = i%2==0?"grid_row_even":"grid_row_odd";
%>
	<tr rowid="<%=sId%>" class="grid_row <%=sRowClassName%><%=(bIsSelected)?" grid_row_active":""%>" >
	<td><input type="checkbox" id="cb_<%=sId%>" class="grid_checkbox" name="RowId" value="<%=i%>" <%=(bIsSelected)?"checked":""%>/><span class="grid_index" id="grid_index_<%=i%>"><%=i%></span></td>
	<td class="doctitle" contextmenu="1"><a unselectable="on" href="#" onclick='view("<%=sId%>",<%=nPageIndex%>,<%=nPageSize%>)'><%=sTitle%></a></td>
	<td><%=sCrtime%></td>
	<td><%=loginUser.getName()%></td>
	<td><span class="doctype_20" style="width:30px;border-right:0;cursor:pointer;float:none;" title="HTML">&nbsp;</span></td>
	</tr>
<%
				}catch(Exception ex){
				ex.printStackTrace();
			}	
		}
	}
%>
	</tbody>
<% if(objs.size()==0){%>
	<tbody id="grid_NoObjectFound">
		<tr><td colspan="5" class="no_object_found" WCMAnt:param="list.NoObjectFound" WCMAnt:param="iflowcontent_query.jsp.none">不好意思, 没有找到符合条件的对象!</td></tr>
	</tbody>
<%}%>
</table>
<script>
	try{
		wcm.Grid.init({
			RecordNum : <%=objs.size()%>
		});
		PageContext.drawNavigator({
			Num : <%=objs.size()%>,
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
	throw new WCMException(LocaleServer.getString("trsserver_query.jsp.label.runtimeexception", "trsserver_query.jsp运行期异常!"), tx);
}
%>
<%
   if(!bRightConfig){
%>
<script language="javascript">
	Ext.Msg.warn(wcm.LANG.trsserver_query_101 || '未找到有效的TRSServer连接，请确认TRSServer配置正确并开启服务！');
</script>
<%
}
%>
<%!
	private String convertDateTimeValueToString(MethodContext _methodContext, CMyDateTime _dtValue) {
		String sDateTimeFormat = CMyDateTime.DEF_DATETIME_FORMAT_PRG;
		if (_methodContext != null) {
			sDateTimeFormat = _methodContext.getValue("DateTimeFormat");
			if (sDateTimeFormat == null) {
				sDateTimeFormat = CMyDateTime.DEF_DATETIME_FORMAT_PRG;
			}
		}
		String sDtValue = _dtValue.toString(sDateTimeFormat);
		return sDtValue;
	}
%>