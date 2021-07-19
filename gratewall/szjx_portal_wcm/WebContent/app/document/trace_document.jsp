<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>

<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.HashSet"%>
<%@ page import="java.util.ArrayList"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.cms.auth.persistent.User" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDocs" %>
<%@ page import="com.trs.components.wcm.content.persistent.Document" %>
<%@ page import="com.trs.components.wcm.content.persistent.Documents" %>
<%@ page import="com.trs.infra.common.WCMException" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.cms.auth.persistent.Groups" %>
<%@ page import="com.trs.infra.support.log.Log" %>
<%@ page import="com.trs.infra.support.log.LogServer" %>
<%@ page import="com.trs.infra.support.log.LogType" %>
<%@ page import="com.trs.infra.common.WCMTypes" %>

<%@ page import="com.trs.infra.support.log.Logs" %>
<%@ page import="com.trs.infra.persistent.db.DBManager" %>
<%@ page import="com.trs.presentation.util.RequestHelper" %>
<%@ page import="com.trs.components.common.publish.domain.publisher.PublishPathCompass" %>
<%@ page import="com.trs.components.common.publish.persistent.element.IPublishContent" %>
<%@ page import="com.trs.components.common.publish.persistent.element.PublishElementFactory" %>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ include file="../system/status_locale.jsp"%>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%
	// 获取参数，校验参数有效性
	int nDocId = currRequestHelper.getInt("DocumentId", 0);
	Document document = Document.findById(nDocId);
	if (document == null)
		throw new WCMException(CMyString.format(LocaleServer.getString("trace_document.jsp.notfindspecialdoc", "没有找到指定的文档！ [ID={0}]"), new int[]{nDocId}));

	// 判断权限，如果有文档修改权限放行
	int nRightIndex = WCMRightTypes.DOC_EDIT;
	if (!AuthServer.hasRight(loginUser, document, nRightIndex)) {
		throw new WCMException(CMyString.format(LocaleServer.getString("trace_document.jsp.sorrynottracecurrdoc", "对不起，您没有跟踪当前档！ [{0}]"), new String[]{document.getTitle()}));
	}
	out.clear();
%>

<html>
<head>
	<title WCMAnt:param="trace_document.jsp.title"> 跟踪文档[<%=document.getTitle()%>]使用情况 </title>
	<style type="text/css">
		body{
			font-weight:normal;
			font-family: 'Courier New' , Monospace;
			font-size:14px;
			padding:0;
			margin:0;
		}
		.info{
			color : blue;
			font-size : 14px;
		}
		
		.status{
			color : red;
			font-size : 14px;
		}

		.published{
			color : blue;
			font-size : 14px;
		}
		.view_area{
			background-repeat: no-repeat;
			background-position: center bottom;
		}
		.tb_box{
			width:100%;
			overflow:auto;
			line-height:20px;
		}
		.tb_title{
			width:100%;
			height:28px;
			font-size:14px;
			font-weight:bold;
			padding-left:10px;
			border-bottom:1px;
			border-bottom-color:#999999;
			border-bottom-style:dashed;
		}
		.tb_content{
			margin-top:5px;
		}
		.ol_content{
			font-size:14px;
			line-height:20px;
			margin-bottom:0px;
		}
		.th_table{
			width:100%;
			border-collapse:collapse;
		}
		.th_tbale th{
			line-height:20px;
			border:1px solid #dddddd;

			background:gray;
			color:white;
			font-size:14px;
		}
		.th_tbale td{
			line-height:20px;
			border-bottom:1px solid #dddddd;
			font-size:12px;
		}
		.windowname{
			color:white;
			font-weight:bold;
		}
	</style>
	<script language="javascript">
	<!--
		function viewLog(_logId){
			window.open("../../console/stat/log_view.jsp?LogId="+_logId,"_blank");
		}
	//-->
	</script>
</head>

<body>
<SCRIPT src="../../console/js/CWCMDialogHead.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
	WCMDialogHead.draw("<%=CMyString.format(LocaleServer.getString("trace_document.jsp.tracedocuse", "跟踪文档[{0}]使用情况"), new String[]{document.getTitle()})%>",true);
