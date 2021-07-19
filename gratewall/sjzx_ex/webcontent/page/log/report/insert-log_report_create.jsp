<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery.min.js"></script>
<freeze:html width="650" height="350">
<head>
<title>增加系统使用情况报告信息</title>
</head>

<script language="javascript">

// 保 存
function func_record_saveRecord()
{
	
	saveRecord( '', '保存日志报告生成' );
}

// 保存并继续
function func_record_saveAndContinue()
{
	saveAndContinue( '', '保存日志报告生成' );
}

// 保存并关闭
function func_record_saveAndExit()
{
	var syear = getFormFieldValue("record:year");
	var smonth = getFormFieldValue("record:month");
	var month = parseInt(smonth);
	if(month<10){
		smonth = "0"+smonth;
	}
	var date = syear+smonth;
	var year = parseInt(date);
	
	if(year < 201307)
	{
		alert("用户您好，系统只能生成2013年6月以后的月报和截止到上月的月报！");
		return;
	}else{
		checkReportName();	
	}
	
	//saveAndExit( '', '保存日志报告生成' );
}

function checkReportName(){
	var reportName = getFormFieldValue("record:report_name");
	var page = new pageDefine("/txn620200110.ajax", "校验报告");
	page.addValue(reportName, "select-key:report_name" );
	page.callAjaxService("callBack");

}

function callBack(errCode, errDesc ,xmlResults ){
	if(errCode!='000000'){
	    if(errCode=='999999'){
		    alert("报告已经存在！")
		    return false; 
	    }else{
	    	 alert("生成报告时发生错误！")
			 return false; 
	    }
		
	}
	saveAndExit( '', '保存日志报告生成' );
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn620200101.do
}
var months = ['一','二','三','四','五','六','七','八','九','十','十一','十二'];

//月份变动
function changeMonth(){
	var type = getFormFieldValue("record:report_type");
	var type_name;
	if(type=='1'){
		type_name='月报';
	}else if(type='0'){
		type_name='年报';
	}else{
		type_name='';
	}
	var syear = getFormFieldValue("record:year");
	var smonth = getFormFieldValue("record:month");
    var reportName ='系统使用情况报告'+syear+'年'+smonth+'月'+type_name;
	setFormFieldValue("record:report_name",reportName);
}

//年份变动
function changeYear(){
	var date = new Date();
	var year = date.getYear();
	var month = date.getMonth();
    var syear = getFormFieldValue("record:year");
	var smonth = getFormFieldValue("record:month");
	var monthObj = document.getElementById("record:month");
	if(year==syear){
	   //删除所有元素
	   monthObj.options.length=0;
	   for(var i = 1; i <= month; i++){
			var option = document.createElement("option");
			option.appendChild(document.createTextNode(months[i-1]+"月"));
			if(i < 10){
				i = ""+i;
			}
			option.value = i;
			if(smonth == i){
				option.selected = true;
			}
			monthObj.appendChild(option);
	  }
	}else{
	  //删除所有元素
      monthObj.options.length=0;
		  for(var i = 1; i <= 12; i++){
			var option = document.createElement("option");
			option.appendChild(document.createTextNode(months[i-1]+"月"));
			if(i < 10){
				i = ""+i;
			}
			option.value = i;
			if(smonth == i){
				option.selected = true;
			}
			monthObj.appendChild(option);
		  }
	}
	//alert(monthObj.options.length);
	syear = getFormFieldValue("record:year");
	smonth = getFormFieldValue("record:month");
	var reportName ='系统使用情况报告'+syear+'年'+smonth+'月月报';
	setFormFieldValue("record:report_name",reportName);

}

// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	var yearObj = document.getElementById("record:year");
	yearObj.innerHTML = "";
	var date = new Date();
	var year = date.getYear();
	var month = date.getMonth();
	for(var i = parseInt(year-1); i <= year; i++){
		var option = document.createElement("option");
		option.value = i;
		option.appendChild(document.createTextNode(i+"年"));
		if(year == i){
			option.selected = true;
		}
		yearObj.appendChild(option);
	}
	//初始化月份
    var monthObj = document.getElementById("record:month");
    monthObj.options.length=0;
	for(var i = 1; i <= month; i++){
			var option = document.createElement("option");
			option.appendChild(document.createTextNode(months[i-1]+"月"));
			if(i < 10){
				i = ""+i;
			}
			option.value = i;
			if(month == i){
				option.selected = true;
			}
			monthObj.appendChild(option);
	  }
	if(month!='0'){
		var reportName ='系统使用情况报告'+year+'年'+month+'月月报';
		setFormFieldValue("record:report_name",reportName);
	}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="增加系统使用情况报告信息"/>
<freeze:errors/>

<freeze:form action="/txn620200103">
  <freeze:block property="record" caption="增加系统使用情况报告信息" width="95%">
      <freeze:button name="record_saveRecord" caption="生 成" hotkey="SAVE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:hidden property="log_report_create_id" caption="日志报告id" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="report_type" caption="报告类型" colspan="2" value="1" valueset="报告类型" style="width:95%" onchange="setReportName();"/>
      <freeze:select property="year" caption="报告年份" style="width:88%" onchange="changeYear();"/>
      <freeze:select property="month" caption="报告月份" style="width:88%" onchange="changeMonth();"/>
      <freeze:text property="report_name" caption="报告名称" colspan="2" style="width:95%" readonly="true"/>
      <freeze:hidden property="create_date" caption="建立日期" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:hidden property="last_mender" caption="最后修改者" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="publish_date" caption="发布日期" datatype="string" maxlength="10" style="width:95%"/>
      <freeze:hidden property="publish_person" caption="发布人" datatype="string" maxlength="100" style="width:95%"/>
      <freeze:hidden property="state" caption="状态" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="operate" caption="操作" datatype="string" maxlength="1" style="width:95%"/>
      <freeze:hidden property="filename" caption="文件名" maxlength="1000" style="width:98%"/>
      <freeze:hidden property="path" caption="路径" maxlength="4000" style="width:98%"/>
      <freeze:hidden property="timestamp" caption="时间戳" datatype="string" maxlength="32" style="width:95%"/>
  </freeze:block>
</freeze:form>
</freeze:body>
</freeze:html>
