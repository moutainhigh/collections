<%@page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.trs.components.metadata.definition.MetaView" %>
<%@ page import="com.trs.components.metadata.definition.MetaDBTables" %>
<%@ page import="com.trs.components.metadata.definition.MetaDBTable" %>
<%@ page import="com.trs.components.metadata.definition.MetaViewTemplateConfig"%>
<%@ page import="com.trs.infra.util.CMyString" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.Properties" %>

<%@include file="../include/public_processor.jsp"%>
<% 
try{

	MetaView oMetaView = (MetaView) processor.excute("wcm61_MetaView", "findViewById");

	String sSiteName = CMyString.transDisplay(oMetaView.getName());
	String sViewDesc = CMyString.transDisplay(oMetaView.getDesc());
	String sTemplatePath = CMyString.transDisplay(oMetaView.getTemplatePath());
	String sMainTableName = CMyString.transDisplay(oMetaView.getMainTableName());

    MetaDBTables oMetaDBTables = (MetaDBTables) processor.excute("MetaDataDef", "queryDBTableInfos");
	//获取视图可配置的模板类型
	Properties templateConfigProperties = MetaViewTemplateConfig.getConfigProperties();
	out.clear();%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<TITLE WCMAnt:param="viewinfo_add_edit_step1.jsp.title">新建/修改视图</TITLE>
<link href="add_edit.css" rel="stylesheet" type="text/css" />
<style type="text/css">
    *{
        margin:0px;
        padding:0px;
        font-size:12px;
    }
    body{
        padding-left:30px;
    }
    .row{
		clear:both;
        margin:10px;
		height:18px;
		line-height:18px;
    }
    .label{
		float:left;
        width:85px;
    }
    .value{
    }
    input{
        width:200px;
        height:18px;
        border:1px solid #7F9DB9;
    }
	.ext-ie .disabledCls input{
		border:1px solid #C9C7BA!important;
		border-right:0px;
	}
	.ext-gecko .disabledCls{
		margin-top:30px;
	}
/*可输入下拉*/
.XCombox *{
	font-size:12px;
}
.ext-strict .XCombox{
	position:relative;
}
.ext-strict .XCombox input{
	position:absolute;
	top:0px;
	left:0px;
	z-index:2;
	width: 182px; 
	height:18px;
	border:1px solid #7F9DB9;
	border-right:0px;
}
.ext-strict .XCombox select{
	position:absolute;
	top:0px;
	left:0px;
	z-index:1;
	width:202px;
	height:20px;
	border:1px solid #7F9DB9;
}
.ext-ie6 .XCombox input{
	width: 182px; 
	height:21px;
	border:1px solid #7F9DB9;
	border-right:0px;
}
.ext-ie6 .XCombox select{
	width:200px;
	height:22px;
	margin-left:-182px;
}
.ext-ie6 .XCombox span{
	width:18px;
}
.messageClassKey{
    white-space:normal !important;
}
</style>
<script src="../../app/js/runtime/myext-debug.js"></script>
<script src="../../app/js/source/wcmlib/core/MsgCenter.js"></script>
<script src="../../app/js/data/locale/metaview.js"></script>
<script src="../../app/js/source/wcmlib/WCMConstants.js"></script>
<script src="../../app/js/source/wcmlib/core/CMSObj.js"></script>
<script src="../../app/js/source/wcmlib/core/AuthServer.js"></script>
<script src="../../app/js/source/wcmlib/components/ProcessBar.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.floatpanel/FloatPanelAdapter.js"></script>
<script src="../../app/js/source/wcmlib/core/LockUtil.js"></script>
<link rel="stylesheet" type="text/css" href="../../app/css/wcm-common.css">


<script src="../../app/js/source/wcmlib/com.trs.validator/lang/cn.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/ValidatorConfig.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/SystemValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/MoreCustomValidator.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.validator/Validator.js"></script>
<link href="../../app/js/source/wcmlib/com.trs.validator/css/validator.css" rel="stylesheet" type="text/css" />

<script src="../../app/js/data/locale/ajax.js"></script>
<script src="../../app/js/source/wcmlib/com.trs.web2frame/AjaxRequest.js"></script>
<script src="../../app/js/source/wcmlib/dialog/Dialog.js"></script>
<script src="../../app/js/source/wcmlib/dialog/DialogAdapter.js"></script>

<script language="javascript" src="viewinfo_add_edit_step1.js" type="text/javascript"></script>
</head>

<body>
<form id="ObjectForm" name="ObjectForm" method="post">
    <div class="row">
        <span class="label" WCMAnt:param="viewinfo_add_edit_step1.jsp.viewDesc">中文名称：</span>
        <span class="value"><input type="text" id="viewDesc" name="viewDesc" value="<%=sViewDesc%>"></span>
    </div>
    <div class="row">
        <span class="label" WCMAnt:param="viewinfo_add_edit_step1.jsp.mainTableName">英文名称：</span>
        <span class="value">
			<span class="XCombox disabledCls">
				<input type="text" id="tableName" name="tableName" readonly="readonly" value="<%=CMyString.filterForHTMLValue(sMainTableName)%>" /><span>
					<select name="tableNameSel" id="tableNameSel" ignore="true" disabled='disabled'>
						<option value="" _id="0" WCMAnt:param="viewinfo_add_edit_step1.jsp.newTableName">--输入新表名--</option> 
						<%
							for (int i = 0, length = oMetaDBTables.size(); i < length; i++) {
								MetaDBTable oMetaDBTable = (MetaDBTable) oMetaDBTables.getAt(i);
								if (oMetaDBTable == null)
									continue;
								String optionContent = CMyString.transDisplay(oMetaDBTable.getAnotherName()+ "[" + oMetaDBTable.getName() + "]");
								boolean bTip = false;
								String title = "";
								if(optionContent.getBytes().length > 24){
									bTip = true;
								}
								if(bTip){
									title="title='" + optionContent + "'";
								}
								
						%>
							<option <%=title%> style="overflow:visible" value="<%=CMyString.filterForHTMLValue(oMetaDBTable.getName())%>" _id="<%=oMetaDBTable.getId()%>"><%=optionContent%></option>
						<%
							}
						%>
					</select>
				</span>
			</span>
        </span><span id="requireContainer" class="requireContainer"></span>
	</div>
	<div class="row">
        <span class="label" WCMAnt:param="viewinfo_add_edit_step1.jsp.collect_edit_page">采编页面：</span>
        <span class="value">
			<select name="TemplatePath" id="TemplatePath">
				<option value="" WCMAnt:param="viewinfo_add_edit_step1.jsp.sys_default_collect_edit_page">系统默认采编页面</option>
				<%
					if(templateConfigProperties.size() > 0){
						String sSelected = "";
						Enumeration en = templateConfigProperties.propertyNames();
						while (en.hasMoreElements()) {
							String skey = (String) en.nextElement();
							String key = new String(skey.getBytes("ISO8859-1"), "GBK");
							String sValue = templateConfigProperties.getProperty(skey);
							sValue = new String(sValue.getBytes("ISO8859-1"),"GBK");
							sSelected = (sTemplatePath.equalsIgnoreCase(sValue)) ? "selected" : "";
				%>
				<option value="<%=CMyString.filterForHTMLValue(sValue)%>" <%=sSelected%>><%=key%></option>
				<%
						}
					}		
				%>
			</select>
    </div>
    <div id="validTip" style="margin-left:10px;width:350px;word-wrap:break-word;"></div>    
</form>
</body>
</html>
<%
}catch(Exception e) {
	e.printStackTrace();
}	
%>