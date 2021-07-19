<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.service.IDocumentService" %>
<%@ page import="com.trs.service.ServiceHelper" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.Date" %>
<!------- WCM IMPORTS END ------------>


<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%
//4.初始化(获取数据)
	int iChannelId = currRequestHelper.getInt("ChannelId", 0);
	int iSiteId = currRequestHelper.getInt("SiteId", 0);
//6.业务代码
	IDocumentService currDocumentService = ServiceHelper.createDocumentService();
	File[] arFile = currDocumentService.getExitedMappingFiles();
	if(arFile == null) {
		arFile = new File[0];
	}
	File[] arXslFile = currDocumentService.getExitedXslFiles();
	if(arXslFile == null) {
		arXslFile = new File[0];
	}
//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>记录导入</TITLE>
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
FloatPanel.addCommand('importBtn', '导入', 'importDocuments',null);
function isUploadXsl() {
	var xmlSources = document.getElementsByName("XmlSource");
	var xslSources = document.getElementsByName("XslSource");
	return (xmlSources[3].checked && xslSources[0].checked);
}
var DocFileName = null;
var XslFileName = null;
var isTRSXsl = false;
function importDocuments(){
	ProcessBar.init('执行进度，请稍候...');
	var sDocFileName = $("DocFile").value;
	var sXslFileName = $("XslFile").value;
	var uploadDoc = false;
	var uploadXsl = false;
	if(FileUploadHelper.validFileExt(sDocFileName, "xml,zip")){
		uploadDoc = true;
	}
	else{
		return false;
	}
	if(isUploadXsl()) {
		if(FileUploadHelper.validFileExt(sXslFileName, "xsl")) {
			uploadXsl = true;
		}
		else{
			return false;
		}
	}
	if(!getXslFile()){
		return false;
	}
	ProcessBar.addState('正在上传文件:'+sDocFileName);
	if(uploadXsl){
		ProcessBar.addState('正在上传文件:'+sXslFileName);
	}
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
			var callBack2 = {
				"upload":function(_transport){
					if(_transport){
						var sResponseText = _transport.responseText;
						var bAlert = FileUploadHelper.fileUploadedAlert(sResponseText,function(){
							XslFileName = sResponseText;
							ProcessBar.next();
						});
						if(bAlert)return;
					}
					var bToCurrentChannel = '';
					var sJumpRepeat = '';
					<%
						if(iChannelId!=0){
					%>
					bToCurrentChannel = $('ToCurrentChannel1').checked;
					<%
						}else if(iSiteId!=0){
					%>
					bToCurrentChannel = false;
					<%
						}
					%>
					var tmp = document.getElementsByName("JumpRepeat");
					for(var i=0;i<tmp.length;i++){
						if(tmp[i].checked){
							sJumpRepeat=tmp[i].value;
							break;
						}
					}
					var xmlSource = 0;
					var xmlSources = document.getElementsByName("XmlSource");
					for(var i=0;i<xmlSources.length;i++){
						if(xmlSources[i].checked){
							xmlSource=xmlSources[i].value;
							break;
						}
					}
					var oPostData = {
						"ChannelId" : getParameter("ChannelId"),
						"ViewId"	: getParameter("ViewId"),
						"ImportFile" : DocFileName||'',
						"XSLFile" : XslFileName||'',
						"ImportToCurrChannel" : bToCurrentChannel,
						"IgnoreTitleSim" : sJumpRepeat,
						"xmlSource" : xmlSource,
						"IsTrsServerFile" : isTRSXsl
					};
					var oHelper = new com.trs.web2frame.BasicDataHelper();
					oHelper.Call('wcm6_MetaDataCenter','importMetaViewDatas',oPostData,true,
						function(_transport,_json){
							ProcessBar.exit();
							var sFunc = ""
							var func = new (top.actualTop||top).Function(sFunc);
							(top.actualTop||top).ReportsDialog.show(_json, '记录导入结果', function(){
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
			};
			if(uploadXsl){
				YUIConnect.setForm('frmUploadXslFile',true,isSSL);
				YUIConnect.asyncRequest('POST',
					'../system/file_upload_dowith.jsp?FileParamName=XslFile',callBack2);
			}
			else{
				callBack2.upload();
			}
		}
	}
	YUIConnect.setForm('frmUploadDocFile',true,isSSL);
	YUIConnect.asyncRequest('POST','../system/file_upload_dowith.jsp?FileParamName=DocFile',callBack1);
	return false;
}

function getXslFile(){
	var xmlSources = document.getElementsByName("XmlSource");
	if(xmlSources[0].checked) {
		XslFileName = $('mapping').value;
		if(!XslFileName){
			$alert('尚未选择由TRS数据库导出的XML文件.');
			return false;
		}
		isTRSXsl = true;
	} else if(xmlSources[1].checked || xmlSources[2].checked) {
		XslFileName = '';
		isTRSXsl = false;
	} else {
		var xslSources = document.getElementsByName('XslSource');
		if(xslSources[0].checked){
			XslFileName = $('XslFile').value;
		}
		else{
			XslFileName = $('selectXslFile').value;
		}
		if(!XslFileName){
			$alert('未选择其他XML文件.');
			return false;
		}
		isTRSXsl = false;
	}
	return true;
}
function displayOtherXml() {
	var oOtherXml = $('div_OtherXml');
	var oMapping = $('div_Mapping');
	var xmlSources = document.getElementsByName("XmlSource");
	if(xmlSources[0].checked) {
		oMapping.style.display = "";
		oOtherXml.style.display = "none";
	} else if(xmlSources[3].checked) {
		oMapping.style.display = "none";
		oOtherXml.style.display = "";
	} else {
		oMapping.style.display = "none";
		oOtherXml.style.display = "none";
	}
	if($('CurrentChannelContainer')){
		if(xmlSources[1].checked){
			$('CurrentChannelContainer').style.display = 'none';
		}else{
			$('CurrentChannelContainer').style.display = '';
		}
	}
}
function refreshExistsXsl(){
}
function manageMapping(){
	var aTop = (top.actualTop || top);
	if(aTop.oMappingDialog == null) {
		TRSCrashBoard.setMaskable(false);
		var sUrl = './document/document_trs_mapping.html?ChannelId='+getParameter("ChannelId");
		aTop.oMappingDialog = TRSDialogContainer.register('Trs_Mapping', '管理TRS映射关系', 
				sUrl, '580', '380', true);
	}
	aTop.oMappingDialog.onClosed = function(){
		var aTop = (top.actualTop || top);
		if(aTop.oMappingEditDialog && aTop.oMappingEditDialog.visible == true) {
			aTop.oMappingEditDialog.close();
		}	
		refreshTRSXml();
	}
	TRSDialogContainer.display('Trs_Mapping');
}
function refreshTRSXml(){
	BasicDataHelper.JspRequest('../document/document_trs_mapping.jsp',null,false,
		function(trans,json){
			var sValue = $('mapping').value;
			var sHtml = '<SELECT id="mapping" style="width:140px;margin:0;padding:0">'+
			'<OPTION value="">--------------------------------------------</OPTION>';
			for(var i=0;i<json.length;i++){
				sHtml += '<OPTION value="'+json[i]["FILENAME"]+'">'+
					json[i]["FILENAME"]+' , '+json[i]["LASTMODIFIED"]+
				'</OPTION>';
			}
			sHtml += '</SELECT>';
			$('span_mapping').innerHTML = sHtml;
			$('mapping').value = sValue;
		}
	);
}
//*/
</SCRIPT>
</HEAD>

<BODY>
<%int nIdx=0;%>
	<DIV>
		<SPAN><%=++nIdx%>、选择您要装载的文件(支持xml、zip格式文件)：</SPAN>
		<SPAN>
			<FORM name="frmUploadDocFile" style="margin:0;padding:0;display:" enctype='multipart/form-data'>
				<input type="file" id="DocFile" name="DocFile">
			</FORM>
		</SPAN>
	</DIV>
	<DIV>
		<DIV><%=++nIdx%>、请选择操作方式：</DIV>
		<DIV style="margin-left:10px; display:none;">
			<DIV>
				<input type="radio" name="XmlSource" value="1" onClick="displayOtherXml();">
				由TRS数据库导出的XML文件
			</DIV>
			<DIV id="div_Mapping" style="margin-left:20px;padding:5px 0;border:1px solid black;display:none">
				<DIV>
					<SPAN style="margin:0 10px">请选择映射文件</SPAN>
					<SPAN id="span_mapping">
						<SELECT id="mapping" style="width:140px;margin:0;padding:0">
							<OPTION value="">--------------------------------------------</OPTION>
<%
	File currFile = null;
	CMyDateTime myDateTime = CMyDateTime.now();
	for(int i=0; i<arFile.length; i++) {
		currFile = arFile[i];
		if(currFile == null)
			continue;
		myDateTime.setDateTime(new Date(currFile.lastModified()));
%>
						<OPTION value="<%=currFile.getName()%>">
							<%=currFile.getName()%> , <%=myDateTime.toString()%>
						</OPTION>
<%
	}
%>
						</SELECT>
					</SPAN>
					<SPAN style="margin:0 10px;text-decoration:underline;cursor:pointer;" onclick="manageMapping();">管理映射文件</SPAN>
				</DIV>
			</DIV>
		</DIV>
		<DIV style="margin-left:10px;">
			<DIV>
				<input type="radio" name="XmlSource" checked value="3" onClick="displayOtherXml();">
				由Excel生成的xml数据文件
			</DIV>
		</DIV>
		<DIV style="margin-left:10px;">
			<DIV>
				<input type="radio" name="XmlSource" value="0" onClick="displayOtherXml();">
				符合WCM规则的文件
			</DIV>
		</DIV>
		<DIV style="margin-left:10px;">
			<DIV>
				<input type="radio" name="XmlSource" value="2" onClick="displayOtherXml();">
				其它类型的XML文件
			</DIV>
			<DIV id="div_OtherXml" style="margin-left:20px;padding:5px 0;border:1px solid black;display:none">
				<DIV>
					<SPAN style="width:150px;">
						<input type="radio" name="XslSource" value="0" checked>上传XSL:
					</SPAN>
					<SPAN style="float:left;width:250px">
						<FORM name="frmUploadXslFile" style="margin:0;padding:0;width:200px" enctype='multipart/form-data'>
							<input type="file" id="XslFile" name="XslFile">
						</FORM>
					</SPAN>
				</DIV>
				<DIV>
					<SPAN style="width:150px;">
						<input type="radio" name="XslSource" value="1">选择已上传XSL:
					</SPAN>
					<SPAN style="width:250px">
						<SELECT id="selectXslFile" style="width:200px;margin:0;padding:0">
							<OPTION value="">--------------------------------------------</OPTION>
<%
	for(int i=0; i<arXslFile.length; i++) {
		currFile = arXslFile[i];
		if(currFile == null)
			continue;
		myDateTime.setDateTime(new Date(currFile.lastModified()));
%>
							<OPTION VALUE="<%=currFile.getName()%>">
								<%=currFile.getName()%> , <%=myDateTime.toString()%>
							</OPTION>
<%
	}
%>
						</SELECT>
					</SPAN>
				</DIV>
			</DIV>
		</DIV>
	</DIV>
	<%
		if(iChannelId!=0){
	%>
	<DIV style="display:'none';" id="CurrentChannelContainer">
		<SPAN><%=++nIdx%>、所有记录全部装载至当前栏目</SPAN>
		<SPAN>
			<input type="radio" id="ToCurrentChannel1" name="ToCurrentChannel" value="true" checked> 是
			<input type="radio" name="ToCurrentChannel" value="false"> 否
		</SPAN>
	</DIV>
	<%
		}
	%>
	<DIV style="display:none;">
		<SPAN><%=++nIdx%>、出现重复标题记录时</SPAN>
		<SPAN>
			<input type="radio" name="JumpRepeat" value="true" checked> 跳过
			<input type="radio" name="JumpRepeat" value="false"> 装载
		</SPAN>
	</DIV>
</BODY>
</HTML>