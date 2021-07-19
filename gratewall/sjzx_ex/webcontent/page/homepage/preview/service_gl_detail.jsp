<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import="cn.gwssi.common.context.Recordset"%>
<%@ page import="cn.gwssi.common.context.vo.VoUser"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="java.util.Enumeration"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="cn.gwssi.common.context.TxnContext"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<freeze:html>
<head>
<title>�������г��������ල����ίԱ��</title>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/script/FusionCharts/FusionCharts.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="/script/lib/jquery171.js"></script>
<script type="text/javascript"
	src="/script/daterangepicker/js/jquery-ui-1.7.1.custom.min.js"></script>
<script type="text/javascript"
	src="/script/daterangepicker/js/daterangerpicker.jQuery.preview.js"></script>
<link rel="stylesheet"
	href="/script/daterangepicker/css/ui.daterangepicker.css"
	type="text/css" />
<link rel="stylesheet"
	href="/script/daterangepicker/css/redmond/jquery-ui-1.7.1.custom.css"
	type="text/css" title="ui-theme" />
<link href="<%=request.getContextPath()%>/css/homepage/style.css"
	rel="stylesheet" type="text/css" />

<%
	DataBus context = (DataBus) request.getAttribute("freeze-databus");
		String obj_str = context.getValue("obj_str");
		String select_obj = context.getRecord("select-key").getValue(
				"service_targets_id") == null ? "" : context.getRecord(
				"select-key").getValue("service_targets_id");
		String select_name = context.getRecord("select-key").getValue(
				"service_targets_name") == null ? "" : context.getRecord(
				"select-key").getValue("service_targets_name");
		%>

