<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.cms.ContextHelper" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.presentation.util.LoginHelper" %>
<%@ page import="com.trs.presentation.util.RequestHelper" %>
<%@ page import="com.trs.presentation.util.ResponseHelper" %>
<%@ page import="com.trs.infra.util.ExceptionNumber" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channels" %> 
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.trs.infra.persistent.WCMFilter"%> 
<%@ page import="com.trs.cms.auth.persistent.User"%> 
<%@ page import="com.trs.components.wcm.publish.WCMFolderPublishConfig"%> 
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishFolder"%>
<%@ page import="com.trs.components.common.publish.persistent.element.PublishElementFactory"%> 
<%@ page import="com.trs.presentation.locale.LocaleServer" %>

<%
	//1.页面状态设定
	ResponseHelper rspsHelper = new ResponseHelper(response);
	rspsHelper.initCurrentPage(request);
	//2.参数获取
	RequestHelper currRequestHelper = new RequestHelper(request, response, application);
	currRequestHelper.doValid();

	//先用admin用户访问
	User loginUser = User.findByName("admin");
	if(loginUser == null){
		loginUser	= User.getSystem();
	}
	ContextHelper.clear();
	ContextHelper.initContext(loginUser);

	LoginHelper currLoginHelper = new LoginHelper(request, application);

	//接收页面参数
	int nFlagId = currRequestHelper.getInt("flag",0);
	int nVipId = currRequestHelper.getInt("vipid",0);
	String sWCMUrl = currRequestHelper.getString("wcmurl");
	//获取访谈对应的channel
	Channels channels = new Channels(null);
	WCMFilter filter = new WCMFilter("WCMCHANNEL",
			"Attribute like '%SysUrl%'", "");
	channels.open(filter);
	Channel docchannel = null;
	boolean zFound = false;
	int nChnlId = 0;
	for (int i = 0, size = channels.size(); i < size && !zFound; i++) {
		docchannel = (Channel) channels.getAt(i);
		if (docchannel != null) {
			zFound = (nVipId + "").equals(docchannel.getAttributeValue("VipId"));
			nChnlId = docchannel.getId();
		}
	}
	if (!zFound) {
		throw new WCMException(LocaleServer.getString("doAction.notExist","访谈对应的栏目不存在!"));
	}
	out.clear();

	WCMFolderPublishConfig chnlConfig = getPublishConfig(docchannel);
	int nOutlineTempId = chnlConfig.getDefaultOutlineTemplateId();
	//执行相关服务
	JSPRequestProcessor processor = new JSPRequestProcessor(request,response);
	String sServiceId="wcm6_publish",sMethodName="preview";
	HashMap parameters = new HashMap();
	parameters.put("ObjectIds", nChnlId + "");
	parameters.put("ObjectType", Channel.OBJ_TYPE + "");

	//判断操作类型，执行相关操作
	switch(nFlagId){
		case 1:
			//设计
			out.println("onOK({ type:1,msg:'" + sWCMUrl + "/app/special/design.jsp?templateId=" + nOutlineTempId + "&HostType=" + Channel.OBJ_TYPE + "&HostId=" + nChnlId + "'})" );
		break;
		case 2:
			//预览
			processor.excute(sServiceId,sMethodName,parameters);
			out.println("onOK({ type:2,msg:'preview'})");
		break;
		case 3:
			//发布
			sMethodName = "fullyPublish";
			processor.excute(sServiceId,sMethodName,parameters);
			out.println("onOK({ type:3,msg:'publish'})");
		break;
	}

%>
<%!
	private WCMFolderPublishConfig getPublishConfig(Channel obj) throws WCMException{
		IPublishFolder publishFolder = (IPublishFolder)PublishElementFactory.makeElementFrom(obj);
		WCMFolderPublishConfig folderConfig = new WCMFolderPublishConfig(publishFolder);
		return folderConfig;
	}
%>