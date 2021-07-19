<%-- <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
--%>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import="cn.gwssi.common.context.Recordset"%>
<%@ page import="cn.gwssi.common.context.vo.VoUser"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="cn.gwssi.common.context.TxnContext"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8 ">
<!-- <meta http-equiv="X-UA-Compatible" content="IE=edge"> -->
<title>统计图</title>
<script src="<%=request.getContextPath() %>/page/zwt/js/jquery.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/page/zwt/js/jquery-ui.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/page/zwt/js/jquery.bgiframe-2.1.2.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/page/zwt/js/jquery-ui-i18n.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/page/zwt/js/d3.v3.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/page/zwt/js/jquery.tipsy.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/page/zwt/js/imdb.min.1.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/page/zwt/js/imdb.data.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/script/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" href="css/jquery-ui.css" type="text/css" media="all">
<link href="<%=request.getContextPath() %>/page/zwt/css/jquery.tipsy.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath() %>/page/zwt/css/imdb.css" rel="stylesheet" type="text/css">
<style type="text/css">
.collect_up, .share_down{width:100%; height:220px; /* border: 1px dashed gray; */}
.svrConfig{height:30px;/*  width: 100%;  */line-height: 30px;margin-top:2px; margin-bottom:5px; 
	 text-align: right; padding-right:30px;}
.radio_span, .cradio_span{ font-size: 10pt; margin-right: 5px; color: #369; cursor:pointer;white-space:nowrap; overflow-x:visible; display:inline-block;text-align: center;}
span.selected{border-bottom:4px solid #69c; background-color: #cef;}
body{overflow:hidden;}

</style>
<head>
<body>
<table style="width: 95%">
  <tr>
    <td width="200" valign="top">
      <div id="toolbox">
		<div id="actor_list">
			<select id="actor_selector" size="10" multiple=""> </select>
		</div>
		<br/>
		<Table style="width: 100%;font-size: 14px; color: #4682B4;text-align: center" >
		 <TR>
		       <td width="100%" class="odd12"align="left">
		        <div class="timezone tj">统计周期：
		        <label><input type="radio" name="time_type" value="00" onclick="changeTimezone('00');" checked="checked">日</label>
		       <!-- <label><input type="radio" name="time_type" onclick="changeTimezone('05');">周</label> -->
		        <label><input type="radio" name="time_type" value="02" onclick="changeTimezone('02');">月</label></div>
		        <div id="Day_zone" class="timezone" >
		      	 开始日期：<input style="width: 100px;" id="startDay" type="text" class="Wdate" onFocus="var endTimeInput=$dp.$('endDay');WdatePicker({onpicked:function(){endTimeInput.focus();},skin:'default',minDate:'2009-01-01',maxDate:'#F{$dp.$D(\'endDay\',{d:-1});}',vel:'startTime'})"/>
		      	 结束日期：<input style="width: 100px;" id="endDay" type="text" class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startDay\',{d:1});}',maxDate:'%y-%M-%d',vel:'endTime'})"/>
		      	 </div>
		      	 
		      	<div id="Month_zone" class="timezone" style="display:none;" >
		      	  开始日期：<input style="width: 100px;" id="startMonth" type="text" class="Wdate" onFocus="var endTimeInput=$dp.$('endMonth');WdatePicker({onpicked:function(){endTimeInput.focus();},dateFmt:'yyyy-MM',minDate:'2009-01',maxDate:'#F{$dp.$D(\'endMonth\',{M:-1});}',vel:'startTime'})"/>
     			  结束日期：<input style="width: 100px;" id="endMonth" type="text" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM',minDate:'#F{$dp.$D(\'startMonth\',{M:1});}',maxDate:'%y-%M',vel:'endTime'})"/>
		      	 </div>
		      	
		      	<div id="Year_zone" class="timezone" style="display:none;" >
		      	   <input style="width: 100px;" id="startYear" type="text" class="Wdate" onFocus="var endTimeInput=$dp.$('endYear');WdatePicker({onpicked:function(){endTimeInput.focus();},dateFmt:'yyyy年',minDate:'2009',maxDate:'#F{$dp.$D(\'endYear\',{y:-1});}',vel:'startTime'})"/>
      			   <input style="width: 100px;" id="endYear" type="text" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy年',minDate:'#F{$dp.$D(\'startYear\',{y:1});}',maxDate:'%y',vel:'endTime'})"/>
		      	 </div>
		      	
		      	<div id="Week_zone" class="timezone" style="display:none;" >
		      	  	开始日期：<input style="width: 100px;" id="startWeek" type="text" class="Wdate" onFocus="var endTimeInput=$dp.$('endWeek');WdatePicker({onpicked:function(){endTimeInput.focus();},minDate:'2009-01-01',maxDate:'%y-%M-%d',isShowWeek:true,onpicked:function() {$dp.$('startTime').value=$dp.cal.getP('W','W');}})"/>至
       				结束日期：<input style="width: 100px;" id="endWeek" type="text" class="Wdate" onFocus="WdatePicker({minDate:'2009-01-01',maxDate:'%y-%M-%d',isShowWeek:true,onpicked:function() {$dp.$('endTime').value=$dp.cal.getP('W','W');}})"/>
		      	 </div> 
		      	 <div class="timezone tj" style="float:right;padding-right:20px;" ><input style=" margin: 5px 10px;" type="button" onclick="setDate()" value="查 询" /></div>
		      	 <div style="clear:both;"></div>
		       </td>
		   </TR>
	</Table>
	</div>
    </td>
    <td><div id="graphHolder" style="background: #f1f2f4;width: 95%; height: 477px; "></div>
    </td>
  </tr>
