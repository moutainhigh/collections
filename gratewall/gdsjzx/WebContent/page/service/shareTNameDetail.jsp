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
.blue {
    background: rgba(0, 0, 0, 0) -moz-linear-gradient(center top , #00adee, #0078a5) repeat scroll 0 0;
    border: 1px solid #0076a3;
    color: #d9eef7;
}
.bigrounded {
    border-radius: 2em;
}
.button {
    border-radius: 0.5em;
    box-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
    cursor: pointer;
    display: inline-block;
    font: 14px/100% Arial,Helvetica,sans-serif;
    margin: 0 2px;
    outline: medium none;
    padding: 0.5em 2em 0.55em;
    text-align: center;
    text-decoration: none;
    text-shadow: 0 1px 1px rgba(0, 0, 0, 0.3);
    vertical-align: baseline;
}
</style>

<script>

	$(function(){
		$.ajax({
		  url: "/gdsjzx/shareResource/querytname.do",
		  cache: false,
		  success: function(data){
		  	for(var i=0;i<data.length;i++){
					$('<a class="button blue bigrounded" href="javascript:void(0);" onclick="tableName(\''+data[i].name+'\')">'+data[i].name+'</a>').appendTo($('#tableTName'));
				}
		  }
		});
	});	
	
	function tableName(name){
				parent.window.name = name;
				parent.window.location.href = 'shareSourcesAdd.jsp';
				window.close();
	}

</script>
</head>
<body>
	<div id="tableTName">
			
	</div>
</body>
</html>