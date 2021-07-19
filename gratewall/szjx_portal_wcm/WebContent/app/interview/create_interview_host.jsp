<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@include file="../include/public_server_nologin.jsp"%>
<%@ page import="com.trs.infra.persistent.WCMFilter" %> 
<%@ page import="com.trs.components.wcm.content.persistent.WebSites" %> 
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %> 
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %> 
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishElement" %> 
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishFolder" %> 
<%@ page import="com.trs.components.wcm.content.persistent.BaseChannel" %> 
<%@ page import="com.trs.components.wcm.resource.Status" %> 
<%@ page import="com.trs.components.common.publish.persistent.element.PublishElementFactory" %> 
<%@ page import="com.trs.components.wcm.publish.WCMFolderPublishConfig" %>
<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@ page import="com.trs.components.common.publish.widget.Master" %>
<%@ page import="com.trs.components.common.publish.widget.Masters" %>
<%@ page import="com.trs.components.common.publish.widget.IWidgetServer" %>
<%@ page import="com.trs.DreamFactory" %>
<%@ page import="com.trs.components.common.publish.persistent.template.Template" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%
	//接收页面参数
	String sSiteId = currRequestHelper.getString("siteId");
	String sId = currRequestHelper.getString("vipId");
	String sName = currRequestHelper.getString("vipName");
	String sUrl = currRequestHelper.getString("url");
	String sWCMUrl = currRequestHelper.getString("wcmurl");
	String sChnlId = currRequestHelper.getString("chnlId");

	class HostCreate4Interview{
		private final String INTERVIEW_SITENAME = LocaleServer.getString("create_interview_host.interview","嘉宾访谈");

		//设置文档细览模板
		private final String INTERVIEW_DETAILNAME = "嘉宾访谈_文档页面";

		private final String INTERVIEW_DATAPATH = "jbft";

		public HostCreate4Interview(){
			super();
		}
		
		public void createVipHost(String sSiteId, String sChnlId, String sVipId, String sVipName,
           String sVisitUrl, String sWCMUrl,RequestHelper currRequestHelper) throws WCMException {
			// 1.构造特定参数
			User user = User.findByName("admin");
			if(user == null){
				user = User.getSystem();
			}
			ContextHelper.initContext(user);

/*by CC 20120410 创建访谈调整为创建时直接选择访谈站点，因此这些步骤在这里就暂时没有作用了，注释掉
			// 2.先检测当前系统是否已创建好站点
			WCMFilter oFilter = new WCMFilter(WebSite.DB_TABLE_NAME, "SiteName=?",
					"");
			oFilter.addSearchValues(0, INTERVIEW_SITENAME);
			WebSites websites = WebSites.openWCMObjs(user, oFilter);
			WebSite currWebSite = null;

			// 3.如果未找到，需创建，获取SiteId，作为新建访谈的父host
			if (websites == null || websites.size() <= 0) {
				currWebSite = createSite(user, sWCMUrl,currRequestHelper);
			} else {
				currWebSite = (WebSite) websites.getAt(0);
			}
*/
			// 获取访谈站点
			int nSiteId = Integer.parseInt(sSiteId);
			int nChnlId = Integer.parseInt(sChnlId);
			WebSite currWebSite = WebSite.findById(nSiteId);

			Channel channel =null;
			try {
				channel = new Channel();
				String sName = getNewChnlName(sVipName, currWebSite);
				channel.setName(sName);
				channel.setDesc(sVipName);
				channel.setType(Channel.TYPE_NORM);
				channel.setParent(nChnlId);
				channel.setSite(currWebSite.getId());
				channel.setAttribute("VipId", sVipId);
				channel.setAttribute("SysUrl", sVisitUrl);
				channel.setAttribute("WCMUrl", sWCMUrl);
				channel.setCanPub(true);
				channel.save(user);
				
				//设置发布属性
				WCMFilter oFilter = new WCMFilter("","MNAME LIKE ?","");
				//oFilter.addSearchValues(0, INTERVIEW_SITENAME);
				oFilter.addSearchValues("%" + INTERVIEW_SITENAME + "%");
				Masters masters = Masters.openWCMObjs(user, oFilter);
				if(masters.size() > 0){
					Master master = (Master)masters.getAt(0);
					setHostRelate(channel,masters);
				}else{
					throw new WCMException(LocaleServer.getString("create_interview_host.notFind","未找到指定的嘉宾访谈母版!"));
				}

			}catch (WCMException e) {
				throw new WCMException(LocaleServer.getString("create_interview_host.createError","创建嘉宾访谈后台栏目结构时发生错误!"),e);
			}
		}
		
		// 创建嘉宾访谈站点，并设置其默认发布等属性
		/**
		*	by CC 20120410 调整为创建访谈时需要选择对应的站点。这样本方法在这里就暂时没有使用到了
		*/
		private WebSite createSite(User _curruser, String _sSiteHttp,RequestHelper currRequestHelper) throws WCMException {
			// 1.获取登录者信息
			User loginUser = ContextHelper.getLoginUser();

			// 2.如果没有就新建，指定基本属性
			WebSite newSite = WebSite.createNewInstance();
			newSite.setName(INTERVIEW_SITENAME);
			newSite.setDesc(INTERVIEW_SITENAME);
			newSite.setParent(0);
			newSite.save();

			// 3.指定发布属性
			IPublishElement publishElement = PublishElementFactory.lookupElement(
					WebSite.OBJ_TYPE, newSite.getId());
			WCMFolderPublishConfig currPublishConfig = new WCMFolderPublishConfig(
					(IPublishFolder) publishElement);
			currPublishConfig.setFolderType(WebSite.OBJ_TYPE, newSite.getId());       
			currPublishConfig.setDataPath(INTERVIEW_DATAPATH);
			
			String special_SiteHttp = _sSiteHttp;
			if (currRequestHelper != null) {
				String sHost = currRequestHelper.getServerName();
				int nPort = currRequestHelper.getServerPort();
				special_SiteHttp = "http://" + sHost + ":" + nPort
                + "/pub/jbft";
			}
			currPublishConfig.setRootDomain(special_SiteHttp);
			currPublishConfig.canEdit(_curruser);
			currPublishConfig.setDefineStatus(true);

			String sPubState = new String(Status.STATUS_ID_NEW + "," + Status.STATUS_ID_EDITED + "," + Status.STATUS_ID_PUBLISHED);
			currPublishConfig.setStatusesCanDoPub(sPubState);
			currPublishConfig.setPageEncoding("UTF-8");
			currPublishConfig.save(loginUser);

			return newSite;
		}

		//创建栏目相关发布属性
		private void setHostRelate(Channel _oSrcChannel, Masters _oMasters) throws WCMException {
			User loginUser = ContextHelper.getLoginUser();
			IPublishElement publishElement = PublishElementFactory.lookupElement(
				Channel.OBJ_TYPE, _oSrcChannel.getId());
			WCMFolderPublishConfig currPublishConfig = new WCMFolderPublishConfig(
					(IPublishFolder) publishElement);
			currPublishConfig.setFolderType(Channel.OBJ_TYPE, _oSrcChannel.getId());       
			currPublishConfig.setDataPath(_oSrcChannel.getId() + "");
			currPublishConfig.canEdit(loginUser);

			IWidgetServer oWidgetServer = (IWidgetServer) DreamFactory
					.createObjectById("IWidgetServer");
			try {
				// 设置概览首页基准和文档页面
				for (int i=0; i<_oMasters.size(); i++) {
					if(((Master)_oMasters.getAt(i)).getMName().equals(INTERVIEW_DETAILNAME)){
						Template indexTemplate = oWidgetServer.createTemplateFromMaster(
								(IPublishFolder) publishElement, (Master)_oMasters.getAt(i),
								Master.MASTER_DETAIL);

						if (indexTemplate != null) {
							indexTemplate.save(loginUser);
							currPublishConfig.setDetailTemplateId(indexTemplate.getId());
						}
					} else {
						Template indexTemplate = oWidgetServer.createTemplateFromMaster(
							(IPublishFolder) publishElement, (Master)_oMasters.getAt(i),
							Master.MASTER_INDEX);

						if (indexTemplate != null) {
							indexTemplate.save(loginUser);
							currPublishConfig.setOutlineTemplateId(indexTemplate.getId() + "");
						}
					}
				}
				currPublishConfig.save(loginUser);
			} catch (Exception e) {
				throw new WCMException(LocaleServer.getString("create_interview_host.setError","设置访谈栏目模板时产生错误！"), e);
			}
		}

		private String getNewChnlName(String _sChnlName, BaseChannel _destSiteOrChnl)
				throws WCMException {
			String sChnlName = _sChnlName;
			// 判断当前名称是否存在，不存在，则直接返回
			if (!existsChnlName(sChnlName, _destSiteOrChnl))
				return sChnlName;

			String sNewChnlName = sChnlName = sChnlName.replaceAll("(_\\d+)+", "");
			
			try{
				int nPostNumber = DBManager.getDBManager().sqlExecuteIntQuery(
						"select max(channelid) from wcmchannel", new int[] {});

				while (existsChnlName(sNewChnlName, _destSiteOrChnl)) {
					sNewChnlName = sChnlName + "_" + nPostNumber;
					nPostNumber++;
				}
			}catch (WCMException e) {
				throw new WCMException(LocaleServer.getString("create_interview_host.getError","获取嘉宾访谈栏目名称时发生错误!"),e);
			}

			return sNewChnlName;
		}

		private boolean existsChnlName(String _sChnlName,
				BaseChannel _destSiteOrChnl) {
			return (Channel.findByName(_destSiteOrChnl.getSiteId(), _sChnlName) != null);
		}
	}

	HostCreate4Interview object = new HostCreate4Interview();
	object.createVipHost(sSiteId, sChnlId, sId,sName,sUrl,sWCMUrl,currRequestHelper);
%>