</SCRIPT>
<div style="width:100%;height:100%;text-align:center;">
<table border="0" cellspacing="0" cellpadding="0" width="70%" height="100%" class="view_area" id="view_area">
<tbody>
	<tr>
		<td valign="top">
		<table border="0" cellspacing="0" cellpadding="0" width="100%" height="auto"  class="tb_box">
		<tbody>
			<tr>
				<td>
					<table border="0" cellspacing="0" cellpadding="0" class="tb_title">
					<tbody>
						<tr>
							<td><span WCMAnt:param="trace_document.jsp.tracedocument_copy">文档复制情况</span></td>
						</tr>
					</tbody>
					</table>

					<table border="0" cellspacing="0" cellpadding="0" class="tb_content">
					<tbody>
						<tr>
							<td><OL class="ol_content">
					<%
						int nMainSiteId = 33;
						// 获取复制的情况
						String sSelectDocumentFields = "DocId,DocStatus,DocTitle"
								+ ",CrUser,CrTime,DocChannel,OperUser";

						WCMFilter filter = new WCMFilter("", "DocOutupId=? and SiteId<>?", "DocId Desc",
								sSelectDocumentFields);
						filter.addSearchValues(nDocId);
						filter.addSearchValues(nMainSiteId);
						filter.setMaxRowNumber(200);
						String sOragTitle = document.getTitle();
						Documents documents = Documents.openWCMObjs(null, filter);
						for (int i = 0, nSize = documents.size(); i < nSize; i++) {
							Document copyDocument = (Document) documents.getAt(i);
							String sStatusName = copyDocument.isDeleted() ? LocaleServer.getString("trace_document.label.deleted", "已删除")
									: copyDocument.getStatusName();
							String sOperUser = (String) copyDocument.getProperty("OPERUSER");
							String sStatusClass = "status";
							String sPubHTML = "";
							if (copyDocument.getStatusId() == Status.STATUS_ID_PUBLISHED) {
								IPublishContent content = PublishElementFactory
										.makeContentFrom(copyDocument, null);
								PublishPathCompass compass = new PublishPathCompass();
								
								String sPubURL = compass.getHttpUrl(content, 0);
								sPubHTML = "&nbsp;&nbsp;<a href='"+sPubURL+"' target='_blank'>"+LocaleServer.getString("trace_document.label.topub", "点击查看发布后的地址")
									+"</a>";
								sStatusClass = "published";
							}
					%>
						<LI>
							<%=CMyString.format(LocaleServer.getString("trace_document.label.info", "文档<span class='info'>{0}</span>被复制到<span class='info'>{1}</span>栏目,用户<span class='info'>{2}</span>于<span class='info'>{3}</span>时分发，文档目前的状态为<span class='{4}'>{5}</span>"), new String[]{sOragTitle,copyDocument.getChannel().getName(),sOperUser,copyDocument.getCrTime().toString(LocaleServer.getString("trace_document.label.datetimeformat","yyyy年MM月dd日hh点mm")),sStatusClass,getStatusLocale(sStatusName)})%>
						<%=sPubHTML%>
						<%
							}
						%>
						</LI>
					</OL></td>
						</tr>
					</tbody>
					</table>
				</td>
			</tr>
		</tbody>
		</table>
		<table border="0" cellspacing="0" cellpadding="0" width="100%" height="auto" class="tb_box">
		<tbody>
			<tr>
				<td>
				<table border="0" cellspacing="0" cellpadding="0" class="tb_title">
				<tbody>
					<tr>
						<td><span WCMAnt:param="trace_document.jsp.docrefstate">文档引用情况</span></td>
					</tr>
				</tbody>
				</table>
				
				<table border="0" cellspacing="0" cellpadding="0" class="tb_content">
				<tbody>
					<tr>
						<td><OL class="ol_content">
				<%
					// 获取引用的情况
					String sSelectChnlDocFields = "DocId,DocStatus,ChnlId,CrTime,CrUser";
					filter = new WCMFilter("", "DocId=? and modal>=2", "RecId Desc",
							sSelectChnlDocFields);
					filter.addSearchValues(nDocId);
					filter.setMaxRowNumber(200);
					ChnlDocs chnldocs = ChnlDocs.openWCMObjs(null, filter);
					for (int i = 0, nSize = chnldocs.size(); i < nSize; i++) {
						ChnlDoc quoteChnlDoc = (ChnlDoc) chnldocs.getAt(i);
						String sStatusName = quoteChnlDoc.isDeleted() ? LocaleServer.getString("trace_document.label.deleted", "已删除")
								: quoteChnlDoc.getStatusName();
				%>
					<LI><span WCMAnt:param="trace_document.jsp.document">文档</span><span class="info"><%=document.getTitle()%></span><span WCMAnt:param="trace_document.jsp.bei">被</span> <span class="info"><%=quoteChnlDoc.getCrUserName()%></span> 
					<span WCMAnt:param="trace_document.jsp.to">于</span><span class="info"><%=quoteChnlDoc.getCrTime().toString(LocaleServer.getString("trace_document.label.datetimeformat","yyyy年MM月dd日HH点mm分"))%></span>
					<span WCMAnt:param="trace_document.jsp.quoteto">引用到</span><span class="info"><%=quoteChnlDoc.getChannel()%></span>
					<span WCMAnt:param="trace_document.jsp.currstatus">目前状态为</span> <span class="status"><%=getStatusLocale(sStatusName)%></span>
				<%
					}
				%>
				</OL></td>
					</tr>
				</tbody>
				</table>
		</td>
			</tr>
		</tbody>
		</table>
		<table border="0" cellspacing="0" cellpadding="0" width="100%" height="auto" class="tb_box">
		<tbody>
			<tr>
				<td>
				<table border="0" cellspacing="0" cellpadding="0" class="tb_title">
				<tbody>
					<tr>
						<td><span WCMAnt:param="trace_document.jsp.docmodifystate">文档修改情况</span></td>
					</tr>
				</tbody>
				</table>
				
				<table border="0" cellspacing="0" cellpadding="0" width="100%" class="tb_content th_tbale">
				<tbody>
					<tr><th WCMAnt:param="trace_document.jsp.sortnum">序号</th><th WCMAnt:param="trace_document.jsp.operateuser">操作用户</th><th WCMAnt:param="trace_document.jsp.operatetime">操作时间</th><th  WCMAnt:param="trace_document.jsp.operatedescription">操作描述</th><th  WCMAnt:param="trace_document.jsp.result">结果</th><th  WCMAnt:param="trace_document.jsp.detail">详细</th></tr>
					<%
