<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>市场主体信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/sczt/base64.js" type="text/javascript"></script>

<script type="text/javascript">
		var entityNo=null;//编号
		var priPid=null;//主键
		var sourceflag=null;//平台 无
		var enttype=null;//类型
		var type=null;//无
		var economicproperty =null;//经济性质 无
		var enttypeName =null;//企业类型中文名
		var year=null;//无
	 $(function(){
		 priPid=getUrlParam("priPid");//主键
  		if(priPid==null){
  			jazz.error("从主页中重新检索");
  		}
		/* entityNO代表的值
  		 隶属信息:1
  		出资信息:9
  		人员信息:10
  		股权信息:3
  		变更信息:5
  		迁移信息:2
  		证照信息:8
  		注吊销信息:7
  		清算信息:6 */
  		entityNo=getUrlParam("flag");
  		priPid=getUrlParam("priPid");
  		sourceflag=getUrlParam("sourceflag");
  		enttype=getUrlParam("enttype"); //企业类型
  		type=getUrlParam("type");
  		economicproperty=getUrlParam("economicproperty");
  		enttypeName = getUrlParam("enttypeName");
  		year = getUrlParam("year");
  		if(economicproperty=="2"){//内资企业
  			main.location.href="<%=request.getContextPath()%>/page/reg/query-panel-right.jsp";
  			
  		}else if(economicproperty=="3"){//外资企业
  	

  			main.location.href="<%=request.getContextPath()%>/page/reg/query-panel-right-waizi.jsp";
  		}else if(economicproperty=="1"){ //个体

  			main.location.href="<%=request.getContextPath()%>/page/reg/query-panel-right-getti.jsp";
  		}else if(economicproperty=="4"){//集团
  			main.location.href="<%=request.getContextPath()%>/page/reg/query-panel-right-jituan.jsp";
  		}
  		$("#left").attr("src","<%=request.getContextPath()%>/page/reg/query-tree-left.jsp");
  	
<%--   		if(economicproperty=='3'){//判断是否为外资
	  			main.location.href="<%=request.getContextPath()%>/page/reg/query-panel-right-waizi.jsp";
  		}else{//内资和个体
  			if(type=='0'){//个体
  	  			main.location.href="<%=request.getContextPath()%>/page/reg/query-panel-right-getti.jsp";
  			}else{
  	  			main.location.href="<%=request.getContextPath()%>/page/reg/query-panel-right.jsp";
  			}
  		} --%>
 	}); 
 	
 	 function getUrlParam(name) {
         var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
         var r = window.location.search.substr(1).match(reg);  //匹配目标参数
         if (r != null) return decode(unescape(r[2])); return null; //返回参数值
     }


   //  $('#main')
</script>
<style type="text/css">

#wrap {
width:760px;
margin:0 auto;
border:1px solid #333;
background-color:#ccc;
}
</style>
</head>


<FRAMESET rows="158,*,31"cols ="*" bordercolor='#006699' border="0" >
	
	<frame src="regDetail_head.jsp">
	<FRAMESET id="wrap" rows="*" cols="*,200px,5,819,*"  style="overflow:scroll;" bordercolor='#006699' border="0" >
			  <FRAME  src="<%=request.getContextPath()%>/page/reg/regDetail_back.jsp"  frameborder="1">
	          <FRAME id="left" name="left" src="" target="main" frameborder="1">
	          <FRAME id="center" name="center"  src="<%=request.getContextPath()%>/page/reg/query-tree-center.jsp" frameborder="0">
	          <FRAME id="main" name="main"  style="height:1500px;"   src="" frameborder="1">
	          <FRAME  src="<%=request.getContextPath()%>/page/reg/regDetail_back.jsp"  frameborder="1">
	
	 <NOFRAMES>
	 <body id ="hei">
	 	<input type="hidden" id="hiddEntType" value="ppp"/>
	 </body>
	 </NOFRAMES>
	</FRAMESET>
	
	<FRAME src="regDetail_footer.jsp">
</FRAMESET>
</html>
