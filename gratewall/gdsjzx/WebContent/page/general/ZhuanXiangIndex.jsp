<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>专项管理</title>
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
	$('#panel_2').panel('close');
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
<div>位置:通用查询>广告监管信息>专项管理</div>
		<div id="demo">
		<div name="tab_name" vtype="tabpanel" overflowtype="2" tabtitlewidth="130"  width="100%" height="800" 
		layout="fit"  orientation="top" id="tab_name" >     
		    <ul>    
		        <li></li>    
		    </ul>    
		    <div>    
		        <div id="tab1">    
		        </div> 
		    </div>    
		</div>
					   
	</div>
<script type="text/javascript">
/* $(function(){
    //调整父页面iframe高度
    try{
        parent.setWinHeight(300);           
    }catch(e){
        jazz.log(e);
    }
    //$("#tab_name").tabpanel("option","width","400");
    
}); */

function test(){
	var gyggcx = {
		 		tabid: 'gyggcx',	
		 		tabtitle: '公益广告查询',	
		 		tabindex: '1',	
		 		taburl: 'zx_gyggcx.jsp'
		 };
	var ggscygl = {
		 		tabid: 'ggscygl',	
		 		tabtitle: '广告审查员管理',	
		 		tabindex: '2',	
		 		taburl: 'zx_ggscygl.jsp'
		 };	
	
	$("#tab_name").tabpanel("addTab",gyggcx);
	$("#tab_name").tabpanel("addTab",ggscygl);
}
$(function(){
	test();
})
</script>
</body>

</html>