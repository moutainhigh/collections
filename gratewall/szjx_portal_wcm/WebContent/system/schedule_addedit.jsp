<%--
/** Title:			schedule_addedit.jsp
 *  Description:
 *		计划调度的添加修改页面
 *  Copyright: 		www.trs.com.cn
 *  Company: 		TRS Info. Ltd.
 *  Author:			CH
 *  Created:		2005-04-05 13:07:21
 *  Vesion:			1.0
 *  Last EditTime:	2005-04-05 / 2005-04-05
 *	Update Logs:
 *		CH@2005-04-05 产生此文件
 *
 *  Parameters:
 *		see schedule_addedit.xml
 *
 */
--%>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" errorPage="../include/error.jsp"%>
<!------- WCM IMPORTS BEGIN ------------>
<%@ page import="com.trs.components.common.job.JobWorkerType" %>
<%@ page import="com.trs.components.common.job.JobWorkerTypes" %>
<%@ page import="com.trs.components.common.job.Schedule" %>
<%@ page import="com.trs.infra.persistent.WCMFilter" %>
<%@ page import="com.trs.infra.util.CMyDateTime" %>
<%@ page import="com.trs.presentation.locale.LocaleServer" %>
<%@ page import="com.trs.presentation.util.PageViewUtil" %>
<%@ page import="com.trs.infra.util.CMyString" %>
<!------- WCM IMPORTS END ------------>

<!--- 页面状态设定、登录校验、参数获取，都放在public_server.jsp中 --->
<%@include file="../include/public_server.jsp"%>
<%!boolean IS_DEBUG = false;%>

<%
//4.初始化(获取数据)
	int currTypeId = currRequestHelper.getInt("TypeId", 0);
	if(currTypeId <= 0){
		//默认取第一种类型
		currTypeId = 1;
	}

	int nScheduleId = currRequestHelper.getInt("ScheduleId", 0);
	Schedule currSchedule = null;
	if(nScheduleId > 0){
		currSchedule = Schedule.findById(nScheduleId);
		currTypeId = currSchedule.getWorkerTypeId();
		if(currSchedule == null){
			throw new WCMException(ExceptionNumber.ERR_OBJ_NOTFOUND, "获取ID为["+nScheduleId+"]的计划调度失败！");
		}
	}else{//nScheduleId==0 create new
		currSchedule = Schedule.createNewInstance();
	}

//5.权限校验
	if(nScheduleId > 0){
		if(!currSchedule.canEdit(loginUser)){
			throw new WCMException(ExceptionNumber.ERR_OBJ_LOCKED, "计划调度被用户["+currSchedule.getLockerUserName()+"]锁定！");
		}
	}

//6.业务代码
	boolean bAddMode = (nScheduleId == 0);
	int nSchMode = (currSchedule==null)?0:currSchedule.getMode();

	CMyDateTime timeNow = CMyDateTime.now();
	CMyDateTime timeEnd = CMyDateTime.now().dateAdd(CMyDateTime.MINUTE, 30);
	CMyDateTime exeTime = null;
	CMyDateTime startTime = null;
	CMyDateTime endTime = null;
	int nInterval = 30;

	if(nScheduleId > 0){
		if(currSchedule.getMode() == Schedule.MODE_MORE_TIMES_DAY){
			exeTime = timeNow;
			startTime = (currSchedule.getStartTime() == null) ? timeNow : currSchedule.getStartTime();
			endTime = (currSchedule.getEndTime() == null) ? timeEnd : currSchedule.getEndTime();
			nInterval = currSchedule.getParam();
		} else {
			exeTime = currSchedule.getExeTime();
			startTime = timeNow;
			endTime = timeEnd;
		}
	} else {
		exeTime = timeNow;
		startTime = timeNow;
		endTime = timeEnd;
		nInterval = 30;
	}

//7.结束
	out.clear();
