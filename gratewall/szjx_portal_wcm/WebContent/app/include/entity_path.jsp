<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" errorPage="error.jsp"%>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel"%>
<%@ page import="com.trs.components.wcm.content.persistent.Document"%>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@ page import="com.trs.infra.common.WCMException"%>
<%@ page import="com.trs.ajaxservice.WebSiteServiceProvider"%>
<%@ page import="com.trs.cms.auth.domain.AuthServer"%>
<%@ page import="com.trs.presentation.util.LoginHelper" %>
<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.infra.util.ExceptionNumber" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.cms.auth.persistent.RightValue" %>
<%@ page import="com.trs.cms.content.CMSObj" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.cms.ContextHelper" %>
<%@include file="./public_server.jsp"%>
<%
	request.setCharacterEncoding("ISO-8859-1");
	String sObjectDesc = CMyString.transDisplay(CMyString.getStr(request.getParameter("objectdesc")));
	int nSiteId = currRequestHelper.getInt("siteid", 0);
	int nChnlId = currRequestHelper.getInt("channelid", 0);
	int nDocId = currRequestHelper.getInt("documentid", 0);	
	int nSiteType = currRequestHelper.getInt("sitetypeid", 0);
	String sHasSite = CMyString.transDisplay(request.getParameter("tracesite"));
	String sHasSiteType = CMyString.transDisplay(request.getParameter("tracesitetype"));

	
	Channel currChnl = null;
	WebSite currSite = null;
	try{ 
		if (nDocId != 0){
			currChnl = Document.findById(nDocId).getChannel();
		}else if(nChnlId!= 0){
			currChnl = Channel.findById(nChnlId);
		}else if(nSiteId != 0){
			currSite = WebSite.findById(nSiteId);
		}
		
		if (currChnl != null){
			if("true".equals(sHasSiteType)){
				out.print(getSiteTypeNavHtml(currChnl.getSite().getType()));
				out.print(" <span style='font-size: 16px;'>&gt;</span> ");
			}
			if("true".equals(sHasSite)){
				out.print(getNavHtml(currChnl, sObjectDesc, loginUser));
			}else{
				out.print(getNavWithoutSiteTrace(currChnl, sObjectDesc, loginUser));
			}
		}else if(currSite != null){
			if("true".equals(sHasSiteType)){
				out.print(getSiteTypeNavHtml(currSite.getType()));
				out.print(" <span style='font-size: 16px;'>&gt;</span> ");
			}
			out.print(getSiteNavHtml(currSite, sObjectDesc, loginUser));
		}else{
			if(nSiteType == 100 || nSiteType == 200)
				out.print(getOtherNavHtml(nSiteType));
			else 
				out.print(getSiteTypeNavHtml(nSiteType));
		}
	}catch(Throwable ex){
		out.print(ex);
	}
