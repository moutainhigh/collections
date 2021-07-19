<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
	<title>广东省工商行政管理局</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<link rel="icon" href="<%=request.getContextPath()%>/static/images/system/index/favicon.ico" type="image/x-icon">
	<link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/static/images/system/index/favicon.ico" media="screen" />
	
	<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/chart/Highmaps-4.2.3/js/highcharts.js"></script>
	<script src="<%=request.getContextPath()%>/chart/Highmaps-4.2.3/js/modules/map.js"></script>
	<script src="<%=request.getContextPath()%>/chart/Highmaps-4.2.3/js/cn-all-sar-Feature.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/chart/Highmaps-4.2.3/js/modules/data.js"></script>
	<script src="<%=request.getContextPath()%>/chart/Highmaps-4.2.3/js/drilldown.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/sczt/index.js"></script>	
	<script src="<%=request.getContextPath()%>/static/js/sczt/highMapsInIndex.js" type="text/javascript"></script>
	
	<link rel="stylesheet" type="text/css"  href="<%=request.getContextPath()%>/static/css/trs/trs.css" media="screen"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/css/trs/loginWindow.css" media="all">
	
	<script>
		var contextPath = '<%=request.getContextPath()%>';
		var loginName = '${user.loginName}';
	</script>
<style type="text/css">
body{
	margin:0px;
	padding:0px;
	background-image: url(" <%=request.getContextPath()%>/page/sjbd/img/bgxx.png");
	background-repeat:repeat;
}
#Container{
    width:100%;
    margin:0 auto;/*设置整个容器在浏览器中水平居中*/
    /* background:#CF3; */
}
#Header{
    height:50px;
    background:#1c97ca;
    /* background:#093; */
}
#Content{
	width:100%;
    height:530px;
    /*此处对容器设置了高度，一般不建议对容器设置高度，一般使用overflow:auto;属性设置容器根据内容自适应高度，如果不指定高度或不设置自适应高度，容器将默认为1个字符高度，容器下方的布局元素（footer）设置margin-top:属性将无效*/
    /*margin-top:0px;此处讲解margin的用法，设置content与上面header元素之间的距离*/
}
#Content-Left{
    height:530px;
    width:35%;
    float:left;/*设置浮动，实现多列效果，div+Css布局中很重要的*/
}
#Content-Main{
	text-align:center;
    height:530px;
    width:65%;
    float:left;/*设置浮动，实现多列效果，div+Css布局中很重要的*/
}
/*注：Content-Left和Content-Main元素是Content元素的子元素，两个元素使用了float:left;设置成两列，这个两个元素的宽度和这个两个元素设置的padding、margin的和一定不能大于父层Content元素的宽度，否则设置列将失败*/

.Clear{
    clear:both;
}
.highcharts-container{
	margin: 0 auto;
	overflow:inherit;
}
.container2{
	text-align: center;
}
.selectpng{
    top: 6px;
    left: -210px;
    /* margin-bottom: 3px; */
    position: relative;
    z-index: 1000;

}

</style>
</head>
<body>