%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>TRS WCM 5.2 <%=LocaleServer.getString("schedule.label.scheduleaddedit", "新建/修改调度任务")%>:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::</TITLE>
<base target="_self">
<LINK href="../style/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/CTRSCalendar_Obj.js"></script>
<script type="text/javascript" src="../js/CTRSCalendar.js"></script>
<script type="text/javascript" src="../js/calendar_lang/cn_utf8.js"></script>
<style type="text/css"> @import url("../js/calendar_style/calendar-blue.css"); </style>
<%@include file="../include/public_client_addedit.jsp"%>
<SCRIPT LANGUAGE="JavaScript">
function setTimeValue(_sElName){
	var sElName = _sElName || "";
	if(sElName == null || sElName.length <= 0){
		CTRSAction_alert("无效的Element名称！");
		return;
	}
	var frm = document.all("frmData");
	var oElement = eval("frm."+sElName);
	var oHour = eval("frm."+sElName+"HOUR");
	var oMinute = eval("frm."+sElName+"MIN");
	if(oElement == null || oHour == null || oMinute == null){
		return;
	}
	var sSTime = oElement.value;
	sSTime += " " + oHour.value + ":" + oMinute.value;
	oElement.value = sSTime;
	return;
}

function finishTimeSetting(_nMode){
	var nValue = _nMode || <%=Schedule.MODE_ONE_TIME_DAY%>;
	var arElName = new Array();
	switch(nValue){
		case "3":
			arElName[0] = "ETIME";
			break;
		case "1":
			arElName[0] = "ETIME";
			break;
		case "2":
			arElName[0] = "SDATE";
			arElName[1] = "EDATE";
			break;
		default:
			CTRSAction_alert("错误的运行机制！");
			return;
	}
	for(var i=0; i<arElName.length; i++){
		setTimeValue(arElName[i]);
	}
}

function setParamAttri(_bSet){
	var oElement = document.all.frmData.PARAM;
	if(!oElement){
		return;
	}
	if(_bSet){
		oElement.pattern = "integer";
	} else {
		oElement.pattern = null;
	}
}

function selectChange(_oSelect){
	var nValue = _oSelect.value || <%=Schedule.MODE_ONE_TIME_DAY%>;
	var trETime = document.all.trETime;
	var divETime = document.all.divETime;
	var trParam = document.all.trParam;
	var arTrSEDate = document.getElementsByName("trSEDate");
	var trOneTimeDay = document.all.trOneTimeDay;
	var i = 0;
	switch(nValue){
		case "3"://One Time Only
			trETime.style.display = "inline";
			divETime.style.display = "inline";
			trParam.style.display = "none";
			trOneTimeDay.style.display = "none";
			setParamAttri(false);
			for(i=0; i<arTrSEDate.length; i++){
				arTrSEDate[i].style.display = "none";
			}
			return;
		case "1"://One Time per Day
			trETime.style.display = "none";
			divETime.style.display = "none";
			trParam.style.display = "none";
			trOneTimeDay.style.display = "inline";
			setParamAttri(false);
			for(i=0; i<arTrSEDate.length; i++){
				arTrSEDate[i].style.display = "none";
			}
			return;
		case "2"://More Time per Day
			trETime.style.display = "none";
			divETime.style.display = "none";
			trParam.style.display = "inline";
			trOneTimeDay.style.display = "none";
			setParamAttri(true);
			for(i=0; i<arTrSEDate.length; i++){
				arTrSEDate[i].style.display = "inline";
			}
			return;
		default:
			CTRSAction_alert("错误的运行机制！");
			return;
	}
}

function getOpArgsAndDraw(_nOpType){
	var nOpType = _nOpType || 0;
	var iframeOpArgs = document.all("ifrmOpArgs");
	var sUrl = "../system/operargs_get.jsp?TypeId="+nOpType+"&ScheduleId=<%=nScheduleId%>"; 
	iframeOpArgs.src = sUrl;
}

