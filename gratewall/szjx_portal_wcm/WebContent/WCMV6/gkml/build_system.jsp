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
<!------- WCM IMPORTS END ------------>


<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%
// 6. 判断权限
	if(!loginUser.isAdministrator()){
		throw new WCMException("您没有权限执行政府信息公开服务系统的创建！");
	}
// 7. 判断一些参数是否设置了，如果没有设置重新设置一次
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

	String sDeptCode = ConfigServer.getServer().getSysConfigValue(
                "DEPARTMENT_CODE", "000014348");

	//获取政府信息公开视图的Id
	MetaView oMetaView = GovInfoViewFinder.findMetaViewOfGovInfo(loginUser);
	int nMetaViewId = 39;
	if(oMetaView != null){
		nMetaViewId = oMetaView.getId();
	}

// 8. 结束
	out.clear();
%>
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>创建政府信息公开服务系统</TITLE>
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
	.title{
		text-align:center;
		font-size:16px;
	}
	.loading{
		position:absolute;
		width:100%;
		height:100%;
		left:0px;
		top:0px;
		background:url('images/build_system_loading.gif') center center no-repeat;
	}
</style>
<script src="../js/com.trs.util/Common.js"></script>
<script>
	$import('com.trs.web2frame.DataHelper');
	$import('com.trs.web2frame.TempEvaler');
	$import('com.trs.wcm.Web2frameDefault');
	$import('com.trs.wcm.MessageCenter');
	$import('com.trs.wcm.util.FileUploadHelper');
	$import('com.trs.util.YUIConnection');	
</script>
<script language="javascript">
<!--

//FloatPanel.addCloseCommand();
//FloatPanel.addCommand('importBtn', '导入', 'importClassInfos',null);
top.actualTop = window;

function onOK(){
	ProcessBar.init('执行进度，请稍候...');
	var sDocFileName = $("DocFile").value;
	if(!FileUploadHelper.validFileExt(sDocFileName, "txt")){
		return false;
	}
	ProcessBar.addState('正在上传文件:'+sDocFileName);
	ProcessBar.addState('正在初始化系统');
	ProcessBar.addState('成功执行完成');
	ProcessBar.start();
	

	// 上传其它分类法文件
	var sSubCatFile = $("SubCatFile").value;
	if(sSubCatFile.length>0){
		uploadSubCat();
		return;
	}
	var sThemeCatFile = $("ThemeCatFile").value;
	if(sThemeCatFile.length>0){
		uploadThemeCat();
		return;
	}
	
	// 发出上传文件的请求，在onFinishUpload.upload中处理上传成功后的操作
	var isSSL = location.href.indexOf("https://")!=-1;
	YUIConnect.setForm('frmUploadDocFile',true,isSSL);
	YUIConnect.asyncRequest('POST', '../system/file_upload_dowith.jsp?FileParamName=DocFile&ResponseType=2', onFinishUpload);
	return false;
}

function uploadSubCat(){	
	var sSubCatFile = $("SubCatFile").value;
	if(!FileUploadHelper.validFileExt(sSubCatFile, "txt")){
		alert("不是文本文件！");
		return false;
	}

	var onFinishUploadSubCat = {
		"upload" : function(_transport){
			var pReturnFileName = null;
			try{
				pReturnFileName = eval(_transport.responseText);
			}catch(error){
				pReturnFileName = [];
			}
			var bAlert = FileUploadHelper.fileUploadedAlert(pReturnFileName[0]||"",function(){
				ProcessBar.next();
			});
			if(bAlert)return;

			// 设置上传后的文件名
			frmBuildSystem.SubCatFileName.value = pReturnFileName[0];

			frmBuildSystem.SubCatContainCode.value = frmUploadSubCatFile.SubCatContainCode[0].checked ? "true" : "false";
			
			// 再次发出上传请求，如果定义了体裁分类，上传
			var sThemeCatFile = $("ThemeCatFile").value;
			if(sThemeCatFile.length>0){
				uploadThemeCat();
			}else{
				uploadOrganCat();			
			}
		}
	};
	
	var isSSL = location.href.indexOf("https://")!=-1;
	YUIConnect.setForm('frmUploadSubCatFile',true,isSSL);
	YUIConnect.asyncRequest('POST', '../system/file_upload_dowith.jsp?FileParamName=SubCatFile&ResponseType=2', onFinishUploadSubCat);
	
}