</table>
<script type="text/javascript">
//var pos = 1; //纵坐标计算的依据， 根据纵轴的显示的内容不同，取不同的值 1数据量 2运行次数 3耗时

/**
 * type 图片的类型 采集 or 共享
 * position 对应需要展示指标在数组中的位置 1数据量 2运行次数 3耗时
 */
function drawShareLine(type){
	$('label.radio_span').removeClass("selected");
	if('share_single' == type){
		$('label.radio_span:eq(0)').addClass("selected");
	}else if('share' == type){
		$('label.radio_span:eq(1)').addClass("selected");
	}
	task_type = type;
//	null != d3.selectAll("#graph") && d3.selectAll("#graph").remove();
  //  $("#graphHolder").html("");
    var a = {
        top: 100,
        right: 40,
        bottom: 0,
        left: 50
    };
    //alert(gwidth);
    width = Math.round($(window).width() - 20 - $("#graphHolder").position().left) - a.left - a.right;
	$("#graphHolder").width(width).css({'overflow-x': "visible", 'overflow-y': "visible"});
    //$("#graphHolder").width(width);
    height = 420;
    $("#graphHolder").height(height);

    var c = null;
    "actors" == chart ? c = actors : "directors" == chart ? c = directors : "composers" == chart && (c = composers);
    dataObject = [];
    //从提供的数据中查找横坐标的起始结束日期
    //该数据将根据用户选择的日期范围决定
    //默认为近30天
    var b2 = dateAry[0].id;
    var d2 = dateAry[dateAry.length-1].id;
    //开始计算横坐标
    //console.log('横坐标  = = ['+b2+', '+d2+']');
    c.forEach(function(a) {
    	selected.has(a.id) && (dataObject.push(a))
    });
    //数据范围doamin(dataStart, dataEnd)
    //网页显示范围range(posLeft, posRight)
    var eu = d3.scale.linear().domain([b2, d2]).range([a.left, width+a.left]);
    x_size = getAxisX(eu);
    var e = d3.scale.linear().domain([b2, d2]).range([a.left, width]);
    if (typeof(b2) == 'undefined' || typeof(d2) == 'undefined') {
        eu = e;
    }
	//$('span.radio_span').parent().find('.selected').removeClass('selected');
    var share_title = '服务';
    if (share_pos == 2) {
        share_title = '数据量'
    }else if (share_pos == 3) {
        share_title = '运行次数'
    }else if(share_pos == 4){
        share_title = '耗时'
    }
    d3.selectAll('#graph_share_2').remove();
		d3.selectAll('#graph_share_3').remove();
		d3.selectAll('#graph_share_4').remove(); 
		d3.selectAll('#graph_share_single_2').remove();
		d3.selectAll('#graph_share_single_3').remove();
		d3.selectAll('#graph_share_single_4').remove(); 
	if("" == d3.selectAll("#graph_"+task_type+"_"+share_pos)){
	//	console.log(dataObject +　' === >>> ' + task_type + share_pos );
		drawChart(c, dataObject, task_type, share_pos, eu, "共享批量"+share_title, 
			"Budget", "M$ (with inflation)", width, height / 2, a);
	}
}
function drawCollectLine(type){
	task_type_c = type;
//		null != d3.selectAll("#graph") && d3.selectAll("#graph").remove();
	  //  $("#graphHolder").html("");
	    var a = {
	        top: 100,
	        right: 40,
	        bottom: 0,
	        left: 50
	    };
	    width = Math.round($(window).width() - 20 - $("#graphHolder").position().left) - a.left - a.right;
	    $("#graphHolder").width(width);//.css({'overflow-x': "visible", 'overflow-y': "visible"});
	    //$("#graphHolder").width(gwidth);
	    height = 420;
	    $("#graphHolder").height(height);

	    var c = null;
	    "actors" == chart ? c = actors : "directors" == chart ? c = directors : "composers" == chart && (c = composers);
	    dataObject = [];
	    var b = "collect",
	        d = "share_single";
	    //从提供的数据中查找横坐标的起始结束日期
	    //该数据将根据用户选择的日期范围决定
	    //默认为近30天
	    var b2 = dateAry[0].id;
	    var d2 = dateAry[dateAry.length-1].id;
	    //开始计算横坐标
	    c.forEach(function(a) {
	    	selected.has(a.id) && (dataObject.push(a))
	    });
	    //数据范围doamin(dataStart, dataEnd)
	    //网页显示范围range(posLeft, posRight)
	    var eu = d3.scale.linear().domain([b2, d2]).range([a.left, width+a.left]);
	    var e = d3.scale.linear().domain([b2, d2]).range([a.left, width]);
	    if (typeof(b2) == 'undefined' || typeof(d2) == 'undefined') {
	        eu = e;
	    }  
	    x_size = getAxisX(eu);
	    if(type.indexOf('collect') > -1 && collect_pos == 4 ){
	    	collect_pos = 2;
	    }
	    task_type = 'collect';
		//$('span.cradio_span').parent().find('.selected').removeClass('selected');
	    var col_title = "采集";
	    if(collect_pos == 3){
	        col_title = "运行次数";
	    }else if(collect_pos == 2){
	        col_title = "数据量";
	    }
		if('collect_etl' == type){
			//$('input[name="r2"]')[0].checked = true;
			//d3.selectAll('#graph_collect_1').remove();
			//if("" == d3.selectAll("#graph_collect_2"))
			//	drawChart(c, dataObject, "collect_etl", collect_pos, eu, "ETL"+col_title, "Budget", "M$ (with inflation)", width, height / 2, a);
			//task_type_c = "collect_etl";
		}else if('collect' == type){
			//$('input[name="r2"]')[1].checked = true;
			d3.selectAll('#graph_collect_3').remove();
			d3.selectAll('#graph_collect_2').remove();
			drawChart(c, dataObject, "collect", collect_pos, eu, "采集"+col_title, "Budget", "M$ (with inflation)", width, height / 2, a);
			//task_type_c = "collect";
		}
	}
