<%--
/** Title:			fckvideo_list.jsp
 *  Description:
 *		FckVideo列表页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			TRS WCM 5.2
 *  Created:		2009-12-30 11:03:54
 *  Vesion:			1.0
 *  Last EditTime:	2009-12-30 / 2009-12-30
 *	Update Logs:
 *		TRS WCM 5.2@2009-12-30 产生此文件
 *
 *  Parameters:
 *		see fckvideo_list.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.video.content.FckVideo" %>
<%@ page import="com.trs.components.video.content.FckVideos" %>
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
	String sSelectFields = "DB_FIELD_DOCUMENTID,DB_FIELD_DOCID,DB_FIELD_TITLE,DB_FIELD_SRCFILENAME,DB_FIELD_FILENAME,crtime";
	WCMFilter filter = new WCMFilter("", currRequestHelper.getWhereSQL(),
	currRequestHelper.getOrderSQL(), sSelectFields);
	
	/**
	  *TODO 改为以下方式
	  *IChannelService currChannelService = (IChannelService)DreamFactory.createObjectById("IChannelService");
	  *FckVideos currFckVideos = currChannelService.getFckVideos(currChannel, filter);
	**/
	FckVideos currFckVideos = FckVideos.openWCMObjs(loginUser, filter);

	CPager currPager = new CPager(currRequestHelper.getInt("PageItemCount", 20));
	currPager.setItemCount( currFckVideos.size() );	
	currPager.setCurrentPageIndex( currRequestHelper.getInt("PageIndex", 1) );

//7.结束
	out.clear();
%>
<%-- /*Server Coding End*/ --%>
<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TRS WCM 视频选件文档插视频的关系列表</title>
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<!--- 列表页的JavaScript引用、隐藏参数输出，都在public_client_list.jsp中 --->
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
									<option value="DB_FIELD_DOCUMENTID,DB_FIELD_DOCID,DB_FIELD_TITLE,DB_FIELD_SRCFILENAME,DB_FIELD_FILENAME">全部</option>
									
									
									<option value="DB_FIELD_DOCUMENTID">文档ID</option>
									

									<option value="DB_FIELD_DOCID">视频文档ID</option>
									

									<option value="DB_FIELD_TITLE">视频title</option>
									

									<option value="DB_FIELD_SRCFILENAME">源视频名</option>
									

									<option value="DB_FIELD_FILENAME">目标视频名</option>
									

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

						
						<TD bgcolor="#BEE2FF"><%=PageViewUtil.getHeadTitle("DB_FIELD_DOCUMENTID", "文档ID", sOrderField, sOrderType)%></TD>
						

						<TD bgcolor="#BEE2FF"><%=PageViewUtil.getHeadTitle("DB_FIELD_DOCID", "视频文档ID", sOrderField, sOrderType)%></TD>
						

						<TD bgcolor="#BEE2FF"><%=PageViewUtil.getHeadTitle("DB_FIELD_TITLE", "视频title", sOrderField, sOrderType)%></TD>
						

						<TD bgcolor="#BEE2FF"><%=PageViewUtil.getHeadTitle("DB_FIELD_SRCFILENAME", "源视频名", sOrderField, sOrderType)%></TD>
						

						<TD bgcolor="#BEE2FF"><%=PageViewUtil.getHeadTitle("DB_FIELD_FILENAME", "目标视频名", sOrderField, sOrderType)%></TD>

						<TD bgcolor="#BEE2FF"><%=PageViewUtil.getHeadTitle("crtime", "时间", sOrderField, sOrderType)%></TD>
						

					</TR>
		<%
			FckVideo currFckVideo = null;
			for(int i=currPager.getFirstItemIndex(); i<=currPager.getLastItemIndex(); i++)
			{//begin for
				try{
					currFckVideo = (FckVideo)currFckVideos.getAt(i-1);
				} catch(Exception ex){
					throw new WCMException("获取第["+i+"]篇FckVideo失败！", ex);
				}
				if(currFckVideo == null){
					throw new WCMException("没有找到第["+i+"]篇FckVideo！");
				}

				try{
		%>
					<TR class="list_tr">
						<TD width="40" NOWRAP><%=i%></TD>
						
						<TD><%=currFckVideo.getDocumentId()%></TD>
						

						<TD><%=currFckVideo.getDocId()%></TD>
						

						<TD><%=PageViewUtil.toHtml(currFckVideo.getTitle())%></TD>
						

						<TD><%=PageViewUtil.toHtml(currFckVideo.getSRCFILENAME())%></TD>
						

						<TD><%=PageViewUtil.toHtml(currFckVideo.getFILENAME())%></TD>

						<TD><%=currFckVideo.getCrTime()%></TD>
						

					</TR>
		<%
				} catch(Exception ex){
					throw new WCMException("获取第["+i+"]篇FckVideo的属性失败！", ex);
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
	currFckVideos.clear();
%>