String sLogUserWhere = "";
boolean bOnlySelf = false;
if (!loginUser.isAdministrator()){
	// 获取用户可管理的用户组
	WCMFilter queryMgrGroups = new WCMFilter("", "exists(select 1 from WCMGrpUser where UserId=? and IsAdministrator=1 and WCMGrpUser.GroupId=WCMGroup.GroupId)", "");
	queryMgrGroups.addSearchValues(loginUser.getId());

	Groups groups = Groups.openWCMObjs(null, queryMgrGroups);
	
	if(groups.isEmpty()){
		sLogUserWhere = " and LogUser=?";
		bOnlySelf = true;
	}else{
		sLogUserWhere = " and exists(select 1 from WCMGrpUser,WCMUser,WCMLog"
		+ "  where WCMGrpUser.GroupId in(" + groups.getIdListAsString() + ") and WCMUser.UserId=WCMGrpUser.UserId and WCMLog.LogUser=WCMUser.UserName)";
	}		
}

//6.业务代码
WCMFilter extraFilter = new WCMFilter("wcmlog a", "LogType=? and LogObjId=? and LogObjType=? and (TOPID=0 or exists(select 1 from wcmlog b where LogType = ?  and LogObjId =? and TOPID>=1 and b.topid=a.logid))" + sLogUserWhere, "logid desc");
extraFilter.addSearchValues(LogType.ID_INFO);
extraFilter.addSearchValues(document.getId());
extraFilter.addSearchValues(WCMTypes.OBJ_DOCUMENT);
extraFilter.addSearchValues(LogType.ID_INFO);
extraFilter.addSearchValues(document.getId());
if(bOnlySelf){
	extraFilter.addSearchValues(loginUser.getName());
}
Logs currLogs = (Logs)LogServer.getLogs(extraFilter);
ArrayList currPubLogs = new ArrayList(8);
int nRowNum=1;
for (int i = 0; i < currLogs.size(); i++){
	Log currLog = (Log)currLogs.getAt(i);
	if(currLog == null)continue;
	if(isPubOperation(currLog.getOpType())){
		currPubLogs.add(currLog);
		continue;
	}
					%>
					<tr><td align=center><%=nRowNum++%></td><td align="center">
					<span class="info"><%=currLog.getUserName()%></span></td><td align="center">
					<%=currLog.getOpTime().toString("yy-MM-dd HH:mm:ss")%></td><td>
					<span class="info"><%=currLog.getDesc()%></span></td>
					<td align="center"><%=currLog.getResult()==1?(LocaleServer.getString("trace_document.jsp.label.success", "成功")):(LocaleServer.getString("trace_document.jsp.label.success.fail", "失败"))%></td><td align="center"><A href="#" onclick="viewLog(<%=currLog.getId()%>);return false;" target="_blank"><%=LocaleServer.getString("system.label.view", "查看")%></A></td>
					</tr>
					<%
}
					%>
				</tbody>
				</table>
		</td>
			</tr>
		</tbody>
		</table>
		<table border="0" cellspacing="0" cellpadding="0" width="100%" height="auto" class="tb_box">
		<tbody>
			<tr>
				<td>
				<table border="0" cellspacing="0" cellpadding="0" class="tb_title">
				<tbody>
					<tr>
						<td><span WCMAnt:param="trace_document.jsp.docpubstate">文档发布情况</span></td>
					</tr>
				</tbody>
				</table>
				<table border="0" cellspacing="0" cellpadding="0" width="100%" class="tb_content th_tbale">
				<tbody>
					<tr><th WCMAnt:param="trace_document.jsp.sortnum">序号</th><th WCMAnt:param="trace_document.jsp.operateuser">操作用户</th><th WCMAnt:param="trace_document.jsp.operatetime">操作时间</th><th  WCMAnt:param="trace_document.jsp.operatedescription">操作描述</th><th  WCMAnt:param="trace_document.jsp.result">结果</th><th  WCMAnt:param="trace_document.jsp.detail">详细</th></tr>
				<%
