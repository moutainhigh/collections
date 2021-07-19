<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>迁移信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/sczt/sczt.js"></script>

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/static/css/reg/reg.css"></link>

<script type="text/javascript">
function altRows(id){
		if(document.getElementsByTagName){  
			
			var table = document.getElementById(id);  
			var rows = table.getElementsByTagName("tr"); 
			 
			for(i = 0; i < rows.length; i++){          
				if(i % 2 == 0){
					rows[i].className = "evenrowcolor";
				}else{
					rows[i].className = "oddrowcolor";
				}      
			}
		}
	}

	window.onload=function(){
		altRows('datashow');
	}

	function handler(){
						if($('.jazz-grid-row').length!=0){
							$('.jazz-grid-row').click( function () {
									var tds=$(this).children("td");
									
										//$(tdmethod());
									for(var i=0;i<tds.length;i++){	
										$('#row'+i).text("");
										if($(tds[i]).html()=="&nbsp;"){
											$('#row'+i).text("");
										}else{				
											$('#row'+i).text($(tds[i]).html());
										}
								 	}
								 	var status=$('#row8').text();
								 	if("迁入"==status){
								 		$(".class_td1:eq(0)").html("");
								 		$(".class_td1:eq(0)").html("迁入函号:");
								 		$(".class_td1:eq(1)").html("");
								 		$(".class_td1:eq(1)").html("迁入地区:");
								 		$(".class_td1:eq(2)").html("");
								 		$(".class_td1:eq(2)").html("迁入原因:");
								 		$(".class_td1:eq(3)").html("");
								 		$(".class_td1:eq(3)").html("迁入地登记机关:");
								 		$(".class_td1:eq(4)").html("");
								 		$(".class_td1:eq(4)").html("迁入日期:");
								 	}else{
								 		$(".class_td1:eq(0)").html("");
								 		$(".class_td1:eq(0)").html("迁出函号:");
								 		$(".class_td1:eq(1)").html("");
								 		$(".class_td1:eq(1)").html("迁出地区:");
								 		$(".class_td1:eq(2)").html("");
								 		$(".class_td1:eq(2)").html("迁出原因:");
								 		$(".class_td1:eq(3)").html("");
								 		$(".class_td1:eq(3)").html("迁出地登记机关:");
								 		$(".class_td1:eq(4)").html("");
								 		$(".class_td1:eq(4)").html("迁出日期:");
								 	}
									$("#formpanel").show(); 
							 		});
								 }else{
									i=i+100;
									$(timedata());
								}
							 }
		function method(i){
							$(".class_td1").get(1).val("");
							 }
</script>
			 
				 
</head>
<body >
<div vtype="gridpanel" name="jbxxGrid" height="236" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()"
			selecttype="1"	titledisplay="true" title="迁入迁出信息"   showborder="false" isshowselecthelper="false"  selecttype="2">
	<!-- 表头 -->
	<div vtype="gridcolumn" name="grid_column" width="100%">
		<div> 
		<!-- 迁入信息 -->
		<div name='letnum' text="函号" textalign="center"  width="20%"></div>
		<div name='area' text="迁入/迁出地区" textalign="center"  width="25%"></div>
		<div name='rea' text="原因" textalign="center" ></div>
		<div name='areregorg' text="所在地登记机关" textalign="center"  width="25%"></div>
		<div name='mdate' text="日期" textalign="center"  width="20%"></div>
	

		
			<!-- 迁入迁出  以下字段name一样数据是否一样 -->
		 <div name='archremovemode' text="企业档案移交方式" textalign="center"></div>
		<div name='historyinfoid' text="业务历史编号" textalign="center"></div>
		<div name='qrqctype' text="迁移类型" textalign="center" width="10%"></div>		
		<div name='memo' text="备注" textalign="center"></div>

		
	</div>
	</div>
	<!-- 表格 -->
	<div vtype="gridtable" name="grid_table"></div>
	<!-- 卡片 -->
	<!-- <div vtype="gridcard" name="grid_card" width="205px" height="90px"></div> -->
	<!-- 分页 -->
	<!-- <div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div> -->
	
</div>
  	<div id="formpanel" class="formpanel_table1" style="height:250px;">
			<div class="font_title">详细信息</div>
			<table id="datashow" class="font_table" >
				<tr class="class_hg"><th class="class_td1">迁出函号:</th><th class="class_td2" id="row1"></th><th class="class_td1">地区:</th><th id="row2" class="class_td5"></th></tr>
				<tr><th class="class_td1">原因:</th><th class="class_td2" id="row3"/><th class="class_td1">地登记机关:</th><th class="class_td5" id="row4"></th></tr>
				<tr><th class="class_td1">日期:</th><th class="class_td2" id="row5"/><th class="class_td1">企业档案移交方式:</th><th class="class_td5" id="row6"></th></tr>
				<tr><th class="class_td1">业务历史编号:</th><th class="class_td2" id="row7"/><th class="class_td1">迁移类型:</th><th class="class_td5" id="row8"></th></tr>
				<tr><th class="class_td1">备注:</th><th class="class_td2" id="row9" colspan="3"/>
			</table>
	</div>
</body>
</html>