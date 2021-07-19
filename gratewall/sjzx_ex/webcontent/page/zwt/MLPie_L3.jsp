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
<html>
    <head>
    <%
 	DataBus db =(DataBus)request.getAttribute("freeze-databus");
 	db = db.getRecord("chart");
 	String chartXML = db.getValue("chartXML");
 	String tableData = db.getValue("tableData");
 	String service_targets_id = db.getValue("service_targets_id");
 	String count_type = db.getValue("count_type");
 	String start_time = db.getValue("start_time");
 	String end_time = db.getValue("end_time");
 	String link2 = db.getValue("link2");
 	
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
            .tbtitle{text-align:left; font-size:12px; font-weight: bold; }
        
        </style>

    </head>
    <script type="text/javascript">
    	var table_obj=<%=tableData%>;
    	var count_type="<%=count_type%>";
    	
    	
    	
    	function changeStatisticsType(type){
    		var id="<%=service_targets_id%>";
    		var url ="/txn53000211.do?select-key:service_targets_id="+id;
    		if(type=="00"){
    			url = url +"&select-key:count_type=00";
    		}else if(type=="01"){
    			url = url +"&select-key:count_type=01";
    		}
    		window.location=url;
    		
    	}
        
         function initTable(){
         //table1
         //tr1
         document.getElementById("select-key:start_time").value="<%=start_time%>";
       	 document.getElementById("select-key:end_time").value="<%=end_time%>";
         
         
         if(count_type=="00"){
         	document.getElementsByName("fb_type")[0].checked="checked";
         }else if(count_type=="01"){
         	document.getElementsByName("fb_type")[1].checked="checked";
         }
        
         var tr = document.createElement("tr");
         var td = document.createElement("td");
         td.innerHTML="����������";
         tr.appendChild(td);
         var td = document.createElement("td");
         td.innerHTML="��"+table_obj[0].service_num+"��";
         tr.appendChild(td); 
         document.getElementById("table1").appendChild(tr);
         
         //table2
         //tr1
         if(table_obj[1].WebService!=0){
         	var tr = document.createElement("tr");
         	var td = document.createElement("td");
         	td.innerHTML="WebService����";
         	tr.appendChild(td);
         	var td = document.createElement("td");
         	td.innerHTML="��"+table_obj[1].WebService+"��";
         	tr.appendChild(td); 
         	document.getElementById("table2").appendChild(tr);
         }
         if(table_obj[1].���ݿ�!=0){
         	var tr = document.createElement("tr");
         	var td = document.createElement("td");
         	td.innerHTML="���ݿ����";
         	tr.appendChild(td);
         	var td = document.createElement("td");
         	td.innerHTML="��"+table_obj[1].���ݿ�+"��";
         	tr.appendChild(td); 
         	document.getElementById("table2").appendChild(tr);
         }
         if(table_obj[1].FTP!=0){
         	var tr = document.createElement("tr");
         	var td = document.createElement("td");
         	td.innerHTML="FTP����";
         	tr.appendChild(td);
         	var td = document.createElement("td");
         	td.innerHTML="��"+table_obj[1].FTP+"��";
         	tr.appendChild(td); 
         	document.getElementById("table2").appendChild(tr);
         }
         if(table_obj[1].SOCKET!=0){
         	var tr = document.createElement("tr");
         	var td = document.createElement("td");
         	td.innerHTML="SOCKET����";
         	tr.appendChild(td);
         	var td = document.createElement("td");
         	td.innerHTML="��"+table_obj[1].SOCKET+"��";
         	tr.appendChild(td); 
         	document.getElementById("table2").appendChild(tr);
         }
         if(table_obj[1].JMS��Ϣ!=0){
         	var tr = document.createElement("tr");
         	var td = document.createElement("td");
         	td.innerHTML="JMS��Ϣ����";
         	tr.appendChild(td);
         	var td = document.createElement("td");
         	td.innerHTML="��"+table_obj[1].JMS��Ϣ+"��";
         	tr.appendChild(td); 
         	document.getElementById("table2").appendChild(tr);
         }
         
         //table3
         //tr1
         var tr = document.createElement("tr");
         var td = document.createElement("td");
         td.innerHTML="��������";
         tr.appendChild(td);
         var td = document.createElement("td");
         td.innerHTML="��"+table_obj[2].����+"��";
         tr.appendChild(td); 
         document.getElementById("table3").appendChild(tr);
         //tr
         var tr = document.createElement("tr");
         var td = document.createElement("td");
         td.innerHTML="��������";
         tr.appendChild(td);
         var td = document.createElement("td");
         td.innerHTML="��"+table_obj[2].����+"��";
         tr.appendChild(td); 
         document.getElementById("table3").appendChild(tr);
         
         //table4
         //tr1
         var tr = document.createElement("tr");
         var td = document.createElement("td");
         td.innerHTML="����������";
         tr.appendChild(td);
         var td = document.createElement("td");
         
         td.innerHTML="��"+table_obj[3].����������.this_share_total+"��";
         tr.appendChild(td); 
         var td = document.createElement("td");
         td.innerHTML="��"+table_obj[3].����������.last_share_total+"��";
         tr.appendChild(td); 
         document.getElementById("table4").appendChild(tr);
         //tr2
          var tr = document.createElement("tr");
         var td = document.createElement("td");
         td.innerHTML="�������";
         tr.appendChild(td);
         var td = document.createElement("td");
         td.innerHTML="��"+table_obj[3].�������.this_exec_counts+"��";
         tr.appendChild(td); 
         var td = document.createElement("td");
         td.innerHTML="��"+table_obj[3].�������.last_exec_counts+"��";
         tr.appendChild(td); 
         document.getElementById("table4").appendChild(tr);
         
         //table5
         //tr1
         var tr = document.createElement("tr");
         var td = document.createElement("td");
         td.innerHTML="��������������";
         tr.appendChild(td);
         var td = document.createElement("td");
         td.innerHTML="��"+table_obj[4].����.this_share_total+"��";
         tr.appendChild(td); 
         var td = document.createElement("td");
         td.innerHTML="��"+table_obj[4].����.last_share_total+"��";
         tr.appendChild(td); 
         document.getElementById("table5").appendChild(tr);
         //tr2
         var tr = document.createElement("tr");
         var td = document.createElement("td");
         td.innerHTML="��������������";
         tr.appendChild(td);
         var td = document.createElement("td");
         td.innerHTML="��"+table_obj[4].����.this_share_total+"��";
         tr.appendChild(td); 
         var td = document.createElement("td");
         td.innerHTML="��"+table_obj[4].����.last_share_total+"��";
         tr.appendChild(td); 
         document.getElementById("table5").appendChild(tr);
         newPie();
         }
         function newPie(){
        	 var chartPie = echarts.init(document.getElementById('chartdiv'));
             var option = <%=chartXML%>;
             chartPie.setOption(option);
             chartPie.on(echarts.config.EVENT.CLICK, function(param){
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
        	 
        	 var id="<%=service_targets_id%>";     
        	 var count_type="<%=count_type%>";
        	 var url ="/txn53000211.do?select-key:service_targets_id="+id+"&select-key:count_type="+count_type;
    		 
        	 document.getElementById('form1').action=url;
        	 document.getElementById('form1').submit();
         }
         function submit_time_type(start_time,end_time){
         	document.getElementById("select-key:start_time").value=start_time;
        	document.getElementById("select-key:end_time").value=end_time;
        	
        	var id="<%=service_targets_id%>";     
        	var count_type="<%=count_type%>";
        	var url ="/txn53000211.do?select-key:service_targets_id="+id+"&select-key:count_type="+count_type;
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
         	var start_time = getNDate(-30);//��ʼʱ��
         	var end_time = getNDate(0);//����ʱ��

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
	               <a href="/page/zwt/MLPie_L1.jsp">��һ��</a><Br/>
	               <a href="<%=link2%>&select-key:count_type=00">�ڶ���</a>
	             </Td>
	             <Td>
	             <div style="font-size: 12px;font-weight: bold; ">
	             <label><input type="radio" name="fb_type" onclick="changeStatisticsType('00')" checked="checked" />����������</label>
          		 <label><input type="radio" name="fb_type" onclick="changeStatisticsType('01')" />�������</label>
    		<span>ʱ�䣺</span><a onclick="curMonth()">����</a>
    		<a onclick="lastMonth()">����</a>
    		<a onclick="last30Day()">��30��</a>
    		<a id="customDate" onclick="showDate()">�Զ���</a>
    		<div id="Day_zone" style="background:#fcfcfc;z-index:9934;display:none;position:absolute; left:300px; top: 80px;border:1px solid #069;" class="timezone" >
		      	 ��ʼ���ڣ�<input style="width: 100px;" id="startDay" type="text" class="Wdate" onFocus="var endTimeInput=$dp.$('endDay');WdatePicker({onpicked:function(){endTimeInput.focus();},skin:'default',minDate:'2009-01-01',maxDate:'#F{$dp.$D(\'endDay\',{d:-1});}',vel:'startTime'})"/>��
		      	 �������ڣ�<input style="width: 100px;" id="endDay" type="text" class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startDay\',{d:1});}',maxDate:'%y-%M-%d',vel:'endTime'})"/>
		    	<input type="button" value="ȷ��" onclick="submitDate()" /><input type="button" value="�ر�" onclick="cancelDate()" />
		    </div>

    	</div>
	              <div id="chartdiv" align="center" style="height:500px;width: 100%;">
	              </div>
	             </Td>
             </Tr>
            </table>
         </div>
        <div style="float: right;width: 34.5%;font-size: 12px;font-weight: bold;">
         <div class="tbtitle"> ����ֲ����</div>
          <Table id="table1" class="tbss" style="width: 90%" cellpadding="0" cellspacing="0" >
            
          </Table>
          <br/>
          <div class="tbtitle">�������ֲ�</div>
          <Table id="table2" class="tbss" style="width: 90%" cellpadding="0" cellspacing="0"  >
            <tr>
              <th>ͳ��ָ��</th>
              <th>����</th>
            </tr>
            
          </Table><br>
          <Table id="table3" class="tbss" style="width: 90%" cellpadding="0" cellspacing="0"  >
            <tr>
              <th>ͳ��ָ��</th>
              <th>����</th>
            </tr>
            
          </Table><br>
                                 <div class="tbtitle">����������</div>
          <Table id="table4" class="tbss" style="width: 90%" cellpadding="0" cellspacing="0" >
            <tr>
              <th>ͳ��ָ��</th>
              <th>����</th>
              <th>����</th>
            </tr>
            
          </Table>
          <Br/>
          <Table id="table5" class="tbss" style="width: 90%" cellpadding="0" cellspacing="0"  >
            <tr>
              <th>ͳ��ָ��</th>
              <th>����</th>
              <th>����</th>
            </tr>
            
          </Table><br>
        </div>
       </div>
        <form action="/txn53000211.do?select-key:service_targets_id=1735dcb0036c477aad1d96aa6aa74db9&select-key:count_type=00" method="post" id="form1" name="form1" target="_self">
  <input type="hidden" name="select-key:service_targets_id" id="select-key:service_targets_id"/>
  <input type="hidden" name="select-key:service_targets_name" id="select-key:service_targets_name"/>
  <input type="hidden" id="select-key:start_time" name="select-key:start_time" />
  <input type="hidden" id="select-key:end_time" name="select-key:end_time"/>
</form> 

    </body>
</html>