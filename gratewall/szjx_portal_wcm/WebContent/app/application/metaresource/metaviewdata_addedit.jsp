<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ page errorPage="/app/application/common/error_for_dialog.jsp"%>
<%@ include file="/app/application/common/metaviewdata_addedit_include_import.jsp"%>
<%@ include file="/app/application/common/metaviewdata_addedit_include_data_build.jsp"%>
<%@ include file="/app/application/common/metaviewdata_addedit_include_function.jsp"%>
<%@ page buffer="64kb" %>
<%!int m_nViewId = <TRS_VIEW FIELD="ViewInfoId"/>;%>
<%out.clear();%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
            "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>新建/修改[<TRS_VIEW FIELD="ViewDesc"/>]数据</title>
<script language="javascript">
<!--
	var m_nChannelId = <%=nChannelId%>;
	var m_nObjectId = <%=nObjectId%>;
	var m_nFlowDocId = <%=nFlowDocId%>;
	var m_nViewId = <%=m_nViewId%>;
//-->
</script>
<%@ include file="/app/application/common/metaviewdata_addedit_include_resource.jsp"%>
<script language="javascript" src="metaviewdata_addedit.js" type="text/javascript"></script>
<link href="metaviewdata_addedit.css" rel="stylesheet" type="text/css" />
</head>

<body>
<div class="box" id="data" action="metaviewdata_addedit_dowith.jsp" serviceId="wcm61_metaviewdata" methodName="saveMetaViewData">
	<input type="hidden" name="objectId" id="objectId" value="<%=nObjectId%>" />
	<input type="hidden" name="flowdocId" id="flowdocId" value="<%=nFlowDocId%>" />
	<input type="hidden" name="channelId" id="channelId" value="<%=nChannelId%>" />
	<input type="hidden" name="DocLevel" id="DocLevel" value="" />
${FieldLayout}
	<div class="row" id="appendix" style="display:none;">
		<div class="label">附件管理：</div>
		<div class="value">
<script language="javascript">
<!--
	new com.trs.ui.XDocAppendixes({
		name : 'myDocAppendixes',
		objectId : '<%=nObjectId%>'
	}).render();
//-->
</script>	
		</div>
	</div>
	<div class="row">
		<div class="label">选择模板：</div>
		<div class="value">
<%@ include file="/app/application/common/TemplateSelectHandler.jsp"%>
		</div>
	</div>
<%@ include file="/app/application/common/TopSetHandler.jsp"%>
<%@ include file="/app/application/common/doclevel_set.jsp"%>
</div>
</body>
<script language="javascript">
<!--
	Event.observe(window, 'load', function(event){
		//20120522 by CC 禁止附件管理拆分为3个部分后，在普通视图中，如果其中一部分为禁用，则附件管理操作项隐藏
		var nFileAppendix = '<TRS_Condition Condition="@HIDDENFILEAPPENDIX" Type="INT" REFERENCE="1">1</TRS_Condition>';
		var nImgAppendix = '<TRS_Condition Condition="@HIDDENIMGAPPENDIX" Type="INT" REFERENCE="1">1</TRS_Condition>';
		var nLinkAppendix = '<TRS_Condition Condition="@HIDDENLINKAPPENDIX" Type="INT" REFERENCE="1">1</TRS_Condition>';
		
		if((nFileAppendix==1) || (nImgAppendix==1) || (nLinkAppendix==1)){
			document.getElementById("appendix").style.display="none";
		} else {
			document.getElementById("appendix").style.display="";
		}
	});
//-->
</script>
</html>