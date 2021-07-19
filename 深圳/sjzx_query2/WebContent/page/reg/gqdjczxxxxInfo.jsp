﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>股权信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/sczt/sczt_gqxx.js" type="text/javascript"></script>

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
		altRows('datashow1');
		altRows('datashow2');
	};
	function handler(){
     	// alert($('.jazz-grid-row').length);
						if($('.jazz-grid-row').length!=0){
							$('.jazz-grid-row').click( function () {
									//$("#formpanel").closeshow();
									var tds=$(this).children("td");
									
									if($(tds).length>20){
									//alert($(tds).length+"oooo")
										for(var i=0;i<tds.length;i++){							
										$('#djrow'+i).text("");
										$('#djrow'+i).text($(tds[i]).text());
											//alert(tds[i].html());
								 			}
										$("#formpanel1").show(); 
									}else{
									//alert($(tds).length+"o")
										for(var i=0;i<tds.length;i++){							
										$('#czrow'+i).text("");
										$('#czrow'+i).text($(tds[i]).text());
										//alert(tds[i].html());
								 		}
									$("#formpanel2").show(); 									
									}
									
							 		});
								 }else{
									i=i+100;
									//alert(i);
									$(timedata());
								}
							 }
				  function closeshow2(){
				  	$('#jbxxGrid2').show();
					 $("#formpanel2").hide();
				 }   
				 function closeshow1(){
				  	$('#jbxxGrid1').show();
					 $("#formpanel1").hide();
				 }  
				 
				 function renderdata(event, obj){
						var data = obj.data;
						for(var i=0;i<data.length;i++){
							var sharfroprop = data[i]["sharfroprop"];
							data[i]["sharfroprop"] = sharfroprop+'%';
							var froam = data[i]["froam"];
							data[i]["froam"] = froam+'万元';
						}
						return data;
					}
</script>
				 
</head>
<body>
<div vtype="gridpanel" name="jbxxGrid1" height="236" width="100%"  id='jbxxGrid1' dataloadcomplete=""  datarender="renderdata()"
			selecttype="1"	titledisplay="true" title="股权冻结信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="100%">
		<div> 
			 <div name='investinfo' text="股东信息" textalign="center"  width="20%"></div>
			 <div name='frodocno' text="冻结文号" textalign="center"  width="20%"></div>
			 <div name='freezeitem' text="冻结事项" textalign="center"  width="20%"></div>
			 <div name='froam' text="冻结金额" textalign="center" width="10%"></div>
			 <div name='sharfroprop' text="冻结比例" textalign="center"  width="7%"></div>
			 <div name='froauth' text="冻结机关" textalign="center"  width="15%"></div>
				<div name='frofrom' text="冻结起始日期" textalign="center"></div>
				<div name='froto' text="冻结截止日期" textalign="center"></div>
			<div name='frozsign' text="冻结标志" textalign="center" width="8%"></div>
			 <div name='thawdocno' text="解冻文号" textalign="center" ></div>
			 <div name='thawgist' text="解冻依据" textalign="center" ></div>
			 <div name='thawauth' text="解冻机关" textalign="center" ></div>
			 <div name='thawdate' text="解冻日期" textalign="center" ></div>
				<div name='corentname' text="相关企业名称" textalign="center"></div>
				<div name='exestate' text="执行状态" textalign="center"></div>
				<div name='historyinfoid' text="业务历史编号" textalign="center" ></div>
	</div>
	</div>
	<div vtype="gridtable" name="grid_table"></div>
</div>
	<div id="formpanel2" class="formpanel_table" style="height:300px;margin-bottom:15px;">
			<div class="font_title">详细信息</div>
			<table id="datashow2" class="font_table" >
				<tr class="class_hg"><th class="class_td1">股东信息:</th><th class="class_td2" id="czrow1"></th><th class="class_td4">冻结文号:</th><th id="czrow2" class="class_td5"></th></tr>
				<tr><th class="class_td1">冻结事项:</th><th class="class_td2" id="czrow3" colspan="3"></th></tr>
				<tr><th class="class_td1">冻结金额:</th><th class="class_td2" id="czrow4"></th><th class="class_td4">冻结比例:</th><th class="class_td5" id="czrow5"></th></tr>
				<tr><th class="class_td1">冻结机关:</th><th class="class_td2" id="czrow6"></th><th class="class_td4">冻结标志:</th><th class="class_td5" id="czrow9"></th></tr>
				<tr><th class="class_td1">冻结起始日期:</th><th class="class_td2" id="czrow7"></th><th class="class_td4">冻结截止日期:</th><th class="class_td5" id="czrow8"></th></tr>
				<tr><th class="class_td1">解冻文号:</th><th class="class_td2" id="czrow9"></th><th class="class_td4">解冻依据:</th><th class="class_td5" id="czrow10"></th></tr>
				<tr><th class="class_td1">解冻机关:</th><th class="class_td2" id="czrow11"></th><th class="class_td4">解冻日期:</th><th class="class_td5" id="czrow12"></th></tr>
				<tr><th class="class_td1">相关企业名称:</th><th class="class_td2" id="czrow13"></th><th class="class_td4">执行状态:</th><th class="class_td5" id="czrow14"></th></tr>
			</table>
	</div>

