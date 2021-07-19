<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>主体登记信息</title>
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

function reset() {
	$("#formpanel").formpanel('reset');
}
function queryUrl() {
	//$("#zzjgGrid").gridpanel("hideColumn", "approvedate");
	//	$("#zzjgGrid").gridpanel("showColumn", "modfydate");
	$('#zzjgGrid').gridpanel('option', 'dataurl',rootPath+
			'/query/list.do');
	$('#zzjgGrid').gridpanel('query', [ 'formpanel']);
}
function add() {
	window.location.href="<%=request.getContextPath()%>/page/senior/customQueryAdd.jsp";
}
function detail(priPid,sourceflag,jbxxid) {
	window.location.href="<%=request.getContextPath()%>/page/reg/regDetail.jsp?flag=0&priPid="+priPid+"&jbxxid="+jbxxid+"&sourceflag="+sourceflag;
}
function renderdata(event, obj){
	var data = obj.data;
	for(var i=0;i<data.length;i++){
		var pripid = data[i]["pripid"];
		var sourceflag = data[i]["sourceflag"];
		var jbxxid = data[i]["jbxxid"];
		var a = data[i]["regno"];
		data[i]["regno"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="detail(\''+pripid+'\',\''+sourceflag+'\',\''+jbxxid+'\')">'+a+'</a></div>';
	}
	return data;
}
$(function(){
	$("#zzjgGrid").gridpanel("hideColumn", "pripid");
	$("#zzjgGrid").gridpanel("hideColumn", "jbxxid");
	$("#zzjgGrid").gridpanel("hideColumn", "sourceflag");
});
</script>
</head>
<body>


		
			<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel"
				titledisplay="true" width="100%" layout="table"  showborder="false"
				layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="450"
				title="查询条件">
		
				<div name='zzjgdm' vtype="textfield" label="主体名称" labelAlign="right" labelwidth='100px' width="410"></div>
				<div name='qymc' vtype="textfield" label="法定代表人" labelAlign="right"  labelwidth='100px' width="410"></div>
				<div name='zch' vtype="textfield" label="注册号" labelAlign="right"  labelwidth='100px' width="410"></div>
				<div name='clrq' vtype="textfield" label="注册资金" labelAlign="right"  labelwidth='100px' width="410"></div>
				<div name='zczj' vtype="datefield" label="成立日期" labelAlign="right"  labelwidth='100px' width="410"></div>
				<div name='zxrq' vtype="datefield" label="注销日期" labelAlign="right"  labelwidth='100px' width="410"></div>
				<div name='dxrq' vtype="datefield" label="吊销日期" labelAlign="right"  labelwidth='100px' width="410"></div>
				<div name='djjg' vtype="comboxfield" label="登记机关" dataurl="[{checked: true,value: '1',text: '分公司'},{value: '2',text: '总公司'},{value: '3',text: '集团'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
				<div name='ztfl' vtype="comboxfield" label="主体分类" dataurl="[{checked: true,value: '1',text: '分公司'},{value: '2',text: '总公司'},{value: '3',text: '集团'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
				<div name='gxqx' vtype="comboxfield" label="管辖区县" dataurl="[{checked: true,value: '1',text: '分公司'},{value: '2',text: '总公司'},{value: '3',text: '集团'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
				<div name='ztzt' vtype="comboxfield" label="主体状态" dataurl="combox.json" labelAlign="right"  labelwidth='100px' width="410"></div>
				<div name='hydm1' vtype="comboxfield" label="行业代码" dataurl="combox.json" labelAlign="right"  labelwidth='100px' width="410"></div>
				<div name='zs' vtype="comboxfield" label="住所" dataurl="combox.json" labelAlign="right"  labelwidth='100px' width="410"></div>
				<div name='hydm' vtype="comboxfield" label="字母拼音" dataurl="combox.json" labelAlign="right"  labelwidth='100px' width="410"></div>
				
				<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
					<div name="query_button" vtype="button" text="查询" 
						icon="../query/queryssuo.png" onclick="queryUrl();"></div>
					<div name="reset_button" vtype="button" text="重置"
						icon="../query/queryssuo.png" click="reset();" ></div>
				</div>
			</div>
		
			<div vtype="gridpanel" name="zzjgGrid" height="400" width="100%"  id='zzjgGrid' datarender="renderdata()"
				titledisplay="true" title="主体登记信息"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
				<!-- 表头 -->
				<div vtype="gridcolumn" name="grid_column" width="100%">
					<div>
					
						<div name='pripid' text="pripid" textalign="center" width="1%" isshowcolumn="false"></div>
						<div name='sourceflag' text="sourceflag" textalign="center" width="1%" isshowcolumn="false"></div>
						<div name='jbxxid' text="jbxxid" textalign="center" width="1%" isshowcolumn="false"></div>
						<div name='entname' text="主体名称" textalign="center" width="15%" ></div>
						<div name='regno' text="注册号" textalign="center" width="10%"></div>
						<div name='principal' text="法定代表人" textalign="center" width="7%"></div>
						<div name='corpname' text="主体分类" textalign="center" width="10%"></div>
						<div name='enttype' text="主体状态" textalign="center" width="6%" ></div>
						<div name='certificatesno' text="登记机关" textalign="center"  width="10%"></div>
						
						<div name='orgname' text="批准机构" textalign="center" width="10%"></div>
						<div name='regdata' text="成立日期" textalign="center"  width="10%"></div>
						<div name='modfydate' text="注销日期" textalign="center"  width="10%"></div>
						<div name='approvedate'  text="注册资金" textalign="center"  isshowcolumn="true" width="10%"></div>
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