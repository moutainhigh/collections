<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title>企业全景分析</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/chart/Highstock-4.2.3/js/highstock.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/chart/Highstock-4.2.3/js/modules/exporting.js"></script>
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/static/css/jbxx/detial.css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/sczt/panoramicAnalysiss.js"></script>

<style type="text/css">
td{
	color: #666;
    font-size: 12px;
    font-family: "瀹嬩綋";
    /* padding: 7px 0px 0px; */
    font-weight:bold;
    word-wrap: break-word;
}
tr{
border:1px solid #AFB0B1;
border-left: 0px solid #AFB0B1;
}
.jazz-tabview-wrap{
	background-color:white;
}
.jazz-tabview-nav jazz-helper-reset jazz-helper-clearfix{
	background: #1c97ca;
}
.jazz-tabview-wrap ul{
	background: #E1F2F9;
}

.nav-content-middle{
	    width: 112px;
    font-family: 微软雅黑,宋体;
    font-weight: bold;

}

body{font-family:"微软雅黑"}


/*重新修改jazz*/
.jazz-tabview .jazz-tabview-selected .nav-content-left {
    background: #fff;
}

.jazz-tabview .jazz-tabview-nav .nav-content-middle {
     background:url();
    margin: 0px 26px 0px 11px;
    _margin:0px 23px 0px 11px;
}

.jazz-tabview .jazz-tabview-nav .nav-content-left { 
    width:14px;
    height:25px;
    float:left;
    background:#fff;
    _margin-right:-3px;
}

/*修改下方的小线条*/
.jazz-tabview .jazz-tabview-header-top, .jazz-tabview .jazz-tabview-header-bottom{
	height: 1px;
	background-color: #3DBDD8;
	_overflow: hidden;
	float:left;
	width: 100%;
	_zoom: 1;
}


/*下方的饼状图显示的表格，line-height为垂直方向竖直对齐*/
.jazz-panel-titlebar{background:url(../../static/script/JAZZ-UI/lib/themes/default/images/titlebackground.png) repeat-x;height:17px;line-height:17px;}

#tabs{border:none;border-collapse: collapse; table-layout: fixed; width: 100%;margin: 0;padding:0}
#tabs tbody tr{border: none;}
</style>
</head>
<body style="overflow-y: hidden;">
<div name="row_id" height="100%" vtype="panel" layout="row" layoutconfig="{rowheight:['50px','*']}">
  <div id="Header" style="background: #1c97ca">
        <div class="headertop"  style="height:50px; width:100%;">
			 <img alt="" style="position:absolute;z-index:2;margin-left: 10px;" height="37" src="<%=request.getContextPath()%>/page/sjbd/img/12.png">
			<div class='header' style="position:absolute;z-index:10;">
				<ul id="nav" >
				</ul>
			</div>
		</div>
    </div>
    
	<!-- <div> -->
	<div name="tab_name" vtype="tabpanel" overflowtype="2" colspan="2" tabtitlewidth="126px"  width="100%" height="100%" 
		 orientation="top" id="tab_name">  
	    <ul style="background: #fff;">    
	        <li><a href="#tab1" style="background:#FDAA29;padding-left:15px;">企业全景分析</a></li> 
	       <!--  <li><a href="#tab2">企业全景分析</a></li>   -->
	     </ul>    
	     <div>    
	        <div id="tab1" style="width: 100%;height:88%;overflow-x: hidden;">
				<div id="container" style="width: 99%;height: 500;"></div>
				<div style="width: 96%;margin:0 auto;overflow-x: hidden;" id="content">
					<div class="jazz-panel-titlebar jazz-panel-header jazz-formpanel-titlebar">
						<div class="jazz-formpanel-titlebar-inner" style="width:100%;">
							<span class="jazz-panel-title-font jazz-formpanel-title-font">
							<a id="entname" style="font-size:16px;font-family:'微软雅黑'">主体名称:</a>
							<!-- <a id="regno" style="float:right;font-size:19px;">注册号:</a> --></span>
							<span class="jazz-panel-rtl-left" style=""></span>
							<span class="jazz-panel-rtl-right" style=""></span>
						</div>
					</div>
					<table id="tabs">
						 <colgroup>
							<col align="right" height="40px" width="119px" style="vertical-align: middle;background-color:#DCEEEF;">
							<col width="255px" style="vertical-align: middle;background-color:white;border-right: 1px dotted #e5e5e5">
							<col align="right" height="40px" width="119px" style="vertical-align: middle;">
							<col width="255px" style="vertical-align: middle;">
						</colgroup> 
						<tbody>
						</tbody>
					</table>
				</div>
	        </div> 
	    	<!--<div id="tab2" style="width: 100%;height:auto;">
	    	 <iframe id="contload" name="contload" scrolling='no' showframeborder="no" height="85%" width="100%" height="100"></iframe>
	    	</div> -->
	    </div> 
	      
	</div>
	<!-- </div> 
	<div id="gwssiimg">
		<img alt="广东省工商局" width="100%" height="67px"
			 src="<%=request.getContextPath()%>/static/images/system/index/B-04.png"
			 style="vertical-align: middle;"/>
	</div>-->
</div>
<p style="width:100%;height:30px;line-height:30px; text-align:center;vertical-align: middle;font-family: '微软雅黑,宋体';color: white-space: border;font-size: 14px;color: white;background-color: #26363b;position: fixed;bottom: 0px;z-index:100;">版权所有：广东省工商行政管理局  地址：广州市天河区体育西路57号红盾大厦  邮编：510620  技术支持：长城计算机软件与系统有限公司   ICP备案号：粤ICP备05028****号*1   网站备案编码：44010****1622</p>    </div>
</body>
</html>