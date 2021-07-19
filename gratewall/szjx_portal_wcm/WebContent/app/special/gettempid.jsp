<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" errorPage="error.jsp"%>
<%@ page import="com.trs.infra.common.WCMException"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.components.wcm.publish.WCMFolderPublishConfig" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishFolder" %>
<%@ page import="com.trs.components.common.publish.persistent.element.PublishElementFactory" %>
<%@ page import="com.trs.components.common.publish.PublishConstants" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.common.publish.persistent.template.Template" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>

<%@ page import="java.util.*" %>

<%
	//wenyh@2009-04-02 ff提交过来的数据是utf8的,统一编码.是否需要拦截处理所有?
	request.setCharacterEncoding("ISO8859-1");

	//toUpperCase
	Map parameters = toUpperCase(request);

	//优先处理存放路径
	int id = Integer.parseInt((String)parameters.get("CHANNELID"));
	int type = Integer.parseInt((String)parameters.get("TEMPTYPE"));
	Channel obj = Channel.findById(id);
	if(obj == null){
		throw new WCMException(CMyString.format(LocaleServer.getString("gettempid.jsp.channel_notfound", "指定的栏目不存在![ObjectId=[{0}]"), new int[]{id}));
	}
	IPublishFolder publishFolder = (IPublishFolder)PublishElementFactory.makeElementFrom(obj);
	WCMFolderPublishConfig folderConfig = new WCMFolderPublishConfig(publishFolder);
	int nTemplateId = 0;
	if(type == PublishConstants.TEMPLATE_TYPE_OUTLINE){
		nTemplateId = folderConfig.getDefaultOutlineTemplateId();
	}else if(type == PublishConstants.TEMPLATE_TYPE_DETAIL){
		nTemplateId = folderConfig.getDetailTemplateId();
	}	
	Template oTemplate = Template.findById(nTemplateId);
	boolean bVisual = false;
	if(oTemplate != null){
		bVisual = oTemplate.getPropertyAsBoolean("Visual", false);
	}
	if(bVisual){
		out.print(nTemplateId + ",true");
	}else{
		out.print(nTemplateId + ",false");
	}
%>
<%!
	/**
	*将request里面的变量名转成大写
	*/
	private Map toUpperCase(ServletRequest request){
		Map originalParameters = request.getParameterMap();
		Map parameters = new HashMap();
		for(Iterator itr = originalParameters.entrySet().iterator(); itr.hasNext(); ){
			Map.Entry entry = (Map.Entry)itr.next();
			String sKey = (String)entry.getKey();
			parameters.put(sKey.toUpperCase(), CMyString.getStr(request.getParameter(sKey)));
		}	
		return parameters;
	}
%>