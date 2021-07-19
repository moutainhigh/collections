<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>变更信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>

<script src="<%=request.getContextPath()%>/static/js/sczt/sczt.js" type="text/javascript"></script>
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
     	// alert($('.jazz-grid-row').length);
						if($('.jazz-grid-row').length!=0){
							$('.jazz-grid-row').click( function () {
									//$("#formpanel").closeshow();
									var tds=$(this).children("td");
									for(var i=0;i<tds.length;i++){							
										$('#row'+i).text("");
										if($(tds[i]).html()=="&nbsp;"){
											$('#row'+i).text("");
										}else{				
											$('#row'+i).text($(tds[i]).html());
										}
								 		}
									//$("#formpanel").appendTo($('#jbxxGrid')); 
									$("#formpanel").show();
							 		});
								 }else{
									i=i+100;
									//alert(i);
									$(timedata());
								}
							 }
</script>
				 
</head>
<body >
<div vtype="gridpanel" name="jbxxGrid" height="345" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()"
			selecttype="1"	titledisplay="true" title="变更信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<!-- 表头 -->
	<div vtype="gridcolumn" name="grid_column" width="98%">
		<div> 
		<!-- 变更信息 -->
			<div name='altitem' text="变更事项" textalign="center"  ></div>
			<div name='altdate' text="变更日期" textalign="center"  width="25%"></div>
			<div name='altbe' text="变更前内容" textalign="center"  ></div>
			<div name='altaf' text="变更后内容" textalign="center"  ></div>
			<div name='alttime' text="变更次数" textalign="center" width="25%"></div>

			<div name='operator' text="操作者" textalign="center" width="25%"></div>
			<div name='operatedate' text="操作日期" textalign="center"></div>
			<div name='modifystatus' text="变更状态" textalign="center"  width="25%"></div>
			<div name='content' text="详细内容" textalign="center"></div>
						
	</div>
	</div>
	<!-- 表格 -->
	<div vtype="gridtable" name="grid_table"></div>
	<!-- 分页 -->
		<!-- <div vtype="paginator" name="grid_paginator" theme="2" pagerows="20"></div> -->
</div>
<div id="formpanel" class="formpanel_table1" style="height:250px;">
			<div class="font_title">详细信息</div>
			<table id="datashow" class="font_table" >
				<tr><th class="class_td1">变更事项:</th><th class="class_td2" id="row1"></th><th class="class_td4">变更日期:</th><th id="row2" class="class_td5"></th></tr>
				<tr><th class="class_td1">变更前内容:</th><th class="class_td2" id="row3"></th><th class="class_td4">变更后内容:</th><th class="class_td5" id="row4"></th></tr>
				<tr><th class="class_td1">变更次数:</th><th class="class_td2" id="row5"/><th class="class_td4">操作者:</th><th class="class_td5" id="row6"></th></tr>
				<tr><th class="class_td1">操作日期:</th><th class="class_td2" id="row7"/><th class="class_td4">变更状态:</th><th class="class_td5" id="row8"></th></tr>
				<tr><th class="class_td1">详细内容:</th><th class="class_td2" id="row9" colspan="3"></th></tr>
			</table>
	</div>
</body>
</html>