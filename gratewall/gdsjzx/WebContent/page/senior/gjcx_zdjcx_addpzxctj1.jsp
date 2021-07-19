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
<script src="<%=request.getContextPath()%>/static/script/JAZZ-UI/demos/index/jazz-ui.js" type="text/javascript"></script>
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



function renderdata(event, obj){
	var data = obj.data;
	var a = ""+data["text"]+"***"+data["value"]+""; 
	return a;
}

//加载选择表中的数据
var tabName="";
function loadtable(){
	tabName=$('#checkTableName', parent.document).val();
	var arrayN=tabName.split(",");
		for(var i=1;i<arrayN.length;i++){
			$('div[name="tablename"]').comboxfield('addOption', arrayN[i], arrayN[i]);
	}
}

	  var ljtj="";//逻辑条件
	  var khleft="";//括弧
	  var tablename="";//选择表名
	  var seldata="";//选择的字段
	  var condition="";//连接条件
	  var value="";//值
	  var khright="";//括弧
	  
	//选择表发送获取表字段
	$(function(){
		$('div[name="tablename"]').comboxfield("option", "change", function(event, ui){  
				//alert(ui.text);
				//获取主题下面的表名
		$.ajax({
			url:'http://localhost:8080/gdsjzx/report/year.do',
			async:false,
			data:{
				tableName:ui.text,
			},
			type:"post",
			dataType : 'json',
			success:function(data){
				$('div[name="seldata"]').comboxfield('removeOption', 'value');
				/*
					加载选择表下的数据
				*/
				$('div[name="seldata"]').comboxfield('addOption', 'text', 'value');
					}
				});
			});
			
		$('div[name="ljtj"]').comboxfield("option", "itemselect", function(event, ui){ 
				ljtj=ui.text;
		});
		$('div[name="khleft"]').comboxfield("option", "itemselect", function(event, ui){  
				khleft=ui.text;
		});
		$('div[name="tablename"]').comboxfield("option", "itemselect", function(event, ui){  
				tablename=ui.text;
		});
		$('div[name="seldata"]').comboxfield("option", "itemselect", function(event, ui){  
				seldata=ui.text;
		});
		$('div[name="condition"]').comboxfield("option", "itemselect", function(event, ui){  
				condition=ui.text;
		});
		$('div[name="value"]').comboxfield("option", "itemselect", function(event, ui){  
				value=ui.text;
		});
		$('div[name="khright"]').comboxfield("option", "itemselect", function(event, ui){  
				khright=ui.text;
		});	
	})
		//并且(and)(cccc1text大于)
	var sql="";
	function getActiveAdd(){
		// var ljtj =$('div[name="ljtj"]').comboxfield("getValue");//1--and  ,, 2--or
		//  alert(ljtj);
		//  var test =$('div[name="test"]').comboxfield("getText");//1--and  ,, 2--or
		sql=ljtj+khleft+tablename+seldata+condition+value+khright;
		alert(sql);
		
	}

</script>
</head>
<body>
<div vtype="formpanel"  id="formpanel" displayrows="2" name="formpanel" titledisplay="true" width="100%" layout="table" 
		showborder="false" layoutconfig="{cols:2, columnwidth: ['14%','12%','16%','18%','14%','12%','14%'],border:false}" height="300" title="查询条件">
		<div id="toolbar" name="toolbar" vtype="toolbar" location="top" align="center">
			<div name="query_button" vtype="button" text="增加条件" 
				icon="../query/queryssuo.png" onclick="getActiveAdd()"></div>
		</div>
		
		<div name='ljtj' vtype="comboxfield"  label="条件" dataurl="[{checked: true,value: '1',text: '并且(and)'},{value: '2',text: '或者(or)'}]" labelAlign="center"  labelwidth='60px' width="160"></div>
		<div name='khleft' vtype="comboxfield" label="括弧"  dataurl="[{checked: true,value: '1',text: '('},{value: '2',text: '(('},{value: '3',text: '((('}]" labelAlign="center"  labelwidth='50px' width="150"></div>
		<div name='tablename' vtype="comboxfield" label="数据表" labelAlign="center"  labelwidth='50px' width="210"></div>
		<div name='seldata' vtype="comboxfield" label="数据项"  labelAlign="center" labelwidth='70px' width="230"></div>
		<div name='condition' vtype="comboxfield" label="条件" dataurl="[{checked: true,value: '1',text: '大于'},{value: '2',text: '不等于'},{value: '3',text: '等于'}]" labelAlign="center"  labelwidth='50px' width="150"></div>
		<div name='value' vtype="comboxfield" label="值" dataurl="[{checked: true,value: '1',text: ' '}]" labelAlign="center"  labelwidth='40px' width="140"></div>
		<div name='khright' vtype="comboxfield" label="括弧" dataurl="[{checked: true,value: '1',text: ')'},{value: '2',text: '))'},{value: '3',text: ')))'}]" labelAlign="center"  labelwidth='50px' width="150"></div>
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
		<div id="sql"><p> </p></div>
		<input type="hidden" id="tabSel"/>	
	
</body>
</html>