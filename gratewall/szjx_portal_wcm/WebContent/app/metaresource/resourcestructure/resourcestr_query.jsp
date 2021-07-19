<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"  errorPage="../../include/error_for_dialog.jsp"%>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.components.metadata.definition.MetaViews" %>
<%
try{
%>
<%@include file="../../include/list_base.jsp"%>
<%@include file="../../include/public_server.jsp"%>
<%@include file="../../include/convertor_helper.jsp"%>
<%
	MetaViews results = (MetaViews)request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	for(int i=0,nNum = results.size(); i<nNum; i++) {
		MetaView oMetaView = (MetaView)results.getAt(i);
		if(oMetaView==null)continue;
		int nMetaViewId = oMetaView.getId();
		String sViewName = oMetaView.getName();
		String sShowName = oMetaView.getDesc();
%>
		<div class="thumb" itemid="<%=nMetaViewId%>" id="thumb_<%=nMetaViewId%>">
			<div class="resource_pic"></div>
			<div class="resource_desc">
				<span class="item_ckx"><input type="checkbox" name="" value="<%=nMetaViewId%>" id="cbx_<%=nMetaViewId%>" /></span>
				<span class="resource_title"><%=CMyString.transDisplay(sViewName)%></span>
				<span class="add_resource"></span>
			</div>
		</div>
<%
	}
%>
<%
}catch(Throwable tx){
	tx.printStackTrace();
	throw new WCMException("resource_query.jsp运行期异常!", tx);
}
%>