</script>

	<script type="text/javascript">
	function wrefresh(){
		parent.is_refresh = false;
		window.reload();
	}
		 $(function() {
			 $("#startDay").val(dateAry[0].md);
			 $("#endDay").val(dateAry[dateAry.length-1].md);
			 /* _IE = (function(){
	    		    var v = 3, div = document.createElement('div'), all = div.getElementsByTagName('i');
	    		    while (
	    		        div.innerHTML = '<!--[if gt IE ' + (++v) + ']><i></i><![endif]-->',
	    		        all[0]
	    		    );
	    		    return v > 4 ? v : false ;
	    		}()); */
			$("#about").dialog({
				autoOpen : false,
				show : "blind",
				hide : "explode",
				width : 800,
				height : 800
			});
			$("#chartSelector").buttonset();
			$("#chartSelector").change(function(event) {
				chartChange($("input[type=radio]:checked").val());
			});
			
			initStarpaths();
			var share_str = '<label class="radio_span" ><input onclick="drawShareLine(\'share_single\')" value="share_single" type="radio" name="r1" />单条</label>'+
			'<label onclick="drawShareLine(\'share\')" class="radio_span" ><input checked value="share" type="radio" name="r1" />批量</label>'+
			'&nbsp;&nbsp;&nbsp;<select onchange="drawAIndex()" id="share_c">'+
	        '<option value="share_amount">数据量</option>'+
			'<option value="share_count">共享次数</option>'+
    	    '<option value="share_time">共享耗时</option></select>';
    		$('#share_config').html(share_str);
    		
    		var collect_str = '<label class="cradio_span" ><input onclick="drawCollectLine(\'collect_etl\')" value="collect_etl" type="radio" name="r2" />ETL任务</label>'+
			'<label class="cradio_span selected" ><input onclick="drawCollectLine(\'collect\')" checked value="collect" type="radio" name="r2" />采集任务</label>'+
			'&nbsp;&nbsp;&nbsp;<select onchange="drawCIndex()" id="collect_c">'+
	        '<option value="collect_amount">数据量</option>'+
			'<option value="collect_count">采集次数</option>'+
    	    '<!-- <option value="collect_time">采集耗时</option> --></select>';
    	    $('#collect_config').html(collect_str);
		}); 

	</script>
	<div
		style="display: none; z-index: 1000; outline: 0px; position: absolute;"
		class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable"
		tabindex="-1" role="dialog" aria-labelledby="ui-dialog-title-about">
	
	</div>
	
	
	<script type="text/javascript">
	function drawPanel(dateType, startDate, endDate){
		var url = "";
		if(startDate && endDate && startDate <= endDate){
			url += "/txn6030011.ajax?select-key:startTime="+startDate+"&select-key:endTime="+endDate;
		}
		if(dateType){
			url += url == "" ? "/txn6030011.ajax?" : "&";
			url += "select-key:query_type="+dateType;
		}
		if(url==""){
			url += "/txn6030011.ajax";
		}
		$.ajax({
			type : "post",
			url : url,
			async : false,
			success : function(xmlResults) {
				if (xmlResults.selectSingleNode("//context/error-code").text != "000000") {
					alert(xmlResults.selectSingleNode("//context/error-desc").text);
					return false;
				} else {
					//var datas = $(xmlResults, "/context/record/data_str");
					actors = eval(xmlResults.selectSingleNode("//context/record/data_str").text);
					dateAry = eval(xmlResults.selectSingleNode("//context/record/time_str").text);
					
					initStarpaths();
				}
			}
		});
	}
	
		function changeTimezone(type) {
			$("#startTime").val("");//设置已选的数值为空
			$("#endTime").val("");
			$("#startMonth").val("");
			$("#endMonth").val("");
			var checkValue = type;
			$(".timezone").not(".tj").hide();
			switch (checkValue) {
			case "00":
				$("#Day_zone").show();
				break;
			case "01":
				$("#Week_zone").show();
				break;
			case "02":
				$("#Month_zone").show();
				break;
			case "05":
				$("#Year_zone").show();
				break;
			default:
				$("#Day_zone").show();
			}
		}

		function setDate() {
			var startTime = $("#startDay").val();
			var endTime = $("#endDay").val();
			var dateType = $('input[name="time_type"]:checked').val();
			dateType = "00"==dateType ? "day" : ("02"==dateType ? "month" : "other");
			if("day"==dateType){
				startTime = $("#startDay").val();
				endTime = $("#endDay").val();
			}else if("month"==dateType){
				startTime = $("#startMonth").val();
				endTime = $("#endMonth").val();
			}else if("week"==dateType){
				startTime = $("#startWeek").val();
				endTime = $("#endWeek").val();
			}
			drawPanel(dateType, startTime, endTime);
		}
		
		function drawAIndex(){
			var type = $('#share_c').val();
			//var pos = 1;
			if("share_count" == type || "share_single_count" == type){
				share_pos = 3;
			}else if("share_amount" == type ||　"share_single_amount" == type){
				share_pos = 2;
			}else if("share_time" == type){
				share_pos = 4;
			}
			$('label.radio_span').each(function(index){
				if($(this).hasClass('selected')){
					task_type = (index == 0 ? 'share_single' : 'share');
				}
			})
			drawShareLine(task_type);
		}
		
		function drawCIndex(){
			var type = $('#collect_c').val();
			collect_pos = 2;
			if('collect_count' == type){
				collect_pos = 3;
			}else if("collect_amount" == type){
				collect_pos = 2;
			}
			/* $('span.cradio_span').each(function(index){
				if($(this).hasClass('selected')){
					task_type_c = (index == 0 ? 'collect_etl' : 'collect');
				}
			}) */
			//console.log("---"  + task_type_c + ' = = = ' + collect_pos);
			drawCollectLine(task_type_c);
		}
	</script>
</body>
</html>