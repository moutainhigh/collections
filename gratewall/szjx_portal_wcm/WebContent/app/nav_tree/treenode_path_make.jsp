<%
/** Title:			channels_get.jsp
 *  Description:
 *		标准WCM V6页面，用于“获取指定节点的路径”。
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			WCM
 *  Created:		2006-11-23 11:47 上午
 *  Vesion:			1.0
 *  Last EditTime:	2006-11-23/2006-11-23
 *	Update Logs:
 *		CH@2006-11-23 created the file 
 *
 *  Parameters:
 *		see treenode_path_make.xml
 *
 */
%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%
//4.初始化（获取数据）

	String sNodeType	= currRequestHelper.getString("NodeType");
	int	nNodeId		= currRequestHelper.getInt("NodeId", 0);
	

//5.权限校验

//6.业务代码
	out.clear();out.write(getNodePath(sNodeType, nNodeId));%><%!
	protected String getNodePath(String _sNodeType, int _nNodeId) throws Exception{
        if (_sNodeType.equalsIgnoreCase("s")) {
            WebSite site = WebSite.findById(_nNodeId);
            if(site == null){
                throw new WCMException(CMyString.format(LocaleServer.getString("treenode_path_make.jsp.siteId_notfound", "站点[ID={0}]没有找到！"), new int[]{_nNodeId}));
            }
            return site.getPropertyAsString("SITETYPE");
        }else if (_sNodeType.equalsIgnoreCase("c")) {
            //数据校验
            Channel channel = Channel.findById(_nNodeId);
            if(channel == null){
                throw new WCMException(CMyString.format(LocaleServer.getString("treenode_path_make.jsp.channelId_notfound", "栏目[ID={0}]没有找到！"), new int[]{_nNodeId}));
            }
            WebSite site = channel.getSite();
            if(site == null){
                throw new WCMException(CMyString.format(LocaleServer.getString("treenode_path_make.jsp.siteId_notfound", "站点[ID={0}]没有找到！"), new int[]{_nNodeId}));
            }
            
            //初始化，最多6级，每一级5个字符
            StringBuffer sbPath = new StringBuffer(6*5);
            
            //先填入SiteType和SiteId            
            sbPath.append(site.getPropertyAsString("SITETYPE"));
            sbPath.append(',');
            sbPath.append(site.getId());
            
            //获取路径数组，最多20级
            int[] pNodePath = new int[20];
            int nCount = 0;
            Channel parent = channel.getParent();
            while (parent != null) {
                pNodePath[nCount] = parent.getId();
                nCount++;
                parent = parent.getParent();
            }
            
            //倒序填充
            for (int i = nCount-1; i >= 0; i--) {  
                sbPath.append(',');
                sbPath.append(pNodePath[i]);
            }
            
            return sbPath.toString();
        }        
        else {
            //throw new WCMException("不支持的类型");
            return String.valueOf(_nNodeId);
        }
    }
%>