function uploadThemeCat(){	
	var sThemeCatFile = $("ThemeCatFile").value;
	if(!FileUploadHelper.validFileExt(sThemeCatFile, "txt")){
		alert("不是文本文件！");
		return false;
	}

	var onFinishUploadThemeCat = {
		"upload" : function(_transport){
			var pReturnFileName = null;
			try{
				pReturnFileName = eval(_transport.responseText);
			}catch(error){
				pReturnFileName = [];
			}
			var bAlert = FileUploadHelper.fileUploadedAlert(pReturnFileName[0]||"",function(){
				ProcessBar.next();
			});
			if(bAlert)return;

			// 设置上传后的文件名
			frmBuildSystem.ThemeCatFileName.value = pReturnFileName[0];

			frmBuildSystem.ThemeCatContainCode.value = frmUploadThemeCatFile.ThemeCatContainCode[0].checked ? "true" : "false";
			
			// 发出上传机构请求
			uploadOrganCat();			
		}
	};
	
	var isSSL = location.href.indexOf("https://")!=-1;
	YUIConnect.setForm('frmUploadThemeCatFile',true,isSSL);
	YUIConnect.asyncRequest('POST', '../system/file_upload_dowith.jsp?FileParamName=ThemeCatFile&ResponseType=2', onFinishUploadThemeCat);
	
}

function uploadOrganCat(){
	// 发出上传文件的请求，在onFinishUpload.upload中处理上传成功后的操作
	var isSSL = location.href.indexOf("https://")!=-1;
	YUIConnect.setForm('frmUploadDocFile',true,isSSL);
	YUIConnect.asyncRequest('POST', '../system/file_upload_dowith.jsp?FileParamName=DocFile&ResponseType=2', onFinishUpload);
	return false;
}

var onFinishUpload = {
	"upload" : function(_transport){
		var DocFileName = null;
		try{
			DocFileName = eval(_transport.responseText);
		}catch(error){
			DocFileName = [];
		}
		var bAlert = FileUploadHelper.fileUploadedAlert(DocFileName[0]||"",function(){
			ProcessBar.next();
		});
		if(bAlert)return;

		// 设置上传后的文件名
		frmBuildSystem.FileName.value = DocFileName[0];
		frmBuildSystem.ContainCode.value = frmUploadDocFile.ContainCode[0].checked ? "true" : "false";
		
		// 再次发出请求，执行业务：创建系统
		buildAllFromTextFile();			
	}
};

function buildAllFromTextFile(){
	Element.show('loading');
	var oHelper = new com.trs.web2frame.BasicDataHelper();
	oHelper.Call('wcm6_MetaDataDef','buildAllFromTextFile', "frmBuildSystem", true,
		function(_transport,_json){
			ProcessBar.exit();

			checkSystemBuilded();
		},
		function(_transport,_json){
			$render500Err(_transport,_json);			
		}
	);
}

<%
	int nConfigId = 0;
	Config oConfig = ConfigServer.getServer().getSysConfig("DEPARTMENT_CODE");
	if(oConfig != null){
		nConfigId = oConfig.getId();
	}
%>
function makeDeptCodeConfigXML(_sDeptCode){
	return '<WCMOBJ><PROPERTIES><CONFIGID><%=nConfigId%></CONFIGID><SITEID>0</SITEID>'
	+'<CKEY><![CDATA[DEPARTMENT_CODE]]></CKEY><CVALUE><![CDATA['+_sDeptCode+']]></CVALUE>'
	+'<CDESC>默认的机构代码</CDESC><CTYPE>20</CTYPE><ENCRYPTED>0</ENCRYPTED></PROPERTIES></WCMOBJ>';
}

