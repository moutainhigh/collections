<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>注吊销信息</title>
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
	};
	
	function handler(){
     	// alert($('.jazz-grid-row').length);
		if($('.jazz-grid-row').length!=0){
			$('.jazz-grid-row').click(function () {
				//$("#formpanel").closeshow();
				var tds=$(this).children("td");
				console.log(tds);
				for(var i=0;i<tds.length;i++){
					//alert($(tds[i]).text());
					//alert($(tds[i]).html());
					$('#row'+i).text("");
					if($(tds[i]).html()=="&nbsp;"){
						$('#row'+i).text("");
					}else{	
						$('#row'+i).text($(tds[i]).html());
					}
			 	}
				$("#formpanel").show(); 
			 });
		}else{
			i=i+100;
			$(timedata());
		}
	}
</script>
				 
</head>
<body>
<div vtype="gridpanel" name="jbxxGrid" height="236" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()"
			selecttype="1"	titledisplay="true" title="注吊销信息"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<!-- 表头 -->
	<div vtype="gridcolumn" name="grid_column" width="100%">
		<div> 
		<!-- 注吊销信息 -->
			<div name='sandocno' title='批准文号' text="批准文号1" textalign="center"  width="10%"></div>
			<div name='sanauth' text="批准机关" textalign="center"  width="10%"></div>
			<div name='registercert' text="登记证数" textalign="center"  width="10%"></div>
			<div name='repcert' text="代表证数" textalign="center"  width="10%"></div>
			<div name='repcarcannum' text="代表证注销数" textalign="center"></div>
			<div name='canrea' text="注销原因" textalign="center"  width="30%"></div>
			<div name='candate' text="注销日期" textalign="center"  ></div>
			<div name='sandate' text="批准日期" textalign="center"  ></div>
			<div name='debtunit' text="债务量" textalign="center"  ></div>
			<div name='handinsigndate' text="签字日期" textalign="center" width="30%"></div>
				<div name='declebrsign' text="债权债务清理情况完结标志" textalign="center"></div>
				<div name='cancelbrsign' text="非法人分支机构注销完毕标志" textalign="center"></div>
				<div name='affwritno' text="清算组成员备案确认文书编号" textalign="center"></div>
				<div name='blicrevorino' text="营业执照缴销正本编号" textalign="center"></div>
				<div name='blicrevorinum' text="营业执照缴销正本数" textalign="center"></div>
				<div name='blicrevdupconum' text="营业执照缴销副本数" textalign="center"></div>
				<div name='pubnewsname' text="公告报纸名称" textalign="center"></div>
				<div name='pubdate' text="公告日期" textalign="center"></div>
				<div name='devicechange' text="三来一补是否设备核转" textalign="center"></div>
				<div name='devicecancel' text="三来一补是否设备核销" textalign="center"></div>
				<div name='takelicenceperson' text="收照人" textalign="center"  ></div>
				<div name='handinlicenceperson' text="提交印章人" textalign="center"  ></div>
				<div name='sealdestorydesc' text="印章缴销情况描述" textalign="center"></div>
				<div name='carevst' text="公章缴销情况" textalign="center"></div>
				<div name='reopendate' text="复业时间" textalign="center"></div>
				<div name='shutoutbegindate' text="停业开始时间" textalign="center"></div>
				<div name='shutoutenddate' text="停业结束时间" textalign="center"></div>
			<div name='bizsequence' text="业务流水号" textalign="center"></div>
	</div>
	</div>
	<div vtype="gridtable" name="grid_table"></div>
</div>
  	<div id="formpanel" class="formpanel_table">
		<div class="font_title">详细信息</div>
			<table id="datashow" class="font_table">
				<tr><th class="class_td1">批准文号:</th><th class="class_td2" id="row1"></th><th class="class_td4">批准机关:</th><th id="row2" class="class_td5"></th></tr>
				<tr><th class="class_td1">登记证数:</th><th class="class_td2" id="row3"/><th class="class_td4">代表证数:</th><th class="class_td5" id="row4"></th></tr>
				<tr><th class="class_td1">代表证注销数:</th><th class="class_td2" id="row5"/><th class="class_td4">注销原因:</th><th class="class_td5" id="row6"></th></tr>
				<tr><th class="class_td1">注销日期:</th><th class="class_td2" id="row7"/><th class="class_td4">批准日期:</th><th class="class_td5" id="row8"></th></tr>
				<tr><th class="class_td1">债务量:</th><th class="class_td2" id="row9"/><th class="class_td4">签字日期:</th><th class="class_td5" id="row10"></th></tr>
				<tr><th class="class_td1">债权债务清理情况完结标志:</th><th class="class_td2" id="row11"/><th class="class_td4">非法人分支机构注销完毕标志:</th><th class="class_td5" id="row12"></th></tr>
				<tr><th class="class_td1">清算组成员备案确认文书编号:</th><th class="class_td2" id="row13"/><th class="class_td4">营业执照缴销正本编号:</th><th class="class_td5" id="row14"></th></tr>
				<tr><th class="class_td1">营业执照缴销正本数:</th><th class="class_td2" id="row15"/><th class="class_td4">营业执照缴销副本数:</th><th class="class_td5" id="row16"></th></tr>
				<tr><th class="class_td1">公告报纸名称:</th><th class="class_td2" id="row17"/><th class="class_td4">公告日期:</th><th class="class_td5" id="row18"></th></tr>
				<tr><th class="class_td1">三来一补是否设备核转:</th><th class="class_td2" id="row19"/><th class="class_td4">三来一补是否设备核销:</th><th class="class_td5" id="row20"></th></tr>
				<tr><th class="class_td1">收照人:</th><th class="class_td2" id="row21"/><th class="class_td4">提交印章人:</th><th class="class_td5" id="row22"></th></tr>
				<tr><th class="class_td1">印章缴销情况描述:</th><th class="class_td2" id="row23" colspan="3"/></tr>
				<tr><th class="class_td1">公章缴销情况:</th><th class="class_td2" id="row24"/><th class="class_td1">复业时间:</th><th class="class_td2" id="row25"/></tr>
				<tr><th class="class_td1">停业开始时间:</th><th class="class_td2" id="row26"/><th class="class_td4">停业结束时间:</th><th class="class_td5" id="row27"></th></tr>
			</table>
		</div>
</body>
</html>