<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>自定义查询</title>
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
			'/query/list2.do');
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
/* 	$('#listId1').boxlist('option', 'dataurl',rootPath+
			'/query/lis1t.do'); */
	$("#listId1").boxlist('option', 
		{
			"click": function(e, ui){alert("单击 ：label:"+ui.text+"   值:"+ui.val+"！  ");},
			"dblclick":function(e,ui){alert("双击： label:"+ui.text+"   值:"+ui.val+"！  ");}
		}
	); 
});



</script>
</head>
<body>
<div vtype="formpanel" id="formpanel" displayrows="2" name="formpanel" titledisplay="true" width="100%" layout="table" 
		showborder="false" layoutconfig="{cols:2, columnwidth: ['10%','10%','12%','12%','12%','12%','12%','10%','10%'],border:false}" height="300" title="查询条件">
		
		<div name='fbsjq1' vtype="comboxfield" label="条件" dataurl="[{checked: true,value: '1',text: '并且(and)'},{value: '2',text: '或者(or)'}]" labelAlign="center"  labelwidth='30px' width="110"></div>
		<div name='zjhm2' vtype="comboxfield" label="括弧" dataurl="[{checked: true,value: '1',text: '('},{value: '2',text: '(('},{value: '3',text: '((('}]" labelAlign="center"  labelwidth='30px' width="110"></div>
		<div name='fbsjq' vtype="comboxfield" label="数据表" dataurl="[{checked: true,value: '1',text: '合同争议建议调节信息'}]" labelAlign="center"  labelwidth='50px' width="120"></div>
		<div name='zjhm3' vtype="comboxfield" label="表字段" dataurl="[{checked: true,value: '1',text: '合同争议建议调节信息'},{value: '2',text: '申请人姓名'},{value: '3',text: '案由'}]" labelAlign="center"  labelwidth='50px' width="120"></div>
		<div name='fbsjq2' vtype="comboxfield" label="比较关系" dataurl="[{checked: true,value: '1',text: '大于'},{value: '2',text: '不等于'},{value: '3',text: '等于'}]" labelAlign="center"  labelwidth='60px' width="160"></div>
		<div name='zjhm' vtype="comboxfield" label="对比表" dataurl="[{checked: true,value: '1',text: ' '}]" labelAlign="center"  labelwidth='60px' width="160"></div>
		<div name='zjhm21' vtype="comboxfield" label="表字段" dataurl="[{checked: true,value: '1',text: ' '}]" labelAlign="center"  labelwidth='50px' width="120"></div>
		<div name='zjhm1' vtype="comboxfield" label="括弧" dataurl="[{checked: true,value: '1',text: ')'},{value: '2',text: '))'},{value: '3',text: ')))'}]" labelAlign="center"  labelwidth='30px' width="110"></div>
		<div name='zjhm311' vtype="comboxfield" label="操作" dataurl="[{checked: true,value: '1',text: ' '}]" labelAlign="center"  labelwidth='30px' width="110"></div>
		<div id="toolbar" name="toolbar" vtype="toolbar" location="top" align="center">
			<div name="query_button" vtype="button" text="添加" 
				icon="../query/queryssuo.png" onclick="getActiveTabIndex();"></div>
				<div name="query_button" vtype="button" text="添加" 
				icon="../query/queryssuo.png" onclick="getActiveTabIndex();"></div>
			
		</div>
	</div>
<!-- 
<div style="float: left;width:50%" >
<div vtype="formpanel" id="formpanel1" displayrows="1" name="formpanel1" titledisplay="true" width="100%" layout="table" 
		showborder="false" layoutconfig="{cols:1, columnwidth: ['100%','*'],border:true}" height="300" title="查询条件">
		<div id="toolbar" name="toolbar" vtype="toolbar" location="top" align="center">
			<div name="query_button" vtype="button" text="增加条件" 
				icon="../query/queryssuo.png" onclick="getActiveTabIndex();"></div>
		</div>
		<div name='fbsjq1' vtype="comboxfield" label="逻辑条件" dataurl="[{checked: true,value: '1',text: '省局'},{value: '2',text: '广州市局'},{value: '3',text: '深圳市局'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
	</div>
</div> -->
</body>
</html>