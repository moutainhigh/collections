<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title></title>
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

	var jcjg = {
		 		tabid: 'jcjg',	
		 		tabtitle: '检查结果',	
		 		tabindex: '2',	
		 		taburl: 'dataCheckResultsTable.jsp'
		 };	
	$("#tab_name").tabpanel("addTab",jcjg);
}
$(function(){
	test();
	$('#tab_name').tabpanel('select',0);
})
    function jcjg(){
		$('#tab_name').tabpanel('select',1);
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
      1、点击查看数据总量一致性检查结果查看本次检查详细结果。</div>
		 			<div name="toolbar1" vtype="toolbar">
						<div name="btn1" align="left" vtype="button" text="查看数据量检查结果" click="jcjg()"></div>
    				</div>
    				
    			<div>2、导出本次活动的检查结果到excel文件。</div>
    				<div name="toolbar2" vtype="toolbar">
						<div name="btn2" align="left" vtype="button" text="导出结果" click="jcjg()"></div>
    				</div>
    			<div>3、保存本次结果到数据库，供以后查看。</div>
    				<div name="toolbar3" vtype="toolbar">
						<div name="btn3" align="left" vtype="button" text="保存结果" click="dcjg()"></div>
    				</div>
		        </div> 
		    </div>    
		</div>
	</div>

</body>

</html>