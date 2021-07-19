<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<html>
	<head>
	
	<%
 	DataBus db =(DataBus)request.getAttribute("freeze-databus");
	String startTime="";
	String endTime="";
 	if(db!=null){
		db = db.getRecord("select-key");
	 	startTime = db.getValue("startTime");
	 	endTime=db.getValue("endTime"); 	
 	}
 %>
    
        <script type="text/javascript" src="<%=request.getContextPath()%>/page/zwt/Charts/jquery.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/page/zwt/js/echarts-all.js" charset="utf-8"></script>
        <%-- <script type="text/javascript" src="<%=request.getContextPath()%>/page/zwt/js/echarts-plain-map.js"></script>
         --%><script type="text/javascript" src="<%=request.getContextPath()%>/script/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/page/zwt/Charts/ECharts_Pie_Option.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/page/zwt/js/CollectFb.js" ></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/page/zwt/js/CollectTable.js"></script>
<style type="text/css">
table, td{
	font-size:12px; 
}
table{width:100%;border-collapse:collapse;margin:1em 0;background:#f8fbfc;}
th, td{text-align:left;padding:.5em;border:1px solid #fff;}
th{background:#328aa4 url(images/tr_back.gif) repeat-x;color:#fff;}
td{background:#e5f1f4;}

.tbtitle{text-align:left; font-size:12px;  font-weight: bold;}
</style>

<script>

	
	function init(){
		//表单1
		if(table_obj){
			document.getElementById("typeNum").innerHTML='共'+table_obj[0].typeNum+'大类';
			document.getElementById("targetsNum").innerHTML='共'+table_obj[0].targetsNum+'个';
			document.getElementById("etl").innerHTML='共'+table_obj[0].etl+'个';
			document.getElementById("WebService").innerHTML='共'+table_obj[0].WebService+'个';
			document.getElementById("ftp").innerHTML='共'+table_obj[0].ftp+'个';
			document.getElementById("jms").innerHTML='共'+table_obj[0].jms+'个';
			document.getElementById("socket").innerHTML='共'+table_obj[0].socket+'个';
			document.getElementById("database").innerHTML='共'+table_obj[0].database+'个';
			document.getElementById("upload").innerHTML='共'+table_obj[0].upload+'个';
			//表单2
			document.getElementById("LASTAMOUNT").innerHTML='共'+table_obj[1].LASTAMOUNT+'条';
			document.getElementById("CURTIMES").innerHTML='共'+table_obj[1].CURTIMES+'次';
			document.getElementById("LASTTIMES").innerHTML='共'+table_obj[1].LASTTIMES+'次';
			document.getElementById("CURAMOUNT").innerHTML='共'+table_obj[1].CURAMOUNT+'条';
		}
		
		$('#startDay').val('<%=startTime%>');
		$('#endDay').val('<%=endTime%>');
		newPie();
		//initOption();
	}
	
function initOption(){
		
		
		if(yAxis_data){
			option.yAxis[0].data=yAxis_data;
		}
		if(series_data1){
			option.series[0].data=series_data1;
		}
		if(service_targets_name){
			option.title.text=service_targets_name+'采集数据分布';
			option.legend.data[0]='采集数据量';
			option.series[0].name='采集数据量';
		}
		
		newPie();
	}
	function newPie(){
   	 var chartPie = echarts.init(document.getElementById('chartdiv'));
        //console.log(option.series.name);
        chartPie.setOption(option);
        chartPie.on(echarts.config.EVENT.PIE_SELECTED, onPieSelect);
        
    }
	function settip(param){
		//console.log(param);
		if(param==1){
			map=taskmap;
		}else if(param==2){
			map=amountmap;
		}else if(param==3){
			map=timesmap;
		}
	}
	function showDate(){
		 $('#Day_zone').show();
	}
	function cancelDate(){
		 $('#Day_zone').hide();
	}
	function submitDate(){
		 var url="/txn53000213.do?select-key:startTime="+$('#startDay').val()
				 +"&select-key:endTime="+$('#endDay').val();
		 window.location=url;
	}
</script>

    </head>
    <body onload="init()">
       
       <div style="width: 100%">
       <div style="font-size:10pt; ">
    		<label><input name="target" type="radio" id="r1" value="times" onclick="settip(1)" />&nbsp;采集任务个数</label>
    		<label><input name="target" type="radio" id="r2" value="times" onclick="settip(2)" />&nbsp;采集数据量</label>
    		<label><input name="target" type="radio" id="r3" value="times" onclick="settip(3)" />&nbsp;采集次数</label>
    		<span>时间：</span><a>本月</a>
    		<a>上月</a>
    		<a>近30天</a>
    		<a id="customDate" onclick="showDate()">自定义</a>
    		<div id="Day_zone" style="background:#fcfcfc;z-index:9934;display:none;position:absolute; left:300px; top: 80px;border:1px solid #069;" class="timezone" >
		      	 开始日期：<input style="width: 100px;" id="startDay" type="text" class="Wdate" onFocus="var endTimeInput=$dp.$('endDay');WdatePicker({onpicked:function(){endTimeInput.focus();},skin:'default',minDate:'2009-01-01',maxDate:'#F{$dp.$D(\'endDay\',{d:-1});}',vel:'startTime'})"/>至
		      	 结束日期：<input style="width: 100px;" id="endDay" type="text" class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startDay\',{d:1});}',maxDate:'%y-%M-%d',vel:'endTime'})"/>
		    	<input type="button" value="确定" onclick="submitDate()" /><input type="button" value="关闭" onclick="cancelDate()" />
		    </div>
    	</div>
       
        <div id="chartdiv" align="center" style="float: left;width: 65%;height:500px">
        	没有相关数据
        	</div>
        <div style="float: right;width: 34.5%;">
           <div class="tbtitle">总体分布情况 </div>
          <Table style="width: 90%"  >
          	<tr>
              <th>统计项</th>
              <th>统计值</th>
              
            </tr>
            <tr><td>服务对象类型</td><td id="typeNum"></td></tr>
            <tr><td>服务对象</td><td id="targetsNum"></td></tr>
            <tr><td>WebService采集任务</td><td id="WebService"></td></tr>
            <tr><td>文件上传采集任务</td><td id="upload"></td></tr>
            <tr><td>FTP采集任务</td><td id="ftp"></td></tr>
            <tr><td>数据库采集任务</td><td id="database"></td></tr>
            <tr><td>JMS采集任务</td><td id="jms"></td></tr>
            <tr><td>SOCKET采集任务</td><td id="socket"></td></tr>
            <tr><td>ETL采集任务</td><td id="etl"></td></tr>
          </Table><Br/>
          <div class="tbtitle">采集任务情况</div>
          <Table  style="width: 90%" cellpadding="0" cellspacing="0"  >
            <tr>
              <th>统计指标</th>
              <th>当月</th>
              <th>上月</th>
            </tr>
            <tr>
              <td>采集数据量</td><td id='CURAMOUNT'></td><td id='LASTAMOUNT'></td>
            </tr>
            <tr>
              <td>采集次数</td><td id='CURTIMES'></td><td id='LASTTIMES'></td>
            </tr>
          </Table>
        </div>
       </div>
 
 
    </body>	
</html>