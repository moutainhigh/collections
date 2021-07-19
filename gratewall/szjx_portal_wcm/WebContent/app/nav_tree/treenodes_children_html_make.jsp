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
<%@ page import="com.trs.cms.content.WCMSystemObject" %>
<%@ page import="com.trs.components.wcm.content.persistent.BaseChannel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSites" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.persistent.BaseObjs" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.DebugTimer" %>
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

	String sNodeIds	= currRequestHelper.getString("NodeIds");	
	

//5.权限校验

//6.业务代码
	out.clear();
	writeTreeNodesChildrenHTML(loginUser, sNodeIds, out, currRequestHelper);

//7.结束
	//out.clear();
%>

<%
	aTimer.stop();
	if(IS_DEBUG)System.out.println(CMyString.format(LocaleServer.getString("group_tree.jsp.createtime", "构造用户[{0}]用户组树所用时间为[{1}]毫秒！"), new Object[]{loginUser.getName(),String.valueOf(aTimer.getTime())}));%>
<%!
	public void writeTreeNodesChildrenHTML(User _loginUser, String _sNodeIds,
            JspWriter out, RequestHelper currRequestHelper) throws Exception {
        Channel currChannel = null;
        boolean bFromSelect = currRequestHelper.getBoolean("FromSelect", false);
        if (bFromSelect) {
            int nChannelId = currRequestHelper.getInt("CurrChannelId", 0);
            currChannel = Channel.findById(nChannelId);
            if (nChannelId > 0 && currChannel == null)
                throw new WCMException(CMyString.format(LocaleServer.getString("treenodes_children_html_make.jsp.channel_notfound", "指定栏目[0]不存在！"), new int[]{currRequestHelper.getInt("CurrChannelId", 0)}));
        }

        String[] pNodeIds = CMyString.split(_sNodeIds, ",");
        int[] pObjectTypes = extractNodeInfo(pNodeIds)[0];
        int[] pObjectIds = extractNodeInfo(pNodeIds)[1];

        writeTreeNodeChildrenHTML(_loginUser, pObjectTypes, pObjectIds, out, 0,
                currChannel, currRequestHelper);
    }

    private int[][] extractNodeInfo(String[] _pNodeIds) throws Exception {
        int[] pObjectTypes = new int[_pNodeIds.length];
        int[] pObjectIds = new int[_pNodeIds.length];
        for (int i = 0; i < _pNodeIds.length; i++) {
            int nPos = _pNodeIds[i].indexOf('_');
            if (nPos <= 0) {
                throw new WCMException(LocaleServer.getString("treenodes_children_html_make.jsp.label.data_not_agreed", "和约定的数据不一致！"));
            }

            pObjectIds[i] = Integer.parseInt(_pNodeIds[i].substring(nPos + 1));

            String sNodeType = _pNodeIds[i].substring(0, nPos);
            if (sNodeType.equalsIgnoreCase("r")) {
                pObjectTypes[i] = WCMSystemObject.OBJ_TYPE;
            } else if (sNodeType.equalsIgnoreCase("s")) {
                pObjectTypes[i] = WebSite.OBJ_TYPE;
            } else if (sNodeType.equalsIgnoreCase("c")) {
                pObjectTypes[i] = Channel.OBJ_TYPE;
            } else {
                throw new WCMException(CMyString.format(LocaleServer.getString("treenodes_children_html_make.jsp.can_not_recognise", "不能识别的类型[{0}]！"), new Object[]{sNodeType}));
            }
        }

        return new int[][] { pObjectTypes, pObjectIds };

    }

    private void writeTreeNodeChildrenHTML(User _loginUser,
            int[] _pObjectTypes, int[] _pObjectIds, JspWriter out,
            int _nParentLevel, Channel _currChannel,
            RequestHelper currRequestHelper) throws Exception {
        if (_nParentLevel >= _pObjectTypes.length) {
            return;
        }

        switch (_pObjectTypes[_nParentLevel]) {
        case WCMSystemObject.OBJ_TYPE:
            writeSitesHTML(_loginUser, _pObjectTypes, _pObjectIds, out,
                    _nParentLevel, _currChannel, currRequestHelper);
            break;
        case WebSite.OBJ_TYPE:
            writeChannelsHTML(_loginUser, true, _pObjectTypes, _pObjectIds,
                    _nParentLevel, out, _currChannel, currRequestHelper);
            break;
        case Channel.OBJ_TYPE:
            writeChannelsHTML(_loginUser, false, _pObjectTypes, _pObjectIds,
                    _nParentLevel, out, _currChannel, currRequestHelper);
            break;
        default:
            break;
        }
    }

    private void writeSitesHTML(User loginUser, int[] _pObjectTypes,
            int[] _pObjectIds, JspWriter out, int _nLevel,
            Channel _currChannel, RequestHelper currRequestHelper)
            throws Exception {
        WebSites sites = getWebSites(loginUser, _pObjectIds[_nLevel],
                _currChannel, currRequestHelper);
        writeHTMLFromObjects(loginUser, out, sites, _pObjectTypes, _pObjectIds,
                _nLevel + 1, _currChannel, currRequestHelper);
    }

    private void writeChannelsHTML(User _loginUser, boolean _bParentIsSite,
            int[] _pObjectTypes, int[] _pObjectIds, int _nLevel, JspWriter out,
            Channel _currChannel, RequestHelper currRequestHelper)
            throws Exception {
        BaseChannel host = null;
        if (_bParentIsSite) {
            host = WebSite.findById(_pObjectIds[_nLevel]);
        } else {
            host = Channel.findById(_pObjectIds[_nLevel]);
        }

        IChannelService channelService = (IChannelService) DreamFactory
                .createObjectById("IChannelService");

        List channels = channelService.getChildren(host);
        writeHTMLFromObjects(_loginUser, out, channels, _pObjectTypes,
                _pObjectIds, _nLevel + 1, _currChannel, currRequestHelper);
    }

    private WebSites getWebSites(User loginUser, int _nSiteType,
            Channel _currChannel, RequestHelper currRequestHelper)
            throws WCMException {
        String sWhere = "Status>=0 and SiteType=?";
        WCMFilter filter = new WCMFilter("", sWhere, "SiteOrder Desc");
        filter.addSearchValues(_nSiteType);

        // 单个站点还是多个站点
        boolean bMultiSites = currRequestHelper.getBoolean("MultiSites", false);
        if (_currChannel != null && !bMultiSites) {
            sWhere += " and SiteId=?";
            filter.setWhere(sWhere);
            filter.addSearchValues(_currChannel.getSiteId());
        }

        WebSites sites = WebSites.openWCMObjs(loginUser, filter);
        if (loginUser.isAdministrator())
            return sites;

        // 5.权限过滤
        int nRightIndex = WCMRightTypes.OBJ_ACCESS;
        for (int i = (sites.size() - 1); i >= 0; i--) {
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
     * @throws IOException
     * @throws WCMException
     */
    private void writeHTMLFromObjects(User loginUser, JspWriter out,
            BaseObjs objects, int[] _pObjectTypes, int[] _pObjectIds,
            int _nCurrLevel, Channel _currChannel,
            RequestHelper currRequestHelper) throws Exception {
        String sTabContent = makeTabContent(_nCurrLevel);
        for (int i = 0, nSize = objects.size(); i < nSize; i++) {
            BaseChannel host = (BaseChannel) objects.getAt(i);
            if (host == null)
                continue;

            writeBaseChannelHTML(loginUser, out, host, sTabContent,
                    _pObjectTypes, _pObjectIds, _nCurrLevel, _currChannel,
                    currRequestHelper);
        }
    }

   
    /**
     * @param loginUser
     * @param out
     * @param host
     * @return
     * @throws IOException
     * @throws WCMException
     */
    private void writeBaseChannelHTML(User loginUser, JspWriter out,
            BaseChannel host, String sTabContent, int[] _pObjectTypes,
            int[] _pObjectIds, int _nCurrLevel, Channel _currChannel,
            RequestHelper currRequestHelper) throws Exception {
        String sDivId = (host.isSite() ? "s" : "c") + "_" + host.getId();
        out.println(sTabContent
                + "<div title=\""
                + CMyString.filterForHTMLValue(host.getDispDesc())
                + "\" classPre=\""
                + getClassPre(host)
                + "\""// 
                + " id=\""
                + sDivId
                + "\"" //
                + getExtraHTMLAttribute(loginUser, host, _currChannel,
                        currRequestHelper) //
                + "><a href=\"#\" " + getExtraAttrOfA(host) + ">"
                + CMyString.transDisplay(host.getDispDesc()) + "</a></div>");

        List lChildren = host.getChildren(loginUser);
        if (lChildren != null && !lChildren.isEmpty()) {
            if (_nCurrLevel < _pObjectIds.length
                    && _pObjectIds[_nCurrLevel] == host.getId()) {
                out.println(sTabContent + "<ul>");
                writeHTMLFromObjects(loginUser, out, lChildren, _pObjectTypes,
                        _pObjectIds, _nCurrLevel + 1, _currChannel,
                        currRequestHelper);
                out.println(sTabContent + "</ul>");
            } else {
                out.println(sTabContent + "<ul></ul>");
            }
        }
    }
    
    /**
     * @param _siteOrChannel
     * @return
     */
    private String getClassPre(BaseChannel _siteOrChannel) {
        if (_siteOrChannel.isSite())
            return "site";
        Channel channel = (Channel) _siteOrChannel;
        if (channel.isNormType())
            return "channel";
        return "channel" + channel.getType();
    }

    /**
     * @param loginUser
     * @param out
     * @param objects
     * @throws IOException
     * @throws WCMException
     */
    private void writeHTMLFromObjects(User loginUser, JspWriter out,
            List objects, int[] _pObjectTypes, int[] _pObjectIds,
            int _nCurrLevel, Channel _currChannel,
            RequestHelper currRequestHelper) throws Exception {
        String sTabContent = makeTabContent(_nCurrLevel);
        for (int i = 0, nSize = objects.size(); i < nSize; i++) {
            BaseChannel host = (BaseChannel) objects.get(i);
            if (host == null)
                continue;

            writeBaseChannelHTML(loginUser, out, host, sTabContent,
                    _pObjectTypes, _pObjectIds, _nCurrLevel, _currChannel,
                    currRequestHelper);
        }
    }

    private String makeTabContent(int _nTabLevel) {
        StringBuffer sbResult = new StringBuffer(_nTabLevel & 4);
        for (int i = 0; i < _nTabLevel; i++) {
            sbResult.append("   ");
        }

        return sbResult.toString();
    }

    private String getExtraHTMLAttribute(User loginUser, BaseChannel host,
            Channel _currChannel, RequestHelper currRequestHelper)
            throws WCMException {
        if (!currRequestHelper.getBoolean("FromSelect", false)) {
            String sAttr = "";
            if (!host.isSite()) {
                Channel channel = (Channel) host;
                if (!channel.isNormType()) {
                    sAttr = " ChannelType=" + channel.getType() + " ";
                }
                if (channel.isVirtual()) {
                    sAttr += " IsV=1 ";
                }
            }
            if (loginUser.isAdministrator()
                    || loginUser.getName().equalsIgnoreCase(
                            host.getCrUserName())) {
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


    private String getExtraAttrOfA(BaseChannel host) {
        String sTitle = CMyString.format(LocaleServer.getString("nav_tree_select_individual.jsp.stitle", "编号：{0}\n创建者：{1}\n创建时间：{2}"), new Object[]{String.valueOf(host.getId()),CMyString.filterForHTMLValue(host.getName()),CMyString.filterForHTMLValue(host.getCrUserName()),host.getCrTime()});
        return " title=\"" + sTitle + "\" ";
    }

    private boolean hasRight(User loginUser, Channel host,
            RequestHelper currRequestHelper, Channel _currChannel)
            throws WCMException {
        boolean bExcludeSelf = currRequestHelper.getBoolean("ExcludeSelf",
                false);
        if (_currChannel != null && bExcludeSelf
                && host.getId() == _currChannel.getId())
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
        if (_currChannel != null && bExcludeParent
                && host.getId() == _currChannel.getParentId())
            return false;

        if (loginUser.isAdministrator())
            return true;

        int nRightIndex = currRequestHelper.getInt("RightIndex",
                WCMRightTypes.OBJ_ACCESS);
        return AuthServer.hasRight(loginUser, host, nRightIndex);

    }
%>