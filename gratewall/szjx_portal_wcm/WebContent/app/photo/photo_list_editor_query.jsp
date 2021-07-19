<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.components.wcm.content.ViewDocument" %>
<%@ page import="com.trs.components.wcm.content.ViewDocuments" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document"%>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<%@ page import="com.trs.webframework.FrameworkConstants" %>
<%@ page import="com.trs.webframework.context.MethodContext" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%
try{
%>
<%@include file="../include/list_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	MethodContext oMethodContext1 = (MethodContext)request.getAttribute(FrameworkConstants.ATTR_NAME_METHODCONTEXT);
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof ViewDocuments)){
		throw new WCMException(CMyString.format(LocaleServer.getString("photo_list_editor.jsp.objtype_isnot_ViewDocument","服务(com.trs.ajaxservice.PhotoServiceProvider.findbyid)返回的对象类型不为ViewDocument，而为{0}，请确认。"),new Object[]{result.getClass()}));
	}
	int nCurrDocId = 0;
	if (oMethodContext1 != null) {
		nCurrDocId = oMethodContext1.getValue("CurrDocId", 0);
	}
	ViewDocuments objs = (ViewDocuments)result;
	FilesMan currFilesMan = FilesMan.getFilesMan();
	int nChannelId = currRequestHelper.getInt("Channelid",0);
	int nSiteId = currRequestHelper.getInt("SiteId",0);
%>
<%
//5. 遍历生成表现
	for (int i = currPager.getFirstItemIndex(); i <= currPager.getLastItemIndex(); i++) {
		try{
			ViewDocument obj = (ViewDocument)objs.getAt(i - 1);
			if (obj == null)
				continue;
			Document currDocument = Document.findById(obj.getDocId());
			if(currDocument == null){
				throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, CMyString.format(LocaleServer.getString("photo_list_editor_query.jsp.doc_notfound", "没有找到指定ID为[{0}]的文档!"), new int[]{ obj.getDocId()}));
			}
			int nDocId = currDocument.getId();
			int nRowId = obj.getChnlDocProperty("recid",0);		
			ChnlDoc chnldoc = ChnlDoc.findById(nRowId);
			if(chnldoc == null){
				throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, CMyString.format(LocaleServer.getString("photo_list_editor_query.jsp.chnldoc_notfound", "没有找到指定ID为[{0}]的chnldoc!"), new int[]{nRowId}));
			}
			int nModal = chnldoc.getPropertyAsInt("MODAL",0);
			String sFileNameLike = currDocument.getPropertyAsString("docrelwords");
			String sDefault = currDocument.getAttributeValue("srcfile");
			String sPicUrl = currDocument.getRelateWords();
			String sFileName = mapfile(sFileNameLike,0,sDefault);			
			boolean bIsSelected = nCurrDocId==nDocId || strSelectedIds.indexOf(","+nRowId+",")!=-1;
			String sRightValue = obj.getRightValue(loginUser).toString();
%>
    <div class="thumb_item<%=(bIsSelected)?" thumb_item_active":""%>" id="thumb_item_<%=nRowId%>" itemId="<%=nRowId%>" right="<%=sRightValue%>" docId="<%=nDocId%>">
        <div class="thumb" id="thumb_<%=nRowId%>" onclick='showDetail("<%=sPicUrl%>",<%=nDocId%>)'>
			<img src="<%=sFileName%>" style="cursor:hand;position:absolute;" onload="resizeIfNeed(this);">		
		</div> 
        <div class="attrs" id="thumb_attrs_<%=nRowId%>">
            <input id="cbx_<%=nRowId%>" type="checkbox"<%=(bIsSelected)?" checked":""%> name="AppendixId" value="<%=nRowId%>"  photo_srcs="<%=sPicUrl%>" photo_url="<%=sDefault%>" photo_docid="<%=nDocId%>" desc="<%=CMyString.filterForHTMLValue(currDocument.getTitle())%>"/>
			<%
				if(nModal == 2){
			%>
			<span class="document_modal_<%=nModal%>" style="width:15px;">&nbsp;</span>
			<%	
				}
			%>
            <span id="desc_<%=nRowId%>" title="<%=CMyString.filterForHTMLValue(currDocument.getTitle())%>" style="color:<%=currDocument.getStatusId() == 10 ? "green" :""%>;text-align:left;"><%=CMyString.filterForHTMLValue(currDocument.getTitle())%></span>
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
			SelectedIds : '<%=CMyString.filterForJs(sOrigSelectedIds)%>',
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
	throw new WCMException(LocaleServer.getString("photo_list_editor_query.jsp.label.runtimeexception", "watermark_query.jsp运行期异常!"), tx);
}
%>
<%!
	private String mapfile(String sFileName,int temp,String _default) throws WCMException{
		if(sFileName == null || (sFileName.trim()).equals("")){
				return "../images/photo/pic_notfound.gif";
			}
			String[] fn = sFileName.split(",");
			String r = "";
			if(fn.length <= temp){
				r = "../../file/read_image.jsp?FileName=" +  _default;
				return r;
			}
			r = fn[temp];
			return "/webpic/" + r.substring(0,8) + "/" + r.substring(0,10) + "/" + r;	
	}
%>
<div id="thumb_NoObjectFound" style="display:none;">
	<div class="no_object_found" WCMAnt:param="list.NoObjectFound">不好意思, 没有找到符合条件的对象!</div>
</div>