int index = 1;
for (Iterator itr = currPubLogs.iterator(); itr.hasNext(); ){
	Log currLog = (Log)itr.next();
					%>
					<tr><td align=center><%=index++%></td><td align="center">
					<span class="info"><%=currLog.getUserName()%></span></td><td align="center">
					<%=currLog.getOpTime().toString("yy-MM-dd HH:mm:ss")%></td><td>
					<span class="info"><%=currLog.getDesc()%></span></td>
					<td align="center"><%=currLog.getResult()==1?(LocaleServer.getString("trace_document.jsp.label.success", "成功")):(LocaleServer.getString("trace_document.jsp.label.success.fail", "失败"))%></td><td align="center"><A href="#" onclick="viewLog(<%=currLog.getId()%>);return false;" target="_blank"><%=LocaleServer.getString("system.label.view", "查看")%></A></td>
					</tr>
					<%
	}
					%>
				</tbody>
				</table>
		</td>
			</tr>
		</tbody>
		</table>
		</td>
	</tr>
</tbody>
</table>
</div>
<script language="javascript">
<!--
	var iRand = new Date().getTime()%5+1;
	document.getElementById('view_area').style.backgroundImage = 'url(../images/document/document_show/bg_tu'+iRand+'.gif)';
//-->
</script>
</body>
</html>
<%!
	private final static Set DOC_PUBOPS = new HashSet(8);
	private final static int DOC_PUBOP_COUNT = 5;
	private static boolean isPubOperation(int _nOpType){
		if(DOC_PUBOPS.contains(String.valueOf(_nOpType))) {
			return true;
		}
		if(DOC_PUBOPS.size() == DOC_PUBOP_COUNT) return false;

		//load.
		String sql = "select NAME from WCMOPERTYPE where OPERTYPEID=?";
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;
		DBManager dbman = DBManager.getDBManager();
		boolean zResult = false;
		try{
			conn = dbman.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,_nOpType);
			rs = pstmt.executeQuery();
			if(rs.next()){
				String name = rs.getString(1);
				System.out.println(_nOpType+":"+name);
				zResult = name.indexOf("文档的") != -1 && name.indexOf("发布") != -1;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			//Ignore.
		}finally{
			if(rs != null) {
				try{
					rs.close();
				}catch(Exception ex){}
			}
			if(pstmt != null) {
				try{
					pstmt.close();
				}catch(Exception ex){}
			}
			if(conn != null) {
				try{
					conn.close();
				}catch(Exception ex){}
			}
		}

		if(zResult) DOC_PUBOPS.add(String.valueOf(_nOpType));

		return zResult;
	}

%>