function saveDeptCode(){
	if(frmSaveDeptCode.DeptCode.value.length <= 0){
		alert("您没有填写机构代码！");
		frmSaveDeptCode.DeptCode.focus();
		return;
	}	

	$('doSaveCodeTarget').onreadystatechange = function(){
		if("complete" == this.readyState){
			$('doSaveCodeTarget').onreadystatechange = null;
			//直接调用会导致上面的请求aborted,所以这里要处理一下
			window.location.reload();
		}
	};

	frmSaveDeptCode.ObjectXML.value = makeDeptCodeConfigXML(frmSaveDeptCode.DeptCode.value);
	frmSaveDeptCode.submit();

	//直接调用会导致上面的请求aborted,所以这里要处理一下
	//window.location.reload();
}

//判断系统是否已经创建完成
function checkSystemBuilded(){
	var sUrl = "build_system_check.jsp";
	new Ajax.Request(sUrl, {onComplete : systemBuildedChecked});
}

//用户信息文件名
var sFileName = null;
function systemBuildedChecked(transport, json){
	eval("var oResult=" + transport.responseText);
	if(oResult["isRunning"]){
		setTimeout(checkSystemBuilded, 1000);
		return;
	}
	Element.hide('loading');
	if(oResult["errorInfo"]){
		alert("出现异常：\n" + oResult["errorInfo"]);
	}
	if(oResult["userInfo"]){
		Element.show("divResult");
		Element.update("divResult", oResult["userInfo"]);
		sFileName = oResult["fileName"];
		download(sFileName);
	}
}

//下载用户信息文件
function download(sFileName){
	sFileName = sFileName || window.sFileName;
	if(sFileName == null) return;
	var sUrl = "../../file/read_file.jsp?FileName=" + sFileName;
	var downloadFrame = $('downloadFrame');
	if(downloadFrame == null){
		downloadFrame = document.createElement("iframe");
		downloadFrame.id = 'downloadFrame';
		document.body.appendChild(downloadFrame);
	}
	downloadFrame.src = sUrl;
}

//删除用户信息文件
Event.observe(window, 'beforeunload', function(){
	if(sFileName == null) return;
	var sUrl = "build_system_check.jsp?fileName=" + sFileName;
	new Ajax.Request(sUrl, {onException:function(){}});
});
</SCRIPT>
</HEAD>

<BODY>
	<div id="loading" class="loading" style="display:none;"></div>
	<DIV class="title">创建政府信息公开服务系统（同步创建机构分类、组织结构、展现层次）</DIV>

	<DIV class="row">
		<SPAN class="label" style="width:100%;">1、选择定义组织结构的文本文件(仅支持txt格式文件)：</SPAN>
		<SPAN style="margin-left:20px;display:block;">
			<FORM name="frmUploadDocFile" style="margin:0;padding:0;display:" enctype='multipart/form-data'>
				<input type="file" id="DocFile" name="DocFile" style="width:400px;">
				以分类代码区分层次:
				<input type="radio" name="ContainCode" value="true"> 是
				<input type="radio" name="ContainCode" value="false" checked> 否
			</FORM>
		</SPAN>
	</DIV>	
	
	<Form Id="frmBuildSystem">	
	<INPUT TYPE="hidden" NAME="FileName">
	<INPUT TYPE="hidden" NAME="ContainCode">
	<INPUT TYPE="hidden" NAME="SubCatFileName">
	<INPUT TYPE="hidden" NAME="SubCatContainCode">
	<INPUT TYPE="hidden" NAME="ThemeCatFileName">
	<INPUT TYPE="hidden" NAME="ThemeCatContainCode">

	<DIV class="row">
		<SPAN class="label">2、选择相关的视图</SPAN>
		<SPAN>
			<select name="ViewId">
		<%
		MetaViews views = MetaViews.openWCMObjs(null, new WCMFilter("", "", "VIEWINFOID"));
		for (int i = 0, nSize = views.size(); i < nSize; i++) {
			MetaView view = (MetaView) views.getAt(i);
			if (view == null)
				continue;

			String sSelected = view.getId()==nMetaViewId ? "selected" : "";

			out.println("<option value=\""+view.getId()+"\" "+sSelected+">" + view.getDesc() + "</option>");			
		}
		%>
			</select>			
		</SPAN>
	</DIV>

