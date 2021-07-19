<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	errorPage="../include/error_for_dialog.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.persistent.Channel"%>
<%@ page import="com.trs.components.wcm.content.persistent.Document"%>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc"%>
<%@ page import="com.trs.infra.util.CMyDateTime"%>
<%@ page import="com.trs.components.wcm.content.persistent.Documents"%>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@ page import="com.trs.infra.common.WCMRightTypes"%>
<%@ page import="com.trs.components.wcm.content.domain.auth.DocumentAuthServer"%>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDocs"%>
<%@ page import="com.trs.infra.persistent.WCMFilter"%>
<%@ page import="com.trs.service.IChannelService"%>
<%@ page import="com.trs.service.IDocumentService"%>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定,登录校验,参数获取,都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%
	int nDocumentId = currRequestHelper.getInt("DocumentId", 0);
	int nChannelId = currRequestHelper.getInt("ChannelId", 0);
	Document document = Document.findById(nDocumentId);
	if(document == null){
		throw new WCMException("无法找到ID为【"+nDocumentId+"】的文档对象！");
	}
	Channel channel = Channel.findById(nChannelId);
	if(channel == null){
		throw new WCMException("无法找到ID为【"+nChannelId+"】的栏目对象！");
	}

	IDocumentService currDocumentService = (IDocumentService)DreamFactory.createObjectById("IDocumentService");
	

	//置顶信息
	boolean bIsCanTop = false;//是否在当前栏目有置顶权限
	//有修改文档的权限时才可做置顶设置
	bIsCanTop = DocumentAuthServer.hasRight(loginUser, channel, document, WCMRightTypes.DOC_EDIT);
	boolean bTopped = false;//是否置顶
	boolean bTopForever = false;//是否永久置顶
	CMyDateTime dtTopInvalidTime = CMyDateTime.now().dateAdd(CMyDateTime.DAY, 1);
	if(nDocumentId>0){
		dtTopInvalidTime = currDocumentService.getTopTime(document, channel);
		bTopped = currDocumentService.isDocumentTopped(document, channel);
		if(bTopped && dtTopInvalidTime == null)
			bTopForever = true;
		if(dtTopInvalidTime == null){
			dtTopInvalidTime = CMyDateTime.now().dateAdd(CMyDateTime.DAY, 1);
		}
	}
	String sTopInvalidTime = dtTopInvalidTime.toString("yyyy-MM-dd HH:MM:ss");
	Documents toppedDocuments = null;
	if(bIsCanTop && channel != null) {
		WCMFilter filter = new WCMFilter("", "DOCORDERPRI>0", "", "DocId, DocTitle, DocChannel");
		IChannelService currChannelService = (IChannelService)DreamFactory.createObjectById("IChannelService");
		toppedDocuments = currChannelService.getDocuments(channel, filter);
	}
out.clear();%><html xmlns="http://www.w3.org/1999/xhtml" xmlns:TRS=""
	xmlns:TRS_UI="">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>置顶设置</title>
<link href="../../app/js/resource/widget.css" rel="stylesheet" type="text/css" />
<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/easyversion/extrender.js"></script>
<script src="../../app/js/easyversion/basicdatahelper.js"></script>
<script src="../../app/js/easyversion/web2frameadapter.js"></script>
<script src="../../app/js/easyversion/ajax.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanel.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanelAdapter.js"></script>
<SCRIPT src="../../app/js/easyversion/calendar3.js"></SCRIPT>
<link href="../../app/js/easyversion/resource/calendar.css" rel="stylesheet" type="text/css" />
<!--page include begin-->
<SCRIPT src="./document_topset.js"></SCRIPT>
<!--page include end-->
<style>
*{
	font-size:12px;
}
html,body{
	height:100%;
	width:100%;
	padding:0px;
	margin:0px;
	overflow-x:hidden;
	overflow-y:auto;
}
.container{
	height:100%;
	width:100%;
	overflow-x:hidden;
	overflow-y:auto;
}
.label{
	margin:10px 2px;
}
.topset_up,.topset_down{cursor:pointer;width:16px;height:16px;line-height:16px;margin-left:3px;float:left;padding:0;zoom:1;background-image: url(../images/icon/icons2.gif);background-repeat: no-repeat; }
.topset_up{background-position: 0 -400px;}
.topset_down{background-position: 0 -384px;}
.topset_row{padding-left:10px;}
#pri_set_deadline{margin-left:4px;}
.ext-ie7 .imgDate{margin:0;padding:0;zoom:1.0;cursor:pointer;}
#topset_order{margin-top:10px;overflow:visible;height:auto;}
</style>
<script language="javascript">
<!--
	var CurrChannelId = <%=nChannelId%>;
	var CurrDocId = <%=nDocumentId%>;
