<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>人员信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/sczt/sczt.js"></script>

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/static/css/reg/reg.css"></link>

<script type="text/javascript">
	function rowclick(event,data){
    	var country = data["country"];//$(tds[13]).text();
    	if(country=="中国"){
    		$("tr[title=waiguo]").hide();
    		$("tr[title=gangao]").hide();
			//$(".waiguo").hide();
			//$(".gangao").hide();
		}else if (country=="香港"||country=="澳门"){
			$("tr[title=waiguo]").hide();
			$("tr[title=gangao]").show();
		}else{
			$("tr[title=waiguo]").show();
			$("tr[title=gangao]").hide();
		}
    }
	function renderdata(event, obj){
		var data = obj.data;
		for(var i=0;i<data.length;i++){
			var repcarto = data[i]["repcarto"];
			var rehomecertidvaliddate = data[i]["rehomecertidvaliddate"];
			var passescertidvaliddate = data[i]["passescertidvaliddate"];
			var hkcertidvaliddate = data[i]["hkcertidvaliddate"];
			var residenceperiod = data[i]["residenceperiod"];
			if(repcarto==undefined){
				data[i]["repcarto"] = "无限期";
			}
			if(rehomecertidvaliddate==undefined){
				data[i]["rehomecertidvaliddate"] = "无限期";
			}
			if(passescertidvaliddate==undefined){
				data[i]["passescertidvaliddate"] = "无限期";
			}
			if(hkcertidvaliddate==undefined){
				data[i]["hkcertidvaliddate"] = "无限期";
			}
			if(residenceperiod==undefined){
				data[i]["residenceperiod"] = "无限期";
			}
		}
	}
</script>
			 
				 
</head>
<body >
<div vtype="gridpanel" name="jbxxGrid" height="236" width="100%"  id='jbxxGrid' dataloadcomplete=""  datarender="renderdata()" style="Position:Reletive"
			selecttype="1"	titledisplay="true" title="人员信息" defaultview="table"  showborder="false" isshowselecthelper="false"  selecttype="2">
	<!-- 表头 -->
	<div vtype="gridcolumn" name="grid_column" width="100%">
		<div> 
		<!-- 人员基本信息 T_SCZT_RYJBXX-->
				<div name='name' text="姓名" textalign="center"  width="15%"></div>
				<div name='enname' text="英文名词" textalign="center"></div>
				<div name='cerno' text="证件号码" textalign="center"  width="20%"></div>
				<div name='certype' text="证件类型" textalign="center"  width="25%"></div>
				<div name='nation' text="民族" textalign="center" ></div>
				<div name='nativeplace' text="籍贯" textalign="center"></div>
				<div name='sex' text="性别" textalign="center" ></div>
				<div name='natdate' text="出生日期" textalign="center" ></div>
				<div name='polstand' text="政治面貌" textalign="center"  ></div>
				<div name='litedeg' text="文化程度" textalign="center"></div>
				<div name='cnposition' text="职务" textalign="center"  width="15%"></div>
				<div name='posbrform' text="职务产生方式" textalign="center"  width="13%"></div>
				<div name='country' text="国别(地区)" textalign="center" ></div>
				<div name='appounit' text="任命单位" textalign="center"  ></div>
				<div name='postalcode' text="邮政编码" textalign="center"></div>
				<div name='tel' text="联系电话" textalign="center" ></div>
				<!-- 人员其他证件 T_SCZT_RYQTZJXX-->
				<div name='homepostcode' text="家庭住所邮政编码" textalign="center" ></div>
				<div name='hometelephone' text="家庭住所联系电话" textalign="center" ></div>
				<div name='residentlocation' text="户口所在地" textalign="center" ></div>
				<div name='homeplace' text="出生地址" textalign="center"></div>
				<div name='addr' text="常住地址" textalign="center" ></div>
				<div name='house' text="居所" textalign="center" ></div>
				<div name='cerissdate' text="证件签发日期" textalign="center" ></div>
				<div name='cervalper' text="证件有效期" textalign="center" ></div>
				<div name='certapproveno' text="核准文号" textalign="center" ></div>
				<div name='certapproveorg' text="身份核证文件" textalign="center" ></div>
				<div name='certdept' text="发证机关" textalign="center"  ></div>
				<div name='certapprovedate' text="核准时间" textalign="center"></div>
				<!-- 高级成员信息 T_SCZT_GJCYXX -->
				<div name='vipsort' text="人员类别" textalign="center" ></div>
				<div name='hrdirector' text="人事部负责人" textalign="center"></div>
				<div name='hrsigndate' text="人事部签字日期" textalign="center"></div><!-- 合并 -->
				<div name='repcarfrom' text="代表证有效期自" textalign="center"></div>
				<div name='repcarto' text="代表证有效期至" textalign="center"></div>
				<!-- 法定代表人 T_SCZT_FDDBR -->	
				<div name='entitylegalrepresentative' text="委派方" textalign="center"  ></div>
				<div name='lerep' text="法定代表人" textalign="center"></div>
				<div name='islerep' text="是否法定代表人" textalign="center"  width="12%"></div>
			
			<!-- 外国 -->
			 <div name='dateofarrvalinchina' text="来华日期" textalign="center" ></div>
			<div name='residenceperiod' text="在华居住期限" textalign="center" ></div> 
			
			<!-- 港澳 -->
			<div name='hkcertid' text="特别行政区护照号" textalign="center" ></div><!-- 合并 -->
			<div name='hkcertidsigndate' text="特别行政区护照签定日期" textalign="center" ></div>
			<div name='hkcertidvaliddate' text="特别行政区护照有效期" textalign="center" ></div>
			<div name='passescertid' text="通行证号" textalign="center" ></div><!-- 合并 -->
			<div name='passescertidsigndate' text="通行证签发日期" textalign="center" ></div>
			<div name='passescertidvaliddate' text="通行证有效日期" textalign="center" ></div>
			<div name='rehomecertid' text="回乡证" textalign="center" ></div><!-- 合并 -->
			<div name='rehomecertidsigndate' text="回乡证签定日期" textalign="center" ></div>
			<div name='rehomecertidvaliddate' text="回乡证有效日期" textalign="center" ></div> 
	</div>
	</div>
	<!-- 表格 -->
	<div vtype="gridtable" name="grid_table" rowselect="rowclick()"></div>
	<div vtype="paginator" name="grid_paginator" theme="2" pagerows="10"></div>
	
