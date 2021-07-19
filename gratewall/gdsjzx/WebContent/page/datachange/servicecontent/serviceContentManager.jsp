<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>服务内容管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
	queryUrl();
});
function reset() {
	$("#formpanel").formpanel('reset');
}
function queryUrl() {
	$('#zzjgGrid').gridpanel('option', 'dataurl',rootPath+'/dataservice/serviceContentList.do');
	$('#zzjgGrid').gridpanel('query', [ 'formpanel'],function(data){
		//console.info(data);
	});
}
function renderdata(event, obj){
	//console.info(obj);
	//console.info(event);
	var data = obj.data;
	for(var i=0;i<data.length;i++){
		
	
		
		
		var fwnrid = data[i]["fwnrid"];
		var servicecontentname = data[i]["servicecontentname"];
		data[i]["servicecontentname"]='<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="yl(\''+fwnrid+'\',\''+1+'\')">'+servicecontentname+'</a></div>';
		
		var isenabled = data[i]["isenabled"];
		if(isenabled=="0"){
			data[i]["isenabled"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="run(\''+fwnrid+'\',\''+1+'\')">启用</a></div>';
		}else{
			data[i]["isenabled"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="run(\''+fwnrid+'\',\''+0+'\')">停用</a></div>';
		}
		data[i]["custom"] = '<div class="jazz-grid-cell-inner"><a href="javascript:void(0);" onclick="update(\''+fwnrid+'\',\''+isenabled+'\')">修改</a></div>';
	}
	return data;
}

//加一个预览数据的界面
function yl(id){
		winEdit = jazz.widget({
	  	     vtype: 'window',
		   	     frameurl: './serviceContentDetial.jsp?id='+id,
		  			name: 'win',
		  	    	title: '预览数据',
		  	    	titlealign: 'left',
		  	    	titledisplay: true,
		  	        width: 1000,
		  	        height: 400,
		  	        modal:true,
		  	        visible: true,
		  	      	resizable: true
		   		});
}



function run(fwnrid,isenabled){	
	var s=isenabled=='1'?"停用":"启用";
	jazz.confirm("是否"+s+"？", function(){
		var params = {
				url : rootPath+'/dataservice/runServiceContent.do?fwnrid='+fwnrid+'&isenabled='+isenabled,
				components: ['zzjgGrid'],
				callback : function(data, r, res) { 
					if (res.getAttr("back") == 'success'){
						queryUrl();
						jazz.info(s+"成功!");
					}else if(res.getAttr("back") == 'exist'){
						jazz.info("该服务内容有被服务引用，无法停用，请去除引用再停用！"
						+"<div style='height: 10px;margin-left:-50px;' name='showpanel' id='showpanel' class='ztree' titledisplay='true'></div>");
					}else{
						jazz.info(s+"失败！");
					}
					var setting = {
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "pid"
			},
			key: {
				checked: "checked"
			}
		},
		check: { 
            /* enable: true, 
            chkboxType:{ "Y":'s', "N":'s'},  */
			enable: true,
			chkStyle: "radio",
			radioType: "level"
        }
	};
	
					var infuncUrl = rootPath+'/dataservice/servicenrTree.do?fwnrid='+fwnrid;
								var inparams = {
									url : infuncUrl,
									callback : function(data, r, res) { 
										var data = data["data"];
										tree = $.fn.zTree.init($("#showpanel"), setting, data);
										tree.expandAll(true);
									}
								};
				$(function(){
					$.DataAdapter.submit(inparams);
					});
				}
		}
		$.DataAdapter.submit(params);
	}, function(){});
	
}

/* function run(fwnrid,isenabled){
	var s=isenabled=='1'?"停用":"启用";
	jazz.confirm("是否"+s+"？", function(){
		var params = {
				url : rootPath+'/dataservice/runServiceContent.do?fwnrid='+fwnrid+'&isenabled='+isenabled,
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
} */
function add(){
	winEdit = jazz.widget({
	     vtype: 'window',
	     frameurl: './serviceContentAdd.jsp?update=false',
	  			name: 'win',
	  	    	title: '新增服务内容',
	  	    	titlealign: 'left',
	  	    	titledisplay: true,
	  	        width: 900,
	  	        height: 550,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true
	   		}); 
}
function update(fwnrid,isenabled){
	if(isenabled=='0'){
		jazz.info("该记录已经处于启用状态,无法修改!");
		return;
	}
	winEdit = jazz.widget({
  	     vtype: 'window',
	   	     frameurl: './serviceContentEdit.jsp?fwnrid='+fwnrid+'&update=true',
	  			name: 'win',
	  	    	title: '修改服务发布',
	  	    	titlealign: 'left',
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
<div>位置：数据服务>服务内容管理</div>
	<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel" titledisplay="true" width="100%" layout="table" 
		showborder="false" layoutconfig="{cols:2, columnwidth: ['50%','*'],border:false}" height="200" title="查询条件">
		
		<div name='createperson' vtype="textfield" label="创建人" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='createtime' vtype="textfield" label="创建时间" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div name='isenabled' vtype="comboxfield" label="状态" dataurl="[{checked: true,value: '0',text: '启用'},{value: '1',text: '停用'}]" labelAlign="right"  labelwidth='100px' width="410"></div>
		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div name="query_button" vtype="button" text="查询" 	icon="../query/queryssuo.png" onclick="queryUrl();"></div>
			<div name="reset_button" vtype="button" text="重置" icon="../query/queryssuo.png" click="reset();" ></div>
		</div>
	</div>

	<div vtype="gridpanel" name="zzjgGrid" height="400" width="100%"  id='zzjgGrid' datarender="renderdata()"
		titledisplay="true" title="查询列表"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
		<div name="toolbar" vtype="toolbar">
			<div name="btn1" align="right" vtype="button" text="新增" click="add()"></div>
    	</div>
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>
				<div name='fwnrid' key="true" visible="false" width="0%"></div>
				<div name='servicecontentname' text="服务内容名称" textalign="center"  width="22%"></div>
<!-- 				<div name='servicecontent' text="服务内容"  visible="false" textalign="center"  width="25%"></div> -->
				<div name='servicecontentshow' text="服务内容描述" textalign="center"  width="28%"></div>
				<div name='isenabled' text="状态" textalign="center"  width="8%"></div>
				<div name='createperson' text="创建人" textalign="center"  width="10%"></div>
				<div name='createtime' text="创建时间" textalign="center"  width="10%"></div>
				<div name='custom' text="操作" textalign="center"  width="20%"></div>
			</div>
		</div>
		<div vtype="gridtable" name="grid_table" ></div>
		<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
	</div>
	
	<div style='padding-left: 100px;height: 10px;'  id='funcTree' name='funcTree' class='ztree' titledisplay='true'></div>
</body>
</html>