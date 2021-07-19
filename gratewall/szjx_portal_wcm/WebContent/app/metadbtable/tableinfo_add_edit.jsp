<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page errorPage="../include/error_for_dialog.jsp"%>
<%@include file="../include/public_processor.jsp"%>

<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="com.trs.components.metadata.definition.MetaDBTable" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title WCMAnt:param="tableinfo_add_edit.jsp.title">新建/修改元数据</title>
<link href="../css/add_edit.css" rel="stylesheet" type="text/css" />
<style type="text/css">
    *{
        font-size:12px;
        margin:0px;
        padding:0px;
    }
    .row{
        margin:10px;
        height:18px;
		clear:both;
    }
    .label{
		float:left;
		width:90px;
        line-height:18px;
    }
    .value{
    }
    input{
        width:200px;
        height:18px;
        border:1px solid silver;
    }
</style>
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/metadbtable.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<!--processbar -->
<script src="../../app/js/source/wcmlib/components/ProcessBar.js"></script>
<link rel="stylesheet" type="text/css" href="../../app/js/source/wcmlib/components/processbar.css">
<!--wcm-dialog start-->
<script src="../../app/js/source/wcmlib/Observable.js"></script>
<script src="../../app/js/source/wcmlib/dragdrop/dd.js"></script>
<script src="../../app/js/source/wcmlib/Component.js"></script>
<script src="../../app/js/source/wcmlib/dialog/Dialog.js"></script>
<SCRIPT src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></SCRIPT>
<link href="../../app/css/wcm-widget.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/dialog/resource/dlg.css" rel="stylesheet" type="text/css" />
<link href="../../app/js/source/wcmlib/button/resource/button.css" rel="stylesheet" type="text/css" />
<!--wcm-dialog end-->
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanelAdapter.js"></script>
<link rel="stylesheet" type="text/css" href="../css/wcm-common.css">
<script src="../../app/js/source/wcmlib/com.trs.validator/lang/cn.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/Validator.js"></script>
<link href="../../app/js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />
<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script src="../../app/js/source/wcmlib/core/LockUtil.js"></script>
<script language="javascript">
window.m_fpCfg = {
	m_arrCommands : [{
		cmd : 'saveData',
		name : wcm.LANG.METADBTABLE_27 || '确定'
	}],
	size : [500, 160]	
};
</script>
<script language="javascript" src="tableinfo_add_edit.js" type="text/javascript"></script>
</head>

<body>
<%
	MetaDBTable metadbTable = (MetaDBTable) processor.excute("wcm61_metadbtable", "findDBTableInfoById");	
	String sTableName = CMyString.transDisplay(metadbTable.getName());
	String sAnotherName = CMyString.transDisplay(metadbTable.getAnotherName());
	String sTableDesc = CMyString.transDisplay(metadbTable.getDesc());
%>
<form id="ObjectForm" name="ObjectForm" method="post">	
    <div id="objectContainer">
		<input type="hidden" id="ObjectId" name="ObjectId" value="<%=metadbTable.getId()%>">
		<div class="row">
			<span class="label" WCMAnt:param="tableinfo_add_edit.jsp.metaName">英文名称：</span>
			<span class="value"><input type="text" id="tableName" name="tableName" value="<%=CMyString.filterForHTMLValue(sTableName)%>"></span>
		</div>
		<div class="row">
			<span class="label" WCMAnt:param="tableinfo_add_edit.jsp.metaName1">中文名称：</span>
			<span class="value"><input type="text" id="anotherName" name="anotherName" value="<%=CMyString.filterForHTMLValue(sAnotherName)%>"></span>
		</div>
		<div class="row">
			<span class="label" WCMAnt:param="tableinfo_add_edit.jsp.metaDesc">元数据描述：</span>
			<span class="value"><input type="text" id="tableDesc" name="tableDesc" style="width:188px" value="<%=CMyString.filterForHTMLValue(sTableDesc)%>"></span>
		</div>
	</div>
    <div id="validTip" style="margin-left:10px;"></div>
</form>
</body>
</html>