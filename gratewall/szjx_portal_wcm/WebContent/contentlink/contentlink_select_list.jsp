<%--
/** Title:			contentlink_list.jsp
 *  Description:
 *		内容超链接列表页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			NZ
 *  Created:		2005-04-01 15:08:21
 *  Vesion:			1.0
 *  Last EditTime:	2005-04-01 / 2005-04-01
 *	Update Logs:
 *		NZ@2005-04-01 产生此文件
 *
 *  Parameters:
 *		see contentlink_list.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentLink" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentLinks" %>
<%@ page import="com.trs.components.wcm.content.persistent.WebSite" %>
<%@ page import="com.trs.infra.persistent.ObjToXmlConverter" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.service.impl.ChannelService" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化（获取数据）
	String sOrderField	= CMyString.showNull(currRequestHelper.getOrderField());
	String sOrderType	= CMyString.showNull(currRequestHelper.getOrderType());

	int nChannelId = currRequestHelper.getInt("ChannelId",0);
	Channel currChannel = Channel.findById(nChannelId);
	if(currChannel == null){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "传入参数有误，没有找到ID为["+nChannelId+"]的频道！");
	}

	WebSite currWebSite = currChannel.getSite();
	if(currWebSite == null){
		throw new WCMException(ExceptionNumber.ERR_PARAM_INVALID, "传入参数有误，没有找到ID为["+nChannelId+"]的频道所属的站点！");
	}

//5.权限校验

//6.业务代码
	String sWhere = currRequestHelper.getWhereSQL();
	String sSelectFields = "CONTENTLINKID,LINKNAME,LINKURL,LINKTITLE";
	WCMFilter filter = currRequestHelper.getPageFilter(null);
	
	if(IS_DEBUG) {
		System.out.println("where:"+sWhere);
		System.out.println("order:"+currRequestHelper.getOrderSQL());
	}

	ChannelService currChannelService = (ChannelService)DreamFactory.createObjectById("IChannelService");
	ContentLinks currContentLinks = currChannelService.getContentLinks(currWebSite, filter);

	ObjToXmlConverter aXMLConvert = new ObjToXmlConverter();
	String sXMLString = "";
	try{
		sXMLString = aXMLConvert.toXMLString(currContentLinks, null, "CONTENTLINKID,LINKNAME,LINKURL,LINKTITLE");
	}catch(Exception ex){
		throw new WCMException(ExceptionNumber.ERR_WCMEXCEPTION, "转换ContentLink对象为XML字符串失败！", ex);
	}

//7.结束
	out.clear();
%>
<%-- /*Server Coding End*/ --%>
<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TRS WCM 5.2内容超链接选择列表页面::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<!--- 列表页的JavaScript引用、隐藏参数输出，都在public_client_list.jsp中 --->
<%@include file="../include/public_client_list.jsp"%>
</head>
<BODY topmargin="0" leftmargin="0">
<!--~== TABLE1 ==~-->
<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="1" class="list_table">
<!--~--- ROW1 ---~-->
<TR>
<TD height="25">
	<SCRIPT src="../js/CWCMDialogHead.js"></SCRIPT>
	<SCRIPT LANGUAGE="JavaScript">
		WCMDialogHead.draw("<%=LocaleServer.getString("contentlink.label.list", "内容超链接列表")%>");
	</SCRIPT>
