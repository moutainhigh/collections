<%@ page contentType="text/html; charset=utf-8" language="java"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<title>深圳市在用特种设备统计表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.form.js" type="text/javascript"></script>

<script src="<%=request.getContextPath()%>/static/script/slides.min.jquery.js" type="text/javascript"></script>
<style type="text/css">
</style>


</head>
<body>
	<div name="CPRQueryListPanel" id="formpanel" vtype="formpanel" width="100%" layout="table" style="text-align: center;" layoutconfig="{cols:2, columnwidth: ['50%','50%']}" labelstyleclass="labelstyle" title="特种设备使用登记统计查询" titledisplay="true" showborder="false" height="100%">


		 <div name="sldw" vtype="comboxfield" label="受理单位" labelwidth="150" labelalign="right" width="400" valuetip="请选择受理单位……"  tooltip=""   ></div><!-- dataurl='' -->
		
		<div name="slType" vtype="comboxfield" label="受理类型" labelwidth="150" labelalign="right" width="400" valuetip="请选择受理类型……"  tooltip=""  ></div> <!-- dataurl='json/data13_sltype.json' -->

		<div name="sltime" lable="受理时间" labelwidth="0" labelalign="right" width="400" style="margin-left: 60px;">
			<div name="sltime_begin" vtype="datefield" label="受理时间" labelwidth="90" labelalign="right" width="205" valuetip="受理开始时间……" tooltip="" rule=""></div>

			<span id="toDates" style="text-align: center; height: 48px; line-height: 63px; margin-left: -11px; margin-right: -11px; color: #666; font-size: 12px">至</span>
			<div name="sltime_end" vtype="datefield" labelwidth="0" labelalign="right" width="110" valuetip="受理结束时间……" tooltip="" rule=""></div>
		</div>



		<div name="sptime" lable="审批登记时间" labelwidth="0" labelalign="right" width="400" style="margin-left: 60px;">
			<div name="sptime_begin" vtype="datefield" label="审批时间" labelwidth="90" labelalign="right" width="205" valuetip="审批时间……" tooltip="" rule=""></div>

			<span id="toDates" style="text-align: center; height: 48px; line-height: 63px; margin-left: -11px; margin-right: -11px; color: #666; font-size: 12px">至</span>
			<div name="sptime_end" vtype="datefield" labelwidth="0" labelalign="right" width="110" valuetip="审批结束时间……" tooltip="" rule=""></div>
		</div>


		<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
			<div name="query_button" vtype="button" text="查询" icon="../query/queryssuo.png" onclick="queryUrl(this);"></div>
			<div name="reset_button" vtype="button" text="重置" icon="../query/queryssuo.png" click="reset();"></div>
		</div>


	</div>


<script type="text/javascript" charset="UTF-8">


