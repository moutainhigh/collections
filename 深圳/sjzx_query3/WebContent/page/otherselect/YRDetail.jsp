<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>一人公司信息</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<%
	String rootPath = request.getContextPath();
%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=rootPath%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/sczt/base64.js"
	type="text/javascript"></script>

<script type="text/javascript">

	var priPid=null;//主键
	var type= null;

	 $(function(){
		 priPid=getUrlParam("pripid");//主键
		 type=getUrlParam("type");
  		if(priPid==null){
  			jazz.error("从主页中重新检索");
  		}
  		
  		$("#left").panel("option","frameurl","<%=request.getContextPath()%>/page/otherselect/query-tree-left.jsp");
  		$("#main").panel("option","frameurl","<%=request.getContextPath()%>/page/otherselect/query-panel-right-yiren.jsp");
  	
 	}); 
 	
 	 function getUrlParam(name) {
         var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
         var r = window.location.search.substr(1).match(reg);  //匹配目标参数
         if (r != null) return unescape(r[2]); return null; //返回参数值
     }

 	function rightPageSet(url){
         var url2="";
         url2="<%=request.getContextPath()%>/page/otherselect/"+url;
        
 		 $("#main").panel("option","frameurl",url2); 
 	
	}
</script>
<style type="text/css">
#wrap {
	width: 760px;
	margin: 0 auto;
	border: 1px solid #333;
	background-color: #ccc;
}

.jazz-row-border {
	background-color: #efefef;
	border: 0 none;
	height: 0px;
	overflow: hidden;
	position: relative;
	width: 100%;
}

.jazz-column-btn {
	cursor: pointer;
	height: 42px;
	margin-top: -21px;
	opacity: 0.7;
	position: absolute;
	top: 50%;
	width: 0px;
}

.footer_tishi {
	height: 40px;
	background-color: black;
	position: relative;
	height: 100%;
	line-height: 30px;
	/* padding-left:15%; */
	font-size: 12px;
	color: #eff1ef;
	font-family: simsun;
}

.yincangdiv {
	overflow-x: hidden;
	overflow-y: hidden;
}
</style>
</head>
<div id="allcontent" vtype="panel" width="100%" height="1500"
	showborder="false" layout="border"
	layoutconfig="{border: false, north_show_border: true, south_show_border: false ,north_drag:false,west_drag:false}">
	<div class="yincangdiv" height="68" region="north">
		<div name="n1" vtype="panel" title="" titledisplay="false"
			height="100%" width="100%"
			frameurl="<%=request.getContextPath()%>/page/reg/regDetail_head.jsp"></div>
	</div>

<%-- 	<div region="west" width="0">
		<div name="w1" vtype="panel" title="" titledisplay="false"
			height="100%" width="100%"
			frameurl="<%=request.getContextPath()%>/page/reg/regDetail_back.jsp"></div>

	</div> --%>
	<div region="center" widht="100%" height="100%" style="margin-top: 10px;">
		<div id="column_id" width="100%" vtype="panel" name="panel"
			layout="column" height="100%" layoutconfig="{width: ['50%','200','4','790','*']}">
			<div width="100%" height="100%">
			</div>
			<div width="100%" height="100%">
				<div id="left" name="left" vtype="panel" title=""
					titledisplay="false" height="100%" width="100%" frameurl=""></div>
			</div>

			<div width="100%" height="100%">
				<div id="center" name="center" vtype="panel" title=""
					titledisplay="false" height="100%" width="100%"
					frameurl="<%=request.getContextPath()%>/page/reg/query-tree-center.jsp"></div>
			</div>

			<div width="100%" height="100%">
				<div id="main" name="main" vtype="panel" title=""
					titledisplay="false" height="100%" width="100%" frameurl=""></div>
				
			</div>
			
			<div width="100%" height="100%">
			
			</div>
		</div>

	</div>
<%-- 	<div region="east" width="0" style="background-color: #fff;">
		<div name="e1" vtype="panel" title="" titledisplay="false"
			height="100%" width="100%"
			frameurl="<%=request.getContextPath()%>/page/reg/regDetail_back.jsp"></div>

	</div> --%>
	<!-- <div region="south" height="31">
		<div name="s1" vtype="panel" title="" titledisplay="false"
			height="100%" width="100%"
			frameurl="<%=request.getContextPath()%>/page/reg/regDetail_footer.jsp"></div>

	</div> -->
</div>

</html>
