<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>应用集成-厂商信息管理</title>
<%
	String contextpath = request.getContextPath();
%>
<script src="<%=contextpath%>/static/script/jazz.js" type="text/javascript"></script>
<script>
	var pkSmFirm;
	$(function(){
		queryData();
	});
	
	function queryData(){
		$('div[name="gridpanel"]').gridpanel('option','dataurl',rootPath+'/firmList/queryLinkman.do?pkSmFirm='+pkSmFirm);
		$('div[name="gridpanel"]').gridpanel('query', [ 'formpanel']);
	}
	
	var winEdit;
	function addData(){
		
		winEdit = top.jazz.widget({
	   	     vtype: 'window',
	   	     frameurl: rootPath+'/firmList/addLinkman.do?pkSmFirm='+pkSmFirm,
	  			name: 'win',
	  	    	title: '新增联系人信息',
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
	
	function updateData(pkSmLikeman){
		
		winEdit = top.jazz.widget({
	   	     vtype: 'window',
	   	     frameurl: rootPath+'/firmList/updateLinkman.do?pkSmLikeman='+pkSmLikeman,
	  			name: 'win',
	  	    	title: '修改联系人信息',
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
	
	
	function deleteData(pkSmLikeman){
		
		
		jazz.confirm("是否确认删除，删除后不可恢复", function(){
			var params = {
					url : rootPath+'/firmList/deleteLinkman.do',
					params: {pkSmLikeman:pkSmLikeman},
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
	
	function initData(res){
		pkSmFirm = res.getAttr("pkSmFirm");
		 
	}
	
	function reset(){
		$("iframe", parent.document).attr("src", rootPath+'/page/integeration/firm.jsp');
	}
	
	function renderdata(event, obj){
		var data = obj.data;
		for(var i=0;i<data.length;i++){
			data[i]["custom"] = '<div class="jazz-grid-cell-inner">'
								+'<a href="javascript:void(0);" title="编辑" onclick="updateData(\''+data[i]["pkSmLikeman"]+'\');"><img src="'+rootPath+'/static/images/other/forma-1.png" width="11px" height="11px" border="0"/></a>&nbsp;&nbsp;&nbsp;'
								+'<a href="javascript:void(0);" title="删除" onclick="deleteData(\''+data[i]["pkSmLikeman"]+'\');"><img src="'+rootPath+'/static/images/other/close.png" width="13px" height="13px" border="0"/></a>'
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
	<div class="title_nav">当前位置：应用集成 > <span>联系人信息管理</span></div>
	
	   	<div name="formpanel" id="formpanel" vtype="formpanel" titledisplay="false" width="100%" layout="table" showborder="false" 
		     layoutconfig="{cols:2, columnwidth: ['50%','*']}" title="联系人信息查询" >
			<div name='smLikeman' id='smLikeman' vtype="textfield" label="联系人名" labelalign="right" labelwidth="200" width="80%" editable="true"></div>
			<!-- <div name='phone' id='phone' vtype="textfield" label="联系电话" labelalign="right" labelwidth="100" width="80%" editable="true"></div> -->
			<div id="toolbar" name="toolbar" vtype="toolbar" >		
		    	<div id="btn3" name="btn3" vtype="button" align="center" defaultview="1" text="查 询"  click="queryData()"></div>
		    	<div id="btn4" name="btn4" vtype="button" align="center" defaultview="1" text="返 回"  click="reset()"></div>
		    </div>
	  	</div>
	   	
	   	<div vtype="gridpanel" name="gridpanel" id="gridpanel" width="100%" layout="fit" showborder="false" datarender="renderdata()" dataurl=""
		 	titledisplay="false"  isshowselecthelper="true" selecttype="0" rowselectable="false">
		 	<div name="toolbar" vtype="toolbar">
				<div id="add_button" name="add_button" vtype="button" align="right" iconurl="<%=contextpath %>/static/images/other/gridadd3.png" text="增加" click="addData()"></div>
	    	</div>
			<div vtype="gridcolumn" name="grid_column" id="grid_column">
				<div> <!-- 单行表头 -->
					<div name="pkSmFirm" visible="false"></div>
					<div name="pkSmLikeman" key="true" visible="false"></div>
					<div name="smLikeman"  text="联系人名" textalign="center" sort="true" width="15%"></div>
					<div name="phone" text="联系电话" textalign="center" sort="true" width="15%"></div>
					<div name="email" text="联系人邮箱" textalign="center" sort="true" width="20%"></div>
					<div name="createrTime" text="创建时间" textalign="center" sort="true" datatype="date" dataformat="YYYY-MM-DD" width="15%"></div>
					<div name="remarks" text="备注" textalign="center" sort="true" width="20%"></div>
					<div name="custom" text="操作" textalign="center"></div>
				</div>
			</div>
			<!-- 表格 -->
			<div vtype="gridtable" name="grid_table" id="grid_table"></div>
			<!-- 分页 -->
			<div vtype="paginator" name="grid_paginator" id="grid_paginator"></div>
	    </div>		
	
</body>
</html>