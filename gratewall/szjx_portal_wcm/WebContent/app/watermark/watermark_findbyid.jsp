<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.wcm.photo.Watermark" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite"%>
<%@ page import="com.trs.infra.support.file.FilesMan" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%
try{
%>
<%@include file="../include/findbyid_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%!
	private boolean hasRight(User _currUser, CMSObj _objCurrent,int _nRightIndex) throws WCMException{
		return AuthServer.hasRight(_currUser,_objCurrent,_nRightIndex);
	}
%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof Watermark)){
		throw new WCMException(CMyString.format(LocaleServer.getString("watermark_findbyid.jsp.jsp.service", "服务(com.trs.ajaxservice.WatermarkServiceProvider.findbyid)返回的对象类型不为Watermark，而为[{0}]，请确认!"), new Object[]{result.getClass()}));
	}
	Watermark obj = (Watermark)result;
	boolean bCanEdit = hasRight(loginUser,obj,32);
	String sEditable = bCanEdit?"editable":"readonly";
	WebSite currWebSite = WebSite.findById(obj.getLibId());
	if(currWebSite == null){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID,CMyString.format(LocaleServer.getString("watermark_findbyid.jsp.jsp.website_notfound", "没有找到ID为{0}的站点"),new int[]{ obj.getLibId()}));
	}
	FilesMan currFilesMan = FilesMan.getFilesMan();
	String sFileName = obj.getWMPicture();
	sFileName = currFilesMan.mapFilePath(sFileName, FilesMan.PATH_HTTP) + sFileName;
%>
<div class="attribute_row <%=sEditable%> main_attr">
	<span class="wcm_attr_value" _fieldName="WMNAME" _fieldValue="<%=CMyString.filterForHTMLValue(obj.getWMName())%>" validation="type:'string',required:'true',desc:'水印名称',max_len:50" validation_desc="水印名称" WCMAnt:paramattr="validation_desc:watermark_findbyid.jsp.desc" title="<%=CMyString.filterForHTMLValue(obj.getWMName())%>"><%=CMyString.filterForHTMLValue(obj.getWMName())%></span>
</div>
<div class="attribute_row_sep"></div>
<div class="attribute_row" style="padding-left:5px;font-size:12px;width:95%;">
	<span title="ID:<%=obj.getId()%>&#13;<%=LocaleServer.getString("watermark.label.cruser", "创建者")%>:<%=obj.getPropertyAsString("cruser")%>&#13;<%=LocaleServer.getString("watermark.label.crtime", "创建时间")%>:<%=obj.getCrTime().toString("yy-MM-dd HH:mm")%>" style="white-space:nowrap;overflow:hidden;"><span style="font-size:10px;" WCMAnt:param="watermark_findbyid.jsp.cruser">用户</span><span style="color:red;font-weight:bold"><%=obj.getPropertyAsString("cruser")%></span><span style="font-size:10px;" WCMAnt:param="watermark_findbyid.jsp.crtime">创建于</span><span style="color:red;font-weight:bold;font-size:10px;"><%=obj.getCrTime().toString("yy-MM-dd HH:mm")%></span>
	</span>
</div>
<div class="attribute_row readonly">
	<span class="wcm_attr_name" WCMAnt:param="watermark_findbyid.jsp.wmname">水印名称:</span>
	<span class="wcm_attr_value" title="<%=CMyString.filterForHTMLValue(obj.getWMName())%>"><%=CMyString.filterForHTMLValue(obj.getWMName())%></span>
</div>
<div class="attribute_row readonly">
	<span class="wcm_attr_name" WCMAnt:param="watermark_findbyid.jsp.libname">所属图库:</span>
	<span class="wcm_attr_value" title="<%=CMyString.filterForHTMLValue(currWebSite.getDesc())%>"><%=CMyString.filterForHTMLValue(currWebSite.getDesc())%></span>
</div>	
<div class="attribute_row_sep"></div>
<div class="attribute_row">
	<a style="TEXT-DECORATION:none;color:#09549F" href="<%=sFileName%>" target="_blank" class="docpuburl" title='<%=obj.getWMPicture()%>'><%=obj.getWMPicture()%></a>		
</div>
<div class="attribute_row_sep"></div>
<%
}catch(Throwable tx){
	tx.printStackTrace();
	throw new WCMException(LocaleServer.getString("watermark_findbyid.jsp.label.runtimeexception", "watermark_findbyid.jsp运行期异常!"), tx);
}
%>