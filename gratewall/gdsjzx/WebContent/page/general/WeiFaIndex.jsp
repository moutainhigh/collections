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
/* $(function(){
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
 */
	
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
		<div>位置:通用查询>广告监管信息>违法广告</div>
		<div id="demo">
		<div name="tab_name" vtype="tabpanel" overflowtype="2" tabtitlewidth="130"  width="100%" height="1240" 
		layout="fit"  orientation="top" id="tab_name" >     
		    <ul>    
		        <li></li>    
		    </ul>    
		    <div height="auto">    
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
	var pmggdj = {
		 		tabid: 'pmggdj',	
		 		tabtitle: '平面广告登记',	
		 		tabindex: '1',	
		 		taburl: 'wf_pmggdjInfo.jsp'
		 };
	var wlwfgg = {
		 		tabid: 'wlwfgg',	
		 		tabtitle: '网络违法广告',	
		 		tabindex: '2',	
		 		taburl: 'wf_wlwfgg.jsp'
		 };	
	var dswfgg = {
		 		tabid: 'dswfgg',	
		 		tabtitle: '电视违法广告',	
		 		tabindex: '3',	
		 		taburl: 'wf_dswfgg.jsp'
		 };
	
	var gbwfgg = {
		 		tabid: 'gbwfgg',	
		 		tabtitle: '广播违法广告',	
		 		tabindex: '4',	
		 		taburl: 'wf_wlwfgg.jsp'
		 }; 
	$("#tab_name").tabpanel("addTab",pmggdj);
	$("#tab_name").tabpanel("addTab",wlwfgg);
	$("#tab_name").tabpanel("addTab",dswfgg);
	$("#tab_name").tabpanel("addTab",gbwfgg);
}
$(function(){
	test();
})
</script>
</body>

</html>