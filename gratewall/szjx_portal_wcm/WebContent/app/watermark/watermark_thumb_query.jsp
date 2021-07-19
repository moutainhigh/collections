<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.wcm.photo.Watermark" %>
<%@ page import="com.trs.wcm.photo.Watermarks" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%
try{
%>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof Watermarks)){
		throw new WCMException(CMyString.format(LocaleServer.getString("watermark_thumb_query.jsp.service", "服务(com.trs.ajaxservice.WatermarkServiceProvider.query)返回的对象集合类型不为Watermarks，而为[{0}]，请确认。!"), new Object[]{result.getClass()}));
	}
	Watermarks objs = (Watermarks)result;
	FilesMan currFilesMan = FilesMan.getFilesMan();
%>
<%
//5. 遍历生成表现
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			Watermark obj = (Watermark)objs.getAt(i - 1);
			if (obj == null)
				continue;
			int nRowId = obj.getId();
			String sFileName = obj.getWMPicture();
			sFileName = currFilesMan.mapFilePath(sFileName, FilesMan.PATH_HTTP) + sFileName;
			boolean bIsSelected = strSelectedIds.indexOf(","+nRowId+",")!=-1;
			WebSite currSite = WebSite.findById(obj.getLibId());
			String sRightValue = getRightValue(currSite, loginUser).toString();
			String sRowClassName = i%2==0?"grid_row_even":"grid_row_odd";
%>
    <div class="thumb_item<%=(bIsSelected)?" thumb_item_active":""%>" id="thumb_item_<%=nRowId%>" itemId="<%=nRowId%>" right="<%=sRightValue%>">
        <div class="thumb" id="thumb_<%=nRowId%>">
			<img src="<%=sFileName%>" style="cursor:hand;position:absolute;" onload="resizeIfNeed(this);" title="<%=CMyString.filterForHTMLValue(obj.getPropertyAsString("wmname"))%>">		
		</div> 
        <div class="attrs" id="thumb_attrs_<%=nRowId%>">
            <input id="cbx_<%=nRowId%>" type="checkbox"<%=(bIsSelected)?" checked":""%>/>
            <span id="desc_<%=nRowId%>" title="<%=CMyString.filterForHTMLValue(obj.getPropertyAsString("wmname"))%>"><%=CMyString.filterForHTMLValue(filter(obj.getPropertyAsString("wmname")))%></span>
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
		myThumbList.init({
			SelectedIds : '<%=sOrigSelectedIds%>',
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
	throw new WCMException(LocaleServer.getString("watermark_thumb_query.jsp.label.runtimeexception","watermark_query.jsp运行期异常!"), tx);
}
%>
<%!
	private String filter(String sTitle) throws WCMException{
		return sTitle.length() > 10 ? sTitle.substring(0,8) + ".." : sTitle;
	}
%>