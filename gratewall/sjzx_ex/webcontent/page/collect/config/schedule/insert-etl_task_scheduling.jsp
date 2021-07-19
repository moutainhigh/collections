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
var weekArry = new Array("一","二","三","四","五","六","日"); 
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
    
    var day;
    var weekDay;
	var week="";
	var checkdWeekDay = 0;
	if(document.getElementById("everyWeek").style.display == "block"){
	
		weekDay=document.getElementsByName("weekDay");
		 
		if(weekDay!=null&&weekDay.length>0){
			for(var i=0;i<weekDay.length;i++){
			   if(weekDay[i].checked==true){
			     //week+=weekDay[i].nextSibling.nodeValue+",";
			     week+=weekDay[i].value+",";
			     flag="true";
			     checkdWeekDay++;
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
		day=document.getElementById("day").value;
		
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
		alert("计划任务次数不能为空且必须为正整数!");
		return false;
	}
	if(cs!=null&&cs!=""){
		  setFormFieldValue("record:scheduling_count",cs);//次数
	}
	
	var jg=document.getElementById("jhzxjg").value;
	if(!/^[1-9]\d*$/.test(jg)){
		alert("每次执行时间间隔不能为空且必须为正整数!");
		return false;
	}
	if(jg!=null&&jg!=""){
		  setFormFieldValue("record:interval_time",jg);//间隔
	}
	var taskType=getFormFieldValue( "record:scheduling_type");
	
	var cjzq = getShowWord(taskType,stime,etime,cs,jg,day,weekDay,week,checkdWeekDay);
	
	var schedule_json = '{"scheduling_type" :"'+ taskType+'","stime" :"'+ stime+'","etime" :"'+ etime+'","scheduling_week" :"'+ week+'","scheduling_day" :"'+ day+'","scheduling_count" :"'+ cs+'","interval_time" :"'+ jg+'"}';
	
		  //  window.dialogArguments.document.getElementById("record:scheduling_type").value = taskType; 
		  //  window.dialogArguments.document.getElementById("record:start_time").value = stime; 
		  //  window.dialogArguments.document.getElementById("record:end_time").value = etime; 
		  //  window.dialogArguments.document.getElementById("record:scheduling_week").value = weekDay; 
		  //  window.dialogArguments.document.getElementById("record:scheduling_day").value = day; 
		  //  window.dialogArguments.document.getElementById("record:scheduling_count").value = cs; 
		  //  window.dialogArguments.document.getElementById("record:interval_time").value = jg; 
		  	window.dialogArguments.document.getElementById("record:start_time").value = stime; 
		    window.dialogArguments.document.getElementById("record:schedule_json").value = schedule_json; 
		    window.dialogArguments.document.getElementById("record:inteval").value = cjzq; 
		    window.close();
		
}

function getShowWord(taskType,stime,etime,cs,jg,day,weekDay,week,checkdWeekDay){
    var resultWord="";
	if(taskType!=null&&taskType=="01"){//每日
			resultWord="周一到周日，";
			if (cs=="1") {
				resultWord+="每天一次";
			} else {
				var a = parseInt(jg);
				var b = 60;
				var c = parseInt(a / b);
				if (c >= 1) {
					resultWord+="每";
					resultWord+=c;
					resultWord+="小时一次";
				} else {
					resultWord+="每";
					resultWord+=jg;
					resultWord+="分钟执行一次";
				}
			}
	}else if(taskType!=null&&taskType=="02"){//每周
			if (checkdWeekDay == 7) {
				resultWord="周一到周日，";
			} else if (week=="1,2,3,4,5") {
				resultWord="周一到周五，";
			} else {
				
				var weekNum = week.split(",");
				for (var i = 0; i < weekNum.length; i++) {
					if (i == 0) {
						resultWord = resultWord+"周"+ weekArry[parseInt(weekNum[i]) - 1] + " ";
					} else {
						resultWord = resultWord+",周"+ weekArry[parseInt(weekNum[i]) - 1] + " ";
					}
				}
			}
			if (cs=="1") {
				resultWord+="每天一次";
			} else {
				var a = parseInt(jg);
				var b = 60;
				var c = parseInt(a / b);
				if (c >= 1) {
					resultWord+="每";
					resultWord+=c;
					resultWord+="小时一次";
				} else {
					resultWord+="每";
					resultWord+=jg;
					resultWord+="分钟执行一次";
				}
			}
	}else if(taskType!=null&&taskType=="03"){//每月
		    resultWord="每月第"+day+"天，";
			if (cs=="1") {
				resultWord+="执行一次";
			} else {
				var a = parseInt(jg);
				var b = 60;
				var c = parseInt(a / b);
				if (c >= 1) {
					resultWord+="每";
					resultWord+=c;
					resultWord+="小时一次";
				} else {
					resultWord+="每";
					resultWord+=jg;
					resultWord+="分钟执行一次";
				}
			}
	}
	
	return resultWord;
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
var cs=parseInt(time)/jg;
 document.getElementById("jhzxcs").value=parseInt(cs);
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

var schedule_json_string = window.dialogArguments.document.getElementById("record:schedule_json").value;
var schedule_json;
if(schedule_json_string==null || schedule_json_string=="" ){
	schedule_json ={
	    
		"scheduling_week" : "",
		"interval_time" : "",
		"stime" : "",
		"etime" : "",
		"scheduling_day" : "",
		"scheduling_count" : "",
		"scheduling_type" : ""
	};
}else{
	schedule_json = eval('('+ schedule_json_string +')');
}
//var jhrw_lx = window.dialogArguments.document.getElementById("record:scheduling_type").value;
var jhrw_lx = schedule_json.scheduling_type;
if(jhrw_lx==null||jhrw_lx==""){
	setFormFieldValue("record:scheduling_type","01");
}else{
	document.getElementById("record:scheduling_type").value=jhrw_lx;
}

//document.getElementById("record:scheduling_week").value=window.dialogArguments.document.getElementById("record:scheduling_week").value;
//document.getElementById("record:interval_time").value=window.dialogArguments.document.getElementById("record:interval_time").value;
//document.getElementById("record:start_time").value=window.dialogArguments.document.getElementById("record:start_time").value;
//document.getElementById("record:end_time").value=window.dialogArguments.document.getElementById("record:end_time").value;
//document.getElementById("record:scheduling_day").value=window.dialogArguments.document.getElementById("record:scheduling_day").value;
//document.getElementById("record:scheduling_count").value=window.dialogArguments.document.getElementById("record:scheduling_count").value;


document.getElementById("record:scheduling_week").value=schedule_json.scheduling_week;
document.getElementById("record:interval_time").value=schedule_json.interval_time;
document.getElementById("record:start_time").value=schedule_json.stime;
document.getElementById("record:end_time").value=schedule_json.etime;
document.getElementById("record:scheduling_day").value=schedule_json.scheduling_day;
document.getElementById("record:scheduling_count").value=schedule_json.scheduling_count;


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

<freeze:form action="/txn30801003">
  <freeze:block property="record" caption="设置采集周期" width="95%">
      <freeze:button name="record_saveAndExit" caption="保 存" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回"hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="task_scheduling_id" caption="计划任务ID" datatype="string" maxlength="32" minlength="1" style="width:95%"/>
   
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
	  <tr  id="zxcs" style="display:none">
		   <td  style="color: red; white-space: nowrap;" align="left" width="150px">*计划任务执行次数：</td>
		   <td colspan=3 ><input type="text" id="jhzxcs" name="jhzxcs" style="width:30%" onBlur="changeCs()" ></td>
	  </tr>
	  <tr id="zxcs" >
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
      <freeze:hidden property="collect_task_id" caption="采集任务ID" datatype="string" maxlength="19" style="width:95%"/>
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
