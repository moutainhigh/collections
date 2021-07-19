<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>企业设立登记</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script>
$(function(){
		var ejzzxsfx={
					tabid: 'ejzzxsfx',	
			 		tabtitle: '组织形式分析',	
			 		tabindex: '1',	
			 		taburl: 'qysldj_zzxsfx_ejzzxsfx.jsp?at=${param.at}&bt=${param.bt}'
		};
		var qsgsfx = {
			 		tabid: 'qsgsfx',	
			 		tabtitle: '全省各市和组织形式分析',	
			 		tabindex: '2',
			 		taburl: 'qysldj_zzxsfx_qsgsfx.jsp?at=${param.at}&bt=${param.bt}'
			 };
			 
		var zzhcyfx = {
			 		tabid: 'zzhcyfx',	
			 		tabtitle: '组织形式和产业分析',	
			 		tabindex: '3',
			 		taburl: 'qysldj_zzxsfx_zzhcyfx.jsp?at=${param.at}&bt=${param.bt}'
			 };
		
		$("#tab_name").tabpanel("addTab",ejzzxsfx);
		$("#tab_name").tabpanel("addTab",qsgsfx);
		$("#tab_name").tabpanel("addTab",zzhcyfx);
		$("#tab_name").tabpanel('select',0);
		
		$('#TabUl li a').click(function(e){
				//$('#tab_name').tabpanel('getActiveIndex');
			/* 	var i=$('#tab_name').tabpanel('getActiveIndex');
			 if(i==0){
					$("iframe[src='serviceEditSjzx.jsp']")[0].contentWindow.checkArea();
				}else{
					$("iframe[src='serviceEditTgf.jsp']")[0].contentWindow.checkArea();
			} */
		});
	});
	
	
</script>
</head>
<body>
	<div id="demo">
		<div name="tab_name" vtype="tabpanel" overflowtype="2" tabtitlewidth="180"  width="100%" height="600" 
		layout="fit"  orientation="top" id="tab_name" >     
		    <ul id="TabUl">    
		        <li >
		        </li>
		    </ul>    
		    <div>    
		        <div id="tab1">   
		    	</div>    
		</div>
	</div>
</body>

</html>