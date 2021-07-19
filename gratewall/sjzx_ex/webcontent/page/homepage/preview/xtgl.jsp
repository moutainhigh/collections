<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import="cn.gwssi.common.context.Recordset"%>
<%@ page import="cn.gwssi.common.context.vo.VoUser"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="cn.gwssi.common.context.TxnContext"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<freeze:html>
<head>
<title>深圳市市场和质量监督管理委员会</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/FusionCharts/FusionCharts.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script><script type="text/javascript" src="/script/lib/jquery171.js"></script>
<script type="text/javascript" src="/script/daterangepicker/js/jquery-ui-1.7.1.custom.min.js"></script>
<script type="text/javascript" src="/script/daterangepicker/js/daterangerpicker.jQuery.preview.js"></script>
<link rel="stylesheet" href="/script/daterangepicker/css/ui.daterangepicker.css" type="text/css" />
<link rel="stylesheet" href="/script/daterangepicker/css/redmond/jquery-ui-1.7.1.custom.css" type="text/css" title="ui-theme" />
<link href="<%=request.getContextPath()%>/css/homepage/style.css" rel="stylesheet" type="text/css" />

<%
	DataBus context = (DataBus) request.getAttribute("freeze-databus");
    String obj_str=context.getValue("obj_str");
    DataBus record = context.getRecord("service-record");
	DataBus chart = context.getRecord("service-chart");
	String chartXML = chart.getValue("chartShareserviceXML");	
	DataBus select = context.getRecord("select-key");
	String show_type=StringUtils.isBlank(select.getValue("show_type"))? "service-chart" :select.getValue("show_type");
	
	DataBus drecord = context.getRecord("detail-record");
	DataBus dchart = context.getRecord("detail-chart");
	String chartdetXML = dchart.getValue("chartSharedetailXML");
	String dshow_type=StringUtils.isBlank(select.getValue("dshow_type"))? "detail-chart" :select.getValue("dshow_type");
	
	DataBus ctrecord = context.getRecord("coltask-record");
	DataBus ctchart = context.getRecord("coltask-chart");
	String chartCollectXML = ctchart.getValue("chartColTaskXML");
	String ctshow_type=StringUtils.isBlank(select.getValue("ctshow_type"))? "coltask-chart" :select.getValue("ctshow_type");
	
	DataBus cdrecord = context.getRecord("coldetail-record");
	DataBus cdchart = context.getRecord("coldetail-chart");
	String chartCollectDetXML = cdchart.getValue("chartColdetailXML");
	String cdshow_type=StringUtils.isBlank(select.getValue("cdshow_type"))? "coldetail-chart" :select.getValue("cdshow_type");
	
	DataBus chartData = context.getRecord("chart");
	String chartXML_share = chartData.getValue("chartXML");
	DataBus db1 = context.getRecord("serviceExchange-data");
	String avg_share_count_rate = db1.getValue("avg_share_count_rate");
	String avg_share_num_rate = db1.getValue("avg_share_num_rate");
	String avg_collect_count_rate = db1.getValue("avg_collect_count_rate");
	String avg_collect_num_rate = db1.getValue("avg_collect_num_rate");
%>

<style>
.grid {
	border-collapse: collapse;
	font-size: 12px;
	margin: 10px 5px 5px 5px;
}

.grid .grid-headrow td{
	border: 1px solid #ccf;
	font-size: 13px;
}

.grid td {
	border-collapse: collapse;
	font-size: 12px;
	border: 1px solid #ccf;
	height: 25px;
}

