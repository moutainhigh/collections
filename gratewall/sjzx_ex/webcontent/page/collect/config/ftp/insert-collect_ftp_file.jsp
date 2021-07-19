<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%-- template single/single-table-insert.jsp --%>
<freeze:html width="750" height="350">
<head>
<title>增加采集数据库信息</title>

<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery1.1.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery/jquery-1.7.1.min.js"></script>

</head>

<script language="javascript">

function checkInput(){
	var file_name_en=getFormFieldValue('record:file_name_en');
	if(!file_name_en){
		alert("输入域【文件英文名称】必须输入有效名称!");
		return false;
	}
	var collect_mode=getFormFieldValue('record:collect_mode');
	if(!collect_mode){
		alert("输入域【采集方式】必须选择一个有效数据!");
		return false;
	}
	var nameType= getFormFieldValue('record:name_type');
	var check=/^((-\d+)|(0+))$/; //非正整数
	if(nameType=='D'){
		var day_num=getFormFieldValue('record:day_num');
		if(day_num.length>5){
			alert("输入域【日期偏移】超过限制长度");
			return false;
		}
		if(!check.test(day_num)){
			alert("输入域【日期偏移】要求输入一个负整数或0");
			return false;
		}
		
	}else if(nameType=='M'){
		var month=getFormFieldValue('record:month');
		if(month.length>5){
			alert("输入域【月份】超过限制长度");
			return false;
		}
		if(!check.test(month)){
			alert("输入域【月份】要求输入一个负整数或0");
			return false;
		}
		var day_month=getFormFieldValue('record:day_month');
		if(!day_month){
			alert("输入域【日期】请选择一个有效数据");
			return false;
		}
	}else{
		alert("输入域【后缀命名方式】有误！");
		return false;
	}
	return true;
}

// 保存并关闭
function func_record_saveAndExit()
{
	if(!checkInput()){
		return;
	}
	
	var page = new pageDefine( "/txn30101107.ajax","保存");
	page.addParameter("record:collect_task_id","select-key:collect_task_id");
	page.addParameter("record:task_name","select-key:task_name");
	page.addParameter("record:flag","select-key:flag");
	page.addParameter("record:service_targets_id","select-key:service_targets_id");
	page.addParameter("record:ftp_task_id","select-key:ftp_task_id");
	page.addParameter("record:file_name_en","record:file_name_en");
	page.addParameter("record:file_name_cn","record:file_name_cn");
	page.addParameter("record:file_description","record:file_description");
	page.addParameter("record:collect_table","record:collect_table");
	page.addParameter("record:collect_mode","record:collect_mode");
	page.addParameter("record:file_sepeator","record:file_sepeator");
	page.addParameter("record:file_title_type","record:file_title_type");
	page.addParameter("record:month","record:month");
	page.addParameter("record:day_month","record:day_month");
	page.addParameter("record:name_type","record:name_type");
	page.addParameter("record:day_num","record:day_num");
	page.callAjaxService("checkTaskBack");
	
}

function checkTaskBack(errorCode, errDesc, xmlResults){
	if(errorCode!="000000"){
		alert(errDesc);
	}else{
		/* var service_targets_id=getFormFieldValue('record:service_targets_id');
		var collect_task_id=getFormFieldValue('record:collect_task_id');
		var task_name=getFormFieldValue('record:task_name');
		var flag=getFormFieldValue('record:flag');
		var href='/txn30101110.do?record:service_targets_id='+service_targets_id
				+'&record:collect_task_id='+collect_task_id
				+'&record:task_name='+task_name
				+'&record:flag='+flag; */
		//parent.window.location.href=href;
		window.dialogArguments.func_refresh();
		//saveAndExit("","",href);
		window.close();
		
		
			
	}
}

window.onbeforeunload = function() { 
	window.dialogArguments.func_refresh();
	return ; 
} 
// 返 回
function func_record_goBack()
{
	window.dialogArguments.func_refresh();
	window.close();
	//goBack();	// /txn30501001.do
}

