<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统日程-新增</title>
<%
	String contextpath = request.getContextPath();
%>
<script src="<%=contextpath%>/static/script/jazz.js" type="text/javascript"></script>
<style type="text/css">
.jazz-field-comp-in2{
	background-color:transparent;
}
</style>
<script type="text/javascript">
function save(){
	var params = {
		url:rootPath+"/home/saveSchedule.do", 
		components: ['formpanel'],
		callback : function(data,obj,res) {
			reload();
			leave();
		}
	};
	$.DataAdapter.submit(params);
}

function leave(){
	window.parent.leave();
}

function reload(){
	window.parent.initDate();
}

$(function(){
	var date = new Date();
	var month = date.getMonth() + 1;
	var day = date.getFullYear()+"-"+month+"-"+date.getDate();
	$("#day").datefield("setValue",day);
	$("#hour").textfield("setValue",date.getHours());
	$("#minute").textfield("setValue",date.getMinutes());
	$("#remindType").comboxfield("setValue","03");
});
</script>
</head>
<body>
   <div id="formpanel" name="formpanel" vtype="formpanel" titledisplay="false" showborder="false" width="100%" layout="table" 
	     layoutconfig="{cols:2, columnwidth: ['50%','*']}" height="100%">
   		<div name='title' id='title' vtype="textfield"  colspan="2" rowspan="1" label="日程内容" labelalign="right" labelwidth="100" width="80%" rule="must" editable="true"></div>
		<div colspan="2" rowspan="1">
			<div name='day' id='day' vtype="datefield" label="创建日期" labelalign="right" labelwidth="100" width="300" ></div>
		   	<div name='hour' id='hour' vtype="textfield" width="70" suffix="时" defaultvalue="0" rule="contrast;>=0;<=24" editable="true"></div>
		   	<div name='minute' id='minute' vtype="textfield" width="70" suffix="分" defaultvalue="0" rule="contrast;>=0;<=60" editable="true"></div>
		</div>
		<div name='effectiveTime' id='effectiveTime' vtype="datefield" colspan="2" rowspan="1" label="有效日期" labelalign="right" labelwidth="100" width="300" ></div>
		<div colspan="2" rowspan="1">
			<div name='remindValue' id='remindValue' vtype="textfield" label="提前" labelalign="right" labelwidth="100"  width="200" rule="numberPlusInt" editable="true"></div>
			<div name='remindType' id='remindType' vtype="comboxfield" suffix="提醒"  width="120" dataurl="<%=contextpath%>/home/getRemind.do" ></div>
		</div>
		<div id="toolbar" name="toolbar" vtype="toolbar" >		
	    	<div id="btn3" name="btn3" vtype="button" text="保  存" defaultview="1" align="center" click="save()"></div>
	    	<div id="btn4" name="btn4" vtype="button" text="返  回" defaultview="1" align="center" click="leave()"></div>
	    </div>
   </div>
</body>
</html>