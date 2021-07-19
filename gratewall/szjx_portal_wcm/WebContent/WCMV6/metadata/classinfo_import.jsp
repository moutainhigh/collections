<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.Date" %>
<!------- WCM IMPORTS END ------------>


<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%
//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>分类法导入</TITLE>
<style>
	body{
		font-size:12px;
	}
	.row{
		width:100%;
        margin:10px 0px;
		display:block;
		line-height:24px;
		height:24px;
	}
	.label{
        width:140px;
	}
</style>
<script src="../js/com.trs.util/Common.js"></script>
<script>
	$import('com.trs.web2frame.DataHelper');
	$import('com.trs.web2frame.TempEvaler');
	$import('com.trs.wcm.Web2frameDefault');
	$import('com.trs.dialog.Dialog');
	$import('com.trs.wcm.MessageCenter');
	$import('com.trs.wcm.util.FileUploadHelper');
	$import('com.trs.util.YUIConnection');
	$import('com.trs.crashboard.CrashBoarder');
</script>
<script language="javascript">
<!--
FloatPanel.addCloseCommand();
FloatPanel.addCommand('importBtn', '导入', 'importClassInfos',null);
var DocFileName = null;
function importClassInfos(){
	ProcessBar.init('执行进度，请稍候...');
	var sDocFileName = $("DocFile").value;
	if(!FileUploadHelper.validFileExt(sDocFileName, "txt,zip")){
		return false;
	}
	ProcessBar.addState('正在上传文件:'+sDocFileName);
	ProcessBar.addState('正在提交数据');
	ProcessBar.addState('成功执行完成');
	ProcessBar.start();
	var isSSL = location.href.indexOf("https://")!=-1;
	var callBack1 = {
		"upload":function(_transport){
			try{
				DocFileName = eval(_transport.responseText);
			}catch(error){
				DocFileName = [];
			}
			var bAlert = FileUploadHelper.fileUploadedAlert(DocFileName[0]||"",function(){
				ProcessBar.next();
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
					var oHelper = new com.trs.web2frame.BasicDataHelper();
					oHelper.Call('wcm6_ClassInfo','importClassInfos',oPostData,true,
						function(_transport,_json){
							ProcessBar.exit();
							var sFunc = ""
							var func = new (top.actualTop||top).Function(sFunc);
							(top.actualTop||top).ReportsDialog.show(_json, '分类法导入结果', function(){
								$dialog().hide();
								$MessageCenter.sendMessage('main','PageContext.RefreshList', 'PageContext', null , true , true);
								FloatPanel.close(true);
							});
							FloatPanel.close();
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

</SCRIPT>
</HEAD>

<BODY>
<%int nIdx=0;%>
	<DIV class="row">
		<SPAN class="label" style="width:100%;"><%=++nIdx%>、选择您要装载的文件(支持txt,zip格式文件)：</SPAN>
		<SPAN style="margin-left:20px;display:block;">
			<FORM name="frmUploadDocFile" style="margin:0;padding:0;display:" enctype='multipart/form-data'>
				<input type="file" id="DocFile" name="DocFile" style="width:400px;">
			</FORM>
		</SPAN>
	</DIV>

	<DIV class="row">
		<SPAN class="label"><%=++nIdx%>、出现重复分类法名称时</SPAN>
		<SPAN>
			<input type="radio" name="importType" value="0" checked> 跳过
			<input type="radio" name="importType" value="1"> 覆盖
			<input type="radio" name="importType" value="2"> 新建
		</SPAN>
	</DIV>

	<DIV class="row">
		<SPAN class="label"><%=++nIdx%>、以分类代码区分层次</SPAN>
		<SPAN>
			<input type="radio" name="ContainCode" value="true" checked> 是
			<input type="radio" name="ContainCode" value="false"> 否
		</SPAN>
	</DIV>
</BODY>
</HTML>