<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@include file="../../../document/document_addedit_import.jsp"%>
<%!boolean IS_DEBUG = false;%>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../../../include/public_server.jsp"%>
<%@include file="../../../../include/validate_publish.jsp"%>
<%
try{
%>
<%@include file="attachments_relations_include.jsp"%>
<%
	out.clear();
%>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:TRS="" xmlns:TRS_UI="">
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="javascript">
<!--
	var index = window.location.href.lastIndexOf("/");
	var sHref = window.location.href.substr(0, index);
	href = sHref + "/../../";
	document.write("<base href='"  + href + "'/>");
//-->
</script>
<TITLE>TRS WCM V6 <%=(nDocumentId>0)?"编辑文档":"新建文档"%></TITLE>
<link href="../css/common.css" rel="stylesheet" type="text/css" />
<link href="../css/document_addedit.css" rel="stylesheet" type="text/css" />
<script src="../js/com.trs.util/Common.js"></script>
<%
	//!@include file="../../../individuation/init_individuation_config.jsp"
%>
<script>
	window.oldActualTop = top.actualTop;
	top.actualTop = window;
	Event.observe(window, 'unload', function(){
		top.actualTop = window.oldActualTop;
	});
</script>
<script label="Imports">
	$import('com.trs.logger.Logger');
	$import('com.trs.dialog.Dialog');
	$import('com.trs.web2frame.DataHelper');
	$import('com.trs.web2frame.TempEvaler');
	$import('com.trs.wcm.Web2frameDefault');
	$import('com.trs.util.JSON');
	$import('com.trs.util.XML');
	$import('com.trs.util.CommonHelper');
	$import('com.trs.wcm.MessageCenter');
	$import('com.trs.wcm.FloatPanelTop');
	$import('com.trs.wcm.util.FileUploadHelper');
	$import('com.trs.wcm.util.LockedUtil');
	$import('com.trs.util.UITransformer');
	$import('com.trs.util.YUIConnection');
	$import('com.trs.wcm.SimpleDragger');
	$import('com.trs.wcm.ui.ButtonGroup');
	$import('com.trs.wcm.ui.Buttons');
	$import('com.trs.wcm.BubblePanel');
	$import('opensource.fckpanel');
	$import('com.trs.validator.Validator');
	$import('wcm52.calendar.Calendar');
</script>
<!--<script src="./document_addedit.js" label="PageScope"></script>//-->
</HEAD>
<BODY>
<%
	String _url_ = "attachments.html";
	String _objType_ = "Appendixs";
	if(request.getParameter("_objType_") != null 
			&& request.getParameter("_objType_").equalsIgnoreCase("relations")){
		_url_ = "relations.html";
		_objType_ = "Relations";
	}

%>
<%if(_objType_.equals("Appendixs")){%>
	<textarea style="display:none" id="appendix_<%=Appendix.FLAG_DOCAPD%>">
	<%=getAppendixsXml(currDocument,Appendix.FLAG_DOCAPD)%>
	</textarea>
	<textarea style="display:none" id="appendix_<%=Appendix.FLAG_DOCPIC%>">
	<%=getAppendixsXml(currDocument,Appendix.FLAG_DOCPIC)%>
	</textarea>
	<textarea style="display:none" id="appendix_<%=Appendix.FLAG_LINK%>">
	<%=getAppendixsXml(currDocument,Appendix.FLAG_LINK)%>
	</textarea>
	<SCRIPT>
		var Appendixs = null;
		try{
			Appendixs = {
				Type_<%=Appendix.FLAG_DOCAPD%>:
					com.trs.util.JSON.parseXml(
						com.trs.util.XML.loadXML($('appendix_<%=Appendix.FLAG_DOCAPD%>').value)),
				Type_<%=Appendix.FLAG_DOCPIC%>:
					com.trs.util.JSON.parseXml(
						com.trs.util.XML.loadXML($('appendix_<%=Appendix.FLAG_DOCPIC%>').value)),
				Type_<%=Appendix.FLAG_LINK%>:
					com.trs.util.JSON.parseXml(
						com.trs.util.XML.loadXML($('appendix_<%=Appendix.FLAG_LINK%>').value))
			}
		}catch(err){
			Appendixs = {
				Type_<%=Appendix.FLAG_DOCAPD%>:{},
				Type_<%=Appendix.FLAG_DOCPIC%>:{},
				Type_<%=Appendix.FLAG_LINK%>:{}
			}
		}
	</SCRIPT>
<%}%>
<%if(_objType_.equals("Relations")){%>
	<textarea style="display:none" id="relations">
	<%//=getRelationsXML(currDocument)%>
	<%=getRelationsXML(loginUser, request.getParameter("RelationDocIds"))%>
	</textarea>
	<SCRIPT>
		//缓存相关文档管理中的数据
		var Relations = null;
		try{
			Relations = com.trs.util.JSON.parseXml(
					com.trs.util.XML.loadXML($('relations').value));
		}catch(err){
			Relations = {};
		}
	</SCRIPT>
<%}%>
<SCRIPT>
	function init(oParams){
		if(oParams){
			if(oParams["Appendixs"]){
				Appendixs = Object.clone(oParams["Appendixs"], true);
			}
			if(oParams["Relations"]){
				Relations = Object.clone(oParams["Relations"], true);
			}
		}
		window.frames["myimage_window"].init();
	}

	var ObjType = '<%=_objType_%>';
	//当前编辑中的文档ID,新文档为0
	var bIsReadOnly = <%=bIsReadOnly%>;
	var bEnablePicLib = <%=bEnablePicLib%>;
	var bEnableFlashLib = <%=bEnableFlashLib%>;
	var CurrDocId = '<%=nDocumentId%>';
	var DocChannelId = '<%=(docChannel!=null)?docChannel.getId():0%>';
	var CurrChannelId = '<%=(currChannel!=null)?currChannel.getId():0%>';
	var bIsCanTop = <%=bIsCanTop%>;
	var bIsCanPub = <%=bIsCanPub%>;
	var bIsNewsOrPics = <%=isNewsOrPics%>;
	PageContext.TitleColor = '<%=sTitleColor%>';
	window.UserName = '<%=loginUser.getName()%>';
</SCRIPT>
<iframe src="../include/processbar.html" id='processbar' frameborder="0" style='position:absolute;left:0;top:0;width:100%;height:100%;display:none' allowtransparency="true"></iframe>
<iframe src="../include/window.html" id='floatpanel' frameborder="0" style='position:absolute;left:0;top:0;width:100%;height:100%;display:none' allowtransparency="true"></iframe>
<!--
<iframe src="document_insert_image_window.html" id='myimage_window' frameborder="0" style='position:absolute;left:0;top:0;width:100%;height:100%;display:none' allowtransparency="true"></iframe>
-->
<iframe src="application/page/<%=_url_%>" id='myimage_window' frameborder="0" style='position:absolute;left:0;top:0;width:100%;height:100%;' allowtransparency="true"></iframe>
</BODY>
</HTML>
<%
}catch(Throwable ex){
	int nDocumentId	  = currRequestHelper.getInt("DocumentId", 0);
%>
<HTML xmlns="http://www.w3.org/1999/xhtml" xmlns:TRS="">
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="javascript">
<!--
	var index = window.location.href.lastIndexOf("/");
	var sHref = window.location.href.substr(0, index);
	href = sHref + "/../../";
	document.write("<base href='"  + href + "'/>");
//-->
</script>
<TITLE>TRS WCM V6 编辑/修改文档</TITLE>
<link href="../css/common.css" rel="stylesheet" type="text/css" />
<link href="../css/document_addedit.css" rel="stylesheet" type="text/css" />
<script src="../js/com.trs.util/Common.js"></script>
<script>
	window.oldActualTop = top.actualTop;
	top.actualTop = window;
	Event.observe(window, 'unload', function(){
		top.actualTop = window.oldActualTop;
	});
</script>
<script label="Imports">
	$import('com.trs.dialog.Dialog');
	$import('com.trs.wcm.util.LockedUtil');
</script>
</HEAD>
<BODY>
<%
	int errorCode = 0;
	if(ex instanceof WCMException){
		WCMException myEx = (WCMException)ex;
		errorCode = myEx.getErrNo();
	}
%>
<textarea id="errorMsg" style="display:none">
<%=ex.getMessage()%>
</textarea>
<textarea id="errorStackTrace" style="display:none">
<%=WCMException.getStackTraceText(ex)%>
</textarea>
<script>
	var CurrDocId = '<%=nDocumentId%>';
	if(CurrDocId!=0){
//		LockerUtil.unlock(CurrDocId,605);
	}
	FaultDialog.show({
		code		: <%=errorCode%>,
		message		: $('errorMsg').value,
		detail		: $('errorStackTrace').value,
		suggestion  : ''
	}, '与服务器交互时出错啦！',function(){top.window.close();});
</script>
</BODY>
</HTML>
<%
}
%>