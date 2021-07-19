<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.DocumentFieldConfig" %>
<%@ page import="com.trs.infra.config.XMLConfigServer" %>
<%@ page import="com.trs.service.IChannelService" %>
<%@ page import="com.trs.service.ServiceHelper" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.components.metadata.definition.IMetaDataDefCacheMgr" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewField" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewFields" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.components.metadata.center.IMetaViewEmployerMgr" %>

<!------- WCM IMPORTS END ------------>
<%@ page import="java.util.Map,java.util.Iterator" %>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	int nViewId = currRequestHelper.getInt("viewId", 0);
	String sChnlDocIds = CMyString.showNull(currRequestHelper.getString("ObjectIds"),"");
	String sDocumentId = CMyString.showNull(currRequestHelper.getString("DocumentIds"),"");
	boolean bExportAll = currRequestHelper.getBoolean("exportAll", false);
	String sChannelIds = "";
	int nChannelId = 0;
	int nSiteId = 0;
	boolean bDiffedChnls = false;
	nChannelId = currRequestHelper.getInt("ChannelId", 0);
	nSiteId = currRequestHelper.getInt("SiteId", 0);
	if(bExportAll) {
		if (nChannelId <= 0 && nSiteId <= 0){
			throw new WCMException( LocaleServer.getString("record_export.need.id","导出所有记录时，至少输入[栏目ID-ChannelId]或者[站点ID-SiteId]！"));
		}
	}else{
	}
//5.权限校验

//6.业务代码

//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE WCMAnt:param="record_export.jsp.title">记录导出</TITLE>
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
	#FieldList{
		height:120px;
		width:100%;
		overflow:auto;
		margin-top:10px;
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
<!--ProcessBar Start-->
<script src="../../app/js/source/wcmlib/components/ProcessBar.js"></script>
<link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">
<link rel="stylesheet" type="text/css" href="../../app/js/source/wcmlib/components/processbar.css">
<!--ProcessBar End-->
<script language="javascript">
	window.m_fpCfg = {
		m_arrCommands : [{
			cmd : 'exportViewDatas',
			name : wcm.LANG.METAVIEWDATA_11 || '导出'
		}],
		size : [500, 220]
	};
</script>
<script language="javascript">
function exportViewDatas(){
	ProcessBar.init(wcm.LANG.METAVIEWDATA_4 || '执行进度，请稍候...');
	//组织参数
	var frmData = $('frmData');
	var sFields = "";
	if($('allFields').checked){
		//sFields = getAllValuesByName('Field');
		sFields = "";
	}else{
		sFields = getValuesByName('Field');
		if(sFields.length == 0){
			alert(wcm.LANG.METAVIEWDATA_128 || "请选择导出字段.");
			return false;
		}
	}
	
	//准备工作
	ProcessBar.addState(wcm.LANG.METAVIEWDATA_5 || '提交数据');
	ProcessBar.addState(wcm.LANG.METAVIEWDATA_6 || '成功执行完成');
	ProcessBar.start();
	
	//ge gfc add @ 2007-4-25 14:01 导出所有
	var bExportAll 	= <%=bExportAll%>;
	var nChannelId 	= <%=nChannelId%>;
	var nSiteId		= <%=nSiteId%>;
	//var sChannelIds = '<%=sChannelIds%>';
	//var bDiffedChnls= <%=bDiffedChnls%>;
	var params = {
		ViewId		: <%=nViewId%>,
		ObjectIds	: '<%=CMyString.filterForJs(sChnlDocIds)%>',
		ExportAppendix: $('chkWithAppendixes').checked,
		ExportFields: sFields,
		FileType : $$F('chkFileType')
	};
	if (bExportAll){
		params['ExportAll'] = true;
		if (nChannelId > 0) {
			params['ChannelId'] = nChannelId;
		}else if (nSiteId > 0){
			params['SiteId'] = nSiteId;
		}
	}
	else{
		params['ObjectIds'] = '<%=CMyString.filterForJs(sChnlDocIds)%>';
	}
	if(params['ExportAll'] || params['ExportAll']=="true"){
		BasicDataHelper.call(
			"wcm61_metaviewdata", //远端服务名				
			"exportAllViewDatas", //远端方法名				
			params,//提交数据
			true, //post,get
			function(transport, json){//响应函数
				ProcessBar.close();
				downloadFile(transport.responseText);
			}
		);
	}else{
		BasicDataHelper.call(
				"wcm61_metaviewdata", //远端服务名				
				"exportViewDatas", //远端方法名				
				params,//提交数据
				true, //post,get
				function(transport, json){//响应函数
					ProcessBar.close();
					downloadFile(transport.responseText);
				}
		);
	}
	//FloatPanel.close();
	return false;
}

