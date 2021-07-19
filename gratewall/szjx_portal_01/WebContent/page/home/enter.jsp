<%@ page contentType="text/html; charset=utf-8"%>  
<!DOCTYPE html>
<html>
<head>
<%
	String contextpath = request.getContextPath();
%>
<title>深圳工商-系统入口列表</title>
<link type="text/css" href="../../static/css/admin/global.css" rel="stylesheet" />
    <link rel="stylesheet" href="<%=contextpath%>/static/portal/index.css"/>
    <link rel="stylesheet" href="<%=contextpath%>/static/portal/enter.css"/>
<script>
	var rootPath="<%=contextpath%>";
</script>

<!--[if IE 7]>
<style>
.wrap .hot_links a img{margin-top:0px !important;}
img{border:0;}

.submenu_content a{height:20px !important;line-height:20px !important; margin-top: 1px !imporant;}
.icon_submenu{background-position-y: 5px !important;}

div.right_menu{top:79px;}

.menu h3{padding:0;margin-left: 16px;	margin-top: -8px !important;}
</style>
<![endif]-->

<style>
body{font-family: "Microsoft YaHei";}

.menu_icon{margin-left: 17px}
.submenu,.submenu1{background: #fff;border-top: 1px solid #e1ecf4;border-bottom: 1px solid #e1ecf4;}
.submenu_content a{color: #666; font: 14px/1em Microsoft YaHei;white-space: nowrap;}
.submenu_content:hover a{color:#0186bc;}


.submenu_content{position: relative;}

.right_menu{width: 616px;overflow-y: hidden; top:79px;height: 228px;background:#fdfdfd;}

.menu h3{padding:0;margin-left: 16px;	margin-top: -14px;}

.addh3{
	background: #fff;
	color: #0186bc;
	font-family: "微软雅黑";
	border-left: 1px solid #80C1DD;
	border-right: 1px solid #80C1DD;
	height: 41px;
    position: relative;
    z-index: 999;
    padding:0
}

.small_interval2{
	padding-bottom: 14px;;
	
}
.wid607{width: 607px}
.mr6{margin-right: 6px;}

.listNumSeri{
	width: 19px;
    height: 20px;
    background:url(../../static/portal/images/36.png);
    display: inline-block;
    text-align: center;
    line-height: 20px;
    position: absolute;
    top:-4px;
    left: 75px;
    color: #fff;
}


div.icoPic {left:62px;}
.clear{clear:both;}
</style>
</head>
<body style="background: #fff" onload="">
<div >
    <div class="menu " id="1" >
        <div class="menu_icon menu_icon1" style="margin-left: 0;">
        	 <span id="RPNO" ></span>
            <span class="icon_permission inline_block"></span>
            <h3 class="select" style="margin-left: 0; margin-top:-14px">登记许可</h3>
        </div>
        
        <div name="RP" class="submenu submenu1 right_menu">
            <div class="inline_block submenu_content">
                <span class="inline_block icon_submenu"></span><a href="">登记许可</a>
            </div>
        </div>
<!--         <h3 class="center addh3" style="margin-left: 0;">登记许可</h3> -->
        <span class="icoPic show1" style="left:44px;"></span>
        <div class="clear"></div>
    </div>
    <div class="menu" id="2" >
        <div class="menu_icon " >
        	<span id="MSNO" ></span>
            <span class="icon_supervise inline_block"></span>
            <h3 style="margin-left: 0;" >市场监管</h3>
        </div>
        <div name="MS" class="submenu right_menu" style="margin-left: -110px;">
            <div class="inline_block submenu_content">
                <span class="inline_block icon_submenu"></span><a href="">市场监管</a>
            </div>
        </div>
         <span class="icoPic show2"></span>
    </div>
   <!--  <div class="menu" id="3" >
        <div class="menu_icon ">
        <span id="PSNO" ></span>
        <span class="icon_service inline_block"></span>
        <h3 style="margin-left: 0;">公共服务</h3>
        </div>
        <div name="PS" class="submenu right_menu" style="margin-left: -197px;">
            <div class="inline_block submenu_content">
                <span class="inline_block icon_submenu"></span><a href="">公共服务</a>
            </div>
        </div>
         <span class="icoPic show3"></span>
    </div> -->
    <div class="menu" id="4" >
        <div class="menu_icon ">
        <span id="AMNO" ></span>
        <span class="icon_manage inline_block"></span>
        <h3 style="margin-left: 0;">政务管理</h3>
        </div>
        <div name="AM" class="submenu right_menu" style="margin-left: -238px;">
            <div class="inline_block submenu_content">
                <span class="inline_block icon_submenu"></span><a href="">政务管理</a>
            </div>
        </div>
         <span class="icoPic show4"></span>
        
    </div>
    <div class="menu" id="5" >
        <div class="menu_icon ">
        <span id="LENO"></span>
        <span class="icon_enforcement inline_block"></span>
        <h3 style="margin-left: 0;">执法办案</h3>
        </div>
        <div name="LE" class="submenu right_menu" style="margin-left: -366px;">
            <div class="inline_block submenu_content">
                <span class="inline_block icon_submenu"></span><a href="">执法办案</a>
            </div>
        </div>
         <span class="icoPic show5"></span>
    </div>
    <div  class="menu" id="6" style="">
        <div class="menu_icon " style="margin-left: 19px">
        <span id="APNO" ></span>
        <span class="icon_decision inline_block"></span>
        <h3 style="margin-left: 0;">管理决策</h3>
        </div>
        <div name="AP" class="submenu right_menu" style="margin-left: -497px;width: 619px;">
            <div class="inline_block submenu_content">
                <span class="inline_block icon_submenu"></span><a href="">管理决策</a>
            </div>
        </div>
         <span class="icoPic show6"></span>
        
    </div>
    <!-- <div class="menu" id="7" >
        <div id ="71" class="menu_icon2 menu_icon20">
            <span class="icon_system inline_block"></span>
        </div>
        <div name="FZXT"  class="submenu right_menu" style="margin-left: -578px;width: 660px">
            <div class="inline_block submenu_content" >
                <span class="inline_block icon_submenu"></span><a href="">机构类系统</a>
            </div>
        </div>
         <span class="icoPic show7"></span>
        <h3 class="center">辅助系统</h3>
    </div> -->
    
    <div class="clear"></div>
</div>
<script type="text/javascript" src="<%=contextpath%>/static/script/JAZZ-UI/external/jquery-1.8.3.js"></script>
<script type="text/javascript" src="<%=contextpath%>/static/portal/enter.js"></script>
<script type="text/javascript" src="<%=contextpath%>/static/script/home/indexPortal.js"></script>
</body>
</html>