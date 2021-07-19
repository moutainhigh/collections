<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>审批查询</title>
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

body{
	overflow-x: hidden;
}
</style>
<script type="text/javascript">

function test(){
			var gdyspsp = {
		 		tabid: 'gdyspsp',	
		 		tabtitle: '固定印刷品审批',	
		 		tabindex: '1',	
		 		taburl: 'sp_gdyspsp.jsp'
		 };
			var ycggsp = {
		 		tabid: 'ycggsp',	
		 		tabtitle: '烟草广告审批',	
		 		tabindex: '2',	
		 		taburl: 'sp_ycggsp.jsp'
		 };	
		var hwggsp = {
		 		tabid: 'hwggsp',	
		 		tabtitle: '户外广告审批',	
		 		tabindex: '3',	
		 		taburl: 'sp_hwggsp.jsp'
		 };	
		 	var jyzgsp = {
		 		tabid: 'jyzgsp',	
		 		tabtitle: '经营资格审批',	
		 		tabindex: '4',	
		 		taburl: 'sp_jyzgsp.jsp'
		 };	
		 	var wzzgggqysp = {
		 		tabid: 'wzzgggqysp',	
		 		tabtitle: '外商资格广告企业审批',	
		 		tabindex: '5',	
		 		taburl: 'sp_wzzgggqysp.jsp'
		 };	
		 	
		 
	
	$("#tab_name").tabpanel("addTab",gdyspsp);
	$("#tab_name").tabpanel("addTab",ycggsp);
	$("#tab_name").tabpanel("addTab",hwggsp);
	$("#tab_name").tabpanel("addTab",jyzgsp);
	$("#tab_name").tabpanel("addTab",wzzgggqysp);
}
$(function(){
	test();
})
</script>
</head>


<body> 
<div>位置:通用查询>广告监管信息>审批查询</div>
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

</body>

</html>