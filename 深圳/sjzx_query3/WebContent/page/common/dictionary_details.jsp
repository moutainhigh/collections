<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%
	String contextpath = request.getContextPath();
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=contextpath %>/static/script/jazz.js" type="text/javascript"></script>
<script>

 	var codeTableEnName;
	var codeTableChName;
	$(function() {
		
		$("#tab2").css("overflow-x", "hidden"); 
		queryTableStruct();
		query();
		$('#codetable').panel('option','title','代码表: '+codeTableEnName+'  '+codeTableChName);
	
		addButton();
		
		
	});
	
    function addButton(){ 
        $('#codetable').panel('addTitleButton', [{ 
            id: "id_1", 
            align: "right", 
            icon: "../static/script/JAZZ-UI/lib/themes/gongshang/images/panel-close.png",        
            click:function(e,ui){ 
            	 $('#codetable').panel('close'); 
            	goback(); 
            } 
         }]);  
    } 

	
	function initData(res){
		codeTableEnName = res.getAttr("codeTableEnName");
		codeTableChName = res.getAttr("codeTableChName");
		
		// alert(pkCodeTableManager);
		 
	}
	
	function renderdata(event, obj){
		var data = obj.data;
		for(var i=0;i<data.length;i++){
			data[i]["custom"] = '<div class="jazz-grid-cell-inner">'
								+'<a href="javascript:void(0);" title="编辑" onclick="updateData(\''+data[i]["code"]+'\');"><img src="'+rootPath+'/static/images/other/forma-1.png" width="11px" height="11px" border="0"/></a>&nbsp;&nbsp;&nbsp;'
								+'<a href="javascript:void(0);" title="删除" onclick="deleteData(\''+data[i]["code"]+'\');"><img src="'+rootPath+'/static/images/other/close.png" width="13px" height="13px" border="0"/></a>&nbsp;&nbsp;&nbsp;'
								+'</div>';
		}
		return data;
	}
	
 	//查询代码表结构
	function queryTableStruct(){
		$('div[name="gridStruct"]').gridpanel('option','dataurl',rootPath+'/dictionaryDetails/queryStruct.do?codeTableEnName='+codeTableEnName);
		$('div[name="gridStruct"]').gridpanel('reload');
	}
 	
 	function query(){
 		$('div[name="gridValue"]').gridpanel('option','dataurl',rootPath+'/dictionaryDetails/queryValue.do?codeTableEnName='+codeTableEnName);
		$('div[name="gridValue"]').gridpanel('query', [ 'formpanel']);
 	}
 	
 	function reset(){
		$('div[name="formpanel"]').formpanel('reset');
	}
 	
 	function goback(){
 		$("iframe", parent.document).attr("src", rootPath+'/page/common/dictionary_list.jsp');
 	}
 	
 	function addData(){
		winEdit = top.jazz.widget({
	   	     vtype: 'window',
	   	     frameurl: rootPath+'/dictionaryDetails/add.do?codeTableEnName='+codeTableEnName+'&update=false',
	  			name: 'win',
	  	    	title: '新增代码信息',
	  	    	titledisplay: true,
	  	        modal:true,
	  	        visible: true,
	  	      	resizable: true,
	  	        minimizable : true, //是否显示最小化按钮
	  		    showborder : false, //true显示窗体边框    false不显示窗体边
	  		   // maximize : true, //默认最大化展开
	  		    closestate : true, //true关闭   false隐藏 
	  		   titleicon : "<%=contextpath%>/static/images/other/notepad-.png",
	  		  	width: 750, 
	          	height: 400, 
	   		});
	}
 	
 	function updateData(code){
		
			
			winEdit = top.jazz.widget({
		   	     vtype: 'window',
		   	     frameurl: rootPath+'/dictionaryDetails/update.do?codeTableEnName='+codeTableEnName+'&update=true&code='+code,
		  			name: 'win',
		  	    	title: '修改代码信息',
		  	    	titledisplay: true,
		  	        modal:true,
		  	        visible: true,
		  	      	resizable: true,
		  	        minimizable : true, //是否显示最小化按钮
		  		    showborder : false, //true显示窗体边框    false不显示窗体边
		  		    //maximize : true, //默认最大化展开
		  		    closestate : true, //true关闭   false隐藏 
		  		    titleicon : "<%=contextpath%>/static/images/other/notepad-.png",
		  		  	width: 750, 
		          	height: 400, 
		   		});
			
		
	}
 	
 	function deleteData(code){
		
		
			jazz.confirm("是否确认删除，删除后不可恢复", function(){
				var params = {
						url : rootPath+'/dictionaryDetails/deleteValue.do?codeTableEnName='+codeTableEnName+'&code='+code,
						components: ['gridValue'],
						callback : function(data, r, res) { 
							if (res.getAttr("back") == 'success'){
								query();
								jazz.info("删除成功");
							}
						}
				}
				$.DataAdapter.submit(params);
			}, function(){})
		
	}
 	
 	function leave(){
		winEdit.window('close'); 
		 
	}
 