function drawParamHtml(_arParams){
	if(_arParams == null || _arParams.length != 3){
		CTRSAction_alert("传入参数有误，参数数组长度必须等于2！");
		return;
	}
	var arParamName = new Array();
	arParamName = _arParams[0];
	var arParamDisp = new Array();
	arParamDisp = _arParams[1];
	var arParamValue = new Array();
	arParamValue = _arParams[2];
	if(arParamName == null || arParamDisp == null || arParamName.length != arParamDisp.length){
		CTRSAction_alert("传入参数有误，参数名称与描述不一致！");
		return;
	}

	var oTable = document.all("tbParams");
	if(oTable == null){
		CTRSAction_alert("页面中没有定义放置参数的TABLE[tbParams]！");
		return;
	}

	var nMaxRows = oTable.rows.length;
	for(var i=(nMaxRows-1); i>=0; i--){
		oTable.deleteRow(i);
	}

	var oTR = null;
	var oTD = null;

	if(arParamName.length <= 0){
		oTR = oTable.insertRow();

		oTD = oTR.insertCell(0);
		oTD.align = "left";
		oTD.innerText = "无参数配置";
		return;
	}

	for(var i=0; i<arParamName.length; i++){
		oTR = oTable.insertRow();

		oTD = oTR.insertCell(0);
		oTD.align = "left";
		oTD.width = "20%";
		oTD.innerText = arParamDisp[i];
		
		oTD = oTR.insertCell(1);
		oTD.width = "80%";
		var sHtml = "<INPUT TYPE=\"text\" NAME=\""+arParamName[i]+"\" FieldName=\"OPARGS\" IsAttr=1  pattern=\"string\" elname=\""+arParamDisp[i]+"\" max_len=\"200\"";
		sHtml += " value=\"" + (arParamValue[i] || "") + "\">";
		oTD.innerHTML = sHtml;
	}
}

function onChangeType(_elSelect){
	var nCurrTypeId = <%=(nScheduleId==0)?currTypeId:currSchedule.getWorkerTypeId()%>;
	var nSelectedTypeId = _elSelect.value || 0;
	
	getOpArgsAndDraw(nSelectedTypeId);
}

function submitForm(){
	var frmData = document.frmData;
	if(frmData.SCHMODE.value=="1") {
		if(!validateTime(frmData.OneTimeDay))
			return;
		frmData.ETIME.value = "<%=exeTime.toString("yyyy-MM-dd")%> "+frmData.OneTimeDay.value;
	} else if(frmData.SCHMODE.value=="2") {
		//验证开始时间、结束时间的格式
		var oStartTime = frmData.MoreTimesDay_SDATE;
		var oEndTime = frmData.MoreTimesDay_EDATE;
		if(!validateTime(oStartTime) || !validateTime(oEndTime)) {
			return;
		}
		//验证开始时间和结束时间
		var nStartTime = new Number( transTime(oStartTime.value) );
		var nEndTime = new Number( transTime(oEndTime.value) );
		if(nStartTime >= nEndTime) {
			CTRSAction_alert("[结束时间]要晚于[开始时间]！");
			return;
		}

		frmData.SDATE.value = "<%=exeTime.toString("yyyy-MM-dd")%> "+frmData.MoreTimesDay_SDATE.value;
		frmData.EDATE.value = "<%=exeTime.toString("yyyy-MM-dd")%> "+frmData.MoreTimesDay_EDATE.value;
		//小时、分钟合成间隔时间
		if(!validateInterval(frmData.IntervalHour, frmData.IntervalMinute)) {
			return;
		}
		frmData.PARAM.value = new Number(frmData.IntervalHour.value * 60) + new Number(frmData.IntervalMinute.value);
	}

	WCMAction.doPost(frmData, document.frmAction);
}

function validateTime(_oEl){
	if(_oEl == null) return true;
	var sValue = _oEl.value || "";
	var sDesc = _oEl.elname || "";
	if(sValue == "") {
		CTRSAction_alert("[" + sDesc + "]不能为空！");
		return false;
	}
	var exp = /^\d\d:\d\d$/g;
	if(sValue.match(exp)){
		var pos = sValue.indexOf(":");
		var nHour = new Number(sValue.substring(0, pos));
		var nMin = new Number(sValue.substring(pos+1));
		if(nHour > 23){
			CTRSAction_alert("[" + sDesc + "]小时不能大于23！");
			return false;
		}
		if(nMin > 59){
			CTRSAction_alert("[" + sDesc + "]分钟不能大于59！");
			return false;
		}
	} else {
		if(sValue.length > 0){
			CTRSAction_alert("[" + sDesc + "]格式必须为HH:mm！");
			return false;
		}
	}
	return true;
}

