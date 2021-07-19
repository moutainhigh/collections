<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze"%>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import="cn.gwssi.common.context.Recordset"%>
<%@ page import="cn.gwssi.common.context.vo.VoUser"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="cn.gwssi.common.context.TxnContext"%>
<html>
    <head>
<%
 	DataBus db =(DataBus)request.getAttribute("freeze-databus");
 	db = db.getRecord("chart");
 	String chartXML = db.getValue("chartXML");
 	String tableData = db.getValue("tableData");
 	String type = db.getValue("type");
 	String count_type = db.getValue("count_type");
 	String start_time = db.getValue("start_time");
 	String end_time = db.getValue("end_time");
 %>
        <title>Multi Level Pie Chart</title>
        <script type="text/javascript" src="/page/zwt/Charts/jquery.min.js"></script>
        <script language="JavaScript" src="/page/zwt/Charts/FusionCharts.js"></script>
        <script type="text/javascript" src="/page/zwt/js/echarts-all.js" charset="utf-8"></script>
        <!-- <script type="text/javascript" src="/page/zwt/js/echarts-plain-map.js"></script>
         --><script type="text/javascript" src="<%=request.getContextPath()%>/script/My97DatePicker/WdatePicker.js"></script>

        <style type="text/css">
            body { background:#ebf0f5;}
            .tbss{border-collapse:collapse;}
            .tbss td, .tbss th{
            	height: 22px;
            	line-height: 22px;
            	border: 1px solid #999;
            	font-size: 12px;
            	color: #333;
            	padding:1px 3px;
            }
            .tbss th{background-color:#8AB0D7; border:1px solid #8AB0D7; color:white;}
            .tbtitle{
               text-align:left; 
               font-size:12px; 
               font-weight: bold;
              }
        </style>

    </head>
    <script>
    	var table_obj=<%=tableData%>;
    	var count_type="<%=count_type%>";
    	
    	
    	
    	function changeStatisticsType(count_type){
    		var type="<%=type%>";
    		
    		var url ="/txn53000210.do?select-key:type="+type;
    		if(count_type=="00"){
    			url = url +"&select-key:count_type=00";
    		}else if(count_type=="01"){
    			url = url +"&select-key:count_type=01";
    		}else if(count_type=="02"){
    			url = url +"&select-key:count_type=02";
    		}
    		window.location=url;
    		
    	}
        
         function initTable(){
         
         if(count_type=="00"){
         	document.getElementsByName("fb_type")[0].checked="checked";
         }else if(count_type=="01"){
         	document.getElementsByName("fb_type")[1].checked="checked";
         }else if(count_type=="02"){
         	document.getElementsByName("fb_type")[2].checked="checked";
         }
         //
         document.getElementById("select-key:start_time").value="<%=start_time%>";
       	 document.getElementById("select-key:end_time").value="<%=end_time%>";
         
         
         //table1
         //tr1
         var tr = document.createElement("tr");
         var td = document.createElement("td");
         td.innerHTML="服务对象";
         tr.appendChild(td);
         var td = document.createElement("td");
         td.innerHTML="共"+table_obj[0].object_num+"个";
         tr.appendChild(td); 
         document.getElementById("table1").appendChild(tr);
         //tr2
         var tr = document.createElement("tr");
         var td = document.createElement("td");
         td.innerHTML="共享服务个数";
         tr.appendChild(td);
         var td = document.createElement("td");
         td.innerHTML="共"+table_obj[0].service_num+"个";
         tr.appendChild(td); 
         document.getElementById("table1").appendChild(tr);
         
         //table2
         //tr1
         if(table_obj[1].WebService!=0){
         	var tr = document.createElement("tr");
         	var td = document.createElement("td");
         	td.innerHTML="WebService服务";
         	tr.appendChild(td);
         	var td = document.createElement("td");
         	td.innerHTML="共"+table_obj[1].WebService+"个";
         	tr.appendChild(td); 
         	document.getElementById("table2").appendChild(tr);
         }
         if(table_obj[1].数据库!=0){
         	var tr = document.createElement("tr");
         	var td = document.createElement("td");
         	td.innerHTML="数据库服务";
         	tr.appendChild(td);
         	var td = document.createElement("td");
         	td.innerHTML="共"+table_obj[1].数据库+"个";
         	tr.appendChild(td); 
         	document.getElementById("table2").appendChild(tr);
         }
         if(table_obj[1].FTP!=0){
         	var tr = document.createElement("tr");
         	var td = document.createElement("td");
         	td.innerHTML="FTP服务";
         	tr.appendChild(td);
         	var td = document.createElement("td");
         	td.innerHTML="共"+table_obj[1].FTP+"个";
         	tr.appendChild(td); 
         	document.getElementById("table2").appendChild(tr);
         }
         if(table_obj[1].SOCKET!=0){
         	var tr = document.createElement("tr");
         	var td = document.createElement("td");
         	td.innerHTML="SOCKET服务";
         	tr.appendChild(td);
         	var td = document.createElement("td");
         	td.innerHTML="共"+table_obj[1].SOCKET+"个";
         	tr.appendChild(td); 
         	document.getElementById("table2").appendChild(tr);
         }
         if(table_obj[1].JMS消息!=0){
         	var tr = document.createElement("tr");
         	var td = document.createElement("td");
         	td.innerHTML="JMS消息服务";
         	tr.appendChild(td);
         	var td = document.createElement("td");
         	td.innerHTML="共"+table_obj[1].JMS消息+"个";
         	tr.appendChild(td); 
         	document.getElementById("table2").appendChild(tr);
         }
         
         //table3
         //tr1
         var tr = document.createElement("tr");
         var td = document.createElement("td");
         td.innerHTML="批量服务";
         tr.appendChild(td);
         var td = document.createElement("td");
         td.innerHTML="共"+table_obj[2].批量+"个";
         tr.appendChild(td); 
         document.getElementById("table3").appendChild(tr);
         //tr
         var tr = document.createElement("tr");
         var td = document.createElement("td");
         td.innerHTML="单条服务";
         tr.appendChild(td);
         var td = document.createElement("td");
         td.innerHTML="共"+table_obj[2].单条+"个";
         tr.appendChild(td); 
         document.getElementById("table3").appendChild(tr);
         
         //table4
         //tr1
         var tr = document.createElement("tr");
         var td = document.createElement("td");
         td.innerHTML="共享数据量";
         tr.appendChild(td);
         var td = document.createElement("td");
         
         td.innerHTML="共"+table_obj[3].共享数据量.this_share_total+"条";
         tr.appendChild(td); 
         var td = document.createElement("td");
         td.innerHTML="共"+table_obj[3].共享数据量.last_share_total+"条";
         tr.appendChild(td); 
         document.getElementById("table4").appendChild(tr);
         //tr2
          var tr = document.createElement("tr");
         var td = document.createElement("td");
         td.innerHTML="共享次数";
         tr.appendChild(td);
         var td = document.createElement("td");
         td.innerHTML="共"+table_obj[3].共享次数.this_exec_counts+"次";
         tr.appendChild(td); 
         var td = document.createElement("td");
         td.innerHTML="共"+table_obj[3].共享次数.last_exec_counts+"次";
         tr.appendChild(td); 
         document.getElementById("table4").appendChild(tr);
         
         //table5
         //tr1
         var tr = document.createElement("tr");
         var td = document.createElement("td");
         td.innerHTML="批量共享数据量";
         tr.appendChild(td);
         var td = document.createElement("td");
         td.innerHTML="共"+table_obj[4].批量.this_share_total+"条";
         tr.appendChild(td); 
         var td = document.createElement("td");
         td.innerHTML="共"+table_obj[4].批量.last_share_total+"条";
         tr.appendChild(td); 
         document.getElementById("table5").appendChild(tr);
         //tr2
         var tr = document.createElement("tr");
         var td = document.createElement("td");
         td.innerHTML="单条共享数据量";
         tr.appendChild(td);
         var td = document.createElement("td");
         td.innerHTML="共"+table_obj[4].单条.this_share_total+"条";
         tr.appendChild(td); 
         var td = document.createElement("td");
         td.innerHTML="共"+table_obj[4].单条.last_share_total+"条";
         tr.appendChild(td); 
         document.getElementById("table5").appendChild(tr);
         newPie();
         }
         
         function newPie(){
        	 var chartPie = echarts.init(document.getElementById('chartdiv'));
             var option = <%=chartXML%>;
             chartPie.setOption(option);
             chartPie.on(echarts.config.EVENT.CLICK, function(param){
             		var link_value = param.data.link;
             		
            	    document.getElementById('form1').action=param.data.link;
            	    document.getElementById('form1').submit();
            	})
         }
         
         function showDate(){
        	 $('#Day_zone').show();
         }
         function cancelDate(){
        	 $('#Day_zone').hide();
         }
         function submitDate(){

        	 document.getElementById("select-key:start_time").value=document.getElementById("startDay").value;
        	 document.getElementById("select-key:end_time").value=document.getElementById("endDay").value;
        	 
        	 var start_time = document.getElementById("startDay").value;
        	 var end_time = document.getElementById("endDay").value;
        	 
        	 
        	 var type="<%=type%>";
        	 var count_type="<%=count_type%>";
        	 var url ="/txn53000210.do?select-key:type="+type+"&select-key:count_type="+count_type;
    		 //var url ="/txn53000210.do?select-key:type="+type+"&select-key:count_type="+count_type+"&select-key:start_time="+start_time+"&select-key:end_time="+end_time;
        	 document.getElementById('form1').action=url;
        	 document.getElementById('form1').submit();
         }
         
         function submit_time_type(start_time,end_time){
         	document.getElementById("select-key:start_time").value=start_time;
        	document.getElementById("select-key:end_time").value=end_time;
        	
        	var type="<%=type%>";
        	var count_type="<%=count_type%>";
        	var url ="/txn53000210.do?select-key:type="+type+"&select-key:count_type="+count_type;
        	document.getElementById('form1').action=url;
        	document.getElementById('form1').submit();
         }
         
         function curMonth(){
         	var myDate = new Date();
         	var year = myDate.getFullYear();
         	var month = myDate.getMonth()+1;
         	
         	var start_time = year+"-";
         	if(month<10){
         			start_time += "0"
         	}
         	start_time +=month+"-"+"01";
         	var end_time = getNDate(0);
         	
         	submit_time_type(start_time,end_time);
         }
         
         function lastMonth(){
         	var myDate = new Date();
         	
         	var year = myDate.getFullYear();
         	var month = myDate.getMonth()+1;
         	var lastYear=year;
         	var lastMonth = month;
         	
         	
         	var start_time = "";
         	if(month<10){
         		if(month==1){
         			start_time = (year-1)+"-";
         			lastYear = year - 1;
         			lastMonth = 12;
         		}else{
         			start_time = year+"-"+"0";
         			lastMonth = month -1;
         		}
         			
         	}else{
         		start_time = year+"-";
         		lastMonth= month-1;
         	}
         	
         	start_time +=lastMonth+"-"+"01";
         	var end_time = getLastMonthDay(lastYear,lastMonth);
         	
         	submit_time_type(start_time,end_time);
         }
         
         function last30Day(){
         	var start_time = getNDate(-30);//开始时间
         	var end_time = getNDate(0);//结束时间
         	
         	submit_time_type(start_time,end_time);
         }
         
         function getNDate(n){
         	var uom = new Date(new Date()-0+n*86400000);
         	uom = uom.getFullYear()+"-"+(uom.getMonth()+1)+"-"+uom.getDate();
         	return uom;
         }
         
         function getLastMonthDay(year,month){
         	var day = new Date(year,month,0);
         	var lastdate = year + '-'+month+'-'+day.getDate();
         	return lastdate;
         }
         
         
         
        </script>
   <body onload="initTable()">
        <div style="width: 100%">
        <div align="center" style="float: left;width: 65%;">
            <table style="width:100%" cellPadding="0" cellspaceing="0">
             <Tr>
	             <Td width="50px" valign="top">
	               <a href="/page/zwt/MLPie_L1.jsp" title="返回服务分布"><img  src="/page/zwt/images/l1.png" border="0" width="90" height="60" /></a><Br/>
	             </Td>
	             <Td>
	             <div style="font-size: 12px;font-weight: bold; ">
    		<label><input type="radio" name="fb_type" onclick="changeStatisticsType('00')" checked="checked"/>共享服务数量</label>
          	<label><input type="radio" name="fb_type" onclick="changeStatisticsType('01')"/>共享数据量</label>
          	<label><input type="radio" name="fb_type" onclick="changeStatisticsType('02')"/>共享次数</label>
    		<span>时间：</span><a onclick="curMonth()">本月</a>
    		<a onclick="lastMonth()">上月</a>
    		<a onclick="last30Day()">近30天</a>
    		<a id="customDate" onclick="showDate()">自定义</a>
    		<div id="Day_zone" style="background:#fcfcfc;z-index:9934;display:none;position:absolute; left:300px; top: 80px;border:1px solid #069;" class="timezone" >
		      	 开始日期：<input style="width: 100px;" id="startDay" type="text" class="Wdate" onFocus="var endTimeInput=$dp.$('endDay');WdatePicker({onpicked:function(){endTimeInput.focus();},skin:'default',minDate:'2009-01-01',maxDate:'#F{$dp.$D(\'endDay\',{d:-1});}',vel:'startTime'})"/>至
		      	 结束日期：<input style="width: 100px;" id="endDay" type="text" class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startDay\',{d:1});}',maxDate:'%y-%M-%d',vel:'endTime'})"/>
		    	<input type="button" value="确定" onclick="submitDate()" /><input type="button" value="关闭" onclick="cancelDate()" />
		    </div>

    	</div>
	             
	              <div id="chartdiv" align="center" style="height:500px;width: 100%;">
	              </div>
	             </Td>
             </Tr>
            </table>
        </div>
        <div style="float: right;width: 34.5%;font;font-size: 12px;font-weight: bold;">	
     
          <div class="tbtitle">总体分布情况</div>
          <Table id="table1" class="tbss" style="width: 90%" cellpadding="0" cellspacing="0" >
            
          </Table><Br/>
                    <div class="tbtitle">共享服务分布</div>
          <Table id="table2" class="tbss" style="width: 90%" cellpadding="0" cellspacing="0"  >
            <tr>
              <th>统计指标</th>
              <th>条数</th>
            </tr>
            
          </Table><br>
          <Table id="table3" class="tbss" style="width: 90%" cellpadding="0" cellspacing="0"  >
            <tr>
              <th>统计指标</th>
              <th>条数</th>
            </tr>
            
          </Table>
          <Br/>
           <div class="tbtitle">共享服务情况</div>
         <Table id="table4" class="tbss" style="width: 90%" cellpadding="0" cellspacing="0" >
            <tr>
              <th>统计指标</th>
              <th>当月</th>
              <th>上月</th>
            </tr>
            
          </Table>
          <Br/>
          <Table id="table5" class="tbss" style="width: 90%" cellpadding="0" cellspacing="0"  >
            <tr>
              <th>统计指标</th>
              <th>当月</th>
              <th>上月</th>
            </tr>
            
          </Table>
          <Br/>
        </div>
       </div>
    <form action="/txn53000211.do?select-key:service_targets_id=1735dcb0036c477aad1d96aa6aa74db9&select-key:count_type=00" method="post" id="form1" name="form1" target="_self">
  <input type="hidden" name="select-key:service_targets_id" id="select-key:service_targets_id"/>
  <input type="hidden" name="select-key:service_targets_name" id="select-key:service_targets_name"/>
  <input type="hidden" id="select-key:start_time" name="select-key:start_time" />
  <input type="hidden" id="select-key:end_time" name="select-key:end_time"/>
</form>    

<body>
<html>