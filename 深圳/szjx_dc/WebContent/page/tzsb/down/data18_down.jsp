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

<script
	src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js"
	type="text/javascript"></script>
<style type="text/css">
body {
	font-family: "微软雅黑";
	font-size: 14px;
}

table {
	width: 96%;
	margin: 0 auto;
}

table h1 {
	font-size: 18px;
}

td {
	padding: 0 8px;
	text-align: center;
}

tr:hover{
	background: #ccc;
}
</style>
</head>
<body id='huawu_num' >

</body>

<script>
$(window).load(function(){

	
	
setTimeout(function(){
	var table =  document.getElementById("lists");
		
	
	
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
	
	$("#list").css("display","block")
}, 2000);
	
	
	
})


</script>
</html>