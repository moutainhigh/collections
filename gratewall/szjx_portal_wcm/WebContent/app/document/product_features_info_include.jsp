<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.common.publish.persistent.template.Template" %>
<%@ page import="com.trs.components.common.publish.persistent.template.Templates" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<!------- WCM IMPORTS END ------------>

<%@include file="../include/public_processor.jsp"%>
<%
	out.clear();
%>

//从系统配置中读取特性库的视图id（PRODUCT_FEATURES_VIEWID）
var $GViewId = <%= CMyString.showEmpty(processor.getParam("featureViewIds"), ConfigServer.getServer().getSysConfigValue("PRODUCT_FEATURES_VIEWID", "181"))%>;

//构造需要用到的模板信息
var $GTemplates = [

<%
try{
	//优先获取链接地址上传入的featureChnlIds，如果没有，则从系统配置中读取栏目的配置id（PRODUCT_FEATURES_CHANNELID）
	String sChannelId = CMyString.showEmpty(processor.getParam("featureChnlIds"), ConfigServer.getServer().getSysConfigValue("PRODUCT_FEATURES_CHANNELID", "1031"));

	HashMap parameters = new HashMap();
	parameters.put("HostType", "101");
	parameters.put("HostId", sChannelId);


	Templates oTemplates = (Templates)processor.excute("wcm61_template", "query", parameters);


	for (int i = 0, nSize = oTemplates.size(); i < nSize; i++) {
		Template template = (Template) oTemplates.getAt(i);
		if (template == null){
			continue;
		}
%>
		{
			Id : '<%=template.getId()%>', 
			Name : '<%=CMyString.filterForJs(template.getName())%>', 
			Desc : '<%=CMyString.filterForJs(CMyString.showEmpty(template.getDesc(), template.getName()))%>'	
		}<%=(i < nSize -1) ? "," : ""%>
<%
	}
}catch(Exception e){
	e.printStackTrace();
}
%>
];