$(function() {

	getcode();
	slType();
	function getcode() {
		$.ajax({
			type : "post",
			url : rootPath + "/data13/sldw_code_value.do",
			data : "randan" + Math.random(),
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				var data = eval("(" + data + ")");
				var dataStr = data.data[0].data;
				for ( var i in dataStr) {
					$("div[name='sldw']").comboxfield('addOption', dataStr[i].text, dataStr[i].value);
				}
			}
		});
	}
	
	
	
	function slType(){
		
		$.ajax({
			type : "post",
			url : rootPath + "/data13/slType_code_value.do",
			data : "randan" + Math.random(),
			dataType : "text",
			async : false,
			cach : false,
			success : function(data) {
				var data = eval("(" + data + ")");
				var dataStr = data.data[0].data;
				for ( var i in dataStr) {
					$("div[name='slType']").comboxfield('addOption', dataStr[i].text, dataStr[i].value);
				}
			}
		});
		
	} 

});



	function reset() {
		$("#formpanel").formpanel('reset');
	}



	function queryUrl(obj) {
		var _this = $(obj);
		$("div[name='query_button']").button('disable');
		_this.removeAttr("onclick");
		_this.find("div.button-text ").html("查询中");
		var aa = $("#formpanel").formpanel('getValue');
		var htmls = "";
		var sldw = aa.data.sldw;
		var slType = aa.data.slType;
		var spBegTime = aa.data.sptime_begin;
		var spEndTime = aa.data.sptime_end;

		var slBegTime = aa.data.sltime_begin;
		var slEndTime = aa.data.sltime_end;

		htmls += "<a href='"+rootPath+"/data13/downExcelByZaiYong.do'>下载</a>";
		htmls += "<table border='1px' bordercolor='#000000' cellspacing='0px' style='border-collapse:collapse' class='outterTable'>";
		htmls += "<tr><td colspan='28'><h1>深圳市特种设备使用登记统计表</h1></td></tr>";
		htmls += "<tr><td rowspan='2'>办理单位</td><td colspan='3'>合计</td><td colspan='3'>锅炉</td><td colspan='3'>压力容器</td><td colspan='3'>电梯</td><td colspan='3'>起重机械</td><td colspan='3'>厂内机动车辆</td><td colspan='3'>大型游乐设施</td><td colspan='3'>压力管道</td><td colspan='3'>客运索道</td></tr>";
		htmls += "<tr><td>受理宗数</td><td>申请设备数</td><td>设备通过数</td><td>受理宗数</td><td>申请设备数</td><td>设备通过数</td><td>受理宗数</td><td>申请设备数</td><td>设备通过数</td><td>受理宗数</td><td>申请设备数</td><td>设备通过数</td><td>受理宗数</td><td>申请设备数</td><td>设备通过数</td><td>受理宗数</td><td>申请设备数</td><td>设备通过数</td><td>受理宗数</td><td>申请设备数</td><td>设备通过数</td><td>受理宗数</td><td>申请设备数</td><td>设备通过数</td><td>受理宗数</td><td>申请设备数</td><td>设备通过数</td></tr>";

		$.ajax({
			type : "post",
			url : rootPath + "/data13/queyShiYongDj.do",
			dataType : "text",
			async : false,
			cach : false,
			data : {
				"sldw" : sldw,
				"slType" : slType,
				"spBegTime" : spBegTime,
				"spEndTime" : spEndTime,
				"slBegTime" : slBegTime,
				"slEndTime" : slEndTime,
			},
			success : function(data) {
				$("div[name='query_button']").button('enable');
				_this.attr("onclick","queryUrl(this);");
				var dataObj = eval("(" + data + ")");
				for (var i = 0; i < dataObj.length; i++) {
					htmls+="<tr>";
					htmls +="<td>"+dataObj[i].办理单位+"</td>";
					htmls+="<td>"+dataObj[i].合计受理宗数+"</td><td>"+dataObj[i].合计申请设备数+"</td><td>"+dataObj[i].合计设备通过数+"</td>";
					htmls+="<td>"+dataObj[i].锅炉受理宗数+"</td><td>"+dataObj[i].锅炉申请设备数+"</td><td>"+dataObj[i].锅炉设备通过数+"</td>";
					htmls+="<td>"+dataObj[i].压力容器受理宗数+"</td><td>"+dataObj[i].压力容器申请设备数+"</td><td>"+dataObj[i].压力容器设备通过数+"</td>";
					htmls+="<td>"+dataObj[i].电梯受理宗数+"</td><td>"+dataObj[i].电梯申请设备数+"</td><td>"+dataObj[i].电梯设备通过数+"</td>";
					htmls+="<td>"+dataObj[i].起重机械受理宗数+"</td><td>"+dataObj[i].起重机械申请设备数+"</td><td>"+dataObj[i].起重机械设备通过数+"</td>";
					htmls+="<td>"+dataObj[i].场内机动车辆受理宗数+"</td><td>"+dataObj[i].场内机动车辆申请设备数+"</td><td>"+dataObj[i].场内机动车辆设备通过数+"</td>";
					htmls+="<td>"+dataObj[i].大型游乐设施受理宗数+"</td><td>"+dataObj[i].大型游乐设施申请设备数+"</td><td>"+dataObj[i].大型游乐设施设备通过数+"</td>";
					htmls+="<td>"+dataObj[i].压力管道受理宗数+"</td><td>"+dataObj[i].压力管道申请设备数+"</td><td>"+dataObj[i].压力管道设备通过数+"</td>";
					htmls+="<td>"+dataObj[i].客运索道受理宗数+"</td><td>"+dataObj[i].客运索道申请设备数+"</td><td>"+dataObj[i].客运索道设备通过数+"</td>";
					
					
					htmls+="</tr>";
				}

			}
		});
		htmls += "</table>";
		var newWim = open("down/data13_down.jsp", "_blank");

		window.setTimeout(function() {
			_this.find(".button-text").html("查询");
			newWim.document.body.innerHTML = htmls;
		}, 1000);

	}
</script>

</body>
</html>