function downloadFile(_sFileUrl){
	var sFileUrl = _sFileUrl;
	var frm = $MsgCenter.getActualTop().$('iframe4download');
	if(frm==null){
		frm = $MsgCenter.getActualTop().document.createElement('IFRAME');
		frm.id = "iframe4download";
		frm.style.display = 'none';
		$MsgCenter.getActualTop().document.body.appendChild(frm);
	}
	sFileUrl = "../file/read_file.jsp?DownName=MetaViewData&FileName=" + sFileUrl;
	frm.src = sFileUrl;
	FloatPanel.close();
}

function clickSelect() {
	var trShow = $('FieldList');
	if($('allFields').checked) {
		trShow.style.display = "none";
		window.parent.makeSizeTo(500,100);
	}
	else {
		trShow.style.display = "";
		window.parent.makeSizeTo(500,220);
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

	//add default fields
	//a.push('channelid','crtime','cruser');
	return a.join(',');
}
function getAllValuesByName(_sName){
	var tmps = document.getElementsByName(_sName);
	var a = [];
	for(var i=0;i<tmps.length;i++){
		a.push(tmps[i].value);
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
<FORM NAME="frmData" ID="frmData" action="" style="margin:0;">
	<INPUT TYPE="hidden" name="DocumentIds" value="<%=CMyString.filterForHTMLValue(sDocumentId)%>">
	<INPUT TYPE="hidden" name="FieldsList" value="">
	<INPUT TYPE="hidden" name="WithAppendixes" value="1">
	<DIV style="height:auto;">
		<SPAN>
			<li><input type="radio" name="chkFileType" value=1 checked><span WCMAnt:param="record_export.jsp.fileType1">导出为xml格式</span>
			<input type="radio" name="chkFileType" value=0><span WCMAnt:param="record_export.jsp.fileType0">导出为excel格式</span><br>
		</SPAN>
		<SPAN>
			<li><input type="checkbox" id="chkWithAppendixes" checked><span WCMAnt:param="record_export.jsp.appendixExport">同时导出文档附件</span><br>
		</SPAN>
		<SPAN>
			<li><span WCMAnt:param="record_export.jsp.fieldExport">选择要导出的字段: </span>
			<input type="checkbox" id="allFields" name="allFields" onClick="selectAllByName('Field', this.checked);"><%=LocaleServer.getString("system.label.all", "全部")%>
		</SPAN>
	</DIV>
	<DIV id="FieldList">
	<table style="border-top:1px;width:100%;">
		<%
			
			IMetaViewEmployerMgr metaViewEmployerMgr = (IMetaViewEmployerMgr)DreamFactory.createObjectById("IMetaViewEmployerMgr");
			Channel oChannel = null;
			MetaView oView = null;
			if(nChannelId > 0){
				oChannel = Channel.findById(nChannelId);
				oView = metaViewEmployerMgr.getViewOfEmployer(oChannel);
			}
			if(nChannelId == 0 && nViewId > 0){
				oView = MetaView.findById(nViewId);
			}
			boolean isMultiTable = oView.isMultiTable();
			MetaViewFields oMetaViewFields = oView.getViewFields(loginUser,null);
			if(oMetaViewFields != null && oMetaViewFields.size() > 0){
				for(int i = 0; i < oMetaViewFields.size(); i++){
					MetaViewField oViewField = (MetaViewField)oMetaViewFields.getAt(i);
					String sFieldName = isMultiTable ? oViewField.getName() : oViewField.getDBName();
					if(i%3==0 && i+3 < oMetaViewFields.size()){	
						out.print("<tr>");
					}
					
		%>
			<td width="33%">
				<input type="checkbox" name="Field" value="<%=sFieldName%>"><%=oViewField.getAnotherName()%>
			</td>
		<%
					if((i+1)%3==0){	
						out.print("</tr>");
					}
				 }
			}
		%>
			<tr>
			<td>
				<input type="checkbox" name="Field" value="cruser"><span WCMAnt:param="record_export.jsp.cruser">创建者</span>
			</td>
			<td>				
			<input type="checkbox" name="Field" value="crtime"><span WCMAnt:param="record_export.jsp.crtime">创建时间</td>
			</SPAN>
			<td>
				<input type="checkbox" name="Field" value="channelid" checked="checked"><span WCMAnt:param="record_export.jsp.toChannel">所属栏目</span>
			</td>
			</tr>
		</table>
	</DIV>
</FORM>
</BODY>
</HTML>