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
		var industryAna={
					tabid: 'industryAna',	
			 		tabtitle: '广东省产业分类情况',	
			 		tabindex: '1',	
			 		taburl: '../qysldj/industryAna.jsp?at=1'
		};
		
		var industryAna_QYLX={
				tabid: 'industryAna_QYLX',	
		 		tabtitle: '产业、企业类型关联分析',	
		 		tabindex: '2',	
		 		taburl: '../qysldj/industryAnaWithEnters.jsp?at=1'
		};
	
		$("#tab_name").tabpanel("addTab",industryAna);
		$("#tab_name").tabpanel("addTab",industryAna_QYLX);
		$('#tab_name').tabpanel('select', 0);
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