<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>执行查询</title>
<style type="text/css">
</style>
</head>
<%
	String json = (String) request.getAttribute("bo");
%>
<%
	String type = (String) request.getParameter("type");
%>

<script src="<%=request.getContextPath()%>/static/script/jazz.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/sczt/base64.js"
	type="text/javascript"></script>
<script language="javascript">
	function doIt(){
		$(function(){
		    $('#doIt').click(function(){
		         $.ajax({
		             type: "GET",
		             url: "../../advQuery/doQuery.do",
		             data: "id=2",
		             async:true,
// 		             dataType: "json",
		             success: function(data){
// 		                         $('#showTable').empty();   //清空resText里面的所有内容
		                         var html = ''; 
// 		                         $.each(data, function(commentIndex, comment){
// 		                               html += '<div class="comment"><h6>' + comment['username']
// 		                                         + ':</h6><p class="para"' + comment['content']
// 		                                         + '</p></div>';
// 		                         });
								html=data;
								console.log(html);
		                         $('#showTable').html(html);
		                      }
		         });
		    });
		});

	}
// 	function query() {
// 		 //console.log(1);
// 		var url = "../../advQuery/getList.do";
// 		$('#gridpanel').gridpanel('option', 'dataurl', url);
// 		$('#gridpanel').gridpanel('query', [ 'formpanel' ]);
// 		//提交form表单，并且回绑gridpanel值。
// 	}
// 	function del(id) {
// 		jazz.confirm("请确认是否删除此查询？", function() {
// 			var params = {
// 				url : "../../advQuery/delQuery.do?id=" + id,
// 				callback : function(data, r, res) {
// 					if (res.getAttr("back") == 'success') {
// 						query();
// 						jazz.info("删除成功");
// 					}
// 				}
// 			}
// 			$.DataAdapter.submit(params);
// 		});
// 	}
</script>
<style>

</style>
<body>

	<div id="queryBaseInfo" name="queryBaseInfo" vtype="formpanel"
		titledisplay="true" width="100%" layout="table"
		layoutconfig="{cols:2, columnwidth: ['50%','50%']}" height="300m"
		title="查询基本信息" style="float:left;">
		<div name='tableNameCn' vtype="textareafield" label="查询表"
			labelalign="right" readonly="true" width="300"></div>
		<div name='columnNameCn' vtype="textareafield" label="查询字段"
			labelalign="right" width="300" readonly="true"></div>
		<div name='queryName1' vtype="textareafield" label="关联条件"
			labelalign="right" readonly="true" width="300"></div>
		<div name='createUser1' vtype="textareafield" label="查询条件"
			labelalign="right" width="300" readonly="true"></div>
	</div>
	
	

 <!-- 	<div vtype="gridpanel" name="gridpanel" id="gridpanel" width="100%" -->
<!-- 		layout="fit" showborder="false" datarender="editColumn" -->
<!-- 		rowselectable="false" titledisplay="false" isshowselecthelper="true" -->
<!-- 		selecttype="0" dataurl="../../advQuery/getList.do"> -->


<!-- 		<div style="height:2px;"></div> -->
<!-- 		<div vtype="gridcolumn" name="grid_column" id="grid_column"> -->
<!-- 			<div id="dateShowRows"> -->
<!-- 				单行表头 -->
				
<!-- 				<div name='queryInfoId' key="true" visible="false"></div> -->
<!-- 				<div name='queryInfoName' text="查询名称" textalign="center" width="28%" -->
<%-- 					dataurl="<%=request.getContextPath()%>/caseselect/code_value.do?type=caseNo"></div> --%>
<!-- 				<div name='creatorId' text="创建人" textalign="center" width="28%"></div> -->
<!-- 				<div name='createdTime' text="创建时间" textalign="center" width="28%"></div> -->
<!-- 				<div name='edit' text="操作" textalign="center" width="18%"></div> -->

<!-- 			</div> -->
<!-- 		</div> -->
		
<!-- 		表格 -->
<!-- 		<div vtype="gridtable" name="grid_table" id="grid_table" rowrender=""></div> -->
<!-- 		分页 -->
<!-- 		<div vtype="paginator" name="grid_paginator" pagerows="50" -->
<!-- 			id="grid_paginator"></div> -->
<!-- 	</div> -->
<!-- 	<div name="toolbar" vtype="toolbar"> -->
<!-- 			<div name="button" vtype="button" text="新增高级查询"></div> -->
<!-- 	</div>   -->
	<div id="toolbar" name="toolbar" vtype="toolbar" align="center" style="margin-right:500px;">
		<div id="doIt" vtype="button" text="执行查询" click="doIt();"></div>
	</div>
	
	<div id="showTable">
		
	</div>
	 
</body>
</html>