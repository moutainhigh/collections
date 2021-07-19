<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>执行之后进入检查结果</title>
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
    function test(){

	var aztfl = {
		 		tabid: 'aztfl',	
		 		tabtitle: '按主题分类',	
		 		tabindex: '2',	
		 		taburl: 'zxjg_aztfl.jsp'
		 };	
	var aqxfl = {
		 		tabid: 'aqxfl',	
		 		tabtitle: '按区县分类',	
		 		tabindex: '3',	
		 		taburl: 'zxjg_aqxfl.jsp'
		 };
	
	var agzlxfl = {
		 		tabid: 'agzlxfl',	
		 		tabtitle: '按规则类型分类',	
		 		tabindex: '4',	
		 		taburl: 'zxjg_agzlxfl.jsp'
		 }; 
	$("#tab_name").tabpanel("addTab",aztfl);
	$("#tab_name").tabpanel("addTab",aqxfl);
	$("#tab_name").tabpanel("addTab",agzlxfl);
}
$(function(){
	test();
	$('#tab_name').tabpanel('select',0);
})
    function ztfl(){
		$('#tab_name').tabpanel('select',1);
    }
    
    function qxfl(){
		$('#tab_name').tabpanel('select',2);
    }
    
    function gzlx(){
    	$('#tab_name').tabpanel('select',3);
    }
</script>
<style>
body{
	overflow-x: hidden;
}
</style>
</head>


<body> 

		<div id="demo">
		<div name="tab_name" vtype="tabpanel" overflowtype="2" tabtitlewidth="130"  width="1100" height="1200" 
		layout="fit"  orientation="top" id="tab_name" >     
		       
		    <ul>    
		        <li><a href="#tab1">总体概述</a></li>    
		    </ul>    
		    <div>       
		    <div>    
		        <div id="tab1">   
		        <div>  您可以做什么：<br>
      1、按【主题分类】  、【区县分类】、【规则类型】查看本次活动检查的详细结果。</div>
		 			<div name="toolbar1" vtype="toolbar">
						<div name="btn1" align="left" vtype="button" text="按主题分类查看" click="ztfl()"></div>
						<div name="btn2" align="left" vtype="button" text="按区县分类查看" click="qxfl()"></div>
						<div name="btn3" align="left" vtype="button" text="按规则类型查看" click="gzlx()"></div>
    				</div>
    				
    			<div>2、导出本次活动的检查结果到excel文件。</div>
    				<div name="toolbar2" vtype="toolbar">
						<div name="btn1" align="left" vtype="button" text="导出结果" click="dcjg()"></div>
    				</div>
    			<div>3、保存本次结果到数据库，供以后查看。</div>
    				<div name="toolbar3" vtype="toolbar">
						<div name="btn1" align="left" vtype="button" text="保存结果" click="dcjg()"></div>
    				</div>
    			<div>4、查看看检查报告预览。</div>
    				<div name="toolbar4" vtype="toolbar">
						<div name="btn1" align="left" vtype="button" text="查看报告" click="dcjg()"></div>
    				</div>
		        </div> 
		    </div>    
		</div>
	</div>

</body>

</html>