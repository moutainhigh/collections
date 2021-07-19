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
<script src="<%=contextpath%>/static/script/jazz.js"
	type="text/javascript"></script>
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
		  		    titleicon : "<%=contextpath%>
	/static/images/other/notepad-.png",
			width : 750,
			height : 400,
		});

	}

	function deleteData(code) {

		jazz.confirm("是否确认删除，删除后不可恢复", function() {
			var params = {
				url : rootPath
						+ '/dictionaryDetails/deleteValue.do?codeTableEnName='
						+ codeTableEnName + '&code=' + code,
				components : [ 'gridValue' ],
				callback : function(data, r, res) {
					if (res.getAttr("back") == 'success') {
						query();
						jazz.info("删除成功");
					}
				}
			}
			$.DataAdapter.submit(params);
		}, function() {
		})

	}

	function leave() {
		winEdit.window('close');

	}
</script>
</head>
<body>
	<div vtype="gridpanel" name="gridpanel" height="400" width="700"
		datarender="renderdata()" titledisplay="true" title="查询结果"
		dataurl="reg.json" isshowselecthelper="true" selecttype="2"
		iseditor="true">

		<div vtype="gridcolumn" name="grid_column">

			<div>
				<!-- 单行表头 -->


				<div datatype="comboxtree"
					dataurl="[{'id': '1', 'pId': '0', 'name': '北京'}, {'id': '2', 'pId': '0', 'name': '天津'},{'id': '3', 'pId': '0', 'name': '黑龙江省'},{'id': '4', 'pId': '0', 'name': '吉林省'},{'id': '31', 'pId': '3', 'name': '长春市'},{'id': '32', 'pId': '0', 'name': '四平市'},{'id': '5', 'pId': '0', 'name': '辽宁省'}, {'id': '11', 'pId': '1', 'name': '东城'}, {'id': '12', 'pId': '1', 'name': '西城'}, {'id': '13', 'pId': '1', 'name': '海淀'}, {'id': '14', 'pId': '1', 'name': '丰台'},{'id': '131', 'pId': '13', 'name': '中关村'}]"
					name="name" text="姓名" textalign="center" sort="true" width="150px"></div>

				<div datatype="combox" name="sex" text="性别" textalign="center"
					sort="true"
					dataurl='[{"text": "男","value": "1"},{"text": "女", "value": "2"}]'
					width="50px"></div>

				<div datatype="number" name="age" text="年龄" textalign="center"
					sort="true" width="100px"></div>

				<div datatype="date" name="birthday" text="出生日期" textalign="center"
					sort="true" dataformat="yyyy-MM-dd" width="150px"></div>

				<div name="org" text="所属机构" textalign="center" sort="true"
					dataurl="/JAZZ/dictionary/queryData.do?dicId=org" width="50px"></div>

				<div name="custom" text="自定义" textalign="center" width="100px"></div>

			</div>

		</div>

		<!-- 表格 -->

		<div vtype="gridtable" name="grid_table"></div>

	</div>



	<br>
	<br>
	<br>

	<div name='combox_name' vtype="comboxfield" editable="ture"
		filterable="true" label="现居地" labelalign="right" width="300"
		dataurl='[{"text": "北京11","value": "001"},{"text": "上海22", "value": "002"}, {"text": "广州33", "value": "003"}]'></div>

	<br>
	<br>
	<br>

	<div id="box_1" name="box_1"></div>
</body>
</html>
