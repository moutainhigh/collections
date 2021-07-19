<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>修改指标</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
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
	$("#zzjgGrid").gridpanel("hideColumn", "approvedate");
		$("#zzjgGrid").gridpanel("showColumn", "modfydate");
	$('#zzjgGrid').gridpanel('option', 'dataurl',rootPath+
			'/query/list.do');
	$('#zzjgGrid').gridpanel('query', [ 'formpanel']);
}

function rowclick(event,data){
	window.location.href="<%=request.getContextPath()%>/page/general/query-panel-right.jsp?priPid="+data.pripid;
}


function renderdata(event, obj){
	var data = obj.data;
	for(var i=0;i<data.length;i++){
		var pripid = data[i]["pripid"];
		data[i]["cz"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="update(\''+pripid+'\')">修改  </a><a href="javascript:void(0);" onclick="update(\''+pripid+'\')">删除  </a><a href="javascript:void(0);" onclick="update(\''+pripid+'\')">启用/停用</a></div>';
	}
	return data;
}

function detail(pripid){
	winEdit = jazz.widget({
 	     vtype: 'window',
	   	     frameurl: './columnDetail.jsp?priPid='+pripid+'&update=true',
	  			name: 'win',
	  	    	title: '详细',
	  	    	titlealign: 'center',
	  	    	titledisplay: true,
	  	        width: 900,
	  	        height: 550,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true
	   		});
}

</script>
</head>
<body>
	<div>位置:历史报表>指标管理</div>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel" titledisplay="true" width="100%" layout="table" 
		showborder="false" layoutconfig="{cols:2, columnwidth: ['50%','*'],border:false}" height="500" title="新增指标">

		<div name='zjhm' vtype="textfield" label="指标代码" dataurl="[{checked: true,value: '1',text: '省局'},{value: '2',text: '广州市局'},{value: '3',text: '深圳市局'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='fbsjq' vtype="textfield" label="指标名称" dataurl="[{checked: true,value: '1',text: '省局'},{value: '2',text: '广州市局'},{value: '3',text: '深圳市局'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='ggmc' vtype="textfield" label="单位类别" dataurl="[{checked: true,value: '1',text: '省局'},{value: '2',text: '广州市局'},{value: '3',text: '深圳市局'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='fbmt' vtype="textfield" label="计量单位" dataurl="[{checked: true,value: '1',text: '省局'},{value: '2',text: '广州市局'},{value: '3',text: '深圳市局'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name="radiofield_name" vtype="radiofield" 
		    label="年度指标" labelalign="right" labelwidth='100px' width="410"
			dataurl="[{checked: true,value: '1',text: '男'},{value: '2',text: '女'}]"></div>

		<div name='fbmt2' vtype="radiofield" label="季度指标"  labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='fbmt3' vtype="radiofield" label="月度指标"  labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='fbmt4' vtype="radiofield" label="是否启用"  labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='fbsjq' vtype="textfield" label="指标简称" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='fbsjq12' vtype="textfield" label="英文简称" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='fbsjq2' vtype="textfield" label="英文全称" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='fbsjq3' vtype="textfield" label="中文描述" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='fbsjq4' vtype="textfield" label="英文描述" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div name="query_button" vtype="button" text="保存" 
				icon="../query/queryssuo.png" onclick="queryUrl();"></div>
			<div name="reset_button" vtype="button" text="关闭"
				icon="../query/queryssuo.png" click="reset();" ></div>
		</div>
	</div>

</body>
</html>