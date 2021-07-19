<%@ page language="java" contentType="text/html; charset=utf-8" 
pageEncoding="utf-8"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"> 
<title>process bar</title> 
<script type="text/javascript" language="JavaScript"> 

// 判断网页是否加载完成 
document.onreadystatechange = function () { 
	if(document.readyState=="complete") { 
		document.getElementById('divprogressbar').style.display='none'; 
		document.getElementById('loadingImg').style.display='none'; 
	}
}
</script> 
</head> 
<body> 
	<div id="divprogressbar" 
	style="position: absolute; width: 100%; height: 100%; left: 0px; top: 0px; background-color: #ffffff; filter: alpha ( opacity = 10 );-moz-opacity:0.1;
	opacity:0.1;z-index: 50000"> 
	</div> 
	<div style="position:absolute;left:250px;top:100px;z-index:51000" id="loadingImg">
		<img src="../images/loading.gif" />
	</div>
</body> 
</html> 

