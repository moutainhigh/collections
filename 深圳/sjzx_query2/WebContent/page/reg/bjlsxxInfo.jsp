<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>标记信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/sczt/sczt.js" type="text/javascript" ></script>

<style>
	.class_td1{
			width:15%;
			height:5%;
			padding:4px;
	}
	.class_td2{
			width:35%;
			height:5%;
	}
	
	.class_td3{
			width:2%;
			height:5%;
			padding:4px;
	}
	
	.class_td4{
			width:15%;
			height:5%;
			padding:4px;
	}
	
	.class_td5{
			width:35%;
			height:5%;
	}
	.class_hg{
		height:5%;
		padding:4px;
	}
</style>
<script type="text/javascript">
	function handler(){
						if($('.jazz-grid-row').length!=0){
							$('.jazz-grid-row').dblclick( function () {
									//$("#formpanel").closeshow();
									var tds=$(this).children("td");
									for(var i=0;i<tds.length;i++){							
										$('#row'+i).text("");
										$('#row'+i).text($(tds[i]).html());
										//alert($(tds[i]).html());
								 		}
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
<div vtype="gridpanel" name="jbxxGrid" height="100" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()"
			selecttype="1"	titledisplay="true" title="标记信息"    layout="fit" showborder="false" isshowselecthelper="false"  selecttype="2">
	<!-- 表头 -->
	<div vtype="gridcolumn" name="grid_column" width="100%">
		<div> 
		<!-- 标记信息 -->
		<div name='scztbjxxid' text="替换数据" textalign="center"  width="10%"></div>
		<div name='isadcorp' text="是否广告企业" textalign="center"  width="10%"></div>
		<div name='isbrandprintcor' text="是否商标印制企业" textalign="center"  width="10%"></div>
		<div name='isbroker' text="是否经纪人" textalign="center"  width="10%"></div>
		<div name='ischangeentityt' text="是否改制" textalign="center"  width="10%"></div>
		<div name='ischangefromgth' text="标志该企业是否由个体户升级而来" textalign="center"  width="10%"></div>
		<div name='ischecklicence' text="是否验照" textalign="center"  width="10%"></div>
		<div name='isinvestcompany' text="是否投资性公司" textalign="center"  width="10%"></div>
		<div name='isruralbroker' text="是否农村经纪人" textalign="center"  width="10%"></div>
		<div name='issecrecy' text="是否保密" textalign="center"  width="10%"></div>
		<div name='issmallmicro' text=" 是否小微" textalign="center"  width="10%"></div>
		<div name='isstockmerger' text="是否股权并购" textalign="center"  width="10%"></div>
		<div name='pripid' text="主体身份代码" textalign="center"  width="10%"></div>
		<div name='isurban' text="城乡标志（是否城镇）" textalign="center"  width="10%"></div>
		<div name='perilindustry' text="高危行业" textalign="center"  width="10%"></div>
		<div name='stockpurchase' text="是否A股并购" textalign="center"  width="10%"></div>
		<div name='timestamp' text="时间戳" textalign="center"  width="10%"></div>

		
		<!-- 市场主体隶属信息 -->
		
		<div name='scztlsxxid' text="替换数据" textalign="center"  width="10%"></div>
		<div name='entname' text="企业(机构)名称" textalign="center"  width="10%"></div>
		<div name='regno' text="注册号" textalign="center"  width="10%"></div>
		<div name='pripid' text="主体身份代码" textalign="center"  width="10%"></div>
		<div name='addr' text="住所" textalign="center"  width="10%"></div>
		<div name='domdistrict' text="隶属企业住所行政区划" textalign="center"  width="10%"></div>
		<div name='regorg' text="登记机关" textalign="center"  width="10%"></div>
		<div name='opscoandform' text="经营范围及方式" textalign="center"  width="10%"></div>
		<div name='country' text="隶属企业国别" textalign="center"  width="10%"></div>
		<div name='entitycharacter' text="隶属企业性质" textalign="center"  width="10%"></div>
		<div name='enterprisetype' text="企业类型" textalign="center"  width="10%"></div>
		<div name='foreignname' text="外文名称" textalign="center"  width="10%"></div>
		<div name='estdate' text="成立日期" textalign="center"  width="10%"></div>
		<div name='subordinaterelation' text="隶属关系" textalign="center"  width="10%"></div>
		<div name='isbranch' text="是否分支机构" textalign="center"  width="10%"></div>
		<div name='isforeign' text="是否外来的企业" textalign="center"  width="10%"></div>
		<div name='parcomid' text="母公司主体身份代码" textalign="center"  width="10%"></div>
		<div name='operbegindate' text="隶属企业营业起始时间" textalign="center"  width="10%"></div>
		<div name='operenddate' text="隶属企业营业终止时间" textalign="center"  width="10%"></div>
		<div name='prilname' text="负责人" textalign="center"  width="10%"></div>
		<div name='tel' text="联系电话" textalign="center"  width="10%"></div>
		<div name='timestamp' text="时间戳" textalign="center"  width="10%"></div>
			
	<!-- 市场主体隶属信息补充信息 -->
	
		<div name='scztlsbcxxid' text="替换数据" textalign="center"  width="10%"></div>
		<div name='exchangerate' text="汇率" textalign="center"  width="10%"></div>
		<div name='pripid' text="主体身份代码" textalign="center"  width="10%"></div>
		<div name='parcomid' text="母公司主体身份代码" textalign="center"  width="10%"></div>
		<div name='regcap' text="注册资本" textalign="center"  width="10%"></div>
		<div name='regcapcur' text="注册资本币种" textalign="center"  width="10%"></div>
		<div name='regcapusd' text="注册资本(金)折万美元" textalign="center"  width="10%"></div>
		<div name='regcaprmb' text="注册资本(金)折人民币" textalign="center"  width="10%"></div>
		<div name='reccap' text="实收资本" textalign="center"  width="10%"></div>
		<div name='timestamp' text="时间戳" textalign="center"  width="10%"></div>
		
	</div>
	</div>
	<!-- 表格 -->
	<div vtype="gridtable" name="grid_table"></div>
	<!-- 卡片 -->
	<!-- <div vtype="gridcard" name="grid_card" width="205px" height="90px"></div> -->
	<!-- 分页 -->
	<!-- <div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div> -->
	
</div>

  	<div id="formpanel" style="display:none;background-color: #F8F9FB;width:100%;height:80%;overflow: hidden;margin:0px;padding:0px">
			<center>
			<table id="datashow" style="margin-top:5%;font-family:'微软雅黑', 'SimSun', 'helvetica', 'arial', 'sans-serif'">
				<tr><th ><input type="button" onclick="closeshow()" value="关闭"></input></th></tr>
				<tr class="class_hg"><th class="class_td1">替换数据1:</th><th class="class_td2" id="row1"></th><th class="class_td4">替换数据2:</th><th id="row2" class="class_td5"></th></tr>
				
				<tr><th class="class_td1">替换数据1:</th><th class="class_td2" id="row3"></th><th class="class_td4">替换数据2:</th><th class="class_td5" id="row4"></th></tr>
				<tr><th class="class_td1">替换数据1:</th><th class="class_td2" id="row5"></th><th class="class_td4">替换数据2:</th><th class="class_td5" id="row6"></th></tr>
				<tr><th class="class_td1">替换数据1:</th><th class="class_td2" id="row7"></th><th class="class_td4">替换数据2:</th><th class="class_td5" id="row8"></th></tr>
				<tr><th class="class_td1">替换数据1:</th><th class="class_td2" id="row9"></th><th class="class_td4">替换数据2:</th><th class="class_td5" id="row10"></th></tr>
				<tr><th class="class_td1">替换数据1:</th><th class="class_td2" id="row11"></th><th class="class_td4">替换数据2:</th><th class="class_td5" id="row12"></th></tr>
				<tr><th class="class_td1">替换数据1:</th><th class="class_td2" id="row13"></th><th class="class_td4">替换数据2:</th><th class="class_td5" id="row14"></th></tr>
				<tr><th class="class_td1">替换数据1:</th><th class="class_td2" id="row15"></th><th class="class_td4">替换数据2:</th><th class="class_td5" id="row16"></th></tr>
				<tr><th class="class_td1">替换数据1:</th><th class="class_td2" id="row17"></th><th class="class_td4">替换数据2:</th><th class="class_td5" id="row18"></th></tr>
				<tr><th class="class_td1">替换数据1:</th><th class="class_td2" id="row19"></th><th class="class_td4">替换数据2:</th><th class="class_td5" id="row0"></th></tr>
				
			</table>
			</center>			
	</div>
</body>
</html>