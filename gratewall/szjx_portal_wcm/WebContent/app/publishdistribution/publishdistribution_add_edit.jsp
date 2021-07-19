<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page errorPage="../include/error_for_dialog.jsp"%>
<%@ page import="com.trs.components.common.publish.persistent.distribute.PublishDistribution" %>
<%@ page import="com.trs.components.common.publish.persistent.distribute.PublishDistributions" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.common.WCMTypes" %>
<%@include file="../include/public_server.jsp"%>
<%
	//接受页面参数
	int nObjectId = currRequestHelper.getInt("ObjectId",0);
	int nSiteId = currRequestHelper.getInt("SiteId",0);
	PublishDistribution currPublishDistribution = null,newPublishDistribution = null;
	PublishDistributions currPublishDistributions = null;
	String sServerAddress = "";
	int nPort = 0;
	String sLoginUser = "";
	String sLoginPassWord = "";
	String sDataPath = "";
	if(nObjectId == 0){
		currPublishDistribution = PublishDistribution.createNewInstance();
	}else{
		currPublishDistribution = PublishDistribution.findById(nObjectId);
		//参数校验
		if(currPublishDistribution == null){
		    throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, CMyString.format(LocaleServer.getString("object.not.found", "没有找到指定的{1} [ID={0}]."), new String[]{String.valueOf(nObjectId),WCMTypes.getLowerObjName(PublishDistribution.OBJ_TYPE)}));
		}
		sServerAddress = CMyString.filterForHTMLValue(currPublishDistribution.getTargetServer());
		nPort = currPublishDistribution.getTargetPort();
		sLoginUser = CMyString.filterForHTMLValue(currPublishDistribution.getLoginUser());
		sLoginPassWord = CMyString.filterForHTMLValue(currPublishDistribution.getLoginPassword());
		sDataPath = CMyString.filterForHTMLValue(currPublishDistribution.getDataPath());
	} 
%>
<HTML xmlns:TRS>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/system.js"></script>
<script src="../../app/js/data/locale/publishdistribution.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<!--wcm-dialog start-->
<SCRIPT src="../../app/js/source/wcmlib/Observable.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/Component.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/Dialog.js"></SCRIPT>
<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<!--wcm-dialog end-->
<!--FloatPanel Inner Start-->
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanelAdapter.js"></script>
<link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">
<SCRIPT src="../../app/js/source/wcmlib/core/LockUtil.js"></SCRIPT>
<!--AJAX-->
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<!-- Validator-->
<script src="../../app/js/source/wcmlib/com.trs.validator/lang/cn.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/Validator.js"></script>
<link href="../../app/js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />
<!--ProcessBar Start-->
<script src="../../app/js/source/wcmlib/components/ProcessBar.js"></script>
<link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">
<link rel="stylesheet" type="text/css" href="../../app/js/source/wcmlib/components/processbar.css">
<!--ProcessBar End-->
<!--page js-->
<script language="JavaScript" src="publishdistribution_add_edit.js"></script>
<style type="text/css">

    .row_title{
        width:80px;
        padding-left:10px;
    }
	.ext-safari .row_title{
		display:inline-block;
	}
	.ext-gecko .row_title{
		display:-moz-inline-stack;
	}
    .row_content{
        width:200px;
        padding:0px;
    }
    .check_row{
        padding-left:10px;
    }
    .input_class{
        width:150px;
    }
	.body{
		font-size:12px;
	}
	.divPadding{
		padding-top:5px;
	}
	.ext-safari .divPadding{
		padding-top:1px;
	}
</style>
<script language="javascript">
	window.m_fpCfg = {
		m_arrCommands : [{
			cmd : 'saveDistribution',
			name : wcm.LANG.PUBLISHDISTRUBUTION_SURE || '确定'
		}],
		size : [350, 220]
	};
