<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtField" %>
<%@ page import="com.trs.components.wcm.content.persistent.ContentExtFields" %>
<%@ page import="com.trs.components.wcm.content.persistent.DocumentFieldConfig" %>
<%@ page import="com.trs.infra.config.XMLConfigServer" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.service.IChannelService" %>
<%@ page import="com.trs.service.ServiceHelper" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	String sChnlDocIds = currRequestHelper.getString("objectids");
	String sDocumentId = currRequestHelper.getString("DocumentIds");
	boolean bExportAll = currRequestHelper.getBoolean("ExportAll", false);
	String sChannelIds = "";
	int nChannelId = 0;
	int nSiteId = 0;
	boolean bDiffedChnls = false;
	if(bExportAll) {
		nChannelId = currRequestHelper.getInt("ChannelId", 0);
		nSiteId = currRequestHelper.getInt("SiteId", 0);
		if (nChannelId <= 0 && nSiteId <= 0){
			throw new WCMException("导出所有文档时，至少输入[栏目ID-ChannelId]或者[站点ID-SiteId]！");
		}
	}else{
		/*sChannelIds = currRequestHelper.getString("ChannelIds");
		if (!CMyString.isEmpty(sChannelIds)){
			String[] arChannelIds = sChannelIds.split(",");
			for (int i = 0; i < arChannelIds.length;){
				try{
					if (Integer.parseInt(arChannelIds[i]) != Integer.parseInt(arChannelIds[++i])){
						bDiffedChnls = true;
						break;
					}
				}catch(NumberFormatException ex){
					throw new WCMException("不合法的栏目ID序列[" + sChannelIds + "]！", ex);
				}
			}
		}//*/
	}
//5.权限校验

//6.业务代码

//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>文档导出</TITLE>
<style>
	body{
		font-size:12px;
		overflow:auto;
	}
	div{
		width:100%;
		display:block;
		line-height:24px;
		height:24px;
	}
	SPAN{
		display:inline;
	}
	li{
		list-style-type: circle;
	}
</style>
<script src="../js/com.trs.util/Common.js"></script>
<script>
	$import('com.trs.web2frame.DataHelper');
	$import('com.trs.web2frame.TempEvaler');
	$import('com.trs.wcm.Web2frameDefault');

	$import('com.trs.dialog.Dialog');
	$import('com.trs.wcm.MessageCenter');
	$import('com.trs.util.YUIConnection');
</script>
<script language="javascript">
<!--
FloatPanel.addCloseCommand();
FloatPanel.addCommand('exportBtn', '导出', 'exportDocuments',null);
//Event.observe(window,'unload',function(){
//	FloatPanel.removeCloseCommand();
//	FloatPanel.removeCommand('exportBtn');
//});
function exportDocuments(){
	ProcessBar.init('执行进度，请稍候...');
	//组织参数
	var frmData = $('frmData');
	var sFields = getValuesByName('Field');
	var sSelectedFields = $('SelectedFields').value;
	if(sFields=="") {
		frmData.FieldsList.value = sSelectedFields;
	}
	else {
		frmData.FieldsList.value = sSelectedFields + "," + sFields;
	}
	
	//准备工作
	ProcessBar.addState('正在提交数据');
	ProcessBar.addState('成功执行完成');
	ProcessBar.start();
	
	//ge gfc add @ 2007-4-25 14:01 导出所有
	var bExportAll 	= <%=bExportAll%>;
	var nChannelId 	= <%=nChannelId%>;
	var nSiteId		= <%=nSiteId%>;
	//var sChannelIds = '<%=sChannelIds%>';
	//var bDiffedChnls= <%=bDiffedChnls%>;
	var params = {
		ExportExtFields: $('chkWithExtFields').checked,
		ExportAppendix: $('chkWithAppendixes').checked,
		ExportFields: frmData.FieldsList.value
	};
	if (bExportAll){
		params['exportAll'] = true;
		if (nChannelId > 0) {
			params['ChannelId'] = nChannelId;
		}else if (nSiteId > 0){
			params['SiteId'] = nSiteId;
		}
	}
	else{
		params['ObjectIds'] = '<%=CMyString.filterForJs(sChnlDocIds)%>';
	}
	BasicDataHelper.call('wcm6_viewdocument', 'export', params, true, function(_trans, _json){
		ProcessBar.exit();
		downloadFile(_trans.responseText);
	});
	FloatPanel.close();
	return false;
}

