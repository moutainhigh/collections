<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>查询sql</title>
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
function jointSql(){
	var theme=$('#theme', parent.document).val();
	var themeCN=$('#themeCN', parent.document).val();
		//alert("theme=="+theme);
		//alert("themeCN=="+themeCN);
	var checkTableName=$('#checkTableName', parent.document).val();
	var checkTableNameCN=$('#checkTableNameCN', parent.document).val();
		//alert("checkTableName=="+checkTableName);
		//alert("checkTableNameCN=="+checkTableNameCN);
	var sqlCondition=$('#sqlCondition', parent.document).val();
	var sqlConditionCN=$('#sqlConditionCN', parent.document).val();
		//alert("sqlCondition=="+sqlCondition);
		//alert("sqlConditionCN=="+sqlConditionCN);
	var selCondition=$('#selCondition', parent.document).val();
	var selConditionCN=$('#selConditionCN', parent.document).val();
		//alert("selCondition=="+selCondition);
		//alert("selConditionCN=="+selConditionCN);
	var selFields=$('#selFields', parent.document).val();
	var selFieldsCN=$('#selFieldsCN', parent.document).val();
		//alert("selFields=="+selFields);
		//alert("selFieldsCN=="+selFieldsCN);
	var tables=checkTableName.split(",");
	
	//组合中文名,数据库名,别名
	/* 	$(function(){
			var ts = String.fromCharCode( 97, 98, 99);
			alert(ts);
		}) */
	
	//var jsonFrom="{";
	/* for(var i=1;i<tables.length;i++){
	var ts = String.fromCharCode( 96+i);
		 from =from+tables[i]+" "+ts+" , ";	
		// jsonFrom=jsonFrom+tables[i]+":"+ts+",";
	}
		from = from.substring(0,from.length-1); */
		//jsonFrom=jsonFrom.substring(0,jsonFrom.length-1)+"}";
		//alert(from);
		//alert(jsonFrom);
	
	var select ="select ";
	
	selFields=selFields.substring(1,selFields.length);
	
	var from=" from ";
	
	//选择表的中文名
	var subcheckTableNameCN=checkTableNameCN.substring(1,checkTableNameCN.length);	
	//选择的表名	
	var subcheckTableName=checkTableName.substring(1,checkTableName.length);
	//连接条件         -/-/t_sczt_lsxx/1/=/t_sczt_ryxx/1/-/-/-/t_sczt_ryxx/1/=/t_sczt_ryxx/1/-/
	var conditions=sqlCondition.split('/');
	//var step=sqlCondition.split('#');

	var sqlConditions=" where ";
	for(var k=0;k<conditions.length;k++){
		if(conditions[k]=="-"){
			conditions[k]=" ";
		};
	
	if(k==2 || k==5 || k%8==2 || k%8==5){
			conditions[k]=conditions[k]+".";
			sqlConditions=sqlConditions+conditions[k];
		}else{
			sqlConditions=sqlConditions+conditions[k]+" ";
		}
	}
	
	//alert(sqlConditions);
	//连接条件中文名      -/-/隶属信息/1/等于/人员信息/1/-/-/-/人员信息/1/等于/人员信息/1/-/
	var conditionsCN=sqlConditionCN.split('/');
	var sqlConditionsCN="";
	for(var k=0;k<conditionsCN.length;k++){
		if(conditionsCN[k]=="-"){
			conditionsCN[k]=" ";
		};
		if(k==2  || k%8==2){
			conditionsCN[k]=conditionsCN[k]+".";
			sqlConditionsCN=sqlConditionsCN+conditionsCN[k];
		}else{
			sqlConditionsCN=sqlConditionsCN+conditionsCN[k]+" ";
		}
	}
	
	//查询条件
	var selcons=selCondition.split('/');
	//var step=sqlCondition.split('#');
	//alert(selCondition+"-------------");
	var selConditions=" and ";
	for(var k=0;k<selcons.length;k++){
		if(selcons[k]=="-"){
			selcons[k]=" ";
		};
	
	if(k==2 ||  k%8==2 ){
			selcons[k]=selcons[k]+".";
			selConditions=selConditions+selcons[k];
		}else{
			selConditions=selConditions+selcons[k]+" ";
		}
	}
	
	//连接条件中文名      -/-/隶属信息/1/等于/人员信息/1/-/-/-/人员信息/1/等于/人员信息/1/-/
	var selconsCN=selConditionCN.split('/');
	var selConditionsCN="";
	for(var k=0;k<selconsCN.length;k++){
		if(selconsCN[k]=="-"){
			selconsCN[k]=" ";
		};
		if(k==2 || k==5 || k%8==2 || k%8==5){
			selconsCN[k]=selconsCN[k]+".";
			selConditionsCN=selConditionsCN+selconsCN[k];
		}else{
			selConditionsCN=selConditionsCN+selconsCN[k]+" ";
		}
	}
	
	//查询结果
	var seletSql=select + selFields + from + subcheckTableName + sqlConditions + selConditions;
	//查询条数
	var countSql=select + " count(1) " + from + subcheckTableName;
	
	//alert(seletSql);
	//alert(countSql);
	
	if($('#containers').find("div").length<3){
		addDiv(themeCN,subcheckTableNameCN);
	}else{
		$('#containers').find("div:gt(0)").remove();
		addDiv(themeCN,subcheckTableNameCN);
	}
}

