<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.service.IDocumentService" %>
<%@ page import="com.trs.service.ServiceHelper" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.Date" %>
<!------- WCM IMPORTS END ------------>


<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
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
<TITLE WCMAnt:param="view_data_import.jsp.title">记录导入</TITLE>
<style>
	body{
		font-size:12px;
		line-height:24px;
	}
</style>
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/metaviewdata.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanelAdapter.js"></script>
<!--wcm-dialog start-->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<!--YUIConnection-->
<script src="../js/source/wcmlib/util/YUIConnection.js"></script>
<!--CrashBoard-->
<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></SCRIPT>
<link href="../../app/js/source/wcmlib/crashboard/resource/crashboard.css" rel="stylesheet" type="text/css" />
<!--ProcessBar Start-->
<script src="../../app/js/source/wcmlib/components/ProcessBar.js"></script>
<link rel="stylesheet" type="text/css" href="../../app/js/source/wcmlib/components/processbar.css">
<!--ProcessBar End-->
<script>
	window.m_fpCfg = {
		m_arrCommands : [{
			cmd : 'importDocuments',
			name : wcm.LANG.METAVIEWDATA_20 || '导入'
		}],
		size : [500, 300]
	};	
</script>
<script language="javascript">
function isUploadXsl() {
	var xmlSources = document.getElementsByName("XmlSource");
	var xslSources = document.getElementsByName("XslSource");
	return (xmlSources[3].checked && xslSources[0].checked);
}
var DocFileName = null;
var XslFileName = null;
var isTRSXsl = false;

