<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.cms.auth.domain.AuthServer" %>
<%@ page import="com.trs.components.wcm.content.persistent.Channel" %>
<%@ page import="com.trs.infra.common.WCMRightTypes" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.components.govinfo.GovInfoViewFinder" %>
<%@ page import="com.trs.components.metadata.definition.MetaViews" %>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.support.config.Config" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@ page import="com.trs.infra.util.CMyFile"%>
<!------- WCM IMPORTS END ------------>
<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%
// 1. 判断权限
	if(!loginUser.isAdministrator()){
		throw new WCMException("您没有权限执行政府信息公开服务系统的创建！");
	}
	// 2 获取是否创建子站的参数
	String sCreateSubSite = request.getParameter("CreateSubSite");
	if(CMyString.isEmpty(sCreateSubSite)){
		sCreateSubSite = "false";
	}
// 2. 判断一些参数是否设置了，如果没有设置重新设置一次
	String[][] pDefaultConfigs = { //
	//
			{ "DEPARTMENT_CODE", "默认的机构代码", "000014348" },//
			{ "DEFAULT_CHANNEL", "默认存储栏目", "0" },//
			{ "GROUP_NAME_DEPARTMENT", "反映出用户实际组织结构的顶级组织名称", "组织机构" },//
			{ "NAME_BY_TEMPLATE_FILE", "是否启用按照模板文件名直接命名", "true" },//
			{ "GOV_PUBLISH_SITEIDS", "信息公开首页默认发布的站点ID序列，多个以逗号隔开", "6" },//
			{ "GOV_SITE_URL", "信息公开首页的站点首页地址", "http://gkml.demo.trs.cn/" },//
			{ "GOV_MAIN_SITEID", "信息公开首页的默认主站ID", "6" },//
			{ "WITH_DEPT_FILTER", "启动按照部门过滤", "true" } //

	};
	for (int i = 0; i < pDefaultConfigs.length; i++) {
		String[] pDefaultConfig = pDefaultConfigs[i];
		if (ConfigServer.getServer().getSysConfig(pDefaultConfig[0]) == null) {
			Config newConfig = Config.createNewInstance();
			newConfig.setConfigKey(pDefaultConfig[0]);
			newConfig.setDesc(pDefaultConfig[1]);
			newConfig.setType(Config.TYPE_CUSTOM_ADD);
			newConfig.setValue(pDefaultConfig[2]);
			newConfig.save();
		}
	}
	// 3. 获取政府信息公开视图的Id和初始化组织机构的文件名
	int nMetaViewId = 39;
	String sOrgancatFileName = "organcat.txt";
	if("true".equalsIgnoreCase(sCreateSubSite)){
		nMetaViewId = 43;
		MetaView oSubMetaView = GovInfoViewFinder.findMetaViewOfChildGovInfo(loginUser);
		if(oSubMetaView != null){
			nMetaViewId = oSubMetaView.getId();
		}
		sOrgancatFileName = "childorgancat.txt";
	}else{
		MetaView oMetaView = GovInfoViewFinder.findMetaViewOfGovInfo(loginUser);
		if(oMetaView != null){
			nMetaViewId = oMetaView.getId();
		}
	}
	// 4. 文件默认路径
    String sContextPath = request.getContextPath();
	String sAbsoluteWCMPath = CMyFile.extractFilePath(application.getRealPath(sContextPath));
	String sFileDir = sAbsoluteWCMPath + "WCMV6\\gkml\\data\\";
	// 5. 结束
	out.clear();%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>创建政府信息公开服务系统</TITLE>
<style>
	html, body{
		height:100%;
		width:100%;
		padding:0px;
		margin:0px;
		overflow:hidden;
	}
	body{
		font-size:12px;
	}
	.row{
		width:100%;
        margin:10px 5px;
		display:block;
		line-height:24px;
	}
	.label{
        width:140px;
	}
	.title{
		text-align:center;
		font-size:16px;
	}
	.tip{
		color:red;
	}
</style>
<link rel="stylesheet" type="text/css" href="../js/source/wcmlib/components/processbar.css">
<link href="../../app/js/easyversion/resource/crashboard.css" rel="stylesheet" type="text/css" />
<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../js/easyversion/extrender.js"></script>
<script src="../js/easyversion/elementmore.js"></script>
<script src="../../app/js/easyversion/ajax.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/source/wcmlib/Observable.js"></script>
<script src="../../app/js/source/wcmlib/dragdrop/dd.js"></script>
<script src="../../app/js/source/wcmlib/dragdrop/wcm-dd.js"></script>
<script src="../../app/js/source/wcmlib/Component.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></script>
<script src="../../app/js/easyversion/basicdatahelper.js"></script>
<script src="../../app/js/easyversion/web2frameadapter.js"></script>
<script src="../js/source/wcmlib/util/YUIConnection.js"></script>
<script src="../js/source/wcmlib/components/ProcessBar.js"></script>

