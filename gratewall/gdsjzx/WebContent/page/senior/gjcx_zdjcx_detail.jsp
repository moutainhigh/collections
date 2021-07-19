<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>详情</title>
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
		data[i]["cz"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="detail(\''+pripid+'\')">详情  </a><a href="javascript:void(0);" onclick="zhixing(\''+pripid+'\')">执行 </a><a href="javascript:void(0);" onclick="update(\''+pripid+'\')">修改 </a><a href="javascript:void(0);" onclick="del(\''+pripid+'\')">删除 </a></div>';
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
$(function(){
	$('#listId1').boxlist('option', 'dataurl',rootPath+
			'/query/list.do');
	/* $("#listId1").boxlist('option', 
		{
			"click": function(e, ui){alert("单击 ：label:"+ui.text+"   值:"+ui.val+"！  ");},
			"dblclick":function(e,ui){alert("双击： label:"+ui.text+"   值:"+ui.val+"！  ");}
		}
	);  */
});

		function back(){
			history.go(-1);
		}
		
		function query(){
		//alert("pp");
			window.location.href="<%=request.getContextPath()%>/page/senior/gjcx_zxsql_zdycxlb.jsp";
		}
</script>
</head>
<body>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel" titledisplay="false" width="100%" layout="table" 
		showborder="false" layoutconfig="{cols:1, columnwidth: ['50%','*'],border:false}" height="200" title="自定义查询信息">

		<div name='fbsjq' vtype="textfield" label="查询名称" dataurl="[{checked: true,value: '1',text: '省局'},{value: '2',text: '广州市局'},{value: '3',text: '深圳市局'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='zjhm' vtype="textfield" label="执行次数" dataurl="[{checked: true,value: '1',text: '省局'},{value: '2',text: '广州市局'},{value: '3',text: '深圳市局'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
		
		<div name='zjhm1' vtype="datefield" label="创建日期" dataurl="[{checked: true,value: '1',text: '省局'},{value: '2',text: '广州市局'},{value: '3',text: '深圳市局'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='zjhm22' vtype="datefield" label="最后执行日期" dataurl="[{checked: true,value: '1',text: '省局'},{value: '2',text: '广州市局'},{value: '3',text: '深圳市局'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
	</div>
	
	<div vtype="formpanel" id="formpanel1" displayrows="1" name="formpanel1" titledisplay="true" width="100%" layout="table" 
		showborder="false" layoutconfig="{cols:1, columnwidth: ['50%','*'],border:false}" height="300" title="已选择数据情况">

		<div name='fbsjq' vtype="textfield" label="选择表信息" dataurl="[{checked: true,value: '1',text: '省局'},{value: '2',text: '广州市局'},{value: '3',text: '深圳市局'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='zjhm' vtype="textfield" label="连接条件" dataurl="[{checked: true,value: '1',text: '省局'},{value: '2',text: '广州市局'},{value: '3',text: '深圳市局'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
		
		<div name='zjhm1' vtype="textfield" label="查询条件" dataurl="[{checked: true,value: '1',text: '省局'},{value: '2',text: '广州市局'},{value: '3',text: '深圳市局'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='zjhm2' vtype="textfield" label="最后执行日期" dataurl="[{checked: true,value: '1',text: '省局'},{value: '2',text: '广州市局'},{value: '3',text: '深圳市局'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
		
		<div name='zjhm3' vtype="comboxfield" label="股东质押信息的股权类型等于" dataurl="[{checked: true,value: '1',text: '1'},{value: '2',text: '2'},{value: '3',text: '3'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='zjhm4' vtype="datefield" label="并且（股东质押信息的质押登记日期  大于等于 " dataurl="[{checked: true,value: '1',text: '省局'},{value: '2',text: '广州市局'},{value: '3',text: '深圳市局'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='zjhm5' vtype="datefield" label="并且  股东质押信息的质押登记日期  小于等于" dataurl="[{checked: true,value: '1',text: '是'},{value: '2',text: '否'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='zjhm6' vtype="comboxfield" label="并且 股东质押信息 的 出质登记操作状态等于" dataurl="[{checked: true,value: '1',text: '是'},{value: '2',text: '否'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='zjhm7' vtype="comboxfield" label="并且 股东质押信息 的 出质登记操作状态等于" dataurl="[{checked: true,value: '1',text: '是'},{value: '2',text: '否'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
	</div>
		

	<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div name="query_button" vtype="button" text="执行查询" 
				icon="../query/queryssuo.png" onclick="query();"></div>
			<div name="reset_button" vtype="button" text="返回"
				icon="../query/queryssuo.png" click="back();" ></div>
		</div>
</body>
</html>