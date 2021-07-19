<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	errorPage="../include/error_for_dialog.jsp"%>
<%@ page import="java.util.StringTokenizer"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.resource.DocLevel"%>
<%@ page import="com.trs.components.wcm.resource.DocLevels"%>
<%@ page import="com.trs.infra.persistent.WCMFilter"%>
<%@ page import="com.trs.components.wcm.content.persistent.Channel"%>
<%@ page import="com.trs.components.wcm.content.persistent.Document"%>
<%@ page import="com.trs.webframework.controler.JSPRequestProcessor"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.trs.components.wcm.content.persistent.Documents"%>
<%@ page import="com.trs.infra.common.WCMTypes"%>
<%@ page import="com.trs.infra.util.CMyString"%>
<%@ page import="com.trs.presentation.util.RequestHelper"%>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定,登录校验,参数获取,都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%@include file="../system/doclevel_locale.jsp"%>
<%
    out.clear();
%><html xmlns="http://www.w3.org/1999/xhtml" xmlns:TRS=""
	xmlns:TRS_UI="">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title WCMAnt:param="change_doclevel.jsp.changelevel">改变密级</title>
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
			cmd : 'saveLevel',
			name : wcm.LANG.DOCUMENT_PROCESS_1 || '执行'
		}],
		size : [440, 90]
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
		height:75px;
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
	Document currDocument = null;	
	int nDocLevelId = 0;
	String strLevelName = "";
	String sDocumentIds = currRequestHelper.getString("ObjectIds");

	//wenyh@2007-06-26 图片复用,修正一下描述
	String sObjDesc = "true".equals(request.getParameter("IsPhoto"))?LocaleServer.getString("change_status.label.photo", "图片"):LocaleServer.getString("change_status.label.document", "文档");
	//hxj@2007-10-9 元数据复用,修正一下描述
	if(currRequestHelper.getString("objDesc") != null){
		sObjDesc = currRequestHelper.getString("objDesc");
	}
	Channel currChannel = null;	
	// 是否只有一篇文档
	boolean bOnlyOne = true;
	if(sDocumentIds.indexOf(',') == -1){//单篇
		int nDocumentId = Integer.parseInt(sDocumentIds);
		currDocument	= Document.findById(nDocumentId);
		if(currDocument == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("object.not.found", "没有找到指定的{1} [ID={0}]."), new String[]{String.valueOf(nDocumentId),WCMTypes.getLowerObjName(Document.OBJ_TYPE)}));
		}
		//else
		if(currDocument != null) {
			nDocLevelId = currDocument.getDocLevleId();
			strLevelName = currDocument.getDocLevelName();
		}
	} else {//多篇
		bOnlyOne = false;
		//获取多篇文档的共同密级
		DocLevel docLevel = makeLevelOfDocuments(sDocumentIds);
		if(docLevel!=null){
			nDocLevelId = docLevel.getId();
			strLevelName = docLevel.getLName();
		}

	}
	//文档密级集合
	DocLevels docLevels = DocLevels.createNewInstance(loginUser);
	try{
		JSPRequestProcessor processor = new JSPRequestProcessor(request, response);
		String sServiceId = "wcm61_doclevel", sMethodName = "query";
		docLevels = (DocLevels)processor.excute(sServiceId, sMethodName);
	}catch(Exception e){
		e.printStackTrace();
	}
%>
<%
	if(bOnlyOne){
%>
	<DIV>
		<SPAN WCMAnt:param="change_doclevel.jsp.currlevel">当前密级为：</SPAN>
		<SPAN style="color:red"><%=getDocLevelLocale(strLevelName)%></SPAN>
	</DIV>
<%
}else if(nDocLevelId==0){
%>
	<DIV><SPAN WCMAnt:param="change_doclevel.jsp.setnewlevelsnotsame">批量设置新的密级,目前密级不尽相同.</SPAN></DIV>
<%
	}
	else{
%>
	<DIV>
		<SPAN WCMAnt:param="change_doclevel.jsp.setnewlevelscurrsamelevel">批量设置新的密级,目前统一密级为:</SPAN><SPAN style="color:red"><%=strLevelName%></SPAN>
	</DIV>
<%
	}