<style>
.nav {margin:0 auto; text-align:center; font-weight:bold; border-bottom:3px solid #579cc6;}
.nav a {display:inline-block; margin:0 3px; height:25px; background:url(/page/zwt/images/bg-index.jpg) left bottom no-repeat; padding-left:15px; color:#666; text-decoration:none; cursor:pointer;}
.nav a span {display:inline-block; height:25px; line-height:25px; background:url(/page/zwt/images/bg-index.jpg) right bottom no-repeat; padding-right:15px;}
.nav a.sel, .nav a:hover {background:url(/page/zwt/images/bg-index.jpg) left top no-repeat; color:#FFF;}
.nav a.sel span, .nav a:hover span {background:url(/page/zwt/images/bg-index.jpg) right top no-repeat;}
.nav a.set {background:url(/page/zwt/images/bg-index.jpg) left top no-repeat; color:#FFF; }
.nav a.set span {background:url(/page/zwt/images/texiao/bg-index.jpg) right top no-repeat;}

.grid {
	border-collapse: collapse;
	font-size: 12px;
	margin: 10px 5px 5px 5px;
}

.grid .grid-headrow td {
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

.xcharts {
	display: inline;
	width: 49.5% !important;
	height: 100% !important;
	border: 1px solid #ccf;
	float: left;
}

.allTheme {
	width: 99%;
	margin: 2px 5px 0;
	border: 1px solid #ddd;
	background: #f8f8f8;
	padding: 5px 0px;
}

.allTheme .line {
	margin: 0 5px;
	color: #666;
}

.allTheme a {
	padding: 2px 4px;
}

.allTheme a:hover {
	color: white !important;
	background: #39f;
	text-decoration: none;
}

.allTheme .nSelected {
	color: white !important;
	background: #39f;
}

.allTheme .title {
	padding: 5px;
	background: #e4ebf4;
	color: #333;
	font-size: 13px;
	border: 1px solid #ccf;
}

.ui-helper-clearfix {
	display: inline;
}

.ui-rangepicker-input {
	width: 160px !important;
	height: 1.1em !important;
	margin-right: 16px !important;
}

.ui-daterangepicker-arrows {
	width: 200px!importaant;
}

.ui-rangepicker-input {
	height: 1.1em !important;
}
div.diva{display:inline; cursor: pointer; white-space: nowrap; padding:3px;}
td div.selected{background-color:#354e8b; color:white; cursor: default;}
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
		//�������˿��λ��
		$('#choose_obj').css("top", "20px");
		$('#choose_obj').css("left", $('#filterName').width()-$('#choose_obj').width());
		$('#choose_obj').show();
	}
    var obj_str=eval('('+'<%=obj_str%>'+')');
    
    function getSelectObjById(select_obj){
    	for(var i=0;i<obj_str.length;i++){
    		var obj=obj_str[i].data;
			for(var j=0;j<obj.length;j++){
				if(obj[j].service_targets_id==select_obj){
					return obj[j];
				}
			}
		}
    	
    }
	

	//��һ�� ��� ��̬��֯ �ڶ���͵�����
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
    
   //����key ��ȡ������� �������
   function getResObjNum(str,share_num,collect_num, index){
	   $('#span_obj a').removeClass('nSelected');
	   $('#span_obj').find('a:eq('+index+')').addClass('nSelected');
	   var obj_num_html="";
	   if(share_num != 0){
	   	obj_num_html+='<a  href="javascript:getServiceInfo(\'share\');">����������('+share_num+')</a>';
	   }
	   if(collect_num != 0){
	   	   if(share_num != 0){
	 	     obj_num_html+='<span class="line">|</span>';
	 	   }
		   obj_num_html+='<a href="javascript:getServiceInfo(\'collect\');">�ɼ��������('+collect_num+')</a>';
	   }
	   obj_num_html+='<span class="line"><!-- |</span><a  href="javascript:getStatInfo();">����ͳ�Ʒ���</a> -->';
	   $('#span_obj_num').html(obj_num_html);
		if($('#span_obj_num a:first').length == 1){
			$('#span_obj_num a:first')[0].click();
	   }
    }
	
   //����key ��ȡ������� js����
   function getResObj(str){
   	for(var i=0;i<obj_str.length;i++){
			if(obj_str[i].name==str){
				return obj_str[i].data;
			}
		}
   }
   

	function getServiceInfo(type) {
		//��ȡ��ѡ��ķ������
		//var objId=getSelectObj();
		var select_obj='<%=select_obj%>';
		if(select_obj!=''){
			var targetId = select_obj;
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
			if(txtStr.match(/(\(|\��)0(\)|\��)$/g)){
				$('#exSvrFrame').hide();
			}else{
				$('#exSvrFrame').attr("src", url);
				$('#exSvrFrame').show();
			}
		}else{
			alert("������ѡ��һ���������");
			
		}
		
	}

	//�������Ĳɼ����񡢹������ͳ����ϸҳ��
	//�������ķ���ͼ��չʾ�����������������ݷֲ�
	function servicePart(){
		var svrId = '<%=select_obj%>';
		var svrName = '<%=select_name%>';
		document.getElementById('select-key:service_targets_id').value = svrId;
		document.getElementById('select-key:service_targets_name').value = svrName;
		document.getElementById('form1').submit();
	}


	function __userInitPage(){
		var obj_html="";
		var select_obj='<%=select_obj%>';
	   var url = "<%=request.getContextPath()%>/txn53000012.do?select-key:service_targets_id=" + select_obj;
	   $('#exSvrFrame').attr("src", url);
		$('#exSvrFrame').show();
	}
	
	_browse.execute('__userInitPage()');
</script>
</head>
<freeze:body>
<table border=0 cellpadding=0 cellspacing=0 width="95%" align="center" style="border-collapse: collapse;">
<tr><td>
<h1><%=select_name %></h1>
</td></tr>
<tr><td>      ���û�������2009��9��2�գ��ṩ�����ɼ��ӿڷ��񡣽�ֹ����ǰ�й��̾����˰���ṩ6�����ݽӿڷ����е�˰�����й��̾��ṩ6�����ݽӿڷ����漰6�����ݱ����ݣ��ֱ�Ϊ����˰�����õȼ�������Ƿ˰��Ϣ������˰��Ǽ���Ϣ���������������Ϣ������������Ϣ������������Ϣ����</td></tr>
</table>


<table border=0 cellpadding=0 cellspacing=0 width="95%" align="center" style="border-collapse: collapse;">
<tr>
<td align="center" style="padding-top:2px;" colspan='2'>
<table align="center" style="width:100%;border-collapse:collapse;" cellpadding="0" cellspacing="0">
	<tr><td>
	<div class="nav">
<a class="sel" href="javascript:;"><span>����������Ϣ</span></a>
<a href="javascript:;" onclick="servicePart()"><span>��������ͳ��</span></a>
</div>
	</td></tr>
</table>
</td></tr>
<tr><td>
	<div style="width: 100%; margin: 5px 0 5px;">
		<div
			style="margin: 0 5px;  line-height: 25px; color: #555; font-weight: bold; background: #e4ebf4; border: 1px solid #ccf; font-size: 12px;">
			&nbsp;&nbsp;˵������</div>

		<iframe height="300px"
			style="height: 250px; width: 99%; display: none;" id='exSvrFrame'></iframe>
	</div>
</td></tr>
	</table>
<form action="/txn53000112.do" method="post" id="form1" name="form1" target="_self">
  <input type="hidden" name="select-key:service_targets_id" id="select-key:service_targets_id"/>
  <input type="hidden" name="select-key:service_targets_name" id="select-key:service_targets_name"/>
</form>
</freeze:body>
</freeze:html>
