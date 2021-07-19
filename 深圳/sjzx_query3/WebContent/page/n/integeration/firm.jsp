<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>应用集成-厂商管理</title>
<%
	String contextpath = request.getContextPath();
%>
<script src="<%=contextpath%>/static/script/jazz.js" type="text/javascript"></script>
<script>
	$(function(){
		queryData();
	});
	
	function queryData(){
		$('div[name="gridpanel"]').gridpanel('option','dataurl',rootPath+'/firmList/queryFirm.do');
		$('div[name="gridpanel"]').gridpanel('query', [ 'formpanel']);
	}
	
	function addData(){
		
		winEdit = top.jazz.widget({
	   	     vtype: 'window',
	   	     frameurl: rootPath+'/firmList/addFirm.do',
	  			name: 'win',
	  	    	title: '新增厂商信息',
	  	    	titledisplay: true,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true,
	  	        minimizable : true, //是否显示最小化按钮
	  		    showborder : true, //true显示窗体边框    false不显示窗体边
	  		    closestate : true, //true关闭   false隐藏 
	  		    titleicon : "<%=contextpath%>/static/images/other/notepad-.png",
	  		  	width: 750, 
	          	height: 500, 
	   		});
	}
	
	function updateData(pkSmFirm){
		
		winEdit = top.jazz.widget({
	   	     vtype: 'window',
	   	     frameurl: rootPath+'/firmList/updateFirm.do?pkSmFirm='+pkSmFirm,
	  			name: 'win',
	  	    	title: '修改厂商信息',
	  	    	titledisplay: true,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true,
	  	        minimizable : true, //是否显示最小化按钮
	  		    showborder : true, //true显示窗体边框    false不显示窗体边
	  		    closestate : true, //true关闭   false隐藏 
	  		    titleicon : "<%=contextpath%>/static/images/other/notepad-.png",
	  		  	width: 750, 
	          	height: 500, 
	   		});
	}
	
	function deleteData(pkSmFirm){
		
		
		jazz.confirm("是否确认删除，删除后不可恢复", function(){
			var params = {
					url : rootPath+'/firmList/deleteFirm.do',
					params: {pkSmFirm:pkSmFirm},
					callback : function(data, r, res) { 
						if (res.getAttr("back") == 'success'){
							queryData();
							jazz.info("删除成功");
						}
					}
			}
			$.DataAdapter.submit(params);
		}, function(){})
	
	}
	var winEdit;
	function checkData(pkSmFirm){
		$("iframe", parent.document).attr("src", rootPath+'/firmList/checkFirm.do?pkSmFirm='+pkSmFirm);

		/* winEdit = jazz.widget({
	   	     vtype: 'window',
	   	     frameurl: rootPath+'/firmList/checkFirm.do?pkSmFirm='+pkSmFirm,
	  			name: 'win',
	  	        width: '100%',
	  	        height: '100%',
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true
	   		}); */
	
	}

	
	function reset(){
		$('div[name="formpanel"]').formpanel('reset');
	}
	
	function renderdata(event, obj){
		var data = obj.data;
		for(var i=0;i<data.length;i++){
			data[i]["custom"] = '<div class="jazz-grid-cell-inner">'
								+'<a href="javascript:void(0);" title="编辑" onclick="updateData(\''+data[i]["pkSmFirm"]+'\');"><img src="'+rootPath+'/static/images/other/forma-1.png" width="11px" height="11px" border="0"/></a>&nbsp;&nbsp;&nbsp;'
								+'<a href="javascript:void(0);" title="联系人" onclick="checkData(\''+data[i]["pkSmFirm"]+'\');"><img src="'+rootPath+'/static/images/other/lianxiren.png" width="11px" height="12px" border="0"/></a>&nbsp;&nbsp;&nbsp;'
								+'<a href="javascript:void(0);" title="删除" onclick="deleteData(\''+data[i]["pkSmFirm"]+'\');"><img src="'+rootPath+'/static/images/other/close.png" width="13px" height="13px" border="0"/></a>'
								+'</div>';
		}
		return data;
	}
	
	function leave(){
		winEdit.window('close'); 
		 
	}

</script>


</head>
<body>
	<div class="title_nav">当前位置：应用集成 > <span>厂商管理</span></div>
	
	   	<div name="formpanel" id="formpanel" vtype="formpanel" titledisplay="false" width="100%" layout="table" showborder="false" 
		     layoutconfig="{cols:2, columnwidth: ['50%','*']}" title="厂商信息查询"  >
			<div name='firmName' id='firmName' vtype="textfield" label="厂商名称" labelalign="right" labelwidth="200" width="80%" editable="true"></div>
			<div name='firmNameShort' id='firmNameShort' vtype="textfield" label="厂商简称" labelalign="right" labelwidth="200" width="80%" editable="true"></div>
			<div id="toolbar" name="toolbar" vtype="toolbar" >		
		    	<div id="btn3" name="btn3" vtype="button" align="center" defaultview="1" text="查 询"  click="queryData()"></div>
		    	<div id="btn4" name="btn4" vtype="button" align="center" defaultview="1" text="重 置"  click="reset()"></div>
		    </div>
	  	</div>
	   	
	   	<div vtype="gridpanel" name="gridpanel" id="gridpanel" width="100%" layout="fit" showborder="false" datarender="renderdata()" dataurl=""
		 	titledisplay="false"  isshowselecthelper="true" selecttype="0" rowselectable="false">
		 	<div name="toolbar" vtype="toolbar">
				<div id="add_button" name="add_button" vtype="button" align="right" iconurl="../../static/images/other/gridadd3.png" text="增加" click="addData()"></div>
	    	</div>
			<div vtype="gridcolumn" name="grid_column" id="grid_column">
				<div> <!-- 单行表头 -->
					<div name="pkSmFirm" key="true" visible="false"></div>
					<div name="firmName"  text="厂商名称" textalign="center" sort="true" width="30%"></div>
					<div name="firmNameShort" text="厂商简称" textalign="center" sort="true" width="13%"></div>
					<div name="phone" text="联系电话" textalign="center" sort="true" width="12%"></div>
					<div name="address" text="联系地址" textalign="center" sort="true" width="30%"></div>
					
					<!-- <div name="zipCode" text="邮编" textalign="center" sort="true" width="10%"></div>
					<div name="fax" text="传真" textalign="center" width="10%"></div>
					<div name="remarks" text="备注" textalign="center" width="15%"></div> -->
					<div name="custom" text="操作" textalign="center" ></div>
				</div>
			</div>
			<!-- 表格 -->
			<div vtype="gridtable" name="grid_table" id="grid_table"></div>
			<!-- 分页 -->
			<div vtype="paginator" name="grid_paginator" id="grid_paginator"></div>
	    </div>		
	
</body>
</html>