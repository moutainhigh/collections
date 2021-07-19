<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="720" height="280">
<head>
<title>查询服务用户列表</title>
<link href="<%=request.getContextPath()%>/script/css/cupertino/jquery.ui.all.css" rel="stylesheet" type="text/css">
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery-ui.custom.min.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery.ui.timepicker.js"></script>
<link href="<%=request.getContextPath()%>/script/lib/jquery.ui.timepicker.css" rel="stylesheet" type="text/css">


<%
DataBus db = (DataBus)request.getAttribute("freeze-databus");
%>
</head>
<script language="javascript">
var limitWeek = new Array;
var limitCache = {
			   'limitTime':'0'
			 , 'limitNumber':'0'
			 , 'limitWeek':'0'
			 , 'limitTotal':'0'
			 , 'stime':'00:00'
			 , 'etime':'24:00'
			 , 'number': 0 
			 , 'total': 0
			 , 'limitWeekArray':[] }
var weeks = ['x','星期一','星期二','星期三','星期四','星期五','星期六','星期日'];	
var svrId = "<%= request.getParameter("svrId") %>";
var userId = "<%= request.getParameter("userId")%>";

function getLimitInfo(){
	var page = new pageDefine("/txn50011701.ajax", "查询某个用户对应某个服务的限定信息");
	page.addValue( userId, "select-key:sys_svr_user_id" );
	page.addValue( svrId, "select-key:sys_svr_service_id" );
	page.callAjaxService("callBack()");
}

function callBack(errCode, errDesc, xmlResults){
		if(errCode!='000000'){
			alert("查询某个用户对应某个服务的限定信息时发生错误！")
			return
		}
     
	 var is_limit_week =  _getXmlNodeValues(xmlResults,'record:is_limit_week');
	 var is_limit_time = _getXmlNodeValues(xmlResults,'record:is_limit_time');
	 var is_limit_number = _getXmlNodeValues(xmlResults,'record:is_limit_number');
	 var is_limit_total = _getXmlNodeValues(xmlResults,'record:is_limit_total');
	 
	 var limit_week = _getXmlNodeValues(xmlResults,'record:limit_week');
	 var limit_start_time = _getXmlNodeValues(xmlResults,'record:limit_start_time');
	 var limit_end_time = _getXmlNodeValues(xmlResults,'record:limit_end_time');
	 var limit_number = _getXmlNodeValues(xmlResults,'record:limit_number');
	 var limit_total = _getXmlNodeValues(xmlResults,'record:limit_total');
	
	 
	if(is_limit_week!=null&&is_limit_week.length>0){
		for(var j=0; j<is_limit_week.length; j++){
			var day = limit_week[j];
			var flag = is_limit_week[j];
			if(flag&&flag=='1'){
				limitWeek.push(day);
			}
			limitCache.limitTime = is_limit_time[0];
			limitCache.limitNumber = is_limit_number[0];
			limitCache.limitTotal = is_limit_total[0];
			limitCache.stime = limit_start_time[0];
			limitCache.etime = limit_end_time[0];
			limitCache.number = limit_number[0];
			limitCache.total = limit_total[0];
		}
		limitCache.limitWeekArray = limitWeek;
	}
	//alert("  limitTime is  " + limitCache.limitTime + " \n  limitNumber is "+ limitCache.limitNumber +" \n  limitWeek is "+ limitCache.limitWeek+ "\n stime is " + limitCache.stime + "\n limitTotal is "+ limitCache.total + " \n limitNumber is " + limitCache.number +" \n limit Days is "+ limitCache.limitWeek.join(','))
	initWeek();
		
	initTime();
	
	initNumber();
	
	initTotal();
	
}

function initWeek(){
	var e = limitCache.limitWeekArray;
	var estr = ","+e.join(",")+","
	var ds = "";
	if( limitCache.limitWeekArray&&limitCache.limitWeekArray.length==0 ){
		ds="disabled"
	}else{
	     $('#limitWeekBtn').val('1').attr('checked', true);
	}
	
	var str = ""
	for(var j=1; j<8; j++){
	
		if(estr.indexOf(","+j+",")>=0){
			str+=" <input type=checkbox onFocus='this.blur();' name='weekDay' value='"+j+"' "+ds+"/> "+ weeks[j]+"";
		}else{
			str+=" <input type=checkbox onFocus='this.blur();' name='weekDay' value='"+j+"' "+ds+" checked /> "+ weeks[j]+"";
		}
	}
	$('#weekTd').html(str);
}

