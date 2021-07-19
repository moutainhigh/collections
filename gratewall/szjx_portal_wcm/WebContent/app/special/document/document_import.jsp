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
//7.结束
	out.clear();
%>
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="document_import.jsp.title">文档导入</TITLE>
<link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">
<link href="../css/widgets.css" rel="stylesheet" type="text/css" />
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
	<%
		if(iChannelId!=0){
	%>
	<DIV>
		<SPAN><%=++nIdx%>.<span WCMAnt:param="document_import.jsp.alldocsloadtocurrchnl">所有文档全部装载至当前栏目</span></SPAN>
		<div style="margin-left:10px;">
			<input type="radio" id="ToCurrentChannel1" name="ToCurrentChannel" value="true" checked><span WCMAnt:param="document_import.jsp.yes">是</span>
			<input type="radio" id="ToCurrent" name="ToCurrentChannel" value="false"><span WCMAnt:param="document_import.jsp.not">否</span>
		</div>
		<div id="byChnlName" style="margin-left:10px;display:none;"><input type="checkbox" id="ImportByChnlName" name="ImportByChnlName" value="" /><span WCMAnt:param="aschnlonlymarkimporttospchnl">按照栏目唯一标识导入到相应栏目</span></div>
	</DIV>
	<%
		}
	%>
	<DIV>
		<SPAN><%=++nIdx%>.<span WCMAnt:param="document_import.jsp.appearsamedoctitle">出现重复标题文档时</span></SPAN>
		<div style="margin-left:10px;">
			<input type="radio" name="JumpRepeat" value="true" checked><span WCMAnt:param="document_import.jsp.skip">跳过</span>
			<input type="radio" name="JumpRepeat" value="false"><span WCMAnt:param="document_import.jsp.load">装载</span>

		<span WCMAnt:param="document_import.jsp.tip">(如果文档创建时间在3天之前，将不做标题重复检查)</span></div>
	</DIV>
<script src="../../../app/js/easyversion/lightbase.js"></script>
<script src="../../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../../app/js/data/locale/system.js"></script>
<script src="../../../app/js/data/locale/document.js"></script>
<script src="../../../app/js/data/locale/ajax.js"></script>
<script src="../../../app/js/easyversion/extrender.js"></script>
<script src="../../../app/js/easyversion/ajax.js"></script>
<script src="../../../app/js/easyversion/basicdatahelper.js"></script>
<script src="../../../app/js/easyversion/web2frameadapter.js"></script>
<script src="../../../app/js/easyversion/elementmore.js"></script>
<script src="../../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../../app/js/source/wcmlib/core/AuthServer.js"></script>
<script src="../../../app/js/source/wcmlib/Observable.js"></script>
<script src="../../js/source/wcmlib/util/YUIConnection.js"></script>
<!-- Component Start -->
<script src="../../../app/js/source/wcmlib/dragdrop/dd.js"></script>
<script src="../../../app/js/source/wcmlib/Component.js"></script>
<script src="../../../app/js/source/wcmlib/dialog/Dialog.js"></script>
<SCRIPT src="../../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
<script src="../../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
<!-- Component End -->
<script language="javascript">
<!--
	window.m_cbCfg = {
		btns : [
			{
				text : '导入',
				cmd : function(){
					importDocuments();
					return false;
				}
			},
			{
				extraCls : 'wcm-btn-close',
				text : '取消'
			}
		]
	};		
//-->
</script>
<script language="javascript">
<!--

Event.observe('ToCurrentChannel1', 'click', function(){
	if($('ToCurrentChannel1').checked){
		$('byChnlName').style.display = "none";
	}
});
Event.observe('ToCurrent', 'click', function(){
	if($('ToCurrent').checked){
		$('byChnlName').style.display = "";
	}
});

var DocFileName = null;
var XslFileName = null;
var isTRSXsl = false;
function importDocuments(){
	var sDocFileName = $("DocFile").value;
	try{
		FileUploadHelper.validFileExt(sDocFileName, "XML,ZIP");
	}catch(err){
		Ext.Msg.alert(err.message, function(){
			$("DocFile").focus();
		});
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
					var oPostData = {
						"ChannelId" : getParameter("ChannelId"),
//						"SiteId" : getParameter("SiteId"),
						"ImportFile" : DocFileName||'',
						"ImportXSLFile" : XslFileName||'',
						"ImportToCurrChannel" : bToCurrentChannel,
						"ImportByChnlName" : $('ImportByChnlName').checked,
						"IgnoreTitleSim" : sJumpRepeat,
						"IsTrsServerFile" : isTRSXsl
					};
					if($("mapping"))
						$("mapping").style.display = "none";
					var oHelper = new com.trs.web2frame.BasicDataHelper();
					oHelper.Call('wcm6_document','importDocuments',oPostData,true,
						function(_transport,_json){
							Ext.Msg.report(_json, wcm.LANG.DOCUMENT_PROCESS_42 || '文档导入结果', function(){
								wcm.CrashBoarder.get(window).notify(1);
								wcm.CrashBoarder.get(window).close();
							});
							wcm.CrashBoarder.get(window).hide();
						},
						function(_transport,_json){
							if($("mapping"))
								$("mapping").style.display = "";
							$render500Err(_transport,_json);
						}
					);
				}
			};
			callBack2.upload();
		}
	}
	YUIConnect.setForm('frmUploadDocFile',true,isSSL);
	YUIConnect.asyncRequest('POST','../../system/file_upload_dowith.jsp?FileParamName=DocFile',callBack1);
	return false;
}
//*/
</SCRIPT>
</BODY>
</HTML>