%>
	<DIV class="changeTo">
		<SPAN WCMAnt:param="change_doclevel.jsp.changelevelto">改变密级为：</SPAN>
		<SPAN id="levels">
		<%
			for (int i = 0, nSize = docLevels.size(); i < nSize; i++) {
				DocLevel docLevel = (DocLevel) docLevels.getAt(i);
				if (docLevel == null)
					continue;
				int nLevelId = docLevel.getId();
		%>
		<input id="<%=nLevelId%>" type="radio" name="DOCLEVEL" value="<%=nLevelId%>" <%=isChecked(nLevelId,nDocLevelId)%>><label for="<%=nLevelId%>" style="cursor:hand">&nbsp;<%=CMyString.transDisplay(getDocLevelLocale(docLevel.getLName()))%></label>&nbsp;&nbsp;
		<%
			}
		%>
		</SPAN>
	</DIV>
</td>
	</tr>
</tbody>
</table>
<script>
	function saveLevel(){
		var radios = document.getElementsByName("DOCLEVEL");
		var sDocLevelId = '';
		for(var i=0;i<radios.length;i++){
			if(radios[i].checked){
				sDocLevelId = radios[i].value;
				break;
			}
		}
		if(sDocLevelId==''){
			Ext.Msg.alert(String.format('未选中任何{0}密级' , '<%=CMyString.filterForJs(sObjDesc)%>'.toLowerCase()));
			return false;
		}
		var oPostData = {
			'DocumentIds': '<%=CMyString.filterForJs(sDocumentIds)%>',
			'DocLevel': sDocLevelId
		};
		var sServiceId = 'wcm61_viewdocument';
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.Call(sServiceId, 'changeDocLevel', oPostData, true,
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
     * 获取多篇文档在指定栏目的密级
     * @param _sDocId
     *  多篇文档ID
     * @return
     *  如果只有一个密级,返回密级对象;否则,返回null
     * @throws WCMException
     */
    public static DocLevel makeLevelOfDocuments(String _sDocId)
		throws WCMException{
		try{
			String sWhere = "exists("//
                + " select DocLevel from WCMDocument "
                + " Where XWCMDocLevel.DOCLEVELID=WCMDocument.DocLevel"
                + " and DocId in(" + _sDocId + ")"
                + ")";

			DocLevels docLevels = DocLevels.openWCMObjs(null, new WCMFilter("", sWhere, ""));
			int nSize = docLevels.size();
			// 如果没有找到对应的文档密级，说明都是null，需要按照普通密级处理
			if(nSize == 0){
				return DocLevel.findById(DocLevel.NORMAL_DOClEVEL_ID);
			}
			if(nSize>1){
				return null;
			}
			
			// 由于历史数据中文档的DocLevel为null，所以需要加上下面的判断
			Documents documents = Documents.openWCMObjs(null, new WCMFilter("","DocId in(" + _sDocId + ") and DocLevel is null", "", "DocId"));
			if(documents.size() > 0){
				return null;
			}

			return (DocLevel)docLevels.getAt(0);
		}catch(Exception e){
			e.printStackTrace();
			throw new WCMException(LocaleServer.getString("change_doclevel.jsp.getdoclevelex", "获取文档密级时，出现异常"),e);
		}
        
    }

	private  String isChecked(int _nDocLevelId,int _nCurLevelId)
		throws WCMException {
		if(_nDocLevelId == _nCurLevelId){
			return "checked";
		}
		// 密级为0时，说明没有设置密级，则默认为普通级别
		if(_nCurLevelId == 0){
			return _nDocLevelId == DocLevel.NORMAL_DOClEVEL_ID ? "checked" : "";
		}
			return "";
	}
%>