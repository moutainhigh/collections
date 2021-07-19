<%
/** Title:			channels_get.jsp
 *  Description:
 *		标准WCM5.2 页面，用于“获取栏目列表”。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WCM
 *  Created:		2004-12-11 16:12
 *  Vesion:			1.0
 *  Last EditTime:	2004-12-11/2004-12-11
 *	Update Logs:
 *		CH@2004-12-11 created the file 
 *
 *  Parameters:
 *		see channels_get.jsp
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.persistent.RightValue" %>
<%@ page import="com.trs.components.wcm.content.persistent.BaseChannel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSites" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.persistent.BaseObjs" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.DebugTimer" %>
<%@ page import="com.trs.presentation.util.RequestHelper" %>
<%@ page import="com.trs.service.IChannelService" %>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%
    DebugTimer aTimer = new DebugTimer();
	aTimer.start();
%>

<%!boolean IS_DEBUG = false;%>
<%
//4.初始化（获取数据）
	String sParentType	= currRequestHelper.getString("ParentType");
	int	nParentId		= currRequestHelper.getInt("ParentId", 0);
	

//5.权限校验

//6.业务代码
	out.clear();
	writeTreeHTML(loginUser, sParentType, nParentId, out, currRequestHelper);

//7.结束
	//out.clear();
%>

<%
	aTimer.stop();
	if(IS_DEBUG)System.out.println(CMyString.format(LocaleServer.getString("group_tree.jsp.createtime", "构造用户[{0}]用户组树所用时间为[{1}]毫秒！"), new Object[]{loginUser.getName(),String.valueOf(aTimer.getTime())}));
%>
<%!
     public void writeTreeHTML(User _loginUser, String _sParentType,
            int _nParentId, JspWriter out, RequestHelper currRequestHelper)
            throws Exception {
        ContextHelper.initContext(_loginUser);

        Channel currChannel = null;
        boolean bFromSelect = currRequestHelper.getBoolean("FromSelect", false);
        if (bFromSelect) {
            int nChannelId = currRequestHelper.getInt(
                    "CurrChannelId", 0);
            currChannel = Channel.findById(nChannelId);
            if (nChannelId>0 && currChannel == null)
                throw new WCMException(LocaleServer.getString("treenode_html_make.jsp.label.channel_not_exist","指定栏目[{0}]不存在！"));
        }

        if (_sParentType.equalsIgnoreCase("r")) {
            writeSitesHTML(_loginUser, _nParentId, out, currRequestHelper,
                    currChannel);
            return;
        } else if (_sParentType.equalsIgnoreCase("s")) {
            writeChannelsHTML(_loginUser, _nParentId, 0, out,
                    currRequestHelper, currChannel);
            return;
        } else if (_sParentType.equalsIgnoreCase("c")) {
            writeChannelsHTML(_loginUser, 0, _nParentId, out,
                    currRequestHelper, currChannel);
            return;
        }
    }

    private void writeSitesHTML(User loginUser, int _nSiteType, JspWriter out,
            RequestHelper currRequestHelper, Channel _currChannel)
            throws Exception {
        WebSites sites = getWebSites(loginUser, _nSiteType, currRequestHelper,
                _currChannel);
        writeHTMLFromObjects(loginUser, out, sites, currRequestHelper,
                _currChannel);
    }

    private void writeChannelsHTML(User _loginUser, int _nParentSiteId,
            int _nParentChannelId, JspWriter out,
            RequestHelper currRequestHelper, Channel _currChannel)
            throws Exception {
        BaseChannel host = null;
        if (_nParentSiteId > 0) {
            host = WebSite.findById(_nParentSiteId);
        } else if (_nParentChannelId > 0) {
            host = Channel.findById(_nParentChannelId);
        }

        IChannelService channelService = (IChannelService) DreamFactory
                .createObjectById("IChannelService");

        List channels = channelService.getChildren(host);
        writeHTMLFromObjects(_loginUser, out, channels, currRequestHelper,
                _currChannel);
    }

    private WebSites getWebSites(User loginUser, int _nSiteType,
            RequestHelper currRequestHelper, Channel _currChannel)
            throws WCMException {
        String sWhere = "Status>=0 and SiteType=?";
        boolean bMultiSites = currRequestHelper.getBoolean("MultiSites", false);        
        if (_currChannel != null && !bMultiSites) {
            sWhere += " and SiteId=?";
        }
        WCMFilter filter = new WCMFilter("", sWhere, "SiteOrder Desc");
        filter.addSearchValues(0, _nSiteType);
        if (_currChannel != null && !bMultiSites) {
            filter.addSearchValues(1, _currChannel.getSiteId());
        }


        WebSites sites = WebSites.openWCMObjs(loginUser, filter);
        if (loginUser.isAdministrator())
            return sites;

        // 5.权限过滤
        int nRightIndex = WCMRightTypes.OBJ_ACCESS;
        for (int i = (sites.size()-1); i >= 0; i--) {
            WebSite site = (WebSite) sites.getAt(i);
            if (site == null)
                continue;

            if (AuthServer.hasRight(loginUser, site, nRightIndex))
                continue;

            sites.removeAt(i, false);
        }
        return sites;
    }

    /**
     * @param loginUser
     * @param out
     * @param objects
     * @throws Exception
     * @throws WCMException
     */
    private void writeHTMLFromObjects(User loginUser, JspWriter out,
            BaseObjs objects, RequestHelper currRequestHelper,
            Channel _currChannel) throws Exception {
        for (int i = 0, nSize = objects.size(); i < nSize; i++) {
            BaseChannel host = (BaseChannel) objects.getAt(i);
            if (host == null)
                continue;

            writeBaseChannelHTML(loginUser, out, host, currRequestHelper,
                    _currChannel);
        }
    }

    /**
     * @param loginUser
     * @param out
     * @param host
     * @return
     * @throws Exception
     */
    private void writeBaseChannelHTML(User loginUser, JspWriter out,
            BaseChannel host, RequestHelper currRequestHelper,
            Channel _currChannel) throws Exception {
        String sDivId = (host.isSite() ? "s" : "c") + "_" + host.getId();
        out.println("<div title=\""
                + CMyString.filterForHTMLValue(host.getDispDesc())
                + "\" classPre=\""
                + getClassPre(host)
                + "\""//
                + getExtraHTMLAttribute(loginUser, host, currRequestHelper,
                        _currChannel) + " id=\"" + sDivId + "\">"
                + getNodeInnerHTML(currRequestHelper, host, sDivId) + "</div>");

        boolean bExcludeChildren = currRequestHelper.getBoolean(
                "ExcludeChildren", false);
        if (_currChannel != null && bExcludeChildren && !host.isSite()
                && _currChannel.getId() == host.getId())
            return;

        List lChildren = host.getChildren(loginUser);
        if (lChildren != null && !lChildren.isEmpty()) {
            out.println("<ul></ul>");
        }
    }

    /**
     * @param _siteOrChannel
     * @return
     */
    private String getClassPre(BaseChannel _siteOrChannel) {
        if(_siteOrChannel.isSite())
            return "site";
        Channel channel = (Channel)_siteOrChannel;
        if(channel.isNormType())
            return "channel";
        return "channel" + channel.getType();
    }

    private String getNodeInnerHTML(RequestHelper currRequestHelper,
            BaseChannel host, String divId) throws Exception {
        if (currRequestHelper.getBoolean("FromSelect", false)) {
            return "<label For=\"c" + divId + "\" style=\"cursor:hand\">"
                    + CMyString.transDisplay(host.getDispDesc()) + "</label>";
        }
        return "<a href=\"#\" "+getExtraAttrOfA(host)+">" + CMyString.transDisplay(host.getDispDesc())
                + "</a>";
    }

    private String getExtraAttrOfA(BaseChannel host) {
		String sTitle = CMyString.format(LocaleServer.getString("nav_tree_select_individual.jsp.stitle", "编号：{0}\n创建者：{1}\n创建时间：{2}"), new Object[]{String.valueOf(host.getId()),CMyString.filterForHTMLValue(host.getName()),CMyString.filterForHTMLValue(host.getCrUserName()),host.getCrTime()});
        return " title=\"" + sTitle + "\" ";
    }

    private String getExtraHTMLAttribute(User loginUser, BaseChannel host,
            RequestHelper currRequestHelper, Channel _currChannel)
            throws WCMException {
        if (!currRequestHelper.getBoolean("FromSelect", false)) {
            String sAttr = "";
            if (!host.isSite()) {
                Channel channel = (Channel) host;
                if (!channel.isNormType()) {
                    sAttr = " ChannelType=" + channel.getType() + " ";
                }
                if(channel.isVirtual()){
                    sAttr += " IsV=1 ";
                }
            }
            if (loginUser.isAdministrator() || loginUser.getName().equalsIgnoreCase(host.getCrUserName())) {
                sAttr += " RV=" + RightValue.getAdministratorValues() + " ";
            } else {
                sAttr += " RV=" + getRightValueByMember(host, loginUser)
                        + " ";
            }
            return sAttr;
        }

        if (host.isSite())
            return " OnlyNode=\"true\" ";

        if (hasRight(loginUser, (Channel) host, currRequestHelper, _currChannel))
            return "";

        return " SDisabled=\"true\" ";
    }


    private boolean hasRight(User loginUser, Channel host,
            RequestHelper currRequestHelper, Channel _currChannel)
            throws WCMException {
        boolean bExcludeSelf = currRequestHelper.getBoolean("ExcludeSelf",
                false);
        if (_currChannel != null && bExcludeSelf && host.getId() == _currChannel.getId())
            return false;

		String sExcludeChannelIds = currRequestHelper
                .getString("ExcludeChannelIds");
		if (sExcludeChannelIds != null
                && sExcludeChannelIds.length() > 0
                && ("," + sExcludeChannelIds + ",").indexOf("," + host.getId()
                        + ",") >= 0) {
            return false;
        }

		boolean bExcludeVirtual = currRequestHelper.getBoolean(
                "ExcludeVirtual", false);
        if (bExcludeVirtual && host.isVirtual())
            return false;
		
		boolean bIsHostTop = host.getType() == Channel.TYPE_TOP_NEWS || host.getType() == Channel.TYPE_TOP_PICS;
        boolean bExcludeTop = currRequestHelper.getBoolean("ExcludeTop", false);
		if (bExcludeTop && bIsHostTop){//图片或头条
			return false;
		}

		//ge gfc add @ 2007-4-2 11:14 增加排除链接栏目的参数判断
		boolean bIsLink = (host.getType() == Channel.TYPE_LINK);
        boolean bExcludeLink = currRequestHelper.getBoolean("ExcludeLink", false);
		if (bExcludeLink && bIsLink){//链接
			return false;
		}

        boolean bExcludeOnlySearch = currRequestHelper.getBoolean("ExcludeOnlySearch", false);
		if (bExcludeOnlySearch && host.isVirtual() && !bIsHostTop){//仅检索
			return false;
		}

        boolean bExcludeParent = currRequestHelper.getBoolean("ExcludeParent",
                false);
        if (_currChannel != null && bExcludeParent && host.getId() == _currChannel.getParentId())
            return false;

        if (loginUser.isAdministrator())
            return true;

        int nRightIndex = currRequestHelper.getInt("RightIndex",
                WCMRightTypes.OBJ_ACCESS);
        return AuthServer.hasRight(loginUser, host, nRightIndex);

    }

    /**
     * @param loginUser
     * @param out
     * @param objects
     * @throws Exception
     * @throws WCMException
     */
    private void writeHTMLFromObjects(User loginUser, JspWriter out,
            List objects, RequestHelper currRequestHelper, Channel _currChannel)
            throws Exception {
        for (int i = 0, nSize = objects.size(); i < nSize; i++) {
            BaseChannel host = (BaseChannel) objects.get(i);
            if (host == null)
                continue;

            writeBaseChannelHTML(loginUser, out, host, currRequestHelper,
                    _currChannel);
        }
    }
%>