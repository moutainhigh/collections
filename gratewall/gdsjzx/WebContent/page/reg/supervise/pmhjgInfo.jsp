<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>拍卖后备案请求核准结果信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>

<script src="<%=request.getContextPath()%>/static/js/sczt/sczt.js" type="text/javascript"></script>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/static/css/reg/reg.css"></link>

<script type="text/javascript">
function rowclick(event,data){
	
}
</script>
				 
</head>
<body >
<div vtype="gridpanel" name="jbxxGrid" height="230" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()" selecttype="1"	titledisplay="true" title="守重信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="98%">
		<div> 
				<div name='id' text="id" visible="false" textalign="center"  width="10%"></div>
				<div name='requestid' text="对应的拍后备案请求的id" visible="false" textalign="center"  width="10%"></div>
				<div name='sourceflag' text="SOURCEFLAG" visible="false" textalign="center"  width="10%"></div>
			<div name='attn' text="企业经办人" visible="false" textalign="center"  width="20%"></div>
				<div name='consultingnumber' text="咨询与办理查询电话" visible="false" textalign="center"  width="10%"></div>
			<div name='filenumber' text="文件字号" visible="false" textalign="center"  width="17%"></div>
			<div name='accper' text="受理人员" visible="false" textalign="center"  width="20%"></div>
			<div name='sector' text="受理机关" visible="false" textalign="center"  width="17%"></div>
				<div name='totalfile' text="委托人的数量" visible="false" textalign="center"  width="10%"></div>
			<div name='type' text="结果类型"" visible="false" textalign="center"  width="10%"></div>
			<div name='date' text="备案结束日期" visible="false" textalign="center"  width="10%"></div>
				<div name='result' text="驳回原因" visible="false" textalign="center"  width="10%"></div>
				<div name='timestamp' text="TIMESTAMP" visible="false" textalign="center"  width="10%"></div>
		</div>
	</div>
	<div vtype="gridtable" name="grid_table" rowselect="rowclick()"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
</div>
<div id="formpanel" class="formpanel_table1" style="height:250px;overflow-y: auto;">
	<div class="font_title">详细信息</div>
	<table id="datashow" class="font_table" >
		<tr><th class="class_td1">企业经办人:</th><th id="row4" class="class_td2"></th>
			<th class="class_td4">咨询与办理查询电话:</th><th id="row5" class="class_td5"></th></tr>
		<tr><th class="class_td1">文件字号:</th><th id="row6" class="class_td2"></th>
			<th class="class_td4">受理人员:</th><th id="row7" class="class_td5"></th></tr>
		<tr><th class="class_td1">受理机关:</th><th id="row8" class="class_td2"></th>
			<th class="class_td4">委托人的数量:</th><th id="row9" class="class_td5"></th></tr>
		<tr><th class="class_td1">结果类型:</th><th id="row10" class="class_td2"></th>
			<th class="class_td4">备案结束日期:</th><th id="row11" class="class_td5"></th></tr>
		<tr><th class="class_td1">驳回原因:</th><th id="row12" class="class_td2" colspan="3"></th>
			</tr>
	</table>
</div>
</body>
</html>