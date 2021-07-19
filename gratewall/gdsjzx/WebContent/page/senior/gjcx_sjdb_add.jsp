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
			"click": function(e, ui){
			alert("单击 ：label:"+ui.text+"   值:"+ui.val+"！  ");
	/* 	$($.ajax({
			url : '/gdsjzx/reg/list.do',
			async: true,
			data : {
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(datajson) {
			
						} 
					})) */
			},
			"dblclick":function(e,ui){alert("双击： label:"+ui.text+"   值:"+ui.val+"！  ");}
		}
	); 
	
	})

function test(){
/* 	var drdbwj={
				tabid: 'drdbwj',	
		 		tabtitle: '导入对比文件',	
		 		tabindex: '1',	
		 		taburl: 'gjcx_sjdb_adddrdbwj.jsp'
	}; */
	var xzcybdsjb = {
		 		tabid: 'xzcybdsjb',	
		 		tabtitle: '选择参与比对数据表',	
		 		tabindex: '2',	
		 		taburl: 'gjcx_sjdb_addxzcybdsjb.jsp'
		 };
	var xzdcsjx = {
		 		tabid: 'xzdcsjx',	
		 		tabtitle: '选择导出数据项',	
		 		tabindex: '3',	
		 		taburl: 'gjcx_sjdb_addxzdcsjx.jsp'
		 };	
	var qdbdtj = {
		 		tabid: 'qdbdtj',	
		 		tabtitle: '确定比对条件',	
		 		tabindex: '4',	
		 		taburl: 'gjcx_sjdb_addqdbdtj.jsp'
		 };
	
	var xzyl = {
		 		tabid: 'xzyl',	
		 		tabtitle: '下载预览',	
		 		tabindex: '5',	
		 		taburl: 'gjcx_sjdb_addxzyl.jsp'
		 }; 
	//$("#tab_name").tabpanel("addTab",drdbwj);
	$("#tab_name").tabpanel("addTab",xzcybdsjb);
	$("#tab_name").tabpanel("addTab",xzdcsjx);
	$("#tab_name").tabpanel("addTab",qdbdtj);
	$("#tab_name").tabpanel("addTab",xzyl);
}
$(function(){
	test();
})
function getActiveTabIndexDown(){
		//可以默认选择
		//$('#tab_name').tabpanel('select', 2);
		var i = $('#tab_name').tabpanel('getActiveIndex');//active 得不到
		//alert(i);
	
		if(i<5){
			i=i+1;
		$('#tab_name').tabpanel('select',i);
	}else{
		//终点tab
		i=5;
		$('#tab_name').tabpanel('select', i);
		}
	}
	
function getActiveTabIndexUp(){
		//可以默认选择
		//$('#tab_name').tabpanel('select', 2);
		var i = $('#tab_name').tabpanel('getActiveIndex');//active 得不到
	
		if(0<=i){
			i=i-1;
		$('#tab_name').tabpanel('select',i);
	}else{
		//终点tab
		i=0;
		$('#tab_name').tabpanel('select', i);
		}
	}
	
</script>


</head>
<body>
	<div>位置:高级查询>自定义查询>新增</div>

<div id="demo">
		<div name="tab_name" vtype="tabpanel" overflowtype="2" tabtitlewidth="130"  width="100%" height="300" 
		layout="fit"  orientation="top" id="tab_name" >     
		    <ul>    
		        <li >
		        <a href="#tab1">导入对比文件</a>
		        </li>    
		    </ul>    
		    <div>    
		        <div id="tab1">   
		        <div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel" titledisplay="true" width="100%" layout="table" 
		showborder="false" layoutconfig="{cols:1, columnwidth: ['100%'],border:false}" height="270" title="查询条件" >

		<div name='fbsjq' vtype="comboxfield" label="上传文件" dataurl="[{checked: true,value: '1',text: '省局'},{value: '2',text: '广州市局'},{value: '3',text: '深圳市局'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='zjhm' vtype="textfield" label="自定义临时表名" dataurl="[{checked: true,value: '1',text: '省局'},{value: '2',text: '广州市局'},{value: '3',text: '深圳市局'}]" labelAlign="right"   width="410"></div>
		<div name='zjhm2' vtype="textfield" label="备注" dataurl="[{checked: true,value: '1',text: '省局'},{value: '2',text: '广州市局'},{value: '3',text: '深圳市局'}]" labelAlign="right"   width="410"></div>
		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
		
	</div>
		        </div> 
		    </div>    
		</div>
					   
	</div>
	<div id="toolbar1" name="toolbar1" vtype="toolbar" location="bottom" align="center">
			<div name="query_button" vtype="button" text="下一步" 
				icon="../query/queryssuo.png" onclick="getActiveTabIndexDown();"></div>
			<div name="query_button1" vtype="button" text="上一步" 
				icon="../query/queryssuo.png" onclick="getActiveTabIndexUp();"></div>
		</div>

</body>
</html>