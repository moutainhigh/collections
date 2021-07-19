<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报表详情</title>
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/static/css/jbxx/detial.css" />
<style type="text/css">
#pluginurl{
	font-size: 14px;
	font-family: inherit;
	display: block;
	right: 3px;
	position: absolute;
}

#save{
	font-size: 14px;
	font-family: inherit;
	display: block;
	right: 40px;
	position: absolute;
}
.oper{
	float:right;
	height: 30px;
	width:200px;
	line-height: 30px;
	position: relative;
}
#context{
	float: left;
	position: relative;
	
}

td{
	
}

span {  
    width: 100%; 
    overflow: auto;  
    word-break:break-all;  
}  
	
</style>

<script type="text/javascript">
	//ajax调用后台，直接展示报表，前台展示不加界面
 function getUrlParam(name) {
         var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
         var r = window.location.search.substr(1).match(reg);  //匹配目标参数
         if (r != null) return unescape(r[2]); return null; //返回参数值
 }
$(document).ready(function(){
  var id =getUrlParam("id");
  htmlobj=$.ajax({url: rootPath+"/cognosController/readCognos.do?id="+id,async:false});
  $("#context").html(htmlobj.responseText);
  
  $("td").on("click", function () {
	  var value=($(this).html());
	  var heightValue=$(this).height();
	  var widthValue=$(this).width();
	  var spanValue=($(this).children("span").html());
	  if( !isNaN(spanValue) ){
	  $(this).children("span").attr("contentEditable","true");
	  $(this).children("span").attr("background","yellow");

// 		  value=value.replace("span","div");
// 		  value=value.replace("span","div");
// 		 $(this).css("padding","0px");
// 		 $(this).html(value);
// 	 	 $(this).children("textarea").height((heightValue-2)+"");
// // 	 	 $(this).children("textarea").width((widthValue-2+""));
// 	 	 $(this).children("textarea").css("max-height",heightValue-2);
// // 	 	 $(this).children("textarea").css("max-width",widthValue-2);
// 	 	 $(this).children("textarea").css("resize","none");
// 	 	 $(this).children("textarea").css("border","none");
// 	 	 $(this).children("textarea").css("overflow","hidden");
	 	 
	  }
	  return false;    
  });
  
  
});

function trim(str){ //删除左右两端的空格
    return str.replace(/(^\s*)|(\s*$)/g, "");
}


function downReport(){
	 var id =getUrlParam("id");
	 var url= rootPath+"/cognosController/downReport.do?id="+id;
	 $("#pluginurl").attr("href",url);
}

function saveReport(){
	var id =getUrlParam("id");
	 var reportHTML=$("#context").html();
	 reportHTML=trim(reportHTML);
	 alert(reportHTML);
	$.ajax({
		   type: "POST",
		   url: rootPath+"/cognosController/saveReport.do",
		   data: {
			   id:id,
			   report:reportHTML
		   },
		   success: function(msg){
		     alert( "保存成功" );
		   }
		});
	
}





</script>

</head>
<body style="overflow:inherit;">
	<div class="headertop" style="background:url(<%=request.getContextPath()%>/static/images/system/index/B-05.png); height:78px; width:100%;">
		<img alt="广东省工商局" width="316px" height="52px"
			 src="<%=request.getContextPath()%>/static/images/system/index/B-06.png"
			 style="background: #ffffff; margin-left:50px;margin-top:10px; vertical-align: middle;"/>
	</div>
<div class="oper" >
	<a href="#" id='pluginurl' onclick="downReport()" > 下载</a>
	<a href="#" id='save' onclick="saveReport()" > 保存</a>
</div>
<div id="context">
</div>
</body>
</html>