function initTime(){
	var e = limitCache.limitTime;
	var st = limitCache.stime;
	var et = limitCache.etime;
	if(e&&e=='1'){
		$('#limitTimeBtn').attr('checked',true);
		$('#limitTimeBtn').val('1');
	}else if(e&&e=="0"){
		$('#limitTimeBtn').attr('disabled',false);
		$('#stime').attr('disabled',true);
		$('#etime').attr('disabled',true);
	}
	
	$('#stime').val(st);
	$('#etime').val(et);
	
	$('#stime').timepicker({
        showLeadingZero: false,
        showCloseButton: true,
        showNowButton:true
    });
    $('#etime').timepicker({
        showLeadingZero: false
    });
    
}

function initNumber(){
	var e = limitCache.limitNumber;
	if(e&&e=="1"){
		$('#limitNumberBtn').attr('checked',true);
		$('#limitNumberBtn').val('1');
		$('#limitNumber').val( limitCache.number  )
	}else if(e&&e=="0"){
		$('#limitNumber').attr('disabled',true);
	}
}

function initTotal(){
	var e = limitCache.limitTotal;
	if(e&&e=="1"){
		$('#limitTotalBtn').attr('checked',true);
		$('#limitTotalBtn').val('1');
		$('#limitTotal').val( limitCache.total )
	}else if(e&&e=="0"){
		$('#limitTotal').attr('disabled',true);
	}
}

_browse.execute(function(){
	getLimitInfo();
	
	$('#btn_close').click(function(){
		window.close();
	})
	
});

//验证是否正整数
function isPositiveInteger( str ){
	var regu = /^[1-9]\d*$/;
	return regu.test(str);
}

function validate(){
	var flag = true;
	//在这里加验证
	var limitNumber = $('#limitNumber').val();
	var limitTotal = $('#limitTotal').val();
	if($("input[name='weekDay']:checked").length==0){
	    alert("开放时间至少选择一天!");
		return;
	}
	//校验限定次数
	if($('#limitNumberBtn').val()=="1"){
		if(limitNumber==""){
			alert("【限定次数】不应为空值！");
			flag = false;
			return flag;
		}else
		{
			if(!isPositiveInteger(limitNumber)){
				alert("【限定次数】应为正整数！");
				flag = false;
				return flag;
			}
		}
	}
	
	//校验限定条数
	if($('#limitTotalBtn').val()=="1"){
		if(limitTotal==""){
			alert("【限定总数】不应为空值！");
			flag = false;
			return flag;
		}else
		{
			if(!isPositiveInteger(limitTotal)){
				alert("【限定总数】应为正整数！");
				flag = false;
				return flag;
			}
		}
	}
	var stime = $('#stime').val();
	var etime = $('#etime').val();
	if(stime!=''&&etime!=''){
		if(parseInt(stime.replace(":",""))>parseInt(etime.replace(":",""))){
		   alert("【限定时间】结束时间不能早于开始时间");
		   flag = false;
		   return flag;
		}
	}
	return flag;
}
// 保存并关闭
function func_record_saveAndExit()
{
	if(!validate()){
		return;
	}
	
	var weeks = new Array;
	$("input[name='weekDay']:checked").each(function(){
		weeks.push( $(this).val() )
	})
	var limitWeek  = $('#limitWeekBtn').val();
	var limitTime = $('#limitTimeBtn').val();
	var limitNumber = $('#limitNumberBtn').val();
	var limitTotal = $('#limitTotalBtn').val();
	var stime = $('#stime').val();
	if(stime&&stime.length==4){
		stime = "0"+stime;
	}
	var etime = $('#etime').val();
	if(etime&&etime.length==4){
		etime = "0"+etime;
	}
	
	var number = $('#limitNumber').val();
	var total = $('#limitTotal').val();
	if(stime==""){stime="00:00"};
	if(etime==""){etime="24:00"};
	
	//alert("limitWeek:"+limitWeek +" \n limitTime "+limitTime+" \n limitNumber "+limitNumber +" \n limitTotal "+limitTotal +" \n stime "+stime+" \n etime "+etime +" \n number "+number +" \n total "+total +" \n can visit weeks is "+weeks.join(","));
		
	var page = new pageDefine("/txn50011706.ajax", "配置某个用户对应某个服务的限定信息");
	page.addValue( userId, "record:sys_svr_user_id" );
	page.addValue( svrId, "record:sys_svr_service_id" );
	page.addValue( limitWeek, "record:is_limit_week_flag" );
	page.addValue( limitTime, "record:is_limit_time" );
	page.addValue( limitNumber, "record:is_limit_number" );
	page.addValue( limitTotal, "record:is_limit_total" );
	
	page.addValue( stime, "record:limit_start_time" );
	page.addValue( etime, "record:limit_end_time" );
	
	if($('#limitNumberBtn').val()=="0"){
		number = "";
	}
	page.addValue( number, "record:limit_number" );
	
	if($('#limitTotalBtn').val()=="0"){
		total ="";
	}
	page.addValue( total, "record:limit_total" );
	page.addValue( weeks.join(";"), "record:visit_weeks" );
	
	page.callAjaxService("callBack2()");
	
}
function callBack2(errCode, errDesc, xmlResults){
		if(errCode!='000000'){
			alert("保存某个用户对应某个服务的限定信息时发生错误！")
			return
		}
		alert("设置成功！");
		//刷新树节点
		var a  =  window.dialogArguments
		if(a){
			 a[0].resetParentTree('xxx',svrId,userId);
		}  
		//关闭页面
	   window.close();
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn50201001.do
}