function validateInterval(_oHour, _oMinute){
	if(_oHour == null || _oMinute == null) return;
	var sHourValue = _oHour.value || "";
	var sHourDesc = _oHour.elname || "";
	var sMinuteValue = _oMinute.value || "";
	var sMinuteDesc = _oMinute.elname || "";

	if(sHourValue == "") {
		_oHour.value = "0";
	}
	if(sMinuteValue == "") {
		_oMinute.value = "0";
	}

	if(!CBaseValidator_isNumber(sHourValue)) {
		CTRSAction_alert("[" + sHourDesc + "]不是整数！");
		return false;
	}
	if(!CBaseValidator_isNumber(sMinuteValue)) {
		CTRSAction_alert("[" + sMinuteDesc + "]不是整数！");
		return false;
	}

	if(_oHour.value < 0) {
		CTRSAction_alert("[" + sHourDesc + "]不能小于0！");
		return false;
	}
	if(_oHour.value > 23) {
		CTRSAction_alert("[" + sHourDesc + "]不能大于23！");
		return false;
	}

	if(_oMinute.value < 0) {
		CTRSAction_alert("[" + sMinuteDesc + "]不能小于0！");
		return false;
	}
	if(_oMinute.value > 59) {
		CTRSAction_alert("[" + sMinuteDesc + "]不能大于59！");
		return false;
	}

	if(_oHour.value == 0 && _oMinute.value == 0) {
		CTRSAction_alert("[" + sHourDesc + "]和[" + sMinuteDesc + "不能都为0！");
		return false;
	}
	return true;
}

function transTime(_time) { //转换时间 HH:mm
	var pos = _time.indexOf(":");
	var nHour = new Number(_time.substring(0, pos));
	var nMinute = new Number(_time.substring(pos+1));
	return nHour * 60 + nMinute;
}

function resetForm(){
	var frm = document.all("frmData");
	frm.reset();
}

</SCRIPT>
</HEAD>

<BODY onUnload="unlock(<%=nScheduleId%>,<%=Schedule.OBJ_TYPE%>);">
<FORM NAME="frmAction" ID="frmAction" METHOD=POST ACTION="./schedule_addedit_dowith.jsp" style="margin-top:0">
	<%=currRequestHelper.toHTMLHidden()%>
	<INPUT TYPE="hidden" NAME="ObjectXML" Value="">
</FORM>
<iframe name="ifrmOpArgs" width=0 height=0 src=""></iframe>
<TABLE width="100%" height="350" border="0" cellpadding="0" cellspacing="0"  >
<TR>
<TD width="100%" height="25">
	<SCRIPT src="../js/CWCMDialogHead.js"></SCRIPT>
	<SCRIPT LANGUAGE="JavaScript">
		WCMDialogHead.draw("<%=LocaleServer.getString("schedule.label.scheduleaddedit", "新建/修改调度任务")%>");
	</SCRIPT>
