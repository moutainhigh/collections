<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../error_for_dialog.jsp"%>
<%@ page import="com.trs.components.common.publish.widget.Widget" %>
<%@ page import="com.trs.components.common.publish.widget.Widgets" %>
<%@ page import="com.trs.components.common.publish.widget.SpecialAuthServer"%>
<%@include file="../../include/list_base.jsp"%>
<%@include file="../../include/public_server.jsp"%>
<%@include file="../../include/convertor_helper.jsp"%>

<%!
	private String mapFile(String _sFileName){
		if(CMyString.isEmpty(_sFileName) || _sFileName.equals("0") || _sFileName.equalsIgnoreCase("none.gif")){
			return "../images/list/none.gif";
		}else{
			return "/webpic/" + _sFileName.substring(0,8) +"/" + _sFileName.substring(0,10) +"/" +_sFileName;
		}
	}
%>
<%
	Widgets objs = (Widgets)request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	String sHostRightValue = SpecialAuthServer.getRightValue(loginUser, Widget.class).toString();
	response.setHeader("HostRightValue",  sHostRightValue);
%>
<%
//5. 遍历生成表现
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			Widget obj = (Widget)objs.getAt(i - 1);
			if (obj == null)
				continue;
			int nRowId = obj.getId();
			String sWidgetName = CMyString.transDisplay(obj.getWname());
			//是否需要权限的处理
			String sRightValue = SpecialAuthServer.getRightValue(loginUser,obj).toString();
			String sCrUser = CMyString.transDisplay(obj.getPropertyAsString("cruser"));
			String sCrTime = convertDateTimeValueToString(oMethodContext, obj.getPropertyAsDateTime("CrTime"));
			String sWidgetPic = CMyString.transDisplay(obj.getWidgetpic());
			String sPicFileName = mapFile(sWidgetPic);
			boolean bCanEdit = SpecialAuthServer.hasRight(loginUser, obj,
                SpecialAuthServer.WIDGET_EDIT);
			boolean bCanExport = SpecialAuthServer.hasRight(loginUser, obj,
                SpecialAuthServer.WIDGET_BROWSE);
			boolean bCanDelete = SpecialAuthServer.hasRight(loginUser, obj,
                SpecialAuthServer.WIDGET_DELETE);
			if(CMyString.isEmpty(sWidgetPic)){
				sPicFileName = "../images/list/none.gif";
			}
%>

<div class="thumb" id="thumb_item_<%=nRowId%>" itemId="<%=nRowId%>" rightValue="<%=sRightValue%>" title="<%=LocaleServer.getString("widget.label.websiteId", "编号")%>:&nbsp;<%=nRowId%>&#13;<%=LocaleServer.getString("widget_query.widget.label.chnlname", "资源名称")%>:&nbsp;<%=CMyString.filterForHTMLValue(obj.getWname())%>&#13;<%=LocaleServer.getString("widget.label.cruser", "创建者")%>:&nbsp;<%=CMyString.filterForHTMLValue(obj.getPropertyAsString("cruser"))%>&#13;<%=LocaleServer.getString("widget.label.crtime", "创建时间")%>:&nbsp;<%=sCrTime%>">
	<div class="desc">
		<input type="checkbox" name="" value="<%=nRowId%>" id="cbx_<%=nRowId%>"/><label for="cbx_<%=nRowId%>"><%=sWidgetName%></label>
	</div>
	<div class="img-box"><img src="<%=sPicFileName%>" onload="resizeIfNeed(this);" border="0" alt=""></div>
	<div class="cruser" WCMAnt:param="widget_query.creator">创建者:<%=CMyString.truncateStr(CMyString.transDisplay(sCrUser), 16)%></div>
	<div class="crtime" WCMAnt:param="widget_query.time">时间:<%=sCrTime%></div>
	<div class="cmds">
		<li class="edit <%=bCanEdit?"":"disableCls"%>" cmd="edit" WCMAnt:param="widget_query.modify">修改</li>
		<li class="export <%=bCanExport?"":"disableCls"%>" cmd="export" WCMAnt:param="widget_query.export">导出</li>
		<li class="delete <%=bCanDelete?"":"disableCls"%>" cmd="delete" WCMAnt:param="widget_query.delete">删除</li>
	</div>
</div>

<%
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
%>
<script>
	try{
		PageContext.drawNavigator({
			Num : <%=currPager.getItemCount()%>,
			PageSize : <%=currPager.getPageSize()%>,
			PageCount : <%=currPager.getPageCount()%>,
			CurrPageIndex : <%=currPager.getCurrentPageIndex()%>
		});
	}catch(err){
		alert(err.message);
		//Just skip it.
	}
</script>