<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@page import="com.gwssi.common.constant.ExConstant"%>
<%-- template single/single-table-update.jsp --%>
<freeze:html width="1000" height="850">
<head>
<title>修改共享ftp服务信息</title>
<style>
.frame-body{table-layout: auto !important;width:95%}
.pages .frame-body{table-layout: fixed;width:100%}

</style>



<script src="<%=request.getContextPath() %> /script/common/js/validator.js"></script>
</head>
<script language="javascript">


// 保 存
function func_record_saveRecord(){

	//saveRecord( '', '保存共享ftp服务' );
	
	var item=getFormFieldValue('record1:datasource_id');
  	if(!checkItem(item,"100","数据源")){
  		    return false;
  	}
  	item=getFormFieldValue('record1:file_name');
  		if(!checkItem(item,"50","文件名称")){
  		  return false;
  	}
  	item=getFormFieldValue('record1:file_type');
  	if(!checkItem(item,"100","文件类型")){
  		    return false;
  	}
  	/* 
  	var nameType= getFormFieldValue('record1:name_type');
	var check=/^((-\d+)|(0+))$/; //非正整数
	if(nameType=='D'){
		var day_num=getFormFieldValue('record1:day_num');
		if(day_num.length>5){
			alert("输入域【日期偏移】超过限制长度");
			return false;
		}
		if(!check.test(day_num)){
			alert("输入域【日期偏移】要求输入一个负整数或0");
			return false;
		}
		
	}else if(nameType=='M'){
		var month=getFormFieldValue('record1:month');
		if(month.length>5){
			alert("输入域【月份】超过限制长度");
			return false;
		}
		if(!check.test(month)){
			alert("输入域【月份】要求输入一个负整数或0");
			return false;
		}
		var day_month=getFormFieldValue('record1:day_month');
		if(!day_month){
			alert("输入域【日期】请选择一个有效数据");
			return false;
		}
	}else{
		alert("输入域【后缀命名方式】有误！");
		return false;
	}
  	 */
  	
	var page = new pageDefine("/txn40401002.ajax", "保存共享ftp服务");	 ///　
	page.addParameter("record1:ftp_service_id","record1:ftp_service_id");
	page.addParameter("record1:service_id","record1:service_id");
	page.addParameter("record1:service_no","record1:service_no");
	page.addParameter("record1:service_name","record1:service_name");
	page.addParameter("record1:service_targets_id","record1:service_targets_id");
	page.addParameter("record1:datasource_id","record1:datasource_id");
	page.addParameter("record1:file_name","record1:file_name");
	page.addParameter("record1:file_type","record1:file_type");
	page.addParameter("record1:srv_scheduling_id","record1:srv_scheduling_id");
	page.callAjaxService('saveData');
	
}
function saveData(errCode,errDesc,xmlResults){
		
		if(errCode != '000000'){
			alert('处理错误['+errCode+']==>'+errDesc);
			return;
		}else{
		   $('#reload')[0].click();
		}
}

// 返 回
function func_record_goBack()
{
	goBack();	// /txn40401001.do
	
}



//定制采集周期
function chooseCjzq(){
var ftp_service_id=getFormFieldValue('record1:ftp_service_id');
	if(ftp_service_id==null||ftp_service_id==""){
	    alert("请先填写服务信息!");
	    clickFlag=0;
	    return false;
    }
	window.showModalDialog("/page/share/ftp/scheduling/insert-share_srv_scheduling.jsp", window, "dialogHeight:350px;dialogWidth:500px;dialogTop:250px;dialogLeft:300px;help:no;scrollbar:no;resizable:no;status:no");
	
}

function getParameter(){
	//从当前页面取值，组成key=value格式的串
	var collectType = "<%=ExConstant.TYPE_SJYLX_FTP%>";
	
	var service_targets_id=getFormFieldValue('record1:service_targets_id');
    var parameter = "input-data:service_targets_id="+ service_targets_id+"&input-data:collectType="+collectType;
	return parameter;
}