</TD>
</TR>
<!--~- END ROW1 -~-->
<!--~--- ROW3 ---~-->
<TR>
  <TD valign="top">
  <!--~== TABLE3 ==~-->
  <TABLE width="100%" height="100%" border="0" cellpadding="2" cellspacing="0" bgcolor="#FFFFFF">
  <!--~--- ROW4 ---~-->
  <TR>
    <TD height="25" valign="top">
    <!--~== TABLE4 ==~-->
    <TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
    <!--~--- ROW5 ---~-->
    <TR>
      <TD align="left" valign="top">
			<script>
				//定义一个单行按钮
				var oTRSButtons = new CTRSButtons();
				oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.refresh", "刷新")%>", "CTRSAction_refreshMe();", "../images/button_refresh.gif", "<%=LocaleServer.getString("contentlink.tip.refresh", "刷新当前页面")%>");
				oTRSButtons.draw();	
			</script>      
      </TD>
	  <TD width="250" nowrap align="right">
	  <form name="frmSearch" onsubmit="CTRSAction_doSearch(this);return false;">		
		<input type="text" name="SearchValue" size=10 value="<%=PageViewUtil.toHtmlValue(currRequestHelper.getSearchValue())%>">
		<select name="SearchKey">
			<option value="LINKNAME,LINKURL,LINKTITLE">全部</option>
			<option value="LINKNAME"><%=LocaleServer.getString("contentlink.label.content", "内容")%></option>
			<option value="LINKTITLE"><%=LocaleServer.getString("contentlink.label.desc", "说明")%></option>
			<option value="LINKURL"><%=LocaleServer.getString("contentlink.label.url", "URL")%></option>
		</select>
		<input type="submit" value="<%=LocaleServer.getString("system.button.search", "检索")%>">		
	  </form>
	  </TD>
	  <script>
		document.frmSearch.SearchKey.value = "<%=CMyString.filterForJs(currRequestHelper.getSearchKey())%>";
	  </script>
    </TR>
    <!--~- END ROW5 -~-->	
    </TABLE>
    <!--~ END TABLE4 ~-->
    </TD>
  </TR>
  <!--~- END ROW4 -~-->
  <!--~-- ROW10 --~-->
  <TR>
    <TD align="left" valign="top" height="20">
    <!--~== TABLE9 ==~-->
    <TABLE width="100%" border="0" cellpadding="0" cellspacing="1" class="list_table">
    <!--~-- ROW11 --~-->
    <TR bgcolor="#BEE2FF" class="list_th">
		<TD width="40" height="20" NOWRAP><a href="javascript:TRSHTMLElement.selectAllByName('ContentLinkIds');"><%=LocaleServer.getString("system.label.selectall", "全选")%></a></TD>
		<TD bgcolor="#BEE2FF"><%=PageViewUtil.getHeadTitle("LINKNAME", LocaleServer.getString("contentlink.label.content", "内容"), sOrderField, sOrderType)%></TD>
		<TD bgcolor="#BEE2FF"><%=PageViewUtil.getHeadTitle("LINKTITLE", LocaleServer.getString("contentlink.label.desc", "说明"), sOrderField, sOrderType)%></TD>	
		<TD bgcolor="#BEE2FF"><%=PageViewUtil.getHeadTitle("LINKURL", LocaleServer.getString("contentlink.label.url", "URL"), sOrderField, sOrderType)%></TD>
    </TR>
    <!--~ END ROW11 ~-->
	<%
	ContentLink currContentLink = null;
	for(int i=0; i<currContentLinks.size(); i++)
	{//begin for
		try{
			currContentLink = (ContentLink)currContentLinks.getAt(i);
		} catch(Exception ex){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取第["+(i+1)+"]篇内容超链接失败！", ex);
		}

		if(currContentLink == null) continue;

		try{
			String sLinkName = PageViewUtil.toHtml(currContentLink.getPropertyAsString("LINKNAME"));
			String sLinkTitle = PageViewUtil.toHtml(currContentLink.getPropertyAsString("LINKTITLE"));
			String sLinkUrl = PageViewUtil.toHtml(currContentLink.getPropertyAsString("LINKURL"));
%>
	<TR class="list_tr" onclick="TRSHTMLTr.onSelectedTR(this);">
		<TD width="40" NOWRAP><INPUT TYPE="checkbox" checked NAME="ContentLinkIds" VALUE="<%=currContentLink.getId()%>" LINKNAME="<%=sLinkName%>" LINKTITLE="<%=sLinkTitle%>" LINKURL="<%=sLinkUrl%>"><%=(i+1)%></TD>
		<TD>&nbsp;<%=sLinkName%></TD>
		<TD>&nbsp;<%=sLinkTitle%></TD>
		<TD>&nbsp;<%=sLinkUrl%></TD>
	</TR>
<%
		} catch(Exception ex){
			throw new WCMException(ExceptionNumber.ERR_PROPERTY_VALUE_INVALID, "获取第["+i+"]篇内容超链接的属性失败！", ex);
		}
	}//end for	
%>
    </TABLE>
    <!--~ END TABLE9 ~-->
    </TD>
  </TR>
  <TR>
	<TD align="center">
		<script>
			//定义一个TYPE_ROMANTIC_BUTTON按钮
			var oTRSButtons		= new CTRSButtons();
			
			oTRSButtons.cellSpacing	= "0";
			oTRSButtons.nType	= TYPE_ROMANTIC_BUTTON;

			oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.confirm", "确定")%>", "onOk()");
			oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.cancel", "取消")%>", "window.close();");
			
			oTRSButtons.draw();	
		</script>
	</TD>
	</TR>
	<TR><TD height="100%">&nbsp;</TD></TR>
  <!--~ END ROW10 ~-->
  </TABLE>
  <!--~ END TABLE3 ~-->
  </TD>
</TR>
<!--~- END ROW3 -~-->
</TABLE>
<script>
function getContentLinkIds(){
	return TRSHTMLElement.getElementValueByName('ContentLinkIds');
}

function onOk(){
	var arEls = document.getElementsByName("ContentLinkIds");
	if(!arEls){
		window.returnValue = null;
		window.close();
	}
	var sXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><WCMCONTENTLINKS>";
	for(var i=0; i<arEls.length; i++){
		var oEl = arEls[i];
		if(!oEl || !oEl.checked) continue;
		sXML += "<WCMCONTENTLINK Version=\"5.2\"><PROPERTIES>";
			sXML += "<CONTENTLINKID>"+oEl.value+"</CONTENTLINKID>";
			sXML += "<LINKNAME><![CDATA["+oEl.LINKNAME+"]]></LINKNAME>";
			sXML += "<LINKTITLE><![CDATA["+oEl.LINKTITLE+"]]></LINKTITLE>";
			sXML += "<LINKURL><![CDATA["+oEl.LINKURL+"]]></LINKURL>";
		sXML += "</PROPERTIES></WCMCONTENTLINK>";
	}
	sXML += "</WCMCONTENTLINKS>";

	window.returnValue = sXML;
	window.close();
}
</script>
</BODY>
</HTML>
<%
//6.资源释放
	currContentLinks.clear();
%>