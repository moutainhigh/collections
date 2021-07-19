<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSites" %>
<%@ page import="com.trs.components.common.publish.persistent.template.Template" %>
<%@ page import="com.trs.components.common.publish.persistent.template.Templates" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.components.wcm.resource.Statuses" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishFolder" %>
<%@ page import="com.trs.components.wcm.publish.WCMFolderPublishConfig" %>
<%@ page import="com.trs.components.common.publish.persistent.element.PublishElementFactory" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<%@ page import="com.trs.ajaxservice.WebSiteHelper" %>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.persistent.RightValue" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="java.lang.Double"%>
<%@ page import="com.trs.DreamFactory" %> 
<%@ page import="com.trs.cms.auth.domain.IObjectMemberMgr" %>

<%
	//构造当前栏目信息
	int nObjectId = processor.getParam("objectid", 0);
	WebSite oWebSite = (WebSite) processor.excute("website", "findbyid");
	String sSiteName = oWebSite.getName();
	String sSiteDesc = oWebSite.getDesc();
	String sCrUser = CMyString.transDisplay(oWebSite.getCrUserName());
	int nDBChnlNameFieldLen = DBManager.getDBManager().getFieldInfo(Channel.DB_TABLE_NAME, "CHNLNAME").getDataLength();
	int nSiteType = processor.getParam("sitetype", 0);
	int nOrder = oWebSite.getOrder();
	int nStatus = oWebSite.getStatus();
	String sRightValue = getRightValue(oWebSite, loginUser).toString();

	String sPublishLimit = CMyString.filterForHTMLValue(oWebSite.getAttributeValue("PublishLimit"));
	String sPubStartDate = CMyString.filterForHTMLValue(oWebSite.getAttributeValue("PubStartDate"));

	//构造当前栏目的发布信息
	Template oOutlineTemplate = null;
	Template oDetailTemplate = null;
	Templates oOtherTemplates = new Templates(loginUser);
	int nOutlineTemplateId = 0;
	int nDetailTemplateId = 0;
	String sOtherTemplateIds = "";
	String sOutlineTemplateName = LocaleServer.getString("website_addedit_init.label.none", "无");
	String sDetailTemplateName = LocaleServer.getString("website_addedit_init.label.none", "无");
	String sExecTime = "00:00";	
	String sStartTime = "00:00";
	String sEndTime = "00:00";
	String sInterval = "00";
	String sDataPath = "";
	String sRootDomain = "";
	String sStatusesCanDoPub = "";
	String sExecTimeHour = "00";
	String sExecTimeMinute = "00";

	String sStartTimeHour = "00";
	String sStartTimeMinute = "00";
	String sEndTimeHour = "00";
	String sEndTimeMinute = "00";
	int nIntervalHour = 0;
	int nIntervalMinute = 30;
	String sIntervalHour = "00";
	String sIntervalMinute = "00";
	int nSiteLanguage = 8;
	int nStatusIdAfterModify = 0;
	int nScheduleMode = 0;
	boolean hasEditRight = true;
	

	if(!oWebSite.isAddMode()){
		hasEditRight = AuthServer.hasRight(loginUser, oWebSite, WCMRightTypes.SITE_EDIT);
		WCMFolderPublishConfig oPublishConfig = null;
		processor.setSelectParameters(new String[]{"objectId"});
		oPublishConfig = (WCMFolderPublishConfig)processor.excute("publish", "getSitePublishConfig");
		nOutlineTemplateId = oPublishConfig.getDefaultOutlineTemplateId();
		oOutlineTemplate = Template.findById(nOutlineTemplateId);
		sOutlineTemplateName =  CMyString.transDisplay(oOutlineTemplate != null ? oOutlineTemplate.getName() : sOutlineTemplateName);
		nDetailTemplateId = oPublishConfig.getDetailTemplateId();
		oDetailTemplate = Template.findById(nDetailTemplateId);
		sDetailTemplateName = CMyString.transDisplay(oDetailTemplate != null ? oDetailTemplate.getName() : sDetailTemplateName);
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
		//System.out.println("nInterval::::::"+nInterval);
		//sInterval = nInterval != 0 ? String.valueOf(nInterval) : "";
		//System.out.println("sInterval::::::"+sInterval);
		//String[] sInter = sInterval.split(":");
		nIntervalHour = (int)Math.floor(nInterval / 60);
		sIntervalHour = (nIntervalHour == 0)?"00":String.valueOf(nIntervalHour);
		sIntervalHour = (nIntervalHour < 10)?"0" + String.valueOf(nIntervalHour):sIntervalHour;
		nIntervalMinute = nInterval % 60;
		//nIntervalMinute = (nIntervalMinute==0)?30:nIntervalMinute;
		/*当小时和分钟都为0的时候，抛开客户端的限制，考虑到数据库中的数据，将时间间隔设置为30*/
		if(nIntervalHour == 0 && nIntervalMinute == 0){
			nIntervalMinute = 30;
		}
		sIntervalMinute = (nIntervalMinute == 0)?"00":String.valueOf(nIntervalMinute);
		sIntervalMinute = (nIntervalMinute < 10)?"0" + String.valueOf(nIntervalMinute):sIntervalMinute;
		sDataPath = oPublishConfig.getDataPath();
		sRootDomain = oPublishConfig.getRootDomain();
		nSiteLanguage = oPublishConfig.getSiteLanguage();
		sStatusesCanDoPub = oPublishConfig.getStatusesCanDoPub();
		nStatusIdAfterModify = oPublishConfig.getStatusIdAfterModify();
		nScheduleMode = oPublishConfig.getScheduleMode();
	}

	//获取兄弟站点列表
	processor.reset();
	processor.setAppendParameters(new String[]{
		"pagesize", "-1", 
		"selectfields", "sitedesc,siteorder",
		"fieldstohtml",  "sitedesc",
		"forindividual", "false"
	});
	processor.setSelectParameters(new String[]{"sitetype"}, true);
	WebSites oSiblings = (WebSites) processor.excute("website", "query");

	//获取可发布文档状态列表
	processor.reset();
	processor.setAppendParameters(new String[]{
		"pagesize", "-1", 
		"selectfields", "statusid,sdisp"
	});
	processor.setSelectParameters(new String[]{}, true);
	Statuses publishStatuses = (Statuses) processor.excute("status", "queryPublishStatuses");

	//获取已发文档编辑后状态列表
	processor.reset();
	processor.setAppendParameters(new String[]{
		"pagesize", "-1", 
		"selectfields", "statusid,sdisp"
	});
	processor.setSelectParameters(new String[]{}, true);
	Statuses documentStatuses = (Statuses) processor.excute("status", "queryDocumentStatuses");
%>
<%!
    private RightValue getRightValue(WebSite site, User loginUser) throws WCMException {
		try {
			if (loginUser.isAdministrator()) {
				return RightValue.getAdministratorRightValue();
			}
			IObjectMemberMgr oObjectMemberMgr = (IObjectMemberMgr) DreamFactory.createObjectById("IObjectMemberMgr");
			RightValue oRightValue = new RightValue();
			if(oObjectMemberMgr.canOperate(loginUser, site.getWCMType(), site.getId())){
				oRightValue = AuthServer.getRightValue(site, loginUser);
			}
			return oRightValue;
		} catch (Exception e) {
			throw new WCMException(CMyString.format(LocaleServer.getString("website_add_edit_init.jsp.fail2build_right_info", "构造[{0}]权限信息失败!"), new Object[]{site}),e);
		}
	}
%>