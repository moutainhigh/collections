<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.common.publish.persistent.distribute.PublishDistribution" %>
<%
try{
%>
<%@include file="../include/findbyid_base.jsp"%>
<%@include file="../include/public_server.jsp"%>
<%@include file="../include/convertor_helper.jsp"%>
<%
	Object result = request.getAttribute(FrameworkConstants.ATTR_NAME_RESULT);
	if(!(result instanceof PublishDistribution)){
		throw new WCMException(CMyString.format(LocaleServer.getString("publishdistribution_findbyid.jsp.service", "服务(com.trs.ajaxservice.DistributeServiceProvider.findbyid)返回的对象类型不为PublishDistribution，而为{0}，请确认。"), new Object[]{result.getClass()}));
	}
	PublishDistribution obj = (PublishDistribution)result;
%>
	<div class="attribute_row readonly">
	<span class="wcm_attr_name" WCMAnt:param="publishdistribution_findbyid.jsp.type">分发类型:</span>
	<span class="wcm_attr_value"><%=obj.getTargetType()%></span>
	</div>
	<div class="attribute_row readonly">
	<span class="wcm_attr_name" WCMAnt:param="publishdistribution_findbyid.jsp.dir">存放目录:</span>
	<span class="wcm_attr_value"><%=obj.getDataPath()%></span>
	</div>
	<div class="attribute_row readonly">
	<span class="wcm_attr_name" WCMAnt:param="publishdistribution_findbyid.jsp.servername">服务器名:</span>
	<span class="wcm_attr_value"><%=obj.getTargetServer()%></span>
	</div>
	<div class="attribute_row readonly">
	<span class="wcm_attr_name" WCMAnt:param="publishdistribution_findbyid.jsp.serverport">端口号:</span>
	<span class="wcm_attr_value"><%=(obj.getTargetPort()==0?"":"" + obj.getTargetPort())%></span>
	</div>
	<div class="attribute_row readonly">
	<span class="wcm_attr_name" WCMAnt:param="publishdistribution_findbyid.jsp.username">用户名:</span>
	<span class="wcm_attr_value"><%=obj.getLoginUser()%></span>
	</div>
<%
}catch(Throwable tx){
	tx.printStackTrace();
	throw new WCMException(LocaleServer.getString("publishdistribution_findbyid.jsp.label.runtimeexception", "publishdistribution_findbyid.jsp运行期异常!"), tx);
}
%>