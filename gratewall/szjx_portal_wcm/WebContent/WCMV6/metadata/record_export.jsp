<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.components.wcm.content.persistent.DocumentFieldConfig" %>
<%@ page import="com.trs.infra.config.XMLConfigServer" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.service.IChannelService" %>
<%@ page import="com.trs.service.ServiceHelper" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.components.metadata.definition.IMetaDataDefCacheMgr" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewField" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>

<!------- WCM IMPORTS END ------------>
<%@ page import="java.util.Map,java.util.Iterator" %>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	int nViewId = currRequestHelper.getInt("viewId", 0);
	String sChnlDocIds = CMyString.showNull(currRequestHelper.getString("ObjectIds"),"");
	String sDocumentId = CMyString.showNull(currRequestHelper.getString("DocumentIds"),"");
	boolean bExportAll = currRequestHelper.getBoolean("ExportAll", false);
	String sChannelIds = "";
	int nChannelId = 0;
	int nSiteId = 0;
	boolean bDiffedChnls = false;
	if(bExportAll) {
		nChannelId = currRequestHelper.getInt("ChannelId", 0);
		nSiteId = currRequestHelper.getInt("SiteId", 0);
		if (nChannelId <= 0 && nSiteId <= 0){
			throw new WCMException("导出所有记录时，至少输入[栏目ID-ChannelId]或者[站点ID-SiteId]！");
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
<TITLE>记录导出</TITLE>
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
FloatPanel.addCommand('exportBtn', '导出', 'exportViewDatas',null);
function exportViewDatas(){
	ProcessBar.init('执行进度，请稍候...');
	//组织参数
	var frmData = $('frmData');
	var sFields = "";
	if($('allFields').checked){
		//sFields = getAllValuesByName('Field');
		sFields = "";
	}else{
		sFields = getValuesByName('Field');
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
		viewId		: <%=nViewId%>,
		ObjectIds	: '<%=CMyString.filterForJs(sChnlDocIds)%>',
		ExportAppendix: $('chkWithAppendixes').checked,
		ExportFields: sFields
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
	sFileUrl = "../file/read_file.jsp?DownName=MetaViewData&FileName=" + sFileUrl;
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
	<DIV>
		<SPAN>
			<li><input type="checkbox" id="chkWithAppendixes" checked>同时导出文档附件<br>
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
			IMetaDataDefCacheMgr dataDefCache = (IMetaDataDefCacheMgr)DreamFactory.createObjectById("IMetaDataDefCacheMgr");
			MetaView oView = MetaView.findById(nViewId);
			boolean isMultiTable = oView.isMultiTable();
			Map mViewFieldMap = dataDefCache.getMetaViewFields(nViewId);
			if(mViewFieldMap != null && mViewFieldMap.size() > 0){
				for(Iterator itrViewField = mViewFieldMap.values().iterator(); itrViewField.hasNext();){
					MetaViewField oViewField = (MetaViewField)itrViewField.next();
					String sFieldName = isMultiTable ? oViewField.getName() : oViewField.getDBName();
		%>
			<SPAN style="width: 30%;">
				<input type="checkbox" name="Field" value="<%=sFieldName%>"><%=oViewField.getAnotherName()%>
			</SPAN>
		<%
				}
			}
		%>
			<SPAN style="width: 30%;">
				<input type="checkbox" name="Field" value="cruser">创建者
			</SPAN>
			<SPAN style="width: 30%;">
				<input type="checkbox" name="Field" value="crtime">创建时间
			</SPAN>
			<SPAN style="width: 30%;">
				<input type="checkbox" name="Field" value="channelid">所属栏目
			</SPAN>
</FORM>
</BODY>
</HTML>