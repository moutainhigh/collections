<%@ page contentType="text/html; charset=GBK" %>
<%@page import="com.gwssi.common.constant.ExConstant" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%@ page import = "cn.gwssi.common.context.Recordset"%>
<%@ page import="cn.gwssi.common.context.vo.VoUser"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="cn.gwssi.common.context.TxnContext"%>	
<%-- template single/single-table-query.jsp --%>
<freeze:html width="650" height="350">
<head>
<title>查询例外日期列表</title>
<%
	HttpSession usersession = request.getSession(false);
		VoUser voUser = (VoUser) usersession
				.getAttribute(TxnContext.OPER_DATA_NODE);
%>

<link rel='stylesheet' type='text/css' href='/script/fullcalendar/fullcalendar.css' />
<link rel='stylesheet' type='text/css' href='/script/fullcalendar/fullcalendar.print.css' media='print' />
<script type='text/javascript' src='/script/lib/jquery/jquery-1.7.1.min.js'></script>
<script type='text/javascript' src='/script/lib/jquery/jquery-ui-1.8.17.custom.min.js'></script>
<script type='text/javascript' src='/script/fullcalendar/fullcalendar.js'></script>
<script type="text/javascript" src="/script/page/calendar.js"></script>
<!-- <script type="text/javascript" src="/script/lib/jquery171.js"></script> -->
<style type="text/css">
.Calendar {
	font-family: Verdana;
	font-size: 12px;
	text-align: center;
	width: 100%;
	padding: 10px;
}
.Calendar table {
	width: 100%;
	background-color: #6699ff;
	table-layout: fixed;
}
.Calendar table td {
	font-size: 13px;
	background-color: #F1F5FF;
	min-height: 60px;
	_height: 60px;
	overflow-y: visible;
}
.Calendar table tr.thead td{
	height: 30px;
	min-height: 30px;
	_height: 30px;
}
.Calendar td div.normal, .Calendar td div.today{
	width: 18px;
	text-align: center;
	height: 18px;
	color: #333;
	float: left;
}
.Calendar td div.cworkday{
	width:60px;
	text-align: right;
	float:right;
	line-height: 18px;
	height: 18px;
	color: #999;
	overflow: hidden;
}
.Calendar td div.today{
	color: #C60;
	fontWeight: bold;
}
.Calendar td div.tasklist{
	color: #555;
	width: 100%;
}
.Calendar td div.tasklist ul{
	list-style-type: none;
	margin: 0;
	padding: 0;
}
.Calendar td div.tasklist ul li{
	width: 100%;
	height: 15px;
	line-height: 15px;
}
table.calTable {
	/*border: 1px solid #6699FF;*/
	width: 95%;
}
.selectedDateDisplayValueSpancls {
	font-size: 18px;
	width:95%;
	text-align: center;
	padding: 5px 10px;
}
#resultContainer {
	width: 100%;
	text-align: left;
	height: 100%;
	margin-top: 1em;
}
.selected{
	width: 100px;
	height:24px;
	line-height: 24px;
}
#idCalendarYear{
	_width: 60px;
}
.fc-event-skin{
background-color:rgb(53,153,255);
}
.fc-border-separate td{
	background: #f7f7f7;
}
.fc-holiday{
	background: #fcfbe6 !important;
}
.holiday_desc {color: #555; float:left;}
.fc-other-month .holiday_desc{color: #999;}
.fc-button-agendaDay .fc-button-inner, .fc-button-agendaWeek .fc-button-inner, .fc-button-month .fc-button-inner{background:#f3f3f3;}
.fc-state-active .fc-button-inner{background:#39f;}
.fc-state-active{border: 1px solid #39f;}
.fc-border-separate td.fc-today{background:rgb(215, 235, 225) !important;}
</style>
</head>

<script language="javascript">
var multiDate = 0;	//0 一天	1 日期段
window.specialDesc = [];
var specialDay = [];
window.holidays = [];
<%
DataBus context = (DataBus) request.getAttribute("freeze-databus");
Recordset rs = context.getRecordset("record");
for(int ii=0; ii<rs.size(); ii++){
	out.println("specialDay.push('"+rs.get(ii).getValue("exception_date")+"');");
	out.println("specialDesc.push('"+rs.get(ii).getValue("exception_desc")+"');");
}
%>
for(var i=0; i<specialDay.length;i++){
	holidays.push(+(DateToJSDate(specialDay[i])));
}
// 设置日期为时间段
function setMultiDate(type){
	if(type == 0){
		multiDate = 1;
		$("#multiAddDate").show();
		//$("#start_date").val($("#selectedDateValueInput").val());
		//$("#end_date").val("");
	}else{
		multiDate = 0;
		$("#start_date").val("");
		$("#end_date").val("");
		$("#multiAddDate").hide();
	}
}

function closeDiv(){
	document.getElementById("start_date").value = '';
	document.getElementById("end_date").value = '';
	setMultiDate(1);
	document.getElementById("resultContainer").style.display = "none";
}

// 保存日历设置
function saveSpecialDate(){
	var checkResult = checkBeforeSubmit();
	if(checkResult == false)
		return;
	var paramValue = '';
	var urlPath = '';
	var type = $("input[name='radioArray']:checked").val();
	if(type == '0'){
		paramValue = "record:exception_date_name="+$("#exception_date_name").val()
			+ "&record:exception_desc="+$("#exception_desc").val()+"&record:creator_id=<%=voUser.getValue("userID")%>&" 
			+ "record:exception_date="+$("#start_date").val()+"&record:end_date="+$("#end_date").val();
		urlPath = "<%=request.getContextPath()%>/txn205006.ajax";
	}else if(type == '1'){
		paramValue = "record:exception_date="+$("#start_date").val()
			+ "&record:end_date="+$("#end_date").val();
		urlPath = "<%=request.getContextPath()%>/txn205007.ajax";
	}else{
		return;
	}
	if(checkResult != false){
	$.ajax({
	  type: "post",
	  url: urlPath + "?" + paramValue,
	  async: false,
	  success: function(xmlResults){
	  	if (xmlResults.selectSingleNode("//context/error-code").text != "000000"){
	  		alert(xmlResults.selectSingleNode("//context/error-desc").text);
	  		return false;
	  	}
	  }
	});
	location.reload();	//不需要完整刷新页面，只需要刷新fullcalendar的event列表
	}
}

function pause(type){
	var word = '';
	if(type=='<%=ExConstant.IS_MARKUP_N%>')
		word = '暂停';
	else if(type=='<%=ExConstant.IS_MARKUP_Y%>'){
		word = '启用';
	}
	var r = confirm("确定"+word+"所有例外吗?");
	if(r==true){
	$.ajax({
	  type: "post",
	  url: "<%=request.getContextPath()%>/txn205010.ajax?record:is_markup="+type,
	  async: false,
	  success: function(xmlResults){
	  	if (xmlResults.selectSingleNode("//context/error-code").text != "000000"){
	  		alert(xmlResults.selectSingleNode("//context/error-desc").text);
	  		return false;
	  	}
	  }
	});
	location.reload();
	}
}

// 提交前的校验
// 例外日期不能为空，起始日期小于等于截止日期
// 如果是设置工作日为工作日，或者设置非工作日为非工作日，则不保存
function checkBeforeSubmit(){
	if(multiDate == 1){
  		if($("#start_date").val().length == 0 || $("#end_date").val().length == 0){
  			return false;
  		}
  		if($("#start_date").val() >= $("#end_date").val()){
  			alert("请选择合适的日期范围.");
  			$("#start_date").val(''); 
  			$("#end_date").val('');
  			return false;
  		}
  	}
	var desc = $("#exception_desc").val();
	if(desc.length > 100){
		alert("例外原因不能超过100字.");
		return false;
	} 
}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	//insertTableNo();
	
	$(document).ready(function() {
		
		var date = new Date();
		var d = date.getDate();
		var m = date.getMonth();
		var y = date.getFullYear();
		
		$('#calendar_task').fullCalendar({
			header: {
				left: 'prev,next,today',
				center: 'title',
				right: 'month'
			//	right: 'month,agendaWeek,agendaDay'
			},
			editable: true,currentTimezone: 'Asia/Beijing',  
            eventClick: function(event) { 
            	//显示任务详细信息
            },
            dayClick: function(date, allDay, jsEvent, view ){ //设置当前日期例外状态 
            	if(view.name != 'month'){
            		return; //只在月视图下有效
            	}
            	if(date.getMonth() != $('#calendar_task').fullCalendar('getDate').getMonth()){
            		return;	//非当月日期
            	}
            	if(date.getDate() < $('#calendar_task').fullCalendar('getDate').getDate()){
            		return; //小于当前日期
            	}
                //调整弹出层显示位置
				$('#resultContainer').css("position", "absolute");
				var rcWidth = $("#resultContainer").width();
				var rcHeight = $("#resultContainer").height();
				var mLeft = jsEvent.pageX;
				var mTop = jsEvent.pageY;
				var tbWidth = $("#calendar_task").width();
				var tbHeight = $("#calendar_task").height();
				var leftW = (mLeft + rcWidth) > tbWidth ? (tbWidth-rcWidth) : mLeft;
				var topW = (mTop + rcHeight) > tbHeight ? (tbHeight-rcHeight) : mTop;
				document.getElementById("resultContainer").style.left = leftW;
				document.getElementById("resultContainer").style.top = topW;
				document.getElementById("resultContainer").style.zIndex = "20";
				if($(this).hasClass('fc-holiday')){
					$('#notWorkday').attr("checked", true);
				}else{
					$('#workday').attr("checked", true);
				}
                $('#resultContainer').show();
                var year = date.getFullYear();
                var month = (date.getMonth()+1)+'';
                month = (month.length == 1) ? '0'+month : month;
                day = date.getDate() + '';
                day = (day.length == 1) ? '0'+day : day;
                var dateStr = year+'-'+month+'-'+day;
                $('#selectedDateValueInput').val(dateStr);
                $('#start_date').val(dateStr);
                $('#end_date').val(dateStr);
            }, 
			//events: taskList	//任务列表
			events: function(start,end, callback) {
				
		    },
		    axisFormat: 'H(:mm)',
		    timeFormat: 'H:mm{-H:mm}',
		    firstHour: '8',
		    maxTime: '24', 
		    slotMinutes: 10,
		    minTime: '1', 
		    weekMode: 'liquid'//,
		    //holidays: specialdays
		});
        
	});
	
	$('.fc-border-separate tbody>tr>td>div').each(function(){
		$(this).height('90');
	});
	//$('.fc-agenda-divider').next('div').css("overflow-y", "visible");
	$('td.fc-header-right span').hide();
}

function DateToJSDate(date) {
    var regex=/^([0-9]{2,4})-([0-1][0-9])-([0-3][0-9])$/;
    var parts=date.replace(regex,"$1 $2 $3").split(' ');
    return new Date(parts[0],parts[1]-1,parts[2],0,0,0);
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body style="padding-bottom:5px;">
<freeze:title caption="服务时间日历"/>
<freeze:errors/>

<div style="text-align:center; width:100%; margin-bottom: 10px;">
<table align='center' cellpadding='0' cellspacing='0' style='width: 95%;'>
	<tr><td height='30' align='center'><div id='calendar_task'></div></td></tr>
</table>
<div style="display: none; width: 320px; height: 200px;background-color:white;filter: alpha(opacity=90); opacity:0.90; border: 1px solid rgb(102, 153, 255);"
			id="resultContainer">
			<div class="selectedDateDisplayValueSpancls">
				<span id="selectedDateDisplayValueSpan"></span><a
					href="javascript:setMultiDate(0)">设为起始日期</a> <input
					style="display: none;" id="selectedDateValueInput" />
				<div style="width: 100%; height: 30px; font-size: 13px;">
					<input name="radioArray" value="0" type="radio" id="notWorkday" /><span>非工作日</span>
					<input name="radioArray" type="radio" value="1" id="workday" /><span>工作日</span>
				</div>
			</div>
			<div style="display: none;" id="multiAddDate">
				开始日期<input onclick="calendar(this, 1)" id="start_date" type="text"
					name="start_date" /><br /> 结束日期<input onclick="calendar(this, 1)"
					id="end_date" type="text" name="end_date" /> <a
					href="javascript:setMultiDate(1)">取消日期设置</a>
			</div>
			<div id="info">
				<span style="display: none;">例外名称</span> <input
					id="exception_date_name" type="hidden" name="exception_date_name" />
				<table width='100%' border=0>
					<tr>
						<td valign="top">例外原因</td>
						<td><textarea maxlength="100" id="exception_desc" rows="3" cols="30"
								name="exception_desc"></textarea></td>
					</tr>
				</table>
			</div>
			<div style="text-align: center;">
				<!-- <input type="button" value="保 存" onclick="saveSpecialDate()" style="width:60px;" /> -->
				<div class="btn_save" onclick="saveSpecialDate()"></div>
				&nbsp;&nbsp;
				<div class="btn_cancel" onclick="closeDiv()"></div>
			</div>
		</div>
</div>
</freeze:body>
</freeze:html>
