<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.resource.Keyword" %>
<%@ page import="com.trs.components.wcm.resource.Keywords" %>
<%@ page import="com.trs.infra.persistent.WCMFilter"%>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.infra.util.CPager" %>

<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
	response.addHeader("Cache-Control","no-store"); //Firefox
	response.setHeader("Pragma","no-cache"); //HTTP 1.0
	response.setDateHeader ("Expires", -1);
	response.setDateHeader("max-age", 0);
%>

<%!
	public String getOrderFlag(String field, String currOrderBy){
		if(CMyString.isEmpty(currOrderBy))return "";
		String[] orderBy = currOrderBy.toLowerCase().split(" ");
		field = field.toLowerCase();
		if(!orderBy[0].equals(field))return "";
		return "&nbsp;" + ("asc".equals(CMyString.showEmpty(orderBy[1], "asc"))?"↑":"↓");
	}
%>

<%
try{
%>
<%
	// 获取数据
	int nSiteType = currRequestHelper.getInt("siteType",0);
	int nSiteId = currRequestHelper.getInt("siteId",0);
	String sKname = currRequestHelper.getString("KNAME");
	String sSearchCruser = currRequestHelper.getString("CrUser");

	String sCurrOrderBy = currRequestHelper.getString("OrderBy");
	if(CMyString.isEmpty(sCurrOrderBy))sCurrOrderBy="CRTIME desc";
	
	//权限校验
	
	//业务代码
	String sSelectFields = "KNAME,CRUSER,CRTIME";
	String sWhere = "SITEID in (0,?) and SITETYPE=?";
	if(!CMyString.isEmpty(sKname)&&!CMyString.isEmpty(sSearchCruser)){
		sWhere += " and (KNAME like ? or CrUser like ?)";
	}else if(!CMyString.isEmpty(sKname)){
		sWhere += " and KNAME like ?";
	}else if(!CMyString.isEmpty(sSearchCruser)){
		sWhere += " and CrUser like ?";
	}
	WCMFilter filter = new WCMFilter("XWCMKEYWORD",sWhere,sCurrOrderBy );
	filter.addSearchValues(nSiteId);
	filter.addSearchValues(nSiteType);
	if(!CMyString.isEmpty(sKname)&&!CMyString.isEmpty(sSearchCruser)){
		filter.addSearchValues('%'+sKname+'%');
		filter.addSearchValues('%'+sSearchCruser+'%');
	}else if(!CMyString.isEmpty(sKname)){
		filter.addSearchValues('%'+sKname+'%');
	}else if(!CMyString.isEmpty(sSearchCruser)){
		filter.addSearchValues('%'+sSearchCruser+'%');
	}
	Keywords currKeywords = Keywords.openWCMObjs(loginUser, filter);
	CPager currPager = new CPager(currRequestHelper.getInt("PageItemCount", 8));
	currPager.setItemCount( currKeywords.size() );	
	currPager.setCurrentPageIndex( currRequestHelper.getInt("currpage", 1) );
%>
<table cellspacing=0 border="1" cellpadding=0 id="grid_table" class="grid_table" borderColor="gray">
	<tr id="grid_head" class="grid_head">
		<td WCMAnt:param="keyword_list.jsp.order" style="width:50px">序号</td>
		<td WCMAnt:param="keyword_list.jsp.modify" style="width:40px">修改</td>
		<td style="text-align:middle" grid_sortby="KNAME"><span WCMAnt:param="keyword_list.jsp.filename">关键词名称</span><%=getOrderFlag("KNAME", sCurrOrderBy)%></td>
		<td style="width:120px" grid_sortby="CRUSER"><span WCMAnt:param="keyword_list.jsp.cruser">创建者</span><%=getOrderFlag("CRUSER", sCurrOrderBy)%></td>
		<td style="width:120px" grid_sortby="CRTIME"><span WCMAnt:param="keyword_list.jsp.crtime">创建时间</span><%=getOrderFlag("CRTIME", sCurrOrderBy)%></td>
		<td WCMAnt:param="keyword_list.jsp.delete" style="width:40px;border-right:0;">删除</td>
	</tr>
	<tbody class="grid_body" id="grid_body">
<%
//5. 遍历生成表现
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			Keyword obj = (Keyword)currKeywords.getAt(i - 1);
			if (obj == null)
				continue;
			int nRowId = obj.getId();
			int nKeywordId = obj.getId();
			String sCruser= obj.getPropertyAsString("CrUser");
			CMyDateTime oCrtime = obj.getPropertyAsDateTime("CrTime");
			String sCrtime = oCrtime.toString("MM-dd HH:mm");
			String sRowClassName = i%2==0?"grid_row_even":"grid_row_odd";
%>
	<tr id="tr_<%=nRowId%>" rowid="<%=nRowId%>" class="grid_row  <%=sRowClassName%>" KeywordId="<%=nKeywordId%>">
		<td><span class="grid_index" id="grid_index_<%=nRowId%>"><%=i%></span></td>
		<td><span class="keyword_edit grid_function" grid_function="keyword_edit">&nbsp;</span></td>
		<td style="text-align:left;padding-left:5px;"><a href="#" onclick="return false;" grid_function="keyword_edit"><%=CMyString.transDisplay(obj.getKNAME())%></a></td>
		<td><%=CMyString.transDisplay(sCruser)%></td>
		<td><%=sCrtime%></td>
		<td><span class="keyword_delete grid_function" grid_function="keyword_delete">&nbsp;</span></td>

	</tr>
<%
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
%>
	</tbody>
	<tbody id="grid_NoObjectFound" style="display:none;">
		<tr><td colspan="6" class="no_object_found" WCMAnt:param="list.NoObjectFound">不好意思, 没有找到符合条件的对象!</td></tr>
	</tbody>
</table>
<input type="hidden" id="siteId" value="<%=nSiteId%>">
<input type="hidden" id="siteType" value="<%=nSiteType%>">
<script>
	try{
		if(<%=currPager.getItemCount()%>==0){
			$('grid_body').appendChild(Element.first($('grid_NoObjectFound')));
		}
		Event.observe('grid_head', 'click', function(event){
			event = event || window.event;
			var dom = Event.element(event);
			var sortField = null;
			while(dom && dom.tagName != "BODY"){
				sortField = dom.getAttribute('grid_sortby');
				if(sortField){
					break;
				}
				if(dom.tagName == 'TABLE') break;
				dom = dom.parentNode;
			}
			if(!sortField){
				return;
			}
			var orderby = (PageContext.params.OrderBy || '<%=CMyString.filterForJs(sCurrOrderBy)%>').toLowerCase();
			var sCurrField = orderby.split(' ')[0];
			var sCurrOrder = orderby.split(' ')[1];
			sortOrder = (sCurrField!=sortField.toLowerCase()||sCurrOrder=='desc')?'asc':'desc';
			PageContext.loadList(Ext.apply(PageContext.params, {OrderBy : sortField + ' ' + sortOrder}));
		});
		Ext.fly('grid_body').on('selectstart', function(event){
			Event.stop(event.browserEvent || event);
			return false;
		});
		Ext.fly('grid_body').on('click', function(event){
			event = event || window.event;
			var srcElement = Event.element(event);
			var oGridRow = new wcm.GridRow(srcElement);
			if(oGridRow.click()){
				var row = wcm.Grid.findRow(srcElement);
				if(row == null) return;
				var bubble = wcm.Grid.doClickRow(row , {
					isCtrl : event.ctrlKey,
					isShift : event.shiftKey,
					event : event,
					target : srcElement
				});
				if(bubble===false)return;
				oGridRow.afterclick();
			}
		}, this);
		wcm.Grid.applyContextMenu();
		var oTmpGridRow = new wcm.GridRow(null);
		oTmpGridRow.afterclick();

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
	throw new WCMException(LocaleServer.getString("list_dowith.runExcep","keyword_list_dowith.jsp运行期异常!"), tx);
}
%>