<%
/*
	<DIV class="row">
		<SPAN class="label">3、同步创建的展现层次</SPAN>
		<SPAN>
			<select name="Level">
				<option value="0">完全映射</option>
				<option value="1">映射到一级</option>
				<option value="2">映射到二级</option>
				<option value="3">映射到三级</option>
				<option value="4">映射到四级</option>
				<option value="5">映射到五级</option>
			</select>
		</SPAN>
	</DIV>
*/
%>
	<INPUT TYPE="hidden" NAME="Level" value="0">

	
	<DIV class="row">
		<SPAN class="label">3、确认创建的范围：</SPAN>
		<SPAN>
				<select Name="CreateMainSite">
					<option value="true">主站</option>
					<option value="false">各下级单位</option>
				</select>
		</SPAN>
	</DIV>	

	</Form>

<Form Id="frmSaveDeptCode" name="frmSaveDeptCode" action="../../console/config/config_addedit_dowith.jsp"
	target="doSaveCodeTarget" method="post">
	<INPUT TYPE="hidden" NAME="ConfigId" value="<%=nConfigId%>">		
	<INPUT TYPE="hidden" NAME="ObjectXML">
	<DIV class="row">
		<SPAN class="label">4、输入您的机构代码（例如：G0003405）</SPAN>
		<SPAN>
			<INPUT TYPE="text" NAME="DeptCode" value="<%=sDeptCode%>">	
		</SPAN>
	</DIV>	
</Form>	

	<DIV class="row">
		<SPAN class="label" style="width:100%;">5、选择定义主题分类的文本文件(仅支持txt格式文件)：</SPAN>
		<SPAN style="margin-left:20px;display:block;">
			<FORM name="frmUploadSubCatFile" style="margin:0;padding:0;display:" enctype='multipart/form-data'>
				<input type="file" id="SubCatFile" name="SubCatFile" style="width:400px;">
				以分类代码区分层次:
				<input type="radio" name="SubCatContainCode" value="true" checked> 是
				<input type="radio" name="SubCatContainCode" value="false"> 否
			</FORM>
		</SPAN>
	</DIV>	

	<DIV class="row">
		<SPAN class="label" style="width:100%;">6、选择定义体裁分类的文本文件(仅支持txt格式文件)：</SPAN>
		<SPAN style="margin-left:20px;display:block;">
			<FORM name="frmUploadThemeCatFile" style="margin:0;padding:0;display:" enctype='multipart/form-data'>
				<input type="file" id="ThemeCatFile" name="ThemeCatFile" style="width:400px;">
				以分类代码区分层次:
				<input type="radio" name="ThemeCatContainCode" value="true" checked> 是
				<input type="radio" name="ThemeCatContainCode" value="false"> 否
			</FORM>
		</SPAN>
	</DIV>	

	

	<iframe name="doSaveCodeTarget" id="doSaveCodeTarget" src="../blank.html" width=0 height=0></iframe>



	<div class="title">
		<INPUT TYPE="button" value="创建整个系统..." onclick="onOK();">&nbsp;&nbsp;
		<INPUT TYPE="button" value="单独设置机构代码" onclick="saveDeptCode();">
		<INPUT TYPE="button" value="下载用户密码文件" onclick="download();">&nbsp;&nbsp;
	</div>
	
	<div class="title">
		<a href="govinfo_resource_distribute.jsp?SiteType=4" title="向发布/预览目录下的站点拷贝需要用到的资源文件">往所有站点中同步展现用的公用文件</a>
	</div>

	<DIV class="row"  id="divResult">		
	</DIV>



</BODY>
</HTML>