</TD>
</TR>
<TR>
<TD width="100%" align="center" valign="center" class="tanchu_content_td" >
<FORM NAME="frmData" ID="frmData" onSubmit="return false;">
	<TABLE width="100%" border="0" cellspacing="0" cellpadding="10"  >
	<TR>
	<TD width="100%" class="tanchu_content" >
		<TABLE width="100%" border="0" cellspacing="2" cellpadding="0"  >
		<TR>
		<TD width="20%" align="left"><%=LocaleServer.getString("schedule.label.name", "名称")%>：</TD>
		<TD width="*"><INPUT name="SCHNAME" type="text" size="30" elname="<%=LocaleServer.getString("schedule.label.name", "名称")%>" pattern="string" not_null="1" max_len="50" value="<%=PageViewUtil.toHtmlValue(currSchedule.getPropertyAsString("SCHNAME"))%>"> <span class="font_red"><%=LocaleServer.getString("system.label.mustfill", "必填")%></span></TD>
		</TR>
		<TR>
			<TD colspan="2" align="left" height="10">&nbsp;</TD>
		</TR>
		<TR>
		<TD align="left"><%=LocaleServer.getString("schedule.label.desc", "描述")%>：</TD>
		<TD><INPUT name="SCHDESC" type="text" max_len="200" pattern="string" elname="<%=LocaleServer.getString("schedule.label.desc", "描述")%>" value="<%=PageViewUtil.toHtmlValue(currSchedule.getPropertyAsString("SCHDESC"))%>"></TD>
		</TR>
		<TR>
			<TD colspan="2" align="left" height="10">&nbsp;</TD>
		</TR>
		<TR>
		<TD align="left"><%=LocaleServer.getString("schedule.label.opertype", "类型")%>：</TD>
		<TD><%=getWorkTypeSelectHtml(loginUser, currSchedule, currTypeId, bAddMode)%></TD>
		</TR>
		<TR>
			<TD colspan="2" align="left" height="10">&nbsp;</TD>
		</TR>
		<TR id="trOptionParam" name="trOptionParam">
		<TD align="left" valign="top"><%=LocaleServer.getString("schedule.label.operarg", "操作参数")%>：</TD>
		<TD>
		<TABLE id="tbParams" name="tbParams" width="100%" border="0" cellspacing="0" cellpadding="0">
		</TABLE>
		</TD>
		</TR>
		<TR>
			<TD colspan="2" align="left" height="10">&nbsp;</TD>
		</TR>
		<TR>
		<TD align="left"><%=LocaleServer.getString("schedule.label.runmode", "运行模式")%>：</TD>
		<TD>
		<SELECT name="SCHMODE" ONCHANGE="selectChange(this)" elname="运行模式" pattern="string" not_null="1" <%=currSchedule.getWorkerTypeId()==7?"disabled":""%>>
		<OPTION value="<%=Schedule.MODE_ONE_TIME_DAY%>" <%=(nSchMode==Schedule.MODE_ONE_TIME_DAY)?"selected":""%>>每天执行一次</OPTION>
		<OPTION value="<%=Schedule.MODE_MORE_TIMES_DAY%>" <%=(nSchMode==Schedule.MODE_MORE_TIMES_DAY)?"selected":""%>>每天执行多次</OPTION>
		</SELECT>
		</TD>
		</TR>
		<TR>
			<TD colspan="2" align="left" height="10">&nbsp;</TD>
		</TR>
		<TR id="trETime">
			<TD align="left" valign="middle"><%=LocaleServer.getString("schedule.label.exectime", "执行时间")%>：</TD>
			<TD>
			<DIV id="divETime" style="display:inline">
			<SCRIPT>TRSCalendar.drawWithTime("ETIME", "<%=PageViewUtil.toHtmlValue(exeTime.toString("yyyy-MM-dd HH:mm"))%>");</SCRIPT>
			</DIV>
			</TD>
		</TR>
		<TR id="trOneTimeDay" style="display:none">
			<TD align="left" valign="middle"><%=LocaleServer.getString("schedule.label.exectime", "执行时间")%>：</TD>
			<TD>
			<input type="text" name="OneTimeDay" ignore="1" size="6" maxlength="5" elname="<%=LocaleServer.getString("schedule.label.exectime", "执行时间")%>" value="<%=exeTime.toString("HH:mm")%>"> <span class="font_red"><%=LocaleServer.getString("system.label.mustfill", "必填")%></span>（时间格式：HH:mm，如12:24）
			</TD>
		</TR>
		<TR id="trSEDate" name="trSEDate" style="display:none">
			<TD align="left" valign="middle"><%=LocaleServer.getString("schedule.label.starttime", "开始时间")%>：</TD>
			<TD>
			<input type="hidden" name="SDATE" value="<%=PageViewUtil.toHtmlValue(startTime.toString())%>">
			<input type="text" name="MoreTimesDay_SDATE" ignore="1" size="6" maxlength="5" elname="<%=LocaleServer.getString("schedule.label.starttime", "开始时间")%>" value="<%=startTime.toString("HH:mm")%>"> <span class="font_red"><%=LocaleServer.getString("system.label.mustfill", "必填")%></span>（时间格式：HH:mm，如12:24）
			</TD>
		</TR>
		<TR id="trSEDate" name="trSEDate" style="display:none">
			<TD align="left" valign="middle"><%=LocaleServer.getString("schedule.label.endtime", "结束时间")%>：</TD>
			<TD>
			<input type="hidden" name="EDATE" value="<%=PageViewUtil.toHtmlValue(endTime.toString())%>">
			<input type="text" name="MoreTimesDay_EDATE" ignore="1" size="6" maxlength="5" elname="<%=LocaleServer.getString("schedule.label.endtime", "结束时间")%>" value="<%=endTime.toString("HH:mm")%>"> <span class="font_red"><%=LocaleServer.getString("system.label.mustfill", "必填")%></span>（时间格式：HH:mm，如12:24）
			</TD>
		</TR>
		<TR id="trParam" style="display:none">
			<TD align="left" valign="middle"><%=LocaleServer.getString("system.label.interval", "间隔时间")%>：</TD>
			<TD>
			<INPUT TYPE="hidden" NAME="PARAM" VALUE="<%=nInterval%>">
			<INPUT TYPE="text" NAME="IntervalHour" ignore="1" size="2" MAXLENGTH="2" elname="<%=LocaleServer.getString("system.label.interval", "间隔时间")%>(<%=LocaleServer.getString("system.label.hour", "小时")%>)" max_value="23" min_value="-1">
			<%=LocaleServer.getString("system.label.hour", "小时")%>
			<INPUT TYPE="text" NAME="IntervalMinute" ignore="1" size="2" MAXLENGTH="2" elname="<%=LocaleServer.getString("system.label.interval", "间隔时间")%>(<%=LocaleServer.getString("system.label.minute", "分钟")%>)" max_value="60" min_value="1">
			<%=LocaleServer.getString("system.label.minute", "分钟")%>
			<span class="font_red"><%=LocaleServer.getString("system.label.mustfill", "必填")%></span>
			</TD>
		</TR>
		</TABLE>
	</TD>
	</TR>
	<TR>
	<TD align="center" >
		<script src="../js/CTRSButton.js"></script>
		<script>
			//定义一个TYPE_ROMANTIC_BUTTON按钮
			var oTRSButtons = new CTRSButtons();
			
			oTRSButtons.cellSpacing	= "0";
			oTRSButtons.nType	= TYPE_ROMANTIC_BUTTON;

			oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.confirm", "确定")%>", "submitForm()");
			oTRSButtons.addTRSButton("<%=LocaleServer.getString("system.button.cancel", "取消")%>", "unlock(<%=nScheduleId%>,<%=Schedule.OBJ_TYPE%>);window.close();");
			
			oTRSButtons.draw();	
		</script>
	</TD>
	</TR>
	</TABLE>
