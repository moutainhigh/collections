<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible"content="IE=8;IE=9;IE=EDGE"> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>年报信息</title>
<%
	String rootPath = request.getContextPath();
%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=rootPath%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/js/sczt/base64.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.cookie.js" type="text/javascript"></script>

<script type="text/javascript">

	var id=null;//主键
	var type= null;
	var isGtOrQe=null;//主键
	var pripid= null;
	var ancheyear=null;//年报年度
	var anchedate= null;//年报时间
	var username = null;
	var identifier = null;
	
	 $(function(){
		 id=getUrlParam("id");//主键
		 type=getUrlParam("type");
		 isGtOrQe=getUrlParam("isGtOrQe");//主键
		 pripid=getUrlParam("pripid");
		 ancheyear=getUrlParam("ancheyear");//年报年度
		 anchedate=getUrlParam("anchedate");//年报时间
		 username=getUrlParam("username");//年报时间
		 identifier=getUrlParam("identifier");//年报时间
		 
		 $.cookie("username",username);
		 $.cookie("identifier",identifier);
		 
		 
		  if(id !=null && id.toString().length>1){
			  id=decode(id);
		    }
		  if(pripid !=null && pripid.toString().length>1){
			  pripid=decode(pripid);
		    }
		  
  		if(id==null&&identifier==null||id==null){
  			jazz.error("从主页中重新检索");
  		}
  		
  		$("#left").panel("option","frameurl","<%=request.getContextPath()%>/page/comselect/queryNB-tree-left.jsp");
  	    if(type =='gt'){
  	    	gridUrl="../../NBselect/queryNBDetailList.do?type=0";
			var tohtml = 'RightNBGTInfo.html';
			if(ancheyear !=null && ancheyear.toString().length>1){
					ancheyear=decode(ancheyear);
			      if(anchedate !=null && anchedate.toString().length>1){
			    	  anchedate=decode(anchedate);
					   }
			      var toHtml ='<%=request.getContextPath()%>/page/comselect/'+tohtml;
		     	 $("#main").panel("option","frameurl",toHtml+'?ancheyear='+ancheyear+'&anchedate='+anchedate+'&ancheid='+id);
			 }else{
				   submitAjax(gridUrl,tohtml);
			 }
			
  	    }else if(type == "qy"){
  	 	 	gridUrl="../../NBselect/queryNBDetailList.do?type=1";
			var tohtml = 'RightNBQYInfo.html';
			if(ancheyear !=null && ancheyear.toString().length>1){
					ancheyear=decode(ancheyear);
			      if(anchedate !=null && anchedate.toString().length>1){
			    	  anchedate=decode(anchedate);
					   }
			      var toHtml ='<%=request.getContextPath()%>/page/comselect/'+tohtml;
			      $("#main").panel("option","frameurl",toHtml+'?ancheyear='+ancheyear+'&anchedate='+anchedate+'&ancheid='+id);
			 }else{
				   submitAjax(gridUrl,tohtml);
			 }
  	    } 
  	//获取年份
  		function submitAjax(gridUrl,tohtml) {
  			var toHtml ='<%=request.getContextPath()%>/page/comselect/'+tohtml;
  				$.ajax({
  						url : gridUrl,
  						type : "POST",
  						data : {
  							pripid : pripid
  						},
  						dataType : "json",
  						success : function(msg) {
  							var dataStr = msg.data[0];
  						//	console.log(dataStr.data.length);
  							if (msg != null && msg != "") {
  								/* for(var i=0;i<=dataStr.data.length;i=i+2){
  									if((i+1)<dataStr.data.length){//RightNBInfo.html
  										$('#ndreport').append("<tr height='30px'><th width='50%'><a onclick=rightPageSet('"+tohtml+"?ancheid="+dataStr.data[i].ancheid+"&ancheyear="+dataStr.data[i].ancheyear+"&anchedate="+dataStr.data[i].anchedate+"') href='#'>"+dataStr.data[i].ancheyear+"年度报告</a></th><th>|</th><th width='50%'><a onclick=rightPageSet('"+tohtml+"?ancheid="+dataStr.data[i].ancheid+"&ancheyear="+dataStr.data[i+1].ancheyear+"&anchedate="+dataStr.data[i+1].anchedate+"') href='#'>"+dataStr.data[i+1].ancheyear+"年度报告</a></th></tr>");
  									}else{
  									    $('#ndreport').append("<tr height='30px'><th width='50%'><a onclick=rightPageSet('"+tohtml+"?ancheid="+dataStr.data[i].ancheid+"&ancheyear="+dataStr.data[i].ancheyear+"&anchedate="+dataStr.data[i].anchedate+"') href='#'>"+dataStr.data[i].ancheyear+"年度报告</a></th></tr>");
  									}
  									
  								} */
  								//alert(dataStr.data[0].ancheyear);
  								//alert(dataStr.data[0].anchedate);
  							 	$("#main").panel("option","frameurl",toHtml+'?ancheyear='+dataStr.data[0].ancheyear+'&anchedate='+dataStr.data[0].anchedate+'&ancheid='+dataStr.data[0].ancheid); 
  								
  							}
  						}
  					});
  		
  	 }
 	}); 
 	
 	 function getUrlParam(name) {
         var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
         var r = window.location.search.substr(1).match(reg);  //匹配目标参数
         if (r != null) return unescape(r[2]); return null; //返回参数值
     }

 	function rightPageSet(url){
         var url2="";
         url2="<%=request.getContextPath()%>/page/comselect/"+url;
        
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