function setLimitWeek(){
	if( $('#limitWeekBtn').val()==0 ){
		 $('#limitWeekBtn').val(1);
		 $("input[name='weekDay']").attr('disabled',false);
	}else{
		$('#limitWeekBtn').val(0);
		$("input[name='weekDay']").attr('disabled',true);
	}
}
function setlimitTime(){
	if( $('#limitTimeBtn').val()==0 ){
		 $('#limitTimeBtn').val(1);
		 $("#stime").attr('disabled',false);
		 $("#etime").attr('disabled',false);
	}else{
		$('#limitTimeBtn').val(0);
		$("#stime").attr('disabled',true);
		$("#etime").attr('disabled',true);
	}
}
function setnumber(){
	if( $('#limitNumberBtn').val()==0 ){
		 $('#limitNumberBtn').val(1);
		 $("#limitNumber").attr('disabled',false);
	}else{
		$('#limitNumberBtn').val(0);
		$("#limitNumber").attr('disabled',true);
	}
}

function setTotal(){
	if( $('#limitTotalBtn').val()==0 ){
		 $('#limitTotalBtn').val(1);
		 $("#limitTotal").attr('disabled',false);
	}else{
		$('#limitTotalBtn').val(0);
		$("#limitTotal").attr('disabled',true);
	}
}
</script>
<freeze:body>
<freeze:errors/>
<freeze:title caption="限制条件" />
<table width="95%" border="0" align="center" class="frame-body" cellpadding="0" cellspacing="0"> 
			    <tr><td >
						<table width="100%" cellspacing="0" cellpadding="0" border="0">
							<tr><td class="leftTitle"></td><td class="secTitle">&nbsp;&nbsp;设置用户服务访问限制条件</td><td class="rightTitle"></td></tr>
						</table>
					</td>
				</tr>
			   
<tr><td style="padding:0;">	
<table width="100%" border="0" style="border-collapse:collapse;border:2px solid #006699;" align="center" class="frame-body" cellpadding="0" cellspacing="0"> 
	  <tr class="framerow">
		         	<td class="odd1_b" align="left" width="150px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;限定星期<input id='limitWeekBtn' onFocus='this.blur();' type='checkbox' value='0' onclick="setLimitWeek()" /></td>
		         	<td class="odd1_b" id="weekTd" ></td>
	  </tr>
	  <tr class="framerow">
		         	<td class="odd2_b" align="left" width="150px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;限定时间<input id='limitTimeBtn' onFocus='this.blur();' type='checkbox' value='0' onclick="setlimitTime();"  /></td>
		         	<td class="odd2_b" align='left' >从 <input id="stime" readonly="true" type="text" value='00:00' size='6' />&nbsp;到 &nbsp; <input id="etime" readonly="true" type="text" value="24:00" size='6' /></td>
	 </tr>
	 <tr class="framerow">
		         	<td class="odd1_b" align="left" width="150px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;限定次数<input id='limitNumberBtn' onFocus='this.blur();' type='checkbox' value='0' onclick="setnumber();"  /></td>
		         	<td class="odd1_b" align='left' >每天&nbsp;<input id="limitNumber"  type="text"  size='6' />&nbsp;次</td>
	 </tr>
	 <tr class="framerow">
		         	<td class="odd2_b" align="left" width="150px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;限定总数<input id='limitTotalBtn' onFocus='this.blur();' type='checkbox' value='0' onclick="setTotal();" /></td>
		         	<td class="odd2_b" align='left'>每天&nbsp;<input id='limitTotal' type="text"  size='6' />&nbsp;条</td>
	 </tr>
<tr><td align="center" colspan="2" class="odd1_b">
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
			      			<input name="btn_config3" id='btn_config3' class="menu" onclick="func_record_saveAndExit();"  style="width: 50px;" type="button" value="保 存"/>
			      		</td>
						<td class="btn_right"></td>
					</tr>
				 </table>
			     <table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
			      			<input name="btn_config4" id='btn_config4' class="menu"  onclick="func_record_goBack();" style="width: 50px;" type="button" value="关 闭"/>
			      		</td>
						<td class="btn_right"></td>
					</tr>
				</table>
</td></tr></table>	
<freeze:frame property="record"> 
	<freeze:hidden property="limit_number"  datatype="string" maxlength="200"/>
    <freeze:hidden property="limit_total" datatype="string" maxlength="200"/>
</freeze:frame>
</freeze:body>
</freeze:html>
