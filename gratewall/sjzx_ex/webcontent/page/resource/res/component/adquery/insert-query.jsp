<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%
	DataBus context = (DataBus) request.getAttribute("freeze-databus");
	String has_purv = context.getRecord("record").getValue("has_purv");
	String max_result = context.getRecord("record").getValue("max_result");
	String reg_org = context.getRecord("record").getValue("reg_org");
	// 处理异常情况
	if (has_purv == null){
		has_purv = "0";
	}
	// 针对高级查询做特殊处理，这里的区县代码为空
	reg_org = "";
%>
<freeze:html width="650" height="350">
<head>
<title>查询范围定义</title>
<style type="text/css">
#totalTableDiv .leftTitle{
	background: url(/module/layout/layout-weiqiang/images_new/r_list_l.jpg) no-repeat !important;
}
#totalTableDiv .secTitle{
	background: #006699 !important;
}
#totalTableDiv .rightTitle{
	background: url(/module/layout/layout-weiqiang/images_new/r_list_r.jpg) no-repeat !important;
}
</style>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery1.1.js"></script>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/component/stepHelp.js"></SCRIPT>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/component/dataSelectPlugin.js"></SCRIPT>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/component/connectConditionPluginJoin.js"></SCRIPT>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/component/queryConditionPlugin.js"></SCRIPT>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/component/generateTotalTable.js"></SCRIPT>
<SCRIPT language="javaScript" type="text/javascript" src="<%=request.getContextPath()%>/script/page/page-download-adquery.js"></SCRIPT>
<script type="text/javascript">
	var funcType;
	var turnType='0';
	
	function addSeparateLine(){
	var ent_sort = document.getElementById("source_table");
	var sortArray = ent_sort.options;
	for (var i=0; i < sortArray.length; i++){
		if (sortArray[i].value == "ETLGCZH" || sortArray[i].value == "XTGL" ){
			var optgroup = document.createElement("optgroup");
			optgroup.label = "---------------";
			ent_sort.insertBefore(optgroup, sortArray[i]);
		}
	}
	sortArray = null;
	ent_sort = null;
}
</script>
</head>
<freeze:body onload="addSeparateLine()">
	<freeze:title caption="新增查询"/>
	<form action="<%=request.getContextPath()%>/dw/aic/gjcx/cxtjdy/preview.jsp" method="post" target="pageList_frameX" style="margin:0;padding:0">
		<input type="hidden" id="record:query_sql" name="record:query_sql" />
		<input type="hidden" id="record:query_dsql" name="record:query_dsql" />
		<input type="hidden" id="sqlCondition" name="sqlCondition" />
		<input type="hidden" id="record:columnsEnArray" name="record:columnsEnArray" />
		<input type="hidden" id="record:columnsCnArray" name="record:columnsCnArray" />
		<input type="hidden" id="has_purv" name="has_purv" value="<%=has_purv %>"/>
		<input type="hidden" id="max_result" name="max_result" value="<%=max_result %>" />
		<input type="hidden" id="reg_org" name="reg_org" value="<%=reg_org %>" />
		<input type="hidden" id="displayType" name="displayType" />
		<div id="stepsContainerDiv" style="margin-left: 29.5px;"></div>
		<div id="step1DIV">
			<table width="95%" border="0" align="center" class="frame-body" cellpadding="0" cellspacing="0"> 
			    <tr>
			      <td><table cellspacing="0" cellpadding="0" width="100%"><tr><td class="leftTitle"></td><td class="secTitle">数据表查询条件</td><td class="rightTitle"></td></tr></table></td>
			    </tr>
			    <tr class="framerow">
		          <td style="border:2px solid #006699;padding:0px;"><div id="div1" style="border:0px;height:100%;width:100%;background-color:#DFE8ED;"></div></td>
		        </tr>
		    </table>	
		    <div id="div2" style="margin-top:10px;"></div>
	        <p><center>
	        <table cellspacing="0" cellpadding="0" class="button_table"><tr><td class="btn_left"></td><td>
	        <input type='button' id='toStep2Button' class="menu" value='下一步'/>
	        </td><td class="btn_right"></td></tr></table>
	        <table cellspacing="0" cellpadding="0" class="button_table"><tr><td class="btn_left"></td><td>
	        <input type='button' id='goBack0' class="menu" value=' 返 回 ' onclick="goBack();"/>
	        </td><td class="btn_right"></td></tr></table>
	        </center></p>
        </div>
        
        <div id="step2DIV" style="display:none;"></div>
        <div id="step2ButtonDiv" style="display:none">
        	<p><center>
        	<table cellspacing="0" cellpadding="0" class="button_table"><tr><td class="btn_left"></td><td>
        	<input type="button" id="preStep1Button" class="menu" value="上一步" />
        	</td><td class="btn_right"></td></tr></table>
        	<table cellspacing="0" cellpadding="0" class="button_table"><tr><td class="btn_left"></td><td>
        	<input type="button" id="toStep3Button" class="menu" value="下一步" />
        	</td><td class="btn_right"></td></tr></table>
        	<table cellspacing="0" cellpadding="0" class="button_table"><tr><td class="btn_left"></td><td>
	        <input type="button" id="goBack1" class="menu" value=" 返 回 " onclick="goBack();" />
	        </td><td class="btn_right"></td></tr></table></center></p>
        </div>
        
        
        <div id="step3DIV" style="display:none">
        	<table width="95%" border="0" align="center" class="frame-body" cellpadding="0" cellspacing="0"> 
			    <tr>
			      <td><table cellspacing="0" cellpadding="0" width="100%"><tr><td class="leftTitle"></td><td class="secTitle">数据表查询条件</td><td class="rightTitle"></td></tr></table></td>
			    </tr>
			    <tr class="framerow">
		          <td style="padding:0px;border:2px solid #006699;"><div id="columnsContainerDiv" style="height:100%;width:100%;background-color:#DFE8ED;"></div></td>
		        </tr>
		    </table>	
		</div>
        <div id="step3ButtonDiv" style="display:none">
        	<p><center>
        	<table cellspacing="0" cellpadding="0" class="button_table"><tr><td class="btn_left"></td><td>
        	<input type='button' id='preStep2Button' class="menu" value="上一步"/>
        	</td><td class="btn_right"></td></tr></table>
        	<table cellspacing="0" cellpadding="0" class="button_table"><tr><td class="btn_left"></td><td>
        	<input type='button' id='preViewButton' class="menu" value="下一步"/>
        	</td><td class="btn_right"></td></tr></table>
	        <table cellspacing="0" cellpadding="0" class="button_table"><tr><td class="btn_left"></td><td>
	        <input type='button' id='goBack2' class="menu" value=" 返 回 " onclick="goBack();"/>
	        </td><td class="btn_right"></td></tr></table></center></p>
        </div>
        
        <div id="step4DIV" style="display:none">
        	<div id="totalTableDiv"></div>
        	<IFRAME id="pageList_frameX" name="pageList_frameX" frameBorder=0 width="95%" align="center" height="300"></IFRAME>
        </div>
        <div id="step4ButtonDiv" style="display:none">
	        <p><center>
	        <table cellspacing="0" cellpadding="0" class="button_table"><tr><td class="btn_left"></td><td>
	        <input type='button' id='preStep3Button' class="menu" value="上一步" />
	        </td><td class="btn_right"></td></tr></table>
        	<table cellspacing="0" cellpadding="0" class="button_table"><tr><td class="btn_left"></td><td>
        	<input type='button' id='saveButton' class="menu" value=" 保存为模板 " />
        	</td><td class="btn_right"></td></tr></table>
	        <table cellspacing="0" cellpadding="0" class="button_table"><tr><td class="btn_left"></td><td>
	        <input type='button' id='goBack2' class="menu" value=" 返 回 " onclick="goBack();"/>
	        </td><td class="btn_right"></td></tr></table></center></p>
        </div>
	</form>
</freeze:body>
<script type="text/javascript">
	window.rootPath = "<%=request.getContextPath()%>";
	var doUpdate = null;
	var ajaxXml = null;
</script>
</freeze:html>
