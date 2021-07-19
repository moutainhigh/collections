<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC>
<html>
<head>
<title>市场主体概况</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script>
$(function(){
		var jjxzfx={
					tabid: 'jjxzfx',	
			 		tabtitle: '经济性质分析',	
			 		tabindex: '1',	
			 		taburl: '../qysldj/economicByIndus.jsp?at=1'
		};
		var qsgshcyfx={
					tabid: 'qsgshcyfx',	
			 		tabtitle: '区域各市和产业分析',	
			 		tabindex: '2',	
			 		taburl: '../qysldj/qysldj_qyfx_qsgshcyfx.jsp?at=1'
		};
		var qyhjjfx={
					tabid: 'qyhjjfx',	
			 		tabtitle: '区域各市和产业分析',	
			 		tabindex: '3',	
			 		taburl: '../qysldj/qysldj_qyfx_qyhjjfx.jsp?at=1'
		};
		
		var qsgsjjxzfx={
				tabid: 'qsgsjjxzfx',	
		 		tabtitle: '全省各市和经济性质分析',	
		 		tabindex: '4',	
		 		taburl: '../qysldj/allProvinceAndEconomic.jsp?at=1'
		};
		var jjxzhcy={
				tabid: 'jjxzhcy',	
		 		tabtitle: '经济性质和产业分析',	
		 		tabindex: '5',	
		 		taburl: '../qysldj/economicAndIndustryco.jsp?at=1'
		};
		$("#tab_name").tabpanel("addTab",jjxzfx);
		$("#tab_name").tabpanel("addTab",qsgshcyfx);
		$("#tab_name").tabpanel("addTab",qyhjjfx);
		$("#tab_name").tabpanel("addTab",qsgsjjxzfx);
		$("#tab_name").tabpanel("addTab",jjxzhcy);
		
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