</FORM>
</TD>
</TR>
</TABLE>
</BODY>
</HTML>

<SCRIPT LANGUAGE="JavaScript">
getOpArgsAndDraw("<%=currTypeId%>");
selectChange(document.all("SCHMODE"));

//设置时间
var nInterval = frmData.PARAM.value;
frmData.IntervalHour.value = (nInterval-nInterval%60)/60;
frmData.IntervalMinute.value = nInterval%60;
</SCRIPT>

<%!
	private String getWorkTypeSelectHtml(User _currUser, Schedule _currSch, int _nTypeId, boolean _bAddMode) throws WCMException {
		int nCurrType = (_currSch==null)?0:_currSch.getWorkerTypeId();
		if(nCurrType == 0){
			nCurrType = _nTypeId;
		}
		WCMFilter filter	= new WCMFilter("", "", "OperId");
		JobWorkerTypes currTypes = JobWorkerTypes.openWCMObjs(_currUser, filter);
		JobWorkerType currType = null;
		int nTypeId = 0;
		StringBuffer sbHtml = new StringBuffer();
		sbHtml.append("<SELECT name=\"OPTYPE\" onchange=\"onChangeType(this)\" elname=\"Worker类型\" pattern=\"string\" not_null=\"1\"");
		//if(!_bAddMode){
		sbHtml.append(" disabled ");
		//}
		sbHtml.append(">");
		for (int i = 0; i < currTypes.size(); i++) {
			currType = (JobWorkerType) currTypes.getAt(i);
			nTypeId = currType.getId();
			if (currType == null || nTypeId == 100)
				continue;

			sbHtml.append("<OPTION value=\"");
			sbHtml.append(nTypeId);
			sbHtml.append("\"");
			if(nTypeId == nCurrType){
				sbHtml.append(" selected ");
			}
			sbHtml.append(">");
			sbHtml.append(currType.getWorkerName());
			sbHtml.append("</OPTION>");
		}
		sbHtml.append("</SELECT>");

		return sbHtml.toString();
	}
%>