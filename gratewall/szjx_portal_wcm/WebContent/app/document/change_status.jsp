<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	errorPage="../include/error_for_dialog.jsp"%>
<%@ page import="java.util.StringTokenizer"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.resource.Status"%>
<%@ page import="com.trs.components.wcm.resource.Statuses"%>
<%@ page import="com.trs.infra.persistent.WCMFilter"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel"%>
<%@ page import="com.trs.components.wcm.content.persistent.ChnlDoc"%>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.trs.components.wcm.content.persistent.Document"%>
<%@ page import="com.trs.infra.common.WCMTypes"%>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@ page import="com.trs.presentation.util.RequestHelper"%>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定,登录校验,参数获取,都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%@include file="../system/status_locale.jsp"%>
<%
    out.clear();
%><html xmlns="http://www.w3.org/1999/xhtml" xmlns:TRS=""
	xmlns:TRS_UI="">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title WCMAnt:param="change_status.jsp.title">改变状态</title>
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<script src="../../app/js/data/locale/document.js"></script>
<!--FloatPanel Inner Start-->
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanelAdapter.js"></script>
<link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">
<!--AJAX-->
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<!--wcm-dialog start-->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<!--wcm-dialog end-->
<script>
	window.m_fpCfg = {
		m_arrCommands : [{
			cmd : 'saveStatus',
			name : wcm.LANG.DOCUMENT_PROCESS_1 || '执行'
		}],
		size : [400, 120]
	};	
</script>
<style>
	body,td{
		font-size:12px;
	}
	div{
		width:100%;
		display:block;
		height:22px;
	}
	SPAN{
		float:left;
		line-height:22px;
	}
	.changeTo{
		height:90px;
		overflow:auto;
		padding-bottom:5px;
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
	//String sDocumentIds = currRequestHelper.getString("DocumentId");

	//wenyh@2007-06-26 图片复用,修正一下描述
	String sObjDesc = "true".equals(request.getParameter("IsPhoto"))?LocaleServer.getString("change_status.label.photo", "图片"):LocaleServer.getString("change_status.label.document", "文档");
	//hxj@2007-10-9 元数据复用,修正一下描述
	if(currRequestHelper.getString("objDesc") != null){
		sObjDesc = currRequestHelper.getString("objDesc");
	}
	Channel currChannel = null;	
	if(sChnlDocIds.indexOf(',') == -1){//单篇
		int nChnlDocId = Integer.parseInt(sChnlDocIds);
		currChnlDoc	= ChnlDoc.findById(nChnlDocId);
		//int nDocumentId = Integer.parseInt(sDocumentIds);
		if(currChnlDoc == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("object.not.found", "没有找到指定的{1} [ID={0}]."), new String[]{String.valueOf(nChnlDocId),WCMTypes.getLowerObjName(ChnlDoc.OBJ_TYPE)}));
		}
		//else
		if(currChnlDoc != null) {
			nStatusId = currChnlDoc.getStatusId();
			strStatusName = getStatusLocale(currChnlDoc.getStatusName());
		}
	} else {//多篇
		if(sChannelIds == null) {
			sChannelIds = "";
		}

		//获取多篇文档的共同状态
		if(sChannelIds != null && sChannelIds.trim() != "") {
			Status status = makeStatusOfDocuments(sChannelIds, sChnlDocIds);
			if(status!=null){
				nStatusId = status.getId();
				strStatusName = getStatusLocale(status.getDisp());
			}
		}

	}
	//文档状态
	JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
	String sServiceId = "wcm6_status", sMethodName = "queryDocumentStatuses";
	HashMap parameters = new HashMap();
	parameters.put("ChannelIds", sChannelIds);
	parameters.put("ChnlDocIds", sChnlDocIds);
	Statuses statuses = (Statuses) processor.excute(sServiceId,
			sMethodName, parameters);
%>
<%
	if(nStatusId==0){
%>
	<DIV><SPAN WCMAnt:param="change_status.jsp.statusDiff">批量设置新的状态,目前状态不尽相同.</SPAN></DIV>
<%
	}
	else if(sChnlDocIds.indexOf(',')!=-1){
%>
	<DIV><SPAN WCMAnt:param="change_status.jsp.statusSame">批量设置新的状态,目前统一状态为:</SPAN><SPAN style="color:red"><%=strStatusName%></SPAN></DIV>
<%
	}
	else{
%>
	<DIV>
		<SPAN WCMAnt:param="change_status.jsp.currStatus">当前状态为：</SPAN>
		<SPAN style="color:red"><%=strStatusName%></SPAN>
	</DIV>
<%
	}
%>
	<DIV class="changeTo">
		<SPAN WCMAnt:param="change_status.jsp.changeStatus">改变状态为：</SPAN>
	<%if(statuses.size() <=0 ){%>
		<SPAN WCMAnt:param="change_status.jsp.NoStatus" style="color: red;">没有找到可以改变到的状态！<BR/>可能是您对所选的文档没有相同的修改状态权限导致</SPAN>
	<%}{%>
		<SPAN id="statuses">
		<%
			for (int i = 0, nSize = statuses.size(); i < nSize; i++) {
				Status status = (Status) statuses.getAt(i);
				if (status == null)
					continue;
				int nCurrStatusId = status.getId();
				if(nCurrStatusId == Status.STATUS_ID_DRAFT)
					continue;
		%>
		<input id="<%=nCurrStatusId%>" type="radio" name="DOCSTATUS" value="<%=nCurrStatusId%>" <%=isChecked(nCurrStatusId,nStatusId,i)%>><label for="<%=status.getId()%>" style="cursor:hand">&nbsp;<%=CMyString.transDisplay(getStatusLocale(status.getDisp()))%></label>&nbsp;&nbsp;
		<%
				if((i+1) % 3 ==0){
		%>
		<br/>
		<%
				}
			}
		}
		%>
		</SPAN>
	</DIV>
</td>
	</tr>
</tbody>
</table>
<script>
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
			Ext.Msg.alert(String.format(wcm.LANG.DOCUMENT_PROCESS_2 || '未选中任何{0}状态' , '<%=CMyString.filterForJs(sObjDesc)%>'.toLowerCase()));
			return false;
		}
		var oPostData = {
			'ObjectIds': '<%=CMyString.filterForJs(sChnlDocIds)%>',
			'StatusId': sDocStatusId
		};
		var sServiceId = 'wcm6_viewdocument';
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.Call(sServiceId, 'changeStatus', oPostData, true,
			function(_transport, _json){
				notifyFPCallback(_transport);
				FloatPanel.close(true);
			},
			function(_transport,_json){
				$render500Err(_transport,_json);
				FloatPanel.close(true);
			}
		);
		return false;
	}
</script>
</BODY>
</HTML>
<%!
	/**
     * 获取多篇文档在指定栏目的状态
     * @param _nChannelId
     *  指定栏目ID
     * @param _sDocId
     *  多篇文档ID
     * @return
     *  如果只有一个状态,返回状态对象;否则,返回null
     * @throws WCMException
     */
    public static Status makeStatusOfDocuments(String _sChannelIds, String _sDocId)
            throws WCMException {
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
	private  String isChecked(int _nStatusId,int _nCurStatusId,int _nCount)
		throws WCMException {
	if(_nStatusId == _nCurStatusId){
		return "checked";
	}
	if(_nCurStatusId == 0){
		return _nCount == 0 ? "checked" : "";
	}
		return "";
	}
%>