<div id="Container" >
    <div id="Header">
        <div class="headertop"  style="height:50px; width:100%;">
			 <img alt="" style="position:absolute;z-index:2;margin-left: 10px;" height="37" src="<%=request.getContextPath()%>/page/sjbd/img/12.png">
			<div class='header' style="position:absolute;z-index:10;">
				<ul id="nav" >
				</ul>
			</div>
		</div>
    </div>
	<!-- <div id="trsback" style="position:relative;height:298px;width:100%;"> background-color: beige-->
	<div id="trsback" style="position:relative;height:66px;width:100%;">
			<img alt="" id="imgContainer" height="620px" width="100%" style="position:absolute;" src="<%=request.getContextPath()%>/page/sjbd/img/8.png">
	
		<%-- <div style="float:left;bottom: 0;position:absolute;z-index:2;">
			<img style="bottom: 0;position:absolute;" width="371" height="246" src="<%=request.getContextPath()%>/static/images/system/index/b_071504.png"/>
		</div> --%>
		<div style="z-index:2; text-align:center;margin-left: auto;margin-right: auto;margin:auto;position:fix;">
			<%-- <img alt="广东省工商局" width="359" style="position:relative;z-index:2;"  height="126" src="<%=request.getContextPath()%>/static/images/system/index/b_0715.png"/> --%>
			<div id="xmkc" class="xmkc" style="position:relative;z-index:10;top:20px;">
				<div id="showTip" style="position: absolute;top:-20px;left:-2px;z-index:12;text-align: left;">
				<font style="color: red;">检索帮助</font>
				</div>
				<div name="class" style="z-index:99;" id="whole" class="wena" onClick="sh('hh');">市场主体</div><img class="selectpng"  onclick="sh('hh')" src="<%=request.getContextPath()%>/page/sjbd/img/32.png" />		
				<div id="hh" class="classlist" style="z-index:99;display:none;background:white;">
					<div class="lis"><a onClick="gets_value('市场主体')">市场主体 </a></div>
					<div class="lis"><a onClick="gets_value('12315')">12315</a></div>
					<div class="lis"><a onClick="gets_value('年度报告')">年度报告</a></div>
					<div class="lis"><a onClick="gets_value('案件信息')">案件信息</a></div>
				</div>classlist
				<input id="inputBox"  type="text" value="请输入内容" onfocus="javascript:if(this.value=='请输入内容')this.value='';">
				<input id="searchBtn" type="button" value="搜索" class="s_btn" onclick="allaction();">
				<div id="tipShow" style="background:white;display:none;width:730px;position: absolute;top:33px;left:-0px;z-index:3;text-align: left;">
					<font style="color: red;">温馨提示:</font><br>
					&nbsp;市场主体检索项：企业名称,注册号,统一信用代码,地址,经营范围,行业,法定代表人名称和证件号,高级人员名称和证件号,投资人名称和证件号,变更历史；<br>
					&nbsp;12315检索项：编号,申诉举报具体问题,受理登记人,事发地,关键字,姓名,地址,涉及主体经营地址,登记部门,涉及主体,商品名称；<br>
					&nbsp;年度报告检索项：企业名称,注册号,企业类型,通讯地址,企业状态,投资人,年报年度；<br>
					&nbsp;案件信息检索项：案件名称,案件编号,企业名称,案由,销案理由,案发地。<br>
				</div>
			</div><!--xmkc -->
		</div>
	</div>
    <div id="Content" style="clear:both;">
    
    	<div id="centerConten" style="z-index:2;position:absolute;height:auto;width:100%;text-align: center;">
			<div id="container2" style="z-index:1;" width="1120" height="auto"></div>
			<div id="container" style="float:left;z-index:2;display: none;" height="auto" width="20%"></div>
			<iframe style="z-index:2;position:absolute;display: none;left: 0;" id="contloads" name="contloads" scrolling="no" frameBorder="no" height="auto"></iframe>
		</div>
  		<div class="results" style="display:none"></div>
		<div class="p" style="display:none"></div>
    </div>


<div class="theme-popover">
     <div class="theme-poptit">
        <a href="javascript:;" title="关闭" class="close">×</a>
        <h3>欢迎您，请登陆</h3>
     </div>
     <div class="theme-popbod dform">
        <ol class="theme-signin">
	        <li><strong>用户名：</strong><input id="dlm" class="ipt" type="text" name="dlm" value="请输入账号" size="20" /></li>
	        <li><strong>密码：</strong><input id="mm" class="ipt" type="password" name="mm" value="" size="20"/> </li>
	        <li><input class="btn btn-primary"  name="submit" onclick="login()" value=" 登 录 " /><span id='login_msg'></span></li>
	        
        </ol>	
     </div>
</div>
<div class="theme-popover-mask"></div>
<p style="width:100%;height:30px;line-height:30px; text-align:center;vertical-align: middle;font-family: '微软雅黑,宋体';color: white-space: border;font-size: 14px;color: white;background-color: #26363b;position: fixed;bottom: 0px;z-index:100;">版权所有：广东省工商行政管理局  地址：广州市天河区体育西路57号红盾大厦  邮编：510620  技术支持：长城计算机软件与系统有限公司   ICP备案号：粤ICP备05028****号*1   网站备案编码：44010****1622</p>    </div>
</body>
</html>