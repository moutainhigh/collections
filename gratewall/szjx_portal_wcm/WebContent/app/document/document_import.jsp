<%--
/*
 *	History			Who			What
 *	2008-01-23		wenyh		修改xsl上传逻辑
 *								(file_upload_dowith.jsp?FileParamName=XslFile -->
 *								xsl_file_upload4docimport_dowith.jsp?FileParamName=XslFile)
 *								,上传的xsl文件需要特殊处理到documentsrc目录.
 *
 */
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
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
<%@ page import="com.trs.components.ckm.ICKMServer" %>
<%@ page import="com.trs.DreamFactory" %>
<!------- WCM IMPORTS END ------------>


<!--- 页面状态设定,登录校验,参数获取,都放在public_server.jsp中 --->
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
	
	ICKMServer currCKMServer = (ICKMServer)DreamFactory.createObjectById("ICKMServer");
	boolean bCKMSimSearch = currCKMServer.isEnableSimSearch();
//7.结束
	out.clear();
%>
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="document_import.jsp.title">文档导入</TITLE>
<link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">
<link href="../../app/js/source/wcmlib/crashboard/resource/crashboard.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<style>
	body{
		font-size:12px;
		line-height:24px;
	}
</style>
</HEAD>

<BODY>
<%int nIdx=0;%>
	<DIV>
		<SPAN><%=++nIdx%>.<span WCMAnt:param="document_import.jsp.loadFile">选择您要装载的文件(支持xml、zip格式文件):</span></SPAN>
		<DIV>
			<FORM name="frmUploadDocFile" style="margin:0;padding:0;display:" enctype='multipart/form-data'>
				<input type="file" id="DocFile" name="DocFile">
			</FORM>
		</DIV>
	</DIV>
	<DIV>
		<DIV><%=++nIdx%>.<span  WCMAnt:param="document_import.jsp.operateStyle">请选择操作方式:</span></DIV>
		<DIV style="margin-left:10px;">
			<DIV >
				<input type="radio" name="XmlSource" value="1" onClick="displayOtherXml();">
				<span WCMAnt:param="document_import.jsp.exportXML">由TRS数据库导出的XML文件</span>
			</DIV>
			<DIV id="div_Mapping" style="margin-left:20px;padding:5px 0;border:1px solid black;display:none">
				<DIV>
					<SPAN style="margin:0 10px" WCMAnt:param="document_import.jsp.selMapFile">请选择映射文件</SPAN>
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
					<SPAN style="margin:0 10px;text-decoration:underline;cursor:pointer;" onclick="manageMapping();" WCMAnt:param="document_import.jsp.mgrMapFile">管理映射文件</SPAN>
				</DIV>
			</DIV>
		</DIV>
		<DIV style="margin-left:10px;">
			<DIV>
				<input type="radio" name="XmlSource" checked value="0" onClick="displayOtherXml();">
				<span WCMAnt:param="document_import.jsp.exportFile">由WCM导出的文件</span>
			</DIV>
		</DIV>
		<DIV style="margin-left:10px;">
			<DIV>
				<input type="radio" name="XmlSource" value="2" onClick="displayOtherXml();">
				<span WCMAnt:param="document_import.jsp.otherXML">其它类型的XML文件</span>
			</DIV>
			<DIV id="div_OtherXml" style="margin-left:20px;padding:5px 0;border:1px solid black;display:none;width:400px;">
				<DIV>
					<SPAN style="width:150px;">
						<input type="radio" name="XslSource" value="0" checked><span WCMAnt:param="document_import.jsp.uploadXSL">上传XSL:</span>
					</SPAN>
					<SPAN style="width:250px;margin-left:25px;">
						<FORM name="frmUploadXslFile" style="margin:0;padding:0;width:200px;display:inline" enctype='multipart/form-data'>
							<input type="file" id="XslFile" name="XslFile">
						</FORM>
					</SPAN>
				</DIV>
				<DIV>
					<SPAN style="width:200px;">
						<input type="radio" name="XslSource" value="1"><span WCMAnt:param="document_import.jsp.selUploadXSL">选择已上传XSL:</span>
					</SPAN>
					<SPAN style="width:250px;margin-left:25px;">
						<SELECT id="selectXslFile" style="width:200px;margin:0;padding:0">
							<OPTION value="">------------------------------------</OPTION>
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
	<DIV>
		<SPAN><%=++nIdx%>.<span WCMAnt:param="document_import.jsp.selecttargetchnlway">选择目标栏目的方式</span></SPAN>
		<div style="margin-left:10px;">
			<input type="radio"  name="ToChannel" value="0" checked><span WCMAnt:param="document_import.jsp.currchnl">当前栏目</span>
			<input type="radio"  name="ToChannel" value="1"><span WCMAnt:param="document_import.jsp.aschnlonly">按照栏目唯一标识</span>
			<input type="radio"  name="ToChannel" value="2"><span WCMAnt:param="document_import.jsp.aschnlid">按照栏目ID</span>
		</div>
	</DIV>
	<%
		}
	%>
	<DIV>
		<SPAN><%=++nIdx%>.<span WCMAnt:param="document_import.jsp.appearsameDocTitleorcontent">出现重复标题<%if(bCKMSimSearch){%>或重复正文<%}%>文档时</span></SPAN>
		<div style="margin-left:10px;">
			<input type="radio" name="JumpRepeat" value="true" checked><span WCMAnt:param="document_import.jsp.skip">跳过</span>
			<input type="radio" name="JumpRepeat" value="false"><span WCMAnt:param="document_import.jsp.load">装载</span>
