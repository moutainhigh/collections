<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>市场主体信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>

<style type="text/css">
	div{
		margin: 0px;
		padding: 0px;
	}

</style>


<script type="text/javascript">
		var entityNo=null;
		var priPid=null;
		var sourceflag=null;
		var enttype=null;
		var type=null;
		var economicproperty =null;
		var enttypeName =null;
		var year=null;
		var open=null;
		var regno = null;
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
  		open = getUrlParam("open");
  		regno = getUrlParam("regno");
  		$('#hiddEntType').val(enttype);
  		if("sczt"==open){
	  		if(economicproperty=='3'){//判断是否为外资
		  			main.location.href="<%=request.getContextPath()%>/page/reg/query-panel-right-waizi.jsp";
	  		}else{//内资和个体
	  			if(type=='0'){//个体
	  	  			main.location.href="<%=request.getContextPath()%>/page/reg/query-panel-right-getti.jsp";
	  			}else{
	  	  			main.location.href="<%=request.getContextPath()%>/page/reg/query-panel-right.jsp";
	  			}
	  		}
  		}else if("ndbg"==open){
  				//judgeType(enttype,year);
  				main.location.href="<%=request.getContextPath()%>/page/reg/annualReport/query-panel-right.jsp?year="+year;
  		}else if("scjg"==open){
				//judgeType(enttype,year);
			main.location.href="<%=request.getContextPath()%>/page/reg/supervise/query-panel-right.jsp";
		}
 	}); 
 	
 	 function getUrlParam(name) {
         var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
         var r = window.location.search.substr(1).match(reg);  //匹配目标参数
         if (r != null) return unescape(r[2]); return null; //返回参数值
     }
   	
	//年报判断类型
	function judgeType(enttype,year){
	if (year != null && year != undefined) {
			if (enttype == "9100" || enttype == "9200") {
			main.location.href="<%=request.getContextPath()%>/page/reg/annualReport/query-panel-right-nh.jsp";
			} else {
				if (enttype == "9999") {
				main.location.href="<%=request.getContextPath()%>/page/reg/annualReport/query-panel-right-gt.jsp";
				} else {
					/* if(economicproperty=="3"){
					ndurl="annualReport/annual-fzjg.json";
					}else{
						if(economicproperty=="2"){
						ndurl="annualReport/annual-hf.json";
						}else{ */
					main.location.href="<%=request.getContextPath()%>/page/reg/annualReport/query-panel-right.jsp";
					/* } 				
					}*/
				}
			}
			refresh();
		}
	
	}
	function saveSum(sum){
		//$('#hiddenSum').attr("value",sum);
		/* $('#hiddenSum').val(sum);
		console.info($('#hiddenSum'));
		console.info($('#hiddEntType'));
		console.info($('#left'));
		console.info($("body")); */
		$('#left').attr("sum",sum);
	//	alert($('#left').attr("sum"));
	}
	
	function getSum(){
		return $('#left').attr("sum");
	}
	
	function iFrameHeight(t) { 
		$('#right').css("height",t);
		parent.iFrameHeight(t);
	}
</script>
</head>
<div>

<FRAMESET rows="*" cols="200,*" bordercolor='#006699' border="0" >
<!--  <FRAME id=topFrame name=topFrame src="left.jsp" noResize scrolling=no> -->
          <FRAME id="left" name="left" src="<%=request.getContextPath()%>/page/reg/query-tree-left.jsp" target="main" frameborder="1" sum="0" >
          <FRAME id="right" name="main" src="" frameborder="" scrolling=yes>
 <NOFRAMES>
 <body>
 	<input type="hidden" id="hiddEntType" value="ppp"/>
 </body>
 </NOFRAMES>
</FRAMESET>
</div>
</html>
