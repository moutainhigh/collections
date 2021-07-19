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
	

	

	

	_browse.execute('__userInitPage()');
</script>
</head>
<freeze:body>
	<freeze:title caption="服务配置概览" />
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
	
</freeze:body>
</freeze:html>