<div vtype="gridpanel" name="jbxxGrid2" height="236" width="100%"  id='jbxxGrid2' dataloadcomplete=""  datarender="renderdata()"
				titledisplay="true" title="股权出质信息" showborder="false" isshowselecthelper="false"  selecttype="2">
	<div vtype="gridcolumn" name="grid_column" width="100%">
		<div> 
			 <div name='regno' text="注册号" textalign="center" width="12%"></div>
			 <div name='entname' text="股权所在公司名称" textalign="center" width="14%"></div>
			 <div name='stockregisterno' text="股权出质登记号" textalign="center"  width="12%"></div>
			 <div name='pledgor' text="出质人" textalign="center"  width="20%"></div>
			 	<div name='blicno' text="出质人证照号码" textalign="center"></div>
			 	<div name='blictype' text="出质人证照类型" textalign="center" ></div>
			 	<div name='cerno' text="出质人证件号码" textalign="center"></div>
				<div name='certype' text="出质人证件类型" textalign="center" ></div>
			
			<div name='imporg' text="质权人" textalign="center"  width="20%"></div>
			<div name='imporgtel' text="质权人联系方式" textalign="center" ></div>
			<div name='zqrblicno' text="质权人证照号码" textalign="center"  ></div>
			<div name='zqrblictype' text="质权人证照类型" textalign="center" ></div>
			<div name='zqrcerno' text="质权人证件号码" textalign="center"  ></div>
			<div name='zqrcertype' text="质权人证件类型" textalign="center" ></div>
			
			<div name='pledamunit' text="出质股权数" textalign="center" ></div>
			<div name='impam' text="出质股权数额单位" textalign="center"></div>
			<div name='applydate' text="受理日期" textalign="center" ></div>
			<div name='approvedate' text="核准日期" textalign="center" ></div>
			<div name='regstatus' text="登记状态" textalign="center"  width="8%"></div>
			<div name='regorg' text="登记机关" textalign="center"  width="14%"></div>
			<div name='rescindopinion' text="解除原因" textalign="center" ></div>
			
			<div name='historyinfoid' text="业务历史编号" textalign="center"></div>
			<div name='stockpledgehostexclusiveid' text="出质人的唯一码" textalign="center" ></div>
	</div>
	</div>
	<div vtype="gridtable" name="grid_table"></div>
</div>
  <div id="formpanel1" class="formpanel_table" style="height:250px;">
			<div class="font_title">详细信息</div>
			<table id="datashow1" class="font_table" >
				<tr class="class_hg"><th class="class_td1">注册号:</th><th class="class_td2" id="djrow1"></th><th class="class_td4">股权所在公司名:</th><th id="djrow2" class="class_td5"></th></tr>
				<tr><th class="class_td1">股权出质登记号:</th><th class="class_td2" id="djrow3"/><th class="class_td4">出质人:</th><th class="class_td5" id="djrow4"></th></tr>
				<tr><th class="class_td1">出质人证照号码:</th><th class="class_td2" id="djrow5"/><th class="class_td4">出质人证照类型:</th><th class="class_td5" id="djrow6"></th></tr>
				<tr><th class="class_td1">出质人证件号码:</th><th class="class_td2" id="djrow7"/><th class="class_td4">出质人证件类型:</th><th class="class_td5" id="djrow8"></th></tr>
				
				<tr><th class="class_td1">质权人:</th><th class="class_td2" id="djrow9"/><th class="class_td4">质权人联系方式:</th><th class="class_td5" id="djrow10"></th></tr>
				<tr><th class="class_td1">质权人证照号码:</th><th class="class_td2" id="djrow11"/><th class="class_td4">质权人证照类型:</th><th class="class_td5" id="djrow12"></th></tr>
				<tr><th class="class_td1">质权人证件号码:</th><th class="class_td2" id="djrow13"/><th class="class_td4">质权人证件类型:</th><th class="class_td5" id="djrow14"></th></tr>
				
				<tr><th class="class_td1">出质股权数:</th><th class="class_td2" id="djrow15"/><th class="class_td4">出质股权数单位:</th><th class="class_td5" id="djrow16"></th></tr>
				<tr><th class="class_td1">受理日期:</th><th class="class_td2" id="djrow17"/><th class="class_td4">核准日期:</th><th class="class_td5" id="djrow18"></th></tr>
				<tr><th class="class_td1">登记状态:</th><th class="class_td2" id="djrow19"/><th class="class_td4">登记机关:</th><th class="class_td5" id="djrow20"></th></tr>
				<tr><th class="class_td4">解除原因:</th><th class="class_td5" id="djrow21" colspan="3"></th></tr>
			</table>
	</div>
	
	
	
</body>
</html>