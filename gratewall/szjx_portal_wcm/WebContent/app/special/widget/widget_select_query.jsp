<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../error_for_dialog.jsp"%>
<%@ page import="com.trs.components.common.publish.widget.Widget" %>
<%@ page import="com.trs.components.common.publish.widget.Widgets" %>
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

			String sCrUser = CMyString.transDisplay(obj.getPropertyAsString("cruser"));
			String sCrTime = convertDateTimeValueToString(oMethodContext, obj.getPropertyAsDateTime("CrTime"));
			String sWidgetPic = CMyString.transDisplay(obj.getWidgetpic());
			String sPicFileName = mapFile(sWidgetPic);
			if(CMyString.isEmpty(sWidgetPic)){
				sPicFileName = "../images/list/none.gif";
			}
			String sWName = CMyString.filterForHTMLValue(obj.getWname());
%>

<div class="thumb" id="thumb_item_<%=nRowId%>" itemId="<%=nRowId%>" wName="<%=sWName%>" onmouseover="imageMouseOver(event, this, <%=i%>);" onmouseout="imageMouseOut(event, this, <%=i%>);" onmousedown="imageMouseDown(event, this);">
	<div class="desc">
		<span><%=i%>.</span> <%=sWidgetName%>
	</div>
	<div class="img-box"><img src="<%=sPicFileName%>" border="0" alt="" onload="resizeIfNeed(this);" ></div>
	<div class="cruser" WCMAnt:param="widget_select_query.creator">创建者：<%=sCrUser%></div>
	<div class="crtime" WCMAnt:param="widget_select_query.time">时间：<%=sCrTime%></div>
	<div class="cmds">
		<li class="selectWidget" cmd="selectWidget"></li>
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