<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>增加共享资源</title>
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
var setting = {
		data: {
			simpleData: {
				enable: true
			}
		}
	};

	var zNodes =[
		{ name:"主体库", open:true,
			children: [
				{ name:"12315主题库",
					children: [
						{ name:"违法广告"},
						{ name:"网格"},
						{ name:"TcaseInfo"}
					]},
				{ name:"产商品主题",isParent:true},
				{ name:"人员主题",isParent:true}
			]}
	];
$(document).ready(function(){
	$("#treeDemo").tree({setting: setting, data: zNodes});
	var tname = parent.window.name;
	//querysrTofrom(aa);查新增表信息
	tableName(tname);
	 $('div[name="tablename"]').textfield('setValue', tname);
});

	/* function querysrTofrom(name){
		$.ajax({
		  url: "/gdsjzx/shareResource/querysrTofrom.do",
		  data:"tname="+name,
		  type:"POST",
		  cache: false,
		  success: function(data){
		    //alert("data"+data);
		    //获取到数据打印在页面上的
		  	for(var i=0;i<data.length;i++){
					$('<a class="button blue bigrounded" href="javascript:void(0);" onclick="tableName(\''+data[i].name+'\')">'+data[i].name+'</a>').appendTo($('#tableTName'));
				}
		  }
		});
	}
 */

	function tableName(name){
		$.ajax({
		  url: "/gdsjzx/shareResource/querytdata.do",
		  data:"tname="+name,
		  type:"POST",
		  cache: false,
		  success: function(data){
		    //alert("data"+data);
		    //获取到数据打印在页面上的
		  	for(var i=0;i<data.length;i++){
					$('<a class="button blue bigrounded" href="javascript:void(0);" onclick="tableName(\''+data[i].name+'\')">'+data[i].name+'</a>').appendTo($('#tableTName'));
				}
		  }
		});
	}

function queryUrl() {
	$('#zzjgGrid').gridpanel('option', 'dataurl',rootPath+'/shareResource/querysr.do');
	$('#zzjgGrid').gridpanel('query', [ 'formpanel']);
}


function renderdata(event, obj){
	var data = obj.data;
	for(var i=0;i<data.length;i++){
		var rsid = data[i]["rsid"];
		var caozuo = data[i]["caozuo"];
		var type=data[i]["tabletype"];
		
		data[i]["caozuo"] = '<div class="jazz-grid-cell-inner"> <a href="javascript:void(0);" onclick="detail(\''+rsid+'\')">详情   </a><a href="javascript:void(0);" onclick="update(\''+rsid+'\')">修改</a>  <a href="javascript:void(0);" onclick="del(\''+rsid+'\')">删除</a> </div>';
		if(type==0){
			data[i]["tabletype"] ="业务表";
		}
		else {
			data[i]["tabletype"] ="代码表";
		}
	}
	return data;
}

function detail(rsid){
	winEdit = jazz.widget({
	     vtype: 'window',
	   	     frameurl: './shareResourceDetail.jsp?rsid='+rsid,
	  			name: 'win',
	  	    	title: '详情',
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
			   	     frameurl: './shareTNameDetail.jsp',
			  			name: 'win',
			  	    	title: '详情',
			  	    	titlealign: 'left',
			  	    	titledisplay: true,
			  	        width: 900,
			  	        height: 550,
			  	        modal:true,
			  	        visible: true,
			  	      	resizable: true
			   		});
	
}
		function save(){
		$.ajax({
		  url: "/gdsjzx/shareResource/insertdata.do",
		  data:"tname="+name,
		  type:"POST",
		  cache: false,
		  success: function(data){
		    //alert("data"+data);
		    //获取到数据打印在页面上的
		  	for(var i=0;i<data.length;i++){
					$('<a class="button blue bigrounded" href="javascript:void(0);" onclick="tableName(\''+data[i].name+'\')">'+data[i].name+'</a>').appendTo($('#tableTName'));
				}
		  }
		});
		}
</script>
</head>
<body>
<div>位置：数据服务>数据共享>增加共享资源</div>
    		<div name="c1" vtype="panel" title="增加共享资源" titledisplay="false" height="100%">
    			<div vtype="formpanel" id="formpanel" displayrows="1" name="formpanel" titledisplay="true" width="100%" layout="table" 
					showborder="false" layoutconfig="{cols:2, columnwidth: ['50%','*'],border:false}" height="250" title="查询条件">
					<div name='tablename' readonly="true" vtype="textfield" label="表名" labelAlign="right" labelwidth='100px' width="410"></div>
					<div name="query_button" vtype="button" text="点击查看表名列表" align="left"
							icon="../query/queryssuo.png" onclick="add();"></div>
					<div name='chname' vtype="textfield" label="表中文名" labelAlign="right" labelwidth='100px' width="410"></div>
					<div name='tabletype' vtype="comboxfield" label="表类型"   labelAlign="right" dataurl="[{checked: true,value: '1',text: '业务表'},{value: '2',text: '代码表'}]" labelwidth='100px' width="410"></div>
					
					<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
						<div name="query_button" vtype="button" text="保存到共享资源" 
							icon="../query/queryssuo.png" onclick="save();"></div>
						<div name="reset_button" vtype="button" text="重置"
							icon="../query/queryssuo.png" click="reset();" ></div>
					</div>
				</div>
    			
    			<div vtype="gridpanel" name="zzjgGrid" height="200" width="100%"  id='zzjgGrid' datarender="renderdata()"
				titledisplay="true" title="字段信息"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
				<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="left">
					<div id="btn1" name="btn1" vtype="button" text="新增"
						icon="../../../style/default/images/save.png" click="add()"></div>
				</div>
				<!-- 表头 -->
				<div vtype="gridcolumn" name="grid_column" width="100%" style="display:none;">
					<div>
						<div name='rsid'></div>
						<div name='tablename' text="库表名" textalign="center"  width="14%"></div>
						<div name='chname' text="库表中文名" textalign="center"  width="14%"></div>
						<div name='theme' text="主题" textalign="center"  width="18%"></div>
						<div name='tabletype' text="表类型" textalign="center"  width="10%"></div>
						<div name='datasum' text="数据量" textalign="center"  width="10%"></div>
						<div name='state' text="状态" textalign="center"  width="10%"></div>
						<div name='description' text="描述" textalign="center" ></div>
						<div name='caozuo' text="操作" textalign="center"  width="24%"></div>
					</div>
				</div>
				<!-- 表格 -->
				<!-- ../../grid/reg3.json -->
				<div vtype="gridtable" name="grid_table" ></div>
				<!-- 分页 -->
				<div vtype="paginator" name="grid_paginator" theme="2" pagerows="20"></div>
			</div>
    		</div>
    	</div>
    </div>
</body>
</html>