<%if(!bCKMSimSearch){%>
		<span WCMAnt:param="document_import.jsp.tip">(如果文档创建时间在3天之前，将不做标题重复检查)</span>
<%}%>
		</div>
	</DIV>
<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/data/locale/system.js"></script>
<script src="../../app/js/data/locale/document.js"></script>
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/easyversion/extrender.js"></script>
<script src="../../app/js/easyversion/ajax.js"></script>
<script src="../../app/js/easyversion/basicdatahelper.js"></script>
<script src="../../app/js/easyversion/web2frameadapter.js"></script>
<script src="../../app/js/easyversion/elementmore.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<script src="../../app/js/source/wcmlib/Observable.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanelAdapter.js"></script>
<script src="../js/source/wcmlib/util/YUIConnection.js"></script>
<script src="../js/source/wcmlib/com.trs.dialog/FaultDialog.js"></script>
<script src="../../app/js/source/wcmlib/components/ProcessBar.js"></script>
<!-- Component Start -->
<script src="../../app/js/source/wcmlib/dragdrop/dd.js"></script>
<script src="../../app/js/source/wcmlib/Component.js"></script>
<script src="../../app/js/source/wcmlib/dialog/Dialog.js"></script>
<script src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></script>
<!-- Component End -->
<!--ProcessBar Start-->
<script src="../../app/js/source/wcmlib/components/ProcessBar.js"></script>
<link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">
<link rel="stylesheet" type="text/css" href="../../app/js/source/wcmlib/components/processbar.css">
<!--ProcessBar End-->
<script language="javascript">
	window.m_fpCfg = {
		m_arrCommands : [{
			cmd : 'importDocuments',
			name : wcm.LANG.DOCUMENT_PROCESS_41 || '导入'
		}],
		size : [500, 330]
	};
