<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../error_for_dialog.jsp"%>
<%@ page import="com.trs.components.common.publish.widget.PageStyle" %>
<%@ page import="com.trs.components.common.publish.widget.PageStyles" %>
<%@include file="../../include/list_base.jsp"%>
<%@include file="../../include/public_server.jsp"%>
<%@include file="../../include/convertor_helper.jsp"%>
<%
	MethodContext oMethodContext1 = (MethodContext)request.getAttribute(FrameworkConstants.ATTR_NAME_METHODCONTEXT);
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof PageStyles)){
		throw new WCMException(CMyString.format(LocaleServer.getString("pagestyle_select_query.jsp.service", "服务(com.trs.ajaxservice.PageStyleServiceProvider.query)返回的对象集合类型不为PageStyles，而为{0}，请确认。"), new Object[]{result.getClass()}));
	}
	PageStyles objs = (PageStyles)result;
	//5. 遍历生成表现
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			PageStyle obj = (PageStyle)objs.getAt(i - 1);
			if (obj == null)
				continue;
			int nRowId = obj.getId();
			
			//获取基本信息
			String sPageStyleDesc = CMyString.showNull(obj.getStyleDesc(),"");
			sPageStyleDesc = CMyString.filterForHTMLValue(sPageStyleDesc);
			String sThumb = CMyString.showNull(obj.getStyleThumb(),"");
			sThumb = CMyString.filterForHTMLValue(sThumb);
			String sThumn_url = "../images/zt_wt.gif";
			if(!CMyString.isEmpty(sThumb)){
				sThumn_url = "../file/read_image.jsp?FileName=" + sThumb;
			}
			String sPageStyleName = CMyString.showNull(obj.getStyleName(),"");
			String sCssDir = "/template/style/style_css/";
			String sCssFilePath = CMyString.filterForHTMLValue(sCssDir + sPageStyleName + ".css");
%>
<div class="thumb" id="thumb_item_<%=nRowId%>"><div style="margin-left:8px;"><img border=0 alt="" src="<%=sThumn_url%>" width="100px" height="100px" style="display:block;"/><input type="radio" name="styleName" value="<%=CMyString.filterForHTMLValue(obj.getStyleName())%>" id="<%=nRowId%>" cssFile="<%=sCssFilePath%>" style="vertical-align:middle;" /><label for="<%=nRowId%>" style="cursor:pointer;display:inline-block;vertical-align:middle;line-height: 20px;" onclick="mark(<%=nRowId%>)"> <%=CMyString.transDisplay(CMyString.truncateStr(sPageStyleDesc,14))%></label></div></div>

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