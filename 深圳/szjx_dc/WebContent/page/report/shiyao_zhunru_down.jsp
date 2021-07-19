<%@ page contentType="text/html; charset=utf-8" language="java"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title></title>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<script
	src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js"
	type="text/javascript"></script>

<script	src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<style>
body{
 font-family: "微软雅黑";
}
/* td{
	border:1px solid #000;
}

tr:hover{background:#ccc;} */


table{border-collapse: collapse;width:100%; border:1px solid #E1E6EB; border-left:none;width:1024px}
table thead th{line-height:20px;padding:8px 12px; border-bottom:1px solid #E1E6EB; border-left:1px solid #E1E6EB; white-space: nowrap; text-align:center; font-weight:normal !important;letter-spacing:1px;}
table tbody td{text-align: center;line-height:20px;padding:8px 10px;font-size:13px;border-bottom:1px solid #E1E6EB; border-left:1px solid #E1E6EB;}
tr:hover{background:#D0E9ED;}

</style>
<script type="text/javascript">
	function downReport(begintime,endtime) {
		var url = rootPath
				+ "/queryFoodstuff/downExcelShiPinZhunRu.do?begintime="
				+ begintime+"&endtime="+endtime+"&id=" + Math.random();
		location = url;
	}
	
	
	 
	$(window).load(function(){

		
		
		setTimeout(function(){
			var table =  document.getElementById("table1");
				
			
			
			  for (var  i = 0; i < table.rows.length; i++) {
				    for (var c = 0; c < table.rows[i].cells.length; c++) {
				      if (c == 0 || c == 1) { //选择要合并的列序数，去掉默认全部合并
				        for (var j = i + 1; j < table.rows.length; j++) {
				        	var cell1 = table.rows[i].cells[c].innerHTML;
				        	var cell2 = table.rows[j].cells[c].innerHTML;
				          if (cell1 == cell2) { 
				            table.rows[j].cells[c].style.display = 'none';
				            table.rows[j].cells[c].style.verticalAlign = 'middle';
				            table.rows[i].cells[c].rowSpan++;
				          } else {
				            table.rows[j].cells[c].style.verticalAlign = 'middle'; //合并后剩余项内容自动居中
				            break;
				          };
				        }
				      }
				    }
				  }
			  
			  
			  
			  
			  
			   table =  document.getElementById("table2");
				
				
				
			  for (var  i = 0; i < table.rows.length; i++) {
				    for (var c = 0; c < table.rows[i].cells.length; c++) {
				      if (c == 0 || c == 1) { //选择要合并的列序数，去掉默认全部合并
				        for (var j = i + 1; j < table.rows.length; j++) {
				        	var cell1 = table.rows[i].cells[c].innerHTML;
				        	var cell2 = table.rows[j].cells[c].innerHTML;
				          if (cell1 == cell2) { 
				            table.rows[j].cells[c].style.display = 'none';
				            table.rows[j].cells[c].style.verticalAlign = 'middle';
				            table.rows[i].cells[c].rowSpan++;
				          } else {
				            table.rows[j].cells[c].style.verticalAlign = 'middle'; //合并后剩余项内容自动居中
				            break;
				          };
				        }
				      }
				    }
				  }
			  
			
			//$("#list").css("display","block")
		}, 2000);
			
			
			
		})
	
	
	
	
</script>
</head>
<body>
</body>
</html>
