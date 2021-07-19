<%@ page import="com.trs.cms.content.CMSObj" %>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.publish.WCMFolderPublishConfig" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channels" %>
<%@ page import="com.trs.components.common.publish.persistent.template.Template" %>
<%@ page import="com.trs.components.common.publish.persistent.template.Templates" %>
<%@ page import="com.trs.components.infoview.persistent.InfoViews" %>
<%@ page import="com.trs.components.wcm.publish.WCMFolderPublishConfig" %>
<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="java.util.List" %>
<%@ page import="org.dom4j.DocumentHelper" %>
<%@ page import="org.dom4j.Element" %>
<%@ page import="org.dom4j.Document" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.components.common.job.Schedule" %>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.components.wcm.content.domain.ChannelMgr" %>
<%@ page import="com.trs.cms.content.CMSBaseObjs" %>
<%@ page import="com.trs.components.wcm.content.persistent.BaseChannel" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.components.metadata.center.IMetaViewEmployerMgr" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.DreamFactory" %>

<%
	//构造当前栏目信息
	Channel oChannel = (Channel) processor.excute("channel", "findbyid");
	String sChnlName = oChannel.getName();
	String sChnlDesc = oChannel.getDesc();
	String sCrUser = oChannel.getCrUserName();
	String sLinkUrl = oChannel.getLinkUrl();
	String sContentAddEditPage = oChannel.getContentAddEditPage();
	String sContentListPage = oChannel.getContentListPage();
	String sContentShowPage = oChannel.getContentShowPage();
	String sPublishLimit = CMyString.filterForHTMLValue(oChannel.getAttributeValue("PublishLimit"));
	String sPubStartDate = CMyString.filterForHTMLValue(oChannel.getAttributeValue("PubStartDate"));
	int nDBChnlNameFieldLen = DBManager.getDBManager().getFieldInfo(Channel.DB_TABLE_NAME, "CHNLNAME").getDataLength();
	int nChnlType = oChannel.getType();
	int nOrder = oChannel.getOrder();
	int nStatus = oChannel.getStatus();

	//构造当前栏目父栏目信息
	Channel oParent = oChannel.getParent();
	String sParentName = oParent != null ? CMyString.transDisplay(oParent.getName()) : LocaleServer.getString("channel.label.none","无");
	String sParentDesc = oParent != null ? CMyString.transDisplay(oParent.getDesc()) : LocaleServer.getString("channel.label.none","无");

	//构造当前栏目站点信息
	WebSite oSite = oChannel.getSite();
	String sSiteName = oSite.getName();
	String sSiteDesc = oSite.getDesc();

	//构造是否有扩展字段权限
	CMSObj host = oParent;
	if(oParent == null){
		host = oSite;
	}
	boolean bHasExtendFieldRight = loginUser.isAdministrator() ? true : AuthServer.hasRight(loginUser, host, WCMRightTypes.CHNL_EXTEND);

	//构造当前栏目的发布信息
	Template oOutlineTemplate = null;
	Template oDetailTemplate = null;
	Template oInfoviewPrintTemplate = null;
	Templates oOtherTemplates = new Templates(loginUser);
	int nOutlineTemplateId = 0;
	int nDetailTemplateId = 0;
	int nInfoviewPrintTemplateId = 0;
	String sOtherTemplateIds = "";
	String sOutlineTemplateName = LocaleServer.getString("channel.label.none","无");
	String sDetailTemplateName = LocaleServer.getString("channel.label.none","无");
	String sInfoviewPrintTemplateName = LocaleServer.getString("channel.label.none", "无");
	String sExecTime = "00:00";	
	String sStartTime = "00:00";
	String sEndTime = "00:00";
	String sInterval = "00";
	String sExecTimeHour = "00";
	String sExecTimeMinute = "00";
	String sStartTimeHour = "00";
	String sStartTimeMinute = "00";
	String sEndTimeHour = "00";
	String sEndTimeMinute = "00";
	int nIntervalHour = 0;
	int nIntervalMinute = 30;
	String sIntervalHour = "00";
	String sIntervalMinute = "30";
	String sDataPath = "";
	int nScheduleMode = 0;
	boolean bExistNewsChannel = false;
	boolean bExistPicsChannel = false;
	boolean hasEditRight = true;
	boolean bHiddenDocTitle = false;

	String sAttrForceSchedulePub = "";

	if(!oChannel.isAddMode()){
		hasEditRight = AuthServer.hasRight(loginUser, oChannel, WCMRightTypes.CHNL_EDIT);
		WCMFolderPublishConfig oPublishConfig = null;
		processor.setSelectParameters(new String[]{"objectId"});
		oPublishConfig = (WCMFolderPublishConfig)processor.excute("publish", "getChannelPublishConfig");
		nOutlineTemplateId = oPublishConfig.getDefaultOutlineTemplateId();
		oOutlineTemplate = Template.findById(nOutlineTemplateId);
		sOutlineTemplateName = CMyString.transDisplay(oOutlineTemplate != null ? oOutlineTemplate.getName() : sOutlineTemplateName);
		nDetailTemplateId = oPublishConfig.getDetailTemplateId();
		oDetailTemplate = Template.findById(nDetailTemplateId);
		sDetailTemplateName = CMyString.transDisplay(oDetailTemplate != null ? oDetailTemplate.getName() : sDetailTemplateName);
		nInfoviewPrintTemplateId = oPublishConfig.getInfoviewPrintTemplateId();
		oInfoviewPrintTemplate = Template.findById(nInfoviewPrintTemplateId);
		sInfoviewPrintTemplateName = CMyString.transDisplay(oInfoviewPrintTemplate != null ? oInfoviewPrintTemplate.getName() : sInfoviewPrintTemplateName);
		sOtherTemplateIds = CMyString.showNull(oPublishConfig.getOtherOutlineTemplateIds());
		if(!CMyString.isEmpty(sOtherTemplateIds)){
			oOtherTemplates = Templates.findByIds(loginUser, sOtherTemplateIds);
		}
		sExecTime = CMyString.showNull(oPublishConfig.getExecTime());
		String[] sExec = sExecTime.split(":");
		sExecTimeHour = (sExec.length > 1) ? sExec[0] : "00";
		sExecTimeMinute = (sExec.length > 1) ? sExec[1] : "00";
		sStartTime = CMyString.showNull(oPublishConfig.getStartTime());
		String[] sStart = sStartTime.split(":");
		sStartTimeHour = (sStart.length > 1) ? sStart[0] : "00";
		sStartTimeMinute = (sStart.length > 1) ? sStart[1] : "00";
		sEndTime = CMyString.showNull(oPublishConfig.getEndTime());
		String[] sEnd = sEndTime.split(":");
		sEndTimeHour = (sEnd.length > 1) ? sEnd[0] : "00";
		sEndTimeMinute = (sEnd.length > 1) ? sEnd[1] : "00";
		int nInterval = oPublishConfig.getInterval();
		//sInterval = nInterval != 0 ? String.valueOf(nInterval) : "";
		//String[] sInter = sInterval.split(":");
		nIntervalHour = (int)Math.floor(nInterval / 60);
		sIntervalHour = (nIntervalHour < 10)?"0"+nIntervalHour:String.valueOf(nIntervalHour);
		nIntervalMinute = nInterval % 60;
		nIntervalMinute = (nIntervalHour==0 && nIntervalMinute==0)?30:nIntervalMinute;
		sIntervalMinute = (nIntervalMinute < 10)?"0"+nIntervalMinute:String.valueOf(nIntervalMinute);
		sDataPath = CMyString.transDisplay(oPublishConfig.getDataPath());
		nScheduleMode = oPublishConfig.getScheduleMode();
		sAttrForceSchedulePub = oChannel.getAttributeValue("FORCESCHEDULEPUB");

		//by CC 20120618 获取栏目是否配置了视图
		IMetaViewEmployerMgr m_oMetaViewEmployerMgr = (IMetaViewEmployerMgr) DreamFactory.createObjectById("IMetaViewEmployerMgr");
		MetaView view = null;
		view = m_oMetaViewEmployerMgr.getViewOfEmployer(oChannel);
		if(view != null){
			bHiddenDocTitle = true;
		}
	}else{
        ChannelMgr oChannelMgr = (ChannelMgr) DreamFactory.createObjectById("ChannelMgr");
		CMSBaseObjs oNews = oChannelMgr.getChildren((BaseChannel)host, Channel.TYPE_TOP_NEWS, true, null);
		bExistNewsChannel = oNews != null && oNews.size() > 0;

		CMSBaseObjs oPics = oChannelMgr.getChildren((BaseChannel)host, Channel.TYPE_TOP_PICS, true, null);
		bExistPicsChannel = oPics != null && oPics.size() > 0;
		
	}

	//获取兄弟栏目列表
	processor.reset();
	processor.setAppendParameters(new String[]{
		"pagesize", "-1", 
		"selectfields", "ChannelId,ChnlDesc,ChnlOrder",
		"excludetoporpic", "true"
	});
	processor.setEscapeParameters(new String[]{"channelid", "siblingchannelid", "parentid", "channelid"});
	processor.setSelectParameters(new String[]{"siblingchannelid", "parentid", "channelid", "siteid"}, true);
	Channels oSiblings = (Channels) processor.excute("channel", "query");
	
	//获取系统导入的表单
	processor.reset();
	processor.setAppendParameters(new String[]{
		"ChannelId", ""+oChannel.getId(),
	});

	Object result = processor.excute("infoview", "queryChannelUsableInfoviews");
	List infoviews = null;
	if(result!=null){
		Document document = DocumentHelper.parseText(result.toString());
		Element root = document.getRootElement();
		infoviews = root.elements("Infoview");
	}

	//定时撤稿设置
	Schedule unpubSchedule = null;
	if(oChannel.getId() > 0){
		processor.reset();
		processor.setAppendParameters(new String[]{
			"SenderId", String.valueOf(oChannel.getId()),
			"SenderType", String.valueOf(Channel.OBJ_TYPE)
		});
		unpubSchedule =(Schedule) processor.excute("publish", "getUnpubSchedule");
	}

%>