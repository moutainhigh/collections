<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="error_for_dialog.jsp"%>
<%@ page import="com.trs.components.special.Special" %>
<%@ page import="com.trs.components.special.Specials" %>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	MethodContext oMethodContext1 = (MethodContext)request.getAttribute(FrameworkConstants.ATTR_NAME_METHODCONTEXT);
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof Specials)){
		throw new WCMException(CMyString.format(LocaleServer.getString("special_query.jsp.service","com.trs.ajaxservice.SpecialServiceProvider.query)返回的对象集合类型不为Specials，而为{0}，请确认。"),new Object[]{result.getClass()}));
	}
	Specials objs = (Specials)result;
	//5. 遍历生成表现
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			Special obj = (Special)objs.getAt(i - 1);
			if (obj == null)
				continue;
			int nRowId = obj.getId();
			
			//获取基本信息
			String sThumb = CMyString.showNull(obj.getViewThumb(),"");
			sThumb = CMyString.filterForHTMLValue(sThumb);
			String sThumn_url = "images/zt_wt.gif";
			if(!CMyString.isEmpty(sThumb)){
				sThumn_url = "file/read_image.jsp?FileName=" + sThumb;
			}
			String sSpecialName = CMyString.showNull(obj.getSpecialName(),"");
%>
<div class="thumb" id="thumb_item_<%=nRowId%>"><div style="margin-left:8px;"><img border=0 alt="" src="<%=sThumn_url%>" width="100px" height="100px"/><br /><input type="radio" name="specialName" style ="vertical-align:middle;" value="<%=CMyString.filterForHTMLValue(sSpecialName)%>" id="<%=nRowId%>"/><label for="<%=nRowId%>" style="cursor:pointer;display:inline-block;vertical-align:middle;" title="<%=CMyString.filterForHTMLValue(sSpecialName)%>"> <%=CMyString.transDisplay(CMyString.truncateStr(sSpecialName,10))%></label></div></div>

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