</script>
</head>
<body class="body">
<form id="distributionForm">
<input type="hidden" name="FolderType" id="FolderType" value="103">
<input type="hidden" name="FolderId" id="FolderId" value="<%=nSiteId%>">
<input type="hidden" name="ObjectId" id="ObjectId" value="<%=nObjectId%>">
<div id="distributionId">
    <div>
        <span class="row_title" WCMAnt:param="publishdistribution_add_edit.jsp.type">类型:</span>
        <span class="row_content">
            <select name="TargetType" id="TargetType" style="font-size:12px;" onchange="changeTargetType(this.value)">
                <option value="SFTP" WCMAnt:param="publishdistribution_add_edit.jsp.SFTP">SFTP服务</option>
                <option value="FILE" selected WCMAnt:param="publishdistribution_add_edit.jsp.FILE">文件系统</option>
                <option value="FTP" WCMAnt:param="publishdistribution_add_edit.jsp.FTP">FTP服务</option>
            </select>
        </span>	
        <span id="TargetTypeAss" style="display:none;"><%=nObjectId==0?"":currPublishDistribution.getTargetType()%></span>
    </div>
    <div id="forFtp" style="display:none;">
        <div class="divPadding">
            <span class="row_title" WCMAnt:param="publishdistribution_add_edit.jsp.address">服务器地址:</span>
            <span class="row_content">
                <input type="text" name="TargetServer" id="TargetServer" value="<%=sServerAddress%>" class="input_class">
                <span style="color:red">*</span>
            </span>
        </div>
        <div class="divPadding">
            <span class="row_title" WCMAnt:param="publishdistribution_add_edit.jsp.port">服务器端口:</span>
            <span class="row_content">
                <input type="text" name="TargetPort" id="TargetPort" value="<%=nPort%>" class="input_class">
                <span style="color:red">*</span>
            </span>
        </div>
        <div class="divPadding">
            <span class="check_row" id="checkInfo">
                <input type="checkbox" name="AnonymFtp" id="AnonymFtp" isBoolean="true" value="<%=currPublishDistribution.getPropertyAsInt("ANONYMFTP",0)%>" onclick="toggleUserInfo(this.checked);"><label for = "AnonymFtp" WCMAnt:param="publishdistribution_add_edit.jsp.annonymouse">匿名登录</label>
                <input type="checkbox" name="PassiveMode" id="PassiveMode" isBoolean="true" value="<%=currPublishDistribution.getPropertyAsInt("PASSIVEMODE",0)%>"><label for="PassiveMode" WCMAnt:param="publishdistribution_add_edit.jsp.passive">使用被动模式</label>
            </span>    
        </div>
        <div id="userInfo">
            <div class="divPadding">
                <span class="row_title" WCMAnt:param="publishdistribution_add_edit.jsp.username">用户名:</span>
                <span class="row_content">
                    <input type="text" name="LoginUser" id="LoginUser" value="<%=sLoginUser%>" class="input_class">
                    <span style="color:red" id="userRed">*</span>
                </span>
            </div>
            <div class="divPadding">
                <span class="row_title" WCMAnt:param="publishdistribution_add_edit.jsp.password">密码：</span>
                <span class="row_content">
                    <input type="password" name="LoginPassword" id="LoginPassword" value="<%=sLoginPassWord%>" class="input_class">
                    <span style="color:red" id="passwordRed">*</span>
                </span>
            </div>
        </div>
    </div>
    <div class="divPadding">
        <span class="row_title" id="pathDesc" WCMAnt:param="publishdistribution_add_edit.jsp.databath">存放路径:</span>
        <span class="row_content">
            <input type="text" name="DataPath" id="DataPath" value="<%=sDataPath%>" class="input_class">
            <span style="color:red">*</span>
        </span>
    </div>
    <div class="divPadding">
        <span class="check_row"><input type="checkbox" name="Enabled" id="Enabled" isBoolean="true" value="<%=currPublishDistribution.getPropertyAsInt("ENABLED",0)%>"><label for="Enabled" WCMAnt:param="publishdistribution_add_edit.jsp.enable" style="line-height:20px">启用配置</label>
		</span>
    </div>
</div>
</form>
<%
	int nDefaultSFTPport = 22;
	if(nObjectId > 0 && nPort != 0){
		nDefaultSFTPport = nPort;
	}
	int nDefaultFTPport = 21;
	if(nObjectId > 0 && nPort != 0){
		nDefaultFTPport = nPort;
	}
%>
<input type="hidden" id="SFTPport" value="<%=nDefaultSFTPport%>">
<input type="hidden" id="ftpport" value="<%=nDefaultFTPport%>">
</body>
</html>