%>
<%!
	public String findSiteTypeDesc(int _nSiteType) throws Throwable {
		switch (_nSiteType) {
		case 0:
			return LocaleServer.getString("websiteroot_findbyid.label.wordsitetype", "文字库");
		case 1:
			return LocaleServer.getString("websiteroot_findbyid.label.imgsitetype", "图片库");
		case 2:
			return LocaleServer.getString("websiteroot_findbyid.label.videositetype", "视频库");
		case 4:
			return LocaleServer.getString("websiteroot_findbyid.label.resource", "资源库");
		case 100:
			return LocaleServer.getString("websiteroot_findbyid.label.xiezuoservice", "协作服务");
		default:
			return LocaleServer.getString("websiteroot_findbyid.label.wordsitetype", "文字库");
		}
	}

	private String getSiteTypeNavHtml(int _nSiteType)throws Throwable {
		String sSiteTypeDesc = findSiteTypeDesc(_nSiteType);
		StringBuffer sbHtml = new StringBuffer(100);
		sbHtml.append("&nbsp;");
		sbHtml.append("<a href='#' class=\"navigation_website\"");
		sbHtml.append(" onclick='PageContext.traceLiterator(").append(_nSiteType).append(");return false;'");
		sbHtml.append(" title='").append(sSiteTypeDesc).append("'");
		sbHtml.append(">");
		sbHtml.append(getDescString(sSiteTypeDesc));
		sbHtml.append("</a>");
		return sbHtml.toString();
	}
	
	private String getSiteNavHtml(WebSite _currSite, String _sDesc, User loginUser)throws WCMException {
		StringBuffer sbHtml = new StringBuffer(100);
		sbHtml.append("<span class='navigation_channel'>");
		sbHtml.append("<a id='navigation_site_entity'");
		sbHtml.append(" _siteid='").append(_currSite.getId());
		sbHtml.append("' href='#' title='");
		sbHtml.append(_currSite.getDesc());
		sbHtml.append("' onclick=\"PageContext.traceLiterator('");
		sbHtml.append(_currSite.getId());
		sbHtml.append("', true,'");
		sbHtml.append(getRightValue(_currSite, loginUser));
		sbHtml.append("'); return false;\" class='navigation_channel_link_current'>");
		sbHtml.append(getDescString(_currSite.getDesc()));
		sbHtml.append("</a></span>");
		if(!CMyString.isEmpty(_sDesc)) {
			sbHtml.append("<span class=\"navigation_channel_desc\" title='");
			sbHtml.append(_sDesc);
			sbHtml.append("'> :&nbsp;");
			sbHtml.append(getDescString(_sDesc));
			sbHtml.append("</span>");
		};
		return sbHtml.toString();
	}

	private String getOtherNavHtml(int nType){
		StringBuffer sbHtml = new StringBuffer();
		String sPathInfo = (nType == 100 ? LocaleServer.getString("websiteroot_findbyid.label.worklist", "我的工作列表") : LocaleServer.getString("websiteroot_findbyid.label.msg", "短消息"));
		String sRootpath = LocaleServer.getString("websiteroot_findbyid.label.xiezuo", "协作服务");
		sbHtml.append("&nbsp;<a href='#' class=\"navigation_website\" title='" + sRootpath + "'>" + sRootpath + "</a>");
		sbHtml.append("<span style='font-size: 16px;'>&gt;</span>");
		sbHtml.append(" <span class=\"navigation_channel\"><a id=\"navigation_site_entity\" href=\"#\" class=\"navigation_channel_link\" title=\"" + sPathInfo + "\">" + sPathInfo + "</a></span>"); 
		return sbHtml.toString();
	}

	private String getNavHtml(Channel _currChannel, String _sDesc, User loginUser)
			throws WCMException {
		StringBuffer sbHtml = new StringBuffer();

		if (_currChannel == null) {
			sbHtml.append(_sDesc);
		} else {
			Channel parent = null;
			StringBuffer sbChannelNav = new StringBuffer();
	
			sbChannelNav.append("<span class=\"navigation_channel\" title='" + _currChannel.getDesc() + "'>");
			sbChannelNav.append("<a id=\"navigation_channel_entity\" href=\"#\" class=\"navigation_channel_link_current\"  onclick=\"PageContext.traceLiterator('" 
					+ _currChannel.getId() + "',false,'" + getRightValue(_currChannel, loginUser) + "'," + _currChannel.getType() + "); return false;\" >");
			sbChannelNav.append(getDescString(_currChannel.getDesc()));
			sbChannelNav.append("</a></span>");
			if(!CMyString.isEmpty(_sDesc)) {
				sbChannelNav.append("<span class=\"navigation_channel_desc\" title='" + _sDesc + "'> :&nbsp;");
				sbChannelNav.append(getDescString(_sDesc));
				sbChannelNav.append("&nbsp;</span>");
			}

			parent = _currChannel.getParent();
			while (parent != null) {
				if (parent == null) {
					break;
				}
				sbChannelNav.insert(0, "</span>");
				sbChannelNav.insert(0, " <span style='font-size: 16px;'>&gt;</span> ");
				sbChannelNav.insert(0, "<span class=\"navigation_channel\">");
				sbChannelNav.insert(0, "</a>");
				sbChannelNav.insert(0, getDescString(parent.getDesc()));
				sbChannelNav.insert(0, "<a href=\"#\" class=\"navigation_channel_link\"  onclick=\"PageContext.traceLiterator('" + parent.getId() + "',false,'" + getRightValue(parent, loginUser)+"'," + parent.getType() + "); return false;\" title='" + parent.getDesc() + "'>");
				parent = parent.getParent();
			}
			sbChannelNav.insert(0, "</span>");
			sbChannelNav.insert(0, " <span style='font-size: 16px;'>&gt;</span> ");
			sbChannelNav.insert(0, "<span class=\"navigation_channel\">");
			sbChannelNav.insert(0, "</a></span>");
			sbChannelNav.insert(0, getDescString(_currChannel.getSite().getDesc()));
			int nSiteId = _currChannel.getSiteId();
			sbChannelNav
					.insert(
							0,
							"<a id=\"navigation_site_entity\" _siteid='" + nSiteId + "' href=\"#\" class=\"navigation_channel_link\"  onclick=\"PageContext.traceLiterator('"
									+ nSiteId + "', true,'" + getRightValue(_currChannel.getSite(), loginUser)
									+ "');return false;\" title='" + _currChannel.getSite().getDesc() + "'>");
			sbChannelNav.insert(0, "<span class=\"navigation_channel\">");
			sbHtml.append(sbChannelNav);
		}//end if

		return sbHtml.toString();
	}//END: getNavHtml

	//wenyh@2007-08-02 added.
	//有些地方不站点上不需要链接
	private String getNavWithoutSiteTrace(Channel channel,String desc,User user) throws WCMException{
		if(channel == null) return desc;

		StringBuffer navHtml = new StringBuffer(256);
		String tempDesc = channel.getSite().getDesc();
		navHtml.append("<span class='navigation_channel'>");
		navHtml.append(getDescString(tempDesc));
		navHtml.append("</span>");
		navHtml.append("<span style='font:16px;'>&nbsp;&gt;</span>");
		Channel parent = channel.getParent();
		while(parent != null){
			navHtml.append("<span class='navigation_channel'>");
			navHtml.append("<a href='#' class='navigation_channel_link'");
			navHtml.append(" onclick='PageContext.traceLiterator(");
			navHtml.append(parent.getId()).append(",");
			navHtml.append(false).append(",");
			navHtml.append("\"").append(getRightValue(parent,user)).append("\"").append(",");
			navHtml.append(parent.getType()).append(");return false'");
			tempDesc = parent.getDesc();
			navHtml.append(" title='").append(tempDesc).append("'>");
			navHtml.append(getDescString(tempDesc));
			navHtml.append("</a>");
			navHtml.append("</span>");
			navHtml.append("<span style='font:16px;'>&nbsp;&gt; </span>");	
			parent = parent.getParent();
		}

		navHtml.append("<span class='navigation_channel'>");
		navHtml.append("<a href='#' class='navigation_channel_link'");
		navHtml.append(" onclick='PageContext.traceLiterator(");
		navHtml.append(channel.getId()).append(",");
		navHtml.append(false).append(",");
		navHtml.append("\"").append(getRightValue(channel,user)).append("\"").append(",");
		navHtml.append(channel.getType()).append(");return false'");
		tempDesc = channel.getDesc();
		navHtml.append(" title='").append(tempDesc).append("'>");
		navHtml.append(getDescString(tempDesc));
		navHtml.append("</a>");
		navHtml.append("</span>");				

		return navHtml.toString();
	}

	private String getRightValue(CMSObj currObj, User _currUser) throws WCMException {
		String sRightValue = "";
		if(_currUser.equals(currObj.getCrUser()) || _currUser.isAdministrator()){
			//如果是CrUser，则64位权限全部置为1
			sRightValue = "1111111111111111111111111111111111111111111111111111111111111111";
		} else {
			RightValue rightValue = getRightValueByMember(currObj, _currUser);
			if(rightValue != null){
				sRightValue = rightValue.toString().toString();
			}
		}
		return sRightValue;
	}

	private String getDescString(String desc){		
		//return CMyString.truncateStr(CMyString.filterForHTMLValue(desc), 40);
		//wenyh@2007-08-13 truncate first.
		return CMyString.filterForHTMLValue(CMyString.truncateStr(desc, 18));
	}
%>