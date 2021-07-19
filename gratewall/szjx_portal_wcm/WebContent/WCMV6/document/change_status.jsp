<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
<%@ page import="java.util.StringTokenizer" %>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.resource.Status" %>
<%@ page import="com.trs.components.wcm.resource.Statuses" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc" %>
<%@ page import="com.trs.presentation.util.RequestHelper" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%
	out.clear();
%><html xmlns="http://www.w3.org/1999/xhtml" xmlns:TRS="" xmlns:TRS_UI="">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>改变状态</title>
<script src="../js/com.trs.util/Common.js"></script>
<script>
//	$import('com.trs.ajaxframe.TagParser');
	$import('com.trs.web2frame.DataHelper');
	$import('com.trs.web2frame.TempEvaler');
	$import('com.trs.wcm.Web2frameDefault');
	$import('com.trs.dialog.Dialog');
	$import('com.trs.wcm.FloatPanel');
	$import('com.trs.wcm.MessageCenter');
	$import('com.trs.wcm.ProcessBar');
</script>
<style>
	body,td{
		font-size:12px;
	}
	div{
		width:100%;
		display:block;
		line-height:24px;
		height:24px;
	}
	SPAN{
		float:left;
	}
</style>
</head>
<body>
<table border=0 cellspacing=0 cellpadding=0 width="100%" height="100%">
<tbody>
	<tr valign="middle">
		<td>
<%
	ChnlDoc currChnlDoc = null;	
	int nStatusId = 0;
	String strStatusName = "";
	String sChnlDocIds = currRequestHelper.getString("ObjectIds");
	String sChannelIds = currRequestHelper.getString("ChannelIds");

	//wenyh@2007-06-26 图片复用,修正一下描述
	String sObjDesc = "true".equals(request.getParameter("IsPhoto"))?"图片":"文档";
	//hxj@2007-10-9 元数据复用,修正一下描述
	if(currRequestHelper.getString("objDesc") != null){
		sObjDesc = currRequestHelper.getString("objDesc");
	}

	Channel currChannel = null;	
	if(sChnlDocIds.indexOf(',') == -1){//单篇
		int nChnlDocId = Integer.parseInt(sChnlDocIds);
		currChnlDoc	= ChnlDoc.findById(nChnlDocId);
		if(currChnlDoc == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取ID为["+nChnlDocId+"]的"+sObjDesc+"失败！");
		}
		//else
		if(currChnlDoc != null) {
			nStatusId = currChnlDoc.getStatusId();
			strStatusName = currChnlDoc.getStatusName();
		}
	}
	else{//多篇
		if(sChannelIds == null) {
			sChannelIds = "";
		}

		//获取多篇文档的共同状态
		if(sChannelIds != null && sChannelIds.trim() != "") {
			Status status = makeStatusOfDocuments(sChannelIds, sChnlDocIds);
			if(status!=null){
				nStatusId = status.getId();
				strStatusName = status.getDisp();
			}
		}

	}
	//文档状态
	WCMFilter filter = new WCMFilter("", "", Status.DB_ID_NAME, Status.DB_ID_NAME);
	Statuses statuses = Statuses.openWCMObjs(loginUser, filter);
	boolean bFirst = true;
%>
<%
	if(nStatusId==0){
%>
	<DIV><SPAN>批量设置新的<%=CMyString.transDisplay(sObjDesc)%>状态,目前状态不尽相同.</SPAN></DIV>
<%
	}
	else if(sChnlDocIds.indexOf(',')!=-1){
%>
	<DIV><SPAN>批量设置新的<%=CMyString.transDisplay(sObjDesc)%>状态,目前统一状态为:</SPAN><SPAN><%=strStatusName%></SPAN><SPAN>.</SPAN></DIV>
<%
	}
	else{
%>
	<DIV>
		<SPAN>当前<%=CMyString.transDisplay(sObjDesc)%>状态为：</SPAN>
		<SPAN><%=strStatusName%></SPAN>
	</DIV>
<%
	}
%>
	<DIV>
		<SPAN>改变状态为：</SPAN>
		<SPAN id="statuses">
		</SPAN>
	</DIV>
</td>
	</tr>
