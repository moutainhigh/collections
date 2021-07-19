<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%-- ----- WCM IMPORTS BEGIN ---------- --%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.webframework.FrameworkConstants" %>
<%@ page import="com.trs.webframework.context.MethodContext" %>
<%@ page import="com.trs.components.common.publish.domain.publisher.PublishPathCompass" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishElement" %>
<%@ page import="com.trs.components.common.publish.persistent.element.PublishElementFactory" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.components.common.publish.domain.template.TemplateEmployMgr" %>
<%@ page import="com.trs.components.common.publish.persistent.template.Template" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishFolder" %>
<%@ page import="com.trs.DreamFactory" %>
<%-- ----- WCM IMPORTS END ---------- --%>

<%-- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --%>
<%
	//TODO 权限
	MethodContext context = (MethodContext)request.getAttribute(FrameworkConstants.ATTR_NAME_METHODCONTEXT);
	/*String str = "";
	if(context != null) {
		str += String.valueOf(context.getValue("channelid", 0));
		str += ", ";
		str += String.valueOf(context.getValue("infoviewid", 0));
	}//*/
	int nCachedInfoviewId = context.getValue("cachedinfoviewid", 0);
	if(nCachedInfoviewId == 0) {
		throw new WCMException("传入的参数-暂存ID(cachedinfoviewid)必须是正整数！");
	}
	int nChnlId = context.getValue("channelid", 0);
	if(nChnlId == 0) {
		throw new WCMException("传入的参数-频道ID(channelid)必须是正整数！");
	}
	Channel chnl = Channel.findById(context.getValue("channelid", 0));
	if(chnl == null) {
		throw new WCMException("[ID=" + nChnlId + "]的频道已不存在！");
	}

	IPublishElement content = (IPublishElement) PublishElementFactory
			.makeElementFrom(chnl);
	PublishPathCompass compass = new PublishPathCompass();
	String sUrl = compass.getAbsoluteHttpPath(content);
	if(CMyString.isEmpty(sUrl)) {
		throw new WCMException("[ID=" + nChnlId + "]的频道可能尚未发布或者已被修改为不可发布！");
	}
	//需要将发布出去的文件名也获取到
	IPublishFolder folder = content.getFolder();
	
	TemplateEmployMgr employMgr = (TemplateEmployMgr) DreamFactory
                    .createObjectById("TemplateEmployMgr");
	Template _template = employMgr.getDefaultOutlineTemplate(folder);
	String sFileName = _template.getOutputFileName();
	String sTempExt = _template.getOutputFileExt();
	sUrl += sFileName + "." + sTempExt + "?mode=1&cachedinfoviewid=" + nCachedInfoviewId;

	//site url
	IPublishElement siteFolder = (IPublishElement) PublishElementFactory
			.makeElementFrom(chnl.getSite());
	String sSiteUrl = compass.getAbsoluteHttpPath(siteFolder);
	String sInfogateUrl = ConfigServer.getServer().getSysConfigValue(
                "INFOGATE_URL", "http://localhost:8080/infogate/");
	sInfogateUrl = CMyString.setStrEndWith(sInfogateUrl, '/');
	sInfogateUrl+="crossdomain.html";
%>
<form id="crossdoamin-form" method="get" action="<%=sInfogateUrl%>" style="display:none;" target="crossdoamin-iframe">
	<input type='hidden' name="siteurl" id="siteurl" value='<%=sSiteUrl%>'/>
</form>
<iframe src="./blank.html" name="crossdoamin-iframe" style="display:none;"></iframe>
<script language="javascript">
<!--
	document.getElementById('crossdoamin-form').submit();
//-->
</script>
<iframe src="<%=sUrl%>" style="width:100%;height:100%;" frameborder="0" id="main"></iframe>