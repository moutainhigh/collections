<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>应用集成</title>
<%
	String appID = request.getParameter("appID");
	String appCode = request.getParameter("appCode");
%>
<script type="text/javascript">
	var appID = "<%=appID%>";
	var appCode = "<%=appCode%>";
</script>
<script src="../../static/script/jazz.js" type="text/javascript"></script>
<script src="../../static/script/home/integeration.js" type="text/javascript"></script>
<style type="text/css">

#serarch{
	height:40px;
	background-color:#4698e6;
	position:relative;
}

.toparrow{
	cursor:pointer;
	width:22px;
	height:10px;
	background:url(../../static/images/other/toparrow.png)  no-repeat;
	position:absolute;
	left:50%;
	top:0px;
	zoom:1;
	z-index:1000;
}

#serarch a.jazz-button{
	cursor:pointer;
	border-style: none;
	margin: -5px 0px 0px 0px;
	border:none;
	background-color:#4698e6;
	background-image:none;
}

#serarch a.jazz-button .button-text{
	color:#ffffff;
	font-size:16px;
	font-family:Microsoft YaHei;
	font-weight:normal;
}
 
#serarch .jazz-button:active .button-background{
	background-color: #0c74aa;
	background-image: none;
}

#serarch .jazz-button-pressed .button-background{
	background-color: #0c74aa;
	background-image: none;
}

#serarch .arrow-align-right{
	border-left-style:none;
}
 
#serarch a.jazz-button .button-main-left{
	margin: 0px 0px 0px 10px;
}
#serarch a.jazz-button .text-align-right{
	margin: 0px 0px 0px 13px;
	vertical-align:-2px;
	*vertical-align:2px;
}

#serarch .jazz-button:hover .button-background {
    background-color: #0c74aa;;
}

a.jazz-menu-label:hover, 
a.jazz-menuitem-content:hover,
.jazz-menu .jazz-menu .jazz-menuitem-content:hover {
	color:#ffffff;
}
.jazz-menu-label.jazz-menu-label-active{
	color:#ffffff;
}

.lefttop{
	width:180px;
	height:32px;
	background:url(../../static/images/other/bglefttop.png)  no-repeat;
	margin: 5px 10px 0 10px;
}

.lefttop .left{
	width:50px;
	height:32px;
	float:left;
	background:url(../../static/images/other/gear.png)  no-repeat 10px center;
	_margin-right:-3px;
}

.lefttop .center{
	height:32px;
	line-height:32px;
	font-size: 12px;
	color: white;	
	font-family:simsun;
}

.lefttop .right{
	width:30px;
	height:32px;
	float:right;
	cursor:pointer;
	background:url(../../static/images/other/rarow.png)  no-repeat 10px center;
	_margin-left:-3px;
}

.jazz-column-border-cursor , .jazz-row-border-cursor{
	background-color:white;
	cursor:none;
}

.jazz-column-btn-l , .jazz-row-btn-t{
	display: none;
}

.button-main, .button-arrow{
	margin:1px 3px 4px 3px;
}

.jazz-row-element, .jazz-column-element{
	background-color: #fff;	
}

</style>
</head>
<body>
	<div id="allcontent" vtype="panel" width="100%" height="100%" showborder="false" 
		 layout="border" layoutconfig="{border: false, north_show_border: true,west_show_border: true, south_show_border: false ,north_drag:false,west_drag:false}">
		<div region="north" height="106">
			<jsp:include page="../../page/home/header.jsp"/>
			<div class="serarch" id="serarch">
				<div class='toparrow'></div>
			</div>
		</div>
	    <div region="west">
	   		<div id="lefttop" name="lefttop" class="lefttop">
	   			<div class="left"></div><div class="right"></div><div class="inline_block center">功能菜单</div>
	   		</div>
	   		<div vtype="panel" width="100%" height="100%" layout="fit" titledisplay="false" bgcolor="#f4f9fd" title="功能菜单"  titleicon="../../static/images/other/gear.png" name="leftcontent" id="leftcontent" showborder="false"
	   		     style="margin: 0 10px 10px 10px">
				<div id="lefttree" name="lefttree"></div>
	    	</div>
		</div>
		<div region="center">
		    <div vtype="panel" width="100%" layout="fit" titledisplay="false" bgcolor="white" name="maincontent" id="maincontent" showborder="false"
		         style="margin: 5px 10px 10px 0">
		</div>
		<div region="south" height="31">
			<jsp:include page="../../page/integeration/footer.jsp"/>
		</div>
	</div>
	</div>
</body>
</html>