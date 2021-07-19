<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<title></title>
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/static/css/jbxx/detial.css" />
<style type="text/css">
#pluginurl{
	font-size: 14px;
	font-family: inherit;
	display: block;
	left: 3px;
	position: absolute;
}

#save{
	font-size: 14px;
	font-family: inherit;
	display: block;
	left: 40px;
	position: absolute;
}

#balance{
	font-size: 14px;
	font-family: inherit;
	display: block;
	left: 50px;
	position: absolute;
}

#check{
	font-size: 14px;
	font-family: inherit;
	display: block;
	left: 123px;
	position: absolute;
}


.oper{
	height: 30px;
	width:150px;
	line-height: 30px;
}
#context{
	float: left;
	position: relative;
	
}


span {  
    width: 100%; 
    overflow: auto;  
    word-break:break-all;  
}  

	
</style>

</head>
<body>
<div class="oper" >
	
<!-- 	<a href="#" id='save' onclick="saveReport()" > 保存</a> -->
	<a href="#" id='pluginurl' onclick="downReport()" > 下载</a>
	<a href="#" id='balance' onclick="balanceInfo()" > 平衡关系</a>
	<a href="#" id='check' onclick="checkReport()" > 校验</a>
</div>
<center><div id="context">
</div>
</body>
</html>
<script type="text/javascript">
	//ajax调用后台，直接展示报表，前台展示不加界面
 function getUrlParam(name) {
         var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
         var r = window.location.search.substr(1).match(reg);  //匹配目标参数
         if (r != null) return unescape(r[2]); return null; //返回参数值
 }
var isFixed = "${param.isFixed}";
var isNowrap = "${param.isNowrap}";
$(document).ready(function(){
  var id =getUrlParam("id");
  //htmlobj=$.ajax({url: rootPath+"/cognosController/readCognos.do?id="+id,async:false});
 // console.info(htmlobj);
//  $("#context").html(htmlobj.responseText);
  $("#context").load(rootPath+"/cognosController/readCognos.do?id="+id+"&t="+(new Date).valueOf(), function(){
	  //alert("success");
	  if(isFixed == '1'){
		  $("#context").append("<style>table {table-layout:fixed; width:100%;}</style>");
	  }
	  if(isNowrap == '1'){
		  $("#context").append("<style>td {white-space:nowrap;word-break:keep-all;}</style>");
	  }
	  $("td").on("click", function () {
		  var value=($(this).html());
		  var heightValue=$(this).height();
		  var widthValue=$(this).width();
		  //delete <span>&nbsp;</span>
		  $(this).children().each(function(index,node){
			  if($(node).html() == '&nbsp;'){
				  $(node).remove();
			  }
		  })
		  var spanValue=($(this).children("span").html());
		  //逗号
		  spanValue = spanValue.replace(/,/g,'');
		  if( !isNaN(spanValue) ){
		  $(this).children("span").attr("contentEditable","true");
		  $(this).children("span").attr("background","yellow");
		  }
		  return false;    
	  });
	  if($("table").length >= 2){
		  $("table")[0].width="100%";
		  $("table")[1].width="100%";
		  $("table")[$("table").length - 1].width="100%";
	  }
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
	 //alert(reportHTML);
	 if(confirm('确定保存报表?')){
		 $.ajax({
		   type: "POST",
		   url: rootPath+"/cognosController/saveReport.do",
		   data: {
			   id:id,
			   report:reportHTML
		   },
		   success: function(msg){
		       alert(msg);
		   }
	      });
	 }
}
function balanceInfo(){
	
	var id =getUrlParam("id");
	$.ajax({
		   type: "POST",
		   url: rootPath+"/cognosController/queryBalanceInfo.do",
		   data: {
			   reportId:id
		   },
		   success: function(msg){
		     if(msg == '-1'){
		    	 alert("未找到平衡关系！");
		     }else if(msg == '-2'){
		    	 alert("未找到报表！");
		     }else{
		    	 msg = eval("(" + msg + ")");
		    	 var row = msg.rowbalance;
		    	 var column = msg.columnbalance;
		    	 var grid = msg.gridbalance;
		    	 var table = msg.tablebalance;
		    	 alert((row ? "横向:" + row + "\n" : "") + (column ? "纵向:" + column + "\n" : "") + (grid ? "单元格:" + grid + "\n": "")  + (table ? "表间:" + table : ""));
		     }
		   }
		});
}
//校验
function checkReport(){
	 var id =getUrlParam("id");
	 //var reportHTML=$("#context").html();
	 var url= rootPath+"/cognosController/checkoutReport.do?reportId="+id;
	 $("#check").attr("href",url);
	/* $.ajax({
		   type: "POST",
		   url: rootPath+"/cognosController/checkoutReport.do",
		   data: {
			   reportId:id,
			   reportContext:reportHTML
		   },
		   success: function(msg){
		     if(msg == '-1'){
		    	 alert("未找到平衡关系！");
		     }else if(msg == ''){
		    	 alert("校验成功！")
		     }else{
		    	 alert(msg);
		     }
		   }
		}); */
}
</script>