.choose_table td {
	background-color: #fefefe;
	border-collapse: collapse;
	font-size: 12px;
	border: 1px solid #69c;
	height: 22px;
	font-weight: normal;
}
.xcharts{display:inline; width:49.5% !important; height: 100% !important; border: 1px solid #ccf;float:left;}

.allTheme {width:99%;margin: 2px 5px 0; border: 1px solid #ddd; background: #f8f8f8;padding:5px 0px;}
.allTheme .line{margin: 0 5px; color: #666;}
.allTheme a {padding: 2px 4px;}
.allTheme a:hover{color:white !important;background:#39f; text-decoration:none;}
.allTheme .nSelected {color:white !important;background:#39f; }
.allTheme .title {
	padding: 5px;
	background: #e4ebf4;
	color: #333;
	font-size: 13px;
	border: 1px solid #ccf;
}

.ui-helper-clearfix{display:inline;}
.ui-rangepicker-input{width:160px !important;height: 1.1em !important; margin-right: 16px !important;}
.ui-daterangepicker-arrows{width: 200px !importaant;}
.ui-rangepicker-input{height: 1.1em !important;}
</style>
<script>

	$(function() {
		$("#filterName").keyup(
				function() {
					$("#choose_obj table tbody tr").hide().filter(
							":contains('" + ($(this).val()) + "')").show();
				}).keyup();
		
	});
	
	function toChangeVal(selVal){
		$('#choose_obj').hide();
		$('.choose_table td').removeClass("selNode");
		$('.choose_table td#'+selVal).addClass("selNode");
		$('#filterName').val($('.choose_table td#'+selVal).text());
		get_shareLogPreview();
	}
	
	function toShow() {
		//调整过滤框的位置
		$('#choose_obj').css("top", "20px");
		$('#choose_obj').css("left", $('#filterName').width()-$('#choose_obj').width());
		$('#choose_obj').show();
	}
    var obj_str=eval('('+'<%=obj_str%>'+')');
	
	function __userInitPage(){
		var obj_type_html="";
		//构造第一行 对象类型
		for(var i=0;i<obj_str.length;i++){
			obj_type_html+='<a href="javascript:getObj(\''+obj_str[i].name+'\', '+i+');">'+obj_str[i].name+'('+obj_str[i].data.length+')</a>';
			if(i!=obj_str.length-1){
				obj_type_html+='<span class="line">|</span>';
			}
		}
		$('#span_obj_type').html(obj_type_html);
		//构造第二行 服务对象
		if(obj_str.length>=1){
			var obj_html="";
			var obj=obj_str[0].data;
			for(var i=0;i<obj.length;i++){
				obj_html+='<a id="'+obj[i].service_targets_id+'" href="javascript:getResObjNum(\''+obj[i].service_targets_name+'\','+obj[i].share_num+','+obj[i].collect_num+', '+i+');">'+obj[i].service_targets_name+'('+(parseInt(obj[i].share_num)+parseInt(obj[i].collect_num))+')</a>';
				if(i!=obj.length-1){
					obj_html+='<span class="line">|</span>';
				}
			}
			$('#span_obj').html(obj_html);
			var obj_num_html="";
			if(obj.length>=1){
				if(obj[0].share_num != 0){
					obj_num_html+='<a href="javascript:getServiceInfo(\'share\');">共享服务个数('+obj[0].share_num+')</a>';
				}
				if(obj[0].collect_num != 0){
					if(obj[0].share_num != 0){
						obj_num_html+='<span class="line">|</span>';
					}
					obj_num_html+='<a href="javascript:getServiceInfo(\'collect\');">采集任务个数('+obj[0].collect_num+')</a>';
				}
				$('#span_obj_num').html(obj_num_html);
				if($('#span_obj_num a:first').length == 1){
					$('#span_obj_num a:first')[0].click();
				}
			}
		}
		$('#span_obj_type a:first')[0].click();
		$('#span_obj a:first')[0].click();
		//getServiceInfo('share');
		if($('#span_obj_num a:first').length == 1){
			$('#span_obj_num a:first')[0].click();
		}
		
		var tbl_html = "<table class='choose_table' width='95%' align='left' border='1'><tbody>";
		var obj_array=new Array;
		for(var i=0;i<obj_str.length;i++){
			for(var j=0;j<obj_str[i].data.length;j++){
				if(obj_str[i].data[j].share_num!='0'){
					obj_array.push([obj_str[i].data[j].service_targets_id,obj_str[i].data[j].service_targets_name]);
				}
			}
		}
		for ( var i = 0; i < obj_array.length; i++) {
			tbl_html += "<tr><td align='left' style='cursor:hand;' id='"+obj_array[i][0]+"' onclick='toChangeVal(\""+obj_array[i][0]+"\");'>" + obj_array[i][1]
					+ "</td></tr>";
		}
		tbl_html += "</tbody></table>";
		$('#choose_obj').html(tbl_html);
		
		/* $('#choose_date').daterangepicker({
			arrows:true,
			onClose:function(){
				get_shareLogPreview();
				/* var posW = new Object();
				posW.left = $('.wrapper div.ui-daterangepicker-arrows').offset().left;
				posW.top = $('.wrapper').offset().top + ($('#choose_date').height() * 2);
				$('.ui-daterangepickercontain').offset(posW); */
				/*	},
			appendTo: '.wrapper',
			posX: $('.wrapper').offset().left, // x position
			posY: ($('.wrapper').offset().top + ($('#choose_date').height() * 2)) // y position
		});
		$('ui-icon-circle-triangle-w').text('');
		$('ui-icon-circle-triangle-e').text('');
		$('a.ui-daterangepicker-prev').bind("click", function(){
			get_shareLogPreview();
		});
		$('a.ui-daterangepicker-next').bind("click", function(){
			get_shareLogPreview();
		}) */
		/* var posW = new Object();
		posW.left = $('.wrapper div.ui-daterangepicker-arrows').offset().left;
		posW.top = $('.wrapper').offset().top + ($('#choose_date').height() * 2);
		$('.ui-daterangepickercontain').offset(posW); */
	}
	
	//第一级 点击 动态组织 第二层和第三层
    function getObj(str, index){
    	var obj=getResObj(str);
    	$('#span_obj_type a').removeClass('nSelected');
    	$('#span_obj_type').find("a:eq("+index+")").addClass('nSelected');
    	var obj_html="";
		for(var i=0;i<obj.length;i++){
			obj_html+='<a id="'+obj[i].service_targets_id+'" href="javascript:getResObjNum(\''+obj[i].service_targets_name+'\','+obj[i].share_num+','+obj[i].collect_num+', '+i+');">'+obj[i].service_targets_name+'('+(parseInt(obj[i].share_num)+parseInt(obj[i].collect_num))+')</a>';
			if(i!=obj.length-1){
				obj_html+='<span class="line">|</span>';
			}
		}
		$('#span_obj').html(obj_html);
		$('#span_obj a:first')[0].click();
		//getServiceInfo('share');
		if($('#span_obj_num a:first').length == 1){
			$('#span_obj_num a:first')[0].click();
		}
    }
    
   //根据key 获取服务对象 服务个数
   function getResObjNum(str,share_num,collect_num, index){
	   $('#span_obj a').removeClass('nSelected');
	   $('#span_obj').find('a:eq('+index+')').addClass('nSelected');
	   var obj_num_html="";
	   if(share_num != 0){
	   	obj_num_html+='<a  href="javascript:getServiceInfo(\'share\');">共享服务个数('+share_num+')</a>';
	   }
	   	if(collect_num != 0){
	   		if(share_num != 0){
	 	   obj_num_html+='<span class="line">|</span>';
	 	   }
		   obj_num_html+='<a href="javascript:getServiceInfo(\'collect\');">采集任务个数('+collect_num+')</a>';
	   	}
	   $('#span_obj_num').html(obj_num_html);
	   /* if(share_num != 0){
			getServiceInfo('share');
		}else if(collect_num != 0){
			getServiceInfo('collect');
		} */
		if($('#span_obj_num a:first').length == 1){
			$('#span_obj_num a:first')[0].click();
		}
    }
	
   //根据key 获取服务对象 js对象
   function getResObj(str){
   	for(var i=0;i<obj_str.length;i++){
			if(obj_str[i].name==str){
				return obj_str[i].data;
			}
		}
   }

	function getServiceInfo(type) {
		//获取所选择的服务对象
		//var objId=getSelectObj();
		var targetId = $('#span_obj a.nSelected').attr('id');
		//console.log("service_target_id: "+targetId);
		var url = "<%=request.getContextPath()%>/txn53000013.do?select-key:service_targets_id=" + targetId;
		$('#span_obj_num a').removeClass('nSelected');
		if (type == 'share') {
			$('#span_obj_num a:eq(0)').addClass('nSelected');
			url = "<%=request.getContextPath()%>/txn53000012.do?select-key:service_targets_id=" + targetId;
		} else {
			$('#span_obj_num a:eq(1)').addClass('nSelected');
			url = "<%=request.getContextPath()%>/txn53000013.do?select-key:service_targets_id=" + targetId;
		}
		var txtStr = $('#span_obj_num a.nSelected').text();
		if(txtStr.match(/(\(|\（)0(\)|\）)$/g)){
			$('#exSvrFrame').hide();
		}else{
			$('#exSvrFrame').attr("src", url);
			$('#exSvrFrame').show();
		}
	}
	
	function toDetail(code1,code2){
		var page = new pageDefine( "/txn53000011.ajax", "共享分布情况详细信息" );
		page.addValue(code1,"detail-record:serviceTypeId");
		page.addValue(code2,"detail-record:serviceTypeName");
		page.callAjaxService("checkTaskBack");
	}

	function checkTaskBack(errorCode, errDesc, xmlResults){
		if(errorCode!="000000"){
			alert(errDesc);
		}else{
		    var detailxml = _getXmlNodeValue(xmlResults,"detail-chart:chartSharedetailXML");
		    var myChart = new FusionCharts( "/FusionCharts/Bar2D.swf", "c001", "100%", "250", "0", "1" );
		    var xml=detailxml;
		   // alert(dxml);
		    myChart.setXMLData(xml);  
		    myChart.render("chartdetContainer");
		}
	}
	
	function toCollectDetail(code1,code2){
		var page = new pageDefine( "/txn53000014.ajax", "采集服务情况详细信息" );
		page.addValue(code1,"coldetail-record:cdserviceTypeId");
		page.addValue(code2,"coldetail-record:cdserviceTypeName");
		page.callAjaxService("collectBack");	
		
	}

	function collectBack(errorCode, errDesc, xmlResults){
		if(errorCode!="000000"){
			alert(errDesc);
		}else{
		    var detailxml = _getXmlNodeValue(xmlResults,"coldetail-chart:chartColdetailXML");
		    var myChart = new FusionCharts( "/FusionCharts/Area2D.swf", "c001", "100%", "255", "0", "1" );
		    var xml=detailxml;
		    myChart.setXMLData(xml);  
		    myChart.render("chartCollectdetContainer");
		}
	}
	
	function get_shareLogPreview()
	{
		//alert(targetId + " ---- " + arguments.length);
		var service_targets_id = "";
		var startTime = "";
		var endTime = "";
		if($('.choose_table td.selNode').length == 1){
			service_targets_id = $('.choose_table td.selNode').attr('id');
		} 
		/* var dayAry = getWeekStartDate();
		startTime = dayAry[0];
		endTime = dayAry[1]; */
		/* var selTime = $('#choose_date').val();
		selTime = selTime.split('至');
		if(selTime && selTime.length>0 && selTime[0].trim() != ''){
			startTime = selTime[0];
		}
		if(selTime.length == 2){
			endTime = selTime[1].trim();
		}else{
			endTime = startTime;
		}  */
		
		var page = new pageDefine( "/txn53000015.ajax", "共享数据量调用次数趋势图" );
		page.addValue(service_targets_id,"select-key:service_targets_id");
		//page.addValue(startTime,"select-key:startTime");
		//page.addValue(endTime,"select-key:endTime");
		page.callAjaxService("getChartData");
	}
	function getChartData(errorCode, errDesc, xmlResults){
		if(errorCode!="000000"){
			alert(errDesc);
		}else{
			var chartXML = _getXmlNodeValue(xmlResults,"chart:chartXML");
			var myChart = new FusionCharts("<%=request.getContextPath()%>/FusionCharts/MSColumn3DLineDY.swf", "ChartId", "100%", "200", "0", "0");
		    myChart.setXMLData(chartXML);		    
			myChart.render("chartContainer_share"); 
		}
	}
/* 	//获得本周的开始日期,结束日期     
	function getWeekStartDate() {      
		var now = new Date();                    
		var nowDayOfWeek = now.getDay();     
		var nowDay = now.getDate();           
		var nowMonth = now.getMonth();    
		var nowYear = now.getYear();    
		nowYear += (nowYear < 2000) ? 1900 : 0;  //  
	    var weekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek);
	    var weekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek));      
	    return [formatDate(weekStartDate),formatDate(weekEndDate)]; 
	}      
 */
	_browse.execute('__userInitPage()');
</script>
</head>
<freeze:body>
	<table align="center" cellpadding="0" cellspacing="0" style="width: 98%">
		<tr>
			<td height="210px" width="35%" align="center" valign="top">
					<table width="98%" border="0" style="margin-left:0;margin-right:0;" class="grid">
					<tr class="grid-headrow"><td align='left' colspan="4">交换服务情况统计(当月)</td></tr>
						<tr class="evenrow">
							<td width="35%" align="right">共享服务数量：</td>
							<td width="15%"><freeze:out value="${serviceExchange-data.share_serv_num}"/>个</td>
							<td width="35%" align="right">本月新增共享服务数量：</td>
							<td width="15%"><freeze:out value="${serviceExchange-data.share_month_add}"/>个</td>
						</tr>
						<tr class="oddrow">
							<td align="right">采集服务数量：</td>
							<td><freeze:out value="${serviceExchange-data.collect_serv_num}"/>个</td>
							<td align="right">本月新增采集服务数量：</td>
							<td><freeze:out value="${serviceExchange-data.collect_month_add}"/>个</td>
						</tr>
						<tr class="evenrow">
							<td align="right">日均共享数据量：</td>
							<td><freeze:out value="${serviceExchange-data.avg_share_count}"/>条</td>
							<td align="right">环比增加：</td>
							<td><freeze:out value="${serviceExchange-data.avg_share_count_rate}"/>%
								<%if(avg_share_count_rate.contains("-")){ %>
								<img src="<%=request.getContextPath()%>/images/down.png"/>
								<%}else { %>
								<img src="<%=request.getContextPath()%>/images/up.png"/>
								<%} %>
							</td>
						</tr>
						<tr class="oddrow">
							<td align="right">日均共享次数：</td>
							<td><freeze:out value="${serviceExchange-data.avg_share_num}"/>次</td>
							<td align="right">环比增加：</td>
							<td><freeze:out value="${serviceExchange-data.avg_share_num_rate}"/>%
								<%if(avg_share_num_rate.contains("-")){ %>
								<img src="<%=request.getContextPath()%>/images/down.png"/>
								<%}else { %>
								<img src="<%=request.getContextPath()%>/images/up.png"/>
								<%} %>
							</td>
						</tr>
						<tr class="evenrow">
							<td align="right">日均采集数据量：</td>
							<td><freeze:out value="${serviceExchange-data.avg_collect_count}"/>条</td>
							<td align="right">环比增加：</td>
							<td><freeze:out value="${serviceExchange-data.avg_collect_count_rate}"/>%
								<%if(avg_collect_count_rate.contains("-")){ %>
								<img src="<%=request.getContextPath()%>/images/down.png"/>
								<%}else { %>
								<img src="<%=request.getContextPath()%>/images/up.png"/>
								<%} %>
							</td>
						</tr>
						<tr class="oddrow">
							<td align="right">日均采集次数：</td>
							<td><freeze:out value="${serviceExchange-data.avg_collect_num}"/>次</td>
							<td align="right">环比增加：</td>
							
							<td><freeze:out value="${serviceExchange-data.avg_collect_num_rate}"/>%
							<%if(avg_collect_num_rate.contains("-")){ %>
								<img src="<%=request.getContextPath()%>/images/down.png"/>
								<%}else { %>
								<img src="<%=request.getContextPath()%>/images/up.png"/>
								<%} %>
							</td>
						</tr>
					</table>
			</td>
			<td height="210px" align="center" >
				<table width="99%" align="left" style="margin-left:0;margin-right:0;" valign="top" border="0" class="grid">
					<tr class="grid-headrow">
						<td align="left" width="20%">服务情况统计</td>
						<td align="right" style="display:none;">
						<div style="display:inline;" class="wrapper">选择时间：<input id="choose_date"
							size="10" /></div></td>
							<td>服务对象：<div style="position:relative;width:100px;display:inline;"><input id="filterName" size="10"
							onfocus="toShow();" /><div id="choose_obj" 
					style="height: 200px; width: 150px; background: white; 
					overflow-y: auto; overflow-x: hidden; z-index:200; display: none; 
					position:absolute;left: 0; top: 25px;"></div></div>
						</td>
					</tr>
					<tr>
						<td colspan="3" valign="top">
							<table width="100%" border='0' align="center" style="border-collapse:collapse:">
								<tr>
									<td width="40%" align="center" style='border:none;'>
							<table width="99%" class="grid" align="left"
								style="border-collapse: collapse;margin-top:0;
								margin-left:0;">
								<tr class="evenrow">
									<td width="18%" rowspan="2">内部系统</td>
									<td width="25%">共享服务个数</td>
									<td width="10%"><freeze:out value="${share_service_total.in_share_total}"/>个</td>
									<td width="15%">共享服务方式</td>
									<td style="white-space: normal;" width="25%"><freeze:out value="${share_service_type.in_share_type}"/></td>
								</tr>
								<tr class="oddrow">
									<td width="25%">采集任务个数</td>
									<td width="5%"><freeze:out value="${collect_service_total.in_collect_total}"/>个</td>
									<td width="20%">采集任务方式</td>
									<td style="white-space: normal;" width="25%"><freeze:out value="${collect_service_type.in_collect_type}"/></td>
								</tr>
								<tr class="evenrow">
									<td width="18%" rowspan="2">外部系统</td>
									<td width="25%">共享服务个数</td>
									<td width="10%"><freeze:out value="${share_service_total.out_share_total}"/>个</td>
									<td width="20%">共享服务方式</td>
									<td style="white-space: normal;" width="25%"><freeze:out value="${share_service_type.out_share_type}"/></td>
								</tr>
								<tr class="oddrow">
									<td width="25%">采集任务个数</td>
									<td width="5%"><freeze:out value="${collect_service_total.out_collect_total}"/>个</td>
									<td width="20%">采集任务方式</td>
									<td style="white-space: normal;" width="25%"><freeze:out value="${collect_service_type.out_collect_type}"/></td>
								</tr>
								<tr class="evenrow">
									<td width="18%" rowspan="2">区县分局</td>
									<td width="25%">共享服务个数</td>
									<td width="10%"><freeze:out value="${share_service_total.sheng_share_total}"/>个</td>
									<td width="20%">共享服务方式</td>
									<td style="white-space: normal;" width="25%"><freeze:out value="${share_service_type.sheng_share_type}"/></td>
								</tr>
								<tr class="oddrow">
									<td width="25%">采集任务个数</td>
									<td width="5%"><freeze:out value="${collect_service_total.sheng_collect_total}"/>个</td>
									<td width="20%">采集任务方式</td>
									<td style="white-space: normal;" width="25%"><freeze:out value="${collect_service_type.sheng_collect_type}"/></td>
								</tr>
							</table>
									</td>
									<td align="left" style='border:none;'>
										<table width="100%" class="grid" align="left"
								style="border-collapse: collapse;margin-top:0;
								margin-left:0;">
								<tr><td style="padding-right:2px;">
								<div id='chartContainer_share'></div>
	<script type="text/javascript">  
	  var myChart = new FusionCharts("<%=request.getContextPath()%>/FusionCharts/MSColumn3DLineDY.swf", "ChartId", "100%", "200", "0", "0");
      myChart.setXMLData("<%=chartXML_share%>");		    
	  myChart.render("chartContainer_share");        
    </script>
    
								
								</td></tr>
								</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td valign="top" align="left" colspan="2">
			 <div style="width:99%; margin:5px 0 5px;">
			 	<div style="margin:0 5px;height:25px;line-height:25px;color:#555;font-weight:bold; background:#e4ebf4;border:1px solid #ccf;font-size:12px;">
			 		&nbsp;&nbsp;交换服务概览</div>
				<div class="allTheme" id="obj_type">
					<span style="margin-left: 5px;" class="title">对象类型</span> 
					<span id="span_obj_type" class="content"></span>
				</div>
				<div class="allTheme" id="obj">
					<span style="margin-left: 5px;" class="title">服务对象</span>
					<span id="span_obj" class="content"></span>
				</div>
				<div class="allTheme" id="service_num">
					<span style="margin-left: 5px;" class="title">服务个数</span> 
					<span id="span_obj_num" class="content"></span>
				</div>
				<iframe height="300px" style="height:250px; width:99%;display:none;" id='exSvrFrame'></iframe>
           </div>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center" valign='top'>
				<table width="98%" border="0"
					style="margin-left: 0; margin-right: 0;" class="grid">
					<tr class="grid-headrow">
						<td align='left' colspan="4">共享分布情况统计</td>
					</tr>
					<tr class="evenrow">
						<td style="height:260px !important;" align="center">
							<div class="xcharts" id="chart_u_l">
<%if(!record.isEmpty()){if(show_type.equals("service-chart")){%>
	<div id="chartContainer" align="left"></div>
	<script>
	    var myChart = new FusionCharts( "/FusionCharts/Pie3D.swf", "c001", "100%", "250", "0", "1" );
	    var xml="<%=chartXML%>";
	    myChart.setXMLData(xml);  
	    myChart.render("chartContainer");   
	</script>
<%}}else{%>
  <div style="font-size: 14px;text-align: center;">查无相关数据！</div>
<%}%></div>
<div class="xcharts" style="float:right;" id="chart_u_r">
<%if(!drecord.isEmpty()){
	if(dshow_type.equals("detail-chart")){%>
	<div id="chartdetContainer" align="left"></div>  
	<script>
	    var myChart = new FusionCharts( "/FusionCharts/Bar2D.swf", "c001", "100%", "250", "0", "1" );
	    var xml="<%=chartdetXML%>";
	    myChart.setXMLData(xml);  
	    myChart.render("chartdetContainer");   
	</script>
<%}}else{%>
  <div style="font-size: 14px;text-align: center;">查无相关数据！</div>
<%}%>
</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center" valign='top'>
				<table width="99%" border="0"
					style="margin-left: 0; margin-right: 0;" class="grid">
					<tr class="grid-headrow">
						<td align='left' colspan="4">采集任务情况统计</td>
					</tr>
					<tr class="evenrow">
						<td style="height:260px !important;" align="center">
							<div class="xcharts" id="chart_d_l">
<%
if(!ctrecord.isEmpty()){
	if(ctshow_type.equals("coltask-chart")){%>
	<div id="chartCollectContainer" align="left"></div>
	<script>
	    var myChart = new FusionCharts( "/FusionCharts/Bar2D.swf", "c001", "100%", "255", "0", "1" );
	    var xml="<%=chartCollectXML%>";
	    myChart.setXMLData(xml);  
	    myChart.render("chartCollectContainer");   
	</script>
<%}}else{%>
  <div style="font-size: 14px;text-align: center;">查无相关数据！</div>
<%}%>

</div>
<div class="xcharts" style="float:right;" id="chart_d_r">
<%
if(!cdrecord.isEmpty()){
	if(cdshow_type.equals("coldetail-chart")){%>
	<div id="chartCollectdetContainer" align="left"></div>  
	<script>
	    var myChart = new FusionCharts( "/FusionCharts/Area2D.swf", "c001", "100%", "255", "0", "1" );
	    var xml="<%=chartCollectDetXML%>";
	    myChart.setXMLData(xml);  
	    myChart.render("chartCollectdetContainer");   
	</script>
<%}}else{%>
  <div style="font-size: 14px;text-align: center;">查无相关数据！</div>
<%}%>
</div></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</freeze:body>
</freeze:html>
