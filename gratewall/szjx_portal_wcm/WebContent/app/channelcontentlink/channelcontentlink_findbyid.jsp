<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.wcm.content.persistent.ChannelContentLink" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.common.WCMRightTypes"%>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel"%>
<%@ page import="com.trs.cms.content.CMSObj"%>

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
	if(!(result instanceof ChannelContentLink)){
		throw new WCMException(CMyString.format(LocaleServer.getString("channelcontentlink_findbyid.jsp.servicecertain","服务(com.trs.ajaxservice.ContentLinkServiceProvider.findbyid)返回的对象类型不为ChannelContentLink，而为{0}，请确认。"),new Object[]{result.getClass()}));
	}
	ChannelContentLink obj = (ChannelContentLink)result;
	
	CMSObj cmsObj = obj.getChannel() != null ? obj.getChannel() : (CMSObj)WebSite.findById(obj.getSiteId());
	boolean bCanEdit = hasRight(loginUser, cmsObj, WCMRightTypes.CONTENT_LINK_EDIT);
	String sEditable = bCanEdit?"editable":"readonly";
%>
<div class="attribute_row <%=sEditable%> main_attr">
	<span class="wcm_attr_value" _fieldName="LINKNAME" _fieldValue="<%=CMyString.filterForHTMLValue(obj.getName())%>" validation="type:'string',required:'true',desc:'热词名称',max_len:30" validation_desc="热词名称" WCMAnt:paramattr="validation_desc:channelcontentlink_findbyid.jsp.content"><%=CMyString.filterForHTMLValue(obj.getName())%></span>
</div>
<div class="attribute_row_sep"></div>
<div class="attribute_row readonly">
	<span class="wcm_attr_name" WCMAnt:param="channelcontentlink_findbyid.jsp.desc">描述:</span>
	<span class="wcm_attr_value" title="<%=CMyString.filterForHTMLValue(obj.getLinkTitle())%>"><%=CMyString.transDisplay(obj.getLinkTitle())%></span>
</div>
<div class="attribute_row">
	<span class="wcm_attr_name" WCMAnt:param="channelcontentlink_findbyid.jsp.link">链接:</span>
</div>
<div class="attribute_row">
	<span><a style="TEXT-DECORATION:none;color:#09549F" href="<%=CMyString.filterForHTMLValue(obj.getLinkUrl())%>" target="_blank" title='<%=CMyString.filterForHTMLValue(obj.getLinkUrl())%>'>&nbsp;&nbsp;<%=controlDisplay(CMyString.transDisplay(obj.getLinkUrl()))%></a></span>
</div>
<div class="attribute_row_sep"></div>		
<%
}catch(Throwable tx){
	tx.printStackTrace();
	throw new WCMException(LocaleServer.getString("channelcontentlink_findbtyid.jsp.runtimeex","channelcontentlink_findbyid.jsp运行期异常!"), tx);
}
%>
<%!
	private String controlDisplay(String param){
		if(param.length() > 14){
			param = param.substring(0,14) + "...";
		}
		return param;
	}
%>