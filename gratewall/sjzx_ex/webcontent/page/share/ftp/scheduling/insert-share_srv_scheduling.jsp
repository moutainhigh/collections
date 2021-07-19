<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="300" height="200">
<head>
<title>任务调度信息</title>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/uploadfile.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery-ui.custom.min.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery.ui.timepicker.js"></script>
	<link href="<%=request.getContextPath()%>/script/lib/jquery.ui.timepicker.css" rel="stylesheet" type="text/css">
	<link href="<%=request.getContextPath()%>/script/css/cupertino/jquery.ui.all.css" rel="stylesheet" type="text/css">
	
</head>

<script language="javascript">

// 保 存
function func_record_saveRecord()
{
	saveRecord( '', '保存任务调度表' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存任务调度表' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	
	var stime=document.getElementById("stime").value;
	
	setFormFieldValue("record:start_time",stime);//开始执行时间
	
	var etime=document.getElementById("etime").value;
	setFormFieldValue("record:end_time",etime);//结束执行时间
	
	//判断开始执行时间小于等于结束执行时间
	
	var shour=parseInt(stime.substring(0,stime.indexOf(":")));
    var ehour=parseInt(etime.substring(0,etime.indexOf(":")));
    if(shour>ehour){
    	alert("开始执行时间小于等于结束执行时间!");
		return false;
    }else if(shour==ehour){
        var sminitue=stime.substring(stime.indexOf(":")+1);
        var eminitue=etime.substring(etime.indexOf(":")+1);
       	
        if(sminitue>eminitue){
         	alert("开始执行时间小于等于结束执行时间!");
			return false;
        }
    }
    
    
	
	if(document.getElementById("everyWeek").style.display == "block"){
	
		var weekDay=document.getElementsByName("weekDay");
		var week="";
		if(weekDay!=null&&weekDay.length>0){
			for(var i=0;i<weekDay.length;i++){
			   if(weekDay[i].checked==true){
			     //week+=weekDay[i].nextSibling.nodeValue+",";
			     week+=weekDay[i].value+",";
			     flag="true";
			   }
			}
		}
		if(week!=null&&week!=""){
		   week=week.substring(0,week.length-1);
		   setFormFieldValue("record:scheduling_week",week);//周天
		}else{
		   alert("至少选择一个周天");
		   return false;
		}
		
		document.getElementById("record:scheduling_day").value="";//日期
	}
	else if(document.getElementById("everyMonth").style.display == "block"){
		var day=document.getElementById("day").value;
		
		if(day!=null&&day!=""){
		  setFormFieldValue("record:scheduling_day",day);//日期
		}else{
		   alert("至少选择一个日期");
		   return false;
		}
		
		document.getElementById("record:scheduling_week").value="";//周天
	}
	else{
		
		document.getElementById("record:scheduling_week").value="";//周天
		document.getElementById("record:scheduling_day").value="";//日期
	}
	var cs=document.getElementById("jhzxcs").value;
	if(!/^[1-9]\d*$/.test(cs)){
		alert("计划任务次数必须为正整数!");
		return false;
	}
	if(cs!=null&&cs!=""){
		  setFormFieldValue("record:scheduling_count",cs);//次数
	}
	
	var jg=document.getElementById("jhzxjg").value;
	if(!/^[1-9]\d*$/.test(jg)){
		alert("每次执行时间间隔必须为正整数!");
		return false;
	}
	if(jg!=null&&jg!=""){
		  setFormFieldValue("record:interval_time",jg);//间隔
	}
	//saveAndExit( '', '保存任务调度表' );	// /txn30801001.do
	
	var page = new pageDefine( "/txn40400003.ajax", "保存任务调度信息");
	page.addParameter("record:service_id","record:service_id");
	page.addParameter("record:service_no","record:service_no");
	page.addParameter("record:service_name","record:service_name");
	page.addParameter("record:srv_scheduling_id","record:srv_scheduling_id");
	page.addParameter("record:scheduling_type","record:scheduling_type");
	page.addParameter("record:service_targets_id","record:service_targets_id");
	page.addParameter("record:start_time","record:start_time");
	page.addParameter("record:end_time","record:end_time");
	page.addParameter("record:scheduling_count","record:scheduling_count");
	page.addParameter("record:scheduling_week","record:scheduling_week");
	page.addParameter("record:scheduling_day","record:scheduling_day");
	page.addParameter("record:interval_time","record:interval_time");
	page.addParameter("record:scheduling_day1","record:scheduling_day1");
	page.addParameter("record:creator_id","record:creator_id");
	page.addParameter("record:created_time","record:created_time");
	page.addParameter("record:last_modify_id","record:last_modify_id");
	page.addParameter("record:last_modify_time","record:last_modify_time");
	page.addParameter("record:is_markup","record:is_markup");
	page.addParameter("record:task_expression","record:task_expression");
	
	page.callAjaxService('insertTaskSchedule');
}
function insertTaskSchedule(errCode,errDesc,xmlResults){
		
		if(errCode != '000000'){
			alert('处理错误['+errCode+']==>'+errDesc);
			return;
		}else{
	
		    var srv_scheduling_id=_getXmlNodeValues(xmlResults,'record:srv_scheduling_id');
		    var scheduling_type=_getXmlNodeValues(xmlResults,'record:scheduling_type');
		   
		    var start_time=_getXmlNodeValues(xmlResults,'record:start_time');
		    var end_time=_getXmlNodeValues(xmlResults,'record:end_time');
		    var scheduling_week=_getXmlNodeValues(xmlResults,'record:scheduling_week');
		    var scheduling_day=_getXmlNodeValues(xmlResults,'record:scheduling_day');
		    var scheduling_count=_getXmlNodeValues(xmlResults,'record:scheduling_count');
		    var interval_time=_getXmlNodeValues(xmlResults,'record:interval_time');
		    var cjzq=_getXmlNodeValues(xmlResults,'record:scheduling_day1');
		    window.dialogArguments.document.getElementById("record1:srv_scheduling_id").value = srv_scheduling_id; 
		    window.dialogArguments.document.getElementById("record1:scheduling_type").value = scheduling_type; 
		    window.dialogArguments.document.getElementById("record1:start_time").value = start_time; 
		    window.dialogArguments.document.getElementById("record1:end_time").value = end_time; 
		    window.dialogArguments.document.getElementById("record1:scheduling_week").value = scheduling_week; 
		    window.dialogArguments.document.getElementById("record1:scheduling_day").value = scheduling_day; 
		    window.dialogArguments.document.getElementById("record1:scheduling_count").value = scheduling_count; 
		    window.dialogArguments.document.getElementById("record1:interval_time").value = interval_time; 
		    window.dialogArguments.document.getElementById("record1:scheduling_day1").value = cjzq; 
		    window.close();
		}
}

// 返 回
function func_record_goBack()
{
	window.close();	// /txn30801001.do
}
// 改变任务调度类型
function changeType()
{
	var taskType=getFormFieldValue( "record:scheduling_type");
	var day=getFormFieldValue( "record:scheduling_day");
	var time=getFormFieldValue("record:start_time");//开始执行时间
	if(time==null||time==""){
		document.getElementById("stime").value="00:00";
	}else{
		document.getElementById("stime").value=time;
	}
	
	time=getFormFieldValue("record:end_time");//结束执行时间
	if(time==null||time==""){
		document.getElementById("etime").value="23:59";
	}else{
		document.getElementById("etime").value=time;
	}
	if(taskType!=null&&(taskType=="01")){
	  document.getElementById("everyWeek").style.display = "none"; 
	  document.getElementById("everyMonth").style.display = "none"; 
	 
	}else if(taskType!=null&&taskType=="02"){
	  document.getElementById("everyWeek").style.display = "block"; 
	  document.getElementById("everyMonth").style.display = "none"; 
	  var cs=getFormFieldValue("record:scheduling_week");//周天
	  var week=cs.split(",");
	  if(week!=null&&week!=""){
	      for(var i=0;i<week.length;i++){
	         if(week[i]!=null&&week[i]=="1"){
	            document.getElementsByName("weekDay")[0].checked="true";
	         }else if(week[i]!=null&&week[i]=="2"){
	         	document.getElementsByName("weekDay")[1].checked="true";
	         }else if(week[i]!=null&&week[i]=="3"){
	         	document.getElementsByName("weekDay")[2].checked="true";
	         }else if(week[i]!=null&&week[i]=="4"){
	         	document.getElementsByName("weekDay")[3].checked="true";
	         }else if(week[i]!=null&&week[i]=="5"){
	         	document.getElementsByName("weekDay")[4].checked="true";
	         }else if(week[i]!=null&&week[i]=="6"){
	         	document.getElementsByName("weekDay")[5].checked="true";
	         }else if(week[i]!=null&&week[i]=="7"){
	         	document.getElementsByName("weekDay")[6].checked="true";
	         }
	      }
	  }
	}else if(taskType!=null&&taskType=="03"){
	  document.getElementById("everyWeek").style.display = "none"; 
	  document.getElementById("everyMonth").style.display = "block"; 
	  document.getElementById("day").value=day;
	}
	 var cs=getFormFieldValue("record:scheduling_count");//执行次数
	 if(cs==""){
	   cs="1";
	 }
	 document.getElementById("jhzxcs").value=cs;
	 
	 var jg=getFormFieldValue("record:interval_time");//执行间隔
	 if(jg==""){
	   jg="0";
	 }
	 document.getElementById("jhzxjg").value=jg;
}
function initWeek() {
 var weeks = ['x', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期日'];
 var str="";
  for (var j = 1; j < 8; j++) {
      str += " <input type=checkbox  name='weekDay' value='" + j + "' " //+ds
      +
      "/> " + weeks[j] + "";
  }
  $('#weekTd').html(str);
}
function initDay() {
  var str = "<select name='day' id='day' style='width: 53px'>"; 
  for (var j = 1; j < 32; j++) {
      str += " <option  value= '" + j + "'> " + j + "</option>";
  }
  $('#dayTd').html(str);
}
function changeCs() {

var cs= document.getElementById("jhzxcs").value;

if(!/^[1-9]\d*$/.test(cs)){
alert("计划任务次数必须为正整数!");
return false;
}
var stime=document.getElementById("stime").value;
var etime=document.getElementById("etime").value;

var shour=parseInt(stime.substring(0,stime.indexOf(":")));
var ehour=parseInt(etime.substring(0,etime.indexOf(":")));

//判断开始执行时间小于等于结束执行时间
    if(shour>ehour){
    	alert("开始执行时间小于等于结束执行时间!");
		return false;
    }else if(shour==ehour){
        var sminitue=parseInt(stime.substring(stime.indexOf(":")+1));
        var eminitue=parseInt(etime.substring(etime.indexOf(":")+1));
       	
        if(sminitue>eminitue){
         	alert("开始执行时间小于等于结束执行时间!");
			return false;
        }
    }

var startTime;
var endTime;
if(shour<10){
	startTime="2013/05/01 0"+stime+":00";
}else{
	startTime="2013/05/01 "+stime+":00";
}
if(ehour<10){
	endTime="2013/05/01 0"+etime+":00";
}else{
	endTime="2013/05/01 "+etime+":00";
}


 //document.getElementById("jhzxjg").value="";
 var time=GetDateDiff(startTime, endTime, "minute");
var jg=parseInt(time/cs);
document.getElementById("jhzxjg").value=jg;
}

function changeJg() {
var jg= document.getElementById("jhzxjg").value;
if(!/^[1-9]\d*$/.test(jg)){
	alert("时间间隔必须为正整数!");
	return false;
	}
var stime=document.getElementById("stime").value;
var etime=document.getElementById("etime").value;

var shour=parseInt(stime.substring(0,stime.indexOf(":")));
var ehour=parseInt(etime.substring(0,etime.indexOf(":")));

//判断开始执行时间小于等于结束执行时间
    if(shour>ehour){
    	alert("开始执行时间小于等于结束执行时间!");
		return false;
    }else if(shour==ehour){
        var sminitue=parseInt(stime.substring(stime.indexOf(":")+1));
        var eminitue=parseInt(etime.substring(etime.indexOf(":")+1));
       	
        if(sminitue>eminitue){
         	alert("开始执行时间小于等于结束执行时间!");
			return false;
        }
    }

var startTime;
var endTime;
if(shour<10){
	startTime="2013/05/01 0"+stime+":00";
}else{
	startTime="2013/05/01 "+stime+":00";
}
if(ehour<10){
	endTime="2013/05/01 0"+etime+":00";
}else{
	endTime="2013/05/01 "+etime+":00";
}
var time=GetDateDiff(startTime, endTime, "minute");
var cs=parseInt(time/jg);
 document.getElementById("jhzxcs").value=cs;
}
function GetDateDiff(startTime, endTime, diffType) { 
 
 var sTime = new Date(startTime); //开始时间 
 var eTime = new Date(endTime); //结束时间 
 //作为除数的数字 
 var divNum = 1; 
 switch (diffType) { 
 case "second": 
 divNum = 1000; 
 break; 
 case "minute": 
 divNum = 1000 * 60; 
 break; 
 case "hour": 
 divNum = 1000 * 3600; 
 break; 
 case "day": 
 divNum = 1000 * 3600 * 24; 
 break; 
 default: 
 break; 
 } 
 return parseInt((eTime.getTime() - sTime.getTime()) / parseInt(divNum)); 
 }

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{

//alert("ddd:"+window.dialogArguments.document.getElementById("record:cj_zq").value);

document.getElementById("record:service_id").value=window.dialogArguments.document.getElementById("record1:service_id").value;
document.getElementById("record:service_no").value=window.dialogArguments.document.getElementById("record1:service_no").value;
document.getElementById("record:service_name").value=window.dialogArguments.document.getElementById("record1:service_name").value;
document.getElementById("record:srv_scheduling_id").value=window.dialogArguments.document.getElementById("record1:srv_scheduling_id").value;
var jhrw_lx = window.dialogArguments.document.getElementById("record1:scheduling_type").value;

if(jhrw_lx==null||jhrw_lx==""){
	setFormFieldValue("record:scheduling_type","01");
}else{
	document.getElementById("record:scheduling_type").value=jhrw_lx;
}

document.getElementById("record:scheduling_week").value=window.dialogArguments.document.getElementById("record1:scheduling_week").value;

document.getElementById("record:interval_time").value=window.dialogArguments.document.getElementById("record1:interval_time").value;
document.getElementById("record:start_time").value=window.dialogArguments.document.getElementById("record1:start_time").value;
document.getElementById("record:end_time").value=window.dialogArguments.document.getElementById("record1:end_time").value;
document.getElementById("record:scheduling_day").value=window.dialogArguments.document.getElementById("record1:scheduling_day").value;
document.getElementById("record:scheduling_count").value=window.dialogArguments.document.getElementById("record1:scheduling_count").value;

	  $('#stime').timepicker({
    showLeadingZero: false,
    showCloseButton: true,
    showNowButton: true
  });
   $('#etime').timepicker({
    showLeadingZero: false,
    showCloseButton: true,
    showNowButton: true
  });
  $("#main_record tr").each(function(){
  	$(this).find("td:first").css("white-space", "nowrap");
  });
 initWeek();
 initDay();
 changeType();
}



_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="设置采集周期"/>
<freeze:errors/>

<freeze:form action="/txn40400003">
  <freeze:block property="record" caption="设置采集周期" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回"hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="srv_scheduling_id" caption="计划任务ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
   
      <freeze:select property="scheduling_type" caption="计划任务类型" colspan="3"   notnull="true" onchange="changeType()" valueset="资源管理_计划任务类型" style="width:30%"/>
      <freeze:hidden property="start_time" caption="计划任务开始时间" colspan="3"  datatype="string" maxlength="11" style="width:95%"/>
      <freeze:hidden property="end_time" caption="计划任务结束时间" colspan="3"  datatype="string" maxlength="11" style="width:95%"/>
      <tr>
		 <td height="32" align="right">计划任务开始时间：</td>
		 <td nowrap  align="left" colspan=""><input id="stime" readonly="true" type="text" value='00:00' style="width:30%" /></td>
	  </tr>
	  <tr>
		 <td height="32" align="right">计划任务结束时间：</td>
		 <td nowrap  align="left" colspan=""><input id="etime" readonly="true" type="text" value='23:59' onChange="changeCs()" style="width:30%" /></td>
	  </tr>
	  <tr id="everyWeek" style="display:none">
		   <td nowrap  align="left" width="150px">周天：</td>
		   <td colspan=3 id="weekTd" ></td>
	  </tr>
	   <tr id="everyMonth" style="display:none">
		   <td nowrap  align="left" width="150px">月天：</td>
		   <td colspan=3 id="dayTd" ></td>
	  </tr>
	  <tr id="zxcs">
		   <td nowrap style="color:red" align="left" width="150px">*计划任务执行次数：</td>
		   <td colspan=3 ><input type="text" id="jhzxcs" value="1" name="jhzxcs" style="width:30%" onBlur="changeCs()" ></td>
	  </tr>
	  <tr id="zxcs">
		   <td nowrap  align="left" width="150px">每次执行时间间隔(分)：</td>
		   <td colspan=3 ><input type="text" id="jhzxjg" name="jhzxjg" style="width:30%" onBlur="changeJg()" ></td>
	  </tr>
	  <freeze:hidden  property="scheduling_week" caption="计划任务周天"   datatype="string" maxlength="100" style="width:95%"/>
     
      <freeze:hidden  property="scheduling_day" caption="计划任务日期"  style="width:95%"/>
      
      <freeze:hidden  property="scheduling_day1" caption="计划任务周期"   datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden  property="scheduling_count" caption="计划任务执行次数"   maxlength="3" />
      <freeze:hidden  property="interval_time" caption="计划任务执行间隔"   maxlength="3" />
     
     
      <freeze:hidden property="creator_id" caption="创建人" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="created_time" caption="创建时间" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="last_modify_id" caption="修改人" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="last_modify_time" caption="修改时间" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="is_markup" caption="有效标记" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="service_id" caption="服务ID" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="service_no" caption="服务编号" datatype="string" maxlength="19" style="width:95%"/>
      <freeze:hidden property="service_name" caption="服务名称" datatype="string" maxlength="19" style="width:95%"/>
	
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