function getParameter(){
	//从当前页面取值，组成key=value格式的串
    var parameter='input-data:service_targets_id='+getFormFieldValue('record:service_targets_id');
	return parameter;
}

function setNameType(obj){
	var nameType=$(obj).val();
	if(nameType=='D'){
		$('#row_7').hide();
		$('#row_8').show();
		
	}else if(nameType=='M'){
		$('#row_8').hide();
		$('#row_7').show();
	}
}
// 请在这里添加，页面加载完成后的用户初始化操作
function __userInitPage()
{
	$(".radioNew").hide();
	$(".radioNew").prev().show();
	var nameType= getFormFieldValue('record:name_type');
	if(nameType=='D'){
		$('#row_7').hide();
		$('#row_8').show();
		
	}else if(nameType=='M'){
		$('#row_8').hide();
		$('#row_7').show();
	}
}

_browse.execute( '__userInitPage()' );
</script>
<freeze:body>
<freeze:title caption="FTP采集文件信息"/>
<freeze:errors/>

<freeze:form action="/txn30101107">

  <freeze:block property="record" caption="FTP采集文件信息" width="95%">
      <freeze:button name="record_saveAndExit" caption="保存并关闭" hotkey="SAVE_CLOSE" onclick="func_record_saveAndExit();"/>
      <freeze:button name="record_goBack" caption="返 回" hotkey="CLOSE" onclick="func_record_goBack();"/>
      <freeze:cell property="task_name" caption="采集任务名称" colspan="2" style="width:95%"/>
      <freeze:text property="file_name_en" caption="文件英文名称" colspan="2" notnull="true" datatype="string" maxlength="100"  style="width:95%"/>
      <freeze:text property="file_name_cn" caption="文件中文名称" colspan="2" datatype="string" maxlength="100"  style="width:95%"/>
      
      <freeze:textarea property="file_description" caption="文件描述" colspan="2" rows="2" maxlength="2000" style="width:95%"/>
      <freeze:text property="file_sepeator" caption="列分隔符" notnull="true" datatype="string" maxlength="10"  style="width:95%"/>
      <freeze:browsebox property="file_title_type" caption="标题行类型" notnull="true" show="name"  valueset="标题行类型" style="width:95%"/>
      
      <freeze:browsebox property="collect_table" caption="采集表" align="center"  valueset="资源管理_服务对象对应采集表" show="name" parameter="getParameter();" style="width:95%" />
      <freeze:browsebox property="collect_mode" caption="采集方式" align="center" notnull="true" show="name" valueset="资源管理_采集方式"  show="name" style="width:95%"/>
      
      <freeze:radio property="name_type" caption="后缀命名方式"  value="M" colspan="2" valueset="文件日期后缀命名方式"  style="width:95%" onclick="setNameType(this);" ></freeze:radio>
     
      <freeze:text property="month" caption="月份"  notnull="false" title="请输入一个不大于0的整数,0表示当月,-1表示上一个月，依此类推"   style="width:95%;"  />
      <freeze:browsebox property="day_month" caption="日期" align="center" notnull="false" show="name" valueset="资源管理_日期选择"  show="name" style="width:95%"/>
      <freeze:text property="day_num" caption="日期偏移"  notnull="false" title="请输入一个不大于0的整数,0表示当天,-1表示前一天，依此类推"   style="width:95%;"  />
      
      <freeze:hidden property="ftp_task_id" caption="主键ID" datatype="string" maxlength="32" />
      <freeze:hidden property="collect_task_id" caption="采集任务ID" style="width:95%"/>
      
      <freeze:hidden property="service_targets_id" caption="服务对象ID" datatype="string" maxlength="32"  style="width:80%"/>
	  <freeze:hidden property="flag" caption="标记是新增还是编辑"  notnull="true" />	
  </freeze:block>

</freeze:form>
</freeze:body>
</freeze:html>
