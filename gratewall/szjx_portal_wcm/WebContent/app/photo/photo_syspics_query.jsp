<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.Appendixes" %>
<%@ page import="com.trs.components.wcm.content.persistent.Appendix" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%
try{
%>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof Appendixes)){
		CMyString.format(LocaleServer.getString("photo_syspics_query.jsp.label.service", "服务(com.trs.ajaxservice.viewDocumentServiceProvider.trsQuery)返回的对象集合类型不为Appendixes,而为({0}),请确认."),new Object[]{result.getClass()});		
	}
	Appendixes objs = (Appendixes)result;
	
%>
<%
//5. 遍历生成表现
	int nNum = 0;
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			Appendix obj = (Appendix)objs.getAt(i - 1);
			if (obj == null)
				continue;	
			Document currDocument = Document.findById(obj.getDocId());
			if(currDocument == null){
				throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,CMyString.format(LocaleServer.getString("photo_syspics_query.jsp.doc_notfound", "没有找到ID为[{0}]的文档!"), new int[]{obj.getDocId()}));
			}
			int nRowId = obj.getPropertyAsInt("APPENDIXID",0);
			boolean bIsSelected = strSelectedIds.indexOf(","+nRowId+",")!=-1;
			String sRightValue =getRightValue(obj, loginUser).toString();
%>
<div class="thumb_item<%=(bIsSelected)?" thumb_item_active":""%>" id="thumb_item_<%=nRowId%>" itemId="<%=nRowId%>" right="<%=sRightValue%>">
        <div class="thumb" id="thumb_<%=nRowId%>" onclick='showDetail("<%=obj.getFile()%>",<%=obj.getDocId()%>)'>
			<img src="<%=mapFileName(obj.getFile())%>" style="position:absolute;right:20px;bottom:20px;cursor:pointer" onload="resizeIfNeed(this);">	
		</div> 
        <div class="attrs" id="thumb_attrs_<%=nRowId%>">
            <input id="cbx_<%=nRowId%>" type="checkbox"<%=(bIsSelected)?" checked":""%> name="AppendixId" value="<%=nRowId%>"  photo_srcs="<%=obj.getFile()%>"/>
            <span id="desc_<%=nRowId%>" title="<%=CMyString.filterForHTMLValue(getAppDesc(currDocument.getTitle(),obj.getDesc()))%>"><%=CMyString.filterForHTMLValue(getAppDesc(currDocument.getTitle(),obj.getDesc()))%></span>
        </div>
    </div>
<%
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
%>
<div id="thumb_NoObjectFound" style="display:none;">
	<div class="no_object_found" WCMAnt:param="photo_syspics_list.html.noFound">不好意思, 没有找到符合条件的对象!</div>
</div>
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
	throw new WCMException(LocaleServer.getString("photo_syspics_list.jsp.label.runtimeexception", "photo_syspics_query.jsp运行期异常!"), tx);
}
%>
<%!
	private String getAppDesc(String _value,String _defaultValue) throws WCMException{
		return _value == null ? _defaultValue:_value;
	}
	private String mapFileName(String _fn) throws WCMException{
		if(_fn == null || (_fn.trim()).equals("")){
			return "../images/photo/pic_notfound.gif";
		}
		String r = _fn == null? "":_fn;
		return "/webpic/" + r.substring(0,8) + "/" + r.substring(0,10) + "/" + r;
	}
%>