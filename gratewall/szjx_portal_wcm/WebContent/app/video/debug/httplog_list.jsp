<%--
/** Title:			HttpLog_list.jsp
 *  Description:
 *		HttpLog列表页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			TRS WCM 5.2
 *  Created:		2009-12-28 19:41:32
 *  Vesion:			1.0
 *  Last EditTime:	2009-12-28 / 2009-12-28
 *	Update Logs:
 *		TRS WCM 5.2@2009-12-28 产生此文件
 *
 *  Parameters:
 *		see HttpLog_list.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	errorPage="../../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.video.persistent.HttpLog" %>
<%@ page import="com.trs.components.video.persistent.HttpLogs" %>
<%@ page import="com.trs.infra.persistent.WCMFilter"%>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@ page import="com.trs.infra.util.CPager"%>
<%@ page import="com.trs.presentation.util.PageHelper"%>
<%@ page import="com.trs.presentation.util.PageViewUtil"%>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>
<%
//4.初始化（获取数据）
	String sOrderField	= CMyString.showNull(currRequestHelper.getOrderField());
	String sOrderType	= CMyString.showNull(currRequestHelper.getOrderType());

//5.权限校验

//6.业务代码
	String sSelectFields = "DB_FIELD_REQUESTURL,DB_FIELD_HTTPRESULT,crtime";
	WCMFilter filter = new WCMFilter("", currRequestHelper.getWhereSQL(),
	currRequestHelper.getOrderSQL(), sSelectFields);
	
	/**
	  *TODO 改为以下方式
	  *IChannelService currChannelService = (IChannelService)DreamFactory.createObjectById("IChannelService");
	  *HttpLogs currHttpLogs = currChannelService.getHttpLogs(currChannel, filter);
	**/
	HttpLogs currHttpLogs = HttpLogs.openWCMObjs(loginUser, filter);

	CPager currPager = new CPager(currRequestHelper.getInt("PageItemCount", 20));
	currPager.setItemCount( currHttpLogs.size() );	
	currPager.setCurrentPageIndex( currRequestHelper.getInt("PageIndex", 1) );

//7.结束
	out.clear();
%>
<%-- /*Server Coding End*/ --%>
<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TRS WCM 视频选件HTTP请求诊断页面</title>
<!--- 列表页的JavaScript引用、隐藏参数输出，都在public_client_list.jsp中 --->
<!--
<%@include file="../../include/public_client_list.jsp"%>
-->
<script type="text/javascript" src="../../js/data/locale/wcm52.js"></script>
<SCRIPT LANGUAGE="JavaScript" src="../../js/wcm52/CTRSHashtable.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../../js/wcm52/CTRSRequestParam.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../../js/wcm52/CTRSAction.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../../js/wcm52/CTRSHTMLTr.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../../js/wcm52/CTRSHTMLElement.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="../../js/wcm52/CTRSButton.js"></SCRIPT>
<%=currRequestHelper.toTRSRequestParam()%>
</head>

<BODY topmargin="0" leftmargin="0" style="margin:5px">
<TABLE width="100%" height="100%" border="0" cellpadding="0"
	cellspacing="1" class="list_table">
	<TR>
		<TD height="26" class="head_td">
		</TD>
	</TR>
	<TR>
		<TD valign="top">
		<TABLE width="100%" border="0" cellpadding="2" height="100%"
			cellspacing="0" bgcolor="#FFFFFF">
			<TR>
				<TD height="25">
				<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
					<TR>
						<TD width="250" nowrap>
							<form name="frmSearch" onsubmit="CTRSAction_doSearch(this);return false;">
								&nbsp; <input type="text" name="SearchValue" size=10 value="<%=PageViewUtil.toHtmlValue(currRequestHelper.getSearchValue())%>">
								<select name="SearchKey">
									<option value="DB_FIELD_REQUESTURL,DB_FIELD_HTTPRESULT">全部</option>
									
									<option value="DB_FIELD_REQUESTURL">请求的URL</option>
									

									<option value="DB_FIELD_HTTPRESULT">请求结果</option>
									

								</select> 
								<input type="submit" value="检索">
							</form>
						</TD>						
						<script>
							document.frmSearch.SearchKey.value = "<%=CMyString.filterForJs(currRequestHelper.getSearchKey())%>";
						</script>
					</TR>
				</TABLE>
				</TD>
			</TR>
			<TR>
				<TD align="left" valign="top" height="20">
				<TABLE width="100%" border="0" cellpadding="0" cellspacing="1"
					class="list_table">
					<TR bgcolor="#BEE2FF" class="list_th">
						<TD width="40" height="20" NOWRAP><a
							href="javascript:TRSHTMLElement.selectAllByName('HttpLogIds');"></a>序号</TD>
						
						<TD bgcolor="#BEE2FF"><%=PageViewUtil.getHeadTitle("DB_FIELD_REQUESTURL", "视频选件HTTP请求的URL", sOrderField, sOrderType)%></TD>
						

						<TD bgcolor="#BEE2FF"><%=PageViewUtil.getHeadTitle("DB_FIELD_HTTPRESULT", "结果", sOrderField, sOrderType)%></TD>

						<TD bgcolor="#BEE2FF"><%=PageViewUtil.getHeadTitle("crtime", "时间", sOrderField, sOrderType)%></TD>
						

					</TR>
		<%
			HttpLog currHttpLog = null;
			for(int i=currPager.getFirstItemIndex(); i<=currPager.getLastItemIndex(); i++)
			{//begin for
				try{
					currHttpLog = (HttpLog)currHttpLogs.getAt(i-1);
				} catch(Exception ex){
					throw new WCMException("获取第["+i+"]篇HttpLog失败！", ex);
				}
				if(currHttpLog == null){
					throw new WCMException("没有找到第["+i+"]篇HttpLog！");
				}

				try{
		%>
					<TR class="list_tr">
						<TD width="40" NOWRAP><%=i%></TD>
						
						<TD><%=PageViewUtil.toHtml(currHttpLog.getRequestUrl())%></TD>
						

						<TD><%=currHttpLog.isResult() ? "成功" : "失败"%></TD>
						
						<TD><%=currHttpLog.getCrTime()%></TD>
					</TR>
		<%
				} catch(Exception ex){
					throw new WCMException("获取第["+i+"]篇HttpLog的属性失败！", ex);
				}
			}//end for	
		%>
				</TABLE>
				</TD>
			</TR>
			<TR>
				<TD class="navigation_page_td" valign="top">
					<%=PageHelper.PagerHtmlGenerator(currPager, currRequestHelper.getInt("PageItemCount", 20), "记录", "条")%>
				</TD>
			</TR>
		</TABLE>
		</TD>
	</TR>
</TABLE>
</BODY>
</HTML>
<%
//6.资源释放
	currHttpLogs.clear();
%>