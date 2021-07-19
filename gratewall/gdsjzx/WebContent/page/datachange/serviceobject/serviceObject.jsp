<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>服务对象管理</title>
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
var area;
$(function(){
	queryUrl();
});
function reset() {
	$("#formpanel").formpanel('reset');
}
function queryUrl() {
	var param=$('#BelongArea').val();
	$('#zzjgGrid').gridpanel('option', 'dataurl',rootPath+'/dataservice/serviceObjectList.do?area="'+param+'"');
	$('#zzjgGrid').gridpanel('query', [ 'formpanel']);
}
function renderdata(event, obj){
	var data = obj.data;
	for(var i=0;i<data.length;i++){
		var fwnrid = data[i]["fwdxjbid"];
		var state = data[i]["state"];
		var controlobjectstate = data[i]["controlobjectstate"];
		var serviceobjectname = data[i]["serviceobjectname"];
		
		if(state=="0"){
			data[i]["state"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="run(\''+fwnrid+'\',\''+1+'\')">启用</a></div>';
		}else{
			data[i]["state"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="run(\''+fwnrid+'\',\''+0+'\')">停用</a></div>';
		}
		
		if(controlobjectstate=="1"){
			data[i]["controlobjectstate"] = "不可控";
		}else{
			data[i]["controlobjectstate"] = "可控";
		}
		data[i]["custom"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="update(\''+fwnrid+'\',\''+state+'\')">修改</a> <a href="javascript:void(0);" onclick="license(\''+fwnrid+'\')">授权</a></div>';
		data[i]["serviceobjectname"] = '<div class="jazz-grid-cell-inner" style="text-align: left"><a href="javascript:void(0);" onclick="detailsfw(\''+fwnrid+'\')">'+data[i]["serviceobjectname"]+'</a>';
	}
	return data;
}

function detailsfw(fwdxjbid){
	winEdit = jazz.widget({
	     vtype: 'window',
	     frameurl: './serviceObjectDetailsfw.jsp?fwdxjbid='+fwdxjbid,
	  			name: 'win',
	  	    	titlealign: 'left',
	  	    	title:'对象提供服务信息',
	  	    	titledisplay: true,
	  	        width: 900,
	  	        height: 550,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true
	   		}); 
}


function notrun(fwdxjbid,state){
	if(state=='1'){
		jazz.info("该记录已经处于停用状态!");
		return;
	}
	jazz.confirm("是否停用？", function(){
		var params = {
				url : rootPath+'/dataservice/runServiceObject.do?fwdxjbid='+fwdxjbid+'&state=1',
				components: ['zzjgGrid'],
				callback : function(data, r, res) { 
					if (res.getAttr("back") == 'success'){
						queryUrl();
						jazz.info("停用成功!");
					}else{
						jazz.info("停用失败!");
					}
				}
		}
		$.DataAdapter.submit(params);
	}, function(){});
}
function run(fwdxjbid,state){
	var s=state=='1'?"停用":"启用";
	jazz.confirm("是否"+s+"？", function(){
		var params = {
				url : rootPath+'/dataservice/runServiceObject.do?fwdxjbid='+fwdxjbid+'&state='+state,
				components: ['zzjgGrid'],
				callback : function(data, r, res) { 
					if (res.getAttr("back") == 'success'){
						queryUrl();
						jazz.info(s+"成功!");
					}else{
						jazz.info(s+"失败!");
					}
				}
		}
		$.DataAdapter.submit(params);
	}, function(){});
}
function license(fwdxjbid){
	winEdit = jazz.widget({
	     vtype: 'window',
	     frameurl: './serviceObjectLicense.jsp?update=false&fwdxjbid='+fwdxjbid,
	  			name: 'win',
	  	    	title: '服务对象授权',
	  	    	titlealign: 'left',
	  	    	titledisplay: true,
	  	        width: 900,
	  	        height: 550,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true
	   		}); 
}
function add(){
	winEdit = jazz.widget({
	     vtype: 'window',
	     frameurl: './serviceObjectEdit.jsp?update=false',
	  			name: 'win',
	  	    	title: '新增服务对象',
	  	    	titlealign: 'left',
	  	    	titledisplay: true,
	  	        width: 900,
	  	        height: 550,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true
	   		}); 
}

function update(fwdxjbid,state){
	if(state=="0"){
		jazz.info("该记录已经处于启用状态，不能修改!");
		return;
	}
	winEdit = jazz.widget({
  	     vtype: 'window',
	   	     frameurl: './serviceObjectEdit.jsp?fwdxjbid='+fwdxjbid+'&update=true',
	  			name: 'win',
	  	    	title: '修改服务对象',
	  	    	titlealign: 'left',
	  	    	titledisplay: true,
	  	        width: 900,
	  	        height: 550,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true
	   		});
}
//T_DM_XZQHDM行政区划代码
$(function(){
	area=$('#BelongArea').val();
	if(area==null){
		 $.ajax({
		url:rootPath+'/dataservice/iniArea.do',
		async:false,
		type:"post",
		dataType : 'json',
		success:function(data){
		//$('#listId2 > ul li').remove();
			for(var t=0;t<data.length;t++){
				var con="<option  value="+data[t].code+">"+data[t].value+"</option>";
				$(con).appendTo($('#BelongArea'));
			}
			}
		});	 	
	}
});
//根据地区选择下一级
function changeArea(){
    $.ajax({
		url:rootPath+'/dataservice/iniArea.do',
		async:false,
		type:"post",
		dataType : 'json',
		success:function(data){
		//$('#listId2 > ul li').remove();
			for(var t=0;t<data.length;t++){
				var con="<option  value="+data[t].code+">"+data[t].value+"</option>";
				$(con).appendTo($('#BelongArea'));
			}
			}
		});	 	
}
</script>
</head>
<body>
<div>位置：数据服务>服务对象管理</div>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel" titledisplay="true" width="100%" layout="table" 
		showborder="false" layoutconfig="{cols:2, columnwidth: ['50%','*'],border:false}" height="250" title="查询条件">
		
		<div name='serviceobjectname' vtype="textfield" label="服务对象名称" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='serviceobjectip' vtype="textfield" label="服务对象IP" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='serviceobjectregorg' vtype="comboxfield" label="所属地市" 
		              dataurl='[{"value":"440000","text":"广东省"},
		              {"value":"440100","text":"广州市"},{"value":"440300","text":"深圳市"},
		              {"value":"440400","text":"珠海市"},{"value":"440200","text":"韶关市"},
		              {"value":"440500","text":"汕头市"},{"value":"440600","text":"佛山市"},
		              {"value":"440700","text":"江门市"},{"value":"440800","text":"湛江市"},
		              {"value":"440900","text":"茂名市"},{"value":"441200","text":"肇庆市"},
		              {"value":"441300","text":"惠州市"},{"value":"441400","text":"梅州市"},
		              {"value":"441500","text":"汕尾市"},
		              {"value":"441600","text":"河源市"},{"value":"441700","text":"阳江市"},
		              {"value":"441800","text":"清远市"},{"value":"441900","text":"东莞市"},
		              {"value":"442000","text":"中山市"},{"value":"445100","text":"潮州市"},
		              {"value":"445200","text":"揭阳市"},{"value":"445300","text":"云浮市"}]' 
		              labelAlign="right" labelwidth='100px' width="410">
	    </div>
		<div name='state' vtype="comboxfield" label="状态" dataurl="[{value: '0',text: '启用'},{value: '1',text: '停用'}]" labelAlign="right" labelwidth='100px' width="410"></div>
		<div name='controlobjectstate' vtype="radiofield" dataurl="[{value: '0',text: '可控'},{value: '1',text: '不可控'}]" label="可控状态" labelAlign="right"  width="420" labelwidth="120"></div>
		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
		<div name="query_button" vtype="button" text="查询" 	icon="../query/queryssuo.png" onclick="queryUrl();"></div>
		<div name="reset_button" vtype="button" text="重置" icon="../query/queryssuo.png" click="reset();" ></div>
		<div name='createtime' style="display:none" vtype="datefield" label="创建时间" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='serviceobjectport' style="display:none" vtype="textfield" label="服务对象端口" labelAlign="right"  labelwidth='100px' width="410"></div>
	</div>
	</div><!-- defaultvalue="3"  -->

	<div vtype="gridpanel" name="zzjgGrid" height="400" width="100%"  id='zzjgGrid' datarender="renderdata()"
		 titledisplay="true" title="查询列表"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
		<div name="toolbar" vtype="toolbar">
			<div name="btn1" align="right" vtype="button" text="新增" click="add()"></div>
    	</div>
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>
				<div name='fwdxjbid' key="true" visible="false" width="0%"></div>
				<div name='serviceobjectname' text="服务对象名称" textalign="center"  width="30%"></div>
				<div name='serviceobjectip' text="服务对象IP" textalign="center"  width="9%"></div>
				<div name='serviceobjectport' text="对象端口" textalign="center"  width="8%"></div>
		<!--    <div name='createperson' text="创建人" textalign="center"  width="10%"></div>
				<div name='createtime' text="创建时间" textalign="center"  width="12%"></div> -->
				<!-- <div name='executetype' text="执行类型" textalign="center"  width="6%"></div> -->
				<div name='controlobjectstate' text="可控状态" textalign="center"  width="8%"></div>
				<div name='state' text="状态" textalign="center"  width="8%"></div>
				<div name='reason' text="描述" textalign="center"  width="23%"></div>
				<div name='custom' text="操作" textalign="center"  width="12%"></div>
			</div>
		</div>
		<div vtype="gridtable" name="grid_table" ></div>
		<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
	</div>
</body>
</html>