</script>
</head>
<body>
	<!-- <div id="codetable">  -->
	<div vtype="panel" id="codetable" width="100%" height="100%" layout="fit" titledisplay="true" bgcolor="white" showborder="false"> 
        
        <div name="tab_name" vtype="tabpanel" overflowtype="2" tabtitlewidth="150" width="100%" height="99.5%" showborder="false" layout="fit"
            orientation="top" id="tab_name">      
            <ul>      
               <li><a href="#tab1">代码表结构</a></li>      
               <li><a href="#tab2">代码表记录维护</a></li>     
            </ul>      
            <div>
            	<div id="tab1" height="100%" weight="100%"> 
		              <div vtype="gridpanel" name="gridStruct" id="gridStruct" width="100%"   showborder="false" height="100%"
							titledisplay="false" dataurl="" layout="fit" isshowselecthelper="true" selecttype="0" rowselectable="false">
					        <!-- 表头 -->
							<div vtype="gridcolumn" name="grid_column" >
								<div>
									<!-- 单行表头 -->
									<div name='columnName' text="字段名" textalign="center" sort="true" width='20%'></div>
									<div name='comments' text="中文名" textalign="center" sort="true" width='17%'></div>
									<div name='constraintType' text="约束类型" textalign="center" sort="true" width='17%'></div>
									<div name='dataType' text="字段类型" textalign="center" sort="true" width='17%'></div>	
									<div name='nullable' text="可否为空" textalign="center" sort="true" width='13%'></div>
									<div name='dataLength' text="字段长度" textalign="center" sort="true" ></div>
								</div>
							</div>
							<!-- 表格 -->
							<div vtype="gridtable" name="grid_table"></div>
							<!-- 分页 -->
							<div vtype="paginator" name="grid_paginator" id="grid_paginator"></div>
		               </div>      
		    	</div>
		        <div id="tab2" height="100%" weight="100%">
		      	
			       <div vtype="formpanel" id="formpanel"  name="formpanel" titledisplay="false" width="100%" layout="table"  showborder="false" 
						layoutconfig="{cols:2, columnwidth: ['50%','*']}" title="代码值查询">

						<div name='code' vtype="textfield" label="代码" labelalign="right" width="80%" labelwidth="200"></div>
						<div name='name' vtype="textfield" label="名称" labelalign="right" width="80%" labelwidth="200"></div>
						
						
        				<div id="toolbar" name="toolbar" vtype="toolbar" >		
		    				<div id="btn3" name="btn3" vtype="button" align="center" defaultview="1" text="查 询"  click="query()"></div>
		    				<div id="btn4" name="btn4" vtype="button" align="center" defaultview="1" text="重 置"  click="reset()"></div>
		   			    </div>
			
						
					</div>
					<div vtype="gridpanel" name="gridValue" id="gridValue" width="100%" showborder="false" height="60%" rowselectable="false"
							isshowselecthelper="true" selecttype="0" titledisplay="false" datarender="renderdata()" dataurl="" layout="fit">
					        <div name="toolbar" vtype="toolbar">
						    <div id="add_button" name="add_button" vtype="button" align="right" text="增加" 
								iconurl="<%=contextpath %>/static/images/other/gridadd3.png" click="addData()"></div>
						 
			
						    </div>
					        <!-- 表头 -->
							<div vtype="gridcolumn" name="grid_column" >
								<div>
									<!-- 单行表头 -->
									<div name='code' text="代码" textalign="center" sort="true" width='19%'></div>
									<div name='name' text="名称" textalign="center" sort="true" width='19%'></div>
									<div name='nameShort' text="简称" textalign="center" sort="true" width='15%'></div>
									<div name='fCode' text="父节点代码" textalign="center" sort="true" width='15%'></div>	
									<div name='chooseMark' text="选用标记" textalign="center" width='15%' sort="true" datatype='comboxfield'
										dataurl='[{"text":"选用","value":"0"},{"text":"停用","value":"1"}]'></div>
									<!-- <div name='createrTime' text="创建时间" textalign="center" width='17%'></div>
									<div name='modifierTime' text="最后修改时间" textalign="center" width='17%'></div> -->
									<div name="custom" text="操作" textalign="center" ></div>
								</div>
							</div>
							<!-- 表格 -->
							<div vtype="gridtable" name="grid_table"></div>
							<!-- 分页 -->
							<div vtype="paginator" name="paginator" id="paginator"></div>		            
					</div>      
		      	
		      	</div>
		               
			</div>
              
        </div>  
                       
    </div>  
	
</body>
</html>