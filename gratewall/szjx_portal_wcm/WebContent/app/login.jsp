<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>深圳市市场和质量监督管理委员会-门户</title>
<%
	String contextpath = request.getContextPath();
%>
<!--<script src="<%=contextpath%>/static/script/jazz.js" type="text/javascript"></script>-->
<script type="text/javascript" src="jquery-1.8.3.js"></script>

<script type="text/javascript">
function post(URL, PARAMS) {        
    var temp = document.createElement("form");        
    temp.action = URL;        
    temp.method = "post";        
    temp.style.display = "none";        
    for (var x in PARAMS) {        
        var opt = document.createElement("textarea");        
        opt.name = x;        
        opt.value = PARAMS[x];        
        // alert(opt.name)        
        temp.appendChild(opt);        
    }        
    document.body.appendChild(temp);        
    temp.submit();        
    return temp;        
}     
function getUserName(){
	var url1='http://mqsweb41:9080/yyjc/home/getUserNameByjsonp.do';
	$.ajax({

		url: url1,
	    type: 'post',
	    dataType:'jsonp',
	    jsonp: "callback",
	    success:function(data){
			postUser(data.ret);
	    },
	    error:function(data){
	     alert("获取当前用户失败，请先刷新门户页面，再刷新重试！谢谢！");
	    }
	});	
}

function postUser(username){
		var urlto="login_dowith.jsp";
		post(urlto, {UserName :username,PassWord:'trsadmin'});
}

 $(function(){     
	getUserName();

});  
</script>

</head>
<body>

</body>
</html>

