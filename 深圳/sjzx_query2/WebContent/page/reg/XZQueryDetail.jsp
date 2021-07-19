<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<head>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>企业限制详细信息</title>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script>
$(window).load(function() {
	var id = getUrlParam("id");
	
	$('div[name="formpanel"]').formpanel('option','dataurl',rootPath+
			'/reg/xzdetailform.do?id='+id);
	$('div[name="formpanel"]').formpanel('reload');
	
	$('#jbxxGrid').gridpanel(
			'option',
			'dataurl',
			rootPath + '/reg/xzdetaillist.do?id='+id
	);
	$('#jbxxGrid').gridpanel('query', [ 'gridpanel' ]);
	
});

function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; // 返回参数值
}

function fixColumn(){
	
}
</script>
<style>
/*文字超出不换行*/
table td{word-break: keep-all;white-space:nowrap;}
.jazz-form-text-label{
	color: #000;
	font-weight: normal;
}
</style>
</head>
<body>
 	<div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" height="35%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['50%','*']}">
        	 <!-- <div id="limituser" name="limituser" vtype="textfield" label="申请人" labelwidth="150" labelalign="right" width="90%" readonly="true"></div>
		 	 <div id="hzuser" name="hzuser" vtype="textfield" label="核准人" labelwidth="150" labelalign="right" width="90%" readonly="true"></div>
			 <div id="hzunit" name="hzunit" vtype="textfield" label="核准单位" labelwidth="150" labelalign="right" width="90%" readonly="true"></div>
		 	 <div id="hztime" name="hztime" vtype="datefield" dataformat="YYYY-MM-DD" label="核准时间" labelwidth="150" labelalign="right" width="80%"  readonly="true"></div>
		 	  -->
		 	 <div id="operator" name="operator" vtype="textfield" label="经办人" labelwidth="150" labelalign="right" width="90%" readonly="true"></div>
		 	 <div id="supunitbysup" name="supunitbysup" vtype="textfield" label="所属辖区" labelwidth="150" labelalign="right" width="90%" readonly="true"></div>
		 	 <div id="supdepbysup" name="supdepbysup" vtype="textfield" label="所属科/所" labelwidth="150" labelalign="right" width="90%" readonly="true"></div>
		 	 <div id="gridname" name="gridname" vtype="textfield" label="所属网格" labelwidth="150" labelalign="right" width="90%" readonly="true"></div>
		 	 <div id="issetlimit" name="issetlimit" vtype="textfield" label="限制状态" labelwidth="150" labelalign="right" width="90%" readonly="true"></div>
		 	 
		 	 <!--  <div id="xzlb" name="xzlb" vtype="textfield" label="限制类别" labelwidth="150" labelalign="right" width="90%" rowspan="1" colspan="2" readonly="true"></div> -->
		 	 <div id="remarks" name="remarks" vtype="textfield" label="备注信息" labelwidth="150" labelalign="right" width="90%" rowspan="1" colspan="2" readonly="true"></div>
    </div>
	<div vtype="gridpanel" name="jbxxGrid" height="65%" width="100%"
		id='jbxxGrid' dataloadcomplete="" datarender="fixColumn()"
		style="Position: Reletive" selecttype="1" titledisplay="true"
		title="限制详细信息" defaultview="table" showborder="false"
		isshowselecthelper="false" selecttype="2">
		<!-- 表头 -->
		<div vtype="gridcolumn" name="grid_column" width="100%">
			<div>
				<div name='limityycode' text="限制原因" textalign="center" width="25%" dataurl="limityycode.json"></div>
				<div name='limititemcode' text="限制项目" textalign="center" width="20%" dataurl="limititemcode.json"></div>
				<div name='jcfs' text="解除方式" textalign="center" width="8%" dataurl="jcfs.json"></div>
				<div name='limittimebegin' text="限制起始时间" datatype="date" dataformat="YYYY-MM-DD" textalign="center" width="10%" dataurl=""></div>
				<div name='limittimeend' text="限制结束时间" datatype="date" dataformat="YYYY-MM-DD" textalign="center" width="10%" dataurl=""></div>
				<div name='limitstate' text="限制状态" textalign="center" width="7%" dataurl="limitstate.json"></div>
                <div name='memo' text="限制说明" textalign="center" width="20%"></div>

			</div>
		</div>
		<!-- 表格 -->
		<div vtype="gridtable" name="grid_table" id="grid_table" rowrender=""></div>
		<!-- 卡片 -->
		<!-- <div vtype="gridcard" name="grid_card" width="205px" height="90px"></div> -->
		<!-- 分页 -->
		<!-- <div vtype="paginator" name="grid_paginator" ></div> -->

	</div>
    
    <script type="text/javascript">
    	$(function(){
    		 $("tr:even").css({background:"#EFF6FA"});
    		 $("tr:odd").css({background:"#FBFDFD"});
    	})
    
    </script> 
</body>
</html>
