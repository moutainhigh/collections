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
	// 2. 文件默认路径
    String sContextPath = request.getContextPath();
	String sAbsoluteWCMPath = CMyFile.extractFilePath(application.getRealPath(sContextPath));
	//String sFileDir = sAbsoluteWCMPath + "wcm\\WCMV6\\gkml\\data\\";
	//安全性问题，不泄露完整路径
	String sFileDir = "wcm\\WCMV6\\gkml\\data\\";
	// 3. 结束
	out.clear();%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/crashboard/resource/crashboard.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<title id="tlDoc">导入政府信息公开相关的视图</title>
<script src="../../app/js/easyversion/lightbase.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/easyversion/extrender.js"></script>
<script src="../../app/js/easyversion/ajax.js"></script>
<script src="../../app/js/easyversion/elementmore.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/source/wcmlib/Observable.js"></script>
<script src="../../app/js/source/wcmlib/dragdrop/dd.js"></script>
<script src="../../app/js/source/wcmlib/dragdrop/wcm-dd.js"></script>
<script src="../../app/js/source/wcmlib/Component.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoard.js"></script>
<script src="../../app/js/source/wcmlib/crashboard/CrashBoardAdapter.js"></script>
<script src="../js/source/wcmlib/util/YUIConnection.js"></script>
<script src="../js/source/wcmlib/components/ProcessBar.js"></script>

<script language="javascript">
var m_cb = null;
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
	cbSelf.setSize("630px","350px");
	cbSelf.center();
}
function onOk(){
	return uploadFile();
}
</script>
<!--Page scope-->
<script> 
	function uploadFile(){
		//浏览的两个视图文件的校验
		var sDocFileName = $("DocFile").value;
		if(!sDocFileName){
			alert("请选择xml文件！");
			return false;
		}
		try{
			FileUploadHelper.validFileExt(sDocFileName, ".xml");
		}catch(err){
			alert(err.message)
			return false;
		}
		var sChildDocFileName = $("ChildDocFile").value;
		if(!sChildDocFileName){
			alert("请选择xml文件！");
			return false;
		}
		try{
			FileUploadHelper.validFileExt(sChildDocFileName, ".xml");
		}catch(err){
			alert(err.message);
			return false;
		}
		//开始上传
		var isSSL = location.href.indexOf("https://")!=-1;
		var callBack1 = {
				"upload":function(_transport){
					//获取第一个文件上传后的参数
					try{
						DocFileName = eval(_transport.responseText);
					}catch(error){
						DocFileName = [];
					}
					var bAlert = FileUploadHelper.fileUploadedAlert(DocFileName[0]||"",function(){
					});
					if(bAlert)return false;
					var callBack2 = {
						"upload":function(_trans){
							ProcessBar.close();
							//获取第二次上传后的参数
							try{
								childDocFileName = eval(_trans.responseText);
							}catch(error){
								childDocFileName = [];
							}
							var bAlert = FileUploadHelper.fileUploadedAlert(DocFileName[0]||"",function(){
							});
							if(bAlert)return false;
							//组织两次的参数返回到打开窗口页面
							var oPostData = {
								"ImportViewFile" : DocFileName[0]||"",
								"ImportChildViewFile" : childDocFileName[0]||"",
								"importType" : 2 //出现重复英文名称则新建
							};
							m_cb.notify(oPostData);
							m_cb.hide();
							return false;
						}
					}
					//开始上传第二个文件
					YUIConnect.setForm('frmUploadChildDocFile',true,isSSL);
					YUIConnect.asyncRequest('POST','./file_upload_dowith.jsp?FileParamName=ChildDocFile&ResponseType=2',callBack2);
					return false;
				}
		}
		YUIConnect.setForm('frmUploadDocFile',true,isSSL);
		ProcessBar.start("上传视图");
		YUIConnect.asyncRequest('POST','./file_upload_dowith.jsp?FileParamName=DocFile&ResponseType=2',callBack1);
		return false;
	}
</SCRIPT>
<style>
	body{
		font-size:12px;		
	}
	.row{
		width:99%;
        margin:10px 0px;
		display:block;
		line-height:24px;		
	}
	.tip{
		color:red;
	}
</style>
<BASE TARGET="_self">
</HEAD>
<BODY>
<DIV class="row">
<DIV class="row tip">
	    <span>完成此步骤,需要上传一些文件,系统默认文件所在目录为：<%=sFileDir%><br></span>
		<span>每一个浏览项的title信息提示您默认的文件路径</span>
	</DIV>
	1、<SPAN WCMAnt:param="classinfo_import.jsp.selFile">选择您要装载的政府信息公开的视图文件(支持xml格式文件,默认文件为:govinfo_view.xml)：</SPAN>
	<SPAN style="margin-left:20px;display:block;">
		<FORM name="frmUploadDocFile" style="margin:0;padding:0;" enctype='multipart/form-data'>
			<input type="file" id="DocFile" name="DocFile" style="width:400px;" title="<%=sFileDir%>govinfo_view.xml">
		</FORM>
	</SPAN>
</DIV>
<DIV class="row">
	2、<SPAN WCMAnt:param="classinfo_import.jsp.selFile">选择您要装载的下级政府信息公开的视图文件(支持xml格式文件,默认文件为:child_govinfo_view.xml)：</SPAN>
	<SPAN style="margin-left:20px;display:block;">
		<FORM name="frmUploadChildDocFile" style="margin:0;padding:0;" enctype='multipart/form-data'>
			<input type="file" id="ChildDocFile" name="ChildDocFile" style="width:400px;" title="<%=sFileDir%>child_govinfo_view.xml">
		</FORM>
	</SPAN>
</DIV>
</BODY>
</HTML>