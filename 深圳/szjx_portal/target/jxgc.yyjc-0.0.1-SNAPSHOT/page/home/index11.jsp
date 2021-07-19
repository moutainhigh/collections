<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>深圳市市场和质量监督管理委员会</title>
<%
	String contextpath = request.getContextPath();
%>
<script src="<%=contextpath%>/static/script/jazz.js" type="text/javascript"></script>

<script type="text/javascript">
$(function(){     

	
	
	$.ajax({
	    url: 'http://localhost:8080/yyjc/home/getUserNameByjsonp.do',
	    type: 'post',
	    dataType:'jsonp',
	    jsonp: "callback",
	    data: {
	        "type":'0',
	        "mobilePhone": $("#tel").val()
	    },
	    success:function(data){
	        alert(data.ret)
	        settime(obj);
	    },
	    error:function(data){
	      alert("失败");
	    }
	});

});  
</script>

</head>
<body>
 <div class="div_login">
                  
                    <div name="dlm" id="dlm" vtype="textfield" label="登录名" labelalign="right" rule="must"  width="300" class="div_dlm"></div><br/>
					<div name="mm" id="mm" vtype="passwordfield" label="密码" labelalign="right" rule="must"  width="300" class="div_mm"></div><br/>
					
                 </div>
	
</body>
</html>