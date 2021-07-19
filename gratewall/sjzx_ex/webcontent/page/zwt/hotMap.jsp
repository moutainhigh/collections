<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>深圳市市场和质量监督管理委员会数据交换平台-热力图</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<script type="text/javascript" src="/page/zwt/Charts/FusionCharts.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/script/My97DatePicker/WdatePicker.js"></script>
<script language="javascript">

function changeTimezone()
{

      $("#startTime").val("");//设置已选的数值为空
      $("#endTime").val("");
      
      var checkValue=$("#statistical_granularity").find("option:selected").val();
      //alert(checkValue);
      
      $(".timezone").hide();
      switch (checkValue) {
	  case "00": $("#Day_zone").show();
	    break;
	  case "01": $("#Week_zone").show();
	    break;
	  case "02": $("#Month_zone").show();
	    break;
	  case "05": $("#Year_zone").show();
	    break;
	  default: $("#Day_zone").show();
		}
      
}
</script>
</head>
<body style="background: #ebf0f5;">
	<script type="text/javascript" src="/page/zwt/js/HeatMap4.js"></script>
	<div align="center" >
	<Table style="width: 700px;font-size: 14px; color: #4682B4;text-align: center" >
	  <tr> 
	   <td width="34%">
	   <div align="center">
	             区县分局<input type="radio" name="choose" checked="checked" /> 
		内部系统<input type="radio" name="choose" /> 
		外部系统<input type="radio" name="choose" />
		</div>
	   </td>
		       <td width="9%" class="odd12">统计类型</td>
		       <td width="7%" class="odd12">
				<select name="select-key:statistical_granularity" id="statistical_granularity" onchange="changeTimezone();" style="width: 50px;" >
					<option value ="00">日</option>
					<option value="02">月</option>
					<option value="05">年</option>
				</select>
		 	   </td>
		       <td width="10%" class="odd12">起止时间</td>
		       <td width="30%" class="odd12">
		        <div id="Day_zone" class="timezone" >
		      	 <input style="width: 100px;" id="startDay" type="text" class="Wdate" onFocus="var endTimeInput=$dp.$('endDay');WdatePicker({onpicked:function(){endTimeInput.focus();},skin:'default',minDate:'2009-01-01',maxDate:'#F{$dp.$D(\'endDay\',{d:-1});}',vel:'startTime'})"/>至
		      	 <input style="width: 100px;" id="endDay" type="text" class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startDay\',{d:1});}',maxDate:'%y-%M-%d',vel:'endTime'})"/>
		      	 </div>
		      	 
		      	<div id="Month_zone" class="timezone" style="display:none;" >
		      	 <input style="width: 100px;" id="startMonth" type="text" class="Wdate" onFocus="var endTimeInput=$dp.$('endMonth');WdatePicker({onpicked:function(){endTimeInput.focus();},dateFmt:'yyyy年MM月',minDate:'2009年01月',maxDate:'#F{$dp.$D(\'endMonth\',{M:-1});}',vel:'startTime'})"/>至
     			  <input style="width: 100px;" id="endMonth" type="text" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy年MM月',minDate:'#F{$dp.$D(\'startMonth\',{M:1});}',maxDate:'%y年%M月',vel:'endTime'})"/>
		      	 </div>
		      	
		      	<div id="Year_zone" class="timezone" style="display:none;" >
		      	   <input style="width: 100px;" id="startYear" type="text" class="Wdate" onFocus="var endTimeInput=$dp.$('endYear');WdatePicker({onpicked:function(){endTimeInput.focus();},dateFmt:'yyyy年',minDate:'2009',maxDate:'#F{$dp.$D(\'endYear\',{y:-1});}',vel:'startTime'})"/>至
      				<input style="width: 100px;" id="endYear" type="text" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy年',minDate:'#F{$dp.$D(\'startYear\',{y:1});}',maxDate:'%y',vel:'endTime'})"/>
		      	 </div>
		      	
		      	<div id="Week_zone" class="timezone" style="display:none;" >
		      	  	<input style="width: 100px;" id="startWeek" type="text" class="Wdate" onFocus="var endTimeInput=$dp.$('endWeek');WdatePicker({onpicked:function(){endTimeInput.focus();},minDate:'2009-01-01',maxDate:'%y-%M-%d',isShowWeek:true,onpicked:function() {$dp.$('startTime').value=$dp.cal.getP('W','W');}})"/>至
       				<input style="width: 100px;" id="endWeek" type="text" class="Wdate" onFocus="WdatePicker({minDate:'2009-01-01',maxDate:'%y-%M-%d',isShowWeek:true,onpicked:function() {$dp.$('endTime').value=$dp.cal.getP('W','W');}})"/>
		      	 </div> 
		       </td>
	</Table>
	</div>
	<div id="chartdiv" align="center">无法加载动画...</div>
	<script type="text/javascript">
		var chart = new FusionCharts("/page/zwt/Charts/HeatMap.swf", "ChartId", "700",
				"480", "0");
		chart.setXMLData(dataString);
		chart.render("chartdiv");
	</script>



</body>
</html>