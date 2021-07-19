<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title>案件基本信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>

<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/static/css/jbxx/detial.css" />

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
a{ 
text-decoration:none;
color: #666;
font-size: 12px;
font-family: "瀹嬩綋";
font-weight:bold;
word-wrap: break-word;
}
/* a:hover {
	color: red;
}
 */



/*重新修改jazz*/
.jazz-panel-titlebar{background:url(../../static/script/JAZZ-UI/lib/themes/default/images/titlebackground.png) repeat-x;height:17px;line-height:17px;}
.jazz-panel-titlebar a{font-size: 16px;font-family: "微软雅黑";color:#327bb9;}
#tabs{border:none;border-collapse: collapse; table-layout: fixed; width: 100%;margin: 0;padding:0}
#tabs tbody tr{border: none;}

</style>

<script type="text/javascript">
$(function(){
	var caseid=getUrlParam("caseid");
	$.ajax({
		url:rootPath+'/caseinfo/caseinfojbxx.do',
		data:{
			caseid : caseid
		},
		type:"post",
		dataType : 'json',
		success:function(data){
			//var st = '<tr><td style="text-align: right;">企业(机构)名称:</td><td>佛山市高明区荷城谁见谁爱服饰店</td><td style="text-align: right;">企业(机构)名称:</td><td>佛山市高明区荷城谁见谁爱服饰店</td></tr><tr><td style="text-align: right;">企业(机构)名称:</td><td colspan="3">佛山市高明区荷城谁见谁爱服饰店</td></tr>';
			$('#tabs tbody').append(data.returnString);
			$('#tabs tbody tr:even').css("background","#EEF6F9");//奇数行
			$('#tabs tbody tr:odd').css("background","#FBFDFC");//偶数行
			$('#casename').empty();
			$('#casename').append("案件名称:"+data.casename);
			$('#caseno').empty();
			$('#caseno').append("案件编号:"+data.caseno);
			$('#entnameurl').empty();
			$('#entnameurl').html(data.entnameurl);
		}
	});
});
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(decodeURI(r[2])); return null; //返回参数值 
}
function marketObject(params){
	var url = rootPath+'/page/trs/panoramicAnalysis.jsp?'+params+'&open=sczt';
	//openWindow(\''+contextPath+'/page/trs/panoramicAnalysis.jsp?'+pagedata[i].url+'&open=sczt'+'\')" href="#" >'+pagedata[i].entName+'&nbsp;&nbsp;'+pagedata[i].regNo+'</a></h3>'+
	window.open(encodeURI(url));
}
</script>
</head>
<body>
<div name="row_id" height="100%"   vtype="panel" layout="row" layoutconfig="{rowheight:['50px','*','30px']}">
	<div id="Header" style="background: #1c97ca">
        <div class="headertop"  style="height:50px; width:100%;">
			 <img alt="" style="position:absolute;z-index:2;margin-left: 10px;" height="37" src="<%=request.getContextPath()%>/page/sjbd/img/12.png">
			<div class='header' style="position:absolute;z-index:10;">
				<ul id="nav" >
				</ul>
			</div>
		</div>
    </div>
	<div id="tab1" style="width: 99.5%;height:auto;padding-left: 2px;">
		<div style="width: 100%;height:auto;" id="content">
			<div class="jazz-panel-titlebar jazz-panel-header jazz-formpanel-titlebar">
				<div class="jazz-formpanel-titlebar-inner" style="width:100%; height:100%">
					<span class="jazz-panel-title-font jazz-formpanel-title-font">
					<a id="casename">案件名称:</a>
					<a id="caseno" style="float:right;">案件编号:</a></span>
					<span class="jazz-panel-rtl-left" style=""></span>
					<span class="jazz-panel-rtl-right" style=""></span>
				</div>
			</div>
			<table id="tabs" >
				<!-- <colgroup>
					<col align="right" height="40px" width="119px" style="vertical-align: middle;background-color:#DCEEEF;border:1px solid #AFB0B1;border-left: 0px solid #AFB0B1;">
					<col width="255px" style="vertical-align: middle;background-color:white;border:1px solid #AFB0B1;">
					<col align="right" height="40px" width="119px" style="vertical-align: middle;background-color:#DCEEEF;border:1px solid #AFB0B1;">
					<col width="255px" style="vertical-align: middle;background-color:white;border:1px solid #AFB0B1;">
				</colgroup> -->
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
	<div style="margin-top: 10px;"><p style="width:100%;height:30px;line-height:30px; text-align:center;vertical-align: middle;font-family: '微软雅黑,宋体';color: white-space: border;font-size: 14px;color: white;background-color: #26363b;position: fixed;bottom: 0px;z-index:100;">版权所有：广东省工商行政管理局  地址：广州市天河区体育西路57号红盾大厦  邮编：510620  技术支持：长城计算机软件与系统有限公司   ICP备案号：粤ICP备05028****号*1   网站备案编码：44010****1622</p></div>
</div>
</body>
</html>