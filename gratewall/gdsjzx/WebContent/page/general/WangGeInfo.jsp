<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>网格信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>

<style type="text/css">

    td{
		text-align: center;
	}
.jazz-pagearea{
	height: 0px;
}

</style>

<script>
$(function(){
	$("#formpanelwgxx").formpanel('option', 'readonly', true);
})
function reset() {
	$("#formpanel").formpanel('reset');
}
function queryUrl() {
	$("#zzjgGrid").gridpanel("hideColumn", "approvedate");
		$("#zzjgGrid").gridpanel("showColumn", "modfydate");
	$('#zzjgGrid').gridpanel('option', 'dataurl',rootPath+
			'/query/list.do');
	$('#zzjgGrid').gridpanel('query', [ 'formpanel']);
}

</script>
</head>
<body>
	<div><a style=" font-weight:bolder;margin-left:10px;">位置:通用查询>企业监管信息>网格信息</a></div>
			<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
				titledisplay="true" width="100%" layout="table"  showborder="false"
				layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="200"
				title="查询条件">
		
				<div name='zzjgdm' vtype="comboxfield" label="管辖分局" dataurl="combox.json" labelAlign="right" labelwidth='100px' width="410" height="50"></div>
				<div name='qymc' vtype="comboxfield" label="管辖部门" dataurl="[{checked: true,value: '1',text: '分公司'},{value: '2',text: '总公司'},{value: '3',text: '集团'}]"  labelAlign="right"  labelwidth='100px' width="410" height="50"></div>
				<div name='zch' vtype="textfield" label="网格名称" labelAlign="right"  labelwidth='100px' width="410" height="50"></div>
				<div name='clrq' vtype="comboxfield" label="网格类型" dataurl="[{checked: true,value: '1',text: '分公司'},{value: '2',text: '总公司'},{value: '3',text: '集团'}]" labelAlign="right"  labelwidth='100px' width="410" height="50"></div>
				<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
					<div name="query_button" vtype="button" text="查询" 
						icon="../query/queryssuo.png" onclick="queryUrl();"></div>
					<div name="reset_button" vtype="button" text="重置"
						icon="../query/queryssuo.png" click="reset();" ></div>
				</div>
			</div>
		
		
		
		
			<div vtype="formpanel" id="formpanelwgxx" displayrows="1" name="formpanelwgxx"
				titledisplay="true" width="100%" layout="table"  showborder="false"
				layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="200"
				title="查询条件">
				
				<div name='zzjgdm' vtype="comboxfield" label="网格编号" dataurl="combox.json" labelAlign="right" labelwidth='100px' width="410" height="30"></div>
				<div name='zch' vtype="textfield" label="网格名称" labelAlign="right"  labelwidth='100px' width="410" height="30"></div>
				<div name='clrq' vtype="comboxfield" label="网格类型" dataurl="combox.json" labelAlign="right"  labelwidth='100px' width="410" height="30"></div>
				<div name='zzjgdm1' vtype="comboxfield" label="网格属性" dataurl="combox.json" labelAlign="right" labelwidth='100px' width="410" height="30"></div>
				<div name='zzjgdm2' vtype="comboxfield" label="管辖分局" dataurl="combox.json" labelAlign="right" labelwidth='100px' width="410" height="30"></div>
				<div name='qymc' vtype="comboxfield" label="管辖部门" dataurl="[{checked: true,value: '1',text: '分公司'},{value: '2',text: '总公司'},{value: '3',text: '集团'}]"  labelAlign="right"  labelwidth='100px' width="410" height="30"></div>
				<div name='qymc2' vtype="comboxfield" label="创建部门" dataurl="[{checked: true,value: '1',text: '分公司'},{value: '2',text: '总公司'},{value: '3',text: '集团'}]"  labelAlign="right"  labelwidth='100px' width="410" height="30"></div>
				<div name='qymc3' vtype="textfield" label="网格描述"   labelAlign="right"  labelwidth='100px' width="410" height="30"></div>
			</div>
			
			<div vtype="gridpanel" name="zzjgGrid" height="400" width="100%"  id='zzjgGrid'
				titledisplay="true" title="网格主题列表"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
				<!-- 表头 -->
				<div vtype="gridcolumn" name="grid_column" width="100%">
					<div>
						<div name='orgno' text="注册号" textalign="center" width="25%" ></div>
						<div name='orgno' text="主体名称" textalign="center" width="25%" ></div>
						<div name='registerno' text="住所" textalign="left" width="25%"></div>
						<div name='principal' text="主体类型" textalign="left" width="25%"></div>
					</div>
				</div>
				<!-- 表格 -->
				<!-- ../../grid/reg3.json -->
				<div vtype="gridtable" name="grid_table"></div>
				<!-- 分页 -->
				<div vtype="paginator" name="grid_paginator" theme="2" pagerows="20"></div>
			</div>


</body>
</html>