</div>
	<div id="formpanel" class="formpanel_table"  >
  	<div class="font_title">详细信息</div>
			<table id="datashow" class="font_table">
				<tr class="class_hg"><th class="class_td1">姓名:</th><th class="class_td2" id="row1"></th><th class="class_td4">英文名词:</th><th id="row2" class="class_td5"></th></tr>
				<tr><th class="class_td1">证件号码:</th><th class="class_td2" id="row3"/><th class="class_td4">证件类型:</th><th class="class_td5" id="row4"></th></tr>
				<tr><th class="class_td1">民族:</th><th class="class_td2" id="row5"/><th class="class_td4">籍贯:</th><th class="class_td5" id="row6"></th></tr>
				<tr><th class="class_td1">性别:</th><th class="class_td2" id="row7"/><th class="class_td4">出生日期:</th><th class="class_td5" id="row8"></th></tr>
				<tr><th class="class_td1">政治面貌:</th><th class="class_td2" id="row9"/><th class="class_td4">文化程度:</th><th class="class_td5" id="row10"></th></tr>
				<tr><th class="class_td1">职务:</th><th class="class_td2" id="row11"/><th class="class_td4">职务产生方式:</th><th class="class_td5" id="row12"></th></tr>
				<tr><th class="class_td1">国别(地区):</th><th class="class_td2" id="row13"/><th class="class_td4">任命单位:</th><th class="class_td5" id="row14"></th></tr>
				<tr><th class="class_td1">邮政编码:</th><th class="class_td2" id="row15"/><th class="class_td4">联系电话:</th><th class="class_td5" id="row16"></th></tr>
				<tr><th class="class_td1">家庭住所邮政编码:</th><th class="class_td2" id="row17"/><th class="class_td4">家庭住所联系电话:</th><th class="class_td5" id="row18"></th></tr>
				<tr><th class="class_td1">户口所在地:</th><th class="class_td2" id="row19"/><th class="class_td4">出生地址:</th><th class="class_td5" id="row20"></th></tr>
				<tr><th class="class_td1">常住地址:</th><th class="class_td2" id="row21"/><th class="class_td4">居所:</th><th class="class_td5" id="row22"></th></tr>
				<tr><th class="class_td1">证件签发日期:</th><th class="class_td2" id="row23"/><th class="class_td4">证件有效期:</th><th class="class_td5" id="row24"></th></tr>
				<tr><th class="class_td1">核准文号:</th><th class="class_td2" id="row25"/><th class="class_td4">身份核证文件:</th><th class="class_td5" id="row26"></th></tr>
				<tr><th class="class_td1">发证机关:</th><th class="class_td2" id="row27"/><th class="class_td4">核准时间:</th><th class="class_td5" id="row28"></th></tr>
				<tr><th class="class_td1">人员类别:</th><th class="class_td2" id="row29"/><th class="class_td4">人事部负责人:</th><th class="class_td5" id="row30"></th></tr>
				<tr><th class="class_td1">人事部签字日期:</th><th class="class_td2" id="row31" colspan="3"/></tr>
				<tr><th class="class_td1">代表证有效期自:</th><th class="class_td2" id="row32"/><th class="class_td4">代表证有效期至:</th><th class="class_td5" id="row33"></th></tr>
				
				<tr><th class="class_td1">委派方:</th><th class="class_td2" id="row34"/><th class="class_td4">法定代表人:</th><th class="class_td5" id="row35"></th></tr>
				
				<tr class="waiguo" title="waiguo"><th class="class_td1">来华日期:</th><th class="class_td2" id="row37"/><th class="class_td4">在华居住期限:</th><th class="class_td5" id="row38"></th></tr>
				
				<tr class="gangao" title="gangao"><th class="class_td1">特别行政区护照号:</th><th class="class_td2" id="row39" colspan="3"/></tr>
				<tr class="gangao" title="gangao"><th class="class_td1">特别行政区护照签定日期:</th><th class="class_td2" id="row40"/><th class="class_td4">特别行政区护照有效期:</th><th class="class_td5" id="row41"></th></tr>
				<tr class="gangao" title="gangao"><th class="class_td1">通行证号:</th><th class="class_td2" id="row42" colspan="3"/></tr>
				<tr class="gangao" title="gangao"><th class="class_td1">通行证签发日期:</th><th class="class_td2" id="row43"/><th class="class_td4">通行证有效日期:</th><th class="class_td5" id="row44"></th></tr>
				<tr class="gangao" title="gangao"><th class="class_td1">回乡证:</th><th class="class_td2" id="row45" colspan="3"/></tr>
				<tr class="gangao" title="gangao"><th class="class_td1">回乡证签定日期:</th><th class="class_td2" id="row46"/><th class="class_td4">回乡证有效日期:</th><th class="class_td5" id="row47"></th></tr>
			</table>
	</div>
</body>
</html>