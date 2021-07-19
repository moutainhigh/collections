<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>抽取任务设置</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery.js"></script>
<style type="text/css">
div{
	background-color:#F1F5FF;
}
</style>
</head>

<script language="javascript">
// 返 回
function func_record_goBack()
{
	goBack();	
}

// 保存的时候首先进行格式校验
// 如果全部满足条件，返回true, 否则报错，并返回false
function verifyAllInput(){
	var timeRgexp = /^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29))$/;
	var numRgexp = /^[1-9]\d*$/;
	
	var startDateFlag = timeRgexp.test($("input#startDate").val());
	if (!startDateFlag){
		alert("【开始日期】不合法，请重新输入!");
		$("input#startDate")[0].focus();
		return false;
	}
	
	if ($("input[@name='endRadio'][@checked]").val() == "0"){
		var endDateFlag = timeRgexp.test($("input#endDate").val());
		if (!endDateFlag){
			alert("【结束日期】不合法，请重新输入!");
			$("input#endDate")[0].focus();
			return false;
		}
	}
	
	if ($("input[@name='endRadio'][@checked]").val() == "1"){
		var runCountFlag = numRgexp.test($("input#runTimesInput").val());
		if (!runCountFlag){
			alert("【运行次数】不合法，请重新输入!");
			$("input#runTimesInput")[0].focus();
			// document.getElementById("runTimesInput").focus();
			return false;
		}	
	}
	return true;
}

// 保存调度信息
function func_record_save()
{
	if (verifyAllInput())
	{
		var schedulerId = $("input#schedulerId").val();
		if (schedulerId != null && schedulerId != "" && schedulerId != "0"){
			var start_time = transDateFormat( $("input#startDate").val(), 
				$("select[@name='selectStartHours'] > option[@selected]").val()+
				":"+$("select[@name='selectStartMinutes'] > option[@selected]").val());
			var end_time = transDateFormat( $("input#endDate").val(), "23:59");
			var run_options = "2";
			if ($("input[@type='radio'][@name='schedulerType'][@checked]").val() == 1){
				run_options = "4";
			}
			var end_options = $("input[@type='radio'][@name='endRadio'][@checked]").val();
			if(!end_options){
				end_options=""
			};
			// alert("end_options:"+end_options);
			
			var run_count = $("input#runTimesInput").val();
			// alert("run_count:"+run_count);
			var delta_value = parseInt($("select[@name='selectDays'] > option[@selected]").val())*24*3600
				+ parseInt($("select[@name='selectHours'] > option[@selected]").val())*3600 
				+ parseInt($("select[@name='selectMinutes'] > option[@selected]").val())*60;
			// alert("delta_value:"+delta_value);
			var pageUrl = "<%=request.getContextPath()%>/txn50106002.ajax?record:start_time="+start_time+"&record:end_time="+end_time
				+"&record:run_options="+run_options+"&record:end_options="+end_options 
				+"&record:run_count="+run_count + "&record:delta_value="+delta_value
				+"&record:scheduler_id="+$("input#schedulerId").val()
				+"&record:dbuser="+$("input#dbUser").val();				
			// alert(pageUrl);
			$.get(pageUrl, function(xmlResults){
				if($("error-code", xmlResults).text()=="000000"){
					var pageUrl2 = "<%=request.getContextPath()%>/txn50106008.ajax?"
						+"&record:wf_name="+$("input#wf_name").val()
						+"&record:rep_foldername="+$("input#rep_foldername").val()
						+"&record:domain_name="+$("input#domain_name").val()
						+"&record:server_name="+$("input#server_name").val();
					$.get(pageUrl2, function(xml){
						if($("error-code", xml).text()=="000000"){
							alert("保存成功！");
							goBack();
						}else{
							alert($("error-desc", xml).text());
						}
					});
				}else{
					alert($("error-desc", xmlResults).text());
				}
				// alert(xmlResults.xml);
			});	
		}else{
			alert("该Workflow没有设定调度信息，需要在etl客户端先进行设定");
		}
	}
}

