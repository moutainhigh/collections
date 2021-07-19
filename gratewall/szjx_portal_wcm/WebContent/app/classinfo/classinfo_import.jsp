<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.Date" %>
<!------- WCM IMPORTS END ------------>

<%@include file="../include/public_server.jsp"%>
<%
	out.clear();
%>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<title WCMAnt:param="classinfo_import.jsp.title">分类法导入</title>
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/classinfo.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<script src="../../app/js/source/wcmlib/components/ProcessBar.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanelAdapter.js"></script>
<link rel="stylesheet" type="text/css" href="../css/wcm-common.css">
<script src="../../app/js/source/wcmlib/com.trs.validator/lang/cn.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/Validator.js"></script>
<link href="../../app/js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script src="../js/source/wcmlib/util/YUIConnection.js"></script>
<script src="../js/source/wcmlib/com.trs.dialog/FaultDialog.js"></script>

<!--wcm-dialog start-->
    <SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
    <SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
    <SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
    <SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
    <link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
    <link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
    <link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<!--wcm-dialog end-->
<script language="javascript">
window.m_fpCfg = {
	m_arrCommands : [{
		cmd : 'importClassInfos',
		name : wcm.LANG.CLASSINFO_22 || '确定'
	}],
	size : [450, 150]	
};
</script>
<style>
	body{
		font-size:12px;		
	}
	.row{
		width:100%;
        margin:10px 0px;
		display:block;
		line-height:24px;		
	}
	.label{
        width:140px;
	}
</style>
<script language="javascript">
<!--
var DocFileName = null;
var PageContext = {
		params: {}
	};
Object.extend(PageContext.params, {
	ownerId : getParameter('OwnerId'),
	ownerType : getParameter('OwnerType')
}); 

function importClassInfos(){
	var sDocFileName = $("DocFile").value;
	try{
		FileUploadHelper.validFileExt(sDocFileName, ".txt,.zip,.xml");
	}catch(err){
		Ext.Msg.alert(err.message, function(){
		});
		return false;
	}
	var isSSL = location.href.indexOf("https://")!=-1;
	var callBack1 = {
		"upload":function(_transport){
			try{
				DocFileName = eval(_transport.responseText);
			}catch(error){
				DocFileName = [];
			}
			var bAlert = FileUploadHelper.fileUploadedAlert(DocFileName[0]||"",function(){
				//ProcessBar.next();
			});
			if(bAlert)return;
			var callBack2 = {
				"upload":function(_transport){
					var importType = '';
					var tmp = document.getElementsByName("importType");
					for(var i=0;i<tmp.length;i++){
						if(tmp[i].checked){
							importType=tmp[i].value;
							break;
						}
					}
					var sContainCode = '';
					var tmp = document.getElementsByName("ContainCode");
					for(var i=0;i<tmp.length;i++){
						if(tmp[i].checked){
							sContainCode=tmp[i].value;
							break;
						}
					}
					var oPostData = {
						"RealFileName": DocFileName[1] || "",
						"ImportFile" : DocFileName[0] || '',
						"ContainCode" : sContainCode,
						"importType" : importType
					};
					Object.extend(oPostData, PageContext.params);
					var oHelper = new com.trs.web2frame.BasicDataHelper();
					ProcessBar.start(wcm.LANG.CLASSINFO_34 || "导入分类法");
					oHelper.Call('wcm61_classinfo','importClassInfos',oPostData,true,
						function(_transport,_json){
							ProcessBar.exit();
							var aTop = $MsgCenter.getActualTop();							
							Ext.Msg.report(_json, (wcm.LANG.CLASSINFO_23 || '分类法导入结果'), function(){
								notifyFPCallback();
								FloatPanel.close();
							});
							FloatPanel.hide();							
						},
						function(_transport,_json){
							$render500Err(_transport,_json);
							FloatPanel.close(true);
						}
					);
				}
			}
			callBack2.upload();
		}
	}
	YUIConnect.setForm('frmUploadDocFile',true,isSSL);
	YUIConnect.asyncRequest('POST','../system/file_upload_dowith.jsp?FileParamName=DocFile&ResponseType=2',callBack1);
	return false;
}

</script>
</head>

<body>
<%int nIdx=0;%>
	<DIV class="row">
		<SPAN><%=++nIdx%>、</span><span WCMAnt:param="classinfo_import.jsp.selectwilluploadfiletxtzipxmlformat">选择您要装载的文件(支持txt,zip,xml格式文件)：</span>
		<SPAN style="margin-left:20px;display:block;">
			<FORM name="frmUploadDocFile" style="margin:0;padding:0;display:" enctype='multipart/form-data'>
				<input type="file" id="DocFile" name="DocFile" style="width:400px;">
			</FORM>
		</SPAN>
	</DIV>

	<DIV class="row">
		<SPAN><%=++nIdx%>、</span><span WCMAnt:param="classinfo_import.jsp.doIfSame">出现重复分类法名称时</span>
		<SPAN>
			<input type="radio" name="importType" value="0" checked> <span WCMAnt:param="classinfo_import.jsp.skip">跳过</span>
			<input type="radio" name="importType" value="1"> <span WCMAnt:param="classinfo_import.jsp.rewrite">覆盖</span>
			<input type="radio" name="importType" value="2"> <span WCMAnt:param="classinfo_import.jsp.new">新建</span>
		</SPAN>
	</DIV>

	<DIV class="row">
		<SPAN><%=++nIdx%>、</span><span WCMAnt:param="classinfo_import.jsp.differ">以分类代码区分层次</span>
		<SPAN>
			<input type="radio" name="ContainCode" value="true" checked><span WCMAnt:param="classinfo_import.jsp.yes"> 是</span>
			<input type="radio" name="ContainCode" value="false"> <span WCMAnt:param="classinfo_import.jsp.no">否</span>
		</SPAN>
	</DIV>
</BODY>
</HTML>