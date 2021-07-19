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
	/* $('#listId1').boxlist('option', 'dataurl',rootPath+
			'/reg/detail.do'); */
	$("#listId1").boxlist('option', 
		{
			"click": function(e, ui){alert("单击 ：label:"+ui.text+"   值:"+ui.val+"！  ");},
			"dblclick":function(e,ui){alert("双击： label:"+ui.text+"   值:"+ui.val+"！  ");},
		}
	); 
});

/* $("#listId1").boxlist({"option","datarender",function(data ){  
alert("pp");
} 
}); */
function chg(){
if(document.getElementById("sel").value=="txt"){
document.getElementById("txt").style.display="";document.getElementById("btn").style.display="none";}else{document.getElementById("txt").style.display="none";document.getElementById("btn").style.display="";}}
</script>
</head>
<body>

<center>
<table>
<tr>
<div>选择主题库:
<select id="sel" onChange="chg()"><option value="txt">txt</option><option value="btn">btn</option></select>
</div>
</tr>
<tr>
<th><div >选择数据表：</div></th>
<th>
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

<div name='list3' id="listId3" vtype="boxlist" scrollheight="80" multiple=true 
	dataurl="data.json" >
</div>
</th>
</tr></table>
</center>


		

</body>
</html>