<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>配置查询结果</title>
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
<center>
<table>
<tr>
<th>
<div style="border:1px solid blue">表信息</div>
<div style="float:right" name='list1' id="listId1" vtype="boxlist" scrollheight="80" multiple=true 
	dataurl="data.json"  
	 >
<!-- data="{'data':[{label:'杭州',value:'8'},{label:'杭州2',value:'9'}]}" -->
</div>

</th>
<th>
<input value="<" type="button" onlcick=""/>
	<br/>
 <input value=">" type="button" onlcick=""/>
 
</th>
<th>
<div style="border:1px solid blue">字段选择</div>
<div name='list2' id="listId2" vtype="boxlist" scrollheight="80" multiple=true 
	dataurl="data.json"  
	 >
</div>
</th>
<th> 
<input value="<" type="button" onlcick=""/>
	<br/>
 <input value=">" type="button" onlcick=""/>
 
 </th>
<th>
<div style="border:1px solid blue">展示信息</div>
<div name='list3' id="listId3" vtype="boxlist" scrollheight="80" multiple=true 
	dataurl="data.json"  
	 >
</div>
</th>
</tr></table>
</center>


		

</body>
</html>