// 时间格式转换(将"2008-09-23 12:00"的格式转化为ETL中的标准格式"9/23/2008/12/00")
// Date为2008-09-23, Time 为 12:00
function transDateFormat(date, time){
	var dateArray = date.split("-");
	if (dateArray.length==3){
		var rv = parseInt(dateArray[1]) + "/" + parseInt(dateArray[2]) + "/" + dateArray[0] + "/";
		var timeArray = time.split(":");
		if (timeArray.length==2){
			rv += timeArray[0] + "/" + timeArray[1];
			return rv;
		}
		return "";
	}
	return "";
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var workflow_id = "<%=request.getParameter("record:workflow_id")%>";
	var dbuser = "<%=request.getParameter("record:dbuser")%>";
	var getSchedulerSrc = "<%=request.getContextPath()%>/txn50106006.ajax?select-key:workflow_id="
		+workflow_id+"&select-key:dbuser="+dbuser;
	$.get(getSchedulerSrc, function(xmlResults){
		var errCode = $("error-code", xmlResults).text();
		if(errCode != "000000"){
			alert($("error-desc", xmlResults)).text();
			return false;
		}
		var size = $("record/run_options", xmlResults).size();
		if (size==0) // 没有设定的调度信息
		{
		}else if (size==1) // 读取并显示出调度信息
		{
			var start_time = $("record/start_time", xmlResults).text();
			var run_options = $("record/run_options", xmlResults).text();
			var end_options = $("record/end_options", xmlResults).text();
			var run_count = $("record/run_count", xmlResults).text();
			var end_time = $("record/end_time", xmlResults).text();
			var delta_value = $("record/delta_value", xmlResults).text();
			var scheduler_id = $("record/scheduler_id", xmlResults).text();
			$("input#schedulerId").val(scheduler_id);
			if (run_options == 2){
				$("input[@name='schedulerType'][@type='radio'][@value='0']").click();
				$("input[@name='schedulerType'][@type='radio'][@value='0']").attr("checked","checked");
			}else if (run_options == 4){
				$("input[@name='schedulerType'][@type='radio'][@value='1']").click();
				$("input[@name='schedulerType'][@type='radio'][@value='1']").attr("checked","checked");
				if (delta_value != null && delta_value != ""){
					setDeltaTime(delta_value);
				}
				if (end_options == 0){
					$("input[@name='endRadio'][@type='radio'][@value='0']").click();
					$("input[@name='endRadio'][@type='radio'][@value='0']").attr("checked","checked");
					if (end_time != null && end_time != ""){
						var endTimeArray = end_time.split("/");
						if (endTimeArray.length==5){
							$("input#endDate").val(endTimeArray[2] + "-" + endTimeArray[0] + "-" + endTimeArray[1]);
						}
					}
				}else if (end_options == 1){
					$("input[@name='endRadio'][@type='radio'][@value='1']").click();
					$("input[@name='endRadio'][@type='radio'][@value='1']").attr("checked","checked");
					if (run_count != null && run_count != ""){
						$("input#runTimesInput").val(run_count);
					}
				}else if (end_options == 2){
					$("input[@name='endRadio'][@type='radio'][@value='2']").click();
					$("input[@name='endRadio'][@type='radio'][@value='2']").attr("checked","checked");
				}
			}
			if (start_time != null && start_time != ""){
				var timeArray = start_time.split("/");
				if (timeArray.length==5){
					$("input#startDate").val(timeArray[2] + "-" + timeArray[0] + "-" + timeArray[1]);
					$("select[@name='selectStartHours'] > option[@value='"+ timeArray[3] +"']").attr("selected", "selected");
					$("select[@name='selectStartMinutes'] > option[@value='"+ timeArray[4] +"']").attr("selected", "selected");
				}
			}
		}
	});	
	$("input[@name='schedulerType'][@type='radio']").bind("click", clickSchedulerTypeHandler);
	$("input[@name='endRadio'][@type='radio']").bind("click", clickEndRadioHandler);
	// $("input#runTimesInput").bind("keyup", keyupInputHandler);
}