</script>
<script language="javascript">
<!--
function isUploadXsl() {
	var xmlSources = document.getElementsByName("XmlSource");
	var xslSources = document.getElementsByName("XslSource");
	return (xmlSources[2].checked && xslSources[0].checked);
}
var DocFileName = null;
var XslFileName = null;
var isTRSXsl = false;
function importDocuments(){
	var sDocFileName = $("DocFile").value;
	var sXslFileName = $("XslFile").value;
	var uploadDoc = false;
	var uploadXsl = false;
	try{
		FileUploadHelper.validFileExt(sDocFileName, "XML,ZIP");
	}catch(err){
		Ext.Msg.$alert(err.message, function(){
			$("DocFile").focus();
		});
		return false;
	}
	if(isUploadXsl()){
		try{
			FileUploadHelper.validFileExt(sXslFileName, "XSL");
			
			uploadXsl =true;
		}catch(err){
			Ext.Msg.$alert(err.message, function(){
				$("XslFile").focus();
			});
			return false;
		}
	}
	if(!getXslFile()){
		return false;
	}
	var isSSL = location.href.toLowerCase().indexOf("https://")!=-1;
	var callBack1 = {
		"upload":function(_transport){
			var sResponseText = _transport.responseText;
			var bAlert = FileUploadHelper.fileUploadedAlert(sResponseText,{
				succ:function(){DocFileName = sResponseText;}
			});
			if(bAlert)return;
			var callBack2 = {
				"upload":function(_transport){
					if(_transport){
						var sResponseText = _transport.responseText;
						var bAlert = FileUploadHelper.fileUploadedAlert(sResponseText,{
							succ:function(){XslFileName = sResponseText;}
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
					var oPostData = {
						"ChannelId" : getParameter("ChannelId"),
//						"SiteId" : getParameter("SiteId"),
						"ImportFile" : DocFileName||'',
						"ImportXSLFile" : XslFileName||'',
						"ImportToCurrChannel" : bToCurrentChannel,
						"ImportByChnlName" : ImportByChnlName,
						"IgnoreTitleSim" : sJumpRepeat,
						"IsTrsServerFile" : isTRSXsl
					};
					if($("mapping"))
						$("mapping").style.display = "none";
					ProcessBar.start(wcm.LANG.DOCUMENT_PROCESS_221||"执行导入文档..");
					var oHelper = new com.trs.web2frame.BasicDataHelper();
					oHelper.Call('wcm6_document','importDocuments',oPostData,true,
						function(_transport,_json){
							ProcessBar.close();
							Ext.Msg.report(_json, wcm.LANG.DOCUMENT_PROCESS_42 || '文档导入结果', function(){
								notifyFPCallback();
								FloatPanel.close();
							});
							FloatPanel.hide();
						},
						function(_transport,_json){
							if($("mapping"))
								$("mapping").style.display = "";
							$render500Err(_transport,_json);
						}
					);
				}
			};
			if(uploadXsl){
				YUIConnect.setForm('frmUploadXslFile',true,isSSL);
				YUIConnect.asyncRequest('POST',
					'../system/xsl_file_upload4docimport_dowith.jsp?FileParamName=XslFile',callBack2);
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
			Ext.Msg.$alert(wcm.LANG.DOCUMENT_PROCESS_43 || '尚未选择映射文件.');
			return false;
		}
		isTRSXsl = true;
	} else if(xmlSources[1].checked) {
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
			Ext.Msg.$alert(wcm.LANG.DOCUMENT_PROCESS_44 || '未选择Xsl文件.');
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
	} else if(xmlSources[2].checked) {
		oMapping.style.display = "none";
		oOtherXml.style.display = "";
	} else {
		oMapping.style.display = "none";
		oOtherXml.style.display = "none";
	}
}
function refreshExistsXsl(){
}

function manageMapping(){
	var cbr = wcm.CrashBoarder.get('Trs_Mapping');
	cbr.show({
		title : wcm.LANG.DOCUMENT_PROCESS_45 || '管理TRS映射文件',
		src : WCMConstants.WCM6_PATH + 'document/document_trs_mapping.jsp',
		width:'580px',
		height:'380px',
		maskable:false,
		params : {ChannelId : getParameter("ChannelId")}
	});
	var cb = cbr.getCrashBoard();
	cb.on('beforeclose', function(){
		refreshTRSXml();
	});
}
function refreshTRSXml(){
	BasicDataHelper.JspRequest('../document/document_trs_mapping_request.jsp',null,false,
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
</BODY>
</HTML>