function importDocuments(){
	ProcessBar.init(wcm.LANG.METAVIEWDATA_4 || '执行进度，请稍候...');
	var sDocFileName = $("DocFile").value;
	var sXslFileName = $("XslFile").value;
	var uploadXsl = false;
	try{
		FileUploadHelper.validFileExt(sDocFileName, "xml,zip")
	}catch(err){
		Ext.Msg.alert(err.message, function(){
			$("DocFile").focus();
		});
		return false;
	}
	if(isUploadXsl()) {
		try{
			if(FileUploadHelper.validFileExt(sXslFileName, "xsl")) {
				uploadXsl = true;
			}
		}catch(err){
			Ext.Msg.alert(err.message, function(){
				$("XslFile").focus();
			});
			return false;
		}
	}
	if(!getXslFile()){
		return false;
	}
	ProcessBar.addState(wcm.LANG.METAVIEWDATA_5 || '提交数据');
	ProcessBar.addState(wcm.LANG.METAVIEWDATA_6 || '成功执行完成');
	ProcessBar.start();
	var isSSL = location.href.indexOf("https://")!=-1;
	var callBack1 = {
		"upload":function(_transport){
			var sResponseText = _transport.responseText;
			var bAlert = FileUploadHelper.fileUploadedAlert(sResponseText,{
				succ:function(){
						DocFileName = sResponseText;
						ProcessBar.next();
					 }
				});
			if(bAlert)return;
			var callBack2 = {
				"upload":function(_transport){
					if(_transport){
						var sResponseText = _transport.responseText;
						var bAlert = FileUploadHelper.fileUploadedAlert(sResponseText,{
							succ:function(){
									XslFileName = sResponseText;
									ProcessBar.next();
								}
							});
						if(bAlert)return;
					}
					var toChannelValue = getToChannelValue();
					var bToCurrentChannel = '';
					var sJumpRepeat = '';
					<%
						if(iChannelId!=0){
					%>
					bToCurrentChannel = toChannelValue == 0 ? true : false;
					<%
						}else if(iSiteId!=0){
					%>
					bToCurrentChannel = false;
					<%
						}
					%>
					var ImportByChnlName = toChannelValue == 1 ? true : false;
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
						"ImportByChnlName" : ImportByChnlName,
						"IgnoreTitleSim" : sJumpRepeat,
						"xmlSource" : xmlSource,
						"IsTrsServerFile" : isTRSXsl
					};
					
					var oHelper = new com.trs.web2frame.BasicDataHelper();
					oHelper.Call('wcm6_MetaDataCenter','importMetaViewDatas',oPostData,true,
						function(_transport,_json){
							ProcessBar.close();
							Ext.Msg.report(_json, (wcm.LANG.METAVIEWDATA_21 || '记录导入结果'), function(){
								notifyFPCallback();
								FloatPanel.close();
							});
							FloatPanel.hide();
						},
						function(_transport,_json){
							$render500Err(_transport,_json);
							FloatPanel.close();
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

function getToChannelValue(){
	var toChannels = document.getElementsByName('ToChannel');
	for(var i = 0; i < toChannels.length; i++){
		if(toChannels[i].checked)
			return toChannels[i].value;
	}
}

function getXslFile(){
	var xmlSources = document.getElementsByName("XmlSource");
	if(xmlSources[0].checked) {
		XslFileName = $('mapping').value;
		if(!XslFileName){
			Ext.Msg.alert(wcm.LANG.METAVIEWDATA_22 || '尚未选择由TRS数据库导出的XML文件.');
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
			Ext.Msg.alert(wcm.LANG.METAVIEWDATA_23 || '未选择其他XML文件.');
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
		aTop.oMappingDialog = TRSDialogContainer.register('Trs_Mapping', (wcm.LANG.METAVIEWDATA_24 || '管理TRS映射关系'), 
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
</SCRIPT>
</HEAD>

<BODY>
<%int nIdx=0;%>
	<DIV>
		<SPAN><%=++nIdx%>、</span><span WCMAnt:param="view_data_import.jsp.selFile">选择您要装载的文件(支持xml、zip格式文件)：</span>
		<SPAN>
			<FORM name="frmUploadDocFile" style="margin:0;padding:0;display:" enctype='multipart/form-data'>
				<input type="file" id="DocFile" name="DocFile">
			</FORM>
		</SPAN>
	</DIV>
	<DIV>
		<DIV><%=++nIdx%>、<font WCMAnt:param="view_data_import.jsp.methodSel">请选择操作方式：</font></DIV>
		<DIV style="margin-left:10px; display:none;">
			<span>
				<input type="radio" name="XmlSource" value="1" onClick="displayOtherXml();">
				<span WCMAnt:param="view_data_import.jsp.fileExfromTRS">由TRS数据库导出的XML文件</span>
			</span>
			<DIV id="div_Mapping" style="margin-left:20px;padding:5px 0;border:1px solid black;display:none">
				<DIV>
					<SPAN style="margin:0 10px" WCMAnt:param="view_data_import.jsp.selMirrFile">请选择映射文件</SPAN>
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
					<SPAN style="margin:0 10px;text-decoration:underline;cursor:pointer;" onclick="manageMapping();" WCMAnt:param="view_data_import.jsp.manageMirFile">管理映射文件</SPAN>
				</DIV>
			</DIV>
		</DIV>
		<DIV style="margin-left:10px;">
			<span>
				<input type="radio" name="XmlSource" checked value="3" onClick="displayOtherXml();">
				<span WCMAnt:param="view_data_import.jsp.fileExfromEXCEL">由Excel生成的xml数据文件</span>
			</span>
		</DIV>
		<DIV style="margin-left:10px;">
			<DIV>
				<input type="radio" name="XmlSource" value="0" onClick="displayOtherXml();">
				<span WCMAnt:param="view_data_import.jsp.fileFromWCM">符合WCM规则的文件</span>
			</DIV>
		</DIV>
		<DIV style="margin-left:10px;">
			<DIV>
				<input type="radio" name="XmlSource" value="2" onClick="displayOtherXml();">
				<span WCMAnt:param="view_data_import.jsp.otherFile">其它类型的XML文件</span>
			</DIV>
			<DIV id="div_OtherXml" style="margin-left:20px;padding:5px 0;border:1px solid black;display:none;width:420px">
				<DIV>
					<SPAN style="width:150px;">
						<input type="radio" name="XslSource" value="0" checked><span WCMAnt:param="view_data_import.jsp.uploadXSL">上传XSL:</span>
					</SPAN>
					<SPAN style="width:250px">
						<FORM name="frmUploadXslFile" style="margin:0;padding:0;width:200px;display:inline" enctype='multipart/form-data'>
							<input type="file" id="XslFile" name="XslFile">
						</FORM>
					</SPAN>
				</DIV>
				<DIV>
					<SPAN style="width:150px;">
						<input type="radio" name="XslSource" value="1"><span WCMAnt:param="view_data_import.jsp.selXSL">选择已上传XSL:</span>
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
	<DIV style="display:none;" id="CurrentChannelContainer">
		<SPAN><%=++nIdx%>、</span><span WCMAnt:param="view_data_import.jsp.uploadToCurChnl">选择目标栏目的方式</span>
		<div style="margin-left:10px;">
			<input type="radio" id="ToCurrentChannel1" name="ToChannel" value="0" checked><span WCMAnt:param="document_import.jsp.currchnl">当前栏目</span>
			<input type="radio"  name="ToChannel" value="1"><span WCMAnt:param="document_import.jsp.aschnlonly">按照栏目唯一标识</span>
			<input type="radio"  name="ToChannel" value="2"><span WCMAnt:param="document_import.jsp.aschnlid">按照栏目ID</span>
		</div>
	</DIV>
	<%
		}
	%>
	<DIV style="display:none;">
		<SPAN><%=++nIdx%>、</span><span WCMAnt:param="view_data_import.jsp.doWhenSame">出现重复标题记录时</span>
		<SPAN>
			<input type="radio" name="JumpRepeat" value="true" checked><span WCMAnt:param="view_data_import.jsp.skip">跳过</span>
			<input type="radio" name="JumpRepeat" value="false"><span WCMAnt:param="view_data_import.jsp.overload">装载</span>
		</SPAN>
	</DIV>
</BODY>
</HTML>