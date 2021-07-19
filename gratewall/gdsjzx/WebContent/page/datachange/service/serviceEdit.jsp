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
<script>
$(function(){
			var update = jazz.util.getParameter("update");
			$('#serviceUpdate').val(update);
	
			var type = jazz.util.getParameter("type");
		if(update=="true"){
			var serviceidupdate = jazz.util.getParameter("serviceid");
			$('#serviceidupdate').val(serviceidupdate);
			var serviceobjectid = jazz.util.getParameter("serviceobjectid");
			$('#serviceobjectid').val(serviceobjectid);
		}
		
		var serviceedittgf={
					tabid: 'serviceEditTgf',	
			 		tabtitle: '外部提供方',	
			 		tabindex: '1',	
			 		taburl: 'serviceEditTgf.jsp'
		};
		var serviceeditsjzx = {
			 		tabid: 'serviceEditSjzx',	
			 		tabtitle: '数据中心',	
			 		tabindex: '2',
			 		taburl: 'serviceEditSjzx.jsp'
			 };
		
		$("#tab_name").tabpanel("addTab",serviceedittgf);
		$("#tab_name").tabpanel("addTab",serviceeditsjzx);
		
		
		if(type=='sjzx'){
				$('#tab_name').tabpanel('select',1);
				$('#tab_name').tabpanel('remove', 0);
		}else{
		if(type=='tgf'){
				$('#tab_name').tabpanel('select',0);
				$('#tab_name').tabpanel('remove', 1);
			}else{
				$('#tab_name').tabpanel('select',0);		
		}
	}
/* 		$('#TabUl li a').click(function(e){
				//$('#tab_name').tabpanel('getActiveIndex');
				var i=$('#tab_name').tabpanel('getActiveIndex');
			 if(i==0){
					$("iframe[src='serviceEditSjzx.jsp']")[0].contentWindow.checkArea();
				}else{
					$("iframe[src='serviceEditTgf.jsp']")[0].contentWindow.checkArea();
			}
		});
 */	});
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
	
	
	<input id="serviceUpdate" type="hidden" />
	<input id="serviceidupdate" type="hidden" />
	<input id="serviceobjectid" type="hidden" />
</body>

</html>