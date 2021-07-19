<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>深圳市市场和质量监督管理委员会</title>
<%
	String rootPath = request.getContextPath();
%>
<script src="<%=rootPath%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=rootPath%>/static/js/sczt/base64.js" type="text/javascript"></script>
<script src="<%=rootPath%>/static/script/home/indexxiao.js" type="text/javascript"></script>

<style type="text/css">
html,body{
	margin:0px;
	padding:0px;
	overflow:hidden;
}

.sjzx_header{
	height:80px;
}

.sjzx_header .title{
	line-height:80px;
    font-family:simsun;
    font-weight:bold;
    font-size:12px;
    padding-right: 50px;
}

.sjzx_header .title span{
	margin: 0 5px;
	float:right;
}

.sjzx_header .title span a{
	color:black;
	text-decoration:none;
}

.sjzx_header .title span a:hover{
	color:blue;
	text-decoration:none;
}


.sjzx_content{
   /*
   样式调试
   */
	position:relative;
	top:-80px;
	bottom:20px;
}

.sjzx_content .serach{
	position:absolute;
	/* top:100px; */
	left:50%;
	width:510px;
	height:200px;
	margin-left:-255px;
}

.sjzx_content .serach .img{
	height:100px;
	width:300px;
	margin-left:100px;
	background-image:url(<%=rootPath%>/static/images/trs/search.png);
	background-repeat:no-repeat;
}

.sjzx_content .serach .content{
	border:1px solid #0086bb;
	height:45px;
	width:700px;
}
.content{
	margin-left:-100px
}

.sjzx_content .serach .content input{
	height:43px;
	width:590px;
	line-height:43px;
	border:none;
}

.sjzx_content .serach .content div{
	 height:100%;
	 width:100px;
	 background-color:#0086bb;
	 float:right;
	 line-height:43px;
     text-align:center;
     color:#ffffff;
     cursor:pointer;
     font-size:14px;
    
}
.yincangdiv { overflow-x: hidden; overflow-y: hidden; }
.yincangdiv_x{overflow-x: hidden;}
.footer_tishi{
	height:40px;
	 background-color:black; 
	position:relative;
	height:100%;
	line-height:30px;
	/* padding-left:15%; */
    font-size: 12px;
	 color: #eff1ef;	 
	font-family:simsun;

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

#searchcount{
	height:40px;	
		width:590px;
	line-height:35px;
	position:relative;
    font-size: 12px;
	font-family:simsun;
	font-family:"宋体";
	margin-left:-100px;
	 text-align: left;
}
.underline{text-decoration:underline}
</style>
<script type="text/javascript">
	function changSearchText(str){
		$('#search_txt').val(str);
	}
	$(function(){
			$.ajax({
				url : rootPath+'/trsSearchxiao/queryxiao.do',
				async: false,
				type : 'post',
				cache : false,
				dataType : 'json',
				success : function(data) {
				 var returnString=data.returnString;
				 var strs= new Array(); 
		
				 strs=returnString.split(","); 
				 for (i=0;i<strs.length ;i++ )
				 {
					//document.write(strs[i]+"<br/>"); //分割后的字符输出
					//$('&nbsp;').appendTo($("#ciyu"));
					$("#searchcount").innerHTML += "&amp;&#12288;";
					$('<font>  &#12288;</font><a href="#"><font   color="#09496a" size="2.5" face="宋体" onclick="changSearchText(\''+strs[i]+'\')" >'+strs[i]+'</font></a><div style="display:none; width:5px; height:5px;"></div> ').appendTo($("#searchcount"));	 
					 
				
				 } 
				
				},
				error : function() {
					$('<div><a style="color:red;margin-left:18%;">热词查询出错!</a><div>').appendTo($("#searchcount"));
				}
			});
	});
</script>
</head>
<body>
	<div id="allcontent" vtype="panel" width="100%" height="50%" showborder="false" 
		 layout="border" layoutconfig="{border: false, north_show_border: true, south_show_border: false ,north_drag:false,west_drag:false}">
		<div class="yincangdiv" region="north">
              <jsp:include page="../../page/trs/trs_header.jsp"/>
		</div>
        <div region="west" width="12%" style="background-color:#fff;"> 
  
  
        </div> 
		<div region="center" class="yincangdiv">
					<div class="sjzx_header">
						<div class="title"></div>
					</div>
					<div class="sjzx_content" style="z-index:999;">
						<div class="serach" width="50%"  style="text-align: center;">
							<div class="img"></div>
							<div class="content"><div onclick="search();">搜 一 下</div><input id="search_txt" type="text"></div>
							
							<div id="searchcount">
								
									<font color="#666666" style="font-weight:bold;">热词搜索：</font>
									

								
							</div>
						</div>
							
					</div>

		</div>
		<div region="east" width="12%"  style="background-color:#fff;"> 
  
           
  
        </div>       
		<div region="south" height="31" class="footer_tishi" style="text-align: center;">
				深圳市市场和质量监督管理委员会	
		<%-- 	<jsp:include page="../../page/integeration/footer.jsp"/> --%>
		</div>
	</div>
</body>
</html>