<script language="javascript">
<!--
var m_cb = null;
var hasError = false;
var bCreateSubSite = "<%=CMyString.filterForJs(sCreateSubSite)%>";
window.m_cbCfg = {
        btns : [
            {
                text : '确定',
                cmd : function(){
                    return onOk();
                }
            }
        ]
	};
function init(param){
	m_cb = wcm.CrashBoarder.get(window);
	var cbSelf = wcm.CrashBoarder.get(window).getCrashBoard();
	cbSelf.setSize("700px","350px");
	cbSelf.center();
}
function onOk(){
	if(!validFile()){
		return false;
	}
	uploadFile();
	return false;
}

function validFile(){
	//校验需要上传的文件
	//定义组织结构口的文件
	var sFileName = $("FileName").value;
	if(!sFileName){
		alert("请选择定义组织结构的文件！");
		return false;
	}
	// 原来的方法直接判断返回值，其实当判断不合符要求的时候是抛出的异常
	try{
		FileUploadHelper.validFileExt(sFileName, "txt")
	}catch(err){
		alert("定义组织结构的文件不是文本文件！");
		return false;
	}

	//定义主题分类的文本文件
	var sSubCatFile = $("SubCatFile").value;
	if(!sSubCatFile){
		alert("请选择定义主题分类的文件！");
		return false;
	}
	
	// 原来的方法直接判断返回值，其实当判断不合符要求的时候是抛出的异常
	try{
		FileUploadHelper.validFileExt(sSubCatFile, "txt")
	}catch(err){
		alert("定义主题分类的文件不是文本文件！");
		return false;
	}

	//定义体裁分类的文本文件
	var sThemeCatFile = $("ThemeCatFile").value;
	if(!sThemeCatFile){
		alert("请选择定义体裁分类的文件！");
		return false;
	}

	// 原来的方法直接判断返回值，其实当判断不合符要求的时候是抛出的异常
	try{
		FileUploadHelper.validFileExt(sThemeCatFile, "txt")
	}catch(err){
		alert("定义体裁分类的文件不是文本文件！");
		return false;
	}
	return true;
}
function uploadFile(){
	//每一个文件的回调中上传下一个文件
	//首先上传主站系统组织结构分类文件
	var onFileFinishUpload = {
		"upload" : function(_transport){
			var FileName = null;
			try{
				FileName = eval(_transport.responseText);
			}catch(error){
				FileName = [];
			}
			var bAlert = FileUploadHelper.fileUploadedAlert(FileName[0]||"",function(){
			});
			if(bAlert)return false;
		
			// 设置上传后的文件名
			frmBuildSystem.FileName.value = FileName[0];
			uploadSubCat();
		}
	}
	ProcessBar.start("上传文件");
	var isSSL = location.href.indexOf("https://")!=-1;
	YUIConnect.setForm('frmUploadFile',true,isSSL);
	YUIConnect.asyncRequest('POST', './file_upload_dowith.jsp?FileParamName=FileName&ResponseType=2', onFileFinishUpload); 
	return false;
}
function uploadSubCat(){
	var onFinishUploadSubCat = {
		"upload" : function(_transport){
			var pReturnFileName = null;
			try{
				pReturnFileName = eval(_transport.responseText);
			}catch(error){
				pReturnFileName = [];
			}
			var bAlert = FileUploadHelper.fileUploadedAlert(pReturnFileName[0]||"",function(){
			});
			if(bAlert)return false;
			// 设置上传后的文件名
			frmBuildSystem.SubCatFileName.value = pReturnFileName[0];

			// 再次发出上传请求，如果定义了体裁分类，上传
			var sThemeCatFile = $("ThemeCatFile").value;
			uploadThemeCat();
		}
	};
	var isSSL = location.href.indexOf("https://")!=-1;
	YUIConnect.setForm('frmUploadSubCatFile',true,isSSL);
	YUIConnect.asyncRequest('POST', './file_upload_dowith.jsp?FileParamName=SubCatFile&ResponseType=2', onFinishUploadSubCat);
}

