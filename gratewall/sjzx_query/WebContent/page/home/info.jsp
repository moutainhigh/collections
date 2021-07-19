<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>深圳市市场和质量监督管理委员会-快搜-详细信息</title>
<%
	String rootPath = request.getContextPath();
%>
<script src="<%=rootPath%>/static/script/jazz.js" type="text/javascript"></script>
<style type="text/css">
html,body{
	margin:0px;
	padding:0px;
}

.jazz-panel-content{
	background:none;
}

.lefttree{
	border:1px solid rgb(214,233,240);
}

.lefttree div{
	height:30px;
	line-height:30px;
}

.lefttree .title{
	padding-left:20px;
	background-color:rgb(214,233,240);
}

.nav{
	padding-left:5px;
}

.left-nav{
	width:90px;
	float:left;
	text-align: center;
	cursor:pointer;
}

.right-nav{
	width:90px;
	display:inline-block;
	text-align: center;
	cursor:pointer;
}

.active{
	font-weight: bold;
}
</style>
<script type="text/javascript">
function fitContent() {
    var scrolltop = $(document).scrollTop();
    var ifm= document.getElementById("maincontent");
    ifm.height = 0 ;
    var subWeb = document.frames ? document.frames["maincontent"].document :ifm.contentDocument;
    if(ifm != null && subWeb != null) {
    	var bodyheight = subWeb.body.scrollHeight;
		var contentheight = 450;
		var maxheight = Math.max(bodyheight ,contentheight);
    	ifm.height = maxheight;
    }
    $(document).scrollTop(scrolltop);
}

$(function(){
 	$('.lefttree').layout({
		layout:"fit"
	}); 
});
</script>
</head>
	<body>
	 	<div id="content"  vtype="panel" name="panel" width="100%" height="100%" showborder="false" layout="column" layoutconfig="{border:true,width:['200','*']}" style="margin:10px;">
	        <div>
	    		<div style="height:30px;line-height: 30px;text-align: center;"><input type="radio" name="expendtype" value="0" onClick="" checked>列表&nbsp;&nbsp;<input type="radio" name="expendtype" value="1" onClick="">菜单树</div>
	    		<div class="lefttree">
		    		<div class="title">个体工商</div>
    				<div class="nav">登记信息</div>
    				<div><div class="left-nav active">基本信息</div>|<div class="right-nav">迁入信息</div></div>
    				<div><div class="left-nav">迁出信息</div>|<div class="right-nav">个体证照</div></div>
    				<div><div class="left-nav">变更信息</div>|<div class="right-nav">注销信息</div></div>
    				<div><div class="left-nav">发照信息</div>|<div class="right-nav">撤销登记</div></div>
    				<div><div class="left-nav">经营者信息</div>|<div class="right-nav">许可证信息</div></div>
    				<div><div class="left-nav">停业复业信息</div>|<div class="right-nav">&nbsp;</div></div>
    				<div class="nav">年报信息</div>
    				<div><div class="left-nav">2014</div>|<div class="right-nav">&nbsp;</div></div>
	    		</div>
	    	</div>
	    	<div style="padding-left:20px;overflow:hidden;">
	    		<iframe id="maincontent" name="maincontent"  src="info-jbxx.jsp" width="100%" height="100%" style="overflow:hidden;" onload="fitContent();" oscrolling="no" frameBorder="0"></iframe>		
	    	</div>   
		</div>
	</body>
</html>