function addDiv(themeCN,subcheckTableNameCN){
		var themeHtml="<div class='divcolor2'><h3><b>主题:"+themeCN+"</b></h3></div>";
		$('#containers').append(themeHtml);
		var tableHtml="<div class='divcolor2'><h3 ><b>所选表的表名称:<b class='h3color1'>"+subcheckTableNameCN+"</b></b></h3></div>";
		$('#containers').append(tableHtml);
		var resultHtml="<div id='dataCount' class='divcolor2'><h3 class='h3style1'><b>查询结果  记录总数:</b></h3><h3 class='h3style2'><b>代码字段：</b></h3></div>";
		$('#containers').append(resultHtml);
}
//------------------------------修改---------------------------------
function updateyl(){
	jointSql();
}

</script>
</head>
<body>
<style>
	#containers div{
		padding-top:6px;padding-left:12px;border-bottom: 2px solid #C5D7DA
	}
	.divcolor1{
		background:#46B8E8;height:30px;
	}
	.divcolor2{
		background: #C0DBF3;height:26px;
	}
	h3{
		font-weight:bold;
	}
	.h3color1{
		color:#EF898F;
	}
	.h3style1{
		float:left;
	}
	.h3style2{
		float:right;
	}
</style>
<div id="containers">
	<div class="divcolor1"><h2>已选择数据情况</h2></div>
	<!-- <div class="divcolor2"><h3><b>企业登记主题</b></h3></div> -->
	<!-- <div class="divcolor2"><h3 class="h3color1"><b>所选表的表名称:</b></h3></div> -->
</div>

	<div vtype="gridpanel" name="zzjgGrid" height="400" width="100%"  id='zzjgGrid' datarender="renderdata()"
		titledisplay="true" title="查询列表"  dataurl="" layout="fit" showborder="false" isshowselecthelper="false" selecttype="2">
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>
				<div name='entname' text="企业名称" textalign="center"  width="34%"></div>
				<div name='regno' text="注册号" textalign="center"  width="33%"></div>
				<div name='industryco' text="市场主体类型" textalign="center"  width="33%"></div>
			</div>
		</div>
		<!-- 表格 -->
		<!-- ../../grid/reg3.json -->
		<div vtype="gridtable" name="grid_table"></div>
		<!-- 分页 -->
		<div vtype="paginator" name="grid_paginator" theme="2" pagerows="20"></div>
	</div>
</body>
</html>