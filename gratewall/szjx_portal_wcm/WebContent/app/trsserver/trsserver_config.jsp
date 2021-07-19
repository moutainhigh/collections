<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>

<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.infra.support.config.ConfigServer" %>
<%@include file="../include/public_server.jsp"%>
<%
	if(!loginUser.isAdministrator()){
		throw new WCMException("您无权配置TRSServer服务");
	}
	//接受页面参数
	ConfigServer configServer = ConfigServer.getServer(); 
	String sDBName = configServer.getSysConfigValue("TRSSERVER_DB","");
	if(sDBName.trim().equals("")){
		sDBName = LocaleServer.getString("trsserver_config.jsp.label.choose", "--请选择--");
	}
%>
<HTML xmlns:TRS>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/system.js"></script>
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
<!--AJAX-->
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/data/locale/publishdistribution.js"></script>
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
<script language="JavaScript" src="trsserver_config.js"></script>
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
		margin:10px;
	}
	.ext-safari .divPadding{
		padding-top:1px;
	}
</style>
<script language="javascript">
	window.m_fpCfg = {
		m_arrCommands : [{
			cmd : 'link',
			name : wcm.LANG.PUBLISHDISTRUBUTION_LINK || '连接'
		},{
			cmd : 'save',
			name : wcm.LANG.PUBLISHDISTRUBUTION_SURE || '确定'
		}],
		size : [350, 220]
	};
</script>
<script language="javascript">
<!--
	ValidationHelper.initValidation();
	ValidationHelper.addValidListener(function(){
		FloatPanel.disableCommand('link', false);
	}, "trsServerForm");
	ValidationHelper.addInvalidListener(function(){
		FloatPanel.disableCommand('link', true);
	}, "trsServerForm");
<%
	if(!loginUser.isAdministrator()){
%>
		FloatPanel.disableCommand('link', true);
<%
	}
%>
//-->
</script>
</head>
<body class="body">
<form id="trsServerForm">
<input type="hidden" name="FolderType" id="FolderType" value="103">
<div id="distributionId">
    <div id="forFtp">
        <div class="divPadding">
            <span class="row_title" WCMAnt:param="publishdistribution_add_edit.jsp.address">服务器地址:</span>
            <span class="row_content">
                <input type="text" name="TargetServer" id="TargetServer" value="<%=configServer.getSysConfigValue("TRSSERVER_HOST","127.0.0.1")%>" class="input_class" validation="type:'string',required:'',max_len:'25',desc:'服务器地址',showid:'ValidatorMsg'" validation_desc="服务器地址" WCMAnt:paramattr="validation_desc:trsserver_config.jsp.address"/>
                <span style="color:red">*</span>
            </span>
        </div>
        <div class="divPadding">
            <span class="row_title" WCMAnt:param="publishdistribution_add_edit.jsp.port">服务器端口:</span>
            <span class="row_content">
                <input type="text" name="TargetPort" id="TargetPort" value="<%=configServer.getSysConfigValue("TRSSERVER_PORT","8888")%>" class="input_class" validation="type:'int',required:'true',value_range:'1,20000',showid:'ValidatorMsg'" validation_desc="服务器端口" WCMAnt:paramattr="validation_desc:trsserver_config.jsp.port">
                <span style="color:red">*</span>
            </span>
        </div>
        <div id="userInfo">
            <div class="divPadding">
                <span class="row_title" WCMAnt:param="publishdistribution_add_edit.jsp.username">用户名:</span>
                <span class="row_content">
                    <input type="text" name="LoginUser" id="LoginUser" value="<%=configServer.getSysConfigValue("TRSSERVER_USER","system")%>" class="input_class" validation="type:'string',required:'',max_len:'25',desc:'用户名',showid:'ValidatorMsg'" validation_desc="用户名" WCMAnt:paramattr="validation_desc:trsserver_config.jsp.username"/>
                    <span style="color:red" id="userRed">*</span>
                </span>
            </div>
            <div class="divPadding">
                <span class="row_title" WCMAnt:param="publishdistribution_add_edit.jsp.password">密码：</span>
                <span class="row_content">
                    <input type="password" name="LoginPassword" id="LoginPassword" value="<%=configServer.getSysConfigValue("TRSSERVER_PWD","manager")%>" class="input_class" validation="type:'string',required:'',max_len:'25',desc:'密码',showid:'ValidatorMsg'" validation_desc="密码" WCMAnt:paramattr="validation_desc:trsserver_config.jsp.pwd"/>
                    <span style="color:red" id="passwordRed">*</span>
                </span>
            </div>
			 <div class="divPadding">
                <span class="row_title" WCMAnt:param="publishdistribution_add_edit.jsp.db">数据源:</span>
                    <select name="TRSDatebase" id="TRSDatebase"  class="input_class" style="margin-left:9px;" disabled>
						<option value="-1" WCMAnt:param="publishdistribution_add_edit.jsp.sel"><%=sDBName%></option>
                    </select>
            </div>
        </div>
    </div>
</div>
</form>
</body>
</html>