function setNameType(obj){
	var nameType=$(obj).val();
	if(nameType=='D'){
		$('#row_3').hide();
		$('#row_4').show();
		
	}else if(nameType=='M'){
		$('#row_4').hide();
		$('#row_3').show();
	}
}

//请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage1()
{
/* 	$(".radioNew").hide();
	$(".radioNew").prev().show();
	var nameType= getFormFieldValue('record1:name_type');
	//alert(nameType);
	if(nameType=='D'){
		$('#row_3').hide();
		$('#row_4').show();
		
	}else if(nameType=='M'){
		$('#row_4').hide();
		$('#row_3').show();
	} */
}

_browse.execute( '__userInitPage1()' );
</script>
<freeze:body>
<freeze:title caption="ftp共享服务配置"/>
<freeze:errors/>

<freeze:form action="/txn40401002">
  <freeze:frame property="primary-key" width="95%">
      <freeze:hidden property="ftp_service_id" caption="FTP服务ID" style="width:95%"/>
  </freeze:frame>

  <freeze:block property="record1" caption="修改共享ftp服务信息" width="95%">
      <freeze:button name="record_saveRecord" caption="保存" hotkey="SAVE" onclick="func_record_saveRecord();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="window.close();"/>
      <freeze:hidden property="ftp_service_id" caption="FTP服务ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="service_id" caption="服务ID" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="service_no" caption="服务编号" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="service_name" caption="服务名称" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:hidden property="service_targets_id" caption="服务对象" datatype="string" maxlength="32" style="width:95%"/>
      <freeze:browsebox property="datasource_id" caption="数据源" show="name" valueset="资源管理_服务对象对应数据源" notnull="true" parameter="getParameter()"  style="width:95%"/>
      <freeze:text property="scheduling_day1" caption="服务调度" datatype="string" readonly="true" style="width:80%"/>&nbsp;<INPUT TYPE="button" Value="设置" onclick="chooseCjzq()" class="FormButton">
      <freeze:text property="file_name" caption="文件名称" datatype="string" maxlength="100" notnull="true" style="width:95%"/>
      <freeze:select property="file_type" caption="文件类型" valueset="共享服务_文件类型" value="01" notnull="true" style="width:95%"/>
     <%--  
      <freeze:radio property="name_type" caption="后缀命名方式"  value="M" colspan="2" valueset="文件日期后缀命名方式"  style="width:95%" onclick="setNameType(this);" ></freeze:radio>
      <freeze:text property="month" caption="月份"  notnull="false" title="请输入一个不大于0的整数,0表示当月,-1表示上一个月，依此类推"   style="width:95%;"  />
      <freeze:browsebox property="day_month" caption="日期" align="center" notnull="false" show="name" valueset="资源管理_日期选择"  show="name" style="width:95%"/>
      <freeze:text property="day_num" caption="日期偏移"  notnull="false" title="请输入一个不大于0的整数,0表示当天,-1表示前一天，依此类推"   style="width:95%;"  />
      --%> 
      
      <freeze:hidden property="scheduling_type" caption="计划任务类型"  />
      <freeze:hidden property="start_time" caption="计划任务开始时间"  />
      <freeze:hidden property="end_time" caption="计划任务结束时间"  />
      <freeze:hidden property="scheduling_week" caption="计划任务周天"  />
      <freeze:hidden property="scheduling_day" caption="计划任务日期"  />
      <freeze:hidden property="scheduling_count" caption="计划任务执行次数"  />
      <freeze:hidden property="interval_time" caption="每次间隔时间"  />
      <freeze:hidden property="srv_scheduling_id" caption="服务调度" />
  </freeze:block>
</freeze:form>
<freeze:include href="/page/share/ftp/service/update-share_ftp_patameter.jsp"></freeze:include>
</freeze:body>
</freeze:html>
