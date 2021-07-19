<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>市场主体信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>


<script type="text/javascript">
		var entityNo=null;
		var priPid=null;
		var sourceflag=null;
		var enttype=null;
		var type=null;
		var economicproperty =null;
		var enttypeName =null;
		var year=null;
	 $(function(){
		 priPid=getUrlParam("priPid");
  		if(priPid==null){
  			alert("从主页中重新检索");
  		}
  		entityNo=getUrlParam("flag");
  		priPid=getUrlParam("priPid");
  		sourceflag=getUrlParam("sourceflag");
  		enttype=getUrlParam("enttype");
  		type=getUrlParam("type");
  		economicproperty=getUrlParam("economicproperty");
  		enttypeName = getUrlParam("enttypeName");
  		year = getUrlParam("year");
  		if(economicproperty=='3'){//判断是否为外资
	  			main.location.href="<%=request.getContextPath()%>/page/reg/query-panel-right-waizi.jsp";
  		}else{//内资和个体
  			if(type=='0'){//个体
  	  			main.location.href="<%=request.getContextPath()%>/page/reg/query-panel-right-getti.jsp";
  			}else{
  	  			main.location.href="<%=request.getContextPath()%>/page/reg/query-panel-right.jsp";
  			}
  		}
 	}); 
 	
 	 function getUrlParam(name) {
         var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
         var r = window.location.search.substr(1).match(reg);  //匹配目标参数
         if (r != null) return unescape(r[2]); return null; //返回参数值
     }
     
   //  $('#main')
</script>
</head>
<FRAMESET rows="*" cols="20%,1%,*" bordercolor='#006699' border="0" framespacing="0" frameborder="yes">
<!--  <FRAME id=topFrame name=topFrame src="left.jsp" noResize scrolling=no> -->
          <FRAME name="left" src="<%=request.getContextPath()%>/page/reg/query-tree-left.jsp" target="main" frameborder="0" noresize="noresize">
          <FRAME name="centers" src="<%=request.getContextPath()%>/page/reg/query-tree-center.jsp" frameborder="1" bordercolor="#008000" framespacing="0"  noresize="noresize">
          <FRAME name="main" src="" frameborder="0"  noresize="noresize">
 <NOFRAMES>
 <body>
 </body>
 </NOFRAMES>
</FRAMESET>

</html>