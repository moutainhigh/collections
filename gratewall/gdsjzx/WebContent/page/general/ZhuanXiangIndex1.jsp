<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>违法广告</title>
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
$(function(){
	$('#panel_1').panel('open');
	$('#panel_2').panel('open');
	$('#panel_3').panel('close');
})

function reset() {
	$("#formpanel").formpanel('reset');
}
function queryUrl() {
	$("#zzjgGrid").gridpanel("hideColumn", "approvedate");
		$("#zzjgGrid").gridpanel("showColumn", "modfydate");
	$('#zzjgGrid').gridpanel('option', 'dataurl',rootPath+
			'/query/list.do');
	$('#zzjgGrid').gridpanel('query', [ 'formpanel']);
}

function openpanel(){
		$('#panel_2').panel('close');
		$('#panel_3').panel('close');
		$('#panel_1').panel('open');
	}
function openpanel1(){
		$('#panel_1').panel('close');
		$('#panel_3').panel('close');
		$('#panel_2').panel('open');
	}
function openpanel2(){
		$('#panel_1').panel('close');
		$('#panel_2').panel('close');
		$('#panel_3').panel('open');
	}
	function rowclick(event,data){
	window.location.href="<%=request.getContextPath()%>/page/general/query-panel-right.jsp?priPid="+data.pripid;
}

	
    /* $(function($){
        //调整父页面iframe高度
        try{
            parent.setWinHeight(325);           
        }catch(e){
            jazz.log(e);
        }
    }); */
</script>
<style>
body{
	overflow-x: hidden;
}
</style>
</head>


<body> 
		<!-- <div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div name="query_button" vtype="button" text="查询" 
				icon="../query/queryssuo.png" onclick="openpanel();"></div>
			<div name="reset_button" vtype="button" text="重置"
				icon="../query/queryssuo.png" click="closepanel();" ></div>
				<div name="query_button1" vtype="button" text="查询1" 
				icon="../query/queryssuo.png" onclick="openpanel1();"></div>
			<div name="reset_button1" vtype="button" text="重置1"
				icon="../query/queryssuo.png" click="closepanel1();" ></div>
				<div name="query_button12" vtype="button" text="查询2" 
				icon="../query/queryssuo.png" onclick="openpanel2();"></div>
			<div name="reset_button2" vtype="button" text="重置2"
				icon="../query/queryssuo.png" click="closepanel2();" ></div>
		</div> -->
	<!-- 	<button type="button" onclick="openpanel()">open</button>
	<button type="button" onclick="closepanel()">close</button> -->
		<div id="panel_1" vtype="panel" name="panel_1" closable="true" 
		width="16%" height="60%" frameurl="wf_left_tree.jsp" style="padding:10px;float:left;border:1px solid red;margin-top:30px"></div>
		<!-- <div id="panel_2" vtype="panel" name="panel_2" closable="true" 
		width="78%" height="100%" frameurl="wf_detail_right.jsp" style="float:right;border:1px solid red;" ></div> -->
		<!-- <div id="panel_3" vtype="panel" name="panel_3" closable="true" 
		width="100%" height="1024" frameurl="WeiFaGangGaoiInfo.jsp">  </div>
	 -->
	<iframe name="frame_panel" width="80%" height=100% style="float:right"></iframe>
 
</body>

</html>