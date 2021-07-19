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
		var qsgsfx={
					tabid: 'qsgsfx',	
			 		tabtitle: '全省企业及经济分布情况',	
			 		tabindex: '1',	
			 		taburl: 'qysldj_qyfx_qsgsfx.jsp?at=${param.at}&bt=${param.bt}'
		};
		var qsgshcyfx={
					tabid: 'qsgshcyfx',	
			 		tabtitle: '各地产业分布情况-产业',	
			 		tabindex: '2',	
			 		taburl: 'qysldj_qyfx_qsgshcyfx.jsp?at=${param.at}&bt=${param.bt}'
		};
		var qyhjjfx={
					tabid: 'qyhjjfx',	
			 		tabtitle: '企业及分布情况-经济性质',	
			 		tabindex: '3',	
			 		taburl: 'qysldj_qyfx_qyhjjfx.jsp?at=${param.at}&bt=${param.bt}'
		};
		$("#tab_name").tabpanel("addTab",qsgsfx);
		$("#tab_name").tabpanel("addTab",qsgshcyfx);
		$("#tab_name").tabpanel("addTab",qyhjjfx);
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