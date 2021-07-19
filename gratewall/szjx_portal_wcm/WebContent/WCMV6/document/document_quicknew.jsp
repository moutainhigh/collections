<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"
	errorPage="../include/error.jsp"%>
<%@include file="../../include/public_server.jsp"%>
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>文档导入</TITLE>
<style>
	body{
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
<script src="../js/com.trs.util/Common.js"></script>
<script>
//	$import('com.trs.ajaxframe.TagParser');
	$import('com.trs.web2frame.DataHelper');
	$import('com.trs.web2frame.TempEvaler');
	$import('com.trs.wcm.Web2frameDefault');
	$import('com.trs.dialog.Dialog');
	$import('com.trs.wcm.MessageCenter');
	$import('com.trs.wcm.util.FileUploadHelper');
	$import('com.trs.util.YUIConnection');
</script>
<script language="javascript">
<!--
FloatPanel.addCloseCommand();
FloatPanel.addCommand('importBtn', '导入', 'importDocument',null);
Event.observe(window,'unload',function(){
	FloatPanel.removeCloseCommand();
	FloatPanel.removeCommand('importBtn');
});
var DocFileName = null;
function importDocument(){
	ProcessBar.init('执行进度，请稍候...');
	var sDocFileName = $("DocFile").value;
	if(!FileUploadHelper.validFileExt(sDocFileName, "doc(.)?,rtf")){
		return false;
	}
	ProcessBar.addState('正在上传文件:'+sDocFileName);
	ProcessBar.addState('正在提交数据');
	ProcessBar.addState('成功执行完成');
	ProcessBar.start();
	var isSSL = location.href.indexOf("https://")!=-1;
	var callBack1 = {
		"upload":function(_transport){
			var sResponseText = _transport.responseText;
			var bAlert = FileUploadHelper.fileUploadedAlert(sResponseText,function(){
				DocFileName = sResponseText;
				ProcessBar.next();
			});
			if(bAlert)return;
		}
	}
	YUIConnect.setForm('frmUploadDocFile',true,isSSL);
	YUIConnect.asyncRequest('POST','../system/upload_office_doc.jsp?FileParamName=DocFile',callBack1);
	return false;
}
</SCRIPT>
</HEAD>

<BODY>
	<DIV>
		<SPAN>选择您要上传的文件(目前只支持Word格式文件)：</SPAN>
		<SPAN>
			<FORM name="frmUploadDocFile" style="margin:0;padding:0;display:" enctype='multipart/form-data'>
				<input type="file" id="DocFile" name="DocFile">
			</FORM>
		</SPAN>
	</DIV>
</BODY>
</HTML>