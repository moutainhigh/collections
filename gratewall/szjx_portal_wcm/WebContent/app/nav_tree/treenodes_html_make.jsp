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
<%@ page import="com.trs.ajaxservice.WebSiteHelper" %>
<%@ page import="com.trs.components.wcm.content.persistent.BaseChannel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channels" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSites" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.util.DebugTimer" %>
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

	String sChannelIds	= currRequestHelper.getString("ChannelIds");	
	boolean bFromSelect = currRequestHelper.getBoolean("FromSelect", false);
	

//5.权限校验

//6.业务代码
	out.clear();
	writeTreeNodesChildrenHTML(loginUser, bFromSelect?0:-1, sChannelIds, out);

//7.结束
	//out.clear();
%>

<%
	aTimer.stop();
	if(IS_DEBUG)System.out.println(CMyString.format(LocaleServer.getString("group_tree.jsp.createtime", "构造用户[{0}]用户组树所用时间为[{1}]毫秒！"), new Object[]{loginUser.getName(),String.valueOf(aTimer.getTime())}));
%>
<%!
    private class TreeNode {
        private Channels m_srcChannels = null;

        private User m_oLoginUser = null;

        private String m_sChannelIds = null;

        private WebSites m_oSites = null;

        private WebSite m_oRoot = null;

        private int m_nChannelId = 0;

        private TreeNode[] m_pNeedExpandTreeNodes = null;

        private int[] m_pNeedExpandTreeNodeIds = null;

        private int m_nNeedExpandTreeNodeCount = 0;
        
        private int m_nMaxChildCount = 0;
        
        private int m_nSiteType = -1;

        public TreeNode(String _sChannelIds, User _loginUser, int _nSiteType) {
            m_oLoginUser = _loginUser;
            m_sChannelIds = _sChannelIds;
            m_nSiteType=_nSiteType;
            try {
                initRoot();
            } catch (Exception e) {
                throw new RuntimeException(LocaleServer.getString("treenodes_html_make.jsp.label.fail2init_obj", "初始化对象失败！"), e);
            }
        }

        public TreeNode(WebSite _site, Channels _srcChannels) {
            m_oRoot = _site;
            m_srcChannels = _srcChannels;
            m_nMaxChildCount = _srcChannels.size();
            m_pNeedExpandTreeNodeIds = new int[m_nMaxChildCount];
            m_pNeedExpandTreeNodes = new TreeNode[m_nMaxChildCount];
            try {
                initSiteChildren();
            } catch (Exception e) {
                throw new RuntimeException(LocaleServer.getString("treenodes_html_make.jsp.label.fail2init_childsite_obj","初始化站点子对象失败！"), e);
            }
        }

        public TreeNode(int _nChannelId, int _nChildMaxCount) {
            m_nChannelId = _nChannelId;
            m_nMaxChildCount = _nChildMaxCount;
            m_pNeedExpandTreeNodeIds = new int[_nChildMaxCount];
            m_pNeedExpandTreeNodes = new TreeNode[_nChildMaxCount];
        }

        private TreeNode addNeedExpandNode(int _nChannelId) {
            for (int i = 0; i < m_nNeedExpandTreeNodeCount; i++) {
                if (m_pNeedExpandTreeNodeIds[i] == _nChannelId)
                    return m_pNeedExpandTreeNodes[i];
            }
            m_pNeedExpandTreeNodeIds[m_nNeedExpandTreeNodeCount] = _nChannelId;
            m_pNeedExpandTreeNodes[m_nNeedExpandTreeNodeCount] = new TreeNode(
                    _nChannelId, m_nMaxChildCount);
            m_nNeedExpandTreeNodeCount++;
            return m_pNeedExpandTreeNodes[m_nNeedExpandTreeNodeCount-1];
        }

        private int[] extractPathIds(Channel _channel) throws Exception {
            int[] pPathIds = new int[100];
            int nPathCount = 0;
            Channel parent = _channel.getParent();
            while (parent != null) {
                pPathIds[nPathCount] = parent.getId();
                nPathCount++;
                parent = parent.getParent();
            }
            int[] pResult = new int[nPathCount];
            for (int i = nPathCount - 1; i >= 0; i--) {
                pResult[nPathCount - i - 1] = pPathIds[i];
            }
            return pResult;
        }

        private void initSiteChildren() throws Exception {            
            for (int i = m_srcChannels.size()-1; i >=0; i--) {
                Channel channel = (Channel) m_srcChannels.getAt(i);
                if (channel == null)
                    continue;
                if (channel.getSiteId() != m_oRoot.getId()) {
                    continue;
                }

                // 加入路线图
                int[] pPathIds = extractPathIds(channel);
                TreeNode currNode = this;
                for (int j = 0; j < pPathIds.length; j++) {
                    currNode = currNode.addNeedExpandNode(pPathIds[j]);
                }

                // 置为已处理
                m_srcChannels.removeAt(i, false);
            }
        }

        private void initRoot() throws Exception {
            // 计算根节点及过滤同级的节点
            m_srcChannels = Channels.findByIds(m_oLoginUser, m_sChannelIds);
            ArrayList lTempChannelId = new ArrayList(m_srcChannels.size());   
            if(m_nSiteType != -1){
                m_oSites = WebSiteHelper.getWebSitesByType(m_oLoginUser, m_nSiteType, null);
            }else{
                m_oSites = new WebSites(m_oLoginUser, m_srcChannels.size(), 1);
            }
            for (int i = m_srcChannels.size() - 1; i >= 0; i--) {
                Channel channel = (Channel) m_srcChannels.getAt(i);
                if (channel == null)
                    continue;

                if (m_nSiteType == -1 && m_oSites.indexOf(channel.getSiteId()) < 0) {
                    m_oSites.addElement(channel.getSiteId());
                }

                if (channel.getParentId() == 0) {
                    m_srcChannels.removeAt(i, false);
                    continue;
                }

                Object parent = channel.getProperty("PARENTID");
                if (lTempChannelId.contains(parent)) {
                    m_srcChannels.removeAt(i, false);
                    continue;
                }

                lTempChannelId.add(parent);
            }
            lTempChannelId.clear();

            // 计算路径
            m_pNeedExpandTreeNodes = new TreeNode[m_oSites.size()];
            for (int i = 0, nSize = m_oSites.size(); i < nSize; i++) {
                WebSite site = (WebSite) m_oSites.getAt(i);
                if (site == null)
                    continue;

                m_pNeedExpandTreeNodes[i] = new TreeNode(site, m_srcChannels);
            }
        }

        /**
         * @return
         */
        public int getChannelId() {
            return m_nChannelId;
        }

        /**
         * @return
         */
        public WebSites getSites() {
            return m_oSites;
        }

        /**
         * @return
         */
        public TreeNode[] getNeedExpandTreeNodes() {
            return m_pNeedExpandTreeNodes;
        }

        /**
         * @return
         */
        public WebSite getRoot() {
            return m_oRoot;
        }

        public TreeNode getExpandNodeById(int _nChannelId) {
            if (m_nNeedExpandTreeNodeCount <= 0)
                return null;

            for (int i = 0; i < m_nNeedExpandTreeNodeCount; i++) {
                //System.out.println("i" + m_pNeedExpandTreeNodeIds[i]);
                if (m_pNeedExpandTreeNodeIds[i] == _nChannelId)
                    return m_pNeedExpandTreeNodes[i];
            }

            return null;
        }
    }

    public void writeTreeNodesChildrenHTML(User _loginUser,
            int _nSiteType, String _sChannelIds, JspWriter out) throws Exception {
        if (_sChannelIds == null || _sChannelIds.length() == 0)
            return;

        TreeNode rootNode = new TreeNode(_sChannelIds, _loginUser, _nSiteType);
        writeHTMLFromObjects(_loginUser, out,
                rootNode.getNeedExpandTreeNodes(), 0);
    }

    private void writeHTMLFromObjects(User loginUser, JspWriter out,
            TreeNode[] _pSiteNodes, int _nCurrLevel) throws Exception {
        String sTabContent = makeTabContent(_nCurrLevel);
        for (int i = 0, nSize = _pSiteNodes.length; i < nSize; i++) {
            TreeNode node = _pSiteNodes[i];
            WebSite site = node.getRoot();

            out
                    .println(sTabContent + "<div title=\""
                            + CMyString.filterForHTMLValue(site.getDispDesc())
                            + "\" classPre=\""+getClassPre(site)+"\" id=\"s_" + site.getId()
                            + "\" OnlyNode=\"true\"><a href=\"#\" "+getExtraAttrOfA(site)+">"
                            + CMyString.transDisplay(site.getDispDesc())
                            + "</a></div>");

            List lChildren = site.getChildren(loginUser);
            if (lChildren != null && !lChildren.isEmpty()) {
                out.println(sTabContent + "<ul>");
                writeHTMLFromObjects(loginUser, out, lChildren, node,
                        _nCurrLevel + 1);
                out.println(sTabContent + "</ul>");
            }
        }
    }

    /**
     * @param loginUser
     * @param out
     * @param objects
     * @throws IOException
     * @throws WCMException
     */
    private void writeHTMLFromObjects(User loginUser, JspWriter out,
            List objects, TreeNode _parentNode, int _nCurrLevel)
            throws Exception {
        String sTabContent = makeTabContent(_nCurrLevel);
        for (int i = 0, nSize = objects.size(); i < nSize; i++) {
            Channel host = (Channel) objects.get(i);
            if (host == null)
                continue;

            out
                    .println(sTabContent + "<div title=\""
                            + CMyString.filterForHTMLValue(host.getDispDesc())
                            + "\" classPre=\""+getClassPre(host)+"\" id=\"c_" + host.getId()
                            + "\"><a href=\"#\" "+getExtraAttrOfA(host)+">"
                            + CMyString.transDisplay(host.getDispDesc())
                            + "</a></div>");

            List lChildren = host.getChildren(loginUser);
            if (lChildren != null && !lChildren.isEmpty()) {
                TreeNode node = _parentNode.getExpandNodeById(host.getId());
                if (node != null) {
                    out.println(sTabContent + "<ul>");
                    writeHTMLFromObjects(loginUser, out, lChildren,
                            node, _nCurrLevel + 1);
                    out.println(sTabContent + "</ul>");
                } else {
                    out.println(sTabContent + "<ul></ul>");
                }
            }
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

    private String makeTabContent(int _nTabLevel) {
        StringBuffer sbResult = new StringBuffer(_nTabLevel & 4);
        for (int i = 0; i < _nTabLevel; i++) {
            sbResult.append("   ");
        }

        return sbResult.toString();
    }
    
    private String getExtraAttrOfA(BaseChannel host) {
        String sTitle = CMyString.format(LocaleServer.getString("nav_tree_select_individual.jsp.stitle", "编号：{0}\n创建者：{1}\n创建时间：{2}"), new Object[]{String.valueOf(host.getId()),CMyString.filterForHTMLValue(host.getName()),CMyString.filterForHTMLValue(host.getCrUserName()),host.getCrTime()});
        return " title=\"" + sTitle + "\" ";
    }

%>