function uploadThemeCat(){	
	var onFinishUploadThemeCat = {
		"upload" : function(_transport){
			var pReturnFileName = null;
			try{
				pReturnFileName = eval(_transport.responseText);
			}catch(error){
				pReturnFileName = [];
			}
			var bAlert = FileUploadHelper.fileUploadedAlert(pReturnFileName[0]||"",function(){
			});
			if(bAlert)return false;
			// 设置上传后的文件名
			frmBuildSystem.ThemeCatFileName.value = pReturnFileName[0];
			//至此，文件全部上传完成，从form中收集返回的数据,提交数据
			buildFormData();
		}
	};
	var isSSL = location.href.indexOf("https://")!=-1;
	YUIConnect.setForm('frmUploadThemeCatFile',true,isSSL);
	YUIConnect.asyncRequest('POST', './file_upload_dowith.jsp?FileParamName=ThemeCatFile&ResponseType=2', onFinishUploadThemeCat);
}
function buildFormData(){
	ProcessBar.close();
	var params = {
		ViewId : frmBuildSystem.ViewId.value,
		FileName : frmBuildSystem.FileName.value,
		ContainCode : 'false',
		SubCatFileName : frmBuildSystem.SubCatFileName.value,
		SubCatContainCode : 'true',
		ThemeCatFileName : frmBuildSystem.ThemeCatFileName.value,
		ThemeCatContainCode : 'true',
		Level : 0,
		CreateMainSite :  bCreateSubSite == 'true' ? false : true,
		AutoCreateChannel : true
	}
	//发送服务，请求
	ProcessBar.start("创建系统");
	var dataHelper = new com.trs.web2frame.BasicDataHelper();
	dataHelper.Call('wcm6_MetaDataDef','buildAllFromTextFile', params, true,
		function(_transport, _json){
			checkSystemBuilded();
		},
		function(_transport,_json){
			$render500Err(_transport,_json);			
		}
	);
}
//判断系统是否已经创建完成
function checkSystemBuilded(){
	var sUrl = "build_system_check.jsp";
	new Ajax.Request(sUrl, {onComplete : systemBuildedChecked});
}
//用户信息文件名
var UserInfoFile = [];
function systemBuildedChecked(transport, json){
	eval("var oResult=" + transport.responseText);
	if(oResult["isRunning"]){
		setTimeout(checkSystemBuilded, 1000);
		return false;
	}
	if(oResult["errorInfo"]){
		alert("出现异常：\n" + oResult["errorInfo"] + "\n请查看后台信息或者日志文件确认问题，之后重新选择文件创建系统！");
		hasError = true;
		ProcessBar.close();
	}
	if(oResult["userInfo"]){
		hasError = false;
		sFileName = oResult["fileName"];
		if(sFileName){
			var param = {};
			if(bCreateSubSite == 'true'){
				param.SubSiteUserInfo = sFileName;
			}else{
				param.MainSiteUserInfo = sFileName;
			}
			ProcessBar.close();
			 if(m_cb){
				m_cb.notify(param);
				m_cb.hide();
			}
			return false;
		}		
	}
}
</SCRIPT>
</HEAD>
<BODY>
	<DIV class="title">创建政府信息公开服务系统</DIV>
	<DIV class="row">
	    <span>完成此步骤,需要上传一些文件,系统默认文件所在目录为：<span class="tip"><%=sFileDir%></span><br></span>
		<span>每一个浏览项的title信息提示您默认的文件路径</span>
	</DIV>
	<DIV class="row">
		<SPAN class="label" style="width:100%;">1、选择定义组织结构的文本文件(仅支持txt格式文件,默认文件为：<%=sOrgancatFileName%>)：</SPAN>
		<SPAN style="margin-left:20px;display:block;">
			<FORM name="frmUploadFile" style="margin:0;padding:0;" enctype='multipart/form-data'>
				<input type="file" id="FileName" name="FileName" title="<%=(sFileDir + sOrgancatFileName)%>" style="width:400px;">
			</FORM>
		</SPAN>
	</DIV>
	<Form Id="frmBuildSystem">	
		<INPUT TYPE="hidden" NAME="FileName">
		<INPUT TYPE="hidden" NAME="SubCatFileName">
		<INPUT TYPE="hidden" NAME="ThemeCatFileName">
		<DIV class="row">
			<SPAN class="label">2、设置对应的视图：</SPAN>
			<SPAN>
				<select name="ViewId">
			<%
			WCMFilter filter = new WCMFilter("", "viewname=?", "VIEWINFOID");
			filter.addSearchValues("GovInfo");
			MetaViews views = MetaViews.openWCMObjs(null, filter);
			for (int i = 0, nSize = views.size(); i < nSize; i++) {
				MetaView view = (MetaView) views.getAt(i);
				if (view == null)
					continue;

				String sSelected = view.getId() == nMetaViewId ? "selected" : "";

				out.println("<option value=\""+view.getId()+"\" "+sSelected+">" + view.getDesc() + "</option>");			
			}
			%>
				</select>			
			</SPAN>
		</DIV>
		<INPUT TYPE="hidden" NAME="Level" value="0">
	</Form>
	<DIV class="row">
		<SPAN class="label" style="width:100%;">3、选择定义主题分类的文本文件(仅支持txt格式文件,默认文件为：subcat.txt)：</SPAN>
		<SPAN style="margin-left:20px;display:block;">
			<FORM name="frmUploadSubCatFile" style="margin:0;padding:0;" enctype='multipart/form-data'>
				<input type="file" id="SubCatFile" name="SubCatFile" title="<%=sFileDir%>subcat.txt" style="width:400px;">
			</FORM>
		</SPAN>
	</DIV>	
	<DIV class="row">
		<SPAN class="label" style="width:100%;">4、选择定义体裁分类的文本文件(仅支持txt格式文件，默认文件为：themecat.txt)：</SPAN>
		<SPAN style="margin-left:20px;display:block;">
			<FORM name="frmUploadThemeCatFile" style="margin:0;padding:0;" enctype='multipart/form-data'>
				<input type="file" id="ThemeCatFile" name="ThemeCatFile" style="width:400px;" title="<%=sFileDir%>themecat.txt">
			</FORM>
		</SPAN>
	</DIV>
</BODY>
</HTML>