</tbody>
</table>
	<textarea id="statues_template" style="display:none;">
	<for select="Statuses.Status" blankText="">
		<record num="3">
			<input id="DOCSTATUS_{#StatusId}" type="radio" name="DOCSTATUS" value="{#StatusId}" {@isChecked(#StatusId)}><label for="DOCSTATUS_{#StatusId}" style="cursor:hand">&nbsp;{@getStatusName(#StatusId)}</label>&nbsp;&nbsp;
		</record>
		<br>
	</for>
	</textarea>
<script>
var allDocStatus = {
<%
	for (int i = 0, nSize = statuses.size(); i < nSize; i++) {
		Status status = (Status) statuses.getAt(i);
		if (status == null)
			continue;
%>
	"<%=status.getId()%>":"<%=status.getDisp()%>",
<%
	}
%>
	"0":"未知"
};
	function isChecked(_nStatusId){
		if(_nStatusId=='<%=nStatusId%>'){
			return 'checked';
		}
		return '';
	}
	function getStatusName(_sStatusId){
		return allDocStatus[_sStatusId];
	}
//	Event.observe(window,'unload',function(){
//		FloatPanel.removeCloseCommand();
//		FloatPanel.removeCommand('savebtn');
//	});
	function saveStatus(){
		var radios = document.getElementsByName("DOCSTATUS");
		var sDocStatusId = '';
		for(var i=0;i<radios.length;i++){
			if(radios[i].checked){
				sDocStatusId = radios[i].value;
				break;
			}
		}
		if(sDocStatusId==''){
			$timeAlert('未选中任何<%=CMyString.filterForJs(sObjDesc)%>状态');
			return false;
		}
		var oPostData = {
			'ObjectIds': getParameter('ObjectIds'),
			'StatusId': sDocStatusId
		};
		var sServiceId = 'wcm6_viewdocument';
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.Call(sServiceId, 'changeStatus', oPostData, true,
			function(_transport, _json){
				$MessageCenter.sendMessage('main', 'PageContext.updateCurrRows', 'PageContext', null);
				FloatPanel.close(true);
			},
			function(_transport,_json){
				$render500Err(_transport,_json);
				FloatPanel.close(true);
			}
		);
		return false;
	}
	var oHelper = new com.trs.web2frame.BasicDataHelper();
	oHelper.Call('wcm6_status','queryDocumentStatuses',
		{"ChnlDocIds":'<%=CMyString.filterForJs(sChnlDocIds)%>',"ChannelIds" : "<%=CMyString.filterForJs(sChannelIds)%>"}, true, 
		function(_transport,_json){
			var sValue = TempEvaler.evaluateTemplater('statues_template', _json,null);
			Element.update($('statuses'),sValue);
			FloatPanel.addCloseCommand();
			FloatPanel.addCommand('savebtn', '执行', 'saveStatus',null);
		}
	);
</script>
</BODY>
</HTML>
<%!
	/**
     * 获取多篇文档在指定频道的状态
     * @param _nChannelId
     *  指定频道ID
     * @param _sDocId
     *  多篇文档ID
     * @return
     *  如果只有一个状态，返回状态对象；否则，返回null
     * @throws WCMException
     */
    public static Status makeStatusOfDocuments(String _sChannelIds, String _sDocId)
            throws WCMException {
		/*
		String[] arrChnlIds = _sChannelIds.split(",");
		int nChannelId = 0;
		if (arrChnlIds.length == 1){
			nChannelId = Integer.parseInt(arrChnlIds[0]);
		}
			
        Channel channel = null;
        if (nChannelId > 0) {
            channel = Channel.findById(nChannelId);
            if (channel == null) {
                throw new WCMException("指定的频道[" + nChannelId + "]不存在！");
            }
            // 检索频道传入的频道号忽略
            if (channel.isOnlySearch()) {
                channel = null;
            }
        }
		*/
        String sWhere = "exists("//
                + " select DocStatus from WCMChnlDoc "
                + " Where WCMStatus.StatusId=WCMChnlDoc.DocStatus "
                + " and RecId in(" + _sDocId + ")"//
                //+ " and " + (channel == null ? "Modal=1" : "ChnlId="+nChannelId)
//                + " " + (channel == null ? "" : " and ChnlId="+nChannelId)//
                + ")";

        Statuses statuses = Statuses.openWCMObjs(null, new WCMFilter("", sWhere, ""));
        int nSize = statuses.size();
        if(nSize == 0 || nSize>1){
            return null;
        }
        
        return (Status)statuses.getAt(0);
    }
%>