// SchedulerType的click事件函数
function clickSchedulerTypeHandler(){
	if (this.value=='0'){
		// 只运行一次的情况
		$("input[@name='endRadio'][@type='radio']").attr("disabled","disabled");
		$("input[@name='endDate'][@type='text']").attr("disabled","disabled");
		$("input#runTimesInput[@type='text']").attr("disabled","disabled");
		$("select[@name='selectDays']").attr("disabled","disabled");
		$("select[@name='selectHours']").attr("disabled","disabled");
		$("select[@name='selectMinutes']").attr("disabled","disabled");
	}else if(this.value=='1'){
		// 每隔 ** 运行一次的情况
		$("input[@name='endRadio'][@type='radio']").removeAttr("disabled");
		$("input[@name='endRadio'][@type='radio'][@checked]").click();
		$("select[@name='selectDays']").removeAttr("disabled");
		$("select[@name='selectHours']").removeAttr("disabled");
		$("select[@name='selectMinutes']").removeAttr("disabled");
	}
}

// EndRadio的click事件函数
function clickEndRadioHandler(){
	if (this.value=='0'){
		// 指定结束日期时结束的情况
		$("input[@name='endDate'][@type='text']").removeAttr("disabled");
		$("input#runTimesInput[@type='text']").attr("disabled","disabled");
	}else if(this.value=='1'){
		// 运行 ** 次后结束的情况
		$("input[@name='endDate'][@type='text']").attr("disabled","disabled");
		$("input#runTimesInput[@type='text']").removeAttr("disabled");
	}else if(this.value=='2'){
		// 一直运行的情况
		$("input[@name='endDate'][@type='text']").attr("disabled","disabled");
		$("input#runTimesInput[@type='text']").attr("disabled","disabled");
	}
}

// 设置延迟时间的回显
function setDeltaTime(delta_value){
	var days = parseInt(delta_value / (24*3600));
	delta_value =  delta_value % (24*3600);
	var hours = parseInt(delta_value / 3600);
	delta_value =  delta_value % 3600;
	var minutes = parseInt(delta_value / 60);
	$("select[@name='selectDays'] > option[@value='"+ days +"']").attr("selected","selected");
	$("select[@name='selectHours'] > option[@value='"+ hours +"']").attr("selected","selected");
	$("select[@name='selectMinutes'] > option[@value='"+ minutes +"']").attr("selected","selected");
}