function downloadFile(_sFileUrl){
	var sFileUrl = _sFileUrl;
	var frm = (top.actualTop||top).$('iframe4download');
	if(frm==null){
		frm = (top.actualTop||top).document.createElement('IFRAME');
		frm.id = "iframe4download";
		frm.style.display = 'none';
		(top.actualTop||top).document.body.appendChild(frm);
	}
	sFileUrl = "../file/read_file.jsp?DownName=DOCUMENT&FileName=" + sFileUrl;
	frm.src = sFileUrl;
	FloatPanel.close(true);
}

function clickSelect() {
	var trShow = $('FieldList');
	if($('allFields').checked) {
		trShow.style.display = "none";
		FloatPanel.sizeBy(0,-120);
	}
	else {
		trShow.style.display = "";
		FloatPanel.sizeBy(0,120);
	}
}
function getValuesByName(_sName){
	var tmps = document.getElementsByName(_sName);
	var a = [];
	for(var i=0;i<tmps.length;i++){
		if(tmps[i].checked){
			a.push(tmps[i].value);
		}
	}
	return a.join(',');
}
function selectAllByName(_sName,_checked){
	var tmps = document.getElementsByName(_sName);
	for(var i=0;i<tmps.length;i++){
		tmps[i].checked = _checked;
	}
}
</SCRIPT>
</HEAD>

<BODY>
<FORM NAME="frmData" ID="frmData" action="document_export_dowith.jsp" style="margin:0;">
	<INPUT TYPE="hidden" name="DocumentIds" value="<%=CMyString.filterForHTMLValue(sDocumentId)%>">
	<INPUT TYPE="hidden" name="FieldsList" value="">
	<INPUT TYPE="hidden" name="WithAppendixes" value="1">
	<INPUT TYPE="hidden" name="WithExtFields" value="1">
	<DIV>
		<SPAN>
			<li><input type="checkbox" id="chkWithAppendixes" checked>同时导出文档附件<br>
		</SPAN>
	</DIV>
	<DIV>
		<SPAN>
			<li><input type="checkbox" id="chkWithExtFields" checked>同时导出扩展字段<br>
		</SPAN>
	</DIV>
	<DIV>
		<SPAN style="width:120px;">
			<li>选择要导出的字段: 
		</SPAN>
		<SPAN>
			<input type="checkbox" id="allFields" name="allFields" checked onClick="selectAllByName('Field', true);clickSelect();"><%=LocaleServer.getString("system.label.all", "全部")%><br>
		</SPAN>
	</DIV>
	<DIV id="FieldList" style="display:none;height:120px;overflow:auto;border:1px solid #eeeeee;padding:10px; margin-bottom: 10px;">
		<%
		List list = XMLConfigServer.getInstance().getConfigObjects(DocumentFieldConfig.class);
		StringBuffer sbSelected = new StringBuffer();
		DocumentFieldConfig currDocumentFieldConfig = null;
		for(java.util.Iterator it=list.iterator(); it.hasNext(); ) {
			currDocumentFieldConfig = (DocumentFieldConfig)it.next();
			if(currDocumentFieldConfig == null || currDocumentFieldConfig.getType() == 3)
				continue;
			if(currDocumentFieldConfig.getType() == 1) {
				if(sbSelected.length() == 0) {
					sbSelected.append(currDocumentFieldConfig.getFields());
				}
				else {
					sbSelected.append(",").append(currDocumentFieldConfig.getFields());
				}
			} else if(currDocumentFieldConfig.getType() == 2) {
		%>
			<SPAN style="width: 80px;">
				<input type="checkbox" checked name="Field" value="<%=currDocumentFieldConfig.getFields()%>"><%=currDocumentFieldConfig.getDesc()%>
			</SPAN>
		<%
			} else if(currDocumentFieldConfig.getType() == 4) {
		%>
			<SPAN style="width: 80px;">
				<input type="checkbox" checked name="Field" disabled value="<%=currDocumentFieldConfig.getFields()%>"><%=currDocumentFieldConfig.getDesc()%>
			</SPAN>
		<%
			}
		}
		%>
			<input type="hidden" id="SelectedFields" name="SelectedFields" value="<%=sbSelected.toString()%>">
</FORM>
</BODY>
</HTML>