//-->
</script>
</head>
<body>
<div class="container">
	<div class="label" WCMAnt:param="metaviewdata_addedit.jsp.topSet">置顶设置:</div>
	<div>
		<div class="topset_row" _action="topset">
			<input type="radio" id="pri_set_0" name="TopFlag" value="0">
			<label for="pri_set_0" WCMAnt:param="metaviewdata_addedit.jsp.noSet">不置顶</label>
		</div>
		<div class="topset_row" _action="topset">
			<input type="radio" id="pri_set_2" name="TopFlag" value="2">
			<label for="pri_set_2" WCMAnt:param="metaviewdata_addedit.jsp.topForEver">永久置顶</label>
		</div>
		<div class="topset_row">
			<span _action="topset">
				<input type="radio" id="pri_set_1" name="TopFlag" value="1">
				<label for="pri_set_1" WCMAnt:param="TopSetHandler.jsp.topTimeVal">限时置顶</label>
			</span>
		</div>
		<div class="topset_row">
			<span id="pri_set_deadline" style="display:<%=(!bTopped || bTopForever)?"none":""%>">
				<input type="text" name="TopInvalidTime" id="TopInvalidTime" elname="限时置顶" value="<%=sTopInvalidTime%>" WCMAnt:paramattr="elname:TopSetHandler.jsp.topTimeVal"/>
				<button type="button" id="TopInvalidTime-btn"><img src="../images/icon/TRSCalendar.gif" border=0 alt=""></button>
				<script>					
					wcm.TRSCalendar.get({
						input : 'TopInvalidTime',
						handler : 'TopInvalidTime-btn',
						withtime : true,
						dtFmt : 'yyyy-mm-dd HH:MM:ss'
					});
				</script>
			</span>
		</div>
	</div>
	
	<div id="topset_order" style="display:<%=(!bTopped)?"none":"''"%>">
		<div class="label" WCMAnt:param="metaviewdata_addedit.jsp.topOrder">置顶排序:</div>
		<div id="topset_order_table">
			<table border=0 cellspacing=1 cellpadding=0 style="width:88%;table-layout:fixed;background:gray;">
				<thead>
					<tr bgcolor="#CCCCCC" align=center valign=middle>
						<td width="32" WCMAnt:param="metaviewdata_addedit.jsp.order">序号</td>
						<td WCMAnt:param="metaviewdata_addedit.jsp.docTitle">文档标题</td>
						<td width="40" WCMAnt:param="metaviewdata_addedit.jsp.listOrder">排序</td>
					</tr>
				</thead>
				<tbody id="topset_order_tbody">
				<%
					if(toppedDocuments==null||toppedDocuments.size()==0){
				%>
					<tr bgcolor="#FFFFFF" align=center valign=middle>
						<td>&nbsp;</td>
						<td align=left>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
				<%
					}else{
						for(int i=0, n=toppedDocuments.size(); i<n; i++){
							Document topDoc = (Document)toppedDocuments.getAt(i);
							if(topDoc==null)continue;
							int nTopDocId = topDoc.getId();
							String sDocTitle = CMyString.truncateStr(topDoc.getTitle(), 55, "...");
							String sDocTitle2 = PageViewUtil.toHtmlValue(topDoc.getTitle());
							String sDocTitle3 = PageViewUtil.toHtmlValue(sDocTitle);
							if(nTopDocId!=nDocumentId){
				%>
					<tr bgcolor="#FFFFFF" align=center valign=middle _docid="<%=nTopDocId%>" _doctitle="<%=sDocTitle3%>">
						<td><%=i+1%></td>
						<td align=left title="<%=nTopDocId%>-<%=sDocTitle2%>"><div style="overflow:hidden"><%=sDocTitle%></div></td>
						<td>&nbsp;</td>
					</tr>
				<%
								continue;
							}//end if
				%>
					<tr bgcolor="#FFFFCF" align=center valign=middle _currdoc="1">
						<td><%=i+1%></td>
						<td align=left style="color:red;" WCMAnt:param="metaviewdata_addedit.jsp.currDocument">--当前文档--</td>
						<td>
							<span class="topset_up" title="上移" _action="topsetUp" WCMAnt:paramattr="title:metaviewdata_addedit.jsp.upper">&nbsp;</span>
							<span class="topset_down" title="下移" _action="topsetDown" WCMAnt:paramattr="title:metaviewdata_addedit.jsp.lower">&nbsp;</span>
						</td>
					</tr>
				<%
						}//end for
					}
				%>
				</tbody>
			</table>	
		</div>
	</div>
</div>

<script language="javascript">
<!--
PgC.IsCanTop = <%=bIsCanTop%>;
PgC.TopFlag = !<%=bTopped%> ? 0 : (!<%=bTopForever%> ? 1 : 2);
$('pri_set_' + PgC.TopFlag).checked = true;	
PgC.init();
//-->
</script>
</BODY>
</HTML>