function keyupInputHandler(){
	alert(document.getElementById("runTimesInput").value);
	/**
	if (event.keyCode<48 || event.keyCode>57) {
		alert("只能输入数字");
		event.returnValue= false;
	}else{
		event.returnValue= true;
	}**/
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="抽取任务设置"/>
<freeze:errors/>

<freeze:form action="/txn501030008">
  <div style="border:1px solid #7F9DB9; width:95%;height:70px;margin-left:3%;margin-top:10px">
  	<div style="position:relative;top:-5px;left:15px;width:100px;background-color:#F1F5FF;z-index:100;text-align:center;">请选择时间类型</div>
  	<div style="margin-left:30px;line-height:30px;"><input type="radio" name="schedulerType" value="0">只运行一次</div>
  	<div style="margin-left:30px;line-height:30px;"><input type="radio" name="schedulerType" value="1">每隔
  		<select name="selectDays" size="1">
		    <option value="0">0</option>
		    <option value="1">1</option>
		    <option value="2">2</option>
		    <option value="3">3</option>
		    <option value="4">4</option>
		    <option value="5">5</option>
		    <option value="6">6</option>
		    <option value="7">7</option>
		    <option value="8">8</option>
		    <option value="9">9</option>
		    <option value="10">10</option>
		    <option value="11">11</option>
		    <option value="12">12</option>
		    <option value="13">13</option>
		    <option value="14">14</option>
		    <option value="15">15</option>
		    <option value="16">16</option>
		    <option value="17">17</option>
		    <option value="18">18</option>
		    <option value="19">19</option>
		    <option value="20">20</option>
		    <option value="21">21</option>
		    <option value="22">22</option>
		    <option value="23">23</option>
		    <option value="24">24</option>
		    <option value="25">25</option>
		    <option value="26">26</option>
		    <option value="27">27</option>
		    <option value="28">28</option>
		    <option value="29">29</option>
		    <option value="30">30</option>
		  </select>天<select name="selectHours" size="1">
		    <option value="0">0</option>
		    <option value="1">1</option>
		    <option value="2">2</option>
		    <option value="3">3</option>
		    <option value="4">4</option>
		    <option value="5">5</option>
		    <option value="6">6</option>
		    <option value="7">7</option>
		    <option value="8">8</option>
		    <option value="9">9</option>
		    <option value="10">10</option>
		    <option value="11">11</option>
		    <option value="12">12</option>
		    <option value="13">13</option>
		    <option value="14">14</option>
		    <option value="15">15</option>
		    <option value="16">16</option>
		    <option value="17">17</option>
		    <option value="18">18</option>
		    <option value="19">19</option>
		    <option value="20">20</option>
		    <option value="21">21</option>
		    <option value="22">22</option>
		    <option value="23">23</option>
		  </select>小时<select name="selectMinutes" size="1">
		    <option value="0">0</option>
		    <option value="1">1</option>
		    <option value="2">2</option>
		    <option value="3">3</option>
		    <option value="4">4</option>
		    <option value="5">5</option>
		    <option value="6">6</option>
		    <option value="7">7</option>
		    <option value="8">8</option>
		    <option value="9">9</option>
		    <option value="10">10</option>
		    <option value="11">11</option>
		    <option value="12">12</option>
		    <option value="13">13</option>
		    <option value="14">14</option>
		    <option value="15">15</option>
		    <option value="16">16</option>
		    <option value="17">17</option>
		    <option value="18">18</option>
		    <option value="19">19</option>
		    <option value="20">20</option>
		    <option value="21">21</option>
		    <option value="22">22</option>
		    <option value="23">23</option>
		    <option value="24">24</option>
		    <option value="25">25</option>
		    <option value="26">26</option>
		    <option value="27">27</option>
		    <option value="28">28</option>
		    <option value="29">29</option>
		    <option value="30">30</option>
		    <option value="31">31</option>
		    <option value="32">32</option>
		    <option value="33">33</option>
		    <option value="34">34</option>
		    <option value="35">35</option>
		    <option value="36">36</option>
		    <option value="37">37</option>
		    <option value="38">38</option>
		    <option value="39">39</option>
		    <option value="40">40</option>
		    <option value="41">41</option>
		    <option value="42">42</option>
		    <option value="43">43</option>
		    <option value="44">44</option>
		    <option value="45">45</option>
		    <option value="46">46</option>
		    <option value="47">47</option>
		    <option value="48">48</option>
		    <option value="49">49</option>
		    <option value="50">50</option>
			<option value="51">51</option>
			<option value="52">52</option>
			<option value="53">53</option>
			<option value="54">54</option>
			<option value="55">55</option>
			<option value="56">56</option>
			<option value="57">57</option>
			<option value="58">58</option>
			<option value="59">59</option>		    
		  </select>分运行一次
  	</div>
  </div>
  
  <div style="width:95%;margin-left:3%;margin-top:10px">
  	<div style="width:49%; height:100px; float:left;border:1px solid #7F9DB9;">
  		<div style="position:relative;top:-5px;left:15px;width:80px;background-color:#F1F5FF;z-index:100;text-align:center;">开始选项</div>
  		<div style="float:left;width:30%;text-align:right;line-height:30px;">开始日期：</div>
  		<div style="float:left;width:100px;height:30px;clear:right;"><freeze:datebox caption="开始时间" property="startDate" numberformat="1"/></div>
  		<div style="float:left;clear:left;width:30%;line-height:30px;text-align:right;">开始时间：</div>
  		<div style="float:left;width:60%;line-height:30px;"><select name="selectStartHours" size="1">
		    <option value="00">00</option>
		    <option value="01">01</option>
		    <option value="02">02</option>
		    <option value="03">03</option>
		    <option value="04">04</option>
		    <option value="05">05</option>
		    <option value="06">06</option>
		    <option value="07">07</option>
		    <option value="08">08</option>
		    <option value="09">09</option>
		    <option value="10">10</option>
		    <option value="11">11</option>
		    <option value="12">12</option>
		    <option value="13">13</option>
		    <option value="14">14</option>
		    <option value="15">15</option>
		    <option value="16">16</option>
		    <option value="17">17</option>
		    <option value="18">18</option>
		    <option value="19">19</option>
		    <option value="20">20</option>
		    <option value="21">21</option>
		    <option value="22">22</option>
		    <option value="23">23</option>
		  </select>时<select name="selectStartMinutes" size="1">
		    <option value="00">00</option>
		    <option value="01">01</option>
		    <option value="02">02</option>
		    <option value="03">03</option>
		    <option value="04">04</option>
		    <option value="05">05</option>
		    <option value="06">06</option>
		    <option value="07">07</option>
		    <option value="08">08</option>
		    <option value="09">09</option>
		    <option value="10">10</option>
		    <option value="11">11</option>
		    <option value="12">12</option>
		    <option value="13">13</option>
		    <option value="14">14</option>
		    <option value="15">15</option>
		    <option value="16">16</option>
		    <option value="17">17</option>
		    <option value="18">18</option>
		    <option value="19">19</option>
		    <option value="20">20</option>
		    <option value="21">21</option>
		    <option value="22">22</option>
		    <option value="23">23</option>
		    <option value="24">24</option>
		    <option value="25">25</option>
		    <option value="26">26</option>
		    <option value="27">27</option>
		    <option value="28">28</option>
		    <option value="29">29</option>
		    <option value="30">30</option>
		    <option value="31">31</option>
		    <option value="32">32</option>
		    <option value="33">33</option>
		    <option value="34">34</option>
		    <option value="35">35</option>
		    <option value="36">36</option>
		    <option value="37">37</option>
		    <option value="38">38</option>
		    <option value="39">39</option>
		    <option value="40">40</option>
		    <option value="41">41</option>
		    <option value="42">42</option>
		    <option value="43">43</option>
		    <option value="44">44</option>
		    <option value="45">45</option>
		    <option value="46">46</option>
		    <option value="47">47</option>
		    <option value="48">48</option>
		    <option value="49">49</option>
		    <option value="50">50</option>
			<option value="51">51</option>
			<option value="52">52</option>
			<option value="53">53</option>
			<option value="54">54</option>
			<option value="55">55</option>
			<option value="56">56</option>
			<option value="57">57</option>
			<option value="58">58</option>
			<option value="59">59</option>		    
		  </select>分
  		</div>
  	</div>
  	<div style="width:49%; height:100px;float:right;border:1px solid #7F9DB9;">
  		<div style="position:relative;top:-5px;left:15px;width:70px;background-color:#F1F5FF;z-index:100;text-align:center;">结束选项</div>
  		<div style="float:left;line-height:30px;margin-left:10px"><input type="radio" name="endRadio" value="0">结束日期：</div>
  		<div style="float:left;width:100px;height:30px;clear:right;"><freeze:datebox caption="结束时间" property="endDate" numberformat="1" /></div>
  		<div style="float:left;clear:both;line-height:30px;width:90%;margin-left:10px"><input type="radio" name="endRadio" value="1">运行<input type="text" size="4" id="runTimesInput" />次后结束</div>
  		<div style="float:left;clear:both;line-height:30px;margin-left:10px"><input type="radio" name="endRadio" value="2">一直运行</div>
  	</div>
  </div>
  <div style="margin-top:15px;text-align:center;">
  	<input type="button" value="保存" class="menu" style="width:60px;" onclick="func_record_save();" />
  	<input type="button" value="返回" class="menu" style="width:60px;" onclick="func_record_goBack();" />
  </div>
  <div style="display:none">
  	<input type="text" id="schedulerId" value="" />
  	<input type="text" id="dbUser" value="<%=request.getParameter("record:dbuser")%>" />
  	<input type="text" id="wf_name" value="<%=request.getParameter("record:wf_name")%>" />
  	<input type="text" id="rep_foldername" value="<%=request.getParameter("record:rep_foldername")%>" />
  	<input type="text" id="domain_name" value="<%=request.getParameter("record:domain_name")%>" />
  	<input type="text" id="server_name" value="<%=request.getParameter("record:server_name")%>" />
  </div>
</freeze:form>
</freeze:body>
</freeze:html>
